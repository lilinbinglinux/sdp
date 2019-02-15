/*
 * 文件名：SendLogController.java
 * 版权：Copyright by www.sdp.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年5月23日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.sdp.cop.octopus.entity.SendRecordInfo;
import com.sdp.cop.octopus.service.SendRecordService;

import springfox.documentation.annotations.ApiIgnore;


/**
 * 发送记录controller
 * @author zhangyunzhen
 * @version 2017年5月23日
 * @see SendRecordController
 * @since
 */
@ApiIgnore
@RequestMapping("/rest/api")
@Controller
public class SendRecordController {
    
    /**
     * SendRecordService
     */
    @Autowired
    private SendRecordService service;
    
    /**
     * 
     * Description: <br>
     *  发送记录页面
     * @return 
     * @see
     */
    @RequestMapping("/logService")
    public String recordLogUI(){
        return "log/sendLog";
    } 
    
    /**
     * Description: <br>
     *  使用datatable对发送日志进行服务端分页操作
     * @param draw ：画板；
     * @param start ：开始记录数；
     * @param length : 每页的长度；
     * @param request ：接受搜索参数；
     * @param sendRecordInfo 查询条件
     * @return String
     */
    @ResponseBody
    @RequestMapping(value="/log/pager")
    public String findByPagerSendRecordInfo(String draw, int start, int length,
                                            SendRecordInfo sendRecordInfo) {
        return service.findByPagerSendRecordInfo(draw, start, length, sendRecordInfo);
    }
    
    /**
     * Description: <br>
     * 查询发送日志记录
     * @param sendRecordInfo
     * @return 
     * @see
     */
    @ResponseBody
    @RequestMapping(value="/log/list/{app}",method=RequestMethod.GET)
    public String findSendRecordInfoByAppname(@PathVariable("app") String app, SendRecordInfo sendRecordInfo) {
        sendRecordInfo.setAppname(app);
        List<SendRecordInfo> infos= service.findSendRecordInfoByAppname(sendRecordInfo);
        return JSON.toJSONString(infos);
    }
}
