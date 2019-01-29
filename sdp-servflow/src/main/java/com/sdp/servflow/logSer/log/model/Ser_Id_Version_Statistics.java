package com.sdp.servflow.logSer.log.model;

public class Ser_Id_Version_Statistics {
	
	// id
	private Integer id;
	
	// 订阅id
	private String orderid;

	// 服务id
	private String ser_id;

	// 版本id
	private String ser_version;
	
	// 服务名称
	private String ser_name;

	// 发送总量
	private Integer sendCount;

	// 发送成功总量
	private Integer successCount;

	// 发送失败总量
	private Integer failCount;

	// 日期(yyyyMMdd)
	private Integer sendDate;

	// 日志来源(0 同步，1 异步，2cas)
	private String sourceType;
	
	// 租户id
	private String tenant_id;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getSer_id() {
		return ser_id;
	}

	public void setSer_id(String ser_id) {
		this.ser_id = ser_id;
	}

	public String getSer_version() {
		return ser_version;
	}

	public void setSer_version(String ser_version) {
		this.ser_version = ser_version;
	}

	public String getSer_name() {
		return ser_name;
	}

	public void setSer_name(String ser_name) {
		this.ser_name = ser_name;
	}

	public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	public Integer getFailCount() {
		return failCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	public Integer getSendDate() {
		return sendDate;
	}

	public void setSendDate(Integer sendDate) {
		this.sendDate = sendDate;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getTenant_id() {
		return tenant_id;
	}

	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}
	
}
