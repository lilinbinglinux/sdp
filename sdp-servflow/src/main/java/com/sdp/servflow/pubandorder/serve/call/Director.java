package com.sdp.servflow.pubandorder.serve.call;


import java.util.HashMap;
import java.util.List;

import com.sdp.frame.util.IdUtil;
import com.sdp.frame.util.SpringUtils;
import com.sdp.servflow.logSer.serIpLimit.model.SerIpLimit;
import com.sdp.servflow.pubandorder.common.Response;
import com.sdp.servflow.pubandorder.init.ResponseCollection;
import com.sdp.servflow.pubandorder.orderServer.mapper.OrderInterfaceMapper;
import com.sdp.servflow.pubandorder.orderServer.model.PublishBo;
import com.sdp.servflow.pubandorder.orderapistore.model.OrderInterfaceBean;

import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class Director {
    private static final Logger logger = LoggerFactory.getLogger(Director.class);

    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }
    
    private OrderInterfaceMapper orderInterfaceMapper = SpringUtils.getBean(OrderInterfaceMapper.class);
    /***
     * Description:
     * 
     * @param publicParam
     *            系统参数
     * @param busiParm
     *            void 业务参数
     * @see
     */
    public Response construct(HashMap<String, Object> publicParam, String busiParm) {
        PublishBo publisher = new PublishBo();

        publisher.setAppId(String.valueOf(publicParam.get("appId")));
        publisher.setTenantId(String.valueOf(publicParam.get("tenant_id")));
        
        List<OrderInterfaceBean> orderInterfaces = orderInterfaceMapper.getOrderInterfaces(publisher);
        if(orderInterfaces == null || orderInterfaces.size() == 0) {
            return   ResponseCollection.getSingleStion().get(18);
        }
        String checkStatus = orderInterfaces.get(0).getCheckStatus();
        if(StringUtil.isBlank(checkStatus)||!checkStatus.equals("1")) {
            return   ResponseCollection.getSingleStion().get(9);
        }
        OrderInterfaceBean bean = orderInterfaces.get(0);
        publicParam.put("order_name", bean.getOrderName());

        // 访问本次服务的唯一标识
        String requestID = IdUtil.createId();
        publicParam.put("requestID", requestID);
        // 1.对ip进行过滤（是否是黑名单）
//        String appId = (String)publicParam.get("appId");
//        String ip_addr =(String)publicParam.get("ipAddr");
//        String tenant_id = (String)publicParam.get("tenant_id");
//        String orderid = bean.getOrderId();
        
//        boolean whiteable = builder.judgeIsAWhiteList( tenant_id, orderid, ip_addr);
//        if (!whiteable) {
//            Response response = ResponseCollection.getSingleStion().get(2);
//            response.setRequestId(requestID);
//            return response;
//        }
        // 2.判断流控（流量超过最大流量了）
//        try {
//            boolean accessable = builder.access( tenant_id, orderid);
//            if (!accessable) {
//                Response response = ResponseCollection.getSingleStion().get(17);
//                response.setRequestId(requestID);
//                return response;
//            }
//        }
//        catch (Exception e) {
//            logger.error("流控判断出错", e);
//            Response response = ResponseCollection.getSingleStion().get(16);
//            response.setRequestId(requestID);
//            return response;
//        }
        // 3.解析流程图并返回
        
        Response response = builder.analysis(publicParam, busiParm,bean);
        response.setRequestId(requestID);
        return response;
    }
    /*
     * public Object build(List<Node> nodes) throws Exception { Builder builder = new
     * ConcreteBuilder(); Director director = new Director(builder); return
     * director.construct(null,null); }
     */
}
