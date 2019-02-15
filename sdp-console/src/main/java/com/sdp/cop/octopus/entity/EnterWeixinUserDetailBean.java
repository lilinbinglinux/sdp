/*
 * 文件名：EnterWeixinUserDetailBean.java
 * 版权：Copyright by www.sdp.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月25日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.entity;


/**
 * 用户详情bean
 * @author zhangyunzhen
 * @version 2017年7月25日
 * @see EnterWeixinUserDetailBean
 * @since
 */
public class EnterWeixinUserDetailBean extends EnterWeixinUserBean {

    /**
     * 手机号
     */
    private String mobile;
    
    /**
     * 邮箱
     */
    private String email;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
