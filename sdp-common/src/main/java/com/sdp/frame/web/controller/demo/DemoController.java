package com.sdp.frame.web.controller.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/** 
 * 
 * @author qxl
 * @date 2017年2月16日 上午10:29:08 
 * @version 1.0.0
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
	
	@RequestMapping("/index")
	public String index(){
		return "demo/index";
	}
	
}
