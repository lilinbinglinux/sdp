package com.sdp.servflow.pubandorder.serve.model;

import java.util.HashMap;
import java.util.Map;

/***
 * 
 * 关于协议的数据
 * 
 * @author 任壮
 * @version 2017年9月26日
 * @see ProtocolData
 * @since
 */
public class ProtocolData {
    
    /***
     * 请求头信息
     */
    private Map<String, Object> requestHeader = new HashMap<String, Object>();
    /***
     * 响应头信息
     */
    private Map<String, Object> ResponseHeader = new HashMap<String, Object>();
    /**
     * 位于url上的参数
     */
    private Map<String, Object> urlParam = new HashMap<String, Object>();
    /**
     * 本次的url
     */
    private String url;
    /**
     * 请求体
     */
    private Object requestBody;
    /***
     * 响应体信息
     */
    private Object responseBody;
    
    /**
     * 请求格式（json.xml.soap）
     */
    private String reqformat;
    /**
     * 响应格式（json.xml.soap）
     */
    private String reposneformat;
    
    public Map<String, Object> getRequestHeader() {
        return requestHeader;
    }
    public void setRequestHeader(Map<String, Object> requestHeader) {
        this.requestHeader = requestHeader;
    }
    public Map<String, Object> getResponseHeader() {
        return ResponseHeader;
    }
    public void setResponseHeader(Map<String, Object> responseHeader) {
        ResponseHeader = responseHeader;
    }
    public Map<String, Object> getUrlParam() {
        return urlParam;
    }
    public void setUrlParam(Map<String, Object> urlParam) {
        this.urlParam = urlParam;
    }
    public Object getRequestBody() {
        return requestBody;
    }
    public void setRequestBody(Object requestBody) {
        this.requestBody = requestBody;
    }
    public Object getResponseBody() {
        return responseBody;
    }
    public void setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
    }
    public String getReqformat() {
        return reqformat;
    }
    public void setReqformat(String reqformat) {
        this.reqformat = reqformat;
    }
    public String getReposneformat() {
        return reposneformat;
    }
    public void setReposneformat(String reposneformat) {
        this.reposneformat = reposneformat;
    }
    @Override
    public String toString() {
        return "ProtocolData [requestHeader=" + requestHeader + ", ResponseHeader="
               + ResponseHeader + ", urlParam=" + urlParam + ", requestBody=" + requestBody
               + ", responseBody=" + responseBody + ", reqformat=" + reqformat + ", reposneformat="
               + reposneformat + "]";
    }
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
