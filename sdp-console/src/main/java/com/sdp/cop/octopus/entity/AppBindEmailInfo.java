/*
 * 文件名：AppEmailAddrInfo.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.entity;

/**
 * app邮箱对应表
 * @author zhangyunzhen
 * @version 2017年7月7日
 * @see AppBindEmailInfo
 * @since
 */
public class AppBindEmailInfo {

    private Long id;

    /**
     * 调用方名字
     */
    private String app;

    /**
     * 邮箱地址
     */
    private String emailAddr;
    

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

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public AppBindEmailInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

    public AppBindEmailInfo(String app, String emailAddr) {
        super();
        this.app = app;
        this.emailAddr = emailAddr;
    }
}
