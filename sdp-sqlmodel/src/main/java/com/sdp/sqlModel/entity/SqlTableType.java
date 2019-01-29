package com.sdp.sqlModel.entity;

import java.util.Date;


/**
 * 数据源
 */
public class SqlTableType {
	/**
	 * 类型ID
	 */
    private String tableTypeId;

	/**
	 * 	类型名称
	 */
	private String tableTypeName;
	/**
	 * 	父类型
	 */
	private String parentId;
	
	/**
	 * 	类型路径
	 */
	private String tableTypePath;
    
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
	 * 	数据源ID
	 */
	private String dataResId;
	
	public String getTableTypeId() {
		return tableTypeId;
	}

	public void setTableTypeId(String tableTypeId) {
		this.tableTypeId = tableTypeId;
	}

	public String getTableTypeName() {
		return tableTypeName;
	}

	public void setTableTypeName(String tableTypeName) {
		this.tableTypeName = tableTypeName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getTableTypePath() {
		return tableTypePath;
	}

	public void setTableTypePath(String tableTypePath) {
		this.tableTypePath = tableTypePath;
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

	public String getDataResId() {
		return dataResId;
	}

	public void setDataResId(String dataResId) {
		this.dataResId = dataResId;
	}
	
}
