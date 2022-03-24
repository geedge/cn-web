package net.geedge.modules.report.entity;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("report_job")
public class ReportJob {
	@TableId
	private Integer id;
	
	private String name;
	
	private Date ctime;
	
	private String remark;
	
	private String uniq;
	
	private String htmlContent;
	
	private Integer state;
	
	private Integer upload;
	
	@TableField(exist = false)
	private List<ReportTask> reportTasks;
}
