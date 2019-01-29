/*
 * 文件名：EmailService.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年5月18日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.sdp.common.entity.Auth2Details;
import com.sdp.common.entity.MailConfigProp;
import com.sdp.cop.octopus.constant.SendModEnum;
import com.sdp.cop.octopus.dao.SendRecordDao;
import com.sdp.cop.octopus.entity.EmailEntity;
import com.sdp.cop.octopus.entity.SMSEntity;
import com.sdp.cop.octopus.entity.SendRecordInfo;
import com.sdp.cop.octopus.util.ExceptionUtil;
import com.sdp.cop.octopus.util.MailUtils;
import com.sdp.cop.octopus.util.MailValid;
import com.sdp.cop.octopus.util.SmUtils;
import com.sdp.cop.octopus.util.sendMessFac.impl.SendMailFactoryImpl;
import com.sdp.cop.octopus.util.sendMessFac.impl.SendSmFactoryImpl;


/**
 * Email Service
 * @author zhangyunzhen
 * @version 2017年5月18日
 * @see MessageSendService
 * @since
 */
@Service
public class MessageSendService {

    /**
     * SendRecordDao
     */
    @Autowired
    private SendRecordDao sendDao;

    /**
     * mail工厂
     */
    @Autowired
    private SendMailFactoryImpl maiFactory;

    /**
     * 短信工厂
     */
    @Autowired
    private SendSmFactoryImpl smFactory;

    /**
     * mail配置信息
     */
    @Autowired
    private MailConfigProp properties;

    /**
     * sm配置信息
     */
    @Autowired
    private Auth2Details auth2Details;

    /**
     * 
     * Description: <br>
     * 发送邮件
     *  支持群发
     * @param emailEntity emailEntity
     * @param appName appName
     * @return 
     * @see
     */
    public String sendMail(EmailEntity emailEntity, String appName) {
        Map<String, String> map = new HashMap<>();   
        boolean flag = false;
        MailUtils mailUtils = (MailUtils)maiFactory.getSender();
        //封装发送记录参数
        SendRecordInfo info = new SendRecordInfo();
        info.setSender(properties.getMailServerUsername());
        info.setReceiver(emailEntity.getTo().toString());
        info.setContent("主题：" + emailEntity.getSubject() + "   内容：" + emailEntity.getContent());
        info.setType(SendModEnum.email.toString());
        info.setAppname(appName);
        
        try {
            flag = mailUtils.sendEmail(emailEntity);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "400");
            map.put("msg", "发送失败");
            //保存错误日志
            info.setErrorlog(ExceptionUtil.getStackTrace(e).substring(0, 4000));
            sendDao.save(info);
            return JSON.toJSONString(map);
        }
        if (flag) {
            map.put("status", "202");
            map.put("msg", "发送成功");
        } else {
            //保存错误日志
            info.setErrorlog("发送方信息不完整");
            map.put("status", "400");
            map.put("msg", "发送失败");
        }
        sendDao.save(info);
        return JSON.toJSONString(map);
    }

    /**
     * 
     * Description: <br>
     *  发送短信
     *      支持群发
     * @param smsEntity  短信实体
     * @param appName   appName
     * @return 
     * @see
     */
    public String sendSm(SMSEntity smsEntity, String appName) {
        Map<String, String> map = new HashMap<>();
        SmUtils smUtils = (SmUtils)smFactory.getSender();

        SendRecordInfo info = new SendRecordInfo();
        info.setSender(auth2Details.getSenderPhoneNumber());
        info.setReceiver(smsEntity.getMobiles().toString());
        info.setContent(smsEntity.getContent());
        info.setType(SendModEnum.shortMessage.toString());
        info.setAppname(appName);

        try {
            smUtils.sendSm(smsEntity);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "400");
            map.put("msg", "发送失败");
            //保存日志
            info.setErrorlog(ExceptionUtil.getStackTrace(e).substring(0, 4000));
            sendDao.save(info);
            return JSON.toJSONString(map);
        }
        map.put("status", "202");
        map.put("msg", "发送成功");
        sendDao.save(info);
        return JSON.toJSONString(map);
    }
    
    /**
     * Description: <br>
     *  邮箱是否存在校验
     * @return 
     * @see
     */
    private List<String> mailValid(EmailEntity emailEntity) {
        String info = "";
        List<String> invalidMail = new ArrayList<>();
        List<String> vertifyMails = new ArrayList<>();
        if (emailEntity.getTo() != null&&emailEntity.getTo().size()>0) {
            vertifyMails = emailEntity.getTo();
        }
        if (emailEntity.getBcc() != null&&emailEntity.getBcc().size()>0) {
            vertifyMails.addAll(emailEntity.getBcc());
        }
        if (emailEntity.getCc() != null&&emailEntity.getCc().size()>0) {
            vertifyMails.addAll(emailEntity.getCc());
        }
        
        for (String toMail : vertifyMails) {
            boolean flag = MailValid.valid(toMail, "mail.bonc.com.cn");
            if (!flag){
                invalidMail.add(toMail);
            };
        }
        return invalidMail;
    }
}
