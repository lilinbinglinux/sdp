package com.sdp.servflow.logSer.log.model;

public class PubStatistics {
	
	// id
	private Integer id;
	
	// 服务id
	private String pubapiId;
	
	// 成功错误编码
	private String code;
	
	// 发送总量
	private Integer sendCount;
	
	// 发送成功总量
	private Integer successCount;
	
	// 发送失败总量
	private Integer failCount;
	
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPubapiId() {
		return pubapiId;
	}

	public void setPubapiId(String pubapiId) {
		this.pubapiId = pubapiId;
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

	public String getTenant_id() {
		return tenant_id;
	}

	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}
	
	
}
