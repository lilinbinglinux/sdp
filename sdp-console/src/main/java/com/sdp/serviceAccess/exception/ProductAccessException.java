/**
 * 
 */
package com.sdp.serviceAccess.exception;

/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: ProductAccessException.java
* @Description: 服务接入的基本异常
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年8月3日 下午12:10:28 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月3日     renpengyuan      v1.0.0               修改原因
*/
public class ProductAccessException extends RuntimeException{

	private static final long serialVersionUID = -297313826442031866L;
	
	public ProductAccessException(){
		super();
	}
	
	public ProductAccessException(String message){
		super(message);
	}
	
	public ProductAccessException(String message,Throwable e){
		super(message,e);
	}
	

}
