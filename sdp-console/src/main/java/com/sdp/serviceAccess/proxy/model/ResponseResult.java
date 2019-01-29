package com.sdp.serviceAccess.proxy.model;

import java.io.Serializable;

/**
 * 请求服务的调用结果
 * @author Administrator
 *
 */
public class ResponseResult implements Serializable{
	
	private static final long serialVersionUID = 3084139223876992237L;

	//结果编码(0：成功，-1：失败。失败时查看errCode与errDesc)
	private String code;
	
	//失败编码(101：参数出错     999：服务内部错误)
	private String errCode;
	
	//失败描述
	private String errDesc;
	
	public ResponseResult(){
		
	}

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
}
