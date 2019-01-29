/**
 * 
 */
package com.sdp.serviceAccess.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.StringUtils;
import com.sdp.common.CurrentUserUtils;
import com.sdp.common.constant.Dictionary;
import com.sdp.common.entity.Status;
import com.sdp.common.entity.Status.Status1;
import com.sdp.common.page.Pagination;
import com.sdp.serviceAccess.entity.POrderWays;
import com.sdp.serviceAccess.entity.PProduct;
import com.sdp.serviceAccess.entity.PProductCase;
import com.sdp.serviceAccess.entity.PProductOrder;
import com.sdp.serviceAccess.entity.ProductFieldItem;
import com.sdp.serviceAccess.exception.ProductAccessException;
import com.sdp.serviceAccess.mapper.POrderWaysMapper;
import com.sdp.serviceAccess.mapper.PProductMapper;
import com.sdp.serviceAccess.mapper.PProductOrderMapper;
import com.sdp.serviceAccess.protyTrans.ProptyTransDom4j;
import com.sdp.serviceAccess.protyTrans.ProtyTransContext;
import com.sdp.serviceAccess.service.IBPMService;
import com.sdp.serviceAccess.service.IPProductCaseService;
import com.sdp.serviceAccess.service.IPProductOrderService;
import com.sdp.serviceAccess.service.IPProductPackageService;
import com.sdp.serviceAccess.service.IPProductService;
import com.sdp.serviceAccess.service.IProductOperateCompontent;
import com.sdp.serviceAccess.util.BaseUtilsService;
import com.sdp.serviceAccess.util.Generator;
import com.sdp.serviceAccess.util.ItemsConvertUtils;
import com.sdp.serviceAccess.util.MapObjUtil;
import com.sdp.util.DateUtils;

/**
 * Copyright: Copyright (c) 2018 BONC
 * 
 * @ClassName: PProductOrderServiceImpl.java
 * @Description: see IPProductOrderService
 *
 * @version: v1.0.0
 * @author: renpengyuan/zy
 * @date: 2018年8月2日 下午4:07:11
 *
 *        Modification History: Date Author Version Description
 *        ---------------------------------------------------------* 2018年8月2日
 *        renpengyuan v1.0.0 修改原因 2018年8月2日 zy 添加方法
 */
@Service
public class PProductOrderServiceImpl implements IPProductOrderService {
	@Autowired
	private PProductOrderMapper productOrderMapper;

	@Autowired
	private PProductMapper pProductMapper;

	@Autowired
	private POrderWaysMapper pOrderWaysMapper;

	@Autowired
	private IBPMService bpmService;

	@Autowired
	private IPProductCaseService productCaseService;

	@Autowired
	private IProductOperateCompontent productOperateService;

	@Autowired
	private IPProductPackageService packageService;

	@Autowired
	private IPProductService productService;

	/**
	 * bpm流程跟踪信息地址
	 */
	@Value("${bpm_monitor_process}")
	private String bpm_monitor_process = "";

	@Override
	public Pagination allOrders(Pagination page, PProductOrder productOrder) {
		try {
			String tenantId = CurrentUserUtils.getInstance().getDataUser()
					.getTenantId();
			String loginId = CurrentUserUtils.getInstance().getDataUser()
					.getLoginId();
			productOrder.setTenantId(tenantId);
			productOrder.setCreateBy(loginId);
			Long total = productOrderMapper.findTotalCount(productOrder);
			int startNum = (int) (page.getPageSize() * (page.getPageNo() - 1));
			int num = page.getPageSize();
			Map<Object, Object> map = MapObjUtil.objectToMap(productOrder);
			map.put("startNum", startNum);
			map.put("num", num);
			List<PProductOrder> products = productOrderMapper
					.findPageAllByCondition(map);
			page.setList(products);
			page.setTotalCount(total);
		} catch (Exception e) {
			throw new ProductAccessException(e.getMessage(), e);
		}
		return page;
	}

	@Override
	public PProductOrder singleOrder(PProductOrder productOrder) {
		String tenantId = CurrentUserUtils.getInstance().getUser()
				.getTenantId();
		String productOrderId = productOrder.getOrderId();
		if (StringUtils.isNullOrEmpty(tenantId)
				|| StringUtils.isNullOrEmpty(productOrderId)) {
			throw new IllegalArgumentException("param is null or blank");
		}
		try {
			productOrder.setOrderId(productOrderId);
			productOrder = productOrderMapper.findById(productOrder);
			ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
			// 解析属性xml
			if (null == productOrder) {
				return new PProductOrder();
			}
			String orderAttr = productOrder.getOrderAttr();
			if (!StringUtils.isNullOrEmpty(orderAttr)) {
				if(packageService.isRelyOnProduct(productOrder.getProductId(), productOrder.getTenantId())) {
					//说明是依赖服务
					productOrder.setOrderRelyOnBasicAttrOrm(ItemsConvertUtils.convertToFieldBeanRelyOn(context.transToBean(orderAttr)));
					productOrder.setOrderRelyOnBasicAttr(null);
				}else {
					List<ProductFieldItem> fieldItems= ItemsConvertUtils.convertToFieldBean(context.transToBean(orderAttr));
					productOrder.setOrderBasicAttrOrm(fieldItems);
					productOrder.setOrderAttr(null);
				}
			}
			orderAttr = productOrder.getOrderControlAttr();
			if (!StringUtils.isNullOrEmpty(orderAttr)) {
				productOrder.setOrderControlResAttrOrm(ItemsConvertUtils
						.convertToFieldBean(context.transToBean(orderAttr)));
			}
			// 对应服务信息
			PProduct pProduct = new PProduct();
			pProduct.setProductId(productOrder.getProductId());
			// pProduct.setTenantId(productOrder.getTenantId());
			pProduct = (PProduct) pProductMapper.findById(pProduct);
			productOrder.setpProduct(pProduct);

			// 对应订购信息
			POrderWays porderWays = new POrderWays(productOrder.getPwaysId());
			porderWays = (POrderWays) pOrderWaysMapper.findById(porderWays);
			productOrder.setpOrderWays(porderWays);

			// 如果订单是审批类型，则需要返回审批流程信息
			if (Dictionary.Ways.ORDER_APPROVE.value == Integer
					.parseInt(porderWays.getPwaysType())) {
				productOrder.setMonitorProcessUrl(bpm_monitor_process
						+ "?params.pDefId=" + productOrder.getBpmModelConfig()
						+ "&params.pId=" + productOrder.getBpmModelNo());
			}

		} catch (Exception e) {
			throw new ProductAccessException(e.getMessage(), e);
		}
		return productOrder;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Status createProductOrder(PProductOrder productOrder,
			boolean isBaseInfo) {
		Status1 status1 = new Status().instanceStatus1();
		String loginId = null;
		if (productOrder == null) {
			throw new IllegalArgumentException("productOrder is null");
		}
		try {
			List<ProductFieldItem> attrItems = productOrder
					.getOrderBasicAttrOrm();
			ProtyTransContext context = new ProtyTransContext(
					new ProptyTransDom4j());
			if (attrItems != null && attrItems.size() >= 1) {
				for (ProductFieldItem productFieldItem : attrItems) {
					if("applyName".equals(productFieldItem.getProCode())) {
						productOrder.setApplyName(productFieldItem.getProValue());
					}
				}
				productOrder.setOrderAttr(context.transToXml(
						ItemsConvertUtils.convertToFieldPro(attrItems), "fieldItems",
						"fieldItem"));
			}
			attrItems = productOrder.getOrderControlResAttrOrm();
			if (attrItems != null && attrItems.size() >= 1) {
				productOrder.setOrderControlAttr(context.transToXml(
						ItemsConvertUtils.convertToFieldPro(attrItems), "fieldItems",
						"fieldItem"));
			}
			Map<String,List<ProductFieldItem>> relyOnAttrOrm = productOrder.getOrderRelyOnBasicAttrOrm();
			if(relyOnAttrOrm!=null&&relyOnAttrOrm.size()>=1) {
				productOrder.setOrderAttr(context.transToXml(ItemsConvertUtils.convertToFieldPro(relyOnAttrOrm), "fieldItems", "fieldItem"));
			}
			if(isBaseInfo){
				BaseUtilsService.saveBaseInfo(productOrder, Dictionary.Directive.SAVE.value);
				loginId = CurrentUserUtils.getInstance().getUser().getLoginId();
			}else{
				productOrder.setCreateDate(DateUtils.getCurrentDate());
				productOrder.setUpdateDate(DateUtils.getCurrentDate());
				productOrder.setUpdateBy(productOrder.getCreateBy());
				loginId = productOrder.getCreateBy();
			}
			Generator idWorker = new Generator(1, 0);
			String orderId = idWorker.nextId() + "";
			productOrder.setOrderId(orderId);

			// TODO 验证配额信息
			productOrderMapper.saveSingle(productOrder);

			POrderWays pOrderWays = new POrderWays(productOrder.getPwaysId());
			pOrderWays = pOrderWaysMapper.findById(pOrderWays);

			// 保存服务实例信息
			PProductCase productCase = new PProductCase();
			productCase
			.setCaseId(UUID.randomUUID().toString().replace("-", "").substring(0, 10));
			productCase.setOrderId(orderId);
			productCase.setProductId(productOrder.getProductId());
			productCase.setProductName(productOrder.getProductName());
			if (!StringUtils.isNullOrEmpty(productOrder.getOrderAttr())) {
				productCase.setCaseAttr(productOrder.getOrderAttr());
			}
			productCase.setCaseStatus(
					Dictionary.InstanceWorkState.UNCREATE.value + "");
			if(isBaseInfo){
				BaseUtilsService.saveBaseInfo(productCase, Dictionary.Directive.SAVE.value);
			}else{
				productCase.setCreateDate(DateUtils.getCurrentDate());
				productCase.setCreateBy(productOrder.getCreateBy());
				productCase.setUpdateDate(DateUtils.getCurrentDate());
				productCase.setUpdateBy(productOrder.getCreateBy());
				productCase.setTenantId(productOrder.getTenantId());
			}
			productCase.setApplyName(productOrder.getApplyName());
			productCaseService.saveCase(productCase,isBaseInfo);
			// 启动bpm流程
			if (Integer.parseInt(pOrderWays
					.getPwaysType()) == Dictionary.Ways.ORDER_APPROVE.value) {
				bpmService.bpmStartProcess(productOrder.getOrderId(),loginId);
			}
			// 自动开通创建实例
			if (Integer.parseInt(pOrderWays
					.getPwaysType()) == Dictionary.Ways.PROBATION.value) {
				productOperateService.createService(productOrder);
			}
			status1.setCode(200);
			status1.setMessage("创建成功");
			status1.setOrderId(orderId);
		} catch (Exception e) {
			throw new ProductAccessException(e.getMessage(), e);

		}
		return status1;
	}

	@Override
	public PProductOrder findByPriId(String orderId) {
		PProductOrder porder = new PProductOrder(orderId);
		return (PProductOrder) productOrderMapper.findById(porder);
	}

	@Override
	public Status updateProOrder(PProductOrder productOrder) {
		Status status;
		try {
			productOrderMapper.update(productOrder);
			status = new Status("创建成功", 200);
		} catch (Exception e) {
			throw new ProductAccessException("updateProItems error", e);
		}
		return status;
	}

	@Override
	public List<PProductOrder> findAllByCondition(PProductOrder productOrder) {
		return productOrderMapper.findAllByCondition(productOrder);
	}

	/**
	 * 根据某个条件模糊查询
	 * 
	 * @see com.sdp.serviceAccess.service.IPProductOrderService#findAllByLikeCondition(com.sdp.serviceAccess.entity.PProductOrder)
	 */
	@Override
	public List<PProductOrder> findAllByLikeCondition(PProductOrder proOrder) {
		return productOrderMapper.findAllByLikeCondition(proOrder);
	}

	/**
	 * 根据某个条件模糊查询 (关联查询服务信息/关联订购方式)
	 * 
	 * @see com.sdp.serviceAccess.service.IPProductOrderService#findAllByLikeCondition(com.sdp.serviceAccess.entity.PProductOrder)
	 */
	@Override
	public List<PProductOrder> findAllByLikeCondition(PProductOrder proOrder,
			boolean productFlag, boolean porderWaysFlag) {
		List<PProductOrder> proOrders = productOrderMapper
				.findAllByLikeCondition(proOrder);
		List<PProductOrder> returnProOrders = new ArrayList<PProductOrder>();
		// 选择性进行多次关联查询
		if (null != proOrders) {
			for (PProductOrder itemOrder : proOrders) {
				if (productFlag) {
					PProduct product = new PProduct(itemOrder.getProductId());
					// product.setTenantId(itemOrder.getTenantId());
					product = (PProduct) pProductMapper.findById(product);
					itemOrder.setpProduct(product);
				}
				if (porderWaysFlag) {
					POrderWays pOrderWays = new POrderWays(
							itemOrder.getPwaysId());
					pOrderWays = (POrderWays) pOrderWaysMapper
							.findById(pOrderWays);
					itemOrder.setpOrderWays(pOrderWays);
				}
				returnProOrders.add(itemOrder);
			}
		}
		return returnProOrders;
	}

	@Override
	public List<PProductOrder> findFinishTaskOrder(Map<Object, Object> map) {
		return productOrderMapper.findFinishTaskOrder(map);
	}

	@Override
	public Long findFinishTaskTotalCount(PProductOrder proOrder) {
		return productOrderMapper.findFinishTaskTotalCount(proOrder);
	}

	@Override
	public Long findTotalCountWithAuth(PProductOrder proOrder) {
		try {
			Map<Object, Object> paraMap = MapObjUtil.objectToMap(proOrder);
			paraMap.put("tenantId", CurrentUserUtils.getInstance().getDataUser().getTenantId());
			paraMap.put("LoginId", CurrentUserUtils.getInstance().getDataUser().getLoginId());
			if (Dictionary.AuthToken.Provider.value
					.equals(CurrentUserUtils.getInstance().getDataUser().getDataAuth())) {
				paraMap.put("providerId", CurrentUserUtils.getInstance().getUser().getLoginId());
			}
			return productOrderMapper.findTotalCountAuth(paraMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Pagination allOrdersWithAuth(Pagination page, PProductOrder productOrder) {
		try {
			Map<Object, Object> paraMap = MapObjUtil.objectToMap(productOrder);
			paraMap.put("tenantId", CurrentUserUtils.getInstance().getDataUser().getTenantId());
			paraMap.put("LoginId", CurrentUserUtils.getInstance().getDataUser().getLoginId());
			if (Dictionary.AuthToken.Provider.value
					.equals(CurrentUserUtils.getInstance().getDataUser().getDataAuth())) {
				paraMap.put("providerId", CurrentUserUtils.getInstance().getUser().getLoginId());
			}
			Long total = productOrderMapper.findTotalCountAuth(paraMap);
			int startNum = (int) (page.getPageSize() * (page.getPageNo() - 1));
			int num = page.getPageSize();
			paraMap.put("startNum", startNum);
			paraMap.put("num", num);
			List<PProductOrder> products = productOrderMapper
					.findPageAllByConditionWithAuth(paraMap);
			page.setList(products);
			page.setTotalCount(total);
		} catch (Exception e) {
			throw new ProductAccessException(e.getMessage(), e);
		}
		return page;
	}
}
