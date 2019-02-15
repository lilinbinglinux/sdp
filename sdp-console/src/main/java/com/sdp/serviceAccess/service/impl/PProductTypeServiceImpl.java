/**
 * 
 */
package com.sdp.serviceAccess.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.StringUtils;
import com.sdp.common.CurrentUserUtils;
import com.sdp.common.entity.Status;
import com.sdp.common.page.Pagination;
import com.sdp.serviceAccess.entity.PProduct;
import com.sdp.serviceAccess.entity.PProductType;
import com.sdp.serviceAccess.exception.ProductAccessException;
import com.sdp.serviceAccess.mapper.PProductMapper;
import com.sdp.serviceAccess.mapper.PProductTypeMapper;
import com.sdp.serviceAccess.service.IPProductTypeService;

/**   
 * Copyright: Copyright (c) 2018 BONC
 * 
 * @ClassName: PProductTypeServiceImpl.java
 * @Description: see IPProductTypeService
 *
 * @version: v1.0.0
 * @author: renpengyuan
 * @date: 2018年8月2日 下午4:01:48 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年8月2日     renpengyuan      v1.0.0               修改原因
 */
@Service("pproductTypeService")
public class PProductTypeServiceImpl implements IPProductTypeService{

	@Autowired
	private PProductTypeMapper proTypeMapper;

	@Autowired
	private PProductMapper proMapper;

	/* (non-Javadoc)
	 * @see com.sdp.serviceAccess.service.IPProductTypeService#categoryInfos(com.sdp.servflow.logSer.log.model.page.Pagination, com.sdp.serviceAccess.entity.PProductType)
	 */
	@Override
	public void categoryInfos(Pagination page, PProductType productType) {

		if(page.getPageSize()==0||page.getPageNo()==0L){
			throw new ProductAccessException("page params illegal");
		}
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			if(productType!=null){
				if(!StringUtils.isNullOrEmpty(productType.getProductTypeName())){
					params.put("productTypeName", productType.getProductTypeName());
				}
				if(!StringUtils.isNullOrEmpty(productType.getProductTypeCode())){
					params.put("productTypeCode", productType.getProductTypeCode());
				}
			}
			//params.put("tenantId", CurrentUserUtils.getInstance().getUser().getTenantId());
			int startNum  = (int) ((page.getPageNo()*page.getPageSize())-page.getPageSize());
			params.put("startNo", startNum);
			params.put("size", page.getPageSize());
			List<PProductType> cates= proTypeMapper.findAllByPage(params);
			page.setTotalCount(Long.valueOf(proTypeMapper.count(params)));
			page.setList(cates);
		}catch(Exception e){
			throw new ProductAccessException("categoryInfos error", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.sdp.serviceAccess.service.IPProductTypeService#createCategory(com.sdp.serviceAccess.entity.PProductType)
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Status createCategory(PProductType productType) {
		Status status;
		try{
			productType.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
			productType.setProductTypeId(UUID.randomUUID().toString().replace("-", ""));
			productType.setCreateBy(CurrentUserUtils.getInstance().getUser().getLoginId());
			productType.setCreateDate(new Date());
			productType.setSortId(proTypeMapper.maxSort());
			String parent = productType.getParentId();
			StringBuilder path = new StringBuilder();
			if(!StringUtils.isNullOrEmpty(parent)){
				PProductType parentType = new PProductType();
				parentType.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
				parentType.setProductTypeId(parent);
				parentType = proTypeMapper.findById(parentType);
				if(parentType!=null&&!StringUtils.isNullOrEmpty(parentType.getTypePath())){
					path.append(parentType.getTypePath());
				}
			}
			path.append("/").append(productType.getProductTypeCode());
			productType.setTypePath(path.toString());
			proTypeMapper.saveSingle(productType);
			status = new Status("创建成功", 200);
		}catch(Exception e){
			throw new ProductAccessException("create category error",e);
		}
		return status;
	}

	/* (non-Javadoc)
	 * @see com.sdp.serviceAccess.service.IPProductTypeService#updateCategory(com.sdp.serviceAccess.entity.PProductType)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Status updateCategory(PProductType productType) {
		Status status;
		try{
			productType.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
			productType.setCreateBy(CurrentUserUtils.getInstance().getUser().getLoginId());
			productType.setCreateDate(new Date());
			String parent = productType.getParentId();
			StringBuilder path = new StringBuilder();
			if(!StringUtils.isNullOrEmpty(parent)){
				PProductType parentType = new PProductType();
				parentType.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
				parentType.setProductTypeId(parent);
				parentType = proTypeMapper.findById(parentType);
				if(parentType!=null&&!StringUtils.isNullOrEmpty(parentType.getTypePath())){
					path.append(parentType.getTypePath());
				}
			}
			path.append("/").append(productType.getProductTypeCode());
			productType.setTypePath(path.toString());
			proTypeMapper.update(productType);
			status = new Status("修改成功", 200);
		}catch(Exception e){
			throw new ProductAccessException("update category error",e);
		}
		return status;
	}

	/* (non-Javadoc)
	 * @see com.sdp.serviceAccess.service.IPProductTypeService#deleteCategory(com.sdp.serviceAccess.entity.PProductType)
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Status deleteCategory(PProductType productType) {
		//查询 当前id为父元素的id，如果有则不能删除 
		Status status;
		String typeId= productType.getProductTypeId();
		if(StringUtils.isNullOrEmpty(typeId)){
			throw new ProductAccessException("typeId is null");
		}
		try {
			List<PProductType> parents = proTypeMapper.findAllByParentId(productType);
			if (parents != null && parents.size() > 0) {
				status = new Status("该服务类别为父元素，不能删除",500);
				return status;
			}
			PProduct product = new PProduct();
			product.setProductTypeId(typeId);
			List<PProduct> pros = proMapper.findAllByCondition(product);
			if (pros != null && pros.size() > 0) {
				status = new Status("该服务类别已被引用，不能删除", 500);
				return status;
			}
			proTypeMapper.delete(productType);
			status = new Status("删除成功", 200);
		} catch (Exception e) {
			throw new ProductAccessException("deleteSvcCategoryInfoByCataId",e);
		}
		return status;
	}

	/* (non-Javadoc)
	 * @see com.sdp.serviceAccess.service.IPProductTypeService#allCates()
	 */
	@Override
	public List<PProductType> allCates() {
		List<PProductType> allCates;
		try{
			PProductType productType = new PProductType();
//			productType.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
			allCates= proTypeMapper.findAll(productType);
		}catch(Exception e){
			throw new ProductAccessException("allCates find error",e);
		}
		return allCates;
	}

	/* (non-Javadoc)
	 * @see com.sdp.serviceAccess.service.IPProductTypeService#verfyCateCode(com.sdp.serviceAccess.entity.PProductType)
	 */
	@Override
	public Boolean verfyCateCode(String typeCode,String typeId) {
		
		if(StringUtils.isNullOrEmpty(typeCode)||StringUtils.isNullOrEmpty(typeId)){
			throw new ProductAccessException("type code  or id is null or blank"); 
		}
		
		try{
			PProductType proType = new PProductType();
			proType.setProductTypeCode(typeCode);
			proType.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
			proType = proTypeMapper.findByTypeCode(proType);
			if("default".equals(typeId)){
				return (Boolean.valueOf(proType==null));
			}else{
				if(proType!=null){
					return (Boolean.valueOf(typeId.equals(proType.getProductTypeId())));
				}else{
					return true;
				}
			}
		}catch(Exception e){
			throw new ProductAccessException("typeCode verfy error",e);
		}
	}

	/* (non-Javadoc)
	 * @see com.sdp.serviceAccess.service.IPProductTypeService#singleCate(com.sdp.serviceAccess.entity.PProductType)
	 */
	@Override
	public PProductType singleCate(String typeId) {
		PProductType proType ;
		if(StringUtils.isNullOrEmpty(typeId)){
			throw new ProductAccessException("productType is null or empty");
		}
		try{
			PProductType productType = new PProductType();
			productType.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
			productType.setProductTypeId(typeId);
			proType = proTypeMapper.findById(productType);
		}catch(Exception e){
            throw new ProductAccessException("single Cate find error",e);
		}
		return proType;
	}

	@Override
	public PProductType singleCateNoAuth(String typeId) {
		PProductType proType ;
		if(StringUtils.isNullOrEmpty(typeId)){
			throw new ProductAccessException("productType is null or empty");
		}
		try{
			PProductType productType = new PProductType();
			productType.setProductTypeId(typeId);
			proType = proTypeMapper.findById(productType);
		}catch(Exception e){
            throw new ProductAccessException("single Cate find error",e);
		}
		return proType;
	}
	
	@Override
	public List<PProductType> getAllPProductTypeByProduct(List<PProduct> proList) {
		List<String> idList = new ArrayList<String>();
		List<PProductType> typeList = new ArrayList<PProductType>();
		if (proList != null && proList.size() > 0) {
			for (PProduct pProduct : proList) {
				String id = pProduct.getProductTypeId();
				if (!StringUtils.isNullOrEmpty(id) && !idList.contains(id)) {
					PProductType type = this.singleCateNoAuth(id);
					if (type != null) {
						typeList.add(type);
						idList.add(id);
						getAllType(idList, typeList, type.getParentId());
					}
				}
			}
		}
		return typeList;
	}

	public void getAllType(List<String> idList, List<PProductType> typeList, String parentId) {
		if (!StringUtils.isNullOrEmpty(parentId) && !idList.contains(parentId)) {
			PProductType parentType = this.singleCateNoAuth(parentId);
			if(parentType!=null) {
				typeList.add(parentType);
				idList.add(parentId);
				getAllType(idList, typeList, parentType.getParentId());
			}
		}
	}

	@Override
	public Boolean verfyCateName(String typeName, String typeId) {
		if(StringUtils.isNullOrEmpty(typeName)||StringUtils.isNullOrEmpty(typeId)){
			throw new ProductAccessException("type name  or id is null or blank"); 
		}
		
		try{
			PProductType proType = new PProductType();
			proType.setProductTypeName(typeName);
			proType.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
			proType = proTypeMapper.findByTypeCode(proType);
			if("default".equals(typeId)){
				return (Boolean.valueOf(proType==null));
			}else{
				if(proType!=null){
					return (Boolean.valueOf(typeId.equals(proType.getProductTypeId())));
				}else{
					return true;
				}
			}
		}catch(Exception e){
			throw new ProductAccessException("typeName verfy error",e);
		}
	}
}
