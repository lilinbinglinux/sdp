package com.sdp.servflow.pubandorder.node.model.field;

import com.sdp.servflow.pubandorder.node.model.parameter.Parameter;

/**
 * 
 * 所有末级节点的父对象
 *
 * @author 任壮
 * @version 2017年10月24日
 * @see XmlNode
 * @since
 */
public class XmlNode {

    /***
     * 标记唯一的id
     */
    private String id;
    /***
     * 名称
     */
    private String name;
    /***
     * 类型（int String Object List boolean ListObjct ListList）
     */
    private String type;
    
    /**
     * ListObjct ListList List下的顺序
     */
    private String order;
    
    public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	/***
     * 长度
     */
    private String length;
    /**
     * 位置
     * 1.url位置（用于get时候的参数）
     * 2.头信息（请求头或响应头的信息）
     * 3.body中（请求体或响应体的信息）
     * 4.属性（位于父节点的属性上）
     */
    private String location;
    
    private String value;
    /**
     * 节点的从根节点到当前节点的路径
     */
    private String path;
    /**
     * 归属的参数
     */
    private Parameter paramter;
    /**
     * 是否必传
     */
    private boolean isMust;
    /**
     * 描述信息
     */
    private String desc;
    
    /***
     * 父节点信息
     */
    private String parentId;
    
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
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getLength() {
        return length;
    }
    public void setLength(String length) {
        this.length = length;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public Parameter getParamter() {
        return paramter;
    }
    public void setParamter(Parameter paramter) {
        this.paramter = paramter;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return "XmlNode [id=" + id + ", name=" + name + ", type=" + type + ", length=" + length
               + ", location=" + location + ", value=" + value + ", path=" + path + "]";
    }
    public boolean isMust() {
        return isMust;
    }
    public void setMust(boolean isMust) {
        this.isMust = isMust;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
