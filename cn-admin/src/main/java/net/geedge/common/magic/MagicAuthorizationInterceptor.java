package net.geedge.common.magic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.geedge.common.utils.Tool;
import net.geedge.modules.sys.entity.SysUserEntity;
import net.geedge.modules.sys.service.SysUserService;
import net.geedge.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Component;
import org.ssssssss.magicapi.core.context.MagicUser;
import org.ssssssss.magicapi.core.exception.MagicLoginException;
import org.ssssssss.magicapi.core.interceptor.AuthorizationInterceptor;

import java.util.Objects;


/**
 * @Auther: lijinyang
 * @Date: 2022/12/27/16:11
 */
@Component
public class MagicAuthorizationInterceptor implements AuthorizationInterceptor {

    private final SysUserService sysUserService;

    private String validToken;

    private MagicUser MagicUser;

    public MagicAuthorizationInterceptor(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
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
        if (requireLogin() && Objects.equals(this.validToken, token)) {
            return this.MagicUser;
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
                this.MagicUser = new MagicUser(username, username, this.validToken);
                return this.MagicUser;
            }
        }
        throw new MagicLoginException("用户名或密码不正确或非管理员账号");
    }
}
