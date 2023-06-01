package net.geedge.modules.entity.service.impl;

import cn.hutool.core.net.Ipv4Util;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.net.InetAddresses;
import com.googlecode.ipv6.IPv6Address;
import net.geedge.common.exception.CNException;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.*;
import net.geedge.modules.entity.service.EntityService;
import net.geedge.modules.knowledge.service.*;
import org.apache.tomcat.util.net.IPv6Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;


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

    @Override
    public Map<String, Object> queryEntityDetail(String entityType, String resource) {
        Map<String, Object> data = new HashMap<>();
        if ("app".equals(entityType)){
            Map<String, Object> categories = appCategoryBuiltInService.queryEntityDetail(resource);
            data.put("categories", categories);
        } else if ("domain".equals(entityType)){
            Map<String, Object> whois = fqdnWhoIsBuiltInService.queryEntityDetail(resource);
            Map<String, Object> categories = fqdnCategoryBuiltInService.queryEntityDetail(resource);
            data.put("whois", whois);
            data.put("categories", categories);
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
        validateEntityDetail(data);
        return data;
    }

    private void validateEntityDetail(Map<String, Object> data) {
        Boolean allEmpty = true;
        for (Object value : data.values()) {
            if (ObjectUtil.isNotEmpty(value)){
                allEmpty = false;
            }
        }
        if (allEmpty) {
            throw new CNException(RCode.ENTITY_DETAIL_NOT_EXIST);
        }
    }

    private String toIpNumber(String resource) throws Exception{
        InetAddress byName = InetAddress.getByName(resource);
        BigInteger ipNumber = InetAddresses.toBigInteger(byName);
        return ipNumber.toString();
    }
}
