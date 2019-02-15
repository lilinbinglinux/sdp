/*
 * 文件名：SmUtils.java
 * 版权：Copyright by www.sdp.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年5月17日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.util;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdp.common.entity.Auth2Details;
import com.sdp.cop.octopus.entity.SMSEntity;
import com.sdp.cop.octopus.util.oauth.AuthUtils;
import com.sdp.cop.octopus.util.oauth.SMS;


/**
 *  短信Utils
 * @author zhangyunzhen
 * @version 2017年5月18日
 * @see SmUtils
 * @since
 */
@Component
public class SmUtils {

    /**
     * 配置信息
     */
    @Autowired
    private Auth2Details auth2Details;

    /**
     * 
     * Description: <br>
     * 发送短信
     * 
     * @param mobile 手机号
     * @param content 短信内容
     * @return 
     * @see
     */
    public void sendSm(SMSEntity smsEntity) throws Exception {
        List<SMS> list = new ArrayList<>();
        for (String mobile : smsEntity.getMobiles()) {
            list.add(new SMS(mobile, smsEntity.getContent()));
        }
        AuthUtils.getProtectedResource(auth2Details, list);

    }

}
