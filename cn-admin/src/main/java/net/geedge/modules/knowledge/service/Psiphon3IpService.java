package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.modules.knowledge.entity.Psiphon3Ip;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/14:16
 */
public interface Psiphon3IpService extends IService<Psiphon3Ip> {

    PageUtils queryListByKnowledgeId(Map<String,Object> params);
    
    void saveBatchForBinary(List<Psiphon3Ip> psiphon3Ips);
    
    void modifyBatchForBinary(List<Psiphon3Ip> psiphon3Ips);

    Map<String, Object> queryIpEntityTag(String resource);
    
    void saveBatchJDBC(Connection conn,List<Psiphon3Ip> psiphon3Ips);
    
    void modifyBatchJDBC(Connection conn,List<Psiphon3Ip> psiphon3Ips);
}
