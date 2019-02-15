/*
 * 文件名：Swagger2.java
 * 版权：Copyright by www.sdp.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月26日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Swagger配置类
 * @author zhangyunzhen
 * @version 2017年7月26日
 * @see Swagger2
 * @since
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.sdp.cop.octopus.controller"))
                    .paths(PathSelectors.any())
                    .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                    .title("消息通知渠道之企业微信管理RESTFUL API")
                    .description("提供了通讯录管理，消息推送，发消息权限管理等API,管理员可通过这些API管理发送权限和对发送消息进行监控，多终端可以通过这些API给相关部门和人员发送消息")
                    .termsOfServiceUrl("")
                    .contact("张允臻")
                    .version("1.0")
                    .build();
    }
}
