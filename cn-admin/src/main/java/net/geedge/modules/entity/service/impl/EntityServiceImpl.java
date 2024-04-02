package net.geedge.modules.entity.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.net.InetAddresses;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.*;
import net.geedge.modules.entity.service.EntityService;
import net.geedge.modules.knowledge.entity.KnowledgeBase;
import net.geedge.modules.knowledge.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssssssss.magicapi.core.service.MagicAPIService;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;


/**
 * @Auther: lijinyang
 * @Date: 2023/05/31/14:15
 */
@Service("EntityService")
public class EntityServiceImpl implements EntityService {
    @Autowired
    private KnowledgeBaseService knowledgeBaseService;

    @Autowired
    private FqdnWhoIsBuiltInService fqdnWhoIsBuiltInService;

    @Autowired
    private FqdnIcpBuiltInService fqdnIcpBuiltInService;

    @Autowired
    private FqdnCategoryBuiltInService fqdnCategoryBuiltInService;

    @Autowired
    private IpAsnBuiltInService ipAsnBuiltInService;

    @Autowired
    private IpLocationBuiltInService ipLocationBuiltInService;

    @Autowired
    private AppCategoryBuiltInService appCategoryBuiltInService;

    @Autowired
    private IocMalwareService iocMalwareService;

    @Autowired
    private IocDarkwebService iocDarkwebService;

    @Autowired
    private DnsServerInfoBuiltInService dnsServerInfoBuiltInService;

    @Autowired
    private MagicAPIService magicAPIService;

    @Override
    public R queryEntityDetail(String entityType, String resource) {
        Map<String, Object> data = new HashMap<>();
        if ("app".equals(entityType)){
            Map<String, Object> category = appCategoryBuiltInService.queryEntityDetail(resource);
            addValidDataToMap(data, "category", category, Constant.APP_CATEGORY_BUILT_IN);
        } else if ("domain".equals(entityType)){
            Map<String, Object> whois = fqdnWhoIsBuiltInService.queryEntityDetail(resource, false);
            Map<String, Object> icp = fqdnIcpBuiltInService.queryEntityDetail(resource, false);
            Map<String, Object> category = fqdnCategoryBuiltInService.queryEntityDetail(resource,false);
            if (ObjectUtil.isEmpty(whois)) {
                whois = fqdnWhoIsBuiltInService.queryEntityDetail(resource, true);
            }
            if (ObjectUtil.isEmpty(icp)) {
                icp = fqdnIcpBuiltInService.queryEntityDetail(resource, true);
            }
            if (ObjectUtil.isEmpty(category)) {
                category = fqdnCategoryBuiltInService.queryEntityDetail(resource, true);
            }
            dateToTimestamp(whois);
            addValidDataToMap(data, "whois", whois, Constant.FQDN_WHO_IS_BUILT_IN);
            addValidDataToMap(data, "icp", icp, Constant.FQDN_ICP_BUILT_IN);
            addValidDataToMap(data, "category", category, Constant.FQDN_CATEGORY_BUILT_IN);
        } else if ("ip".equals(entityType)){
            boolean isIpv6 = false;
            try {
                isIpv6 = IPUtils.isIpv6Address(resource);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            Map<String, Object> asn = ipAsnBuiltInService.queryEntityDetail(resource,isIpv6);
            String ipNumber = null;
            try {
                ipNumber = toIpNumber(resource);
            } catch (Exception e) {
                return R.error(400,"ip "+e.getMessage()+"is not ipv4 or ipv6");
            }
            Map<String, Object> location = ipLocationBuiltInService.queryEntityDetail(ipNumber);
            addValidDataToMap(data, "asn", asn, Constant.IP_ASN_BUILT_IN);
            addValidDataToMap(data, "location", location, Constant.IP_LOCATION_BUILT_IN);
        } else if ("subscriber".equals(entityType)){
            Map<String,Object> params = new HashMap<>();
            params.put("resource",resource);
            return magicAPIService.execute("GET", "/v1/entity/detail/basic/subscriber", params);
        }
        return R.ok(data);
    }

    private void addValidDataToMap(Map<String, Object> map, String key, Map<String, Object> value, String source) {
        map.put(key, null);
        if (ObjectUtil.isNotEmpty(value)) {
            KnowledgeBase knowledgeBase = knowledgeBaseService.getOne(new QueryWrapper<KnowledgeBase>().eq("source", source));
            if (ObjectUtil.isNotEmpty(knowledgeBase) && knowledgeBase.getStatus() == 1 && "1".equals(value.get("isValid").toString())) {
                map.put(key, value);
            }
        }
    }

    private void dateToTimestamp(Map<String, Object> whois){
        if (ObjectUtil.isNotEmpty(whois)) {
            if (ObjectUtil.isNotEmpty(whois.get("createDate"))){
                Date createDate = (Date)whois.get("createDate");
                whois.put("createDate", createDate.getTime()/1000);
            }
            if (ObjectUtil.isNotEmpty(whois.get("expireDate"))){
                Date expireDate = (Date)whois.get("expireDate");
                whois.put("expireDate", expireDate.getTime()/1000);
            }
        }
    }

    private String toIpNumber(String resource) throws Exception{
        InetAddress byName = InetAddress.getByName(resource);
        BigInteger ipNumber = InetAddresses.toBigInteger(byName);
        return ipNumber.toString();
    }

    @Override
    public R queryEntityTag(String entityType, String resource) {
        Map<String, Object> data = new HashMap<>();
        if ("app".equals(entityType)){
        } else if ("domain".equals(entityType)){
            Map<String, Object> malware = iocMalwareService.queryDomainEntityTag(resource, false);
            Map<String, Object> darkweb = iocDarkwebService.queryDomainEntityTag(resource, false);
            if (ObjectUtil.isEmpty(malware)) {
                malware = iocMalwareService.queryDomainEntityTag(resource, true);
            }
            if (ObjectUtil.isEmpty(darkweb)) {
                darkweb = iocDarkwebService.queryDomainEntityTag(resource, true);
            }
            addValidDataToMap(data, "malware", malware, Constant.IOC_MALWARE);
            addValidDataToMap(data, "darkweb", darkweb, Constant.IOC_DARKWEB);
        } else if ("ip".equals(entityType)){
            Map<String, Object> malware = iocMalwareService.queryIpEntityTag(resource);
            Map<String, Object> darkweb = iocDarkwebService.queryIpEntityTag(resource);
            Map<String, Object> dnsServerInfo = dnsServerInfoBuiltInService.queryIpEntityTag(resource);
            addValidDataToMap(data, "malware", malware, Constant.IOC_MALWARE);
            addValidDataToMap(data, "darkweb", darkweb, Constant.IOC_DARKWEB);
            addValidDataToMap(data, "dnsServerInfo", dnsServerInfo, Constant.DNS_SERVER_INFO_BUILT_IN);
        }
        return R.ok(data);
    }

    private void addValidListToMap(Map<String, Object> map, String key, List<Map> value, String source) {
        map.put(key, null);
        List<Map> list = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(value)) {
            for (int i = 0; i<value.size(); i++) {
                KnowledgeBase knowledgeBase = knowledgeBaseService.getOne(new QueryWrapper<KnowledgeBase>().like("source", source));
                if (ObjectUtil.isNotEmpty(knowledgeBase) && knowledgeBase.getStatus() == 1 && "1".equals(value.get(i).get("isValid").toString())) {
                    list.add(value.get(i));
                }
            }
            map.put(key, list);
        }
    }

    @Override
    public R queryEntityList(String entityType, Map<String, Object> params){
        preHandleParams(params);
        String resource = (String)params.get("resource");
        int pageNo = (Integer)params.get("pageNo");
        int pageSize = (Integer)params.get("pageSize");
        List<Map<String,Object>> entityList = createEntityList(entityType, resource);
        IPage page = listToPage(entityList, pageNo, pageSize);
        PageUtils pageUtils = new PageUtils(page);
        Map<String, Object> data = JSONObject.parseObject(JSONUtil.toJsonStr(pageUtils));
        data.put("resultType", "table");
        data.put("result", pageUtils.getList());
        data.remove("list");
        return R.ok(data);
    }

    private void preHandleParams(Map<String, Object> params) {
        int pageNo = ObjectUtil.isEmpty(params.get("pageNo")) ? 1 : Integer.parseInt((String) params.get("pageNo"));
        int pageSize = ObjectUtil.isEmpty(params.get("pageSize")) ? 10 : Integer.parseInt((String) params.get("pageSize"));
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);
    }

    private IPage listToPage(List<Map<String,Object>> list, int pageNo, int pageSize){
        List<Map<String,Object>> pageList = new ArrayList<>();
        int curIdx = pageNo > 1 ? (pageNo - 1) * pageSize : 0;
        for (int i = 0; i < pageSize && curIdx + i < list.size(); i++) {
            pageList.add(list.get(curIdx + i));
        }
        IPage page = new Page<>(pageNo, pageSize);
        page.setRecords(pageList);
        page.setTotal(list.size());
        return page;
    }

    private List<Map<String,Object>> createEntityList(String entityType, String resource) {
        List<Map<String,Object>> entityList = new ArrayList<>();
        if ("app".equals(entityType)){
        } else if ("domain".equals(entityType)){
            Map<String, Object> malware = iocMalwareService.queryDomainEntityTag(resource,false);
            Map<String, Object> darkweb = iocDarkwebService.queryDomainEntityTag(resource, false);
            if (ObjectUtil.isEmpty(malware)) {
                malware = iocMalwareService.queryDomainEntityTag(resource, true);
            }
            if (ObjectUtil.isEmpty(darkweb)) {
                darkweb = iocDarkwebService.queryDomainEntityTag(resource, true);
            }
            malware = createEnableMap("malware", malware, Constant.IOC_MALWARE);
            darkweb = createEnableMap("darkweb", darkweb, Constant.IOC_DARKWEB);
            mapChangeTimestamp(malware);
            mapChangeTimestamp(darkweb);
            addEntityList(entityList, malware);
            addEntityList(entityList, darkweb);
        } else if ("ip".equals(entityType)){
            Map<String, Object> malware = createEnableMap("malware", iocMalwareService.queryIpEntityTag(resource), Constant.IOC_MALWARE);
            Map<String, Object> darkweb = createEnableMap("darkweb", iocDarkwebService.queryIpEntityTag(resource), Constant.IOC_DARKWEB);
            Map<String, Object> dnsServerInfo = createEnableMap("dnsServerInfo", dnsServerInfoBuiltInService.queryIpEntityTag(resource), Constant.DNS_SERVER_INFO_BUILT_IN);
            mapChangeTimestamp(malware);
            mapChangeTimestamp(darkweb);
            mapChangeTimestamp(dnsServerInfo);
            addEntityList(entityList, malware);
            addEntityList(entityList, darkweb);
            addEntityList(entityList, dnsServerInfo);
        }
        sortByUpdateTime(entityList);
        return entityList;
    }

    private Map<String, Object> createEnableMap(String key, Map<String, Object> value, String source) {
        Map<String, Object> map = new HashMap<>();
        if (ObjectUtil.isNotEmpty(value)) {
            KnowledgeBase knowledgeBase = knowledgeBaseService.getOne(new QueryWrapper<KnowledgeBase>().like("source", source));
            if (ObjectUtil.isNotEmpty(knowledgeBase) && knowledgeBase.getStatus() == 1) {
                map.put(key, value);
            }
        }
        return map;
    }

    private List<Map> createEnableList(List<Map> value, String source) {
        List<Map> list = null;
        if (ObjectUtil.isNotEmpty(value)) {
            KnowledgeBase knowledgeBase = knowledgeBaseService.getOne(new QueryWrapper<KnowledgeBase>().eq("source", source));
            if (ObjectUtil.isNotEmpty(knowledgeBase) && knowledgeBase.getStatus() == 1) {
                list = value;
            }
        }
        return list;
    }

    private void addEntityList(List<Map<String,Object>> entityList, Map<String, Object> tagMap) {
        if(ObjectUtil.isNotEmpty(tagMap)) {
            Map.Entry<String, Object> next = tagMap.entrySet().iterator().next();
            if (ObjectUtil.isNotEmpty(next.getValue())) {
                entityList.add(tagMap);
            }
        }
    }

    private void addListTag(String ListTagsName, List<Map<String,Object>> entityList, List<Map> ListTags) {
        for (Map<String,Object> tag: ListTags) {
            Map<String, Object> tagMap = new HashMap<>();
            tagMap.put(ListTagsName, tag);
            entityList.add(tagMap);
        }
    }

    private void sortByUpdateTime(List<Map<String,Object>> entityList) {
        entityList.sort(Comparator.nullsLast(Comparator.comparing(obj -> {
            Map.Entry<String, Object> next = obj.entrySet().iterator().next();
            Map<String,Object> value = (Map)next.getValue();
            Long updateTime = 0L;
            if(ObjectUtil.isNotEmpty(value.get("updateTime"))) {
                updateTime = (Long) value.get("updateTime");
            }
            return 0-updateTime;
        })));
    }

    private void mapChangeTimestamp(Map<String,Object> tagMap) {
        if(ObjectUtil.isNotEmpty(tagMap)) {
            Map.Entry<String, Object> next = tagMap.entrySet().iterator().next();
            if (ObjectUtil.isNotEmpty(next.getValue())) {
                Map<String, Object> value = (Map)next.getValue();
                if(ObjectUtil.isNotEmpty(value.get("updateTime"))) {
                    if (value.get("updateTime") instanceof Date) {
                        Date updateTime = (Date) value.get("updateTime");
                        value.put("updateTime", updateTime.getTime() / 1000);
                    } else {
                        value.put("updateTime", value.get("updateTime"));
                    }
                }
                if (ObjectUtil.isNotEmpty(value.get("createTime"))) {
                    if (value.get("createTime") instanceof Date) {
                        Date createTime = (Date) value.get("createTime");
                        value.put("createTime", createTime.getTime() / 1000);
                    } else {
                        value.put("createTime", value.get("createTime"));
                    }
                }
                tagMap.put(next.getKey(),value);
            }
        }
    }

    private List<Map> listChangeTimestamp(List<Map> tagsList) {
        List<Map> newList = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(tagsList)) {
            for (Map tagMap : tagsList) {
                if (ObjectUtil.isNotEmpty(tagMap)) {
                    if (ObjectUtil.isNotEmpty(tagMap.get("updateTime"))) {
                        Date updateTime = (Date) tagMap.get("updateTime");
                        tagMap.put("updateTime", updateTime.getTime() / 1000);
                    }
                    if (ObjectUtil.isNotEmpty(tagMap.get("createTime"))) {
                        Date createTime = (Date) tagMap.get("createTime");
                        tagMap.put("createTime", createTime.getTime() / 1000);
                    }
                }
                newList.add(tagMap);
            }
        }
        return newList;
    }
}
