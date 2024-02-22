package net.geedge.modules.sys.shiro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import net.geedge.common.utils.Constant;
import net.geedge.common.utils.Tool;
import net.geedge.modules.sys.dao.SysRoleDao;
import net.geedge.modules.sys.dao.SysRoleMenuDao;
import net.geedge.modules.sys.dao.SysUserDao;
import net.geedge.modules.sys.dao.SysUserRoleDao;
import net.geedge.modules.sys.entity.SysMenuEntity;
import net.geedge.modules.sys.entity.SysRoleEntity;
import net.geedge.modules.sys.entity.SysRoleMenuEntity;
import net.geedge.modules.sys.entity.SysUserEntity;
import net.geedge.modules.sys.entity.SysUserRoleEntity;
import net.geedge.modules.sys.service.SysConfigService;
import net.geedge.modules.sys.service.SysMenuService;

/**
 * 认证
 */
@Component
public class UserRealm extends AuthorizingRealm {

	private static final Log log = Log.get();
	@Autowired
	private SysUserDao sysUserDao;

	@Autowired
	private SysRoleDao sysRoleDao;

	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;

	@Autowired
	private SysMenuService sysMenuService;

	@Autowired
	private SysConfigService sysConfigService;

	@Autowired
	private SysUserRoleDao sysUserRoleDao;

	@Value("${ldap.default.mapping:{\"email\":\"mail\",\"username\":\"uid|sAMAccountName|cn\"}}")
	private String defaultMappingStr;

	private static final String LDAP_MAPPING_USERNAME_KEY = "username";

	/**
	 * 授权(验证权限时调用)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SysUserEntity user = (SysUserEntity) principals.getPrimaryPrincipal();

		// 查看用户所有角色
		List<SysRoleEntity> rolesByUesr = null;
		
		if(Tool.ObjectUtil.isNotEmpty(user.getApiKeyId())) {
			rolesByUesr = sysRoleDao.getRolesBySysApiKeyId(user.getApiKeyId());
		}else {
			rolesByUesr = sysRoleDao.getRolesByUesrId(user.getId().intValue());
		}

		List<String> permsList;
		if (CollectionUtils.isEmpty(rolesByUesr)) {
			permsList = Collections.emptyList();
		} else {
			SysRoleEntity buildRole = rolesByUesr.stream()
					.filter(sysRoleEntity -> sysRoleEntity.getId().equals(Constant.SUPER_ADMIN)).findAny().orElse(null);
			// admin 角色 拥有所有接口权限
			List<SysMenuEntity> menuList;
			if (buildRole != null) {
				menuList = sysMenuService.list();
			} else {
				Set<Integer> roleIds = rolesByUesr.stream().map(SysRoleEntity::getId).collect(Collectors.toSet());
				List<SysRoleMenuEntity> sysRoleMenuEntities = sysRoleMenuDao.selectList(
						new LambdaQueryWrapper<SysRoleMenuEntity>().in(SysRoleMenuEntity::getRoleId, roleIds));
				if (CollectionUtils.isNotEmpty(sysRoleMenuEntities)) {
					// 所有叶子节点
					Set<Integer> menuIds = sysRoleMenuEntities.stream().map(SysRoleMenuEntity::getMenuId)
							.collect(Collectors.toSet());
					menuList = sysMenuService
							.list(new LambdaQueryWrapper<SysMenuEntity>().in(SysMenuEntity::getId, menuIds));
					// 根据叶子节点得到完整的权限
					menuList = sysMenuService.getMenuByLeafMenu(menuList);
				} else {
					menuList = Collections.emptyList();
				}
			}

			permsList = new ArrayList<>();
			for (SysMenuEntity menu : menuList) {
				permsList.add(menu.getPerms());
			}
		}

		// 用户权限列表
		Set<String> permsSet = new HashSet<>();
		for (String perms : permsList) {
			if (StringUtils.isEmpty(perms))
				continue;
			permsSet.addAll(Arrays.asList(perms.trim().split(",")));
		}

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permsSet);
		return info;
	}

	/**
	 * 认证(登录时调用)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		// 查询用户信息
		SysUserEntity user = sysUserDao.selectOne(new QueryWrapper<SysUserEntity>().eq("username", token.getUsername()));
		// 账号不存在，使用ldap验证
		if (user == null) {
			StringBuilder sb = new StringBuilder();
			sb.append(token.getPassword());
//			user = getLdapAuthentication(token.getUsername(), sb.toString());
			if (user == null) {
				throw new IncorrectCredentialsException("The account or password is incorrect");
			}
			user.setSource("ldap");
			user.setStatus(1);
			sysUserDao.insert(user);

			// ldap 登录用户默认分配 common 角色
			SysUserRoleEntity sysUserRole = new SysUserRoleEntity();
			sysUserRole.setUserId(user.getId().intValue());
			sysUserRole.setRoleId(Constant.SYSTEM_COMMON_ROLE);
			sysUserRoleDao.insert(sysUserRole);
			String salt = RandomStringUtils.randomAlphanumeric(20);
			user.setSalt(salt);
			user.setPin(ShiroUtils.sha256(sb.toString(), salt));
		} else if(user.getSource().equals("ldap")){
			//ldap非首次登陆，更新同步内容
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(token.getPassword());
//			SysUserEntity tmpUser = getLdapAuthentication(token.getUsername(), stringBuilder.toString());
			SysUserEntity tmpUser = null;
			if(tmpUser == null) {
				// 验证失败 则登录失败
				throw new IncorrectCredentialsException("The account or password is incorrect");
			}
			@SuppressWarnings("unchecked")
			Map<String,String> defaultMapping = JSONUtil.toBean(defaultMappingStr, HashMap.class);
			for(Entry<String,String> entry : defaultMapping.entrySet()) {
				String key = entry.getKey();
				String value = BeanUtil.getProperty(tmpUser, key);
				BeanUtil.setProperty(user, key, value);
			}
			sysUserDao.updateById(user);
			String salt = RandomStringUtils.randomAlphanumeric(20);
			user.setSalt(salt);
			user.setPin(ShiroUtils.sha256(stringBuilder.toString(), salt));
		} else {
			// 判断系统内用户账号是否锁定
			if (user.getStatus() == 0) {
				throw new LockedAccountException("The account has been locked, please contact the administrator");
			}
		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPin(),ByteSource.Util.bytes(user.getSalt()), getName());
		return info;
	}

	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
		shaCredentialsMatcher.setHashAlgorithmName(ShiroUtils.hashAlgorithmName);
		shaCredentialsMatcher.setHashIterations(ShiroUtils.hashIterations);
		super.setCredentialsMatcher(shaCredentialsMatcher);
	}
}
