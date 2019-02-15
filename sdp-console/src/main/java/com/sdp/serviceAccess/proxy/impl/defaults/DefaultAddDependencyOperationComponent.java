package com.sdp.serviceAccess.proxy.impl.defaults;

import java.util.HashMap;
import java.util.List;

/**  
 * Copyright ©1997-2018 BONC Corporation, All Rights Reserved.
 * @Title: DefaultOperationComponent.java
 * @Prject: bconsole
 * @Package: com.sdp.serviceAccess.proxy.impl
 * @Description: TODO
 * @Company: BONC
 * @author: LiJinfeng  
 * @date: 2018年1月9日 下午7:28:20
 * @version: V1.0  
 */

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.sdp.common.constant.Dictionary;
import com.sdp.common.entity.Status;
import com.sdp.serviceAccess.entity.ProductRelyOnItem;
import com.sdp.serviceAccess.mapper.POperMapper;
import com.sdp.serviceAccess.proxy.model.CSvcBusiInfo;
import com.sdp.serviceAccess.proxy.specific.BusinessHandleService;
import com.sdp.serviceAccess.util.RestTemplateUtil;


/**
 * @ClassName: DefaultChangeResourceOperationComponent
 * @Description: TODO
 * @author: RENPENGYUAN
 * @date: 2018年10月18日 下午7:05:20
 */
@Service
public class DefaultAddDependencyOperationComponent extends  AbstractDefaultOperationComponent<Status, Map<String, Object>>{
//	注入回调服务
	@Autowired 
	private BusinessHandleService businessHandleService;
	
	@Override
	@Autowired
	public void setCOperDao(POperMapper cOperDao) {
		super.cOperDao = cOperDao;
	}
	
	
	@Override
	public String getServiceName() {
		return "addDependencyOperationComponent";
	}
    
	
	@Override
	public void checkRequest(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> params) {
		super.checkRequest(cSvcBusiInfo, params);
		if(null == params) {
			throw new IllegalArgumentException(Dictionary.ErrorInfo.REQUEST_EXCEPTION.errorCode);
		}
	}
	
	
	@Override
	public boolean isNeedRequest() {
		return true;
	}

	

	@Override
	public Map<String, Object> getRequest(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> params) {
		if(null == params) {
			params = new HashMap<>();
		}
		super.getRequest(cSvcBusiInfo, params);
		if(cSvcBusiInfo.getIsRelyOnProduct()) {
			List<ProductRelyOnItem> relyItems = cSvcBusiInfo.getRelyOnAttrOrm();
			for(ProductRelyOnItem item:relyItems) {
				String serviceId = item.getRelyOnCaseId();
				@SuppressWarnings("unchecked")
				Map<String,Object> param = (Map<String, Object>) params.get(item.getRelyOnProductCode());
				if(param!=null&&param.size()>=1) {
					param.put("serviceId",serviceId);
				}
			}
		}else {
			params.put("serviceId", cSvcBusiInfo.getInstanceNo());
		}
		params.put("action", "addDependency");
		return params;
	}
	

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> callServiceMethod(String url, Map<String, Object> request) {
		return RestTemplateUtil.put(url, request, Map.class, new HashMap<String, Object>());
	}
	

	@Override
	public boolean isHaveData() {
		return true;
	}
	
	
	@Override
	public boolean isNeedHandle() {
		return true;
	}
	
	@Override
	public Map<String,Object> parseFeedback(String feedbackStr) {
		return JSON.parseObject(feedbackStr, new TypeReference<Map<String,Object>>(){});	
	}
	
	
	@Override
	public Status callHandleMethod(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> result, Map<String, Object> feedback) {
		return businessHandleService.changeResSVCOfHandleFeedback(cSvcBusiInfo, result, feedback);
	}
	
    
	@Override
	public Status getReturn(Integer code, String message, Map<String, Object> feedback) {
		return this.getStatusReturn(code, message);
	}
}
