package com.sdp.servflow.pubandorder.cache;

import java.io.Serializable;
import java.util.Map;

import com.sdp.servflow.pubandorder.node.model.field.XmlNode;
import com.sdp.servflow.pubandorder.node.model.node.EndNode;
import com.sdp.servflow.pubandorder.node.model.node.Node;
import com.sdp.servflow.pubandorder.node.model.node.StartNode;

/**
 * @Title: CacheModel.java
 * @Description: 缓存cache 的 实体entity
 * @Date：2018年3月23日 下午6:54:54
 * @author rpy
 */
public class NodesCacheModel implements Serializable{
	private static final long serialVersionUID = 1L;
	private String appId;  //当前创建服务id
	private String tenantId; //当前请求的tenantId
	private Map<String,XmlNode> xmlNodes; //解析完毕后的xmlNodes
	private EndNode endNode; //解析完毕后的endNode
	private StartNode startNode; //解析完毕后的startNode
	private Map<String, Node> nodes; //解析完毕后的各实体的Nodes
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public Map<String, XmlNode> getXmlNodes() {
		return xmlNodes;
	}
	public void setXmlNodes(Map<String, XmlNode> xmlNodes) {
		this.xmlNodes = xmlNodes;
	}
	public EndNode getEndNode() {
		return endNode;
	}
	public void setEndNode(EndNode endNode) {
		this.endNode = endNode;
	}
	public Map<String, Node> getNodes() {
		return nodes;
	}
	public void setNodes(Map<String, Node> nodes) {
		this.nodes = nodes;
	}
	public StartNode getStartNode() {
		return startNode;
	}
	public void setStartNode(StartNode startNode) {
		this.startNode = startNode;
	}
	public static NodesCacheModel getcacheModel (String key){
		EhCache cache = EhCache.getInstance();
		return (NodesCacheModel) cache.get(key);
	}
	public static void setCacheModel (String key,NodesCacheModel nodesCacheModel){
		EhCache cache = EhCache.getInstance();
		cache.put(key, nodesCacheModel);
	}
}
