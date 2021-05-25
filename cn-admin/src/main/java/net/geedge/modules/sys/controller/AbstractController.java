/**

 *

 *

 */

package net.geedge.modules.sys.controller;

import cn.hutool.log.Log;
import net.geedge.modules.sys.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;

/**
 * Controller公共组件
 *

 */
public abstract class AbstractController {
	protected Log logger = Log.get();
	
	protected SysUserEntity getUser() {
		return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getUserId() {
		return getUser().getId();
	}

}
