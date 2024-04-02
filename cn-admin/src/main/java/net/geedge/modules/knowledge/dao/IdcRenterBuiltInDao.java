package net.geedge.modules.knowledge.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import net.geedge.modules.knowledge.entity.IdcRenterBuiltIn;

@Mapper
public interface IdcRenterBuiltInDao extends BaseMapper<IdcRenterBuiltIn>{
	
	void insertBatchForBinary(@Param("idcRenterBuiltIns")List<IdcRenterBuiltIn> idcRenterBuiltIns);
    
    void updateBatchForBinary(@Param("idcRenterBuiltIns")List<IdcRenterBuiltIn> idcRenterBuiltIns);

    List<IdcRenterBuiltIn> queryPage(IPage<IdcRenterBuiltIn> page, @Param("params") Map<String, Object> params);
}
