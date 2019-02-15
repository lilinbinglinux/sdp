package com.sdp.servflow.logSer.flowControl.mapper;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.servflow.logSer.flowControl.model.CounterBean;

@Repository
public class FlowControlMapper {
	
	@Resource
    private DaoHelper daoHelper;
	
	private final String baseMapperSouth="com.sdp.servflow.logSer.flowControl.mapper.FlowControlMapper.";

	public List<CounterBean> selectInitDate() {
		return daoHelper.queryForList(baseMapperSouth+"selectInitDate");
	}

	public Date selectLastModifiedTimeSerIpLimit(String schemaName) {
		return (Date) daoHelper.queryOne(baseMapperSouth+"selectLastModifiedTimeSerIpLimit",schemaName);
	}
}
