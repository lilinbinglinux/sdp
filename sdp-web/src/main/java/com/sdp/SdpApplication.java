package com.sdp;

import javax.servlet.MultipartConfigElement;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.sdp.common.FtpConfigProperties;
import com.sdp.common.entity.Auth2Details;
import com.sdp.common.entity.EnterpriseWeixinProp;
import com.sdp.common.entity.MailConfigProp; 


//@EnableFeignClients
//@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@Configuration
@ComponentScan(basePackages = "com")
@MapperScan(basePackages={"com.sdp.frame.base.dao","com.sdp.servflow.logSer.log.mapper","com.sdp.sqlModel.mapper","com.sdp.serviceAccess.mapper","com.sdp.code.mapper"})
@EnableConfigurationProperties({MailConfigProp.class,Auth2Details.class,EnterpriseWeixinProp.class,FtpConfigProperties.class})
@EnableScheduling
public class SdpApplication extends SpringBootServletInitializer{
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {  
        return application.sources(SdpApplication.class);  
    } 
	
	public static void main(String[] args) {
		SpringApplication.run(SdpApplication.class, args);
	}

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//文件最大
		factory.setMaxFileSize("1024MB"); //KB,MB
		//设置总上传数据总大小
		factory.setMaxRequestSize("102400MB");
		return factory.createMultipartConfig();
	}


}