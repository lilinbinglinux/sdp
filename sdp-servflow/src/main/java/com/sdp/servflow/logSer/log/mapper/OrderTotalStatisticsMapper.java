package com.sdp.servflow.logSer.log.mapper;

import java.util.List;
import java.util.Map;

import com.sdp.servflow.logSer.log.model.OrderTotalStatistics;

public interface OrderTotalStatisticsMapper {

	OrderTotalStatistics selectOrderTotalSendCount(OrderTotalStatistics lts);

	OrderTotalStatistics findOrderTotalSta(OrderTotalStatistics lts);

	void orderTotalStaUpdate(OrderTotalStatistics sts);

	void orderTotalStaSave(OrderTotalStatistics sts);

	int selectCountFromOrderTotalSta(Map<String, String> map);

	List<OrderTotalStatistics> selectDataFromOrderTotalSta(Map<String, String> map);

}
