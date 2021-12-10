package net.geedge.modules.galaxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import freemarker.template.TemplateException;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.FreeMarkerUtil;
import net.geedge.common.utils.RCode;
import net.geedge.common.utils.Tool;
import net.geedge.modules.galaxy.entity.GalaxyProxy;
import net.geedge.modules.galaxy.service.GalaxyProxyService;
import net.geedge.modules.sys.controller.AbstractController;
import net.geedge.modules.sys.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.*;

/**
 * Cryptocurrency接口
 * @Auther: lijinyang
 * @Date: 2021/12/08/14:29
 */
@RestController
public class CryptocurrencyController extends AbstractController {
    @Autowired
    private GalaxyProxyService galaxyProxyService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private RestTemplate restTemplate;

    private final static List<String> HEADER_LIST = Tool.ListUtil.toList("HOST","REFERER","ORIGIN");
    /**
     * cors 响应头
     */
    private final static Map<String,String> CORS_HEADER = new LinkedHashMap<String, String>();

    static {
        CORS_HEADER.put("Access-Control-Allow-Origin", "*");
        CORS_HEADER.put("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        CORS_HEADER.put("Access-Control-Allow-Headers", "*");
        CORS_HEADER.put("Access-Control-Max-Age", "86400");
    }

    @RequestMapping("/cryptcy/**")
    public void cryptcy(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException {
        /* 请求路径 */
        String servletPath = request.getServletPath();
        /* 请求方式 */
        String method = request.getMethod().toUpperCase();
        /* 请求头 */
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headers = Tool.MapUtil.newHashMap();
        for (; headerNames.hasMoreElements();) {
            String key = headerNames.nextElement();
            //解决跨域问题
            if (HEADER_LIST.contains(key.toUpperCase())) {
                continue;
            }
            String value = request.getHeader(key);
            headers.put(key,value);
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
        if (Tool.StrUtil.isBlank(galaxyUrl)) {
            Tool.ExceptionUtil.wrapAndThrow(new CNException(RCode.GALAXY_PROXY_CONFIG_URL_ISNULL));
        }
        if (!galaxyUrl.endsWith("/")) {
            galaxyUrl = Tool.StrUtil.concat(true, galaxyUrl, "/");
        }
        QueryWrapper<GalaxyProxy> queryWrapper = new QueryWrapper<GalaxyProxy>()
                .eq("path", servletPath).eq("method", method).eq("state", 1);
        /* 查询匹配的接口 */
        GalaxyProxy proxy = galaxyProxyService.getOne(queryWrapper);
        if (proxy == null) {
            this.responseError(response, HttpServletResponse.SC_NOT_FOUND, "Not found", null);
            logger.warn("path not found,url:{},method:{}", servletPath, method);
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
        Bindings bindings = jsEngine.createBindings();
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
                if (code != 200) {
                    this.responseError(response, code, bindings.get("msg").toString(), null);
                }
            }
            String targetUrl = proxy.getTargetUrl();
            if (targetUrl.startsWith("/")) {
                targetUrl = targetUrl.substring(1);
            }
            targetUrl = Tool.StrUtil.concat(true, galaxyUrl,targetUrl);
            logger.debug("targetUrl: {}",targetUrl);
            /* 补充默认请求参数 */
            if (Tool.StrUtil.isNotBlank(targetParam)) {
                String templateIntoString = FreeMarkerUtil.processTemplateIntoString(String.valueOf(proxy.getId())+"-parameter",proxy.getTargetParam(), bindings);
                parameter = Tool.JSONUtil.toBean(templateIntoString, HashMap.class);
            }else {
                parameter = null;
            }
            /* 代理请求头参数 */
            MultiValueMap httpHeaders = new LinkedMultiValueMap();//设置请求头
            if (Tool.StrUtil.isNotBlank(targetHeader)) {
                String templateIntoString = FreeMarkerUtil.processTemplateIntoString(String.valueOf(proxy.getId())+"-headers", proxy.getTargetHeader(), bindings);
                headers = Tool.JSONUtil.toBean(templateIntoString, HashMap.class);
                headers.forEach((k,v) ->httpHeaders.put(k,v));
            }
            if(logger.isDebugEnabled()) {
                logger.debug("headers : {} ", Tool.JSONUtil.toJsonStr(headers));
                logger.debug("parameter : {} ", Tool.JSONUtil.toJsonStr(parameter));
            }
            //构建请求参数
            RequestEntity requestEntity = this.buildRequestEntity(targetUrl, method, httpHeaders, parameter);
            //发送请求，获取响应结果
            ResponseEntity<LinkedHashMap> responseEntity = restTemplate.exchange(requestEntity, LinkedHashMap.class);
            HttpStatus statusCode = responseEntity.getStatusCode();
            HttpHeaders responseHeader = responseEntity.getHeaders();
            logger.info("targetUrl : {},statusCode : {}", targetUrl,statusCode.toString());
            LinkedHashMap body = responseEntity.getBody();
            //返回实际请求url,便于开发测试
            body.put("originalUrl", requestEntity.getUrl().toString());
            if(logger.isDebugEnabled()) {
                logger.debug("target body: {}", Tool.JSONUtil.toJsonStr(body));
            }
            int code = statusCode.value();
            //代理返回错误，直接返回
            if (code > 399) {
                this.responseError(response, code, statusCode.name(), Tool.JSONUtil.toJsonStr(body));
                return;
            }
            //请求galaxy接口
            /* 处理接口返回的内容 */
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
            if (Tool.CollUtil.isNotEmpty(responseHeader)) {
                responseHeader.forEach((k,v) -> {
                    if (Tool.StrUtil.equals(k, "Content-Length")) {
                        response.setHeader(k, Tool.StrUtil.join(";", (Tool.JSONUtil.toJsonStr(bindings.get("result"))).getBytes().length));
                    } else {
                        response.setHeader(k, Tool.StrUtil.join(";", v.toArray()));
                    }
                });
            }
            //设置 cors 响应头
            this.setCorsHeaders(response);
            Tool.IoUtil.writeUtf8(response.getOutputStream(), false, Tool.JSONUtil.toJsonStr(bindings.get("result")));
            response.flushBuffer();
        } catch (ScriptException e) {
            logger.error(e, "script error,preHandle : {},postHandle : {}",preHandle,postHandle);
        }

    }
    private void setCorsHeaders(HttpServletResponse response) {
        Set<Map.Entry<String, String>> entrySet = CORS_HEADER.entrySet();
        entrySet.forEach((en)->{
            response.setHeader(en.getKey(), en.getValue());
        });
    }

    /**
     * 响应错误信息
     * @param response
     * @param code
     * @param msg
     * @param body
     * @throws IOException
     */
    private void responseError(HttpServletResponse response, int code, String msg, String body) throws IOException {
        response.reset();
        response.setStatus(code, msg);
        //设置 cors 响应头
        this.setCorsHeaders(response);
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        if (body != null) {
            Tool.IoUtil.writeUtf8(response.getOutputStream(), false, body);
        }
        response.flushBuffer();
    }

    private RequestEntity buildRequestEntity(String url,String method,MultiValueMap httpHeaders,Map<String,String> params) {
        boolean isGet = Tool.StrUtil.equalsIgnoreCase(method, "GET");
        RequestEntity requestEntity = null;
        if (isGet) {
            if (Tool.MapUtil.isNotEmpty(params)) {
                if (url.endsWith("/")) {
                    url = url.substring(0, url.length() -1);
                }
                StringBuilder sb = new StringBuilder(url);
                if (!url.endsWith("?")) {
                    sb.append("?");
                }
                params.forEach((k,v) -> {
                    try {
                        String encode = URLEncoder.encode(v, "UTF-8").replaceAll("\\+", "%20");
                        sb.append(k).append("=").append(encode).append("&");
                    } catch (UnsupportedEncodingException e) {
                        logger.error(e);
                    }
                });
                sb.deleteCharAt(sb.length()-1);
                url = sb.toString();
            }
        }
        requestEntity = new RequestEntity((isGet?null:params),httpHeaders, HttpMethod.valueOf(method), URI.create(url));
        return requestEntity;
    }
}
