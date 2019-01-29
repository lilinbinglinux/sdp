package com.sdp.pageModel.entity;

import com.sdp.common.entity.BaseInfo;

/**
 * @description:
 * 资源类型实体类
 * @author: zhoutao
 * @version: 15:12 2018/4/11
 * @see:
 * @since:
 * @modified by:
 */
public class PageResType extends BaseInfo {
    /**
     * 主键
     */
    private String resTypeId;

    /**
     * 组件分类名称
     */
    private String resTypeName;

    /**
     * 父类型
     */
    private String resParentId;

    /**
     * 是否为公共类型(0 否，1 是)
     */
    private int pubFlag;

    private int sortId;

    public String getResTypeId() {
        return resTypeId;
    }

    public void setResTypeId(String resTypeId) {
        this.resTypeId = resTypeId;
    }

    public String getResParentId() {
        return resParentId;
    }

    public void setResParentId(String resParentId) {
        this.resParentId = resParentId;
    }

    public String getResTypeName() {
        return resTypeName;
    }

    public void setResTypeName(String resTypeName) {
        this.resTypeName = resTypeName;
    }

    public int getPubFlag() {
        return pubFlag;
    }

    public void setPubFlag(int pubFlag) {
        this.pubFlag = pubFlag;
    }

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }
}
