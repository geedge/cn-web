package net.geedge.common.magic;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.geedge.common.utils.Tool;
import net.geedge.modules.sys.entity.SysUserEntity;
import net.geedge.modules.sys.service.SysUserService;
import net.geedge.modules.sys.shiro.ShiroUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.ssssssss.magicapi.core.context.MagicUser;
import org.ssssssss.magicapi.core.exception.MagicLoginException;
import org.ssssssss.magicapi.core.interceptor.AuthorizationInterceptor;



/**
 * @Auther: lijinyang
 * @Date: 2022/12/27/16:11
 */
@Component
public class MagicAuthorizationInterceptor implements AuthorizationInterceptor {

    private final SysUserService sysUserService;

    private String validToken;

    private MagicUser magicUser;

    private final RedisTemplate redisTemplate;

    public MagicAuthorizationInterceptor(SysUserService sysUserService,RedisTemplate redisTemplate) {
        this.sysUserService = sysUserService;
        this.redisTemplate = redisTemplate;
    }
    /**
     * 配置是否需要登录
     */
    @Override
    public boolean requireLogin() {
        return true;
    }

    /**
     * 根据Token获取User
     */
    @Override
    public MagicUser getUserByToken(String token) throws MagicLoginException {
        if (requireLogin() && token != null && redisTemplate.hasKey(token)) {
            String str = (String)redisTemplate.opsForValue().get(token);
            MagicUser magicUser = JSON.parseObject(str,MagicUser.class);
            return magicUser;
        } else {
            throw new MagicLoginException("token无效");
        }
    }

    @Override
    public MagicUser login(String username, String password) throws MagicLoginException {
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<SysUserEntity>()
            .eq("username",username).eq("build_in",1);
        SysUserEntity sysUser = sysUserService.getOne(queryWrapper);
        if (sysUser!=null) {
            String pin = sysUser.getPin();
            String salt = sysUser.getSalt();
            if (requireLogin() && pin.equals(ShiroUtils.sha256(password, salt))) {
                this.validToken = Tool.IdUtil.simpleUUID();
                // 登录成功后 构造MagicUser对象
                this.magicUser = new MagicUser(username, username, this.validToken);
                String str = JSON.toJSONString(this.magicUser);
                redisTemplate.opsForValue().set(this.validToken,str,100000);
                return this.magicUser;
            }
        }
        throw new MagicLoginException("用户名或密码不正确或非管理员账号");
    }
}
