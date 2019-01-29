package com.sdp.sqlModel.entity;

import java.util.Date;

/**
 * 码表类型
 */
public class SqlCodeType {
	/**
     * 主键唯一标识，码表类型ID
     */
	private String codeTypeId;
	
    /**
     * 码表类型名称
     */
    private String codeTypeName;

    /**
     * 码表类型名称
     */
    private String parentId;
    
    /**
     * 是否sql(0否 1是)
     */
    private String sqlFlag;
    
    /**
     * sql语句
     */
    private String codeSql;
    
    /**
     * 是否公用(0否 1是)
     */
    private String pubFlag;
    
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

	public String getCodeTypeId() {
		return codeTypeId;
	}

	public void setCodeTypeId(String codeTypeId) {
		this.codeTypeId = codeTypeId;
	}

	public String getCodeTypeName() {
		return codeTypeName;
	}

	public void setCodeTypeName(String codeTypeName) {
		this.codeTypeName = codeTypeName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getSqlFlag() {
		return sqlFlag;
	}

	public void setSqlFlag(String sqlFlag) {
		this.sqlFlag = sqlFlag;
	}

	public String getCodeSql() {
		return codeSql;
	}

	public void setCodeSql(String codeSql) {
		this.codeSql = codeSql;
	}

	public String getPubFlag() {
		return pubFlag;
	}

	public void setPubFlag(String pubFlag) {
		this.pubFlag = pubFlag;
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
