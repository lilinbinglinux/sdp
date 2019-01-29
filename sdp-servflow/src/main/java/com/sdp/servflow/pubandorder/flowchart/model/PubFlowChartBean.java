package com.sdp.servflow.pubandorder.flowchart.model;

/**
 * 
 * 编排后注册的整体服务
 *
 * @author ZY
 * @version 2017年8月9日
 * @see PubFlowChart
 * @since
 */
public class PubFlowChartBean {
    /**
     * 节点id
     */
    private String node_id;
    
    /**
     * 流程图中每个节点服务id
     */
    private String pubid;
    
    /**
     * 流程图中每个服务节点name
     */
    private String name;
    
    /**
     * 流程图中每个服务节点排序
     */
    private int sort;
    
    /**
     * 流程图id
     */
    private String flowChartId;
    
    /**
     * 租户id
     */
    private String tenant_id;
    
    /**
     * 存链路上下一个接口节点id(如果有多个用，分割)
     */
    private String nextnode;
    
    /**
     * 存链路上下一个接口节点id(如果有多个用，分割)
     */
    private String prenode;
    
    /**
     * 接口节点条件
     */
    private String condition;
    
    /**
     *typeId 矩形为0，菱形为1，开始节点为001，结束节点为999
     */
    private String typeId;
    
    
    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getPubid() {
        return pubid;
    }

    public void setPubid(String pubid) {
        this.pubid = pubid;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getFlowChartId() {
        return flowChartId;
    }

    public void setFlowChartId(String flowChartId) {
        this.flowChartId = flowChartId;
    }

    public String getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }

    public String getNextnode() {
        return nextnode;
    }

    public void setNextnode(String nextnode) {
        this.nextnode = nextnode;
    }

    public String getPrenode() {
        return prenode;
    }

    public void setPrenode(String prenode) {
        this.prenode = prenode;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

}
