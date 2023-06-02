package net.geedge.modules.entity.service.impl;

import cn.hutool.core.net.Ipv4Util;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.net.InetAddresses;
import com.googlecode.ipv6.IPv6Address;
import net.geedge.common.exception.CNException;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.*;
import net.geedge.modules.entity.service.EntityService;
import net.geedge.modules.knowledge.entity.KnowledgeBase;
import net.geedge.modules.knowledge.service.*;
import org.apache.tomcat.util.net.IPv6Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @Auther: lijinyang
 * @Date: 2023/05/31/14:15
 */
@Service("EntityService")
public class EntityServiceImpl implements EntityService {

    @Autowired
    private FqdnWhoIsBuiltInService fqdnWhoIsBuiltInService;

    @Autowired
    private FqdnCategoryBuiltInService fqdnCategoryBuiltInService;

    @Autowired
    private IpAsnBuiltInService ipAsnBuiltInService;

    @Autowired
    private IpLocationBuiltInService ipLocationBuiltInService;

    @Autowired
    private AppCategoryBuiltInService appCategoryBuiltInService;

    @Autowired
    private AppTagUserDefinedService appTagUserDefinedService;

    @Autowired
    private IocMalwareService iocMalwareService;

    @Autowired
    private IocDarkwebService iocDarkwebService;

    @Autowired
    private Psiphon3IpService psiphon3IpService;

    @Autowired
    private IpTagUserDefinedService ipTagUserDefinedService;

    @Autowired
    private DomainTagUserDefinedService domainTagUserDefinedService;

    @Override
    public Map<String, Object> queryEntityDetail(String entityType, String resource) {
        Map<String, Object> data = new HashMap<>();
        if ("app".equals(entityType)){
            Map<String, Object> category = appCategoryBuiltInService.queryEntityDetail(resource);
            data.put("category", category);
        } else if ("domain".equals(entityType)){
            Map<String, Object> whois = fqdnWhoIsBuiltInService.queryEntityDetail(resource);
            Map<String, Object> category = fqdnCategoryBuiltInService.queryEntityDetail(resource);
            data.put("whois", whois);
            data.put("category", category);
        } else if ("ip".equals(entityType)){
            Map<String, Object> asn = ipAsnBuiltInService.queryEntityDetail(resource);
            String ipNumber = null;
            try {
                ipNumber = toIpNumber(resource);
            } catch (Exception e) {
                return R.error(400,e.getMessage());
            }
            Map<String, Object> location = ipLocationBuiltInService.queryEntityDetail(ipNumber);
            data.put("asn", asn);
            data.put("location", location);
        }
        validateEntityQuery(data, RCode.ENTITY_DETAIL_NOT_EXIST);
        return data;
    }

    private void validateEntityQuery(Map<String, Object> data, RCode code) {
        Boolean allEmpty = true;
        for (Object value : data.values()) {
            if (ObjectUtil.isNotEmpty(value)){
                allEmpty = false;
            }
        }
        if (allEmpty) {
            throw new CNException(code);
        }
    }

    private String toIpNumber(String resource) throws Exception{
        InetAddress byName = InetAddress.getByName(resource);
        BigInteger ipNumber = InetAddresses.toBigInteger(byName);
        return ipNumber.toString();
    }

    @Override
    public Map<String, Object> queryEntityTag(String entityType, String resource) {
        Map<String, Object> data = new HashMap<>();
        if ("app".equals(entityType)){
            List<Map> userDefinedTags = appTagUserDefinedService.queryAppEntityTag(resource);
            data.put("userDefinedTags", userDefinedTags);
        } else if ("domain".equals(entityType)){
            Map<String, Object> malware = iocMalwareService.queryDomainEntityTag(resource);
            Map<String, Object> darkweb = iocDarkwebService.queryDomainEntityTag(resource);
            List<Map> userDefinedTags = domainTagUserDefinedService.queryDomainEntityTag(resource);
            data.put("malware", malware);
            data.put("darkweb", darkweb);
            data.put("userDefinedTags", userDefinedTags);
        } else if ("ip".equals(entityType)){
            Map<String, Object> malware = iocMalwareService.queryIpEntityTag(resource);
            Map<String, Object> darkweb = iocDarkwebService.queryIpEntityTag(resource);
            Map<String, Object> psiphon3Ip = psiphon3IpService.queryIpEntityTag(resource);
            List<Map> userDefinedTags = ipTagUserDefinedService.queryIpEntityTag(resource);
            data.put("malware", malware);
            data.put("darkweb", darkweb);
            data.put("psiphon3Ip", psiphon3Ip);
            data.put("userDefinedTags", userDefinedTags);
        }
        validateEntityQuery(data,RCode.ENTITY_TAG_NOT_EXIST);
        return data;
    }
}
