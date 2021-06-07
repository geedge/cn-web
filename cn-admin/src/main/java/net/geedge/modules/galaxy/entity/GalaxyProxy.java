package net.geedge.modules.galaxy.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("galaxy_proxy")
public class GalaxyProxy {
	@TableId
	private Integer id;
	private String name;
	private String path;
	private String version;
	private String method;
	private String targetUrl;
	private String targetParam;
	private String targetHeader;
	private String preHandle;
	private String postHandle;
	private Date ctime;
	private Date utime;
	private Integer state;
	private String remark;
}
