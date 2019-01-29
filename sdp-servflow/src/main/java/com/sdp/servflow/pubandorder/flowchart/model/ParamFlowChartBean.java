package com.sdp.servflow.pubandorder.flowchart.model;

/**
 * 
 * 编排后服务之间的映射关系
 *
 * @author ZY
 * @version 2017年8月21日
 * @see ParamFlowChartBean
 * @since
 */
public class ParamFlowChartBean {
    
    /**
     * 映射关系id，主键
     */
    private String relationid;
    
    /**
     * 参数映射中前一个服务的pubid
     */
    private String prepubid;
    
    /**
     * 参数映射中后一个服务的pubid
     */
    private String pubid;
    
    /**
     * 流程图id(编排服务id)
     */
    private String flowChartId;
    
    /**
     * 参数映射中该服务请求参数id
     */
    private String pre_paramid;
    
    /**
     * 参数映射中该服务请求参数名称
     */
    private String pre_paramname;
    
    /**
     * 映射中要对应的参数id
     */
    private String next_paramid;
    
    /**
     * 映射中要对应的参数名称
     */
    private String next_paramname;
    
    /**
     * 当前登录用户login_id
     */
    private String login_id;
    
    /**
     * 当前登录用户租户id
     */
    private String tenant_id;
    
    /**
     * 类型
     */
    private String type;
    
    /**
     * 正则表达式映射规则
     */
    private String regex;
    
    /**
     * 常量参数值
     */
    private String constantparam;
    
    /**
     * 菱形节点id
     */
    private String node_id;

    public String getFlowChartId() {
        return flowChartId;
    }

    public void setFlowChartId(String flowChartId) {
        this.flowChartId = flowChartId;
    }

    public String getPubid() {
        return pubid;
    }

    public void setPubid(String pubid) {
        this.pubid = pubid;
    }

    public String getPre_paramid() {
        return pre_paramid;
    }

    public void setPre_paramid(String pre_paramid) {
        this.pre_paramid = pre_paramid;
    }

    public String getPre_paramname() {
        return pre_paramname;
    }

    public void setPre_paramname(String pre_paramname) {
        this.pre_paramname = pre_paramname;
    }

    public String getNext_paramid() {
        return next_paramid;
    }

    public void setNext_paramid(String next_paramid) {
        this.next_paramid = next_paramid;
    }

    public String getNext_paramname() {
        return next_paramname;
    }

    public void setNext_paramname(String next_paramname) {
        this.next_paramname = next_paramname;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
    
    public String getConstantparam() {
        return constantparam;
    }

    public void setConstantparam(String constantparam) {
        this.constantparam = constantparam;
    }

    public String getRelationid() {
        return relationid;
    }

    public void setRelationid(String relationid) {
        this.relationid = relationid;
    }
    

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }
    
    public String getPrepubid() {
        return prepubid;
    }

    public void setPrepubid(String prepubid) {
        this.prepubid = prepubid;
    }

    public ParamFlowChartBean(String relationid,String prepubid, String pubid, String flowChartId,
                              String pre_paramid, String pre_paramname, String next_paramid,
                              String next_paramname, String node_id, String login_id, String tenant_id,
                              String type, String regex,String constantparam) {
        super();
        this.relationid = relationid;
        this.prepubid = prepubid;
        this.pubid = pubid;
        this.flowChartId = flowChartId;
        this.pre_paramid = pre_paramid;
        this.pre_paramname = pre_paramname;
        this.next_paramid = next_paramid;
        this.next_paramname = next_paramname;
        this.node_id = node_id;
        this.login_id = login_id;
        this.tenant_id = tenant_id;
        this.type = type;
        this.regex = regex;
        this.constantparam = constantparam;
    }

    public ParamFlowChartBean() {
        super();
    }
    
    
    
}
