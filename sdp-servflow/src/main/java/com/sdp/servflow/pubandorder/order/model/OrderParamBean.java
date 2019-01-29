package com.sdp.servflow.pubandorder.order.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * 接口订阅——参数实体类
 *
 * @author 牛浩轩
 * @version 2017年6月14日
 * @see OrderParamBean
 * @since
 */
@XStreamAlias("param")
public class OrderParamBean {

    /**
     * 参数id
     */
    private String orderparamId;
    
    /**
     * 参数
     */
    private String name;
    
    /**
     * 描述
     */
    private String paramdesc;
    
    /**
     * 参数类型
     */
    private String paramtype;
    
    /**
     * 是否可为空
     */
    private String isempty;
    
    /**
     * 类型（0为请求参数，1为响应参数）
     */
    private String type;
    
    /**
     * 父节点
     */
    private String parentId;
    
    /**
     * 注册服务参数id
     */
    private String pubparamId;
    
    /**
     * 订阅服务id
     */
    private String orderid;
    
    /**
     * 租户tenant_id
     */
    private String tenant_id; 
    
    /**
     * 某个参数内的参数带着list的集合
     */
    private OrderParamBeanList orderParamBean;

    /**
     * 订阅接口名称
     */
    private String ordername;
    
    /**
     * 映射参数名称
     */
    private String ecode;
    
    public String getOrderparamId() {
        return orderparamId;
    }

    public void setOrderparamId(String orderparamId) {
        this.orderparamId = orderparamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParamdesc() {
        return paramdesc;
    }

    public void setParamdesc(String paramdesc) {
        this.paramdesc = paramdesc;
    }

    public String getParamtype() {
        return paramtype;
    }

    public void setParamtype(String paramtype) {
        this.paramtype = paramtype;
    }

    public String getIsempty() {
        return isempty;
    }

    public void setIsempty(String isempty) {
        this.isempty = isempty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPubparamId() {
        return pubparamId;
    }

    public void setPubparamId(String pubparamId) {
        this.pubparamId = pubparamId;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
    
    public String getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }

    public OrderParamBeanList getOrderParamBean() {
        return orderParamBean;
    }

    public void setOrderParamBean(OrderParamBeanList orderParamBean) {
        this.orderParamBean = orderParamBean;
    }
    
    public String getOrdername() {
        return ordername;
    }

    public void setOrdername(String ordername) {
        this.ordername = ordername;
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    @Override
    public String toString() {
        return "OrderParamBean [orderparamId=" + orderparamId + ", name=" + name + ", paramdesc="
               + paramdesc + ", paramtype=" + paramtype + ", isempty=" + isempty + ", type=" + type
               + ", parentId=" + parentId + ", pubparamId=" + pubparamId + ", orderid=" + orderid
               + ", orderParamBean=" + orderParamBean + ", getOrderparamId()=" + getOrderparamId()
               + ", getName()=" + getName() + ", getParamdesc()=" + getParamdesc()
               + ", getParamtype()=" + getParamtype() + ", getIsempty()=" + getIsempty()
               + ", getType()=" + getType() + ", getParentId()=" + getParentId()
               + ", getPubparamId()=" + getPubparamId() + ", getOrderid()=" + getOrderid()
               + ", getOrderParamBean()=" + getOrderParamBean() + ", getClass()=" + getClass()
               + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }

    public OrderParamBean() {
        super();
    }
    
    
}
