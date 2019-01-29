/*
 * 文件名：EnterpriseWeiChatController.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月18日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.cop.octopus.entity.OctopusResult;
import com.sdp.cop.octopus.service.EnterpriseWeiChatService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * 微信企业号Controller
 * @author zhangyunzhen
 * @version 2017年7月18日
 * @see EnterpriseWeichatController
 * @since
 */
@Controller
@RequestMapping("/rest/api/weichat")
@Api(value="企业微信消息推送",description="获取发送消息权限（获取部门及部门成员集合），消息推送相关API")
public class EnterpriseWeichatController {

    /**
     * 企业号service
     */
    @Autowired
    private EnterpriseWeiChatService service;
    
    /**
     * Description: <br>
     *  获取部门和部门用户信息
     * @param app app名字
     * @return  
     * @see
     */
    @ApiOperation(value="获取发送消息权限（获取部门和部门成员集合）",notes="根据app名字获取对应的部门和部门成员",produces="application/json",response=OctopusResult.class)
    @ApiImplicitParam(dataType="String",name="app",value="终端名字",required=true,paramType="query")
    @ApiResponses({@ApiResponse(code=401,message="无发送权限")})
    @RequestMapping(value="/user/list",method=RequestMethod.GET)
    @ResponseBody
    public String getDepartmentAnduserByApp(String app) {
        return service.getDepartmentAnduserByApp(app);
    }
    
/*    *//**
     * Description: <br>
     *  获取部门集合
     * @param id
     * @return 
     * @see
     *//*
    @RequestMapping(value="/department/list",method=RequestMethod.GET)
    @ResponseBody
    public String getDepartmentList(String id){
        return service.getDepartmentList(id);
    }*/
    
    /**
     * Description: <br>
     * 发送消息
     * @param user 接收成员
     * @param partment 接受部门
     * @param content 消息内容
     * @return 
     * @see
     */
    @ApiOperation(value="企业微信消息推送",notes="选择性给不同用户推送消息",produces="application/json",response=OctopusResult.class)
    @ApiImplicitParams({@ApiImplicitParam(value="终端名字",dataType="String",paramType="path",required=true,name="app"),
        @ApiImplicitParam(value="成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个(部门ID为空时，此字段为必填字段)",dataType="String",paramType="form",required=false,name="userIds"),
        @ApiImplicitParam(value="部门ID列表，多个接收者用‘|’分隔，最多支持100个(成员ID为空时，此字段为必填字段)",dataType="String",paramType="form",required=false,name="departmentIds"),
        @ApiImplicitParam(value="消息内容，最长不超过2048个字节",dataType="String",paramType="form",required=true,name="content")})
    @ApiResponses({@ApiResponse(code=401,message="部门id无效或无权限给某部门发消息"),
        @ApiResponse(code=401,message="用户id无效或无权限给某用户发消息"),
        @ApiResponse(code=200,message="发送成功"),
        @ApiResponse(code=500,message="发送失败")})
    @RequestMapping(value="/message/send/{app}",method=RequestMethod.POST)
    @ResponseBody
    public String sendMessage(@PathVariable String app,String userIds,String departmentIds,String content){
        return service.sendMessage(app, userIds, departmentIds, content);
    }
}
