package com.sdp.servflow.pubandorder.flowchart.service;

import java.util.List;
import java.util.Map;

import com.sdp.servflow.pubandorder.flowchart.model.ParamFlowChartBean;

/**
 * 
 * 操作参数映射
 *
 * @author ZY
 * @version 2017年8月21日
 * @see ParamFlowChartBeanService
 * @since
 */
public interface ParamFlowChartBeanService{
    
	void insert(ParamFlowChartBean paramFlowChartBean);
	
	void updateByPrimaryKey(ParamFlowChartBean paramFlowChartBean);
	
//	void insertObj(String pubrobj, String regexobj);

    void deleteByCondition(Map<String, String> map);

    List<ParamFlowChartBean> getAllByCondition(Map<String, String> map);

//    void updateParamRelation(String pubrobj);
    
    String paramIsCount(String flowChartId);

	List<ParamFlowChartBean> findLayoutParam( String pubid , String flowChartId,String node_id);

}
