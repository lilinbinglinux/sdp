package com.sdp.frame.security.interceptor.manager;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.sdp.frame.security.interceptor.ButtonSecurityInterceptor;
import com.sdp.frame.security.interceptor.ISecurityInterceptor;
import com.sdp.frame.security.interceptor.LoginCheckInterceptor;
import com.sdp.frame.security.interceptor.ResourceSecurityInterceptor;
import com.sdp.frame.security.wrap.SecurityRequestWrap;

/** 
 * @author 作者: jxw 
 * @date 创建时间: 2017年1月16日 下午7:04:48 
 * @version 版本: 1.0 
*/
public class SecurityInterceptorManager {
	
	private ISecurityInterceptor rootSecurityInterceptor;
	
	List<ISecurityInterceptor> securityInterceptors = new ArrayList<ISecurityInterceptor>();
	
	
	public SecurityInterceptorManager(List<ISecurityInterceptor> securityInterceptors) {
		super();
		this.securityInterceptors = securityInterceptors;
		ISecurityInterceptor tempSecurityInterceptor = null;
		for(ISecurityInterceptor securityInterceptor:securityInterceptors){
			if(tempSecurityInterceptor == null){
				tempSecurityInterceptor = securityInterceptor;
				rootSecurityInterceptor = tempSecurityInterceptor;
				continue;
			}
			tempSecurityInterceptor.setChildInterceptor(securityInterceptor);
			tempSecurityInterceptor = securityInterceptor;
		}
	}
	
	public SecurityInterceptorManager() {
		super();
		securityInterceptors.add(new LoginCheckInterceptor());
		securityInterceptors.add(new ResourceSecurityInterceptor());
		securityInterceptors.add(new ButtonSecurityInterceptor());
		
		ISecurityInterceptor tempSecurityInterceptor = null;
		for(ISecurityInterceptor securityInterceptor:securityInterceptors){
			if(tempSecurityInterceptor == null){
				tempSecurityInterceptor = securityInterceptor;
				rootSecurityInterceptor = tempSecurityInterceptor;
				continue;
			}
			tempSecurityInterceptor.setChildInterceptor(securityInterceptor);
			tempSecurityInterceptor = securityInterceptor;
		}
	}
	
	

	public void doInterceptor(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		SecurityRequestWrap securityRequestWrap = new SecurityRequestWrap(request,response);
		if(rootSecurityInterceptor!=null)
		rootSecurityInterceptor.doInterceptor(securityRequestWrap);
	}

	public List<ISecurityInterceptor> getSecurityInterceptors() {
		return securityInterceptors;
	}

	public void setSecurityInterceptors(List<ISecurityInterceptor> securityInterceptors) {
		this.securityInterceptors = securityInterceptors;
	}

	public SecurityInterceptorManager(List<ISecurityInterceptor> securityInterceptors,
			ISecurityInterceptor rootSecurityInterceptor) {
		super();
		this.securityInterceptors = securityInterceptors;
		this.rootSecurityInterceptor = rootSecurityInterceptor;
	}
	
	
	
	
}

