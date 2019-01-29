package com.sdp.servflow.pubandorder.flowchart.service;

import java.util.Map;

import com.sdp.servflow.pubandorder.flowchart.model.FlowChart;

public interface FlowChartService {
	
	void addFlowChart(FlowChart flowChart);
	
	void updateFlowChart(FlowChart flowChart);
	
	void updateName(FlowChart flowChart);
	
	void deleteFlowChart(Map<String, String> map);
	
	FlowChart findByIdAndTenantId(Map<String, String> map);
	
	Map selectAll(String start,String length,Map<String,Object>paramMap);
	//Pagination findAllByCondition(FlowChart flowChart, Pagination pagination);
	
	//删除和编排有关的所有信息
    void deleteLaySerAll(String flowChartId);
}
