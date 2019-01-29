/**
 * 
 */
package com.sdp.common;

/**
 * @author renpengyuan
 * @date 2017年9月23日
 */
public class BoncException extends Exception{
	private static final long serialVersionUID = -6691438996541149644L;
	public BoncException() {
	}

	public BoncException(String message) {
		super(message);
	}

	public BoncException(String message, Throwable cause) {
		super(message, cause);
	}

	public BoncException(Throwable cause) {
		super(cause);
	}
}