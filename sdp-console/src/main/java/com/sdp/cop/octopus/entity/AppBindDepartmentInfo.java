/*
 * 文件名：AppBindDepartmentInfo.java
 * 版权：Copyright by www.sdp.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月20日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.entity;

/**
 * app部门对应表
 * @author zhangyunzhen
 * @version 2017年7月20日
 * @see AppBindDepartmentInfo
 * @since
 */
public class AppBindDepartmentInfo {

    private Long id;

    /**
     * app名字
     */
    private String app;

    /**
     * 部门id
     */
    private Integer departmentId;

    /**
     * 部门名字
     */
    private String departmentName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public AppBindDepartmentInfo(String app, Integer departmentId, String departmentName) {
        super();
        this.app = app;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public AppBindDepartmentInfo() {
        super();
    }

}
