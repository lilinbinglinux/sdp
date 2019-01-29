package com.sdp.serviceAccess.entity;

/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: ProductRelyOnItem.java
* @Description: 服务注册实体
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年10月10日 下午7:51:35 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年10月10日     renpengyuan      v1.0.0          修改原因
* 2018年10月14日     renpengyuan		v1.0.1       增加依赖服务实例属性
*/
public class ProductRelyOnItem {
	
	private String relyOnProductCode; //依赖的服务编码
	
	private String isShowRelyOnPros; //是否展示依赖服务的属性值 10 是  20否
	
	private String isConfRelyOnPros; //是否修改依赖服务的属性值  10是  20否
	
	private String relyOnOrder;  //依赖顺序排序，数字越小，则说明优先级越大。 例如 1 依赖 2和3 ， 2和3 存在依赖关系，则数据为 1、2、3 。如果 2和 3 不存在依赖关系，但是 2、3 和 4存在依赖关系，则是1、2/3、4 这种
	
	private String isAddiCasePros; //是否追加到当前实例属性，如果是，则将依赖的服务属性追加到当前申请或者修改的服务对应实例的属性里面；如果为否，则将实例进行分开进行持久化，不涉及到实例属性合并
	
	private String relyOnCaseId; //依赖服务实例id
    
	public String getRelyOnCaseId() {
		return relyOnCaseId;
	}

	public void setRelyOnCaseId(String relyOnCaseId) {
		this.relyOnCaseId = relyOnCaseId;
	}

	public String getRelyOnProductCode() {
		return relyOnProductCode;
	}

	public void setRelyOnProductCode(String relyOnProductCode) {
		this.relyOnProductCode = relyOnProductCode;
	}

	public String getIsShowRelyOnPros() {
		return isShowRelyOnPros;
	}

	public void setIsShowRelyOnPros(String isShowRelyOnPros) {
		this.isShowRelyOnPros = isShowRelyOnPros;
	}

	public String getIsConfRelyOnPros() {
		return isConfRelyOnPros;
	}

	public void setIsConfRelyOnPros(String isConfRelyOnPros) {
		this.isConfRelyOnPros = isConfRelyOnPros;
	}

	public String getRelyOnOrder() {
		return relyOnOrder;
	}

	public void setRelyOnOrder(String relyOnOrder) {
		this.relyOnOrder = relyOnOrder;
	}

	public String getIsAddiCasePros() {
		return isAddiCasePros;
	}

	public void setIsAddiCasePros(String isAddiCasePros) {
		this.isAddiCasePros = isAddiCasePros;
	}
	 
	
	
}
