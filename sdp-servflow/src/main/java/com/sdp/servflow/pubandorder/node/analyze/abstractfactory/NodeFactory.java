package com.sdp.servflow.pubandorder.node.analyze.abstractfactory;

import org.jdom.Element;

import com.sdp.servflow.common.BoncException;
import com.sdp.servflow.pubandorder.node.model.node.Node;

public interface NodeFactory {

    /***
     * 
     * Description: 根据element元素的内容解析出不同的节点信息
     * 
     *@param element
     *@return Node
     *
     * @see
     */
    public Node createNode(Element element) throws BoncException;
}
