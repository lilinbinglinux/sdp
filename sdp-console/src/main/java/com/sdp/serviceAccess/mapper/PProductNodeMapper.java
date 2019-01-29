/**
 * 
 */
package com.sdp.serviceAccess.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sdp.serviceAccess.entity.PProductNode;

/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: PProductNodeMapper.java
* @Description: 节点Mapper
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年8月2日 下午3:45:36 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月2日     renpengyuan      v1.0.0               修改原因
*/
@Repository
public interface PProductNodeMapper extends BaseMapper<PProductNode>{
    
	void save(List<PProductNode> nodes);
	
	List<PProductNode> findByCondition(PProductNode node); // caseId  tenantId
	
	void deleteByCondition(PProductNode node); //caseId tenantId
	
}
