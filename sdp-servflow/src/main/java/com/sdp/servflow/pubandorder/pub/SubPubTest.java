package com.sdp.servflow.pubandorder.pub;

import com.sdp.servflow.pubandorder.pub.impl.PublisherImpOne;
import com.sdp.servflow.pubandorder.pub.impl.SubcriberImpOne;
import com.sdp.servflow.pubandorder.pub.model.SubscribePublish;

/**
 * 
 * 发布订阅测试类
 *
 * @author ZY
 * @version 2017年5月16日
 * @see SubPubTest
 * @since
 */
public class SubPubTest {
   /* public static void main(String[] args) {  
        SubscribePublish<String> subscribePublish = new SubscribePublish<String>("订阅器");  
        PublisherImpOne<String> publisher1 = new PublisherImpOne<String>("mytopictest");  
        PublisherImpOne<String> publisher2 = new PublisherImpOne<String>("topic2");
        ISubcriber<String> subcriber1 = new SubcriberImpOne<String>("订阅者1");  
        
        //订阅接口
//        surbcriber1.subcribe(subscribePublish);  
//        subcriber2.subcribe(subscribePublish);  
        
//        publisher1.kafkapublish(subscribePublish,"消息1",true);  
//        subcriber1.kafkasubcribe(publisher1.getName());
        
        publisher1.kafkapublish(subscribePublish,"mytopictest",true);
//        subcriber1.kafkasubcribe(publisher1.getName());
        
//        publisher1.kafkapublish(subscribePublish,"消息1",true);  
//        subcriber1.kafkasubcribe(publisher1.getName());
        
        //subcriber1.unSubcribe(subscribePublish);
        
        //注册接口
//        publisher1.publish(subscribePublish, "to",true);  
//        publisher1.publish(subscribePublish, "yy",true);
        
    }  */
}
