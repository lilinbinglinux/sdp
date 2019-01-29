package com.sdp.servflow.logSer.log.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.servflow.logSer.log.mapper.OrderTotalStatisticsMapper;
import com.sdp.servflow.logSer.log.model.OrderTotalStatistics;
import com.sdp.servflow.logSer.log.model.Ser_Id_Version_Statistics;

/** 
* @Title: OrderTotalStatisticsService.java 
* @Description: 操作 订阅总计记录表 order_total_statistics 
*/ 
@Service
public class OrderTotalStatisticsService {
	
	@Autowired
	private OrderTotalStatisticsMapper orderTotalStatisticsMapper;
	
	public OrderTotalStatistics selectOrderTotalSendCount(OrderTotalStatistics lts) {
		return orderTotalStatisticsMapper.selectOrderTotalSendCount(lts);
	}
	
	public OrderTotalStatistics findOrderTotalSta(OrderTotalStatistics lts) {
		return orderTotalStatisticsMapper.findOrderTotalSta(lts);
	}
	
	public void orderTotalStaUpdate(OrderTotalStatistics sts) {
		orderTotalStatisticsMapper.orderTotalStaUpdate(sts);
	}
	
	public void orderTotalStaSave(OrderTotalStatistics sts) {
		orderTotalStatisticsMapper.orderTotalStaSave(sts);
	}
	
	public int selectCountFromOrderTotalSta(Map<String,String> map) {
		return orderTotalStatisticsMapper.selectCountFromOrderTotalSta(map);
	}
	
	public List<OrderTotalStatistics> selectDataFromOrderTotalSta(Map<String,String> map) {
		return orderTotalStatisticsMapper.selectDataFromOrderTotalSta(map);
	}
	
}
