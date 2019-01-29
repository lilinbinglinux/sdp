package com.sdp.sqlModel.entity;

/**
 * 数据源
 */
public class SqlTableForeignkey {
	/**
	 * 外键ID
	 */
    private String foreignKeyId;

	/**
	 * 	主表ID
	 */
	private String mainTable;
	/**
	 * 	主字段
	 */
	private String mainField;
	/**
	 * 	外表ID
	 */
	private String joinTable;
	
	/**
	 * 	关联字段
	 */
	private String joinField;
	/**
	 * 	关联字段
	 */
	private String lineStart;
	/**
	 * 	关联字段
	 */
	private String lineEnd;
	
	/**
	 * 	租户ID
	 */
	private String tenantId;
	/**
	 * 	Y轴
	 */
	private float foreignY;
	/**
	 * 	X轴
	 */
	private float foreignX;
	
	public String getForeignKeyId() {
		return foreignKeyId;
	}

	public void setForeignKeyId(String foreignKeyId) {
		this.foreignKeyId = foreignKeyId;
	}

	public String getMainTable() {
		return mainTable;
	}

	public void setMainTable(String mainTable) {
		this.mainTable = mainTable;
	}

	public String getMainField() {
		return mainField;
	}

	public void setMainField(String mainField) {
		this.mainField = mainField;
	}

	public String getJoinTable() {
		return joinTable;
	}

	public void setJoinTable(String joinTable) {
		this.joinTable = joinTable;
	}

	public String getJoinField() {
		return joinField;
	}

	public void setJoinField(String joinField) {
		this.joinField = joinField;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getLineStart() {
		return lineStart;
	}

	public void setLineStart(String lineStart) {
		this.lineStart = lineStart;
	}

	public String getLineEnd() {
		return lineEnd;
	}

	public void setLineEnd(String lineEnd) {
		this.lineEnd = lineEnd;
	}

	public float getForeignY() {
		return foreignY;
	}

	public void setForeignY(float foreignY) {
		this.foreignY = foreignY;
	}

	public float getForeignX() {
		return foreignX;
	}

	public void setForeignX(float foreignX) {
		this.foreignX = foreignX;
	}
	
}
