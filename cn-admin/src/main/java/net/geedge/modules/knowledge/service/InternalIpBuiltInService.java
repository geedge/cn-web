package net.geedge.modules.knowledge.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import net.geedge.common.utils.PageUtils;
import net.geedge.modules.knowledge.entity.InternalIpBuiltIn;

public interface InternalIpBuiltInService extends IService<InternalIpBuiltIn>{
	
	void saveBatchForBinary(List<InternalIpBuiltIn> internalIpBuiltIns);
    
    void modifyBatchForBinary(List<InternalIpBuiltIn> internalIpBuiltIns);
	
	void saveBatchJDBC(Connection conn,List<InternalIpBuiltIn> internalIpBuiltIns);
    
    void modifyBatchJDBC(Connection conn,List<InternalIpBuiltIn> internalIpBuiltIns);

    PageUtils queryList(Map<String,Object> params);
    
}
