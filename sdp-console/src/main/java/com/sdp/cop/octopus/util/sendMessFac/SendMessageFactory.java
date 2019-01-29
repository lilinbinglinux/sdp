/*
 * 文件名：SenderFactory.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年5月17日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.util.sendMessFac;


/**
 *  渠道工厂
 * @author zhangyunzhen
 * @version 2017年5月17日
 * @see SendMessageFactory
 * @since
 */
public interface SendMessageFactory {
    
    /**
     * Description: <br>
     *  得到实例
     * @return 
     * @see
     */
     Object getSender(); 
}
