/**  
 * Copyright ©1997-2018 BONC Corporation, All Rights Reserved.
 * @Title: OperationComponent.java
 * @Prject: bconsole
 * @Package: com.bonc.serviceAccess.proxy
 * @Description: TODO
 * @Company: BONC
 * @author: LiJinfeng  
 * @date: 2018年1月8日 下午5:30:33
 * @version: V1.0  
 */

package com.sdp.serviceAccess.proxy;

import java.util.Map;

import com.sdp.common.entity.Status;
import com.sdp.serviceAccess.mapper.POperMapper;
import com.sdp.serviceAccess.proxy.model.CSvcBusiInfo;

/**
 * @ClassName: OperationComponent
 * @Description: TODO
 * @author: LiJinfeng
 * @date: 2018年1月10日 下午2:20:39
 * @param <T>  返回值类型
 * @param <R>  SP返回参数中data的类型
 */
public interface OperationComponent<T,R> {
	
    /**
     * @Title: operateService
     * @Description: 统一服务操作方法，统一入口
     * @return: T
     * @param cSvcBusiInfo
     * @param params
     * @return
     * @throws: 
     */
    T operateService(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> params);
    
    /**
     * @Title: checkRequest
     * @Description: 验证请求参数
     * @return: void
     * @param cSvcBusiInfo
     * @param params
     * @throws: 
     */
    void checkRequest(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> params);
    
    /**
     * @Title: getUrl
     * @Description: 获取url
     * @return: String
     * @param cSvcBusiInfo
     * @param params
     * @return
     * @throws: 
     */
    String getUrl(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> params);
    
    /**
     * @Title: isNeedRequest
     * @Description: 是否需要请求参数（get一定不需要）
     * @return: boolean
     * @return
     * @throws: 
     */
    boolean isNeedRequest();
    
    /**
     * @Title: getRequest
     * @Description: 获取请求参数（url中的参数不在次获取，在getUrl中拼接）
     * @return: Map<String,Object>
     * @param cSvcBusiInfo
     * @param params
     * @return
     * @throws: 
     */
    Map<String, Object> getRequest(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> params);
    
    /**
     * @Title: setCOperDao
     * @Description: 注入Dao对象
     * @return: void
     * @param cOperDao
     * @throws: 
     */
    void setCOperDao(POperMapper cOperDao);
    
    /**
     * @Title: callProxyServiceMethod
     * @Description: 调用服务提供方接口的代理方法
     * @return: Map<String,Object>
     * @param url
     * @param request
     * @return
     * @throws: 
     */
    Map<String, Object> callProxyServiceMethod(String url, Map<String, Object> request, CSvcBusiInfo cSvcBusiInfo);
    
    /**
     * @Title: callServiceMethod
     * @Description: 调用服务提供方接口
     * @return: Map<String,Object>
     * @param url
     * @param request
     * @return
     * @throws: 
     */
    Map<String, Object> callServiceMethod(String url, Map<String, Object> request);
      
    /**
     * @Title: checkResponse
     * @Description: 验证SP返回值
     * @return: void
     * @param response
     * @param cSvcBusiInfo
     * @param request
     * @throws: 
     */
    void checkResponse(Map<String, Object> response, CSvcBusiInfo cSvcBusiInfo, Map<String, Object> request);
    
    /**
     * @Title: getResult
     * @Description: 获取result
     * @return: Map<String,Object>
     * @param response
     * @return
     * @throws: 
     */
    Map<String, Object> getResult(Map<String, Object> response);
    
    /**
     * @Title: getFeedback
     * @Description: 获取feedback
     * @return: R
     * @param response
     * @param cSvcBusiInfo
     * @return
     * @throws: 
     */
    R getFeedback(Map<String, Object> response, CSvcBusiInfo cSvcBusiInfo);
    
    R parseFeedback(String feedbackStr);
    
    /**
     * @Title: isHaveData
     * @Description: 此请求是否有返回数据
     * @return: boolean
     * @return
     * @throws: 
     */
    boolean isHaveData();
    
    /**
     * @Title: getServiceName
     * @Description: 获取服务实现类名字
     * @return: String
     * @return
     * @throws: 
     */
    String getServiceName();
    
    /**
     * @Title: isNeedHandle
     * @Description: 是否需要调用handle方法
     * @return: boolean
     * @return
     * @throws: 
     */
    boolean isNeedHandle();
    
    /**
     * @Title: callProxyHandleMethod
     * @Description: 调用云控制台处理数据方法的代理方法
     * @return: Status
     * @param cSvcBusiInfo
     * @param result
     * @param feedback
     * @param request
     * @return
     * @throws: 
     */
    Status callProxyHandleMethod(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> result, R feedback, 
    		Map<String, Object> request);
    
    /**
     * @Title: callHandleMethod
     * @Description: 调用云控制台处理数据方法
     * @return: Status
     * @param cSvcBusiInfo
     * @param result
     * @param feedback
     * @return
     * @throws: 
     */
    Status callHandleMethod(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> result, R feedback);
    
    /**
     * @Title: checkStatus
     * @Description: 验证handle方法返回值
     * @return: void
     * @param status
     * @param cSvcBusiInfo
     * @param request
     * @throws: 
     */
    void checkStatus(Status status, CSvcBusiInfo cSvcBusiInfo, Map<String, Object> request);
    
    /**
     * @Title: getReturn
     * @Description: 获取返回值
     * @return: T
     * @param code
     * @param message
     * @param feedback
     * @return
     * @throws: 
     */
    T getReturn(Integer code, String message, R feedback);

}
