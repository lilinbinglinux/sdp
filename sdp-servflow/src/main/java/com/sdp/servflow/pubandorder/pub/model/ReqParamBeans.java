package com.sdp.servflow.pubandorder.pub.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * 用来对参数进行xml的解析，ReqparamBean的List集
 *
 * @author ZY
 * @version 2017年6月20日
 * @see ReqParamBeans
 * @since
 */
@XStreamAlias("params")
public class ReqParamBeans {
    
    /**
     * 参数类型，请求参数或响应参数
     */
    private String type;
    
    /**
     * ReqparamBean的List集
     */
    private List<ReqparamBean> allparams;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ReqparamBean> getAllparams() {
        return allparams;
    }

    public void setAllparams(List<ReqparamBean> allparams) {
        this.allparams = allparams;
    }

    @Override
    public String toString() {
        return "ReqParamBeans [type=" + type + ", allparams=" + allparams + "]";
    }
    
    public ReqParamBeans() {
        super();
    }

    public ReqParamBeans(String type, List<ReqparamBean> allparams) {
        super();
        this.type = type;
        this.allparams = allparams;
    }
    
    

}
