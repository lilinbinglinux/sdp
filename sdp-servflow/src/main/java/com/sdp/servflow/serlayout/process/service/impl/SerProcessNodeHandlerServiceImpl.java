package com.sdp.servflow.serlayout.process.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.util.IdUtil;
import com.sdp.servflow.pubandorder.node.analyze.factory.NodeFactoryImp;
import com.sdp.servflow.pubandorder.node.model.field.Attribute;
import com.sdp.servflow.pubandorder.node.model.field.Field;
import com.sdp.servflow.pubandorder.node.model.field.XmlNode;
import com.sdp.servflow.pubandorder.node.model.node.ConditionNode;
import com.sdp.servflow.pubandorder.node.model.node.EndNode;
import com.sdp.servflow.pubandorder.node.model.node.GroupNode;
import com.sdp.servflow.pubandorder.node.model.node.InterfaceNode;
import com.sdp.servflow.pubandorder.node.model.node.Node;
import com.sdp.servflow.pubandorder.node.model.node.NodeJoin;
import com.sdp.servflow.pubandorder.node.model.node.StartNode;
import com.sdp.servflow.pubandorder.node.model.parameter.Parameter;
import com.sdp.servflow.pubandorder.servicebasic.model.ServiceMainBean;
import com.sdp.servflow.pubandorder.servicebasic.model.ServiceVersionBean;
import com.sdp.servflow.pubandorder.servicebasic.service.ServiceMainService;
import com.sdp.servflow.pubandorder.servicebasic.service.ServiceVersionService;
import com.sdp.servflow.serlayout.datahandler.SerFlowDataConstant;
import com.sdp.servflow.serlayout.datahandler.SerNormalData;
import com.sdp.servflow.serlayout.process.service.SerProcessNodeHandlerService;
import com.sdp.servflow.serlayout.process.util.JSONObjectNUllUtil;
import com.sdp.servflow.serlayout.process.util.SerProcessConstantFlag;
import com.sdp.servflow.serlayout.process.util.VersionCreateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 编排流程图节点json转换对象
 * @author zy
 *
 */
@Service
public class SerProcessNodeHandlerServiceImpl implements SerProcessNodeHandlerService{
	Logger logger = Logger.getLogger(SerProcessNodeHandlerServiceImpl.class);
	
	@Autowired
	private ServiceMainService serviceMainService;
	
	@Autowired
	private ServiceVersionService serviceVersionService;
	
	@Autowired
	private SerNormalData serNormalData;
	
	/**
	 * 开始节点
	 */
	@Override
	public StartNode startNodeHandler(String startNodeStr, JSONArray nodeJoinList) {
		JSONObject startnodeObj= JSONObject.fromObject(startNodeStr);
		
		StartNode startnode = new StartNode();
		startnode.setAgreement(startnodeObj.getString("agreement"));
		startnode = (StartNode) nodeHandler(startnodeObj,startnode,nodeJoinList);
		
		//设置入参
		Parameter startparam = new Parameter();
		startparam.setFormat(startnodeObj.getString("reqformat"));
		startparam.setType(startnodeObj.getString("reqdatatype"));
		startparam.setParameterType("inparameter");
		if(startnodeObj.has("inCharset")) {
			startparam.setCharset(startnodeObj.getString("inCharset"));
		}
		
		
		if(startnodeObj.get("inparameter") != null){
			if(StringUtils.isNotBlank(startnodeObj.getString("inparameter"))){
				JSONObject params = (JSONObject) startnodeObj.get("inparameter");
				JSONArray paramJsonArray = JSONArray.fromObject(params.get("list"));
				startparam = paramHandler(paramJsonArray,startparam);
			}
		}
		
		startnode.setParam(startparam);
		
		return startnode;
	}
	
	/**
	 * 聚合节点
	 */
	@Override
	public GroupNode groupNodeHandler(String groupNodeStr, JSONArray nodeJoinList) {
		JSONObject groupnodeStrObj= JSONObject.fromObject(groupNodeStr);
		GroupNode groupnode = new GroupNode();
		groupnode = (GroupNode)nodeHandler(groupnodeStrObj, groupnode, nodeJoinList);
		
		//设置参数
		Parameter groupnodeparam = new Parameter();
		groupnodeparam.setFormat(groupnodeStrObj.getString("respformat"));
		groupnodeparam.setType(groupnodeStrObj.getString("returndatatype"));
		groupnodeparam.setParameterType("outparameter");
		if(groupnodeStrObj.has("outCharset")) {
			groupnodeparam.setCharset(groupnodeStrObj.getString("outCharset"));
		}
		
		if(groupnodeStrObj.get("allparameter") != null){
			if(StringUtils.isNotBlank(groupnodeStrObj.getString("allparameter"))){
				JSONObject params = (JSONObject) groupnodeStrObj.get("allparameter");
				JSONArray paramJsonArray = JSONArray.fromObject(params.get("list"));
				groupnodeparam = paramHandler(paramJsonArray,groupnodeparam);
			}
		}
		groupnode.setParam(groupnodeparam);
		
		return groupnode;
	}

	
	/**
	 * 接口节点
	 */
	@Override
	public InterfaceNode interfaceNodeHandler(String interfaceNodeStr, JSONArray nodeJoinList) {
		JSONObject interNodeObj= JSONObject.fromObject(interfaceNodeStr);
		InterfaceNode interfaceNode = new InterfaceNode();
		
		interfaceNode = (InterfaceNode) nodeHandler(interNodeObj,interfaceNode,nodeJoinList);
		interfaceNode.setUrl(interNodeObj.getString("url"));
		interfaceNode.setPort(interNodeObj.getString("port"));
		interfaceNode.setAgreement(interNodeObj.getString("agreement"));
		interfaceNode.setMethod(interNodeObj.getString("method"));
		if(interNodeObj.has("callType")) {
			interfaceNode.setCallType(interNodeObj.getString("callType"));
		}
		
		
		//设置入参
		Parameter inparam = new Parameter();
		inparam.setFormat(interNodeObj.getString("reqformat"));
		inparam.setType(interNodeObj.getString("reqdatatype"));
		inparam.setParameterType("inparameter");
		if(interNodeObj.has("inCharset")) {
			inparam.setCharset(interNodeObj.getString("inCharset"));
		}
		
		
		if(interNodeObj.get("inparameter") != null){
			if(StringUtils.isNoneBlank(interNodeObj.getString("inparameter"))){
				JSONObject params = (JSONObject) interNodeObj.get("inparameter");
				JSONArray paramJsonArray = JSONArray.fromObject(params.get("list"));
				inparam = paramHandler(paramJsonArray,inparam);
			}
		}
		
		interfaceNode.setInParameter(inparam);
		
		//设置出参
		Parameter outparam = new Parameter();
		outparam.setFormat(interNodeObj.getString("respformat"));
		outparam.setType(interNodeObj.getString("returndatatype"));
		outparam.setParameterType("outparameter");
		if(interNodeObj.has("outCharset")) {
			outparam.setCharset(interNodeObj.getString("outCharset"));
		}
		
		
		if(interNodeObj.get("outparameter") != null){
			if(StringUtils.isNoneBlank(interNodeObj.getString("outparameter"))){
				JSONObject params = (JSONObject) interNodeObj.get("outparameter");
				JSONArray paramJsonArray = JSONArray.fromObject(params.get("list"));
				outparam = paramHandler(paramJsonArray,outparam);
			}
		}
		
		interfaceNode.setOutParameter(outparam);
		
		return interfaceNode;
		
	}
	
	
	/**
	 * 结束节点
	 */
	@Override
	public EndNode endNodeHandler(String endNodeStr, JSONArray nodeJoinList) {
		JSONObject endnodeObj= JSONObject.fromObject(endNodeStr);
		
		EndNode endnode = new EndNode();
		endnode.setAgreement(endnodeObj.getString("agreement"));
		endnode = (EndNode) nodeHandler(endnodeObj,endnode,nodeJoinList);
		
		//设置出参
		Parameter endparam = new Parameter();
		endparam.setFormat(endnodeObj.getString("respformat"));
		endparam.setType(endnodeObj.getString("returndatatype"));
		endparam.setParameterType("outparameter");
		
		if(endnodeObj.has("outCharset")) {
			endparam.setCharset(endnodeObj.getString("outCharset"));
		}
		
		if(endnodeObj.get("outparameter") != null){
			if(StringUtils.isNoneBlank(endnodeObj.getString("outparameter"))){
				JSONObject params = (JSONObject) endnodeObj.get("outparameter");
				JSONArray paramJsonArray = JSONArray.fromObject(params.get("list"));
				endparam = paramHandler(paramJsonArray,endparam);
			}
		}
		
		endnode.setParam(endparam);
		
		return endnode;
	}
	
	
	/**
	 * 条件节点
	 */
	@Override
	public ConditionNode conditionNodeHandler(String conditionNodeStr, JSONArray nodeJoinList) {
		JSONObject conditionNodeObj= JSONObject.fromObject(conditionNodeStr);
		ConditionNode conditionNode = new ConditionNode();
		
		conditionNode = (ConditionNode) nodeHandler(conditionNodeObj,conditionNode,nodeJoinList);
		Map<String, String> joinfield = new HashMap<String,String>();
		Map<String, String> constantField = new HashMap<String,String>();
		List<String> fieldList = new ArrayList<String>();
		String code = "";
		
		if(conditionNodeObj.has("relationSet")&&StringUtils.isNotBlank(conditionNodeObj.getString("relationSet"))){
			JSONArray relationSetArray = JSONArray.fromObject(conditionNodeObj.get("relationSet"));
			for(int i=0;i<relationSetArray.size();i++){
				JSONObject relationSetObj = (JSONObject) relationSetArray.get(i);
				joinfield.put(relationSetObj.getString("nextparamId"), relationSetObj.getString("preparamId"));
				if(relationSetObj.has("constantparam")&&StringUtils.isNotBlank(relationSetObj.getString("constantparam"))) {
					constantField.put(relationSetObj.getString("nextparamId"), relationSetObj.getString("constantparam"));
					conditionNode.setConstantField(constantField);
				}
			}
		}
		
		if(conditionNodeObj.has("conditionObj")&&StringUtils.isNotBlank(conditionNodeObj.getString("conditionObj"))){
			JSONArray conditionArray = JSONArray.fromObject(conditionNodeObj.get("conditionObj"));
			for(int i=0;i<conditionArray.size();i++){
				JSONObject conditionObj = (JSONObject) conditionArray.get(i);
				JSONArray preparamIdSet = JSONArray.fromObject(conditionObj.get("preparamIdSet"));
				for(int j=0;j<preparamIdSet.size();j++){
					fieldList.add(preparamIdSet.get(i).toString());
				}
				code = conditionObj.getString("condition");
			}
		}
		
		conditionNode.setFieldList(fieldList);
		conditionNode.setCode(code);
		conditionNode.setJoinfield(joinfield);
		
		return conditionNode;
	}
	
	/**
	 * 线节点
	 */
	@Override
	public NodeJoin joinNodeHandler(String joinNodeStr, JSONArray nodeJoinList) {

		JSONObject joinNodeObj= JSONObject.fromObject(joinNodeStr);
		NodeJoin joinNode = new NodeJoin();
		joinNode = (NodeJoin) nodeHandler(joinNodeObj,joinNode,nodeJoinList);
		joinNode.setStartNodeId(joinNodeObj.getString("startNodeId"));
		joinNode.setEndNodeId(joinNodeObj.getString("endNodeId"));
		joinNode.setJoinType(joinNodeObj.getString("joinType"));
		joinNode.setJoinDirection(joinNodeObj.getString("joinDirection"));
		joinNode.setPath(joinNodeObj.getString("path"));
		
		Map<String, String> joinfield = new HashMap<String,String>();
		Map<String, String> constantField = new HashMap<String,String>();
		List<String> fieldList = new ArrayList<String>();
		String code = "";
		
		if(joinNodeObj.has("relationSet")&&StringUtils.isNotBlank(joinNodeObj.getString("relationSet"))){
			JSONArray relationSetArray = JSONArray.fromObject(joinNodeObj.get("relationSet"));
			for(int i=0;i<relationSetArray.size();i++){
				JSONObject relationSetObj = (JSONObject) relationSetArray.get(i);
				joinfield.put(relationSetObj.getString("nextparamId"), relationSetObj.getString("preparamId"));
				if(relationSetObj.has("constantparam")&&StringUtils.isNotBlank(relationSetObj.getString("constantparam"))) {
					constantField.put(relationSetObj.getString("nextparamId"), relationSetObj.getString("constantparam"));
					joinNode.setConstantField(constantField);
				}
			}
		}
		
		if(joinNodeObj.has("conditionObj")&&StringUtils.isNotBlank(joinNodeObj.getString("conditionObj"))){
			JSONArray conditionArray = JSONArray.fromObject(joinNodeObj.get("conditionObj"));
			for(int i=0;i<conditionArray.size();i++){
				JSONObject conditionObj = (JSONObject) conditionArray.get(i);
				JSONArray preparamIdSet = JSONArray.fromObject(conditionObj.get("preparamIdSet"));
				for(int j=0;j<preparamIdSet.size();j++){
					fieldList.add(preparamIdSet.get(i).toString());
				}
				code = conditionObj.getString("condition");
			}
		}
		
		joinNode.setFieldList(fieldList);
		joinNode.setCode(code);
		joinNode.setJoinfield(joinfield);
		
		return joinNode;
		
	}
	
	
	
	
	
	@Override
	public List<ServiceMainBean> old_serviceMainBeanHandler(String serId){
		Map<String, String> map = new HashMap<String,String>();
		map.put("serId", serId);
		map.put("tenantId",CurrentUserUtils.getInstance().getUser().getTenantId());
		map.put("loginId", CurrentUserUtils.getInstance().getUser().getLoginId());
		
		return serviceMainService.getAllByCondition(map);
	}
	
	@Override
	public ServiceMainBean serviceMainBeanHandler(JSONObject nodeobj,String updateFlag,String versionflag,String typeStr) {
		ServiceMainBean serviceMainBean = new ServiceMainBean();
		
		//设置ser_main
		serviceMainBean.setSerId(nodeobj.getString("flowChartId"));
		serviceMainBean.setSerName(nodeobj.getString("serName"));
		serviceMainBean.setSerType(nodeobj.getString("serviceType"));
		String strcode = nodeobj.getString("flowChartId").toString();
		serviceMainBean.setSerCode(strcode.substring(strcode.length()-6, strcode.length()));
		serviceMainBean.setSerVersion("1.0.0");
		serviceMainBean.setSerResume(nodeobj.getString("serdesc"));
		//typeStr = serNormalData.serTypeHandler(typeStr);
		
		System.out.println("ServiceMainBean update/insert-------------------------"+typeStr);
//		if (typeStr.equals(SerFlowDataConstant.synchronously_id)) {
//			//同步
			serviceMainBean.setSynchFlag("0");
//		} else if(typeStr.equals(SerFlowDataConstant.asynchronously_id)){
//			//异步
//			serviceMainBean.setSynchFlag("1");
//		}
		serviceMainBean.setStopFlag("0");
		serviceMainBean.setDelFlag("0");
		serviceMainBean.setLoginId(CurrentUserUtils.getInstance().getUser().getLoginId());
		serviceMainBean.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
		
		
		if(updateFlag.equals(SerProcessConstantFlag.ADDUPDATE_ADD)){
			//添加
			serviceMainBean.setCreatTime(new Date());
			serviceMainService.insert(serviceMainBean);
		}else if(updateFlag.equals(SerProcessConstantFlag.ADDUPDATE_UPDATE)){
			//更新
			ServiceMainBean bean = serviceMainService.getByPrimaryKey(nodeobj.getString("flowChartId"));
			
			String version = bean.getSerVersion();
			//是否更新版本号
			if(versionflag.equals(SerProcessConstantFlag.VERSIONFLAG_NEWVERSION)){
				version = VersionCreateUtil.genNewVersion(bean.getSerVersion());
			}
			
			serviceMainBean.setSerVersion(version);
			serviceMainService.update(serviceMainBean);
			
		}
		
		return serviceMainBean;
	}


	@Override
	public void serviceVersionBeanHandler(JSONObject nodeobj, String flowxml, ServiceMainBean serviceMainBean,String updateFlag) {
		
		nodeobj = JSONObjectNUllUtil.clearNUll(nodeobj);
		ServiceVersionBean serviceVersionBean = new ServiceVersionBean();
		serviceVersionBean.setSerVersion(serviceMainBean.getSerVersion());
		serviceVersionBean.setSerId(serviceMainBean.getSerId());
		serviceVersionBean.setSerAgreement(nodeobj.getString("agreement"));
		serviceVersionBean.setSerRequest(nodeobj.getString("reqformat"));
		serviceVersionBean.setSerResponse(nodeobj.getString("respformat"));
		
		System.out.println(nodeobj.get("agreement").getClass());
		System.out.println(nodeobj.get("agreement"));
		serviceVersionBean.setSerRestType("post");
		//serviceVersionBean.setSerPoint("8080");
		serviceVersionBean.setSerFlow(flowxml);
		serviceVersionBean.setCreatTime(new Date());
		serviceVersionBean.setDelFlag("0");
		serviceVersionBean.setStopFlag("0");
		serviceVersionBean.setLoginId(CurrentUserUtils.getInstance().getUser().getLoginId());
		serviceVersionBean.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
		
		if(updateFlag.equals(SerProcessConstantFlag.ADDUPDATE_ADD)){
			//添加
			serviceVersionBean.setSerVerId(IdUtil.createId());
			serviceVersionBean.setPushState(SerProcessConstantFlag.PUSHFLAG_ADD);
			serviceVersionService.insert(serviceVersionBean);
		}else if(updateFlag.equals(SerProcessConstantFlag.ADDUPDATE_UPDATE)){
			//更新
			ServiceVersionBean bean = serviceVersionService.getByPrimaryKey(nodeobj.getString("serVerId"));
			if(bean != null){
				//服务的版本不变化
				serviceVersionBean.setSerVersion(bean.getSerVersion());
				
				serviceVersionBean.setSerVerId(bean.getSerVerId());
				serviceVersionBean.setPushState(SerProcessConstantFlag.PUSHFLAG_UPDATE);
				serviceVersionService.update(serviceVersionBean);
			}
		}
	}
	
	/**
	 * 节点对象转为json串
	 */
	@Override
	public Map<String,Object> objTojson(List<Node> nodelist,String flowchartId,String serVerId){
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		JSONArray allarray  = new JSONArray();
		JSONArray nodearray  = new JSONArray();
		JSONArray nodeJoinarray  = new JSONArray();
		
		JSONObject startinparam = new JSONObject();
		JSONObject startoutparam = new JSONObject();
		
		String startreqformat = "";
		String startrespformat = "";
		String strartreqdatatype = "";
		String startreturndatatype = "";
		String startinCharset = "";
		String startoutCharset = "";
		
		//开始节点和结束节点信息的初始化
		for(Node node:nodelist){
			if(node.getNodeStyle().equals(Node.startNodeStyle)){
				StartNode startnode = (StartNode)node;
				startinCharset = startnode.getParam().getCharset();
				if(startnode.getParam() != null){
					startinparam = getParamObj(startnode.getParam());
					startreqformat = startnode.getParam().getFormat();
					strartreqdatatype = startnode.getParam().getType();
				}
			}
			if(node.getNodeStyle().equals(Node.endNodeStyle)){
				EndNode endnode = (EndNode)node;
				startoutCharset = endnode.getParam().getCharset();
				if(endnode.getParam() != null){
					startoutparam = getParamObj(endnode.getParam());
					startrespformat = endnode.getParam().getFormat();
					startreturndatatype = endnode.getParam().getType();
				}
			}
		}
		
		//对象转json
		for(Node node:nodelist){
			//开始节点和结束节点
			if(node.getNodeStyle().equals(Node.startNodeStyle) || node.getNodeStyle().equals(Node.endNodeStyle)){
				StartNode startnode = (StartNode)node;
				ServiceMainBean mainbean = serviceMainService.getByPrimaryKey(flowchartId);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("node", startnode);
				String agreement = startnode.getAgreement();
				agreement = agreement != null?agreement:"";
				map.put("serVerId", serVerId);
				map.put("agreement", agreement);
				map.put("port","");
				map.put("reqformat", startreqformat);
				map.put("respformat", startrespformat);
				map.put("reqdatatype", strartreqdatatype);
				map.put("returndatatype", startreturndatatype);
				map.put("inparameter", startinparam);
				map.put("outparameter", startoutparam);
				
				if(mainbean !=null) {
					map.put("serdesc", mainbean.getSerResume());
				}else {
					map.put("serdesc", "");
				}
				
				map.put("inCharset", startinCharset);
				map.put("outCharset", startoutCharset);
				
				JSONObject nodeObj = getNodeObj(map,flowchartId);
				System.out.println("开始节点------"+nodeObj);
				nodearray.add(nodeObj);
				allarray.add(nodeObj);
			}
			
			//服务节点
			if(node.getNodeStyle().equals(Node.interfaceNodeStyle)){
				InterfaceNode internode = (InterfaceNode)node;
				
				JSONObject inparam = getParamObj(internode.getInParameter());
				JSONObject outparam = getParamObj(internode.getOutParameter());
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("node", internode);
				
				map.put("serVerId", serVerId);
				map.put("url",internode.getUrl());
				map.put("port",internode.getPort());
				map.put("agreement", internode.getAgreement());
				map.put("method",internode.getMethod());
				map.put("reqformat", internode.getInParameter().getFormat());
				map.put("inCharset", internode.getInParameter().getCharset());
				map.put("respformat", internode.getOutParameter().getFormat());
				map.put("outCharset", internode.getOutParameter().getCharset());
				map.put("reqdatatype", internode.getInParameter().getType());
				map.put("returndatatype", internode.getOutParameter().getType());
				map.put("inparameter", inparam);
				map.put("outparameter", outparam);
				map.put("serdesc", "");
				map.put("callType", internode.getCallType());
				
				JSONObject nodeObj = getNodeObj(map,flowchartId);
				System.out.println("服务节点------"+nodeObj);
				nodearray.add(nodeObj);
				allarray.add(nodeObj);
			}
			
			//聚合节点
			if(node.getNodeStyle().equals(Node.groupNodeEndStyle) || node.getNodeStyle().equals(Node.groupNodeStartStyle)) {
				GroupNode groupNode = (GroupNode)node;
				JSONObject allparam = getParamObj(groupNode.getParam());
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("node", groupNode);
				map.put("serVerId", serVerId);
				map.put("respformat", groupNode.getParam().getFormat());
				map.put("outCharset", groupNode.getParam().getCharset());
				map.put("returndatatype", groupNode.getParam().getType());
				map.put("allparameter", allparam);
				
				JSONObject nodeObj = getNodeObj(map,flowchartId);
				System.out.println("聚合节点------"+nodeObj);
				nodearray.add(nodeObj);
				allarray.add(nodeObj);
				
			}
			
			//线节点
			if(node.getNodeStyle().equals(Node.lineNodeStyle)){
				JSONObject nodeObj;
				try {
					NodeJoin joinNode = (NodeJoin)node;
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("node", joinNode);
					map.put("serVerId", serVerId);
					map.put("startNodeId", joinNode.getStartNodeId());
					map.put("endNodeId", joinNode.getEndNodeId());
					map.put("joinType",joinNode.getJoinType());
					map.put("joinDirection",joinNode.getJoinDirection());
					map.put("path",joinNode.getPath());
					nodeObj = getNodeObj(map,flowchartId);
					
					JSONArray relationSet = getRelationObj(joinNode,nodelist);
					JSONObject conditionObj = getConditionObj(joinNode,nodelist);
					nodeObj.put("relationSet", relationSet);
					nodeObj.put("conditionObj", conditionObj);
					
					System.out.println("条件节点------"+nodeObj);
				
					nodeJoinarray.add(nodeObj);
					allarray.add(nodeObj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
//		for(NodeJoin join:nodeJoinlist){
//			JSONObject joinobj = JSONObject.fromObject(join);
//			nodeJoinarray.add(joinobj);
//			allarray.add(joinobj);
//		}
		
		jsonMap.put("nodeJsonArray", nodearray);
		jsonMap.put("nodeJoinJsonArray", nodeJoinarray);
		jsonMap.put("allJsonArray", allarray);
		return jsonMap;
	}
	
	/**
	 * 参数适配
	 * @param paramJsonArray
	 * @param param
	 * @return
	 */
	private Parameter paramHandler(JSONArray paramJsonArray,Parameter param){
		List<Field> fieldList = new ArrayList<Field>();
		
		List<Attribute> attributes = new ArrayList<Attribute>();
		
		for(int i=0;i<paramJsonArray.size();i++){
			JSONObject paramobj = JSONObject.fromObject(paramJsonArray.get(i));
			if(paramobj.getString("parampos").equals(SerFlowDataConstant.req_xmlattribute)||paramobj.getString("parampos").equals(SerFlowDataConstant.resp_xmlattribute)){
				Attribute attribute = new Attribute();
				attribute = (Attribute) XmlNodeHandler(paramobj,attribute);
				attributes.add(attribute);
			}
		}
		
		for(int i=0;i<paramJsonArray.size();i++){
			JSONObject paramobj = JSONObject.fromObject(paramJsonArray.get(i));
			Field paramfield = new Field();
			paramfield = (Field) XmlNodeHandler(paramobj,paramfield);
			
			List<Attribute> field_attributes = new ArrayList<Attribute>();
			
			for(Attribute attribute:attributes) {
				if(attribute.getParentId().equals(paramobj.getString("id"))) {
					field_attributes.add(attribute);
				}
			}
			
			paramfield.setAttribute(field_attributes);
			
			if(!paramobj.getString("parampos").equals(SerFlowDataConstant.req_xmlattribute)&&!paramobj.getString("parampos").equals(SerFlowDataConstant.resp_xmlattribute)){
				fieldList.add(paramfield);
			}
			
		}
		
		param.setFildList(fieldList);
		return param;
	}
	
	
	/**
	 * 适配公共node的参数值
	 * @param nodeObj
	 * @param node
	 * @return
	 */
	private Node nodeHandler(JSONObject nodeObj,Node node,JSONArray nodeJoinList){
		if(nodeObj.getString("nodeStyle").equals(Node.lineNodeStyle)) {
			node.setNodeId(nodeObj.getString("joinId"));
			node.setNodeStyle(nodeObj.getString("nodeStyle"));
			node.setSerType(nodeObj.getString("serviceType"));
			List<String> joinids = new ArrayList<String>();
			joinids.add(nodeObj.getString("endNodeId"));
			node.setTargetlist(joinids);
		}else {
			node.setClientX(Float.parseFloat(nodeObj.getString("clientX")));
			node.setClientY(Float.parseFloat(nodeObj.getString("clientY")));
			node.setNodeId(nodeObj.getString("nodeId"));
			node.setNodeHeight(Float.parseFloat(nodeObj.getString("nodeHeight")));
			
			//开始节点和接口节点，节点名称设为接口名称
			if((nodeObj.getString("nodeType").equals("circle") && nodeObj.getString("nodeStyle").equals(Node.startNodeStyle))
					|| (nodeObj.getString("nodeType").equals("rectangle") && nodeObj.getString("nodeStyle").equals(Node.interfaceNodeStyle))){
				node.setNodeName(nodeObj.getString("serName"));
			}else{
				node.setNodeName(nodeObj.getString("nodeName"));
			}
			
			node.setNodeRadius(Float.parseFloat(nodeObj.getString("nodeRadius")));
			node.setNodeStyle(nodeObj.getString("nodeStyle"));
			node.setNodeType(nodeObj.getString("nodeType"));
			
			//聚合节点上没有serviceType属性
			if(nodeObj.has("serviceType")) {
				node.setSerType(nodeObj.getString("serviceType"));
			}
			
			if(nodeObj.has("componentType")) {
				node.setComponentType(nodeObj.getString("componentType"));
			}
			node.setNodeWidth(Float.parseFloat(nodeObj.getString("nodeWidth")));
			
			List<String> joinids = new ArrayList<String>();
			for(int i=0;i<nodeJoinList.size();i++) {
				JSONObject joinobj = (JSONObject) nodeJoinList.get(i);
				if(joinobj.getString("startNodeId").equals(node.getNodeId())) {
					joinids.add(joinobj.getString("joinId"));
				}
			}
			node.setTargetlist(joinids);
		}
		
		return node;
	}
	
	/**
	 * 参数父类XmlNode属性适配
	 * @param paramobj
	 * @param xmlNode
	 * @return
	 */
	private XmlNode XmlNodeHandler(JSONObject paramobj,XmlNode xmlNode){
		
		xmlNode.setId(paramobj.getString("id"));
		xmlNode.setName(paramobj.getString("ecode"));
		xmlNode.setType(paramobj.getString("reqtype"));
		xmlNode.setLength(paramobj.getString("maxlength"));
		xmlNode.setLocation(paramobj.getString("parampos"));
		xmlNode.setDesc(paramobj.getString("reqdesc"));
		xmlNode.setMust(Boolean.parseBoolean(paramobj.getString("isempty")));
		xmlNode.setParentId(paramobj.getString("parentId")); 
		if(paramobj.has("order")) {
			xmlNode.setOrder(paramobj.getString("order"));
		}
		
		
		if(paramobj.has("namespace")&&paramobj.getString("parampos").equals("7")) {
			xmlNode.setValue(paramobj.getString("namespace"));
		}
		
		return xmlNode;
	}

		
	
	private JSONObject getParamObj(Parameter param){
		JSONArray paramarray = new JSONArray();
		
		if(param.getFildList() != null){
			List<Field> fields = param.getFildList();
			for(Field field:fields){
				if(null != field.getAttribute()&&field.getAttribute().size()>0) {
					List<Attribute> attributes = field.getAttribute();
					for(Attribute attribute:attributes) {
						JSONObject obj = new JSONObject();
						obj.put("id", attribute.getId());
						obj.put("ecode", attribute.getName());
						obj.put("reqdesc", attribute.getDesc());
						obj.put("reqtype", attribute.getType());
						obj.put("parampos", attribute.getLocation());
						obj.put("isempty", attribute.isMust());
						obj.put("type", "0");
						obj.put("maxlength",attribute.getLength());
						obj.put("parentId", attribute.getParentId());
						paramarray.add(obj);
					}
					
				}
				JSONObject obj = new JSONObject();
				obj.put("id", field.getId());
				obj.put("ecode", field.getName());
				obj.put("reqdesc", field.getDesc());
				obj.put("reqtype", field.getType());
				obj.put("parampos", field.getLocation());
				obj.put("isempty", field.isMust());
				obj.put("type", "0");
				obj.put("maxlength",field.getLength());
				obj.put("namespace",field.getValue());
				
				String parentId = field.getParentId();
				parentId = parentId != null ? parentId:"";
				
				obj.put("parentId", parentId);
				obj.put("order", field.getOrder());
				paramarray.add(obj);
			}
		}
		
		JSONObject listobj = new JSONObject();
		listobj.put("list", paramarray);
		return listobj;
	}
	
	
	private JSONObject getNodeObj(Map<String,Object> map,String flowchartId){
		Node node = (Node) map.get("node");
		JSONObject obj1 = new JSONObject();
		if(node.getNodeStyle().equals(Node.lineNodeStyle)) {
			obj1.put("flowChartId", flowchartId);
			obj1.put("serVerId", map.get("serVerId"));
			obj1.put("joinId", node.getNodeId());
			obj1.put("startNodeId", map.get("startNodeId"));
			obj1.put("endNodeId", map.get("endNodeId"));
			obj1.put("joinType", map.get("joinType"));
			obj1.put("joinDirection", map.get("joinDirection"));
			obj1.put("path", map.get("path"));
			obj1.put("startSet", map.get("startNodeId"));
			obj1.put("endSet", map.get("endNodeId"));
			obj1.put("nodeStyle", node.getNodeStyle());
			obj1.put("serviceType", node.getSerType());
		}else {
			obj1.put("flowChartId", flowchartId);
			obj1.put("serVerId", map.get("serVerId"));
			obj1.put("nodeId", node.getNodeId());
			obj1.put("nodeType", node.getNodeType());
			obj1.put("nodeStyle", node.getNodeStyle());
			obj1.put("componentType", node.getComponentType());
			obj1.put("clientX", node.getClientX());
			obj1.put("clientY", node.getClientY());
			obj1.put("nodeWidth", node.getNodeWidth());
			obj1.put("nodeHeight", node.getNodeHeight());
			obj1.put("nodeRadius", node.getNodeRadius());
			obj1.put("other", "");
			obj1.put("startSet", "");
			obj1.put("endSet", "");
			
			if(!node.getNodeStyle().equals(Node.groupNodeEndStyle) && !node.getNodeStyle().equals(Node.groupNodeStartStyle)) {
				obj1.put("url", map.get("url"));
				obj1.put("port", map.get("port"));
				obj1.put("agreement", map.get("agreement"));
				obj1.put("method", map.get("method"));
				obj1.put("reqformat", map.get("reqformat"));
				obj1.put("inCharset", map.get("inCharset"));
				obj1.put("reqdatatype", map.get("reqdatatype"));
				obj1.put("serdesc", map.get("serdesc"));
				obj1.put("callType", map.get("callType"));
			}
			
			obj1.put("respformat", map.get("respformat"));
			obj1.put("outCharset", map.get("outCharset"));
			obj1.put("returndatatype",  map.get("returndatatype"));
			obj1.put("setline", node.getTargetlist());
			 
			if(map.get("node").getClass() == StartNode.class || 
					map.get("node").getClass() == EndNode.class || 
					map.get("node").getClass() == InterfaceNode.class) {
				obj1.put("serviceType", node.getSerType());
			}
			
			if(map.get("inparameter") != null){
				obj1.put("inparameter", map.get("inparameter"));
			}
			
			if(map.get("outparameter") != null){
				obj1.put("outparameter", map.get("outparameter"));
			}
			
		    if(map.get("allparameter") != null) {
		    		obj1.put("allparameter", map.get("allparameter"));
		    }
			
			//如果是开始节点
			if(map.get("node").getClass() == StartNode.class){
				obj1.put("nodeName", "开始");
				obj1.put("serName", node.getNodeName());
			}else{
				obj1.put("nodeName", node.getNodeName());
				obj1.put("serName", node.getNodeName());
			}
		}
		
		return obj1;
	}

	/**
	 * 关系映射对象转relation json串
	 * @param joinfield
	 * @param node
	 * @param constantField
	 * @return
	 * @throws Exception 
	 */
	private JSONArray getRelationObj(NodeJoin node,List<Node> nodelist) throws Exception{
		JSONArray relationarray = new JSONArray();
		Map<String, String> joinfield = node.getJoinfield();
		Map<String, String> constantField = node.getConstantField();
		
		NodeFactoryImp factory = NodeFactoryImp.getOneNodeFactory();
		Map<String,XmlNode> xmlNodeMap = factory.getXmlNode(nodelist);
		
		for (Object keyobj : joinfield.keySet()) {
			JSONObject obj = new JSONObject();
			obj.put("joinId", node.getNodeId());
			
			obj.put("nextparamId",keyobj);
			XmlNode xmlNode = xmlNodeMap.get(keyobj);
			if(xmlNode != null){
				obj.put("nextparamname",xmlNode.getName());
			}else{
				obj.put("nextparamname","");
			}
			
			obj.put("preparamId", joinfield.get(keyobj));
			XmlNode prexmlNode = xmlNodeMap.get(joinfield.get(keyobj));
			if(prexmlNode != null){
				obj.put("preparamname",prexmlNode.getName());
			}else{
				obj.put("preparamname","");
			}
			
							
			if(constantField.containsKey(keyobj)){
				obj.put("constantparam", constantField.get(keyobj));
			}
			
			relationarray.add(obj);
				
		}
		return relationarray;
	}
	
	/**
	 * 条件对象转condition json串
	 * @param node
	 * @return
	 * @throws Exception 
	 */
	private JSONObject getConditionObj(NodeJoin node,List<Node> nodelist) throws Exception{
		JSONObject conditionobj = new JSONObject();
		conditionobj.put("joinId", node.getNodeId());
		String conditionobjstr = node.getCode();
		
		//特殊字符处理
		if(conditionobjstr.contains("'")) {
			conditionobjstr= conditionobjstr.replace("'", "%@");
		}else if(conditionobjstr.contains("\"")) {
			conditionobjstr = conditionobjstr.replace("\"", "%*");
		}
		System.err.println(conditionobjstr);
		conditionobj.put("condition",conditionobjstr);
		
		JSONArray preparamIdSet = new JSONArray();
		JSONArray preparamNameSet = new JSONArray();
		
		List<String> fieldList = node.getFieldList();
		if(fieldList != null&&fieldList.size()>0){
			preparamIdSet = JSONArray.fromObject(fieldList);
			NodeFactoryImp factory = NodeFactoryImp.getOneNodeFactory();
			Map<String,XmlNode> xmlNodeMap = factory.getXmlNode(nodelist);
			for(String fieldId:fieldList){
				XmlNode xmlNode = xmlNodeMap.get(fieldId);
				if(xmlNode != null){
					preparamNameSet.add(xmlNode.getName());
				}
			}
				
		}
		
		
		conditionobj.put("preparamIdSet", preparamIdSet);
		conditionobj.put("preparamnameSet", preparamNameSet);
		return conditionobj;
		
	}


}
