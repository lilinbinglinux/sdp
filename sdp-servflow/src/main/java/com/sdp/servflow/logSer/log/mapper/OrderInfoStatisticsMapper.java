package com.sdp.servflow.logSer.log.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sdp.servflow.logSer.log.model.OrderInfoStatistics;

public interface OrderInfoStatisticsMapper {

	OrderInfoStatistics findOrderInfo(OrderInfoStatistics ls);

	void orderInfoStatisticsUpdate(OrderInfoStatistics ls);

	void orderInfoStatisticsSave(OrderInfoStatistics ls);

	Integer selectOrderInfoSendCount(
            @Param(value = "tableName") String tableName,
            @Param(value = "sendDate") int sendDate,
            @Param(value = "ls") OrderInfoStatistics ls);

	List<OrderInfoStatistics> selectFailMsg(OrderInfoStatistics sVersionStatistics);
}
