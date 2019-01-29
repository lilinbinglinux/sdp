/**
 * 
 */
package com.sdp.serviceAccess.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sdp.serviceAccess.entity.PContinueOrder;

/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: PContinueOrderMapper.java
* @Description: 续订mapper
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年8月2日 下午3:44:23 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月2日     renpengyuan      v1.0.0               修改原因
*/
@Repository
public interface PContinueOrderMapper extends BaseMapper<PContinueOrder>{
	
	List<PContinueOrder> findByCondition(PContinueOrder continueOrder);

}
