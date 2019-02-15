/*
 * 文件名：SendLog.java
 * 版权：Copyright by www.sdp.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年5月22日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.entity;

import java.util.Date;

/**
 * 记录信息表
 * @author zhangyunzhen
 * @version 2017年5月22日
 * @see SendRecordInfo
 * @since
 */
public class SendRecordInfo {

    /**
     * 主键
     */
    private Long recordId;
    
    /**
     *  发送方 
     */
    private String sender;
    
    /**
     * 接收方
     */
    private String receiver;
    
    /**
     * 内容
     */
    private String content;
    
    /**
     * 发送类型
     * email:邮件
     * mobile:手机
     */
    private String type;
    
    /**
     * 发送时间
     */
    private Date sendtime = new Date();
    
    /**
     * 错误日志
     */
    private String errorlog;
    
    /**
     * 客户端名称
     */
    private String appname;
    
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    
    

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    public String getErrorlog() {
        return errorlog;
    }

    public void setErrorlog(String errorlog) {
        this.errorlog = errorlog;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }
    
    public SendRecordInfo() {
        super();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public SendRecordInfo(String sender, String receiver, String content, String type,
                          Date sendtime, String errorlog, String appname) {
        super();
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.type = type;
        this.sendtime = sendtime;
        this.errorlog = errorlog;
        this.appname = appname;
    }


    
}
