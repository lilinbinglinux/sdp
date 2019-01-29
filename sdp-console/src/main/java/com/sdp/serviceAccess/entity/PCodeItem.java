package com.sdp.serviceAccess.entity;

import java.util.Date;

public class PCodeItem {

    private String itemId;

    private String itemName;

    private String itemCode;

    private String itemResume;

    private String setId;

    private String parentId;

    private String typePath;

    private String typeStatus;

    private Integer sortId;

    private Date createDate;

    private String createBy;

    private String tenantId;

    public void setItemId(String itemId) { this.itemId = itemId; }

    public void setItemName(String itemName) { this.itemName = itemName; }

    public void setItemCode(String itemCode) { this.itemCode = itemCode; }

    public void setItemResume(String itemResume) { this.itemResume = itemResume; }

    public void setSetId(String setId) { this.setId = setId; }

    public void setParentId(String parentId) { this.parentId = parentId; }

    public void setTypePath(String typePath) { this.typePath = typePath; }

    public void setTypeStatus(String typeStatus) { this.typeStatus = typeStatus; }

    public void setSortId(Integer sortId) { this.sortId = sortId; }

    public void setCreateDate(Date createDate) { this.createDate = createDate; }

    public void setCreateBy(String createBy) { this.createBy = createBy; }

    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getItemId() { return itemId; }

    public String getItemName() { return itemName; }

    public String getItemCode() { return itemCode; }

    public String getItemResume() { return itemResume; }

    public String getSetId() { return setId; }

    public String getParentId() { return parentId; }

    public String getTypePath() { return typePath; }

    public String getTypeStatus() { return typeStatus; }

    public Integer getSortId() { return sortId; }

    public Date getCreateDate() { return createDate; }

    public String getCreateBy() { return createBy; }

    public String getTenantId() { return tenantId; }

}
