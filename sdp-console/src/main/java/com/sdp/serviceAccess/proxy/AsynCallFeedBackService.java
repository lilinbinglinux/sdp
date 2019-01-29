package com.sdp.serviceAccess.proxy;


import org.springframework.stereotype.Service;

import com.sdp.serviceAccess.proxy.model.FeedBackRequestParam;
import com.sdp.serviceAccess.proxy.model.ResponseResult;

/**
 * 由于服务提供方的接口大多是异步的，此接口提供给服务提供方用于接收接口的返回信息
 * @author Administrator
 */
@Service
public interface AsynCallFeedBackService {

	/**
	 * @param feedBackRequestParam
	 * @return
	 */
	ResponseResult feedback(Long operId,FeedBackRequestParam feedBackRequestParam);

}
