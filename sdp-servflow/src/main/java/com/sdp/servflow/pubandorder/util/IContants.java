package com.sdp.servflow.pubandorder.util;


import java.util.HashMap;
import java.util.Map;

import com.sdp.servflow.pubandorder.common.Response;

/***
 * 
 * 一些常量和公用的数据
 *
 * @author 任壮
 * @version 2017年7月27日
 * @see IContants
 * @since
 */
public class IContants {
    
    public static final String SYSTEM_ERROR_MSG = "系统异常";
    /**
     * http协议
     * */
    public static final String HTTP = "http";
    /**
     * webService协议
     * */
    public static final String WEBSERVICE = "webService";
    /**
     * socket协议
     * */
    public static final String SOCKET = "socket";
    /**
     * json格式
     * */
    public static final String JSON = "application/json";
    /**
     * application/x-www-form-urlencoded格式（表单提交）
     * */
    public static final String FORM = "application/x-www-form-urlencoded";
    /**
     * xml格式
     * */
    public static final String XML = "application/xml";
    /**
     * soap1_2
     * */
    public static final String SOAP_1_2 = "application/soap+xml";
    /**
     * text/xml 格式  soap1_1
     * */
    public static final String SOAP_1_1 = "text/xml";
    public static final String CONTENT_TYPE_SOAP12="application/soap+xml";
    
    public static final String XMLTYPE = "text/xml; charset=utf-8";
    public static final String DEFAULT_CHARSER = "utf-8";
    
    
    /**
     * get请求
     * */
    public static final String GET = "GET";
    /**
     * post格式
     * */
    public static final String POST = "POST";
    /**
     * ListOBJECT
     * */
    public static final String LISTOBJECT = "List_Object";
    /**
     * LISTLIST
     * */
    public static final String LISTLIST = "List_List";
    /**
     * LISTLIST
     * */
    public static final String LIST = "List";
    /**
     * array
     * */
    public static final String ARRAY = "array";
    /**
     * field
     * */
    public static final String FIELD = "field";
    /**
     * attr
     * */
    public static final String ATTR = "attr";
    /**
     * Object
     * */
    public static final String OBJECT = "Object";
    /**
     * 请求体的信息
     * */
    public static final String REQBODY = "0";
    /**
     * 位于url上的参数
     * */
    //public static final String URLPARAM = "url";
    public static final String URLPARAM = "1";
    /**
     * 响应头上的信息
     * */
    public static final String RESPONSEHEADER = "2";
    /**
     * 响应体的信息
     * */
    public static final String RESPONSEBODY = "3";
    /**
     * 请求头的信息
     * */
    public static final String REQUESTHEADER = "4";
    /**
     * 请求属性的信息
     * */
    public static final String REQATTRIBUTE = "5";
    /**
     * 响应属性的信息
     * */
    public static final String RESPATTRIBUTE = "6";
    /**
     * 命名空间
     * */
    public static final String NAME_SPACE = "7";
    /**
     * 返回的码表
     * */
    public static Map<Integer, Response> CODESTABLE = new HashMap<Integer, Response>();
    
    /****
     * 
     * 参数类型
     *
     * @author 任壮
     * @version 2017年9月23日
     * @see ParamType
     * @since
     */
        public static  class ParamType {
        /**布尔值*/
        public static String BOOLEAN = "boolean";
        /**Object*/
        public static String OBJECT = "Object";
        /**List*/
        public static final String LIST = "List";
        /**int*/
        public static final String INT = "int";
        /**String*/
        public static final String STRING = "String";
    };
    
}
