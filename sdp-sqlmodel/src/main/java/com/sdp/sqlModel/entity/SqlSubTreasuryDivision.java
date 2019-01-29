package com.sdp.sqlModel.entity;

import java.util.Date;


/**
 * 子库
 */
public class SqlSubTreasuryDivision {
	/**
	 * 子库ID
	 */
    private String subTreasuryDivisionId;
    /**
	 * 分库ID
	 */
    private String subTreasuryId;
    /**
	 * 数据源ID
	 */
    private String dataResId;
    /**
	 * 	分库类型值（默认存储）
	 */
	private String typeValue1;
	/**
	 * 	分库类型值
	 */
	private String typeValue2;

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

	public String getDataResId() {
		return dataResId;
	}

	public void setDataResId(String dataResId) {
		this.dataResId = dataResId;
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

	public String getSubTreasuryDivisionId() {
		return subTreasuryDivisionId;
	}

	public void setSubTreasuryDivisionId(String subTreasuryDivisionId) {
		this.subTreasuryDivisionId = subTreasuryDivisionId;
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

	public String getSubTreasuryId() {
		return subTreasuryId;
	}

	public void setSubTreasuryId(String subTreasuryId) {
		this.subTreasuryId = subTreasuryId;
	}

			
}
