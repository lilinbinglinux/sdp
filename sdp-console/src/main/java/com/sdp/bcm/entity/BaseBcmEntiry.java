package com.sdp.bcm.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: BaseEntiry.java
* @Description: 基础entity类的父类 提供基础的信息 字段 属性等
*
* @version: v1.0.0
* @author: llb
* @date: 2018年9月20日 下午2:35:46 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年9月20日     llb      v1.0.0               修改原因
*/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseBcmEntiry {
	 /**
     * 租户id
     */
    public String tenantId;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}
