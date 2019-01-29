package com.sdp.servflow.protocolConversion;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.alibaba.fastjson.JSONObject;

public class Soap2Xml {

//	public static String SoapToXml(String version,String xml) {
//		
//	}
	
	public static String xmlToJSON(String xml) {
		JSONObject obj = new JSONObject();
		try {
			InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(is);
			Element root = doc.getRootElement();
			System.out.println("root name :"+root.getName());
			Element body = root.getChild("b");
			String s=body.getTextTrim();
			System.out.println(s);
			return null;
//			Map map = iterateElement(root);
//			obj.put(root.getName(), map);
//			return obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Map iterateElement(Element root) {
		List childrenList = root.getChildren();
		Element element = null;
		Map map = new HashMap();
		List list = null;
		for (int i = 0; i < childrenList.size(); i++) {
			list = new ArrayList();
			element = (Element) childrenList.get(i);
			if (element.getChildren().size() > 0) {
				if (root.getChildren(element.getName()).size() > 1) {
					if (map.containsKey(element.getName())) {
						list = (List) map.get(element.getName());
					}
					list.add(iterateElement(element));
					map.put(element.getName(), list);
				} else {
					map.put(element.getName(), iterateElement(element));
				}
			} else {
				if (root.getChildren(element.getName()).size() > 1) {
					if (map.containsKey(element.getName())) {
						list = (List) map.get(element.getName());
					}
					list.add(element.getTextTrim());
					map.put(element.getName(), list);
				} else {
					map.put(element.getName(), element.getTextTrim());
				}
			}
		}

		return map;
	}
	
	public static void main(String[] args) {
		String xml ="<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">"
				+ "<soap12:Body>"
				+ "<ROOT abc=\"123\">" + "<Data>" 
				+ "<Row><SFZH>1q2w3e</SFZH><XM>kobe</XM><PERSONID>123</PERSONID></Row>"
				+ "<Row><SFZH>0o9i8u</SFZH><XM>james</XM><PERSONID>123</PERSONID></Row>"
				+ "</Data>" 
				+ "<Status>00</Status><ErrorMsg></ErrorMsg>"
				+ "</ROOT></soap12:Body></soap12:Envelope>";
		String xml2="<a><body><c><d><name>kobe</name><age>24</age></d>"
				+ "<d><name>james</name><age>31</age></d>"
				+ "zhangsan</c></body></a>";
		System.out.println("xml : "+xml2);
		String str = xmlToJSON(xml2);
//		System.out.println(str);
	}
}
