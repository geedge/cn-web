package net.geedge.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import net.geedge.common.exception.CNException;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.Constant;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;
import net.geedge.common.utils.Tool;
import net.geedge.modules.sys.dao.SysRoleDao;
import net.geedge.modules.sys.dao.SysUserDao;
import net.geedge.modules.sys.entity.SysApiKey;
import net.geedge.modules.sys.entity.SysRoleEntity;
import net.geedge.modules.sys.entity.SysUserEntity;
import net.geedge.modules.sys.entity.SysUserRoleEntity;
import net.geedge.modules.sys.service.SysApiKeyService;
import net.geedge.modules.sys.service.SysMenuService;
import net.geedge.modules.sys.service.SysRoleService;
import net.geedge.modules.sys.service.SysUserRoleService;
import net.geedge.modules.sys.service.SysUserService;
import net.geedge.modules.sys.shiro.ShiroUtils;

@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {

	private static Log log = Log.get();

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Autowired
	private SysRoleDao sysRoleDao;

	@Autowired
	private SysMenuService sysMenuService;
	
	@Autowired
	private SysApiKeyService sysApiKeyService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		if (ObjectUtil.isNotEmpty(params.get("ids"))) {
            params.put("ids", Arrays.asList(String.valueOf(params.get("ids")).split(StringPool.COMMA)));
        }
		IPage page = new Query(SysUserEntity.class).getPage(params);
		List<SysUserEntity> userList = baseMapper.queryList(page, params);

		// 补充 Notificationscript roles 信息
		if (CollectionUtils.isNotEmpty(userList)) {
			List<SysUserRoleEntity> userRoleList = sysUserRoleService.list();
			Map<Integer, List<SysUserRoleEntity>> userRoleMap = userRoleList.stream().collect(Collectors.groupingBy(SysUserRoleEntity::getUserId));
			List<SysRoleEntity> roleList = sysRoleService.list();
			Map<Integer, SysRoleEntity> roleIdAndEntityMap = new HashMap<>();
			for (SysRoleEntity sysRoleEntity : roleList) {
				roleIdAndEntityMap.put(sysRoleEntity.getId(), sysRoleEntity);
			}

			for (SysUserEntity userEntity : userList) {
				List<SysUserRoleEntity> userRoleEntities = userRoleMap.get(userEntity.getId().intValue());
				if (CollectionUtils.isEmpty(userRoleEntities)) {
					userEntity.setRoles(Collections.emptyList());
				} else {
					List<SysRoleEntity> roles = new ArrayList<>();
					for (SysUserRoleEntity userRoleEntity : userRoleEntities) {
						roles.add(roleIdAndEntityMap.get(userRoleEntity.getRoleId()));
					}
					userEntity.setRoles(roles);
				}
			}
		}

		page.setRecords(userList);
		return new PageUtils(page);
	}

    private void validateUserInfo(SysUserEntity user) {
	    Integer status = user.getStatus();
        ValidateUtils.is(user.getUsername()).notNull(RCode.SYS_USER_USERNAME_ISNULL)
                .and(status).notNull(RCode.SYS_USER_STATUS_ISNULL).and(user.getName()).notNull(RCode.SYS_USER_NAME_ISNULL)
                .and(user.getPin()).notNull(RCode.SYS_USER_PASSWORD_ISNULL);

        List<SysUserEntity> list = this.list(new QueryWrapper<SysUserEntity>().lambda().eq(SysUserEntity::getName, user.getName()).ne(ObjectUtil.isNotEmpty(user.getId()),SysUserEntity::getId, user.getId()));
        if(ObjectUtil.isNotEmpty(list)) {
        	throw new CNException(RCode.SYS_USER_NAME_DUPLICATE);
        }
        
	    if(status != 0 && status != 1){
	    	throw new CNException(RCode.SYS_USER_STATUS_INVALIDE);
	    }
	    String email = user.getEmail();
	    if(StringUtils.isNotEmpty(email)){
	    	ValidateUtils.is(email).email(RCode.SYS_USER_EMAIL_FORMAT);
	    }

	    // 校验角色
	    String roleIds = user.getRoleIds();
	    if (StringUtils.isEmpty(roleIds)) throw new CNException(RCode.SYS_ROLE_ID_ISNULL);

	    List<SysRoleEntity> roleList = sysRoleService.list();
	    List<Integer> roleIdList = roleList.stream().map(SysRoleEntity::getId).collect(Collectors.toList());

	    for (String roleId : Arrays.asList(roleIds.split(","))) {
		    if (!roleIdList.contains(Integer.valueOf(roleId)))
			    throw new CNException(RCode.SYS_ROLE_ISNULL);
	    }
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer saveUser(SysUserEntity user) {

		// 校验 user 相关信息
		this.validateUserInfo(user);

		user.setCtime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setSalt(salt);
		user.setPin(ShiroUtils.sha256(user.getPin(), user.getSalt()));
		user.setCby(ShiroUtils.getUserId().intValue());
		this.save(user);

		// 保存用户 和 角色 关联关系
		sysUserRoleService.saveOrUpdate(user.getId(), user.getRoleIds());
		
		return user.getId().intValue();
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer update(SysUserEntity user) {
		
		SysUserEntity byId = this.getById(user.getId());
		if (StringUtils.isBlank(user.getPin())) {
			user.setPin(byId.getPin());
		} else {
			// 如果不是本人更改 自己密码的话，删除用户session
			Long loginUserId = ShiroUtils.getUserId();
			if (!loginUserId.equals(user.getId())) {
				// remove user session
				this.removeUserSession(user.getId());
			}
			user.setPin(ShiroUtils.sha256(user.getPin(), byId.getSalt()));
		}

		user.setCby(ShiroUtils.getUserId().intValue());
		this.updateById(user);

		// 处理用户角色关系
		this.handleUserRoleRelation(user.getId(), user.getRoleIds());
		
		return user.getId().intValue();
	}
	
	/**
	 * remove user session
	 *
	 * @param userId
	 */
	private void removeUserSession(Long userId) {
		List<String> removeKeys = new ArrayList<>();
		try {
			Map<String, SysUserEntity> shiroSessionInfoMap = ShiroUtils.getShiroSessionInfoMap();
			for (Map.Entry<String, SysUserEntity> entry : shiroSessionInfoMap.entrySet()) {
				SysUserEntity userEntity = entry.getValue();
				if (userId.equals(userEntity.getId())) {
					removeKeys.add(entry.getKey());
				}
			}
		} catch (Exception e) {
            log.error(e, "[removeUserSession] [error] [userId: {}]", userId);
		} finally {
			// 删除 关联key
			if (CollectionUtils.isNotEmpty(removeKeys)) {
				ShiroUtils.deleteSession(removeKeys);
			}
		}
	}

	/**
	 * 处理用户 和 角色 关联关系
	 * @param userId
	 * @param roleIds
	 */
	private void handleUserRoleRelation(Long userId, String roleIds) {
		// 当前用户关联的角色
		List<SysUserRoleEntity> roles = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRoleEntity>().eq(SysUserRoleEntity::getUserId, userId.intValue()));
		List<Integer> idList = roles.stream().map(SysUserRoleEntity::getRoleId).distinct().collect(Collectors.toList());

		// 角色信息未改变 不进行处理
		List<Integer> roleIdList = new ArrayList<>();
		for (String s : Arrays.asList(roleIds.split(","))) {
			roleIdList.add(Integer.valueOf(s));
		}
		if (idList.size() == roleIdList.size() && Tool.CollUtil.containsAll(idList, roleIdList))
			return;

		// 角色变更
		sysUserRoleService.saveOrUpdate(userId, roleIds);
		
		// remove user session
		this.removeUserSession(userId);
		
		// 变更 sys_api_key role_id  注：当前 用户只能关联一个角色
		String updateRoleId = Arrays.asList(roleIds.split(",")).get(0);
		sysApiKeyService.updateRoleIdByUser(userId.intValue(), Integer.valueOf(updateRoleId));
	}

	@Override
	public boolean updatePassword(Long userId, String pin, String newPin) {
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setPin(newPin);
        return this.update(userEntity,
        	new LambdaUpdateWrapper<SysUserEntity>()
					.eq(Tool.ObjectUtil.isNotEmpty(userId),SysUserEntity::getId, userId)
					.eq(Tool.ObjectUtil.isNotEmpty(pin),SysUserEntity::getPin, pin));
    }

	@Override
	public boolean checkName(SysUserEntity user) {
		List<SysUserEntity> list = this.list(new LambdaUpdateWrapper<SysUserEntity>()
				.eq(Tool.ObjectUtil.isNotEmpty(user.getUsername()), SysUserEntity::getUsername, user.getUsername())
				.eq(Tool.ObjectUtil.isNotEmpty(user.getStatus()), SysUserEntity::getStatus, 1));
		boolean dumpFlag = true; //是否名称重复，true为重复

		//当只有一个结果并且这个结果是自身时，或没有结果时，说明名称不重复
		if ((list == null || list.size() == 0) ||
					(list.size() == 1 && list.get(0).getId().longValue() == user.getId().longValue())) {
			dumpFlag = false;
		}
		return dumpFlag;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteUsers(String userIds) {
		ValidateUtils.is(userIds).notNull(RCode.SYS_USER_ID_ISNULL);
		String[] split = userIds.split(",");
		if (ArrayUtils.contains(split, "1")) {
			throw new CNException(RCode.SYS_USER_DELADMIN);
		}

		SysUserEntity userEntity = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		if (ArrayUtils.contains(split, userEntity.getId().toString())) {
			throw new CNException(RCode.SYS_USER_CANNOTDEL);
		}
		// 用户创建的 api key
        List<SysApiKey> apiKeyList = sysApiKeyService.list(new LambdaUpdateWrapper<SysApiKey>().in(SysApiKey::getUby, Arrays.asList(split)));
        // 删除相关 sys_api_key
        sysApiKeyService.remove(new LambdaUpdateWrapper<SysApiKey>().in(SysApiKey::getUby, Arrays.asList(split)));

		// 删除 user
		this.removeByIds(Arrays.asList(split));

		// 删除 关联 role 关系
		sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRoleEntity>().in(SysUserRoleEntity::getUserId, Arrays.asList(split)));
		
		// 删除用户token 信息
		List<String> removeKeys = new ArrayList<>();
		List<Long> userIdList = new ArrayList<>(split.length);
		for (int i = 0; i < split.length; i++) {
			userIdList.add(Long.valueOf(split[i]));
		}
		try {
			Map<String, SysUserEntity> shiroSessionInfoMap = ShiroUtils.getShiroSessionInfoMap();
			for (Map.Entry<String, SysUserEntity> entry : shiroSessionInfoMap.entrySet()) {
				SysUserEntity userInfo = entry.getValue();
				if (userIdList.contains(userInfo.getId())) {
					removeKeys.add(entry.getKey());
				}
			}
			for (SysApiKey apiKey : apiKeyList) {
                removeKeys.add(apiKey.getToken());
            }
		} catch (RuntimeException e) {
			log.error(String.format("Failed to obtain user login information, the error message is：%s,delete user ID:%s", e.getMessage(), userIdList.toString()), e);
		} finally {
			// 删除 关联key
			if (CollectionUtils.isNotEmpty(removeKeys)) {
				ShiroUtils.deleteSession(removeKeys);
			}
		}
	}

	@Override
	public Map<String, Object> getUserPermissions(HttpServletRequest request) {
		String requestToken = request.getHeader(Constant.AUTH_TOKEN_CODE);
		requestToken = StringUtils.isEmpty(requestToken) ? request.getParameter(Constant.AUTH_TOKEN_CODE) : requestToken;
		if (StringUtils.isEmpty(requestToken)) {
			throw new CNException(RCode.SYS_TOKEN_ISNULL);
		}
		Session session = ShiroUtils.getSessionById(requestToken);
		if(Tool.ObjectUtil.isNull(session)) {
			throw new CNException(RCode.SYS_LOGIN_REQUIRED);
		}
		PrincipalCollection primaryPrincipal = (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
		SysUserEntity userEntity = (SysUserEntity) primaryPrincipal.getPrimaryPrincipal();

		SysUserEntity user = this.getById(userEntity.getId());
		if (user == null)
			throw new CNException(RCode.SYS_USER_NOT_EXIST);

		Map<String, Object> resultMap = new HashMap<>(4);
		List<SysRoleEntity> rolesByUesr = sysRoleDao.getRolesByUesrId(user.getId().intValue());
		resultMap.put("roles", rolesByUesr);

		List<Integer> roleIdList = rolesByUesr.stream().map(SysRoleEntity::getId).collect(Collectors.toList());
		Map<String, Object> menuInfoByRoles = sysMenuService.getMenuInfoByRoles(roleIdList);
		resultMap.put("menus", menuInfoByRoles.get("menus"));
		resultMap.put("buttons", menuInfoByRoles.get("buttons"));
		return resultMap;
	}

	@Override
	public List<SysUserEntity> getUserListByRoleId(Integer roleId) {
		List<SysUserEntity> userList = this.getBaseMapper().getUserListByRoleId(roleId);
		return userList;
	}

	@Override
	public SysUserEntity queryUserInfo(Long userId) {
		SysUserEntity user = this.getById(userId);
		//获取用户所属的角色列表
		List<Integer> roleIdList = sysUserRoleService.queryRoleIdList(userId.intValue());
		List<SysRoleEntity> roles = sysRoleService.list(new QueryWrapper<SysRoleEntity>().lambda().in(SysRoleEntity::getId, roleIdList));
		user.setRoles(roles);
		return user;
	}
}
