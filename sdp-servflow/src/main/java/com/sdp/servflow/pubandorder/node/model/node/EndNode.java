package com.sdp.servflow.pubandorder.node.model.node;

import java.io.Serializable;

public class EndNode extends StartNode implements Serializable{

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 3406554239361580279L;

    @Override
    public String toString() {
        return "EndNode [getParam()=" + getParam() + ", getAgreement()=" + getAgreement()
               + ", toString()=" + super.toString() + ", getFlowChartId()=" + getFlowChartId()
               + ", getNodeId()=" + getNodeId() + ", getNodeName()=" + getNodeName()
               + ", getClientX()=" + getClientX() + ", getClientY()=" + getClientY()
               + ", getNodeWidth()=" + getNodeWidth() + ", getNodeHeight()=" + getNodeHeight()
               + ", getNodeRadius()=" + getNodeRadius() + ", getOther()=" + getOther()
               + ", getNodeType()=" + getNodeType() + ", getParentNode()=" + getParentNode()
               + ", getNodeStyle()=" + getNodeStyle() + ", getTargetlist()=" + getTargetlist()
               + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
    }
    

}
