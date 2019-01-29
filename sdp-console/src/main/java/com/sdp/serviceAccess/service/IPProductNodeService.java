/**
 * 
 */
package com.sdp.serviceAccess.service;

import java.util.List;
import java.util.Map;

import com.sdp.common.entity.Status;
import com.sdp.common.page.Pagination;
import com.sdp.serviceAccess.entity.PProductCase;
import com.sdp.serviceAccess.entity.PProductNode;

/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: IPProductNodeService.java
* @Description: 节点业务逻辑抽象接口
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年8月2日 下午4:01:11 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月2日     renpengyuan      v1.0.0               修改原因
* 2018年10月9日    renpengyuan      v1.0.1            增加动态属性列配置(isNeedCompare)
* 2019年1月19日	  zy        							根据实例查询节点信息(findByCase method)
*/
public interface IPProductNodeService {
    
	Status saveNode(List<PProductNode> nodes);
	
	Map<String,Object> caseByNodes(PProductCase productcase,Pagination page,boolean isNeedCompare);
	
	/**
	 * 
	* @Title: findByCase  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param @param productcase
	* @param @return    参数  
	* @return List<PProductNode>    返回类型  
	* @throws
	 */
	List<PProductNode> findByCase(PProductCase productcase);
	
}
