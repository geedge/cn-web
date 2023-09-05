package net.geedge.modules.knowledge.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import net.geedge.modules.knowledge.entity.KnowledgeBaseAuditLog;

@Mapper
public interface KnowledgeBaseAuditLogDao extends BaseMapper<KnowledgeBaseAuditLog> {

	List<KnowledgeBaseAuditLog> queryList(IPage page, @Param("params") Map<String, Object> params);
	
}
