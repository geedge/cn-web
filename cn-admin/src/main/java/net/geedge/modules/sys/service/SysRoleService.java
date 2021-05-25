package net.geedge.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.modules.sys.entity.SysRoleEntity;

import java.util.Map;

public interface SysRoleService extends IService<SysRoleEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveRole(SysRoleEntity role);

    void update(SysRoleEntity role);

    void deleteBatch(Integer[] roleIds);

    Map getMenuListByRoleId(Integer roleId);
}
