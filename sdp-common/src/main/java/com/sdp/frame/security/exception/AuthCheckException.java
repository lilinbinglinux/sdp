package com.sdp.frame.security.exception;

import com.sdp.common.BaseException;

/** 
 * @author 作者: jxw 
 * @date 创建时间: 2017年1月23日 下午6:09:09 
 * @version 版本: 1.0 
*/
public class AuthCheckException extends BaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8427546712194588063L;

	public AuthCheckException(String msg){
		super(msg);
	}
}

