package com.sdp.servflow.sysConfig.service.serviceImpl;

import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.servflow.sysConfig.model.SysCommonCfg;
import com.sdp.servflow.sysConfig.service.SysCommonCfgService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysCommonCfgServiceImpl implements SysCommonCfgService {

    @Autowired
    private DaoHelper daoHelper;

    @Override
    public SysCommonCfg selectByKey(String cfg_key) {
        return (SysCommonCfg)daoHelper.queryOne("com.bonc.frame.web.mapper.sys_config.SysCommonCfgMapper.selectByKey",cfg_key);
    }
}
