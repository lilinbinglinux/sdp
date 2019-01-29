package com.sdp.servflow.pubandorder.node.model.node;

import java.io.Serializable;

import com.sdp.servflow.pubandorder.node.model.parameter.Parameter;

public class StartNode extends Node implements Serializable{
    
    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = -7935752700898404068L;
  
    /***
     * 请求协议（webservice 或者 http）
     */
    private String agreement;
    /**
     * 参数
     */
    private Parameter param;

    public Parameter getParam() {
        return param;
    }

    public void setParam(Parameter param) {
        this.param = param;
    }


    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    @Override
    public String toString() {
        return "StartNode [agreement=" + agreement + ", param=" + param + "]";
    }



}
