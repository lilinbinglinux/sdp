package com.sdp.sqlModel.entity;

import java.util.Date;
import java.util.List;


/**
 * 分库
 */
public class SqlSubTreasury {
	/**
	 * 分库ID
	 */
    private String subTreasuryId;
    /**
	 * 形态ID
	 */
    private String shapeId;
    /**
     * 字段名称
     */
    private String fieldSqlName;
    /**
   	 * 	分库类型值（默认存储）
   	 */
   	private String typeValue1;
   	/**
   	 * 	分库类型值
   	 */
   	private String typeValue2;
	
	/**
	 *  分库类型（1 String 2 数值 3 date）
	 */
	private String subTreasuryType;
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
	
    private  List<SqlSubTreasuryDivision> sqlSubTreasuryDivision;
    
	public String getSubTreasuryId() {
		return subTreasuryId;
	}

	public void setSubTreasuryId(String subTreasuryId) {
		this.subTreasuryId = subTreasuryId;
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

	public String getSubTreasuryType() {
		return subTreasuryType;
	}

	public void setSubTreasuryType(String subTreasuryType) {
		this.subTreasuryType = subTreasuryType;
	}

	public List<SqlSubTreasuryDivision> getSqlSubTreasuryDivision() {
		return sqlSubTreasuryDivision;
	}

	public void setSqlSubTreasuryDivision(List<SqlSubTreasuryDivision> sqlSubTreasuryDivision) {
		this.sqlSubTreasuryDivision = sqlSubTreasuryDivision;
	}

	public String getFieldSqlName() {
		return fieldSqlName;
	}

	public void setFieldSqlName(String fieldSqlName) {
		this.fieldSqlName = fieldSqlName;
	}

	public String getTypeValue1() {
		return typeValue1;
	}

	public void setTypeValue1(String typeValue1) {
		this.typeValue1 = typeValue1;
	}

	public String getTypeValue2() {
		return typeValue2;
	}

	public void setTypeValue2(String typeValue2) {
		this.typeValue2 = typeValue2;
	}

			
}
