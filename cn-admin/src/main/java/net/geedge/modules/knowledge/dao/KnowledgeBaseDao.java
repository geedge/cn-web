package net.geedge.modules.knowledge.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import net.geedge.modules.knowledge.entity.KnowledgeBase;

@Mapper
public interface KnowledgeBaseDao extends BaseMapper<KnowledgeBase> {

	List<KnowledgeBase> queryPage(IPage<KnowledgeBase> page,  @Param("params")Map<String, Object> params);
	
	KnowledgeBase queryInfo(@Param("knowledgeId")Integer id);

	List<KnowledgeBase> queryList(@Param("params")Map<String, Object> params);
	
	void deleteBySource(@Param("source")String source,@Param("knowledgeIds")List<Integer> knowledgeIds);
}
