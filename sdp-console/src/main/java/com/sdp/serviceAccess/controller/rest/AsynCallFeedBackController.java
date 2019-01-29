package com.sdp.serviceAccess.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sdp.serviceAccess.proxy.AsynCallFeedBackService;
import com.sdp.serviceAccess.proxy.model.FeedBackRequestParam;
import com.sdp.serviceAccess.proxy.model.ResponseResult;

/**
 * 服务异步调用信息反馈控制器
 * 由于服务提供方的接口大多是异步的，此接口提供给服务提供方用于接收接口的返回信息
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/v1/")
public class AsynCallFeedBackController {

	@Autowired
	AsynCallFeedBackService  service;
	
	private static final Logger LOG = LoggerFactory.getLogger(AsynCallFeedBackController.class);
	
	/**
	 * @param operId  唯一标识
	 * @param feedBackRequestParam code(必要)  errCode(非必要)  errDesc(非必要) data(非必要)
	 * @return
	 */
	 @RequestMapping(value = {"feedback/{operId}"}, method = RequestMethod.PUT)
	 @ResponseBody
	 public ResponseResult feedback(@PathVariable("operId") Long operId,@RequestBody FeedBackRequestParam feedBackRequestParam){
		 LOG.info("AsynCallFeedBackController.feedback接收的参数:operId:{},code:{},errCode:{},errDesc:{},data:{}",
				 operId,feedBackRequestParam.getCode(),feedBackRequestParam.getErrCode(),feedBackRequestParam.getErrDesc(),
				 feedBackRequestParam.getData());
		 ResponseResult result = service.feedback(operId, feedBackRequestParam);
		 LOG.info("返回结果"+result.getCode());
		 return result; 
	 }
}
