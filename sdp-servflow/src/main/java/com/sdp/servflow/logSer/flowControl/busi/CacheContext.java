package com.sdp.servflow.logSer.flowControl.busi;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdp.frame.util.StringUtil;
import com.sdp.servflow.logSer.flowControl.busi.cacheStrategy.ISTableStrategy;
import com.sdp.servflow.logSer.flowControl.busi.cacheStrategy.ScheduleStrategy;
import com.sdp.servflow.logSer.flowControl.busi.flowControlStrategy.RedisFCStrategy;
import com.sdp.servflow.logSer.flowControl.busi.flowControlStrategy.SingleFCStrategy;
import com.sdp.servflow.logSer.flowControl.model.CounterBean;
import com.sdp.servflow.logSer.flowControl.service.FlowControlService;
import com.sdp.servflow.logSer.util.ToolUtils;

@Component
public class CacheContext {
	
	@Autowired
	private FlowControlService flowControlService;
	
//	@Autowired
//	private AppInitConfigBean appInitConfigBean;
	
	@Autowired
	private ISTableStrategy iSTableStrategy;
	
	@Autowired
	private ScheduleStrategy scheduleStrategy;
	
	@Autowired
	private RedisFCStrategy redisFCStrategy;
	
	@Autowired
	private SingleFCStrategy singleFCStrategy;
	
	private Logger logger = LoggerFactory.getLogger(CacheContext.class);
	
	public static ConcurrentHashMap<String, CounterBean> flowControlContainer;
	
	public void flushCache(){
//		try {
//    		// cache_policy : 1 去infomation_schema查找
//    		// cache_policy : 2 定时全部刷新(分布式下选择)
//    		// cache_policy : 3 初始化后被通知调用(不在此处替代缓存)(只能单机版环境下使用该策略)
//    		if(appInitConfigBean.getCache_policy() == 1){
//    			iSTableStrategy.flushCache();
//    		}else if(appInitConfigBean.getCache_policy() == 2){
//    			scheduleStrategy.flushCache();
//    		}else if(appInitConfigBean.getCache_policy() == 3){
//    			// do nothing
//    		}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}	
	}
	
	/** 
    * @Title: loadCache 
    * @Description:该方法为全部刷新缓存时使用（考虑了数据的crud）
    */ 
    public void loadCache() {
    	ConcurrentHashMap<String, CounterBean> map=new ConcurrentHashMap<String, CounterBean>();
    	List<CounterBean> list=flowControlService.selectInitDate();
    	if(list!=null && list.size()>0){
    		for(CounterBean cb:list){
    			if(cb!=null){
    				if(StringUtil.isNotEmpty(cb.getTenant_id())
    						&& StringUtil.isNotEmpty(cb.getOrderid())
    						&& cb.getAcc_freq()!=0
    						&& StringUtil.isNotEmpty(cb.getAcc_freq_type())){
    					String key=ToolUtils.getSpecialStitching(cb.getTenant_id(),cb.getOrderid());
    					map.put(key, cb);
    				}
    			}
    		}
    	}
    	flowControlContainer= map;
    	logger.info("--- > flowControlContainer replacement, refresh cache");
    }
    
    
    public void addAndUpdateFlowControlRule(String tenant_id,String order_id,int acc_freq,String acc_freq_type){
//      	try {
//     		if(appInitConfigBean.getCache_policy() == 3){
//     			if(StringUtil.isNotEmpty(tenant_id)
//     					&& StringUtil.isNotEmpty(order_id)
//     					&& acc_freq!=0
//     					&& StringUtil.isNotEmpty(acc_freq_type)){
//     				String key=ToolUtils.getSpecialStitching(tenant_id,order_id);
//     				CounterBean cb=new CounterBean();
//     				cb.setTenant_id(tenant_id);
//     				cb.setOrderid(order_id);
//     				cb.setAcc_freq(acc_freq);
//     				cb.setAcc_freq_type(acc_freq_type);
//     				CacheContext.flowControlContainer.put(key, cb);
//     			}
//     		}
// 		} catch (Exception e) {
// 			e.printStackTrace();
// 		}
      }
    
    /** 
     * @Title: addSerIpLimit 
     * @Description: 单机版应用移除限流缓存 
     */ 
     public void removeFlowControlRule(String tenant_id,String order_id){
//     	try {
//     		if(appInitConfigBean.getCache_policy() == 3){
//     			if(StringUtil.isNotEmpty(tenant_id)
//     					&& StringUtil.isNotEmpty(order_id)){
//     				String key=ToolUtils.getSpecialStitching(tenant_id,order_id);
//     				CacheContext.flowControlContainer.remove(key);
//     			}
//     		}
// 		} catch (Exception e) {
// 			e.printStackTrace();
// 		}
     }
     
     public boolean access(String tenant_id,String order_id){
		return false;
//     	try {
//     		if(appInitConfigBean.getLimit_policy() == 2){
//     			return redisFCStrategy.access(tenant_id,order_id);
//     		}else{
//     			return singleFCStrategy.access(tenant_id,order_id);
//     		}
// 		} catch (Exception e) {
// 			e.printStackTrace();
// 			return false;
// 		}
     }

	public void serLimitInit() throws Exception {
//		if(appInitConfigBean.getLimit_policy() == 2){
//			redisFCStrategy.serLimitInit();
//		}else if(appInitConfigBean.getLimit_policy() == 1){
//			singleFCStrategy.serLimitInit();
//		}
	}
	
	public void flowControlInitConfig(){
		// 从数据库加载信息
//		if(appInitConfigBean.getCache_policy() == 1){
//			// 如果是去infomation_schema查找
//			ISTableStrategy.LAST_MODIFIED_TIME = flowControlService.selectLastModifiedTimeSerIpLimit(appInitConfigBean.getSchemaName());
//		}
//		loadCache();
	}
}
