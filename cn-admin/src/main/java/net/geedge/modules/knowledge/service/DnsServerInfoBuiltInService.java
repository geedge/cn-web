package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;

import net.geedge.modules.knowledge.entity.DnsServerInfoBuiltIn;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface DnsServerInfoBuiltInService extends IService<DnsServerInfoBuiltIn>{

    Map<String, Object> queryIpEntityTag(String resource);
    
    void saveBatchJDBC(Connection conn , List<DnsServerInfoBuiltIn> dnsServerInfoBuiltIns);
    
    void modifyBatchJDBC(Connection conn , List<DnsServerInfoBuiltIn> dnsServerInfoBuiltIns);
}
