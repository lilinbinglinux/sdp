/*
 * 文件名：EnterWeixinDepartmentBean.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月20日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.entity;

import java.util.List;

/**
 * 企业微信部门bean
 * @author zhangyunzhen
 * @version 2017年7月20日
 * @see EnterWeixinDepartmentBean
 * @since
 */
public class EnterWeixinDepartmentBean {
    /**
     * 部门id
     */
    private Integer departmentId;
    /**
     * 部门名字
     */
    private String departmentName;
    /**
     * 部门成员
     */
    private List<EnterWeixinUserBean> userlist;

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<EnterWeixinUserBean> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<EnterWeixinUserBean> userlist) {
        this.userlist = userlist;
    }
}
