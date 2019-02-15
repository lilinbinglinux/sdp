package com.sdp.servflow.logSer.serIpLimit.mapper;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.servflow.logSer.serIpLimit.model.SerIpLimit;

@Repository
public class SerIpLimitMapper {
	
	@Resource
    private DaoHelper daoHelper;
	
	private final String baseMapperSouth="com.sdp.servflow.logSer.serIpLimit.mapper.SerIpLimitMapper."; 

	public Date selectLastModifiedTimeSerIpLimit(String schemaName) {
		return (Date) daoHelper.queryOne(baseMapperSouth+"selectLastModifiedTimeSerIpLimit",schemaName);
	}

	public List<SerIpLimit> selectAllSerIpLimit() {
		return daoHelper.queryForList(baseMapperSouth+"selectAllSerIpLimit");
	}
	
}
