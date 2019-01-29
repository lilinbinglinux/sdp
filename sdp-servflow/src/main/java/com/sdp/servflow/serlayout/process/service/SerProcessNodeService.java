package com.sdp.servflow.serlayout.process.service;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.sdp.servflow.serlayout.process.model.ServiceInfoPo;

public interface SerProcessNodeService {
	/**
	 * 获取所有节点信息
	 * @param flowChartId
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getNodeJson(String flowChartId,String serVerId,String serflowDataType) throws Exception;
	
	/**
	 * 流程图添加
	 * @param updateflag
	 * @param serNodeArray
	 * @param serJoinArray
	 * @param serFlowType 
	 * @return
	 * @throws RuntimeException
	 */
	@Transactional(rollbackFor=RuntimeException.class)
	public String addNode(String serNodeArray, String serJoinArray,String serFlowType) throws RuntimeException;
	
	/**
	 * 流程图更新
	 * @param updateFlag 判断新建版本或更新当前版本
	 * @param serNodeArray
	 * @param serJoinArray
	 * @param serJoinArray2 
	 * @return
	 */
	@Transactional(rollbackFor=RuntimeException.class)
	public void updateNode(String serId,String updateFlag, String serNodeArray, String serJoinArray, String serFlowType);
	
	/**
	 * 获取服务综合信息
	 * @return
	 */
	public ServiceInfoPo getServiceInfoPo(Map<String,String> map);
	
	/**
	 * 
	 * @param id
	 * @param serFlowType
	 * @return
	 */
	public String getSerName(String id,String serFlowType);
	
	

}
