/*
 * 文件名：ConfigPropController.java
 * 版权：Copyright by www.sdp.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月17日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.sdp.cop.octopus.entity.ConfigPropBean;
import com.sdp.cop.octopus.service.ConfigPropService;
import com.sdp.cop.octopus.service.EnterpriseWeiChatService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;


/**
 * 界面化配置controller
 * @author zhangyunzhen
 * @version 2017年7月17日
 * @see ConfigPropController
 * @since
 */
@Controller
@RequestMapping("/rest/api/config")
@Api(value = "配置管理", description = "基本配置，微信配置相关API")
@ApiIgnore
public class ConfigPropController {

    /**
     * service
     */
    @Autowired
    private ConfigPropService service;

    /**
     * 企业微信service
     */
    @Autowired
    private EnterpriseWeiChatService enterpriseWeiChatService;

    /**
     * Description: <br>
     * 修改配置信息
     * @param type 配置信息的类型
     * @param bean 配置信息dto
     * @return String String
     * @see
     */
    @ApiIgnore
    @RequestMapping(value = "/update/prop/{type}", method = RequestMethod.POST)
    @ResponseBody
    public String updateProp(@PathVariable("type") String type, ConfigPropBean bean) {
        return service.updateProp(type, bean);
    }

    /**
     * Description: <br>
     *  获取配置信息
     * @return 配置信息
     * @see
     */
    @ApiOperation(value = "基本配置页面", notes = "进入基本配置页面", produces = "text/html")
    @RequestMapping(value = "/basic/prop", method = RequestMethod.GET)
    public String getPropInfo(@ApiIgnore Model model) {
        return service.getPropINfo(model);
    }

    /**
     * Description: <br>
     * 微信发送权限管理页面
     * @return 
     * @see
     */
    @ApiOperation(value = "微信配置页面", notes = "进入微信配置页面", produces = "text/html")
    @RequestMapping(value = "/weixin/prop", method = RequestMethod.GET)
    public String weixinConfigureUI(@ApiIgnore Model model) {
        String jsonString = enterpriseWeiChatService.getDepartmentList("");
        System.out.println(jsonString);
        model.addAttribute("departmentlist", JSON.parse(jsonString));
        return "log/configer2";
    }
}
