package com.sdp.servflow.logSer.serIpLimit.model;

import java.util.Date;

public class SerIpLimit {

	// id
	private String ip_id;

	// 订阅id
	private String order_id;
	
	// 服务id
	private String app_id;

	// ip地址（all 不限制）
	private String ip_addr;

	// 名单类型（0 白名单 1 黑名单）
	private String name_type;

	// 创建时间
	private Date creatime;

	// 租户id
	private String tenant_id;
	
	public SerIpLimit(){
	}

	public SerIpLimit(String ip_id, String order_id, String app_id, String ip_addr, String name_type, Date creatime,
			String tenant_id) {
		super();
		this.ip_id = ip_id;
		this.order_id = order_id;
		this.app_id = app_id;
		this.ip_addr = ip_addr;
		this.name_type = name_type;
		this.creatime = creatime;
		this.tenant_id = tenant_id;
	}

	public String getIp_id() {
		return ip_id;
	}

	public void setIp_id(String ip_id) {
		this.ip_id = ip_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getIp_addr() {
		return ip_addr;
	}

	public void setIp_addr(String ip_addr) {
		this.ip_addr = ip_addr;
	}

	public String getName_type() {
		return name_type;
	}

	public void setName_type(String name_type) {
		this.name_type = name_type;
	}

	public Date getCreatime() {
		return creatime;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public void setCreatime(Date creatime) {
		this.creatime = creatime;
	}

	public String getTenant_id() {
		return tenant_id;
	}

	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}

}
