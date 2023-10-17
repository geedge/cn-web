package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;

import net.geedge.modules.knowledge.entity.DnsServerInfoBuiltIn;

import java.util.Map;

public interface DnsServerInfoBuiltInService extends IService<DnsServerInfoBuiltIn>{

    Map<String, Object> queryIpEntityTag(String resource);
}
