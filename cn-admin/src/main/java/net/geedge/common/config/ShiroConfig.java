package net.geedge.common.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.catalina.connector.Connector;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import net.geedge.common.interceptor.LicenseShiroFilter;
import net.geedge.common.interceptor.TokenCheckFilter;
import net.geedge.common.utils.Constant;
import net.geedge.modules.sys.dao.SysApiKeyDao;
import net.geedge.modules.sys.dao.SysLoginSessionDao;
import net.geedge.modules.sys.dao.SysUserDao;
import net.geedge.modules.sys.entity.SysConfigEntity;
import net.geedge.modules.sys.service.LicenseService;
import net.geedge.modules.sys.service.SysConfigService;
import net.geedge.modules.sys.shiro.MariadbSessionDao;
import net.geedge.modules.sys.shiro.MariadbSessionFactory;
import net.geedge.modules.sys.shiro.MariadbSessionManager;
import net.geedge.modules.sys.shiro.UserRealm;

/**
 * Shiro的配置文件
 *
 */
@Configuration
@DependsOn(value = {"mybatisPlusConfig"})
public class ShiroConfig {
	@Autowired
	private SysUserDao sysUserDao;

	@Autowired
	private SysConfigService sysconfigService;

	@Autowired
	private SysApiKeyDao sysApiKeyDao;

	@Autowired
	private LicenseService licenseService;
	
	@Value("${compileType:release}")
	private String compileType;
	
	@Autowired
	private SysLoginSessionDao loginSessionDao;

	@Bean("shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(securityManager);
		shiroFilter.setLoginUrl("/sys/login");
		shiroFilter.setUnauthorizedUrl("/");

		Map<String, String> filterMap = new LinkedHashMap<>();
		filterMap.put("/metrics", "anon");
		filterMap.put("/prometheus", "anon");
		filterMap.put("/prom/**", "anon");
		filterMap.put("/swagger/**", "anon");
		filterMap.put("/v2/api-docs", "anon");
		filterMap.put("/swagger-ui.html", "anon");
		filterMap.put("/webjars/**", "anon");
		filterMap.put("/swagger-resources/**", "anon");
		filterMap.put("/static/**", "anon");
		filterMap.put("/cn/**", "anon");
		filterMap.put("/", "anon");
		filterMap.put("/config.json", "anon");
		filterMap.put("/index.html", "anon");
		filterMap.put("/login.html", "anon");
		filterMap.put("/sys/login", "anon");
		filterMap.put("/healthy", "anon");
		filterMap.put("/actuator/**", "anon");
		filterMap.put("/gitLog", "anon");
		filterMap.put("/favicon.ico", "anon");
		filterMap.put("/captcha.jpg", "anon");
		filterMap.put("/api/**", "anon");
		filterMap.put("/terminal.ws", "anon");
		filterMap.put("/terminal/monitor.ws", "anon");
		filterMap.put("/sys/license/**", "anon");// license 相关接口
		filterMap.put("/setup/**", "anon");// license 相关接口
		filterMap.put("/sys/appearance", "anon");
		filterMap.put("/magic/web/**", "anon");
		/*filterMap.put("/v1/knowledgeBase/items/batch", "anon");*/
		filterMap.put("/**", "tokenCheckFilter,licenseShiroFilter");
		
		Integer loginExpiration = getTimeOutData();
		Map<String, Filter> cumstomFilterMap = new LinkedHashMap<String, Filter>();
		TokenCheckFilter tokenCheckFilter = new TokenCheckFilter(sysApiKeyDao,loginSessionDao);
		LicenseShiroFilter licenseShiroFilter = new LicenseShiroFilter(licenseService,compileType);
		cumstomFilterMap.put("tokenCheckFilter", tokenCheckFilter);
		cumstomFilterMap.put("licenseShiroFilter", licenseShiroFilter);
		shiroFilter.setFilters(cumstomFilterMap);
		shiroFilter.setFilterChainDefinitionMap(filterMap);

		return shiroFilter;
	}

	@Bean
	@ConditionalOnExpression("${cn.shiroPermisSupport:true}")
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}

	@Bean(name = { "tomcatServletWebServerFactory" })
	public ConfigurableServletWebServerFactory webServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
			@Override
			public void customize(Connector connector) {
				connector.setProperty("relaxedPathChars", "\"<>[\\]^`{|}");

				connector.setProperty("relaxedQueryChars", "\"<>[\\]^`{|}");
			}
		});

		return factory;
	}

	/**
	 *  shiro sessionDao层的实现 通过mariadb管理
	 */
	@Bean
	public SessionDAO sessionDAO(){
		MariadbSessionDao sessionDao = new MariadbSessionDao();
		return sessionDao;
	}


	/**
	 * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
	 *
	 * @return
	 */
	@Bean
	public UserRealm myShiroRealm() {
		UserRealm myShiroRealm = new UserRealm();
		// 关闭 cache
		myShiroRealm.setCachingEnabled(false);
		return myShiroRealm;
	}

	/**
	 * 自定义 sessionManager
	 *
	 * @return
	 */
	@Bean
	public SessionManager sessionManager() {
		Integer loginExpiration = getTimeOutData();
		MariadbSessionManager mySessionManager = new MariadbSessionManager();
		mySessionManager.setSessionDAO(sessionDAO());
		mySessionManager.setGlobalSessionTimeout(loginExpiration * 60 * 1000);
		// Disable Session ID cookie
		mySessionManager.setSessionIdCookieEnabled(false);
		mySessionManager.setSessionFactory(new MariadbSessionFactory());
		return mySessionManager;
	}

	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm.
		securityManager.setRealm(myShiroRealm());
		// 自定义session管理
		securityManager.setSessionManager(sessionManager());
		return securityManager;
	}

	private Integer getTimeOutData() {
		SysConfigEntity configEntity = sysconfigService.getOne(new QueryWrapper<SysConfigEntity>().lambda()
				.eq(SysConfigEntity::getCkey, Constant.SYSCONFIG_SESSION_TIMEOUT_KEY));
		Integer loginExpiration = 30;
		if (ObjectUtil.isNotEmpty(configEntity)) {
			loginExpiration = StrUtil.isNotBlank(configEntity.getCvalue())
					? Integer.valueOf(configEntity.getCvalue())
					: loginExpiration;
		}
		return loginExpiration;
	}
}
