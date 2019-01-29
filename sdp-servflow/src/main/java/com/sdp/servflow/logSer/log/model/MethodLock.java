package com.sdp.servflow.logSer.log.model;

public class MethodLock {
	
	// 实例全局id(具有唯一性和一次性
	private Integer instanceId;
	
	// 锁定的方法名
	private String methodName;
	
	// 方法描述信息
	private String methodDesc;
	
	// 保存时间 格式 yyyymmdd
	private Integer updateTime;

	public Integer getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Integer instanceId) {
		this.instanceId = instanceId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodDesc() {
		return methodDesc;
	}

	public void setMethodDesc(String methodDesc) {
		this.methodDesc = methodDesc;
	}

	public Integer getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}
	
}
