package net.geedge.modules.report.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("report_task")
public class ReportTask {
	@TableId
	private Integer id;
	
	private Integer jobId;
			
	private String type;
	
	private Integer state;
	
	private Double percent;
	
	private Date startTime;
	
	private Date endTime;
	
	private Date ctime;
	
	private String source;
	
	private Integer tempId;
		
	private String config;
	
	private String credential;
	
	private String failedMsg;
		
	@TableField(exist = false)
	private ReportResult reportResult;
}
