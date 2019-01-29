package com.sdp.servflow.pubandorder.flowchart.model;


public class ProcessNodeInfo {
	
	private String infoId;
	
	/**
	 * 流程图ID
	 */
	private String flowChartId;
	
	private String joinType;
	
	private String userId;
	
	private String updateTime;
	
	private String tenantId;

	public String getFlowChartId() {
		return flowChartId;
	}

	public String getJoinType() {
		return joinType;
	}

	public String getUserId() {
		return userId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setFlowChartId(String flowChartId) {
		this.flowChartId = flowChartId;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	
	
}
