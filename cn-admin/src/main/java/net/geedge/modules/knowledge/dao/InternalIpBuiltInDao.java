package net.geedge.modules.knowledge.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import net.geedge.modules.knowledge.entity.InternalIpBuiltIn;

@Mapper
public interface InternalIpBuiltInDao extends BaseMapper<InternalIpBuiltIn>{
	
	void insertBatchForBinary(@Param("internalIpBuiltIns")List<InternalIpBuiltIn> internalIpBuiltIns);
    
    void updateBatchForBinary(@Param("internalIpBuiltIns")List<InternalIpBuiltIn> internalIpBuiltIns);
}
