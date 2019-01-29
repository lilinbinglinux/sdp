/*
 * 文件名：ApplicationFilterConfiguration.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：ke_wang
 * 修改时间：2016年9月30日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus;


import javax.servlet.Filter;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sdp.cop.octopus.filter.SimpleCORSFilter;


/**
 * ApplicationFilterConfiguration
 * springboot配置应用过滤器
 * @author ke_wang
 * @version 2016年9月30日
 * @see ApplicationFilterConfiguration
 * @since
 */
@Configuration
public class ApplicationFilterConfiguration {

    /**
     * 
     * Description:
     * filterRegistration
     * @return FilterRegistrationBean 
     * @see
     */
    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(simpleCORSFilter());
        registration.setName("SimpleCORS Filter");
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }

    /**
     * 
     * Description:
     * 返回SSOFilter
     * @return com.bonc.sso.client.SSOFilter
     * @see
     */
    @Bean(name = "SimpleCORS Filter")
    public Filter simpleCORSFilter() {
        return new SimpleCORSFilter();
    }
}
