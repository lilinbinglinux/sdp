package com.sdp.sqlModel.entity;

/**
 * 数据源类型表
 */
public class SqlDataResType {
	/**
     * 主键唯一标识，数据源类型ID
     */
	private String dataResTypeId;
	
    /**
     * 数据源类型名称
     */
    private String dataResTypeName;
	
    /**
     * 数据源类型分类(2 oracle 1 mysql 3 DB2 5 sqlserver)
     */
    private String resType;
	
    /**
     * 数据源驱动
     */
    private String dataDriver;
    
    /**
     * 说明
     */
    private String resume;
    
    /**
     * 状态(0启用 1停用)
     */
    private String dataStutas;
    
    /**
     * 序号
     */
    private Integer sortNum;
    
    /**
     * 租户ID
     */
    private String tenantId;

	public String getDataResTypeId() {
		return dataResTypeId;
	}

	public void setDataResTypeId(String dataResTypeId) {
		this.dataResTypeId = dataResTypeId;
	}

	public String getDataResTypeName() {
		return dataResTypeName;
	}

	public void setDataResTypeName(String dataResTypeName) {
		this.dataResTypeName = dataResTypeName;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getDataDriver() {
		return dataDriver;
	}

	public void setDataDriver(String dataDriver) {
		this.dataDriver = dataDriver;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getDataStutas() {
		return dataStutas;
	}

	public void setDataStutas(String dataStutas) {
		this.dataStutas = dataStutas;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
    
    
    
    
    
    
    
    
    
    
    
    
	
}
