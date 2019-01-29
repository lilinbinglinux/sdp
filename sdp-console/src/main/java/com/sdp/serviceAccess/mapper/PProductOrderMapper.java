/**
 * 
 */
package com.sdp.serviceAccess.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sdp.serviceAccess.entity.PProductOrder;

/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: PProductOrderMapper.java
* @Description: 订单Mapper
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年8月2日 下午3:43:32 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月2日     renpengyuan      v1.0.0               修改原因
*/
@Repository
public interface PProductOrderMapper extends BaseMapper<PProductOrder>{

	List<PProductOrder> findAllByCondition(PProductOrder productOrder);

	List<PProductOrder> findAllByLikeCondition(PProductOrder proOrder);

	Long findTotalCount(PProductOrder productOrder);

	List<PProductOrder> findPageAllByCondition(Map<Object,Object> map);

	List<PProductOrder> findFinishTaskOrder(Map<Object,Object> map);

	Long findFinishTaskTotalCount(PProductOrder proOrder);
	
	Long findTotalCountAuth(Map<Object,Object> map);
	
	List<PProductOrder> findPageAllByConditionWithAuth(Map<Object,Object> map);

}
