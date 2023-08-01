package net.geedge.modules.knowledge.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.geedge.modules.knowledge.dao.DnsServerInfoBuiltInDao;
import net.geedge.modules.knowledge.entity.DnsServerInfoBuiltIn;
import net.geedge.modules.knowledge.service.DnsServerInfoBuiltInService;

@Service("dnsServerInfoBuiltInService")
public class DnsServerInfoBuiltInServiceImpl extends ServiceImpl<DnsServerInfoBuiltInDao,DnsServerInfoBuiltIn> implements DnsServerInfoBuiltInService{

}
