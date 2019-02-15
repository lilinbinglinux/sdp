/*
 * 文件名：SimpleCORSFilter.java
 * 版权：Copyright by www.sdp.com.cn
 * 描述：
 * 修改人：Anchor
 * 修改时间：2017年8月9日
 */

package com.sdp.cop.octopus.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 解决跨域请求的过滤器
 * @author Anchor
 * @version 2017年8月9日
 * @see SimpleCORSFilter
 * @since
 */

public class SimpleCORSFilter implements Filter{  
    
    @Override  
    public void destroy() {  
          
    }  
  
    @Override  
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {  
            HttpServletResponse response = (HttpServletResponse) res;  
            response.setHeader("Access-Control-Allow-Origin", "*");  
            response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");  
            response.setHeader("Access-Control-Max-Age", "3600");  
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");  
            chain.doFilter(req, res);  
          
    }  
  
    @Override  
    public void init(FilterConfig arg0) throws ServletException {  
          
    }  
  
}
