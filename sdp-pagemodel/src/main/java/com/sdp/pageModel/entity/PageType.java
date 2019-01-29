package com.sdp.pageModel.entity;

import com.sdp.common.entity.BaseInfo;

/**
 * @description:
 * 页面模板类型实体类
 * @author: zhoutao
 * @version: 11:19 2018/4/11
 * @see:
 * @since:
 * @modified by:
 */
public class PageType extends BaseInfo{
    /**
     * 主键
     */
    private String pageTypeId;

    /**
     * 页面分类名称
     */
    private String pageTypeName;

    /**
     * 父类型主键
     */
    private String pageParentId;

    /**
     * 页面类型路径
     */
    private String pageTypePath;

    /**
     * 序号
     */
    private Integer pageSortId;


    public String getPageTypeName() {
        return pageTypeName;
    }

    public String getPageTypeId() {
        return pageTypeId;
    }

    public void setPageTypeId(String pageTypeId) {
        this.pageTypeId = pageTypeId;
    }

    public String getPageParentId() {
        return pageParentId;
    }

    public void setPageParentId(String pageParentId) {
        this.pageParentId = pageParentId;
    }

    public void setPageTypeName(String pageTypeName) {
        this.pageTypeName = pageTypeName;
    }

    public String getPageTypePath() {
        return pageTypePath;
    }

    public void setPageTypePath(String pageTypePath) {
        this.pageTypePath = pageTypePath;
    }

    public Integer getPageSortId() {
        return pageSortId;
    }

    public void setPageSortId(Integer pageSortId) {
        this.pageSortId = pageSortId;
    }
}
