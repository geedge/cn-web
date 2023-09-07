package net.geedge.modules.rule.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("rule_commit_history")
public class RuleCommitHistory {
	@TableId
	private Integer opSeq;
	
	private Integer ruleId;
	
	private String opType;
	
	private Integer opVersion;
	
	private Integer opUser;
	
	private Date opTime;
}
