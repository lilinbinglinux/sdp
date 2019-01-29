package com.sdp.servflow.pubandorder.order.model;

import java.util.Date;

/**
 * 
 * 接口订阅模块的bean
 *
 * @author 牛浩轩
 * @version 2017年6月12日
 * @see InterfaceOrderBean
 * @since
 */
public class InterfaceOrderBean {
    /**
     * 主键编号id
     */
    private String orderid;
    
    /**
     * 订阅名称
     */
    private String name;
    
    /**
     * 编码格式
     */
    private String ordercode;
    
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
     * 所需响应参数格式
     */
    private String respformat;
    
    /**
     * 说明
     */
    private String orderdesc;
    
    /**
     * 创建日期
     */
    private Date createdate;
    
    /**
     * 租户id
     */
    private String tenant_id;
    
    /**
     * 注册服务id
     */
    private String pubapiId;
    
    /**
     * 注册服务名称
     */
    private String pubname;
    
    /**
     * 登录id
     */
    private String login_id;
	
    /**
     * 被限制的ip
     */
    private String limitIp;
    
    /**
     * 申请服务的appId
     */
    private String appId;
    
    /**
     * 申请接口为0，订阅接口为1
     */
    private String type;
    
    /**
     * 审核状态(未审批000，审批中001，审批通过100，审批不通过999)
     */
    private String checkstatus;
    
    private String token_id;
    
    /**
     * api类别编码（api为空,docker为002,openstack为003）
     */
    private String stylecode;
    
    public String getOrderid() {
    	return orderid;
    }
    public void setOrderid(String orderid) {
    	this.orderid = orderid;
    }
    public String getName() {
    	return name;
    }
    public void setName(String name) {
    	this.name = name;
    }
    public String getOrdercode() {
    	return ordercode;
    }
    public void setOrdercode(String ordercode) {
    	this.ordercode = ordercode;
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
    public String getOrderdesc() {
    	return orderdesc;
    }
    public void setOrderdesc(String orderdesc) {
    	this.orderdesc = orderdesc;
    }
    public Date getCreatedate() {
    	return createdate;
    }
    public void setCreatedate(Date createdate) {
    	this.createdate = createdate;
    }
    public String getTenant_id() {
        return tenant_id;
    }
    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }
    public String getPubapiId() {
    	return pubapiId;
    }
    public void setPubapiId(String pubapiId) {
    	this.pubapiId = pubapiId;
    }
    public String getPubname() {
        return pubname;
    }
    public void setPubname(String pubname) {
        this.pubname = pubname;
    }
    public String getLogin_id() {
        return login_id;
    }
    public void setLogin_id(String login_id) {
        this.login_id = login_id;
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
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getCheckstatus() {
        return checkstatus;
    }
    public void setCheckstatus(String checkstatus) {
        this.checkstatus = checkstatus;
    }
    public String getToken_id() {
        return token_id;
    }
    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }
    public String getStylecode() {
        return stylecode;
    }
    public void setStylecode(String stylecode) {
        this.stylecode = stylecode;
    }
    
}
