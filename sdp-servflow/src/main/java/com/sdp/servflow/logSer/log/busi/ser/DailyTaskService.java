package com.sdp.servflow.logSer.log.busi.ser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import com.sdp.servflow.logSer.InstanceParam;
import com.sdp.servflow.logSer.log.model.LogDictionaryBean;
import com.sdp.servflow.logSer.log.model.MethodLock;
import com.sdp.servflow.logSer.log.service.LogBaseService;

@Component
public class DailyTaskService {
	
	@Autowired
	private LogBaseService logBaseService;

	private Logger logger = LoggerFactory.getLogger(DailyTaskService.class);
	
	public void run1() {
		try {
			int today = Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date()));
			try {
				// 1.首先实例抢先注册，若抢到，则执行后续任务
				MethodLock ml=new MethodLock();
				ml.setInstanceId(InstanceParam.DISTRIBUTED_GUID);
				ml.setMethodName("judgeTableCapacity");
				ml.setMethodDesc("每晚0时定时任务---争抢到的实例判断最新保存的日志表的表容量是否已达上限");
				ml.setUpdateTime(today);
				
				logBaseService.registerMethodLock(ml);
				logger.info("---> This instance(DISTRIBUTED_GUID--{}) register MethodLock succeeded for judgeTableCapacity !!! ",InstanceParam.DISTRIBUTED_GUID);
			} catch (DuplicateKeyException e) {
				logger.info("---> Other instances are already  for judgeTableCapacity !!! ");
				// 1.5若其他实例抢到注册了该方法，则无需再向下执行
				return;
			}
			
			// 2.统计前一天服务表中的发送量之和，更新到字典表中
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			int yesterday = Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(cal.getTime()));
			int countSumYesterDay = logBaseService.selectCountSumYesterDay(InstanceParam.CURRENT_TABLE_NAME,yesterday);
			
			// 3.去字典表昨天对应的昨天日期与表添加该总量之和信息
			LogDictionaryBean ldb = new LogDictionaryBean();
			ldb.setDayDateTime(yesterday);
			ldb.setDayTotalCount(countSumYesterDay);
			logBaseService.updateLogDictionaryForYesCount(ldb);
			
			// 4.判断表容量是否超量，若已经超量，则更换表保存的表名称
			int tableCapacoty = logBaseService.selectSumCapacotyMatchedTable();
			
			// 4.1 获取下一天的日期
			Calendar cal2 = Calendar.getInstance();
			cal2.add(Calendar.DATE, 1);
			int tomorrow = Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(cal2.getTime()));
			// 4.2 首先获取最新详情记录表的名称
			LogDictionaryBean ldb2 = logBaseService.selectMaxDayData();
			String tableName = ldb2.getTableName();
			
			logger.info("---> The table is {}, yesterday total is {}, all total is {} !!! ",tableName,countSumYesterDay,tableCapacoty);
			
			if(tableCapacoty>InstanceParam.TABLE_CAPACITY){
				logger.info("---> Old table has been excessive, Will Create a new table !!! ");
				
				// 4.3如果表容量已达到上限，则换表更新
				int logNum = Integer.valueOf(tableName.replaceAll(InstanceParam.TABLE_NAME_PREFIX, ""));
				String newTableName = InstanceParam.TABLE_NAME_PREFIX + (++logNum);
				
				// 4.4创建新表 + 创建分区
				logBaseService.createNewLogTableAndAddPartition(newTableName,today,tomorrow);
				
				// 4.5新表更换完毕，字典表注册新表
				LogDictionaryBean ldb3=new LogDictionaryBean();
				ldb3.setDayDateTime(today);
				ldb3.setTableName(newTableName);
				logBaseService.insertDictionaryData(ldb3);
				logger.info("---> This instance register LogDictionary : dayDateTime : {} , tableName : {} successed !!!",today,newTableName);
				
				// 4.6创建了新表后，即改变了今天存储日志表的表名
				InstanceParam.CURRENT_TABLE_NAME = newTableName;
				logger.info("---> A new table has been created, Name -->{} , Partition --> {}",newTableName,"p"+today);
			}else{
				// 4.7如果没达到上限，则添加分区，分区以p+yyyymmdd命名
				logBaseService.addNewPartition(tableName,today,tomorrow);
				logger.info("---> Old table is not excessive.And alter -->{} add Partition -->{}",tableName,"p"+today);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("---> cron=\"0 * * * * ? \" Execution failed !!! ");
		}
	}

}
