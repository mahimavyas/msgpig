package com.msgpig.notification.configurations;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

//    @Bean(name = { "restTemplate" })
//    @Primary
//    public RestTemplate restTemplate() {
//        final RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
//        return restTemplate;
//    }
    
    @Bean
    public RequestConfig requestConfig() {
      RequestConfig result = RequestConfig.custom()
        .setConnectionRequestTimeout(10000)
        .setConnectTimeout(10000)
        .setSocketTimeout(10000)
        .build();
      return result;
    }

    @Bean
    public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager, RequestConfig requestConfig) {
      CloseableHttpClient result = HttpClientBuilder
        .create()
        .setConnectionManager(poolingHttpClientConnectionManager)
        .setDefaultRequestConfig(requestConfig)
        .build();
      return result;
    }

    @Bean(name = { "restTemplate" })
    public RestTemplate restTemplate(HttpClient httpClient) {
      HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
      requestFactory.setHttpClient(httpClient);
      return new RestTemplate(requestFactory);
    }

    
    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
      PoolingHttpClientConnectionManager result = new PoolingHttpClientConnectionManager();
      result.setMaxTotal(7200);
      // Default max per route is used in case it's not set for a specific route
      result.setDefaultMaxPerRoute(20);
      return result;
    }
    
//	  private ClientHttpRequestFactory clientHttpRequestFactory() {
//	        final HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//	        factory.setReadTimeout(50000);
//	        factory.setConnectTimeout(50000);
//	        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
//	    	cm.setMaxTotal(7200);
//	    	cm.setDefaultMaxPerRoute(1200);	
//	    	CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();      
//	        /*RequestConfig requestConfig = RequestConfig.custom()
//	                .setConnectTimeout(5000)
//	                .setConnectionRequestTimeout(5000)
//	                .setSocketTimeout(0)
//	                .build();
//	        
//	    	CloseableHttpClient httpClient = HttpClients.custom()
//	    	        .setConnectionManager(cm)
//	    	     //   .setDefaultRequestConfig(requestConfig)
//	    	        .setRetryHandler((exception, executionCount, context) -> {
//	    	        	try {
//							Thread.sleep(5000);
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							logger.error(e.getMessage());
//						}
//	                    if (executionCount > 3) {
//	                    	logger.error("Maximum tries reached for client http pool ");
//	                        return false;
//	                    }
//	                    if (exception instanceof org.apache.http.NoHttpResponseException) {
//	                    	logger.error("No response from server on " + executionCount + " call");
//	                        return true;
//	                    }
//	                    return false;
//	                })
//	    	        
//	    	        .build();
//	    	*/
//	       
//	        
//	       
//	         
//
//	         factory.setHttpClient(httpClient);
//	         return factory;
//	        
//	    }
}