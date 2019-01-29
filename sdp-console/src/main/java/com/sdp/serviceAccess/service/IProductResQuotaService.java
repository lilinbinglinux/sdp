package com.sdp.serviceAccess.service;

import java.util.List;
import java.util.Map;

import com.sdp.common.page.Pagination;

/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: IProductResQuotaService.java
* @Description: 配额管理业务逻辑抽象接口层
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年11月6日 下午4:01:11 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年11月6日    renpengyuan      v1.0.0         
*/
public interface IProductResQuotaService {
	
	String USED_PRO_KEY="used";
	
	String QUOTA_PRODUCT_KEY="bcm";
    
	Pagination getTenants(Pagination page);
	
	
	Pagination findQuotaByTenant(String tenantId,Pagination page);
	
}
