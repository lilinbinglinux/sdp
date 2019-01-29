package com.sdp.servflow.pubandorder.flowchart.service;

import java.util.List;
import java.util.Map;

import com.sdp.servflow.pubandorder.flowchart.model.PubFlowChartBean;

public interface PubFlowChartBeanService{
    
	void insert(PubFlowChartBean pubFlowChart);
	
	void deleteByCondition(Map<String, String> map);
	
	List<PubFlowChartBean> select(String flowChartId);

	PubFlowChartBean getConditionBypubid(Map<String, String> map);
	
	List<PubFlowChartBean> getAllByCondition(Map<String, String> map);
}
