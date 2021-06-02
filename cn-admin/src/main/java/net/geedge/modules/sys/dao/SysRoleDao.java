package net.geedge.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import net.geedge.modules.sys.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {

    List<SysRoleEntity> getRolesByUesrId(@Param("userId") Integer userId);
    
    List<SysRoleEntity> getRolesBySysApiKeyId(@Param("sysApiKeyId") Integer sysApiKeyId);
    
    List<SysRoleEntity> selectSysRoleListWithPage(IPage<SysRoleEntity> page, @Param("params")Map<String, Object> params);
}
