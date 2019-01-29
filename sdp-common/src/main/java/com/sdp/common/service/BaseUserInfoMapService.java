package com.sdp.common.service;

import java.util.HashMap;
import java.util.Map;

import com.sdp.common.CurrentUserUtils;

public class BaseUserInfoMapService {
	
	public static Map<String,Object> baseUserInfoMap(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tenantId", CurrentUserUtils.getInstance().getUser().getTenantId());
		map.put("createBy", CurrentUserUtils.getInstance().getUser().getLoginId());
		return map;
	}

}
