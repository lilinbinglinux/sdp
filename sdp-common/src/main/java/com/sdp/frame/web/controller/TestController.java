package com.sdp.frame.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sdp.frame.web.HttpClientUtil;


@Controller      
@RequestMapping(value={"/test"})
public class TestController {
    @RequestMapping(value="/testPost",method=RequestMethod.GET)
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        String str=HttpClientUtil.sendPost("http://127.0.0.1:8080/servflow/login/actionLogin", "loginId=admin&password=admin");
        System.out.println(str);
        /*response.setContentType("multipart/form-data");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(str);
        out.println(str);*/
        
    }

}
