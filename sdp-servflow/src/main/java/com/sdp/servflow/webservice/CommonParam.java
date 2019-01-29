/**
 * 
 */
package com.sdp.servflow.webservice;

import java.io.Serializable;

/**
 * @author renpengyuan
 * @date 2017年9月25日
 */
public class CommonParam implements Serializable{
	private static final long serialVersionUID = 1L;
	private String appId;
	private String tenant_id;
	private String login_id;
	private String token_id;
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTenant_id() {
		return tenant_id;
	}
	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	public String getToken_id() {
		return token_id;
	}
	public void setToken_id(String token_id) {
		this.token_id = token_id;
	}
}
