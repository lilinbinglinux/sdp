package com.sdp.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoExceptionController {
    
    @RequestMapping("/ex1")
    public String baseExcept() throws BaseException {
        throw new BaseException("哈哈，出错了，抛自定义异常，进行捕捉----------");
    }
    
    @RequestMapping("/ex2")
    public String nullExcept() throws NullPointerException {
        throw new NullPointerException("哈哈，出错了，抛空指针异常，不进行捕捉------");
    }
    
    @RequestMapping("/ex3")
    public String datatable(){
        return "usermanage";
    }
    
    @ResponseBody
    @RequestMapping(value = "/ex4", method = RequestMethod.POST)
    public String aadatas(){
        String json = "["+
                          "['a','aa','aaa','aaaa'],"+
                          "['b','bb','bbb','bbbb'],"+
                          "['c','cc','ccc','cccc'],"+
                          "['a','aa','aaa','aaaa'],"+
                          "['b','bb','bbb','bbbb'],"+
                          "['c','cc','ccc','cccc'],"+
                          "['a','aa','aaa','aaaa'],"+
                          "['b','bb','bbb','bbbb'],"+
                          "['c','cc','ccc','cccc'],"+
                          "['a','aa','aaa','aaaa'],"+
                          "['b','bb','bbb','bbbb'],"+
                          "['c','cc','ccc','cccc'] "+
                      "]";
        return json;
    }
    
    @ResponseBody
    @RequestMapping(value = "/ex5", method = RequestMethod.POST)
    public String datas(String pdataId){
        System.out.println(pdataId);
        String json = "[{name:'a1',num1:22,count1:'70.25%',num2:21,count2:'46.02%'},{name:'a2',num1:57,count1:'18.21%',num2:14,count2:'31.16%'},{name:'a3',num1:34,count1:'10.8%',num2:116,count2:'24.48%'},{name:'a4',num1:12,count1:'3.96%',num2:195,count2:'4.24%'},{name:'a5',num1:33,count1:'1.06%',num2:13,count2:'2.96%'}]";
        return json;
    }
    
    @RequestMapping(value = "/proiframe")
    public String param(){
        System.out.println("----------reqmanage");
        return "puborder/proiframe";
    }
}
