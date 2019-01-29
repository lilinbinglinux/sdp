package com.sdp.servflow.pubandorder.order.service;

import java.util.List;
import java.util.Map;

import com.sdp.servflow.pubandorder.order.model.InterfaceOrderBean;

/**
 * 
 * 订阅接口模块对应的Service
 *
 * @author 牛浩轩
 * @version 2017年6月12日
 * @see InterfaceOrderBeanService
 * @since
 */
public interface InterfaceOrderBeanService {
    
   /**
    * 
    * Description: “我的订阅”模块查找
    * 
    *@param start
    *@param length
    *@param paramMap
    *@return Map
    *
    * @see
    */
    Map selectMine(String start,String length,Map<String,Object>paramMap);
    
    /**
     * 
     * Description: 按orderId查找单个订阅接口
     * 
     *@param orderId
     *@return InterfaceOrderBean
     *
     * @see
     */
    InterfaceOrderBean selectByPrimaryKey(String orderId);

    /**
     * 
     * Description: 查询单个的pubapiId
     * 
     *@param orderid
     *@return String
     *
     * @see
     */
    String selectPubApiId(String orderid);
    
    /**
     * 
     * Description: 新增订阅服务
     * 
     *@param interfaceOrder void
     *
     * @see
     */
    String insert(InterfaceOrderBean interfaceOrder);
    
    /**
     * 
     * Description: 修改订阅信息
     * 
     *@param interfaceOrderBean void
     *
     * @see
     */
    void updateOrderInter(InterfaceOrderBean interfaceOrderBean);
    
    /**
     * 
     * Description: 取消一条订阅接口
     * 
     *@param orderId void
     *
     * @see
     */
    void deleteOrderInter(String orderId);
    
    /**
     * 
     * Description:找到最大的appid，用来添加的时候实现递增 
     * 
     *@return String
     *
     * @see
     */
    String getMaxAppId();
    
    List<InterfaceOrderBean> getAllByCondition(Map<String,String> map);
    
    boolean isValidation(InterfaceOrderBean interfaceOrderBean);
    
    InterfaceOrderBean upSort(String sort);
}
