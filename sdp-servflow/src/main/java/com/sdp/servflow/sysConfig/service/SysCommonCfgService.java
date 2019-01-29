package com.sdp.servflow.sysConfig.service;

import com.sdp.servflow.sysConfig.model.SysCommonCfg;

public interface SysCommonCfgService {


    SysCommonCfg selectByKey(String cfg_key);

}
