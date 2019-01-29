package com.sdp.servflow.pubandorder.serve.param;


import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.sdp.servflow.pubandorder.node.analyze.factory.NodeFactoryImp;
import com.sdp.servflow.pubandorder.node.model.node.EndNode;
import com.sdp.servflow.pubandorder.node.model.node.Node;
import com.sdp.servflow.pubandorder.node.model.node.StartNode;
import com.sdp.servflow.pubandorder.util.IContants;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 页面展示参数的工厂 模板模式+抽象工厂模式
 * 
 * @author 任壮
 * @version 2017年12月27日
 * @see ShowFactory
 * @since
 */
public abstract class ShowFactory {

    // 报文模板中被替换的部分
    public static final String REPLACE_CONTENT = "${busiparam}";

    // 同步订阅的请求头模板
    public static final String SOAP_SYN_REQUEST =

        " <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.bonc.com/\">    \n"
                                                  + "     <soapenv:Header/>                                                                                                       \n"
                                                  + "     <soapenv:Body>                                                                                                          \n "
                                                  + "         <web:apis>                                                                                                          \n"
                                                  + "           <busiparam>${busiparam}</busiparam>                                                                               \n"
                                                  + "        </web:apis>                                                                                                          \n"
                                                  + "     </soapenv:Body>                                                                                                         \n"
                                                  + "  </soapenv:Envelope>";

    // 同步订阅的请求头模板
    public static final String SOAP_SYN_RESPONSE = " <soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">     \n"
                                                   + "     <soap:Body>                                                            \n"
                                                   + "         <ns1:apisResponse xmlns:ns1=\"http://webservice.bonc.com/\">         \n"
                                                   + "             <return>                                                       \n"
                                                   + "                 <requestId>可以用来查询本次访问的唯一记录</requestId>    \n"
                                                   + "                 <respCode>00000</respCode>                                 \n"
                                                   + "                 <respDesc>调用成功</respDesc>                        \n"
                                                   + "                 <result>${busiparam}</result>                        \n"
                                                   + "             </return>                                                      \n"
                                                   + "         </ns1:apisResponse>                                                \n"
                                                   + "     </soap:Body>                                                           \n"
                                                   + " </soap:Envelope>                                                           \n";

    // 异步的请求体
    public static final String SOAP_ASY_REQUEST = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.bonc.com/\"> \n"
                                                  + "    <soapenv:Header/>                                                                                               \n"
                                                  + "    <soapenv:Body>                                                                                                  \n"
                                                  + "        <web:apisPublish>                                                                                          \n"
                                                  + "          <busiparam>${busiparam}</busiparam>                                                        \n"
                                                  + "       </web:apisPublish>                                                                                           \n"
                                                  + "    </soapenv:Body>                                                                                                 \n"
                                                  + " </soapenv:Envelope>                                                                                                \n";

    // 异步的响应体
    public static final String SOAP_ASY_RESPONSE = " <soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">      \n"
                                                   + "     <soap:Body>                                                             \n"
                                                   + "         <ns1:apisPublishResponse xmlns:ns1=\"http://webservice.bonc.com/\">   \n"
                                                   + "             <return>                                                        \n"
                                                   + "                 <respCode>00000</respCode>                                  \n"
                                                   + "                 <respDesc>调用成功</respDesc>                               \n"
                                                   + "             </return>                                                       \n"
                                                   + "         </ns1:apisPublishResponse>                                          \n"
                                                   + "     </soap:Body>                                                            \n"
                                                   + " </soap:Envelope>                                                            \n";
    //HTTP同步的响应信息
    private static final String HTTP_SYN_RESPONSE = " {                                     \n"
                                                + "    \"respCode\": \"00000\",              \n"
                                                + "    \"respDesc\": \"调用成功\",           \n"
                                                + "    \"result\": ${busiparam},                   \n"
                                                + "    \"requestId\": \"可以用来查询本次访问的唯一记录\"                 \n"
                                                + " }                                     \n"

    ;
    //HTTP异步的响应信息
    private static final String HTTP_ASY_RESPONSE = " {                                     \n"
        + "    \"respCode\": \"00000\",              \n"
        + "    \"respDesc\": \"调用成功\",           \n"
        + "    \"result\": ${busiparam},                   \n"
        + "    \"requestId\": \"可以用来查询本次访问的唯一记录\"                 \n"
        + " }                                     \n"
        
    ;
    //同步的xml返回
    private static final String HTTP_SYN_XMl = 
    		
    		
   "<xml><respCode>00000</respCode><respDesc>调用成功</respDesc><result> <![CDATA[${busiparam}]]></result></xml>";
    
    
    
    

    // public abstract String getParam(String xml);

    /***
     * Description:解析模板
     * 
     * @param serFlow
     * @param type 同步、异步（0同步 1异步）
     * @return
     * @throws Exception
     *             Map<String,String>
     * @see
     */
    public Map<String, String> getParam(String type, String serFlow)
        throws Exception {
        // 1.解析xml
        InputStream is = new ByteArrayInputStream(serFlow.getBytes("utf-8"));
        SAXBuilder sb = new SAXBuilder();
        Document doc = sb.build(is);
        Element sourceRoot = doc.getRootElement();
        List<Element> list = sourceRoot.getChildren();

        NodeFactoryImp factory = NodeFactoryImp.getOneNodeFactory();
        StartNode startNode = null;
        EndNode endNode = null;
        // 2.取出开始结束节点
        for (Element element : list) {
            Node node = factory.createNode(element);
            if ((Node.startNodeStyle).equals(node.getNodeStyle())) {
                startNode = (StartNode)node;
            }
            else if ((Node.endNodeStyle).equals(node.getNodeStyle())) {
                endNode = (EndNode)node;
            }
        }

        if(endNode!=null) {
            endNode.setAgreement(startNode.getAgreement());
        }
        // 3.构造入参和出现信息
        Map<String, String> map = new HashMap<String, String>();
        map.put("inputParam", buildParam(type, startNode));
        map.put("outputParam", buildParam(type, endNode));

        return map;
    }

    /***
     * Description:构建最后的返回值
     * 
     * @param node
     * @return Map<String,String>
     * @see
     */

    private String buildParam(String type, StartNode node) {

        if (node.getAgreement() == null) {
            return null;
        }

        String paramString = getParam(node.getParam().getFormat(), node);

        if (node.getAgreement().equals(IContants.HTTP)) {
            // http直接在返回响应参数

            if ((Node.startNodeStyle).equals(node.getNodeStyle())) {

            	
                return paramString;
            }
            else if ((Node.endNodeStyle).equals(node.getNodeStyle())) {
                // 响应报文中替换参数
                if(type.equals("0")){
                	if(IContants.JSON.equals(node.getParam().getFormat())) {
                		paramString = HTTP_SYN_RESPONSE.replace(REPLACE_CONTENT, paramString);
                	}else {
                		paramString = HTTP_SYN_XMl.replace(REPLACE_CONTENT, paramString);
                	}
                	
                }else if(type.equals("1")){
                    paramString = HTTP_ASY_RESPONSE.replace(REPLACE_CONTENT, paramString);
                }
            }

        }
        else if (node.getAgreement().equals(IContants.WEBSERVICE)) {
            // 同步订阅中获取的参数
            // webservice在响应参数包装一层这个系统暴露出的接口的信息
            if ((Node.startNodeStyle).equals(node.getNodeStyle())) {
                
                if(type.equals("0")){
                    paramString = SOAP_SYN_REQUEST.replace(REPLACE_CONTENT, paramString);
                }else if(type.equals("1")){
                    paramString = SOAP_ASY_REQUEST.replace(REPLACE_CONTENT, paramString);
                }
            }
            else if ((Node.endNodeStyle).equals(node.getNodeStyle())) {
                // 响应报文中替换参数
                if(type.equals("0")){
                    paramString = SOAP_SYN_RESPONSE.replace(REPLACE_CONTENT, paramString);
                }else if(type.equals("1")){
                    paramString = SOAP_ASY_RESPONSE.replace(REPLACE_CONTENT, paramString);
                }
            }
        }

        return paramString;
    }

    /**
     * Description: 抽象工厂，用于构建参数
     * 
     * @param format
     * @param node
     * @return String
     * @see
     */
    private String getParam(String format, StartNode node) {

        if (format == null) {
            return "不支持的转换方式";
        }
        else if (format.equals(IContants.JSON)) {
            return new JsonFactory().getParamString(node);
        }
        else if (format.equals(IContants.XML)||format.equals(IContants.SOAP_1_1)||format.equals(IContants.SOAP_1_2)) {
            return new XmlFactory().getParamString(node);
        }
        return "不支持的转换方式";


    }

    /***
     * Description: 获取参数
     * 
     * @param node
     * @return String
     * @see
     */
    protected abstract String getParamString(StartNode node);

}
