package com.sdp.servflow.serlayout.sso.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/getUser")
public class GetUser {
	
	@RequestMapping(value = "/tokenuserinfo",method = RequestMethod.POST)
	@ResponseBody
	public String tokenuserinfo(@RequestBody String token) {
		System.out.println("-------------------"+token);
		return token;
	}
	
	@RequestMapping(value = "/loginIduserinfo",method = RequestMethod.POST)
	@ResponseBody
	public String loginIduserinfo(@RequestBody String loginId) {
		System.out.println("-------------------"+loginId);
		return loginId;
	}
	

}
