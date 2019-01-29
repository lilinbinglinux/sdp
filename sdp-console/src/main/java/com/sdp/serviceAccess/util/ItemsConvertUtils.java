/**
 * 
 */
package com.sdp.serviceAccess.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.StringUtils;
import com.sdp.frame.web.HttpClientUtil;
import com.sdp.servflow.pubandorder.protocol.HttpUtil;
import com.sdp.serviceAccess.entity.ProductFieldItem;
import com.sdp.serviceAccess.entity.ProductRelyOnItem;

/**   
 * Copyright: Copyright (c) 2018 BONC
 * 
 * @ClassName: ItemsConvertUtils.java
 * @Description: 属性转换工具类
 *
 * @version: v1.0.0
 * @author: renpengyuan
 * @date: 2018年8月8日 上午10:26:11 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年8月8日     renpengyuan      v1.0.0               修改原因
 */
public class ItemsConvertUtils {
	public  static List<Map<String,Map<String,String>>> convertToFieldPro(Map<String,List<ProductFieldItem>> allItems){
		List<Map<String,Map<String,String>>> result = new ArrayList<>();
		Map<String,String> valuePro;
		Map<String,Map<String,String>> singlePro;
		Map<String,String> alPro;
		for(String key:allItems.keySet()) {
			List<ProductFieldItem> items = allItems.get(key);
			if(items!=null&&items.size()>=1) {
				String relyOnCode = null;
				boolean isRelyOn= false;
				if(!key.equals("rely_on_code_default")) {
					relyOnCode= key;
					isRelyOn=true;
				}
				for(ProductFieldItem item:items) {
					alPro = new HashMap<String,String>();
					valuePro= new HashMap<String,String>();
					singlePro =new HashMap<>();
					if(isRelyOn) {
						singlePro.put("At", alPro);
						alPro.put("relyOnCode", relyOnCode);
					}
					singlePro.put("Vl", valuePro);
					result.add(singlePro);
					valuePro.put("proName", item.getProName());
					valuePro.put("proCode", item.getProCode());
					valuePro.put("proEnName", item.getProEnName());
					valuePro.put("proDataType", item.getProDataType());
					valuePro.put("proShowType", item.getProShowType());
					valuePro.put("proDesc", item.getProDesc());
					valuePro.put("proVerfyRule", item.getProVerfyRule());
					valuePro.put("proVerfyTips", item.getProVerfyTips());
					valuePro.put("proLabel", item.getProLabel());
					valuePro.put("proChargePrice", item.getProChargePrice());
					valuePro.put("proChargeTimeType", item.getProChargeTimeType());
					valuePro.put("proChargeType", item.getProChargeType());
					valuePro.put("proValue", item.getProValue());
					valuePro.put("proInitValue", item.getProInitValue());
					valuePro.put("proUnit", item.getProUnit());
					valuePro.put("proIsFixed", item.getProIsFixed());
					valuePro.put("proIsChecked", item.getProIsChecked());
					valuePro.put("proOrder", item.getProOrder());
				}
				
			}
		}
		return result;
	}

	public static List<ProductFieldItem> convertToFieldBean(List<Map<String,String>> items){
		List<ProductFieldItem> result = new ArrayList<>();
		ProductFieldItem fieldItem;
		for(Map<String,String> item:items){
			fieldItem = new ProductFieldItem();
			fieldItem.setProName(StringUtils.isNullOrEmpty(item.get("proName"))||"null".equals(item.get("proName"))?null:item.get("proName"));
			fieldItem.setProCode(StringUtils.isNullOrEmpty(item.get("proCode"))||"null".equals(item.get("proCode"))?null:item.get("proCode"));
			fieldItem.setProEnName(StringUtils.isNullOrEmpty(item.get("proEnName"))||"null".equals(item.get("proEnName"))?null:item.get("proEnName"));
			fieldItem.setProDataType(StringUtils.isNullOrEmpty(item.get("proDataType"))||"null".equals(item.get("proDataType"))?null:item.get("proDataType"));
			fieldItem.setProShowType(StringUtils.isNullOrEmpty(item.get("proShowType"))||"null".equals(item.get("proShowType"))?null:item.get("proShowType"));
			fieldItem.setProDesc(StringUtils.isNullOrEmpty(item.get("proDesc"))||"null".equals(item.get("proDesc"))?null:item.get("proDesc"));
			fieldItem.setProVerfyRule(StringUtils.isNullOrEmpty(item.get("proVerfyRule"))||"null".equals(item.get("proVerfyRule"))?null:item.get("proVerfyRule"));
			fieldItem.setProVerfyTips(StringUtils.isNullOrEmpty(item.get("proVerfyTips"))||"null".equals(item.get("proVerfyTips"))?null:item.get("proVerfyTips"));
			fieldItem.setProLabel(StringUtils.isNullOrEmpty(item.get("proLabel"))||"null".equals(item.get("proLabel"))?null:item.get("proLabel"));
			fieldItem.setProUnit(StringUtils.isNullOrEmpty(item.get("proUnit"))||"null".equals(item.get("proUnit"))?null:item.get("proUnit"));
			fieldItem.setProChargePrice(StringUtils.isNullOrEmpty(item.get("proChargePrice"))||"null".equals(item.get("proChargePrice"))?null:item.get("proChargePrice"));
			fieldItem.setProChargeTimeType(StringUtils.isNullOrEmpty(item.get("proChargeTimeType"))||"null".equals(item.get("proChargeTimeType"))?null:item.get("proChargeTimeType"));
			fieldItem.setProChargeType(StringUtils.isNullOrEmpty(item.get("proChargeType"))||"null".equals(item.get("proChargeType"))?null:item.get("proChargeType"));
			fieldItem.setProValue(StringUtils.isNullOrEmpty(item.get("proValue"))||"null".equals(item.get("proValue"))?null:item.get("proValue"));
			fieldItem.setProInitValue(StringUtils.isNullOrEmpty(item.get("proInitValue"))||"null".equals(item.get("proInitValue"))?null:item.get("proInitValue"));
			fieldItem.setProIsFixed(StringUtils.isNullOrEmpty(item.get("proIsFixed"))||"null".equals(item.get("proIsFixed"))?null:item.get("proIsFixed"));
			fieldItem.setProIsChecked(StringUtils.isNullOrEmpty(item.get("proIsChecked"))||"null".equals(item.get("proIsChecked"))?null:item.get("proIsChecked"));
			fieldItem.setProOrder(StringUtils.isNullOrEmpty(item.get("proOrder"))||"null".equals(item.get("proOrder"))?null:item.get("proOrder"));
			result.add(fieldItem);
		}
		return result;
	}
	
	public static Map<String,List<ProductFieldItem>> convertToFieldBeanRelyOn(List<Map<String,String>> items){
		Map<String,List<ProductFieldItem>> retu = new HashMap<>();
		List<ProductFieldItem> result =null;
		ProductFieldItem fieldItem;
		for(Map<String,String> item:items){
			String relyOnCode=item.get("relyOnCode");
			if(!StringUtils.isNullOrEmpty(relyOnCode)&&!"null".equals(relyOnCode)) {
				result= retu.get(relyOnCode);
				if(result==null||result.size()==0) {
					result = new ArrayList<ProductFieldItem>();
					retu.put(relyOnCode, result);
				}
				fieldItem = new ProductFieldItem();
				fieldItem.setProName(StringUtils.isNullOrEmpty(item.get("proName"))||"null".equals(item.get("proName"))?null:item.get("proName"));
				fieldItem.setProCode(StringUtils.isNullOrEmpty(item.get("proCode"))||"null".equals(item.get("proCode"))?null:item.get("proCode"));
				fieldItem.setProEnName(StringUtils.isNullOrEmpty(item.get("proEnName"))||"null".equals(item.get("proEnName"))?null:item.get("proEnName"));
				fieldItem.setProDataType(StringUtils.isNullOrEmpty(item.get("proDataType"))||"null".equals(item.get("proDataType"))?null:item.get("proDataType"));
				fieldItem.setProShowType(StringUtils.isNullOrEmpty(item.get("proShowType"))||"null".equals(item.get("proShowType"))?null:item.get("proShowType"));
				fieldItem.setProDesc(StringUtils.isNullOrEmpty(item.get("proDesc"))||"null".equals(item.get("proDesc"))?null:item.get("proDesc"));
				fieldItem.setProVerfyRule(StringUtils.isNullOrEmpty(item.get("proVerfyRule"))||"null".equals(item.get("proVerfyRule"))?null:item.get("proVerfyRule"));
				fieldItem.setProVerfyTips(StringUtils.isNullOrEmpty(item.get("proVerfyTips"))||"null".equals(item.get("proVerfyTips"))?null:item.get("proVerfyTips"));
				fieldItem.setProLabel(StringUtils.isNullOrEmpty(item.get("proLabel"))||"null".equals(item.get("proLabel"))?null:item.get("proLabel"));
				fieldItem.setProUnit(StringUtils.isNullOrEmpty(item.get("proUnit"))||"null".equals(item.get("proUnit"))?null:item.get("proUnit"));
				fieldItem.setProChargePrice(StringUtils.isNullOrEmpty(item.get("proChargePrice"))||"null".equals(item.get("proChargePrice"))?null:item.get("proChargePrice"));
				fieldItem.setProChargeTimeType(StringUtils.isNullOrEmpty(item.get("proChargeTimeType"))||"null".equals(item.get("proChargeTimeType"))?null:item.get("proChargeTimeType"));
				fieldItem.setProChargeType(StringUtils.isNullOrEmpty(item.get("proChargeType"))||"null".equals(item.get("proChargeType"))?null:item.get("proChargeType"));
				fieldItem.setProValue(StringUtils.isNullOrEmpty(item.get("proValue"))||"null".equals(item.get("proValue"))?null:item.get("proValue"));
				fieldItem.setProInitValue(StringUtils.isNullOrEmpty(item.get("proInitValue"))||"null".equals(item.get("proInitValue"))?null:item.get("proInitValue"));
				fieldItem.setProIsFixed(StringUtils.isNullOrEmpty(item.get("proIsFixed"))||"null".equals(item.get("proIsFixed"))?null:item.get("proIsFixed"));
				fieldItem.setProIsChecked(StringUtils.isNullOrEmpty(item.get("proIsChecked"))||"null".equals(item.get("proIsChecked"))?null:item.get("proIsChecked"));
				fieldItem.setProOrder(StringUtils.isNullOrEmpty(item.get("proOrder"))||"null".equals(item.get("proOrder"))?null:item.get("proOrder"));
				result.add(fieldItem);
			}
		}
		return retu;
	}
	
	public static List<Map<String,Map<String,String>>>  convertToFieldPro(List<ProductFieldItem> items){
		Map<String,List<ProductFieldItem>> retu = new HashMap<>();
		retu.put("rely_on_code_default", items);
		return convertToFieldPro(retu);
	}
	
	public static  List<Map<String,Map<String,String>>> convertToRelyOnPro(List<ProductRelyOnItem> items){
		List<Map<String,Map<String,String>>> result = new ArrayList<>();
		Map<String,String> valuePro;
		Map<String,Map<String,String>> singlePro;
		for(ProductRelyOnItem item:items){
			valuePro= new HashMap<String,String>();
			singlePro =new HashMap<>();
			valuePro.put("relyOnProductCode", item.getRelyOnProductCode());
			valuePro.put("isShowRelyOnPros", item.getIsShowRelyOnPros());
			valuePro.put("isConfRelyOnPros", item.getIsConfRelyOnPros());
			valuePro.put("relyOnOrder", item.getRelyOnOrder());
			valuePro.put("isAddiCasePros", item.getIsAddiCasePros());
			valuePro.put("relyOnCaseId", item.getRelyOnCaseId());
			singlePro.put("Vl", valuePro);
			result.add(singlePro);
		}
		return result;
	}

	public static List<ProductRelyOnItem> convertToRelyOnBean(List<Map<String,String>> items){
		List<ProductRelyOnItem> result = new ArrayList<>();
		ProductRelyOnItem fieldItem;
		for(Map<String,String> item:items){
			fieldItem = new ProductRelyOnItem();
			fieldItem.setRelyOnProductCode(StringUtils.isNullOrEmpty(item.get("relyOnProductCode"))||"null".equals(item.get("relyOnProductCode"))?null:item.get("relyOnProductCode"));
			fieldItem.setIsShowRelyOnPros(StringUtils.isNullOrEmpty(item.get("isShowRelyOnPros"))||"null".equals(item.get("isShowRelyOnPros"))?null:item.get("isShowRelyOnPros"));
			fieldItem.setIsConfRelyOnPros(StringUtils.isNullOrEmpty(item.get("isConfRelyOnPros"))||"null".equals(item.get("isConfRelyOnPros"))?null:item.get("isConfRelyOnPros"));
			fieldItem.setRelyOnOrder(StringUtils.isNullOrEmpty(item.get("relyOnOrder"))||"null".equals(item.get("relyOnOrder"))?null:item.get("relyOnOrder"));
			fieldItem.setIsAddiCasePros(StringUtils.isNullOrEmpty(item.get("isAddiCasePros"))||"null".equals(item.get("isAddiCasePros"))?null:item.get("isAddiCasePros"));
			fieldItem.setRelyOnCaseId(StringUtils.isNullOrEmpty(item.get("relyOnCaseId"))||"null".equals(item.get("relyOnCaseId"))?null:item.get("relyOnCaseId"));
			result.add(fieldItem);
		}
		return result;
	}
}
