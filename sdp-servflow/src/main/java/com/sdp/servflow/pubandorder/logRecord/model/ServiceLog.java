package com.sdp.servflow.pubandorder.logRecord.model;

import java.util.Date;

/***
 * 
 * 服务日志记录类
 *
 * @author 任壮
 * @version 2017年9月24日
 * @see ServiceLog
 * @since
 */
public class ServiceLog {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 注册服务id
     */
    private String pubid;
    /**
     * 归属的编排服务id
     */
    private String layoutSerId;
    /***
     * 请求报文
     */
    private String requestMsg;
    /***
     * responseMsg
     */
    private String responseMsg;
    /***
     * 一次交互使用的id（调用一次接口分配一个requestID）
     */
    private String requestID;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 租户id
     */
    private String tenant_id;
    /**
     * 耗时（毫秒）
     */
    private Long usetime;
    public String getPubid() {
        return pubid;
    }
    public void setPubid(String pubid) {
        this.pubid = pubid;
    }
    public String getLayoutSerId() {
        return layoutSerId;
    }
    public void setLayoutSerId(String layoutSerId) {
        this.layoutSerId = layoutSerId;
    }
    public String getRequestMsg() {
        return requestMsg;
    }
    public void setRequestMsg(String requestMsg) {
        this.requestMsg = requestMsg;
    }
    public String getResponseMsg() {
        return responseMsg;
    }
    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }
    public String getTenant_id() {
        return tenant_id;
    }
    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }
    public String getRequestID() {
        return requestID;
    }
    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }
    public ServiceLog() {
        super();
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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
    public Long getUsetime() {
        return usetime;
    }
    public void setUsetime(Long usetime) {
        this.usetime = usetime;
    }
    public ServiceLog(Integer id, String pubid, String layoutSerId, String requestMsg,
                      String responseMsg, String requestID, String startTime, String endTime,
                      String tenant_id, Long usetime) {
        super();
        this.id = id;
        this.pubid = pubid;
        this.layoutSerId = layoutSerId;
        this.requestMsg = requestMsg;
        this.responseMsg = responseMsg;
        this.requestID = requestID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tenant_id = tenant_id;
        this.usetime = usetime;
    }
}
