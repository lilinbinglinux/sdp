
/**
 * 
 */
package com.sdp.serviceAccess.entity;

import java.util.List;
import java.util.Map;

/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: PProductCase.java
* @Description: 该类的功能描述
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年8月2日 下午3:25:00 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月2日     renpengyuan      v1.0.0               修改原因
* 2018年8月8日     renpengyuan      v1.0.1             增加实例属性映射
* 2018年10月12日   renpengyuan      v1.0.2           增加是否展示实例属性
* 2018年10月14日   renpengyuan      v1.0.3	       增加实例依赖关系属性及其映射
* 2018年10月15日   renpengyuan      v1.0.4           增加实例依赖映射
*/
public class PProductCase extends BaseInfo{
     
	private String caseId;
	
	private String targetCaseId;
	
	public String getTargetCaseId() {
		return targetCaseId;
	}

	public void setTargetCaseId(String targetCaseId) {
		this.targetCaseId = targetCaseId;
	}

	private String orderId;
	
	private String productId;
	
	private String productName;
	
	private String caseAttr;
	
	private String receipt;
	
	private String caseStatus;
	
	private String isShow;  //是否显示   10是  20否
	
	private String caseRelyShipAttr;
	
	private String isChangeResource; //是否变更资源 10 是  20 否
	
	private String applyName;//申请名称--来自订单的申请名称
	
	private String operaType; //10 changeReource 20 addDependency
	
	private Object pNodesCounts;//不同状态节点统计
	
	public String getOperaType() {
		return operaType;
	}

	public void setOperaType(String operaType) {
		this.operaType = operaType;
	}

	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

	public String getIsChangeResource() {
		return isChangeResource;
	}

	public void setIsChangeResource(String isChangeResource) {
		this.isChangeResource = isChangeResource;
	}

	private List<ProductRelyOnItem> caseRelyShipAttrOrm;
	
	private Map<String,List<ProductFieldItem>> caseRelyAttrOrm;
	
	public Map<String, List<ProductFieldItem>> getCaseRelyAttrOrm() {
		return caseRelyAttrOrm;
	}

	public void setCaseRelyAttrOrm(Map<String, List<ProductFieldItem>> caseRelyAttrOrm) {
		this.caseRelyAttrOrm = caseRelyAttrOrm;
	}

	public String getCaseRelyShipAttr() {
		return caseRelyShipAttr;
	}

	public void setCaseRelyShipAttr(String caseRelyOnAttr) {
		this.caseRelyShipAttr = caseRelyOnAttr;
	}

	public List<ProductRelyOnItem> getCaseRelyShipAttrOrm() {
		return caseRelyShipAttrOrm;
	}

	public void setCaseRelyShipAttrOrm(List<ProductRelyOnItem> caseRelyAttrOrm) {
		this.caseRelyShipAttrOrm = caseRelyAttrOrm;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String showFlag) {
		this.isShow = showFlag;
	}

	private List<ProductFieldItem> caseAttrOrm;

	public List<ProductFieldItem> getCaseAttrOrm() {
		return caseAttrOrm;
	}

	public void setCaseAttrOrm(List<ProductFieldItem> caseAttrOrm) {
		this.caseAttrOrm = caseAttrOrm;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCaseAttr() {
		return caseAttr;
	}

	public void setCaseAttr(String caseAttr) {
		this.caseAttr = caseAttr;
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	public Object getpNodesCounts() {
		return pNodesCounts;
	}

	public void setpNodesCounts(Object pNodesCounts) {
		this.pNodesCounts = pNodesCounts;
	}
	
	
	
}
