package com.sdp.servflow.pubandorder.init;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.sdp.servflow.kafka.KafkaPropertiy;
import com.sdp.servflow.pubandorder.init.sysConfig.SysConfigSer;
import com.sdp.servflow.pubandorder.orderServer.Publish;
/***
 * 
 * 项目初始化信息同步
 *
 * @author 任壮
 * @version 2017年12月1日
 * @see InitConsumer
 * @since
 */
@Component()
@Order(value=2)
public class InitConsumer implements CommandLineRunner {
    private static final Logger logger = Logger.getLogger(InitConsumer.class);
    @Autowired
    private SysConfigSer sysConfigSer;
    @Autowired
    private Publish publish;
    //由于Springboot默认的是单例，所以用属性值加锁就足够了
    private Object topicLock = new Object();
    @Override
    public void run(String... args) throws Exception {
        try {
            synchronized (topicLock) {
            KafkaPropertiy property = sysConfigSer.getKafka();
            //TODO 判断启动kafka信息. 如果启动不成功，等待去启动
            int updateCout =  sysConfigSer.updateKafka(property);
            if(updateCout>0) {
                KafkaPropertiy.getSingleStion().setSingleStion(property);
                logger.info("---当前实例占用的topic信息--"+KafkaPropertiy.getSingleStion().getTopicId());
              //  publish.addConsumer();
            }else{
                logger.error("没有读取到kafka信息");
            }
            
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
      
    }
    
    
}
