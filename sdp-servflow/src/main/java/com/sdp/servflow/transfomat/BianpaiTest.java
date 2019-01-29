package com.sdp.servflow.transfomat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import com.alibaba.fastjson.JSONObject;
import com.sdp.servflow.common.BoncException;
import com.sdp.servflow.test.INetProtocolConstants;
import com.sdp.servflow.test.INetProtocolFactory;

public class BianpaiTest {/*
	public static void main(String[] args) throws ServletException, IOException{
		//		test1();
		//		test2();
		//		test3();
		//				test4();
		test5();
	}
	static INetProtocolFactory<?> getInetObj(String type){
		try {
			return INetProtocolFactory.createInet(type);
		} catch (BoncException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	// xml -- > soap -- > json
	@SuppressWarnings("unchecked")
	static void test5() throws ServletException, IOException{

		String xmlRequest = "<person><name>张三</name></person>";
		String xmlUrl="http://localhost:8080/restController/xmltest";
		Map<String,Object> params = new HashMap<String, Object>();
		params.put(INetProtocolConstants.SOAP_PARAMS_TYPE_KEY, INetProtocolConstants.SOAP_TYPE_1);
		params.put(INetProtocolConstants.SOAP_PARAMS_DATA_KEY, xmlRequest);
		params.put(INetProtocolConstants.SOAP_PARAMS_WSDL_KEY, xmlUrl);
		Map<String,String> webserviceReturn = null;
		try {
			webserviceReturn=(Map<String, String>) getInetObj(INetProtocolFactory.WEBSERVICE).requestApi(params);
		} catch (BoncException e) {
		}


		xmlRequest = webserviceReturn.get(INetProtocolConstants.REQUEST_MAP_RESULT_KEY);
		List<MappingBean> list =new ArrayList<MappingBean>();
		MappingBean mb1=new MappingBean("person/country","getCountryRequest/name",null,null,null);
		list.add(mb1);
		String soapRequest=ProtConversion.protocolChange(PROTOCOL_TYPE.XML, PROTOCOL_TYPE.SOAP_1_1, xmlRequest, list, null);
		System.out.println(soapRequest);
		String soapUrl="http://localhost:8080/ws";
		params.put(INetProtocolConstants.SOAP_PARAMS_TYPE_KEY, INetProtocolConstants.SOAP_TYPE_1);
		params.put(INetProtocolConstants.SOAP_PARAMS_DATA_KEY, soapRequest);
		params.put(INetProtocolConstants.SOAP_PARAMS_WSDL_KEY, soapUrl);
		try {
			webserviceReturn = (Map<String, String>) getInetObj(INetProtocolFactory.WEBSERVICE).requestApi(params);
		} catch (BoncException e) {
		}
		System.out.println("---------------------------");
		    System.out.println(webserviceReturn);

		List<MappingBean> list2 =new ArrayList<MappingBean>();
		MappingBean mb2=new MappingBean("getCountryResponse/country/capital","capital",null,null,null);
		list2.add(mb2);
		String jsonRequest=ProtConversion.protocolChange(PROTOCOL_TYPE.SOAP_1_1, PROTOCOL_TYPE.JSON, webserviceReturn.get(INetProtocolConstants.REQUEST_MAP_RESULT_KEY), list2, null);
		Map<String,Object> map1= new HashMap<String,Object>();
		map1.put("Content-type", "application/json;charset=utf-8");
		params.put(INetProtocolConstants.HTTP_PARAMS_URL_KEY, "http://localhost:8080/restController/jsontest");
		params.put(INetProtocolConstants.HTTP_PARAMS_TYPE_KEY, "post");
		params.put(INetProtocolConstants.HTTP_PARAMS_DATA_KEY, JSONObject.parse(jsonRequest));
		params.put(INetProtocolConstants.HTTP_PARMAS_HEADER_KEY, map1);
		try {
			System.out.println(getInetObj(INetProtocolFactory.RESTFUL).requestApi(params));
		} catch (BoncException e) {
		}

	}

	// xml -- > soap -- > json
	static void test4() throws ServletException, IOException{
		String xmlRequest = "<person><name>张三</name></person>";
		System.out.println("xml -- Request : "+xmlRequest);
		String xmlUrl="http://localhost:8080/restController/xmltest";
		String xmlResponse=HttpURLConnectionServlet.doPost(xmlUrl,xmlRequest,"application/xml");
		System.out.println("xml -- Response : "+xmlResponse);


		List<MappingBean> list =new ArrayList<MappingBean>();
		MappingBean mb1=new MappingBean("person/country","getCountryRequest/name",null,null,null);
		list.add(mb1);
		String soapRequest=ProtConversion.protocolChange(PROTOCOL_TYPE.XML, PROTOCOL_TYPE.SOAP_1_1, xmlResponse, list, null);
		System.out.println("soap -- Request : "+soapRequest);
		String soapUrl="http://localhost:8080/ws";
		String soapResponse=HttpURLConnectionServlet.doPost(soapUrl,soapRequest,"text/xml;charset=utf-8");
		System.out.println("soap -- Response : "+soapResponse);


		List<MappingBean> list2 =new ArrayList<MappingBean>();
		MappingBean mb2=new MappingBean("getCountryResponse/country/capital","capital",null,null,null);
		list2.add(mb2);
		String jsonRequest=ProtConversion.protocolChange(PROTOCOL_TYPE.SOAP_1_1, PROTOCOL_TYPE.JSON, soapResponse, list2, null);
		System.out.println("json -- Request : "+jsonRequest);
		String jsonUrl="http://localhost:8080/restController/jsontest";
		String jsonResponse=HttpURLConnectionServlet.doPost(jsonUrl,jsonRequest,"application/json");
		System.out.println("json -- Response : "+jsonResponse);
	}

	// json
	static void test1() throws ServletException, IOException{
		String data = "{\"capital\":\"北京\"}";
		String url="http://localhost:8080/restController/jsontest";
		String result=HttpURLConnectionServlet.doPost(url,data,"application/json");
		System.out.println(result);
	}
	// xml
	static void test2() throws ServletException, IOException{
		String data = "<person><name>张三</name></person>";
		String url="http://localhost:8080/restController/xmltest";
		String result=HttpURLConnectionServlet.doPost(url,data,"application/xml");
		System.out.println(result);
	}
	// soap
	static void test3() throws ServletException, IOException{
		String data ="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:gs=\"http://www.yourcompany.com/webservice\">"
				+ "<soapenv:Header/><soapenv:Body><gs:getCountryRequest><gs:name>"+"中国"
				+ "</gs:name></gs:getCountryRequest></soapenv:Body></soapenv:Envelope>"; 
		String url="http://localhost:8080/ws";
		String result=HttpURLConnectionServlet.doPost(url,data,"text/xml;charset=utf-8");
		System.out.println(result);
	}
*/}
