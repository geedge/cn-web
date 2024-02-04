package net.geedge.common.interceptor;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.http.HttpStatus;

import Aladdin.HaspStatus;
import net.geedge.common.utils.R;
import net.geedge.common.utils.Tool;
import net.geedge.modules.sys.service.LicenseService;

public class LicenseShiroFilter extends AccessControlFilter {
	
    private String compileType;
    private LicenseService licenseService;
    
    public LicenseShiroFilter(LicenseService licenseService,String compileType) {
    	this.compileType = compileType;
    	this.licenseService = licenseService;
    }
    
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		// debug 模式不校验 license
		if (Tool.StrUtil.equalsIgnoreCase(compileType, "debug")) {
			return true;
		}
		Integer status = licenseService.verify();
		if (!Tool.NumberUtil.equals(status, HaspStatus.HASP_STATUS_OK)) {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.setStatus(HttpStatus.FORBIDDEN.value());
			httpResponse.getWriter()
					.write(Tool.JSONUtil.toJsonStr(R.error(999,"Invalid license,code: " + status)));
			return false;
		}
		return true;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		return false;
	}
}
