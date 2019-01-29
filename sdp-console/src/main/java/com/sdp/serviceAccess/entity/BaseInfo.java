/**
 * 
 */
package com.sdp.serviceAccess.entity;

import java.util.Date;

import javax.persistence.Column;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: BaseInfo.java
* @Description: 基础entity类的父类 提供基础的信息 字段 属性等
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年8月2日 下午2:35:46 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月2日     renpengyuan      v1.0.0               修改原因
*/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseInfo {
	    /**
	     * 租户id
	     */
	    public String tenantId;

	    /**
	     * 创建时间
	     */
	    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
	    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	    public Date createDate;

	    /**
	     * 创建人
	     */
	    private String createBy;

	    /**
	     * 更新时间
	     */
	    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
	    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	    public Date updateDate;

	    /**
	     * 更新人
	     */
	    private String updateBy;

	    public String getTenantId() {
	        return tenantId;
	    }

	    public void setTenantId(String tenantId) {
	        this.tenantId = tenantId;
	    }

	    public Date getCreateDate() {
	        return createDate;
	    }

	    public void setCreateDate(Date createDate) {
	        this.createDate = createDate;
	    }

	    public Date getUpdateDate() {
	        return updateDate;
	    }

	    public void setUpdateDate(Date updateDate) {
	        this.updateDate = updateDate;
	    }

	    public String getCreateBy() {
	        return createBy;
	    }

	    public void setCreateBy(String createBy) {
	        this.createBy = createBy;
	    }

	    public String getUpdateBy() {
	        return updateBy;
	    }

	    public void setUpdateBy(String updateBy) {
	        this.updateBy = updateBy;
	    }
}
