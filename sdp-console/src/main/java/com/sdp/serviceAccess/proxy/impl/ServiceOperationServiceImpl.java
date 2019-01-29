/**  
 * Copyright ©1997-2018 BONC Corporation, All Rights Reserved.
 * @Title: ServiceOperationServiceImpl.java
 * @Prject: bconsole
 * @Package: com.bonc.serviceAccess.proxy
 * @Description: TODO
 * @Company: BONC
 * @author: LiJinfeng  
 * @date: 2018年1月8日 下午5:07:17
 * @version: V1.0  
 */

package com.sdp.serviceAccess.proxy.impl;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.sdp.common.entity.Status;
import com.sdp.frame.util.SpringUtils;
import com.sdp.serviceAccess.proxy.OperationComponent;
import com.sdp.serviceAccess.proxy.ServiceOperationServcie;
import com.sdp.serviceAccess.proxy.model.CSvcBusiInfo;

/**
 * @ClassName: ServiceOperationServiceImpl
 * @Description: TODO
 * @author: LiJinfeng
 * @date: 2018年1月8日 下午5:07:17
 */
@Service
@Transactional
public class ServiceOperationServiceImpl implements ServiceOperationServcie{
	
	//存放服务提供方名字与服务实现类前缀的映射
	private static final Map<String, Object> mapping = new HashMap<>();
	
	static {
		//初始化mapping
	}
	
	@SuppressWarnings("rawtypes")
	private OperationComponent getOperationComponent(CSvcBusiInfo cSvcBusiInfo,String action){
		String operationComponentType = MapUtils.getString(mapping, cSvcBusiInfo.getServiceProvider());
		String operationComponentName = null;
		if(null == operationComponentType) {
			operationComponentName = "default" + action + "OperationComponent";
		}
		else {
			operationComponentName = operationComponentType + action + "OperationComponent";
		}
		return (OperationComponent)SpringUtils.getBean(operationComponentName);
	}
	
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Status createService(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> params) {
		OperationComponent operationComponent = this.getOperationComponent(cSvcBusiInfo,"Create");
		return (Status) operationComponent.operateService(cSvcBusiInfo, params);	
	}
	
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Status startService(CSvcBusiInfo cSvcBusiInfo) {
		OperationComponent operationComponent = this.getOperationComponent(cSvcBusiInfo, "Start");
		return (Status) operationComponent.operateService(cSvcBusiInfo, null);
	}

	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Status stopService(CSvcBusiInfo cSvcBusiInfo) {
		OperationComponent operationComponent = this.getOperationComponent(cSvcBusiInfo, "Stop");
		return (Status) operationComponent.operateService(cSvcBusiInfo, null);
	}

	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Status deleteService(CSvcBusiInfo cSvcBusiInfo) {
		OperationComponent operationComponent = this.getOperationComponent(cSvcBusiInfo, "Delete");
		return (Status) operationComponent.operateService(cSvcBusiInfo, null);
	}


	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, Object> getInstanceStatusInfos(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> params) {
		OperationComponent operationComponent = this.getOperationComponent(cSvcBusiInfo, "Status");
		return (Map<String, Object>)operationComponent.operateService(cSvcBusiInfo, params);
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, Object> getResourceUsage(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> params) {
		OperationComponent operationComponent = this.getOperationComponent(cSvcBusiInfo, "ResourceUsage");
		return (Map<String, Object>)operationComponent.operateService(cSvcBusiInfo, params);
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Status changeResource(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> params) {
		OperationComponent operationComponent = this.getOperationComponent(cSvcBusiInfo,"ChangeResource");
		return (Status) operationComponent.operateService(cSvcBusiInfo, params);	
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Status addDependency(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> params) {
		OperationComponent operationComponent = this.getOperationComponent(cSvcBusiInfo,"AddDependency");
		return (Status) operationComponent.operateService(cSvcBusiInfo, params);	
	}
	
	
}
