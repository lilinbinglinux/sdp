package com.sdp.servflow.pubandorder.node.xmlpackage;

import static com.sdp.frame.util.StringUtil.nullToOtherString;
import static com.sdp.frame.util.StringUtil.nullToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import com.sdp.servflow.common.BoncException;
import com.sdp.servflow.pubandorder.node.model.field.Attribute;
import com.sdp.servflow.pubandorder.node.model.field.Field;
import com.sdp.servflow.pubandorder.node.model.node.ConditionNode;
import com.sdp.servflow.pubandorder.node.model.node.EndNode;
import com.sdp.servflow.pubandorder.node.model.node.GroupNode;
import com.sdp.servflow.pubandorder.node.model.node.InterfaceNode;
import com.sdp.servflow.pubandorder.node.model.node.Node;
import com.sdp.servflow.pubandorder.node.model.node.NodeJoin;
import com.sdp.servflow.pubandorder.node.model.node.StartNode;
import com.sdp.servflow.pubandorder.node.model.parameter.Parameter;
import com.sdp.servflow.pubandorder.util.IContants;
import com.sdp.servflow.pubandorder.util.XmlUtil;

/**
 * 
 * builder的具体实现类
 *
 * @author 任壮
 * @version 2017年11月7日
 * @see ConcreteBuilder
 * @since
 */
public class ConcreteBuilder implements Builder{

    private Element root = new Element("root");
    
    /**
     * 
     * Description: 初始化node节点
     * 
     *@param node
     *@return Element
     *
     * @see
     */
   private  Element initElement(Node node){
       Element element = new Element("node");
       root.addContent(element);
       element.setAttribute("id", nullToString(node.getNodeId()));
       element.setAttribute("serType", nullToString(node.getSerType()));
       element.setAttribute("componentType", nullToString(node.getComponentType()));
       element.setAttribute("title", nullToString(node.getNodeName()));
       element.setAttribute("nodetype", nullToString(node.getNodeType()));
       element.setAttribute("nodestyle", nullToString(node.getNodeStyle()));
       element.setAttribute("x", nullToString(node.getClientX()));
       element.setAttribute("y", nullToString(node.getClientY()));
       element.setAttribute("width", nullToString(node.getNodeWidth()));
       element.setAttribute("height", nullToString(node.getNodeHeight()));
       element.setAttribute("radius", nullToString(node.getNodeRadius()));
       Element targetlist = new Element("targetlist");
       if(node.getTargetlist()!=null) {
           for(String targetId:node.getTargetlist()) {
               Element target = initNodeJoin(targetId);
               targetlist.addContent(target);
           }
       }
       element.addContent(targetlist);
       return element;
   }
   /***
    * 
    * Description: 初始化指向下一个节点的信息
    * 
    *@param element
    *@return
    *@throws BoncException Element
    *
    * @see
    */
   private Element initNodeJoin(String lineNodeId)  {
       Element target = new Element("target");
    /*   target.setAttribute("joinid", nullToString(nodeJoin.getJoinId()));
       target.setAttribute("jointype", nullToString(nodeJoin.getJoinType()));
       target.setAttribute("joindirection", nullToString(nodeJoin.getJoinDirection()));
       target.setAttribute("path", nullToString(nodeJoin.getPath()));
       target.setAttribute("id", nullToString(nodeJoin.getEndNodeId()));*/
       target.setAttribute("id", lineNodeId);
       return target;
   }
   
    /**
     * 
     * Description: 初始化参数信息
     * 
     *@param parameter
     *@return Element
     *
     * @see
     */
   private  Element initParamter(Parameter parameter){
       String name = parameter.getParameterType().equals(Parameter.outparameter)?"outparameter":"inparameter";
       String charset = null == parameter.getCharset()?IContants.DEFAULT_CHARSER:parameter.getCharset();
       Element element = new Element(name);
       element.setAttribute("format", parameter.getFormat());
       element.setAttribute("charset", charset);
       element.setAttribute("type", parameter.getType());
       List<Field> fieldList = parameter.getFildList();
       //TODO 将list拼装到xml中(父节点值为null时或者为空字符时都认为是根节点)
       element.addContent(makeUpField(fieldList, null));
       element.addContent(makeUpField(fieldList, ""));
       return element;
   }
   /**
    * 
    * Description: 根据一系列field信息拼装参数节点信息
    * 
    *@param fildList
    *@return List<Field>
    *
    * @see
    */
   private List<Element> makeUpField(List<Field> fieldList,String parentId){
       if(fieldList==null || fieldList.size()==0)
           return null;
       List<Element> elementList =new ArrayList<Element>();
       
       for(Field field: fieldList) {
           if(parentId==field.getParentId()||
                   (parentId !=null && parentId.equals(field.getParentId()) )
               ) {
               Element child = field2Element(field);
               elementList.add(child);
               List<Element> childList = makeUpField(fieldList, field.getId());
               child.addContent(childList);
           }
       }
       
       return elementList;
       
   }
   /**
    * 
    * Description: 将每一个field对象转为xml中的一个节点
    * 
    *@param field
    *@return Element
    *
    * @see
    */
   private Element field2Element(Field field) {
       Element element = new Element(changeTypeName(field.getType()));
       element.setAttribute("id", nullToString(field.getId()));
       element.setAttribute("name", nullToString(field.getName()));
       element.setAttribute("fieldtype", nullToString(field.getType()));
       element.setAttribute("fieldlen", nullToString(field.getLength()));
       element.setAttribute("location", nullToString(field.getLocation()));
       element.setAttribute("order", nullToString(field.getOrder()));
       element.setAttribute("isMust", nullToOtherString(field.isMust(), "false"));
       element.setText(nullToString(field.getValue()));
       element.setAttribute("desc", nullToString(field.getDesc()));
       //标签名为attr的属性值
       List<Attribute> attrList = field.getAttribute();
       
       if(attrList!=null && attrList.size()>0) {
           List<Element> attrElements = new ArrayList<Element>();
           for(Attribute attr:attrList) {
               Element el=  attribute2Elment(attr);
               if(el!=null) {
                   attrElements.add(el); 
               }
           }
           if(attrElements.size()>0)   
               element.addContent(attrElements);
       }
       return element;
   }
   
   /***
    * 
    * Description: 将属性值转为一个xml节点
    * 
    *@param attribute
    *@return List<Element>
    *
    * @see
    */
    private Element attribute2Elment(Attribute attribute) {
        Element element = new Element("attr");
        element.setAttribute("id", nullToString(attribute.getId()));
        element.setAttribute("name", nullToString(attribute.getName()));
        element.setAttribute("fieldtype", nullToString(attribute.getType()));
        element.setAttribute("fieldlen", nullToString(attribute.getLength()));
        element.setAttribute("location",nullToString(attribute.getLocation()));
        element.setAttribute("isMust", nullToOtherString(attribute.isMust(), "false"));
        element.setText(nullToString(attribute.getValue()));
        element.setAttribute("desc", nullToString(attribute.getDesc()));
       // element.setAttribute("isNamespace", nullToString(attribute.isNameSpace()));
    return element;
}
    
    /**
     * 
     * Description: 创建条件节点中的condition节点信息
     *
     * 
     *@param node
     *@return Element
     *
     * @see
     */
    private Element  makeUpCondition(ConditionNode node) {
        //1.追加condition节点
        Element condition = new Element("condition");
        Element code = new Element("code");
        // <![CDATA[name.length<11]]>   xml可能会解析不了<特殊字符(需要验证)  jdom会在内部进行特殊字符的替换  解析的时候重新替换特殊字符
        code.setText(node.getCode());
        condition.addContent(code);
        Element source = new Element("source");
        if(node.getFieldList() != null && node.getFieldList().size()>0 ) {
            for(String sourceField :node.getFieldList()){
                Element field = new Element("field");
                field.setText(sourceField);
                source.addContent(field);
            }
        }
        condition.addContent(source);
        return condition;
    }
    
    /**
     * 
     * Description: 组装条件节点中的映射关系(joinparameter节点信息)
     * 
     *@param node
     *@return Element
     *
     * @see
     */
    private Element makeUpJoin(ConditionNode node) {
        //2.追加joinparameter节点
        Element joinparameter = new Element("joinparameter");
        //参数映射为参数（两个id进行映射）
        Map<String, String> joinfields = node.getJoinfield();
        if(joinfields != null && joinfields.size()>0 ) {
            for(Map.Entry<String, String> entry :joinfields.entrySet()){
                Element field = makeUpJoinfield(entry.getKey(), entry.getValue());
                joinparameter.addContent(field);
            }
        }
        //参数映射为常量
        Map<String, String> constantFields = node.getConstantField();
        if(constantFields != null && constantFields.size()>0 ) {
            for(Map.Entry<String, String> entry :constantFields.entrySet()){
                Element field = makeUpJoinfield(entry.getKey(), entry.getValue());
                field.setAttribute("type","constant");
                joinparameter.addContent(field);
            }
        }
        return joinparameter;
    }
    
    
    
    /***
     * 构建joinfield的数据  （以key,value形式存在）
     */
    private Element makeUpJoinfield(String key, String value) {

        Element joinfield = new Element("joinfield");
        
            Element sourcfield = new Element("sourcfield");
            sourcfield.setText(nullToString(value));
            joinfield.addContent(sourcfield);
            
            Element targetfield = new Element("targetfield");
            targetfield.setText(nullToString(key));
            joinfield.addContent(targetfield);
        
        return joinfield;
    
    }
    
    
    @Override
    public void buildStartNode(StartNode node) {
        Element startElement  =  initElement(node);
        startElement.setAttribute("agreement", nullToString(node.getAgreement()));
        Element paramElement   = initParamter(node.getParam());
        startElement.addContent(paramElement);
    }

    
  
    
    //初始化线的节点，暂时去除
    //@Override
    private void buildConditionNode(ConditionNode node) {
        Element conditionElement  =  initElement(node);
        Element condition  =  makeUpCondition(node);
        conditionElement.addContent(condition);
        Element join  =  makeUpJoin(node);
        conditionElement.addContent(join);
    }

    @Override
    public void buildInterfaceNode(InterfaceNode node) {
        Element interfaceNode  =  initElement(node);
        interfaceNode.setAttribute("agreement", nullToString(node.getAgreement()));
        interfaceNode.setAttribute("method", nullToString(node.getMethod()));
        interfaceNode.setAttribute("url", nullToString(node.getUrl()));
        interfaceNode.setAttribute("port", nullToString(node.getPort()));
        interfaceNode.setAttribute("callType", nullToOtherString(node.getCallType(), "0"));
        interfaceNode.addContent(initParamter(node.getInParameter()));
        interfaceNode.addContent(initParamter(node.getOutParameter()));
    }

    @Override
    public void buildEndNode(EndNode node) {
        //结束节点暂时处理和开始节点一致
        buildStartNode(node);
    
        
    }

    @Override
    public void buildNodeJoin(NodeJoin node) {

        Element nodeJoin  =  initElement(node);
        //之前在线上的一些属性
        nodeJoin.setAttribute("joinid", nullToString(node.getJoinId()));
        nodeJoin.setAttribute("jointype", nullToString(node.getJoinType()));
        nodeJoin.setAttribute("joindirection", nullToString(node.getJoinDirection()));
        nodeJoin.setAttribute("path", nullToString(node.getPath()));
        nodeJoin.setAttribute("startNodeId", nullToString(node.getStartNodeId()));
        
        
        Element condition  =  makeUpCondition(node);
        nodeJoin.addContent(condition);
        Element join  =  makeUpJoin(node);
        nodeJoin.addContent(join);
    
        
    }

    @Override
    public String getResult() throws Exception {
        return XmlUtil.element2String(root);
    }

    
    /**
     * 
     * Description: 将type和fileItem的名称做了一次映射
     * 
     *@param type
     *@return String
     *
     * @see
     */
    private String changeTypeName(String type) {
        String filedItem = null;
        
        if("Object".equalsIgnoreCase(type))
            filedItem ="fielditem";
        else if("list".equalsIgnoreCase(type))
            filedItem ="fieldlist";
        else if(IContants.ARRAY.equalsIgnoreCase(type))
            filedItem ="fieldArary";
        else if(IContants.LISTOBJECT.equalsIgnoreCase(type))
        	filedItem ="fieldlistObject";
        else if(IContants.LISTLIST.equalsIgnoreCase(type))
        	filedItem ="fieldlistList";
        else
            filedItem ="field";
        return filedItem;
    }
	@Override
	public void buildGroupNode(GroupNode node) {
        Element groupElement  =  initElement(node);
        Element paramElement   = initParamter(node.getParam());
        groupElement.addContent(paramElement);
    }
}
