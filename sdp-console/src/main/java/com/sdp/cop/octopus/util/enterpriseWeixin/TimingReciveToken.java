/*
 * 文件名：TimingReciveToken.java
 * 版权：Copyright by www.sdp.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月19日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.util.enterpriseWeixin;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdp.common.entity.EnterpriseWeixinProp;
import com.sdp.cop.octopus.entity.AccessToken;
import com.sdp.cop.octopus.util.HttpClientUtil;


/**
 * 企业微信定时获取access_token
 * @author zhangyunzhen
 * @version 2017年7月19日
 * @see TimingReciveToken
 * @since
 */
@Component
public class TimingReciveToken {

    private static final Logger logger = Logger.getLogger(TimingReciveToken.class);

    /**
     * 企业微信配置文件
     */
    @Autowired
    private EnterpriseWeixinProp prop;
    
    /**
     * Description: <br>
     *  定时获取通讯录access_token
     * @see
     */
    @Scheduled(cron = "0 0 0/2 * * ?  ")
    public void ReceiveAddressListAcessToken() {
        try {
            String requestUrl = prop.getAccessTokenUrl().replace("CORPID", prop.getCorpid()).replace("CORPSECRET",
                prop.getCorpsecretAddresslist());
            String result = HttpClientUtil.doGetWithSSl(requestUrl,prop.getHost());
            if (StringUtils.isNotBlank(result)) {
                JSONObject jsonObject = JSON.parseObject(result);
                String access_token = jsonObject.getString("access_token");
                AccessToken.setENTERPRISE_WEIXIN_ACCESS_TOKEN_ADDRESSLIST(access_token);
                logger.info("获取企业微信通讯录accesstoken:" + access_token);
                System.out.println("获取企业微信通讯录accesstoken:" + access_token);
            }
        
        } catch (Exception e) {
            logger.error("获取信息失败");
        }
    }
    
    /**
     * Description: <br>
     * 定时获取copc的token
     *  
     * @see
     */
    @Scheduled(cron = "0 0 0/2 * * ?  ")
    public void ReceiveCOPCToken() {
        try {
            String requestUrl = prop.getAccessTokenUrl().replace("CORPID", prop.getCorpid()).replace("CORPSECRET",
                prop.getCorpsecretCopc());
            String result = HttpClientUtil.doGetWithSSl(requestUrl,prop.getHost());
            if (StringUtils.isNotBlank(result)) {
                JSONObject jsonObject = JSON.parseObject(result);
                String access_token = jsonObject.getString("access_token");
                AccessToken.setENTERPRISE_WEIXIN_ACCESS_TOKEN_COPC(access_token);
                logger.info("获取企业微信copc应用accesstoken:" + access_token);
                System.out.println("获取企业微信copc应用accesstoken:" + access_token);
            }
        } catch (Exception e) {
            logger.error("获取信息失败");
        }
    }
}
