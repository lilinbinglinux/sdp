package com.sdp.sqlModel.service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.alibaba.fastjson.JSON;

@Service
public class CreateXmlFormat {
	
	public static String createXml(String inputAttr) {

		String xmlString = "";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			document.setXmlStandalone(true);

			Element itemInfo = document.createElement("root");
			document.appendChild(itemInfo);

			Map<String, Object> reqest1 = (Map<String, Object>) JSON.parse(inputAttr);
			if (reqest1 != null && !reqest1.isEmpty()) {
				for (String key : reqest1.keySet()) {
					Map<String, Object> reqest2 = (Map<String, Object>) reqest1.get(key);
					if (reqest2.get("type").equals("String")) {
						// String
						Element itemStatistics = document.createElement("field");
						itemStatistics.setAttribute("name", reqest2.get("name").toString());
						itemStatistics.setAttribute("fieldtype", "String");
						itemStatistics.setTextContent("测试"+reqest2.get("name"));
						itemInfo.appendChild(itemStatistics);
					} else if (reqest2.get("type").equals("List")) {
						// List
						Element itemStatistics = document.createElement("field");
						itemStatistics.setAttribute("name",reqest2.get("name").toString());
						itemStatistics.setAttribute("fieldtype", "List");
						itemStatistics.setTextContent("测试"+reqest2.get("name"));
						itemInfo.appendChild(itemStatistics);
					} else if (reqest2.get("type").equals("Map")) {
						// MAP
						Element fielditem = document.createElement("fielditem");
						fielditem.setAttribute("name",reqest2.get("name").toString());
						fielditem.setAttribute("fieldtype",reqest2.get("type").toString());
						if(reqest2.get("value").equals("*")){
							fielditem.setTextContent("测试"+reqest2.get("name").toString());
						}else{
							List<Map<String, Object>> reqest3 = (List<Map<String, Object>>) reqest2.get("value");
							for (Map<String, Object> map : reqest3) {
								Element field = document.createElement("field");
								field.setAttribute("name", map.get("name").toString());
								field.setAttribute("fieldtype", map.get("type").toString());
								field.setTextContent("测试"+map.get("name"));
								fielditem.appendChild(field);
							}
						}
						itemInfo.appendChild(fielditem);
					}else if (reqest2.get("type").equals("List<Map>")) {
						Element fielditem = document.createElement("fieldlist");
						fielditem.setAttribute("name",reqest2.get("name").toString());
						fielditem.setAttribute("fieldtype","List<Map>");
//						fielditem.setTextContent("测试"+reqest2.get("name").toString());
						if(reqest2.get("value").equals("*")){
							fielditem.setTextContent("测试"+reqest2.get("name").toString());
						}else{
							List<Map<String, Object>> reqest3 = (List<Map<String, Object>>) reqest2.get("value");
							for (Map<String, Object> map : reqest3) {
								if (map.get("type").equals("String") || map.get("type").equals("List") ){
									Element itemStatistics = document.createElement("field");
									itemStatistics.setAttribute("name",reqest2.get("name").toString());
									itemStatistics.setAttribute("fieldtype", "List");
									itemStatistics.setTextContent("测试"+reqest2.get("name"));
									fielditem.appendChild(itemStatistics);
								}else if(map.get("type").equals("Map")){
									if(map.get("value").equals("*")){
										fielditem.setTextContent("测试"+map.get("name").toString());
									}else{
										List<Map<String, Object>> reqest31 = (List<Map<String, Object>>) map.get("value");
										Element fielditem1 = document.createElement("fielditem");
										fielditem1.setAttribute("name","Map");
										fielditem1.setAttribute("fieldtype","Map");
										//								fielditem1.setTextContent("测试Map");
										for (Map<String, Object> map1 : reqest31) {
											Element field = document.createElement("field");
											field.setAttribute("name", map1.get("name").toString());
											field.setAttribute("fieldtype", map1.get("type").toString());
											field.setTextContent("测试"+map1.get("name"));
											fielditem1.appendChild(field);
										}
										fielditem.appendChild(fielditem1);
									}
								}
							}
						}
						itemInfo.appendChild(fielditem);
					}
				}
			}
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource domSource = new DOMSource(document);

			// xml transform String
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			transformer.transform(domSource, new StreamResult(bos));
			xmlString = bos.toString();
			System.out.println(xmlString);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return xmlString;
	}
	
	public static void main(String[] args) {
		//String
//		Map<String, Object> req1 = new HashMap<String, Object>();
//		req1.put("name", "id");
//		req1.put("type", "String");
//		//list
//		Map<String, Object> req5 = new HashMap<String, Object>();
//		req5.put("name", "list1");
//		req5.put("type", "List");
//		
//		//Map
//		Map<String, Object> req = new HashMap<String, Object>();
//		req.put("name", "tenantId");
//		req.put("type", "String");
//		Map<String, Object> req3 = new HashMap<String, Object>();
//		req3.put("name", "age");
//		req3.put("type", "String");
//		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
//		list1.add(req);
//		list1.add(req3);
//		Map<String, Object> req4 = new HashMap<String, Object>();
//		req4.put("value", list1);
//		req4.put("name", "map");
//		req4.put("type", "Map");
//		//list<Map>
//		Map<String, Object> reqList = new HashMap<String, Object>();
//		List<Object> list11 = new ArrayList<Object>();
//		reqList.put("name", "map");
//		reqList.put("type", "List<Map>");
//		Map<String, Object> req51 = new HashMap<String, Object>();
//		req51.put("name", "id");
//		req51.put("type", "String");
//		list11.add(req51);
//		list11.add(req4);
//		reqList.put("value", list11);
//		
//		
//		Map<String, Object> req88 = new HashMap<String, Object>();
//		req88.put("name", "tenantId");
//		req88.put("type", "String");
//		Map<String, Object> req99 = new HashMap<String, Object>();
//		req99.put("name", "age");
//		req99.put("type", "String");
//		List<Map<String, Object>> list00 = new ArrayList<Map<String, Object>>();
//		list00.add(req99);
//		list00.add(req88);
//		Map<String, Object> req000 = new HashMap<String, Object>();
//		req000.put("value", list00);
//		req000.put("name", "map");
//		req000.put("type", "Map");
//		
//		
//		
//		Map<String, Object> reqest = new HashMap<String, Object>();
//		reqest.put("id", req1);
//		reqest.put("req121", req000);
//		reqest.put("list", req5);
//		reqest.put("List<Map>", reqList);
//		JSON.toJSONString(reqest);
//		Map<String, Object> req1 = new HashMap<String, Object>();
//		req1.put("name", "tableName");
//		req1.put("type", "String");
//		//list
//		Map<String, Object> req5 = new HashMap<String, Object>();
//		req5.put("name", "tableId");
//		req5.put("type", "String");
		Map<String, Object> req000 = new HashMap<String, Object>();
		req000.put("value", "*");
		req000.put("name", "map");
		req000.put("type", "Map");
		Map<String, Object> reqList = new HashMap<String, Object>();
		reqList.put("name", "map");
		reqList.put("type", "List<Map>");
		reqList.put("value", "*");
		Map<String, Object> reqest = new HashMap<String, Object>();
//		reqest.put("id", req1);
//		reqest.put("req121", req5);
//		reqest.put("req121", req000);
		reqest.put("List<Map>", reqList);
		 createXml(JSON.toJSONString(reqest));
	}
}
