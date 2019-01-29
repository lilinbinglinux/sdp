package com.sdp.servflow.logSer.flowControl.busi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FlowControlTasks {
	
	@Autowired
	private FlowControlManager flowControlManager; 
	
	@Scheduled(cron="0 * * * * ? ")
	public void run(){
		flowControlManager.replaceCache();
	}
}
