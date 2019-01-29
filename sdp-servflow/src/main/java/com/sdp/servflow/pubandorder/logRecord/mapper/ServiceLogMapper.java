package com.sdp.servflow.pubandorder.logRecord.mapper;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.servflow.pubandorder.logRecord.model.ServiceLog;

@Component
public class ServiceLogMapper {
    @Resource
    private DaoHelper daoHelper;
    
    
    public int insert(ServiceLog log) {
        return daoHelper.insert("com.bonc.frame.web.mapper.puborder.ServiceLogMapper.insert", log);
    }
    
    public int update(ServiceLog log) {
        return daoHelper.update("com.bonc.frame.web.mapper.puborder.ServiceLogMapper.update", log);
    }
    
}
