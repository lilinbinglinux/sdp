package com.sdp.servflow.logSer.flowControl.busi.flowControlStrategy;

public abstract class FlowControlStrategy {
	
	protected static long PER_SENCOND=1000;
	protected static long PER_MINUTE=PER_SENCOND*60;
	protected static long PER_HOUR=PER_MINUTE*60;
	protected static long PER_DAY=PER_HOUR*24;
    
	// 限流根据策略返回是否可以访问
	public abstract boolean access(String tenant_id,String order_id) throws Exception;
	
	public long returnInterval(String acc_freq_type){
    	if(acc_freq_type == null){
    		return 0;
    	}else if("0".equals(acc_freq_type)){
    		return PER_SENCOND;
    	}else if("1".equals(acc_freq_type)){
    		return PER_MINUTE;
    	}else if("2".equals(acc_freq_type)){
    		return PER_HOUR;
    	}else if("3".equals(acc_freq_type)){
    		return PER_DAY;
    	}else{
    		return 0; 
    	}
    }
}
