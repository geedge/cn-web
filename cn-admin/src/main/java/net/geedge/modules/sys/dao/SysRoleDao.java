package net.geedge.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.geedge.modules.sys.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {

    List<SysRoleEntity> getRolesByUesrId(@Param("userId") Integer userId);
    
    List<SysRoleEntity> getRolesBySysApiKeyId(@Param("sysApiKeyId") Integer sysApiKeyId);
}
