package com.sdp.servflow.pubandorder.flowchart.model;

/**
 * 
 * 节点Model
 *
 * @author ZY
 * @version 2017年8月2日
 * @see ProcessNode
 * @since
 */
public class ProcessNode {
    
    /**
     * 节点id
     */
	private String nodeId;
	
	/**
	 * 节点名称
	 */
	private String nodeName;
	
	/**
	 * 流程图id
	 */
	private String flowChartId;
	
	/**
	 * 节点类型（圆形或者矩形）
	 */
	private String nodeType;
	
	/**
	 * 起点x轴位置
	 */
	private float  clientX;
	
	/**
	 * 起点y轴位置
	 */
	private float  clientY;
	
	/**
	 * 节点宽度
	 */
	private float  nodeWidth;
	
	/**
	 * 节点高度
	 */
	private float  nodeHeight;
	
	/**
	 * 节点半径
	 */
	private float  nodeRadius;
	
	/**
	 * 创建时间
	 */
	private String creatime;
	
	/**
	 * 创建人
	 */
	private String userId;
	
	/**
	 * 修改时间
	 */
	private String updateTime;
	
	/**
	 * 租户ID
	 */
	private String tenantId;
	
	/**
	 * 
	 */
	private String  componentType;
	
	/**
	 * 服务中的id值
	 */
	private String pubid;
	
	/**
	 * 用户可能会保存的其他值
	 */
	private Object other;
	
	private String startSet;
	
	private String endSet;
	

	public String getNodeId() {
		return nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public String getFlowChartId() {
		return flowChartId;
	}

	public String getNodeType() {
		return nodeType;
	}

	public float getClientX() {
		return clientX;
	}

	public float getClientY() {
		return clientY;
	}

	public float getNodeWidth() {
		return nodeWidth;
	}

	public float getNodeHeight() {
		return nodeHeight;
	}

	public float getNodeRadius() {
		return nodeRadius;
	}

	public String getCreatime() {
		return creatime;
	}

	public String getUserId() {
		return userId;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public void setFlowChartId(String flowChartId) {
		this.flowChartId = flowChartId;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public void setClientX(float clientX) {
		this.clientX = clientX;
	}

	public void setClientY(float clientY) {
		this.clientY = clientY;
	}

	public void setNodeWidth(float nodeWidth) {
		this.nodeWidth = nodeWidth;
	}

	public void setNodeHeight(float nodeHeight) {
		this.nodeHeight = nodeHeight;
	}

	public void setNodeRadius(float nodeRadius) {
		this.nodeRadius = nodeRadius;
	}

	public void setCreatime(String creatime) {
		this.creatime = creatime;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getComponentType() {
		return componentType;
	}

	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

    public String getPubid() {
        return pubid;
    }

    public void setPubid(String pubid) {
        this.pubid = pubid;
    }
    
    public Object getOther() {
        return other;
    }

    public void setOther(Object other) {
        this.other = other;
    }

    public String getStartSet() {
        return startSet;
    }

    public void setStartSet(String startSet) {
        this.startSet = startSet;
    }

    public String getEndSet() {
        return endSet;
    }

    public void setEndSet(String endSet) {
        this.endSet = endSet;
    }
    
}
