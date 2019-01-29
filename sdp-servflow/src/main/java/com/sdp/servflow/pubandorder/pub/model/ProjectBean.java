package com.sdp.servflow.pubandorder.pub.model;

import java.util.Date;

/**
 * 
 * 服务注册项目或模块Bean
 *
 * @author ZY
 * @version 2017年5月19日
 * @see ProjectBean
 * @since
 */
public class ProjectBean {
    /**
     * 主键Id
     */
    private String proid;
    
    /**
     * 名称
     */
    private String name;
    
    /**
     * 英文名称
     */
    private String proecode;
    
    /**
     * 版本
     */
    private String proversion;
    
    /**
     * 描述
     */
    private String prodescribe;
    
    /**
     * 创建日期
     */
    private Date createdate;
    
    /**
     *父id(项目的父id为ROOT,模块文件夹的父id为项目) 
     */
    private String parentId;
    
    /**
     * 项目类型(0为项目   1为模块  2为api)
     */
    private String typeId;
    
    /**
     *租户id 
     */
    private String tenant_id;
    
    public String getProid() {
        return proid;
    }
    public void setProid(String proid) {
        this.proid = proid;
    }
    public Date getCreatedate() {
        return createdate;
    }
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getProecode() {
        return proecode;
    }
    public void setProecode(String proecode) {
        this.proecode = proecode;
    }
    public String getProversion() {
        return proversion;
    }
    public void setProversion(String proversion) {
        this.proversion = proversion;
    }
    public String getProdescribe() {
        return prodescribe;
    }
    public void setProdescribe(String prodescribe) {
        this.prodescribe = prodescribe;
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
        return "ProjectBean [proid=" + proid + ", name=" + name + ", proecode=" + proecode
               + ", proversion=" + proversion + ", prodescribe=" + prodescribe + ", createdate="
               + createdate + ", parentId=" + parentId + ", typeId=" + typeId + ", tenantId="
               + tenant_id + "]";
    }
    
    
    
    
    
    
    

}
