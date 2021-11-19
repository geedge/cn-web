package net.geedge.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.modules.sys.entity.SysConfigEntity;
/**
 * 系统安全设定
 *
 * @author lijinyang
 * @date 2021/11/22
 * */
public interface SysSecurityService extends IService<SysConfigEntity> {
    /**
     * 系统安全设定查询
     *
     */
    String querySecurity();
    /**
     * 系统安全设定修改
     *
     */
    boolean updateSecurity(String security);
}
