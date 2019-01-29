package com.sdp.servflow.pubandorder.flowchart.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.util.JsonUtils;
import com.sdp.servflow.pubandorder.flowchart.model.FlowChart;
import com.sdp.servflow.pubandorder.flowchart.service.FlowChartService;




@Controller
@RequestMapping("/flowchart")
public class FlowChartController {
	@Autowired
	private FlowChartService flowChartService;
	
	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String index() {
		return "process/flowchart";
	}
	/**
	 * 查询流程图数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/selectPage" }, method = RequestMethod.POST)
	@ResponseBody
	public Map chartlist(String start, String length, String jsonStr,FlowChart flowChart) {
		Map<String, Object> paramMap = JsonUtils.stringToCollect(jsonStr);
		String tenant_id = CurrentUserUtils.getInstance().getUser().getTenantId();
		paramMap.put("tenant_id", tenant_id);
		return flowChartService.selectAll(start, length, paramMap);
	}
	
	@RequestMapping(value={"/updateJsp/{flowChartId}"},method=RequestMethod.GET)
	public String updateJsp(HttpServletRequest request,Model model,@PathVariable String flowChartId){
		String tenantId = CurrentUserUtils.getInstance().getUser().getTenantId();
		Map<String , String> map=new HashMap<String,String>();
		map.put("tenantId", tenantId);
		map.put("flowChartId", flowChartId);
		FlowChart flowChart=flowChartService.findByIdAndTenantId(map);
		model.addAttribute("flowChart", flowChart);
		return "process/flowChartAdd";
	}

	
	@RequestMapping(value={"/update"},method=RequestMethod.POST)
	@ResponseBody
	public String update(HttpServletRequest request,FlowChart flowChart){
		String tenantId = CurrentUserUtils.getInstance().getUser().getTenantId();
		String returnString="error";
		try {
			flowChart.setTenant_id(tenantId);
			flowChartService.updateName(flowChart);
			returnString="success";
		} catch (Exception e) {
//			log.xssInfo("context", e);
		}
		return returnString;
	}
	
	
	@RequestMapping(value={"/delete"},method=RequestMethod.POST)
	@ResponseBody
	public String delete(String flowChartId){
	    if(StringUtils.isNotBlank(flowChartId)){
	        flowChartService.deleteLaySerAll(flowChartId);
	    }
		return "";
	}
	

}
