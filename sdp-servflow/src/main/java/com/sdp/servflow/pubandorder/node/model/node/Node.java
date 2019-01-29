package com.sdp.servflow.pubandorder.node.model.node;

import java.io.Serializable;
import java.util.List;

/***
 * 
 * 存放关于节点的信息
 *
 * @author 任壮
 * @version 2017年10月20日
 * @see Node
 * @since
 */
public class Node  implements Serializable{
    
	private static final long serialVersionUID = 1L;
    
    /**
     * 节点类型
     */
    public static final String startNodeStyle = "1";		//开始节点
    //public static final String conditionNodeStyle = "2";	//条件节点
    public static final String lineNodeStyle = "2"; //线节点
    public static final String interfaceNodeStyle = "3";	//接口节点
    public static final String endNodeStyle = "4";	//结束节点
    public static final String groupNodeStartStyle = "5";	//聚合开始节点
    public static final String groupNodeEndStyle = "6";	//聚结束节点
    
    
    /*
     * 普通接口组件
     */
    public static final String commonComponentType = "0";
    /*
     * 自定义组件
     */
    public static final String defineComponentType = "1";
    
    /**
     * 服务ID（流程图ID）
     */
    private String flowChartId;
    /***
     * 服务类型
     */
    private String serType;
    
    /***
     * 表示唯一的节点
     */
    private String nodeId;
    
    /***
     * 节点的名称
     */
    private String nodeName;
    
    /***
     * 节点类型（圆形或者矩形）
     */
    private String nodeType;
    /***
     * 组件类型（）
     */
    private String componentType;
    
    /**
     * 节点风格，开始节点，条件节点，接口节点，结束节点,聚合节点
     */
    private String nodeStyle;
    
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
     * 用户可能会保存的其他值
     */
    private Object other;
    /**
     * 下一个节点的集合
     */
    //private List<NodeJoin> targetlist;
    private List<String> targetlist;
  
    /**
     * 父节点
     */
    private Node parentNode;
    
    public String getFlowChartId() {
        return flowChartId;
    }
    public void setFlowChartId(String flowChartId) {
        this.flowChartId = flowChartId;
    }
    public String getNodeId() {
        return nodeId;
    }
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
    public String getNodeName() {
        return nodeName;
    }
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
    public float getClientX() {
        return clientX;
    }
    public void setClientX(float clientX) {
        this.clientX = clientX;
    }
    public float getClientY() {
        return clientY;
    }
    public void setClientY(float clientY) {
        this.clientY = clientY;
    }
    public float getNodeWidth() {
        return nodeWidth;
    }
    public void setNodeWidth(float nodeWidth) {
        this.nodeWidth = nodeWidth;
    }
    public float getNodeHeight() {
        return nodeHeight;
    }
    public void setNodeHeight(float nodeHeight) {
        this.nodeHeight = nodeHeight;
    }
    public float getNodeRadius() {
        return nodeRadius;
    }
    public void setNodeRadius(float nodeRadius) {
        this.nodeRadius = nodeRadius;
    }
    public Object getOther() {
        return other;
    }
    public void setOther(Object other) {
        this.other = other;
    }
    public String getNodeType() {
        return nodeType;
    }
    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }
   
    public Node getParentNode() {
        return parentNode;
    }
    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }
    public String getNodeStyle() {
        return nodeStyle;
    }
    public void setNodeStyle(String nodeStyle) {
        this.nodeStyle = nodeStyle;
    }
    public List<String> getTargetlist() {
		return targetlist;
	}
	public void setTargetlist(List<String> targetlist) {
		this.targetlist = targetlist;
	}
	public String getComponentType() {
        return componentType;
    }
    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }
    @Override
    public String toString() {
        return "Node [flowChartId=" + flowChartId + ", serType=" + serType + ", nodeId=" + nodeId
               + ", nodeName=" + nodeName + ", nodeType=" + nodeType + ", componentType="
               + componentType + ", nodeStyle=" + nodeStyle + ", clientX=" + clientX + ", clientY="
               + clientY + ", nodeWidth=" + nodeWidth + ", nodeHeight=" + nodeHeight
               + ", nodeRadius=" + nodeRadius + ", other=" + other + ", targetlist=" + targetlist
               + ", parentNode=" + parentNode + "]";
    }
    public String getSerType() {
        return serType;
    }
    public void setSerType(String serType) {
        this.serType = serType;
    }
    

    
    
}
