package net.geedge.common.config;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import cn.hutool.log.Log;

@Configuration
public class RestTemplateConfig {
	private static final Log log = Log.get();
	@Value("${restTemplate.readTimeout:30000}")
	private int readTimeout;
	@Value("${restTemplate.connectTimeout:300000}")
	private int connectTimeout;
	
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder,ClientHttpRequestFactory factory,ResponseErrorHandler errorHandler){
    	RestTemplate restTemplate = builder.build();
    	restTemplate.setRequestFactory(factory);
    	restTemplate.setErrorHandler(errorHandler);
    	List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
    	interceptors.add(new ClientHttpRequestInterceptor() {
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
		});
    	restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
 
    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(this.connectTimeout);
        factory.setReadTimeout(this.readTimeout);
        return factory;
    }
 
    /**
     * resttemplate 请求响应不抛出异常
     * @return
     */
    @Bean
    public ResponseErrorHandler responseErrorHandler() {
    	return new ResponseErrorHandler() {
			
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				int rawStatusCode = response.getRawStatusCode();
				return rawStatusCode >= 400;
			}
			
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				log.warn("resttemplate response error,StatusCode:{}", response.getRawStatusCode());
			}
		};
    }
}