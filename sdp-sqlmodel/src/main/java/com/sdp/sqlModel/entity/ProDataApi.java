package com.sdp.sqlModel.entity;

import java.util.Date;

/**
 * 数据接口表
 */
public class ProDataApi {
	/**
     * 数据接口ID
     */
	private String dataApiId;
	
	/**
     * 数据接口名称
     */
	private String dataApiName;
	
	/**
     * 数据源ID
     */
	private String dataResId;
	
	/**
     * 项目模块ID
     */
	private String modelId;
	
	/**
     * 输入参数
     */
	private String inputAttr;

	/**
     * 返回参数
     */
	private String outputAttr;
	
	/**
	 * 返回参数
	 */
	private String outputSample;
	
	/**
     * sql语句
     */
	private String sqlText;
	
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

	public String getDataApiId() {
		return dataApiId;
	}

	public void setDataApiId(String dataApiId) {
		this.dataApiId = dataApiId;
	}

	public String getDataApiName() {
		return dataApiName;
	}

	public void setDataApiName(String dataApiName) {
		this.dataApiName = dataApiName;
	}

	public String getDataResId() {
		return dataResId;
	}

	public void setDataResId(String dataResId) {
		this.dataResId = dataResId;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getInputAttr() {
		return inputAttr;
	}

	public void setInputAttr(String inputAttr) {
		this.inputAttr = inputAttr;
	}

	public String getOutputAttr() {
		return outputAttr;
	}

	public void setOutputAttr(String outputAttr) {
		this.outputAttr = outputAttr;
	}

	public String getSqlText() {
		return sqlText;
	}

	public void setSqlText(String sqlText) {
		this.sqlText = sqlText;
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

	public String getOutputSample() {
		return outputSample;
	}

	public void setOutputSample(String outputSample) {
		this.outputSample = outputSample;
	}
	
}
