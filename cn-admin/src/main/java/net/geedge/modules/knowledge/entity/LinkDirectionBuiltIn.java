package net.geedge.modules.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("cn_link_direction_built_in ")
public class LinkDirectionBuiltIn {
	
	@TableId
	private Integer id;

	private String interfaceName;
	
	private Integer direction;

	private Long bandwidth;
	
	private String peerProvince;
	
	private String peerCity;
	
	private Integer linkId;
	
	private Integer knowledgeId;
}
