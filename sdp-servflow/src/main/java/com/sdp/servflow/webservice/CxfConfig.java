package com.sdp.servflow.webservice;
//package com.bonc.servflow.webservice;
//
//import javax.xml.ws.Endpoint;
//
//import org.apache.cxf.Bus;
//import org.apache.cxf.bus.spring.SpringBus;
////import org.apache.cxf.jaxws.EndpointImpl;
//import org.apache.cxf.transport.servlet.CXFServlet;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author renpengyuan
// * @date 2017年9月25日
// */
//@Configuration
//@ComponentScan(basePackages = "com.bonc.servflow.webservice")
//public class CxfConfig {
//	@Bean
//    public ServletRegistrationBean webservicedispatcherServlet() {
//        return new ServletRegistrationBean(new CXFServlet(), "/soap/*");
//    }
//    @Bean(name = Bus.DEFAULT_BUS_ID)
//    public SpringBus springBus() {
//        return new SpringBus();
//    }
//    @Bean
//    public WebServiceInterface weather() {
//        return new WebServiceImpl();
//    }
//    @Bean
//    public Endpoint endpoint() {
//        EndpointImpl endpoint = new EndpointImpl(springBus(),weather());
//        endpoint.publish("");
//        return endpoint;
//    }
//}
