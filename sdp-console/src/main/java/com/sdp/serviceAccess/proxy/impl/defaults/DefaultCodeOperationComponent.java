/**  
 * Copyright ©1997-2018 BONC Corporation, All Rights Reserved.
 * @Title: DefaultGetStatusOperationComponent.java
 * @Prject: bconsole
 * @Package: com.bonc.serviceAccess.proxy.impl.defaults
 * @Description: TODO
 * @Company: BONC
 * @author: LiJinfeng  
 * @date: 2018年1月23日 下午5:20:37
 * @version: V1.0  
 */

package com.sdp.serviceAccess.proxy.impl.defaults;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.sdp.common.constant.Dictionary;
import com.sdp.common.entity.Status;
import com.sdp.serviceAccess.mapper.POperMapper;
import com.sdp.serviceAccess.proxy.model.CSvcBusiInfo;
import com.sdp.serviceAccess.util.RestTemplateUtil;

/**
 * @ClassName: DefaultGetStatusOperationComponent
 * @Description: TODO
 * @author: LiJinfeng
 * @date: 2018年1月23日 下午5:20:37
 */
@Service
public class DefaultCodeOperationComponent 
		extends AbstractDefaultOperationComponent<Map<String, Object>, Map<String, Object>>{

	//注入回调服务
	
	@Override
	@Autowired
	public void setCOperDao(POperMapper cOperDao) {
		super.cOperDao = cOperDao;
	}
	
	
	@Override
	public String getServiceName() {
		return "defaultCodeOperationComponent";
	}
	
	
	@Override
	public void checkRequest(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> params) {
		super.checkRequest(cSvcBusiInfo, params);
		if(null == params || StringUtils.isBlank(MapUtils.getString(params, "keys"))) {
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
		return null;
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
	public Map<String,Object> parseFeedback(String feedbackStr) {
		return JSON.parseObject(feedbackStr, new TypeReference<Map<String,Object>>(){});	
	}
	
	
	@Override
	public Status callHandleMethod(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> result,
			Map<String, Object> feedback) {
		return null;
	}

	
	@Override
	public Map<String, Object> getReturn(Integer code, String message, Map<String, Object> feedback) {
		return super.getMapReturn(code, message, feedback);
	}

}
