package com.sdp.servflow.serlayout.process.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.common.CurrentUserUtils;
import com.sdp.servflow.pubandorder.node.analyze.abstractfactory.NodeFactory;
import com.sdp.servflow.pubandorder.node.analyze.factory.NodeFactoryImp;
import com.sdp.servflow.pubandorder.node.model.node.EndNode;
import com.sdp.servflow.pubandorder.node.model.node.GroupNode;
import com.sdp.servflow.pubandorder.node.model.node.InterfaceNode;
import com.sdp.servflow.pubandorder.node.model.node.Node;
import com.sdp.servflow.pubandorder.node.model.node.NodeJoin;
import com.sdp.servflow.pubandorder.node.model.node.StartNode;
import com.sdp.servflow.pubandorder.node.xmlpackage.XmlBuilderUtil;
import com.sdp.servflow.pubandorder.servicebasic.model.ServiceMainBean;
import com.sdp.servflow.pubandorder.servicebasic.service.ServiceMainService;
import com.sdp.servflow.serlayout.datahandler.SerFlowDataConstant;
import com.sdp.servflow.serlayout.datahandler.SerNormalData;
import com.sdp.servflow.serlayout.process.model.ServiceInfoPo;
import com.sdp.servflow.serlayout.process.service.SerProcessNodeHandlerService;
import com.sdp.servflow.serlayout.process.service.SerProcessNodeService;
import com.sdp.servflow.serlayout.process.service.ServiceInfoPoService;
import com.sdp.servflow.serlayout.process.util.JSONObjectNUllUtil;
import com.sdp.servflow.serlayout.process.util.SerProcessConstantFlag;
import com.sdp.servflow.serlayout.sso.model.SerspLoginBean;
import com.sdp.servflow.serlayout.sso.service.SerspLoginBeanService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class SerProcessNodeServiceImpl implements SerProcessNodeService {
	@Autowired
	private SerProcessNodeHandlerService nodeHandlerService;
	
	@Autowired
	private ServiceInfoPoService serviceInfoPoService;
	
	@Autowired
	private SerNormalData serNormalData;
	

	@Autowired
	private ServiceMainService serviceMainService;
	
	@Autowired
    private SerspLoginBeanService serspLoginBeanService;
	
	/**
	 * 显示所有节点
	 */
	@Override
	public Map<String,Object> getNodeJson(String flowChartId,String serVerId,String serflowDataType) throws Exception {
		
		/*
		 * 根据serflowDataType的不同，从不同数据源架加载流程图数据
		 */
		Map<String,Object> map = serNormalData.getSerFlow(flowChartId, serVerId, serflowDataType);
		if(map.get("flag").equals("fail")) {
			return (Map<String, Object>) map.get("serFlow");
		}
		String serflowStr = (String)map.get("serFlow");
		/*
		 * 流程图serflowStr xml解析
		 */
		InputStream is = new ByteArrayInputStream(serflowStr.getBytes("utf-8"));
        SAXBuilder sb = new SAXBuilder();
        Document doc = sb.build(is);
        Element sourceRoot = doc.getRootElement();
        List<Element> list = sourceRoot.getChildren();
        
        List<Node> nodelist = new ArrayList<Node>();
        List<NodeJoin> nodeJoinlist = new ArrayList<NodeJoin>();
      
        NodeFactory factory = NodeFactoryImp.getOneNodeFactory();
        for(Element element: list)
        {
            Node node = factory.createNode(element);
            nodelist.add(node);
        }
        
        return nodeHandlerService.objTojson(nodelist,flowChartId,serVerId);
        
	}
	
	
	/**
	 * 添加节点信息
	 */
	@Override
	public String addNode(String serNodeArray, String serJoinArray,String serFlowType) throws RuntimeException {
		try {
		System.out.println("serNodeArray:   "+serNodeArray);
		System.out.println("serJoinArray    "+serJoinArray);
		System.out.println("serFlowType     "+serFlowType);
		
		Map<String,Object> map = getSerProcessXml(serNodeArray,serJoinArray);
		JSONObject startObj = (JSONObject) map.get("startObj");
		startObj = JSONObjectNUllUtil.clearNUll(startObj);
		String flowXml =  map.get("flowXml").toString();
		
		String typeStr = serNormalData.serTypeHandler(serFlowType);
		
		if(typeStr.equals(SerFlowDataConstant.spcas_id)) {
			//单点类型
			serNormalData.serspLoginBeanHandler(startObj,flowXml,SerProcessConstantFlag.ADDUPDATE_ADD);
		}else {
			//同步类型
			ServiceMainBean serviceMainBean = nodeHandlerService.serviceMainBeanHandler(startObj,SerProcessConstantFlag.ADDUPDATE_ADD,SerProcessConstantFlag.VERSIONFLAG_NEWVERSION,typeStr);
			nodeHandlerService.serviceVersionBeanHandler(startObj,flowXml,serviceMainBean,SerProcessConstantFlag.ADDUPDATE_ADD);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return "success";
	}
	
	/**
	 * 更新节点信息
	 * 
	 * 最高版本：
				新建版本：main更新（serId）版本号更新，version添加(serVerId)
				覆盖版本：main更新(serId) 版本号不更新，version更新

	  子版本：
				新建版本：main更新(serId) 版本号更新，version添加(serVerId)
				覆盖版本：main不变(serId+serVerId) 版本号不更新，version更新(serVerId)
	 */
	@Override
	public void updateNode(String serId,String updateFlag, String serNodeArray, String serJoinArray, String serFlowType) {
		try {
			Map<String,Object> map = getSerProcessXml(serNodeArray,serJoinArray);
			JSONObject startObj = (JSONObject) map.get("startObj");
			String flowXml =  map.get("flowXml").toString();
			String typeStr = serNormalData.serTypeHandler(serFlowType);
			
			if(typeStr.equals(SerFlowDataConstant.spcas_id)) {
				//单点数据源
				serNormalData.serspLoginBeanHandler(startObj, flowXml,SerProcessConstantFlag.ADDUPDATE_UPDATE);
			}else {
				//同步异步数据源
				if(updateFlag.equals("isreplace")){
					//覆盖当前版本：ser_main更新，ser_version更新
					Map<String, String> conditionmap = new HashMap<String,String>();
					conditionmap.put("serId", startObj.getString("flowChartId"));
					conditionmap.put("serVerId", startObj.getString("serVerId"));
					
					List<ServiceInfoPo> pos = serviceInfoPoService.getAllEqualInfoByCondition(conditionmap);
					ServiceMainBean serviceMainBean = new ServiceMainBean();
					serviceMainBean.setSerId(startObj.getString("flowChartId"));
					
					//父版本
					if(pos != null&&pos.size()>0){
						serviceMainBean = nodeHandlerService.serviceMainBeanHandler(startObj,SerProcessConstantFlag.ADDUPDATE_UPDATE,SerProcessConstantFlag.VERSIONFLAG_OLDVERSION,typeStr);
					}
					
					nodeHandlerService.serviceVersionBeanHandler(startObj,flowXml,serviceMainBean,SerProcessConstantFlag.ADDUPDATE_UPDATE);
					
				}else if(updateFlag.equals("noreplace")){
					//新建版本：ser_main更新，ser_version添加
					ServiceMainBean serviceMainBean = nodeHandlerService.serviceMainBeanHandler(startObj,SerProcessConstantFlag.ADDUPDATE_UPDATE,SerProcessConstantFlag.VERSIONFLAG_NEWVERSION,typeStr);
					nodeHandlerService.serviceVersionBeanHandler(startObj,flowXml,serviceMainBean,SerProcessConstantFlag.ADDUPDATE_ADD);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * 获取流程图xml串
	 * @param serNodeArray
	 * @param serJoinArray
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getSerProcessXml(String serNodeArray, String serJoinArray) throws Exception{
		List<Node> nodelist = new ArrayList<Node>();
		//List<NodeJoin> nodeJoinList = new ArrayList<NodeJoin>();
		
		JSONObject initnodeobj = null;
		
		//线节点处理
		JSONArray jsonserNodeArray = JSONArray.fromObject(serJoinArray);
		for(int i=0;i<jsonserNodeArray.size();i++){
			JSONObject joinobj = JSONObject.fromObject(jsonserNodeArray.get(i));
			joinobj = JSONObjectNUllUtil.clearNUll(joinobj);
			System.out.println(joinobj);
			NodeJoin joinNode = nodeHandlerService.joinNodeHandler(joinobj.toString(), jsonserNodeArray);
			nodelist.add(joinNode);
		}
		
			
			JSONArray nodejsonarray = JSONArray.fromObject(serNodeArray);
			for(int i=0;i<nodejsonarray.size();i++){
				JSONObject nodeobj = JSONObject.fromObject(nodejsonarray.get(i));
				nodeobj = JSONObjectNUllUtil.clearNUll(nodeobj);
				System.out.println(nodeobj);
				
				//开始节点
				if(nodeobj.get("nodeType").equals("circle")&&nodeobj.get("nodeStyle").equals(Node.startNodeStyle)){
					initnodeobj = nodeobj;
					StartNode startNode = nodeHandlerService.startNodeHandler(nodeobj.toString(), jsonserNodeArray);
					nodelist.add(startNode);
				}
				
				//聚合节点
				if(nodeobj.get("nodeType").equals("circle")&&(nodeobj.get("nodeStyle").equals(Node.groupNodeEndStyle)
						||nodeobj.get("nodeStyle").equals(Node.groupNodeStartStyle))) {
					GroupNode groupNode = nodeHandlerService.groupNodeHandler(nodeobj.toString(), jsonserNodeArray);
					nodelist.add(groupNode);
				}
				
				//接口节点
				if(nodeobj.get("nodeType").equals("rectangle")&&nodeobj.get("nodeStyle").equals(Node.interfaceNodeStyle)){
					InterfaceNode interfaceNode = nodeHandlerService.interfaceNodeHandler(nodeobj.toString(), jsonserNodeArray);
					nodelist.add(interfaceNode);
				}
				
//				//条件节点
//				if(nodeobj.get("nodeType").equals("diamond")&&nodeobj.get("nodeStyle").equals(Node.conditionNodeStyle)){
//					ConditionNode conditionNode = nodeHandlerService.conditionNodeHandler(nodeobj.toString(), nodeJoinList);
//					nodelist.add(conditionNode);
//				}
				
				//结束节点
				if(nodeobj.get("nodeType").equals("circle")&&nodeobj.get("nodeStyle").equals(Node.endNodeStyle)){
					EndNode endNode = nodeHandlerService.endNodeHandler(nodeobj.toString(), jsonserNodeArray);
					nodelist.add(endNode);
				}
			}
			
			String flowXml = XmlBuilderUtil.getSingleStion().build(nodelist);
			System.out.println(flowXml);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("flowXml", flowXml);
			map.put("startObj", initnodeobj);
			return map;
	}
	
	/**
	 * 获取服务综合信息
	 */
	@Override
	public ServiceInfoPo getServiceInfoPo(Map<String,String> map) {
		map.put("tenantId", CurrentUserUtils.getInstance().getUser().getTenantId());
		map.put("loginId", CurrentUserUtils.getInstance().getUser().getLoginId());
		List<ServiceInfoPo> list = serviceInfoPoService.getAllByCondition(map);
		ServiceInfoPo po = new ServiceInfoPo();
		if(list != null && list.size() == 1){
			po =  list.get(0);
		}
		return po;
	}

	public static void main(String[] args) {
		String s = "'2'";
		System.out.println(s.replace("'", "\\\""));
	}


	@Override
	public String getSerName(String id,String serFlowType) {
		String typeStr = serNormalData.serTypeHandler(serFlowType);
		String serName = "";
		if(typeStr.equals(SerFlowDataConstant.asynchronously_id) || typeStr.equals(SerFlowDataConstant.synchronously_id)) {	
			ServiceMainBean bean = serviceMainService.getByPrimaryKey(id);
			if(bean != null) {
				serName = bean.getSerName();
			}
		}else if(typeStr.equals(SerFlowDataConstant.spcas_id)) {
			SerspLoginBean bean = serspLoginBeanService.getAllByPrimaryKey(id);
			if(bean != null) {
				serName =  bean.getSpname();
			}
		}
		return serName;
	}

}
