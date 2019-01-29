package com.sdp.servflow.pubandorder.orderServer.model;

/**
 * 
 * 存放关于 其他方调用第三方集成平台必传的一些数据
 *
 * @author 任壮
 * @version 2017年11月28日
 * @see PublishBo
 * @since
 */
public class PublishBo {
    
    /***
     * 发布人发布消息使用的接口
     */
    private String ip;

    /***
     * 发布人发布消息使用的接口
     */
    private String appId;
    /***
     * 发布人发布消息使用的serId(对应ser_main表中的ser_Id)
     */
    private String serId;
    /**
     * 请求体中的信息
     */
    private String msg;
    /**
     * 标记的本次访问的参数
     */
    private String requestId;
    public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	/**
     * 租户id
     */
    private String tenantId;
    public String getMsg() {
        return msg;
    }
    public String getTenantId() {
        return tenantId;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    @Override
	public String toString() {
		return "PublishBo [ip=" + ip + ", appId=" + appId + ", serId=" + serId + ", msg=" + msg + ", requestId="
				+ requestId + ", tenantId=" + tenantId + "]";
	}
	public String getSerId() {
        return serId;
    }
    public void setSerId(String serId) {
        this.serId = serId;
    }
}
