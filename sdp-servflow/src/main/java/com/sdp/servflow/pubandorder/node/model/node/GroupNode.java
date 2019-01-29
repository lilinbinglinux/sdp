package com.sdp.servflow.pubandorder.node.model.node;

import java.io.Serializable;

import com.sdp.servflow.pubandorder.node.model.parameter.Parameter;

public class GroupNode extends Node  implements Serializable{
    
	private static final long serialVersionUID = 1L;

     public Parameter getParam() {
		return param;
	}

	public void setParam(Parameter param) {
		this.param = param;
	}

	/**
     * 参数
     */
    private Parameter param;
}
