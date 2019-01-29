package com.sdp.servflow.pubandorder.serapplication.model;

import java.util.Date;

/**
 * Description:
 *
 * @author Niu Haoxuan
 * @date Created on 2017/11/10.
 */
public class SerApplicationBean {
    /**
     * 应用id
     */
    private String applicationId;

    /**
     * 应用名称
     */
    private String applicationName;

    /**
     * 创建人
     */
    private String creatUser;

    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 返回时间格式
     */
    private String createDateString;

    /**
     * 是否删除(0 正常 1删除)
     */
    private String delFlag;

    /**
     * 租户ID
     */
    private String tenantId;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
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

    public String getCreateDateString() {
        return createDateString;
    }

    public void setCreateDateString(String createDateString) {
        this.createDateString = createDateString;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
