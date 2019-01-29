/*
 * 文件名：AppDepartmentBean.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月21日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.entity;


/**
 * app 部门dto
 * @author zhangyunzhen
 * @version 2017年7月21日
 * @see AppDepartmentBean
 * @since
 */
public class AppDepartmentBean {
    /**
     * app名字
     */
    private String app;
    /**
     * 部门
     */
    private String departments;
    
    public String getApp() {
        return app;
    }
    public void setApp(String app) {
        this.app = app;
    }
    public String getDepartments() {
        return departments;
    }
    public void setDepartments(String departments) {
        this.departments = departments;
    }
    public AppDepartmentBean() {
        super();
        // TODO Auto-generated constructor stub
    }
    public AppDepartmentBean(String app, String departments) {
        super();
        this.app = app;
        this.departments = departments;
    }
    
    
    
}
