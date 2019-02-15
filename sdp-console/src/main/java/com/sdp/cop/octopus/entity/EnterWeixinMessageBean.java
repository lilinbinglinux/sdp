/*
 * 文件名：EnterWeixinMessageBean.java
 * 版权：Copyright by www.sdp.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月20日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.entity;


import java.util.HashMap;
import java.util.Map;


/**
 * 企业微信message bean
 * @author zhangyunzhen
 * @version 2017年7月20日
 * @see EnterWeixinMessageBean
 * @since
 */
public class EnterWeixinMessageBean {
    /**
     * 成员ID列表（消息接收者，多个接收者用‘|’分隔)（可选）
     */
    private String touser;

    /**
     * 部门ID列表，多个接收者用‘|’分隔(可选)
     */
    private String toparty;

    /**
     * 标签ID列表，多个接收者用‘|’分隔(可选)
     */
    private String totag;

    /**
     * 消息类型
     */
    private String msgtype;

    /**
     * 企业应用的id
     */
    private Integer agentid;

    /**
     * 消息内容
     */
    private Map<String, String> text = new HashMap<>();

    /**
     * 图片内容
     */
    private Map<String, String> image;

    /**
     * 表示是否是保密消息
     */
    private Integer safe;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getToparty() {
        return toparty;
    }

    public void setToparty(String toparty) {
        this.toparty = toparty;
    }

    public String getTotag() {
        return totag;
    }

    public void setTotag(String totag) {
        this.totag = totag;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public Map<String, String> getText() {
        return text;
    }

    public Integer getAgentid() {
        return agentid;
    }

    public void setAgentid(Integer agentid) {
        this.agentid = agentid;
    }

    public void setText(Map<String, String> text) {
        this.text = text;
    }

    public Map<String, String> getImage() {
        return image;
    }

    public void setImage(Map<String, String> image) {
        this.image = image;
    }

    public Integer getSafe() {
        return safe;
    }

    public void setSafe(Integer safe) {
        this.safe = safe;
    }

}
