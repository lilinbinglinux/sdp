package com.sdp.frame.web.controller.login;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.frame.security.authication.IAuthication;
import com.sdp.frame.util.MD5Util;
import com.sdp.frame.util.ResponseMessage;
import com.sdp.frame.util.UserUtil;
import com.sdp.frame.web.entity.user.User;
import com.sdp.frame.web.exception.LoginException;

/**
 * @author 作者: 吕一凡 
 * @date 2017年1月10日 下午3:28:40 
 * @version 版本: 1.0
 * 登录控制器
 */
@Controller      
@RequestMapping(value={"/login"})
public class LoginController {
	
	@Resource
	private IAuthication normalAuthicationProvider;
	
	
	@RequestMapping("/toLogin")
	public String Login(){
		return "login/login";
	}
	
	
	/*@RequestMapping(value={"/",""})
	public String toLogin(){
		return "redirect:login/toLogin";
	}*/
	
	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest request){
		normalAuthicationProvider.invalidateSessionResource(request);
		return "login/login";
	}
	
	@RequestMapping(value="/actionLogin",method=RequestMethod.POST)
	public String actionLogin(HttpServletRequest request, HttpServletResponse response){
		try {
			Map<String,String> map = normalAuthicationProvider.authCheck(request);
			String userId = map.get("userId");
			String loginId = map.get("loginId");
			normalAuthicationProvider.putUserResource(request, userId);
			normalAuthicationProvider.putUserMenuResource(request, userId);
			normalAuthicationProvider.putMenuResource(request,userId);
			normalAuthicationProvider.putButtonResource(request, userId);
			normalAuthicationProvider.doUserLoginLog(request, loginId);
			normalAuthicationProvider.countOnlineUser(request, userId);
			response.sendRedirect(request.getContextPath()+"/platform/index");
		} catch (LoginException e) {
			request.setAttribute("message", e.getMessage().toString());
			return "login/login";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/updateUserPassword")
	@ResponseBody
	public Object updateUserPassword(HttpServletRequest request, HttpServletResponse response
			                        ,String oldPasswd,String newPasswd) throws NoSuchAlgorithmException{
		String oldPasswdMd5 = MD5Util.Bit32(oldPasswd);
		User user = UserUtil.getUserResource(request);
		if(!user.getPassword().equals(oldPasswdMd5)){
			return ResponseMessage.createFailMessage("旧密码输入错误！");
		}
		String newPasswdMd5 = MD5Util.Bit32(newPasswd);
		if(user.getPassword().equals(newPasswdMd5)){
			return ResponseMessage.createFailMessage("新密码不能与旧密码一样！");
		}
		normalAuthicationProvider.updateUserPasswd(request, newPasswdMd5);
		return ResponseMessage.createSuccessMessage("密码修改成功！");
	}
	
	@RequestMapping("/getLoginUser")
	@ResponseBody
	public Object getLoginUserInfo(HttpServletRequest request){
		User user = UserUtil.getUserResource(request);
		return user.clone();
	}
}
