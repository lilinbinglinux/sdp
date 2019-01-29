package com.sdp.servflow.pubandorder.register.service;

/**
 * Description:
 *
 * @author Niu Haoxuan
 * @date Created on 2017/12/27.
 */
public interface ServiceRegisterService {

    /**
     * 保存节点
     * @param serNodeArray
     * @param serJoinArray
     * @param serFlowType
     * @return
     */
    String addStartNode(String serNodeArray, String serJoinArray, String serFlowType);

}