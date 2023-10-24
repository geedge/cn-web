package net.geedge.modules.knowledge.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import net.geedge.modules.sys.entity.SysUserEntity;

@Data
@TableName("cn_knowledge_base_audit_log")
public class KnowledgeBaseAuditLog {
	@TableId
	private Integer logId;

	private Integer knowledgeId;

	private String opType;

	private String description;

	private Integer commitVersion;

	private Integer status;
	
	private Integer isPublished;
	
	private Integer opUser;
	
	private Date opTime;

	private String color;
	
	private Integer apiKeyId;
	
	@TableField(exist = false)
	private SysUserEntity user;
}
