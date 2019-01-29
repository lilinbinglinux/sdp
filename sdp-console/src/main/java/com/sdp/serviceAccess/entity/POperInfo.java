/**
 * 
 */
package com.sdp.serviceAccess.entity;


/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: POperInfo.java
* @Description: 回调基本操作日志对应model类。
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年8月13日 上午10:45:54 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月13日     renpengyuan      v1.0.0               修改原因
*/
public class POperInfo extends BaseInfo{
	
	
	/**
     * 主键唯一标识
     */
    private Long operId;
    
    /**
     * 对应的服务对象名字
     */
    private String serviceName;

    /**
     * CSvcBusiInfo的JSON字符串
     */
    private String busiInfo;
    
    /**
     * 参数map的JSON字符串
     */
    private String paramsInfo;
    
    /**
     * 异步接口反馈信息JSON字符串
     */
    private String resultInfo;
    
    /**
     * 1   待处理
     * 2   处理成功
     * 3   SP调用响应异常
     * 4   SP调用响应失败
     * 5   SP调用响应成功
     * 6   SP回调超时
     * 7   SP回调响应异常
     * 8   SP回调响应失败
     * 9   SP回调响应成功
     * 10  handle调用响应异常
     * 11  handle调用响应失败
     * 12  handle调用响应成功
     */
    private int state;

	/**
	 * @return the operId
	 */
	public Long getOperId() {
		return operId;
	}

	/**
	 * @param operId the operId to set
	 */
	public void setOperId(Long operId) {
		this.operId = operId;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the busiInfo
	 */
	public String getBusiInfo() {
		return busiInfo;
	}

	/**
	 * @param busiInfo the busiInfo to set
	 */
	public void setBusiInfo(String busiInfo) {
		this.busiInfo = busiInfo;
	}

	/**
	 * @return the paramsInfo
	 */
	public String getParamsInfo() {
		return paramsInfo;
	}

	/**
	 * @param paramsInfo the paramsInfo to set
	 */
	public void setParamsInfo(String paramsInfo) {
		this.paramsInfo = paramsInfo;
	}

	/**
	 * @return the resultInfo
	 */
	public String getResultInfo() {
		return resultInfo;
	}

	/**
	 * @param resultInfo the resultInfo to set
	 */
	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}
    
    

}
