package com.sdp.servflow.pubandorder.pub;

import com.sdp.servflow.pubandorder.pub.model.SubscribePublish;

/**
 * 
 * 发布者接口
 *
 * @author ZY
 * @version 2017年5月16日
 * @see IPublisher
 * @since
 */
public interface IPublisher<M> {
    
    public void publish(SubscribePublish subscribePublish,M message,boolean isInstantMsg); 
    
//    public void kafkapublish(SubscribePublish subscribePublish,M message,boolean isInstantMsg); 

}
