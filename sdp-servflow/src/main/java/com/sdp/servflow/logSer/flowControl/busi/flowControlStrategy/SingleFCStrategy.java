package com.sdp.servflow.logSer.flowControl.busi.flowControlStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdp.frame.util.StringUtil;
import com.sdp.servflow.logSer.flowControl.busi.CacheContext;
import com.sdp.servflow.logSer.flowControl.model.CounterBean;
import com.sdp.servflow.logSer.util.ToolUtils;

@Component
public class SingleFCStrategy extends FlowControlStrategy{
	
	@Autowired
	private CacheContext cacheContext;

	@Override
	public boolean access(String tenant_id, String order_id) throws Exception {
		if(StringUtil.isNotEmpty(tenant_id)
    			&& StringUtil.isNotEmpty(order_id)){
    		String key=ToolUtils.getSpecialStitching(tenant_id,order_id);
    		if(CacheContext.flowControlContainer == null){
    			throw new NullPointerException("flowControlContainer is null, error!!!");
    		}
			CounterBean cb=CacheContext.flowControlContainer.get(key);
    		if(cb!=null){
    			if(cb.getAcc_freq() == 0 || cb.getAcc_freq_type() ==null){
    				return false;
    			}
    			synchronized(cb){
    				long now = System.currentTimeMillis();
    				long interval=returnInterval(cb.getAcc_freq_type());
    				if (now < cb.getLastAccessTime() + interval) {
    					// 在时间窗口内
    					int reqCount=cb.getReqCount();
    					cb.setReqCount(++reqCount);
    					// 判断当前时间窗口内是否超过最大请求控制数
    					return cb.getReqCount() <= cb.getAcc_freq();
    				} else {
    					cb.setLastAccessTime(now);
    					// 超时后重置
    					cb.setReqCount(1);
    					return true;
    				}
    			}
    		}else{
    			// 缓存中找不到对象默认给true
    			return true;
    		}
    	}else{
    		return false;
    	}
	}
	
	public void serLimitInit() throws Exception {
		cacheContext.flowControlInitConfig();
	}

}
