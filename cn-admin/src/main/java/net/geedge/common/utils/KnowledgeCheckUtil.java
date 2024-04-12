package net.geedge.common.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.geedge.common.exception.CNException;
import org.apache.commons.lang3.math.NumberUtils;

import com.googlecode.ipv6.IPv6Address;
import com.googlecode.ipv6.IPv6AddressRange;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

public class KnowledgeCheckUtil {
	
	public static final String ipv6AddrDefault = "::";
	
	public static final String[] defaultIps={"0.0.0.0","::"};
	
	private static final String ipv4AddrDefault = "0.0.0.0";
	
	public static final String ipv6Mask = "ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff";
	
	public static final String maskRegex = "(255\\.255\\.255\\.(0|128|192|224|240|248|252|254|255))|(255\\.255\\.(0|128|192|224|240|248|252|254|255)\\.0)|(255\\.(0|128|192|224|240|248|252|254|255)\\.0\\.0)|((128|192|224|240|248|252|254|0)\\.0\\.0\\.0)";

    public static final String maskDicr = "(^\\d{1}$)|(^[1-2]{1}\\d{1}$)|(^3[0-2]{1}$)";

    private static final Pattern IPV4_PATTERN =
            Pattern.compile(
                    "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

    private static final Pattern IPV6_STD_PATTERN =
            Pattern.compile(
                    "^\\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:)))(%.+)?\\s*$");

    private static final Pattern IPV6_HEX_COMPRESSED_PATTERN =
            Pattern.compile(
                    "^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");

    private static final Pattern IPV4_PATTERN_WITH_CIDR = Pattern.compile("^(?:(?:[0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}(?:[0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\/([1-9]|[1-2]\\d|3[0-2])$");


    private static final Pattern IPV6_PATTERN_WITH_CIDR =
            Pattern.compile(
                    "^(([\\da-fA-F]{1,4}:){6}((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)|::([\\da−fA−F]1,4:)0,4((25[0−5]|2[0−4]\\d|[01]?\\d\\d?)\\.)3(25[0−5]|2[0−4]\\d|[01]?\\d\\d?)|^([\\da-fA-F]{1,4}:):([\\da-fA-F]{1,4}:){0,3}((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)|([\\da−fA−F]1,4:)2:([\\da−fA−F]1,4:)0,2((25[0−5]|2[0−4]\\d|[01]?\\d\\d?)\\.)3(25[0−5]|2[0−4]\\d|[01]?\\d\\d?)|^([\\da-fA-F]{1,4}:){3}:([\\da-fA-F]{1,4}:){0,1}((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)|([\\da−fA−F]1,4:)4:((25[0−5]|2[0−4]\\d|[01]?\\d\\d?)\\.)3(25[0−5]|2[0−4]\\d|[01]?\\d\\d?)|^([\\da-fA-F]{1,4}:){7}[\\da-fA-F]{1,4}|:((:[\\da−fA−F]1,4)1,6|:)|^[\\da-fA-F]{1,4}:((:[\\da-fA-F]{1,4}){1,5}|:)|([\\da−fA−F]1,4:)2((:[\\da−fA−F]1,4)1,4|:)|^([\\da-fA-F]{1,4}:){3}((:[\\da-fA-F]{1,4}){1,3}|:)|([\\da−fA−F]1,4:)4((:[\\da−fA−F]1,4)1,2|:)|^([\\da-fA-F]{1,4}:){5}:([\\da-fA-F]{1,4})?|([\\da−fA−F]1,4:)6:)\\/([1-9]|[1-9]\\d|1[01]\\d|12[0-8])$");
    private static final Pattern IPV6_HEX_COMPRESSED_PATTERN_WITH_CIDR =
            Pattern.compile(
                    "^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)\\/([1-9]|[1-9]\\d|1[01]\\d|12[0-8])$");
    private static final Pattern IPV6_STD_PATTERN_WITH_CIDR =
            Pattern.compile(
                    "^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}\\/([1-9]|[1-9]\\d|1[01]\\d|12[0-8])$");
	
	/**
     * 根据ip地址判断是否为ip4
     *
     * @param input IP地址
     * @return true 是 , false 不是
     */
    public static boolean isIPv4Address(final String input) {

        return IPV4_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6StdAddress(final String input) {

        return IPV6_STD_PATTERN.matcher(input).matches();
    }
    
    
    /**
     * CIDR格式转换为ip形式
     * @param bitMask
     * @return
     */
    public static String intToMask(int bitMask) {
        if (bitMask > 32)
            return null;

        int tmpMask[] = {0, 0, 0, 0};
        int times = bitMask / 8;
        int i = 0;
        for (; i < times; i++) {
            tmpMask[i] = 255;
        }
        if(bitMask-times*8==0){
            return tmpMask[0]+"."+tmpMask[1]+"."+tmpMask[2] +"."+tmpMask[3];}
        for (int j = 1; j <= 8; j++) {
            if (j <= bitMask - times * 8)
                tmpMask[i] = 2 * tmpMask[i] + 1;
            else
                tmpMask[i] = 2 * tmpMask[i];
        }
        return tmpMask[0] + "." + tmpMask[1] + "."+tmpMask[2] +"." + tmpMask[3];
    }
    
    /**
     * 根据ip地址判断是否为ip6
     * @param input
     * @return
     */
    public static boolean isIPv6Address(final String input) {
        return isIPv6StdAddress(input);
    }
    
    /**
     * 判断ipv6的掩码范围
     *
     * @param maskIp
     * @return
     */
    public static boolean isIp6Mask(String maskIp) {
        boolean flag = true;
        if (!NumberUtils.isDigits(maskIp)) {
            flag = false;
        } else {
            int num = Integer.parseInt(maskIp);
            if (num > 128 || num < 0) {
                flag = false;
            }
        }
        return flag;
    }
    
    public static boolean isMask(String maskIp) {
        boolean a = maskIp.matches(maskRegex);
        boolean b = maskIp.matches(maskDicr);
        if (!a && !b) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * IPv4 范围验证
     *
     * @param ip
     * @param
     * @param ip
     * @return 1:校验成功 -2:IP范围的起始IP必须小于终止IP
     */
    public static int validIPv4Range(String ip) {
        String prefix = ip.split("-")[0];
        String subfix = ip.split("-")[1];
        if (ipToLong(prefix) > ipToLong(subfix)) {
            //errInfo.append("IP范围的起始IP必须小于终止IP");
            return -2;
        }
        return 1;
    }
    
    public static long ipToLong(String strIp) {
        String[] ip = strIp.split("\\.");
        return (Long.parseLong(ip[0]) << 24)+(Long.parseLong(ip[1]) << 16) + (Long.parseLong(ip[2]) << 8) + Long.parseLong(ip[3]);
    }


    public static int ipToInt(String ipAddr) {
        return bytesToInt(ipToBytesByReg(ipAddr));
    }
    
    /**
     * 根据位运算把 byte[] -> int
     *
     * @param bytes
     * @return int
     */
    public static int bytesToInt(byte[] bytes) {
        int addr = bytes[3] & 0xFF;
        addr |= ((bytes[2] << 8) & 0xFF00);
        addr |= ((bytes[1] << 16) & 0xFF0000);
        addr |= ((bytes[0] << 24) & 0xFF000000);
        return addr;
    }
    
    /**
     * 把IP地址转化为int
     *
     * @param ipAddr
     * @return int
     */
    public static byte[] ipToBytesByReg(String ipAddr) {
        byte[] ret = new byte[4];
        try {
            String[] ipArr = ipAddr.split("\\.");
            ret[0] = (byte) (Integer.parseInt(ipArr[0]) & 0xFF);
            ret[1] = (byte) (Integer.parseInt(ipArr[1]) & 0xFF);
            ret[2] = (byte) (Integer.parseInt(ipArr[2]) & 0xFF);
            ret[3] = (byte) (Integer.parseInt(ipArr[3]) & 0xFF);
            return ret;
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(ipAddr + " is invalid IP");
        }

    }
    
	/**
     *验证 Ip格式是否正确
     *
     * @param val
     * @return
     */
    public static boolean ipFormat(String val) {
        String[] resFirArr = val.split("\\/");
        String[] resSecArr = val.split("-");
        boolean flag = true;
        if (resFirArr.length > 2 || resSecArr.length > 2) {//数据完整性
            flag = false;
        } else if (resFirArr.length == 2 && resSecArr.length == 1) {//"/"格式,mask
            String srcIp = resFirArr[0];
            String maskSrcIp = resFirArr[1];
            //如果ip4是整数值mask,进行ip形式的转换
            if (KnowledgeCheckUtil.isIPv4Address(srcIp) && maskSrcIp.matches(KnowledgeCheckUtil.maskDicr)) {
                maskSrcIp = KnowledgeCheckUtil.intToMask(Integer.parseInt(maskSrcIp));
            }
            //srcIp为ipv4，maskSrcIp非ipv4，格式错误
            if (KnowledgeCheckUtil.isIPv4Address(srcIp) && !KnowledgeCheckUtil.isIPv4Address(maskSrcIp)) {
                flag = false;
            }
            //srcIp为ipv6，maskSrcIp非ipv6，格式错误
            if (KnowledgeCheckUtil.isIPv6Address(srcIp) && (!KnowledgeCheckUtil.isIPv6Address(maskSrcIp) && !KnowledgeCheckUtil.isIp6Mask(maskSrcIp))) {
                flag = false;
            }
            //非ipv6非ipv4格式
            if ((!KnowledgeCheckUtil.isIPv6Address(srcIp) && !KnowledgeCheckUtil.isIPv6Address(maskSrcIp))
                    && (!KnowledgeCheckUtil.isIPv4Address(srcIp) && !KnowledgeCheckUtil.isIPv4Address(maskSrcIp))) {
                flag = false;
            }
            //maskSrcIp非掩码
            if (KnowledgeCheckUtil.isIPv4Address(srcIp) && KnowledgeCheckUtil.isIPv4Address(maskSrcIp) && !KnowledgeCheckUtil.isMask(maskSrcIp)) {
                flag = false;
            }
        } else if (resSecArr.length == 2 && resFirArr.length == 1) {//"-"格式,rang
            String srcIp = resSecArr[0];
            String maskSrcIp = resSecArr[1];
            //srcIp为ipv4，maskSrcIp非ipv4，格式错误
            if (KnowledgeCheckUtil.isIPv4Address(srcIp) && !KnowledgeCheckUtil.isIPv4Address(maskSrcIp)) {
                flag = false;
            }
            //srcIp为ipv6，maskSrcIp非ipv6，格式错误
            if (KnowledgeCheckUtil.isIPv6Address(srcIp) && !KnowledgeCheckUtil.isIPv6Address(maskSrcIp)) {
                flag = false;
            }
            //非ipv6
            if ((!KnowledgeCheckUtil.isIPv6Address(srcIp) && !KnowledgeCheckUtil.isIPv6Address(maskSrcIp))
                    && (!KnowledgeCheckUtil.isIPv4Address(srcIp) && !KnowledgeCheckUtil.isIPv4Address(maskSrcIp))) {
                flag = false;
            }
            //rang
            if (KnowledgeCheckUtil.isIPv4Address(srcIp) && KnowledgeCheckUtil.isIPv4Address(maskSrcIp)) {
                //if (!IpCheckUtil.isMask(maskSrcIp)) {
                int ipNum = KnowledgeCheckUtil.validIPv4Range(srcIp + "-" + maskSrcIp);
                if (ipNum != 1) {
                    flag = false;
                }
                //}
            }
        } else if (resSecArr.length == 1 && resFirArr.length == 1) {//mask
            String maskSrcIp = resSecArr[0].trim();
            //maskSrcIp非ipv4,ipv6，格式错误
            if (!KnowledgeCheckUtil.isIPv4Address(maskSrcIp) && !KnowledgeCheckUtil.isIPv6Address(maskSrcIp)) {
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }
    
    /**
     * IP解析校验
     * ip：单IP
     * ip1-ip2：ip range，与单ip的约束相同，并且ip1不能大于ip2，并且ip1和ip2的ip地址类型要一致，即同为ipv4或者ipv6
     * ip/CIDR：数值掩码类型的ip，ip与单ip的约束相同，并且如果ip为ipv4格式，则CIDR的取值为0-32，如果ip为ipv6格式，则CIDR的取值为0-128
     * ip/mask：点分式掩码类型的ip，ip与单ip的约束相同，并且如果ip为ipv4格式，则mask格式为255.*.*.*，如果ip为ipv6格式，则mask格式如ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff
     * @param ip 传递IP字符串
     */
    public static boolean validIpFormat(String input){
        try {
            if (StrUtil.isEmpty(input)) {
                return false;
            }
            if (!input.contains("/") && !input.contains("-")) {
                //单IP情况
                int ipType = getIpType(input);
                if (ipType == -1) {
                    return false;
                }
                if (ipType == 4) {
                    if (!isIPv4Address(input)) {
                        return false;
                    }
                } else {
                    if (!isIPv6Address(input)) {
                        return false;
                    }
                }
			} else if (input.contains("-")) {
				  //ip range
				  String[] ipArray = input.split("-");
				  if (ipArray.length != 2) {
				      return false;
				  }
				  String startIp = ipArray[0];
				  String endIp = ipArray[1];
				  int startType = getIpType(startIp);
				  int endType = getIpType(endIp);
				  if (startType == -1 || endType == -1) {
				      return false;
				  }
				  if (startType != endType) {
				      return false;
				  }
				  if (startType == 4) {
				      if (validIPv4Range(input) != 1) {
				          return false;
				      }
				  } else {
				      if (!validIPv6Range(startIp, endIp)) {
				          return false;
				      }
				  }
				} else {
				   //CIDR + MASK
				   String[] ipArray = input.split("/");
				   if (ipArray.length != 2) {
				       return false;
				   }
				   String ip = ipArray[0];
				   String temp = ipArray[1];
				   int ipType = getIpType(ip);
				   if (ipType == -1) {
				       return false;
				   }
				   if (NumberUtils.isDigits(temp)) {
				       //CIDR格式
				       if (!validIpTypeCidr(ipType, Integer.valueOf(temp))) {
				           return false;
				       }
				   } else {
				       //mask
				       if (ipType == 4) {
				           if (!isMask(temp)) {
				               return false;
				           }
				       }
				   }
				}
            String[] ipdata =ipData(input);
            String src_ip = ipdata[1];
            String mask_src_ip = ipdata[2];
            String type = ipdata[3];
            boolean b = true;
            if (StrUtil.equals("4",type)){
                if (StrUtil.equals(ipv4AddrDefault,src_ip) && (StrUtil.equals(ipv4AddrDefault,mask_src_ip) || StrUtil.equals(mask_src_ip,"32") || StrUtil.equals(mask_src_ip,"255.255.255.255"))){
                    b = false;
                }
            }else if (StrUtil.equals("6",type)){
                if ((StrUtil.equals(ipv6AddrDefault,src_ip) || StrUtil.equals(ipv6Mask,src_ip)) && (
                		StrUtil.equals(ipv6AddrDefault,mask_src_ip) || StrUtil.equals(ipv6Mask,mask_src_ip) || StrUtil.equals("128",mask_src_ip)
                        )){
                    b = false;
                }
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }
    
    /**
     * 根据IP中间连接符号去拆分开始结束IP
     * @param val example: 192.0.2.130-192.0.2.131 或 192.168.0.1/24
     * @return
     */
    public static String[] ipData(String val) {
        //第一位存format，第二位ip，第三位maskIp
        String[] ipData = new String[4];
        String[] ipFir = val.split("/");//mask
        String[] ipSec = val.split("-");//range
        ipData[3] = isIPv6Address(ipFir[0]) || isIPv6Address(ipSec[0]) ? "6" : "4";
        Integer addrType = Integer.valueOf(ipData[3]);
        if (ipFir.length == 2 && ipSec.length == 1) {//mask
            ipData[0] = NumberUtils.isDigits(ipFir[1]) ? "CIDR" : "mask";
            ipData[1] = ipFir[0];
            ipData[2] = ipFir[1];
        }
        if (ipFir.length == 1 && ipSec.length == 2) {//range
            ipData[0] = "range";
            ipData[1] = ipSec[0];
            ipData[2] = ipSec[1];
        }
        return ipData;
    }

    /**
     * 根据value获取IP格式、开始及结束IP
     * @param val example: 192.0.2.130-192.0.2.131 或 192.168.0.1/24
     * @return
     */
    public static String[] ipToData(String val) {
        //第一位存format，第二位ip，第三位maskIp
        String[] ipData = new String[4];
        String[] ipFir = val.split("/");//mask
        String[] ipSec = val.split("-");//range
        ipData[3] = isIPv6Address(ipFir[0]) || isIPv6Address(ipSec[0]) ? "6" : "4";
        Integer addrType = Integer.valueOf(ipData[3]);
        if (ipFir.length == 2 && ipSec.length == 1) {//mask
            ipData[0] = NumberUtils.isDigits(ipFir[1]) ? "CIDR" : "mask";
            ipData[1] = ipFir[0];
            ipData[2] = ipFir[1];
        }
        if (ipFir.length == 1 && ipSec.length == 2) {//range
            ipData[0] = "Range";
            ipData[1] = ipSec[0];
            ipData[2] = ipSec[1];
        }
        if (ipFir.length == 1 && ipSec.length == 1) {//Single
            ipData[0] = "Single";
            ipData[1] = ipSec[0];
            ipData[2] = ipSec[0];
        }
        return ipData;
    }

    /**
     * 判断Ipv6结束Ip是否大于开始Ip startIp < endIp
     * @param startIp 开始IP
     * @param endIp 结束IP
     * @return true: 正确 false 错误
     */
    public static boolean validIPv6Range(String startIp,String endIp) {
        try {
            IPv6AddressRange.fromFirstAndLast(IPv6Address.fromString(startIp), IPv6Address.fromString(endIp));
        } catch (IllegalArgumentException ex) {
            return false;
        }
        return true;
    }
    
    /**
     * 判断cidr是否与IP类型对应 ipv4 1-32  ipv6 1-128
     * @param ipType ip类型 4 ,6
     * @param cidr cidr 数值
     * @return
     */
    public static boolean validIpTypeCidr(Integer ipType,Integer cidr){
        if(ipType == 4){
            if(cidr >0 && cidr <= 32 ){
                return true;
            }
        }else if(ipType == 6){
            if(cidr >0 && cidr <= 128){
                return true;
            }
        }
        return false;
    }
    
    /**
     * 根据IP类型
     * @param input 输入参数
     * @return 4: ipv4 , 6:ipv6 , -1 未识别
     */
    public static int getIpType(String input){
        if(StrUtil.isEmpty(input)){
            return -1;
        }
        if(isIPv4Address(input)){
            return 4;
        }else if(isIPv6Address(input)){
            return 6;
        }else {
            return -1;
        }
    }
    
    public static boolean validateFqdn(String val) {
	    // 加上域名规定字符校验
	    if (StrUtil.isNotBlank(val)) {
            //域名只支持 字母数字.-_
            String pattern = "^[*$]{1}[0-9a-zA-Z-._]{1,}$";
            Pattern r = Pattern.compile(pattern);
            // 只支持右匹配, 完全匹配
            if ((StrUtil.startWith(val, "*") || StrUtil.startWith(val, "$"))
                    && !(StrUtil.endWith(val, "*") || StrUtil.endWith(val, "$"))) {
                if (StrUtil.startWith(val, "$")){
                    if (StrUtil.startWith(disposeFqdn(val), ".")){
                        return false;
                    }
                    String strFqdn = val.replace("$", "").trim();
//                  域名中的标号都由英文字母和数字组成，每一个标号不超过63个字符，也不区分大小写字母。标号中除连字符（-）外不能使用其他的标点符号。
//                  级别最低的域名写在最左边，而级别最高的域名写在最右边。由多个标号组成的完整域名总共不超过255个字符
                    String fqdnPattern = "^(?=^.{3,255}$)[a-zA-Z0-9_][-a-zA-Z0-9_]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$";
                    Pattern fqdnr = Pattern.compile(fqdnPattern);
                    Matcher m = fqdnr.matcher(strFqdn.trim());
                    if (!m.matches()){
                    	return false;
                    }
                }
                Matcher m = r.matcher(val.trim());
                if (!m.matches()) {
                    return false;
                }
                //验证域名的合法 长度小于255
                String domain = val.replace("*", "").replace("$", "").trim();
                //域名 长度小于255最多三级，不以.-结尾
                if (domain.length() > 255) {
                    return false;
                }
                if (domain.length() < 3){
                    return false;
                }
                //域名不能以.或-或_结尾，且.与.不能连续
                if (domain.contains("..") || StrUtil.endWith(domain, ".") || StrUtil.endWith(domain, "-") || StrUtil.endWith(domain, "_")) {
                    return false;
                }
                //每个.之间字符不大于63
                String[] split = domain.split("\\.");
                for (int i = 0; i < split.length; i++) {
                    if (split[i].length() > 63) {
                        return false;
                    }
                }
            } else {
                pattern = "^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$";
                if(Pattern.matches(pattern, val)) {
                    return true;
                }
                return false;
            }
        }else {
	    	return false;
	    }
		return true;
	}
	
    public static String disposeFqdn(String val){
        String fqdn;
        if (val.startsWith("*") || val.startsWith("$")){
            fqdn = val.substring(1);
        }else {
            fqdn = ".";
        }
        return fqdn;
    }
	    
    public static boolean validateApp(String val) {
    	String pattern = ".*[*?!&$%#^,.;:<>/@\"{}\\-\\]\\[=+_\\\\|].*$";
    	if(Pattern.matches(pattern, val)) {
    		return false;
    	}
    	return true;
    }

    /**
     * ip相关处理 校验ip格式 返回值包含 计算起始ip 末尾ip
     * @param ipFormat
     * @param ip1
     * @param ip2
     * @return
     */
    public static Map<String,String> ipBinaryHandler(String ipFormat, String ip1, String ip2){
        Map<String,String> result = Tool.MapUtil.newHashMap();
        boolean validIpFormat = KnowledgeCheckUtil.validIpFormat(ip1);
        if(!validIpFormat) {
            throw new CNException(RCode.IMPORT_ERROR);
        }
        if(StrUtil.equals(ipFormat, Constant.IpFormatType.SINGLE.getValue())) {
            result.put("ip2",ip1);
            result.put("start_ip_binary",ip1);
            result.put("end_ip_binary",ip1);
        }else if(StrUtil.equals(ipFormat, Constant.IpFormatType.RANGE.getValue())){
            validIpFormat = KnowledgeCheckUtil.validIpFormat(ip1+"-"+ip2);
            if(!validIpFormat) {
                throw new CNException(RCode.IMPORT_ERROR);
            }
            result.put("ip2",ip2);
            result.put("start_ip_binary",ip1);
            result.put("end_ip_binary",ip2);
        }else {
            validIpFormat = KnowledgeCheckUtil.validIpFormat(ip1+"/"+ip2);
            if(!validIpFormat) {
                throw new CNException(RCode.IMPORT_ERROR);
            }
            result.put("ip2",ip2);
            boolean iPv4Address = KnowledgeCheckUtil.isIPv4Address(ip1);
            Map<String, String> ipsStartEnd = null;
            String startIp = "";
            String endIp = "";
            if(iPv4Address) {
                ipsStartEnd = IPUtils.getIpsV4ByCidr(ip1+"/"+ip2);
                startIp = ipsStartEnd.get("startIpStr");
                endIp = ipsStartEnd.get("endIpStr");
            }else {
                ipsStartEnd = IPUtils.getIpsV6ByCidr(ip1+"/"+ip2);
                startIp = ipsStartEnd.get("startIpStr");
                endIp = ipsStartEnd.get("endIpStr");
            }
            result.put("start_ip_binary",startIp);
            result.put("end_ip_binary",endIp);
        }
        return result;
    }
}
