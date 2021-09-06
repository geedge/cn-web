package net.geedge.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import net.geedge.modules.sys.entity.LicenseEntity;
import net.geedge.modules.sys.service.LicenseService;
@Configuration
public class LicenseInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private LicenseService licenseService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
		//获取license
		/*LicenseEntity licenseEntity = licenseService.getAndSetLicenseCache();
		//验证并返回结果
		return licenseService.verify(licenseEntity);*/
	}

}