/**
 * 
 */
package com.sdp.serviceAccess.controller;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.entity.Status;
import com.sdp.common.page.Pagination;
import com.sdp.serviceAccess.entity.PProductOrder;
import com.sdp.serviceAccess.service.IPOrderWaysService;
import com.sdp.serviceAccess.service.IPProductOrderService;
import com.sdp.serviceAccess.util.JsonXMLUtils;

/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: PProductOrderController.java
* @Description: 订单控制类
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年8月2日 下午4:34:38 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月2日     renpengyuan      v1.0.0               修改原因
*/
@Controller
@RequestMapping("/v1/pro/productOrder")
public class PProductOrderController {
	
    private static final Logger LOG = LoggerFactory.getLogger(PProductOrderController.class);
	
	@Autowired
	private IPProductOrderService orderService;
	
	@Autowired
	private IPOrderWaysService payWays;
    
	@RequestMapping(value={"/page"},method=RequestMethod.GET)
	public String toPage(){
		return "product/order";
	}
	
	/**
	* 获取所有订单
	* @param @param page
	* @param @param productOrder
	* @param @return    参数
	* @return ResponseEntity<Pagination>    返回类型
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/allOrders"},method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Pagination> allProducts(@RequestBody Map<String,Object> param) throws Exception{
	    try{
	    	Pagination page = JsonXMLUtils.map2obj((Map<String, Object>)param.get("page"),Pagination.class); 
	    	PProductOrder productOrder = JsonXMLUtils.map2obj((Map<String, Object>)param.get("productOrder"),PProductOrder.class); 
	    		return new ResponseEntity<Pagination>(orderService.allOrders(page, productOrder),HttpStatus.OK);
	    }catch(Exception e){
	    		LOG.error(e.getMessage(),e);
	    		throw e;
	    }
	}
	
	/**
	 * 
	* 根据订单Id查看订单信息信息
	* @param @param orderId
	* @param @return    参数
	* @return ResponseEntity<PProductOrder>    返回类型
	 */
	@RequestMapping(value={"/singleOrder/{orderId}"},method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<PProductOrder> singleProduct(@PathVariable("orderId")String orderId ){
		try{
			PProductOrder productOrder = new PProductOrder();
			productOrder.setOrderId(orderId);
	    		return new ResponseEntity<PProductOrder>(orderService.singleOrder(productOrder),HttpStatus.OK);
	    }catch(Exception e){
	    	LOG.error(e.getMessage(),e);
	    	throw e;
	    }
	}
	
	/**
	 * 
	* 保存服务申请信息
	* @param @param product
	* @param @param productIcon
	* @param @return    参数
	* @return ResponseEntity<Status>    返回类型
	 */
	@RequestMapping(value={"/apply"},method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Status> resgProduct(@RequestBody PProductOrder productOrder){
		try{
	    	return new ResponseEntity<Status>(orderService.createProductOrder(productOrder,true),HttpStatus.OK);
	    }catch(Exception e){
	    	LOG.error(e.getMessage(),e);
	    	throw e;
	    }
	}	
}
