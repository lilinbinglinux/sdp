package com.sdp.bcm.entity;

/**
 * @author lumeiling
 * @package com.sdp.bcm.entity
 * @create 2018-11-2018/11/27 下午7:48
 **/
public class ProUser {
    public String projectId;
    public String userId;
    public String projectRoleId;
    public String closeFlag;
    public Integer sortId;
    public String tenantId;

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    public String getProjectId() {
        return this.projectId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserId() {
        return this.userId;
    }

    public void setProjectRoleId(String projectRoleId) {
        this.projectRoleId = projectRoleId;
    }
    public String getProjectRoleId() {
        return this.projectRoleId;
    }

    public void setCloseFlag(String closeFlag){
        this.closeFlag = closeFlag;
    }
    public String getCloseFlag() {
        return this.closeFlag;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }
    public Integer getSortId() {
        return this.sortId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
    public String getTenantId() {
        return this.tenantId;
    }
}
