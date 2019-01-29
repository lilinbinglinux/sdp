/**
 * 
 */
package com.sdp.serviceAccess.service;

import java.util.Map;

import com.sdp.common.entity.Status;
import com.sdp.serviceAccess.entity.PProductPackage;

/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: IPProductPackageService.java
* @Description: 服务套餐业务逻辑抽象接口
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年8月2日 下午3:56:11 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月2日     renpengyuan      v1.0.0               修改原因
*/
public interface IPProductPackageService {
    
	Status createPackage(PProductPackage productPackage);
	
	PProductPackage singlePackageInfo(String productPackageId);
	
	Status updatePackage(PProductPackage productPackage);

	Status deletePackage(PProductPackage productPackage);
	
	Map<String,Object> productPackages(String productId);
	
	PProductPackage singlePackageInfo(String productPackageId,String tenantId);
	
	Map<String,Object> productPackages(String productId,String tenantId);
	
	Boolean isRelyOnProduct(String productId,String tenantId);
	
}
