package com.sdp.servflow.serlayout.process.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.CurrentUserUtils;
import com.sdp.servflow.serlayout.process.service.SerProcessNodeService;


/**
 * 流程图编排操作
 * @author zy
 *
 */
@Controller
@RequestMapping("/serprocess")
public class SerProcessNodeController {
	
	@Autowired
	private SerProcessNodeService serProcessNodeService;
	
	
	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String index(String id,Model model,String flowType,String serVerId,String serFlowType,String serFlowTypeName, String typeId, String typeName) throws Exception {
		model.addAttribute("flowChartId", id);
		model.addAttribute("flowType", flowType);
		model.addAttribute("serVerId",serVerId);
		model.addAttribute("tenantId",CurrentUserUtils.getInstance().getUser().getLoginId());
		model.addAttribute("serName",serProcessNodeService.getSerName(id,serFlowType));
		model.addAttribute("serFlowType",serFlowType);
		model.addAttribute("serFlowTypeName",serFlowTypeName);
		
		Map<String,Object> jsonMap = serProcessNodeService.getNodeJson(id,serVerId,serFlowType);
		model.addAttribute("serNodeArray", jsonMap.get("nodeJsonArray").toString());
		model.addAttribute("serJoinArray", jsonMap.get("nodeJoinJsonArray").toString());

		model.addAttribute("typeId", serFlowType);
		model.addAttribute("typeName", serFlowTypeName);

		System.out.println(jsonMap.get("nodeJsonArray").toString());

		return "serlayout/process";
	}
	
	
	@RequestMapping(value={"/findNode"},method=RequestMethod.POST)
	@ResponseBody
	public String findNode(String flowChartId,String serVerId,String serFlowType) throws Exception{
		Map<String,Object> jsonMap = null;
		jsonMap = serProcessNodeService.getNodeJson(flowChartId,serVerId,serFlowType);
		
		return jsonMap.get("allJsonArray").toString();
	}
	
	@RequestMapping(value={"/addNode"},method=RequestMethod.POST)
	@ResponseBody
	@Transactional(rollbackFor=RuntimeException.class)
	public String addNode(String serNodeArray,String serJoinArray,String serFlowType) throws Exception{
		try {
			serProcessNodeService.addNode(serNodeArray,serJoinArray,serFlowType);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return "success";
	}
	
	@RequestMapping(value={"/updateNode"},method=RequestMethod.POST)
	@ResponseBody
	@Transactional(rollbackFor=RuntimeException.class)
	public String updateNode(String serId,String updateFlag,String serNodeArray,String serJoinArray,String serFlowType) throws Exception{
		try {
			serProcessNodeService.updateNode(serId,updateFlag,serNodeArray,serJoinArray,serFlowType);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return "success";
	}
	
}
