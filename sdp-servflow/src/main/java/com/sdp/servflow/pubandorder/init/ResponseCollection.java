package com.sdp.servflow.pubandorder.init;

import com.sdp.servflow.pubandorder.common.Response;
import com.sdp.servflow.pubandorder.util.IContants;

/**
 * 
 * 响应的信息的集合
 *
 * @author 任壮
 * @version 2017年12月6日
 * @see ResponseCollection
 * @since
 */
public class ResponseCollection {

    
    private ResponseCollection(){}
    private static class InnerClass{
        private  static final  ResponseCollection builder = new ResponseCollection();
    }
    /**
     * 根据类加载机制生成的单例模式（实现了延迟加载）
     */
    public static ResponseCollection getSingleStion(){
        return InnerClass.builder;
    }
        
       /**
        * 
        * Description: 获取一个返回值的对象
        * 
        *@param nodes 节点信息
        *@param joins 线信息
        *@return String
     * @throws Exception 
        *
        * @see
        */
    public Response get(int recId)  {
        Response response = null;
      try {
          response =     IContants.CODESTABLE.get(recId).clone();
    }
    catch (CloneNotSupportedException e) {
        e.printStackTrace();
    }
    return response;
    }

}
