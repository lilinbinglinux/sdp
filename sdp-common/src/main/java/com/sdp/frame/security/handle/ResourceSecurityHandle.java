package com.sdp.frame.security.handle;

import org.springframework.stereotype.Component;

import com.sdp.frame.security.exception.AuthCheckException;
import com.sdp.frame.security.wrap.SecurityRequestWrap;
import com.sdp.frame.util.RequestUtil;

/**
 * @author 作者: 吕一凡 
 * @date 2017年1月9日 下午9:24:26 
 * @version 版本: 1.0
 */
@Component
public class ResourceSecurityHandle implements ISecurityHandle{
	
	private String deniedUri = "/resources/errors/503_error.jsp";

	@Override
	public void successHandle(SecurityRequestWrap securityRequestWrap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void failHandle(SecurityRequestWrap securityRequestWrap) throws Exception {
		if(RequestUtil.isAjaxReq(securityRequestWrap.getReq())){//如果是ajax请求
			throw new AuthCheckException("您无权访问该页面！");
		}else{//不是ajax请求
			securityRequestWrap.getResp()
		      .sendRedirect(securityRequestWrap.getReq().getContextPath()+deniedUri);
		}
	}

	public String getDeniedUri() {
		return deniedUri;
	}

	public void setDeniedUri(String deniedUri) {
		this.deniedUri = deniedUri;
	}

}
