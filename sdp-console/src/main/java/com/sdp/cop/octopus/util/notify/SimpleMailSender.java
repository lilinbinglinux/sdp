package com.sdp.cop.octopus.util.notify;


import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Callable;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sdp.cop.octopus.entity.MailSenderInfo;
import com.sdp.cop.octopus.util.ThreadPoolUtil;

/** 
* 简单邮件（不带附件的邮件）发送器 
* @author ke_wang
* @since 2016-05-25
*/
public class SimpleMailSender{

    /**
     * 说明：日志实例
     * 功能：打印日志
     */
    private Logger logger = LoggerFactory.getLogger(SimpleMailSender.class);

    /**
     * 以文本格式发送邮件
     * @param mailInfo MailSenderInfo 待发送的邮件的信息
     * @return boolean 
     * @see MailSenderInfo
     */
    public boolean sendTextMail(MailSenderInfo mailInfo) {
        logger.info("--------------------------正在向以下邮箱发送邮件!--------------------------");
        for (InternetAddress address : mailInfo.getToAddress()) {
            String eMailAddress = address.getAddress();
            int length = 70 - eMailAddress.length();
            StringBuilder builder = new StringBuilder("-");
            for (int i = 0; i < length / 2; i++ ) {
                builder.append(" ");
            }
            builder.append("[" + eMailAddress + "]");
            for (int i = 1, l = length / 2 + length % 2; i < l; i++ ) {
                builder.append(" ");
            }
            builder.append("-");
            logger.info(builder.toString());
        }
        logger.info("--------------------------------------------------------------------------");

        // 判断是否需要身份认证
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        if (mailInfo.isValidate()) {
            // 如果需要身份认证，则创建一个密码验证器
            authenticator = new MyAuthenticator(mailInfo.getFromAddress(), mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address from = new InternetAddress(mailInfo.getFromAddress());
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中
            mailMessage.setRecipients(Message.RecipientType.TO, mailInfo.getToAddress());
            // 设置邮件消息的主题
            mailMessage.setSubject(mailInfo.getSubject());
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // 设置邮件消息的主要内容
            String mailContent = mailInfo.getContent();
            mailMessage.setText(mailContent);
            // 发送邮件
            Transport.send(mailMessage);
            //if (logger.isInfoEnabled())
            logger.info(
                "---------------------------------邮件发送成功!--------------------------------");
            return true;
        } catch (MessagingException ex) {
            logger.error("----------------------------邮件服务器无法连接!----------------------------");
            ex.printStackTrace();
            logger.error(
                "--------------------------------------------------------------------------");
        }
        return false;
    }

    /**
     * 以HTML格式发送邮件
     * @param mailInfo  MailSenderInfo待发送的邮件信息
     * @return boolean
     * @throws MessagingException 
     * @throws UnsupportedEncodingException 
     * @see MailSenderInfo
     */
    public boolean sendHtmlMail(MailSenderInfo mailInfo) throws Exception {
        logger.info(
            "----------------------------The E-mail is being sent!----------------------------");
        // 判断是否需要身份认证
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        // 如果需要身份认证，则创建一个密码验证器
        if (mailInfo.isValidate()) {
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        sendMailSession.setDebug(true); // Enable the debug mode
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address from = new InternetAddress(mailInfo.getFromAddress());
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中
            mailMessage.setRecipients(Message.RecipientType.TO, mailInfo.getToAddress());
            //设置抄送
            if(mailInfo.getCc()!=null){
                mailMessage.setRecipients(Message.RecipientType.CC, mailInfo.getCc());
            }
            //设置密送
            if(mailInfo.getBcc()!=null){
                mailMessage.setRecipients(Message.RecipientType.BCC, mailInfo.getBcc());
            }
            // 设置邮件消息的主题
            mailMessage.setSubject(MimeUtility.encodeText(mailInfo.getSubject(), "UTF-8", "B"));
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容
            html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            // 将MiniMultipart对象设置为邮件内容
            mailMessage.setContent(mainPart);
            // 发送邮件
           /* Transport.send(mailMessage);*/
            //使用线程发送邮件
            return ThreadPoolUtil.getLongThread(new MailRunner(mailMessage,sendMailSession,mailInfo));
        } catch (MessagingException ex) {
            logger.error(
                "-----------------------------------------------------------------------------------------------");
            throw ex;
        }
    }
    
    /**
     *  发送邮件时使用线程
     * @author zhangyunzhen
     * @version 2017年6月2日
     * @see MailRunner
     * @since
     */
    private class MailRunner implements Callable<Boolean>{
        //邮件信息
        Message message;
        Session session;
        MailSenderInfo info;
        
        public MailRunner(Message message,Session session,MailSenderInfo info){
            this.message = message;
            this.session = session;
            this.info = info;
        }

        @Override
        public Boolean call() throws Exception {
            System.out.println("线程");
            /*Transport.send(message);*/
            Transport trans = session.getTransport("smtp");
			if (info.getMailServerPort() == null || "".equals(info.getMailServerPort())) {
				trans.connect(info.getMailServerHost(), info.getUserName(), info.getPassword());
			} else {
				trans.connect(info.getMailServerHost(), Integer.valueOf(info.getMailServerPort()), info.getUserName(),
						info.getPassword());
			}
            trans.sendMessage(message, message.getAllRecipients());
            return true;
        }
        
    }
    
}
