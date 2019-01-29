package com.sdp.servflow.logSer.flowControl.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sdp.servflow.logSer.flowControl.mapper.FlowControlMapper;
import com.sdp.servflow.logSer.flowControl.model.CounterBean;

@Service
public class FlowControlService {
	
	@Resource
	private FlowControlMapper flowControlMapper;
	
	public List<CounterBean> selectInitDate() {
		return flowControlMapper.selectInitDate();
	}

	public Date selectLastModifiedTimeSerIpLimit(String schemaName) {
		return flowControlMapper.selectLastModifiedTimeSerIpLimit(schemaName);
	}
}
