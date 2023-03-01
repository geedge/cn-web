package net.geedge.modules.knowledge.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import net.geedge.modules.knowledge.entity.KnowledgeBase;

@Mapper
public interface KnowledgeBaseDao extends BaseMapper<KnowledgeBase> {

	
}
