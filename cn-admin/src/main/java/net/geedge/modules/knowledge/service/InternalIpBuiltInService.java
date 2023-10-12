package net.geedge.modules.knowledge.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import net.geedge.common.utils.PageUtils;
import net.geedge.modules.knowledge.entity.InternalIpBuiltIn;
import net.geedge.modules.knowledge.entity.IpTagUserDefined;

public interface InternalIpBuiltInService extends IService<InternalIpBuiltIn>{
	
	void saveBatchForBinary(List<InternalIpBuiltIn> internalIpBuiltIns);
    
    void modifyBatchForBinary(List<InternalIpBuiltIn> internalIpBuiltIns);

    PageUtils queryListByKnowledgeId(Map<String,Object> params);
}
