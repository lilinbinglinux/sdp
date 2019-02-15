/*
 * 文件名：EmailTaskController.java
 * 版权：Copyright by www.sdp.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年5月18日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sdp.cop.octopus.entity.EmailEntity;
import com.sdp.cop.octopus.entity.SMSEntity;
import com.sdp.cop.octopus.service.MessageSendService;

import springfox.documentation.annotations.ApiIgnore;


/**
 * 发送消息Controller
 * @author zhangyunzhen
 * @version 2017年5月18日
 * @see MessageSendController
 * @since
 */
@ApiIgnore
@RequestMapping("/rest/api")
@RestController
public class MessageSendController {

    /**
     * emailService
     */
    @Autowired
    public MessageSendService msgService;

    /**
     * Description: <br>
     *  发送邮件
     * @param email 邮箱地址
     * @param txt 邮件内容
     * @param subject 主题
     * @return 
     * @see
     */
    @RequestMapping(value = "/mail/send/{app}", method = RequestMethod.POST)
    public String sendMail(@RequestBody EmailEntity emailEntity,
                           @PathVariable("app") String appName) {
        return msgService.sendMail(emailEntity, appName);
    }

    /**
     * Description: <br>
     *   发短信
     * @param mobile 手机号
     * @param content 内容
     * @see
     */
    @RequestMapping(value = "/message/send/{app}", method = RequestMethod.POST)
    public String sendSm(@RequestBody SMSEntity smsEntity, @PathVariable("app") String appName) {
        return msgService.sendSm(smsEntity, appName);
    }   

}
