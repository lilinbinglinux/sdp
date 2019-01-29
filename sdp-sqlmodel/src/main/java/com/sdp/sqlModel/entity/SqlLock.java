package com.sdp.sqlModel.entity;

import java.util.Date;

public class SqlLock {
	/**
	 * 数据源ID
	 */
    private String dataResId;
    
    /**
     * 操作时间
     */
    private Date createDate;
    
    /**
     * 登录者ID
     */
    private String loginId;
    /**
     * uuid
     */
    private String uuid;
    
    /**
	 * 	状态
	 */
	private String dataStatus;

	public String getDataResId() {
		return dataResId;
	}

	public void setDataResId(String dataResId) {
		this.dataResId = dataResId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
    
	
}
