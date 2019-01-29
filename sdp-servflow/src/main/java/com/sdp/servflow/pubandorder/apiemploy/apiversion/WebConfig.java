package com.sdp.servflow.pubandorder.apiemploy.apiversion;

import org.springframework.boot.autoconfigure.web.WebMvcRegistrationsAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Component
public class WebConfig extends WebMvcRegistrationsAdapter{
	
	public RequestMappingHandlerMapping getRequestMappingHandlerMapping(){
		return new CustomRequestMappingHandlerMapping();
	}

}
