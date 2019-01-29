package com.sdp.servflow.serlayout.process.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sdp.common.CurrentUserUtils;
import com.sdp.common.page.Pagination;
import com.sdp.servflow.pubandorder.apiemploy.apiversion.ApiVersion;
import com.sdp.servflow.serlayout.process.model.ServiceInfoPo;
import com.sdp.servflow.serlayout.process.service.ServiceInfoPoService;

import net.sf.json.JSONObject;

/**
 * 流程图列表操作
 * @author zy
 *
 */
@Controller
@RequestMapping("/processTable")
public class SerProcessController {
	@Autowired
	private ServiceInfoPoService serviceInfoPoService;

	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String index() throws Exception {
		return "serlayout/flowchart";
	}

	@RequestMapping(value = { "/index/{typeId}/{typeName}" }, method = RequestMethod.GET)
	public ModelAndView indexByType(@PathVariable String typeId,@PathVariable String typeName,Model model) throws Exception {
		model.addAttribute("typeId",typeId);
		model.addAttribute("typeName",typeName);
		return new ModelAndView("serlayout/flowchart");
	}

	@RequestMapping(value = { "/getTreeData" },method = RequestMethod.POST)
	@ResponseBody
	public Pagination getTreeData(HttpServletRequest request,Pagination page,String serCode,String serName){
		Map<String,String> conmap = new HashMap<String,String>();
		conmap.put("delFlag", "0");
		conmap.put("synchFlag", "0");
		conmap.put("tenantId", CurrentUserUtils.getInstance().getUser().getTenantId());
		conmap.put("loginId", CurrentUserUtils.getInstance().getUser().getLoginId());
		int total=serviceInfoPoService.getAllCount(conmap);

		if(total > 0){
			int startNum=(int) (page.getPageSize()*(page.getPageNo()-1));
			int num=page.getPageSize();
			Map<String, String> map = new HashMap<String, String>();
			map.put("tenant_id", CurrentUserUtils.getInstance().getUser().getTenantId());
			map.put("login_id",CurrentUserUtils.getInstance().getUser().getLoginId());
			map.put("startNum",String.valueOf(startNum));
			map.put("num",String.valueOf(num));
			JSONObject obj = new JSONObject();
			obj.put("serName", serName);
			obj.put("serCode", serCode);
			List<ServiceInfoPo> list = serviceInfoPoService.getTreeData(obj.toString(),String.valueOf(startNum),String.valueOf(num));
			page.setList(list);
			page.setTotalCount(Long.valueOf(total));
		}
		return page;
	}

	@RequestMapping(value = { "/{version}/getTreeData" },method = RequestMethod.POST)
	@ApiVersion(2)
	@ResponseBody
	public Pagination getTreeDataV2(HttpServletRequest request,Pagination page,String serName,String serCode,String typeId){
		Map<String,String> conmap = new HashMap<String,String>();
		conmap.put("delFlag", "0");
		conmap.put("synchFlag", "0");
		conmap.put("tenantId", CurrentUserUtils.getInstance().getUser().getTenantId());
		conmap.put("loginId", CurrentUserUtils.getInstance().getUser().getLoginId());
		conmap.put("typeId", typeId);
		int total=serviceInfoPoService.getAllCount(conmap);

		if(total > 0){
			int startNum=(int) (page.getPageSize()*(page.getPageNo()-1));
			int num=page.getPageSize();
			Map<String, String> map = new HashMap<String, String>();
			map.put("tenant_id", CurrentUserUtils.getInstance().getUser().getTenantId());
			map.put("login_id",CurrentUserUtils.getInstance().getUser().getLoginId());
			map.put("startNum",String.valueOf(startNum));
			map.put("num",String.valueOf(num));
			JSONObject obj = new JSONObject();
			obj.put("serName", serName);
			obj.put("serCode", serCode);
			obj.put("typeId", typeId);
			List<ServiceInfoPo> list = serviceInfoPoService.getTreeData(obj.toString(),String.valueOf(startNum),String.valueOf(num));
			page.setList(list);
			page.setTotalCount(Long.valueOf(total));
		}
		return page;
	}

	@RequestMapping(value = { "/deleteSerProcess" },method = RequestMethod.POST)
	@ResponseBody
	public String deleteSerProcess(ServiceInfoPo serviceInfoPo){
		serviceInfoPoService.deleteSerProcess(serviceInfoPo);
		return "success";

	}


}
