package com.sdp.servflow.logSer.flowControl.busi.flowControlStrategy;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdp.frame.util.StringUtil;
import com.sdp.servflow.logSer.InstanceParam;
import com.sdp.servflow.logSer.flowControl.busi.CacheContext;
import com.sdp.servflow.logSer.flowControl.busi.RedisManager;
import com.sdp.servflow.logSer.flowControl.model.CounterBean;
import com.sdp.servflow.logSer.util.ToolUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisNoScriptException;

@Component
public class RedisFCStrategy extends FlowControlStrategy{
	
	@Autowired
	private CacheContext cacheContext;
//	
//	@Autowired
//	private AppInitConfigBean appInitConfigBean;

	public static String SCRIPTSHA1;
	
	private Logger logger = LoggerFactory.getLogger(RedisFCStrategy.class);
	
	@Override
	public boolean access(String tenant_id, String order_id) throws Exception {
		if(StringUtil.isNotEmpty(tenant_id)
    			&& StringUtil.isNotEmpty(order_id)){
    		Jedis jedis=null;
    		if(CacheContext.flowControlContainer == null){
    			throw new NullPointerException("flowControlContainer is null, error!!!");
    		}
    		try {
    			String key=ToolUtils.getSpecialStitching(tenant_id,order_id);
    			CounterBean cb=CacheContext.flowControlContainer.get(key);
    			if(cb!=null){
    				long interval=returnInterval(cb.getAcc_freq_type());
    				if(interval == 0){
    					return true;
    				}
    				jedis=RedisManager.getJedis();
    				long result = (long) jedis.evalsha(SCRIPTSHA1, 1, cb.genBucketKey(key),
    						String.valueOf(System.currentTimeMillis()),
    						String.valueOf(cb.getAcc_freq()),
    						String.valueOf(interval));
    				if(result == 1l){
    					return true;
    				}else if(result == 2l){
    					return true;
    				}else if(result == 3l){
    					return false;
    				}else if(result == 4l){
    					return true;
    				}
    				return result == 1L;
    			}else{
    				// 缓存中找不到对象默认给true
        			return true;
    			}
    		} catch (JedisNoScriptException e) {
    			// 捕捉到此异常 可能是redis重启后 s SCRIPTSHA1 失效了 需要重新加载 再次重试
    			try {
    				loadScript();
    				return access(tenant_id,order_id);
    			} catch (Exception e1) {
    				e1.printStackTrace();
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    			throw e;
    		} finally{
    			if(jedis !=null){
    				jedis.close();
    			}
    		}
    		return false;
    	}else{
    		return false;
    	}
	}
	
	public void loadScript() throws Exception{
    	// 1.加载lua脚本到redis
    	String luaScript = readFile(InstanceParam.LUA_SCRIPT_NAME);
        String scriptSha1=RedisManager.getJedis().scriptLoad(luaScript);
		// 2.赋值lua脚本加载后的脚本id
        SCRIPTSHA1=scriptSha1;
    }
	
	public String readFile(String fileName){
    	InputStream input = null;
    	try {
    		input=Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    		StringBuilder sb=new StringBuilder();
	        byte[] byt = new byte[1024];
			int len = 0;
			while ((len = input.read(byt)) != -1) {
				sb.append(new String(byt, 0, len));
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(input!=null){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    	return null;
    }

	public void serLimitInit() throws Exception {
		// 初始化redis连接池
		redisConnectionPoolInit();
		// 加载服务流控限制
		serRateLimitLoad();
	}
	
	public void redisConnectionPoolInit() {
//		JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
//		jedisPoolConfig.setMaxIdle(appInitConfigBean.getRedis_maxIdle());
//		jedisPoolConfig.setMaxTotal(appInitConfigBean.getRedis_maxTotal());
//		RedisManager.jedisPool = new JedisPool(jedisPoolConfig,appInitConfigBean.getRedis_ip(),appInitConfigBean.getRedis_port());
//		logger.info("---> Redis Connection Pool Init !!! ");
	}
	
	public void serRateLimitLoad() throws Exception {
		// 1.加载
		loadScript();
    	logger.info("---> jedis.scriptLoad --- > scriptSha1 : {} ",SCRIPTSHA1);
    	cacheContext.flowControlInitConfig();
	}
}
