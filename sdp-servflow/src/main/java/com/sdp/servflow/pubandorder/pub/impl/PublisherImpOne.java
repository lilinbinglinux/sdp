package com.sdp.servflow.pubandorder.pub.impl;

import com.sdp.servflow.pubandorder.pub.IPublisher;
import com.sdp.servflow.pubandorder.pub.model.SubscribePublish;

/**
 * 
 * 发布者实现类
 *
 * @author ZY
 * @version 2017年5月16日
 * @see PublisherImpOne
 * @since
 */
public class PublisherImpOne<M> implements IPublisher<M> {
    private String name;  
    
    public PublisherImpOne(String name) {  
        super();  
        this.name = name;  
    } 
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void publish(SubscribePublish subscribePublish, M message,boolean isInstantMsg) {  
        subscribePublish.publish(this.name, message, isInstantMsg);  
    }

//    public void kafkapublish(SubscribePublish subscribePublish,M message,boolean isInstantMsg) {
//        subscribePublish.kafkapublish(this.name,message,isInstantMsg);
//    }
}
