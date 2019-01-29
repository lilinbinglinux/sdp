package com.sdp.servflow.pubandorder.order.service;

import java.util.Map;

import com.sdp.servflow.pubandorder.order.model.OrderParamBean;

/**
 * 
 * 订阅接口参数匹配
 *
 * @author 牛浩轩
 * @version 2017年7月3日
 * @see OrderParamBeanService
 * @since
 */
public interface OrderParamBeanService {

    /**
     * 
     * Description:新增参数 
     * 
     *@param orderModel
     *@return int
     *
     * @see
     */
    int insert(OrderParamBean orderModel);
    
    /**
     * 
     * Description: xml解析并新增参数
     * 
     *@param xmlcontext
     *@param orderid void
     *
     * @see
     */
    void insertByxml(String xmlcontext, String orderid);
    
    /**
     * 
     * Description: 通过orderid查询该接口下的订阅参数
     * 
     *@param start
     *@param length
     *@param paramMap
     *@return Map
     *
     * @see
     */
    Map selectParam(String start,String length,Map<String,Object>paramMap);
}
