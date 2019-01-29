/**
 * 文件名：SmSenderFactory.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年5月17日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.util.sendMessFac.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdp.cop.octopus.util.SmUtils;
import com.sdp.cop.octopus.util.sendMessFac.SendMessageFactory;

/**
 * 短信SenderFactory
 * @author zhangyunzhen
 * @version 2017年5月17日
 * @see SendSmFactoryImpl
 * @since
 */
@Component
public class SendSmFactoryImpl implements SendMessageFactory {

    /**
     * mailUtils
     */
    @Autowired
    private SmUtils smUtils;
    
    /**
     * 得到SmSender实例
     */
    @Override
    public Object getSender() {
        return smUtils;
    }

}
