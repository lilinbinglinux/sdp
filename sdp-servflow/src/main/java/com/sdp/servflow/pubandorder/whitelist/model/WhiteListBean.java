package com.sdp.servflow.pubandorder.whitelist.model;

import java.util.Date;

/**
 * Description:
 *
 * @author Niu Haoxuan
 * @date Created on 2017/11/28.
 */
public class WhiteListBean {
    /**
     * id
     */
    private String ipID;

    /**
     * appId
     */
    private String appId;

    /**
     * 订阅服务id
     */
    private String orderId;

    /**
     * IP地址
     */
    private String ipAddr;

    /**
     * 名单类型
     */
    private String nameType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 租户id
     */
    private String tenantId;

    public String getIpID() {
        return ipID;
    }

    public void setIpID(String ipID) {
        this.ipID = ipID;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
