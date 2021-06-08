package net.geedge.common.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import cn.hutool.log.Log;
import net.geedge.common.utils.Tool;

@Configuration
public class RestTemplateConfig {
	private static final Log log = Log.get();
	@Value("${restTemplate.readTimeout:30000}")
	private int readTimeout;
	@Value("${restTemplate.connectTimeout:30000}")
	private int connectTimeout;
	
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory){
    	RestTemplate restTemplate = new RestTemplate(factory);
    	restTemplate.setInterceptors(Tool.CollUtil.toList(new ClientHttpRequestInterceptor() {
    		@Override
    	    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    	        traceRequest(request, body);
    	        ClientHttpResponse response = execution.execute(request, body);
    	        traceResponse(response);
    	        return response;
    	    }
    	 
    	    private void traceRequest(HttpRequest request, byte[] body) throws IOException {
    	        log.debug("===========================request begin================================================");
    	        log.debug("URI         : {}", request.getURI());
    	        log.debug("Method      : {}", request.getMethod());
    	        log.debug("Headers     : {}", request.getHeaders());
    	        log.debug("Request body: {}", new String(body, "UTF-8"));
    	        log.debug("==========================request end================================================");
    	    }
    	 
    	    private void traceResponse(ClientHttpResponse response) throws IOException {
    	        log.debug("============================response begin==========================================");
    	        log.debug("Status code  : {}", response.getStatusCode());
    	        log.debug("Status text  : {}", response.getStatusText());
    	        log.debug("Headers      : {}", response.getHeaders());
    	        log.debug("=======================response end=================================================");
    	    }
		}));
        return restTemplate;
    }
 
    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(this.connectTimeout);
        factory.setReadTimeout(this.readTimeout);
        return factory;
    }
 
}