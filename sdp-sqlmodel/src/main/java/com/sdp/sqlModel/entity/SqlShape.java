package com.sdp.sqlModel.entity;

import java.util.Date;
import java.util.List;


/**
 * 形态
 */
public class SqlShape {
	/**
	 * 形态ID
	 */
    private String shapeId;
	/**
	 * 	表ID
	 */
	private String tableId;
	
	/**
	 * 	字段类型(1 分区 2 分表 3 分库)
	 */
	private String shapeType;
	
	/**
	 *  说明
	 */
	private String shapeResume;
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
	 * 分区信息
	 */
    private String partitionValue;
    
    /**
     * 状态(0.自动  1.手动)
     */
    private String state;
   
	//分库
    private SqlSubTreasury sqlSubTreasury;
    //分表
    private SqlSubTable sqlSubTable;

	public String getShapeId() {
		return shapeId;
	}

	public void setShapeId(String shapeId) {
		this.shapeId = shapeId;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getShapeType() {
		return shapeType;
	}

	public void setShapeType(String shapeType) {
		this.shapeType = shapeType;
	}

	public String getShapeResume() {
		return shapeResume;
	}

	public void setShapeResume(String shapeResume) {
		this.shapeResume = shapeResume;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public SqlSubTreasury getSqlSubTreasury() {
		return sqlSubTreasury;
	}

	public void setSqlSubTreasury(SqlSubTreasury sqlSubTreasury) {
		this.sqlSubTreasury = sqlSubTreasury;
	}

	public SqlSubTable getSqlSubTable() {
		return sqlSubTable;
	}

	public void setSqlSubTable(SqlSubTable sqlSubTable) {
		this.sqlSubTable = sqlSubTable;
	}

	public String getPartitionValue() {
		return partitionValue;
	}

	public void setPartitionValue(String partitionValue) {
		this.partitionValue = partitionValue;
	}


}
