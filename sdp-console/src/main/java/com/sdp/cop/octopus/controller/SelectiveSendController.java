/*
 * 文件名：SelectiveSendController.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月25日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.cop.octopus.entity.OctopusResult;
import com.sdp.cop.octopus.service.SelectiveSendService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * 选择发送消息service
 * @author zhangyunzhen
 * @version 2017年7月25日
 * @see SelectiveSendController
 * @since
 */
@Controller
@RequestMapping("/rest/api")
@Api(value="多渠道消息管理",description="多渠道消息发送API")
public class SelectiveSendController {

    /**
     * SelectiveSendService
     */
    @Autowired
    private SelectiveSendService selectiveSendService;

    /**
     * Description: <br>
     *  选择性发送消息 
     *     
     * @param app app名字
     * @param content 消息内容
     * @param userID 用户名id
     * @param departments 部门id
     * @return 
     * @see
     */
    @ApiOperation(value="多渠道发送消息",notes="根据成员id和部门id选择任一发送方式发送消息",produces="application/json",response=OctopusResult.class)
    @ApiImplicitParams({@ApiImplicitParam(value="终端名字",dataType="String",paramType="form",required=true,name="app"),
        @ApiImplicitParam(value="成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个",dataType="String",paramType="form",required=false,name="userIds"),
        @ApiImplicitParam(value="部门ID列表，多个接收者用‘|’分隔，最多支持100个",dataType="String",paramType="form",required=false,name="departmentIds"),
        @ApiImplicitParam(value="消息内容，最长不超过2048个字节",dataType="String",paramType="form",required=true,name="content"),
        @ApiImplicitParam(value="发送方式",dataType="String",paramType="form",required=true,name="type",allowableValues="email,shortMessage,weiChat"),
        @ApiImplicitParam(value="邮件主题(只有在发送方式选择为email时，才需要填写)",dataType="String",paramType="form",required=false,name="subject")})
    @ApiResponses({@ApiResponse(code=401,message="部门id无效或无权限给某部门发消息"),
        @ApiResponse(code=401,message="用户id无效或无权限给某用户发消息"),
        @ApiResponse(code=200,message="发送成功"),
        @ApiResponse(code=500,message="发送失败")})
    @RequestMapping(value = "send/message/selective", method = RequestMethod.POST)
    @ResponseBody
    public String selectiveSend(String app, String content, String userIds, String departmentIds,
                                String type,String subject) {
        return selectiveSendService.selectiveSend(app, content, userIds, departmentIds, type,subject);
    }
}
