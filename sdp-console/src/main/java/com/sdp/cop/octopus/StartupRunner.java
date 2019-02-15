/*
 * 文件名：StartupRunner.java
 * 版权：Copyright by www.sdp.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月17日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sdp.common.entity.Auth2Details;
import com.sdp.common.entity.EnterpriseWeixinProp;
import com.sdp.common.entity.MailConfigProp;
import com.sdp.cop.octopus.util.enterpriseWeixin.TimingReciveToken;


/**
 * 启动后加载配置
 * @author zhangyunzhen
 * @version 2017年7月17日
 * @see StartupRunner
 * @since
 */
@Component
public class StartupRunner implements CommandLineRunner {

    /**
     * mailConfigProp
     */
    @Autowired
    private MailConfigProp mailConfigProp;

    /**
     * shortMessageProp
     */
    @Autowired
    private Auth2Details shortMessageProp;

    /**
     * WeiChatConfigProp
     */
    @Autowired
    private EnterpriseWeixinProp weiChatConfigProp;

    /**
     * TimingReciveToken
     */
    @Autowired
    private TimingReciveToken token;
    
    @Override
    public void run(String... args)
         {
        
        /**
         * 获取token
         */
        token.ReceiveAddressListAcessToken();
        token.ReceiveCOPCToken();
        
        /**
         * 加载配置文件
         */
        try {
            String basePath = StartupRunner.class.getResource("/").getFile() + "temp/";
            File emailFile = new File(basePath + MailConfigProp.class.getSimpleName() + ".out");
            File shortMessageFile = new File(basePath + Auth2Details.class.getSimpleName() + ".out");
            File weixinChatMessageFile = new File(
                basePath + EnterpriseWeixinProp.class.getSimpleName() + ".out");
            ObjectInputStream inputStream = null;
            if (emailFile.isFile()) {
                inputStream = new ObjectInputStream(new FileInputStream(emailFile));
                MailConfigProp prop = (MailConfigProp)inputStream.readObject();
                mailConfigProp.setMailServerHost(prop.getMailServerHost());
                mailConfigProp.setMailServerAdmin(prop.getMailServerAdmin());
                mailConfigProp.setMailServerPassword(prop.getMailServerPassword());
                mailConfigProp.setMailServerPort(prop.getMailServerPort());
                mailConfigProp.setMailServerUsername(prop.getMailServerUsername());
                inputStream.close();
            }
            if (shortMessageFile.isFile()) {
                inputStream = new ObjectInputStream(new FileInputStream(shortMessageFile));
                Auth2Details auth2Details = (Auth2Details)inputStream.readObject();
                shortMessageProp.setScope(auth2Details.getScope());
                shortMessageProp.setGrantType(auth2Details.getGrantType());
                shortMessageProp.setClientId(auth2Details.getClientId());
                shortMessageProp.setClientSecret(auth2Details.getClientSecret());
                shortMessageProp.setAccessToken(auth2Details.getAccessToken());
                shortMessageProp.setAuthenticationServerUrl(auth2Details.getAuthenticationServerUrl());
                shortMessageProp.setPortalSmsUrl(auth2Details.getPortalSmsUrl());
                shortMessageProp.setSenderPhoneNumber(auth2Details.getSenderPhoneNumber());
                inputStream.close();
            }
            if (weixinChatMessageFile.isFile()) {
                inputStream = new ObjectInputStream(new FileInputStream(weixinChatMessageFile));
                EnterpriseWeixinProp pro = (EnterpriseWeixinProp)inputStream.readObject();
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

}
