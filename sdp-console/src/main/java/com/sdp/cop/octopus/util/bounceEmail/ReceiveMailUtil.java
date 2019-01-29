/*
 * 文件名：ReceiveMailUtil.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月5日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.util.bounceEmail;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.FromStringTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SentDateTerm;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.sdp.common.entity.MailConfigProp;
import com.sdp.cop.octopus.entity.BounceEmailLog;


/**
 * 收取邮件Util
 * @author zhangyunzhen
 * @version 2017年7月5日
 * @see ReceiveMailUtil
 * @since
 */
public class ReceiveMailUtil {

    /**
     * Description: <br>
     *  获取邮件
     * 
     * @throws Exception 
     * @see
     */
    public static List<BounceEmailLog> receiveMail(MailConfigProp properties)
        throws Exception {

        //获取配置信息
        String host = properties.getMailServerHost();
        String username = properties.getMailServerUsername();
        String pwd = properties.getMailServerPassword();
        String admin = properties.getMailServerAdmin();            
        
        Properties props = new Properties();
        //存储接收邮件服务器使用的协议
        props.setProperty("mail.store.protocol", "pop3");
        //设置接收邮件服务器的地址
        props.setProperty("mail.pop3.host",host);
        //根据属性新建一个邮件会话.  
        Session session = Session.getInstance(props);
        //从会话对象中获得POP3协议的Store对象  
        Store store = session.getStore("pop3");
        //如果需要查看接收邮件的详细信息，需要设置Debug标志  
        session.setDebug(false);

        store.connect(host, username, pwd);

        //获取邮件服务器的收件箱  
        Folder folder = store.getFolder("INBOX");
        //以只读权限打开收件箱  
        folder.open(Folder.READ_ONLY);

        //创建搜索条件
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(currentDate);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1); //设置为前一天
        Date dBefore = calendar.getTime(); //得到前一天的时间

        SearchTerm t1 = new FromStringTerm(admin);
        SearchTerm t2 = new SentDateTerm(ComparisonTerm.GE, dBefore);
        SearchTerm t3 = new SentDateTerm(ComparisonTerm.LE, currentDate);
        SearchTerm t4 = new AndTerm(t2, t3);
        SearchTerm st = new AndTerm(t1, t4);
        //搜索邮件  
        Message[] msgs = folder.search(st);

        List<BounceEmailLog> list = parseMessage(msgs);
        //关闭连接  
        folder.close(false);
        store.close();
        return list;
    }

    /** 
     * 获取邮件内容 
     * @param messages 要解析的邮件列表 
     * @throws ParseException 
     */
    public static List<BounceEmailLog> parseMessage(Message... messages)
        throws MessagingException, IOException, ParseException {

        List<BounceEmailLog> list = new ArrayList<>();

        if (messages == null || messages.length < 1) {
            return list;
        }

        // 解析所有邮件  
        for (int i = 0, count = messages.length; i < count; i++ ) {
            MimeMessage msg = (MimeMessage)messages[i];

            StringBuffer content = new StringBuffer(30);
            getMailTextContent(msg, content);
            //System.out.println("邮件正文：" + content);

            BounceEmailLog log = new BounceEmailLog();
            log.setBounceEmaContent(content.toString());
            Document document = Jsoup.parse(content.toString());
            Elements element = document.select("[class=infoTt]");
            System.out.println("=========退信原因：" + element.get(0).text() + "=========");
            log.setBounceReason(element.get(0).text());
            Elements element1 = document.select("[class=Tb_miWp]");
            log.setOriEmaSubject(element1.select("td").get(1).text());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date time = sdf.parse(element1.select("td").get(0).text());
            log.setOriEmaDate(time);
            log.setOriEmaTo(element1.select("td").get(2).text());
            System.out.println("=======日期" + element1.select("td").get(0).text());
            System.out.println("=======原邮件主题" + element1.select("td").get(1).text());
            System.out.println("=======原邮件收件人" + element1.select("td").get(2).text());
            Elements element2 = document.select("[class=ul_lst]");
            log.setProposedSolution(element2.select("li").get(0).text());
            log.setBounceSubject(msg.getSubject());
            System.out.println("=======原邮件退信原因" + element2.select("li").get(0).text());
            System.out.println("=======主题" + msg.getSubject());

            list.add(log);
        }

        return list;
    }

    /** 
     * 获得邮件主题 
     * @param msg 邮件内容 
     * @return 解码后的邮件主题 
     */
    public static String getSubject(MimeMessage msg)
        throws UnsupportedEncodingException, MessagingException {
        return MimeUtility.decodeText(msg.getSubject());
    }

    /** 
     * 获得邮件发件人 
     * @param msg 邮件内容 
     * @return 姓名 <Email地址> 
     * @throws MessagingException 
     * @throws UnsupportedEncodingException  
     */
    public static String getFrom(MimeMessage msg)
        throws MessagingException, UnsupportedEncodingException {
        String from = "";
        Address[] froms = msg.getFrom();
        if (froms.length < 1) throw new MessagingException("没有发件人!");

        InternetAddress address = (InternetAddress)froms[0];
        String person = address.getPersonal();
        if (person != null) {
            person = MimeUtility.decodeText(person) + " ";
        } else {
            person = "";
        }
        from = person + "<" + address.getAddress() + ">";

        return from;
    }

    /** 
     * 根据收件人类型，获取邮件收件人、抄送和密送地址。如果收件人类型为空，则获得所有的收件人 
     * <p>Message.RecipientType.TO  收件人</p> 
     * <p>Message.RecipientType.CC  抄送</p> 
     * <p>Message.RecipientType.BCC 密送</p> 
     * @param msg 邮件内容 
     * @param type 收件人类型 
     * @return 收件人1 <邮件地址1>, 收件人2 <邮件地址2>, ... 
     * @throws MessagingException 
     */
    public static String getReceiveAddress(MimeMessage msg, Message.RecipientType type)
        throws MessagingException {
        StringBuffer receiveAddress = new StringBuffer();
        Address[] addresss = null;
        if (type == null) {
            addresss = msg.getAllRecipients();
        } else {
            addresss = msg.getRecipients(type);
        }

        if (addresss == null || addresss.length < 1) throw new MessagingException("没有收件人!");
        for (Address address : addresss) {
            InternetAddress internetAddress = (InternetAddress)address;
            receiveAddress.append(internetAddress.toUnicodeString()).append(",");
        }

        receiveAddress.deleteCharAt(receiveAddress.length() - 1); //删除最后一个逗号  

        return receiveAddress.toString();
    }

    /** 
     * 获得邮件发送时间 
     * @param msg 邮件内容 
     * @return yyyy年mm月dd日 星期X HH:mm 
     * @throws MessagingException 
     */
    public static String getSentDate(MimeMessage msg, String pattern)
        throws MessagingException {
        Date receivedDate = msg.getSentDate();
        if (receivedDate == null) return "";

        if (pattern == null || "".equals(pattern)) pattern = "yyyy年MM月dd日 E HH:mm ";

        return new SimpleDateFormat(pattern).format(receivedDate);
    }

    /** 
     * 判断邮件中是否包含附件 
     * @param msg 邮件内容 
     * @return 邮件中存在附件返回true，不存在返回false 
     * @throws MessagingException 
     * @throws IOException 
     */
    public static boolean isContainAttachment(Part part)
        throws MessagingException, IOException {
        boolean flag = false;
        if (part.isMimeType("multipart/*")) {
            MimeMultipart multipart = (MimeMultipart)part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++ ) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String disp = bodyPart.getDisposition();
                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT)
                                     || disp.equalsIgnoreCase(Part.INLINE))) {
                    flag = true;
                } else if (bodyPart.isMimeType("multipart/*")) {
                    flag = isContainAttachment(bodyPart);
                } else {
                    String contentType = bodyPart.getContentType();
                    if (contentType.indexOf("application") != -1) {
                        flag = true;
                    }

                    if (contentType.indexOf("name") != -1) {
                        flag = true;
                    }
                }

                if (flag) break;
            }
        } else if (part.isMimeType("message/rfc822")) {
            flag = isContainAttachment((Part)part.getContent());
        }
        return flag;
    }

    /**  
     * 判断邮件是否已读  
     * @param msg 邮件内容  
     * @return 如果邮件已读返回true,否则返回false  
     * @throws MessagingException   
     */
    public static boolean isSeen(MimeMessage msg)
        throws MessagingException {
        return msg.getFlags().contains(Flags.Flag.SEEN);
    }

    /** 
     * 判断邮件是否需要阅读回执 
     * @param msg 邮件内容 
     * @return 需要回执返回true,否则返回false 
     * @throws MessagingException 
     */
    public static boolean isReplySign(MimeMessage msg)
        throws MessagingException {
        boolean replySign = false;
        String[] headers = msg.getHeader("Disposition-Notification-To");
        if (headers != null) replySign = true;
        return replySign;
    }

    /** 
     * 获得邮件的优先级 
     * @param msg 邮件内容 
     * @return 1(High):紧急  3:普通(Normal)  5:低(Low) 
     * @throws MessagingException  
     */
    public static String getPriority(MimeMessage msg)
        throws MessagingException {
        String priority = "普通";
        String[] headers = msg.getHeader("X-Priority");
        if (headers != null) {
            String headerPriority = headers[0];
            if (headerPriority.indexOf("1") != -1 || headerPriority.indexOf("High") != -1)
                priority = "紧急";
            else if (headerPriority.indexOf("5") != -1 || headerPriority.indexOf("Low") != -1)
                priority = "低";
            else
                priority = "普通";
        }
        return priority;
    }

    /** 
     * 获得邮件文本内容 
     * @param part 邮件体 
     * @param content 存储邮件文本内容的字符串 
     * @throws MessagingException 
     * @throws IOException 
     */
    public static void getMailTextContent(Part part, StringBuffer content)
        throws MessagingException, IOException {
        //如果是文本类型的附件，通过getContent方法可以取到文本内容，但这不是我们需要的结果，所以在这里要做判断  
        boolean isContainTextAttach = part.getContentType().indexOf("name") > 0;
        if (part.isMimeType("text/*") && !isContainTextAttach) {
            content.append(part.getContent().toString());
        } else if (part.isMimeType("message/rfc822")) {
            getMailTextContent((Part)part.getContent(), content);
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart)part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++ ) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                getMailTextContent(bodyPart, content);
            }
        }
    }

    /**  
     * 保存附件  
     * @param part 邮件中多个组合体中的其中一个组合体  
     * @param destDir  附件保存目录  
     * @throws UnsupportedEncodingException  
     * @throws MessagingException  
     * @throws FileNotFoundException  
     * @throws IOException  
     */
    public static void saveAttachment(Part part, String destDir)
        throws UnsupportedEncodingException, MessagingException, FileNotFoundException,
        IOException {
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart)part.getContent(); //复杂体邮件  
            //复杂体邮件包含多个邮件体  
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++ ) {
                //获得复杂体邮件中其中一个邮件体  
                BodyPart bodyPart = multipart.getBodyPart(i);
                //某一个邮件体也有可能是由多个邮件体组成的复杂体  
                String disp = bodyPart.getDisposition();
                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT)
                                     || disp.equalsIgnoreCase(Part.INLINE))) {
                    InputStream is = bodyPart.getInputStream();
                    saveFile(is, destDir, decodeText(bodyPart.getFileName()));
                } else if (bodyPart.isMimeType("multipart/*")) {
                    saveAttachment(bodyPart, destDir);
                } else {
                    String contentType = bodyPart.getContentType();
                    if (contentType.indexOf("name") != -1
                        || contentType.indexOf("application") != -1) {
                        saveFile(bodyPart.getInputStream(), destDir,
                            decodeText(bodyPart.getFileName()));
                    }
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            saveAttachment((Part)part.getContent(), destDir);
        }
    }

    /**  
     * 读取输入流中的数据保存至指定目录  
     * @param is 输入流  
     * @param fileName 文件名  
     * @param destDir 文件存储目录  
     * @throws FileNotFoundException  
     * @throws IOException  
     */
    private static void saveFile(InputStream is, String destDir, String fileName)
        throws FileNotFoundException, IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream bos = new BufferedOutputStream(
            new FileOutputStream(new File(destDir + fileName)));
        int len = -1;
        while ((len = bis.read()) != -1) {
            bos.write(len);
            bos.flush();
        }
        bos.close();
        bis.close();
    }

    /** 
     * 文本解码 
     * @param encodeText 解码MimeUtility.encodeText(String text)方法编码后的文本 
     * @return 解码后的文本 
     * @throws UnsupportedEncodingException 
     */
    public static String decodeText(String encodeText)
        throws UnsupportedEncodingException {
        if (encodeText == null || "".equals(encodeText)) {
            return "";
        } else {
            return MimeUtility.decodeText(encodeText);
        }
    }

/*    public static void main(String[] args) {
        try {
            receiveMail();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/
}
