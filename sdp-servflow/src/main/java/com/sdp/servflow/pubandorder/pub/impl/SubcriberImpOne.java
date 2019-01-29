package com.sdp.servflow.pubandorder.pub.impl;

import com.sdp.servflow.kafka.KafkaConsumer;
import com.sdp.servflow.pubandorder.pub.ISubcriber;
import com.sdp.servflow.pubandorder.pub.model.SubscribePublish;

//import kafka.consumer.ConsumerIterator;

/**
 * 
 * 订阅者实现类
 *
 * @author ZY
 * @version 2017年5月16日
 * @see SubcriberImpOne
 * @since
 */
public class SubcriberImpOne<M> implements ISubcriber<M> {
    public String name;  
    public SubcriberImpOne(String name) {  
        super();  
        this.name = name;  
    }  
    
    /**
     * 订阅
     */
    public void subcribe(SubscribePublish subscribePublish) {  
        subscribePublish.subcribe(this);  
    }  
    
    /**
     * 退订
     */
    public void unSubcribe(SubscribePublish subscribePublish) {  
        subscribePublish.unSubcribe(this);  
    }  
  
    public void update(String publisher,M message) {  
        System.out.println(this.name+"收到"+publisher+"发来的消息:"+message.toString());  
    }

    @Override
    public void kafkasubcribe(String topic) {
      /*  ConsumerIterator<String, String> it = new KafkaConsumer().consume(topic);
        while (it.hasNext())
            System.out.println(this.name+"收到"+topic+"发来的消息:"+it.next().message());  */
    }  
}
