package com.sdp.serviceAccess.proxy.model;

import java.io.Serializable;

/**
 * 提供给服务提供方回调接口的请求参数
 * @author Administrator
 *
 */
public class FeedBackRequestParam implements Serializable {

	private static final long serialVersionUID = 9179057693545696745L;
	
	//结果编码（0：成功，-1：失败。失败时查看errCode与errDesc）  不可为null
	private String code;
	
	//失败编码（由各服务提供方自己定义） 可为null 
	private String errCode;
	
	//失败描述  可为null
	private String errDesc;
	
	//反馈信息，若没有则不添加此项   可为null
	private String data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrDesc() {
		return errDesc;
	}

	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
