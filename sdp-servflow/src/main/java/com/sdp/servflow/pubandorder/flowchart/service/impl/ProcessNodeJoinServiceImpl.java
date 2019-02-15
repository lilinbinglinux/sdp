package com.sdp.servflow.pubandorder.flowchart.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.servflow.pubandorder.flowchart.model.ProcessNodeJoin;
import com.sdp.servflow.pubandorder.flowchart.service.ProcessNodeJoinService;
@Service
public class ProcessNodeJoinServiceImpl implements ProcessNodeJoinService{
	
    /**
     * DaoHelper
     */
    @Resource
    private DaoHelper daoHelper;

	@Override
	public void addJoin(ProcessNodeJoin processNodeJoin) {
	    daoHelper.insert("com.sdp.servflow.pubandorder.flowchart.mapper.NodeJoinMapper.addJoin", processNodeJoin);
	}

	@Override
	public List<ProcessNodeJoin> findAllByFlowId(Map<String, String> map) {
	    return daoHelper.queryForList("com.sdp.servflow.pubandorder.flowchart.mapper.NodeJoinMapper.findAllByFlowId", map);
	}

	@Override
	public void deleteAll(Map<String, String> map) {
	    daoHelper.delete("com.sdp.servflow.pubandorder.flowchart.mapper.NodeJoinMapper.deleteAll", map);
	}

}
