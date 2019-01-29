package com.sdp.frame.base.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/** 
 * @author 作者: jxw 
 * @date 创建时间: 2017年2月10日 下午3:35:17 
 * @version 版本: 1.0 
 * 统一异常处理
*/
@Component 
public class SpringMVCExceptionResolver implements HandlerExceptionResolver{
	
	private Log log = LogFactory.getLog(this.getClass());

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ex.printStackTrace();
        log.error("服务发生异常。",ex);
        return null;
	}
}

