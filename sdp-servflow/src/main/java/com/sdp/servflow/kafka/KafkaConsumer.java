package com.sdp.servflow.kafka;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sdp.servflow.pubandorder.util.ConfigUtil;

//import kafka.consumer.ConsumerConfig;
//import kafka.consumer.ConsumerIterator;
//import kafka.consumer.KafkaStream;
//import kafka.javaapi.consumer.ConsumerConnector;
//import kafka.producer.ProducerConfig;
//import kafka.serializer.StringDecoder;
//import kafka.utils.VerifiableProperties;

@Component
@Scope("prototype")
public class KafkaConsumer {

	private static final Logger logger = Logger.getLogger(KafkaConsumer.class);
//	private ConsumerConnector consumer = null;
//
//
//	public KafkaConsumer() {
//
//	
//		System.out.println("---------consume初始化------" + hashCode());
//		// ConsumerConfig config = new ConsumerConfig(props);
//		/*
//		 * props.put("zookeeper.connect", "192.168.50.77:2181");
//		 * //props.put("zookeeper.connect", "127.0.0.1:2181");
//		 * 
//		 * //group 代表一个消费组 props.put("group.id", "jd-group");
//		 * 
//		 * //zk连接超时 // props.put("zookeeper.session.timeout.ms", "4000");
//		 * //props.put("zookeeper.sync.time.ms", "200");
//		 * //props.put("auto.commit.interval.ms", "1000");
//		 * //props.put("auto.offset.reset", "smallest"); //序列化类
//		 * props.put("serializer.class", "kafka.serializer.StringEncoder");
//		 */
//
//		// TODO 有kafka时候打开连接
//		// consumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);
//	}
//
//	public ConsumerIterator<String, String> consume(String topic) {
//		// group 代表一个消费组
//		// props.put("group.id", groupId);  ProducerConfig config = new ProducerConfig(ConfigUtil.getKafkaProperties()); 
//		ConsumerConfig config = new ConsumerConfig(ConfigUtil.getKafkaProperties());
//		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);
//
//		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
//		topicCountMap.put(topic, new Integer(1));
//
//		StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
//		StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());
//
//		Map<String, List<KafkaStream<String, String>>> consumerMap = consumer.createMessageStreams(topicCountMap,
//				keyDecoder, valueDecoder);
//		KafkaStream<String, String> stream = consumerMap.get(topic).get(0);
//		ConsumerIterator<String, String> it = stream.iterator();
//		return it;
//	}
//
//	public static void main(String[] args) {
//		try {
//
//			ConsumerIterator<String, String> it = new KafkaConsumer().consume("kafkaTest");
//			while (true) {
//				System.out.println("--消费出的是--" + it.next().message());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void shutdown() {
//		consumer.shutdown();
//	}

}
