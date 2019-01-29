/**  
 * Copyright ©1997-2018 BONC Corporation, All Rights Reserved.
 * @Title: DefaultOperationComponent.java
 * @Prject: bconsole
 * @Package: com.bonc.serviceAccess.proxy.impl
 * @Description: TODO
 * @Company: BONC
 * @author: LiJinfeng  
 * @date: 2018年1月9日 下午7:28:20
 * @version: V1.0  
 */

package com.sdp.serviceAccess.proxy.impl.defaults;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.sdp.common.constant.Dictionary;
import com.sdp.common.entity.Status;
import com.sdp.serviceAccess.entity.POperInfo;
import com.sdp.serviceAccess.mapper.POperMapper;
import com.sdp.serviceAccess.proxy.model.CSvcBusiInfo;
import com.sdp.serviceAccess.proxy.specific.BusinessHandleService;
import com.sdp.serviceAccess.util.RestTemplateUtil;

/**
 * @ClassName: DefaultOperationComponent
 * @Description: TODO
 * @author: LiJinfeng
 * @date: 2018年1月9日 下午7:28:20
 */
@Service
public class DefaultCreateOperationComponent extends AbstractDefaultOperationComponent<Status, Map<String, Object>>{
	
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
		return "defaultCreateOperationComponent";
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
	@SuppressWarnings("unchecked")
	public Map<String, Object> callServiceMethod(String url, Map<String, Object> request) {
		return RestTemplateUtil.post(url, request, Map.class, new HashMap<String,Object>());
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
		return businessHandleService.createSVCOfHandleFeedback(cSvcBusiInfo, result, feedback);
	}
	
    
	@Override
	public Status getReturn(Integer code, String message, Map<String, Object> feedback) {
		return this.getStatusReturn(code, message);
	}
	

}
