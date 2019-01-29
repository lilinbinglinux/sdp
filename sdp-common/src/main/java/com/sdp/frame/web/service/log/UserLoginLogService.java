package com.sdp.frame.web.service.log;

import java.util.Map;

public interface UserLoginLogService {

	public Map selectAll(String start,String length,Map<String,Object>paramMap);
	
	public void deleteLoginMessage(Integer date);
}
