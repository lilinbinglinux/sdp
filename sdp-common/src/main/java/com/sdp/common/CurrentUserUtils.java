
package com.sdp.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sdp.frame.security.util.Constant;
import com.sdp.frame.web.entity.user.User;


/**
 *
 * 管理当前当前登录对象
 *
 * @author ZY
 * @version 2017年7月20日
 * @see CurrentUserUtils
 * @since
 */
public final class CurrentUserUtils {

    /**
     * 当前登陆用户session的key
     */
    private final String curUser = Constant.ResourceType.UserResource.toString();

    private final String curProjectId = Constant.ResourceType.ProjectIdResource.toString();

    public final static String DATAUTH = "dataAuth";
    /**
     * CurrentUserUtils类型
     */
    private static CurrentUserUtils INSTANCE = null;

    private CurrentUserUtils(){

    }

    /**
     *
     * Description:
     * 获取实例
     * @return CurrentUserUtils
     * @see
     */
    public static CurrentUserUtils getInstance(){
        if(INSTANCE == null){
            synchronized (CurrentUserUtils.class) {
                if(INSTANCE == null) {
                    INSTANCE = new CurrentUserUtils();
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
    public User getUser(){
//    	User user = new User();
//    	user.setLoginId("admin");
//    	user.setTenantId("systenant");
//    	return user;
        return (User)getSession().getAttribute(curUser);
    }
    /**
     *
     * Description:
     * 获取当前session里面放置的DataUser对象
     * @return User
     * @see
     */
    public User getDataUser(){
        return (User)getSession().getAttribute(DATAUTH);
    }

    /**
     *
     * Description:
     * 把当前User对象放置到session里面
     * @param user User
     * @see
     */
//    public void setUser(User user){
//    	System.out.println(user);
//        getSession().setAttribute(curUser, user);
//    }

    /**
     *
     * Description:
     * 把当前projectId放置到session里面
     * @return  projectId
     * @see
     */
    public String getProjectId(){
        return (String)getSession().getAttribute(curProjectId);
    }

}