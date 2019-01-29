package com.sdp.servflow.logSer.log.model;

public class CodeTable {
	
	//主键
    private Integer recId;
    //返回编码
    private String code;
    //描述信息
    private String desc;
    //数量
    private Integer sendCount;
    //(0为响应提示信息，1为码表描述信息)
    private String type;
    //归属页面信息
    private String location;

    public Integer getRecId() {
        return recId;
    }

    public void setRecId(Integer recId) {
        this.recId = recId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
