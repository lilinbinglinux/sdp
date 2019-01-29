/*
 * 文件名：ConfigPropBean.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月17日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.entity;

import com.sdp.common.entity.Auth2Details;
import com.sdp.common.entity.EnterpriseWeixinProp;
import com.sdp.common.entity.MailConfigProp;

/**
 * 配置参数dto
 * @author zhangyunzhen
 * @version 2017年7月17日
 * @see ConfigPropBean
 * @since
 */
public class ConfigPropBean {

    /**
     * 邮箱配置
     */
    private MailConfigProp mailConfigProp;

    /**
     * 短信配置
     */
    private Auth2Details shortMessageProp;

    /**
     * 微信配置
     */
    private EnterpriseWeixinProp weiChatConfigProp;

    public MailConfigProp getMailConfigProp() {
        return mailConfigProp;
    }

    public void setMailConfigProp(MailConfigProp mailConfigProp) {
        this.mailConfigProp = mailConfigProp;
    }

    public Auth2Details getShortMessageProp() {
        return shortMessageProp;
    }

    public void setShortMessageProp(Auth2Details shortMessageProp) {
        this.shortMessageProp = shortMessageProp;
    }

    public EnterpriseWeixinProp getWeiChatConfigProp() {
        return weiChatConfigProp;
    }

    public void setWeiChatConfigProp(EnterpriseWeixinProp weiChatConfigProp) {
        this.weiChatConfigProp = weiChatConfigProp;
    }



}
