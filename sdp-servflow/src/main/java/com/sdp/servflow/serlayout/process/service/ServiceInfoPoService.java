package com.sdp.servflow.serlayout.process.service;

import java.util.List;
import java.util.Map;

import com.sdp.servflow.serlayout.process.model.ServiceInfoPo;


public interface ServiceInfoPoService {
	/**
	 * 获得最新版本服务信息
	 * @param map
	 * @return
	 */
	public List<ServiceInfoPo> getAllEqualInfoByCondition(Map<String,String> map);
	
	/**
	 * 获得所有版本服务信息
	 * @param map
	 * @return
	 */
	public List<ServiceInfoPo> getAllByCondition(Map<String,String> map);
	
	public int getAllCount(Map<String,String> map);
	
	/**
	 * 获取所有版本服务树形信息
	 * @return
	 */
	public List<ServiceInfoPo> getTreeData(String jsonStr,String startNum,String num);
	
	/**
	 * 删除
	 * @param po
	 * @return
	 */
	public String deleteSerProcess(ServiceInfoPo po);

}
