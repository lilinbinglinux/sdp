package com.sdp.servflow.serlayout.process.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.CurrentUserUtils;
import com.sdp.servflow.pubandorder.serapitype.service.ServiceApiTypeService;
import com.sdp.servflow.pubandorder.serapitype.service.ServiceTypeService;
import com.sdp.servflow.pubandorder.servicebasic.model.ServiceMainBean;
import com.sdp.servflow.pubandorder.servicebasic.service.ServiceMainService;

import net.sf.json.JSONArray;

/**
 * 流程图编排中layer弹框
 * @author zy
 *
 */

@Controller
@RequestMapping("/processlayer")
public class ProcessLayerController {
	
    @Autowired
    private ServiceTypeService serviceTypeService;
    
    @Autowired
    private ServiceApiTypeService serviceApiTypeService;
    
    @Autowired
    private ServiceMainService serviceMainService;
    
	@RequestMapping(value = "/apiparam",method = RequestMethod.GET)
    public String apiparam(String nodeId,String nodeType,String nodeName,Model model,String serFlowType
    		,String nodeStyle, String typeName){
		model.addAttribute("nodeId", nodeId);
		model.addAttribute("nodeType", nodeType);
		model.addAttribute("nodeName", nodeName);
		model.addAttribute("nodeStyle", nodeStyle);
		
		model.addAttribute("serviceTypes", JSONArray.fromObject(serviceTypeService.selectCollection(serFlowType)));
		model.addAttribute("apiServiceTypes",serviceApiTypeService.selectFilterDate());

		model.addAttribute("typeName", typeName);

        return "serlayout/layer/apiparam";
    }
	
	@RequestMapping(value = "/relationparam",method = RequestMethod.GET)
    public String relationparam(String joinId,Model model){
		model.addAttribute("joinId", joinId);
//		model.addAttribute("nodeType", nodeType);
//		model.addAttribute("nodeName", nodeName);
        return "serlayout/layer/relationparam";
    }
	
	@RequestMapping(value = "/checkSreName",method = RequestMethod.POST)
	@ResponseBody
    public boolean checkSreName(String serName){
		Map<String, String> map = new HashMap<String,String>();
		map.put("serName", serName);
		map.put("synchFlag", "0");
		map.put("delFlag", "0");
		map.put("loginId", CurrentUserUtils.getInstance().getUser().getLoginId());
		map.put("tenantId", CurrentUserUtils.getInstance().getUser().getTenantId());
		List<ServiceMainBean> list = serviceMainService.getAllByCondition(map);
		if(null == list || list.size() <= 0) {
			return true;
		}else {
			return false;
		}
    }
	
	

}
