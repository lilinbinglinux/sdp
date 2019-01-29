package com.sdp.sqlModel.entity;

import java.util.Date;

/**
 * 项目模块表
 */
public class ProModel {

	/**
     * 项目模块ID
     */
	private String modelId;
	
	/**
     * 项目模块名称
     */
	private String modelName;
	
	/**
     * 父类型
     */
	private String parentId;
	
	/**
     * 类型（0 项目  1模块 2 数据接口 3 服务 4页面）
     */
	private String modelFlag;
	
	/**
     * 类型路径
     */
	private String modelTypePath;
	
	/**
     * 说明
     */ 
	private String resume;
	
	/**
     * 序号
     */
	private Integer sortId;
	
	/**
     * 创建时间
     */
	private Date createDate;
	
	/**
     * 创建人
     */
	private String createBy;
	
	/**
     * 租户ID
     */
	private String tenantId;

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getModelFlag() {
		return modelFlag;
	}

	public void setModelFlag(String modelFlag) {
		this.modelFlag = modelFlag;
	}

	public String getModelTypePath() {
		return modelTypePath;
	}

	public void setModelTypePath(String modelTypePath) {
		this.modelTypePath = modelTypePath;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}
	
	public Integer getSortId() {
		return sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}
