
package com.sdp.cop.octopus.util.sendMessFac.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdp.cop.octopus.util.MailUtils;
import com.sdp.cop.octopus.util.sendMessFac.SendMessageFactory;


/**
 * 
 *  SendMail工厂
 * @author zhangyunzhen
 * @version 2017年5月17日
 * @see SendMailFactoryImpl
 * @since
 */
@Component
public class SendMailFactoryImpl implements SendMessageFactory {
 
    @Autowired
    private MailUtils mailUtils;
    
    /**
     * 得到mailSender实例
     */
    public  Object getSender() {
        return mailUtils;
    }
    
}
