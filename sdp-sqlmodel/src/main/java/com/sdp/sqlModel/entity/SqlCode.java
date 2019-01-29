package com.sdp.sqlModel.entity;

import java.util.Date;

/**
 * 码表
 */
public class SqlCode {
	/**
     * 主键唯一标识，码值ID
     */
	private String codeId;
	
	/**
     * 码值编码
     */
    private String codeItem;
    
	/**
     * 码值名称
     */
    private String codeName;
    
	/**
     * 码表类型ID
     */
    private String codeTypeId;
    
	/**
     * 码值父ID
     */
    private String parentId;
    
	/**
     * 码值说明
     */
    private String codeResume;
    
	/**
     * 码值路径
     */
    private String codePath;
    
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

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getCodeItem() {
		return codeItem;
	}

	public void setCodeItem(String codeItem) {
		this.codeItem = codeItem;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getCodeTypeId() {
		return codeTypeId;
	}

	public void setCodeTypeId(String codeTypeId) {
		this.codeTypeId = codeTypeId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCodeResume() {
		return codeResume;
	}

	public void setCodeResume(String codeResume) {
		this.codeResume = codeResume;
	}

	public String getCodePath() {
		return codePath;
	}

	public void setCodePath(String codePath) {
		this.codePath = codePath;
	}

	public int getSortId() {
		return sortId;
	}

	public void setSortId(int sortId) {
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
