/*
 * 文件名：BounceEmailLog.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月5日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.entity;


import java.util.Date;


import com.alibaba.fastjson.annotation.JSONField;


/**
 * 邮件退信记录表
 * @author zhangyunzhen
 * @version 2017年7月5日
 * @see BounceEmailLog
 * @since
 */
public class BounceEmailLog {

    /**
     * 主键
     */
    private Long id;

    /**
     * 退回邮件的主题
     */
    private String bounceSubject;

    /**
     * 原邮件的发送时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date oriEmaDate;

    /**
     * 原邮件的收件人
     */
    private String oriEmaTo;

    /**
     * 原邮件的主题
     */
    private String oriEmaSubject;
    
    /**
     * 退信邮件的内容
     */
    private String bounceEmaContent;

    /**
     * 退信原因
     */
    private String bounceReason;

    /**
     * 建议解决方案
     */
    private String proposedSolution;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;
    
    /**
     * app名字
     */
    private String app;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBounceSubject() {
        return bounceSubject;
    }

    public void setBounceSubject(String bounceSubject) {
        this.bounceSubject = bounceSubject;
    }

    public Date getOriEmaDate() {
        return oriEmaDate;
    }
    public String getBounceEmaContent() {
        return bounceEmaContent;
    }

    public void setBounceEmaContent(String bounceEmaContent) {
        this.bounceEmaContent = bounceEmaContent;
    }

    public void setOriEmaDate(Date oriEmaDate) {
        this.oriEmaDate = oriEmaDate;
    }

    public String getOriEmaTo() {
        return oriEmaTo;
    }

    public void setOriEmaTo(String oriEmaTo) {
        this.oriEmaTo = oriEmaTo;
    }

    public String getOriEmaSubject() {
        return oriEmaSubject;
    }

    public void setOriEmaSubject(String oriEmaSubject) {
        this.oriEmaSubject = oriEmaSubject;
    }

    public String getBounceReason() {
        return bounceReason;
    }

    public void setBounceReason(String bounceReason) {
        this.bounceReason = bounceReason;
    }

    public String getProposedSolution() {
        return proposedSolution;
    }

    public void setProposedSolution(String proposedSolution) {
        this.proposedSolution = proposedSolution;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

}
