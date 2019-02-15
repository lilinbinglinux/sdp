package com.sdp.servflow.pubandorder.flowchart.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.servflow.pubandorder.flowchart.model.ProcessNodeInfo;
import com.sdp.servflow.pubandorder.flowchart.service.ProcessNodeInfoService;
@Service
public class ProcessNodeInfoServiceImpl implements ProcessNodeInfoService{

    /**
     * DaoHelper
     */
    @Resource
    private DaoHelper daoHelper;
	
	@Override
	public void addInfo(ProcessNodeInfo processNodeInfo) {
	    daoHelper.insert("com.sdp.servflow.pubandorder.flowchart.mapper.ProcessNodeInfoMapper.addInfo", processNodeInfo);
	}

}
