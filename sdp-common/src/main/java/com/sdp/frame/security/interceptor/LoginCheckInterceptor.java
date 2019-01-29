package com.sdp.frame.security.interceptor;

import org.springframework.stereotype.Component;

import com.sdp.frame.security.handle.LoginCheckHandle;
import com.sdp.frame.security.util.Constant;
import com.sdp.frame.security.wrap.SecurityRequestWrap;

/**
 * @author 作者: 吕一凡 
 * @date 2017年1月9日 下午9:19:26 
 * @version 版本: 1.0
 * 验证是否登录拦截器类
 */
@Component
public class LoginCheckInterceptor extends AbstractSecurityInterceptor{
	LoginCheckHandle securityHandle = new LoginCheckHandle();

	@Override
	public void doValidation(SecurityRequestWrap securityRequestWrap) throws Exception {
		Object obj = this.securityRepository.getResourceByType(Constant.ResourceType.UserResource, securityRequestWrap.getReq());
	    if(obj==null){//未登录
	    	securityHandle.failHandle(securityRequestWrap);
	    }else{//登录绕过该拦截器
	    	this.doNext(securityRequestWrap);
	    }
	}
}
