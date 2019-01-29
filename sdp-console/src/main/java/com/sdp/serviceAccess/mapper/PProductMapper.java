/**
 * 
 */
package com.sdp.serviceAccess.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sdp.serviceAccess.entity.PProduct;
import com.sdp.serviceAccess.entity.PProductType;

/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: PProductMapper.java
* @Description: 服务注册Mapper
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年8月2日 下午3:42:14 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月2日     renpengyuan      v1.0.0               修改原因
*/
@Repository
public interface PProductMapper extends BaseMapper<PProduct>{
	
	List<PProduct> findAllByCondition(PProduct product);
	
	List<PProduct> findAllByConditionAndPage(Map<String,Object> map);
	
	List<Map<String,Object>> findProductOrderCountByProductType(Map<String,Object> params);
	
	void modifyCasePro(Map<String,String> params);
	
	List<PProduct> findAllByCase(Map<String,Object> map);
}
