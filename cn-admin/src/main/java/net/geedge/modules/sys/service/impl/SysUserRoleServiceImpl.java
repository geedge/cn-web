package net.geedge.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.geedge.common.utils.Tool;
import net.geedge.modules.sys.dao.SysUserRoleDao;
import net.geedge.modules.sys.entity.SysUserRoleEntity;
import net.geedge.modules.sys.service.SysUserRoleService;


@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRoleEntity> implements SysUserRoleService {

	@Override
	public void saveOrUpdate(Long userId, String roleIds) {
		// 先删除用户与角色关系
		this.remove(new LambdaQueryWrapper<SysUserRoleEntity>().eq(SysUserRoleEntity::getUserId, userId));

		if (Tool.StrUtil.isEmpty(roleIds)) return;

		// 保存用户与角色关系
		List<SysUserRoleEntity> saveList = new ArrayList<>();
		SysUserRoleEntity entity;
		for (String roleId : Arrays.asList(roleIds.split(","))) {
			entity = new SysUserRoleEntity();
			entity.setUserId(userId.intValue());
			entity.setRoleId(Integer.valueOf(roleId));
			saveList.add(entity);
		}

		this.saveBatch(saveList);
	}

	@Override
	public List<Integer> queryRoleIdList(Integer userId) {
		return baseMapper.queryRoleIdList(userId);
	}

	@Override
	public int deleteBatch(Integer[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}
}
