package com.sdp.serviceAccess.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.bonc.security.client.SecurityClient;
import com.bonc.security.entity.Tenantinfo;
import com.bonc.security.util.LoginUtil;
import com.bonc.security.util.StringUtils;
import com.sdp.common.constant.Dictionary;
import com.sdp.common.page.Pagination;
import com.sdp.serviceAccess.entity.PProduct;
import com.sdp.serviceAccess.entity.PProductCase;
import com.sdp.serviceAccess.entity.ProductFieldItem;
import com.sdp.serviceAccess.exception.ProductAccessException;
import com.sdp.serviceAccess.mapper.PProductCaseMapper;
import com.sdp.serviceAccess.mapper.PProductMapper;
import com.sdp.serviceAccess.protyTrans.ProptyTransDom4j;
import com.sdp.serviceAccess.protyTrans.ProtyTransContext;
import com.sdp.serviceAccess.service.IProductResQuotaService;
import com.sdp.serviceAccess.util.ItemsConvertUtils;

/**   
 * Copyright: Copyright (c) 2018 BONC
 * 
 * @ClassName: PProductResQuotaServiceImpl.java
 * @Description: 配额管理业务逻辑抽象接口层
 *
 * @version: v1.0.0
 * @author: renpengyuan
 * @date: 2018年11月6日 下午4:01:11 
 *
 * Modification History: 
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年11月6日    renpengyuan      v1.0.0         
 */
@Service
public class PProductResQuotaServiceImpl implements IProductResQuotaService{

	@Value("${bcm.resources.quota.url}")
	private String quotaUrl;

	@Autowired
	private PProductCaseMapper proCaseMapper;

	@Autowired
	private PProductMapper productMapper;

	@Override
	public Pagination getTenants(Pagination page) {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		List<Tenantinfo> tenants = new ArrayList<>();
		if(LoginUtil.isAdmin(requestAttributes.getRequest())) {
			tenants = SecurityClient.findAllTenants();
		}else {
			tenants.add(SecurityClient.findCurrentTenant(requestAttributes.getRequest()));
		}
		if(tenants==null||tenants.size()==0) {
			throw new ProductAccessException("tenants info is null or empty");
		}

		PProduct product = new PProduct();
		product.setProductId(QUOTA_PRODUCT_KEY);
		product  = productMapper.findById(product);
		if(product==null){
           return null;
		}
		String productAttr = product.getProductAttr();
		ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
		List<ProductFieldItem> items = ItemsConvertUtils.convertToFieldBean(context.transToBean(productAttr));
		Map<String,String> quotaLables = new HashMap<String,String>();
		Map<String,String> LableName = new HashMap<String,String>();
		if(!org.springframework.util.CollectionUtils.isEmpty(items)) {
			for(ProductFieldItem item:items) {
				if(item.getProLabel().contains("30")) {
					quotaLables.put(item.getProCode(),item.getProCode().toLowerCase());
					LableName.put(item.getProCode(),item.getProName());
				}
			}
		}
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		for(Tenantinfo tenantInfo:tenants) {
			Map<String,Object> tenantQuotas = new HashMap<String,Object>();
			result.add(tenantQuotas);
			Map<String,Object> tenantQuota= new HashMap<String,Object>();
			Map<String,Object> tenantQuotaUsed = new HashMap<String,Object>();
			tenantQuotas.put("tenantId", tenantInfo.getTenantId());
			tenantQuotas.put("tenantName", tenantInfo.getTenantName());
			tenantQuotas.put("resQuota", tenantQuota);
			tenantQuotas.put("usedQuota", tenantQuotaUsed);
			@SuppressWarnings("unchecked")
			String quotaStr= doGet(quotaUrl+tenantInfo.getTenantId(),null);
			JSONObject bcmResources = JSONObject.parseObject(quotaStr);
			System.out.println(bcmResources);
			for(String key:quotaLables.keySet()) {
				tenantQuota.put(LableName.get(key), bcmResources.get(quotaLables.get(key)));
				tenantQuotaUsed.put(LableName.get(key), bcmResources.get(USED_PRO_KEY+quotaLables.get(key).substring(0, 1).toUpperCase() + quotaLables.get(key).substring(1)));
			}
		}
		page.setList(result);
		page.setTotalCount((long)tenants.size());
	    return page;
	}


	@Override
	public Pagination findQuotaByTenant(String tenantId,Pagination page) {

		if(com.mysql.jdbc.StringUtils.isNullOrEmpty(tenantId)) {
			throw new ProductAccessException("tenantId is nill or empty");
		}

		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("tenantId", tenantId);
			params.put("productId", "bcm");
			params.put("caseStatus", Dictionary.InstanceWorkState.RUNNING.value);
			params.put("startNo", (page.getPageNo()*page.getPageSize()-page.getPageSize()));
			params.put("size", page.getPageSize());
			List<PProductCase> cases= proCaseMapper.findAllByConditionAndPage(params);
			ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
			if(cases!=null&&cases.size()>=1) {
				Iterator<PProductCase> itor = cases.iterator();
				while(itor.hasNext()) {
					PProductCase instance = itor.next();
					instance.setCaseAttrOrm(ItemsConvertUtils.convertToFieldBean(context.transToBean(instance.getCaseAttr())));
				}
			}
			Integer count = proCaseMapper.count(params);
			page.setList(cases);
			page.setTotalCount((long) count);
			page.setPageCount(new Double(Math.ceil(count/page.getPageSize())).longValue());
		}catch(Exception e) {
			throw new ProductAccessException("quota error",e);
		}

		return page;
	}

	private String doGet(String url, Map<String, Object> params) {
		URL u = null;
		HttpURLConnection con = null;
		StringBuffer sb = new StringBuffer();
		String urlParmas = null;
		if (params != null) {
			Iterator var5 = params.entrySet().iterator();

			while(var5.hasNext()) {
				Map.Entry<String, Object> e = (Map.Entry)var5.next();
				sb.append((String)e.getKey());
				sb.append("=");
				sb.append(e.getValue());
				sb.append("&");
			}
            if(sb.length()>=1){
				urlParmas=sb.substring(0, sb.length() - 1);
			}

		}

		StringBuffer result = new StringBuffer();

		String lines;
		try {
			if (!"".equals(sb.toString().trim())&& StringUtils.isNotBlank(urlParmas)) {
				url = url + "?" + urlParmas;
			}

			u = new URL(url);
			con = (HttpURLConnection)u.openConnection();
			con.setConnectTimeout(60*1000);
			con.setReadTimeout(60*1000);
			con.connect();
			System.out.println("[doGet]ResponseCode=====" + con.getResponseCode());
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

			while((lines = reader.readLine()) != null) {
				result.append(lines);
			}

			return result.toString();
		} catch (Exception var11) {
			System.out.println("API对接异常！" + url + "参数——》" + urlParmas);
			var11.printStackTrace();
			lines = "ERROR";
		} finally {
			if (con != null) {
				con.disconnect();
			}

		}

		return lines;
	}

}
