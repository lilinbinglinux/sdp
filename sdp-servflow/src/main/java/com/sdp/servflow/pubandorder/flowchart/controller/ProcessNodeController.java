package com.sdp.servflow.pubandorder.flowchart.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.CurrentUserUtils;
import com.sdp.servflow.pubandorder.flowchart.model.ProcessNode;
import com.sdp.servflow.pubandorder.flowchart.model.ProcessNodeJoin;
import com.sdp.servflow.pubandorder.flowchart.service.ProcessNodeJoinService;
import com.sdp.servflow.pubandorder.flowchart.service.ProcessNodeService;
import com.sdp.servflow.pubandorder.order.service.InterfaceOrderBeanService;
import com.sdp.servflow.pubandorder.pub.service.PublisherBeanService;

import net.sf.json.JSONArray;



@Controller
@RequestMapping("/process")
public class ProcessNodeController {
	

	@Autowired
	private ProcessNodeService processNodeService;
	
	@Autowired
	private PublisherBeanService publisherBeanService;
	
	@Autowired
	private ProcessNodeJoinService joinService;
	
	@Autowired
	private InterfaceOrderBeanService interfaceOrderBeanService;
	
	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String index(String id,Model model,String  flowType) {
		model.addAttribute("flowChartId", id);
		model.addAttribute("flowType", flowType);
		String flowChartName = "";
		if(StringUtils.isNoneBlank(id)){
		    Map<String , String> map=new HashMap<String,String>();
	        map.put("pubid", id);
	        map.put("tenant_id", CurrentUserUtils.getInstance().getUser().getTenantId());
	        flowChartName = publisherBeanService.getAllByCondition(map).get(0).getName();
		}
		model.addAttribute("flowChartName",flowChartName);
		model.addAttribute("tenantId",CurrentUserUtils.getInstance().getUser().getLoginId());
		return "process/process";
	}
	
	@RequestMapping(value = { "/apiindex" }, method = RequestMethod.GET)
    public String apiindex(String id,Model model) {
        model.addAttribute("flowChartId", id);
        return "process/apiflowchart";
    }
	
	/**
	 * 
	 * Description:流程图的更新和添加 
	 * 
	 *@param updateflag
	 *@param conditions
	 *@param pubSers
	 *@param flowChartName
	 *@param others
	 *@param nodeRelation
	 *@return String
	 *
	 * @see
	 */
	@RequestMapping(value={"/addupdateNode"},method=RequestMethod.POST)
	@ResponseBody
	public String addNodeArray(String updateflag,String conditions,String pubSers,String flowChartName,String others,
	                           String nodeRelation){
	    processNodeService.addAll(updateflag,conditions,pubSers,flowChartName,others,nodeRelation);
		return "success";
	}
	
	@RequestMapping(value={"/findNode"},method=RequestMethod.POST)
	@ResponseBody
	public String findNode(String flowChartId) throws Exception{
	    
	    String tenantId = CurrentUserUtils.getInstance().getUser().getTenantId();
        Map<String, String> map = new HashMap<String,String>();
        map.put("flowChartId",flowChartId);
        map.put("tenantId",tenantId);
        
	    List<ProcessNode> pn = processNodeService.findNode(flowChartId);
		List<ProcessNodeJoin> jo = joinService.findAllByFlowId(map);
		
		List<Object> list = new ArrayList<Object>();
		//先添加node数据，再添加线
		list.addAll(pn);
		list.addAll(jo);
		
		JSONArray json =JSONArray.fromObject(list);
		System.out.println(json.toString());
   	 	
		return json.toString();
	}
	
	@RequestMapping(value={"/updateSelectAll"},method=RequestMethod.POST)
    @ResponseBody   
    public Map<String,Object> updateSelectAll(String nodeId,String flowChartId){
	    return processNodeService.updateSelectAll(nodeId,flowChartId);
    }
	
}
