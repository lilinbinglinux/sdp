package com.sdp.bcm.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加认证模型
 * 
 */
public class SwCreateCredentialsModel {

	/**
	 * 租户名称
	 */
    private String tenantName;

    /**
     * 代码托管工具
     * 1:gitlab|2:svn|3:github
     */
    private int codeControlType;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 仓库地址
     */
    private String registoryAddress;

    private String accessToken;

    /**
     * 是否公有,0:公有|1:私有
     */
    private Byte publicOrPrivateFlag;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 创建人
     */
    private String createdBy;

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public int getCodeControlType() {
        return codeControlType;
    }

    public void setCodeControlType(int codeControlType) {
        this.codeControlType = codeControlType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegistoryAddress() {
        return registoryAddress;
    }

    public void setRegistoryAddress(String registoryAddress) {
        this.registoryAddress = registoryAddress;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Byte getPublicOrPrivateFlag() {
        return publicOrPrivateFlag;
    }

    public void setPublicOrPrivateFlag(Byte publicOrPrivateFlag) {
        this.publicOrPrivateFlag = publicOrPrivateFlag;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

}
