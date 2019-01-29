package com.sdp.servflow.logSer.log.service;

import java.util.ArrayList;
import java.util.List;

import com.sdp.frame.util.StringUtil;
import com.sdp.servflow.logSer.CacheCode;
import com.sdp.servflow.logSer.log.mapper.CodeTableMapper;
import com.sdp.servflow.logSer.log.mapper.OrderInfoStatisticsMapper;
import com.sdp.servflow.logSer.log.model.CodeTable;
import com.sdp.servflow.logSer.log.model.OrderInfoStatistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 
* @Title: CodeTableService.java 
* @Description: 操作 码表codetable
*/ 
@Service
public class CodeTableService {

	@Autowired
	private CodeTableMapper codeTableMapper;
	
	@Autowired
	private OrderInfoStatisticsMapper orderInfoStatisticsMapper;
	
	public List<CodeTable> selectFailMsg(OrderInfoStatistics serVersionStatistics) {
		List<OrderInfoStatistics> list = orderInfoStatisticsMapper.selectFailMsg(serVersionStatistics);
		List<CodeTable> codeList = new ArrayList<CodeTable>();
		CacheCode cacheMap = CacheCode.getInStance();
		CodeTable cTable;
		for (OrderInfoStatistics sVersionStatistics:list) {
			String desc = cacheMap.getCacheTest(sVersionStatistics.getCode());
				if(StringUtil.isNotEmpty(desc)){
					cTable = new CodeTable();
					cTable.setCode(sVersionStatistics.getCode());
					cTable.setDesc(desc);
					cTable.setSendCount(sVersionStatistics.getSendCount());
					codeList.add(cTable);
				}else{
					CodeTable codeTable = new CodeTable();
					codeTable.setCode(sVersionStatistics.getCode());
					cTable = codeTableMapper.selectFailMsg(codeTable);
					if(null != cTable){
						cTable.setSendCount(sVersionStatistics.getSendCount());
						codeList.add(cTable);
						cacheMap.setCacheTest(cTable.getCode() ,600000,cTable.getDesc());//缓存存在时间600s
					}
				}
		}
		return codeList;
	}
	
}
