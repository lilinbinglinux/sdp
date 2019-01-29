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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.sdp.common.constant.Dictionary;
import com.sdp.common.entity.Status;
import com.sdp.serviceAccess.mapper.POperMapper;
import com.sdp.serviceAccess.proxy.IConstants;
import com.sdp.serviceAccess.proxy.model.CSvcBusiInfo;
import com.sdp.serviceAccess.util.RestTemplateUtil;

import org.springframework.stereotype.Service;

/**
 * @ClassName: DefaultGetStatusOperationComponent
 * @Description: TODO
 * @author: LiJinfeng
 * @date: 2018年1月23日 下午5:20:37
 */
@Service
public class DefaultStatusOperationComponent 
extends AbstractDefaultOperationComponent<Map<String, Object>, List<Map<String, Object>>>{

	//注入回调服务

	@Override
	@Autowired
	public void setCOperDao(POperMapper cOperDao) {
		super.cOperDao = cOperDao;
	}

	@Override
	public String getServiceName() {
		return "defaultStatusOperationComponent";
	}


	@Override
	public void checkRequest(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> params) {
		super.checkRequest(cSvcBusiInfo, params);
		if(null == params || StringUtils.isBlank(MapUtils.getString(params, "serviceIds"))) {
			throw new IllegalArgumentException(Dictionary.ErrorInfo.REQUEST_EXCEPTION.errorCode);
		}
	}


	@Override
	public boolean isNeedRequest() {	
		return false;
	}


	@Override
	public String getUrl(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> params) {
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
		.append("status")
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
	public Map<String, Object> getResult(Map<String, Object> response){
		Map<String, Object> result = new HashMap<String, Object>();
		result.putAll(response);
		return result;
	}

	@Override
	public  List<Map<String,Object>> parseFeedback(String feedbackStr) {
		return JSON.parseObject(feedbackStr, new TypeReference<List<Map<String,Object>>>(){});	
	}
    public static void main(String[] args) {
    System.out.println(JSON.parseObject("[]", new TypeReference<List<Map<String,Object>>>(){})	);	
	}

	@Override
	public Status callHandleMethod(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> result,
			List<Map<String, Object>> feedback) {
		return null;
	}




	@Override
	public Map<String, Object> getReturn(Integer code, String message, List<Map<String, Object>> feedback) {
		if(feedback!=null&&feedback.size()>=1){
			Map<String, Object> map;
			for(int i=0;i<feedback.size();i++){
				map = feedback.get(i);
				if(map!=null){
					String status = map.get("status")!=null?map.get("status").toString():"";
					if(status.equalsIgnoreCase("RUNNING") || status.equalsIgnoreCase("STOPPED") 
							|| status.equalsIgnoreCase("FAILED")|| status.equalsIgnoreCase("WAITING")||status.equalsIgnoreCase("WARNING")){
						map.put("status", this.convertStatus(status.toUpperCase()));
					}
					if(map.get("nodes")!=null){
						map.put("nodes", this.convertNodes(map.get("nodes").toString()));
					}
					feedback.set(i, map);
				}
			}
		}
		return super.getMapReturn(code, message, feedback);
	}

	public String convertStatus(String oldStatus) {
		if("RUNNING".equals(oldStatus)) {
			return "20";
		}
		else if("STOPPED".equals(oldStatus)) {
			return "30";
		}
		else if("FAILED".equals(oldStatus)){
			return "40";
		}else {
			return oldStatus;
		}
	}

	private List<Map> convertNodes(String nodesStr){
		List<Map> nodes = JSONObject.parseArray(nodesStr, Map.class);
		if(nodes!=null&&nodes.size()>=1){
			for(int i=0;i<nodes.size();i++){
				Map<String,Object> map = nodes.get(i);
				if(map!=null){
					String status = map.get("status")!=null?map.get("status").toString():"";
					if(status.equalsIgnoreCase("RUNNING") || status.equalsIgnoreCase("STOPPED") 
							|| status.equalsIgnoreCase("FAILED")){
						map.put("status", convertStatus(status.toUpperCase()));
						nodes.set(i, map);
					}
				}
			}
		}
		return nodes;
	}


}
