package com.sdp.servflow.pubandorder.util;


import com.alibaba.fastjson.JSONObject;

import net.sf.json.JSONArray;
import net.sf.json.xml.XMLSerializer;


/***
 * 转换格式
 *
 * @author 任壮
 * @version 2017年7月27日
 * @see ParseUtil
 * @since
 */
public class ParseUtil {

    /**
     * XML转换为JSON
     * 
     * @param xml
     * @return json形式的字符串
     */
    public static String xmlToJson(String xml) {
        XMLSerializer serializer = new XMLSerializer();
        return serializer.read(xml).toString();
    }

    
    
    public static void main(String[] args) {
        com.alibaba.fastjson.JSONArray array = new com.alibaba.fastjson.JSONArray();
        JSONObject json = new JSONObject();
        json.put("name", "123");
        json.put("try", "123");
        array.add(json);
        JSONObject json2 = new JSONObject();
        json2.put("name", "456");
        json2.put("try", "456");
        array.add(json2);
        JSONObject rootq = new JSONObject();
        rootq.put("rootq", array);
        JSONObject root = new JSONObject();
        root.put("root", rootq);
        System.out.println(jsonToXML(root.toJSONString()));
        
        String xml ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
       +"<xml><root><rootq><user><name>123</name><try>123</try></user><user><name>1234</name><try>1234</try></user><user1><name>456</name><try>456</try></user1></rootq></root></xml>";
        
        System.out.println(xmlToJson(xml));

    }
    
    /**
     * JSON 转换为XML
     * 
     * @param json
     * @return xml字符串
     */
    public static String jsonToXML(String json) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        // 根节点名称
        xmlSerializer.setRootName("xml");
        // 不对类型进行设置
        xmlSerializer.setTypeHintsEnabled(false);
        String xmlStr = "";
        if (json.startsWith("[") && json.endsWith("]")) {
            // jsonArray
            JSONArray jobj = JSONArray.fromObject(json);
            xmlStr = xmlSerializer.write(jobj);
        }
        else {
            // jsonObject
            net.sf.json.JSONObject jobj = net.sf.json.JSONObject.fromObject(json);
            xmlStr = xmlSerializer.write(jobj);
        }
        return xmlStr;

    }

    /**
     * 替换json字符串中的指定的key
     * 
     * @param jsonString
     *            要替换的字符串
     * @param source
     *            需要替换的key名称
     * @param target
     *            替换后的key名称
     **/
    public static String parseRespCode(String jsonString, String source, String target)
        throws Exception {

        return jsonString;
    }

    /*
     * public static String parseRespCode(String source, String format) throws Exception { String
     * respCode = "00000"; if (StringUtils.isBlank(source)) { return respCode; } if
     * ("json".equals(format)) { respCode = (String)
     * JSONObject.parseObject(source).get(ESBConfig.getRespCodeKey()); } else if
     * ("xml".equals(format)) { Reader reader = new StringReader(source); SAXReader saxReader = new
     * SAXReader(); Document document = saxReader.read(reader); Element root =
     * document.getRootElement(); respCode = root.elementText(ESBConfig.getRespCodeKey()); } return
     * respCode; }
     */
}
