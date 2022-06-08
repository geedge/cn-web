package net.geedge.modules.report.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.hutool.core.lang.UUID;
import lombok.Data;
import net.geedge.modules.sys.entity.SysUserEntity;

@Data
@TableName("report_temp")
public class ReportTemp {
	@TableId
	private Integer id;
	
	private String name;
		
	private Date ctime;
			
	private String remark;
	
	private String source;
		
	private String uniq;
	
	@JsonIgnore
	private String htmlContent;
	
	private String config;
	
	private Date utime;
	
	private Integer state;
	
	private Integer categoryId;
	
	private Long uid;
	
	private Integer buildIn;
	
	private Date schedulerStart;
	
	private Date schedulerEnd;
	
	@TableField(exist = false)
	private SysUserEntity sysUser;
	
	@TableField(exist = false)
	private Integer total;
}
