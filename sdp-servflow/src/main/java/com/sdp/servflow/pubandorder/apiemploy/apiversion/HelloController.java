package com.sdp.servflow.pubandorder.apiemploy.apiversion;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/{version}")
public class HelloController {
 
    @RequestMapping("/hello")
    @ApiVersion(1)
    @ResponseBody
    public String hello(HttpServletRequest request){
        System.out.println("haha1..........");
         
        return "hello1";
    }
     
    @RequestMapping("/hello")
    @ApiVersion(2)
    @ResponseBody
    public String hello2(HttpServletRequest request){
        System.out.println("haha2.........");
         
        return "hello2";
    }
     
    @RequestMapping("/hello")
    @ApiVersion(5)
    @ResponseBody
    public String hello5(HttpServletRequest request){
        System.out.println("haha5.........");
         
        return "hello5";
    }
}
