package com.sdp.servflow.logSer.log.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.servflow.logSer.log.mapper.LogRecordMapper;
import com.sdp.servflow.logSer.log.model.LogBean;

/** 
* @Title: LogRecordService.java 
* @Description: 操作 日志记录表 例如 log_1
*/ 
@Service
public class LogRecordService {
	
	@Autowired
	private LogRecordMapper logRecordMapper;
	
	public void insertBatchLog(String table, int GUID, int dayTime, List<LogBean> list) {
		logRecordMapper.insertBatchLog(table,GUID,dayTime,list);
	}
	
	public List<LogBean> selectSendLog(LogBean logBean, int startNum, int num) {
		// 1.先获取表名
		String tableName=logRecordMapper.getTableNameByData(logBean.getDayTime());
		if(tableName!=null && !"".equals(tableName)){
			Map<String, String> map = new HashMap<String, String>();
			
			map.put("tableName",tableName);
			map.put("orderid",logBean.getOrderid());
			map.put("logType",String.valueOf(logBean.getLogType()));
			map.put("tenant_id",logBean.getTenant_id());
			map.put("dayTime",String.valueOf(logBean.getDayTime()));
			map.put("code",logBean.getCode());
			map.put("startNum",String.valueOf(startNum));
			map.put("num",String.valueOf(num));
			map.put("startTimeStr", logBean.getStartTimeStr());
			map.put("endTimeStr", logBean.getEndTimeStr());
			return logRecordMapper.selectSendLog(map);
		}else{
			return null;
		}
	}
	
	public List<LogBean> selectLogDetail(LogBean logBean) {
		// 1.先获取表名
		String tableName=logRecordMapper.getTableNameByData(logBean.getDayTime());
		if(tableName!=null && !"".equals(tableName)){
			Map<String, String> map = new HashMap<String, String>();
			
			map.put("tableName",tableName);
			map.put("orderid",logBean.getOrderid());
			map.put("logType",String.valueOf(logBean.getLogType()));
			map.put("tenant_id",logBean.getTenant_id());
			map.put("dayTime",String.valueOf(logBean.getDayTime()));
			map.put("requestId", logBean.getRequestId());
			
			return logRecordMapper.selectLogDetail(map);
		}else{
			return null;
		}
	}
}
