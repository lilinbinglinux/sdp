package com.sdp.serviceAccess.proxy.specific;


import java.util.Map;

import com.sdp.common.entity.Status;
import com.sdp.serviceAccess.proxy.model.CSvcBusiInfo;

/**
 * @description:
 * for云控制台对接接口规范，
 * 调用接口返回报文中有业务数据需要保存处理，需要实现此接口中对应的方法
 * @author: wangke
 * @version: 14:30 2018/1/6
 * @see:
 * @since:
 * @modified by:
 */
public interface BusinessHandleService {

    /**
     * 创建服务回调方法
     *    保存处理接口返回的报文数据
     * @param cSvcBusiInfo
     * @param result
     * @param feedback
     * @see CSvcBusiInfo
     * @return
     */
    Status createSVCOfHandleFeedback(CSvcBusiInfo cSvcBusiInfo, Map<String,Object> result, Map<String,Object> feedback);

    /**
     * 启用服务回调方法
     *    保存处理接口返回的报文数据
     * @param cSvcBusiInfo
     * @param result
     * @return
     */
    Status startSVCOfHandleFeedback(CSvcBusiInfo cSvcBusiInfo, Map<String,Object> result);

    /**
     * 停用服务回调方法
     *    保存处理接口返回的报文数据
     * @param cSvcBusiInfo
     * @param result
     * @return
     */
    Status offSVCOfHandleFeedback(CSvcBusiInfo cSvcBusiInfo, Map<String,Object> result);

    /**
     * 删除服务回调方法
     *    保存处理接口返回的报文数据
     * @param cSvcBusiInfo
     * @param result
     * @return
     */
    Status deleteSVCOfHandleFeedback(CSvcBusiInfo cSvcBusiInfo, Map<String,Object> result);
    
    /**
     * 变更服务资源回调方法
     *    保存处理接口返回的报文数据
     * @param cSvcBusiInfo
     * @param result
     * @param feedback
     * @see CSvcBusiInfo
     * @return
     */
    Status changeResSVCOfHandleFeedback(CSvcBusiInfo cSvcBusiInfo, Map<String,Object> result, Map<String,Object> feedback);
}
