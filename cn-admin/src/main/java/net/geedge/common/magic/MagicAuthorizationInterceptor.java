package net.geedge.common.magic;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.stereotype.Component;
import org.ssssssss.magicapi.core.context.MagicUser;
import org.ssssssss.magicapi.core.exception.MagicLoginException;
import org.ssssssss.magicapi.core.interceptor.AuthorizationInterceptor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import net.geedge.common.utils.Tool;
import net.geedge.modules.sys.entity.SysUserEntity;
import net.geedge.modules.sys.service.SysUserService;
import net.geedge.modules.sys.shiro.MariadbSession;
import net.geedge.modules.sys.shiro.ShiroUtils;



/**
 * @Auther: lijinyang
 * @Date: 2022/12/27/16:11
 */
@Component
public class MagicAuthorizationInterceptor implements AuthorizationInterceptor {

    private final SysUserService sysUserService;

    private String validToken;

    private MagicUser magicUser;


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
        if (requireLogin() && token != null) {
            SysUserEntity user = ShiroUtils.getUserEntityBySessionId(token);
            MagicUser magicUser = new MagicUser();
            try {
				BeanUtils.copyProperties(user, magicUser);
			} catch (Exception e) {
				e.printStackTrace();
				throw new MagicLoginException("something error");
			}
            return magicUser;
        } else {
            throw new MagicLoginException("Token invalid please check certification information");
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
                MariadbSession session = new MariadbSession();
                SimplePrincipalCollection primaryPrincipal = new SimplePrincipalCollection(sysUser, "");
    			session.setAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY, primaryPrincipal);
    			session.setId(validToken);
    			ShiroUtils.save(session);
                return this.magicUser;
            }
        }
        throw new MagicLoginException("Incorrect username or password or non-administrator account");
    }
}
