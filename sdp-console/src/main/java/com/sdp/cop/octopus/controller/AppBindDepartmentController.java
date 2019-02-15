/*
 * 文件名：AppBindDepartmentController.java
 * 版权：Copyright by www.sdp.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月20日
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
import com.sdp.cop.octopus.entity.OctopusResult;
import com.sdp.cop.octopus.service.AppBindDepartmentService;

import springfox.documentation.annotations.ApiIgnore;


/**
 * app绑定部门controller
 * @author zhangyunzhen
 * @version 2017年7月20日
 * @see AppBindDepartmentController
 * @since
 */
@Controller
@ApiIgnore
@RequestMapping("/rest/api/department")
public class AppBindDepartmentController {

    /**
     * app绑定部门service
     */
    @Autowired
    private AppBindDepartmentService service;

    /**
     * Description: <br>
     *  列表数据
     * 
     * @return 
     * @see
     */
    @RequestMapping("/app/relation/data")
    @ResponseBody
    public String getBindInfo() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", service.getBindInfo());
        return JSON.toJSONString(map);
    }

    /**
     * Description: <br>
     *  app绑定部门
     * @param app
     * @param departments
     * @return 
     * @see
     */
    @RequestMapping(value="/bind",method=RequestMethod.POST)
    @ResponseBody
    public String bindDepartment(String app, String[] departments) {
        OctopusResult result = null;
        boolean flag = service.bindDepartment(app, departments);
        if(flag){
            result = OctopusResult.ok();
        }else{
            result = OctopusResult.build(500, "新增失败");
        }
        return JSON.toJSONString(result);
    }
    
    /**
     * Description: <br>
     * 修改绑定信息
     * @param app app名字
     * @param departments 部门
     * @return 
     * @see
     */
    @RequestMapping(value="/update",method=RequestMethod.POST)
    @ResponseBody
    public String updateAppBind(String app, String[] departments) {
        OctopusResult result = null;
        boolean flag = service.updateAppBind(app, departments);
        if(flag){
            result = OctopusResult.ok();
        }else{
            result = OctopusResult.build(500, "编辑失败");
        }
        return JSON.toJSONString(result);
    }
    
    /**
     * Description: <br>
     * 根据app名字删除绑定信息
     * @param app app名字
     * @return 
     * @see
     */
    @RequestMapping(value="/unbind/{app}",method=RequestMethod.GET)
    @ResponseBody
    public String deleteAppBind(@PathVariable("app")String app) {
        OctopusResult result = null;
        boolean flag = service.deleteAppBind(app);
        if(flag){
            result = OctopusResult.ok();
        }else{
            result = OctopusResult.build(500, "删除失败");
        }
        return JSON.toJSONString(result);
    }
}
