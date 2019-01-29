package com.sdp.servflow.logSer;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdp.servflow.logSer.flowControl.busi.CacheContext;
import com.sdp.servflow.logSer.flowControl.busi.FlowControlManager;
import com.sdp.servflow.logSer.flowControl.busi.RedisManager;
import com.sdp.servflow.logSer.log.busi.ser.LogManage;
import com.sdp.servflow.logSer.log.exception.DataMissingException;
import com.sdp.servflow.logSer.log.model.LogBean;
import com.sdp.servflow.logSer.serIpLimit.busi.SerIpLimitManager;

import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/TBtest")
public class Test1Controller {
	
	@Autowired
	private LogManage logManage;
	
	@Autowired
	private SerIpLimitManager serIpLimitManager;
	
	@Autowired
	private FlowControlManager flowControlManager;
	
    @RequestMapping(value = "/add", method = RequestMethod.POST)
	public String test(@RequestBody LogBean lb) throws InterruptedException, DataMissingException{
    	System.out.println("123123");
		if(lb!=null){
			lb.setStartTime(new Timestamp(System.currentTimeMillis()));
			Thread.sleep(52);
			lb.setEndTime(new Timestamp(System.currentTimeMillis()));
			logManage.addLog(lb);
		}
		return "添加成功";
	}
    
    @RequestMapping(value = "/ipTest", method = RequestMethod.POST)
	public String test2(@RequestBody JSONObject job) throws Exception{
    	String tenant_id=job.getString("tenant_id");
    	String order_id=job.getString("order_id");
    	String ip=job.getString("ip");
    	boolean flag=serIpLimitManager.judgeIsAWhiteList(tenant_id,order_id,ip);
    	return flag+"";
	}
    
    @RequestMapping(value = "/ipTestOperation", method = RequestMethod.POST)
	public String test6(@RequestBody JSONObject job) throws Exception{
    	String caozuo=job.getString("caozuo");
    	if(caozuo!=null && !"".equals(caozuo)){
    		String tenant_id=job.getString("tenant_id");
    		String order_id=job.getString("order_id");
    		if(caozuo.equals("add")){
    			String name_type=job.getString("name_type");
    			String ip_addrs=job.getString("ip_addrs");
    			String Separator=job.getString("Separator");
    			serIpLimitManager.addSerIpLimitRule(tenant_id,order_id,name_type,ip_addrs,Separator);
    		}else if(caozuo.equals("remove")){
    			serIpLimitManager.removeSerIpLimitRule(tenant_id, order_id);
    		}
    	}
    	return "";
	}
    
    @RequestMapping(value = "/redisTest", method = RequestMethod.POST)
	public String test3(@RequestBody JSONObject job) throws Exception{
    	Jedis j=RedisManager.getJedis();
    	return "接收到了";
	}
    
    @RequestMapping(value = "/ratelimitTestOperation", method = RequestMethod.POST)
	public String test4(@RequestBody JSONObject job) throws Exception{
    	String caozuo=job.getString("caozuo");
    	String tenant_id=job.getString("tenant_id");
    	String order_id=job.getString("order_id");
    	if(caozuo!=null && !"".equals(caozuo)){
    		int acc_freq=Integer.valueOf(job.getString("acc_freq"));
    		String acc_freq_type=job.getString("acc_freq_type");
    		flowControlManager.addFlowControlRule(tenant_id, order_id, acc_freq, acc_freq_type);
    	}else if(caozuo.equals("remove")){
    		flowControlManager.removeFlowControlRule(tenant_id, order_id);
    	}
		return "";
	}
    
    @RequestMapping(value = "/ratelimitTest", method = RequestMethod.POST)
   	public String test7(@RequestBody JSONObject job) throws Exception{
   		String tenant_id=job.getString("tenant_id");
   		String order_id=job.getString("order_id");
   		boolean flag = false;
   		flag = flowControlManager.access(tenant_id,order_id);
   		return flag+"";
   	}
    
    @RequestMapping(value = "/returnCache", method = RequestMethod.GET)
   	public Map<String, String> returnCache() throws Exception{
    	Map<String, String> map= new HashMap<String, String>();
    	map.put("flowControlContainer", JSON.toJSONString(CacheContext.flowControlContainer));
    	map.put("serIpLimitContainer", JSON.toJSONString(SerIpLimitManager.serIpLimitContainer));
    	return map;
   	}
    
    @RequestMapping(value = "/test5", method = RequestMethod.GET)
	public void test5() throws Exception{
    	System.out.println("111");
//    	flowControlManager.loadScript();
	}
}
