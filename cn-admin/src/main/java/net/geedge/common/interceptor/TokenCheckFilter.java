package net.geedge.common.interceptor;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import net.geedge.common.utils.Constant;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.common.utils.SpringContextUtils;
import net.geedge.common.utils.Tool;
import net.geedge.modules.sys.dao.SysApiKeyDao;
import net.geedge.modules.sys.dao.SysUserDao;
import net.geedge.modules.sys.entity.SysApiKey;
import net.geedge.modules.sys.entity.SysUserEntity;
import net.geedge.modules.sys.shiro.ShiroUtils;

public class TokenCheckFilter extends AccessControlFilter {

    private Integer loginExpiration;
    private SysUserDao sysUserDao;
    private RedisTemplate<String, String> redisTemplate;
    private SysApiKeyDao sysApiKeyDao;
    
    public TokenCheckFilter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public TokenCheckFilter(RedisTemplate redisTemplate, Integer loginExpiration) {
        this.redisTemplate = redisTemplate;
        this.loginExpiration = loginExpiration;
    }

    public TokenCheckFilter(RedisTemplate redisTemplate, Integer loginExpiration, SysUserDao sysUserDao, SysApiKeyDao sysApiKeyDao) {
        this.redisTemplate = redisTemplate;
        this.loginExpiration = loginExpiration;
        this.sysUserDao = sysUserDao;
        this.sysApiKeyDao = sysApiKeyDao;
    }


//    @Override
//    protected boolean preHandle(ServletRequest req, ServletResponse resp) throws Exception {
//        HttpServletRequest request=(HttpServletRequest) req;
//        HttpServletResponse response=(HttpServletResponse)resp;
//        //跨域请求标志
//        String origin = request.getHeader("Origin");
//        if(StringUtils.isNotBlank(origin)){
//            response.setHeader("Access-Control-Allow-Origin",origin);
//            response.setHeader("Access-Control-Allow-Credentials","true");
//        }
//
//        return super.preHandle(request, response);
//    }

    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object o) throws Exception {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String requestToken = request.getHeader(Constant.AUTH_TOKEN_CODE);
        if (StringUtils.isBlank(requestToken)) {
            requestToken = request.getParameter(Constant.AUTH_TOKEN_CODE);
        }
        if (StringUtils.isBlank(requestToken)) {
        	response.setStatus(500);
            response.getWriter().write(JSON.toJSON(R.error(RCode.SYS_LOGIN_REQUIRED)).toString());
            return false;
        }

        Boolean exist = redisTemplate.hasKey(ShiroUtils.REDIS_KEYPREFIX + requestToken);
        if (exist) {
        	SysUserEntity userEntity = ShiroUtils.getUserEntity();
        	if(Tool.ObjectUtil.equals(ShiroUtils.getUserEntity().getId(), 0L)) {
        		// 第三方api key登录
        		return true;
        	}
            // 重新查询数据库
            SysUserEntity entity = this.sysUserDao.selectById(ShiroUtils.getUserEntity().getId());
            if (entity.getStatus() == 0) {
            	response.setStatus(500);
                response.getWriter().write(JSON.toJSON(R.error(RCode.SYS_LOGIN_LOCK)).toString());
                return false;
            }
            // key 存在 证明验证成功，刷新过期时间
            redisTemplate.expire(requestToken, this.loginExpiration, TimeUnit.MINUTES);
            return true;
        } else {
        	Date now = new Date();
        	String time = DateUtil.format(now , "yyyy-MM-dd hh:mm:ss");
        	// 查询sys_api_key是否有该token记录
        	SysApiKey apiKey = sysApiKeyDao.selectOne(new QueryWrapper<SysApiKey>().lambda().eq(SysApiKey::getToken, requestToken)
        			.and(wrapper -> wrapper.ge(SysApiKey::getEtime,time).or().isNull(SysApiKey::getEtime)));
        	// 如果有的话则认证通过  同时将该值存放到redis里
        	if(Tool.ObjectUtil.isEmpty(apiKey)) {
        		// 如果没有则 返回响应值
        		response.setStatus(500);
                response.getWriter().write(JSON.toJSON(R.error(RCode.SYS_LOGIN_REQUIRED)).toString());
                return false;
        	}else {
        		RedisConnectionFactory factory = SpringContextUtils.getBean("redisConnectionFactory", RedisConnectionFactory.class);
        		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        		redisTemplate.setKeySerializer(new StringRedisSerializer());
        		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        		redisTemplate.setConnectionFactory(factory);
        		redisTemplate.afterPropertiesSet();
        		
        		
        		//获取存在缓存中的过期时间
        		Date expireAt = apiKey.getEtime();
        		
        		SimpleSession session = new SimpleSession();
    			SysUserEntity user = new SysUserEntity();
    			user.setApiKeyId(apiKey.getId());
    			user.setId(0L);
    			user.setName(apiKey.getName());
    			SimplePrincipalCollection primaryPrincipal =new SimplePrincipalCollection(user,"");
    			session.setAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY, primaryPrincipal);
    			session.setId(apiKey.getToken());

    			if(!Tool.ObjectUtil.isEmpty(expireAt)){
        			long seconds = DateUtil.between(now, expireAt, DateUnit.SECOND);
            		redisTemplate.opsForValue().set(ShiroUtils.REDIS_KEYPREFIX + requestToken, session,seconds,TimeUnit.SECONDS);
        		}else{
        			redisTemplate.opsForValue().set(ShiroUtils.REDIS_KEYPREFIX + requestToken, session);
        		}
    			
    			DefaultSubjectContext context =new DefaultSubjectContext();
    			context.setSession(session);
    			SecurityManager securityManager = SecurityUtils.getSecurityManager();
    			Subject subject = securityManager.createSubject(context);
    			ThreadContext.bind(subject);
    			return true;
        	}
        }
    }

    public Integer getLoginExpiration() {
		return loginExpiration;
	}

	public void setLoginExpiration(Integer loginExpiration) {
		this.loginExpiration = loginExpiration;
	}

	@Override
    protected boolean onAccessDenied(ServletRequest req, ServletResponse resp) throws Exception {

        return false;
    }
}
