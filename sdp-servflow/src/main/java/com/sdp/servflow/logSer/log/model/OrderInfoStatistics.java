package com.sdp.servflow.logSer.log.model;

public class OrderInfoStatistics {

	// id
	private Integer id;

	// 服务id
	private String orderid;

	// 发送总量
	private Integer sendCount;

	// 成功错误编码
	private String code;

	// 日期(yyyyMMdd)
	private Integer sendDate;

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

	public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	public Integer getSendDate() {
		return sendDate;
	}

	public void setSendDate(Integer sendDate) {
		this.sendDate = sendDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTenant_id() {
		return tenant_id;
	}

	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}

}
