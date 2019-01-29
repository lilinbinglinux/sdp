package com.sdp.frame; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.sdp.frame.security.SpringMVCSecurityInterceptor;


@Configuration
@ComponentScan(basePackages = "com.bonc")  
@ConfigurationProperties(prefix = "system.conf", ignoreUnknownFields = true)
public class MvcConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    SpringMVCSecurityInterceptor springMVCSecurityInterceptor;
    private String skipUrl;
	
	public String getSkipUrl() {
		return skipUrl;
	}

	public void setSkipUrl(String skipUrl) {
		this.skipUrl = skipUrl;
	}

	private String[] skipUrls;
	

	@Override
    public void addInterceptors(InterceptorRegistry registry) {  
	    if ((skipUrl != null) && (skipUrl.trim().length() > 0)) {
	      this.skipUrls = skipUrl.split(",");
	    }
        registry.addInterceptor(springMVCSecurityInterceptor)
            .addPathPatterns("/**")  
            .excludePathPatterns(skipUrls);
        super.addInterceptors(registry);
    }
}