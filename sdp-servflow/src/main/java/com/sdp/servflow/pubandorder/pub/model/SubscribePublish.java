package com.sdp.servflow.pubandorder.pub.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sdp.servflow.kafka.KafkaProducer;
import com.sdp.servflow.kafka.model.JProducer;
import com.sdp.servflow.pubandorder.pub.ISubcriber;

/**
 * 
 * 订阅器类
 *
 * @author ZY
 * @version 2017年5月16日
 * @see SubscribePublish
 * @since
 */
public class SubscribePublish<M> extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(SubscribePublish.class);
    //订阅器名称  
    private String name;  
    //订阅器队列容量  
    final int QUEUE_CAPACITY = 20;  
    //订阅器存储队列  
    private BlockingQueue<Msg> queue = new ArrayBlockingQueue<Msg>(QUEUE_CAPACITY);  
    //订阅者  
    private List<ISubcriber> subcribers  = new ArrayList<ISubcriber>();
    
    private JProducer jProducer=new JProducer();
    
    public static List<String> kafkaList=new ArrayList<String>();
    
    public static String productOrderTopic="mytopic";
    
      
    /**  
     * @Description:构造方法     
     * @param name 
     */  
    public SubscribePublish(String name) {  
        this.name = name;  
    }  
  
    /**  
     * @Description: 接收发布者的消息 (QUEUE)
     */  
    public void publish(String publisher,M message,boolean isInstantMsg) {  
        if(isInstantMsg){  
            update(publisher,message);  
            return;  
        }  
       Msg<M> m = new Msg<M>(publisher,message);  
        if(!queue.offer(m)){  
            update();  
        }
    }
    
    /**  
     * @Description: 接收发布者的消息 (kafka)
     */  
//    public void kafkapublish(String name,M message,boolean isInstantMsg) {  
//        KafkaProducer simpleProducer = new KafkaProducer();
//        simpleProducer.sendMsg(name,message.toString());
//    }  
    
    /**   W
     * @Description: 订阅  
     */  
    public void subcribe(ISubcriber subcriber) {  
        subcribers.add(subcriber);  
    }  
    /**   
     * @Description: 退订  
     */  
    public void unSubcribe(ISubcriber subcriber) {  
        subcribers.remove(subcriber);  
    }  
      
    /**  
     * @Description: 发送存储队列所有消息  
     */  
    public void update(){  
        Msg m = null;  
        while((m = queue.peek())!= null){  
            this.update(m.getPublisher(),(M)m.getMsg());  
        }  
    }  
  
    /**  
     * @Description: 发送消息    
     */  
    public void update(String publisher,M Msg) {  
        for(ISubcriber subcriber:subcribers){  
            subcriber.update(publisher,Msg);  
        }         
    } 
    
//    public void run(String name,M message,boolean isInstantMsg){
//        KafkaProducer simpleProducer = new KafkaProducer();
//        simpleProducer.sendMsg(name,message.toString());
//    }
}  
  
    /**  
     * @Description: 消息类 
     */  
    class Msg<M>{   
        private String publisher;  
        private M m;  
          
        public Msg(String publisher, M m) {  
            this.publisher = publisher;  
            this.m = m;  
        }  
      
        public String getPublisher() {  
            return publisher;  
        }  
      
        public void setPublisher(String publisher) {  
            this.publisher = publisher;  
        }  
      
        public M getMsg() {  
            return m;  
        }  
      
        public void setMsg(M m) {  
            this.m = m;  
        }
}
