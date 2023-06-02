package net.geedge.modules.knowledge.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import net.geedge.modules.knowledge.entity.IdcRenterBuiltIn;

@Mapper
public interface IdcRenterBuiltInDao extends BaseMapper<IdcRenterBuiltIn>{
	
	void insertBatchForBinary(@Param("idcRenterBuiltIns")List<IdcRenterBuiltIn> idcRenterBuiltIns);
    
    void updateBatchForBinary(@Param("idcRenterBuiltIns")List<IdcRenterBuiltIn> idcRenterBuiltIns);
}
