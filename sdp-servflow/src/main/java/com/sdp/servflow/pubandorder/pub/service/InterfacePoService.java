package com.sdp.servflow.pubandorder.pub.service;

import java.util.List;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;

import com.sdp.servflow.pubandorder.pub.model.InterfacePo;


/**
 * 
 * 抽象类InterfacePo对应Service,统一处理API和项目Project
 *
 * @author ZY
 * @version 2017年6月10日
 * @see InterfacePoService
 * @since
 */
public interface InterfacePoService {
    
    /**
     * 
     * Description:获取所有的项目，模块和API 
     * 
     *@param request
     *@return List<InterfacePo>
     *
     * @see
     */
    List<InterfacePo> selectAll();
	
	/**
	 * 
	 * Description: 删除项目或API
	 * 
	 *@param id
	 *@param typeId void
	 * @param tenant_id 
	 *@return String
	 * @see
	 */
    String delete(String id, String typeId, String tenant_id);
	
}
