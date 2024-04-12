package net.geedge.modules.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.geedge.modules.sys.entity.SysApiKey;

@Mapper
public interface SysApiKeyDao extends BaseMapper<SysApiKey>{

	List<SysApiKey> queryList(IPage page,@Param("params") Map<String, Object> params);
	
	@Update("UPDATE sys_api_key SET role_id = #{roleId} where create_by = #{userId};")
    void updateRoleIdByCreateBy(@Param("userId") Integer userId, @Param("roleId") Integer roleId);
}
