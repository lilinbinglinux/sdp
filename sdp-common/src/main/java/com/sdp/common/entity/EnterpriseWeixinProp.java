/*
 * 文件名：EnterWeixinProp.java
 * 版权：Copyright by www.sdp.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月19日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.common.entity;

import java.io.Serializable;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 企业微信配置信息类
 * @author zhangyunzhen
 * @version 2017年7月19日
 * @see EnterpriseWeixinProp
 * @since
 */
@ConfigurationProperties(prefix = "weixin.enterprise")
public class EnterpriseWeixinProp implements Serializable {

    /**
     * 用户凭证id
     */
    private String corpid;

    /**
     * 通讯录用户凭证密钥
     */
    private String corpsecretAddresslist;

    /**
     * 能力开放平台用户凭证密钥
     */
    private String corpsecretCopc;

    /**
     * copc企业应用的id
     */
    private Integer agentidCopc;

    /**
     * 获取access_token的url
     */
    private String accessTokenUrl;

    /**
     * 获取部门列表url
     */
    private String departmentlistUrl;

    /**
     * 发送消息url
     */
    private String messageSendUrl;

    /**
     * 获取部门成员url
     */
    private String userSimplelistUrl;
    
    /**
     *获取部门成员详情url 
     */
    private String userDetaillistUrl;
    /**
     * 根据用户id获取用户详情
     */
    private String userDetailUrl;
    
    /**
     * 企业微信域名
     */
    private String host;
    
    
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getCorpid() {
        return corpid;
    }

    public void setCorpid(String corpid) {
        this.corpid = corpid;
    }

    public String getCorpsecretAddresslist() {
        return corpsecretAddresslist;
    }

    public void setCorpsecretAddresslist(String corpsecretAddresslist) {
        this.corpsecretAddresslist = corpsecretAddresslist;
    }

    public String getCorpsecretCopc() {
        return corpsecretCopc;
    }

    public void setCorpsecretCopc(String corpsecretCopc) {
        this.corpsecretCopc = corpsecretCopc;
    }

    public Integer getAgentidCopc() {
        return agentidCopc;
    }

    public void setAgentidCopc(Integer agentidCopc) {
        this.agentidCopc = agentidCopc;
    }

    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public String getDepartmentlistUrl() {
        return departmentlistUrl;
    }

    public void setDepartmentlistUrl(String departmentlistUrl) {
        this.departmentlistUrl = departmentlistUrl;
    }

    public String getMessageSendUrl() {
        return messageSendUrl;
    }

    public void setMessageSendUrl(String messageSendUrl) {
        this.messageSendUrl = messageSendUrl;
    }

    public String getUserSimplelistUrl() {
        return userSimplelistUrl;
    }

    public void setUserSimplelistUrl(String userSimplelistUrl) {
        this.userSimplelistUrl = userSimplelistUrl;
    }

    public String getUserDetaillistUrl() {
        return userDetaillistUrl;
    }

    public void setUserDetaillistUrl(String userDetaillistUrl) {
        this.userDetaillistUrl = userDetaillistUrl;
    }

    public String getUserDetailUrl() {
        return userDetailUrl;
    }

    public void setUserDetailUrl(String userDetailUrl) {
        this.userDetailUrl = userDetailUrl;
    }

}
