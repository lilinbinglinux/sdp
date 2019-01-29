package com.sdp.common.entity;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;

/**
 * 调取配置文件oauth信息
 * @author zhangyunzhen
 * @version 2017年5月18日
 * @see Auth2DetailsConfigProp
 * @since
 */
@ConfigurationProperties(prefix="oauth")
@Scope("singleton")
public class Auth2Details implements Serializable
{

	private String scope;
	private String grantType;
	private String clientId;
	private String clientSecret;
	private String accessToken;
	private String authenticationServerUrl;
	private String portalSmsUrl;
	private String senderPhoneNumber;
	/*暂未用到*/
	private String refreshToken;
	private String username;
	private String password;
	private String resourceServerUrl;
	private boolean isAccessTokenRequest;
	
	
	
	public String getSenderPhoneNumber() {
        return senderPhoneNumber;
    }
    public void setSenderPhoneNumber(String senderPhoneNumber) {
        this.senderPhoneNumber = senderPhoneNumber;
    }
    public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getGrantType() {
		return grantType;
	}
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getAuthenticationServerUrl() {
		return authenticationServerUrl;
	}
	public void setAuthenticationServerUrl(String authenticationServerUrl) {
		this.authenticationServerUrl = authenticationServerUrl;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAccessTokenRequest() {
		return isAccessTokenRequest;
	}
	public void setAccessTokenRequest(boolean isAccessTokenRequest) {
		this.isAccessTokenRequest = isAccessTokenRequest;
	}
	public String getResourceServerUrl() {
		return resourceServerUrl;
	}
	public void setResourceServerUrl(String resourceServerUrl) {
		this.resourceServerUrl = resourceServerUrl;
	}
    public String getPortalSmsUrl() {
        return portalSmsUrl;
    }
    public void setPortalSmsUrl(String portalSmsUrl) {
        this.portalSmsUrl = portalSmsUrl;
    }
	
}
