/**
 * 
 */
package com.sdp.serviceAccess.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: PProductPackage.java
* @Description: 服务套餐类型
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年8月2日 下午2:57:28 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月2日     renpengyuan      v1.0.0               修改原因
* 2018年8月7日     renpengyuan      v1.0.1              增加订购方式/控制方式/按照资源方式控制的扩展属性
* 2018年10月12日   renpengyuan      v1.0.2              增加依赖服务属性配置/依赖服务属性配置映射
*/
public class PProductPackage extends BaseInfo{
    
	private String packageId;
	
	private String productId;
	
	private String packageName;
	
	private String packageStatus;
	
	private String packageIntrdo;
	
	private String detailedIntrdo;
	
	private String packageAttr;
	
	private String packageControlAttr;
	
	private String recFlag;
	
	private Integer sortId;
	
	private String orderType;  //10 审批  20 付费  30 自动开通
	
	private String controlType;   //10 按时间  20 按资源
	
	private List<ProductFieldItem> packageBasicAttrOrm;
	
	private List<ProductFieldItem> packageControlResAttrOrm;
	
	private String  relyOnBasicAttr;
	
	private Map<String,List<ProductFieldItem>> relyOnBasicAttrOrm;
	
	public String getRelyOnBasicAttr() {
		return relyOnBasicAttr;
	}

	public void setRelyOnBasicAttr(String relyOnBasicAttr) {
		this.relyOnBasicAttr = relyOnBasicAttr;
	}

	public Map<String,List<ProductFieldItem>> getRelyOnBasicAttrOrm() {
		return relyOnBasicAttrOrm;
	}

	public void setRelyOnBasicAttrOrm(Map<String,List<ProductFieldItem>> relyOnBasicAttrOrm) {
		this.relyOnBasicAttrOrm = relyOnBasicAttrOrm;
	}

	public String getPackageControlAttr() {
		return packageControlAttr;
	}

	public void setPackageControlAttr(String packageControlAttr) {
		this.packageControlAttr = packageControlAttr;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getControlType() {
		return controlType;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	public List<ProductFieldItem> getPackageBasicAttrOrm() {
		return packageBasicAttrOrm;
	}

	public void setPackageBasicAttrOrm(List<ProductFieldItem> packageBasicAttrOrm) {
		this.packageBasicAttrOrm = packageBasicAttrOrm;
	}

	public List<ProductFieldItem> getPackageControlResAttrOrm() {
		return packageControlResAttrOrm;
	}

	public void setPackageControlResAttrOrm(List<ProductFieldItem> packageControlResAttrOrm) {
		this.packageControlResAttrOrm = packageControlResAttrOrm;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageStatus() {
		return packageStatus;
	}

	public void setPackageStatus(String packageStatus) {
		this.packageStatus = packageStatus;
	}

	public String getPackageIntrdo() {
		return packageIntrdo;
	}

	public void setPackageIntrdo(String packageIntrdo) {
		this.packageIntrdo = packageIntrdo;
	}

	public String getDetailedIntrdo() {
		return detailedIntrdo;
	}

	public void setDetailedIntrdo(String detailedIntrdo) {
		this.detailedIntrdo = detailedIntrdo;
	}

	public String getPackageAttr() {
		return packageAttr;
	}

	public void setPackageAttr(String packageAttr) {
		this.packageAttr = packageAttr;
	}

	public String getRecFlag() {
		return recFlag;
	}

	public void setRecFlag(String recFlag) {
		this.recFlag = recFlag;
	}

	public Integer getSortId() {
		return sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}
//	public static void main(String[] args) {
//		PProductPackage productPackage = new PProductPackage();
//		productPackage.setProductId("test");
//		productPackage.setPackageName("测试套餐1");
//		productPackage.setControlType("10");
//		productPackage.setDetailedIntrdo("详细介绍");
//		productPackage.setOrderType("10");
//		productPackage.setPackageIntrdo("粗略介绍");
//		List<ProductFieldItem> items = new ArrayList<>();
//		String [] pros = {"CPU","Memeory","Storage"};
//		String [] unis = {"个","G","G"};
//		for(int i=0;i<3;i++){
//			ProductFieldItem item = new ProductFieldItem();
//			item.setProCode(pros[i]);
//			item.setProEnName(pros[i]);
//			item.setProLabel("10,20");
//			item.setProShowType("10");
//			item.setProUnit(unis[i]);
//			item.setProDesc("desc:"+pros[i]);
//			item.setProChargePrice("10");
//			items.add(item);
//		}
//		productPackage.setPackageControlResAttrOrm(items);
//		List<ProductFieldItem> itemsValue = new ArrayList<>();
//		for(int i=0;i<3;i++){
//			ProductFieldItem item = new ProductFieldItem();
//			item.setProCode(pros[i]);
//			item.setProEnName(pros[i]);
//			item.setProLabel("10,20");
//			item.setProShowType("10");
//			item.setProUnit(unis[i]);
//			item.setProDesc("desc:"+pros[i]);
//			item.setProChargePrice("10");
//			item.setProValue("1011");
//			itemsValue.add(item);
//		}
//		productPackage.setPackageBasicAttrOrm(itemsValue);
//System.out.println(JSON.toJSONString(productPackage));
//		
//	}
	
}
