package com.sdp.servflow.logSer.log.busi.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sdp.servflow.logSer.log.busi.ser.DailyTaskService;

@Component
public class DailyTasks {
	
	@Autowired
	private DailyTaskService dailyTaskService;
	
	/** 
	* @Title: run 
	* @Description: 每晚0点  首先实例抢先注册，若抢到，则判断表容量是否超量，若已经超量，则更换表保存的表名称
	* fkr
	* 2017年11月15日
	*/ 
	@Scheduled(cron="0 0 0 * * ? ")
    public void run1(){
		dailyTaskService.run1();
	}
	
}
