package net.geedge.modules.knowledge.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import net.geedge.modules.knowledge.entity.IdcRenterBuiltIn;

public interface IdcRenterBuiltInService extends IService<IdcRenterBuiltIn>{
	
	void saveBatchForBinary(List<IdcRenterBuiltIn> idcRenterBuiltIns);
    
    void modifyBatchForBinary(List<IdcRenterBuiltIn> idcRenterBuiltIns);
}
