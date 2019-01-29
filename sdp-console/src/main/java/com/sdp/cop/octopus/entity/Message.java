package com.sdp.cop.octopus.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sdp.common.CustomJsonDateDeserializer;

/**
 * 站内信
 *
 */
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

    private String messSenderId;

    private String messReceiverId;

    private String messTitle;

    private String messType;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date messSendTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date messReadTime;

    /**
     * 暂定1是已读，其他为未读
     */
    private String messFlag;

    private String messContent;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private String messStartSendTime;
    
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private String messEndSendTime;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessSenderId() {
        return messSenderId;
    }

    public void setMessSenderId(String messSenderId) {
        this.messSenderId = messSenderId;
    }

    public String getMessReceiverId() {
        return messReceiverId;
    }

    public void setMessReceiverId(String messReceiverId) {
        this.messReceiverId = messReceiverId;
    }

    public String getMessTitle() {
        return messTitle;
    }

    public void setMessTitle(String messTitle) {
        this.messTitle = messTitle;
    }

    public String getMessType() {
        return messType;
    }

    public void setMessType(String messType) {
        this.messType = messType;
    }
    
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getMessSendTime() {
        return messSendTime;
    }
    
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public void setMessSendTime(Date messSendTime) {
        this.messSendTime = messSendTime;
    }

    public Date getMessReadTime() {
        return messReadTime;
    }

    public void setMessReadTime(Date messReadTime) {
        this.messReadTime = messReadTime;
    }

    public String getMessFlag() {
        return messFlag;
    }

    public void setMessFlag(String messFlag) {
        this.messFlag = messFlag;
    }

    public String getMessContent() {
        return messContent;
    }

    public void setMessContent(String messContent) {
        this.messContent = messContent;
    }

	public String getMessStartSendTime() {
		return messStartSendTime;
	}

	public void setMessStartSendTime(String messStartSendTime) {
		this.messStartSendTime = messStartSendTime;
	}

	public String getMessEndSendTime() {
		return messEndSendTime;
	}

	public void setMessEndSendTime(String messEndSendTime) {
		this.messEndSendTime = messEndSendTime;
	}
    
}