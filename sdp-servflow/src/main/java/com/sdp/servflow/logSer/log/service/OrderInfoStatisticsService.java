package com.sdp.servflow.logSer.log.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.servflow.logSer.log.mapper.OrderInfoStatisticsMapper;
import com.sdp.servflow.logSer.log.model.OrderInfoStatistics;

/** 
* @Title: OrderInfoStatisticsService.java 
* @Description: 操作 订阅详情记录表 order_info_statistics 
*/ 
@Service
public class OrderInfoStatisticsService {
	
	@Autowired
	private OrderInfoStatisticsMapper orderInfoStatisticsMapper;
	
	
	public OrderInfoStatistics findOrderInfo(OrderInfoStatistics ls) {
		return orderInfoStatisticsMapper.findOrderInfo(ls);
	}
	
	public void orderInfoStatisticsUpdate(OrderInfoStatistics ls) {
		orderInfoStatisticsMapper.orderInfoStatisticsUpdate(ls);
	}
	
	public void orderInfoStatisticsSave(OrderInfoStatistics ls) {
		orderInfoStatisticsMapper.orderInfoStatisticsSave(ls);
	}
	
	public Integer selectOrderInfoSendCount(String tableName, int sendDate, OrderInfoStatistics ls) {
		return orderInfoStatisticsMapper.selectOrderInfoSendCount(tableName,sendDate,ls);
	}
}
