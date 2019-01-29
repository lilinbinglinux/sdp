package com.sdp.servflow.pubandorder.apiemploy.adapter;

import java.util.HashMap;
import java.util.Map;

import com.sdp.servflow.pubandorder.format.imp.XmlFormat;

import net.sf.json.JSONObject;


/*
 * xml格式传参模板：
                <apixml>
                  <commonparam>
                    <token__id>ddDmHfi8BOII8LeRaADb2A==</token__id>
                    <appId>0001L</appId>
                  </commonparam>
                  <othparam>123123123</othparam>
                </apixml>
 */


/**
 * 
 * 请求参数为xml的适配处理
 *
 * @author ZY
 * @version 2017年7月26日
 * @see XmlParamAdapter
 * @since
 */
public class XmlParamAdapter {
    
    public static final String commonparamname = "commonparam";
    public static final String othoaramname = "othparam";
    
    
    /**
     * 
     * Description:处理请求参数 
     * 
     *@param paramStr
     *@return Map<String,Object> 返回公共参数和业务参数，分别存储 
     *
     * @see
     */
    public static Map<String,Object> getParams(String paramStr){
        
        Map<String,Object> map = new HashMap<String,Object>();
        
        JSONObject params = JSONObject.fromObject(XmlFormat.otherToJson(paramStr));
        JSONObject commonparam = JSONObject.fromObject(params.get(commonparamname)); 
        
        map.put(commonparamname, commonparam);
        map.put(othoaramname,params.get(othoaramname));
        
        return map;
        
    }
    
}
