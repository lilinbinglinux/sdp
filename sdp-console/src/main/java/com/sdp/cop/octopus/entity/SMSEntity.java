/*
 * 文件名：SMSEntity.java
 * 版权：Copyright by www.sdp.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年5月22日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.entity;

import java.util.List;

/**
 * SMS DTO
 * @author zhangyunzhen
 * @version 2017年5月22日
 * @see SMSEntity
 * @since
 */
public class SMSEntity {

    /**
     * 手机号list
     */
    private List<String> mobiles;
    /**
     * 短信内容
     */
    private String content;

    public List<String> getMobiles() {
        return mobiles;
    }

    public void setMobiles(List<String> mobiles) {
        this.mobiles = mobiles;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    
}
