package com.sdp.sqlModel.entity;

/**
 * 数据源
 */
public class SqlDataRes{
	/**
	 * 数据源ID
	 */
    private String dataResId;

	/**
	 * 	数据源类型
	 */
	private String dataResTypeId;

	/**
	 * 	数据源名称
	 */
	private String dataResTypeName;
	/**
	 * 	数据库说明
	 */
	private String dataResDesc;
	/**
	 * 	数据源连接
	 */
	private String dataResUrl;
	/**
	 * 	数据源用户名
	 */
	private String username;
	/**
	 * 	数据源密码
	 */
	private String password;
	/**
	 * 	是否默认数据源
	 */
	private String isDefault;
	/**
	 * 	状态
	 */
	private String dataStatus;
	
	/**
	 * 	序号
	 */
	private Integer sortNum;
	/**
	 * 备注
	 */
	private String resume;
	
	/**
     * 租户ID
     */
    private String tenantId;
    
	 /**
     * Schema名称
     */
    private String tableSchema;
    

	public String getTableSchema() {
		return tableSchema;
	}

	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}
    
	public String getDataResId() {
		return dataResId;
	}
	public void setDataResId(String dataResId) {
		this.dataResId = dataResId;
	}
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
	public String getDataResDesc() {
		return dataResDesc;
	}
	public void setDataResDesc(String dataResDesc) {
		this.dataResDesc = dataResDesc;
	}
	public String getDataResUrl() {
		return dataResUrl;
	}
	public void setDataResUrl(String dataResUrl) {
		this.dataResUrl = dataResUrl;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	public String getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	
}
