package com.sdp.bcm.entity;

import java.util.Date;

/**
 * @author lumeiling
 * @package com.bonc.bcm.entity
 * @create 2018-11-2018/11/23 下午12:34
 **/
public class ProProject {
    public String projectId;
    public String projectName;
    public String projectCode;
    public String projectResume;
    public String hostAddr;
    public Integer projectLevel;
    public Integer sortId;
    public String stateFlag;
    public String delFlag;
    public Date createTime;
    public String createUser;
    public String tenantId;
    public String profile;

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    public String getProjectId() {
        return this.projectId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
    public String getProjectCode() {
        return this.projectCode;
    }

    public void setProjectResume(String projectResume) {
        this.projectResume = projectResume;
    }
    public String getProjectResume() {
        return this.projectResume;
    }

    public void setHostAddr(String hostAddr) {
        this.hostAddr = hostAddr;
    }
    public String getHostAddr() {
        return this.hostAddr;
    }

    public void setProjectLevel(Integer projectLevel) {
        this.projectLevel = projectLevel;
    }
    public Integer getProjectLevel() {
        return this.projectLevel;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }
    public Integer getSortId() {
        return this.sortId;
    }

    public void setStateFlag(String stateFlag) {
        this.stateFlag = stateFlag;
    }
    public String getStateFlag() {
        return stateFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
    public String getDelFlag() {
        return this.delFlag;
    }

    public void setCreateDate(Date createTime) {
        this.createTime = createTime;
    }
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    public String getCreateUser() {
        return this.createUser;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
    public String getTenantId() {
        return this.tenantId;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
    public String getProfile() {
        return this.profile;
    }
}
