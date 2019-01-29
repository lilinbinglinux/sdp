package com.sdp.servflow.pubandorder.pub.model;

import java.util.List;

import com.sdp.servflow.pubandorder.flowchart.model.ParamFlowChartBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * 请求参数信息
 *
 * @author ZY
 * @version 2017年5月23日
 * @see ReqModel
 * @since
 */
@XStreamAlias("param")
public class ReqparamBean {
    /**
     * 主键
     */
    private String id;
    
    /**
     * 编码
     */
    private String ecode;
    
    /**
     * 描述
     */
    private String reqdesc;
    
    /**
     * 参数位置
     */
    private String parampos;
    
    /**
     * 参数类型
     */
    private String reqtype;
    
    /**
     * 是否可为空(1为是，0为否)
     */
    private String isempty;
    
    /**
     * 有参数存在嵌套情况，如果没有，则设为ROOT
     */
    private String parentId;
    
    /**
     * 用来页面层级展示适应的字段
     */
    private String _parentId;
    
    /**
     * 参数层级路径(只有子节点存)
     */
    private String parampath;
    
    /**
     * 所有节点都存
     */
    private String path;
    
    /**
     * 参数层级路径的值（末级节点保存）
     */
    private String ecodepath;
    
    /**
     * 所属API id
     */
    private String pubid;
    
    /**
     * 是请求参数还响应参数(1为请求参数，0为响应参数)
     */
    private String type;
    
    /**
     * 可能某个参数内的参数为list集合
     */
    private ReqparamBeanList subparam;
    
    /**
     * 租户id
     */
    private String tenant_id;
    
    /**
     * 排序
     */
    private String sort;
    
    /**
     * 最后更新时间
     */
    private String lastUpdateTime;
    
    /**
     * String类型的最大长度
     */
    private String maxlength;
    
    private List<ReqparamBean> children;
    
    private List<ParamFlowChartBean> paramFlowChartBeans;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public String getReqdesc() {
        return reqdesc;
    }

    public void setReqdesc(String reqdesc) {
        this.reqdesc = reqdesc;
    }

    public String getParampos() {
        return parampos;
    }

    public void setParampos(String parampos) {
        this.parampos = parampos;
    }

    public String getReqtype() {
        return reqtype;
    }

    public void setReqtype(String reqtype) {
        this.reqtype = reqtype;
    }

    public String getIsempty() {
        return isempty;
    }

    public void setIsempty(String isempty) {
        this.isempty = isempty;
    }

    public String getPubid() {
        return pubid;
    }

    public void setPubid(String pubid) {
        this.pubid = pubid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public ReqparamBeanList getParamlist() {
        return subparam;
    }

    public void setParamlist(ReqparamBeanList paramlist) {
        this.subparam = paramlist;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public ReqparamBeanList getSubparam() {
        return subparam;
    }

    public void setSubparam(ReqparamBeanList subparam) {
        this.subparam = subparam;
    }
    
    public String getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }

    public String get_parentId() {
        return parentId; //页面上取值时可以直接得到parentId值
    }

    public void set_parentId(String _parentId) {
        this._parentId = _parentId;
    }
    
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getParampath() {
        return parampath;
    }

    public void setParampath(String parampath) {
        this.parampath = parampath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getEcodepath() {
        return ecodepath;
    }

    public void setEcodepath(String ecodepath) {
        this.ecodepath = ecodepath;
    }

    public List<ReqparamBean> getChildren() {
        return children;
    }

    public void setChildren(List<ReqparamBean> children) {
        this.children = children;
    }
    
    public List<ParamFlowChartBean> getParamFlowChartBeans() {
        return paramFlowChartBeans;
    }

    public void setParamFlowChartBeans(List<ParamFlowChartBean> paramFlowChartBeans) {
        this.paramFlowChartBeans = paramFlowChartBeans;
    }

    public String getMaxlength() {
        return maxlength;
    }

    public void setMaxlength(String maxlength) {
        this.maxlength = maxlength;
    }

    public ReqparamBean(String id, String ecode, String reqdesc, String parampos, String reqtype,
                        String isempty, String parentId,String parampath, String pubid, String type,
                        ReqparamBeanList subparam, String tenant_id,String maxlength) {
        super();
        this.id = id;
        this.ecode = ecode;
        this.reqdesc = reqdesc;
        this.parampos = parampos;
        this.reqtype = reqtype;
        this.isempty = isempty;
        this.parentId = parentId;
        this.parampath = parampath;
        this.pubid = pubid;
        this.type = type;
        this.subparam = subparam;
        this.tenant_id = tenant_id;
        this.maxlength = maxlength;
    }


    @Override
    public String toString() {
        return "ReqparamBean [id=" + id + ", ecode=" + ecode + ", reqdesc=" + reqdesc
               + ", parampos=" + parampos + ", reqtype=" + reqtype + ", isempty=" + isempty
               + ", parentId=" + parentId + ", pubid=" + pubid + ", type=" + type + ", subparam="
               + subparam + ", tenant_id=" + tenant_id + ", getId()=" + getId() + ", getEcode()="
               + getEcode() + ", getReqdesc()=" + getReqdesc() + ", getParampos()=" + getParampos()
               + ", getReqtype()=" + getReqtype() + ", getIsempty()=" + getIsempty()
               + ", getPubid()=" + getPubid() + ", getType()=" + getType() + ", getParamlist()="
               + getParamlist() + ", getParentId()=" + getParentId() + ", getSubparam()="
               + getSubparam() + ", getTenant_id()=" + getTenant_id() + ", getClass()="
               + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
               + "]";
    }

    public ReqparamBean() {
        super();
    }
    
    
    
}
