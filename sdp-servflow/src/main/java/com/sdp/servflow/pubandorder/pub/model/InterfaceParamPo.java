package com.sdp.servflow.pubandorder.pub.model;

import java.util.List;

/**
 * 
 * 服务和参数的共同属性的抽象实体
 *
 * @author ZY
 * @version 2017年8月16日
 * @see InterfaceParamPo
 * @since
 */
public class InterfaceParamPo {
    private String id;
    private String name;
    private String pId;
    private String reqtype;
    private List<InterfaceParamPo> children;
    /**
     * 区分是接口还是参数,0为服务，1为参数
     */
    private String flag;
    
    /**
     * 是否默认展开
     */
    private boolean open;
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
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    public boolean isOpen() {
        return open;
    }
    public void setOpen(boolean open) {
        this.open = open;
    }
    public List<InterfaceParamPo> getChildren() {
        return children;
    }
    public void setChildren(List<InterfaceParamPo> children) {
        this.children = children;
    }
    public String getReqtype() {
        return reqtype;
    }
    public void setReqtype(String reqtype) {
        this.reqtype = reqtype;
    }
	@Override
	public String toString() {
		return "InterfaceParamPo [id=" + id + ", name=" + name + ", pId=" + pId + ", reqtype=" + reqtype + ", children="
				+ children + ", flag=" + flag + ", open=" + open + "]";
	}
    
    
    
    
    
    
    
}
