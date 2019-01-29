package com.sdp.cop.octopus.entity;

import java.security.GeneralSecurityException;
import java.util.Properties;
import javax.mail.internet.InternetAddress;

import com.sun.mail.util.MailSSLSocketFactory;


/**
 * 邮件实体
 * @author zhangyunzhen
 * @version 2017年5月18日
 * @see MailSenderInfo
 * @since
 */
public class MailSenderInfo {  
	/**
	 * 发送邮件的服务器的IP
	 */
    private String mailServerHost; 
	
	/**
	 * 发送邮件的服务器的端口
	 */
    private String mailServerPort = "25"; 
	
	/**
	 * 邮件发送者的地址 
	 */
    private String fromAddress; 
	
    /**
     * 邮件接收者的地址
     */
    private InternetAddress[] toAddress; 
	
    /**
	 * 抄送邮箱地址
	 */
    private InternetAddress[] cc;
    
    /**
     * 密送邮箱地址
     */
    private InternetAddress[] bcc;
    
    /**
     * 登陆邮件发送服务器的用户名
     */
    private String userName;
	
	/**
	 * 登陆邮件发送服务器的密码
	 */
    private String password; 
	
    /**
     * 是否需要身份验证 
     */
    private boolean validate = false; 
    
    /**
     * SSL加密
     */
    private boolean ssl = false;

    /**
     * 邮件主题
     */
    private String subject; 
	
    /**
     * 邮件的文本内容 
     */
    private String content;
    
	/**
	 * 邮件附件的文件名 
	 */
    private String[] attachFileNames;
	
    public MailSenderInfo(String host, String port, boolean validate, boolean isSSL, 
    							String username, String pwd, String from,
    								InternetAddress[] to,InternetAddress[] cc,InternetAddress[] bcc, String subject, String txt) {
    	this.mailServerHost = host;
    	this.mailServerPort = port;
    	this.validate = validate;
    	this.ssl = isSSL;
    	this.userName = username;
    	this.password = pwd;
    	this.fromAddress = from;
    	this.toAddress = to;
    	this.cc = cc;
    	this.bcc = bcc;
    	this.subject = subject;
    	this.content = txt;
    }
    
    public String[] getAttachFileNames() { 
    	return attachFileNames; 
    }
    
    public String getContent() { 
    	return content; 
    }
    
    public String getFromAddress() { 
    	return fromAddress; 
    }
    
    public String getMailServerHost() { 
    	return mailServerHost; 
    }
    
    public String getMailServerPort() { 
    	return mailServerPort; 
    }
    
    public String getPassword() { 
    	return password; 
    }
    
	/** 
	  * 获得邮件会话属性 
	  * @return pro Properties
	  */ 
    public Properties getProperties(){ 
    	Properties pro = new Properties(); 
    	pro.put("mail.smtp.host", this.mailServerHost); 
    	pro.put("mail.smtp.port", this.mailServerPort); 
    	pro.put("mail.smtp.auth", validate ? "true" : "false");
        pro.put("mail.debug", "true");
        if (ssl) {
            pro.put("mail.smtp.ssl.enable", ssl ? "true" : "false");
            MailSSLSocketFactory socketFactory = null;
            try {
                socketFactory = new MailSSLSocketFactory();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
            socketFactory.setTrustAllHosts(true);
            pro.put("mail.smtp.ssl.socketFactory", socketFactory);
            pro.put("mail.smtp.ssl.trust", "*");
            pro.put("mail.smtp.ssl.checkserveridentity", "false");            
        }
    	return pro; 
    }
    
    public String getSubject() { 
    	return subject; 
    }
    
    public InternetAddress[] getToAddress() {
    	return toAddress;
    }
    
    public String getUserName() { 
    	return userName; 
    }
    
    public boolean isValidate() { 
    	return validate; 
    }
    
    public void setAttachFileNames(String[] fileNames) { 
    	this.attachFileNames = fileNames; 
    }
    
    public void setContent(String textContent) { 
    	this.content = textContent; 
    }
    
    public void setFromAddress(String fromAddress) { 
    	this.fromAddress = fromAddress; 
    }
    
    public void setMailServerHost(String mailServerHost) { 
    	this.mailServerHost = mailServerHost; 
    }
    
    public void setMailServerPort(String mailServerPort) { 
    	this.mailServerPort = mailServerPort; 
    }
    
    public void setPassword(String password) { 
    	this.password = password; 
    }
    
    public void setSubject(String subject) { 
    	this.subject = subject; 
    }
    
    public void setToAddress(InternetAddress[] toAddress) {
    	this.toAddress = toAddress;
    }
    
    public void setUserName(String userName) { 
    	this.userName = userName; 
    }
    
    public void setValidate(boolean validate) { 
    	this.validate = validate; 
    }

    public InternetAddress[] getCc() {
        return cc;
    }

    public void setCc(InternetAddress[] cc) {
        this.cc = cc;
    }

    public InternetAddress[] getBcc() {
        return bcc;
    }

    public void setBcc(InternetAddress[] bcc) {
        this.bcc = bcc;
    }
    
    public boolean getSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }
    
} 