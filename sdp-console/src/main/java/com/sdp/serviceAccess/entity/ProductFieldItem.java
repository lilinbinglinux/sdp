/**
 * 
 */
package com.sdp.serviceAccess.entity;

/**   
 * Copyright: Copyright (c) 2018 BONC
 * 
 * @ClassName: ProductFieldItem.java
 * @Description: 服务个性属性列表类
 *
 * @version: v1.0.0
 * @author: renpengyuan
 * @date: 2018年8月7日 下午4:02:47 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年8月7日     renpengyuan      v1.0.0               修改原因
 */
public class ProductFieldItem {

	private String proName;

	private String proCode;

	private String proEnName;

	private String proDataType;

	private String proShowType;

	private String proDesc;

	private String proVerfyRule;

	private String proVerfyTips;

	private String proValue;
	
	private String proLabel; //10 申请  20访问  30 资源
	
	private String proUnit;
	
	private String proChargePrice;
	
	private String proChargeTimeType; //10 年  20 月  30 日 
	
	private String proChargeType;  //10 按时间  20 按资源 30按时间+资源
	
	private String proInitValue; //服务初始属性value
	
	private String proIsFixed;  //是否固定值  10是  20否
	
	private String proIsChecked;  //是否显示  10是  20否
	
	private String proOrder;	//顺序
	
	public String getProOrder() {
		return proOrder;
	}

	public void setProOrder(String proOrder) {
		this.proOrder = proOrder;
	}

	public String getProIsChecked() {
		return proIsChecked;
	}

	public void setProIsChecked(String proIsChecked) {
		this.proIsChecked = proIsChecked;
	}
	
	public String getProIsFixed() {
		return proIsFixed;
	}

	public void setProIsFixed(String proIsFixed) {
		this.proIsFixed = proIsFixed;
	}

	public String getProInitValue() {
		return proInitValue;
	}

	public void setProInitValue(String proInitValue) {
		this.proInitValue = proInitValue;
	}

	public String getProChargePrice() {
		return proChargePrice;
	}

	public void setProChargePrice(String proChargePrice) {
		this.proChargePrice = proChargePrice;
	}

	public String getProChargeTimeType() {
		return proChargeTimeType;
	}

	public void setProChargeTimeType(String proChargeTimeType) {
		this.proChargeTimeType = proChargeTimeType;
	}

	public String getProChargeType() {
		return proChargeType;
	}

	public void setProChargeType(String proChargeType) {
		this.proChargeType = proChargeType;
	}

	public String getProUnit() {
		return proUnit;
	}

	public void setProUnit(String proUnit) {
		this.proUnit = proUnit;
	}

	public String getProLabel() {
		return proLabel;
	}

	public void setProLabel(String proLabel) {
		this.proLabel = proLabel;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProCode() {
		return proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	public String getProEnName() {
		return proEnName;
	}

	public void setProEnName(String proEnName) {
		this.proEnName = proEnName;
	}

	public String getProDataType() {
		return proDataType;
	}

	public void setProDataType(String proDataType) {
		this.proDataType = proDataType;
	}

	public String getProShowType() {
		return proShowType;
	}

	public void setProShowType(String proShowType) {
		this.proShowType = proShowType;
	}

	public String getProDesc() {
		return proDesc;
	}

	public void setProDesc(String proDesc) {
		this.proDesc = proDesc;
	}

	public String getProVerfyRule() {
		return proVerfyRule;
	}

	public void setProVerfyRule(String proVerfyRule) {
		this.proVerfyRule = proVerfyRule;
	}

	public String getProVerfyTips() {
		return proVerfyTips;
	}

	public void setProVerfyTips(String proverfyTips) {
		this.proVerfyTips = proverfyTips;
	}

	public String getProValue() {
		return proValue;
	}

	public void setProValue(String proValue) {
		this.proValue = proValue;
	}



}
