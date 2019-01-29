/**
 * 
 */
package com.sdp.serviceAccess.service;

import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

import com.sdp.common.entity.Status;
import com.sdp.common.page.Pagination;
import com.sdp.serviceAccess.entity.PProduct;
import com.sdp.serviceAccess.entity.ProductFieldItem;

/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: IPProductService.java
* @Description: 服务注册业务逻辑抽象接口
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年8月2日 下午3:55:40 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月2日     renpengyuan      v1.0.0               修改原因
*/
public interface IPProductService {
    
	Pagination allProducts(Pagination page ,PProduct product,boolean isOrderCondition);
	
	Status createProduct(PProduct product,MultipartFile productIcon);
	
	PProduct singleProduct(PProduct product);
	
	Status modifyStatus(PProduct product);
	
	Status updateProItems(PProduct product);
	
	Boolean verfyProductCode(String productCode); 
	
	Status modifyProduct(PProduct product,MultipartFile productIcon);
	
	List<Map<String, Object>>  productInfosByCat();
	
	Status createProperties(PProduct product);
	/**
	 * 判断是否为依赖服务并且返回需要进行展示实例信息服务的依赖链
	 * */
	public Map<String,List<ProductFieldItem>> relyOnProductInfos(PProduct product);
	
	/**
	 * 找出当前服务依赖的属性信息，并且进行转换map
	 */
    public Map<String,Map<String,String>> relyOnInfos(PProduct product);

    /**
     * 查询有实例的服务
     * @param page
     * @param currentDataAuth
     * @return
     */
	List<PProduct> getProductByCase(PProduct product, String currentDataAuth);

	Integer allProductsCntWithAuth(PProduct product);

	Pagination allProductsWithAuth(Pagination page, PProduct product, boolean isOrderCondition);	
	
	/**
	 *查询当前服务下的所有资源属性值 
	 */
	Map<String,Map<String,String>> getResPros(String productId);
}
