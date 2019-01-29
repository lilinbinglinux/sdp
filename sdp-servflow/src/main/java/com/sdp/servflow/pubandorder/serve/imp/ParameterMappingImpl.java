package com.sdp.servflow.pubandorder.serve.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.XMLOutputter;
import org.jsoup.helper.StringUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdp.servflow.pubandorder.node.model.field.Attribute;
import com.sdp.servflow.pubandorder.node.model.field.XmlNode;
import com.sdp.servflow.pubandorder.node.model.node.ConditionNode;
import com.sdp.servflow.pubandorder.serve.ParameterMapping;
import com.sdp.servflow.pubandorder.serve.format.FormatConversion;
import com.sdp.servflow.pubandorder.serve.model.ProtocolData;
import com.sdp.servflow.pubandorder.util.IContants;
import com.sdp.servflow.pubandorder.util.Invoke;
import com.sdp.servflow.transfomat.MappingBean;
import com.sdp.servflow.transfomat.PROTOCOL_TYPE;
import com.sdp.servflow.transfomat.ProtConversion;
@Service
public class ParameterMappingImpl implements ParameterMapping{
 
    private static final Logger logger =Logger.getLogger(ParameterMappingImpl.class);
    
    @Override
    public ProtocolData mapping(ConditionNode conditionNode, Map<String, ProtocolData> resource, 
                              Map<String, XmlNode> xmlNodes) throws Exception {
        
        ProtocolData data = new ProtocolData();
        String format =  null;//xml or json
        
        String type =null;//Obejct or List
        
        Element element = null;
        JSONObject json = null;
       JSONArray jsonArray = null;
       
       Map<String, Namespace> nameSpaces = new HashMap<String, Namespace>();
       
       if(xmlNodes != null) {
           // 1.处理value存在情况下，目前只有soap的命名空间存在value
           for(Map.Entry<String, XmlNode> xmlNode: xmlNodes.entrySet()) {
               String location = xmlNode.getValue().getLocation();
               if(location != null && IContants.NAME_SPACE.equals(location) ) {
                   nameSpaces.put(xmlNode.getValue().getName().split(":")[1],Namespace.getNamespace(xmlNode.getValue().getName().split(":")[1], xmlNode.getValue().getValue()) );
               }
                   
           }
       }
       
       
       // 2.处理常量映射
       Map<String, String> constant = conditionNode.getConstantField();
       if(constant != null)
       for(Map.Entry<String, String> join: constant.entrySet()) {
               List<Object> values= new ArrayList<Object>();
               XmlNode targetNode = xmlNodes.get(join.getKey());
               
               //参数的位置  在url上还是在请求体请求头上
               String  targetParampos = targetNode.getLocation();
               values.add(constant.get(join.getKey()));
               logger.info(" --常量参数映射----values---"+values);
               if(IContants.REQATTRIBUTE.equals(targetParampos)||IContants.RESPATTRIBUTE.equals(targetParampos)
                   //说明这个参数位于属性位置
                   ||IContants.REQBODY.equals(targetParampos)||
                   IContants.RESPONSEBODY.equals(targetParampos)||IContants.RESPONSEHEADER.equals(targetParampos)){
                   //说明是请求值
                   if(format == null)
                       format =   targetNode.getParamter().getFormat();
                   if(IContants.XML.equals(format)||IContants.SOAP_1_1.equals(format)||IContants.SOAP_1_2.equals(format)){
                       String attrName=targetNode instanceof Attribute?targetNode.getName():null;
                       element=      FormatConversion.putValueToXml(element, targetNode.getPath(), values, attrName,nameSpaces);
                   }else{
                       if(type ==null)
                           type =  targetNode.getParamter().getType();
                           if(IContants.OBJECT.equals(type)) {
                               if(json == null)
                                   json = new JSONObject();
                               FormatConversion.putValueToJSON(json, targetNode.getPath(), values);
                           }else   if(IContants.LIST.equals(type)||IContants.LISTOBJECT.equals(type)||IContants.LISTLIST.equals(type)){
                               if(jsonArray == null)
                                   jsonArray =new JSONArray();
                               FormatConversion.putValueToJSON(jsonArray, targetNode.getPath(), values);
                           }
                   }
                   
               }else if(IContants.REQUESTHEADER.equals(targetParampos)) {
                   data.getRequestHeader().put(targetNode.getName(), values!=null?values.get(0):null);
               }else if(IContants.URLPARAM.equals(targetParampos)) {
                   data.getUrlParam().put(targetNode.getName(), values!=null?values.get(0):null);
               }else if(IContants.NAME_SPACE.equals(targetParampos)) {
                   //TODO 命名空间的放入
               }
       }
       // 3.处理变量
        for(Map.Entry<String, String> join: conditionNode.getJoinfield().entrySet()) {
            if( StringUtil.isBlank(join.getKey())|| StringUtil.isBlank(join.getValue())) {
                //对于没有映射关系的跳过
                continue;
            }
            List<Object> values= new ArrayList<Object>();
                XmlNode targetNode = xmlNodes.get(join.getKey());
                XmlNode sourceNode = xmlNodes.get(join.getValue());
                
                //参数的位置  在url上还是在请求体请求头上
                String  targetParampos = targetNode.getLocation();
            //    String  sourceParampos = sourceNode.getLocation();
              //来源服务
                String sourceSerId = sourceNode.getParamter().getBelongNode().getNodeId();
                //数据来源
                if(IContants.URLPARAM.equals(sourceNode.getLocation())){
                    // 来自于url
                    values.add(resource.get(sourceSerId).getUrlParam().get(sourceNode.getName()));
                }else if(IContants.REQUESTHEADER.equals(sourceNode.getLocation())){
                    // 来自于header
                    values.add(resource.get(sourceSerId).getRequestHeader().get(sourceNode.getName()));
                }else if(IContants.RESPONSEHEADER.equals(sourceNode.getLocation())){
                    // 来自于header
                    values.add(resource.get(sourceSerId).getResponseHeader().get(sourceNode.getName()));
                }else{
                    //来自请求体中
                    String   sourceContent =  (String)((resource.get(sourceSerId)).getResponseBody());
                    values =FormatConversion.getValues(sourceContent, sourceNode, nameSpaces);
                }
                logger.info(" --变量参数映射---values---"+values);
                if(IContants.REQATTRIBUTE.equals(targetParampos)||IContants.RESPATTRIBUTE.equals(targetParampos)
                    //说明这个参数位于属性位置
                    ||IContants.REQBODY.equals(targetParampos)||
                    IContants.RESPONSEBODY.equals(targetParampos)||IContants.RESPONSEHEADER.equals(targetParampos)){
                    //说明是请求值
                    if(format == null)
                        format =   targetNode.getParamter().getFormat();
                    if(IContants.XML.equals(format)||IContants.SOAP_1_1.equals(format)||IContants.SOAP_1_2.equals(format)){
                        String attrName=targetNode instanceof Attribute?targetNode.getName():null;
                        element=      FormatConversion.putValueToXml(element, targetNode.getPath(), values, attrName,nameSpaces);
                    }else{
                        if(type ==null)
                            type =  targetNode.getParamter().getType();
                            if(IContants.OBJECT.equals(type)) {
                                if(json == null)
                                    json = new JSONObject();
                                FormatConversion.putValueToJSON(json, targetNode.getPath(), values);
                            }else   if(IContants.LIST.equals(type)||IContants.LISTOBJECT.equals(type)||IContants.LISTLIST.equals(type)){
                                if(jsonArray == null)
                                    jsonArray =new JSONArray();
                                FormatConversion.putValueToJSON(jsonArray, targetNode.getPath(), values);
                            }
                    }
                    
                }else if(IContants.REQUESTHEADER.equals(targetParampos)) {
                    data.getRequestHeader().put(targetNode.getName(), values!=null?values.get(0):null);
                }else if(IContants.URLPARAM.equals(targetParampos)) {
                    data.getUrlParam().put(targetNode.getName(), values!=null?values.get(0):null);
                }
        }
        
        // 4.将soap声明的命名空间放入指定声明的位置
      /*  if(xmlNodes != null) {
            for(Map.Entry<String, XmlNode> xmlNode: xmlNodes.entrySet()) {
                String location = xmlNode.getValue().getLocation();
                // 归属的接口id
                if(  location != null && IContants.NAME_SPACE.equals(location) ) {
                    FormatConversion.putNameSpaceToXmlNode(element, xmlNode.getValue().getPath(),
                      nameSpaces); 
                }
            }
        }*/
        
        String result = null;
        if(IContants.XML.equals(format)||IContants.SOAP_1_1.equals(format)||IContants.SOAP_1_2.equals(format)){
            XMLOutputter xmlOut = new XMLOutputter();
            result = xmlOut.outputString(element);
        }else{
                if(json != null &&IContants.OBJECT.equals(type)) {
                    result =JSONObject.toJSONString(json); 
                }else   if(IContants.LIST.equals(type)||IContants.LISTOBJECT.equals(type)||IContants.LISTLIST.equals(type)){
                    result =JSONObject.toJSONString(jsonArray); 
                }
        }
        data.setRequestBody(result);
    return data;
    }
    
    /***
     * 
     * Description: 获取所有的value
     * 
     *@return List<Object>
     * @throws Exception 
     *
     * @see
     */
    private List<Object> getValues(String sourceParamId, Map<String, XmlNode> xmlNodes,
                                   Map<String, ProtocolData> resource,
                                   Map<String, Namespace> nameSpaces) throws Exception {
        List<Object> values= new ArrayList<Object>();    
        
       
        
                XmlNode sourceNode = xmlNodes.get(sourceParamId);
                
              //来源服务
                String sourceSerId = sourceNode.getParamter().getBelongNode().getNodeId();
                //数据来源
                if(IContants.URLPARAM.equals(sourceNode.getLocation())){
                    // 来自于url
                    values.add(resource.get(sourceSerId).getUrlParam().get(sourceNode.getName()));
                }else if(IContants.REQUESTHEADER.equals(sourceNode.getLocation())){
                    // 来自于header
                    values.add(resource.get(sourceSerId).getRequestHeader().get(sourceNode.getName()));
                }else if(IContants.RESPONSEHEADER.equals(sourceNode.getLocation())){
                    // 来自于header
                    values.add(resource.get(sourceSerId).getResponseHeader().get(sourceNode.getName()));
                }else{
                    //来自请求体中
                    String   sourceContent =  (String)((resource.get(sourceSerId)).getResponseBody());
                    values =FormatConversion.getValues(sourceContent, sourceNode, nameSpaces);
                }
                logger.info(" --获取条件--values---"+values);
        
        return values;
    }
    
    @Override
    public boolean JudgeCondition(ConditionNode conditionNode, Map<String, ProtocolData> resource, 
                               Map<String, XmlNode> xmlNodes) throws Exception {
        boolean result =false;
        String condition =conditionNode.getCode();
        if(condition==null||condition.length()==0){
            return true;
        }
        //1.获取到所有的namespaces
        Map<String, Namespace> nameSpaces = new HashMap<String, Namespace>();
        
        if(xmlNodes != null) {
            // 1.处理value存在情况下，目前只有soap的命名空间存在value
            for(Map.Entry<String, XmlNode> xmlNode: xmlNodes.entrySet()) {
                String location = xmlNode.getValue().getLocation();
                if(location != null && IContants.NAME_SPACE.equals(location) ) {
                    nameSpaces.put(xmlNode.getValue().getName().split(":")[1],Namespace.getNamespace(xmlNode.getValue().getName().split(":")[1], xmlNode.getValue().getValue()) );
                }
                    
            }
        }
        
        // 2.对所有的值进行判断
        for(String join: conditionNode.getFieldList()) {
            List<Object> values=  getValues(join, xmlNodes, resource, nameSpaces);
            
            String name = xmlNodes.get(join).getName();
            Map<String, Object> conditions = new HashMap<String, Object>();   
            for(Object obj:values) {
                conditions.put(name, obj);
                Object code = Invoke.convertToCode(condition,conditions);  
                result= (boolean)code;
                if(result == false) {
                    return false;
                }
            }
        }
        
        return  result;
    }
    
    
    /**
     * 
     * Description: 
     * @deprecated
     *@param obj
     *@param xmlNode
     *@return Object
     *
     * @see
     */
    @SuppressWarnings("unused")
    private Object initObject(Object obj, XmlNode xmlNode)
    {
        Object object = null;
            //目标协
            String format =   xmlNode.getParamter().getFormat();
            String type = xmlNode.getParamter().getType();
            if(IContants.JSON.equals(format))
            {
                
                if(IContants.OBJECT.equals(type)) {
                    object = new JSONObject();
                }else   if(IContants.LIST.equals(type)||IContants.LISTOBJECT.equals(type)||IContants.LISTLIST.equals(type)){
                    object =new JSONArray();
                }
            }else  if(IContants.XML.equals(format)){
                object = new Element(null);
            }
            return object;
    }

    /****
     * 
     * Description: 获取pub的相应的请求和响应值
     * @deprecated
     *@param resourceMap 之前所有集合的数据
     *@param targetParamType   当前服务的请求格式
     *@param parms
     *@param reqNameSpaces  请求时的命名空间
     *@param rePonseNameSpaces  响应时的命名空间
     *@return ProtocolData
     *
     * @see
     */
    @SuppressWarnings("unused")
    private ProtocolData  getMapping(Map<String, ProtocolData> resourceMap,
                                           String targetParamType,List<HashMap<String, Object>>  parms,
                                           Map<String, Namespace>    reqNameSpaces,Map<String, Namespace>    rePonseNameSpaces){
        
            ProtocolData data = new ProtocolData();
            
            String result = "";
            //映射时候需要的参数集合
            List<MappingBean> list =new ArrayList<MappingBean>();
        
            for(HashMap<String, Object> parm : parms) {
                Object value = null;
                
                    
                    //参数的位置  在url上还是在请求体请求头上
                    String  targetParampos = (String)parm.get("targetParampos");
                    String  sourceParampos = (String)parm.get("sourceParampos");
                    
                    // 源路径
                    String sourPath="";
                    // 目标路径
                    String targetPath="";
                    // 源xml元素节点的属性名称
                    String sourceAttrName=null;
                    // 目标xml元素节点的属性名称
                    String targetAttrName=null;
                    //来源于属性的值
                    if(IContants.REQATTRIBUTE.equals(sourceParampos)||IContants.RESPATTRIBUTE.equals(sourceParampos))
                    {
                        sourceAttrName = (String) parm.get("sourceEcode");
                    }
                    if(IContants.REQATTRIBUTE.equals(sourceParampos)||IContants.RESPATTRIBUTE.equals(sourceParampos)){
                        targetAttrName =  (String) parm.get("targetEcode");
                    }
                
                
                
                if("0".equals(parm.get("sourceType"))){
                    value = parm.get("constantparam");
                }else{
                    //来源的参数位置 位于请求还是响应
                    String sourceParamType = (String)parm.get("sourceSerId");
                  //来源服务
                    String sourceSerId = (String)parm.get("sourceSerId");
                    //前一个的返回格式
                    String respformat =  resourceMap.get(sourceSerId).getReposneformat();
                    String reqformat =  resourceMap.get(sourceSerId).getReqformat();
                    
                    
                    String format = "1".equals(sourceParamType)?respformat:reqformat;
                    
                    
                    String   sourceContent =  (String)((resourceMap.get(sourceSerId)).getResponseBody());
                    value =  ProtConversion.getValueByString(sourceContent,
                        (String) parm.get("sourcePath"), sourceAttrName, transform(format),rePonseNameSpaces) ;    
                }
                    if(IContants.REQATTRIBUTE.equals(sourceParampos)||IContants.RESPATTRIBUTE.equals(sourceParampos))  {
                        //说明这个参数位于属性位置
                        targetAttrName =  (String)parm.get("targetEcode");
                        MappingBean mb=new MappingBean(sourPath, targetPath, sourceAttrName, targetAttrName, null);
                        mb.setValue(value);
                        list.add(mb);
                    }else if(IContants.REQBODY.equals(targetParampos)||
                        IContants.RESPONSEBODY.equals(targetParampos)||IContants.RESPONSEHEADER.equals(targetParampos)){
                        //说明是请求值
                        sourPath =(String)parm.get("sourcePath");
                        targetPath =(String) parm.get("targetEcodePath");
                        MappingBean mb=new MappingBean(sourPath, targetPath, sourceAttrName, targetAttrName, null);
                        mb.setValue(value);
                        list.add(mb);
                    }else if(IContants.REQUESTHEADER.equals(targetParampos)) {
                        data.getRequestHeader().put((String)parm.get("targetEcode"), value);
                    }else if(IContants.URLPARAM.equals(targetParampos)) {
                        data.getUrlParam().put((String)parm.get("targetEcode"), value);
                    }
            }
            result =  ProtConversion.putValue(list, transform(targetParamType), reqNameSpaces);
            data.setRequestBody(result);
        return data;
    }
    
    
    //两个格式进行转换
    public PROTOCOL_TYPE transform(String type) {
        if(IContants.JSON.equals(type)){
            return PROTOCOL_TYPE.JSON;
        }else if(IContants.XML.equals(type)){
            return  PROTOCOL_TYPE.XML;
        }else if(IContants.SOAP_1_1.equals(type)){
            return  PROTOCOL_TYPE.SOAP_1_1;
        }else if(IContants.CONTENT_TYPE_SOAP12.equals(type)){
            return  PROTOCOL_TYPE.SOAP_1_1;
        }else{
            return null;
        }
    }

   
    
}
