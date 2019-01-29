package com.sdp.servflow.kafka.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

//import kafka.common.FailedToSendMessageException;
//import kafka.javaapi.producer.Producer;
//import kafka.producer.KeyedMessage;
//import kafka.producer.ProducerConfig;

/**
 * @Note Kafka JProducer
 */
@Component
@Configurable
public class JProducer {
//	private Producer<String, String> producer;
//	
//	public JProducer() {
//		InputStream input = null;
//		try {
//		    Properties props = new Properties();
//	        props.put("metadata.broker.list", "127.0.0.1:9092");
//	        props.put("serializer.class", "kafka.serializer.StringEncoder");
//	        props.put("key.serializer.class", "kafka.serializer.StringEncoder");
//	        props.put("request.required.acks", "0");
//	        producer = new Producer<String, String>(new ProducerConfig(props));
//		} finally{
//			if(input!=null){
//				try {
//					input.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//	
//	public void sendList(List<KeyedMessage<String, String>> keyMeslist) throws FailedToSendMessageException{
//		try {
//		    System.out.println("----------keyMeslist----------");
//			producer.send(keyMeslist);
//		} catch(FailedToSendMessageException e1){
//			throw e1;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void sendSingle(String topic, String jsonstr) throws FailedToSendMessageException{
//		try {
//			producer.send(new KeyedMessage<String, String>(topic, jsonstr));
//		} catch(FailedToSendMessageException e1){
//			throw e1;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}