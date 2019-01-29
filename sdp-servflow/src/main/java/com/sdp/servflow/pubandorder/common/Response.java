package com.sdp.servflow.pubandorder.common;

public class Response implements Cloneable{

	/**
	 * 返回编码
	 * */
	private String respCode;

	/**
	 * 返回编码描述
	 * */
	private String respDesc;
	/**
	 * 返回结果
	 * */
	private String result;
	/**
	 * 标记的连接
	 */
	private String requestId;
	
	
	@Override
    public Response clone()
        throws CloneNotSupportedException {
        return (Response)super.clone();
    }
	
	
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespDesc() {
		return respDesc;
	}
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
    public String getRequestId() {
        return requestId;
    }


    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }


    @Override
    public String toString() {
        return "Response [respCode=" + respCode + ", respDesc=" + respDesc + ", result=" + result
               + ", requestId=" + requestId + "]";
    }
}
