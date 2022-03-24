package net.geedge.modules.report.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import cn.hutool.core.lang.UUID;
import lombok.Data;

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
	
	private String htmlContent;
	
	private String config;
	
	private Date utime;
	
	private Integer state;
}
