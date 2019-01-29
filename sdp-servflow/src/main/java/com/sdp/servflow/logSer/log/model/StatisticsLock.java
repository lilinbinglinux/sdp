package com.sdp.servflow.logSer.log.model;

public class StatisticsLock {
	// 实例全局id(具有唯一性和一次性
	private Integer instanceId;
	// 保存时间 格式 yyyymmddhhmm
	private String updateTime;

	public Integer getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Integer instanceId) {
		this.instanceId = instanceId;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
