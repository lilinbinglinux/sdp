package com.sdp.servflow.pubandorder.flowchart.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.servflow.pubandorder.flowchart.model.ParamFlowChartBean;
import com.sdp.servflow.pubandorder.flowchart.model.ProcessNode;
import com.sdp.servflow.pubandorder.flowchart.model.ProcessNodeJoin;
import com.sdp.servflow.pubandorder.flowchart.model.PubFlowChartBean;
import com.sdp.servflow.pubandorder.flowchart.service.ParamFlowChartBeanService;
import com.sdp.servflow.pubandorder.flowchart.service.ProcessNodeJoinService;
import com.sdp.servflow.pubandorder.flowchart.service.ProcessNodeService;
import com.sdp.servflow.pubandorder.flowchart.service.PubFlowChartBeanService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class ProcessNodeServiceImpl implements ProcessNodeService{

	/**
     * DaoHelper
     */
    @Resource
    private DaoHelper daoHelper;

    //线
    @Autowired
    private ProcessNodeJoinService joinService;

    //映射关系找节点
    @Resource
    private PubFlowChartBeanService pubFlowChartBeanService;

    //映射关系
    @Resource
    private ParamFlowChartBeanService paramFlowChartBeanService;

	@Override
	public void addNode(ProcessNode processNode){
	    daoHelper.insert("com.sdp.servflow.pubandorder.flowchart.mapper.ProcessNodeMapper.addNode", processNode);
	}

	@Override
	public void deleteAll(Map<String, String> map) {
	    daoHelper.delete("com.sdp.servflow.pubandorder.flowchart.mapper.ProcessNodeMapper.deleteAll", map);
	}

	@Override
	public List<ProcessNode> findNodeByFId(Map<String, String> map) {
	    return daoHelper.queryForList("com.sdp.servflow.pubandorder.flowchart.mapper.ProcessNodeMapper.findNodeByFId", map);
	}

	@Override
	public String selectPrePubId(String nodeId){
        return (String)daoHelper.queryOne("com.sdp.servflow.pubandorder.flowchart.mapper.ProcessNodeMapper.selectprepubid", nodeId);
	}

    @Override
    public String selectNextPubId(String nodeId) {
        return (String)daoHelper.queryOne("com.sdp.servflow.pubandorder.flowchart.mapper.ProcessNodeMapper.selectnextpubid", nodeId);
    }

    /**
     * 编排画布线上映射关系回显
     */
    @Override
    public Map<String,Object> updateSelectAll(String nodeId, String flowChartId) {

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("preid", selectPrePubId(nodeId));
        map.put("nextid", selectNextPubId(nodeId));
        map.put("pubSers", pubFlowChartBeanService.select(flowChartId));

        return map;
    }

    @Override
    public List<ProcessNode> findNode(String flowChartId) {
        String tenantId = CurrentUserUtils.getInstance().getUser().getTenantId();
        Map<String, String> map = new HashMap<String,String>();
        map.put("flowChartId",flowChartId);
        map.put("tenant_id",tenantId);
        map.put("user_id",CurrentUserUtils.getInstance().getUser().getLoginId());
        List<ProcessNode> pn =findNodeByFId(map);
        for(ProcessNode node:pn){
            //设置菱形上映射关系回显
            if(node.getNodeType().equals("diamond")){
                Map<String, String> pubparammap = new HashMap<String,String>();
                pubparammap.put("flowChartId", flowChartId);
                pubparammap.put("node_id", node.getNodeId());
                
                List<ParamFlowChartBean> params = paramFlowChartBeanService.getAllByCondition(pubparammap);
                JSONArray jsonArray = JSONArray.fromObject(params);
                
                PubFlowChartBean bean = new PubFlowChartBean();
                List<PubFlowChartBean> beans = pubFlowChartBeanService.getAllByCondition(pubparammap);
                if(null != bean&&beans.size()>0){
                    bean = beans.get(0);
                }
                
                Map<String,Object> diamondmap = new HashMap<String,Object>();
                diamondmap.put("relationparam", jsonArray);
                diamondmap.put("pubSers", bean);
                node.setOther(diamondmap);
            }
            //设置矩形上属性关系
            else if(node.getNodeType().equals("rectangle")){
                JSONObject otherpbj = new JSONObject();
                otherpbj.put("pubid", node.getOther());
                node.setOther(otherpbj);
            }
            //设置圆形上属性关系
            else if(node.getNodeType().equals("circle")){
                JSONObject otherpbj = new JSONObject();
                otherpbj.put("pubid", flowChartId);
                node.setOther(otherpbj);
            }
        }
        return pn;
    }

    /**
     * 添加所有节点相关信息
     */
    @Override
    public String addAll(String updateflag,String conditions, String pubSers, String flowChartName, String others,
                         String nodeRelation) {

        String tenantId = CurrentUserUtils.getInstance().getUser().getTenantId();

        JSONObject jobj = JSONObject.fromObject(conditions);
        String nodeSrt = jobj.getString("nodearray");
        String joinStr = jobj.getString("joinarray");
        String flowChartId = jobj.getString("flowChartId");
        JSONArray nodeArr = JSONArray.fromObject(nodeSrt);
        JSONArray joinArr = JSONArray.fromObject(joinStr);
        List<ProcessNodeJoin> joinlist = (List)JSONArray.toList(joinArr, ProcessNodeJoin.class);
        List<ProcessNode> nodelist = new ArrayList<ProcessNode>();

        //如果是更新则先删除后添加
        if(updateflag.equals("update")){
            Map<String , String> map=new HashMap<String,String>();
            map.put("tenant_id", tenantId);
            map.put("flowChartId", flowChartId);

            //1.清空节点
            deleteAll(map);
            //2.清空线
            joinService.deleteAll(map);
            //3.清空流程图中服务信息
            pubFlowChartBeanService.deleteByCondition(map);
            //4.清空关系映射
            paramFlowChartBeanService.deleteByCondition(map);

        }

        //1.添加节点
        for(int i=0;i<nodeArr.size();i++){
            JSONObject nodeobj =  JSONObject.fromObject(nodeArr.get(i));
            if(nodeobj.get("nodeType").equals("rectangle")){
                JSONObject otherpbj =  JSONObject.fromObject(nodeobj.get("other"));
                nodeobj.put("other",otherpbj.get("pubid"));
            }else{
                nodeobj.put("other","");
            }
            ProcessNode node = (ProcessNode)nodeobj.toBean(nodeobj, ProcessNode.class);
            nodelist.add(node);
        }

        addConfNode(tenantId,flowChartId,others,nodeArr,nodelist);

        if(joinlist.size()>0){
            //2.添加线
            addConfJoin(tenantId,flowChartId,joinlist);
        }

        //3.新增流程图中服务信息
        addConfPubFlowChart(tenantId,flowChartId,pubSers);

        //4.添加映射关系
        addConfParamFlowChart(tenantId,flowChartId,nodeRelation);

        //5.添加条件
        addParamFlowChartCondition(tenantId,flowChartId,nodeRelation);

        return "success";
    }


	private void addConfNode(String tenantId ,String flowChartId,String others,JSONArray nodeArr,List<ProcessNode> nodelist){
        if(nodelist.size()>0){
            for(ProcessNode processNode:nodelist){
                processNode.setCreatime((new Date()).toGMTString());
                processNode.setTenantId(tenantId);
                processNode.setUserId(CurrentUserUtils.getInstance().getUser().getLoginId());
                processNode.setFlowChartId(flowChartId);
                processNode.setPubid(processNode.getOther().toString());
/*                if (processNode.getOther() != null) {
				}else{
					processNode.setPubid("");
				}
*/                addNode(processNode);
            }
        }

    }

    private void addConfJoin(String tenantId,String flowChartId,List<ProcessNodeJoin> joinlist){

        for(ProcessNodeJoin processNodeJoin: joinlist){
            processNodeJoin.setCreatime((new Date()).toGMTString());
            processNodeJoin.setTenantId(tenantId);
            processNodeJoin.setFlowChartId(flowChartId);
            joinService.addJoin(processNodeJoin);
        }
    }

    private void addConfPubFlowChart(String tenantId,String flowChartId,String pubSers){
        if(StringUtils.isNotBlank(pubSers)){
            JSONArray sSer = JSONArray.fromObject(pubSers);
            for(int i=0;i<sSer.size();i++){
               /* if(!sSer[i].contains("}")){
                    sSer[i] = sSer[i]+"}";
                }*/
                PubFlowChartBean bean = JSON.parseObject(sSer.get(i).toString(), PubFlowChartBean.class);
                if(bean.getPubid()!=null){
                    JSONArray nextnodeobjs = JSONArray.fromObject(bean.getNextnode());
                    if(nextnodeobjs.size()>0){
                        String nextnode = nextnodeobjs.getString(0);
                        if(nextnodeobjs.size()>1){
                            for(int j=1;j<nextnodeobjs.size();j++){
                                nextnode += ","+nextnodeobjs.get(j).toString();
                            }
                        }
                        bean.setNextnode(nextnode);
                    }else{
                        bean.setNextnode("");
                    }

                    JSONArray prenodeobjs = JSONArray.fromObject(bean.getPrenode());
                    if(prenodeobjs.size()>0){
                        String prenode = prenodeobjs.getString(0);
                        if(prenodeobjs.size()>1){
                            for(int j=1;j<prenodeobjs.size();j++){
                                prenode += ","+prenodeobjs.get(j).toString();
                            }
                        }
                        bean.setPrenode(prenode);
                    }else{
                        bean.setPrenode("");
                    }

                    bean.setFlowChartId(flowChartId);
                    bean.setTenant_id(CurrentUserUtils.getInstance().getUser().getTenantId());
                    pubFlowChartBeanService.insert(bean);
                }
            }
        }
    }

    private void addConfParamFlowChart(String tenantId,String flowChartId,String nodeRelation){

        JSONArray json = JSONArray.fromObject(nodeRelation); // 首先把字符串转成 JSONArray  对象
        for(int i=0;i<json.size();i++){
            JSONObject noderealobj =  JSONObject.fromObject(json.get(i));
            System.out.println(noderealobj.get("relations"));
            if(null != noderealobj.get("relations")&&!noderealobj.get("relations").equals("")&&!noderealobj.get("relations").equals("\"null\"")){
                JSONArray realarray = JSONArray.fromObject(noderealobj.get("relations"));
                for(int j=0;j<realarray.size();j++){
                    JSONObject realobj =  JSONObject.fromObject(realarray.get(j));
                    System.out.println(realobj);

                    String type = "1";
                    if(StringUtils.isNotBlank(realobj.getString("constantparam"))){
                        type = "0";
                    }
                    ParamFlowChartBean paramFlowChartBean = new ParamFlowChartBean(
                        realobj.getString("relationid"),
                        realobj.getString("prepubid"),
                        realobj.getString("pubid"),realobj.getString("flowChartId"),
                        realobj.getString("preparamid"),realobj.getString("preparamname"),
                        realobj.getString("nextparamid"),realobj.getString("nextparamname"),
                        realobj.getString("diamondid"),
                        CurrentUserUtils.getInstance().getUser().getLoginId(),
                        CurrentUserUtils.getInstance().getUser().getTenantId(),
                        type,
                        realobj.getString("regex"),
                        realobj.getString("constantparam")
                        );
                    paramFlowChartBeanService.insert(paramFlowChartBean);
                }

            }
        }
    }

    //添加条件
    private void addParamFlowChartCondition(String tenantId, String flowChartId, String nodeRelation) {

        JSONArray json = JSONArray.fromObject(nodeRelation); // 首先把字符串转成 JSONArray  对象
        for (int i = 0; i < json.size(); i++) {
            JSONObject noderealobj = JSONObject.fromObject(json.get(i));
            System.out.println(noderealobj.get("parm"));
            if(null != noderealobj.get("parm")){
                JSONArray parmarray = JSONArray.fromObject(noderealobj.get("parm"));
                for (int j = 0; j < parmarray.size(); j++) {
                    JSONObject paramobj = JSONObject.fromObject(parmarray.get(j));
                    if(null != paramobj.get("arr")&&paramobj.get("arr").toString()!=""){
                        System.out.println(paramobj.get("arr"));
                        JSONArray paramobj1 = JSONArray.fromObject(paramobj.get("arr"));
                        for (int k = 0; k < paramobj1.size(); k++) {
                            JSONObject obj =  JSONObject.fromObject(paramobj1.get(k));
                            System.out.println(obj);
                            if (!("".equals(obj.getString("preparamname")))) {
                            	ParamFlowChartBean paramFlowChartBean = new ParamFlowChartBean(
                            			obj.getString("relationid"),
                            			obj.getString("prepubid"),
                            			obj.getString("pubid"), obj.getString("flowChartId"),
                            			obj.getString("preparamid"), obj.getString("preparamname"),
                            			"", "",
                            			obj.getString("node_id"),
                            			CurrentUserUtils.getInstance().getUser().getLoginId(),
                            			CurrentUserUtils.getInstance().getUser().getTenantId(),
                            			"3",
                            			"",
                            			""
                            			);
                            	paramFlowChartBeanService.insert(paramFlowChartBean);
							}
                    }
                    }
                }
                
            }
            
        }


    	/*for (int i = 0; i < paramArr.size(); i++) {
    		JSONObject param = JSONObject.fromObject(paramArr.get(i));


    		JSONArray paramArrs = JSONArray.fromObject(param.get("arr"));

    		System.err.println(paramArrs);
    		List<ParamFlowChartBean> paramlist = (List)JSONArray.toList(paramArrs, ParamFlowChartBean.class);
    		for (ParamFlowChartBean paramFlowChartBean : paramlist) {
    			paramFlowChartBean.setLogin_id(CurrentUserUtils.getInstance().getUser().getLoginId());
    			paramFlowChartBean.setTenant_id(tenantId);
    			paramFlowChartBean.setType("3");
    			paramFlowChartBeanService.insert(paramFlowChartBean);
			}
		}

	*/
    }

}
