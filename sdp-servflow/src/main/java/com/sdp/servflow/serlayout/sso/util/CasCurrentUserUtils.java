
package com.sdp.servflow.serlayout.sso.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sdp.servflow.serlayout.sso.model.Userinfo;


/**
 * 
 * 管理当前当前登录对象
 * @author ke_wang
 * @version 2016年6月24日
 * @see CurrentUserUtils
 * @since
 */
public final class CasCurrentUserUtils {
    
    /**
     * 当前登陆用户session的key
     */
    private final String curUser = "cur_user";
    
    /**
     * CAS_ENABLE
     */
    private static final String CAS_ENABLE = "cas_enable";
    
    /**
     * CurrentUserUtils类型
     */
    private static CasCurrentUserUtils INSTANCE = null;
	
    private CasCurrentUserUtils(){
        
    }
	
    /**
     * 
     * Description:
     * 获取实例
     * @return CurrentUserUtils 
     * @see
     */
    public static CasCurrentUserUtils getInstance(){
        if(INSTANCE == null){
            synchronized (CasCurrentUserUtils.class) {
                if(INSTANCE == null) {
                    INSTANCE = new CasCurrentUserUtils();
                }
            }
        }
        return INSTANCE;
    }
    
    /**
     * 
     * Description:
     * 获取当前Request
     * @return HttpServletRequest
     * @see
     */
    private HttpServletRequest getRequest() {  
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();  
        return requestAttributes.getRequest();  
    } 
	
    /**
     * 
     * Description:
     * 获取当前Session
     * @return HttpSession
     * @see
     */
    private HttpSession getSession() {  
        return getRequest().getSession(true);  
    }
	
    /**
     * 
     * Description:
     * 获取当前session里面放置的User对象
     * @return User
     * @see
     */
    public Userinfo getUser(){
        return (Userinfo)getSession().getAttribute(curUser);
    }
	
    /**
     * 
     * Description:
     * 把当前User对象放置到session里面
     * @param user User
     * @see
     */
    public void setUser(Userinfo user){
        getSession().setAttribute(curUser, user);
    }
    
    /**
     * 
     * Description: <br>
     *  获取casEnable
     * @return Boolean
     * @see
     */
    public Boolean getCasEnable(){
        return (Boolean) getSession().getAttribute(CAS_ENABLE);
    }
    
    /**
     * 
     * Description: 
     * setCasEnable
     * @param cas_enable 
     * @see
     */
    public void setCasEnable(Boolean cas_enable){
        getSession().setAttribute(CAS_ENABLE, cas_enable);
    }
	
    /**
     * 
     * Description:
     * 退出当前用户
     * @param user User
     * @see
     */
    public void loginoutUser(Userinfo user){
        Enumeration<String> em = getSession().getAttributeNames();
        while (em.hasMoreElements()) {
            getSession().removeAttribute(em.nextElement().toString());
        }
        getSession().removeAttribute(curUser);
        getSession().invalidate();
    }
}