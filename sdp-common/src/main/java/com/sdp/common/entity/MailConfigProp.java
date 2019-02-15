/*
 * 文件名：ConfigurationProperties.java
 * 版权：Copyright by www.sdp.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年5月17日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.common.entity;


import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 *  调取配置文件mail信息
 * @author zhangyunzhen
 * @version 2017年5月18日
 * @see MailConfigProp
 * @since
 */
@ConfigurationProperties
public class MailConfigProp implements Serializable {
    /**
     * 发送邮件的服务器的IP
     */
    private String mailServerHost;

    /**
     * 发送邮件的服务器的端口
     */
    private String mailServerPort;

    /**
     * 登陆邮件发送服务器的用户名
     */
    private String mailServerUsername;

    /**
     * 登陆邮件发送服务器的密码
     */
    private String mailServerPassword;

    /**
     * 管理员邮箱
     */
    private String mailServerAdmin;
    
    /**
     * SSL加密连接启用开关
     */
    private boolean mailServerIsSSL;
    
    public boolean getMailServerIsSSL() {
        return mailServerIsSSL;
    }

    public void setMailServerIsSSL(boolean mailServerIsSSL) {
        this.mailServerIsSSL = mailServerIsSSL;
    }

    public String getMailServerAdmin() {
        return mailServerAdmin;
    }

    public void setMailServerAdmin(String mailServerAdmin) {
        this.mailServerAdmin = mailServerAdmin;
    }

    public String getMailServerHost() {
        return mailServerHost;
    }

    public void setMailServerHost(String mailServerHost) {
        this.mailServerHost = mailServerHost;
    }

    public String getMailServerPort() {
        return mailServerPort;
    }

    public void setMailServerPort(String mailServerPort) {
        this.mailServerPort = mailServerPort;
    }

    public String getMailServerUsername() {
        return mailServerUsername;
    }

    public void setMailServerUsername(String mailServerUsername) {
        this.mailServerUsername = mailServerUsername;
    }

    public String getMailServerPassword() {
        return mailServerPassword;
    }

    public void setMailServerPassword(String mailServerPassword) {
        this.mailServerPassword = mailServerPassword;
    }

}
