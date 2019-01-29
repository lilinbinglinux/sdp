package com.sdp.sso.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.bonc.sso.client.SSOFilter;
import com.sdp.frame.web.service.user.UserService;
import com.sdp.sso.env.EnvCondition;
import com.sdp.sso.util.FrameConfigurationProperties;

@Component
@Conditional(value = { EnvCondition.class })
public class SecurityFrameWorkFilters {
	@Autowired
	private FrameConfigurationProperties frameConfigurationProperties;
	
	@Autowired
    private UserService userService;
	
	@Bean
	public FilterRegistrationBean ssoRegistrationBean() {
		System.out.println("参数配置="+JSON.toJSONString(frameConfigurationProperties));
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();        
		registrationBean.setFilter(new SSOFilter());
		registrationBean.setName(frameConfigurationProperties.getSso().getName());
		registrationBean.addInitParameter("serverName", frameConfigurationProperties.getSso().getServerName());
		registrationBean.addInitParameter("casServerUrlPrefix", frameConfigurationProperties.getSso().getCasServerUrlPrefix());
		registrationBean.addInitParameter("casServerLoginUrl", frameConfigurationProperties.getSso().getCasServerLoginUrl());
		registrationBean.addInitParameter("singleSignOut", frameConfigurationProperties.getSso().getSingleSignOut());
		registrationBean.addInitParameter("skipUrls", frameConfigurationProperties.getSso().getSkipUrls());
		registrationBean.addInitParameter("loginUserHandle", frameConfigurationProperties.getSso().getLoginUserHandle());
		registrationBean.addInitParameter("characterEncoding", frameConfigurationProperties.getSso().getCharacterEncoding());
		registrationBean.addInitParameter("encoding", frameConfigurationProperties.getSso().getEncoding());
		//        registrationBean.addUrlPatterns("/*");
		//        registrationBean.addInitParameter("redirectAfterValidation", "false");
		registrationBean.setOrder(Integer.parseInt(frameConfigurationProperties.getSso().getOrder()));
		return registrationBean;
	}
	
	//本地处理登录地址
	@Bean   
     public FilterRegistrationBean loginHanderFilterConfiguration(UserService userService) {
         final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
         LoginHandlerFilter loginHandlerFilter = new LoginHandlerFilter();
         loginHandlerFilter.setUserService(userService);
         registrationBean.setFilter(loginHandlerFilter);
         registrationBean.setName(frameConfigurationProperties.getLoginHandlerConf().getName());
         registrationBean.addInitParameter("skipUrls", frameConfigurationProperties.getLoginHandlerConf().getSkipUrls());
         //registrationBean.addUrlPatterns("/*");
         registrationBean.setOrder(Integer.parseInt(frameConfigurationProperties.getLoginHandlerConf().getOrder()));
         return registrationBean;
     }
}