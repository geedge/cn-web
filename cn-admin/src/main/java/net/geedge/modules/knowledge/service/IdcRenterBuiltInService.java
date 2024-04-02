package net.geedge.modules.knowledge.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import net.geedge.common.utils.PageUtils;
import net.geedge.modules.knowledge.entity.IdcRenterBuiltIn;

public interface IdcRenterBuiltInService extends IService<IdcRenterBuiltIn>{
	
	void saveBatchForBinary(List<IdcRenterBuiltIn> idcRenterBuiltIns);
    
    void modifyBatchForBinary(List<IdcRenterBuiltIn> idcRenterBuiltIns);

    PageUtils queryList(Map<String,Object> params);
    
    void saveBatchJDBC(Connection conn,List<IdcRenterBuiltIn> idcRenterBuiltIns);
    
    void modifyBatchJDBC(Connection conn,List<IdcRenterBuiltIn> idcRenterBuiltIns);
}
