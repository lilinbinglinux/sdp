package com.sdp.servflow.pubandorder.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
//@EnableScheduling
public class TopicScheduling {
    @Autowired
    private TopicService TopicService;
    
    /***
     * 
     * Description: 对topic做心跳
     *  void
     *
     * @see
     */
    @Scheduled(cron = "0/60 * * * * ?")
    public void topicHeartbeat() {
        TopicService.heartbeat();
    }

}
