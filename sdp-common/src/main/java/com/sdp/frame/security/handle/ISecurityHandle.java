package com.sdp.frame.security.handle;

import java.io.IOException;

import com.sdp.frame.security.wrap.SecurityRequestWrap;

/**
 * @author 作者: 吕一凡 
 * @date 2017年1月9日 下午9:24:11 
 * @version 版本: 1.0
 */
public interface ISecurityHandle {
	
	public void successHandle(SecurityRequestWrap securityRequestWrap)  throws IOException,Exception;
	
	public void failHandle(SecurityRequestWrap securityRequestWrap)  throws IOException,Exception;
}
