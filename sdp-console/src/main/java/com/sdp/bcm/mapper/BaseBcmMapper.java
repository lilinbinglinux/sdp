/**
 * 
 */
package com.sdp.bcm.mapper;

import java.util.List;
import java.util.Map;

import com.sdp.bcm.entity.BaseBcmEntiry;

/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: BaseBcmMapper.java
* @Description: 提供基于泛型的基础操作Mapper
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年9月20日 下午3:36:39 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月2日     llb      v1.0.0               修改原因
*/
public interface BaseBcmMapper<T extends BaseBcmEntiry> {
    
	T findById(T entity);
	
	List<T> findAll(T entity);
	
	void saveSingle(T entity);
	
	void update(T entity);
	
	void delete(T entity);
	
	Integer maxSort();
	
	Integer count(Map<String,Object> params);
}
