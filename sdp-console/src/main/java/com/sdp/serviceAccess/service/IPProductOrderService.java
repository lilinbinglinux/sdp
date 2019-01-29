/**
 * 
 */
package com.sdp.serviceAccess.service;

import java.util.List;
import java.util.Map;

import com.sdp.common.entity.Status;
import com.sdp.common.page.Pagination;
import com.sdp.serviceAccess.entity.PProductOrder;
import com.sdp.serviceAccess.entity.ProductFieldItem;

/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: IPProductOrderService.java
* @Description: 服务订单业务逻辑抽象接口
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年8月2日 下午3:58:42 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月2日     renpengyuan      v1.0.0               修改原因
*/
public interface IPProductOrderService {
	
	/**
	 * 
	* 查询所有订单
	* @param @param page
	* @param @param productOrder
	* @param @return    参数
	* @return Pagination    返回类型
	 */
	Pagination allOrders(Pagination page, PProductOrder productOrder);
	
	/**
	 * 
	* 根据订单id查询详细信息
	* @param @param productOrder
	* @param @return    参数
	* @return PProductOrder    返回类型
	 */
	PProductOrder singleOrder(PProductOrder productOrder);
	
	/**
	 * 
	* 保存订单信息
	* @param @param productOrder
	* @param @return    参数
	* @return Status    返回类型
	 */
	Status createProductOrder(PProductOrder productOrder,boolean isBaseInfo);
	
	/**
	 * 
	* 根据主键查询
	* @param @param orderId
	* @param @return    参数
	* @return PProductOrder    返回类型
	 */
	PProductOrder findByPriId(String orderId);
	
	/**
	 * 
	* 更新
	* @param @param productOrder
	* @param @return    参数
	* @return PProductOrder    返回类型
	 */
	Status updateProOrder(PProductOrder productOrder);
	
	/**
	 * 
	* 根据某个条件查询
	* @param @return    参数
	* @return PProductOrder    返回类型
	 */
	List<PProductOrder> findAllByCondition(PProductOrder productOrder);
	
	/**
	 * 
	* 根据某个条件模糊查询
	* @param @param proOrder
	* @param @return    参数
	* @return List<PProductOrder>    返回类型
	 */
	List<PProductOrder> findAllByLikeCondition(PProductOrder proOrder);
	
	/**
	 * 
	* 根据某个条件模糊查询，并同时查询对应服务信息
	* @param @param proOrder
	* @param  productFlag  是否关联查询订购方式信息
	* @param  porderWaysFlag 是否关联查询订购方式信息
	* @param @return    参数
	* @return List<PProductOrder>    返回类型
	 */
	List<PProductOrder> findAllByLikeCondition(PProductOrder proOrder,boolean productFlag,boolean porderWaysFlag);
	
	/**
	 * 
	* 查询已审批订单
	* @param  参数
	* @return List<PProductOrder>    返回类型
	 */
	List<PProductOrder> findFinishTaskOrder(Map<Object,Object> map);
	
	/**
	 * 查询已办订单总数
	* (这里用一句话描述这个方法的作用)
	* @param  参数
	* @return Long    返回类型
	 */
	Long findFinishTaskTotalCount(PProductOrder proOrder);

	Long findTotalCountWithAuth(PProductOrder proOrder);

	Pagination allOrdersWithAuth(Pagination page, PProductOrder productOrder);
	
}
