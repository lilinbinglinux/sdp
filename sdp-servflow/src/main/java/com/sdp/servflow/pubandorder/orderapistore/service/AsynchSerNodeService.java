package com.sdp.servflow.pubandorder.orderapistore.service;

import java.util.Map;

/**
 * Description: 发布订阅（异步）Service
 *
 * @author Niu Haoxuan
 * @date Created on 2017/12/22.
 */
public interface AsynchSerNodeService {

    /**
     * 解析xml，显示所有节点
     * @param appResume
     * @return
     * @throws Exception
     */
    Map<String,Object> getNodeJson(String flowchartId,String appResume) throws Exception;

    /**
     * 获取所有节点信息
     * @param flowChartId
     * @return
     * @throws Exception
     */
    Map<String,Object> getNodeJsonExceptloginId(String flowChartId,String serVerId) throws Exception;

}
