package com.sdp.servflow.pubandorder.servicebasic.model;

import java.util.Date;

/**
 * Description: 服务版本Bean
 *
 * @author 牛浩轩
 * @date Created on 2017/11/8.
 */
public class ServiceVersionBean {
    /**
     * 版本ID
     */
    private String serVerId;

    /**
     * 版本代码
     */
    private String serVersion;

    /**
     * 服务ID
     */
    private String serId;

    /**
     * 服务协议(0 http 1 soap 2 socket)
     */
    private String serAgreement;

    /**
     * 请求参数格式(0 json 1 xml 2 text)
     */
    private String serRequest;

    /**
     * 响应参数格式(0 json 1 xml 2 text)
     */
    private String serResponse;

    /**
     * 请求方式(0 get 1 post)
     */
    private String serRestType;

    /**
     * 端口
     */
    private String serPoint;

    /**
     * 入参说明
     */
    private String inputResume;

    /**
     * 出参说明
     */
    private String outResume;

    /**
     * 编排流程
     */
    private String serFlow;

    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 读取时间，格式转化
     */
    private String createDateString;

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
     * 用户login_id
     */
    private String loginId;

    /**
     * 推送状态
     */
    private String pushState;

    public String getSerVersion() {
        return serVersion;
    }

    public void setSerVersion(String serVersion) {
        this.serVersion = serVersion;
    }

    public String getSerId() {
        return serId;
    }

    public void setSerId(String serId) {
        this.serId = serId;
    }

    public String getSerAgreement() {
        return serAgreement;
    }

    public void setSerAgreement(String serAgreement) {
        this.serAgreement = serAgreement;
    }

    public String getSerRequest() {
        return serRequest;
    }

    public void setSerRequest(String serRequest) {
        this.serRequest = serRequest;
    }

    public String getSerResponse() {
        return serResponse;
    }

    public void setSerResponse(String serResponse) {
        this.serResponse = serResponse;
    }

    public String getSerRestType() {
        return serRestType;
    }

    public void setSerRestType(String serRestType) {
        this.serRestType = serRestType;
    }

    public String getSerPoint() {
        return serPoint;
    }

    public void setSerPoint(String serPoint) {
        this.serPoint = serPoint;
    }

    public String getInputResume() {
        return inputResume;
    }

    public void setInputResume(String inputResume) {
        this.inputResume = inputResume;
    }

    public String getOutResume() {
        return outResume;
    }

    public void setOutResume(String outResume) {
        this.outResume = outResume;
    }

    public String getSerFlow() {
        return serFlow;
    }

    public void setSerFlow(String serFlow) {
        this.serFlow = serFlow;
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

    public String getSerVerId() {
        return serVerId;
    }

    public void setSerVerId(String serVerId) {
        this.serVerId = serVerId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPushState() {
        return pushState;
    }

    public void setPushState(String pushState) {
        this.pushState = pushState;
    }
}
