package com.sdp.servflow.serlayout.process.util;

import java.util.Iterator;

import net.sf.json.JSONObject;

public class JSONObjectNUllUtil {
	public static JSONObject clearNUll(JSONObject nodeobj) {
		Iterator<String> sIterator = nodeobj.keys();  
		while(sIterator.hasNext()){  
		    // 获得key  
		    String key = sIterator.next();  
		    // 根据key获得value, value也可以是JSONObject,JSONArray,使用对应的参数接收即可  
		   if(nodeobj.get(key) == null || nodeobj.getString(key) == "null"
				   || nodeobj.getString(key) == "undefined" ) {
			   nodeobj.put(key, "");
		   }
		}  
		
		return nodeobj;
	}

}
