package com.sdp.servflow.pubandorder.serve.call;

import org.apache.commons.httpclient.HttpClient;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdp.servflow.logSer.flowControl.busi.FlowControlManager;
import com.sdp.servflow.logSer.serIpLimit.busi.SerIpLimitManager;
import com.sdp.servflow.pubandorder.common.Response;
import com.sdp.servflow.pubandorder.init.ResponseCollection;
import com.sdp.servflow.pubandorder.orderServer.mapper.OrderInterfaceMapper;
import com.sdp.servflow.pubandorder.orderServer.model.PublishBo;
import com.sdp.servflow.pubandorder.orderapistore.model.OrderInterfaceBean;
import com.sdp.servflow.pubandorder.serve.LayoutServer;
import com.sdp.servflow.pubandorder.serve.mapper.ServerMapper;

import java.util.HashMap;
import java.util.List;

/**
 *
 * builder的具体实现类
 *
 * @author 任壮
 * @versions 2017年12月12日11:21:52
 * @see ConcreteBuilder
 * @since
 */
@Component
public class ConcreteBuilder implements Builder{

    @Autowired
    private SerIpLimitManager serIpLimitManager;
    @Autowired
    private FlowControlManager   flowControlManager;
    @Autowired
    private LayoutServer   layoutServer;
    @Autowired
    private ServerMapper serverMapper;




    @Override
    public boolean judgeIsAWhiteList(String tenant_id,String orderid,String ip_addr) {
        return  serIpLimitManager.judgeIsAWhiteList( tenant_id, orderid, ip_addr);
    }

    @Override
    public boolean access(String tenant_id,String order_id) throws Exception {
        return flowControlManager.access(tenant_id, order_id);
    }
    @Override
    public Response analysis(HashMap<String, Object> publicParam, String busiParm, OrderInterfaceBean bean ) {



        publicParam.put("ser_id", bean.getSerId());
        publicParam.put("ser_version", bean.getSerVersion());
        publicParam.put("order_id", bean.getOrderId());
        //获取调用服务的记录
        HashMap<String, String> ser_flow =    serverMapper.getServerFlow(publicParam);
        if(ser_flow == null){
            return   ResponseCollection.getSingleStion().get(15);
        }
        //esb识别的xml信息
        String esbXml = ser_flow.get("ser_flow");
        HttpClient httpClient = new HttpClient ();
        return layoutServer.analysis(publicParam, busiParm, httpClient, esbXml);
    }


}
