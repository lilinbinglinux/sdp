/**
 * 
 */
package com.sdp.serviceAccess.service;

import java.util.List;

import com.sdp.common.entity.Status;
import com.sdp.common.page.Pagination;
import com.sdp.serviceAccess.entity.PProduct;
import com.sdp.serviceAccess.entity.PProductType;

/**   
 * Copyright: Copyright (c) 2018 BONC
 * 
 * @ClassName: IPProductTypeService.java
 * @Description: 服务类型业务逻辑抽象接口
 *
 * @version: v1.0.0
 * @author: renpengyuan
 * @date: 2018年8月2日 下午3:54:54 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年8月2日     renpengyuan      v1.0.0               修改原因
 */
public interface IPProductTypeService {

	void categoryInfos(Pagination page,PProductType productType);

	Status createCategory(PProductType productType);

	Status updateCategory(PProductType productType);

	Status deleteCategory(PProductType productType);

	List<PProductType> allCates();

	Boolean verfyCateCode(String typeCode,String typeId);

	PProductType singleCate(String typeId);
	
	PProductType singleCateNoAuth(String typeId);

	List<PProductType> getAllPProductTypeByProduct(List<PProduct> proList);
	
	Boolean verfyCateName(String typeName,String typeId);

}
