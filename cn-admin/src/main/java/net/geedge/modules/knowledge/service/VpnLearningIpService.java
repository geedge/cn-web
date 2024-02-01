package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;

import net.geedge.common.utils.PageUtils;
import net.geedge.modules.knowledge.entity.IpAsnBuiltIn;
import net.geedge.modules.knowledge.entity.VpnLearningIp;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2024/01/02/9:43
 */
public interface VpnLearningIpService extends IService<VpnLearningIp> {

    List<Map> queryIpEntityTag(String resource);
    
    void saveBatchJDBC(Connection conn,List<VpnLearningIp> vpnLearningIps);
	
	void modifyBatchJDBC(Connection conn,List<VpnLearningIp> vpnLearningIps);

    PageUtils queryListByKnowledgeId(Map<String,Object> params);
}
