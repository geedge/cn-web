package net.geedge.modules.report.entity;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import net.geedge.modules.sys.entity.SysUserEntity;

@Data
@TableName("report_job")
public class ReportJob {
	@TableId
	private Integer id;
	
	private String name;
	
	private Date ctime;
	
	private Date ftime;
	
	private String remark;
	
	private String uniq;
	
	@JsonIgnore
	private String htmlContent;
	
	private Integer state;
	
	private Integer upload;
	
	private Date startTime;
	
	private Date endTime;
	
	private Long uid;
	
	private Integer tempId;
	
	private Integer categoryId;
	
	@TableField(exist = false)
	private List<ReportTask> reportTasks;
	
	@TableField(exist = false)
	private ReportTemp reportTemp;
	
	@TableField(exist = false)
	private SysUserEntity sysUser;
	
	@TableField(exist = false)
	private Double percent;
}
