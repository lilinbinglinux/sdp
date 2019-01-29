package com.sdp.servflow.pubandorder.util;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sdp.servflow.pubandorder.node.model.node.Node;
import com.sdp.servflow.pubandorder.node.model.node.NodeJoin;

/***
 * 
 * 将对象映射到节点信息上
 *
 * @author 任壮
 * @version 2017年11月30日
 * @see ThreadPoolUtil
 * @since
 */
public class ThreadPoolUtil {
    
    private ThreadPoolUtil(){}
    private static class InnerClass{
        private  static final  ThreadPoolUtil builder = new ThreadPoolUtil();
    }
    /**
     * 根据类加载机制生成的单例模式（实现了延迟加载）
     */
    public static ThreadPoolUtil getSingleStion(){
        return InnerClass.builder;
    }
        
       /**
        * 
        * Description: 获取线程缓冲池
        * 
        *@param nodes 节点信息
        *@param joins 线信息
        *@return String
     * @throws Exception 
        *
        * @see
        */
    public ExecutorService getCachedThreadPool()  {
        return Executors.newCachedThreadPool();
    }
    
    
    
    
    
}
