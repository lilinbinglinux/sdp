/*
 * 文件名：BouncEmailController.java
 * 版权：Copyright by www.sdp.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月5日
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

import com.sdp.cop.octopus.entity.BounceEmailLog;
import com.sdp.cop.octopus.service.BouncEmailLogService;

import springfox.documentation.annotations.ApiIgnore;


/**
 * 
 * 退信Controller
 * @author zhangyunzhen
 * @version 2017年7月5日
 * @see BouncEmailLogController
 * @since
 */
@RequestMapping("/rest/api")
@Controller
@ApiIgnore
public class BouncEmailLogController {

    /**
     * service
     */
    @Autowired
    private BouncEmailLogService service;

    /**
     * Description: <br>
     *  跳转到退信日志列表页面
     * 
     * @return 
     * @see
     */
    @RequestMapping("/logBounceEmail")
    public String recordLogUI() {
        return "log/BounEmailLog";
    }

    /**
     * Description: <br>
     *  获取邮件回退列表数据
     * @return 
     * @see
     */
    @RequestMapping("/log/bounceEmail/pager")
    @ResponseBody
    public String findByPagerSendRecordInfo(String draw, int start, int length,
                                            BounceEmailLog bounceEmailLog) {
        return service.findByPagerBounceMailInfo(draw, start, length, bounceEmailLog);
    }

    /**
     * Description: <br>
     *  根据app和收件人邮箱号查询回退邮件信息
     * 
     * @param app app名字
     * @param addr 邮箱地址
     * @return 
     * @see
     */
    @RequestMapping(value = "/log/back/{app}",method=RequestMethod.GET)
    @ResponseBody
    public String findbackInfoByAppAndEmailAddr(@PathVariable("app")String app,String emailAddr){
        return service.findbackInfoByAppAndEmailAddr(app, emailAddr);
    }

}
