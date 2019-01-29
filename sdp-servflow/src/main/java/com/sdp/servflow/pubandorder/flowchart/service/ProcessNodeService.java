package com.sdp.servflow.pubandorder.flowchart.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sdp.servflow.pubandorder.flowchart.model.ProcessNode;

public interface ProcessNodeService{
	/**
	 * 增加流程图节点信息
	 * @param processNode
	 */
	void addNode(ProcessNode processNode) ;
	
	public void deleteAll(Map<String, String> map) ;

	List<ProcessNode> findNodeByFId(Map<String , String> map);

	public String selectPrePubId(String join_id);
	
	public String selectNextPubId(String join_id);
	
	/*
	 * 编排画布线上映射关系回显
	 */
	Map<String,Object> updateSelectAll(String join_id, String flowChartId);

	List<ProcessNode> findNode(String flowChartId);
	
	/*
	 * 添加所有节点相关信息
	 */
    String addAll(String updateflag,String conditions, String pubSers, String flowChartName, String others,
                String nodeRelation);

}
