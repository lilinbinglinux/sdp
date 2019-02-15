/**  
 * Copyright ©1997-2018 BONC Corporation, All Rights Reserved.
 * @Title: DefaultStopOperationComponent.java
 * @Prject: bconsole
 * @Package: com.sdp.serviceAccess.proxy.impl.defaults
 * @Description: TODO
 * @Company: BONC
 * @author: LiJinfeng  
 * @date: 2018年1月12日 上午10:06:44
 * @version: V1.0  
 */

package com.sdp.serviceAccess.proxy.impl.defaults;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.sdp.common.entity.Status;
import com.sdp.serviceAccess.mapper.POperMapper;
import com.sdp.serviceAccess.proxy.model.CSvcBusiInfo;
import com.sdp.serviceAccess.proxy.specific.BusinessHandleService;
import com.sdp.serviceAccess.util.RestTemplateUtil;

/**
 * @ClassName: DefaultStopOperationComponent
 * @Description: TODO
 * @author: LiJinfeng
 * @date: 2018年1月12日 上午10:06:44
 */
@Service
public class DefaultStopOperationComponent extends AbstractDefaultOperationComponent<Status, Object>{

	//注入回调服务
	@Autowired 
	private BusinessHandleService businessHandleService;
	
	@Override
	@Autowired
	public void setCOperDao(POperMapper cOperDao) {
		super.cOperDao = cOperDao;
	}
	
	
	@Override
	public String getServiceName() {
		return "defaultStopOperationComponent";
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
		params.put("serviceId",cSvcBusiInfo.getInstanceNo());
		params.put("action", "stop");
		return params;
	}
	

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> callServiceMethod(String url, Map<String, Object> request) {
		return RestTemplateUtil.put(url, request, Map.class, new HashMap<String, Object>());
	}
	
    
	@Override
	public boolean isHaveData() {
		return false;
	}
	
	
	@Override
	public boolean isNeedHandle() {
		return true;
	}
	
	@Override
	public List<Map<String,Object>> parseFeedback(String feedbackStr) {
		return JSON.parseObject(feedbackStr, new TypeReference<List<Map<String,Object>>>(){});	
	}
	
    
	@Override
	public Status callHandleMethod(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> result, Object feedback) {
		return businessHandleService.offSVCOfHandleFeedback(cSvcBusiInfo, result);
	}
	

	@Override
	public Status getReturn(Integer code, String message, Object feedback) {
		return this.getStatusReturn(code, message);
	}
	
	

}
