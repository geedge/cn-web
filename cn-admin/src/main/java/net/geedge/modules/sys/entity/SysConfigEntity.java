/**

 *

 *

 */

package net.geedge.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 系统配置信息
 *

 */
@Data
@TableName("sys_config")
public class SysConfigEntity {
	@TableId
	private Long id;
	@NotBlank(message="参数名不能为空")
	private String ckey;
	@NotBlank(message="参数值不能为空")
	private String cvalue;
	private Integer state;
	private String remark;

}
