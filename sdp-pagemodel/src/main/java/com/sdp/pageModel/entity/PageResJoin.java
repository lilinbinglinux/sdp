package com.sdp.pageModel.entity;

/**
 * @description:
 * @author: zhoutao
 * @version: 15:38 2018/4/11
 * @see:
 * @since:
 * @modified by:
 */
public class PageResJoin {
    /**
     * 主键
     */
    private String resJoinId;

    /**
     * 资源Id
     */
    private String resId;

    /**
     * 页面或模版ID
     */
    private String pageModuleId;

    /**
     * 序号
     */
    private int sortId;

    public String getResJoinId() {
        return resJoinId;
    }

    public void setResJoinId(String resJoinId) {
        this.resJoinId = resJoinId;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getPageModuleId() {
        return pageModuleId;
    }

    public void setPageModuleId(String pageModuleId) {
        this.pageModuleId = pageModuleId;
    }

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }
}
