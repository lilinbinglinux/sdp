/*
 * 文件名：TestTask.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月6日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.util.bounceEmail;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sdp.common.entity.MailConfigProp;
import com.sdp.cop.octopus.constant.SendModEnum;
import com.sdp.cop.octopus.constant.TimeConstant;
import com.sdp.cop.octopus.dao.AppBindEmailDao;
import com.sdp.cop.octopus.dao.BounceEmailRecordDao;
import com.sdp.cop.octopus.dao.SendRecordDao;
import com.sdp.cop.octopus.entity.AppBindEmailInfo;
import com.sdp.cop.octopus.entity.BounceEmailLog;
import com.sdp.cop.octopus.entity.EmailEntity;
import com.sdp.cop.octopus.entity.SendRecordInfo;
import com.sdp.cop.octopus.util.DateUtils;
import com.sdp.cop.octopus.util.ExceptionUtil;
import com.sdp.cop.octopus.util.MailUtils;
import com.sdp.cop.octopus.util.sendMessFac.impl.SendMailFactoryImpl;


/**
 * 定时接受邮件
 * @author zhangyunzhen
 * @version 2017年7月6日
 * @see TimingReceiveTask
 * @since
 */
@Component
public class TimingReceiveTask {

    /**
     * bounceEmailRecordDao
     */
    @Autowired
    private BounceEmailRecordDao bounceDao;

    /**
     * 发送记录dao
     */
    @Autowired
    private SendRecordDao sendRecordDao;

    /**
     * app邮箱对应dao
     */
    @Autowired
    private AppBindEmailDao appEmailAddrRecordDao;

    /**
     * 发送邮件工厂
     */
    @Autowired
    private SendMailFactoryImpl sendMailFac;

    /**
     * properties
     */
    @Autowired
    private MailConfigProp properties;
    
    /**
     * sendDao
     */
    @Autowired
    private SendRecordDao sendDao;
    
    /**
     * Description: <br>
     *  处理退信邮件
     *      1.获取退信邮件
     *      2.存入数据库
     *      3.转发退信邮件
     *      4.保存退信发送记录
     * @throws Exception 
     *  
     * @see
     */
    @Scheduled(cron = "0 0 1 * * ? ")
    public void ReceiveBounceEmail() {
        List<BounceEmailLog> list;
        try {
            list = ReceiveMailUtil.receiveMail(properties);
            for (BounceEmailLog bounceEmailLog : list) {
                bounceDao.save(bounceEmailLog);
                sendBounceEmail(bounceEmailLog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Description: <br>
     *  发送退信邮件
     * 
     * @param emailEntity  邮件实体
     * @throws Exception 
     * @see
     */
    private boolean sendBounceEmail(BounceEmailLog log) {
        MailUtils mailUtils = (MailUtils)sendMailFac.getSender();
        boolean flag = false;

        //查询退回邮件对应的发送记录
        SendRecordInfo sendRecordInfo = new SendRecordInfo();
        sendRecordInfo.setContent(log.getOriEmaSubject());
        sendRecordInfo.setReceiver(log.getOriEmaTo());
        sendRecordInfo.setSendtime(getBeforeDateTime());
        sendRecordInfo.setType(SendModEnum.email.toString());
        Map<String, Object> spec = searchParam(sendRecordInfo);
		// 获取数据
		Map<String, Object> infoMap = sendDao.findRecordForPage(spec,0,0);
        List<SendRecordInfo> sendRecordInfos = (List<SendRecordInfo>)(infoMap.get("data"));

        if (sendRecordInfos != null && sendRecordInfos.size() > 0) {
            //在回退信息表中保存app名字
            bounceDao.delete(log);;
            log.setApp(sendRecordInfos.get(0).getAppname());
            bounceDao.save(log);
            
            //查找app对应的回退邮箱
            List<AppBindEmailInfo> infos = appEmailAddrRecordDao.findByApp(sendRecordInfos.get(0).getAppname());
            if (infos != null && infos.size() > 0) {//app邮箱地址里有对应记录
                SendRecordInfo info = new SendRecordInfo();

                //给调用方转发退信邮件
                EmailEntity emailEntity = new EmailEntity();
                emailEntity.setContent(log.getBounceEmaContent());
                List<String> sender = new ArrayList<String>();
                sender.add(infos.get(0).getEmailAddr());
                emailEntity.setTo(sender);
                emailEntity.setSubject(log.getBounceSubject());
                try {
                    //封装发送记录参数
                    info.setSender(properties.getMailServerUsername());
                    info.setReceiver(emailEntity.getTo().toString());
                    info.setContent("主题：" + emailEntity.getSubject());
                    info.setType(SendModEnum.bounceMessage.toString());
                    info.setAppname(sendRecordInfos.get(0).getAppname());
                    
                    flag = mailUtils.sendEmail(emailEntity);
                    
                } catch (Exception e) {
                    info.setErrorlog(ExceptionUtil.getStackTrace(e).substring(0, 4000));
                    sendDao.save(info);
                    e.printStackTrace();
                    return false;
                }

                if(!flag){
                    //保存错误日志
                    info.setErrorlog("发送方信息不完整");
                }
                sendDao.save(info);
            }
        }
        return flag;
    }

    /**
     * Description: <br>
     * 封装条件参数
     * 
     * @param sendRecordInfo
     * @return 
     * @see
     */
	private Map<String, Object> searchParam(
			SendRecordInfo sendRecordInfo) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(sendRecordInfo.getAppname())) {
			paraMap.put("appname", "%" + sendRecordInfo.getAppname() + "%");
		}
		if (StringUtils.isNotBlank(sendRecordInfo.getStartTime())) {
			Date start = DateUtils.formatStringToDate(
					sendRecordInfo.getStartTime() + TimeConstant.START_SUFFIX);
			paraMap.put("sendtime", start);
		}
		if (StringUtils.isNotBlank(sendRecordInfo.getEndTime())) {
			Date end = DateUtils.formatStringToDate(
					sendRecordInfo.getEndTime() + TimeConstant.END_SUFFIX);
			paraMap.put("endTime", end);
		}
		if (StringUtils.isNotBlank(sendRecordInfo.getType())) {
			paraMap.put("type", sendRecordInfo.getType());
		}
		return paraMap;
	}

    /**
     * Description: <br>
     *  获取前一天的当前时刻
     * 
     * @return 
     * @see
     */
    private Date getBeforeDateTime() {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(currentDate);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1); //设置为前一天
        Date dBefore = calendar.getTime(); //得到前一天的时间
        return dBefore;
    }
}
