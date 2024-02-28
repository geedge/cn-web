package net.geedge.modules.location.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("user_subscriber")
public class UserSubscriber {
	@TableId
	private Integer id;
	
	private Long userId;
	
	private String subscriberId;
	
	private Date ctime;
	
	private Date utime;
	
	@TableField(exist = false)
	private Integer active = 0;
}
