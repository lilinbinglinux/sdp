package com.sdp.servflow.pubandorder.init.sysConfig;

import com.sdp.servflow.kafka.KafkaPropertiy;

/**
 * 
 *  项目的配置信息
 *
 * @author 任壮
 * @version 2017年7月27日
 * @see SysConfigSer
 * @since
 */
public interface SysConfigSer {
    
    /**
     * 同步码表信息
     * */
    void syncodeTable();
    /**
     * 获取kafka的信息
     * */
    KafkaPropertiy getKafka();
    /**
     * 更新kafka的信息
     * */
    int updateKafka(KafkaPropertiy kafka);
}
