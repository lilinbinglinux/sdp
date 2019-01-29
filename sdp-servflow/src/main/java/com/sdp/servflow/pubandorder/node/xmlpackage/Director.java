package com.sdp.servflow.pubandorder.node.xmlpackage;

import java.util.List;

import com.sdp.servflow.pubandorder.node.model.node.EndNode;
import com.sdp.servflow.pubandorder.node.model.node.GroupNode;
import com.sdp.servflow.pubandorder.node.model.node.InterfaceNode;
import com.sdp.servflow.pubandorder.node.model.node.Node;
import com.sdp.servflow.pubandorder.node.model.node.NodeJoin;
import com.sdp.servflow.pubandorder.node.model.node.StartNode;

public class Director {
    private Builder builder;
    public Director (Builder builder){
        this.builder = builder;
    }
    //仅允许同一个包下调用（可以实现方法入口都来自单例中的build）
    void construct(List<Node> nodes) {
        //1.先拼接节点  
        for(Node node: nodes)
        {
            
            if(Node.startNodeStyle.equals(node.getNodeStyle())){
                builder.buildStartNode((StartNode)node);}
            if(Node.lineNodeStyle.equals(node.getNodeStyle()))
                builder.buildNodeJoin((NodeJoin)node);
            if(Node.interfaceNodeStyle.equals(node.getNodeStyle()))
                builder.buildInterfaceNode((InterfaceNode)node);
            if(Node.endNodeStyle.equals(node.getNodeStyle()))
                builder.buildEndNode((EndNode)node);
            if(Node.groupNodeEndStyle.equals(node.getNodeStyle()))
            	builder.buildGroupNode((GroupNode)node);
            if(Node.groupNodeStartStyle.equals(node.getNodeStyle()))
            	builder.buildGroupNode((GroupNode)node);
        }
        //2.拼接线上的信息
       /* for(NodeJoin join : joins){
            builder.buildNodeJoin(join);
        }*/
            
    }
}
