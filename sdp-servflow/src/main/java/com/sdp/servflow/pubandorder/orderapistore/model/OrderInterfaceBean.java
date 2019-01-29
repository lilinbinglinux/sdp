package com.sdp.servflow.pubandorder.orderapistore.model;

import java.util.Date;

import com.sdp.servflow.pubandorder.serapitype.model.ServiceTypeBean;
import com.sdp.servflow.pubandorder.servicebasic.model.ServiceMainBean;
import com.sdp.servflow.pubandorder.servicebasic.model.ServiceVersionBean;

/**
 * Description: 服务订阅Bean
 *
 * @author Niu Haoxuan
 * @date Created on 2017/11/15.
 */
public class OrderInterfaceBean {
    /**
     * 订阅服务ID
     */
    private String orderId;

    /**
     * 订阅名称
     */
    private String orderName;

    /**
     * 订阅服务
     */
    private String orderSerName;

    /**
     * 订阅编码
     */
    private String orderCode;

    /**
     * 所需请求协议
     */
    private String protocal;

    /**
     * 服务地址
     */
    private String url;

    /**
     * 提供的请求参数格式
     */
    private String reqformat;

    /**
     * 所需响应的参数格式
     */
    private String respformat;

    /**
     * 应用ID
     */
    private String applicationId;

    /**
     * 说明
     */
    private String orderDesc;

    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 创建日期
     */
    private String createDateString;

    /**
     * 服务ID
     */
    private String serId;

    /**
     * 版本ID
     */
    private String serVersion;

    /**
     * 是否限制ip访问（0不需要限制ip，1限制ip）
     */
    private String limitIp;

    /**
     * 服务编码
     */
    private String appId;

    /**
     * 访问频率
     */
    private Integer accFreq;

    /**
     * 访问频率时间类型（0.秒 1.分钟 2.小时 3.天）
     */
    private String accFreqType;

    /**
     * 订阅用户login_id
     */
    private String loginId;

    /**
     * 订阅接口描述
     */
    private String appResume;

    /**
     * 是否代订阅(0否 1是)
     */
    private String repFlag;

    /**
     * 代订阅人
     */
    private String repUserId;

    /**
     * 审核状态
     */
    private String checkStatus;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 订阅次数统计
     */
    private String countTime;

    /**
     * 服务表查询映射字段
     */
    private ServiceMainBean serviceMain;

    /**
     * 服务版本表映射字段
     */
    private ServiceVersionBean serviceVersionBean;

    /**
     * 服务类型查询
     */
    private ServiceTypeBean serviceType;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getProtocal() {
        return protocal;
    }

    public void setProtocal(String protocal) {
        this.protocal = protocal;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReqformat() {
        return reqformat;
    }

    public void setReqformat(String reqformat) {
        this.reqformat = reqformat;
    }

    public String getRespformat() {
        return respformat;
    }

    public void setRespformat(String respformat) {
        this.respformat = respformat;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getSerId() {
        return serId;
    }

    public void setSerId(String serId) {
        this.serId = serId;
    }

    public String getSerVersion() {
        return serVersion;
    }

    public void setSerVersion(String serVersion) {
        this.serVersion = serVersion;
    }

    public String getLimitIp() {
        return limitIp;
    }

    public void setLimitIp(String limitIp) {
        this.limitIp = limitIp;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getAccFreq() {
        return accFreq;
    }

    public void setAccFreq(Integer accFreq) {
        this.accFreq = accFreq;
    }

    public String getAccFreqType() {
        return accFreqType;
    }

    public void setAccFreqType(String accFreqType) {
        this.accFreqType = accFreqType;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getAppResume() {
        return appResume;
    }

    public void setAppResume(String appResume) {
        this.appResume = appResume;
    }

    public String getRepFlag() {
        return repFlag;
    }

    public void setRepFlag(String repFlag) {
        this.repFlag = repFlag;
    }

    public String getRepUserId() {
        return repUserId;
    }

    public void setRepUserId(String repUserId) {
        this.repUserId = repUserId;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getCountTime() {
        return countTime;
    }

    public void setCountTime(String countTime) {
        this.countTime = countTime;
    }

    public ServiceMainBean getServiceMain() {
        return serviceMain;
    }

    public void setServiceMain(ServiceMainBean serviceMain) {
        this.serviceMain = serviceMain;
    }

    public ServiceVersionBean getServiceVersionBean() {
        return serviceVersionBean;
    }

    public void setServiceVersionBean(ServiceVersionBean serviceVersionBean) {
        this.serviceVersionBean = serviceVersionBean;
    }

    public ServiceTypeBean getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceTypeBean serviceType) {
        this.serviceType = serviceType;
    }

    public String getCreateDateString() {
        return createDateString;
    }

    public void setCreateDateString(String createDateString) {
        this.createDateString = createDateString;
    }

    public String getOrderSerName() {
        return orderSerName;
    }

    public void setOrderSerName(String orderSerName) {
        this.orderSerName = orderSerName;
    }
}
