package com.sdp.servflow.pubandorder.logRecord.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.servflow.pubandorder.logRecord.mapper.ServiceLogMapper;
import com.sdp.servflow.pubandorder.logRecord.model.ServiceLog;
import com.sdp.servflow.pubandorder.logRecord.service.ServiceLogService;

@Service
public class ServiceLogServiceImpl implements ServiceLogService{

    @Autowired
    private ServiceLogMapper logMapper;
    
    @Override
    public void saveLog(ServiceLog log) {
        logMapper.insert(log);
    }

    @Override
    public void updateLog(ServiceLog log) {
        logMapper.update(log);
        
    }

}
