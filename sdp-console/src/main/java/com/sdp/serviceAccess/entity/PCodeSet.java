package com.sdp.serviceAccess.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class PCodeSet {
	
	private String setId;

	private String setName;
	
	private String parentId;
	
	private String typePath;
	
	private String typeStatus;

	private Integer sortId;

	private Date createDate;

	private String createBy;

	private String tenantId;
	
	public void setId(String setId) {
		this.setId = setId;
	}
	
	public void setName(String setName) {
		this.setName = setName;
	}
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public void setTypePath(String typePath) {
		this.typePath = typePath;
	}
	
	public void setTypeStatus(String typeStatus) {
		this.typeStatus = typeStatus;
	}

	public void setSortId(Integer sortId) { this.sortId = sortId; }

	public void setCreateDate(Date createDate) { this.createDate = createDate; }

	public void setCreateBy(String createBy) { this.createBy = createBy; }

	public void setTenantId(String tenantId) { this.tenantId = tenantId; }
	
	public String getSetId() { return setId; }
	
	public String getSetName() {
		return setName;
	}
	
	public String getParentId() {
		return parentId;
	}
	
	public String getTypePath() {
		return typePath;
	}
	
	public String getTypeStatus() {
		return typeStatus;
	}

	public Integer getSortId() { return sortId; }

	public Date getCreateDate() { return createDate; }

	public String getCreateBy() { return createBy; }

	public String getTenantId() { return tenantId; }

//	public List<Map<String,Object>>
}
