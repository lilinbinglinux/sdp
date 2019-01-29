package com.sdp.servflow.logSer.log.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.servflow.logSer.log.mapper.LogSerTotalStatisticsMapper;
import com.sdp.servflow.logSer.log.model.Ser_Id_Version_Statistics;

/** 
* @Title: LogSerTotalStatisticsService.java 
* @Description: 操作 服务记录统计表 log_ser_id_version_statistics 
*/ 
@Service
public class LogSerTotalStatisticsService {
	
	@Autowired
	private LogSerTotalStatisticsMapper logSerTotalStatisticsMapper;

	public int selectCountSer(Map map) {
		return logSerTotalStatisticsMapper.selectCountSer(map);
	}
	
	public List<Ser_Id_Version_Statistics> selectserVersion(Map map) {
		return logSerTotalStatisticsMapper.selectserVersion(map);
	}
	
	public Ser_Id_Version_Statistics selectSyncSeridVerIdName(Map<String,String> map) {
		return logSerTotalStatisticsMapper.selectSyncSeridVerIdName(map);
	}

	public Ser_Id_Version_Statistics selectAsynSeridName(Map<String, String> map) {
		return logSerTotalStatisticsMapper.selectAsynSeridName(map);
	}
	
	public Ser_Id_Version_Statistics selectCASSeridName(Map<String, String> map) {
		return logSerTotalStatisticsMapper.selectCASSeridName(map);
	}
	
	public Ser_Id_Version_Statistics selectSer_id_version_nameSendCount(Ser_Id_Version_Statistics sivs) {
		return logSerTotalStatisticsMapper.selectSer_id_version_nameSendCount(sivs);
	}
	
	public Ser_Id_Version_Statistics selectSer_id_Version_Name(Ser_Id_Version_Statistics sivs) {
		return logSerTotalStatisticsMapper.selectSer_id_Version_Name(sivs);
	}
	
	public void ser_id_Version_NameUpdate(Ser_Id_Version_Statistics sivsTotal) {
		logSerTotalStatisticsMapper.ser_id_Version_NameUpdate(sivsTotal);
	}
	
	public void ser_id_Version_NameSave(Ser_Id_Version_Statistics sivsTotal) {
		logSerTotalStatisticsMapper.ser_id_Version_NameSave(sivsTotal);
	}
}
