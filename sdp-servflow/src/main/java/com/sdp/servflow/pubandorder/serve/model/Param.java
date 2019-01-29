package com.sdp.servflow.pubandorder.serve.model;

import java.util.Map;

/**
 * 公共参数
 * 
 * @author 任壮
 * @date 2018年1月29日
 */
public class Param {
    private String appId;
	private String order_id;
	private String url;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	//记录日志时候使用 区别cas 和同步 异步订阅
	private String sourceType;
	private String order_name;
	private String ser_id;
	private String tenant_id;
	private String requestID;
	private	Map<String, Object> urlParam;
	//业务参数
	private String busiParm;
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getOrder_name() {
		return order_name;
	}
	public void setOrder_name(String order_name) {
		this.order_name = order_name;
	}
	public String getSer_id() {
		return ser_id;
	}
	public void setSer_id(String ser_id) {
		this.ser_id = ser_id;
	}
	public String getTenant_id() {
		return tenant_id;
	}
	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}
	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	public String getBusiParm() {
		return busiParm;
	}
	public void setBusiParm(String busiParm) {
		this.busiParm = busiParm;
	}
	public Map<String, Object> getUrlParam() {
		return urlParam;
	}
	public void setUrlParam(Map<String, Object> urlParam) {
		this.urlParam = urlParam;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
