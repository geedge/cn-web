package net.geedge.modules.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("cn_internal_ip_built_in")
public class InternalIpBuiltIn {
	
	@TableId
	private Integer id;
	
	private String addrFormat;
	
	private String ip1;
	
	private String ip2;
	
	private String startIpBinary;
	
	private String endIpBinary;
}
