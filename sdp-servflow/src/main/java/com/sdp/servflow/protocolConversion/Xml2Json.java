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

public class Xml2Json {

	public static String xmlToJSON(String xml) {
		JSONObject obj = new JSONObject();
		try {
			InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(is);
			Element root = doc.getRootElement();
			Map map = iterateElement(root);
			obj.put(root.getName(), map);
			return obj.toString();
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
		test1();
//		test2();
//		test3();
	}


	/** 
	* @Title: test1 
	* @Description: 
	* xml格式:
	* <ROOT>
	    <Data>
	        <Row>
	            <SFZH>1q2w3e</SFZH>
	            <XM>kobe</XM>
	            <PERSONID>123</PERSONID>
	        </Row>
	        <Row>
	            <SFZH>0o9i8u</SFZH>
	            <XM>james</XM>
	            <PERSONID>123</PERSONID>
	        </Row>
	    </Data>
	    <Status>00</Status>
	    <ErrorMsg></ErrorMsg>
	</ROOT>
	转换后的json格式:
	{
	    "ROOT": {
	        "Status": "00",
	        "Data": {
	            "Row": [
	                {
	                    "SFZH": "1q2w3e",
	                    "PERSONID": "123",
	                    "XM": "kobe"
	                },
	                {
	                    "SFZH": "0o9i8u",
	                    "PERSONID": "123",
	                    "XM": "james"
	                }
	            ]
	        },
	        "ErrorMsg": ""
	    }
	}
	* tinybad
	* 2017年9月22日
	*/ 
	private static void test1() {
		String xml = "<ROOT abc=\"123\">" + "<Data>" 
				+ "<Row><SFZH>1q2w3e</SFZH><XM>kobe</XM><PERSONID>123</PERSONID></Row>"
				+ "<Row><SFZH>0o9i8u</SFZH><XM>james</XM><PERSONID>123</PERSONID></Row>"
				+ "</Data>" 
				+ "<Status>00</Status><ErrorMsg></ErrorMsg>"
				+ "</ROOT>";
		System.out.println(xml);
		String str = xmlToJSON(xml);
		System.out.println(str);
	}
	
	/** 
	* @Title: test2 
	* @Description: 
	* xml格式:(目前不支持)，只支持  一组<Row>标签 ,解决办法做拆分
	* <Row>
		    <SFZH>1q2w3e</SFZH>
		    <XM>kobe</XM>
		    <PERSONID>123</PERSONID>
		</Row>
		<Row>
		    <SFZH>0o9i8u</SFZH>
		    <XM>james</XM>
		    <PERSONID>456</PERSONID>
		</Row>
	转换后json格式:
		
	* tinybad
	* 2017年9月22日
	*/ 
	private static void test2() {
		String xml = "<Row><SFZH>1q2w3e</SFZH><XM>kobe</XM><PERSONID>123</PERSONID></Row>"
				+ "<Row><SFZH>0o9i8u</SFZH><XM>james</XM><PERSONID>123</PERSONID></Row>";
		System.out.println(xml);
		String str = xmlToJSON(xml);
		System.out.println(str);
	}
	
	/** 
	* @Title: test3 
	* @Description: 
	* xml格式:(目前不支持),解决办法做拆分
	* 	<SFZH>1q2w3e</SFZH>
		<XM>kobe</XM>
		<PERSONID>123</PERSONID>
		<SFZH>0o9i8u</SFZH>
		<XM>james</XM>
		<PERSONID>456</PERSONID>
	转换后jsonl格式:
	* tinybad
	* 2017年9月22日
	*/ 
	private static void test3() {
		String xml = "<a><SFZH>1q2w3e</SFZH><XM>kobe</XM><PERSONID>123</PERSONID></a>";
		System.out.println(xml);
		String str = xmlToJSON(xml);
		System.out.println(str);
	}
}
