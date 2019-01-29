/**
 * 
 */
package com.sdp.serviceAccess.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.xml.XmlBinaryStreamProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.StringUtils;
import com.sdp.common.CurrentUserUtils;
import com.sdp.common.entity.Status;
import com.sdp.frame.web.entity.user.User;
import com.sdp.serviceAccess.entity.PProduct;
import com.sdp.serviceAccess.entity.PProductPackage;
import com.sdp.serviceAccess.entity.ProductFieldItem;
import com.sdp.serviceAccess.entity.ProductRelyOnItem;
import com.sdp.serviceAccess.exception.ProductAccessException;
import com.sdp.serviceAccess.mapper.PProductMapper;
import com.sdp.serviceAccess.mapper.PProductPackageMapper;
import com.sdp.serviceAccess.protyTrans.ProptyTransDom4j;
import com.sdp.serviceAccess.protyTrans.ProtyTransContext;
import com.sdp.serviceAccess.service.IPProductPackageService;
import com.sdp.serviceAccess.util.ItemsConvertUtils;
import com.sdp.serviceAccess.util.RestTemplateUtil;

/**   
 * Copyright: Copyright (c) 2018 BONC
 * 
 * @ClassName: PProductPackageServiceImpl.java
 * @Description: see IPProductPackageService
 *
 * @version: v1.0.0
 * @author: renpengyuan
 * @date: 2018年8月2日 下午4:06:28 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年8月2日     renpengyuan      v1.0.0               修改原因
 */
@Service
public class PProductPackageServiceImpl implements IPProductPackageService{

	@Autowired
	private PProductPackageMapper packageMapper;

	@Autowired
	private PProductMapper productMapper;

	/* (non-Javadoc)
	 * @see com.bonc.serviceAccess.service.IPProductPackageService#createPackage(com.bonc.serviceAccess.entity.PProductPackage)
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Status createPackage(PProductPackage productPackage) {
		Status status; 
		if(productPackage==null){
			throw new IllegalArgumentException("package product model is null");
		}
		try{
			productPackage.setPackageId(UUID.randomUUID().toString().replace("-", ""));
			List<ProductFieldItem> attrItems = productPackage.getPackageBasicAttrOrm();
			ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
			if(attrItems!=null&&attrItems.size()>=1){
				productPackage.setPackageAttr(context.transToXml(ItemsConvertUtils.convertToFieldPro(attrItems), "fieldItems", "fieldItem"));
			}
			attrItems = productPackage.getPackageControlResAttrOrm();
			if(attrItems!=null&&attrItems.size()>=1){
				productPackage.setPackageControlAttr(context.transToXml(ItemsConvertUtils.convertToFieldPro(attrItems), "fieldItems", "fieldItem"));
			}

			//判断是否为依赖服务的套餐配置   先判断是否有依赖服务的属性配置，再判断依赖服务的是否可以配置的服务是否匹配
			Map<String,List<ProductFieldItem>> relyOnBasicAttrOrm = productPackage.getRelyOnBasicAttrOrm();
			if(relyOnBasicAttrOrm!=null&&relyOnBasicAttrOrm.size()>=1) {
				productPackage.setPackageAttr(context.transToXml(ItemsConvertUtils.convertToFieldPro(relyOnBasicAttrOrm), "fieldItems", "fieldItem"));
			}
			productPackage.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
			productPackage.setCreateDate(new Date());
			productPackage.setCreateBy(CurrentUserUtils.getInstance().getUser().getLoginId());
			packageMapper.saveSingle(productPackage);
			status = new Status("创建成功",200);
		}catch(Exception e){
			throw new ProductAccessException("create product package error",e);
		}
		return status;
	}

	/* (non-Javadoc)
	 * @see com.bonc.serviceAccess.service.IPProductPackageService#singlePackageInfo(java.lang.String)
	 */
	@Override
	public PProductPackage singlePackageInfo(String productPackageId) {
		PProductPackage productPackage = new PProductPackage();

		if(StringUtils.isNullOrEmpty(productPackageId)){
			throw new IllegalArgumentException("productPackageid is null or empty");
		}

		try{
			//productPackage.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
			productPackage.setPackageId(productPackageId);
			productPackage = packageMapper.findById(productPackage);

			if(productPackage!=null){
				String attr= productPackage.getPackageAttr();
				ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
				if(!StringUtils.isNullOrEmpty(attr)){
					if(isRelyOnProduct(productPackage.getProductId(), productPackage.getTenantId())) {
						//说明是依赖服务
						productPackage.setRelyOnBasicAttrOrm(ItemsConvertUtils.convertToFieldBeanRelyOn(context.transToBean(attr)));
						productPackage.setRelyOnBasicAttr(null);
					}else {
						List<ProductFieldItem> fieldItems= ItemsConvertUtils.convertToFieldBean(context.transToBean(attr));
						User user = CurrentUserUtils.getInstance().getUser();
						if (fieldItems != null && fieldItems.size() >= 1) {
							if (user != null) {
								checkKeyValue(fieldItems, CurrentUserUtils.getInstance().getUser().getTenantId());
							}
						}
						productPackage.setPackageBasicAttrOrm(fieldItems);
						productPackage.setPackageAttr(null);
					}
				}
				attr = productPackage.getPackageControlAttr();
				if(!StringUtils.isNullOrEmpty(attr)){
					productPackage.setPackageControlResAttrOrm(ItemsConvertUtils.convertToFieldBean(context.transToBean(attr)));
					productPackage.setPackageControlAttr(null);
				}
			}

		}catch(Exception e){
			throw new ProductAccessException("singlepackage info find error",e);

		}
		return productPackage;
	}

	/* (non-Javadoc)
	 * @see com.bonc.serviceAccess.service.IPProductPackageService#updatePackage(com.bonc.serviceAccess.entity.PProductPackage)
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Status updatePackage(PProductPackage productPackage) {
		Status status;
		if(productPackage==null){
			throw new IllegalArgumentException("productPackage is null");
		}

		try{
			//productPackage.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
			PProductPackage old = packageMapper.findById(productPackage);
			if(old==null){
				throw new ProductAccessException("old package is null ");
			}
			old.setCreateBy(CurrentUserUtils.getInstance().getUser().getLoginId());
			old.setCreateDate(new Date());
			old.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
			old.setPackageName(productPackage.getPackageName());
			old.setPackageIntrdo(productPackage.getPackageIntrdo());
			List<ProductFieldItem> attrItems = productPackage.getPackageBasicAttrOrm();
			ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
			if(attrItems!=null&&attrItems.size()>=1){
				old.setPackageAttr(context.transToXml(ItemsConvertUtils.convertToFieldPro(attrItems), "fieldItems", "fieldItem"));
			}
			attrItems = productPackage.getPackageControlResAttrOrm();
			if(attrItems!=null&&attrItems.size()>=1){
				old.setPackageControlAttr(context.transToXml(ItemsConvertUtils.convertToFieldPro(attrItems), "fieldItems", "fieldItem"));
			}
			//判断是否为依赖服务的套餐配置   先判断是否有依赖服务的属性配置，再判断依赖服务的是否可以配置的服务是否匹配
			Map<String,List<ProductFieldItem>> relyOnBasicAttrOrm = productPackage.getRelyOnBasicAttrOrm();
			if(relyOnBasicAttrOrm!=null&&relyOnBasicAttrOrm.size()>=1) {
				old.setPackageAttr(context.transToXml(ItemsConvertUtils.convertToFieldPro(relyOnBasicAttrOrm), "fieldItems", "fieldItem"));
			}
			old.setOrderType(productPackage.getOrderType());
			old.setControlType(productPackage.getControlType());
			if(!StringUtils.isNullOrEmpty(productPackage.getPackageStatus())){
				old.setPackageStatus(productPackage.getPackageStatus());
			}
			packageMapper.update(old);
			status = new Status("修改成功",200);
		}catch(Exception e){
			throw new ProductAccessException("update package error", e);
		}
		return status;
	}

	/* (non-Javadoc)
	 * @see com.bonc.serviceAccess.service.IPProductPackageService#deletePackage(com.bonc.serviceAccess.entity.PProductPackage)
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Status deletePackage(PProductPackage productPackage) {

		Status status;

		if(productPackage==null){
			throw new IllegalArgumentException("productPackage is null");
		}

		try{
			productPackage.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
			packageMapper.delete(productPackage);
			status = new Status("删除成功",200);
		}catch(Exception e){
			throw new ProductAccessException("delete package error", e);
		}

		return status;
	}

	/* (non-Javadoc)
	 * @see com.bonc.serviceAccess.service.IPProductPackageService#productPackages(java.lang.String)
	 */
	@Override
	public Map<String,Object> productPackages(String productId) {

		if(StringUtils.isNullOrEmpty(productId)){
			throw new IllegalArgumentException("productId is null or empty");
		}
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			PProductPackage productPackage = new PProductPackage();
			PProduct product = new PProduct();
			//productPackage.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
			productPackage.setProductId(productId);
			product.setProductId(productId);
			//product.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());

			product = productMapper.findById(product);

			if(product==null){
				result.put("productInfo", null);
				result.put("packageInfos", null);
				return result;
			}
			product.setProductLogo(null);
			product.setProductAttr(null);
			List<PProductPackage> packages = packageMapper.findAllByCondition(productPackage);

			String attr= product.getProductAttr();
			ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
			if(!StringUtils.isNullOrEmpty(attr)){
				product.setProductAttrOrm(ItemsConvertUtils.convertToFieldBean(context.transToBean(attr)));
				product.setProductAttr(null);
			}
			attr = product.getRelyOnAttr();
			if(!StringUtils.isNullOrEmpty(attr)) {
				product.setRelyOnAttrOrm(ItemsConvertUtils.convertToRelyOnBean(context.transToBean(attr)));
			    product.setRelyOnAttr(null);
			}
			result.put("productInfo", product);
			result.put("packageInfos", packages);

		}catch(Exception e){
			throw new ProductAccessException("productPackages info find error",e);

		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.bonc.serviceAccess.service.IPProductPackageService#singlePackageInfo(java.lang.String, java.lang.String)
	 */
	@Override
	public PProductPackage singlePackageInfo(String productPackageId, String tenantId) {
		PProductPackage productPackage = new PProductPackage();

		if(StringUtils.isNullOrEmpty(productPackageId)){
			throw new IllegalArgumentException("productPackageid is null or empty");
		}

		try{
			//productPackage.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
			productPackage.setPackageId(productPackageId);
			productPackage = packageMapper.findById(productPackage);

			if(productPackage!=null){
				String attr= productPackage.getPackageAttr();
				ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
				if(!StringUtils.isNullOrEmpty(attr)){
					if(isRelyOnProduct(productPackage.getProductId(), productPackage.getTenantId())) {
						//说明是依赖服务
						productPackage.setRelyOnBasicAttrOrm(ItemsConvertUtils.convertToFieldBeanRelyOn(context.transToBean(attr)));
						productPackage.setRelyOnBasicAttr(null);
					}else {
						List<ProductFieldItem> fieldItems= ItemsConvertUtils.convertToFieldBean(context.transToBean(attr));
						if(fieldItems!=null&&fieldItems.size()>=1){
							checkKeyValue(fieldItems, tenantId);
						}
						productPackage.setPackageBasicAttrOrm(fieldItems);
						productPackage.setPackageAttr(null);
					}
				}
				attr = productPackage.getPackageControlAttr();
				if(!StringUtils.isNullOrEmpty(attr)){
					productPackage.setPackageControlResAttrOrm(ItemsConvertUtils.convertToFieldBean(context.transToBean(attr)));
					productPackage.setPackageControlAttr(null);
				}
			}

		}catch(Exception e){
			throw new ProductAccessException("singlepackage info find error",e);

		}
		return productPackage;
	}

	/* (non-Javadoc)
	 * @see com.bonc.serviceAccess.service.IPProductPackageService#productPackages(java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, Object> productPackages(String productId, String tenantId) {
		if(StringUtils.isNullOrEmpty(productId)||StringUtils.isNullOrEmpty(tenantId)){
			throw new IllegalArgumentException("productId or tenantId is null or empty");
		}
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			PProductPackage productPackage = new PProductPackage();
			PProduct product = new PProduct();
			//productPackage.setTenantId(tenantId);
			productPackage.setProductId(productId);
			product.setProductId(productId);
			//product.setTenantId(tenantId);
			product.setImgFlag("true");

			product = productMapper.findById(product);

			if(product==null){
				result.put("productInfo", null);
				result.put("packageInfos", null);
				return result;
			}

			product.setProductAttr(null);
			List<PProductPackage> packages = packageMapper.findAllByCondition(productPackage);

			String attr= product.getProductAttr();
			ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
			if(!StringUtils.isNullOrEmpty(attr)){
				product.setProductAttrOrm(ItemsConvertUtils.convertToFieldBean(context.transToBean(attr)));
				product.setProductAttr(null);
			}
			attr = product.getRelyOnAttr();
			if(!StringUtils.isNullOrEmpty(attr)) {
				product.setRelyOnAttrOrm(ItemsConvertUtils.convertToRelyOnBean(context.transToBean(attr)));
			    product.setRelyOnAttr(null);
			}
			result.put("productInfo", product);
			result.put("packageInfos", packages);

		}catch(Exception e){
			throw new ProductAccessException("productPackages info find error",e);

		}
		return result;
	}
	private void checkKeyValue(List<ProductFieldItem> items,String tenantId){
		if(items!=null&&items.size()>=1){
			for(ProductFieldItem item:items){
				String checkUrl = item.getProInitValue();
				if(!StringUtils.isNullOrEmpty(checkUrl)&&!StringUtils.isNullOrEmpty(tenantId)){
					if(checkUrl.startsWith("http://")||checkUrl.startsWith("https://")){
						Map<String,Object> params =new HashMap<String,Object>();
						params.put("tenantId", tenantId);
						Map<String,Object> response = RestTemplateUtil.post(checkUrl,params,Map.class,new HashMap<String,Object>());
						if(response!=null&&response.size()>=1){
							if(response.get("data")!=null){
								item.setProInitValue(response.get("data").toString());
							}
						}
					}

				}
			}
		}
	}
	public Boolean isRelyOnProduct(String productId,String tenantId) {
		PProduct product = new PProduct();
		product.setProductId(productId);
		product = productMapper.findById(product);
		return !StringUtils.isNullOrEmpty(product.getRelyOnAttr()) ;
	}

}
