package com.sdp.servflow.logSer.log.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sdp.servflow.logSer.log.model.LogBean;

public interface LogRecordMapper {
	
	void insertBatchLog(
            @Param(value = "tableName") String tableName,
            @Param(value = "GUID") int GUID,
            @Param(value = "dayTime") int dayTime,
            @Param(value = "list") List<LogBean> list);

	List<LogBean> selectSendLog(Map<String, String> map);

	List<LogBean> selectLogDetail(Map<String, String> map);

	String getTableNameByData(Integer dayTime);
	
}
