package com.sdp.servflow.pubandorder.node.xmlpackage;


import com.sdp.servflow.pubandorder.node.model.node.EndNode;
import com.sdp.servflow.pubandorder.node.model.node.GroupNode;
import com.sdp.servflow.pubandorder.node.model.node.InterfaceNode;
import com.sdp.servflow.pubandorder.node.model.node.NodeJoin;
import com.sdp.servflow.pubandorder.node.model.node.StartNode;

/**
 * 
 * 构造系统能识别的xml
 *
 * @author 任壮
 * @version 2017年11月7日
 * @see Builder
 * @since
 */
public interface Builder {
    
  void  buildStartNode(StartNode node);
  
  //void  buildConditionNode(ConditionNode node);
  
  void  buildInterfaceNode(InterfaceNode node);
  
  void  buildEndNode(EndNode node);
  /**
   * 初始化线节点
   * @param node
   */
  void  buildNodeJoin(NodeJoin node);
  /**
   * 初始化聚合节点
   * @param node
   */
  void  buildGroupNode(GroupNode node);
  
  /**
   * 
   * Description: 获取最终需要的字符串
   * 
   *@return
   *@throws Exception String
   *
   * @see
   */
  String getResult() throws Exception;

}
