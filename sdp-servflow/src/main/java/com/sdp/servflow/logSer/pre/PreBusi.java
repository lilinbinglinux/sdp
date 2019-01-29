package com.sdp.servflow.logSer.pre;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import com.sdp.servflow.logSer.InstanceParam;
import com.sdp.servflow.logSer.flowControl.busi.FlowControlManager;
import com.sdp.servflow.logSer.log.exception.DatabaseException;
import com.sdp.servflow.logSer.log.exception.InitErrorException;
import com.sdp.servflow.logSer.log.model.LogDictionaryBean;
import com.sdp.servflow.logSer.log.service.LogBaseService;
import com.sdp.servflow.logSer.serIpLimit.busi.SerIpLimitManager;
import com.sdp.servflow.logSer.serIpLimit.service.SerIpLimitService;

//@Component
public class PreBusi {

	private Logger logger = LoggerFactory.getLogger(PreBusi.class);
	
	@Autowired
	private LogBaseService logBaseService;
	
	@Autowired
	private SerIpLimitService serIpLimitService;
	
//	@Autowired
//	private AppInitConfigBean appInitConfigBean;
	
	@Autowired
	private SerIpLimitManager serIpLimitManager;
	
	@Autowired
	private FlowControlManager flowControlManager;
	
	/** 
	* @Title: assigningGUID 
	* @Description: 获取该实例在分布式系统中的唯一标识 
	* 2017年11月10日
	*/ 
	/*public void assigningGUID() throws DatabaseException {
		try {
			InstanceParam.DISTRIBUTED_GUID=logBaseService.getInstanceGUID();
			logger.info("---> This instance get the DISTRIBUTED_GUID is {}", InstanceParam.DISTRIBUTED_GUID);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("---> get DISTRIBUTED_GUID ERROR !!!");
			throw new DatabaseException(e.toString());
		}
	}*/
	
	/** 
	* @Title: checkLogDictionaryTable 
	* @Description: 从日志字典表里获取最大日期对应的表
	* 2017年11月10日
	*/ 
	public void checkLogDictionaryTable() throws InitErrorException {
		try {
			LogDictionaryBean ldb=logBaseService.selectMaxDayData();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			
			Integer dayDate = Integer.valueOf(sdf.format(new Date()));
			String tableName=null;
			
			// 是否添加分区标识符
			boolean flag=false;
			if(null == ldb){
				// 为空代表字典表无数据，初始化数据
				tableName = InstanceParam.TABLE_NAME_PREFIX+1;
				flag = true;
			}else{
				if(null == ldb.getTableName() || "".equals(ldb.getTableName())){
					throw new InitErrorException("init Error!!!");
				}else{
					// 判断最大日期  与  当天是否相等， 若不相等，则更改标识符
					// 注意 日期已超出Integer常量池范围,需要使用equals方法
					if(!dayDate.equals(ldb.getDayDateTime())){
						flag = true;
					}
					tableName = ldb.getTableName();
				}	
			}	
			
			if(flag){
				// 若标识符为true  则  1.在最新详情记录表中添加今天的分区  2.注册字典表
				
				LogDictionaryBean ldb2=new LogDictionaryBean();
				ldb2.setTableName(tableName);
				ldb2.setDayDateTime(dayDate);
				
				// 获取下一天日期
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, 1);
				int tomorrow = Integer.valueOf(sdf.format(cal.getTime()));
				
				// 添加分区
				addPartitionsForLogTable(tableName,dayDate,tomorrow);
				
				// 注册字典表
				registerLogDictionaryData(ldb2);
				
			}
			InstanceParam.CURRENT_TABLE_NAME=tableName;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("---> get DISTRIBUTED_GUID ERROR !!!");
			throw new InitErrorException(e.toString());
		}
	}
	
	public void checkLastTwoRecordByLogDicTable() throws InitErrorException {
		try {
			//1.判断字典表中，是否有前一个记录（即倒数第二个）
			LogDictionaryBean ldb=logBaseService.selectLastTwoRecordByLogDicTable();
			if(ldb!=null){
				//2.如果有，则判断前一个记录是否统计了日总量
				if(ldb.getDayTotalCount() == null){
					//3.若没统计，则统计并记录
					int lastTwoDayCount = logBaseService.selectCountSumYesterDay(ldb.getTableName(),ldb.getDayDateTime());
					ldb.setDayTotalCount(lastTwoDayCount);
					logBaseService.updateLogDictionaryForYesCount(ldb);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("---> check last two record by logDicTable ERROR !!!");
			throw new InitErrorException(e.toString());
		}
	}

	private void addPartitionsForLogTable(String tableName, Integer dayDate, int tomorrow) {
		try {
			// 1.首先先判断分区是否已经添加
			String partitionName = logBaseService.selectTodayPartition(tableName,"p"+dayDate);
			if(null == partitionName || "".equals(partitionName)){

				// 2.如果没有添加，则添加上今天的分区
				logBaseService.addNewPartition(tableName,dayDate,tomorrow);
				logger.info("---> This instances added partitions successed !!! ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("---> Other instances had added partitions !!! ");
		}
	}

	private void registerLogDictionaryData(LogDictionaryBean ldb) throws DatabaseException {
		try {
			LogDictionaryBean ldb2=logBaseService.selectDictionaryData(ldb);
			if(ldb2 == null){
				logBaseService.insertDictionaryData(ldb);
				logger.info("---> This instance(DISTRIBUTED_GUID--{}) register LogDictionary succeeded !!! ",InstanceParam.DISTRIBUTED_GUID);
			}
		} catch (DuplicateKeyException e) {
			logger.info("---> Other instances are already registered !!! ");
		} catch (Exception e) {
			throw new DatabaseException(e.toString());
		}
	}

	public void serIpLimitInit() throws DatabaseException {
		try {
			// 1.加载白名单表最近一次修改的时间
//			if(appInitConfigBean.getCache_policy() == 1){
//				SerIpLimitManager.LAST_MODIFIED_TIME = serIpLimitService.selectLastModifiedTimeSerIpLimit(appInitConfigBean.getSchemaName());
//			}
			
			// 2.加载白名单里的白名单配置
			serIpLimitManager.loadCache();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(e.toString());
		}
	}

	public void serLimitInit() throws Exception {
		flowControlManager.serLimitInit();
	}

}
