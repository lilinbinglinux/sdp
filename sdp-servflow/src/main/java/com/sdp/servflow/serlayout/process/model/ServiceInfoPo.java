package com.sdp.servflow.serlayout.process.model;

import java.util.List;

/**
 * 页面列表显示类
 * @author zy
 *
 */
public class ServiceInfoPo {
	/**
     * 版本ID
     */
    private String serVerId;
	/**
	 * 服务ID
	 */
	private String serId;
	
	/**
	 * 服务名称
	 */
	private String serName;
	
	/**
	 * 服务类型
	 */
	private String serType;
	
	/**
	 * 服务编码
	 */
	private String serCode;
	
	/**
	 * 服务版本
	 */
	private String serVersion;
	
	/**
	 * 流程图xml
	 */
	private String serFlow;
	
	/**
	 * 创建时间
	 */
	private String creatTime;
	
	/**
     * 是否停用(0 正常 1删除)
     */
    private String stopFlag;
	
	/**
	 * 是否删除(0 正常 1删除)
	 */
	private String delFlag;
	
	/**
     * 同步、异步（0同步 1异步）
     */
    private String synchFlag;
	
	/**
     * 租户ID
     */
    private String tenantId;

    /**
     * 用户ID
     */
    private String loginId;
    
    /**
     * 父id（用来展示父子关系）
     */
    private String parentId;

	/**
	 * 查看当前状态是否已经推送
	 */
	private String pushState;
    
    private List<ServiceInfoPo> children;

	public String getSerId() {
		return serId;
	}

	public void setSerId(String serId) {
		this.serId = serId;
	}

	public String getSerName() {
		return serName;
	}

	public void setSerName(String serName) {
		this.serName = serName;
	}

	public String getSerType() {
		return serType;
	}

	public void setSerType(String serType) {
		this.serType = serType;
	}

	public String getSerCode() {
		return serCode;
	}

	public void setSerCode(String serCode) {
		this.serCode = serCode;
	}

	public String getSerVersion() {
		return serVersion;
	}

	public void setSerVersion(String serVersion) {
		this.serVersion = serVersion;
	}

	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public String getStopFlag() {
		return stopFlag;
	}

	public void setStopFlag(String stopFlag) {
		this.stopFlag = stopFlag;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getSerFlow() {
		return serFlow;
	}

	public void setSerFlow(String serFlow) {
		this.serFlow = serFlow;
	}

	public String getSerVerId() {
		return serVerId;
	}

	public void setSerVerId(String serVerId) {
		this.serVerId = serVerId;
	}
	
	public String getSynchFlag() {
		return synchFlag;
	}

	public void setSynchFlag(String synchFlag) {
		this.synchFlag = synchFlag;
	}
	
	public List<ServiceInfoPo> getChildren() {
		return children;
	}

	public String getPushState() {
		return pushState;
	}

	public void setPushState(String pushState) {
		this.pushState = pushState;
	}

	public void setChildren(List<ServiceInfoPo> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "ServiceInfoPo [serVerId=" + serVerId + ", serId=" + serId + ", serName=" + serName + ", serType="
				+ serType + ", serCode=" + serCode + ", serVersion=" + serVersion + ", serFlow=" + serFlow
				+ ", creatTime=" + creatTime + ", stopFlag=" + stopFlag + ", delFlag=" + delFlag + ", synchFlag="
				+ synchFlag + ", tenantId=" + tenantId + ", loginId=" + loginId + ", parentId=" + parentId + "]";
	}

	
    

}
