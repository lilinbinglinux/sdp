package com.sdp.servflow.pubandorder.task.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.servflow.kafka.KafkaPropertiy;
import com.sdp.servflow.pubandorder.init.InitConsumer;
import com.sdp.servflow.pubandorder.init.sysConfig.SysConfigSer;
import com.sdp.servflow.pubandorder.task.TopicService;

@Service
public class TopicServiceImpl implements TopicService{
    private static final Logger logger = Logger.getLogger(TopicServiceImpl.class);
    @Autowired
    private SysConfigSer sysConfigSer;
    @Autowired
    private InitConsumer initConsumer;
    @Override
    public void heartbeat() {

        
        try {
            KafkaPropertiy property = KafkaPropertiy.getSingleStion();
            //如果当前的信息为空，初始化这个信息
            if(property==null || property.getTopicId()== null || property.getTopicId().isEmpty()) {
                 String[] args = new String[0];
                 initConsumer.run(args);
            }else{
                // 更新topic的时间
                int updateCount =  sysConfigSer.updateKafka(property);
                logger.info("更新topic"+property.getTopicId()+"的时间,更新的记录数"+updateCount);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("更新tipic的定时器出现异常", e);
        }
        
        
      
    
        
    }

}
