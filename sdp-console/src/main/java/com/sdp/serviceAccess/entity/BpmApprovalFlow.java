package com.sdp.serviceAccess.entity;

/**
 * 
* @Description: bpm工单审批历史信息
  @ClassName: BpmApprovalFlow
* @author zy
* @date 2018年8月9日
* @company:www.sdp.com.cn
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月9日     zy      v1.0.0               修改原因
 */
public class BpmApprovalFlow extends BaseInfo{
	private String flowId;
	private String orderId;
	
	/**
	 * 是否同意(1 为同意，0 为不同意)
	 */
	private Integer  isAgreement;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 待办任务id
	 */
	private String pendtaskId;

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getIsAgreement() {
		return isAgreement;
	}

	public void setIsAgreement(Integer isAgreement) {
		this.isAgreement = isAgreement;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPendtaskId() {
		return pendtaskId;
	}

	public void setPendtaskId(String pendtaskId) {
		this.pendtaskId = pendtaskId;
	}

	public BpmApprovalFlow(String orderId) {
		super();
		this.orderId = orderId;
	}

	public BpmApprovalFlow() {
		super();
	}
	
	
	
	
	
}
