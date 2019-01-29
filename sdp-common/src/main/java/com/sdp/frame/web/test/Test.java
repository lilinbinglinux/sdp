package com.sdp.frame.web.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import com.sdp.frame.web.service.channelApi.ChannelApiService;

public class Test {
	@Autowired
	ChannelApiService channelApiService;
	
	public Integer getChannelContactCount(@PathVariable(value = "phone") String phone,
			@PathVariable(value = "startTime") String startTime,
			@PathVariable(value = "endTime") String endTime){
		return channelApiService.channelContactCount(phone, startTime, endTime);
	}

}
