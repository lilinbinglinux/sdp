package com.sdp.pageModel.entity;

import com.sdp.common.entity.BaseInfo;

/**
 * @description:
 * 组件分类实体类
 * @author: zhoutao
 * @version: 14:33 2018/4/11
 * @see:
 * @since:
 * @modified by:
 */
public class PageModuleType extends BaseInfo {
    /**
     * 主键
     */
    private String  moduleTypeId;

    /**
     * 组件分类名称
     */
    private String moduleTypeName;

    /**
     * 父类型id
     */
    private String moduleParentId;

    /**
     * 是否为公共类型(0 否，1 是)
     */
    private int pubFlag;

    /**
     * 序号
     */
    private int sortId;
    
    /**
     * 用来判断是否还有孩子节点，1代表有，0为无
     */
    private String isParentId;

    public String getModuleTypeId() {
        return moduleTypeId;
    }

    public void setModuleTypeId(String moduleTypeId) {
        this.moduleTypeId = moduleTypeId;
    }

    public String getModuleParentId() {
        return moduleParentId;
    }

    public void setModuleParentId(String moduleParentId) {
        this.moduleParentId = moduleParentId;
    }

    public String getModuleTypeName() {
        return moduleTypeName;
    }

    public void setModuleTypeName(String moduleTypeName) {
        this.moduleTypeName = moduleTypeName;
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

	public String getIsParentId() {
		return isParentId;
	}

	public void setIsParentId(String isParentId) {
		this.isParentId = isParentId;
	}
    
    
}
