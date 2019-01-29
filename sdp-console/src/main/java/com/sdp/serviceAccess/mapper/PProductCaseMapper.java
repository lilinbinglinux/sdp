/**
 * 
 */
package com.sdp.serviceAccess.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sdp.serviceAccess.entity.PProductCase;

/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: PProductCaseMapper.java
* @Description: 实例Mapper
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年8月2日 下午3:45:04 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月2日     renpengyuan      v1.0.0               修改原因
*/
@Repository
public interface PProductCaseMapper extends BaseMapper<PProductCase>{
	
	List<PProductCase> findByCondition(PProductCase productCase);
	List<PProductCase> findAllByConditionAndPage(Map<String,Object> map);
	Integer countWithAuth(Map<Object, Object> params);
	List<PProductCase> findAllByConditionAndPageWithAuth(Map<Object, Object> params);
	

}
