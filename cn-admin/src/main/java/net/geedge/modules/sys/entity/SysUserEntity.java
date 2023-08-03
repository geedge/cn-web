package net.geedge.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.geedge.common.validator.group.AddGroup;
import net.geedge.common.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系统用户
 */
@Data
@TableName("sys_user")
public class SysUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户ID
	 */
	@TableId
	private Long id;

	private String name;
	/**
	 * 用户名
	 */
	@NotBlank(message="登录用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String username;
	
	/**
	 * 密码
	 */
	@NotBlank(message="密码不能为空", groups = AddGroup.class)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String pin;

	/**
	 * 盐
	 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String salt;
	/**
	 * 语言
	 */
	private String lang;
	/**
	 * 主题
	 */
	private String theme;

	private String lastLoginIp;
	
	private Date lastLoginTime;
	
	/**
	 * 创建时间
	 */
	private Date ctime;
	private Integer cby;
	/**
	 * 邮箱
	 */
	@NotBlank(message="邮箱不能为空", groups = {AddGroup.class, UpdateGroup.class})
	@Email(message="邮箱格式不正确", groups = {AddGroup.class, UpdateGroup.class})
	private String email;

	private String mobile;
	/**
	 * 状态  0：禁用   1：正常
	 */
	private Integer status;
	/**
	 * 来源
	 */
	private String source;
	
	private Integer buildIn;
	/**
	 * 角色ID列表
	 */
	@TableField(exist = false)
	private String roleIds;

	/**
	 * 用户组ID列表
	 */
	@TableField(exist=false)
	private List<Long> usergroupIds;

	
	
	/**
	 * 用户关联角色列表
	 */
	@TableField(exist = false)
	private List<SysRoleEntity> roles;

	@TableField(exist = false)
	private String userName;

	@TableField(exist = false)
	private Integer apiKeyId;
}
