package com.sdp.servflow.pubandorder.serapitype.model;

import java.util.Date;

/**
 * description: ServiceApiTypeBean类
 *
 * @author niu
 * @date Created on 2017/10/24.
 */
public class ServiceApiTypeBean {
    /**
     * 类型ID
     */
    private String apiTypeId;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 父ID
     */
    private String parentId;

    /**
     * 是否共享（0否 1是）
     */
    private String shareFlag;

    /**
     * 创建者
     */
    private String creatUser;

    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 是否删除（0正常 1删除）
     */
    private String delFlag;

    /**
     * 是否停用（0正常 1停用）
     */
    private String stopFlag;

    public String getApiTypeId() {
        return apiTypeId;
    }

    public void setApiTypeId(String apiTypeId) {
        this.apiTypeId = apiTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getShareFlag() {
        return shareFlag;
    }

    public void setShareFlag(String shareFlag) {
        this.shareFlag = shareFlag;
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

//	@Override
//	public String toString() {
//		return "ServiceApiTypeBean [apiTypeId=" + apiTypeId + ", typeName=" + typeName + ", parentId=" + parentId
//				+ ", shareFlag=" + shareFlag + ", creatUser=" + creatUser + ", creatTime=" + creatTime + ", delFlag="
//				+ delFlag + ", stopFlag=" + stopFlag + "]";
//	}
    
    
}
