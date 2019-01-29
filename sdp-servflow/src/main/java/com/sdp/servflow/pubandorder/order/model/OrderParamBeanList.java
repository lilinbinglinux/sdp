package com.sdp.servflow.pubandorder.order.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * 用来对参数进行xml解析，是某个参数可能嵌套的内部集
 *
 * @author 牛浩轩
 * @version 2017年7月10日
 * @see OrderParamBeanList
 * @since
 */
@XStreamAlias("listParam")
public class OrderParamBeanList {
    /**
     * 子参数可能包含多个参数
     */
    private List<OrderParamBean> orderParamBeanList;

    public List<OrderParamBean> getOrderParamBeanList() {
        return orderParamBeanList;
    }

    public void setOrderParamBeanList(List<OrderParamBean> orderParamBeanList) {
        this.orderParamBeanList = orderParamBeanList;
    }

    public OrderParamBeanList(List<OrderParamBean> orderParamBeanList) {
        super();
        this.orderParamBeanList = orderParamBeanList;
    }
    
}
