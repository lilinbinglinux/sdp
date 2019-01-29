/*
 * 文件名：SelectiveSendService.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月25日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.sdp.cop.octopus.constant.SendModEnum;
import com.sdp.cop.octopus.entity.EmailEntity;
import com.sdp.cop.octopus.entity.EnterWeixinUserDetailBean;
import com.sdp.cop.octopus.entity.OctopusResult;
import com.sdp.cop.octopus.entity.SMSEntity;


/**
 * 选择性发送消息service
 * @author zhangyunzhen
 * @version 2017年7月25日
 * @see SelectiveSendService
 * @since
 */
@Service
public class SelectiveSendService {

    /**
     * 企业微信service
     */
    @Autowired
    private EnterpriseWeiChatService enterpriseWeiChatService;

    /**
     * 发送消息service
     */
    @Autowired
    private MessageSendService messageSendService;

    /**
     * Description: <br>
     *  选择性发送消息 
     *      步骤：1.权限校验
     *          2.获取全部用户
     *          3.根据发送类型发送消息
     *      
     * @param app app名字
     * @param content 消息内容
     * @param userID 用户名id
     * @param departments 部门id
     * @return 
     * @see
     */
    public String selectiveSend(String app, String content, String userID,
                                       String departments, String type,String subject) {
        String result = "";
        boolean flag = false;
        List<EnterWeixinUserDetailBean> userDetailBeans = new ArrayList<>();
        //1.发送权限校验
        //部门权限校验
        if (StringUtils.isNotBlank(departments)) {
            flag = enterpriseWeiChatService.checkDepartment(app, departments);
            if (flag) { //有发送权限
                //2.获取用户详细信息
                String[] departmentIds = departments.split("\\|");
                for (String departmentId : departmentIds) {
                    List<EnterWeixinUserDetailBean> userbean = enterpriseWeiChatService.getUserDetailListByDepartmentId(
                        Integer.valueOf(departmentId));
                    userDetailBeans.addAll(userbean);
                }
            } else {
                return JSON.toJSONString(OctopusResult.build(401, "部门id无效或无权限给某部门发消息"));
            }
        }
        //用户权限校验
        if (StringUtils.isNotBlank(userID)) {
            flag = enterpriseWeiChatService.checkUser(app, userID);
            if (flag) { //有发送权限
                String[] userArr = userID.split("\\|");
                List<String> asList = Arrays.asList(userArr);
                for (String userid : asList) {
                    EnterWeixinUserDetailBean bean = enterpriseWeiChatService.getUserDetailInfoByUserId(
                        userid);
                    userDetailBeans.add(bean);
                }
            }else{
                return JSON.toJSONString(OctopusResult.build(401, "用户id无效或无权限给某用户发消息"));
            }
        }
        //3.根据发送类型给用户发送消息
        if (SendModEnum.email.toString().equals(type)) {
            result = sendEmail(userDetailBeans, content, app,subject);
        }else if(SendModEnum.shortMessage.toString().equals(type)){
            result = sendShortMessage(userDetailBeans, content, app);
        }else if(SendModEnum.weiChat.toString().equals(type)){
            result = sendWeichat(userDetailBeans, content, app);
        }
        return result;
    }

    /**
     * Description: <br>
     *  发短信
     * @param beans
     * @param content
     * @param app
     * @return 
     * @see
     */
    private String sendShortMessage(List<EnterWeixinUserDetailBean> beans, String content,
                                 String app) {
        //封装发送短信参数
        List<String> mobileList = new ArrayList<>();
        for (EnterWeixinUserDetailBean bean : beans) {
            mobileList.add(bean.getMobile());
        }
        SMSEntity smsEntity = new SMSEntity();
        smsEntity.setContent(content);
        smsEntity.setMobiles(mobileList);
        return messageSendService.sendSm(smsEntity, app);

    }

    /**
     * Description: <br>
     *  发邮件
     * 
     * @param beans
     * @param content
     * @param app
     * @return 
     * @see
     */
    public String sendEmail(List<EnterWeixinUserDetailBean> beans, String content, String app,String subject) {
        //封装发送邮件参数
        List<String> emailList = new ArrayList<>();
        for (EnterWeixinUserDetailBean bean : beans) {
            emailList.add(bean.getEmail());
        }
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setTo(emailList);
        emailEntity.setContent(content);
        if (StringUtils.isNotBlank(subject)) {
            emailEntity.setSubject(subject);
        }else{
            emailEntity.setSubject("通知公告");
        }
        return messageSendService.sendMail(emailEntity, app);
    }

    /**
     * Description: <br>
     *  发微信
     * @param beans
     * @param content
     * @param app
     * @return 
     * @see
     */
    public String sendWeichat(List<EnterWeixinUserDetailBean> beans, String content, String app) {
        //封装发送微信参数
        StringBuffer sb = new StringBuffer();
        for (EnterWeixinUserDetailBean bean : beans) {
            sb.append(bean.getUserid()+"|");
        }
        String userIds = sb.substring(0, sb.length()-1);
        return enterpriseWeiChatService.sendMessage(app, userIds, "", content);
    }
}
