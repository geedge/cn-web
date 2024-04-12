package net.geedge.common.interceptor;

import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.filter.AccessControlFilter;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import net.geedge.common.utils.Constant;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.common.utils.Tool;
import net.geedge.modules.sys.dao.SysApiKeyDao;
import net.geedge.modules.sys.entity.SysApiKey;
import net.geedge.modules.sys.entity.SysUserEntity;
import net.geedge.modules.sys.shiro.MariadbSession;
import net.geedge.modules.sys.shiro.ShiroUtils;

public class TokenCheckFilter extends AccessControlFilter {

	private SysApiKeyDao sysApiKeyDao;

	public TokenCheckFilter(SysApiKeyDao sysApiKeyDao) {
		this.sysApiKeyDao = sysApiKeyDao;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object o) throws Exception {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String requestToken = request.getHeader(Constant.AUTH_TOKEN_CODE);
		if (StringUtils.isBlank(requestToken)) {
			requestToken = request.getParameter(Constant.AUTH_TOKEN_CODE);
		}
		if (StringUtils.isBlank(requestToken)) {
			response.getWriter().write(JSON.toJSON(R.error(RCode.SYS_LOGIN_REQUIRED)).toString());
			return false;
		}
		
		Session session = ShiroUtils.getSessionById(requestToken);
		if (Tool.ObjectUtil.isNotNull(session)) {
			SysUserEntity userEntity = ShiroUtils.getUserEntity();
			if (Tool.ObjectUtil.isNull(userEntity)) {
				return false;
			}
			return true;
		} else {
			boolean login = this.apiKeyLogin(requestToken);
			if(!login) {
				response.getWriter().write(JSON.toJSON(R.error(RCode.SYS_LOGIN_REQUIRED)).toString());
			}
			return login;
		}
	}

	/**
	 * 验证 apikey 登录
	 * @param token
	 * @return
	 */
	private boolean apiKeyLogin(String token) {
		Date now = new Date();
		String time = DateUtil.format(now, "yyyy-MM-dd hh:mm:ss");
		// 查询sys_api_key是否有该token记录
		SysApiKey apiKey = sysApiKeyDao.selectOne(new QueryWrapper<SysApiKey>().lambda().eq(SysApiKey::getToken, token).and(
						wrapper -> wrapper.ge(SysApiKey::getEtime, time).or().isNull(SysApiKey::getEtime)));
		// 如果有的话则认证通过 同时将该值存放到session里
		if (ObjectUtil.isEmpty(apiKey)) {
			// 如果没有则 返回响应值
			return false;
		} else {
			// 获取存在缓存中的过期时间
			Date expireAt = apiKey.getEtime();
			MariadbSession session = new MariadbSession();
			SysUserEntity user = new SysUserEntity();
			user.setApiKeyId(apiKey.getId());
			user.setId(0L);
			user.setName(apiKey.getName());
			SimplePrincipalCollection primaryPrincipal = new SimplePrincipalCollection(user, "");
			session.setAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY, primaryPrincipal);
			session.setId(apiKey.getToken());
			// 设置超时时间
			if (!ObjectUtil.isEmpty(expireAt)) {
				long ms = DateUtil.between(now, expireAt, DateUnit.MS);
				session.setTimeout(ms);
			} else {
				session.setTimeout(Integer.MAX_VALUE);
			}
			// 手动保存 session 信息
			ShiroUtils.save(session);
			DefaultSubjectContext context = new DefaultSubjectContext();
			context.setSession(session);
			SecurityManager securityManager = SecurityUtils.getSecurityManager();
			Subject subject = securityManager.createSubject(context);
			ThreadContext.bind(subject);
			return true;
		}
		
	}

	@Override
	protected boolean onAccessDenied(ServletRequest req, ServletResponse resp) throws Exception {

		return false;
	}

}
