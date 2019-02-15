package com.sdp.mall.entity;
//package com.sdp.mall.entity;
//
//import java.util.Date;
//
//import javax.persistence.Entity;
//
//import com.sdp.common.entity.BaseInfo;
//
///**
// * 
//* @Description: 计费模型实体
//  @ClassName: CChargeAttrValueInfo
//* @author zy
//* @date 2018年5月8日
//* @company:www.sdp.com.cn
// */
//@Entity
//public class CChargeAttrValueInfo extends BaseInfo{
//	/**
//     * 记录的唯一标识
//     */
//	private Long valueId;
//    
//    /**
//     * 服务提供者信息表主键
//     */
//    private Long providerId;
//    
//    /**
//     * 属性信息表主键
//     */
//    private Long attrId;
//    
//    /**
//     * 计费类型，参见数据字典 Dictionary.ChargeType.type
//     */
//    private Integer chargeType;
//    
//    /**
//     * 计费单价
//     */
//    private Integer chargePrice;
//    
//    /**
//     * 属性单位
//     */
//    private String metadataUnit;
//    
//    /**
//     * 计费时间单位，参见数据字典 Dictionary.ChargeTimeType.type
//     */
//    private Integer chargeTimeType;
//    
//    /**
//     * 时间值
//     */
//    private Integer timeCount;
//    
//    /**
//     * 属性值
//     */
//    private String valueObject;
//
//	public Long getValueId() {
//		return valueId;
//	}
//
//	public void setValueId(Long valueId) {
//		this.valueId = valueId;
//	}
//
//	public Long getProviderId() {
//		return providerId;
//	}
//
//	public void setProviderId(Long providerId) {
//		this.providerId = providerId;
//	}
//
//	public Long getAttrId() {
//		return attrId;
//	}
//
//	public void setAttrId(Long attrId) {
//		this.attrId = attrId;
//	}
//
//	public Integer getChargeType() {
//		return chargeType;
//	}
//
//	public void setChargeType(Integer chargeType) {
//		this.chargeType = chargeType;
//	}
//
//	public Integer getChargePrice() {
//		return chargePrice;
//	}
//
//	public void setChargePrice(Integer chargePrice) {
//		this.chargePrice = chargePrice;
//	}
//
//	public String getMetadataUnit() {
//		return metadataUnit;
//	}
//
//	public void setMetadataUnit(String metadataUnit) {
//		this.metadataUnit = metadataUnit;
//	}
//
//	public Integer getChargeTimeType() {
//		return chargeTimeType;
//	}
//
//	public void setChargeTimeType(Integer chargeTimeType) {
//		this.chargeTimeType = chargeTimeType;
//	}
//	
//	public Integer getTimeCount() {
//		return timeCount;
//	}
//
//	public void setTimeCount(Integer timeCount) {
//		this.timeCount = timeCount;
//	}
//
//	public String getValueObject() {
//		return valueObject;
//	}
//	
//	public void setValueObject(String valueObject) {
//		this.valueObject = valueObject;
//	}
//
//	public CChargeAttrValueInfo(Long valueId, Long providerId, Long attrId, Integer chargeType, Integer chargePrice,
//			String metadataUnit, Integer chargeTimeType,
//			String tenantId,Date createDate,String createBy) {
//		super();
//		this.valueId = valueId;
//		this.providerId = providerId;
//		this.attrId = attrId;
//		this.chargeType = chargeType;
//		this.chargePrice = chargePrice;
//		this.metadataUnit = metadataUnit;
//		this.chargeTimeType = chargeTimeType;
//		this.tenantId = tenantId;
//		this.createDate = createDate;
//		this.createBy = createBy;
//	}
//	
//
//	public CChargeAttrValueInfo(Long providerId, Long attrId, Integer chargeType, Integer chargePrice,
//			String metadataUnit, Integer chargeTimeType) {
//		super();
//		this.providerId = providerId;
//		this.attrId = attrId;
//		this.chargeType = chargeType;
//		this.chargePrice = chargePrice;
//		this.metadataUnit = metadataUnit;
//		this.chargeTimeType = chargeTimeType;
//	}
//
//	public CChargeAttrValueInfo() {
//		super();
//	}
//	
//	
//
//	
//	
//	
//    
//    
//    
//    
//    
//
//}
