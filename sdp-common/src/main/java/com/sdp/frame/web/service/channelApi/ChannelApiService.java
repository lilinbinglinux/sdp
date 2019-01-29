package com.sdp.frame.web.service.channelApi;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

@FeignClient(name = "channelapi")
public interface ChannelApiService {

	@RequestMapping(value = "/channelapi/v1/channelContactCount", method = RequestMethod.GET)
	Integer channelContactCount(@PathVariable(value = "phone") String phone,
			@PathVariable(value = "startTime") String startTime,
			@PathVariable(value = "endTime") String endTime);

	@RequestMapping(value = "/channelapi/v1/smsMarketingBatch", method = RequestMethod.GET)
	Integer smsMarketingBatch(@RequestBody JSONArray jsonarray );
}
