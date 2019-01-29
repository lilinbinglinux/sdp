package com.sdp.servflow.kafka;

import java.io.IOException;

import java.io.InputStream;
import java.util.Properties;
 
//import kafka.javaapi.producer.Producer;
//import kafka.producer.KeyedMessage;
//import kafka.producer.ProducerConfig;
 
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sdp.servflow.pubandorder.util.ConfigUtil;
@Component
public class KafkaProducer {
//    private static final Logger logger = Logger.getLogger(KafkaProducer.class);
//    Producer<String, String> procuder;
//    
//    
//    public KafkaProducer() {
//         
//        ProducerConfig config = new ProducerConfig(ConfigUtil.getKafkaProperties()); 
//         
//        logger.info("set config info(" + config + ") ok.");
//         
//        procuder = new Producer<>(config);
//    }
//       public static void main(String[] args) {
//            try {
//                KafkaProducer producer = new KafkaProducer();
//                for(int i =0;i<100;i++){
//                    
//                    producer.sendMsg("kafkaTest", "您好，这是一条测试信息"+i);
//                }
//            }
//            catch (Exception e) {
//                // TODO: handle exception
//            }
       /*    Properties props = new Properties();
           props.put("bootstrap.servers", "115.28.65.149:2181");
           props.put("metadata.broker.list", "172.16.23.32:9092,172.16.23.36:9092,172.16.23.37:9092");
           props.put("acks", "all");
           props.put("retries", 0);
           props.put("batch.size", 16384);
           props.put("linger.ms", 1);
           props.put("buffer.memory", 33554432);
           props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
           props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
           
           ProducerConfig config = new ProducerConfig(props); 
           Producer<String, String>   procuder = new Producer<>(config);
           for(int i = 0; i < 100; i++)
               procuder.send(new KeyedMessage<String, String>("kafkaTest","测试信息789"));
   
           procuder.close();*/
       }
    
//    public void sendMsg(String topic, String message){
//        procuder.send(new KeyedMessage<String, String>(topic, message));
//        logger.info("send message over.");
//    }
//}
