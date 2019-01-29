/**
 * 
 */
package com.sdp.serviceAccess.entity;

import com.alibaba.fastjson.JSON;

/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: PProductType.java
* @Description: 服务类型表基本entity类
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年8月2日 下午2:29:33 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月2日     renpengyuan      v1.0.0               修改原因
* 2018年8月3日     renpengyuan      v1.0.1             增加TypeCode编码(用户自定义),增加parentName
*/
public class PProductType extends BaseInfo{
    
	private String productTypeId;
	
	private String productTypeName;
	
	private String parentId;
	
	private String typePath;
	
	private String typeStatus;
	
	private Integer sortId;
	
	private String productTypeCode;
	
	private String parentName;
	
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getTypePath() {
		return typePath;
	}

	public void setTypePath(String typePath) {
		this.typePath = typePath;
	}

	public String getTypeStatus() {
		return typeStatus;
	}

	public void setTypeStatus(String typeStatus) {
		this.typeStatus = typeStatus;
	}

	public Integer getSortId() {
		return sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	public String getProductTypeCode() {
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}
	
//public static void main(String[] args) {
//	PProductType type= new PProductType();
//	type.setProductTypeName("容器云");
//	type.setProductTypeCode("BCMCODE");
//	type.setTenantId("tenant_system");
//	type.setTypeStatus("10");
//	System.out.println(JSON.toJSONString(type));
//}	
	
}
