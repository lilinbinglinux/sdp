package com.sdp.cop.octopus.util.notify;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
  
/**
 * 用户信息认证实体
 * @author ke_wang
 * @since 2016-05-25
 *
 */
public class MyAuthenticator extends Authenticator{
	
	/**
	 * 用户名
	 */
    private String userName=null;
	
	/**
	 * 密码
	 */
    private String password=null;
	 
    public MyAuthenticator(){
		
    }
    
    public MyAuthenticator(String username, String password) { 
    	this.userName = username; 
    	this.password = password; 
    }
    
    protected PasswordAuthentication getPasswordAuthentication(){
    	return new PasswordAuthentication(userName, password);
    }
}
 
