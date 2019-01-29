package com.sdp.servflow.serlayout.sso.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.util.JsonUtils;
import com.sdp.servflow.pubandorder.serapitype.model.ServiceTypeBean;
import com.sdp.servflow.pubandorder.serapitype.service.ServiceTypeService;
import com.sdp.servflow.serlayout.datahandler.SerFlowDataConstant;
import com.sdp.servflow.serlayout.sso.service.SerspLoginBeanService;
import com.sdp.servflow.sysConfig.service.SysCommonCfgService;

@Controller
@RequestMapping("/serspLogin")
public class SerspLoginBeanController {
	
	@Autowired
	private SerspLoginBeanService serspLoginBeanService;
	
	@Autowired
	private ServiceTypeService serviceTypeService;
	
	@Autowired
	private SysCommonCfgService sysCommonCfgService;
	
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public String index(Model model) {
		List<ServiceTypeBean> beans = new ArrayList<ServiceTypeBean>();
		beans.add(serviceTypeService.selectByPrimaryKey(SerFlowDataConstant.spcas_id));
		beans.add(serviceTypeService.selectByPrimaryKey(SerFlowDataConstant.spinput_id));
		model.addAttribute("serFlowTypes", beans);
		return "serlayout/sso/ssoflowchart";
	}
	
	@ResponseBody
    @RequestMapping(value = "/selectPage",method = RequestMethod.POST)
    public Map selectPage(String start, String length, String jsonStr) {
        @SuppressWarnings("unchecked")
        Map<String, Object> paramMap = JsonUtils.stringToCollect(jsonStr);
        paramMap.put("tenantId", CurrentUserUtils.getInstance().getUser().getTenantId());
        paramMap.put("loginId", CurrentUserUtils.getInstance().getUser().getLoginId());
        paramMap.put("delflag", "0");
        return serspLoginBeanService.selectPage(start, length, paramMap);
    }
	
	@ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public void delete(String sploginid) {
		serspLoginBeanService.delete(sploginid);
    }
	
	@ResponseBody
    @RequestMapping(value = "/getParams",method = RequestMethod.POST)
	public Map<String,Object> getParams(String sploginid) {
		return serspLoginBeanService.getParams(sploginid);
	}
	
	@ResponseBody
    @RequestMapping(value = "/getUrl",method = RequestMethod.POST)
	public String getUrl(String sploginid) {
		String url = sysCommonCfgService.selectByKey(SerFlowDataConstant.spcasUrl_http).getCfg_value();
		url = String.format(url, CurrentUserUtils.getInstance().getUser().getTenantId(),sploginid);
		return url;
	}
	

}
