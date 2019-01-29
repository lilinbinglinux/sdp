package com.sdp.servflow.logSer.log.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.servflow.logSer.log.mapper.LogBaseMapper;
import com.sdp.servflow.logSer.log.model.LogDictionaryBean;
import com.sdp.servflow.logSer.log.model.MethodLock;

/** 
* @Title: LogBaseService.java 
* @Description: 操作 日志字典表 log_dictionary操作 及 管理日志记录表等
*/ 
@Service
public class LogBaseService {
	
	@Autowired
	private LogBaseMapper logBaseMapper;

	public int getInstanceGUID() {
		return logBaseMapper.getInstanceGUID();
	}
	
	public LogDictionaryBean selectMaxDayData() {
		return logBaseMapper.selectMaxDayData();
	}
	
	public void insertDictionaryData(LogDictionaryBean ldb) {
		logBaseMapper.insertDictionaryData(ldb);
	}
	
	public void registerMethodLock(MethodLock ml) {
		logBaseMapper.registerMethodLock(ml);
	}
	
	public int selectSumCapacotyMatchedTable() {
		return logBaseMapper.selectSumCapacotyMatchedTable();
	}
	
	public int selectCountSumYesterDay(String tableName,int yesterday) {
		return logBaseMapper.selectCountSumYesterDay(tableName,yesterday);
	}
	
	public void updateLogDictionaryForYesCount(LogDictionaryBean ldb) {
		logBaseMapper.updateLogDictionaryForYesCount(ldb); 
	}
	
	public void createNewLogTableAndAddPartition(String newTableName, int today, int tomorrow) {
		logBaseMapper.createNewLogTableAndAddPartition(newTableName,today,tomorrow);
	}
	
	public void addNewPartition(String tableName, int today, int tomorrow) {
		logBaseMapper.addNewPartition(tableName,today,tomorrow);
	}
	
	public String selectTodayPartition(String tableName,String partitionName) {
		return logBaseMapper.selectTodayPartition(tableName,partitionName);
	}
	
	public LogDictionaryBean selectLastTwoRecordByLogDicTable() {
		return logBaseMapper.selectLastTwoRecordByLogDicTable();
	}
	
	public LogDictionaryBean selectDictionaryData(LogDictionaryBean ldb) {
		return logBaseMapper.selectDictionaryData(ldb);
	}

}
