package com.sdp.servflow.logSer.log.model;

import java.sql.Timestamp;

/** 
* @Title: AccessLogBean.java 
* @Description: 日志记录类
* fkr 
* 2017年11月9日
*/ 
public class LogBean {
	
	// id
	private Integer id;
	
	// 日志类型 0 代表服务，1代表 流程版本
	private Integer logType;
	
	// 服务id
	private String pubapiId;
	
	// 订阅名称
	private String order_name;
	
	// 订阅id
	private String orderid;
	
	// 请求报文
	private String requestMsg;
	
	// 返回报文
	private String responseMsg;
	
	// 开始时间
	private Timestamp startTime;
	
	private String startTimeStr;
	
	// 结束时间
	private Timestamp endTime;
	
	private String endTimeStr;
	
	// 请求id
	private String requestId;
	
	// 耗时 毫秒（结束时间 - 开始时间）
	private Long useTime;

	// 天数
	private Integer dayTime;
	
	// 成功失败详细编码
	private String code;
	
	// 实例全局id(具有唯一性和一次性)
	private Integer instanceId;
	
	// 数据来源 (0 同步 1 异步 2 cas)
	private String sourceType;
	
	// url地址
	private String url;
	
	// 租户id
	private String tenant_id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getPubapiId() {
		return pubapiId;
	}

	public void setPubapiId(String pubapiId) {
		this.pubapiId = pubapiId;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRequestMsg() {
		return requestMsg;
	}

	public void setRequestMsg(String requestMsg) {
		this.requestMsg = requestMsg;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public String getOrder_name() {
		return order_name;
	}

	public void setOrder_name(String order_name) {
		this.order_name = order_name;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Long getUseTime() {
		return useTime;
	}

	public void setUseTime(Long useTime) {
		this.useTime = useTime;
	}

	public Integer getDayTime() {
		return dayTime;
	}

	public void setDayTime(Integer dayTime) {
		this.dayTime = dayTime;
	}

	/*public Integer getSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(LOG_CONSTANT lc) {
		if(lc == LOG_CONSTANT.SENDSUCCESS){
			this.sendFlag = 0;
		}else{
			this.sendFlag = 1;
		}
	}*/

	public Integer getInstanceId() {
		return instanceId;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public void setInstanceId(Integer instanceId) {
		this.instanceId = instanceId;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getLogType() {
		return logType;
	}

	public void setLogType(Integer logType) {
		this.logType = logType;
	}

	public String getTenant_id() {
		return tenant_id;
	}

	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}

	@Override
	public String toString() {
		return "LogBean [id=" + id + ", logType=" + logType + ", pubapiId=" + pubapiId + ", order_name=" + order_name
				+ ", orderid=" + orderid + ", requestMsg=" + requestMsg + ", responseMsg=" + responseMsg
				+ ", startTime=" + startTime + ", startTimeStr=" + startTimeStr + ", endTime=" + endTime
				+ ", endTimeStr=" + endTimeStr + ", requestId=" + requestId + ", useTime=" + useTime + ", dayTime="
				+ dayTime + ", code=" + code + ", instanceId=" + instanceId + ", sourceType=" + sourceType
				+ ", tenant_id=" + tenant_id + "]";
	}
	
	
}
