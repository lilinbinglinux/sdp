package com.sdp.servflow.pubandorder.orderServer.service;

import java.util.List;
import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sdp.servflow.kafka.KafkaConsumer;
import com.sdp.servflow.kafka.KafkaProducer;
import com.sdp.servflow.kafka.KafkaPropertiy;
import com.sdp.servflow.pubandorder.orderServer.Publish;
import com.sdp.servflow.pubandorder.orderServer.mapper.OrderInterfaceMapper;
import com.sdp.servflow.pubandorder.orderServer.model.PublishBo;
import com.sdp.servflow.pubandorder.orderServer.publish.Order;
import com.sdp.servflow.pubandorder.orderServer.publish.Releaser;
import com.sdp.servflow.pubandorder.orderapistore.model.OrderInterfaceBean;
import com.sdp.servflow.pubandorder.util.IContants;
import com.sdp.servflow.pubandorder.util.ThreadPoolUtil;

//import kafka.consumer.ConsumerIterator;
@Service
public class PublishIml implements Publish{
    
    
    @Autowired
    private KafkaProducer producer;
    @Autowired
    private OrderInterfaceMapper orderInterfaceMapper;
    @Autowired
    private KafkaConsumer consumer;
    
    
    private static final Logger logger = Logger.getLogger(Publish.class);
    
   

    
    @Override
    public void release(PublishBo publisher) {
        try {
        	  logger.info("publisher+发布者细信息"+publisher); 
            // 将信息发布到kafka中取（暂时使用一个topic，后期根据业务扩展到多个topic或者多个分组）
         /*   if(null != publisher) {
                
                String message = JSONObject.toJSONString(publisher);
                logger.info("发布者细信息"+message); 
                producer.sendMsg(KafkaPropertiy.getSingleStion().getTopicId(),message);
            }*/
        	  toOrder(publisher);
        	
        	
        }
        catch (Exception e) {
            logger.error(IContants.SYSTEM_ERROR_MSG, e);
        }
                
        
    }
    @Override
    public void toOrder(PublishBo publisher) {

        try {
            // 1.创建发布者
            Releaser releaser = new Releaser() ;  
            
            // 2.建立观察者和发布者之间的关系（循环）
            // TODO 从数据库中获取不同的bean信息 循环调用
            List<OrderInterfaceBean> orderInterfaces = orderInterfaceMapper.getOrderInterfaces(publisher);
            
            if(orderInterfaces != null && orderInterfaces.size() > 0) {
                
                for(OrderInterfaceBean orderBean :orderInterfaces) {
                    
                    Order observer = new Order(orderBean);  
                    observer.registerSubject(releaser);  
                    
                }
            }
            
            // 3.发布者发布信息()
            releaser.setPublisher(publisher);
        }
        catch (Exception e) {
            logger.error(IContants.SYSTEM_ERROR_MSG, e);
        }
    }
    
    
    
    @Override
    public void addConsumer() {
        //创建一个可重用线程数的线程池
           ExecutorService pool = ThreadPoolUtil.getSingleStion().getCachedThreadPool();
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        
//                        ConsumerIterator<String, String>  iterator   = consumer.consume(KafkaPropertiy.getSingleStion().getTopicId());
//                        //iterator.hasNext()
//                        while(true) {
//                            String message = iterator.next().message();
//                            System.out.println("-------接受到的message---"+message);
//                            try {   
//                                // 接受到一个任务后 开启一个线程去处理订阅
//                                final PublishBo    publisher = JSONObject.parseObject(message, PublishBo.class);
//                                ExecutorService threadPool = ThreadPoolUtil.getSingleStion().getCachedThreadPool();
//                                threadPool.execute(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        toOrder(publisher);
//                                        
//                                    }
//                                });
//                            }
//                            catch (Exception e) {
//                                e.printStackTrace();
//                                logger.error("格式转换错误",e);
//                            }
//                        }
                    }
                    catch (Exception e) {
                        logger.error("-kafka消费段异常",e);
                        //如果出现异常，1分钟后重启一个线程
                        try {
                            Thread.sleep(1*60*1000);
                        }
                        catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        if (consumer != null) {
                            logger.info("-----------kafka消费段异常   consumer.shutdown()");
//                            consumer.shutdown();
                            addConsumer();
                        }
                    }finally {
                        logger.info("------线程结束---");
                    }
                }
            });
    }
}
