package net.geedge.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.modules.sys.entity.SysUserEntity;

import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2022/02/14/11:52
 */
public interface CtlService extends IService<SysUserEntity> {
    /**
     * 管理员账号密码重置接口
     */
    Map<String, Object> resetAdmin();
    /**
     * 数据库密码加密接口
     */
    Map<String, String> aes(String content);
}
