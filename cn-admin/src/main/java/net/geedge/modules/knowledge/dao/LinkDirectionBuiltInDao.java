package net.geedge.modules.knowledge.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import net.geedge.modules.knowledge.entity.LinkDirectionBuiltIn;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface LinkDirectionBuiltInDao extends BaseMapper<LinkDirectionBuiltIn>{

    List<LinkDirectionBuiltIn> queryPageByKnowledgeId(IPage<LinkDirectionBuiltIn> page, @Param("params") Map<String, Object> params);

}
