package com.sdp.frame.security.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.sdp.frame.security.handle.ISecurityHandle;
import com.sdp.frame.security.repository.ISecurityRepository;
import com.sdp.frame.security.repository.SecurityRepositoryFactory;
import com.sdp.frame.security.wrap.SecurityRequestWrap;

/**
 * @author 作者: 吕一凡 
 * @date 2017年1月9日 下午9:18:06 
 * @version 版本: 1.0
 * 拦截器父类
 */

public abstract class AbstractSecurityInterceptor implements ISecurityInterceptor{
	
	protected Log log = LogFactory.getLog(this.getClass());
	
	private ISecurityInterceptor securityInterceptor;
	
	private ISecurityHandle securityHandle;
	
	protected ISecurityRepository securityRepository = SecurityRepositoryFactory.getRepository();
	
	@Override
	public void doInterceptor(SecurityRequestWrap securityRequestWrap) throws Exception{
		this.doValidation(securityRequestWrap);
	}
	
	//处理成功后调用下一个interceptor
	@Override
	public void doNext(SecurityRequestWrap securityRequestWrap) throws Exception{
		if(securityInterceptor!=null){
			securityInterceptor.doInterceptor(securityRequestWrap);
		}
	} 
	//设置孩子节点
	public void setChildInterceptor(ISecurityInterceptor securityInterceptor){
	       this.securityInterceptor = 	securityInterceptor;
	}
	
	public abstract void doValidation(SecurityRequestWrap securityRequestWrap)throws Exception;
	
	
	//获得当前访问的url
	protected String getCurrentRequestUrl(SecurityRequestWrap securityRequestWrap){
		String currentURL = securityRequestWrap.getReq().getRequestURI();   
		return currentURL;
	}
	
	//获得根目录
	protected String getRootRequestUrl(SecurityRequestWrap securityRequestWrap){
		String rootUrl = securityRequestWrap.getReq().getContextPath();
		return rootUrl;
	}

	public ISecurityInterceptor getSecurityInterceptor() {
		return securityInterceptor;
	}

	public void setSecurityInterceptor(ISecurityInterceptor securityInterceptor) {
		this.securityInterceptor = securityInterceptor;
	}
	

	public AbstractSecurityInterceptor() {
		super();
	}

	public ISecurityHandle getSecurityHandle() {
		return securityHandle;
	}
	
	public void setSecurityHandle(ISecurityHandle securityHandle) {
		this.securityHandle = securityHandle;
	}
}
