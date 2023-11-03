package net.geedge.modules.knowledge.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import net.geedge.modules.knowledge.entity.InternalIpBuiltIn;

@Mapper
public interface InternalIpBuiltInDao extends BaseMapper<InternalIpBuiltIn>{
	
	void insertBatchForBinary(@Param("internalIpBuiltIns")List<InternalIpBuiltIn> internalIpBuiltIns);
    
    void updateBatchForBinary(@Param("internalIpBuiltIns")List<InternalIpBuiltIn> internalIpBuiltIns);

    List<InternalIpBuiltIn> queryPageByKnowledgeId(IPage<InternalIpBuiltIn> page, @Param("params") Map<String, Object> params);
}
