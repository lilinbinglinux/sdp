package com.sdp.servflow.logSer.flowControl.busi;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/** 
* @Title: RedisManager.java 
* @Description: redis连接池
*/ 
public class RedisManager {
	public static JedisPool jedisPool;
 	
	public static Jedis getJedis() throws Exception {
		if(null != jedisPool){
			return jedisPool.getResource();
		}
		throw new Exception("Jedispool was not init");
	}
	
}
