/**
 * 
 */
package com.sdp.serviceAccess.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

/**   
 * Copyright: Copyright (c) 2018 BONC
 * 
 * @ClassName: PProductOrder.java
 * @Description: 订单类
 *
 * @version: v1.0.0
 * @author: renpengyuan
 * @date: 2018年8月2日 下午3:03:58 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年8月2日     renpengyuan      v1.0.0               修改原因
 * 2018年8月8日     renpengyuan      v1.0.1           扩展  付费类型 / 修改订单状态(增加 不同状态码) / 价格 属性  /
 * 2018年10月12日   renpengyuan      v1.0.2           增加依赖服务属性/ 依赖服务属性映射字段 
 */
public class PProductOrder extends BaseInfo {

	private String orderId;

	private String productId;
	
	private String packageId;

	private String productName;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date expireTime;

	private Integer frequency;
	
	/**
	 * xml格式属性
	 */
	private String orderAttr;
	
	private String orderControlAttr;

	private int orderStatus;
	
	private BigDecimal price;
	
	/**
     * bpm流程标识
     */
	private String bpmModelConfig;
	
	/**
     * bpm实例唯一标识
     */
	private String bpmModelNo;
	
	/**
     * 当前订单监控信息
     */
    private String monitorProcessUrl;
	
	/**
	 * 审批日期
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date bpmSignDate;

	/**
	 * 订购方式
	 */
	private String pwaysId;
	/**
	 * 其他属性
	 */
	private List<ProductFieldItem> orderBasicAttrOrm;
	
	private String orderRelyOnBasicAttr;
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date queryStartTime;
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date queryEndTime;
	
	private String applyName;//申请名称--来自属性中的applyName
	
	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	
	public Date getQueryStartTime() {
		return queryStartTime;
	}

	public void setQueryStartTime(Date queryStartTime) {
		this.queryStartTime = queryStartTime;
	}

	public Date getQueryEndTime() {
		return queryEndTime;
	}

	public void setQueryEndTime(Date queryEndTime) {
		this.queryEndTime = queryEndTime;
	}

	private Map<String,List<ProductFieldItem>> orderRelyOnBasicAttrOrm;
	
	public String getOrderRelyOnBasicAttr() {
		return orderRelyOnBasicAttr;
	}

	public void setOrderRelyOnBasicAttr(String orderRelyOnBasicAttr) {
		this.orderRelyOnBasicAttr = orderRelyOnBasicAttr;
	}

	public Map<String,List<ProductFieldItem>> getOrderRelyOnBasicAttrOrm() {
		return orderRelyOnBasicAttrOrm;
	}

	public void setOrderRelyOnBasicAttrOrm(Map<String,List<ProductFieldItem>> orderRelyOnBasicAttrOrm) {
		this.orderRelyOnBasicAttrOrm = orderRelyOnBasicAttrOrm;
	}

	/**
	 * 资源属性
	 */
	private List<ProductFieldItem> orderControlResAttrOrm;
	
	/**
	 * 套餐实体
	 */
	private PProductPackage proPackage;
	
	/**
	 * 服务实体
	 */
	private PProduct pProduct;
	
	/**
	 * 订购方式实体
	 */
	private POrderWays pOrderWays;
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getPwaysId() {
		return pwaysId;
	}

	public void setPwaysId(String pwaysId) {
		this.pwaysId = pwaysId;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderAttr() {
		return orderAttr;
	}

	public void setOrderAttr(String orderAttr) {
		this.orderAttr = orderAttr;
	}

	public List<ProductFieldItem> getOrderBasicAttrOrm() {
		return orderBasicAttrOrm;
	}

	public void setOrderBasicAttrOrm(List<ProductFieldItem> orderBasicAttrOrm) {
		this.orderBasicAttrOrm = orderBasicAttrOrm;
	}

	public List<ProductFieldItem> getOrderControlResAttrOrm() {
		return orderControlResAttrOrm;
	}

	public void setOrderControlResAttrOrm(List<ProductFieldItem> orderControlResAttrOrm) {
		this.orderControlResAttrOrm = orderControlResAttrOrm;
	}

	public String getOrderControlAttr() {
		return orderControlAttr;
	}

	public void setOrderControlAttr(String orderControlAttr) {
		this.orderControlAttr = orderControlAttr;
	}

	public String getBpmModelNo() {
		return bpmModelNo;
	}

	public void setBpmModelNo(String bpmModelNo) {
		this.bpmModelNo = bpmModelNo;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public PProductPackage getProPackage() {
		return proPackage;
	}

	public void setProPackage(PProductPackage proPackage) {
		this.proPackage = proPackage;
	}

	public PProduct getpProduct() {
		return pProduct;
	}

	public void setpProduct(PProduct pProduct) {
		this.pProduct = pProduct;
	}

	public Date getBpmSignDate() {
		return bpmSignDate;
	}

	public void setBpmSignDate(Date bpmSignDate) {
		this.bpmSignDate = bpmSignDate;
	}

	public POrderWays getpOrderWays() {
		return pOrderWays;
	}

	public void setpOrderWays(POrderWays pOrderWays) {
		this.pOrderWays = pOrderWays;
	}
	
	public String getBpmModelConfig() {
		return bpmModelConfig;
	}

	public void setBpmModelConfig(String bpmModelConfig) {
		this.bpmModelConfig = bpmModelConfig;
	}
	
	public String getMonitorProcessUrl() {
		return monitorProcessUrl;
	}

	public void setMonitorProcessUrl(String monitorProcessUrl) {
		this.monitorProcessUrl = monitorProcessUrl;
	}

	public PProductOrder(String orderId) {
		super();
		this.orderId = orderId;
	}

	public PProductOrder() {
		super();
	}
	
	
	
}
