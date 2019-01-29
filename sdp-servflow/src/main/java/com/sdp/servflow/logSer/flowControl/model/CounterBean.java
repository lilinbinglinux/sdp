package com.sdp.servflow.logSer.flowControl.model;

/**
 * @Title: CounterBean.java
 * @Description: 计数器 限流 tinybad
 */
public class CounterBean {
	// 订阅id
	private String orderid;

	// 访问频率
	private int acc_freq;

	// 访问频率时间类型（0为秒 1为分钟 2为小时 3为天）
	private String acc_freq_type;
	
	// 访问次数
	public int reqCount = 0;
	
	// 上次访问时间
	public long lastAccessTime=System.currentTimeMillis();
	
	// 租户id
	private String tenant_id;

	public int getAcc_freq() {
		return acc_freq;
	}

	public void setAcc_freq(int acc_freq) {
		this.acc_freq = acc_freq;
	}

	public String getAcc_freq_type() {
		return acc_freq_type;
	}

	public void setAcc_freq_type(String acc_freq_type) {
		this.acc_freq_type = acc_freq_type;
	}

	public int getReqCount() {
		return reqCount;
	}

	public void setReqCount(int reqCount) {
		this.reqCount = reqCount;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public long getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}
	
	public String genBucketKey(String orderid) {
        return "rate:orderid:"+orderid;
    }

	public String getTenant_id() {
		return tenant_id;
	}

	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}
}
