/**

 *

 *

 */

package net.geedge.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.modules.sys.entity.SysLogEntity;

import java.util.Map;


/**
 * 系统日志
 *

 */
public interface SysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
