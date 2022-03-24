package net.geedge.modules.report.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("report_result")
public class ReportResult {
	@TableId
	private Integer id;
	
	private Integer taskId;
	
	private String result;
	
	private Date ctime;
}
