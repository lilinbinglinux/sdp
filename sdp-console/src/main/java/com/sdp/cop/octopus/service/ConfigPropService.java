/*
 * 文件名：ConfigPropService.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月17日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.alibaba.fastjson.JSON;
import com.sdp.common.entity.Auth2Details;
import com.sdp.common.entity.EnterpriseWeixinProp;
import com.sdp.common.entity.MailConfigProp;
import com.sdp.cop.octopus.constant.SendModEnum;
import com.sdp.cop.octopus.entity.ConfigPropBean;
import com.sdp.cop.octopus.util.enterpriseWeixin.TimingReciveToken;


/**
 * 界面化配置service
 * @author zhangyunzhen
 * @version 2017年7月17日
 * @see ConfigPropService
 * @since
 */
@Service
public class ConfigPropService {

    /**
     * mailConfigProp
     */
    @Autowired
    private MailConfigProp mailConfigProp;

    /**
     * 短信配置信息
     */
    @Autowired
    private Auth2Details auth2Details;

    /**
     * 微信配置信息
     */
    @Autowired
    private EnterpriseWeixinProp weiChatConfigProp;

    /**
     * request
     */
    @Autowired
    private HttpServletRequest request;
    
    /**
     * 定时任务获取token
     */
    @Autowired
    private TimingReciveToken reciveToken;

    /**
     * Description: <br>
     *  修改配置文件
     * @param type 配置文件类型
     * @param bean 配置文件bean
     * @param request rquest
     * @return 
     * @see
     */
    public String updateProp(String type, ConfigPropBean bean) {
        Map<String, String> map = new HashMap<>();
        try {
            if (SendModEnum.email.toString().equals(type)) {//修改邮件配置文件
                mailConfigProp.setMailServerAdmin(bean.getMailConfigProp().getMailServerAdmin());
                mailConfigProp.setMailServerHost(bean.getMailConfigProp().getMailServerHost());
                mailConfigProp.setMailServerPassword(bean.getMailConfigProp().getMailServerPassword());
                mailConfigProp.setMailServerPort(bean.getMailConfigProp().getMailServerPort());
                mailConfigProp.setMailServerUsername(bean.getMailConfigProp().getMailServerUsername());
                mailConfigProp.setMailServerIsSSL(bean.getMailConfigProp().getMailServerIsSSL());
                writeObject(mailConfigProp);
                map.put("status", "200");
            } else if (SendModEnum.shortMessage.toString().equals(type)) {//修改短信配置文件
                auth2Details.setGrantType(bean.getShortMessageProp().getGrantType());
                auth2Details.setClientId(bean.getShortMessageProp().getClientId());
                auth2Details.setClientSecret(bean.getShortMessageProp().getClientSecret());
                auth2Details.setAuthenticationServerUrl(
                    bean.getShortMessageProp().getAuthenticationServerUrl());
                auth2Details.setPortalSmsUrl(bean.getShortMessageProp().getPortalSmsUrl());
                auth2Details.setSenderPhoneNumber(bean.getShortMessageProp().getSenderPhoneNumber());
                writeObject(auth2Details);
                map.put("status", "200");
            } else if(SendModEnum.weiChat.toString().equals(type)) { //修改微信配置文件
                weiChatConfigProp.setAccessTokenUrl(bean.getWeiChatConfigProp().getAccessTokenUrl());
                weiChatConfigProp.setAgentidCopc(bean.getWeiChatConfigProp().getAgentidCopc());
                weiChatConfigProp.setCorpid(bean.getWeiChatConfigProp().getCorpid());
                weiChatConfigProp.setCorpsecretAddresslist(bean.getWeiChatConfigProp().getCorpsecretAddresslist());
                weiChatConfigProp.setCorpsecretCopc(bean.getWeiChatConfigProp().getCorpsecretCopc());
                weiChatConfigProp.setDepartmentlistUrl(bean.getWeiChatConfigProp().getDepartmentlistUrl());
                weiChatConfigProp.setMessageSendUrl(bean.getWeiChatConfigProp().getMessageSendUrl());
                weiChatConfigProp.setUserSimplelistUrl(bean.getWeiChatConfigProp().getUserSimplelistUrl());
                writeObject(weiChatConfigProp);
                /**
                 * 重新获取token
                 */
                reciveToken.ReceiveAddressListAcessToken();
                reciveToken.ReceiveCOPCToken();
                map.put("status", "200");
            }
            return JSON.toJSONString(map);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "500");
            return JSON.toJSONString(map);
        }
    }

    /**
     * Description: <br>
     *  获取配置信息
     * 
     * @return 配置信息
     * @see
     */
    public String getPropINfo(Model model) {
        model.addAttribute("weiChatConfigProp", weiChatConfigProp);
        model.addAttribute("mailConfigProp", mailConfigProp);
        model.addAttribute("shortMessageProp", auth2Details);
        return "log/configer";
    }

    /**
     * Description: <br>
     *      将对象序列化到本地
     * @param object 序列化对象
     * @param request request
     * @throws Exception 抛出异常
     * @see
     */
    private void writeObject(Object object)
        throws Exception {
        String path = ConfigPropService.class.getResource("/").getFile();
        System.out.println("保存配置类根路径:" + path);
        File file = new File(path + "temp");
        if (!file.isDirectory()) {
            file.mkdir();
        }
        //文件名字
        String fileName = object.getClass().getSimpleName() + ".out";
        File file2 = new File(file, fileName);
        //判断对象文件是否存在
        if (file2.isFile()) {
            file2.delete();
        }
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
            new FileOutputStream(file2));
        objectOutputStream.writeObject(object);
        objectOutputStream.close();
    }
}
