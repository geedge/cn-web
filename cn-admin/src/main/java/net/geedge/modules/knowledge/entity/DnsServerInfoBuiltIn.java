package net.geedge.modules.knowledge.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("cn_dns_server_info_built_in")
public class DnsServerInfoBuiltIn {
	
	@TableId
	private Integer id;
	
	private String ipAddr;
	
	private String locationCountry;
	
	private String locationProvince;
	
	private String locationRegion;
	
	private String asn;
	
	private String dnsServerRole;
	
	private String orgnization;
	
	private String operationSystem;
	
	private String dnsSoftware;
	
	private Integer dnssecSupport;
	
	private Integer dotSupport;
	
	private Integer dohSupport;
	
	private String rootMirrorLocations;
	
	private Date createTime;
	
	private Date updateTime;
}
