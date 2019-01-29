package com.sdp.servflow.pubandorder.logRecord.service;

import com.sdp.servflow.pubandorder.logRecord.model.ServiceLog;

/***
 * 
 * 服务日志记录
 *
 * @author 任壮
 * @version 2017年9月24日
 * @see ServiceLogService
 * @since
 */
public interface ServiceLogService {
    
    /**
     * 新增日志
     * */
    void saveLog(ServiceLog serLog);
    /**
     * 修改日志
     * */
    void updateLog(ServiceLog serLog);

}
