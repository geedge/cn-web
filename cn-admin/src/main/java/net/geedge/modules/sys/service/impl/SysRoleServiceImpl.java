package net.geedge.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import net.geedge.common.exception.CNException;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.common.utils.Tool;
import net.geedge.modules.sys.dao.SysMenuDao;
import net.geedge.modules.sys.dao.SysRoleDao;
import net.geedge.modules.sys.entity.SysMenuEntity;
import net.geedge.modules.sys.entity.SysRoleEntity;
import net.geedge.modules.sys.entity.SysRoleMenuEntity;
import net.geedge.modules.sys.entity.SysUserEntity;
import net.geedge.modules.sys.entity.SysUserRoleEntity;
import net.geedge.modules.sys.service.SysMenuService;
import net.geedge.modules.sys.service.SysRoleMenuService;
import net.geedge.modules.sys.service.SysRoleService;
import net.geedge.modules.sys.service.SysUserRoleService;
import net.geedge.modules.sys.service.SysUserService;
import net.geedge.modules.sys.shiro.ShiroUtils;

@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {

    private static Log log = Log.get();

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuDao sysMenuDao;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	if (ObjectUtil.isNotEmpty(params.get("ids"))) {
            params.put("ids", Arrays.asList(String.valueOf(params.get("ids")).split(StringPool.COMMA)));
        }
    	IPage<SysRoleEntity> page=this.page(new Query(SysRoleEntity.class).getPage(params));

		List<SysRoleEntity> list=this.baseMapper.selectSysRoleListWithPage(page,params);
		page.setRecords(list);
        return new PageUtils(page);
    }

    /**
     * 校验 role 是否存在
     *
     * @param roleId
     */
    private void validateRoleExist(Integer roleId) {
        SysRoleEntity role = this.getById(roleId);

        if (role == null)
            throw new CNException(RCode.SYS_ROLE_ISNULL);
    }

    /**
     * 校验 角色 相关信息
     *
     * @param role
     */
    private void validateRoleInfo(SysRoleEntity role) {
        String name = role.getName();
        ValidateUtils.is(name).notNull(RCode.SYS_ROLE_NAME_ISNULL);

        // 校验名称重复
        List<SysRoleEntity> list = this.list(new QueryWrapper<SysRoleEntity>().lambda().eq(SysRoleEntity::getName, name));

        boolean flag = false;
        if ((list == null || list.size() == 0) || (list != null && list.size() == 1 && list.get(0).getId().equals(role.getId()))) {
            flag = true;
        }
        if (!flag)
            throw new CNException(RCode.SYS_ROLE_NAME_DUPLICATE);


        List<Integer> menuIds = role.getMenuIds();
        if (CollectionUtils.isEmpty(menuIds))
            throw new CNException(RCode.SYS_ROLE_MENUID_ISNULL);

        List<SysMenuEntity> menuList = sysMenuService.list(new LambdaQueryWrapper<SysMenuEntity>().eq(SysMenuEntity::getState, 1));
        List<Integer> menuIdList = menuList.stream().map(SysMenuEntity::getId).collect(Collectors.toList());

        for (Integer menuId : menuIds) {
            if (!menuIdList.contains(menuId))
                throw new CNException(RCode.SYS_MENU_ISNULL);
        }
        
        role.setUby(ShiroUtils.getUserId());
        role.setUtime(new Date());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(SysRoleEntity role) {
        // 校验 角色 相关信息
        this.validateRoleInfo(role);

        // i18n 默认与 name 保持一致
        if (Tool.ObjectUtil.isEmpty(role.getI18n())) {
            role.setI18n(role.getName());
        }

        this.save(role);

        sysRoleMenuService.saveOrUpdate(role.getId(), role.getMenuIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleEntity role) {
        // 校验 角色 相关信息
        this.validateRoleInfo(role);

        SysRoleEntity oldRole = this.getById(role.getId());
        if (oldRole == null)
            throw new CNException(RCode.SYS_ROLE_ISNULL);

        // 内置角色 name 不允许修改
        if (oldRole.getBuildIn() == 1) {
            if(!StrUtil.equals(oldRole.getName(),role.getName()))
                throw new CNException(RCode.SYS_ROLE_NAME_CANNOT_MODIFY);
        }

        this.updateById(role);

        // 处理角色菜单关系
        this.handleRoleMenuRelation(role.getId(), role.getMenuIds());
    }

    /**
     * 处理 角色 和 菜单 关联关系
     * @param roleId
     * @param menuIds
     */
    private void handleRoleMenuRelation(Integer roleId, List<Integer> menuIds) {
        // 当前角色关联的菜单
        List<SysRoleMenuEntity> menus = sysRoleMenuService.list(new LambdaQueryWrapper<SysRoleMenuEntity>().eq(SysRoleMenuEntity::getRoleId, roleId));
        List<Integer> menuIdList = menus.stream().map(SysRoleMenuEntity::getMenuId).distinct().collect(Collectors.toList());

        // menu 信息未改变 不进行处理
        if (menuIdList.size() == menuIds.size() && Tool.CollUtil.containsAll(menuIdList, menuIds))
            return;

        // 更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(roleId, menuIds);

        // 角色关联 用户
        List<SysUserEntity> userList = sysUserService.getUserListByRoleId(roleId);
        if (CollectionUtils.isEmpty(userList))
            return;

        List<Long> userIds = userList.stream().map(SysUserEntity::getId).distinct().collect(Collectors.toList());

        List<String> removeKeys = new ArrayList<>();
        try {
            Map<String, SysUserEntity> shiroSessionInfoMap = ShiroUtils.getShiroSessionInfoMap();
            for (Map.Entry<String, SysUserEntity> entry : shiroSessionInfoMap.entrySet()) {
                SysUserEntity userEntity = entry.getValue();
                if (userIds.contains(userEntity.getId())) {
                    removeKeys.add(entry.getKey());
                }
            }
        } catch (RuntimeException e) {
            log.error(String.format("获取用户登录信息失败,错误信息是：%s,角色Id是：%s", e.getMessage(), roleId), e);
        }  finally {
            // 删除 关联key
            if (CollectionUtils.isNotEmpty(removeKeys)) {
                ShiroUtils.deleteSession(removeKeys);
            }
        }
    }

    @Override
    public Map getMenuListByRoleId(Integer roleId) {
        // 校验 role 是否存在
        this.validateRoleExist(roleId);

        Map resultData = new HashMap(4);
        List<SysMenuEntity> menuList = sysMenuService.getMenuList(null);
        
        List<SysMenuEntity> roleMenuList = sysMenuDao.getRoleMenuList(roleId);
        Set<Integer> selectedIds = roleMenuList.stream().filter(Objects::nonNull).map(SysMenuEntity::getId).collect(Collectors.toSet());

        resultData.put("menus", menuList);
        resultData.put("selectedIds", selectedIds);

        return resultData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R deleteBatch(Integer[] roleIds, Map<String, Object> params) {
        Integer force = 0;
        if(ObjectUtil.isNotEmpty(params.get("force"))){
            force = Integer.parseInt((String)params.get("force"));
        }
        // 内置角色不允许删除
        List<SysRoleEntity> buildInList = this.list(new LambdaQueryWrapper<SysRoleEntity>().eq(SysRoleEntity::getBuildIn, 1));
        List<Integer> buildInRoleIdList = buildInList.stream().map(SysRoleEntity::getId).collect(Collectors.toList());
        for (Integer roleId : roleIds) {
            if (buildInRoleIdList.contains(roleId))
                throw new CNException(RCode.SYS_ROLE_CANNOT_DELETE);
        }
        if(force == 0){
            List<Map<String, Object>> list = creatUserRoleList(roleIds);
            if(isAssociatedUser(list)){
                throw new CNException(RCode.SYS_ROLE_IS_RELATED_CANNOT_DELETE);
            }
        }

        // 删除角色
        this.removeByIds(Arrays.asList(roleIds));

        // 删除角色与菜单关联
        sysRoleMenuService.deleteBatch(roleIds);

        // 删除角色与用户关联
        sysUserRoleService.deleteBatch(roleIds);

        return R.ok();
    }

    private List<Map<String, Object>> creatUserRoleList(Integer[] roleIds) {
        List<Map<String, Object>> list = new ArrayList<>();
        List<SysRoleEntity> roleList = this.list(new LambdaQueryWrapper<SysRoleEntity>().eq(SysRoleEntity::getBuildIn, 0));
        List<SysUserRoleEntity> sysUserRoleList = sysUserRoleService.getBaseMapper().selectList(new QueryWrapper<>());
        for (Integer roleId : roleIds) {
            Map<String, Object> roleMap = new HashMap<>();
            List<Map<String, Object>> relates = new ArrayList<>();
            SysRoleEntity role = null;
            for (SysRoleEntity r :roleList) {
                if (r.getId()==roleId) {
                    role = r;
                    break;
                }
            }
            roleMap.put("id", roleId);
            roleMap.put("name", role.getName());
            List<SysUserRoleEntity> part = sysUserRoleList.stream().filter(o -> o.getRoleId() == roleId).collect(Collectors.toList());
            if(ObjectUtil.isNotEmpty(part)) {
                List<Integer> userIds = part.stream().map(SysUserRoleEntity::getUserId).collect(Collectors.toList());
                for (Integer userId : userIds) {
                    Map<String, Object> userMap = new HashMap<>();
                    SysUserEntity user = sysUserService.getById(userId);
                    userMap.put("type", "user");
                    userMap.put("id", userId);
                    userMap.put("name", user.getName());
                    relates.add(userMap);
                }
                roleMap.put("relates", relates);
            }
            list.add(roleMap);
        }
        return list;
    }

    private boolean isAssociatedUser(List<Map<String, Object>> list){
        for (Map<String,Object> map : list) {
            if(ObjectUtil.isNotEmpty(map.get("relates"))) {
                return true;
            }
        }
        return false;
    }
}
