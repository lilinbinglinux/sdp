/*
 * 文件名：TimingReceive.java
 * 版权：Copyright by www.sdp.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月12日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.util.weixin;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdp.cop.octopus.entity.AccessToken;
import com.sdp.cop.octopus.util.HttpClientUtil;


/**
 * 定时获取access_token
 * @author zhangyunzhen
 * @version 2017年7月12日
 * @see TimingReceive
 * @since
 */
@Component
public class TimingGetAceessToken {

    private static final Logger logger = Logger.getLogger(TimingGetAceessToken.class);

    /**
     * 获取acesstoken地址
     */
    @Value("${access_token_url}")
    private String accessTokenUrl;

    /**
     * 验证用户身份唯一凭证
     */
    @Value("${appid}")
    private String appid;

    /**
     * 验证用户身份唯一凭证密钥
     */
    @Value("${appsecret}")
    private String appsecret;

    /**
     * Description: <br>
     *  定时获取access_token
     * @see
     */
/*    @Scheduled(cron = "0 0 1 * * ? ")*/
    public void ReceiveAcessToken() {
        try {
            String requestUrl = accessTokenUrl.replace("APPID", appid).replace("APPSECRET",
                appsecret);
            String result = HttpClientUtil.doGet(requestUrl);
            if (StringUtils.isNotBlank(result)) {
                JSONObject jsonObject = JSON.parseObject(result);
                String access_token = jsonObject.getString("access_token");
                AccessToken.setACCESS_TOKEN(access_token);
                logger.info("获取accesstoken:" + access_token);
            }
        } catch (Exception e) {
            logger.error("获取信息失败");
        }
    }

}
