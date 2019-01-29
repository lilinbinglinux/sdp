package com.sdp.pageModel.entity;

import java.util.List;

import com.sdp.common.entity.BaseInfo;

/**
 * @description:
 * 组件实体类
 * @author: zhoutao
 * @version: 14:42 2018/4/11
 * @see:
 * @since:
 * @modified by:
 */
public class PageModule extends BaseInfo {

    /**
     * 主键
     */
    private String moduleId;

    /**
     * 组件分类Id
     */
    private String moduleTypeId;

    /**
     * 组件名称
     */
    private String moduleName;

    /**
     * 组件内容
     */
    private String moduleText;

    /**
     * 组件css样式
     */
    private String moduleStyle;

    /**
     * 组件js
     */
    private String moduleJs;

    /**
     * 是否为公共类型（0 否，1 是）
     */
    private int pubFlag;

    /**
     * 排序
     */
    private int sortId;
    
    /**
     * 提示说明
     */
    private String moduleTip;

    /**
     * 当前组件引入的资源Id，sort1:id1,sort2:id2 以逗号区分资源Id，冒号区分顺序和主键
     */
    private String pageResIds;
    
    /**
     * 当前组件引入的资源对象
     */
    private List<PageRes> pageRes;

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleTypeId() {
        return moduleTypeId;
    }

    public void setModuleTypeId(String moduleTypeId) {
        this.moduleTypeId = moduleTypeId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleText() {
        return moduleText;
    }

    public void setModuleText(String moduleText) {
        this.moduleText = moduleText;
    }

    public String getModuleStyle() {
        return moduleStyle;
    }

    public void setModuleStyle(String moduleStyle) {
        this.moduleStyle = moduleStyle;
    }

    public String getModuleJs() {
        return moduleJs;
    }

    public void setModuleJs(String moduleJs) {
        this.moduleJs = moduleJs;
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

    public String getPageResIds() {
        return pageResIds;
    }

    public void setPageResIds(String pageResIds) {
        this.pageResIds = pageResIds;
    }

	public List<PageRes> getPageRes() {
		return pageRes;
	}

	public void setPageRes(List<PageRes> pageRes) {
		this.pageRes = pageRes;
	}

	public String getModuleTip() {
		return moduleTip;
	}

	public void setModuleTip(String moduleTip) {
		this.moduleTip = moduleTip;
	}
	
    
    
}
