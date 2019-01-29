package com.sdp.servflow.protocolConversion;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Json2Xml {
	public static String JsonToXml(Object json) {
		if (json == null) {
			return null;
		} else {
			Element elements = new Element("m4k5o6");
			getXMLFromObject(json, "m4k5o6", elements);
			XMLOutputter xmlOut = new XMLOutputter();
			String res = xmlOut.outputString(elements);
			res = res.replaceAll("<m4k5o6>", "").replaceAll("</m4k5o6>", "");
			return res;
		}
	}

	private static void getXMLFromObject(Object obj, String tag, Element parent) {
		if (obj == null)
			return;
		Element child;
		String eleStr;
		Object childValue;
		if (obj instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject) obj;
			for (Object temp : jsonObject.keySet()) {
				eleStr = temp.toString();
				childValue = jsonObject.get(temp);
				child = new Element(eleStr);
				if (childValue instanceof JSONArray)
					getXMLFromObject(childValue, eleStr, parent);
				else {
					parent.addContent(child);
					getXMLFromObject(childValue, eleStr, child);
				}
			}
		} else if (obj instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray) obj;
			for (int i = 0; i < jsonArray.size(); i++) {
				childValue = jsonArray.get(i);
				child = new Element(tag);
				parent.addContent(child);
				getXMLFromObject(childValue, tag, child);
			}
		} else if (obj instanceof Date) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			parent.setText(sf.format((Date) obj));
		} else {
			parent.setText(obj.toString());
		}
	}

	public static void main(String[] args) {
		test1();
//		test2();
//		test3();
	}
	
	/** 
	* @Title: test1 
	* @Description: 
	* json参数格式
	* {
	    "ROOT": {
	        "Data": {
	            "Row": [
	                {
	                    "SFZH": "1q2w3e",
	                    "XM": "kobe",
	                    "PERSONID": "123"
	                },
	                {
	                    "SFZH": "0o9i8u",
	                    "XM": "james",
	                    "PERSONID": "456"
	                }
	            ]
	        },
	        "Status": "00",
	        "ErrorMsg": ""
	    }
	}
	转换后xml格式:
	<ROOT>
	    <Data>
	        <Row>
	            <SFZH>1q2w3e</SFZH>
	            <XM>kobe</XM>
	            <PERSONID>123</PERSONID>
	        </Row>
	        <Row>
	            <SFZH>0o9i8u</SFZH>
	            <XM>james</XM>
	            <PERSONID>456</PERSONID>
	        </Row>
	    </Data>
	    <Status>00</Status>
	    <ErrorMsg></ErrorMsg>
	</ROOT>
	* tinybad
	* 2017年9月22日
	*/ 
	private static void test1() {
		JSONObject rootObject = new JSONObject();

		JSONArray dataArray = new JSONArray();
		JSONObject dataObject1 = new JSONObject();
		dataObject1.put("PERSONID", "123");
		dataObject1.put("XM", "kobe");
		dataObject1.put("SFZH", "1q2w3e");
		dataArray.add(dataObject1);
		JSONObject dataObject2 = new JSONObject();
		dataObject2.put("PERSONID", "456");
		dataObject2.put("XM", "james");
		dataObject2.put("SFZH", "0o9i8u");
		dataObject2.put("123s12d", "fasd");
		dataArray.add(dataObject2);
		
		JSONObject dataRootObject = new JSONObject();
		dataRootObject.put("Row", dataArray);
		JSONObject dataObject = new JSONObject();
		dataObject.put("Status", "00");
		dataObject.put("ErrorMsg", "");
		dataObject.put("Data", dataRootObject);
		rootObject.put("ROOT", dataObject);

		System.out.println(rootObject.toString());
		System.out.println(JsonToXml(rootObject));
	}
	
	/** 
	* @Title: test2 
	* @Description: 
	* json参数格式 :
	* {
		  "Row": [
		    {
		      "SFZH": "1q2w3e", 
		      "XM": "kobe", 
		      "PERSONID": "123"
		    }, 
		    {
		      "SFZH": "0o9i8u", 
		      "XM": "james", 
		      "PERSONID": "456"
		    }
		  ]
		}
	转换后xml格式:
		<Row>
		    <SFZH>1q2w3e</SFZH>
		    <XM>kobe</XM>
		    <PERSONID>123</PERSONID>
		</Row>
		<Row>
		    <SFZH>0o9i8u</SFZH>
		    <XM>james</XM>
		    <PERSONID>456</PERSONID>
		</Row>
	* tinybad
	* 2017年9月22日
	*/ 
	private static void test2() {
		JSONObject rootObject = new JSONObject();

		JSONArray dataArray = new JSONArray();
		JSONObject dataObject1 = new JSONObject();
		dataObject1.put("PERSONID", "123");
		dataObject1.put("XM", "kobe");
		dataObject1.put("SFZH", "1q2w3e");
		dataArray.add(dataObject1);
		JSONObject dataObject2 = new JSONObject();
		dataObject2.put("PERSONID", "456");
		dataObject2.put("XM", "james");
		dataObject2.put("SFZH", "0o9i8u");
		dataArray.add(dataObject2);

		JSONObject dataRootObject = new JSONObject();
		dataRootObject.put("Row", dataArray);

		System.out.println(dataRootObject.toString());
		System.out.println(JsonToXml(dataRootObject));
	}

	/** 
	* @Title: test3 
	* @Description: 
	* json参数格式:
	* [
		  {
		    "SFZH": "1q2w3e", 
		    "XM": "kobe", 
		    "PERSONID": "123"
		  }, 
		  {
		    "SFZH": "0o9i8u", 
		    "XM": "james", 
		    "PERSONID": "456"
		  }
		]
	转换后xml格式:
		<SFZH>1q2w3e</SFZH>
		<XM>kobe</XM>
		<PERSONID>123</PERSONID>
		<SFZH>0o9i8u</SFZH>
		<XM>james</XM>
		<PERSONID>456</PERSONID>
	* tinybad
	* 2017年9月22日
	*/ 
	private static void test3() {
		JSONObject rootObject = new JSONObject();

		JSONArray dataArray = new JSONArray();
		JSONObject dataObject1 = new JSONObject();
		dataObject1.put("PERSONID", "123");
		dataObject1.put("XM", "kobe");
		dataObject1.put("SFZH", "1q2w3e");
		dataArray.add(dataObject1);
		JSONObject dataObject2 = new JSONObject();
		dataObject2.put("PERSONID", "456");
		dataObject2.put("XM", "james");
		dataObject2.put("SFZH", "0o9i8u");
		dataArray.add(dataObject2);

		System.out.println(dataArray.toString());
		System.out.println(JsonToXml(dataArray));
	}

}
