/*
 * 文件名：Status.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：zhoutao
 * 修改时间：2017年3月14日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.common.entity;

/**
 * 新增、删除、更新操作返回对象
 * @author zhoutao
 * @version 2017年8月16日
 * @see Status
 * @since
 */
public class Status {
	private String message;
	private Integer code;

	public Status() {

	}

	/**
	 *
	 * Description:
	 * 构造返回信息，如果需要返回对象是Status的情况下
	 * @param code
	 * @param message
	 */
	public Status(String message, Integer code) {
		this.message = message;
		this.code = code;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}

	public Status1 instanceStatus1() {
		return 	new Status1();
	}

	public class Status1 extends Status{
		private String orderId;

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

	}
}
