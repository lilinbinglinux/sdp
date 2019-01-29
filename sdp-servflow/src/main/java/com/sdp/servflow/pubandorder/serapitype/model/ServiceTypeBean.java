package com.sdp.servflow.pubandorder.serapitype.model;

import java.util.Date;

/**
 * Description:服务类型Bean
 *
 * @author 牛浩轩
 * @date Created on 2017/10/31.
 */
public class ServiceTypeBean {
    /**
     * 类型ID
     */
    private String serTypeId;

    /**
     * 类型名称
     */
    private String serTypeName;

    /**
     * 父ID
     */
    private String parentId;

    /**
     * id层级路径
     */
    private String idPath;

    /**
     * 名称层级路径
     */
    private String namePath;

    /**
     * 创建者
     */
    private String creatUser;

    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 返回时间
     */
    private String createDateString;

    /**
     * 是否删除（0正常 1删除）
     */
    private String delFlag;

    /**
     * 是否停用（0正常 1停用）
     */
    private String stopFlag;
    
    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 父节点名称
     */
    private String parentName;

    public String getSerTypeId() {
        return serTypeId;
    }

    public void setSerTypeId(String serTypeId) {
        this.serTypeId = serTypeId;
    }

    public String getSerTypeName() {
        return serTypeName;
    }

    public void setSerTypeName(String serTypeName) {
        this.serTypeName = serTypeName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCreatUser() {
        return creatUser;
    }

    public void setCreatUser(String creatUser) {
        this.creatUser = creatUser;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getStopFlag() {
        return stopFlag;
    }

    public void setStopFlag(String stopFlag) {
        this.stopFlag = stopFlag;
    }

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

    public String getIdPath() {
        return idPath;
    }

    public void setIdPath(String idPath) {
        this.idPath = idPath;
    }

    public String getNamePath() {
        return namePath;
    }

    public void setNamePath(String namePath) {
        this.namePath = namePath;
    }

    public String getCreateDateString() {
        return createDateString;
    }

    public void setCreateDateString(String createDateString) {
        this.createDateString = createDateString;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

//	@Override
//	public String toString() {
//		return "ServiceTypeBean [serTypeId=" + serTypeId + ", serTypeName=" + serTypeName + ", parentId=" + parentId
//				+ ", creatUser=" + creatUser + ", creatTime=" + creatTime + ", delFlag=" + delFlag + ", stopFlag="
//				+ stopFlag + "]";
//	}
    
    
}
