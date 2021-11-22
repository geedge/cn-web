package net.geedge.modules.sys.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.modules.sys.entity.SysConfigEntity;


/**
 * 系统外观设置
 *
 * @author lijinyang
 * @date 2021/11/17
 * */
public interface SysAppearanceService extends IService<SysConfigEntity> {
    /**
     * 系统外观查询
     */
    JSONObject queryAppearance();
    /**
     * 系统外观更改
     */
    boolean updateAppearance(JSONObject appearance);
}
