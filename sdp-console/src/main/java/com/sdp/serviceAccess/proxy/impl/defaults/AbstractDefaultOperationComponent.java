/**  
 * Copyright ©1997-2018 BONC Corporation, All Rights Reserved.
 * @Title: AbstractOperationComponentService.java
 * @Prject: bconsole
 * @Package: com.bonc.serviceAccess.proxy
 * @Description: TODO
 * @Company: BONC
 * @author: LiJinfeng  
 * @date: 2018年1月8日 下午6:37:25
 * @version: V1.0  
 */

package com.sdp.serviceAccess.proxy.impl.defaults;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.sdp.common.constant.Dictionary;
import com.sdp.common.entity.Status;
import com.sdp.serviceAccess.entity.POperInfo;
import com.sdp.serviceAccess.mapper.POperMapper;
import com.sdp.serviceAccess.proxy.IConstants;
import com.sdp.serviceAccess.proxy.OperationComponent;
import com.sdp.serviceAccess.proxy.model.CSvcBusiInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: AbstractOperationComponentService
 * @Description: TODO
 * @author: LiJinfeng
 * @param <T> 
 * @param <R>
 * @date: 2018年1月8日 下午6:37:25
 */
//@Slf4j
public abstract class AbstractDefaultOperationComponent<T,R> implements OperationComponent<T,R>{
    
	private static Logger log = LoggerFactory.getLogger(Object.class);
	
	protected POperMapper cOperDao;	
	
	@Override
	public T operateService(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> params) {
		T returnInfo = null;
		Map<String, Object> response = null;
		try {
			//1.验证参数合法性
			this.checkRequest(cSvcBusiInfo, params);
			log.info("request:CSvcBusiInfo is {},param is {},", cSvcBusiInfo.toString(),
					null == params ? "null" : params.toString());
			//2.获取url
			String url = this.getUrl(cSvcBusiInfo, params);
			System.out.println(url);
			//3.获取请求参数request
			Map<String, Object> request = this.getRequest(cSvcBusiInfo, params);
			log.info("request info:{}",JSON.toJSONString(request));
			//4.调用SP接口
			response = this.callProxyServiceMethod(url, request, cSvcBusiInfo);
			log.info("response of SP:reponse is {}",null == response ? "null" : response.toString());
			//5.校验返回信息
			this.checkResponse(response, cSvcBusiInfo, request);
			//分情况返回
			if(!cSvcBusiInfo.getAsyn() && this.isNeedHandle()){
				//同步且需要处理数据
				//6.获取reesult
				Map<String, Object> result = this.getResult(response);
				//7.获取feedback
				R feedback = this.getFeedback(response, cSvcBusiInfo);
				Status status = new Status("成功", 200);
				if(!cSvcBusiInfo.getServiceId().equals("bdi")) {
					//8.调用handle方法
				    status = this.callProxyHandleMethod(cSvcBusiInfo, result, feedback, request);
					log.info("reponse of handle:status is {}", null == status ? "null" : status.toString());
					//9.解析status
					this.checkStatus(status, cSvcBusiInfo, request);
				}
				
				//10.获取返回结果
				returnInfo = this.getReturn(Dictionary.ResultCode.SUCCESS.value, status.getMessage(), feedback);
				return returnInfo;			
			}
			else if(!cSvcBusiInfo.getAsyn() && !this.isNeedHandle()){
				//同步不需要处理数据
				//7.获取feedback
				R feedback = this.getFeedback(response, cSvcBusiInfo);
				//10.获取返回结果
				returnInfo = this.getReturn(Dictionary.ResultCode.SUCCESS.value, "成功", feedback);
				return returnInfo;			
			}
			else {
				//异步
				returnInfo = this.getReturn(Dictionary.ResultCode.SUCCESS.value, "成功", null);
				return returnInfo;
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			String errorCode = e.getMessage();
			String errorDesc = Dictionary.ErrorInfo.getErrorDesc(errorCode);
			if(response!=null&&response.get("errDesc")!=null){
				errorDesc= errorDesc+":reason:"+response.get("errDesc").toString();
			}
			if(Dictionary.ErrorInfo.isNeedCallHandle(errorCode) && this.isNeedHandle()) {
				//需要调用handle方法的异常
				//1.获取reesult
				Map<String, Object> result = new HashMap<>();
				result.put("code", Dictionary.ResultCode.FAIL);
				result.put("errCode", errorCode);
	        	result.put("errDesc", errorDesc);
				//2.调用handle方法(不判断返回值，失败也不会再次调用)
				this.callHandleMethod(cSvcBusiInfo, result, null);
			}
			//3.返回结果(无论是否调用handle都返回失败)
			returnInfo = this.getReturn(Integer.parseInt(errorCode), errorDesc, null);
			return returnInfo;
		} catch (Exception e) {
			//未知异常
			e.printStackTrace();
			returnInfo = this.getReturn(Integer.parseInt(Dictionary.ErrorInfo.SERVER_INTERNAL_EXCEPTION.errorCode), 
					Dictionary.ErrorInfo.SERVER_INTERNAL_EXCEPTION.errorDesc, null);
			return returnInfo;
		} finally {
			log.info("return:returnInfo is {}", null == returnInfo ? "null" : returnInfo.toString());
		}
		
	}
    
	@Override
	public void checkRequest(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> params) {
		if(null == cSvcBusiInfo) {
			throw new IllegalArgumentException(Dictionary.ErrorInfo.REQUEST_EXCEPTION.errorCode);
		}
	}

	//默认获取url的方法
	@Override
	public String getUrl(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> params) {
		StringBuilder url = new StringBuilder();
		url.append(cSvcBusiInfo.getUrl())
//		   .append(IConstants.SYMBOL_SLASH)
//		   .append(cSvcBusiInfo.getServiceProvider())
		   .append(IConstants.SYMBOL_SLASH)
		   .append(cSvcBusiInfo.getServiceVersion())
		   .append(IConstants.SYMBOL_SLASH)
		   .append(cSvcBusiInfo.getServiceName())
		   .append(IConstants.SYMBOL_SLASH)
		   .append(cSvcBusiInfo.getInstanceNo());
		return url.toString();
	}
	
	//获取request
	@Override
	public Map<String, Object> getRequest(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> params){
		if(!this.isNeedRequest()) {
			return null;
		}
		if(null == params) {
			params = new HashMap<>();
		}
		params.put("tenantId",cSvcBusiInfo.getTenantId());
		params.put("userId", cSvcBusiInfo.getLoginId());
		params.put("serviceName", cSvcBusiInfo.getOrderServiceName());
		if(cSvcBusiInfo.getAsyn()) {
			//异步才有操作记录
			params.put("operId", this.getOperId(cSvcBusiInfo,params));
		}
		return params;
	}

	
	@Override
	public Map<String, Object> callProxyServiceMethod(String url, Map<String, Object> request, 
			CSvcBusiInfo cSvcBusiInfo){
		Map<String, Object> response = null;
		try {
			response = this.callServiceMethod(url, request);
		} catch (Exception e) {
			this.setState(request, Dictionary.OperState.SP_CALL_EXCEPTION.value,
					cSvcBusiInfo);
			e.printStackTrace();
			throw new IllegalArgumentException(Dictionary.ErrorInfo.SP_CALL_EXCEPTION.errorCode);
		}
		return response;
	}
	
	@Override
	public void checkResponse(Map<String, Object> response, CSvcBusiInfo cSvcBusiInfo, Map<String, Object> request) {
		if(null == response || null == response.get("code")) {
			this.setState(request, 
					Dictionary.OperState.SP_CALL_RSPONSE_EXCEPTION.value, cSvcBusiInfo);
			throw new IllegalArgumentException(Dictionary.ErrorInfo.SP_CALL_RESPONSE_EXCEPTION.errorCode);
		}
		String code = MapUtils.getString(response, "code");
		if("0".equals(code)) {
			if(this.isHaveData() && !cSvcBusiInfo.getAsyn() && null == response.get("data") ) {
				//应该有数据+同步+data为null
				this.setState(request, 
						Dictionary.OperState.SP_CALL_RSPONSE_EXCEPTION.value, cSvcBusiInfo);
				throw new IllegalArgumentException(Dictionary.ErrorInfo.SP_CALL_RESPONSE_EXCEPTION.errorCode);
			}
			else {
				//调用SP响应成功
				this.setState(request, 
						Dictionary.OperState.SP_CALL_RESPONSE_SUCCESS.value, cSvcBusiInfo);
			}	
		}
		else {
			if("-1".equals(code)) {
				log.error("======errdesc:===="+response.get("errDesc"));
				this.setState(request, 
						Dictionary.OperState.SP_CALL_RESPONSE_FAIL.value, cSvcBusiInfo);
				throw new IllegalArgumentException(Dictionary.ErrorInfo.SP_CALL_RESPONSE_FAIL.errorCode);
			}
			else {
				this.setState(request, 
						Dictionary.OperState.SP_CALL_RSPONSE_EXCEPTION.value, cSvcBusiInfo);
				throw new IllegalArgumentException(Dictionary.ErrorInfo.SP_CALL_RESPONSE_EXCEPTION.errorCode);
			}
		}
	}
	
	
	@Override
    public Map<String, Object> getResult(Map<String, Object> response){
		Map<String, Object> result = new HashMap<>();
    	result.putAll(response);
    	result.remove("data");
    	return result;
    }
	
    
	//获取返回结果中的data
	@Override
	public R getFeedback(Map<String, Object> response, CSvcBusiInfo cSvcBusiInfo){
		if(null != response.get("data")) {
			String dataStr = response.get("data").toString();
			return this.parseFeedback(dataStr);
		}
		else {
			return null;
		}
    }
	
	@Override
	public Status callProxyHandleMethod(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> result, R feedback, 
			Map<String, Object> request) {
		Status status = null;
		try {
			status = this.callHandleMethod(cSvcBusiInfo, result, feedback);
		} catch (Exception e) {
			this.setState(request, Dictionary.OperState.HANDLE_EXCEPTION.value,
					cSvcBusiInfo);
			e.printStackTrace();
			throw new IllegalArgumentException(Dictionary.ErrorInfo.HANDLE_EXCEPTION.errorCode);
		}
		return status;
	}

	@Override
	public void checkStatus(Status status, CSvcBusiInfo cSvcBusiInfo, Map<String, Object> request) {
		if (null == status || null == status.getCode()) {
			this.setState(request, 
					Dictionary.OperState.HANDLE_RESPONSE_EXCEPTION.value, cSvcBusiInfo);
			throw new IllegalArgumentException(Dictionary.ErrorInfo.HANDLE_RESPONSE_EXCEPTION.errorCode);
		} else {
			if ("2".equals(status.getCode().toString().substring(0, 1))) {
				//handle成功
				this.setState(request, 
						Dictionary.OperState.HANDLE_RESPONSE_SUCCESS.value, cSvcBusiInfo);
			}
			else {
				//handle失败
				this.setState(request, 
						Dictionary.OperState.HANDLE_RESPONSE_FAIL.value, cSvcBusiInfo);
				throw new IllegalArgumentException(Dictionary.ErrorInfo.HANDLE_RESPONSE_FAIL.errorCode);
			}
		}
	}

	//常用的两个getReturn方法
	protected Status getStatusReturn(Integer code,String message) {	
		Status status = new Status();
		if(Dictionary.ResultCode.SUCCESS.value == code) {
			status.setCode(Dictionary.HttpStatus.OK.value);
		}
		else {
			status.setCode(Dictionary.HttpStatus.UNPROCESABLE.value);
		}
		status.setMessage(message);
		return status;
	}
	
    protected Map<String,Object> getMapReturn(Integer code,String message,R feedback) {
    	Map<String,Object> result = new HashMap<>();
    	if(Dictionary.ResultCode.SUCCESS.value == code) {
    		result.put("code", code);
    		if(null != feedback) {
    			result.put("data", feedback);
    		}
    	}
    	else {
    		result.put("code", Dictionary.ResultCode.FAIL.value);
    		result.put("errorCode", code);
    		result.put("errorDesc", message);
    	}
		return result;
	}
	//默认COperInfo组装方法
	protected long getOperId(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> params) {
		POperInfo cOperInfo = new POperInfo();
		cOperInfo.setBusiInfo(JSON.toJSONString(cSvcBusiInfo));
		cOperInfo.setParamsInfo(JSON.toJSONString(params));
		cOperInfo.setCreateBy(cSvcBusiInfo.getLoginId());
		cOperInfo.setServiceName(this.getServiceName());
		cOperInfo.setState(Dictionary.OperState.PENDING.value);
		cOperInfo.setTenantId(cSvcBusiInfo.getTenantId());
		cOperInfo.setCreateBy(cSvcBusiInfo.getLoginId());
		cOperInfo.setCreateDate(new Date());
		cOperDao.saveSingle(cOperInfo);
		return cOperInfo.getOperId();
	}
	
	protected void setState(Map<String, Object> request, int state, CSvcBusiInfo cSvcBusiInfo){
		if(null == request || null == request.get("operId") || !cSvcBusiInfo.getAsyn()) {
			return;
		}
		//异步才有操作记录
		long operId = MapUtils.getLongValue(request, "operId");
		POperInfo cOperInfo = new POperInfo();
		cOperInfo.setOperId(operId);
		cOperInfo.setTenantId(cSvcBusiInfo.getTenantId());
		cOperInfo = cOperDao.findById(cOperInfo);
		if(null != cOperInfo) {
			cOperInfo.setState(state);
			 cOperDao.saveSingle(cOperInfo);
		}
		
	}
}
