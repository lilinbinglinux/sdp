/**
 * 
 */
package com.sdp.serviceAccess.controller.rest;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sdp.common.CurrentUserUtils;
import com.sdp.common.entity.Status;
import com.sdp.common.page.Pagination;
import com.sdp.serviceAccess.entity.POrderWays;
import com.sdp.serviceAccess.entity.PProductCase;
import com.sdp.serviceAccess.entity.PProductOrder;
import com.sdp.serviceAccess.exception.ProductAccessException;
import com.sdp.serviceAccess.mapper.PProductCaseMapper;
import com.sdp.serviceAccess.mapper.PProductOrderMapper;
import com.sdp.serviceAccess.service.IPOrderWaysService;
import com.sdp.serviceAccess.service.IPProductOrderService;
import com.sdp.serviceAccess.service.IProductOperateCompontent;
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
@RestController
@RequestMapping("/api/productOrder")
public class RestPProductOrderController {

	private static final Logger LOG = LoggerFactory.getLogger(RestPProductOrderController.class);

	@Autowired
	private IPProductOrderService orderService;

	@Autowired
	private PProductOrderMapper orderMapper;

	@Autowired
	private IProductOperateCompontent operaService;

	@Autowired
	private PProductCaseMapper caseMapper;
	
	@Autowired
	private IPOrderWaysService waysConfig;

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
	public ResponseEntity<Pagination> auth_allProducts(@RequestBody Map<String,Object> param) throws Exception{
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

	/**
	 * 
	 * apply to API
	 * @param @param product
	 * @param @param productIcon
	 * @param @return    参数
	 * @return ResponseEntity<Status>    返回类型  
	 */
	@RequestMapping(value={"/{userId}/{tenantId}/apply/api"},method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Status> resgProduct(@RequestBody PProductOrder productOrder,@PathVariable("userId")String userId,@PathVariable("tenantId")String tenantId){
		if(!userId.equals(productOrder.getCreateBy())||!tenantId.equals(productOrder.getTenantId())){
			throw new ProductAccessException("apply error,desc: userId or tenantId is vaild");
		}
		return new ResponseEntity<Status>(orderService.createProductOrder(productOrder,false),HttpStatus.OK);
	}

	@RequestMapping(value={"/test/{operaType}/{orderId}"})
	@ResponseBody
	public ResponseEntity<Status> operaService(@PathVariable("operaType") String operaType,@PathVariable("orderId")String orderId){
		Status status=null;
		try{

			if("create".equals(operaType)){
				PProductOrder order = new PProductOrder();
				order.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
				order.setOrderId(orderId);
				order = orderMapper.findById(order);
				status=operaService.createService(order);
			}else if("stop".equals(operaType)){
				PProductCase pCase= new PProductCase();
				pCase.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
				pCase.setOrderId(orderId);
				List<PProductCase> cases = caseMapper.findByCondition(pCase);
				cases.get(0).setCaseStatus("30");
				status=operaService.startOrStop(cases.get(0));
			}else if("start".equals(operaType)){
				PProductCase pCase= new PProductCase();
				pCase.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
				pCase.setOrderId(orderId);
				List<PProductCase> cases = caseMapper.findByCondition(pCase);
				cases.get(0).setCaseStatus("20");
				status=operaService.startOrStop(cases.get(0));
			}else if("delete".equals(operaType)){
				PProductCase pCase= new PProductCase();
				pCase.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
				pCase.setOrderId(orderId);
				List<PProductCase>  cases = caseMapper.findByCondition(pCase);
				cases.get(0).setCaseStatus("1");
				status=operaService.deleteService(cases.get(0));
			}
		}catch(Exception e){
			LOG.error(e.getMessage());
			throw e;
		}
		return new ResponseEntity<>(status,HttpStatus.OK);
	}

	/**获取配置流程实例id*/
	@RequestMapping(value={"/{productId}/{tenantId}/waysConfig"},method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<POrderWays> waysConfig(@PathVariable("productId") String productId,@PathVariable("tenantId") String tenantId){
		try{
			return new ResponseEntity<POrderWays>(waysConfig.findByPriId("4"),HttpStatus.OK);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw e;
		}
	}	
	
	/**
	 * 获取所有订单
	 * @param @param page
	 * @param @param productOrder
	 * @param @return    参数
	 * @return ResponseEntity<Pagination>    返回类型
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/findTotalCountWithAuth"},method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Long> auth_findTotalCountWithAuth(@RequestBody Map<String,Object> param) throws Exception{
		try{
			PProductOrder productOrder = JsonXMLUtils.map2obj((Map<String, Object>)param.get("productOrder"),PProductOrder.class); 
			return new ResponseEntity<Long>(orderService.findTotalCountWithAuth(productOrder),HttpStatus.OK);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/allOrdersWithAuth"},method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Pagination> auth_allProductsWithAuth(@RequestBody Map<String,Object> param) throws Exception{
		try{
			Pagination page = JsonXMLUtils.map2obj((Map<String, Object>)param.get("page"),Pagination.class); 
			PProductOrder productOrder = JsonXMLUtils.map2obj((Map<String, Object>)param.get("productOrder"),PProductOrder.class); 
			return new ResponseEntity<Pagination>(orderService.allOrdersWithAuth(page, productOrder),HttpStatus.OK);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw e;
		}
	}
}
