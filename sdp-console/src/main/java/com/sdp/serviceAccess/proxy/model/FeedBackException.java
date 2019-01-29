package com.sdp.serviceAccess.proxy.model;

import java.io.Serializable;

/**
 * 回调服务异常类
 * @author Administrator
 *
 */
public class FeedBackException extends RuntimeException {

	private static final long serialVersionUID = -6521673271244696931L;
	
	public Serializable exMsg;
	
	public FeedBackException(Serializable exMsg){
		this.exMsg = exMsg;
	}

	public Serializable getExMsg() {
		return exMsg;
	}

	public void setExMsg(Serializable exMsg) {
		this.exMsg = exMsg + "feed back exception";
	}
}
