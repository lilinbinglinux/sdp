package com.sdp.servflow.pubandorder.security.model;

/**
 * 
 * 服务安全参数Model
 *
 * @author ZY
 * @version 2017年7月21日
 * @see SecurityCodeModel
 * @since
 */
public class SecurityCodeBean {
    
    /**
     * token_id安全验证唯一标识
     */
    private String token_id;
    
    /**
     * API申请用户login_id
     */
    private String login_id;
    
    /**
     * 所申请api的appid
     */
    private String appId;
    
    /**
     * 申请API用户的租户id
     */
    private String tenant_id;

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

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
    
}
