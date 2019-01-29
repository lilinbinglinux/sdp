package com.sdp.sqlModel.entity;

import java.util.Date;
import java.util.List;


/**
 * 分表
 */
public class SqlSubTable {
	/**
	 * 分表ID
	 */
    private String subTableId;

    /**
	 * 形态ID
	 */
    private String shapeId;
    /**
     * 字段名称
     */
    private String fieldSqlName;
	
	/**
	 * 	分表类型(1 date 2 string(截取长度) 3 数值(100-1，200-2) 4 自定义名称)
	 */
	private String subTableType;
	/**
	 * 	分表类型值（默认存储）
	 */
	private String subTableTypeValue1;
	/**
	 * 	分表类型值
	 */
	private String subTableTypeValue2;
	/**
	 * 1:date 2:15710069561 3:100
	 * 	横向分表自定义表名称(tableSqlName_date2018,tableSqlName_string1,tableSqlName_num1)
	 */
//	private String customValue;
	
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
	
	public String getSubTableId() {
		return subTableId;
	}

	public void setSubTableId(String subTableId) {
		this.subTableId = subTableId;
	}

	public String getShapeId() {
		return shapeId;
	}

	public void setShapeId(String shapeId) {
		this.shapeId = shapeId;
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

//	public String getCustomValue() {
//		return customValue;
//	}
//
//	public void setCustomValue(String customValue) {
//		this.customValue = customValue;
//	}

	public String getFieldSqlName() {
		return fieldSqlName;
	}

	public void setFieldSqlName(String fieldSqlName) {
		this.fieldSqlName = fieldSqlName;
	}

	public String getSubTableType() {
		return subTableType;
	}

	public void setSubTableType(String subTableType) {
		this.subTableType = subTableType;
	}

	public String getSubTableTypeValue1() {
		return subTableTypeValue1;
	}

	public void setSubTableTypeValue1(String subTableTypeValue1) {
		this.subTableTypeValue1 = subTableTypeValue1;
	}

	public String getSubTableTypeValue2() {
		return subTableTypeValue2;
	}

	public void setSubTableTypeValue2(String subTableTypeValue2) {
		this.subTableTypeValue2 = subTableTypeValue2;
	}

			
}
