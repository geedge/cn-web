package net.geedge.modules.sys.service.impl;


import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.common.utils.Constant;
import net.geedge.common.utils.Tool;
import net.geedge.modules.sys.dao.SysUserDao;
import net.geedge.modules.sys.entity.SysUserEntity;
import net.geedge.modules.sys.service.CtlService;
import net.geedge.modules.sys.service.SysUserService;
import net.geedge.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2022/02/14/13:39
 */
@Service("CtlService")
public class CtlServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements CtlService{
    private static final Log log = Log.get();

    @Value("${cn.adminId:1}")
    private String adminId;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 管理员账号密码重置接口
     */
    @Override
    public Map<String, Object> resetAdmin() {
        String pin = Tool.RandomUtil.randomNumbers(10);
        SysUserEntity user = sysUserService.getOne(new QueryWrapper<SysUserEntity>().lambda().eq(SysUserEntity::getId, adminId));
        if(Tool.ObjectUtil.isEmpty(user)) {
            user = new SysUserEntity();
        }
        user.setName("admin");
        user.setUsername("admin");
        user.setCtime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setSalt(salt);
        user.setPin(ShiroUtils.sha256(pin, user.getSalt()));
        user.setCby(0);
        sysUserService.saveOrUpdate(user);

        Map<String,Object> result =new HashMap<String,Object>();
        result.put("id", user.getId());
        result.put("name", "admin");
        result.put("username", "admin");
        result.put("pin", pin);
        return result;
    }
    /**
     * 数据库密码加密接口
     */
    @Override
    public Map<String, String> aes(String content) {
        Map<String,String> result =new HashMap<String,String>();
        String encrypt = Tool.AesUtil.encrypt(content, Constant.AES_SECRET_KEY);
        result.put("content", content);
        result.put("encrypt", encrypt);
        return result;
    }
}
