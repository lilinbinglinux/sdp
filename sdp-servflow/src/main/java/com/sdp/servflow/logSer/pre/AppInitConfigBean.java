package com.sdp.servflow.logSer.pre;
//package com.sdp.servflow.logSer.pre;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//
//@ConfigurationProperties(prefix = "logAndFlowControl",ignoreUnknownFields = false)
//public class AppInitConfigBean {
//	// 限流策略 1 单机版  2 分布式版
//	private int limit_policy;
//	
//	// 缓存刷新策略（涉及黑白名单与限流名单的加载） 1 去infomation_schema查找  2.定时全部刷新
//	private int cache_policy;
//	
//	// redis相关配置
//	private String redis_ip;
//	private int redis_port;
//	private int redis_maxIdle;
//	private int redis_maxTotal;
//	
//	// 项目初始配置
//	private String schemaName;
//	
//	public int getLimit_policy() {
//		return limit_policy;
//	}
//	public void setLimit_policy(int limit_policy) {
//		this.limit_policy = limit_policy;
//	}
//	
//	public String getRedis_ip() {
//		return redis_ip;
//	}
//	public void setRedis_ip(String redis_ip) {
//		this.redis_ip = redis_ip;
//	}
//	public int getRedis_port() {
//		return redis_port;
//	}
//	public void setRedis_port(int redis_port) {
//		this.redis_port = redis_port;
//	}
//	public int getRedis_maxIdle() {
//		return redis_maxIdle;
//	}
//	public void setRedis_maxIdle(int redis_maxIdle) {
//		this.redis_maxIdle = redis_maxIdle;
//	}
//	public int getRedis_maxTotal() {
//		return redis_maxTotal;
//	}
//	public void setRedis_maxTotal(int redis_maxTotal) {
//		this.redis_maxTotal = redis_maxTotal;
//	}
//	public String getSchemaName() {
//		return schemaName;
//	}
//	public void setSchemaName(String schemaName) {
//		this.schemaName = schemaName;
//	}
//	public int getCache_policy() {
//		return cache_policy;
//	}
//	public void setCache_policy(int cache_policy) {
//		this.cache_policy = cache_policy;
//	}
//	
//}
