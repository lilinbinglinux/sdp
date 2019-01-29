package com.sdp.frame.web.controller.workbench;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/** 
 * @author 作者: jxw 
 * @date 创建时间: 2016-10-4 上午11:31:02 
 * @version 版本: 1.0 
 * 描述：工作台页面
 */

@Controller      
@RequestMapping("/workbench")
public class WorkbenchController {

	//工作台首页
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String main(){
		return "workbench/index";
	}
}
