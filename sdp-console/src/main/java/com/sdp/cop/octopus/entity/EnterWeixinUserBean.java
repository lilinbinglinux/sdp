/*
 * 文件名：EnterWeixinUserBean.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月20日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.entity;

/**
 * 企业微信成员信息
 * @author zhangyunzhen
 * @version 2017年7月20日
 * @see EnterWeixinUserBean
 * @since
 */
public class EnterWeixinUserBean {
    /**
     * 成员id
     */
    private String userid;

    /**
     * 成员名称
     */
    private String name;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
