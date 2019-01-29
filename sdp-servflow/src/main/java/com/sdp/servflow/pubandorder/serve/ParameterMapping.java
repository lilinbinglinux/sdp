package com.sdp.servflow.pubandorder.serve;

import java.util.HashMap;
import java.util.Map;

import com.sdp.servflow.pubandorder.node.model.field.XmlNode;
import com.sdp.servflow.pubandorder.node.model.node.ConditionNode;
import com.sdp.servflow.pubandorder.node.model.node.InterfaceNode;
import com.sdp.servflow.pubandorder.serve.model.ProtocolData;
import com.sdp.servflow.transfomat.PROTOCOL_TYPE;
/***
 * 
 * 参数映射
 *
 * @author 任壮
 * @version 2017年9月26日
 * @see ParameterMapping
 * @since
 */
public interface ParameterMapping {
    
    
    public PROTOCOL_TYPE transform(String type);
   // public ProtocolData mapping(HashMap<String, Object> pubInterface,ProtocolData busiParm,String sourceParamType,String targetParamType,String prePubId );

    /***
     * 
     * Description: 
     * 
     *@param conditionNode 映射节点的信息
     *@param resource 所有返回参数的集合
     *@param node 调用的接口节点
     * @param xmlNodes 
     *@return ProtocolData
     * @throws Exception 
     *
     * @see
     */
    ProtocolData mapping(ConditionNode conditionNode, Map<String, ProtocolData> resource,  Map<String, XmlNode> xmlNodes) throws Exception;
    /**
     * 
     * Description: 条件判断
     * 
     *@param conditionNode
     *@param resource
     *@param xmlNodes
     *@return boolean
     * @throws Exception 
     *
     * @see
     */
    public boolean JudgeCondition(ConditionNode conditionNode, Map<String, ProtocolData> resource, 
                                  Map<String, XmlNode> xmlNodes) throws Exception;

}
