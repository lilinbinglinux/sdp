package com.sdp.servflow.pubandorder.flowchart.model;

/**
 * 
 * 流程图Model
 *
 * @author ZY
 * @version 2017年8月2日
 * @see FlowChart
 * @since
 */
public class FlowChart {
    
    /**
     * 流程图ID
     */
    private String flowChartId;
	
	/**
	 * 流程图名称
	 */
    private String flowChartName;
	
	/**
	 * 创建日期
	 */
    private String creaTime;
	
	/**
	 * 创建者登录id
	 */
    private String userId;
	
	/**
	 * 更新时间
	 */
    private String updateTime;
	
	/**
	 * 租户ID
	 */
    private String tenant_id;

    public String getFlowChartId() {
        return flowChartId;
    }

    public void setFlowChartId(String flowChartId) {
        this.flowChartId = flowChartId;
    }

    public String getFlowChartName() {
        return flowChartName;
    }

    public void setFlowChartName(String flowChartName) {
        this.flowChartName = flowChartName;
    }

    public String getCreaTime() {
        return creaTime;
    }

    public void setCreaTime(String creaTime) {
        this.creaTime = creaTime;
    }

    public String getUserId() {
        return userId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }

}
