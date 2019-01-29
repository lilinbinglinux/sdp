package com.sdp.servflow.pubandorder.orderServer.publish;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

import org.apache.commons.httpclient.HttpClient;
import org.jsoup.helper.StringUtil;

import com.sdp.frame.util.SpringUtils;
import com.sdp.servflow.pubandorder.common.Response;
import com.sdp.servflow.pubandorder.init.ResponseCollection;
import com.sdp.servflow.pubandorder.orderServer.model.PublishBo;
import com.sdp.servflow.pubandorder.orderapistore.model.OrderInterfaceBean;
import com.sdp.servflow.pubandorder.serve.LayoutServer;

/***
 * 
 * 对订阅者的处理（调用各个接口，将转化后的数据发送给不同的调用方）
 *
 * @author 任壮
 * @version 2017年11月28日
 * @see Order
 * @since
 */
public class Order implements Observer{

    /**
     * 对于数据库中的一条发布订阅记录
     */
    private OrderInterfaceBean orderInterfaceBean;
    
    public Order(OrderInterfaceBean orderInterfaceBean) {
        this.orderInterfaceBean = orderInterfaceBean;
    }
    
    
    public void registerSubject(Observable obs)  {
        obs.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof PublishBo) {
            PublishBo publisher = (PublishBo)arg;
            sendMsg(orderInterfaceBean,publisher);
        }
    }

    
    
    /***
     * 
     * Description: 根据订阅信息从kafka拽出信息，并发送到各个接口中
     * 
     *@param orderInterfaceBean   订阅表中数据
     * @param publisher   从kafka中拽取出的内容（包括用户端发送的内容）
     *
     * @see
     */
    /**
     * Description: 
     * 
     *@param orderBean
     *@param publisher void
     *
     * @see 
     */
    private Response sendMsg(OrderInterfaceBean orderBean, PublishBo publisher) {
    	String checkStatus = orderBean.getCheckStatus();
    	if(StringUtil.isBlank(checkStatus)||!checkStatus.equals("1")) {
    		return   ResponseCollection.getSingleStion().get(9);
    	}
        LayoutServer  layout =  SpringUtils.getBean(LayoutServer.class);
        
        HashMap<String, Object> publicParam = new HashMap<String, Object>();
        publicParam.put("ser_version", orderBean.getSerVersion());
        publicParam.put("ser_id", orderBean.getApplicationId());
        publicParam.put("appId", orderBean.getAppId());
        publicParam.put("tenant_id", orderBean.getTenantId());
        publicParam.put("order_id", orderBean.getOrderId());
        publicParam.put("order_name", orderBean.getOrderName());
        publicParam.put("requestID", publisher.getRequestId());
        publicParam.put("sourceType", "1");
        HttpClient httpClient = new HttpClient();
        //解析xml
        return  layout.analysis(publicParam, publisher.getMsg(), httpClient, orderBean.getAppResume());
    }

    public OrderInterfaceBean getOrderInterfaceBean() {
        return orderInterfaceBean;
    }

    public void setOrderInterfaceBean(OrderInterfaceBean orderInterfaceBean) {
        this.orderInterfaceBean = orderInterfaceBean;
    }

    
    

}
