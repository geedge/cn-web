/**

 *

 *
 * 
 */

package net.geedge.common.utils;

import cn.hutool.core.net.Ipv4Util;
import cn.hutool.log.Log;
import org.apache.commons.lang.StringUtils;

import com.googlecode.ipv6.IPv6Address;
import com.googlecode.ipv6.IPv6Network;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * IP地址
 *
 * @author Mark sunlightcs@gmail.com
 */
public class IPUtils {
	private static Log logger = Log.get();

	/**
	 * 获取IP地址
	 * 
	 * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
	 * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
	 */
	public static String getIpAddr(HttpServletRequest request) {
    	String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
        	logger.error("IPUtils ERROR ",e);
        }
        
//        //使用代理，则获取第一个IP地址
//        if(StringUtils.isEmpty(ip) && ip.length() > 15) {
//			if(ip.indexOf(",") > 0) {
//				ip = ip.substring(0, ip.indexOf(","));
//			}
//		}
        
        return ip;
    }
	
	/**
	 * ipv4 通过cidr获取起始网络段地址
	 * @param cidr
	 * @return
	 */
	public static Map<String,String> getIpsV4ByCidr(String cidr) {
        Map<String,String> map = new HashMap<>();
        String ip = cidr.split("/")[0];
        int mask = Integer.parseInt(cidr.split("/")[1]);
        String ipFrom = Ipv4Util.getBeginIpStr(ip, mask);
        String ipTo = Ipv4Util.getEndIpStr(ip, mask);
        map.put("startIpStr",ipFrom);
        map.put("endIpStr",ipTo);
        return map;
    }
	
	/**
	 * ipv6 通过cidr获取起始网络段地址
	 * @param cidr
	 * @return
	 */
	public static Map<String,String>  getIpsV6ByCidr(String cidr) {
        String ip = cidr.split("/")[0];
        int maskNum = Integer.parseInt(cidr.split("/")[1]);
        if (ip.contains("%")){
            ip = ip.substring(0,ip.indexOf("%"));
        }
        Map<String,String> map = new HashMap<>();
        IPv6Network network = IPv6Network.fromString(ip+"/"+maskNum);
        IPv6Address b = network.getLast();
        IPv6Address a = network.getFirst();
        map.put("startIpStr",a.toLongString());
        map.put("endIpStr",b.toLongString());
        return map;
    }
	
	
}
