package com.sdp.servflow.pubandorder.node.analyze.factory;
import static com.sdp.servflow.pubandorder.util.IContants.ARRAY;
import static com.sdp.servflow.pubandorder.util.IContants.FIELD;
import static com.sdp.servflow.pubandorder.util.IContants.LIST;
import static com.sdp.servflow.pubandorder.util.IContants.LISTLIST;
import static com.sdp.servflow.pubandorder.util.IContants.LISTOBJECT;
import static com.sdp.servflow.pubandorder.util.IContants.OBJECT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jdom.Element;

import com.sdp.frame.util.StringUtil;
import com.sdp.servflow.common.BoncException;
import com.sdp.servflow.pubandorder.node.analyze.abstractfactory.NodeFactory;
import com.sdp.servflow.pubandorder.node.model.field.Attribute;
import com.sdp.servflow.pubandorder.node.model.field.Field;
import com.sdp.servflow.pubandorder.node.model.field.XmlNode;
import com.sdp.servflow.pubandorder.node.model.node.EndNode;
import com.sdp.servflow.pubandorder.node.model.node.GroupNode;
import com.sdp.servflow.pubandorder.node.model.node.InterfaceNode;
import com.sdp.servflow.pubandorder.node.model.node.Node;
import com.sdp.servflow.pubandorder.node.model.node.NodeJoin;
import com.sdp.servflow.pubandorder.node.model.node.StartNode;
import com.sdp.servflow.pubandorder.node.model.parameter.Parameter;
import com.sdp.servflow.pubandorder.serve.format.FormatConversion;
import com.sdp.servflow.pubandorder.util.IContants;
/***
 * 
 * 解析xml到各个节点
 * 原型模式和工厂模式结合使用（不适合使用单例模式，会影响效率）
 *
 * @author 任壮
 * @version 2017年10月20日
 * @see NodeFactoryImp
 * @since
 */
public class NodeFactoryImp implements NodeFactory,Cloneable {
    
    private static NodeFactoryImp factory = new NodeFactoryImp();  
    
    /**
     * 
     * Description: 获取工厂的一个拷贝（利用Cloneable效率高点） 
     * 
     *@return NodeFactoryImp
     *
     * @see
     */
    public static NodeFactoryImp getOneNodeFactory() {  
        try {  
            return (NodeFactoryImp) factory.clone();  
        } catch (CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return null;  
    } 
      
    @Override
    public NodeFactoryImp clone()
        throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return (NodeFactoryImp)super.clone();
    }
    
    @Override
    public Node createNode(Element element) throws BoncException {
        Node node = null;
        try {
            
            String type =element.getAttributeValue("nodestyle");
            if(type.equalsIgnoreCase(Node.startNodeStyle)) {
                node = createStartNode(element);
            } else   if(type.equalsIgnoreCase(Node.lineNodeStyle)) {
                node = createConditionNode(element);
            } else   if(type.equalsIgnoreCase(Node.interfaceNodeStyle)) {
                node = createInterfaceNode(element);
            } else   if(type.equalsIgnoreCase(Node.endNodeStyle)) {
                node = createEndNode(element);
            } else   if(type.equalsIgnoreCase(Node.groupNodeStartStyle)||type.equalsIgnoreCase(Node.groupNodeEndStyle)) {
                node = createGroupNode(element);
            }  
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new BoncException("节点信息解析错误"+e.getMessage());
        }
        return node;
    }
    /*****
     * 创建聚合节点
     * @param element
     * @return
     */
    private Node createGroupNode(Element element) {
    	GroupNode node  = new   GroupNode();
        initNode(element,node);
        //实例化参数的信息（出参）
        Parameter param = createParamter(element.getChild("outparameter"),node);
        node.setParam(param);
        return node;
    }

	private List<String> createNodeJoin(Element element) throws BoncException {
        List<String> joins = new ArrayList<String>();
        List<Element> targetlist = element.getChild("targetlist").getChildren("target");
        for(Element target :targetlist)  {
            /*  NodeJoin nodejoin = new NodeJoin();
          nodejoin.setJoinId(target.getAttributeValue("joinid"));
            nodejoin.setJoinType(target.getAttributeValue("jointype"));
            nodejoin.setJoinDirection(target.getAttributeValue("joindirection"));
            nodejoin.setPath(target.getAttributeValue("path"));
            nodejoin.setStartNodeId(element.getAttributeValue("id"));
            nodejoin.setEndNodeId(target.getAttributeValue("id"));
            joins.add(nodejoin);*/
            joins.add(target.getAttributeValue("id"));
        }
        return joins;
    }

    /***
     *  fielditem   代表Object类型
        fieldlist   代表list类型（list中存的是对象）
        fieldArary  array [1,2,3]
        field       为末级节点
     path保存的形式为[节点名称:节点类型]   root:Object/username:field
             这里只保存field的节点信息（其中的field的path和在filed之下的attr的path是一样的）（fieldArary的处理形式和field差不多 认为是末级节点）
     * 初始化入参和出参的参数
     */
    private Parameter createParamter(Element element,Node node){
        //TODO 解析入参和出参
        Parameter param = new Parameter();
        param.setBelongNode(node);
        param.setFormat(element.getAttributeValue("format"));
        param.setType(element.getAttributeValue("type"));
        param.setParameterType(element.getName());
        //默认是utf-8
        param.setCharset(element.getAttributeValue("charset")!=null?element.getAttributeValue("charset"):IContants.DEFAULT_CHARSER); 
        List<Field> fildList = new ArrayList<Field>();
        getAllChildren(element,fildList,param);
        //TODO解析节点
        param.setFildList(fildList);
        //System.out.println(param);
        return param;
    }
    
    
    
    /***
     * 
     * Description: 所有的子节点保存为Field类型的对象
     * 
     *@param element
     *@param fildList void
     * @param paramter 
     *
     * @see
     */
    private void getAllChildren(Element source, List<Field> fildList, Parameter paramter) {
        List<Element> elementsList = new ArrayList<>();
        getAllElements( source, elementsList);
        //TODO 转变格式
        if(elementsList.size()>0)
        {
            for(Element element:elementsList){
                Field field = new Field();
                field.setId(element.getAttributeValue("id"));
                field.setParentId(element.getParentElement().getAttributeValue("id"));
                field.setLength(element.getAttributeValue("fieldlen"));
                field.setLocation(element.getAttributeValue("location"));
                field.setName(element.getAttributeValue("name"));
                field.setOrder(element.getAttributeValue("order"));
                field.setParamter(paramter);
                field.setMust(Boolean.valueOf(element.getAttributeValue("isMust")));
                field.setPath(getPath(element, new StringBuilder()).toString());
                field.setType(element.getAttributeValue("fieldtype"));
                field.setValue(element.getText().trim());
                field.setDesc(element.getAttributeValue("desc"));
                List<Attribute> attributeList =getAttribute(element,  field);
               // System.out.println("----field-"+field.getName()+attributeList.size());
                field.setAttribute(attributeList);
                fildList.add(field);
               // System.out.println("值------"+field);
            }
        }
    }
    
    /**
     * 
     * Description: 遍历末级节点的所有属性
     * 
     *@param element
     *@param path
     *@return List<Attribute>
     *
     * @see
     */
    private List<Attribute> getAttribute(Element el,Field parentField){
        List<Attribute> attributeList = new ArrayList<Attribute>();
        List<Element> targetlist = el.getChildren();
        if(targetlist!=null)
        {
        for(Element element:targetlist)
        {
            
            if(element.getName()==null || !element.getName().equals("attr"))
            {
                continue;
            }
            Attribute attr = new Attribute();
            attr.setId(element.getAttributeValue("id"));
            attr.setParentId(element.getParentElement().getAttributeValue("id"));
            attr.setLength(element.getAttributeValue("fieldlen"));
            attr.setLocation(element.getAttributeValue("location"));
            attr.setName(element.getAttributeValue("name"));
            attr.setBelongField(parentField);
            attr.setParamter(parentField.getParamter());
            attr.setPath(parentField.getPath());
            attr.setType(element.getAttributeValue("fieldtype"));
            attr.setValue(element.getText().trim());
           // attr.setNameSpace(Boolean.valueOf(element.getAttributeValue("isNamespace")));
            attr.setMust(Boolean.valueOf(element.getAttributeValue("isMust")));
            attr.setDesc(element.getAttributeValue("desc"));
            attributeList.add(attr);  
        }
        }
        return attributeList;
    }


    /**
     * 
     * Description: 获取从末级节点到当前节点的路径
     * fielditem   代表Object类型
        fieldlist   代表list类型（list中存的是对象）
        fieldArary  array [1,2,3]
        field       为末级节点
     * 
     *@param element
     *@param sb
     *@return StringBuilder
     *
     * @see
     */
    private StringBuilder getPath(Element element,StringBuilder sb)
    {
        //根节点下的元素（inParamter或outParamater元素）
        if(element!=null && !element.isRootElement() 
            
            /* && 
            element.getParentElement()!=null && 
            !element.getParentElement().isRootElement()*/)
        {
            
            String filedItem =element.getName();
            String type =null;
            switch (filedItem) {
                case "fielditem":
                    type =OBJECT;
                    break;
                case "fieldlist":
                    type =LIST;
                    break;
                case "fieldlistObject":
                	type =LISTOBJECT;
                	break;
                case "fieldlistList":
                	type =LISTLIST;
                	break;
                case "fieldArary":
                    type =ARRAY;
                    break;
                case "field":
                    type =FIELD;
                    break;
                default:
                    break;
            }
            
            if(type!=null)
            {
                sb.insert(0,"/");
                if(StringUtils.isNotBlank(element.getAttributeValue("order"))) {
                	sb.insert(0, element.getAttributeValue("order"));
                	sb.insert(0,FormatConversion.splitString);
                }
                sb.insert(0, type);
                sb.insert(0,FormatConversion.splitString);
                sb.insert(0, element.getAttributeValue("name"));
            }
             
            return getPath(element.getParentElement(), sb);
        }else{
            //此时代表遍历结束（到达root节点）
            if(sb.length()>0)
           sb =new StringBuilder( sb.substring(0, sb.length()-1));
            return sb;
        }
    }
    
    
    /**    
     * 
     * Description: 获取所有的子节点
     * 
     *@param element
     *@param fildList void
     *
     * @see
     */
    private void getAllElements(Element element, List<Element> fildList) {
        List<Element> targetlist = element.getChildren();
        //不包含属性
        if(targetlist!=null&&targetlist.size()>0)
        {
            for(Element child :targetlist)
            {
                if(child.getName()!=null && child.getName().equals("attr")){
                    //如果这个节点之下有属性的话，只需要添加一次父节点到fildList
                    // fildList.add(element);
                    continue;
                }else {
                    getAllElements(child, fildList);
                }
                fildList.add(child);
            }
        }else{
            //fildList.add(element);
        }
    }

    /**
     * 初始换节点信息（各种节点的公共信息抽取）
     */
    public <T extends Node>T initNode(Element element, Node node){
       // System.out.println(T instanceof Node);
        //1.初始化开始节点的信息
        //Node node = new Node();
          
        node.setNodeId(element.getAttributeValue("id"));
        node.setSerType(element.getAttributeValue("serType"));
        node.setComponentType(element.getAttributeValue("componentType"));
        node.setNodeName(element.getAttributeValue("title"));
        node.setNodeStyle(element.getAttributeValue("nodestyle"));
        node.setNodeType(element.getAttributeValue("nodetype"));
        String x=element.getAttributeValue("x");
        if(StringUtil.isNotEmpty(x))
        {
            node.setClientX(Float.parseFloat(x));
        }
        String y=element.getAttributeValue("y");
        if(StringUtil.isNotEmpty(y))
        {
            node.setClientY(Float.parseFloat(y));
        }
        String width=element.getAttributeValue("width");
        if(StringUtil.isNotEmpty(width))
        {
            node.setNodeWidth(Float.parseFloat(width));
        }
        String height=element.getAttributeValue("height");
        if(StringUtil.isNotEmpty(height))
        {
            node.setNodeHeight(Float.parseFloat(height));
        }
        String nodeRadius=element.getAttributeValue("radius");
        if(StringUtil.isNotEmpty(nodeRadius))
        {
            node.setNodeRadius(Float.parseFloat(nodeRadius));
        }
        List<String> joinList=null;
        try {
            joinList = createNodeJoin(element);
        }
        catch (BoncException e) {
            e.printStackTrace();
        }
        node.setTargetlist(joinList);
        
        
       return  (T)node;
    }
    
    
    /***
     * 
     * Description: 解析开始节点
     * 
     * xml:
      <node id="1" title="开始" nodetype="1" x="" y=""> 
       <targetlist> 
       <target id="2"/> 
        </targetlist>
       <inparameter format="xml"> 
          <fielditem id="1" name="student"> 
        <field id="4" name="mathematics" fieldtype="String"/> 
      </fielditem> 
    </inparameter> 
     </node> 
     * 
     * 
     *@param element
     *@return Node
     *
     * @see
     */
    private StartNode createStartNode(Element element){
        StartNode node  = new   StartNode();
        node.setAgreement(element.getAttributeValue("agreement"));
        initNode(element,node);
        //实例化参数的信息（入参）
        Parameter param = createParamter(element.getChild("inparameter"),node);
        node.setParam(param);
       // System.out.println("---开始节点-"+node);
        return node;
    }
    /***
     * 
     * Description: 解析之前的条件节点（对应现在的线节点）
     * 
     *@param element
     *@return Node
     *
     * @see
     */
    private NodeJoin createConditionNode(Element element) {
    	NodeJoin node = new NodeJoin();
        //1.初始化参数
        initNode(element,node);
        List<String> list = new ArrayList<String>();
        String endNodeId= element.getChild("targetlist").getChild("target").getAttributeValue("id");
        List<Element> targetlist = element.getChild("condition").getChild("source").getChildren("field");
        if(targetlist!=null && targetlist.size()>0) {
            for(Element target :targetlist) {
                String s = target.getText();
                list.add(s);
            }
            node.setFieldList(list);
        }
        //2.解析线上的部分信息
        node.setJoinId(element.getAttributeValue("joinid"));
        node.setJoinType(element.getAttributeValue("jointype"));
        node.setJoinDirection(element.getAttributeValue("joindirection"));
        node.setPath(element.getAttributeValue("path"));
        node.setStartNodeId(element.getAttributeValue("startNodeId"));
        node.setEndNodeId(endNodeId);
        //3.解析条件
        String code =element.getChild("condition").getChild("code").getText();
        node.setCode(code);
        
        
        
        //3.解析映射关系
        Map<String, String> map  = new HashMap<String, String>();
        Map<String, String> constantMap  = new HashMap<String, String>();
        List<Element> joinfieldList = element.getChild("joinparameter").getChildren("joinfield");
        for(Element target :joinfieldList) {
           String sourcfield= target.getChildText("sourcfield");
           String targetfield= target.getChildText("targetfield");
           String type= target.getAttributeValue("type");
           if(type!=null&&type.equals("constant")) {
               constantMap.put(targetfield, sourcfield);
           }else{
               map.put(targetfield, sourcfield);
           }
               
        }
        node.setJoinfield(map);
        node.setConstantField(constantMap);
     //   System.out.println("--条件节点--"+node);
        return node;
    }
    /***
     * 
     * Description: 解析接口节点
     * 
     *@param element
     *@return Node
     *
     * @see
     */
    private InterfaceNode createInterfaceNode(Element element) {
        InterfaceNode node = new InterfaceNode();
        node.setAgreement(element.getAttributeValue("agreement"));
        node.setMethod(element.getAttributeValue("method"));
        node.setUrl(element.getAttributeValue("url"));
        node.setPort(element.getAttributeValue("port"));
        node.setCallType(element.getAttributeValue("callType"));
        initNode(element,node);
        //实例化参数的信息（入参和出参）
        Parameter inparameter = createParamter(element.getChild("inparameter"),node);
        node.setInParameter(inparameter);
        Parameter outparameter = createParamter(element.getChild("outparameter"),node);
        node.setOutParameter(outparameter);
       // System.out.println("---接口节点-"+node);
        return node;
    }
    /***
     * 
     * Description: 解析结束节点
     * 
     *@param element
     *@return Node
     *
     * @see
     */
    private EndNode createEndNode(Element element) {
        EndNode node = new EndNode();
        initNode(element,node);
        //实例化参数的信息（入参）
        Parameter outparameter = createParamter(element.getChild("outparameter"),node);
        node.setParam(outparameter);
       // System.out.println("---结束节点-"+node);
        return node;
    }
    
    /**
     * 
     * Description: 传入esbXml将其解析为不同类型的对象
     * 
     *@param esbXml
     *@return Map<String,Node>
     * @throws Exception 
     *
     * @see
     */
    public  Map<String,XmlNode> getXmlNode(Collection<Node> nodes) throws Exception {
        Map<String,XmlNode> xmlsNodes = new HashMap<String,XmlNode>();
        for(Node node:nodes)
        {
            if(Node.startNodeStyle.equals(node.getNodeStyle())) {
                StartNode startNode = (StartNode)node;
                analyzeParam(xmlsNodes, startNode.getParam());
            }else if(Node.interfaceNodeStyle.equals(node.getNodeStyle())) {
                InterfaceNode interfaceNode =(InterfaceNode)node;
                analyzeParam(xmlsNodes, interfaceNode.getInParameter());
                analyzeParam(xmlsNodes, interfaceNode.getOutParameter());
                
            }else if(Node.endNodeStyle.equals(node.getNodeStyle())) {
                EndNode endNode =(EndNode)node;
                analyzeParam(xmlsNodes, endNode.getParam());
            }
        }
        return xmlsNodes;
    }
    /***
     * 
     * Description: 解析参数中的节点信息
     * 
     *@param xmlsNodes
     *@param param void
     *
     * @see
     */
    private void analyzeParam( Map<String,XmlNode> xmlsNodes,Parameter param)  {
        for( Field field : param.getFildList())
        {
            xmlsNodes.put(field.getId(), field);
            List<Attribute>  list = field.getAttribute();
            if(list!=null && list.size()>0) {
                for( Attribute attr : list) {
                    xmlsNodes.put(attr.getId(), attr);
                }
            }
            
        }
    }
}
