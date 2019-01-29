/**
 *
 */
package com.sdp.serviceAccess.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.StringUtils;
import com.sdp.common.CurrentUserUtils;
import com.sdp.common.constant.Dictionary;
import com.sdp.common.entity.Status;
import com.sdp.common.page.Pagination;
import com.sdp.serviceAccess.entity.PProduct;
import com.sdp.serviceAccess.entity.PProductCase;
import com.sdp.serviceAccess.entity.PProductNode;
import com.sdp.serviceAccess.entity.ProductFieldItem;
import com.sdp.serviceAccess.entity.ProductRelyOnItem;
import com.sdp.serviceAccess.exception.ProductAccessException;
import com.sdp.serviceAccess.mapper.PProductCaseMapper;
import com.sdp.serviceAccess.mapper.PProductMapper;
import com.sdp.serviceAccess.mapper.PProductNodeMapper;
import com.sdp.serviceAccess.protyTrans.ProptyTransDom4j;
import com.sdp.serviceAccess.protyTrans.ProtyTransContext;
import com.sdp.serviceAccess.proxy.ServiceOperationServcie;
import com.sdp.serviceAccess.proxy.model.CSvcBusiInfo;
import com.sdp.serviceAccess.service.IPProductCaseService;
import com.sdp.serviceAccess.service.IPProductNodeService;
import com.sdp.serviceAccess.service.IProductOperateCompontent;
import com.sdp.serviceAccess.util.BaseUtilsService;
import com.sdp.serviceAccess.util.ItemsConvertUtils;
import com.sdp.serviceAccess.util.MapObjUtil;
import com.sdp.util.DateUtils;

import io.swagger.models.auth.In;
import net.sf.json.JSONObject;

/**
 * Copyright: Copyright (c) 2018 BONC
 *
 * @ClassName: PProductCaseServiceImpl.java
 * @Description: see IPProductCaseService
 *
 * @version: v1.0.0
 * @author: renpengyuan
 * @date: 2018年8月2日 下午4:09:57
 *
 *        Modification History: Date Author Version Description
 *        ---------------------------------------------------------* 2018年8月2日
 *        renpengyuan v1.0.0 修改原因
 */
@Service
public class PProductCaseServiceImpl implements IPProductCaseService {

	@Autowired
	private PProductCaseMapper productCaseMapper;

	@Autowired
	private PProductMapper productMapper;

	@Autowired
	private IProductOperateCompontent operCompontent;

	@Autowired
	private ServiceOperationServcie operService;
	
	@Autowired
	private IPProductNodeService nodeService;
	
	@Autowired
	private PProductNodeMapper nodeMapper;

	private static final ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());

	private static final Logger LOG = LoggerFactory.getLogger(PProductCaseServiceImpl.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.bonc.serviceAccess.service.IPProductCaseService#saveCase(com.bonc.
	 * serviceAccess.entity.PProductCase)
	 */
	@Override
	public Status saveCase(PProductCase productCase, boolean isCurrentUserFlag) {
		Status status;

		if (productCase == null) {
			throw new IllegalArgumentException("productCase is null");
		}

		String orderId = productCase.getOrderId();
		try {
			// TODORENPENGYAUN

			// 将依赖服务属性进行持久化
			PProduct product = new PProduct();
			product.setProductId(productCase.getProductId());
			product = productMapper.findById(product);
			String productRelyOnAttr = product.getRelyOnAttr();
			if (!StringUtils.isNullOrEmpty(productRelyOnAttr)) {
				// 通过当前实例的实例属性找出依赖服务的依赖链
				// 先通过productCase判断是否为依赖服务。如果是，则 将 productCase Attr
				// 拆分为所有依赖服务的所有属性，并且通过
				// 遍历的方式把当前服务依赖的Case 进行持久化，在实例里面进行服务实例的依赖数据持久化。
				Map<String, List<ProductFieldItem>> relyPros = ItemsConvertUtils
						.convertToFieldBeanRelyOn(context.transToBean(productCase.getCaseAttr()));
				List<ProductRelyOnItem> relyOnItems = ItemsConvertUtils
						.convertToRelyOnBean(context.transToBean(productRelyOnAttr));
				if (relyOnItems != null && relyOnItems.size() >= 1 && relyPros != null && relyPros.size() >= 1) {
					for (ProductRelyOnItem relyItem : relyOnItems) {
						PProductCase relyCase = new PProductCase();
						relyCase.setCaseId(UUID.randomUUID().toString().replace("-", "").substring(0, 10));
						relyItem.setRelyOnCaseId(relyCase.getCaseId());
						relyCase.setOrderId(orderId);
						relyCase.setProductId(relyItem.getRelyOnProductCode());
						relyCase.setProductName(productCase.getProductName() + "_" + relyItem.getRelyOnProductCode());
//						relyCase.setIsShow(relyItem.getIsShowRelyOnPros().equals("10") ? "10" : "20");
						relyCase.setIsShow("20");
						if (CollectionUtils.isNotEmpty(relyPros.get(relyItem.getRelyOnProductCode()))) {
							relyCase.setCaseAttr(context.transToXml(
									ItemsConvertUtils.convertToFieldPro(relyPros.get(relyItem.getRelyOnProductCode())),
									"fieldItems", "fieldItem"));
						}
						relyCase.setCaseStatus(Dictionary.InstanceWorkState.UNCREATE.value + "");
						if (isCurrentUserFlag) {
							BaseUtilsService.saveBaseInfo(relyCase, Dictionary.Directive.SAVE.value);
						} else {
							relyCase.setCreateDate(DateUtils.getCurrentDate());
							relyCase.setCreateBy(productCase.getCreateBy());
							relyCase.setUpdateDate(DateUtils.getCurrentDate());
							relyCase.setUpdateBy(productCase.getCreateBy());
							relyCase.setTenantId(productCase.getTenantId());
						}
						relyCase.setApplyName(productCase.getApplyName());
						productCaseMapper.saveSingle(relyCase);
					}
					if (CollectionUtils.isNotEmpty(relyPros.get(productCase.getProductId()))) {
						productCase.setCaseAttr(context.transToXml(
								ItemsConvertUtils.convertToFieldPro(relyPros.get(productCase.getProductId())),
								"fieldItems", "fieldItem"));
						productCase.setCaseRelyShipAttr(context.transToXml(
								ItemsConvertUtils.convertToRelyOnPro(relyOnItems), "fieldItems", "fieldItem"));
					}
				}
			} else {
				List<ProductFieldItem> caseAttrOrm = productCase.getCaseAttrOrm();
				if (caseAttrOrm != null && caseAttrOrm.size() >= 1) {
					productCase.setCaseAttr(context.transToXml(ItemsConvertUtils.convertToFieldPro(caseAttrOrm),
							"fieldItems", "fieldItem"));
				}
			}

			// 因为实例编号是 由外部服务提供，所以这里不需要生成id
			if (isCurrentUserFlag) {
				productCase.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
				productCase.setCreateBy(CurrentUserUtils.getInstance().getUser().getLoginId());
			}
			productCase.setCreateDate(new Date());
			if("10".equals(product.getShowInstance())) {
				productCase.setIsShow("10");
			}else {
				productCase.setIsShow("20");
			}
			productCaseMapper.saveSingle(productCase);
			status = new Status("增加成功", 200);
		} catch (Exception e) {
			throw new ProductAccessException("save case error", e);
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.bonc.serviceAccess.service.IPProductCaseService#updateCase(com.bonc.
	 * serviceAccess.entity.PProductCase)
	 */
	@Override
	@Deprecated
	public Status updateCase(PProductCase productCase) {
		Status status;
		String caseId = productCase.getCaseId();
		String tenantId = productCase.getTenantId();

		if (StringUtils.isNullOrEmpty(caseId) || StringUtils.isNullOrEmpty(tenantId)) {
			throw new IllegalArgumentException("caseId or tenantId is null or empty");
		}
		try {
			PProductCase oldCase = productCaseMapper.findById(productCase);
			oldCase.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
			oldCase.setCreateBy(CurrentUserUtils.getInstance().getUser().getLoginId());
			oldCase.setCreateDate(new Date());
			oldCase.setCaseStatus(productCase.getCaseStatus());

			List<ProductFieldItem> caseAttrOrm = productCase.getCaseAttrOrm();

			if (caseAttrOrm != null && caseAttrOrm.size() >= 1) {
				oldCase.setCaseAttr(context.transToXml(ItemsConvertUtils.convertToFieldPro(caseAttrOrm), "fieldItems",
						"fieldItem"));
			} else {
				oldCase.setCaseAttr(productCase.getCaseAttr());
			}

			productCaseMapper.update(oldCase);

			status = new Status("更新成功", 200);
		} catch (Exception e) {
			throw new ProductAccessException("update case error", e);
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.bonc.serviceAccess.service.IPProductCaseService#deleteCase(com.bonc.
	 * serviceAccess.entity.PProductCase)
	 */
	@Override
	public Status deleteCase(PProductCase productCase) {
		Status status;

		String caseId = productCase.getCaseId();
		String tenantId = CurrentUserUtils.getInstance().getUser().getTenantId();
		productCase.setTenantId(tenantId);

		if (StringUtils.isNullOrEmpty(caseId) || StringUtils.isNullOrEmpty(tenantId)) {
			throw new IllegalArgumentException("caseId or tenantId is null or empty");
		}

		try {
			status = operCompontent.deleteService(productCase);
		} catch (Exception e) {
			throw new ProductAccessException("delete error", e);
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.bonc.serviceAccess.service.IPProductCaseService#findByProduct(com.
	 * bonc.serviceAccess.entity.PProductCase,
	 * com.bonc.serviceAccess.entity.PProduct)
	 */
	@Override
	public Pagination findByProduct(Map<String,Object> params, Pagination page, boolean isNeedCompare) {
		PProductCase productCase = new PProductCase();
		String tenantId = CurrentUserUtils.getInstance().getUser().getTenantId();
		String productId = params.get("productId").toString() ;
		String currentDataAuth = params.get("dataAuth").toString();
		productCase.setTenantId(tenantId);
		productCase.setProductId(productId);
		try {
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("productId", productId);

			// String currentDataAuth =
			// CurrentUserUtils.getInstance().getUser().getDataAuth();

			if (StringUtils.isNullOrEmpty(currentDataAuth)) {
				page.setTotalCount(0L);
				page.setList(null);
				return page;
			}
			if (Dictionary.AuthToken.LoginId.value.equals(currentDataAuth)) {
				params.put("loginId", CurrentUserUtils.getInstance().getUser().getLoginId());
			}
			if (Dictionary.AuthToken.TenantId.value.equals(currentDataAuth)) {
				params.put("tenantId", CurrentUserUtils.getInstance().getUser().getTenantId());
			}
			int startNum = (int) (page.getPageNo() * page.getPageSize() - page.getPageSize());
			params.put("startNo", startNum);
			params.put("size", page.getPageSize());
		    params.put("isShow", "10");
			List<PProductCase> cases = productCaseMapper.findAllByConditionAndPage(params);
			Map<String, String> caseAttrCompare = new HashMap<String, String>();
			if (isNeedCompare) {// 是否需要对比动态列配置
				PProduct product = new PProduct();
				product.setProductId(productId);
				product.setCreateBy(params.get("loginId") == null ? null : params.get("loginId").toString());
				product.setTenantId(params.get("tenantId") == null ? null : params.get("tenantId").toString());
				List<PProduct> products = productMapper.findAllByCondition(product);
				if (products != null && products.size() >= 1) {
					product = products.get(0);
				}
				String exampleAttr = product.getExampleAttr();
				if (!StringUtils.isNullOrEmpty(exampleAttr)) {

					List<ProductFieldItem> exampleAttrOrm = ItemsConvertUtils
							.convertToFieldBean(context.transToBean(exampleAttr));
					for (ProductFieldItem item : exampleAttrOrm) {
						caseAttrCompare.put(item.getProCode(), "true");
					}
				}
			}
			for (PProductCase case1 : cases) {
				if (!case1.getProductId().equalsIgnoreCase("bcm")) {
					packInstanceItem(case1, isNeedCompare, caseAttrCompare);
				}
				case1 = caseNodesState(case1);
			}
			page.setTotalCount((long) productCaseMapper.count(params));
			page.setList(cases);
		} catch (Exception e) {
			throw new ProductAccessException("fiendByProduct error", e);
		}
		return page;
	}

	private String packServiceIds(boolean isRelyProduct, List<ProductRelyOnItem> relyShips, String initSvcInstanceId) {
		StringBuilder builder = new StringBuilder(initSvcInstanceId);
		if (isRelyProduct) {
			for (ProductRelyOnItem relyShip : relyShips) {
				String relyCaseId = relyShip.getRelyOnCaseId();
				if (!StringUtils.isNullOrEmpty(relyCaseId)) {
					builder.append(",").append(relyCaseId);
				}
			}
		}
		return builder.toString();
	}

	private void packStatusResult(List<Map<String, Object>> result, List<ProductRelyOnItem> relyShips,
			String tenantId) {
		for (Map<String, Object> item : result) {
			String serviceId = item.get("serviceId").toString();
			String status = item.get("status").toString();

			String caseId = serviceId;
			PProductCase productCase = new PProductCase();
			productCase.setCaseId(caseId);
			productCase.setTenantId(tenantId);
			productCase = productCaseMapper.findById(productCase);

			if (productCase != null) {
				if (productCase.getCaseId().equals(serviceId) && !productCase.getCaseStatus().equals(status)) { // 当前的查询的serviceId
					// 和
					// 需要处理的实例id相同才可进行后续操作
					// 并且
					// 要更新的状态和
					// 当前状态不一致
					   Pattern pattern = Pattern.compile("[0-9]*");
			           Matcher isNum = pattern.matcher(status);
					if (!"WAITING".equals(status) && !"WARNING".equals(status)&&isNum.matches()) { // 过滤掉
						// waiting
						// 和
						// warning
						// 两种特殊状态
						// 更新case
						productCase.setCaseStatus(Integer.parseInt(status) + "");
						productCase.setReceipt(JSON.toJSONString(result));
						productCaseMapper.update(productCase);

						PProductNode node = new PProductNode();
						node.setCaseId(productCase.getCaseId());
						node.setTenantId(productCase.getTenantId());
						List<PProductNode> oldNodes = nodeMapper.findByCondition(node);
						// 判断nodes 是否为空
						if (result.get(0).get("nodes") != null) {
							List<Map> nodes = (List<Map>) result.get(0).get("nodes");
							if (nodes.size() >= 1 && oldNodes != null && oldNodes.size() ==nodes.size()) {
								int count = 0;
								for (Map map : nodes) {
									if (!map.get("status").toString().equals(oldNodes.get(count).getCaseStatus())) {
										PProductNode node1 = new PProductNode();
										node1.setTenantId(productCase.getTenantId());
										node1.setNodeId(map.get("nodeId").toString());
										node1.setCaseStatus(map.get("status").toString());
										nodeMapper.update(node1);
									}
									count++;
								}
							}
						} else {
							// 如果nodes 为空，说明 当前状态为faild 或者 是 其他
							if (status.equals(Dictionary.InstanceWorkState.FAIL.value + "")) {
								if (oldNodes != null && oldNodes.size() >= 1) {
									for (PProductNode oldNode : oldNodes) {
										oldNode.setCaseStatus(Dictionary.InstanceWorkState.FAIL.value + "");
										nodeMapper.update(oldNode);
									}
								}
							}
						}
					}
				}
			}
		}

	}

	private void packInstanceItem(PProductCase productCase, boolean isNeedCompare,
			Map<String, String> caseAttrCompare) {
		// 获取服务实例的实时状态
		CSvcBusiInfo busiInfo = operCompontent.packageBusiInfo(productCase, "status");
		Map<String, Object> params = new HashMap<String, Object>();
		PProduct product = new PProduct();
		product.setProductId(productCase.getProductId());
		String relyShips = productCase.getCaseRelyShipAttr();
		List<ProductRelyOnItem> relyShipsOrm = null;
		if (!StringUtils.isNullOrEmpty(relyShips)) {
			relyShipsOrm = ItemsConvertUtils.convertToRelyOnBean(context.transToBean(relyShips));
		}
		boolean isRelyProduct = CollectionUtils.isNotEmpty(relyShipsOrm);
		params.put("serviceIds", packServiceIds(isRelyProduct, relyShipsOrm, productCase.getCaseId())); // 如果为依赖服务，查找出依赖链后，把需要展示的依赖链的服务id全部放到这个参数里面进行状态查询

		Map<String, Object> data = operService.getInstanceStatusInfos(busiInfo, params);
		List<Map<String, Object>> result = (List<Map<String, Object>>) data.get("data");
		// 自动过滤云控制台存在的实例，而docs平台对应的实例因意外原因不存在的情况

		if (result == null || result.size() == 0) { // 说明状态查询的resultdata
			// 为null，目前查不到当前实例,如果实例的状态不是未创建、异常、启动中，则更改为未创建。
			if (Dictionary.InstanceWorkState.UNCREATE.value != Integer.parseInt(productCase.getCaseStatus())
					&& Dictionary.InstanceWorkState.EXCEPTION.value != Integer.parseInt(productCase.getCaseStatus())
					&& Dictionary.InstanceWorkState.STARTING.value!=Integer.parseInt(productCase.getCaseStatus())
					) {
				productCase.setReceipt(data != null && data.size() >= 1 ? JSON.toJSONString(data) : null);
				upCaseAndNode(productCase, Dictionary.InstanceWorkState.UNCREATE.value + "");
			}
		} else { // 说明状态查询的result data 有值. 这时候 需要处理的状态值 为 Running,Failed
			// ,未转换状态的为 WAITING 和 WARNING
			packStatusResult(result, relyShipsOrm, productCase.getTenantId());
		}

		String caseAttr = productCase.getCaseAttr();
		if (!StringUtils.isNullOrEmpty(caseAttr)) {
			List<ProductFieldItem> caseAttrOrm = ItemsConvertUtils.convertToFieldBean(context.transToBean(caseAttr));
			if (isNeedCompare) {
				if (caseAttrCompare != null && caseAttrCompare.size() >= 1) {
					Iterator<ProductFieldItem> itemsItor = caseAttrOrm.iterator();
					while (itemsItor.hasNext()) {
						String code = itemsItor.next().getProCode();
						if (!caseAttrCompare.containsKey(code)) { // 如果对比的动态列中不含有当前的code，则从返回的orm中删除
							itemsItor.remove();
						}
					}
				}
			}
			productCase.setCaseAttrOrm(caseAttrOrm);
			productCase.setCaseAttr(null);
		}
	}

	private void upCaseAndNode(PProductCase productCase, String targetCaseStatus) {
		productCase.setCaseStatus(targetCaseStatus);
		productCaseMapper.update(productCase);
		PProductNode nodeParam = new PProductNode();
		nodeParam.setCaseId(productCase.getCaseId());
		nodeParam.setTenantId(productCase.getTenantId());
		List<PProductNode> nodes = nodeMapper.findByCondition(nodeParam);
		if (nodes != null && nodes.size() >= 1) {
			for (PProductNode node : nodes) {
				node.setCaseStatus(targetCaseStatus);
				nodeMapper.update(node);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.bonc.serviceAccess.service.IPProductCaseService#singleCase(com.bonc.
	 * serviceAccess.entity.PProductCase)
	 */
	@Override
	public PProductCase singleCase(PProductCase productCase) {

		String tenantId = CurrentUserUtils.getInstance().getUser().getTenantId();
		String caseId = productCase.getCaseId();

		if (StringUtils.isNullOrEmpty(caseId)) {
			throw new IllegalArgumentException("caseId is null or empty");
		}
		try {
			String currentDataAuth = CurrentUserUtils.getInstance().getUser().getDataAuth();
			if (Dictionary.AuthToken.LoginId.value.equals(currentDataAuth)) {
				productCase.setCreateBy(CurrentUserUtils.getInstance().getUser().getLoginId());
				productCase.setTenantId(tenantId);
			}
			if (Dictionary.AuthToken.TenantId.value.equals(currentDataAuth)) {
				productCase.setTenantId(tenantId);
			}
			productCase = productCaseMapper.findById(productCase);
			if (productCase == null) {
				return null;
			}
			String caseAttr = productCase.getCaseAttr();

			if (!StringUtils.isNullOrEmpty(caseAttr)) {
				if (!StringUtils.isNullOrEmpty(productCase.getCaseRelyShipAttr())) {
					Map<String, List<ProductFieldItem>> fieldItems = new HashMap<String, List<ProductFieldItem>>();
					List<ProductRelyOnItem> relyShips = ItemsConvertUtils
							.convertToRelyOnBean(context.transToBean(productCase.getCaseRelyShipAttr()));
					if (CollectionUtils.isNotEmpty(relyShips)) {
						for (ProductRelyOnItem item : relyShips) {
							String relyCaseId = item.getRelyOnCaseId();
							String isShow = item.getIsShowRelyOnPros();
							if ("10".equals(isShow) && !StringUtils.isNullOrEmpty(relyCaseId)) {
								PProductCase caseParam = new PProductCase();
								caseParam.setCaseId(relyCaseId);
								caseParam = productCaseMapper.findById(caseParam);
								if (caseParam != null && !StringUtils.isNullOrEmpty(caseParam.getCaseAttr())) {
									fieldItems.put(item.getRelyOnProductCode(), ItemsConvertUtils
											.convertToFieldBean(context.transToBean(caseParam.getCaseAttr())));
								}
							}
						}
					}
					fieldItems.put(productCase.getProductId(),
							ItemsConvertUtils.convertToFieldBean(context.transToBean(caseAttr)));
					productCase.setCaseRelyAttrOrm(fieldItems);
					productCase.setCaseAttr(null);
					productCase.setCaseAttrOrm(null);
				} else {
					productCase.setCaseAttrOrm(ItemsConvertUtils.convertToFieldBean(context.transToBean(caseAttr)));
					productCase.setCaseAttr(null);
				}
			}
		} catch (Exception e) {
			throw new ProductAccessException("singleCase find error", e);
		}
		return productCase;
	}

	@Override
	public Map<String, Object> findQuota(String tenantId, String svcCode) {
		Map<String, Object> map = new HashMap<String, Object>();

		PProductCase pCase = new PProductCase();
		pCase.setTenantId(tenantId);
		pCase.setProductId(svcCode);
		pCase.setCaseStatus(String.valueOf(Dictionary.InstanceWorkState.RUNNING.value));
		List<PProductCase> caseList = productCaseMapper.findByCondition(pCase);

		List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
		Map<String, ProductFieldItem> numMap = new HashMap<>();

		if (caseList != null && caseList.size() > 0) {

			for (int i = 0, len = caseList.size(); i < len; i++) {

				PProductCase tempCase = caseList.get(i);

				String orderAttr = tempCase.getCaseAttr();

				if (!StringUtils.isNullOrEmpty(orderAttr)) {

					List<ProductFieldItem> fields = ItemsConvertUtils
							.convertToFieldBean(context.transToBean(orderAttr));
					if (fields != null && fields.size() > 0) {

						for (int index = 0, indexLen = fields.size(); index < indexLen; index++) {
							try {
								ProductFieldItem fieldItem = fields.get(index);
								int dataType = Integer.valueOf(fieldItem.getProDataType()).intValue();
								if (Dictionary.MetadataDataType.SHORT.value == Integer.valueOf(dataType).intValue()
										|| Dictionary.MetadataDataType.INT.value == Integer.valueOf(dataType).intValue()
										|| Dictionary.MetadataDataType.LONG.value == Integer.valueOf(dataType)
										.intValue()
										|| Dictionary.MetadataDataType.FLORT.value == Integer.valueOf(dataType)
										.intValue()
										|| Dictionary.MetadataDataType.DOUBLE.value == Integer.valueOf(dataType)
										.intValue()) {
									BigDecimal value = new BigDecimal(fieldItem.getProValue());
									if (numMap.get(fieldItem.getProCode()) != null) {
										ProductFieldItem oldField = numMap.get(fieldItem.getProCode());
										BigDecimal subNum = value.add(new BigDecimal(oldField.getProValue()));
										fieldItem.setProValue(subNum.toPlainString());
										numMap.put(fieldItem.getProCode(), fieldItem);
									} else {
										numMap.put(fieldItem.getProCode(), fieldItem);
									}
								}
							} catch (Exception e) {}
						}
					}
				}
			}
			for (String key : numMap.keySet()) {
				Map<String, Object> singleObj = new HashMap<String, Object>();
				singleObj.put("attrName", key);
				singleObj.put("valueObject", numMap.get(key).getProValue());
				detailList.add(singleObj);
			}
			map.put("detailList", detailList);
		}
		return map;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.bonc.serviceAccess.service.IPProductCaseService#findByProduct(com.
	 * bonc.serviceAccess.entity.PProductCase,
	 * com.bonc.serviceAccess.entity.PProduct)
	 */
	@Override
	public Pagination findCasePageWithAuth(PProductCase productCase, Pagination page) {
		try {

			Map<Object, Object> paraMap = MapObjUtil.objectToMap(productCase);
			paraMap.put("tenantId", CurrentUserUtils.getInstance().getDataUser().getTenantId());
			paraMap.put("LoginId", CurrentUserUtils.getInstance().getDataUser().getLoginId());
			if (Dictionary.AuthToken.Provider.value
					.equals(CurrentUserUtils.getInstance().getDataUser().getDataAuth())) {
				paraMap.put("providerId", CurrentUserUtils.getInstance().getUser().getLoginId());
			}
			int startNum = (int) (page.getPageNo() * page.getPageSize() - page.getPageSize());
			paraMap.put("startNo", startNum);
			paraMap.put("size", page.getPageSize());
			List<PProductCase> cases = productCaseMapper.findAllByConditionAndPageWithAuth(paraMap);
			page.setTotalCount((long) productCaseMapper.countWithAuth(paraMap));
			page.setList(cases);
		} catch (Exception e) {
			throw new ProductAccessException("fiendByProduct error", e);
		}
		return page;
	}

	@Override
	public Integer finCaseCtnWithAuth(PProductCase productCase) {
		Map<Object, Object> paraMap;
		try {
			paraMap = MapObjUtil.objectToMap(productCase);
			paraMap.put("tenantId", CurrentUserUtils.getInstance().getDataUser().getTenantId());
			paraMap.put("LoginId", CurrentUserUtils.getInstance().getDataUser().getLoginId());
			if (Dictionary.AuthToken.Provider.value
					.equals(CurrentUserUtils.getInstance().getDataUser().getDataAuth())) {
				paraMap.put("providerId", CurrentUserUtils.getInstance().getUser().getLoginId());
			}
			if (Dictionary.AuthToken.Provider.value
					.equals(CurrentUserUtils.getInstance().getDataUser().getDataAuth())) {
				paraMap.put("providerId", CurrentUserUtils.getInstance().getUser().getLoginId());
			}
			return productCaseMapper.countWithAuth(paraMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	
	/**
	 * 
	* @Title: caseNodesState  
	* @Description: 统计实例节点对应状态数量  
	* @param @param productCase
	* @param @return    参数  
	* @return PProductCase    返回类型  
	* @throws
	 */
	private PProductCase caseNodesState(PProductCase productCase) {
		List<PProductNode> nodes = nodeService.findByCase(productCase);
		int runCount = 0;
		int failCount = 0;
		int stopCount = 0;
		int nodestate = 0;
		for(PProductNode node:nodes) {
			if(null != node.getCaseAttrOrm()) {
				for(ProductFieldItem field : node.getCaseAttrOrm()) {
					if(field.getProCode().equalsIgnoreCase(node.NODESTATE)) {
						nodestate++;
						if(field.getProValue().equalsIgnoreCase(Dictionary.NodeStateStr.RUNNING.value)) {
							runCount++;
						}else if(field.getProValue().equalsIgnoreCase(Dictionary.NodeStateStr.FAIL.value)){
							failCount++;
						}else if(field.getProValue().equalsIgnoreCase(Dictionary.NodeStateStr.STOP.value)) {
							stopCount++;
						}
					}
				}
			}
		}
		JSONObject nodeCoun =  new JSONObject();
		if(nodestate == 0) {
			nodeCoun.put("runCount", -1);
			nodeCoun.put("failCount", -1);
			nodeCoun.put("stopCount", -1);
			nodeCoun.put("error", "该服务缺少nodeState访问属性");
		}else {
			nodeCoun.put("runCount", runCount);
			nodeCoun.put("failCount", failCount);
			nodeCoun.put("stopCount", stopCount);

		}
		productCase.setpNodesCounts(nodeCoun);
		return productCase;
	}
	

}
