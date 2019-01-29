package com.sdp.frame.security.wrap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 作者: 吕一凡 
 * @date 2017年1月9日 下午9:21:18 
 * @version 版本: 1.0
 * 用户请求方法类
 */
public class SecurityRequestWrap {
	
	private HttpServletRequest req;
	
	private HttpServletResponse resp;
	
	public SecurityRequestWrap(HttpServletRequest req, HttpServletResponse resp) {
		super();
		this.req = req;
		this.resp = resp;
	}
	
	
	public HttpServletRequest getReq(){
		return this.req;
	}
	
	public HttpServletResponse getResp() {
		return resp;
	}	

	public HttpSession getSession(){
		return req.getSession();
	}	
	
}
