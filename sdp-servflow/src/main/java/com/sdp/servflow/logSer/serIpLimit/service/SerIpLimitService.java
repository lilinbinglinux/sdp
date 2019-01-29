package com.sdp.servflow.logSer.serIpLimit.service;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.sdp.servflow.logSer.serIpLimit.mapper.SerIpLimitMapper;
import com.sdp.servflow.logSer.serIpLimit.model.SerIpLimit;

@Service
public class SerIpLimitService {
		
	@Resource
	private SerIpLimitMapper serIpLimitMapper;

	public Date selectLastModifiedTimeSerIpLimit(String schemaName) {
		return serIpLimitMapper.selectLastModifiedTimeSerIpLimit(schemaName);
	}

	public List<SerIpLimit> selectAllSerIpLimit() {
		return serIpLimitMapper.selectAllSerIpLimit();
	}
	
}
