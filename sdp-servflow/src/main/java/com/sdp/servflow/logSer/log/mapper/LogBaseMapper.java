package com.sdp.servflow.logSer.log.mapper;

import org.apache.ibatis.annotations.Param;

import com.sdp.servflow.logSer.log.model.LogDictionaryBean;
import com.sdp.servflow.logSer.log.model.MethodLock;

public interface LogBaseMapper {

	int getInstanceGUID();

	LogDictionaryBean selectMaxDayData();

	void insertDictionaryData(LogDictionaryBean ldb);

	void registerMethodLock(MethodLock ml);

	int selectSumCapacotyMatchedTable();

	int selectCountSumYesterDay(
			 @Param(value = "tableName")String tableName, 
			 @Param(value = "yesterday")int yesterday);

	void updateLogDictionaryForYesCount(LogDictionaryBean ldb);

	void createNewLogTableAndAddPartition(
            @Param(value = "newTableName") String newTableName,
            @Param(value = "today") int today,
            @Param(value = "tomorrow") int tomorrow);
	
	void addNewPartition(
            @Param(value = "tableName") String tableName,
            @Param(value = "today") int today,
            @Param(value = "tomorrow") int tomorrow);
	
	String selectTodayPartition(
            @Param(value = "tableName") String tableName,
            @Param(value = "partitionName") String partitionName);

	LogDictionaryBean selectLastTwoRecordByLogDicTable();

	LogDictionaryBean selectDictionaryData(LogDictionaryBean ldb);
	
}
