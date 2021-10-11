package net.geedge.common.utils;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import freemarker.template.TemplateException;
import net.geedge.common.exception.CNException;
import net.geedge.modules.galaxy.entity.GalaxyProxy;
import net.geedge.modules.galaxy.service.GalaxyProxyService;
import net.geedge.modules.sys.service.SysConfigService;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Lihe
 * @version 1
 * @description
 * @date 2021/10/9
 */
@Component
public class GalaxyUtils {

	private static Log logger = Log.get();

	private static SysConfigService sysConfigService;

	private static GalaxyProxyService galaxyProxyService;

	private static RestTemplate restTemplate;

	/**
	 * cors 响应头
	 */
	private final static Map<String, String> CORS_HEADER = new LinkedHashMap<String, String>();

	static {
		CORS_HEADER.put("Access-Control-Allow-Origin", "*");
		CORS_HEADER.put("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
		CORS_HEADER.put("Access-Control-Allow-Headers", "*");
		CORS_HEADER.put("Access-Control-Max-Age", "86400");
	}

	/**
	 * 构造GalaxyUtils类，注入静态方法
	 * @param sysConfigService
	 * @param galaxyProxyService
	 * @param restTemplate
	 */
	private GalaxyUtils(SysConfigService sysConfigService, GalaxyProxyService galaxyProxyService, RestTemplate restTemplate) {
		GalaxyUtils.sysConfigService = sysConfigService;
		GalaxyUtils.galaxyProxyService = galaxyProxyService;
		GalaxyUtils.restTemplate = restTemplate;
	}

	/**
	 * 发送galaxy请求
	 * @param path galaxy请求路径，ex:/entity/ip/detail/domains
	 * @param method : 请求方式 GET,POST
	 * @param parameter 参数集合
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static Object sendGalaxyRequest(String path, String method,Map<String,String> parameter) throws IOException, TemplateException {
		/* 请求头 */
		Object result = null;
		logger.info("request Galaxy path : {}", path);
		String galaxyUrl = sysConfigService.getValue("galaxy_url");
		if (Tool.StrUtil.isBlank(galaxyUrl)) {
			Tool.ExceptionUtil.wrapAndThrow(new CNException(RCode.GALAXY_PROXY_CONFIG_URL_ISNULL));
		}
		if (!galaxyUrl.endsWith("/")) {
			galaxyUrl = Tool.StrUtil.concat(true, galaxyUrl, "/");
		}
		QueryWrapper<GalaxyProxy> queryWrapper = new QueryWrapper<GalaxyProxy>().eq("path", path).eq("method", method).eq("state", 1);
		/* 查询匹配的 接口 */
		GalaxyProxy proxy = galaxyProxyService.getOne(queryWrapper);
		if (proxy == null) {
			logger.warn("path not found,url:{},method:{}", path, method);
			return result;
		}
		Map<String,Object> param = Tool.MapUtil.newHashMap();
		param.put("parameter",parameter);
		String targetParam = proxy.getTargetParam();
		String targetUrl = proxy.getTargetUrl();
		if (targetUrl.startsWith("/")) {
			targetUrl = targetUrl.substring(1);
		}
		targetUrl = Tool.StrUtil.concat(true, galaxyUrl, targetUrl);
		/* 补充默认请求参数 */
		if (Tool.StrUtil.isNotBlank(targetParam)) {
			String templateIntoString = Tool.FreeMarkerUtil.processTemplateIntoString(String.valueOf(proxy.getId()) + "-parameter", proxy.getTargetParam(), param);
			parameter = Tool.JSONUtil.toBean(templateIntoString, HashMap.class);
		} else {
			parameter = null;
		}
		/* 代理 请求头参数 */
		MultiValueMap httpHeaders = new LinkedMultiValueMap();//设置请求头

		//构造请求参数
		RequestEntity requestEntity = buildRequestEntity(targetUrl, method.toUpperCase(), httpHeaders, parameter);
		//发送请求，获取响应结果
		ResponseEntity<LinkedHashMap> responseEntity = restTemplate.exchange(requestEntity, LinkedHashMap.class);
		HttpStatus statusCode = responseEntity.getStatusCode();
		logger.info("targetUrl : {},statusCode : {}", targetUrl, statusCode.toString());

		result =  responseEntity.getBody();

		int code = statusCode.value();
		// 代理返回 错误，直接返回
		if (code > 399) {
			return null;
		}
		return result;
	}


	private static RequestEntity buildRequestEntity(String url, String method, MultiValueMap httpHeaders, Map<String, String> params) {
		boolean isGet = Tool.StrUtil.equalsIgnoreCase(method, "GET");
		RequestEntity requestEntity = null;
		if (isGet) {
			if (Tool.MapUtil.isNotEmpty(params)) {
				if (url.endsWith("/")) {
					url = url.substring(0, url.length() - 1);
				}
				StringBuilder sb = new StringBuilder(url);
				if (!url.endsWith("?")) {
					sb.append("?");
				}
				params.forEach((k, v) -> {
					try {
						String encode = URLEncoder.encode(v, "UTF-8").replaceAll("\\+", "%20");
						sb.append(k).append("=").append(encode).append("&");
					} catch (UnsupportedEncodingException e) {
						logger.error(e);
					}
				});
				sb.deleteCharAt(sb.length() - 1);
				url = sb.toString();
			}
		}
		requestEntity = new RequestEntity((isGet ? null : params), httpHeaders, HttpMethod.valueOf(method), URI.create(url));
		return requestEntity;
	}

}
