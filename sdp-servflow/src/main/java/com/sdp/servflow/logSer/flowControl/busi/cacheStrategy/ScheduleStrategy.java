package com.sdp.servflow.logSer.flowControl.busi.cacheStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdp.servflow.logSer.flowControl.busi.CacheContext;

@Component
public class ScheduleStrategy extends CacheFlushStrategy{

	@Autowired
	private CacheContext cacheContext;
	
	@Override
	public void flushCache() {
		cacheContext.loadCache();
	}

}
