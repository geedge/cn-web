package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.modules.knowledge.entity.VpnLearningDomain;
import net.geedge.modules.knowledge.entity.VpnLearningIp;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2024/01/02/10:36
 */
public interface VpnLearningDomainService extends IService<VpnLearningDomain> {

    List<Map> queryDomainEntityTag(String resource);
    
    void saveBatchJDBC(Connection conn,List<VpnLearningDomain> vpnLearningDomains);
	
	void modifyBatchJDBC(Connection conn,List<VpnLearningDomain> vpnLearningDomains);
}
