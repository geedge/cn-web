package net.geedge.modules.sys.shiro;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import cn.hutool.core.util.StrUtil;
import net.geedge.common.utils.Constant;

public class MariadbSessionManager extends DefaultWebSessionManager{
	private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    public MariadbSessionManager() {
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader(Constant.AUTH_TOKEN_CODE);
        if (StrUtil.isEmpty(token)) {
            token = request.getParameter(Constant.AUTH_TOKEN_CODE);
        }
        // 如果请求头中有 token 则其值为 sessionId
        if (!StrUtil.isEmpty(token)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, token);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return token;
        } else {
            // 否则按默认规则从cookie取sessionId
            return super.getSessionId(request, response);
        }
    }

}
