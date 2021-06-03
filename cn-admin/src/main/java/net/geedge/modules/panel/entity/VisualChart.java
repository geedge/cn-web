package net.geedge.modules.panel.entity;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import net.geedge.modules.sys.entity.SysUserEntity;

@Data
@TableName("visual_chart")
public class VisualChart {
	@TableId
	private Integer id;
	private String name;
	private String i18n;
	private Integer panelId;
	private Integer pid;
	private Integer type;
	private Integer x;
	private Integer y;
	private Integer w;
	private Integer h;
	private String params;
	private Long cby;
	private Date ctime;
	private Long uby;
	private Date utime;
	private String remark;
	private Integer state;
	private Integer buildIn;
	
	@TableField(exist = false)
	private SysUserEntity uuser;
	
	@TableField(exist = false)
	private SysUserEntity cuser;
	
	@TableField(exist = false)
	private List<VisualChart> children;
	
	@TableField(exist = false)
	private VisualChart parent;
	
	@TableField(exist = false)
	private VisualPanel panel;
}
