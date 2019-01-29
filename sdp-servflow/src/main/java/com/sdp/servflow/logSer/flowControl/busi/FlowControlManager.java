package com.sdp.servflow.logSer.flowControl.busi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlowControlManager {
	
	@Autowired
	private CacheContext cacheContext;
	
    /** 
    * @Title: addSerIpLimit 
    * @Description: 单机版应用添加限流缓存 
    */ 
    public void addFlowControlRule(String tenant_id,String order_id,int acc_freq,String acc_freq_type){
    	cacheContext.addAndUpdateFlowControlRule(tenant_id,order_id,acc_freq,acc_freq_type);
    }
    
    /** 
     * @Title: addSerIpLimit 
     * @Description: 单机版应用修改限流缓存 
     */ 
     public void updateFlowControlRule(String tenant_id,String order_id,int acc_freq,String acc_freq_type){
    	 cacheContext.addAndUpdateFlowControlRule(tenant_id,order_id,acc_freq,acc_freq_type);
     }
     
    /** 
     * @Title: addSerIpLimit 
     * @Description: 单机版应用移除限流缓存 
     */ 
     public void removeFlowControlRule(String tenant_id,String order_id){
    	 cacheContext.removeFlowControlRule(tenant_id, order_id);
     }
    
    public boolean access(String tenant_id,String order_id){
    	return cacheContext.access(tenant_id, order_id);
    }
    
    public void replaceCache() {
    	cacheContext.flushCache();
    }

	public void serLimitInit() throws Exception {
		cacheContext.serLimitInit();
	}
    
}
