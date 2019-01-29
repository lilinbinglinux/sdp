package com.sdp.servflow.logSer;

public class InstanceParam {
	
	// 分布式系统里  唯一标识号
	public static int DISTRIBUTED_GUID; 
	
	// 程序初始化完成标识
	public static boolean APP_START_FLAG;
	
	// 当前日志保存的 表name
	public static String CURRENT_TABLE_NAME;
	
	// 日志表前缀
	public static final String TABLE_NAME_PREFIX="log_";
	
	// 日志表 表容量最大上限
	public static final int TABLE_CAPACITY=1000000;
	
	// 白名单 内存中 map容量大小
	public static final int WHITE_IP_MAP_SIZE=1024;
	
	// lua脚本名称 -- 计数器 限流脚本
	public static final String LUA_SCRIPT_NAME="counter_rate_limiter.lua";
	// 类 令牌桶限流 脚本
	//	public static final String LUA_SCRIPT_NAME="token_bucket_rate_limiter.lua";
	
	public static final String SPECIALCHAR="@_@";
}
