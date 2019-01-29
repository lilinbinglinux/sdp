package com.sdp.servflow.pubandorder.serve.call;

import java.util.HashMap;

import com.sdp.servflow.pubandorder.common.Response;
import com.sdp.servflow.pubandorder.orderapistore.model.OrderInterfaceBean;

/**
 * 
 * 同步服务编排调用的信息
 *
 * @author 任壮
 * @version 2017年11月7日
 * @see Builder
 * @since
 */
public interface Builder {
    /**
     * 
     * Description: 判断ip是否过滤
     * 
     *@param sil
     *@return boolean
     *
     * @see
     */
    boolean judgeIsAWhiteList(String tenant_id,String orderid,String ip_addr) ;
    /**
     * 
     * Description: 流控判断
     * 
     *@param sil
     *@return boolean
     *
     * @see
     */
    boolean access(String tenant_id,String order_id) throws Exception;
    /***
     * 
     * Description: 
     * 
     *@param publicParam  公共参数
     *@param busiParm     业务参数
     *@return Response
     *
     * @see
     */
     Response analysis(HashMap<String, Object> publicParam, String busiParm,  OrderInterfaceBean bean
     );

}
