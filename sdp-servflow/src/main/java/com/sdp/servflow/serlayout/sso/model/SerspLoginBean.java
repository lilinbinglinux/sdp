package com.sdp.servflow.serlayout.sso.model;

import java.util.Date;

/**
 * 
 * @author zy
 * @Date 2017年12月22日
 */
public class SerspLoginBean {
	/**
	 * 单点登录ID
	 */
	private String sploginid;
	
	/**
	 * 单点登录名称
	 */
	private String spname;
	
	/**
	 * 服务编码
	 */
	private String spcode;
	
	/**
	 * 服务类型
	 */
	private String sptype;
	
	/**
	 * 服务类型id
	 */
	private String sptypeId;
	
	
	/**
	 * url地址
	 */
	private String sppath;
	
	/**
	 * 链接描述
	 */
	private String spresume;
	
	/**
	 * 服务协议(0 http 1 soap 2 socket)
	 */
	private String spagreement;
	
	/**
	 * 请求方式(0 get 1 post)
	 */
	private String sprestype;
	
	/**
	 * 编排流程
	 */
	private String spflow;
	
	/**
	 * 创建时间
	 */
	private Date creatime; 
	
	/**
	 * 是否删除(0 正常 1删除)
	 */
	private String delflag;
	
	/**
	 * 是否停用(0 正常 1删除)
	 */
	private String stopflag;
	
	/**
	 * 租户id
	 */
	private String tenantId;
	
	/**
	 * 用户loginId
	 */
	private String loginId;

	public String getSploginid() {
		return sploginid;
	}

	public void setSploginid(String sploginid) {
		this.sploginid = sploginid;
	}

	public String getSpname() {
		return spname;
	}

	public void setSpname(String spname) {
		this.spname = spname;
	}

	public String getSpcode() {
		return spcode;
	}

	public void setSpcode(String spcode) {
		this.spcode = spcode;
	}

	public String getSptype() {
		return sptype;
	}

	public void setSptype(String sptype) {
		this.sptype = sptype;
	}

	public String getSppath() {
		return sppath;
	}

	public void setSppath(String sppath) {
		this.sppath = sppath;
	}

	public String getSpresume() {
		return spresume;
	}

	public void setSpresume(String spresume) {
		this.spresume = spresume;
	}

	public String getSpagreement() {
		return spagreement;
	}

	public void setSpagreement(String spagreement) {
		this.spagreement = spagreement;
	}

	public String getSprestype() {
		return sprestype;
	}

	public void setSprestype(String sprestype) {
		this.sprestype = sprestype;
	}

	public String getSpflow() {
		return spflow;
	}

	public void setSpflow(String spflow) {
		this.spflow = spflow;
	}

	public Date getCreatime() {
		return creatime;
	}

	public void setCreatime(Date creatime) {
		this.creatime = creatime;
	}

	public String getDelflag() {
		return delflag;
	}

	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}

	public String getStopflag() {
		return stopflag;
	}

	public void setStopflag(String stopflag) {
		this.stopflag = stopflag;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	

	public String getSptypeId() {
		return sptypeId;
	}

	public void setSptypeId(String sptypeId) {
		this.sptypeId = sptypeId;
	}

	@Override
	public String toString() {
		return "SerspLoginBean [sploginid=" + sploginid + ", spname=" + spname + ", spcode=" + spcode + ", sptype="
				+ sptype + ", sptypeId=" + sptypeId + ", sppath=" + sppath + ", spresume=" + spresume + ", spagreement="
				+ spagreement + ", sprestype=" + sprestype + ", spflow=" + spflow + ", creatime=" + creatime
				+ ", delflag=" + delflag + ", stopflag=" + stopflag + ", tenantId=" + tenantId + ", loginId=" + loginId
				+ "]";
	}

	

}
