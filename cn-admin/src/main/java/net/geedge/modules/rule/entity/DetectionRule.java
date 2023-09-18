package net.geedge.modules.rule.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("detection_rule")
public class DetectionRule {
	
	@TableId
	private Integer ruleId;
	
	private String ruleType;
	
	private String name;
	
	private String category;
	
	private String eventType;
	
	private String description;
	
	private Integer isBuiltIn;
	
	private Integer status;
	
	private String ruleConfig;
	
	private String trigger;
	
	private Integer cUser;
	
	private Date cTime;
	
	private Integer opUser;
	
	private Date opTime;
	
	private Integer isValid;
}
