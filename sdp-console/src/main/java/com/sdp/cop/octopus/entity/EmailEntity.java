/*
 * 文件名：EmailInfo.java
 * 版权：Copyright by www.sdp.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年5月19日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.entity;

import java.util.List;

/**
 * email DTO
 * @author zhangyunzhen
 * @version 2017年5月19日
 * @see EmailEntity
 * @since
 */
public class EmailEntity {

    /**
     * 接收者邮箱号list
     */
    private List<String> to;
    /**
     * 设置抄送
     */
    private List<String> cc;
    /**
     * 设置密送
     */
    private List<String> bcc;
    /**
     * 邮件内容
     */
    private String content;
    /**
     * 邮件主题
     */
    private String subject;
    

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public List<String> getTo() {
        return to;
    }
    public void setTo(List<String> to) {
        this.to = to;
    }
    public List<String> getCc() {
        return cc;
    }
    public void setCc(List<String> cc) {
        this.cc = cc;
    }
    public List<String> getBcc() {
        return bcc;
    }
    public void setBcc(List<String> bcc) {
        this.bcc = bcc;
    }

    
}
