package com.sdp.frame.web.controller.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.frame.util.IdUtil;
import com.sdp.frame.util.JsonUtils;
import com.sdp.frame.web.entity.role.Role;
import com.sdp.frame.web.service.resources.ResourceService;
import com.sdp.frame.web.service.role.RoleService;

/**
 * @author 作者: 吕一凡 
 * @date 2017年1月17日 下午4:31:46 
 * @version 版本: 1.0
 */
@Controller
@RequestMapping("/role")
public class RoleController {

	@Resource
	private RoleService roleService;
	
	@Resource
	private ResourceService resourceService;
	
	@RequestMapping("/index")
	public String index(){
		return "systemconfig/rolemanage";
	}

	@ResponseBody
	@RequestMapping("/selectPage")
	public Map selectPage(String start,String length,String jsonStr){
		Map<String,Object> paramMap = JsonUtils.stringToCollect(jsonStr);
		return roleService.selectAll(start, length,paramMap);
	}
	
	@ResponseBody
	@RequestMapping("/selectById")
	public Role selectById(String roleId){
		return roleService.selectByRoleId(roleId);
	}
	
	@ResponseBody
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public int insert(Role role){
		role.setRoleId(IdUtil.createId());
		return roleService.insert(role);
	}
	
	@ResponseBody
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public int update(Role role){
		return roleService.update(role);
	}
	
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public int delete(String roleId){
		return roleService.deleteByRoleId(roleId);
	}
	
	@ResponseBody
	@RequestMapping("/selectRoleResources")
	public List selectRoleResources(String roleId){
		return roleService.selectResourcesByRoleId(roleId);
	}
	
	@ResponseBody
	@RequestMapping("/auth")
	public String auth(String jsonStr,String roleId) throws Exception{
		List<Map> list = JsonUtils.toList(jsonStr, Map.class);
		roleService.insertRoleResourceRef(list,roleId);
		return "1";
	}
	
	
	@ResponseBody
	@RequestMapping("/resources")
	public List selectResources(){
		return resourceService.selectResourcesList();
	}
}
