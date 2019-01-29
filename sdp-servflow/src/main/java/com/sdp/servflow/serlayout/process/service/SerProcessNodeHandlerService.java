package com.sdp.servflow.serlayout.process.service;

import java.util.List;
import java.util.Map;

import com.sdp.servflow.pubandorder.node.model.node.ConditionNode;
import com.sdp.servflow.pubandorder.node.model.node.EndNode;
import com.sdp.servflow.pubandorder.node.model.node.GroupNode;
import com.sdp.servflow.pubandorder.node.model.node.InterfaceNode;
import com.sdp.servflow.pubandorder.node.model.node.Node;
import com.sdp.servflow.pubandorder.node.model.node.NodeJoin;
import com.sdp.servflow.pubandorder.node.model.node.StartNode;
import com.sdp.servflow.pubandorder.servicebasic.model.ServiceMainBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 编排流程图节点json转换对象
 * @author zy
 *
 */
public interface SerProcessNodeHandlerService {
	/**
	 * 开始节点适配
	 * @param serNodeArray
	 * @param nodeJoinList
	 * @return
	 */
	public StartNode startNodeHandler(String serNodeArray,JSONArray nodeJoinList);
	
	/**
	 * 聚合节点适配
	 * @param serNodeArray
	 * @param nodeJoinList
	 * @return
	 */
	public GroupNode groupNodeHandler(String string, JSONArray jsonserNodeArray);
	
	/**
	 * 接口节点适配
	 * @param interfaceNodeStr
	 * @param nodeJoinList
	 * @return
	 */
	public InterfaceNode interfaceNodeHandler(String interfaceNodeStr, JSONArray nodeJoinList);
	
	/**
	 * 结束节点适配
	 * @param string
	 * @param nodeJoinList
	 * @return
	 */
	public EndNode endNodeHandler(String string, JSONArray nodeJoinList);
	
	/**
	 * 条件节点适配
	 * @param string
	 * @param nodeJoinList
	 * @return
	 */
	public ConditionNode conditionNodeHandler(String string, JSONArray nodeJoinList);
	
	/**
	 * 线节点适配
	 * @param string
	 * @param nodeJoinList
	 * @return
	 */
	public NodeJoin joinNodeHandler(String string, JSONArray nodeJoinList);
	
	/**
	 * 查询前一个版本的ServiceMainBean
	 * @param nodeobj
	 * @return
	 */
	public List<ServiceMainBean> old_serviceMainBeanHandler(String serId);
	
	/**
	 * ServiceMainBean适配
	 * @param nodeobj
	 * @return
	 */
	public ServiceMainBean serviceMainBeanHandler(JSONObject nodeobj,String updateFlag,String versionflag,String typeStr);
	
	/**
	 * ServiceVersionBean适配
	 * @param nodeobj
	 * @param flowxml
	 * @param bean
	 */
	public void serviceVersionBeanHandler(JSONObject nodeobj,String flowxml,ServiceMainBean bean,String updateFlag);
	
	/**
	 * 对象转json串
	 * @param nodelist
	 * @param nodeJoinlist
	 * @param flowchartId
	 * @param serVerId
	 * @return
	 */
	public Map<String,Object> objTojson(List<Node> nodelist,String flowchartId,String serVerId);
	
	

	

}
