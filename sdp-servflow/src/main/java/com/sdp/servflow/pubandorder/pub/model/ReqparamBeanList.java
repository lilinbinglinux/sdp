package com.sdp.servflow.pubandorder.pub.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * 用来对参数进行xml解析，是某个参数可能嵌套的内部集
 *
 * @author ZY
 * @version 2017年6月20日
 * @see ReqparamBeanList
 * @since
 */

@XStreamAlias("listParam")
public class ReqparamBeanList {
    /**
     * 子参数可能包含多个参数
     */
    private List<ReqparamBean> listParam;

    public List<ReqparamBean> getListParam() {
        return listParam;
    }

    public void setListParam(List<ReqparamBean> listParam) {
        this.listParam = listParam;
    }

    public ReqparamBeanList(List<ReqparamBean> listParam) {
        super();
        this.listParam = listParam;
    }
    
}
