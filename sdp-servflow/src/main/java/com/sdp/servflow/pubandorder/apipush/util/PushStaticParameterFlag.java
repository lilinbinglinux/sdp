package com.sdp.servflow.pubandorder.apipush.util;

/**
 * Description:推送所需要的静态参数变量
 *
 * @author Niu Haoxuan
 * @date Created on 2018/2/2.
 */
public class PushStaticParameterFlag {

    //准备创建的分类名称
    public final static String PUBLISHER_TYPENAME = "编排服务";

    //创建的分类排序
    public final static String PUBLISHER_SORT = "3";

    //创建的分类描述
    public final static String PUBLISHER_DESCRIPTION = "存放已经编排好的服务";

    //成功（避免多次定义不统一的问题）
    public final static String PUSH_SUCCESS = "success";

    //失败（避免多次定义不统一的问题）
    public final static String PUSH_FAIL = "error";

}
