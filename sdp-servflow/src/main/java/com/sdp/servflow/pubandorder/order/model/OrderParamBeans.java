package com.sdp.servflow.pubandorder.order.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * 用来对参数进行xml的解析，OrderParamBean的List集
 *
 * @author 牛浩轩
 * @version 2017年7月10日
 * @see OrderParamBeans
 * @since
 */
@XStreamAlias("params")
public class OrderParamBeans {

    /**
     * 请求参数或响应参数
     */
    private String type;
    
    /**
     * OrderParamBean的List集
     */
    private List<OrderParamBean> allparams;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<OrderParamBean> getAllparams() {
        return allparams;
    }

    public void setAllparams(List<OrderParamBean> allparams) {
        this.allparams = allparams;
    }

    @Override
    public String toString() {
        return "OrderParamBeans [type=" + type + ", allparams=" + allparams + "]";
    }

    public OrderParamBeans(String type, List<OrderParamBean> allparams) {
        super();
        this.type = type;
        this.allparams = allparams;
    }
    
}
