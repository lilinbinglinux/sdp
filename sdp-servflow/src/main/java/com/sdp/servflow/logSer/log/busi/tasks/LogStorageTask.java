package com.sdp.servflow.logSer.log.busi.tasks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sdp.servflow.logSer.InstanceParam;
import com.sdp.servflow.logSer.log.busi.ser.LogStorageService;
import com.sdp.util.SpringUtil;

@Component
public class LogStorageTask {
	
	private ExecutorService executorService=Executors.newSingleThreadExecutor();
	
	/** 
	* @Title: LogStorageTask.java 
	* @Description:访问日志每1min批量入库 
	* fkr 
	* 2017年11月9日
	*/ 
	@Scheduled(cron="0/30 * * * * ? ")
    public void run(){
		dealBusi();
	}
	
	private void dealBusi() {
		try {
			if(InstanceParam.APP_START_FLAG){
				executorService.execute(new StorageLogRun1());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class StorageLogRun1 implements Runnable{

		public void run() {
			SpringUtil.getBean(LogStorageService.class).dealBusi();
		}
		
	}
	
}
