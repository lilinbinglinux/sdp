package com.sdp.servflow.pubandorder.protocol;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 客户端
 *
 * @author 任壮
 * @version 2017年7月27日
 * @see TClint
 * @since
 */
public class TClint { 
    public static void main(String[] args) throws UnsupportedEncodingException {
	
	JSONObject json =new JSONObject();
	json.put("city", "北京");
	
System.out.println(
SocketUtil.remoteOperate("172.16.11.59", 8099, json.toString(),"utf-8"));	;

}
    

}
