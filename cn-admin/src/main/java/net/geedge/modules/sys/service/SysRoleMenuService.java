package net.geedge.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.modules.sys.entity.SysRoleMenuEntity;

import java.util.List;

public interface SysRoleMenuService extends IService<SysRoleMenuEntity> {

	void saveOrUpdate(Integer roleId, List<Integer> menuIdList);
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Integer> queryMenuIdList(Integer roleId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Integer[] roleIds);
}