package net.geedge.modules.knowledge.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import net.geedge.modules.sys.entity.SysUserEntity;

@Data
@TableName("knowledge_base")
public class KnowledgeBase {
	@TableId
	private Integer id;
	
	private String tagName;
	
	private String tagType;
	
	private String remark;
	
	private Long cby;
	
	private Date ctime;
	
	private Long uby;
	
	private Date utime;
	
	private String knowledgeId;
	
	@TableField(exist = false)
	private String version;
	
	@TableField(exist = false)
	private String path;
	
	@TableField(exist = false)
	private String format;

	@TableField(exist = false)
	private SysUserEntity cbyObj;

	@TableField(exist = false)
	private SysUserEntity ubyObj;
}
