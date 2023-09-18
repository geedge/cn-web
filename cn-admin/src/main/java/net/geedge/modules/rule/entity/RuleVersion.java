package net.geedge.modules.rule.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("rule_version")
public class RuleVersion {
	
	private Integer version;
	
	private Date opTime;
}
