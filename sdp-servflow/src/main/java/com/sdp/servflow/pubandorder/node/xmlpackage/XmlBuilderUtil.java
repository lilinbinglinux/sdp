package com.sdp.servflow.pubandorder.node.xmlpackage;

import java.util.List;

import com.sdp.servflow.pubandorder.node.model.node.Node;
import com.sdp.servflow.pubandorder.node.model.node.NodeJoin;

/***
 * 
 * 将对象映射到节点信息上
 *
 * @author 任壮
 * @version 2017年11月7日
 * @see XmlBuilderUtil
 * @since
 */
public class XmlBuilderUtil {
    
    private XmlBuilderUtil(){}
    private static class InnerClass{
        private  static final  XmlBuilderUtil builder = new XmlBuilderUtil();
    }
    /**
     * 根据类加载机制生成的单例模式（实现了延迟加载）
     */
    public static XmlBuilderUtil getSingleStion(){
        return InnerClass.builder;
    }
        
       /**
        * 
        * Description: 根据节点信息和线的信息生成数据中存储的xml
        * 
        *@param nodes 节点信息
        *@param joins 线信息
        *@return String
     * @throws Exception 
        *
        * @see
        */
    public String build(List<Node> nodes) throws Exception {
        Builder builder = new ConcreteBuilder();
        Director director = new Director(builder);
        director.construct(nodes);
        return builder.getResult();
    }
    
    
    
    
    
}
