/**
 * 
 */
package com.sdp.serviceAccess.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;
import com.sdp.common.CurrentUserUtils;
import com.sdp.common.constant.Dictionary;
import com.sdp.common.constant.Dictionary.HttpStatus;
import com.sdp.common.entity.Status;
import com.sdp.common.page.Pagination;
import com.sdp.frame.web.entity.user.User;
import com.sdp.serviceAccess.entity.PProduct;
import com.sdp.serviceAccess.entity.PProductOrder;
import com.sdp.serviceAccess.entity.ProductFieldItem;
import com.sdp.serviceAccess.entity.ProductRelyOnItem;
import com.sdp.serviceAccess.exception.ProductAccessException;
import com.sdp.serviceAccess.mapper.PProductMapper;
import com.sdp.serviceAccess.mapper.PProductOrderMapper;
import com.sdp.serviceAccess.protyTrans.ProptyTransDom4j;
import com.sdp.serviceAccess.protyTrans.ProtyTransContext;
import com.sdp.serviceAccess.service.IPProductService;
import com.sdp.serviceAccess.util.ItemsConvertUtils;
import com.sdp.serviceAccess.util.RestTemplateUtil;

/**
 * Copyright: Copyright (c) 2018 BONC
 * 
 * @ClassName: PProductServiceImpl.java
 * @Description: see IPProductService
 *
 * @version: v1.0.0
 * @author: renpengyuan
 * @date: 2018年8月2日 下午4:05:38
 *
 *        Modification History: Date Author Version Description
 *        ---------------------------------------------------------* 2018年8月2日
 *        renpengyuan v1.0.0 修改原因
 */
@Service("pProductService")
public class PProductServiceImpl implements IPProductService {

	@Autowired
	private PProductMapper productMapper;

	@Autowired
	private PProductOrderMapper orderMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sdp.serviceAccess.service.IPProductService#allProducts(com.sdp.
	 * xintegration.logSer.log.model.page.Pagination,
	 * com.sdp.serviceAccess.entity.PProduct)
	 */
	@Override
	public Pagination allProducts(Pagination page, PProduct product, boolean isOrderCondition) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			if (product != null) {
				if (!StringUtils.isNullOrEmpty(product.getProductId())) {
					params.put("productId", product.getProductId());
				}
				if (!StringUtils.isNullOrEmpty(product.getProductName())) {
					params.put("productName", product.getProductName());
				}
				if (!StringUtils.isNullOrEmpty(product.getOrderType())) {
					params.put("orderType", product.getOrderType());
				}
				if (!StringUtils.isNullOrEmpty(product.getProductTypeId())) {
					params.put("productTypeId", product.getProductTypeId());
				}
				if (!StringUtils.isNullOrEmpty(product.getProductStatus())) {
					params.put("productStatus", product.getProductStatus());
				}
				if (!StringUtils.isNullOrEmpty(product.getSortType())) {
					params.put("sortType", product.getSortType());
				}
			}
			int startNum = (int) ((page.getPageNo() * page.getPageSize()) - page.getPageSize());
			params.put("startNo", startNum);
			params.put("size", page.getPageSize());
			User currentUser = CurrentUserUtils.getInstance().getUser();
			if (isOrderCondition) {
				if (!Dictionary.AuthToken.All.value.equals(currentUser.getDataAuth())) {
					// 查订单 下面所有的 productId
					PProductOrder productOrder = new PProductOrder();
					productOrder.setTenantId(currentUser.getTenantId());
					List<PProductOrder> ordersByUser = orderMapper.findAllByCondition(productOrder);
					// 通过所有的productId查询当前用户可以看到的所有服务数据
					List<String> productIds = new ArrayList<String>();
					if (ordersByUser != null && ordersByUser.size() >= 1) {
						Map<String, String> hash = new HashMap<String, String>();
						for (PProductOrder order : ordersByUser) {
							String flag = hash.get(order.getProductId());
							if (StringUtils.isNullOrEmpty(flag)) {
								hash.put(order.getProductId(), "true");
								productIds.add(order.getProductId());
							}
						}
						params.put("productIds", productIds);
					}
					// 查询所有ids 的product信息
					if (productIds.size() == 0) {
						page.setList(null);
						page.setTotalCount(0L);
						return page;
					}
				}
			}else {
				params.put("craeteBy",currentUser.getLoginId());
				params.put("tenantId",currentUser.getTenantId());
			}
			page.setList(productMapper.findAllByConditionAndPage(params));
			page.setTotalCount((long) productMapper.count(params));
		} catch (Exception e) {
			throw new ProductAccessException("all Product find error", e);
		}

		return page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sdp.serviceAccess.service.IPProductService#createProduct(com.sdp.
	 * serviceAccess.entity.PProduct)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Status createProduct(PProduct product, MultipartFile productIcon) {

		Status status;

		if (product == null) {
			throw new IllegalArgumentException("product is null");
		}

		try {
			product.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
			product.setCreateBy(CurrentUserUtils.getInstance().getUser().getLoginId());
			product.setCreateDate(new Date());

			if (productIcon != null && productIcon.getBytes().length > 0) {
				product.setProductLogo(productIcon.getBytes());
			}
			ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
			List<ProductFieldItem> productAttrOrm = product.getProductAttrOrm();
			if (productAttrOrm != null && productAttrOrm.size() >= 1) {
				product.setProductAttr(context.transToXml(ItemsConvertUtils.convertToFieldPro(productAttrOrm),
						"fieldItems", "fieldItem"));
			}
			productAttrOrm = product.getExampleAttrOrm();
			if (productAttrOrm != null && productAttrOrm.size() >= 1) {
				product.setExampleAttr(context.transToXml(ItemsConvertUtils.convertToFieldPro(productAttrOrm),
						"fieldItems", "fieldItem"));
			}
			productAttrOrm = product.getNodeAttrOrm();
			if (productAttrOrm != null && productAttrOrm.size() >= 1) {
				product.setNodeAttr(context.transToXml(ItemsConvertUtils.convertToFieldPro(productAttrOrm),
						"fieldItems", "fieldItem"));
			}

			List<ProductRelyOnItem> relyOnItems = product.getRelyOnAttrOrm();
			if (relyOnItems != null && relyOnItems.size() >= 1) {
				product.setRelyOnAttr(context.transToXml(ItemsConvertUtils.convertToRelyOnPro(relyOnItems), "feldItems",
						"feldItems"));
			}

			productMapper.saveSingle(product);
			status = new Status("创建成功", 200);
		} catch (Exception e) {
			throw new ProductAccessException("create product error", e);
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sdp.serviceAccess.service.IPProductService#singleProduct(com.sdp.
	 * serviceAccess.entity.PProduct)
	 */
	@Override
	public PProduct singleProduct(PProduct product) {
		String checkKv = product.getCheckKv();

		String tenantId = null;
		if(CurrentUserUtils.getInstance().getUser()!=null)
		{
			tenantId = CurrentUserUtils.getInstance().getUser().getTenantId();
		}
		String productId = product.getProductId();
		if (StringUtils.isNullOrEmpty(productId)) {
			throw new IllegalArgumentException("param is null or blank");
		}

		try {
			product.setProductId(productId);
			product = productMapper.findById(product);
			if (product == null) {
				return null;
			}
			ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
			String productAttr = product.getProductAttr();
			if (!StringUtils.isNullOrEmpty(productAttr)) {
				List<ProductFieldItem> fieldItems = ItemsConvertUtils
						.convertToFieldBean(context.transToBean(productAttr));
				if (!StringUtils.isNullOrEmpty(checkKv) && "20".equals(checkKv)
						&& !StringUtils.isNullOrEmpty(tenantId)) {
					if (fieldItems != null && fieldItems.size() >= 1) {
						checkKeyValue(fieldItems, tenantId);
					}
				}
				product.setProductAttrOrm(fieldItems);
				product.setProductAttr(null);
			}
			productAttr = product.getExampleAttr();
			if (!StringUtils.isNullOrEmpty(productAttr)) {
				product.setExampleAttrOrm(ItemsConvertUtils.convertToFieldBean(context.transToBean(productAttr)));
				product.setExampleAttr(null);
			}

			productAttr = product.getNodeAttr();
			if (!StringUtils.isNullOrEmpty(productAttr)) {
				product.setNodeAttrOrm(ItemsConvertUtils.convertToFieldBean(context.transToBean(productAttr)));
				product.setNodeAttr(null);
			}

			String relyOnAttr = product.getRelyOnAttr();
			if (!StringUtils.isNullOrEmpty(relyOnAttr)) {
				product.setRelyOnAttrOrm(ItemsConvertUtils.convertToRelyOnBean(context.transToBean(relyOnAttr)));
				product.setRelyOnAttr(null);
			}
		} catch (Exception e) {
			throw new ProductAccessException("find single Product error", e);
		}
		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sdp.serviceAccess.service.IPProductService#modifyStatus(com.sdp.
	 * serviceAccess.entity.PProduct)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Status modifyStatus(PProduct product) {
		Status status;

		if (StringUtils.isNullOrEmpty(product.getProductId())) {
			throw new IllegalArgumentException("productId is null or blank");
		}

		try {
			String targetStatus = product.getProductStatus();
			product.setProductStatus(null);
			PProduct oldProduct = productMapper.findById(product);
			oldProduct.setCreateDate(new Date());
			oldProduct.setCreateBy(CurrentUserUtils.getInstance().getUser().getLoginId());
			oldProduct.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
			oldProduct.setProductStatus(targetStatus);
			productMapper.update(oldProduct);
			status = new Status("修改成功", 200);
		} catch (Exception e) {
			throw new ProductAccessException("modifyStatus error", e);
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sdp.serviceAccess.service.IPProductService#updateProItems(com.sdp.
	 * serviceAccess.entity.PProduct)
	 */
	@Override
	public Status updateProItems(PProduct product) {
		Status status;

		if (product == null) {
			throw new IllegalArgumentException("product is null");
		}

		try {
			PProduct oldProduct = productMapper.findById(product);
			oldProduct.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
			oldProduct.setCreateBy(CurrentUserUtils.getInstance().getUser().getLoginId());
			oldProduct.setCreateDate(new Date());

			ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
			List<ProductFieldItem> productAttrOrm = product.getProductAttrOrm();
			if (productAttrOrm != null && productAttrOrm.size() >= 1) {
				oldProduct.setProductAttr(context.transToXml(ItemsConvertUtils.convertToFieldPro(productAttrOrm),
						"fieldItems", "fieldItem"));
			}
			productAttrOrm = product.getExampleAttrOrm();
			if (productAttrOrm != null && productAttrOrm.size() >= 1) {
				oldProduct.setExampleAttr(context.transToXml(ItemsConvertUtils.convertToFieldPro(productAttrOrm),
						"fieldItems", "fieldItem"));
			}
			productAttrOrm = product.getNodeAttrOrm();
			if (productAttrOrm != null && productAttrOrm.size() >= 1) {
				oldProduct.setNodeAttr(context.transToXml(ItemsConvertUtils.convertToFieldPro(productAttrOrm),
						"fieldItems", "fieldItem"));
			}

			List<ProductRelyOnItem> relyOnItems = product.getRelyOnAttrOrm();
			if (relyOnItems != null && relyOnItems.size() >= 1) {
				oldProduct.setProductAttr(context.transToXml(ItemsConvertUtils.convertToRelyOnPro(relyOnItems),
						"relyOnItems", "relyOnItems"));
			}

			productMapper.update(oldProduct);
			status = new Status("修改成功", 200);
		} catch (Exception e) {
			throw new ProductAccessException("updateProItems error", e);
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sdp.serviceAccess.service.IPProductService#verfyProductCode(java.lang.
	 * String)
	 */
	@Override
	public Boolean verfyProductCode(String productCode) {

		if (StringUtils.isNullOrEmpty(productCode)) {
			throw new IllegalArgumentException("productCode is null or empty");
		}

		try {
			PProduct product = new PProduct();
			product.setProductId(productCode);
			product.setProductStatus(Dictionary.SvcState.REGISTERED.value + "");
			product = productMapper.findById(product);
			return (Boolean.valueOf(product == null));
		} catch (Exception e) {
			throw new ProductAccessException("verfyProductCode error");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sdp.serviceAccess.service.IPProductService#modifyProduct(com.sdp.
	 * serviceAccess.entity.PProduct,
	 * org.springframework.web.multipart.MultipartFile)
	 */
	@Override
	public Status modifyProduct(PProduct product, MultipartFile productIcon) {

		Status status;

		if (product == null) {
			throw new IllegalArgumentException("product is null");
		}

		if (StringUtils.isNullOrEmpty(product.getProductId())) {
			throw new IllegalArgumentException("productid is null");
		}

		try {
			product.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
			product.setCreateBy(CurrentUserUtils.getInstance().getUser().getLoginId());
			product.setCreateDate(new Date());

			if (productIcon != null && productIcon.getBytes().length > 0) {
				product.setProductLogo(productIcon.getBytes());
			}
			ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
			List<ProductFieldItem> productAttrOrm = product.getProductAttrOrm();
			if (productAttrOrm != null && productAttrOrm.size() >= 1) {
				product.setProductAttr(context.transToXml(ItemsConvertUtils.convertToFieldPro(productAttrOrm),
						"fieldItems", "fieldItem"));
			}
			productAttrOrm = product.getExampleAttrOrm();
			if (productAttrOrm != null && productAttrOrm.size() >= 1) {
				product.setExampleAttr(context.transToXml(ItemsConvertUtils.convertToFieldPro(productAttrOrm),
						"fieldItems", "fieldItem"));
			}
			productAttrOrm = product.getNodeAttrOrm();
			if (productAttrOrm != null && productAttrOrm.size() >= 1) {
				product.setNodeAttr(context.transToXml(ItemsConvertUtils.convertToFieldPro(productAttrOrm),
						"fieldItems", "fieldItem"));
			}

			List<ProductRelyOnItem> relyOnItems = product.getRelyOnAttrOrm();
			if (relyOnItems != null && relyOnItems.size() >= 1) {
				product.setRelyOnAttr(context.transToXml(ItemsConvertUtils.convertToRelyOnPro(relyOnItems),
						"fieldItems", "fieldItems"));
			}

			productMapper.update(product);
			status = new Status("更新成功", 200);
		} catch (Exception e) {
			throw new ProductAccessException("modify product error", e);
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sdp.serviceAccess.service.IPProductService#productInfosByCat(java.lang.
	 * String)
	 */
	@Override
	public List<Map<String, Object>> productInfosByCat() {

		//		String tenantId= CurrentUserUtils.getInstance().getUser().getTenantId();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			//			params.put("tenantId", tenantId);
			params.put("orderStatus", Dictionary.OrderState.APPROVAL_SUCCESS.value);
			return packResult(productMapper.findProductOrderCountByProductType(params));
		} catch (Exception e) {
			throw new ProductAccessException("productInfosByCat", e);
		}
	}

	private List<Map<String, Object>> packResult(List<Map<String, Object>> params) {
		if (params == null || params.size() == 0) {
			return null;
		}
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String tmpProductTypeId = "";
		Map<String, Object> cache = null;
		List<Map<String, Object>> prosCache = null;
		Map<String, Object> signlePro = null;
		int count = 1;
		for (Map<String, Object> singleResult : params) {
			String nowProductTypeId = singleResult.get("productTypeId").toString();
			if (!tmpProductTypeId.equals(nowProductTypeId)) {
				if (cache != null && cache.size() >= 1) {
					result.add(cache);
				}
				prosCache = new ArrayList<>();
				cache = new LinkedHashMap<String, Object>();
			}
			if (cache.get("productTypeId") == null) {
				cache.put("productTypeId", nowProductTypeId);
				cache.put("productTypeName", singleResult.get("productTypeName"));
				cache.put("parentId", singleResult.get("parentId"));
				cache.put("typePath", singleResult.get("typePath"));
				cache.put("sortId", singleResult.get("sortId"));
				cache.put("typeStatus", singleResult.get("typeStatus"));
			}
			if (singleResult.get("productId") != null) {
				signlePro = new HashMap<String, Object>();
				signlePro.put("productId", singleResult.get("productId"));
				signlePro.put("productName", singleResult.get("productName"));
				signlePro.put("productIntrod", singleResult.get("productIntrod"));
				signlePro.put("detailedIntrod", singleResult.get("detailedIntrod"));
				signlePro.put("createDate", singleResult.get("createDate"));
				signlePro.put("orderSuccessCtn", singleResult.get("orderSuccessCtn"));
				signlePro.put("productStatus", singleResult.get("productStatus"));
				prosCache.add(signlePro);
				cache.put("productResults", prosCache);
			} else {
				cache.put("productResults", null);
			}
			tmpProductTypeId = nowProductTypeId;
			count++;
			if (count == params.size()) {
				result.add(cache);
			}
		}
		return result;
	}

	private void checkKeyValue(List<ProductFieldItem> items, String tenantId) {
		if (items != null && items.size() >= 1) {
			for (ProductFieldItem item : items) {
				String checkUrl = item.getProInitValue();
				if (!StringUtils.isNullOrEmpty(checkUrl) && !StringUtils.isNullOrEmpty(tenantId)) {
					if (checkUrl.startsWith("http://") || checkUrl.startsWith("https://")) {
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("tenantId", tenantId);
						Map<String, Object> response = RestTemplateUtil.post(checkUrl, params, Map.class,
								new HashMap<String, Object>());
						if (response != null && response.size() >= 1) {
							if (response.get("data") != null) {
								item.setProInitValue(response.get("data").toString());
							}
						}
					}

				}
			}
		}
	}

	@Override
	public Status createProperties(PProduct product) {
		Status status = new Status("create error", HttpStatus.INVALID_REQUEST.value);
		try {
			if (StringUtils.isNullOrEmpty(product.getProductId())) {
				throw new ProductAccessException("productId is null");
			}
			List<ProductFieldItem> caseAttrOrm = product.getExampleAttrOrm();
			List<ProductFieldItem> nodeAttrOrm = product.getNodeAttrOrm();
			List<ProductRelyOnItem> relyOnAttrOrm = product.getRelyOnAttrOrm();
			if ((caseAttrOrm == null || caseAttrOrm.size() == 0) && (nodeAttrOrm == null || nodeAttrOrm.size() == 0)) {
				throw new ProductAccessException("properties is null or empty");
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("productId", product.getProductId());
			params.put("tenantId", CurrentUserUtils.getInstance().getUser().getTenantId());
			ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
			if (caseAttrOrm != null && caseAttrOrm.size() >= 1) {
				params.put("caseAttr", context.transToXml(ItemsConvertUtils.convertToFieldPro(caseAttrOrm),
						"fieldItems", "fieldItem"));
			} else if (nodeAttrOrm != null && nodeAttrOrm.size() >= 1) {
				params.put("nodeAttr", context.transToXml(ItemsConvertUtils.convertToFieldPro(caseAttrOrm),
						"fieldItems", "fieldItem"));
			} else if (relyOnAttrOrm != null && relyOnAttrOrm.size() >= 1) {
				params.put("relyOnAttr", context.transToXml(ItemsConvertUtils.convertToRelyOnPro(relyOnAttrOrm),
						"relyOnItems", "relyOnItem"));
			}
			productMapper.modifyCasePro(params);
			status = new Status("create success", HttpStatus.OK.value);
		} catch (Exception e) {
			throw new ProductAccessException("create pro error ,desc:" + e.getMessage());
		}
		return status;
	}

	// 基于依赖服务的测试用例
	// public static void main(String[] args) {
	//	 List<ProductRelyOnItem> relyOnItems = new ArrayList<>();
	//	 for(int i=0;i<3;i++) {
	//		 ProductRelyOnItem item = new ProductRelyOnItem();
	//		 item.setRelyOnProductCode("mysql"+i);
	//		 item.setRelyOnOrder(i+"");
	//		 item.setIsShowRelyOnPros("10");
	//		 item.setIsConfRelyOnPros("20");
	//		 item.setIsAddiCasePros("20");
	//		 relyOnItems.add(item);
	//	 }
	//	 ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
	//	 System.out.println(context.transToXml(ItemsConvertUtils.convertToRelyOnPro(relyOnItems), "relyOnItems", "relyOnItem"));
	//	 System.out.println(JSON.toJSONString(context.transToBean(context.transToXml(ItemsConvertUtils.convertToRelyOnPro(relyOnItems), "relyOnItems", "relyOnItem"))));
	//}
	/**
	 * 判断是否为依赖服务并且返回需要进行展示实例信息服务的依赖链
	 */
	public Map<String, List<ProductFieldItem>> relyOnProductInfos(PProduct product) {
		String relyOnShipAttr = product.getRelyOnAttr();
		ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
		Map<String, List<ProductFieldItem>> retu = new HashMap<String, List<ProductFieldItem>>();
		if (!StringUtils.isNullOrEmpty(relyOnShipAttr)) { // 处理依赖的服务，不包括自己本身。
			List<ProductRelyOnItem> relyShipOrm = ItemsConvertUtils
					.convertToRelyOnBean(context.transToBean(relyOnShipAttr));
			for (ProductRelyOnItem relyShip : relyShipOrm) {
				String relyOnCode = relyShip.getRelyOnProductCode();
				String isShowPro = relyShip.getIsShowRelyOnPros();
				if ("10".equals(isShowPro)) {
					// 说明需要将依赖链进行 持久化并且 做实例信息匹配
					PProduct param = new PProduct();
					param.setProductId(relyOnCode);
					param = productMapper.findById(param);
					// 返回的需要进行依赖连append的服务，找出属性值并且进行转换
					List<ProductFieldItem> items = ItemsConvertUtils
							.convertToFieldBean(context.transToBean(param.getProductAttr()));
					retu.put(param.getProductId(), items);
					packShowField(items);
				}
			}
		}
		List<ProductFieldItem> items = ItemsConvertUtils
				.convertToFieldBean(context.transToBean(product.getProductAttr()));
		retu.put(product.getProductId(), items);
		packShowField(items);
		return retu;
	}

	private void packShowField(List<ProductFieldItem> items) {
		Iterator<ProductFieldItem> itor = items.iterator();
		while (itor.hasNext()) {
			ProductFieldItem item = itor.next();
			if (!item.getProLabel().contains("20")) {
				itor.remove();
			}
		}
	}

	@Override
	public Map<String, Map<String, String>> relyOnInfos(PProduct product) {
		String relyOnShipAttr = product.getRelyOnAttr();
		ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
		Map<String, Map<String, String>> retu = new HashMap<String, Map<String, String>>();
		if (!StringUtils.isNullOrEmpty(relyOnShipAttr)) { // 处理依赖的服务，不包括自己本身。
			List<ProductRelyOnItem> relyShipOrm = ItemsConvertUtils
					.convertToRelyOnBean(context.transToBean(relyOnShipAttr));
			for (ProductRelyOnItem relyShip : relyShipOrm) {
				Map<String, String> item = new HashMap<String, String>();
				retu.put(relyShip.getRelyOnProductCode(), item);
				item.put("relyOnProductCode", relyShip.getRelyOnProductCode());
				item.put("isShowRelyOnPros", relyShip.getIsShowRelyOnPros());
				item.put("isConfRelyOnPros", relyShip.getIsConfRelyOnPros());
				item.put("relyOnOrder", relyShip.getRelyOnOrder());
				item.put("isAddiCasePros", relyShip.getIsAddiCasePros());
			}
		}
		return retu;
	}

	@Override
	public List<PProduct> getProductByCase(PProduct product, String currentDataAuth) {
		Map<String, Object> params = new HashMap<String, Object>();
		String loginId = CurrentUserUtils.getInstance().getUser().getLoginId();
		String TenantId = CurrentUserUtils.getInstance().getUser().getTenantId();
		if (Dictionary.AuthToken.LoginId.value.equals(currentDataAuth)) {
			params.put("loginId", loginId);
		}
		else if (Dictionary.AuthToken.TenantId.value.equals(currentDataAuth)) {
			params.put("tenantId", TenantId);
		} else {
			return null;
		}
		List<PProduct> productList = productMapper.findAllByCase(params);
		return productList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sdp.serviceAccess.service.IPProductService#allProducts(com.sdp.
	 * xintegration.logSer.log.model.page.Pagination,
	 * com.sdp.serviceAccess.entity.PProduct)
	 */
	@Override
	public Integer allProductsCntWithAuth(PProduct product) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			if (product != null) {
				if (!StringUtils.isNullOrEmpty(product.getProductId())) {
					params.put("productId", product.getProductId());
				}
				if (!StringUtils.isNullOrEmpty(product.getProductName())) {
					params.put("productName", product.getProductName());
				}
				if (!StringUtils.isNullOrEmpty(product.getOrderType())) {
					params.put("orderType", product.getOrderType());
				}
				if (!StringUtils.isNullOrEmpty(product.getProductTypeId())) {
					params.put("productTypeId", product.getProductTypeId());
				}
				if (!StringUtils.isNullOrEmpty(product.getProductStatus())) {
					params.put("productStatus", product.getProductStatus());
				}
				if (!StringUtils.isNullOrEmpty(product.getSortType())) {
					params.put("sortType", product.getSortType());
				}
			}
			params.put("loginId", CurrentUserUtils.getInstance().getDataUser().getLoginId());
			params.put("tenantId", CurrentUserUtils.getInstance().getDataUser().getTenantId());
			return productMapper.count(params);
		} catch (Exception e) {
			throw new ProductAccessException("all Product find error", e);
		}
	}

	@Override
	public Pagination allProductsWithAuth(Pagination page, PProduct product, boolean isOrderCondition) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			if (product != null) {
				if (!StringUtils.isNullOrEmpty(product.getProductId())) {
					params.put("productId", product.getProductId());
				}
				if (!StringUtils.isNullOrEmpty(product.getProductName())) {
					params.put("productName", product.getProductName());
				}
				if (!StringUtils.isNullOrEmpty(product.getOrderType())) {
					params.put("orderType", product.getOrderType());
				}
				if (!StringUtils.isNullOrEmpty(product.getProductTypeId())) {
					params.put("productTypeId", product.getProductTypeId());
				}
				if (!StringUtils.isNullOrEmpty(product.getProductStatus())) {
					params.put("productStatus", product.getProductStatus());
				}
				if (!StringUtils.isNullOrEmpty(product.getSortType())) {
					params.put("sortType", product.getSortType());
				}
			}
			int startNum = (int) ((page.getPageNo() * page.getPageSize()) - page.getPageSize());
			params.put("startNo", startNum);
			params.put("size", page.getPageSize());
			params.put("loginId",CurrentUserUtils.getInstance().getDataUser().getLoginId());
			params.put("tenantId",CurrentUserUtils.getInstance().getDataUser().getTenantId());
			if (isOrderCondition) {
				User currentUser = CurrentUserUtils.getInstance().getDataUser();
				if (!Dictionary.AuthToken.All.value.equals(currentUser.getDataAuth())) {
					// 查订单 下面所有的 productId
					PProductOrder productOrder = new PProductOrder();
					productOrder.setCreateBy(currentUser.getLoginId());
					productOrder.setTenantId(currentUser.getTenantId());
					List<PProductOrder> ordersByUser = orderMapper.findAllByCondition(productOrder);
					// 通过所有的productId查询当前用户可以看到的所有服务数据
					if (ordersByUser != null && ordersByUser.size() >= 1) {
						List<String> productIds = new ArrayList<String>();
						Map<String, String> hash = new HashMap<String, String>();
						for (PProductOrder order : ordersByUser) {
							String flag = hash.get(order.getProductId());
							if (StringUtils.isNullOrEmpty(flag)) {
								hash.put(order.getProductId(), "true");
								productIds.add(order.getProductId());
							}
						}
						// 查询所有ids 的product信息
						if (productIds.size() == 0) {
							page.setList(null);
							page.setTotalCount(0L);
							return page;
						}
						params.put("productIds", productIds);
					}
				}
			}
			page.setList(productMapper.findAllByConditionAndPage(params));
			page.setTotalCount((long) productMapper.count(params));
		} catch (Exception e) {
			throw new ProductAccessException("all Product find error", e);
		}

		return page;
	}

	@Override
	public Map<String, Map<String,String>> getResPros(String productId) {
		PProduct product=  new PProduct();
		product.setProductId(productId);
		product = productMapper.findById(product);
		String prosXml = product.getProductAttr();
		String relyOnXml = product.getRelyOnAttr();
		ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
		Map<String,Map<String,String>> result = new HashMap<String,Map<String,String>>();
		if(!StringUtils.isNullOrEmpty(relyOnXml)) {
			List<ProductRelyOnItem> relyOnItems = ItemsConvertUtils.convertToRelyOnBean(context.transToBean(relyOnXml));
			if(relyOnItems!=null&&relyOnItems.size()>=1) {
				for(ProductRelyOnItem rely:relyOnItems) {
					PProduct product1=  new PProduct();
					product1.setProductId(rely.getRelyOnProductCode());
					product1 = productMapper.findById(product1);
					if(!StringUtils.isNullOrEmpty(product1.getProductAttr())) {
						Map<String,String> singleRelyPros = new HashMap<String,String>();
						result.put(rely.getRelyOnProductCode(), singleRelyPros);
						packRes(singleRelyPros, product1.getProductAttr(), context);
					}
				}
			}
		}
		if(!StringUtils.isNullOrEmpty(prosXml)) {
			Map<String,String> pros = new HashMap<String,String>();
			result.put(productId, pros);
			packRes(pros, prosXml, context);
		}
		return result;
	}

	private  void packRes(Map<String,String> resLabel,String prosXml,ProtyTransContext context) {
		List<ProductFieldItem> prosItems = ItemsConvertUtils.convertToFieldBean(context.transToBean(prosXml));
		if(prosItems!=null&&prosItems.size()>=1) {
			for(ProductFieldItem item:prosItems) {
				if(item.getProLabel().contains("30")) {
					resLabel.put(item.getProEnName(),"true");
				}
			}
		}
	}
}
