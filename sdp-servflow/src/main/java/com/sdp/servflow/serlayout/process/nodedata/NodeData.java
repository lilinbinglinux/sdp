package com.sdp.servflow.serlayout.process.nodedata;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.sdp.servflow.common.BoncException;
import com.sdp.servflow.pubandorder.node.analyze.factory.NodeFactoryImp;
import com.sdp.servflow.pubandorder.node.model.node.Node;
import com.sdp.servflow.pubandorder.node.model.node.NodeJoin;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

public class NodeData {
	
	private final String xml =
			"  <root>     "
			    	  +"    <node id=\"1\" title=\"开始\" nodestyle=\"1\" agreement=\"http\" x=\"134.98012\" y=\"207.99147\" width=\"70.00000\"  height=\"50.00000\" radius=\"25.00000\" nodetype=\"circle\"> "
			    	  +"      <targetlist>     "
			    	  +"        <target joinid=\"1-2\" jointype=\"r\" joindirection=\"l\" path=\"M160,208L204,208M204,208L204,233M204,233L204,233M204,233L204,259M204,259L248,259M248,259L242.34314575050763,264.65685424949237M248,259L242.34314575050763,253.34314575050763L242.34314575050763,264.65685424949237\" id=\"2\" />    "
			    	  +"      </targetlist>      "
			    	  +"      <inparameter format=\"json\" type=\"Object\">     "
			    	  +"        <fielditem id=\"1\" name=\"root\">     "
			    	  +"          <field id=\"2\" name=\"username\" fieldtype=\"String\" fieldlen=\"30\" location=\"请求体\"></field>      "
			    	  +"          <fieldlist id=\"14\" name=\"rulelist\">     "
			    	  +"            <field id=\"15\" name=\"rule\" fieldtype=\"String\" fieldlen=\"30\" location=\"请求体\">     "
			    	  +"              <attr name=\"code\" fieldtype=\"String\" fieldlen=\"30\" location=\"请求体\"/>     "
			    	  +"            </field>     "
			    	  +"          </fieldlist>      "
			    	  +"          <fieldArray id=\"16\" name=\"menu\" fieldtype=\"String\" fieldlen=\"30\" location=\"请求体\">     "
			    	  +"            <attr id=\"17\" name=\"src\" fieldtype=\"String\" fieldlen=\"30\" location=\"请求体\"/>     "
			    	  +"          </fieldArray>     "
			    	  +"        </fielditem>     "
			    	  +"      </inparameter>     "
			    	  +"    </node>      "
			    	  +"    <node id=\"2\" title=\"节点名称\" nodestyle=\"3\" agreement=\"http\" url=\"接口路径\" method=\"post\" x=\"248.00000\" y=\"224.00000\" width=\"130.00000\"  height=\"70.00000\" radius=\"25.00000\" nodetype=\"rectangle\">     "
			    	  +"      <targetlist>     "
			    	  +"        <target joinid=\"2-3\" jointype=\"r\" joindirection=\"l\" path=\"M378,259L434,259M434,259L434,267M434,267L434,267M434,267L434,274M434,274L490,274M490,274L484.34314575050763,279.65685424949237M490,274L484.34314575050763,268.34314575050763L484.34314575050763,279.65685424949237\" id=\"3\" />    "
			    	  +"      </targetlist>      "
			    	  +"      <inparameter format=\"json\" type=\"Object\">     "
			    	  +"        <fielditem id=\"a1\" name=\"root\">     "
			    	  +"          <field id=\"a2\" name=\"username\" fieldtype=\"String\" fieldlen=\"30\" location=\"请求体\"></field>      "
			    	  +"          <field id=\"a3\" name=\"password\" fieldtype=\"String\" fieldlen=\"30\" location=\"请求体\"></field>     "
			    	  +"        </fielditem>     "
			    	  +"      </inparameter>      "
			    	  +"      <outparameter format=\"json\" type=\"Object\">     "
			    	  +"        <field id=\"r1\" name=\"result\" fieldtype=\"String\" fieldlen=\"30\" location=\"响应体\"/>     "
			    	  +"      </outparameter>     "
			    	  +"    </node>      "
			    	  +"    <node id=\"3\" title=\"结束\" nodestyle=\"4\" x=\"730.98010\" y=\"289.99146\" width=\"70.00000\"  height=\"50.00000\" radius=\"25.00000\" nodetype=\"circle\">     "
			    	  +"      <targetlist></targetlist>      "
			    	  +"      <outparameter format=\"xml\" type=\"Object\">     "
			    	  +"        <fielditem id=\"1\" name=\"student\">     "
			    	  +"          <field id=\"b4\" name=\"sex\" fieldtype=\"String\" fieldlen=\"2\"/>     "
			    	  +"        </fielditem>     "
			    	  +"      </outparameter>     "
			    	  +"    </node>     "
			    	  +"  </root>    "
			    	 ;
			

	public String getJsonNode() throws Exception {

		InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
        SAXBuilder sb = new SAXBuilder();
        Document doc = sb.build(is);
        Element sourceRoot = doc.getRootElement();
        List<Element> list = sourceRoot.getChildren();
        List<Node> nodelist = new ArrayList<Node>();
        
        List<Object> objlist = new ArrayList<Object>();
        
        Map<String,Node> map = new HashMap<String,Node>();
      
        NodeFactoryImp factory = NodeFactoryImp.getOneNodeFactory();
        for(  Element element: list)
        {
            Node node = factory.createNode(element);
            objlist.add(node);
        }
        
        List<List<NodeJoin>> nodejoins = new ArrayList<List<NodeJoin>>();
		List<NodeJoin> joins = new ArrayList<NodeJoin>();
		
		for (Element element : list) {
			List<NodeJoin> nodejoinlist = new ArrayList<NodeJoin>();
			//nodejoinlist = factory.createNodeJoin(element);
			nodejoins.add(nodejoinlist);
		}
		
		for(List<NodeJoin> nodejoin:nodejoins){
			for(NodeJoin join:nodejoin){
				objlist.add(join);
			}
		}
		
		 JsonConfig jsonConfig = new JsonConfig();
         jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
         JSONArray json =JSONArray.fromObject(objlist, jsonConfig);
   	 	 System.out.println(json.toString());
		
		return json.toString();

	}
	
	public List<NodeJoin> getJsonNodeJoin() throws Exception{
		InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
		SAXBuilder sb = new SAXBuilder();
		Document doc = sb.build(is);
		Element sourceRoot = doc.getRootElement();
		@SuppressWarnings("unchecked")
		List<Element> list = sourceRoot.getChildren();

		NodeFactoryImp factory = NodeFactoryImp.getOneNodeFactory();
		
		List<List<NodeJoin>> nodejoins = new ArrayList<List<NodeJoin>>();
		List<NodeJoin> joins = new ArrayList<NodeJoin>();
		
		for (Element element : list) {
			List<NodeJoin> nodejoinlist = new ArrayList<NodeJoin>();
			//nodejoinlist = factory.createNodeJoin(element);
			nodejoins.add(nodejoinlist);
		}
		
		for(List<NodeJoin> nodejoin:nodejoins){
			for(NodeJoin join:nodejoin){
				joins.add(join);
			}
		}
		
		return joins;
	}

}
