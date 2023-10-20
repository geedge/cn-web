package net.geedge.modules.detection.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.net.InetAddresses;
import net.geedge.common.utils.R;
import net.geedge.modules.detection.service.DetectionService;
import net.geedge.modules.knowledge.entity.KnowledgeBase;
import net.geedge.modules.knowledge.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/10/20/15:10
 */
@Service("DetectionService")
public class DetectionServiceImpl implements DetectionService {

    @Autowired
    private AppCategoryBuiltInService appCategoryBuiltInService;

    @Autowired
    private FqdnCategoryBuiltInService fqdnCategoryBuiltInService;

    @Autowired
    private IocMalwareService iocMalwareService;

    @Autowired
    private IpAsnBuiltInService ipAsnBuiltInService;

    @Autowired
    private IpLocationBuiltInService ipLocationBuiltInService;

    @Autowired
    private KnowledgeBaseService knowledgeBaseService;

    @Override
    public R queryDetectionEntityDetail(String entityType, String resource) {
        Map<String, Object> data = new HashMap<>();
        if ("app".equals(entityType)){
            Map<String, Object> category = appCategoryBuiltInService.queryEntityDetail(resource);
            addValidDataToMap(data, "category", category);
        } else if ("domain".equals(entityType)){
            Map<String, Object> malware = iocMalwareService.queryDomainDetectionEntityDetail(resource);
            Map<String, Object> category = fqdnCategoryBuiltInService.queryEntityDetail(resource,false);
            addValidDataToMap(data, "category", category);
            addValidDataToMap(data, "malware", malware);
        } else if ("ip".equals(entityType)){
            Map<String, Object> asn = ipAsnBuiltInService.queryEntityDetail(resource);
            Map<String, Object> malware = iocMalwareService.queryIpDetectionEntityDetail(resource);
            String ipNumber = null;
            try {
                ipNumber = toIpNumber(resource);
            } catch (Exception e) {
                return R.error(400,"ip "+e.getMessage()+"is not ipv4 or ipv6");
            }
            Map<String, Object> location = ipLocationBuiltInService.queryEntityDetail(ipNumber);
            addValidDataToMap(data, "asn", asn);
            addValidDataToMap(data, "malware", malware);
            addValidDataToMap(data, "location", location);
        }
        return R.ok(data);
    }

    private void addValidDataToMap(Map<String, Object> map, String key, Map<String, Object> value) {
        map.put(key, null);
        if (ObjectUtil.isNotEmpty(value) && ObjectUtil.isNotEmpty(value.get("knowledgeId"))) {
            KnowledgeBase knowledgeBase = knowledgeBaseService.getOne(new QueryWrapper<KnowledgeBase>().eq("knowledge_id", value.get("knowledgeId")));
            if (knowledgeBase.getStatus() == 1 && "1".equals(value.get("isValid").toString())) {
                map.put(key, value);
            }
        }
    }

    private String toIpNumber(String resource) throws Exception{
        InetAddress byName = InetAddress.getByName(resource);
        BigInteger ipNumber = InetAddresses.toBigInteger(byName);
        return ipNumber.toString();
    }

}
