package com.sdp.servflow.logSer.log.model;

public class OrderTotalStatistics {

	// id
	private Integer id;

	// 订阅id
	private String orderid;
	
	// 订阅名称
	private String order_name;

	// 发送总量
	private Integer sendCount;

	// 发送成功总量
	private Integer successCount;

	// 发送失败总量
	private Integer failCount;
	
	// 日期(yyyyMMdd)
	private Integer sendDate;
	
	// 服务id
	private String ser_id;
	
	// 版本号（同步服务使用）
	private String ser_version;

	// 租户id
	private String tenant_id;

	//订阅名称
	private String orderName;

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

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

	public String getOrder_name() {
		return order_name;
	}

	public void setOrder_name(String order_name) {
		this.order_name = order_name;
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

	public String getTenant_id() {
		return tenant_id;
	}

	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}

}
