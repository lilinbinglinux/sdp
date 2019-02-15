/**  
 * Copyright ©1997-2018 BONC Corporation, All Rights Reserved.
 * @Title: DefaultGetResourceUsageOperationComponent.java
 * @Prject: bconsole
 * @Package: com.sdp.serviceAccess.proxy.impl.defaults
 * @Description: TODO
 * @Company: BONC
 * @author: LiJinfeng  
 * @date: 2018年2月1日 下午4:44:34
 * @version: V1.0  
 */

package com.sdp.serviceAccess.proxy.impl.defaults;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.sdp.common.constant.Dictionary;
import com.sdp.common.entity.Status;
import com.sdp.serviceAccess.mapper.POperMapper;
import com.sdp.serviceAccess.proxy.IConstants;
import com.sdp.serviceAccess.proxy.model.CSvcBusiInfo;
import com.sdp.serviceAccess.util.RestTemplateUtil;

/**
 * @ClassName: DefaultGetResourceUsageOperationComponent
 * @Description: TODO
 * @author: LiJinfeng
 * @date: 2018年2月1日 下午4:44:34
 */
@Service
public class DefaultResourceUsageOperationComponent 
extends AbstractDefaultOperationComponent<Map<String, Object>, List<Map<String, Object>>>{

	@Override
	@Autowired
	public void setCOperDao(POperMapper cOperDao) {
		super.cOperDao = cOperDao;
	}


	@Override
	public String getServiceName() {
		return "defaultResourceUsageOperationComponent";
	}


	@Override
	public void checkRequest(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> params) {
		super.checkRequest(cSvcBusiInfo, params);
		if(null == params || StringUtils.isBlank(MapUtils.getString(params, "resourceNames"))) {
			throw new IllegalArgumentException(Dictionary.ErrorInfo.REQUEST_EXCEPTION.errorCode);
		}
	}


	@Override
	public boolean isNeedRequest() {	
		return false;
	}


	@Override
	public String getUrl(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> params) {
		//拼接url，带参数
		StringBuilder url = new StringBuilder();
		StringBuilder paramStr =  new StringBuilder();
		int i= 0 ;
		for (String key : params.keySet()) {
			if(i>0){
				paramStr.append(IConstants.SYMBOL_AND);
			}
			paramStr.append(key);
			paramStr.append(IConstants.SYMBOL_EQUALS);
			paramStr.append(params.get(key).toString());
			i++;
		}
		url.append(cSvcBusiInfo.getUrl())
		//		   .append(IConstants.SYMBOL_SLASH)
		//		   .append(cSvcBusiInfo.getServiceProvider())
		.append(IConstants.SYMBOL_SLASH)
		.append(cSvcBusiInfo.getServiceVersion())
		.append(IConstants.SYMBOL_SLASH)
		.append(cSvcBusiInfo.getServiceName())
		.append(IConstants.SYMBOL_SLASH)
		.append(cSvcBusiInfo.getInstanceNo())
		.append(IConstants.SYMBOL_SLASH)
		.append("usage")
		.append(IConstants.SYMBOL_SLASH)
		.append("batch")
		.append(IConstants.SYMBOL_QUESTION_MARK)
		.append(paramStr);
		return url.toString();
	}


	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> callServiceMethod(String url, Map<String, Object> request) {
		return RestTemplateUtil.get(url, Map.class, new HashMap<String, Object>());
	}


	@Override
	public boolean isHaveData() {
		return true;
	}


	@Override
	public boolean isNeedHandle() {
		return false;
	}

	@Override
	public List<Map<String,Object>> parseFeedback(String feedbackStr) {
		return JSON.parseObject(feedbackStr, new TypeReference<List<Map<String,Object>>>(){});	
	}


	@Override
	public Status callHandleMethod(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> result,
			List<Map<String, Object>> feedback) {
		return null;
	}


	@Override
	public Map<String, Object> getReturn(Integer code, String message, List<Map<String, Object>> feedback) {
		return super.getMapReturn(code, message, feedback);
	}

}
