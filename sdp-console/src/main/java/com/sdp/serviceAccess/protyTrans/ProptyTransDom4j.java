/**
 * 
 */
package com.sdp.serviceAccess.protyTrans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.StringUtils;

/**   
 * Copyright: Copyright (c) 2018 BONC
 * 
 * @ClassName: ProptyTransDom4j.java
 * @Description: link ProtyTransBase
 *
 * @version: v1.0.0
 * @author: renpengyuan
 * @date: 2018年7月31日 下午3:12:48 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年7月31日     renpengyuan      v1.0.0               修改原因
 */
public class ProptyTransDom4j implements ProptyTransBase{

	/* (non-Javadoc)
	 * @see com.sdp.serviceAccess.ProptyTransBase#transToXml(java.util.List)
	 */
	@Override
	public String transToXml(List<Map<String, Map<String,String>>> proties,String listRoot,String mapRoot) {
		if(proties==null||proties.size()==0){
			return null;
		}
		if(StringUtils.isNullOrEmpty(listRoot)||StringUtils.isNullOrEmpty(mapRoot)){
			throw new ProtyTransException("null listRoot or mapRoot Name");
		}
		StringBuilder strB= new StringBuilder();
		strB.append("<").append(listRoot).append(">");
		for(Map<String,Map<String,String>> singlePro:proties){
			Map<String,String> atPro = singlePro.get("At");
			Map<String,String> vlPro = singlePro.get("Vl");
			strB.append("<").append(mapRoot);
			boolean endFlag = true;
			if(atPro!=null&&atPro.size()>=0){
				for(String key:atPro.keySet()){
					strB.append(" ").append(key).append("=").append("\"").append(atPro.get(key)).append("\"");
				}
				if(vlPro==null||vlPro.size()==0){
					strB.append("/");
					endFlag = false;
				}
			}
			strB.append(">");
			if(vlPro!=null&&vlPro.size()!=0){
                for(String key:vlPro.keySet()){
                	strB.append("<").append(key).append(">").append(vlPro.get(key)).append("</").append(key).append(">");
                }
			}
			if(endFlag){
				strB.append("</").append(mapRoot).append(">");
			}
		}
		strB.append("</").append(listRoot).append(">");
		return strB.toString();
	}

	/* (non-Javadoc)
	 * @see com.sdp.serviceAccess.ProptyTransBase#transToBean(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> transToBean(String proXml) {
		if(StringUtils.isNullOrEmpty(proXml)){
			throw new ProtyTransException("proXml is null");
		}
		List<Map<String, String>> proBean = new ArrayList<Map<String, String>>();
		try{
			Document document = DocumentHelper.parseText(proXml); 
			Element pros = document.getRootElement();
			Iterator<Element> rootIter = pros.elementIterator();
			Map<String, String> basicPro =null;
			while (rootIter.hasNext()) {
				Element singlePro = rootIter.next();
				Iterator<Element> childIter = singlePro.elementIterator();
				basicPro = new HashMap<String, String>();
				List<Attribute> attributes = singlePro.attributes();
				for (int i = 0; i < attributes.size(); ++i) { // 添加节点属性
					basicPro.put(attributes.get(i).getName(), attributes.get(i).getValue());
				}
				while (childIter.hasNext()) { // 添加子节点
					Element attr = childIter.next();
					basicPro.put(attr.getName().trim(), attr.getText().trim());
				}
				proBean.add(basicPro);
			}

		}catch(Exception e){
			throw new ProtyTransException("Dom4j Trans to bean Exception",e);
		}
		return proBean;
	}
	//测试relyOn 属性 用例
//	public static void main(String[] args) {
//		ProptyTransDom4j dm = new ProptyTransDom4j();
//		String xml = "<fieldItems><fieldItem relyOnCode=\"bcm\"><proVerfyTips>null</proVerfyTips><proDesc>CPU</proDesc><proChargeTimeType>null</proChargeTimeType><proInitValue>null</proInitValue><proCode>CPU</proCode><proVerfyRule>null</proVerfyRule><proChargeType>null</proChargeType><proChargePrice>null</proChargePrice><proIsFixed>20</proIsFixed><proShowType>10</proShowType><proLabel>10,20,30</proLabel><proUnit>null</proUnit><proValue>2</proValue><proEnName>CPU</proEnName><proName>CPU</proName><proDataType>60</proDataType></fieldItem><fieldItem><proVerfyTips>null</proVerfyTips><proDesc>内存</proDesc><proChargeTimeType>null</proChargeTimeType><proInitValue>null</proInitValue><proCode>Memory</proCode><proVerfyRule>null</proVerfyRule><proChargeType>null</proChargeType><proChargePrice>null</proChargePrice><proIsFixed>20</proIsFixed><proShowType>10</proShowType><proLabel>10,20,30</proLabel><proUnit>null</proUnit><proValue>4</proValue><proEnName>Memory</proEnName><proName>内存</proName><proDataType>60</proDataType></fieldItem><fieldItem relyOnCode=\"mysql\"><proVerfyTips>null</proVerfyTips><proDesc>硬盘</proDesc><proChargeTimeType>null</proChargeTimeType><proInitValue>null</proInitValue><proCode>Storage</proCode><proVerfyRule>null</proVerfyRule><proChargeType>null</proChargeType><proChargePrice>null</proChargePrice><proIsFixed>20</proIsFixed><proShowType>10</proShowType><proLabel>10,20,30</proLabel><proUnit>null</proUnit><proValue>50</proValue><proEnName>Storage</proEnName><proName>硬盘</proName><proDataType>60</proDataType></fieldItem></fieldItems>";
//	    System.out.println(JSON.toJSONString(dm.transToBean(xml)));
//	}


}
