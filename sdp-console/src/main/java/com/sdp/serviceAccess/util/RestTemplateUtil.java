/**  
 * Copyright ©1997-2018 BONC Corporation, All Rights Reserved.
 * @Title: RestTemplateUtil.java
 * @Prject: channelcoord
 * @Package: com.bonc.utils
 * @Description: TODO
 * @Company: BONC
 * @author: LiJinfeng  
 * @date: 2018年1月10日 下午4:30:12
 * @version: V1.0  
 */

package com.sdp.serviceAccess.util;

import java.nio.charset.Charset;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName: RestTemplateUtil
 * @Description: TODO
 * @author: LiJinfeng
 * @date: 2018年1月10日 下午4:30:12
 */
@Component
public class RestTemplateUtil { 
	private static RestTemplate restTemplate;

	@Autowired
	public void setRestTemplate(RestTemplate restTemplate) {
		RestTemplateUtil.restTemplate = restTemplate;
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setReadTimeout(60*1000);// ms
		factory.setConnectTimeout(60*1000);// ms
		RestTemplateUtil.restTemplate.setRequestFactory(factory);
}

	public static <T> T get(String url, Class<T> responseType, Map<String, ?> uriVariables) {
		ResponseEntity<T> responseEntity = restTemplate.getForEntity(url, responseType, uriVariables);
		return RestTemplateUtil.resolveResponseEntity(responseEntity);
	}

	public static <T> T get(String url, Class<T> responseType) {
		ResponseEntity<T> responseEntity = restTemplate.getForEntity(url, responseType);
		return RestTemplateUtil.resolveResponseEntity(responseEntity);

	}

	public static <T> T post(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) {
		ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, request, responseType, uriVariables);
		return RestTemplateUtil.resolveResponseEntity(responseEntity);
	}
	
	public static <T> T put(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) {
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, 
        		RestTemplateUtil.getRequestEntity(request), responseType, uriVariables);
        return RestTemplateUtil.resolveResponseEntity(responseEntity);
	}
	
	public static <T> T delete(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) {
		ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, 
        		RestTemplateUtil.getRequestEntity(request), responseType, uriVariables);
        return RestTemplateUtil.resolveResponseEntity(responseEntity);
	}
	
	//解析响应信息
	public static <T> T resolveResponseEntity(ResponseEntity<T> responseEntity) {
		if(responseEntity.getStatusCode().is2xxSuccessful()) {
			return responseEntity.getBody();
		}
		return null;
	}
	
	//构建请求体
	public static <T> HttpEntity<T> getRequestEntity(T request) {
		// 请求头
        HttpHeaders headers = new HttpHeaders();
        MimeType mimeType = MimeTypeUtils.parseMimeType("application/json");
        MediaType mediaType = new MediaType(mimeType.getType(), mimeType.getSubtype(), Charset.forName("UTF-8"));
        headers.setContentType(mediaType);
        // 请求体
        return new HttpEntity<>(request, headers);
	}

}
