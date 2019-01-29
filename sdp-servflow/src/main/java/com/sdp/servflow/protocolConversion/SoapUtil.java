package com.sdp.servflow.protocolConversion;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

public class SoapUtil {

	/**
	 * 解析soapXML
	 * @param soapXML
	 * @return
	 */
	public static Map<String,String> parseSoapMessage(String soapXML) {
		Map<String,String> resultBean = new HashMap<String, String>();
		try {
			SOAPMessage msg = formatSoapString(soapXML);
			SOAPBody body = msg.getSOAPBody();
			Iterator<SOAPElement> iterator = body.getChildElements();
			parse(iterator, resultBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultBean;
	}

	public static void main(String[] args) {
		System.out.println("开始解析soap...");

//		String deptXML = "<SOAP:Envelope xmlns:SOAP=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP:Header/><SOAP:Body><ns:OP_SDMS_Consume_Material_SynResponse xmlns:ns=\"http://toSDMS.material.service.ffcs.com\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><ns:return><ns:BASEINFO><MSGID>?</MSGID><PMSGID>?</PMSGID><SENDTIME>20140212094609</SENDTIME><S_PROVINCE>?</S_PROVINCE><S_SYSTEM>?</S_SYSTEM><SERVICENAME>?</SERVICENAME><T_PROVINCE>?</T_PROVINCE><T_SYSTEM>?</T_SYSTEM><RETRY>?</RETRY></ns:BASEINFO><ns:MESSAGE><RESULT>E</RESULT><REMARK/><XMLDATA><![CDATA[<response><RESULT>E</RESULT><MESSAGE>平台系统处理时发生异常!保存接口接收数据出错!</MESSAGE></response>]]></XMLDATA></ns:MESSAGE></ns:return></ns:OP_SDMS_Consume_Material_SynResponse></SOAP:Body></SOAP:Envelope>";
		
		String deptXML = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Header/><SOAP-ENV:Body>"
				+ "<ns2:getCountryResponse xmlns:ns2=\"http://www.yourcompany.com/webservice\"><ns2:country><ns2:NAME>Spain</ns2:NAME><ns2:POPULATION>46704314</ns2:POPULATION>"
				+ "<ns2:capital>Madrid</ns2:capital><ns2:currency>EUR</ns2:currency></ns2:country></ns2:getCountryResponse>"
				+ "</SOAP-ENV:Body></SOAP-ENV:Envelope>";
		
		System.out.println(deptXML);
//		Map<String,String> ret = parseSoapMessage(deptXML);
		try {
			SOAPMessage msg = formatSoapString(deptXML);
			SOAPBody body = msg.getSOAPBody();
			Iterator<SOAPElement> iterator = body.getChildElements();
			PrintBody(iterator, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("解析soap结束...");
	}

	/**
	 * 把soap字符串格式化为SOAPMessage
	 * 
	 * @param soapString
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private static SOAPMessage formatSoapString(String soapString) {
		MessageFactory msgFactory;
		try {
			msgFactory = MessageFactory.newInstance();
			SOAPMessage reqMsg = msgFactory.createMessage(new MimeHeaders(),
					new ByteArrayInputStream(soapString.getBytes("UTF-8")));
			reqMsg.saveChanges();
			return reqMsg;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解析soap xml
	 * @param iterator
	 * @param resultBean
	 */
	private static void parse(Iterator<SOAPElement> iterator, Map<String,String> resultBean) {
		while (iterator.hasNext()) {
			SOAPElement element = iterator.next();
			if ("ns:BASEINFO".equals(element.getNodeName())) {
				continue;
			} else if ("ns:MESSAGE".equals(element.getNodeName())) {
				Iterator<SOAPElement> it = element.getChildElements();
				SOAPElement el = null;
				while (it.hasNext()) {
					el = it.next();
					if ("RESULT".equals(el.getLocalName())) {
						resultBean.put("result",el.getValue());
						System.out.println("#### " + el.getLocalName() + "  ====  " + el.getValue());
					} else if ("REMARK".equals(el.getLocalName())) {
						resultBean.put("remark", null != el.getValue() ? el.getValue() : "");
						System.out.println("#### " + el.getLocalName() + "  ====  " + el.getValue());
					} else if ("XMLDATA".equals(el.getLocalName())) {
						resultBean.put("xmldata",el.getValue());
						System.out.println("#### " + el.getLocalName() + "  ====  " + el.getValue());
					}
				}
			} else if (null == element.getValue()
					&& element.getChildElements().hasNext()) {
				parse(element.getChildElements(), resultBean);
			}
		}
	}

	private static void PrintBody(Iterator<SOAPElement> iterator, String side) {
		while (iterator.hasNext()) {
			SOAPElement element = (SOAPElement) iterator.next();
			System.out.println("Local Name : " + element.getLocalName());
			System.out.println("Node Name : " + element.getNodeName());
			System.out.println("Tag Name : " + element.getTagName());
			System.out.println("Value : " + element.getValue());
			if (null == element.getValue()
					&& element.getChildElements().hasNext()) {
				PrintBody(element.getChildElements(), side + "-----");
			}
		}
	}
}
