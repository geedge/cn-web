package net.geedge.modules.sys.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("sys_api_key")
public class SysApiKey {
	
	private Integer id;
	
	private String name;
	
	private String token;
	
	private Integer roleId;
	
	private Integer uby;
	
	private Date utime;
	
	private Date etime;
	
	@TableField(exist = false)
	private SysUserEntity createUser;
	
	@TableField(exist = false)
	private SysRoleEntity role;
}
