/*
 * 文件名：AppEmailBindController.java
 * 版权：Copyright by www.sdp.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.sdp.cop.octopus.service.AppBindEmailService;
import com.sdp.cop.octopus.util.RegexValidateUtil;

import springfox.documentation.annotations.ApiIgnore;

/**
 *  app绑定邮箱Controller
 * @author zhangyunzhen
 * @version 2017年7月7日
 * @see AppBindEmailController
 * @since
 */
@RequestMapping("/rest/api/email")
@Controller
@ApiIgnore
public class AppBindEmailController {

    /**
     * AppEmailBindService
     */
    @Autowired
    private AppBindEmailService service;
    
    /**
     * Description: <br>
     *  app进行回退邮箱绑定
     * @param app app名字
     * @param addr 邮箱地址
     * @return 
     * @see
     */
    @RequestMapping(value = "/bind/{app}",method=RequestMethod.GET)
    @ResponseBody
    public String appEmailBind(@PathVariable("app")String app,String emailAddr){
        if(!RegexValidateUtil.checkEmail(emailAddr)){
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "401");
            map.put("msg", "邮箱格式不正确");
            return JSON.toJSONString(map);
        }
        return service.appEmailBind(app, emailAddr);
    }
    
    /**
     * Description: <br>
     *  app解除回退邮箱绑定
     * @param app  app名字
     * @param emailAddr 邮箱地址
     * @return 
     * @see
     */
    @RequestMapping(value = "/unbind/{app}", method = RequestMethod.GET)
    @ResponseBody
    public String appEmailUnbind(@PathVariable("app")String app,String emailAddr){
        if(!RegexValidateUtil.checkEmail(emailAddr)){
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "401");
            map.put("msg", "邮箱格式不正确");
            return JSON.toJSONString(map);
        }
        return service.appEmailUnbind(app, emailAddr);
    }
}
