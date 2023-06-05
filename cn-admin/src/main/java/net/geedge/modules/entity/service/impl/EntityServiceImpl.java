package net.geedge.modules.entity.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.net.InetAddresses;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.*;
import net.geedge.modules.entity.service.EntityService;
import net.geedge.modules.knowledge.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.net.InetAddress;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
        return data;
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
        return data;
    }

    @Override
    public Map<String, Object> queryEntityList(String entityType, Map<String, Object> params){
        String resource = (String)params.get("resource");
        int pageNo = Integer.parseInt((String) params.get("pageNo"));
        int pageSize = Integer.parseInt((String) params.get("pageSize"));
        List<Map<String,Object>> entityList = createEntityList(entityType, resource);
        IPage page = listToPage(entityList, pageNo, pageSize);
        PageUtils pageUtils = new PageUtils(page);
        Map<String, Object> data = JSONUtil.parseObj(JSONUtil.toJsonStr(pageUtils));
        data.put("resultType", "table");
        data.put("result", pageUtils.getList());
        data.remove("list");
        return data;
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
        List<Map> userDefinedTags = new ArrayList<>();
        if ("app".equals(entityType)){
            Map<String, Object> category = new HashMap<>();
            category.put("category",appCategoryBuiltInService.queryEntityDetail(resource));
            userDefinedTags = appTagUserDefinedService.queryAppEntityTag(resource);
            mapChangeTimestamp(category);
            addEntityList(entityList, category);
        } else if ("domain".equals(entityType)){
            Map<String, Object> whois = new HashMap<>();
            Map<String, Object> category = new HashMap<>();
            Map<String, Object> malware = new HashMap<>();
            Map<String, Object> darkweb = new HashMap<>();
            whois.put("whois", fqdnWhoIsBuiltInService.queryEntityDetail(resource));
            category.put("category", fqdnCategoryBuiltInService.queryEntityDetail(resource));
            malware.put("malware", iocMalwareService.queryDomainEntityTag(resource));
            darkweb.put("darkweb", iocDarkwebService.queryDomainEntityTag(resource));
            userDefinedTags = domainTagUserDefinedService.queryDomainEntityTag(resource);
            mapChangeTimestamp(whois);
            mapChangeTimestamp(category);
            mapChangeTimestamp(malware);
            mapChangeTimestamp(darkweb);
            addEntityList(entityList, whois);
            addEntityList(entityList, category);
            addEntityList(entityList, malware);
            addEntityList(entityList, darkweb);
        } else if ("ip".equals(entityType)){
            Map<String, Object> malware = new HashMap<>();
            Map<String, Object> darkweb = new HashMap<>();
            Map<String, Object> psiphon3Ip = new HashMap<>();
            malware.put("malware", iocMalwareService.queryIpEntityTag(resource));
            darkweb.put("darkweb", iocDarkwebService.queryIpEntityTag(resource));
            psiphon3Ip.put("psiphon3Ip", psiphon3IpService.queryIpEntityTag(resource));
            userDefinedTags = ipTagUserDefinedService.queryIpEntityTag(resource);
            mapChangeTimestamp(malware);
            mapChangeTimestamp(darkweb);
            mapChangeTimestamp(psiphon3Ip);
            addEntityList(entityList, malware);
            addEntityList(entityList, darkweb);
            addEntityList(entityList, psiphon3Ip);
        }
        userDefinedTags = listChangeTimestamp(userDefinedTags);
        addUserDefinedTag(entityList, userDefinedTags);
        sortByUpdateTime(entityList);
        return entityList;
    }

    private void addEntityList(List<Map<String,Object>> entityList, Map<String, Object> tagMap) {
        if(ObjectUtil.isNotEmpty(tagMap)) {
            Map.Entry<String, Object> next = tagMap.entrySet().iterator().next();
            if (ObjectUtil.isNotEmpty(next.getValue())) {
                entityList.add(tagMap);
            }
        }
    }

    private void addUserDefinedTag(List<Map<String,Object>> entityList, List<Map> userDefinedTags) {
        for (Map<String,Object> tag: userDefinedTags) {
            Map<String, Object> tagMap = new HashMap<>();
            tagMap.put("userDefinedTag", tag);
            entityList.add(tagMap);
        }
    }

    private void sortByUpdateTime(List<Map<String,Object>> entityList) {
        entityList.sort(Comparator.comparing(obj -> {
                    Map.Entry<String, Object> next = obj.entrySet().iterator().next();
                    Map<String,Object> value = (Map)next.getValue();
                    Long updateTime = (Long)value.get("updateTime");
                    return 0-updateTime;
                }));
    }

    private void mapChangeTimestamp(Map<String,Object> tagMap) {
        if(ObjectUtil.isNotEmpty(tagMap)) {
            Map.Entry<String, Object> next = tagMap.entrySet().iterator().next();
            if (ObjectUtil.isNotEmpty(next.getValue())) {
                Map<String, Object> value = (Map)next.getValue();
                if (value.get("updateTime") instanceof Date) {
                    Date updateTime = (Date) value.get("updateTime");
                    Date createTime = (Date) value.get("createTime");
                    value.put("updateTime", updateTime.getTime() / 1000);
                    value.put("createTime", createTime.getTime() / 1000);
                } else {
                    value.put("updateTime", value.get("updateTime"));
                    value.put("createTime", value.get("createTime"));
                }
                tagMap.put(next.getKey(),value);
            }
        }
    }

    private List<Map> listChangeTimestamp(List<Map> tagsList) {
        List<Map> newList = new ArrayList<>();
        for (Map tagMap: tagsList) {
            if (ObjectUtil.isNotEmpty(tagMap)) {
                Date updateTime = (Date) tagMap.get("updateTime");
                Date createTime = (Date) tagMap.get("createTime");
                tagMap.put("updateTime", updateTime.getTime()/1000);
                tagMap.put("createTime", createTime.getTime()/1000);
                newList.add(tagMap);
            }
        }
        return newList;
    }
}
