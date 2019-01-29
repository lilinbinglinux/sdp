/**
 * 
 */
package com.sdp.serviceAccess.util;

import java.util.Map;

import com.alibaba.fastjson.JSON;

/**   
 * Copyright: Copyright (c) 2018 BONC
 * 
 * @ClassName: JsonXMLUtils.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0
 * @author: renpengyuan
 * @date: 2018年8月16日 上午11:42:41 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年8月16日     renpengyuan      v1.0.0               修改原因
 */
public class JsonXMLUtils {
	public static String obj2json(Object obj) throws Exception {
		return JSON.toJSONString(obj);
	}

	public static <T> T json2obj(String jsonStr, Class<T> clazz) throws Exception {
		return JSON.parseObject(jsonStr, clazz);
	}

	@SuppressWarnings("unchecked")
	public static <T> Map<String, Object> json2map(String jsonStr)     throws Exception {
		return JSON.parseObject(jsonStr, Map.class);
	}

	public static <T> T map2obj(Map<?, ?> map, Class<T> clazz) throws Exception {
		return JSON.parseObject(JSON.toJSONString(map), clazz);
	}
}
