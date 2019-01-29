/*
 * 文件名：TreeNode.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：ke_wang
 * 修改时间：2016年6月17日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.common.entity;

public class TreeNode {
    /**
     * id
     */
    private String id;
    
    /**
     * name
     */
    private String name;
    
    /**
     * parentId
     */
    private String pId;

    /**
     * 是否为父节点
     */
    private boolean isParent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

	public TreeNode(String id, String name, String pId, boolean isParent) {
		super();
		this.id = id;
		this.name = name;
		this.pId = pId;
		this.isParent = isParent;
	}

	public TreeNode() {
		super();
	}
    
    
}
