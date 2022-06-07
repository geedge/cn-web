package net.geedge.modules.report.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import cn.hutool.core.lang.UUID;
import lombok.Data;
import net.geedge.modules.sys.entity.SysUserEntity;

@Data
@TableName("report_category")
public class ReportCategory {
	@TableId
	private Integer id;
	
	private String name;
		
	private Date ctime;
			
	private String remark;
		
	private String htmlContent;
	
	private String config;
	
	private Date utime;
		
	private Long uid;
	
	private Integer buildIn;
	
	@TableField(exist = false)
	private SysUserEntity sysUser;
}
