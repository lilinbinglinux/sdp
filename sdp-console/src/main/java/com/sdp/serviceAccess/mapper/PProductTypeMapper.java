/**
 * 
 */
package com.sdp.serviceAccess.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sdp.serviceAccess.entity.PProductType;

/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: PProductTypeMapper.java
* @Description: 服务类型mapper
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年8月2日 下午3:40:47 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月2日     renpengyuan      v1.0.0               修改原因
*/
@Repository
public interface PProductTypeMapper extends BaseMapper<PProductType>{
    
	List<PProductType> findAllByParentId(PProductType productType);
	
	PProductType findByTypeCode(PProductType productType);
	
	@Deprecated 
	List<PProductType> findAllByTypeNameOrTypeCode(PProductType productType);
	
	List<PProductType> findAllByPage(Map<String,Object> map);
	
}
