package com.sdp.servflow.pubandorder.protocol;
/**
 * 
 *//*
package com.bonc.servflow.pubandorder.protocol;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

*//**
 * @author renpengyuan
 * @date 2017年9月21日
 *//*
import org.apache.axis.client.Call;  
import org.apache.axis.client.Service;
import org.apache.axis.types.Schema;
import org.apache.camel.component.dataset.DataSet;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.xml.sax.InputSource;  
public class WebserviceTool {
    public static void main(String[] args) {
        try {  
            String endpoint = "http://www.webxml.com.cn/webservices/ChinaTVprogramWebService.asmx?wsdl";  
            String soapAction = "http://WebXml.com.cn/";
            String funcName="getAreaDataSet";
            // 直接引用远程的wsdl文件  
            // 以下都是套路  
            Service service = new Service();  
            Call call = (Call) service.createCall();  
            call.setTargetEndpointAddress(endpoint);  
            call.setUseSOAPAction(true);
            call.setSOAPActionURI(soapAction+funcName);
            call.setOperationName(new QName(soapAction, funcName));// WSDL里面描述的接口名称  
//          call.addParameter(new QName(soapAction, "startCity"),  
//                  org.apache.axis.encoding.XMLType.XSD_STRING,  
//                  javax.xml.rpc.ParameterMode.IN);// 接口的参数  
//          call.addParameter(new QName(soapAction, "lastCity"),  
//                  org.apache.axis.encoding.XMLType.XSD_STRING,  
//                  javax.xml.rpc.ParameterMode.IN);// 接口的参数  
//          call.addParameter(new QName(soapAction, "theDate"),  
//                  org.apache.axis.encoding.XMLType.XSD_STRING,  
//                  javax.xml.rpc.ParameterMode.IN);// 接口的参数  
//          call.addParameter(new QName(soapAction, "userID"),  
//                  org.apache.axis.encoding.XMLType.XSD_STRING,  
//                  javax.xml.rpc.ParameterMode.IN);// 接口的参数  
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_SCHEMA);// 设置返回类型  
//          String a1 = "北京";  
//          String a2 = "上海";  
//          String a3 = "2017";  
//          String a4 = "123";  
            Schema result = (Schema) call.invoke(new Object[] { });  
            StringBuffer a  = new StringBuffer();
            // 给方法传递参数，并且调用方法  
            for (int i = 0; i < result.get_any().length; i++) {  
                System.out.println(result.get_any()[i]);  
                System.out.println(xmlElements(result.get_any()[i].toString(),false));
                a.append(result.get_any()[i]);
            }  
        } catch (Exception e) {  
            e.printStackTrace();
            System.err.println(e.toString());  
        }  
    }  
    *//**  
     * 解析xml字符串返回一个Map  
     *   
     * @param xmlDoc  
     * @return Map  
     * @throws DocumentException 
     *//*  
    public static Map xmlElements(String xmlDoc,boolean useattr) throws DocumentException {  
          Document doc = DocumentHelper.parseText(xmlDoc);  
          Element root = doc.getRootElement();  
          Map<String, Object> map = (Map<String, Object>) xml2map(root);  
          if(root.elements().size()==0 && root.attributes().size()==0){  
              return map;  
          }  
          if(useattr){  
              //在返回的map里加根节点键（如果需要）  
              Map<String, Object> rootMap = new HashMap<String, Object>();  
              rootMap.put(root.getName(), map);  
              return rootMap;  
          }  
          return map;  
}
    private static Map xml2map(Element e) {  
        Map map = new LinkedHashMap();  
        List list = e.elements();  
        if (list.size() > 0) {  
            for (int i = 0; i < list.size(); i++) {  
                Element iter = (Element) list.get(i);  
                List mapList = new ArrayList();  
  
                if (iter.elements().size() > 0) {  
                    Map m = xml2map(iter);  
                    if (map.get(iter.getName()) != null) {  
                        Object obj = map.get(iter.getName());  
                        if (!(obj instanceof List)) {  
                            mapList = new ArrayList();  
                            mapList.add(obj);  
                            mapList.add(m);  
                        }  
                        if (obj instanceof List) {  
                            mapList = (List) obj;  
                            mapList.add(m);  
                        }  
                        map.put(iter.getName(), mapList);  
                    } else  
                        map.put(iter.getName(), m);  
                } else {  
                    if (map.get(iter.getName()) != null) {  
                        Object obj = map.get(iter.getName());  
                        if (!(obj instanceof List)) {  
                            mapList = new ArrayList();  
                            mapList.add(obj);  
                            mapList.add(iter.getText());  
                        }  
                        if (obj instanceof List) {  
                            mapList = (List) obj;  
                            mapList.add(iter.getText());  
                        }  
                        map.put(iter.getName(), mapList);  
                    } else  
                        map.put(iter.getName(), iter.getText());  
                }  
            }  
        } else  
            map.put(e.getName(), e.getText());  
        return map;  
    }  
}*/