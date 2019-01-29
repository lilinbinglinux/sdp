package com.sdp.servflow.logSer.flowControl.model;

import java.util.Date;

/** 
* @Title: FlowControlBean.java 
* @Description: 类 令牌桶算法 限流
* tinybad 
*/ 
public class FlowControlBean {
	
	public FlowControlBean(){
	}
	
	public FlowControlBean(long capacity, long intervalInMills){
		init(capacity,intervalInMills,0);
	}
	
	public FlowControlBean(long capacity, long intervalInMills, long maxBurstTime){
		init(capacity,intervalInMills,maxBurstTime);
	}
	
	private void init(long capacity, long intervalInMills, long maxBurstTime){
		this.capacity = capacity;
        this.intervalInMills = intervalInMills;
        intervalPerPermit = intervalInMills / capacity;
        if(maxBurstTime>0){
        	maxBurstTokens = Math.min(maxBurstTime/intervalPerPermit, capacity);
        }else{
        	maxBurstTokens = capacity;
        }
	}
	
	// id
	private String id;
	// 服务id
	private String pubId;
	// 间隔（毫秒）
	private long intervalInMills;
	// 最大容量（限制的次数）
	private long capacity;
	// 创建时间
	private Date createTime;
	// 租户id
	private String tenant_id;
	
	// 以下字段不在数据库中存储
	// 每一次允许通过所耗时间隔
	private long intervalPerPermit;
	
	// 如果用户长时间不使用该桶, 则保存最大令牌=intervalPerPermit * maxBurstTime
	private long maxBurstTokens;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPubId() {
		return pubId;
	}

	public void setPubId(String pubId) {
		this.pubId = pubId;
	}

	public long getIntervalInMills() {
		return intervalInMills;
	}

	public void setIntervalInMills(long intervalInMills) {
		this.intervalInMills = intervalInMills;
	}

	public long getCapacity() {
		return capacity;
	}

	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTenant_id() {
		return tenant_id;
	}

	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}

	public long getIntervalPerPermit() {
		return intervalPerPermit;
	}

	public void setIntervalPerPermit(long intervalPerPermit) {
		this.intervalPerPermit = intervalPerPermit;
	}

	public long getMaxBurstTokens() {
		return maxBurstTokens;
	}

	public void setMaxBurstTokens(long maxBurstTokens) {
		this.maxBurstTokens = maxBurstTokens;
	}
	
	public String genBucketKey(String pubId) {
        return "rate:pubId:"+pubId;
    }
}
