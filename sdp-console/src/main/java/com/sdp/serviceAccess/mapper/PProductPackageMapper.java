/**
 * 
 */
package com.sdp.serviceAccess.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sdp.serviceAccess.entity.PProductPackage;

/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: PProductPackageMapper.java
* @Description: 套餐Mapper
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年8月2日 下午3:42:46 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月2日     renpengyuan      v1.0.0               修改原因
*/
@Repository
public interface PProductPackageMapper extends BaseMapper<PProductPackage>{
     List<PProductPackage> findAllByCondition(PProductPackage productPackage);
}
