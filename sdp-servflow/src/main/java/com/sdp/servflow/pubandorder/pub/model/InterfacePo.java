package com.sdp.servflow.pubandorder.pub.model;

/**
 * 
 * 接口注册树形数据抽象对象,统一处理API和项目Project
 *
 * @author ZY
 * @version 2017年5月24日
 * @see InterfacePo
 * @since
 */
public class InterfacePo {
    
    /**
     * 主键
     */
    private String id;
    
    /**
     * 名称
     */
    private String name;
    
    /**
     * 父id
     */
    private String parentId;
    
    /**
     * 类型
     */
    private String typeId;
    /**
     * 租户id
     */
    private String tenant_id;
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
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getTypeId() {
        return typeId;
    }
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
    public String getTenant_id() {
        return tenant_id;
    }
    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }
    @Override
    public String toString() {
        return "InterfacePo [id=" + id + ", name=" + name + ", parentId=" + parentId + ", typeId="
               + typeId + ", tenant_id=" + tenant_id + "]";
    }
    
    
    

}
