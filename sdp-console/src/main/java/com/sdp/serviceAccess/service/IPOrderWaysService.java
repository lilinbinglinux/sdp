package com.sdp.serviceAccess.service;

import java.util.List;

import com.sdp.serviceAccess.entity.POrderWays;

public interface IPOrderWaysService {
	/**
	 * 
	* 根据主键查询
	* @param @param pwaysId
	* @param @return    参数
	* @return POrderWays    返回类型
	 */
	POrderWays findByPriId(String pwaysId);
	
	/**
	 * 
	* 获取所有订购方式
	* @param  参数
	* @return List<POrderWays>    返回类型
	 */
	List<POrderWays> allPorderWays(POrderWays porderWays);

}
