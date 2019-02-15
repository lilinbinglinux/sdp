/**
 * 
 */
package com.sdp.serviceAccess.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.common.CurrentUserUtils;
import com.sdp.common.constant.Dictionary;
import com.sdp.common.entity.Status;
import com.sdp.common.page.Pagination;
import com.sdp.serviceAccess.entity.PProduct;
import com.sdp.serviceAccess.entity.PProductCase;
import com.sdp.serviceAccess.entity.PProductNode;
import com.sdp.serviceAccess.entity.ProductFieldItem;
import com.sdp.serviceAccess.exception.ProductAccessException;
import com.sdp.serviceAccess.mapper.PProductCaseMapper;
import com.sdp.serviceAccess.mapper.PProductMapper;
import com.sdp.serviceAccess.mapper.PProductNodeMapper;
import com.sdp.serviceAccess.protyTrans.ProptyTransDom4j;
import com.sdp.serviceAccess.protyTrans.ProtyTransContext;
import com.sdp.serviceAccess.service.IPProductNodeService;
import com.sdp.serviceAccess.util.ItemsConvertUtils;

/**   
 * Copyright: Copyright (c) 2018 BONC
 * 
 * @ClassName: PProductNodeServiceImpl.java
 * @Description: see IPProductNodeService
 *
 * @version: v1.0.0
 * @author: renpengyuan
 * @date: 2018年8月2日 下午4:10:31 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年8月2日     renpengyuan      v1.0.0               修改原因
 */
@Service
public class PProductNodeServiceImpl implements IPProductNodeService{


	@Autowired
	private PProductNodeMapper productNodeMapper;

	@Autowired
	private PProductCaseMapper productCaseMapper;
	
	@Autowired
	private PProductMapper productMapper;

	/* (non-Javadoc)
	 * @see com.sdp.serviceAccess.service.IPProductNodeService#saveNode(java.util.List)
	 */
	@Override
	public Status saveNode(List<PProductNode> nodes) {
		Status status;
		try{
			productNodeMapper.save(nodes);	
			status = new Status("创建成功", 200);
		}catch(Exception e){
			throw new ProductAccessException("saveNodes error", e);
		}
		return status;
	}

	/* (non-Javadoc)
	 * @see com.sdp.serviceAccess.service.IPProductNodeService#caseByNodes(com.sdp.serviceAccess.entity.PProductCase)
	 */
	@Override
	public Map<String, Object> caseByNodes(PProductCase productCase,Pagination page,boolean isNeedCompare) {
		Map<String,Object> result ;
		String tenantId= CurrentUserUtils.getInstance().getUser().getTenantId();
		String loginId= CurrentUserUtils.getInstance().getUser().getLoginId();
//		String tenantId = "tenant_system";
//		String loginId = "admin";
		String caseId = productCase.getCaseId();
		if(StringUtils.isEmpty(caseId)){
			throw new IllegalArgumentException("caseId is null or empty");
		}

		try{
			result = new HashMap<String,Object>();
			PProductNode node = new PProductNode();
			node.setCaseId(caseId);
			String currentDataAuth = CurrentUserUtils.getInstance().getUser().getDataAuth();
			if(Dictionary.AuthToken.LoginId.value.equals(currentDataAuth)){
				productCase.setCreateBy(loginId);
				node.setCreateBy(loginId);
				productCase.setTenantId(tenantId);
				node.setTenantId(tenantId);
			}
			if(Dictionary.AuthToken.TenantId.value.equals(currentDataAuth)){
				productCase.setTenantId(tenantId);
				node.setTenantId(tenantId);
			}
			productCase = productCaseMapper.findById(productCase);
			List<PProductNode> nodes = productNodeMapper.findByCondition(node);
			Map<String,String> caseAttrCompare = new HashMap<String,String>();
			Map<String,String> nodeAttrCompare = new HashMap<String,String>();
			ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
			if(isNeedCompare) {//是否需要对比动态列配置
				PProduct product = new PProduct();
				product.setProductId(productCase.getProductId());
				List<PProduct> products=  productMapper.findAllByCondition(product);
				if(products!=null&&products.size()>=1) {
					product=products.get(0);
				}
				String exampleAttr = product.getExampleAttr();
				String nodeAttr = product.getNodeAttr();
				if(!StringUtils.isEmpty(exampleAttr)) {
					List<ProductFieldItem> exampleAttrOrm = ItemsConvertUtils.convertToFieldBean(context.transToBean(exampleAttr));
					for(ProductFieldItem item: exampleAttrOrm) {
						caseAttrCompare.put(item.getProCode(), "true");
					}
				}
				if(!StringUtils.isEmpty(nodeAttr)) {
					List<ProductFieldItem> nodeAttrOrm = ItemsConvertUtils.convertToFieldBean(context.transToBean(nodeAttr));
					for(ProductFieldItem item: nodeAttrOrm) {
						nodeAttrCompare.put(item.getProCode(), "true");
					}
				}
			}
			String caseAttr = productCase.getCaseAttr();
			if(!StringUtils.isEmpty(caseAttr)){
				List<ProductFieldItem> caseAttrOrm = ItemsConvertUtils.convertToFieldBean(context.transToBean(caseAttr));
				if(isNeedCompare&&caseAttrCompare!=null&&caseAttrCompare.size()>=1) {
							Iterator<ProductFieldItem> itemsItor = caseAttrOrm.iterator();
							while(itemsItor.hasNext()) {
								String code = itemsItor.next().getProCode();
								if(!caseAttrCompare.containsKey(code)) { //如果对比的动态列中不含有当前的code，则从返回的orm中删除
									itemsItor.remove();
								}
							}
				}
				productCase.setCaseAttrOrm(caseAttrOrm);
			}
			long total=0L;
			List<PProductNode> nodes1 =  new ArrayList<>();
			if(nodes!=null&&nodes.size()>=1){
				total= nodes.size();
				long pageNo = page.getPageNo();
				int size = page.getPageSize();
				PProductNode singleNode;
				long length = size*pageNo;
				length = nodes.size()<=length?nodes.size():length;
				for(int i=(int)(pageNo-1)*size;i<length;i++){
					singleNode=nodes.get(i);
					caseAttr= singleNode.getCaseAttr();
					if(!StringUtils.isEmpty(caseAttr)){
						List<ProductFieldItem> nodeAttrOrm = ItemsConvertUtils.convertToFieldBean(context.transToBean(caseAttr));
						if(isNeedCompare&&nodeAttrCompare!=null&&nodeAttrCompare.size()>=1) {
									Iterator<ProductFieldItem> itemsItor = nodeAttrOrm.iterator();
									while(itemsItor.hasNext()) {
										String code = itemsItor.next().getProCode();
										if(!nodeAttrCompare.containsKey(code)) { //如果对比的动态列中不含有当前的code，则从返回的orm中删除
											itemsItor.remove();
										}
									}
						}
						singleNode.setCaseAttrOrm(nodeAttrOrm);
					}
					nodes1.add(singleNode);
				}
			}
			result.put("caseInfo", productCase);
			page.setList(nodes1);
			page.setTotalCount((long)nodes1.size());
			result.put("nodesInfo", page);
            result.put("count", total);
		}catch(Exception e){
            throw new ProductAccessException("find case By nodes error", e);
		}

		return result;
	}

	@Override
	public List<PProductNode> findByCase(PProductCase productcase) {
		PProductNode pnode = new PProductNode();
		pnode.setCaseId(productcase.getCaseId());
		List<PProductNode> pnodes = productNodeMapper.findByCondition(pnode);
		ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
		for(PProductNode node : pnodes) {
			if(StringUtils.isNotEmpty(node.getCaseAttr())) {
				List<ProductFieldItem> nodeAttrOrm = ItemsConvertUtils.convertToFieldBean(context.transToBean(node.getCaseAttr()));
				node.setCaseAttrOrm(nodeAttrOrm);
			}
		}
		return pnodes;
	}

}
