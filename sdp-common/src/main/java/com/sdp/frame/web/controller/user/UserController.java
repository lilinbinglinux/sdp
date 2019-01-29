package com.sdp.frame.web.controller.user;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.util.JsonUtils;
import com.sdp.frame.util.UserUtil;
import com.sdp.frame.web.entity.user.User;
import com.sdp.frame.web.service.organization.OrganizationService;
import com.sdp.frame.web.service.role.RoleService;
import com.sdp.frame.web.service.user.UserService;

/**
 * 用户管理相关操作Controller
 * @author 作者: 吕一凡 
 * @date 2017年1月11日 上午11:50:37 
 * @version 版本: 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private UserService userService;
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private OrganizationService organizationService;

	@ResponseBody
	@RequestMapping("/selectPage")
	public Map selectPage(String start,String length,String jsonStr){
		Map<String,Object> paramMap = JsonUtils.stringToCollect(jsonStr);
		Map<String,Object> map = userService.getUserByCondition(paramMap, start, length);
		return map;
	}
	
	@RequestMapping("/index")
	public String index(){
		return "systemconfig/usermanage";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getUserById",method=RequestMethod.POST)
	public Object selectUserById(String userId){
		User user = this.userService.selectByUserId(userId);
		user.setPassword("");
		return user;
	}
	
	@ResponseBody
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public int  update(User u,String orgIds){
		u.setOrgId(orgIds);
		return userService.update(u);
	}
	
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public int  delete(String id) throws Exception{
		return userService.deleteByUserId(id);
	}
	
	@ResponseBody
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public int  insert(User u,HttpServletRequest request,String orgIds) throws Exception{
		u.setCreaterId(UserUtil.getUserResource(request).getUserId());
		u.setOrgId(orgIds);
		return userService.insert(u);
	}
	
	@ResponseBody
	@RequestMapping(value="/resetPasswd",method=RequestMethod.POST)
	public String  resetPasswd(String userId)throws Exception{
	    this.userService.initPasswd(userId);
	    return "1";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/org",method=RequestMethod.POST)
	public Object  org(String userId)throws Exception{
	    return organizationService.selectAll();
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getUserRole",method=RequestMethod.POST)
	public Object  getUserRole(String userId)throws Exception{
		return userService.selectRoleByUser(userId);
	}
	
	@ResponseBody
	@RequestMapping(value="/role",method=RequestMethod.POST)
	public Object  role(String userId)throws Exception{
		return roleService.selectAll();
	}
	
	@ResponseBody
	@RequestMapping(value="/userAuth",method=RequestMethod.POST)
	public String  userAuth(String jsonStr,String userId)throws Exception{
		List<Map> list = JsonUtils.toList(jsonStr, Map.class);
		userService.userAuth(list, userId);
		return "1";
	}
	
	/**
     * 获取在线登录用户信息
     * @param model
     * @return
     */
    @RequestMapping(value = "/findCurUser", method = RequestMethod.GET)
    public ResponseEntity<User> findCurUser(HttpServletRequest request) {
    	User user = CurrentUserUtils.getInstance().getUser();
    	/*if(null == user){
    		user = new User();
    		user.setState("0");
    	}*/
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

}
