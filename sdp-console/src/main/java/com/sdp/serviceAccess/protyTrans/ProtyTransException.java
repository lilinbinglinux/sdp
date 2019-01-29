/**
 * 
 */
package com.sdp.serviceAccess.protyTrans;

/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: ProtyTransException.java
* @Description: 转换基本类
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年7月31日 下午3:21:16 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年7月31日     renpengyuan      v1.0.0               修改原因
*/
public class ProtyTransException extends RuntimeException{
	
	private static final long serialVersionUID = 5162710183389028792L;
    
	public ProtyTransException(){
		super();
	}
	
	public ProtyTransException(String message){
		super(message);
	}
	
	public ProtyTransException(String message,Throwable th){
		super(message,th);
	}
	
}
