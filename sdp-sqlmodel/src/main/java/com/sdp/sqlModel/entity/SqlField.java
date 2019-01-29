package com.sdp.sqlModel.entity;

import java.util.Date;


/**
 * 数据源
 */
public class SqlField {
	/**
	 * 字段ID
	 */
    private String fieldId;

	/**
	 * 	字段名称
	 */
	private String fieldName;
	/**
	 * 	字段英文名称
	 */
	private String fieldSqlName;
	/**
	 * 	表ID
	 */
	private String tableId;
	
	/**
	 * 	字段类型(1 数值型 2 字符串型 3 日期型 3 日期型 4 大字段型 5 图片字段型)
	 */
	private String fieldType;
	
	/**
	 * 	字段类型(字段实际类型)
	 */
	private String dateType;
	
	/**
	 * 	字段长度
	 */
	private Integer fieldLen;
	/**
	 * 	小数点位数
	 */
	private Integer fieldDigit;
	/**
	 * 	是否主键（0否 1是）
	 */
	private String fieldKey;
	/**
	 * 	是否索引（0否 1是）
	 */
	private String sortIndex;
	/**
	 * 	是否为空（0否 1是）
	 */
	private String isNull;
	/**
	 * 	码表ID
	 */
	private String codesetid;
	/**
	 *  说明
	 */
	private String fieldResume;
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
	
	/**
	 * 	序号
	 */
	
	private Integer sortId;
	
	 /**
     * 主键约束名称
     */
    private String primaryConstraint;
    
	
	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getPrimaryConstraint() {
		return primaryConstraint;
	}

	public void setPrimaryConstraint(String primaryConstraint) {
		this.primaryConstraint = primaryConstraint;
	}
	
	public String getFieldId() {
		return fieldId;
	}
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldSqlName() {
		return fieldSqlName;
	}
	public void setFieldSqlName(String fieldSqlName) {
		this.fieldSqlName = fieldSqlName;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public Integer getFieldLen() {
		return fieldLen;
	}
	public void setFieldLen(Integer fieldLen) {
		this.fieldLen = fieldLen;
	}
	public Integer getFieldDigit() {
		return fieldDigit;
	}
	public void setFieldDigit(Integer fieldDigit) {
		this.fieldDigit = fieldDigit;
	}

	public String getFieldKey() {
		return fieldKey;
	}
	public void setFieldKey(String fieldKey) {
		this.fieldKey = fieldKey;
	}
	public String getSortIndex() {
		return sortIndex;
	}
	public void setSortIndex(String sortIndex) {
		this.sortIndex = sortIndex;
	}
	
	
	public String getCodesetid() {
		return codesetid;
	}
	public void setCodesetid(String codesetid) {
		this.codesetid = codesetid;
	}
	public String getFieldResume() {
		return fieldResume;
	}
	public void setFieldResume(String fieldResume) {
		this.fieldResume = fieldResume;
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

	public String getIsNull() {
		return isNull;
	}

	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}
	
	
			
}
