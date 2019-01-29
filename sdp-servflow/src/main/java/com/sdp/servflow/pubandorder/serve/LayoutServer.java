package com.sdp.servflow.pubandorder.serve;


import java.util.HashMap;

import org.apache.commons.httpclient.HttpClient;

import com.sdp.servflow.pubandorder.common.Response;


/**
 * 服务编排
 *
 * @author 任壮
 * @version 2017年7月27日
 * @see LayoutServer
 * @since
 */
public interface LayoutServer {


    /**
     * 
     * Description: 解析服务编排的xml信息
     * 
     *@param publicParam
     *@param busiParm
     *@param httpClient
     *@param esbXml
     *@return Response
     *
     * @see
     */
    Response analysis(HashMap<String, Object> publicParam, String busiParm, HttpClient httpClient,
                      String esbXml);

 
}
