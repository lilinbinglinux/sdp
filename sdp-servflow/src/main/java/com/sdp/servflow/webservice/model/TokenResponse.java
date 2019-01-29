/**
 * 
 */
package com.sdp.servflow.webservice.model;

import java.io.Serializable;

/**
 * @author renpengyuan
 * @date 2017年9月26日
 */
public class TokenResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	/**返回结果编码*/
    private String code ; 
    /**返回结果详细信息*/
    private String msg;
    /**key*/
    private String key;
    /**token值*/
    private String access_token;
    /**token 的类型*/
    private String token_type;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
