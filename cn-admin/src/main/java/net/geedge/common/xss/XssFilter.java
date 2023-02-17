/**

 *

 *
 * 
 */

package net.geedge.common.xss;

import cn.hutool.log.Log;
import net.geedge.common.utils.Tool;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * XSS过滤
 *
 * @author Mark sunlightcs@gmail.com
 */
public class XssFilter implements Filter {

	private final static Log logger = Log.get();

	//不拦截的地址
	private List<String> excludedList = new ArrayList<String>();

	@Override
	public void init(FilterConfig config) throws ServletException {
		excludedList.add("/magic/web");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain){
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
				(HttpServletRequest) request);
		try {
			String url = xssRequest.getServletPath();
			if (isExcluded(url)) {
				chain.doFilter(request, response);
			} else {
				chain.doFilter(xssRequest, response);
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	@Override
	public void destroy() {
	}

	/**
	 * 是否拦截
	 */
	private boolean isExcluded(String url){
		if (Tool.StrUtil.isBlank(url)){
			return false;
		}
		for (String excluded : excludedList){
			if (url.contains(excluded)){
				return true;
			}
		}
		return false;
	}
}