package com.sdp.sqlModel.entity;

import java.util.Date;
import java.util.List;


/**
 * 数据源
 */
public class SqlTable{
	/**
	 * 表ID
	 */
    private String tableId;

	/**
	 * 	表名称
	 */
	private String tableName;
	/**
	 * 	表英文名称
	 */
	private String tableSqlName;
	/**
	 * 	数据源ID
	 */
	private String dataResId;
	
	/**
	 * 	表类型ID
	 */
	private String tableTypeId;
	/**
	 * 	是否主表
	 */
	private String tableMainFlag;
	/**
	 * 	说明
	 */
	private String tableResume;
	/**
	 * 	Y轴
	 */
	private float tableY;
	/**
	 * 	X轴
	 */
	private float tableX;
	
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
     * 高
     */
    private float tableHight;
    /**
     * 宽
     */
    private float tableWidth;
	
	/**
	 * 	序号
	 */
	
	private Integer sortId;
	
	private List<SqlField> sqlFieldes;
	
	private List<SqlShape> sqlShape;

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableSqlName() {
		return tableSqlName;
	}

	public void setTableSqlName(String tableSqlName) {
		this.tableSqlName = tableSqlName;
	}

	public String getDataResId() {
		return dataResId;
	}

	public void setDataResId(String dataResId) {
		this.dataResId = dataResId;
	}

	public String getTableTypeId() {
		return tableTypeId;
	}

	public void setTableTypeId(String tableTypeId) {
		this.tableTypeId = tableTypeId;
	}

	public String getTableMainFlag() {
		return tableMainFlag;
	}

	public void setTableMainFlag(String tableMainFlag) {
		this.tableMainFlag = tableMainFlag;
	}

	public String getTableResume() {
		return tableResume;
	}

	public void setTableResume(String tableResume) {
		this.tableResume = tableResume;
	}

	public float getTableY() {
		return tableY;
	}

	public void setTableY(float tableY) {
		this.tableY = tableY;
	}

	public float getTableX() {
		return tableX;
	}

	public void setTableX(float tableX) {
		this.tableX = tableX;
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

	public List<SqlField> getSqlFieldes() {
		return sqlFieldes;
	}

	public void setSqlFieldes(List<SqlField> sqlFieldes) {
		this.sqlFieldes = sqlFieldes;
	}

	public List<SqlShape> getSqlShape() {
		return sqlShape;
	}

	public void setSqlShape(List<SqlShape> sqlShape) {
		this.sqlShape = sqlShape;
	}

	public float getTableHight() {
		return tableHight;
	}

	public void setTableHight(float tableHight) {
		this.tableHight = tableHight;
	}

	public float getTableWidth() {
		return tableWidth;
	}

	public void setTableWidth(float tableWidth) {
		this.tableWidth = tableWidth;
	}
	
}
