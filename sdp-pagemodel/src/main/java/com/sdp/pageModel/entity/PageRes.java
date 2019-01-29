package com.sdp.pageModel.entity;

import com.sdp.common.entity.BaseInfo;

/**
 * @description:
 * 资源实体类
 * @author: zhoutao
 * @version: 15:18 2018/4/11
 * @see:
 * @since:
 * @modified by:
 */
public class PageRes extends BaseInfo {
    /**
     * 主键
     */
    private String resId;
    /**
     * 资源分类
     */
    private String resTypeId;

    /**
     * 资源名称
     */
    private String resName;

    /**
     * 资源内容
     */
    private byte[] resText;

    /**
     * 资源类型(0 文本 1 js 2 css 3 图片 4 flash 5 视频)
     */
    private int resType;

    /**
     * 文件格式
     */
    private String fileSuffix;

    /**
     * 序号
     */
    private int sortId;

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getResTypeId() {
        return resTypeId;
    }

    public void setResTypeId(String resTypeId) {
        this.resTypeId = resTypeId;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public byte[] getResText() {
        return resText;
    }

    public void setResText(byte[] resText) {
        this.resText = resText;
    }

    public int getResType() {
        return resType;
    }

    public void setResType(int resType) {
        this.resType = resType;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }
}
