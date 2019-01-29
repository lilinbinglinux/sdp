package com.sdp.servflow.pubandorder.servicebasic.model;

import java.util.Date;

/**
 * Description: 服务表的Bean
 *
 * @author 牛浩轩
 * @date Created on 2017/11/8.
 */
public class ServiceMainBean {
    /**
     * 服务ID
     */
    private String serId;

    /**
     * 服务名称
     */
    private String serName;

    /**
     * 服务类型
     */
    private String serType;

    /**
     * 服务编码
     */
    private String serCode;

    /**
     * 最新版本
     */
    private String serVersion;

    /**
     * url地址
     */
    private String serPath;

    /**
     * 接口描述
     */
    private String serResume;

    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 读取时间格式转化
     */
    private String createDateString;

    /**
     * 同步、异步（0同步 1异步）
     */
    private String synchFlag;

    /**
     * 是否删除(0 正常 1删除)
     */
    private String delFlag;

    /**
     * 是否停用(0 正常 1删除)
     */
    private String stopFlag;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 用户ID
     */
    private String loginId;

    /**
     * 拼接url
     */
    private String url;

    /**
     * 服务类型名称
     */
    private String serTypeName;

    /**
     * 服务协议
     */
    private String serAgreement;

    /**
     * 异步订阅url(http,无参数版)
     */
    private String baseHttpUrl;

    /**
     * 异步订阅webservice(无参数版)
     */
    private String baseWebServiceUrl;

    public String getSerId() {
        return serId;
    }

    public void setSerId(String serId) {
        this.serId = serId;
    }

    public String getSerName() {
        return serName;
    }

    public void setSerName(String serName) {
        this.serName = serName;
    }

    public String getSerType() {
        return serType;
    }

    public void setSerType(String serType) {
        this.serType = serType;
    }

    public String getSerCode() {
        return serCode;
    }

    public void setSerCode(String serCode) {
        this.serCode = serCode;
    }

    public String getSerVersion() {
        return serVersion;
    }

    public void setSerVersion(String serVersion) {
        this.serVersion = serVersion;
    }

    public String getSerPath() {
        return serPath;
    }

    public void setSerPath(String serPath) {
        this.serPath = serPath;
    }

    public String getSerResume() {
        return serResume;
    }

    public void setSerResume(String serResume) {
        this.serResume = serResume;
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

    public String getSynchFlag() {
        return synchFlag;
    }

    public void setSynchFlag(String synchFlag) {
        this.synchFlag = synchFlag;
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

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSerTypeName() {
        return serTypeName;
    }

    public void setSerTypeName(String serTypeName) {
        this.serTypeName = serTypeName;
    }

    public String getSerAgreement() {
        return serAgreement;
    }

    public void setSerAgreement(String serAgreement) {
        this.serAgreement = serAgreement;
    }

    public String getBaseHttpUrl() {
        return baseHttpUrl;
    }

    public void setBaseHttpUrl(String baseHttpUrl) {
        this.baseHttpUrl = baseHttpUrl;
    }

    public String getBaseWebServiceUrl() {
        return baseWebServiceUrl;
    }

    public void setBaseWebServiceUrl(String baseWebServiceUrl) {
        this.baseWebServiceUrl = baseWebServiceUrl;
    }
}
