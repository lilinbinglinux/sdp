package com.sdp.servflow.pubandorder.node.model.node;

import java.io.Serializable;

import com.sdp.servflow.pubandorder.node.model.parameter.Parameter;

public class InterfaceNode extends Node  implements Serializable{
    
	private static final long serialVersionUID = 1L;
	
	
    public static final String synType = "0"; //调用类型为同步调用
    public static final String asyType = "1"; //调用类型为异步调用
    
    /***
     * url地址
     */
    private String url;
    /***
     * 端口号
     */
    private String port;
    /***
     * 请求协议
     */
    private String agreement;
    /***
     * 请求方式(get/post)
     */
    private String method;
    /***
     *入参
     */
    private Parameter inParameter;
    public String getCallType() {
		return callType;
	}
	public void setCallType(String callType) {
		this.callType = callType;
	}
	/***
     *出参
     */
    private Parameter outParameter;
    /***
     *调用方式（0同步  1异步）
     */
    private String callType ;
    
    
    public String getAgreement() {
        return agreement;
    }
    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Parameter getInParameter() {
        return inParameter;
    }
    @Override
	public String toString() {
		return "InterfaceNode [url=" + url + ", port=" + port + ", agreement=" + agreement + ", method=" + method
				+ ", inParameter=" + inParameter + ", outParameter=" + outParameter + ", callType=" + callType + "]";
	}
	public void setInParameter(Parameter inParameter) {
        this.inParameter = inParameter;
    }
    public Parameter getOutParameter() {
        return outParameter;
    }
    public void setOutParameter(Parameter outParameter) {
        this.outParameter = outParameter;
    }
    public String getPort() {
        return port;
    }
    public void setPort(String port) {
        this.port = port;
    }
    

}
