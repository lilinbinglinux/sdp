package com.sdp.cop.octopus.util;


import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdp.common.entity.MailConfigProp;
import com.sdp.cop.octopus.entity.EmailEntity;
import com.sdp.cop.octopus.entity.MailSenderInfo;
import com.sdp.cop.octopus.util.notify.SimpleMailSender;


/**
 * 
 * 邮件发送Utils
 * @author zhangyunzhen
 * @version 2017年5月17日
 * @see MailUtils
 * @since
 */
@Component
public class MailUtils {

    /**
     * 配置信息
     */
    @Autowired
    private MailConfigProp properties;

    /**
     * 发送邮件
     * @param internetAddress InternetAddress[]
     * @param txt String
     * @param subject String
     * @return boolean Boolean
     * @throws Exception 
     */
    public boolean sendEmail(EmailEntity emailEntity)
        throws Exception {

        //发送方信息
        //MailConfigurationProperties properties = SpringUtils.getBean(MailConfigurationProperties.class);
        String host = properties.getMailServerHost();
        String username = properties.getMailServerUsername();
        String pwd = properties.getMailServerPassword();
        String from = properties.getMailServerUsername();
        String port = properties.getMailServerPort();
        boolean isSSL = properties.getMailServerIsSSL();
        if ("".equals(host) || "".equals(username) || "".equals(pwd) || "".equals(from)) {
            return false;
        }

        //收件方邮箱号类型转换
        /*  InternetAddress[] internetAddresses = new InternetAddress[emailEntity.getTo().size()];
        for (int i = 0; i < emailEntity.getTo().size(); i++ ) {
            internetAddresses[i] = new InternetAddress(emailEntity.getTo().get(i));
        }*/
        String strTo = emailEntity.getTo().toString();
        InternetAddress[] to = InternetAddress.parse(strTo.substring(1, strTo.length() - 1));

        InternetAddress[] cc = null;
        if (emailEntity.getCc() != null && emailEntity.getCc().size() != 0) {
            String strCc = emailEntity.getCc().toString();
            cc = InternetAddress.parse(strCc.substring(1, strCc.length() - 1));
        }

        InternetAddress[] bcc = null;
        if (emailEntity.getBcc() != null && emailEntity.getBcc().size() != 0) {
            String strBcc = emailEntity.getBcc().toString();
            bcc = InternetAddress.parse(strBcc.substring(1, strBcc.length() - 1));
        }
        
        MailSenderInfo mailInfo = new MailSenderInfo(host, port, true, isSSL, username, pwd, from, to, cc,
            bcc, emailEntity.getSubject(), emailEntity.getContent());

        SimpleMailSender sms = new SimpleMailSender();
        return sms.sendHtmlMail(mailInfo);
    }
}
