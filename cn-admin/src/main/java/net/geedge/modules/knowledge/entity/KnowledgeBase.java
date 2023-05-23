package net.geedge.modules.knowledge.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("cn_knowledge_base")
public class KnowledgeBase {
	@TableId
	private Integer knowledgeId;

	private String name;

	private String category;

	private String source;

	private String description;

	private Date opTime;

	private Date cTime;

	private Long opUser;

	private Integer isBuiltIn;

	private Integer isPublished;

	private Integer status;

}
