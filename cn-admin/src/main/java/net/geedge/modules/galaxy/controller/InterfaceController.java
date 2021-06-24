package net.geedge.modules.galaxy.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import freemarker.template.TemplateException;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.RCode;
import net.geedge.common.utils.Tool;
import net.geedge.modules.galaxy.entity.GalaxyProxy;
import net.geedge.modules.galaxy.service.GalaxyProxyService;
import net.geedge.modules.sys.controller.AbstractController;
import net.geedge.modules.sys.service.SysConfigService;
/**
 * galaxy 代理接口
 * @author ThinkPad
 *
 */
@RestController
public class InterfaceController extends AbstractController {
	@Autowired
	private GalaxyProxyService galaxyProxyService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private RestTemplate restTemplate;
	
	private final static List<String> HEADER_LIST = Tool.ListUtil.toList("HOST","REFERER","ORIGIN");
	
	@RequestMapping("/interface/**")
	@SuppressWarnings("all")
	public void index(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException {
		/* 请求路径 */
		String servletPath = request.getServletPath().substring("/interface".length());
		/* 请求方式 */
		String method = request.getMethod().toUpperCase();
		/* 请求头 */
		Enumeration<String> headerNames = request.getHeaderNames();
		Map<String, String> headers = Tool.MapUtil.newHashMap();
		for (; headerNames.hasMoreElements();) {
			
			String key = headerNames.nextElement();
			//解决跨域问题
			if(HEADER_LIST.contains(key.toUpperCase())) {
				continue;
			}
			String value = request.getHeader(key);
			headers.put(key, value);
		}
		logger.info("request path : {}", servletPath);
		/* 请求参数 */
		Map<String,String[]> param = request.getParameterMap();
		
		Map parameter = Tool.MapUtil.newHashMap();
		for(String key :param.keySet()) {
			parameter.put(key, (param.get(key))[0]);
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug("request path : {},method : {},parameter : {}", servletPath,method,Tool.JSONUtil.toJsonStr(parameter));
		}
		
		String galaxyUrl = sysConfigService.getValue("galaxy_url");
		if(Tool.StrUtil.isBlank(galaxyUrl)) {
			Tool.ExceptionUtil.wrapAndThrow(new CNException(RCode.GALAXY_PROXY_CONFIG_URL_ISNULL));
		}
		if(!galaxyUrl.endsWith("/")) {
			galaxyUrl = Tool.StrUtil.concat(true, galaxyUrl,"/");
		}
		QueryWrapper<GalaxyProxy> queryWrapper = new QueryWrapper<GalaxyProxy>()
				.eq("path", servletPath).eq("method", method).eq("state", 1);
		/* 查询匹配的 接口 */
		GalaxyProxy proxy = galaxyProxyService.getOne(queryWrapper);
		if (proxy == null) {
			this.responseError(response, HttpServletResponse.SC_NOT_FOUND, "Not found", null);
			logger.warn("path not found,url:{},method:{}", servletPath,method);
			return;
		}
		if(logger.isDebugEnabled()) {
			logger.debug("galaxy proxy : {} ",proxy);
		}
		/* 执行前执行 */
		String preHandle = proxy.getPreHandle();
		String postHandle = proxy.getPostHandle();
		String targetParam = proxy.getTargetParam();
		String targetHeader = proxy.getTargetHeader();
		
		//获取单例 js 引擎
		ScriptEngine jsEngine = Tool.ScriptUtil.getJsEngine();
		//设置 js bindings
		Bindings bindings = jsEngine.getBindings(ScriptContext.ENGINE_SCOPE);
		bindings.put("nowTimestamp", System.currentTimeMillis());//当前时间戳
		bindings.put("code", 200);
		bindings.put("msg", "");
		bindings.put("method", method);
		bindings.put("path", servletPath);
		bindings.put("headers", headers);
		bindings.put("parameter", parameter);
		bindings.put("proxy", proxy);
		try {
			if (Tool.StrUtil.isNotBlank(preHandle)) {
				jsEngine.eval(preHandle, bindings);
				int code = Tool.NumberUtil.parseInt(bindings.get("code").toString());
				//返回不等于 200 ，说明校验失败直接返回
				if(code != 200) {
					this.responseError(response, code, bindings.get("msg").toString(), null);
				}
			}
			
			String targetUrl = proxy.getTargetUrl();
			if(targetUrl.startsWith("/")) {
				targetUrl = targetUrl.substring(1);
			}
			targetUrl = Tool.StrUtil.concat(true, galaxyUrl,targetUrl);
			logger.debug("targetUrl: {}",targetUrl);
			/* 补充默认请求参数 */
			if(Tool.StrUtil.isNotBlank(targetParam)) {
				String templateIntoString = Tool.FreeMarkerUtil.processTemplateIntoString(String.valueOf(proxy.getId())+"-parameter", proxy.getTargetParam(), bindings);
				parameter = Tool.JSONUtil.toBean(templateIntoString, HashMap.class);
			}else {
				parameter = null;
			}
			/* 代理 请求头参数 */
			MultiValueMap httpHeaders = new LinkedMultiValueMap();//设置请求头
			if(Tool.StrUtil.isNotBlank(targetHeader)) {
				String templateIntoString = Tool.FreeMarkerUtil.processTemplateIntoString(String.valueOf(proxy.getId())+"-headers", proxy.getTargetHeader(), bindings);
				headers = Tool.JSONUtil.toBean(templateIntoString, HashMap.class);
				headers.forEach((k,v) -> httpHeaders.put(k,v));
			}
			if(logger.isDebugEnabled()) {
				logger.debug("headers : {} ", Tool.JSONUtil.toJsonStr(headers));
				logger.debug("parameter : {} ", Tool.JSONUtil.toJsonStr(parameter));
			}
		    //代理 http 请求
			ResponseEntity<LinkedHashMap> responseEntity = this.exchange(targetUrl, method, httpHeaders, parameter, LinkedHashMap.class);
			HttpStatus statusCode = responseEntity.getStatusCode();
			HttpHeaders responseHeader = responseEntity.getHeaders();
			logger.info("targetUrl : {},statusCode : {}", targetUrl,statusCode.toString());
			LinkedHashMap body = responseEntity.getBody();
			if(logger.isDebugEnabled()) {
				logger.debug("target body: {}", Tool.JSONUtil.toJsonStr(body));
			}
			int code = statusCode.value();
			// 代理返回 错误，直接返回
			if(code > 399) {
				this.responseError(response, code, statusCode.name(), Tool.JSONUtil.toJsonStr(body));
				return;
			}
			//请求galaxy接口
			/*处理接口返回的内容*/
			bindings.put("status", code);
			bindings.put("result", body);
			bindings.put("responseHeader", responseHeader);
			if (Tool.StrUtil.isNotBlank(postHandle)) {
				jsEngine.eval(postHandle, bindings);
			}
			response.reset();
			/*组织响应前端的内容*/
			response.setStatus(code);
			responseHeader = (HttpHeaders)bindings.get("responseHeader");
			if(Tool.CollUtil.isNotEmpty(responseHeader)) {
				responseHeader.forEach((k,v) -> response.setHeader(k, Tool.StrUtil.join(";", v.toArray())));
			}
			response.setHeader("Access-Control-Allow-Origin", "*");

			Tool.IoUtil.writeUtf8(response.getOutputStream(), false, Tool.JSONUtil.toJsonStr(bindings.get("result")));
			response.flushBuffer();
		} catch (ScriptException e) {
			logger.error(e, "script error,preHandle : {},postHandle : {}",preHandle,postHandle);
		}

	}

	/**
	 * 响应错误信息
	 * @param response
	 * @param code
	 * @param msg
	 * @param body
	 * @throws IOException
	 */
	private void responseError(HttpServletResponse response,int code,String msg,String body) throws IOException {
		response.reset();
		response.sendError(code, msg);
		if(body != null) {
			Tool.IoUtil.writeUtf8(response.getOutputStream(), false, body);
		}
	}
	
	@SuppressWarnings("all")
	private <T> ResponseEntity<T> exchange(String url,String method,MultiValueMap httpHeaders,Map<String,String> params,Class<T> responseType) {
		
		HttpEntity httpEntity = null;
		ResponseEntity<T> responseEntity = null;
		if(Tool.StrUtil.equalsIgnoreCase(method, "GET")) {
			httpEntity = new HttpEntity(httpHeaders);
			if(Tool.MapUtil.isNotEmpty(params)) {
				if(url.endsWith("/")) {
					url = url.substring(0, url.length() -1);
				}
				StringBuilder sb = new StringBuilder(url);
				if(!url.endsWith("?")) {
					sb.append("?");
				}
				params.forEach((k,v) -> {
					sb.append(k).append("=").append(v).append("&");
				});
				sb.deleteCharAt(sb.length()-1);
				url = sb.toString();
			}
		}else {
			httpEntity = new HttpEntity(params,httpHeaders);
		}
		responseEntity = restTemplate.exchange(url, HttpMethod.resolve(method), httpEntity, responseType);
		return responseEntity;
	}
	
}
