package com.sdp.servflow.pubandorder.flowchart.model;


/**
 * 
 * 线信息Model
 *
 * @author ZY
 * @version 2017年8月2日
 * @see ProcessNodeJoin
 * @since
 */
public class ProcessNodeJoin {
	
	/**
	 * 线ID
	 */
	private String joinId;
	
	/**
	 * 开始节点ID
	 */
	private String startNodeId;
	
	/**
	 * 结束节点ID
	 */
	private String endNodeId;
	
	/**
	 * 开始节点方向
	 */
	private String joinType;
	
	/**
	 * 结束节点方向
	 */
	private String joinDirection;
	
	/**
	 * 创建时间
	 */
	private String creatime;
	
	/**
	 * 创建时间
	 */
	private String tenantId;
	
	/**
	 * 流程图ID
	 */
	private String flowChartId;
	
	/**
	 * 路径
	 */
	private String path;
	
	public String getStartNodeId() {
		return startNodeId;
	}

	public String getEndNodeId() {
		return endNodeId;
	}

	public String getJoinType() {
		return joinType;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setStartNodeId(String startNodeId) {
		this.startNodeId = startNodeId;
	}

	public void setEndNodeId(String endNodeId) {
		this.endNodeId = endNodeId;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getCreatime() {
		return creatime;
	}

	public void setCreatime(String creatime) {
		this.creatime = creatime;
	}

	public String getJoinId() {
		return joinId;
	}

	public void setJoinId(String joinId) {
		this.joinId = joinId;
	}

	public String getFlowChartId() {
		return flowChartId;
	}

	public void setFlowChartId(String flowChartId) {
		this.flowChartId = flowChartId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getJoinDirection() {
		return joinDirection;
	}

	public void setJoinDirection(String joinDirection) {
		this.joinDirection = joinDirection;
	}
}
