package net.geedge.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import net.geedge.modules.sys.entity.SysConfigEntity;
import net.geedge.modules.sys.entity.SysUserEntity;
import net.geedge.modules.sys.service.SysConfigService;
import net.geedge.modules.sys.shiro.ShiroUtils;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SysMenuInterceptor extends HandlerInterceptorAdapter {

    private SysConfigService sysConfigService;


    public SysMenuInterceptor(SysConfigService sysConfigService) {
        this.sysConfigService = sysConfigService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // GET 请求不拦截,不进行校验
        if(StrUtil.equals(request.getMethod(),HttpMethod.GET.toString())) {
            return true;
        } else {
            SysUserEntity user = ShiroUtils.getUserEntity();
            SysConfigEntity developDdmin = sysConfigService.getOne(new LambdaQueryWrapper<SysConfigEntity>().eq(SysConfigEntity::getParamKey, "develop_admin"));
            if(!StrUtil.equals(user.getUsername(),developDdmin.getParamValue())) {
                response.sendError(HttpStatus.SC_NOT_FOUND, "Not Found");
                return false;
            }
            return true;
        }
    }
}