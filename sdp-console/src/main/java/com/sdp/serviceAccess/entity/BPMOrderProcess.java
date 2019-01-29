package com.sdp.serviceAccess.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
* @Description: 审批待办流程图对象  PO  对象，不是实体类
  @ClassName: BPMOrderProcess
* @author zy
* @date 2018年8月9日
* @company:www.bonc.com.cn
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月9日     zy      v1.0.0               修改原因
 */
public class BPMOrderProcess {
	
	/**
     * 流程模型Id
     */
	private String procId;
	 /**
     * 流程Id
     */
    private String procInstId;

    /**
     * 当前任务Id
     */
    private String taskId;

    /**
     * 是否签收, 10已签收，20 未签收，30默认签收
     */
    private int assignee;
    
    /**
     *订单id
     */
    private String orderId;
    
    /**
     * 服务名称
     */
    private String productName;
    
    /**
     * 订单申请人
     */
    private String applyUserName;
    
    /**
     * 当前订单监控信息
     */
    private String monitorProcessUrl;
    
    /**
     * 订单申请时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderStartDate;
    
    /**
     * 订单审批时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approvalDate;

	private String applyName;//申请名称--来自订单的的applyName
	
	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	
	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public int getAssignee() {
		return assignee;
	}

	public void setAssignee(int assignee) {
		this.assignee = assignee;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public Date getOrderStartDate() {
		return orderStartDate;
	}

	public void setOrderStartDate(Date orderStartDate) {
		this.orderStartDate = orderStartDate;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getMonitorProcessUrl() {
		return monitorProcessUrl;
	}

	public void setMonitorProcessUrl(String monitorProcessUrl) {
		this.monitorProcessUrl = monitorProcessUrl;
	}
	
	
}
