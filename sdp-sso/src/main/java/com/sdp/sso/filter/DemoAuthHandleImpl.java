package com.sdp.sso.filter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bonc.security.client.SecurityClient;
import com.bonc.security.entity.Userinfo;
import com.bonc.sso.client.IAuthHandle;
import com.sdp.SpringApplicationContext;
import com.sdp.frame.security.authication.provider.NormalAuthicationProvider;
import com.sdp.frame.security.util.Constant;
import com.sdp.frame.web.entity.user.User;
import com.sdp.frame.web.service.user.UserService;
import com.sdp.util.MD5Pass;

//@Component
public class DemoAuthHandleImpl  {
    
    @Autowired
    private UserService userService;
    
    private Logger logger = LoggerFactory.getLogger(LoginHandlerFilter.class);
    /**
     * 单点登录成功后操作
     *
     * @param request
     *  请求
     * @param response
     *  响应
     * @param loginId
     *  登录名
     * @return 当前应用是否允许登录
     */
 
    public boolean onSuccess(HttpServletRequest request, HttpServletResponse response, String loginId) {


        String loginid = SecurityClient.findCurrentLoginId(request);
        if(StringUtils.isBlank(loginid)){
            loginid = "";
        }
        
        logger.info("-----------  Current cas user loginid   :   "+loginid+" ------------- ");
        
        // 业务代码
        SpringApplicationContext.CONTEXT.getAutowireCapableBeanFactory().autowireBean(this);
        HttpSession session = request.getSession();
        User user = userService.selectByLoginId(loginid);
        Userinfo userinfo = SecurityClient.findUserByLoginId(loginid);
        try {
        if(userinfo != null) {
            	if(null == user){
                    User userobj = new User();
                    userobj.setUserId(userinfo.getLoginId());
                    userobj.setLoginId(loginid);
                    userobj.setPassword(MD5Pass.getMD5Passwd("123456"));
                    userobj.setUserName(userinfo.getUserName());
                    userobj.setSex(userinfo.getSex().getSexName());
                    userobj.setEmall(userinfo.getEmail());
                    userobj.setMobile(userinfo.getMobile());
                    userobj.setState("1");
                    userobj.setPwdState("1");
                    userobj.setMemo("bcm user");
                    
                    //先写死租户，防止能力平台过来的用户没有租户
                    //userobj.setTenantId("tenant_system");
                    
                    if(StringUtils.isNotBlank(SecurityClient.findCurrentTenantId(request))){
                        userobj.setTenantId(SecurityClient.findCurrentTenantId(request));                    
                    }else{
                        userobj.setTenantId(loginid);
                    }
                    
                    session.setAttribute(Constant.ResourceType.UserResource.toString(), userobj);
                    userService.insert(userobj);
					
                    user = userobj;
                }
            	
            	
            		session.setAttribute(Constant.ResourceType.UserResource.toString(), user);
                
                User xuser = userService.selectByLoginId("admin");
                
                NormalAuthicationProvider provider = new NormalAuthicationProvider();
                provider.putButtonResource(request, xuser.getUserId());
                provider.putMenuResource(request, xuser.getUserId());
                provider.putUserMenuResource(request, xuser.getUserId());
                
                provider.putUserResource(request, user.getUserId());
        }
        
        } catch (Exception e) {
			e.printStackTrace();
		}
        
        return true;
    
    
    }

}
