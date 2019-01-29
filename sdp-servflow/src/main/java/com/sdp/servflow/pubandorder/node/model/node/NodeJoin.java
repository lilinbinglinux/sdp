package com.sdp.servflow.pubandorder.node.model.node;

import java.io.Serializable;

public class NodeJoin extends ConditionNode  implements Serializable{
    
	private static final long serialVersionUID = 1L;
	/**
	 * 线ID
	 */
	private String joinId;
	
	/**
	 * 开始节点ID
	 */
	private String startNodeId;
	
	/**
	 * 结束节点ID
	 */
	private String endNodeId;
	
	/**
	 * 开始节点方向
	 */
	private String joinType;
	
	/**
	 * 结束节点方向
	 */
	private String joinDirection;
	
	/**
	 * 路径
	 */
	private String path;

	public String getJoinId() {
		return joinId;
	}

	public void setJoinId(String joinId) {
		this.joinId = joinId;
	}

	public String getStartNodeId() {
		return startNodeId;
	}

	public void setStartNodeId(String startNodeId) {
		this.startNodeId = startNodeId;
	}

	public String getEndNodeId() {
		return endNodeId;
	}

	public void setEndNodeId(String endNodeId) {
		this.endNodeId = endNodeId;
	}

	public String getJoinType() {
		return joinType;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

	public String getJoinDirection() {
		return joinDirection;
	}

	public void setJoinDirection(String joinDirection) {
		this.joinDirection = joinDirection;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "NodeJoin [joinId=" + joinId + ", startNodeId=" + startNodeId + ", endNodeId=" + endNodeId
				+ ", joinType=" + joinType + ", joinDirection=" + joinDirection + ", path=" + path + "]";
	}

}
