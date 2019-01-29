package com.sdp.sqlModel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class AnalysisXmlFormat {
	 public static void main(String[] args) {
//	 	 String xml="<?xml version='1.0' encoding='UTF-8'?><root><field fieldtype='String' name='id'>测试String</field>"
//	 	 		+ "<field fieldtype='String' name='id2'>测试String</field>"
//	 	        +"<fielditem fieldtype='Map' name='id'><field fieldtype='String12' name='key12'>测试MAP1</field>"
//	 	        + "<field fieldtype='String34' name='key34'>测试MAP1</field>"
//	 	        +"</fielditem><fieldlist fieldtype='List&lt;Map&gt;' name='fieldlist'><field fieldtype='String..3333' name='id03333'>测试String33</field>"
//	 	        + "<field fieldtype='String....' name='id0000'>测试String</field><fielditem name='id'>"
//	 	        +" <field fieldtype='String' name='key'>测试MAP1</field></fielditem></fieldlist></root>";
//		 String xml = "<?xml version='1.0' encoding='UTF-8'?><root><fielditem name='Map'><field fieldtype='String' name='age'>"
//		 		+ "测试age</field><field fieldtype='String' name='tenantId'>测试tenantId</field></fielditem>"
//		 				+ "<field fieldtype='String' name='id'>测试id</field><field fieldtype='List' name='list1'>测试list1</field>"
//		 						+ "<fieldlist fieldtype='List&lt;Map&gt;' name='map'><field fieldtype='List' name='map'>测试map</field>"
//		 								+ "<fielditem fieldtype='Map' name='Map'><field fieldtype='String' name='tenantId'>测试tenantId</field>"
//		 										+ "<field fieldtype='String' name='age'>测试age</field></fielditem></fieldlist></root>";
//	 	 String xml = "<?xml version='1.0' encoding='UTF-8'?><root><fielditem fieldtype='Map' name='map'>测试map</fielditem></root>";
		 String xml ="<?xml version='1.0' encoding='UTF-8'?><root><fieldlist fieldtype='List&lt;Map&gt;' name='map'>测试map</fieldlist></root>";
	 	 String a = readStringXml(xml);
//	 	parseJSON2Map(a);
	 	System.out.println(parseJSON2Map(a));
	 }
	 
	 public List<Map<String, Object>> getField(String xml){
		 List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
	      Document doc = null;
	      try {
	          // 下面的是通过解析xml字符串的
	          doc = DocumentHelper.parseText(xml); // 将字符串转为XML
	          Element rootElt = doc.getRootElement(); // 获取根节点
	          //String list字段
	          Iterator iter = rootElt.elementIterator("field"); // 获取根节点下的子节点field
	          while (iter.hasNext()) {
	        	  Map<String,Object> stringMap = new HashMap<String,Object>();
	              Element recordEle = (Element) iter.next();
	              recordEle.attribute("name").getValue();
	              recordEle.attribute("fieldtype").getValue();
	              stringMap.put("name", recordEle.attribute("name").getValue());
	              stringMap.put("fieldtype", recordEle.attribute("fieldtype").getValue());
	              listMap.add(stringMap);
	          }
	      } catch (DocumentException e) {
	          e.printStackTrace();
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
	      return listMap;
	 }
	 public List<Map<String, Object>> getFieldMap(String xml){
		 List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
	      Document doc = null;
	      try {
	          // 下面的是通过解析xml字符串的
	          doc = DocumentHelper.parseText(xml); // 将字符串转为XML
	          Element rootElt = doc.getRootElement(); // 获取根节点
	          Iterator iterss = rootElt.elementIterator("fielditem"); ///获取根节点下的子节点fielditem
	          Map<String,Object> map = new HashMap<String,Object>();
	          while (iterss.hasNext()) {
	        	  List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
	              Element recordEless = (Element) iterss.next();
	              Iterator itersElIterator = recordEless.elementIterator("field"); // 获取子节点fielditem下的子节点field
	              while (itersElIterator.hasNext()) {
	            	  Map<String,Object> mapMap = new HashMap<String,Object>();
	            	  Element recordEle = (Element) itersElIterator.next();
	            	  recordEle.attribute("name").getValue();
		              recordEle.attribute("fieldtype").getValue();
		              map.put(recordEle.attribute("name").getValue(), recordEle.attribute("fieldtype").getValue());
		              mapMap.put("name", recordEle.attribute("name").getValue());
		              mapMap.put("fieldtype", recordEle.attribute("fieldtype").getValue());
		              listMap.add(mapMap);
	              }
	              Map<String,Object> mapCom = new HashMap<String,Object>();
	              if(listMap != null && !listMap.isEmpty()){
	            	  if(recordEless.attribute("name") != null){
	            		  mapCom.put("name", recordEless.attribute("name").getValue());
	            	  }
	            	  mapCom.put("fieldtype", "Map");
	            	  mapCom.put("value", listMap);
	            	  resultMap.add(mapCom);
	              }
	          }
	      } catch (DocumentException e) {
	          e.printStackTrace();
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
		return resultMap;
	 }
	 /**
	  * 获取Map
	  * @param xml
	  * @param reqMap
	  * @param sql
	  * @return
	  */
	 public String getFieldMap(String xml,Map<String, Object> reqMap,String sql){
	      Document doc = null;
	      try {
	          // 下面的是通过解析xml字符串的
	          doc = DocumentHelper.parseText(xml); // 将字符串转为XML
	          Element rootElt = doc.getRootElement(); // 获取根节点
	          //map字段
	          Iterator iterss = rootElt.elementIterator("fielditem"); ///获取根节点下的子节点fielditem
	          while (iterss.hasNext()) {
	        	  Element recordEless = (Element) iterss.next();
	        	  Map<String, Object> reqMap2 = (Map<String, Object>) reqMap.get(recordEless.attribute("name").getValue().toString());
	        	  sql = fieldItem( recordEless, reqMap2, sql);
	          }
	      } catch (DocumentException e) {
	          e.printStackTrace();
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
		return sql;
	 }
	 
	 public String fieldItem(Element recordEless,Map<String, Object> reqMap,String sql){
		Iterator itersElIterator = recordEless.elementIterator("field"); // 获取子节点fielditem下的子节点field
		System.out.println(recordEless.attribute("name").getValue());
		while (itersElIterator.hasNext()) {
			Element recordEle = (Element) itersElIterator.next();
//			Map<String, Object> map = (Map<String, Object>) reqMap.get(recordEless.attribute("name").getValue().toString());
			if (!recordEle.attribute("fieldtype").equals("List")) {
				sql = sql.replaceAll("\\$\\{"+recordEle.attribute("name").getValue().toString()+"\\}",reqMap.get(recordEle.attribute("name").getValue().toString()).toString());
			} else if (recordEle.attribute("fieldtype").equals("List")) {
				List list = (List) reqMap.get(recordEle.attribute("name").getValue().toString());
				StringBuffer buffer = new StringBuffer();
				for (int i = 0; i < list.size(); i++) {
					if (i == list.size() - 1) {
						buffer.append("'" + list.get(i) + "'");
					} else {
						buffer.append("'" + list.get(i) + "',");
					}
				}
				sql = sql.replaceAll("\\$\\{" + recordEle.attribute("name").getValue().toString() + "\\}", buffer.toString());
			}
		}
		Iterator itersItem = recordEless.elementIterator("fielditem");
		while (itersItem.hasNext()) {
			Element itersItemes = (Element) itersItem.next();
//			fieldItem(itersItemes, (Map<String, Object>) reqMap.get(recordEless.attribute("name").getValue()), sql);
			sql = fieldItem(itersItemes, (Map<String, Object>) reqMap.get(itersItemes.attribute("name").getValue()), sql);
		}
		return sql;
	 }
	 /**
	  * getFieldList获取list<object>
	  * @param xml
	  * @param reqMap
	  * @param sql
	  * @return
	  */
	public String getFieldList(String xml, Map<String, Object> reqMap, String sql) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xml); // 将字符串转为XML
			Element rootElt = doc.getRootElement(); // 获取根节点
			Iterator iterLMap = rootElt.elementIterator("fieldlist"); /// 获取根节点下的子节点fieldlist
			while (iterLMap.hasNext()) {
				Element recordEless = (Element) iterLMap.next();
				System.out.println("00"+recordEless.attribute("name").getValue());
				List<Object> reqList = (List<Object>) reqMap.get(recordEless.attribute("name").getValue());
				sql = fieldList(recordEless, reqList, sql);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sql;
	}
	 
	public String fieldList(Element recordEless,List<Object> reqMap1, String sql){
		Iterator iterString = recordEless.elementIterator("field"); // 获取根节点下的子节点field
		System.out.println(recordEless.attribute("name").getValue());
		while (iterString.hasNext()) {
			Element recordEle = (Element) iterString.next();
			if (!recordEle.attribute("fieldtype").equals("List")) {
				sql = sql.replaceAll("\\$\\{" + recordEle.attribute("name").getValue().toString() + "\\}",
						reqMap1.get(Integer.parseInt(recordEless.attribute("sortId").getValue().toString())).toString());
			} else if (recordEle.attribute("fieldtype").equals("List")) {
				List list = (List) reqMap1.get(Integer.parseInt(recordEless.attribute("sortId").getValue().toString()));
				StringBuffer buffer = new StringBuffer();
				for (int i = 0; i < list.size(); i++) {
					if (i == list.size() - 1) {
						buffer.append("'" + list.get(i) + "'");
					} else {
						buffer.append("'" + list.get(i) + "',");
					}
				}
				sql = sql.replaceAll("\\$\\{" + recordEle.attribute("name").getValue().toString() + "\\}", buffer.toString());
			}
//			recordEle.attribute("name").getValue();
//			recordEle.attribute("fieldtype").getValue();
		}
		Iterator iterMap = recordEless.elementIterator("fielditem"); /// 获取根节点下的子节点fielditem
		while (iterMap.hasNext()) {
			Element iterMapss = (Element) iterMap.next();
			sql = fieldItem( iterMapss, (Map<String, Object>) reqMap1.get(Integer.parseInt(iterMapss.attribute("sortId").getValue())), sql);
		}
		Iterator iterList = recordEless.elementIterator("fieldlist");
		while (iterList.hasNext()) {
			Element iterMapss = (Element) iterList.next();
			List<Object> reqMap = (List<Object>) reqMap1.get(Integer.parseInt(iterMapss.attribute("sortId").getValue()));
			sql = fieldList( iterMapss, reqMap, sql);
		}
		return sql;
	}
	 
	public static String readStringXml(String xml) {
		  Map<String,Object> resultMap = new HashMap<String,Object>();
	      Document doc = null;
	      try {
	          // 下面的是通过解析xml字符串的
	          doc = DocumentHelper.parseText(xml); // 将字符串转为XML
	          Element rootElt = doc.getRootElement(); // 获取根节点
	          //String list字段
	          Iterator iter = rootElt.elementIterator("field"); // 获取根节点下的子节点field
	          int i=0;
	          // 遍历head节点
	          while (iter.hasNext()) {
	        	  Map<String,Object> stringMap = new HashMap<String,Object>();
	              Element recordEle = (Element) iter.next();
	              recordEle.attribute("name").getValue();
	              recordEle.attribute("fieldtype").getValue();
	              stringMap.put("name", recordEle.attribute("name").getValue());
	              stringMap.put("fieldtype", recordEle.attribute("fieldtype").getValue());
	              resultMap.put(i+"String", stringMap);
	              i++;
	          }
	          //map字段
	          Iterator iterss = rootElt.elementIterator("fielditem"); ///获取根节点下的子节点body
	          Map<String,Object> map = new HashMap<String,Object>();
	          // 遍历body节点
	          int j=0;
	          while (iterss.hasNext()) {
	        	  List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
	              Element recordEless = (Element) iterss.next();
	              Iterator itersElIterator = recordEless.elementIterator("field"); // 获取子节点body下的子节点form
	              // 遍历Header节点下的Response节点
	              while (itersElIterator.hasNext()) {
	            	  Map<String,Object> mapMap = new HashMap<String,Object>();
	            	  Element recordEle = (Element) itersElIterator.next();
	            	  recordEle.attribute("name").getValue();
		              recordEle.attribute("fieldtype").getValue();
		              map.put(recordEle.attribute("name").getValue(), recordEle.attribute("fieldtype").getValue());
		              mapMap.put("name", recordEle.attribute("name").getValue());
		              mapMap.put("fieldtype", recordEle.attribute("fieldtype").getValue());
		              listMap.add(mapMap);
	              }
	              Map<String,Object> mapCom = new HashMap<String,Object>();
	              if(recordEless.attribute("name") != null){
	            	  mapCom.put("name", recordEless.attribute("name").getValue());
	              }
	              mapCom.put("fieldtype", "Map");
	              resultMap.put(j+"Map", mapCom);
	              if(listMap != null && !listMap.isEmpty()){
	            	  mapCom.put("value", listMap);
	              }else{
	            	  mapCom.put("value", "*");
	              }
	              j++;
	          }
	          //List<Map>字段
	          Iterator iterLMap = rootElt.elementIterator("fieldlist"); ///获取根节点下的子节点body
	          Map<String,Object> listMapMap = new HashMap<String,Object>();
	          // 遍历body节点
	          int num=0;
	          while (iterLMap.hasNext()) {
	        	  List<Map<String, Object>> listMapes = new ArrayList<Map<String, Object>>();
	        	  //String list字段
	        	  Element recordEless = (Element) iterLMap.next();
		          Iterator iterString = recordEless.elementIterator("field"); // 获取根节点下的子节点field
		          // 遍历head节点
		          while (iterString.hasNext()) {
		        	  Map<String,Object> stringMap = new HashMap<String,Object>();
		              Element recordEle = (Element) iterString.next();
		              recordEle.attribute("name").getValue();
		              recordEle.attribute("fieldtype").getValue();
		              stringMap.put("name", recordEle.attribute("name").getValue());
		              stringMap.put("fieldtype", recordEle.attribute("fieldtype").getValue());
		              listMapes.add(stringMap);
		          }
		          
		          //map字段
//		          int sum =0;
		          Iterator iterMap = recordEless.elementIterator("fielditem"); ///获取根节点下的子节点body
		          // 遍历body节点
		          while (iterMap.hasNext()) {
		        	  List<Map<String, Object>> listMapValue = new ArrayList<Map<String, Object>>();
		              Element recordMap = (Element) iterMap.next();
		              Map<String,Object> valueMap = new HashMap<String,Object>();
		              Iterator itersElIterator = recordMap.elementIterator("field"); // 获取子节点body下的子节点form
		              // 遍历Header节点下的Response节点
		              while (itersElIterator.hasNext()) {
		            	  Map<String,Object> stringMap = new HashMap<String,Object>();
		            	  Element recordEle = (Element) itersElIterator.next();
		            	  recordEle.attribute("name").getValue();
			              recordEle.attribute("fieldtype").getValue();
			              map.put(recordEle.attribute("name").getValue(), recordEle.attribute("fieldtype").getValue());
			              stringMap.put("name", recordEle.attribute("name").getValue());
			              stringMap.put("fieldtype", recordEle.attribute("fieldtype").getValue());
			              listMapValue.add(stringMap);
		              }
		              if(recordMap.attribute("name") != null){
		            	  valueMap.put("name", recordMap.attribute("name").getValue());
		              }
		              valueMap.put("fieldtype", "Map");
		              if(listMapValue != null && !listMapValue.isEmpty()){
		            	  valueMap.put("value", listMapValue);
		              }else{
		            	  valueMap.put("value", "*");
		              }
		              listMapes.add(valueMap);
		          }
		          if(recordEless.attribute("name") != null){
		        	  listMapMap.put("name", recordEless.attribute("name").getValue());
		          }
		          listMapMap.put("fieldtype", "List<Map>");
		          if(listMapes != null && !listMapes.isEmpty()){
		        	  listMapMap.put("value", listMapes);
		          }else{
		        	  listMapMap.put("value", "*");
		          }
		          resultMap.put(num+"List<Map>", listMapMap);
		          num++;
	          }
	          System.out.println(JSON.toJSONString(resultMap));
	      } catch (DocumentException e) {
	          e.printStackTrace();

	      } catch (Exception e) {
	          e.printStackTrace();

	      }
	      return JSON.toJSONString(resultMap);
	  }
	
	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		ListOrderedMap map = new ListOrderedMap();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}
}
