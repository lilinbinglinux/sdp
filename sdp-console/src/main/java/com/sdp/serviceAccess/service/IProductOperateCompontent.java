/**
 * 
 */
package com.sdp.serviceAccess.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.glassfish.jersey.server.model.Producing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.mysql.jdbc.StringUtils;
import com.sdp.common.CurrentUserUtils;
import com.sdp.common.constant.Dictionary;
import com.sdp.common.entity.Status;
import com.sdp.serviceAccess.entity.PProduct;
import com.sdp.serviceAccess.entity.PProductCase;
import com.sdp.serviceAccess.entity.PProductNode;
import com.sdp.serviceAccess.entity.PProductOrder;
import com.sdp.serviceAccess.entity.ProductFieldItem;
import com.sdp.serviceAccess.entity.ProductRelyOnItem;
import com.sdp.serviceAccess.exception.ProductAccessException;
import com.sdp.serviceAccess.mapper.PProductCaseMapper;
import com.sdp.serviceAccess.mapper.PProductMapper;
import com.sdp.serviceAccess.mapper.PProductNodeMapper;
import com.sdp.serviceAccess.mapper.PProductOrderMapper;
import com.sdp.serviceAccess.protyTrans.ProptyTransDom4j;
import com.sdp.serviceAccess.protyTrans.ProtyTransContext;
import com.sdp.serviceAccess.proxy.OperationComponent;
import com.sdp.serviceAccess.proxy.ServiceOperationServcie;
import com.sdp.serviceAccess.proxy.model.CSvcBusiInfo;
import com.sdp.serviceAccess.util.ItemsConvertUtils;

/**   
 * Copyright: Copyright (c) 2018 BONC
 * 
 * @ClassName: IProductOperateCompontent.java
 * @Description: 服务对接层
 *
 * @version: v1.0.0
 * @author: renpengyuan
 * @date: 2018年8月10日 下午4:48:33 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年8月10日     renpengyuan      v1.0.0               修改原因
 */
@Component("productOperateComponent")
public class IProductOperateCompontent {

	@Autowired
	private PProductOrderMapper orderMapper;

	@Autowired
	private PProductMapper  productMapper;

	@Autowired
	private PProductCaseMapper caseMapper;

	@Autowired
	private ServiceOperationServcie  operaservice;

	@Autowired
	private IPProductCaseService caseService;

	@Autowired
	private PProductNodeMapper nodeMapper;

	@Transactional(rollbackFor=Exception.class)
	public Status createService(PProductOrder productOrder){
		Status status = null;
		String tenantId= productOrder.getTenantId();
		String productId = productOrder.getProductId();
		if(StringUtils.isNullOrEmpty(tenantId)||StringUtils.isNullOrEmpty(productId)){
			throw new IllegalArgumentException("tenantId or productId is null or empty");
		}
		List<ProductFieldItem> items= productOrder.getOrderBasicAttrOrm();
		Map<String,List<ProductFieldItem>> relyOnItems = productOrder.getOrderRelyOnBasicAttrOrm(); //  存在依赖服务的属性集合
		if(CollectionUtils.isEmpty(items)&&StringUtils.isNullOrEmpty(productOrder.getOrderAttr())&&(relyOnItems==null||relyOnItems.size()==0)){
			throw new ProductAccessException("attr is null");
		}

		try{
			PProduct param = new PProduct();
			param.setProductId(productId);
			PProduct product = productMapper.findById(param);
			if(product==null){
				throw new ProductAccessException("product service is null");
			}

			//2.用户服务实例信息
			PProductCase caseParam = new PProductCase();
			caseParam.setTenantId(tenantId);
			caseParam.setOrderId(productOrder.getOrderId());
			caseParam.setProductId(productOrder.getProductId());
			List<PProductCase> cases = caseMapper.findByCondition(caseParam);
			if(cases==null||cases.size()==0){
				throw new ProductAccessException("product service case is null");
			}
			caseParam = cases.get(0);
			if (product.getProductId().equalsIgnoreCase("bcm")) {
				status = new Status();
				status.setCode(Dictionary.HttpStatus.OK.value);
				caseParam.setCaseStatus(Dictionary.InstanceWorkState.RUNNING.value+"");
				caseMapper.update(caseParam);
				status = new Status("成功", Dictionary.ResultCode.SUCCESS.value);
				return status;
			}
			//3.封装请求信息
			CSvcBusiInfo cSvcBusiInfo = new CSvcBusiInfo();
			Map<String,String> relyOrder = null;
			if (caseParam!=null) {
				cSvcBusiInfo.setInstanceNo(caseParam.getCaseId());
				cSvcBusiInfo.setServiceId(caseParam.getProductId());
				cSvcBusiInfo.setServiceProvider(caseParam.getProductId());
				cSvcBusiInfo.setAsyn(caseParam.getProductId().equalsIgnoreCase("api")||caseParam.getProductId().equalsIgnoreCase("bdi")?false:true);
				cSvcBusiInfo.setServiceVersion(product.getProductVersion());
				cSvcBusiInfo.setUrl(product.getApiAddr());
				cSvcBusiInfo.setServiceName(product.getProductId());
				cSvcBusiInfo.setOrderServiceName(productOrder.getProductName());
				cSvcBusiInfo.setTenantId(caseParam.getTenantId());
				cSvcBusiInfo.setLoginId(caseParam.getCreateBy());
				cSvcBusiInfo.setIsRelyOnProduct(false);
				cSvcBusiInfo.setRelyOnAttrOrm(null);
				if(!StringUtils.isNullOrEmpty(caseParam.getCaseRelyShipAttr())) {
					cSvcBusiInfo.setIsRelyOnProduct(true);
					ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
					List<ProductRelyOnItem> relyShipOrm = ItemsConvertUtils.convertToRelyOnBean(context.transToBean(caseParam.getCaseRelyShipAttr()));
					cSvcBusiInfo.setRelyOnAttrOrm(relyShipOrm);
					if(relyShipOrm!=null&&relyShipOrm.size()>=1) {
						relyOrder= new HashMap<String,String>();
						for(ProductRelyOnItem relyship:relyShipOrm) {
							relyOrder.put(relyship.getRelyOnProductCode(), relyship.getRelyOnOrder());
						}
					}
				}
			}

			//4.封装申请属性
			Map<String, Object> params = packageRequestParams(!StringUtils.isNullOrEmpty(product.getRelyOnAttr()), productOrder.getOrderAttr(),relyOrder);
			//5.如果url为空或者不符合规则 ，则 判定不需要进行调用服务提供方,直接更新实例状态为运行
			if(!StringUtils.isNullOrEmpty(cSvcBusiInfo.getUrl())&&(cSvcBusiInfo.getUrl().contains("http://")||cSvcBusiInfo.getUrl().contains("https://"))){
				status = operaservice.createService(cSvcBusiInfo, params);
				caseParam.setCaseStatus(Dictionary.InstanceWorkState.STARTING.value+"");
				caseMapper.update(caseParam);
			}	
		}catch(Exception e){
			e.printStackTrace();
			throw new ProductAccessException("create service error", e);
		}
		return status;
	}


	@Transactional(rollbackFor=Exception.class)
	public Status changeResource(PProductCase productCase){
		
		String tenantId = CurrentUserUtils.getInstance().getUser().getTenantId();
		PProductCase productcase1  = caseMapper.findById(productCase);
		productcase1.setCaseAttrOrm(productCase.getCaseAttrOrm());
		productcase1.setOperaType(productCase.getOperaType());
		ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
		productcase1.setCaseAttr(context.transToXml(ItemsConvertUtils.convertToFieldPro(productcase1.getCaseAttrOrm()), "fieldItems", "fieldItem"));
		Status status = null;
		String productId = productcase1.getProductId();
		if(StringUtils.isNullOrEmpty(tenantId)||StringUtils.isNullOrEmpty(productId)){
			throw new IllegalArgumentException("tenantId or productId is null or empty");
		}
		try{
			PProduct param = new PProduct();
			param.setProductId(productId);
			PProduct product = productMapper.findById(param);
			if(product==null){
				throw new ProductAccessException("product service is null");
			}

			//3.封装请求信息
			CSvcBusiInfo cSvcBusiInfo = new CSvcBusiInfo();
			Map<String,String> relyOrder = null;
			if (productcase1!=null) {
				cSvcBusiInfo.setInstanceNo(productcase1.getCaseId());
				cSvcBusiInfo.setServiceId(productcase1.getProductId());
				cSvcBusiInfo.setServiceProvider(productcase1.getProductId());
				cSvcBusiInfo.setAsyn(productcase1.getProductId().equalsIgnoreCase("api")||productcase1.getProductId().equalsIgnoreCase("bdi")?false:true);
				cSvcBusiInfo.setServiceVersion(product.getProductVersion());
				cSvcBusiInfo.setUrl(product.getApiAddr());
				cSvcBusiInfo.setServiceName(product.getProductId());
				cSvcBusiInfo.setOrderServiceName(productcase1.getApplyName());
				cSvcBusiInfo.setTenantId(productcase1.getTenantId());
				cSvcBusiInfo.setLoginId(productcase1.getCreateBy());
				cSvcBusiInfo.setIsRelyOnProduct(false);
				cSvcBusiInfo.setRelyOnAttrOrm(null);
			}

			//4.封装申请属性
			Map<String, Object> params = packageRequestParams(false, productcase1.getCaseAttr(),relyOrder);
			//5.如果url为空或者不符合规则 ，则 判定不需要进行调用服务提供方,直接更新实例状态为运行
			if(!StringUtils.isNullOrEmpty(cSvcBusiInfo.getUrl())&&(cSvcBusiInfo.getUrl().contains("http://")||cSvcBusiInfo.getUrl().contains("https://"))){
				if("10".equals(productCase.getOperaType())) {
					status = operaservice.changeResource(cSvcBusiInfo, params);	
				}else if("20".equals(productCase.getOperaType())){
					status= operaservice.addDependency(cSvcBusiInfo, params);
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new ProductAccessException("change service resource error", e);
		}
		return status;
	}

	/**   
	 * @Function: IProductOperateCompontent.java
	 * @Description: 该函数的功能描述
	 *
	 * @param:描述1描述
	 * @return：返回结果描述
	 * @throws：异常描述
	 *
	 * @version: v1.0.0
	 * @author: renpengyuan-admin
	 * @date: 2018年8月10日 下午5:37:47 
	 *
	 * Modification History:
	 * Date         Author          Version            Description
	 *---------------------------------------------------------*
	 * 2018年8月10日     renpengyuan-admin           v1.0.0               修改原因
	 */
	@Transactional(rollbackFor=Exception.class)
	public Status startOrStop(PProductCase productCase){
		//TODO 暂不支持批量操作(批量操作需区分不同实例状态)
		Status status = new Status();

		String tenantId= CurrentUserUtils.getInstance().getUser().getTenantId();
		String operaToStatus = productCase.getCaseStatus();
		String caseId = productCase.getCaseId();

		if(StringUtils.isNullOrEmpty(tenantId)||StringUtils.isNullOrEmpty(operaToStatus)||StringUtils.isNullOrEmpty(caseId)){
			throw new IllegalArgumentException("tenantId or operaToStatus is null or empty");
		}

		try {

			PProductCase caseNow= caseMapper.findById(productCase);
			if (caseNow == null) {
				throw new ProductAccessException("case is empty");
			} else if (operaToStatus.equals(caseNow.getCaseStatus())) {
				status.setCode(Dictionary.HttpStatus.INVALID_REQUEST.value);
				status.setMessage("服务操作出错");
				return status;
			}

			// 更新服务为中间状态
			Integer nodeState = Dictionary.NodeState.STARTING.value;
			if (Dictionary.InstanceWorkState.RUNNING.value == Integer.parseInt(operaToStatus)) {
				caseNow.setCaseStatus(String.valueOf(Dictionary.InstanceWorkState.STARTING.value));
			} else if (Dictionary.InstanceWorkState.STOP.value == Integer.parseInt(operaToStatus)) {
				if(caseNow.getCaseStatus().equals(Dictionary.InstanceWorkState.RUNNING.value+"")) {
					caseNow.setCaseStatus(String.valueOf(Dictionary.InstanceWorkState.STOPING.value));
					nodeState = Dictionary.NodeState.STOPING.value;
				}else {
					status.setCode(Dictionary.HttpStatus.INVALID_REQUEST.value);
					status.setMessage("服务操作出错");
					return status;
				}

			}
			// 获取实例节点信息,并根据状态更新
			PProductNode nodeParam = new PProductNode();
			nodeParam.setTenantId(tenantId);
			nodeParam.setCaseId(productCase.getCaseId());
			List<PProductNode> nodes = nodeMapper.findByCondition(nodeParam);
			if (CollectionUtils.isNotEmpty(nodes)) {
				for (PProductNode oneRow : nodes) {
					oneRow.setCaseStatus(operaToStatus);
					nodeMapper.update((oneRow));
				}
			}
			caseNow.setTenantId(tenantId);
			caseNow.setCreateBy(CurrentUserUtils.getInstance().getUser().getLoginId());
			caseNow.setCreateDate(new Date());
			caseMapper.update(caseNow);


			status = updateService(packageBusiInfo(caseNow,"startOrStop"), operaToStatus);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProductAccessException("updateSvcInstanceState");
		}
		return status;

	}

	//更新服务
	private Status updateService(CSvcBusiInfo cSvcBusiInfo,String operaToStatus){
		String productId = cSvcBusiInfo.getServiceId();
		if(productId.equalsIgnoreCase("bcm")){
			PProductCase oldCase = new PProductCase();
			oldCase.setTenantId(cSvcBusiInfo.getTenantId());
			oldCase.setCaseId(cSvcBusiInfo.getInstanceNo());
			oldCase = caseMapper.findById(oldCase);
			oldCase.setCaseStatus(operaToStatus);
			oldCase.setTenantId(cSvcBusiInfo.getTenantId());
			oldCase.setUpdateBy(cSvcBusiInfo.getLoginId());
			caseMapper.update(oldCase);
			return new Status("修改成功", HttpStatus.OK.value());
		}else{
			if (Dictionary.InstanceOperation.STARTING.value == Integer.parseInt(operaToStatus)) {
				return  operaservice.startService(cSvcBusiInfo);
			} else if (Dictionary.InstanceOperation.STOPOING.value == Integer.parseInt(operaToStatus)) {
				return operaservice.stopService(cSvcBusiInfo);
			}
		}
		return null;
	}

	@Transactional(rollbackFor=Exception.class)
	public Status deleteService(PProductCase productCase) {
		// 如果没有查到，返回未找到异常信息
		if (null == productCase) {
			throw new IllegalArgumentException("productcase is null");
		}
		try {
			Status status = new Status("已删除", HttpStatus.OK.value());
			productCase = caseMapper.findById(productCase);
			CSvcBusiInfo busiInfo = packageBusiInfo(productCase, "delete");
			if (!productCase.getProductId().equalsIgnoreCase("bcm")
					&& !productCase.getProductId().equalsIgnoreCase("api")) {
				status = operaservice.deleteService(busiInfo);
				if (status.getCode() == 0) {
					productCase.setCaseStatus(Dictionary.InstanceWorkState.STOPING + "");
					caseMapper.update(productCase);
				}
			} else {
				caseMapper.delete(productCase);
			}
			return status;
		} catch (Exception ex) {
			throw new ProductAccessException("deleteSvcInstanceById");
		}
	}

	/**
	 * 封装请求实体
	 *
	 * @param instanceInfo
	 * @return
	 */
	public CSvcBusiInfo packageBusiInfo(PProductCase productCase,String type) {
		CSvcBusiInfo busiInfo = new CSvcBusiInfo();
		PProduct product = new PProduct();
		product.setProductId(productCase.getProductId());
		product=  productMapper.findById(product);
		if (product == null) {
			throw new ProductAccessException("service is not find");
		}

		PProductOrder pOrder = new PProductOrder();
		pOrder.setOrderId(productCase.getOrderId());
		PProductOrder order = orderMapper.findById(pOrder);
		if(order==null){
			throw new ProductAccessException("order is null");
		}
		//// TODO: 2018/1/19  服务或服务提供方表缺少响应方式（同步、异步）字段
		// 暂时先默认异步
		busiInfo.setAsyn(true);
		if("status".equals(type)){
			busiInfo.setAsyn(false);
		}
		busiInfo.setServiceVersion(product.getProductVersion());
		busiInfo.setInstanceNo(productCase.getCaseId());
		busiInfo.setTenantId(productCase.getTenantId());
		//zy 2018/7/24 修改为传服务编码
		//busiInfo.setServiceName(serviceInfo.getSvcName());
		busiInfo.setServiceName(product.getProductId());
		busiInfo.setOrderServiceName(pOrder.getProductName());
		busiInfo.setUrl(product.getApiAddr());
		busiInfo.setServiceId(product.getProductId());
		//zy 2018/7/24 修改为传服务提供商编码
		//busiInfo.setServiceProvider(serviceInfo.getSvcProviderInfo().getProviderName());
		busiInfo.setServiceProvider(product.getProductId());
		//busiInfo.setLoginId(CurrentUserUtils.getInstance().getUser().getLoginId());
		return busiInfo;
	}

	private Map<String,Object> packageRequestParams(boolean isRelyOn,String orderAttr,Map<String,String> relyOnOrder){
		Map<String,Object> params = new HashMap<String,Object>();
		if(!StringUtils.isNullOrEmpty(orderAttr)){
			ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
			if(!isRelyOn) { //判断是否为依赖服务
				//不是依赖服务
				List<ProductFieldItem> items= ItemsConvertUtils.convertToFieldBean(context.transToBean(orderAttr));
				if (CollectionUtils.isNotEmpty(items)) {
					for(ProductFieldItem item:items){
						if(item.getProLabel().contains("10")){
							params.put(item.getProEnName(), item.getProValue());
						}
					}
				}
			}else {
				//是依赖服务
				Map<String,List<ProductFieldItem>> relyOnItems= ItemsConvertUtils.convertToFieldBeanRelyOn(context.transToBean(orderAttr));
				if(relyOnItems!=null&&relyOnItems.size()>=1) {
					for(String key:relyOnItems.keySet()) {
						List<ProductFieldItem> singleProductItems = relyOnItems.get(key);
						if(CollectionUtils.isNotEmpty(singleProductItems)) {
							Map<String,String> properties = new HashMap<String,String>();
							params.put(key, properties);
							for(ProductFieldItem item:singleProductItems) {
								if(item.getProLabel().contains("10")){
									properties.put(item.getProEnName(), item.getProValue());
								}
							}
							if(relyOnOrder!=null&&relyOnOrder.size()>=1) {
								properties.put("relyOnSort", relyOnOrder.get(key));
							}
						}
					}
				}
			}
		}
		return params;
	}
}
