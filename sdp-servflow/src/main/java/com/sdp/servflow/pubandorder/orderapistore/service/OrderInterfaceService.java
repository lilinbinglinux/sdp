package com.sdp.servflow.pubandorder.orderapistore.service;

import java.util.List;
import java.util.Map;

import com.sdp.servflow.pubandorder.orderapistore.model.OrderInterfaceBean;

/**
 * Description:服务订阅表的service
 *
 * @author Niu Haoxuan
 * @date Created on 2017/11/15.
 */
public interface OrderInterfaceService {
    /**
     * 查询服务详情
     * @param serId
     * @return
     */
    OrderInterfaceBean selectContextPage(String serId);

    /**
     * 将参数从xml变为json
     * @param serFlow
     * @return
     */
    Map<String, String> getParam(String serFlow) throws Exception;

    /**
     * 插入一条订阅记录
     * @param orderInterfaceBean
     * @return
     */
    String insert(OrderInterfaceBean orderInterfaceBean);

    /**
     * 更新一条记录
     * @param orderInterfaceBean
     * @return
     */
    int update(OrderInterfaceBean orderInterfaceBean);

    /**
     * 插入一条订阅记录(异步)
     * @param orderInterfaceBean
     * @return
     */
    String insertAsynchronized(OrderInterfaceBean orderInterfaceBean, String serNodeArray, String serJoinArray, String insertOrUpdateFlag) throws RuntimeException;


    /**
     * 按照条件查询订阅表
     * @param orderInterfaceBean
     * @return
     */
    List<OrderInterfaceBean> getAllByCondition(OrderInterfaceBean orderInterfaceBean);

    /**
     * 查询订阅审核状态
     * @param paramMap
     * @return
     */
    Map selectInterfaceOrder(String start, String length, Map<String, Object> paramMap);

    /**
     * 修改订阅审核状态
     * @param orderInterfaceBean
     */
    void updateByPrimaryKey(OrderInterfaceBean orderInterfaceBean);

    /**
     * 我的订阅查询
     * @param start
     * @param length
     * @param paramMap
     * @return
     */
    Map selectMine(String start, String length, Map<String, Object> paramMap);

    /**
     * 我的订阅删除
     * @param paramMap
     */
    void deleteOrderInter(Map<String, String> paramMap);

    /**
     * 我的订阅 根据serId和serVersion删除
     * @param paramMap
     */
    void deleteBySerIdAndVersion(Map<String, String> paramMap);

    /**
     * 通过ID查询“我的订阅”
     * @param id
     * @return
     */
    OrderInterfaceBean selectByOrderId(String id);

    /**
     * 通过ID查询订阅表及主表
     * @param orderId
     * @return
     */
    OrderInterfaceBean selectGetSerNameByOrderId(String orderId);

}