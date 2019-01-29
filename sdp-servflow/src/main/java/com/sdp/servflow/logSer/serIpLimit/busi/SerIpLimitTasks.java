package com.sdp.servflow.logSer.serIpLimit.busi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SerIpLimitTasks {
	
	@Autowired
	private SerIpLimitManager serIpLimitManager;
	
	@Scheduled(cron="0 * * * * ? ")
	public void replaceCache(){
		serIpLimitManager.replaceCache();
	}
	
}
