package com.sdp.servflow.pubandorder.serve;


import java.util.HashMap;

import org.apache.commons.httpclient.HttpClient;

import com.sdp.servflow.pubandorder.common.Response;
import com.sdp.servflow.pubandorder.node.model.node.InterfaceNode;
import com.sdp.servflow.pubandorder.pub.model.PublisherBean;
import com.sdp.servflow.pubandorder.serve.model.ProtocolData;

import net.sf.json.JSONObject;


/**
 * 服务鉴权
 *
 * @author 任壮
 * @version 2017年7月27日
 * @see ServeAuth
 * @since
 */
public interface ServeAuth {

    /**
     * 判断该ip是否有权限
     */
    boolean ipAuth(HashMap<String, Object> res);

    /***
     * Description: 服务调用
     * 
     * @param sysParm
     *            系统参数
     * @param busiParm
     *            业务参数
     * @param urlParam 
     * @return Response
     * @see
     */
    Object invocation(JSONObject sysParm, String busiParm);
    
    /**
     * 
     * Description: 注册服务的在线测试
     * 
     *@param publisherBean
     *          注册的服务
     *@param sampledata
     *          注册服务的测试数据
     *@return Response
     *
     * @see
     */
    Response apiOnlineTest(PublisherBean publisherBean,String sampledata);
    /**
     * 
     * Description:  根据注册的服务调用服务(在这一步之前所有的鉴权 以及格式已经转换完毕)
     * 
     *@param pubInterface
     *@param busiParm
     *@return 字符串
     *@throws Exception String
     *
     * @see
     */
    

    ProtocolData server(InterfaceNode pubInterface, ProtocolData sourceData, HttpClient httpClient)
        throws Exception;

}
