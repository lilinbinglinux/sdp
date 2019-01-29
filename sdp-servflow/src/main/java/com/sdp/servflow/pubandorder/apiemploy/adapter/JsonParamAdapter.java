package com.sdp.servflow.pubandorder.apiemploy.adapter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;


/*
 * json格式传参模板：
   {
        "commonparam": {
            "token_id": "ddDmHfi8BOII8LeRaADb2A==",
            "appId": "0001L"
        },
        "othparam": {
            "smsSetId": "3037145623",
            "telPhone": "13621920001",
            "sendContent": "短信测试",
            "sendLev": "2",
            "externalId": "85e0e1dbb09841b49f6411a85eb5bcaa",
            "invalidTime": "201701101630",
            "eventType": "abc"
        }
    }
 * 
 */

/**
 * 
 * 请求参数为json的适配处理
 *
 * @author ZY
 * @version 2017年7月26日
 * @see JsonParamAdapter
 * @since
 */
public class JsonParamAdapter {
    
    /**
     * 定义传参模板中参数名称
     */
    public static final String commonparamname = "commonparam";
    public static final String busiparammname = "busiparam";
    public static final String reserveparam = "reserveparam";
    
    /**
     * 
     * Description:处理请求参数 
     * 
     *@param paramStr
     *@return Map<String,Object> 返回公共参数和业务参数，分别存储 
     *
     * @see
     */
    @SuppressWarnings("rawtypes")
    public static Map<String,Object> getParams(String paramStr){
        Map<String,Object> map = new HashMap<String,Object>();
        
        JSONObject json = JSONObject.fromObject(paramStr);
        Iterator keys = json.keys();
        
        JSONObject commonObj = null;
        String othparamStr = "";
        String reserveStr = "";
        
        while(keys.hasNext()){  
            String key = keys.next().toString();
            String value = json.getString(key);
            switch (key){
                case commonparamname: 
                    commonObj = JSONObject.fromObject(value);
                    break;
                case busiparammname: 
                    othparamStr = value;
                    break;
                case reserveparam:
                    reserveStr = value;
            }
        }
        
        map.put(commonparamname, commonObj);
        map.put(busiparammname, othparamStr);
        map.put(reserveparam, reserveStr);
        
        return map;
    }
    
}
