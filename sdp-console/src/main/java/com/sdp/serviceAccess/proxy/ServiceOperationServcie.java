/**  
 * Copyright ©1997-2018 BONC Corporation, All Rights Reserved.
 * @Title: SvcOperationServcie.java
 * @Prject: bconsole
 * @Package: com.bonc.serviceAccess.proxy
 * @Description: TODO
 * @Company: BONC
 * @author: LiJinfeng  
 * @date: 2018年1月8日 下午5:05:46
 * @version: V1.0  
 */

package com.sdp.serviceAccess.proxy;

import java.util.Map;

import com.sdp.common.entity.Status;
import com.sdp.serviceAccess.proxy.model.CSvcBusiInfo;

/**
 * @ClassName: SvcOperationServcie
 * @Description: TODO
 * @author: LiJinfeng
 * @date: 2018年1月8日 下午5:05:46
 */
public interface ServiceOperationServcie {
	
	/**
	 * @Title: createService
	 * @Description: 创建服务接口
	 * @return: Status
	 * @param cSvcBusiInfo
	 * @param params
	 * @return
	 * @throws: 
	 */
	Status createService(CSvcBusiInfo cSvcBusiInfo,Map<String, Object> params);
	
	/**
	 * @Title: startService
	 * @Description: 启用服务接口
	 * @return: Status
	 * @param cSvcBusiInfo
	 * @return
	 * @throws: 
	 */
	Status startService(CSvcBusiInfo cSvcBusiInfo);
	
	/**
	 * @Title: stopService
	 * @Description: 停止服务接口
	 * @return: Status
	 * @param cSvcBusiInfo
	 * @return
	 * @throws: 
	 */
	Status stopService(CSvcBusiInfo cSvcBusiInfo);
	
	/**
	 * @Title: deleteService
	 * @Description: 删除服务接口
	 * @return: Status
	 * @param cSvcBusiInfo
	 * @return
	 * @throws: 
	 */
	Status deleteService(CSvcBusiInfo cSvcBusiInfo);
	
	/**
	 * @Title: getInstanceStatusInfos
	 * @Description: 服务实例状态查询接口
	 * @return: Map<String,Object>
	 * @param cSvcBusiInfo
	 * @param params
	 * @return
	 * @throws: 
	 */
	Map<String,Object> getInstanceStatusInfos(CSvcBusiInfo cSvcBusiInfo, Map<String,Object> params);
	
	/**
	 * @Title: getResourceUsage
	 * @Description: 服务实例状态查询接口
	 * @return: Map<String,Object>
	 * @param cSvcBusiInfo
	 * @param params
	 * @return
	 * @throws: 
	 */
	Map<String,Object> getResourceUsage(CSvcBusiInfo cSvcBusiInfo, Map<String,Object> params);
	
	
	/**
	 * @Title: changeResource
	 * @Description: 修改服务资源
	 * @return: Status
	 * @param cSvcBusiInfo
	 * @param params
	 * @return
	 * @throws: 
	 */
	Status changeResource(CSvcBusiInfo cSvcBusiInfo,Map<String, Object> params);
	
	
	/**
	 * @Title: addDependency
	 * @Description: 后期添加依赖
	 * @return: Status
	 * @param cSvcBusiInfo
	 * @param params
	 * @return
	 * @throws: 
	 */
	Status addDependency(CSvcBusiInfo cSvcBusiInfo,Map<String, Object> params);

}
