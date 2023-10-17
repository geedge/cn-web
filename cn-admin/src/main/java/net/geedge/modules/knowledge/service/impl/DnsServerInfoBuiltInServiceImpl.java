package net.geedge.modules.knowledge.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.geedge.modules.knowledge.dao.DnsServerInfoBuiltInDao;
import net.geedge.modules.knowledge.entity.DnsServerInfoBuiltIn;
import net.geedge.modules.knowledge.service.DnsServerInfoBuiltInService;

import java.util.Map;

@Service("dnsServerInfoBuiltInService")
public class DnsServerInfoBuiltInServiceImpl extends ServiceImpl<DnsServerInfoBuiltInDao,DnsServerInfoBuiltIn> implements DnsServerInfoBuiltInService{

    @Override
    public Map<String, Object> queryIpEntityTag(String resource) {
        return this.baseMapper.queryIpEntityTag(resource);
    }
}
