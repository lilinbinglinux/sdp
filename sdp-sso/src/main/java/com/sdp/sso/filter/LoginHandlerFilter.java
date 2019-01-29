package com.sdp.sso.filter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bonc.security.client.SecurityClient;
import com.bonc.security.entity.Userinfo;
import com.sdp.SpringApplicationContext;
import com.sdp.frame.security.authication.provider.NormalAuthicationProvider;
import com.sdp.frame.security.util.Constant;
import com.sdp.frame.web.entity.user.User;
import com.sdp.frame.web.service.user.UserService;
import com.sdp.util.MD5Pass;

public class LoginHandlerFilter implements Filter {
	
	@Autowired
    private UserService userService;
	
    public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private List<Filter> filterList = new ArrayList();

    private String[] skipUrls;
    
	private Logger logger = LoggerFactory.getLogger(LoginHandlerFilter.class);

    public void init(FilterConfig filterConfig)
        throws ServletException {
        String skipUrl = filterConfig.getInitParameter("skipUrls");
        if ((skipUrl != null) && (skipUrl.trim().length() > 0)) {
            this.skipUrls = skipUrl.split(",");
        }
        for (Filter fitler : this.filterList) {
            fitler.init(filterConfig);
        }
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain)
                             throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        
        request.getSession();
        response.setHeader("P3P","CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");

        if (preFilter(servletRequest, servletResponse)) {
            chain.doFilter(servletRequest, servletResponse);
        }
        else {
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
	                    userobj.setUserName(userinfo.getUserName() == null?"":userinfo.getUserName());
	                    userobj.setSex(userinfo.getSex() == null?"":userinfo.getSex().getSexName());
	                    userobj.setEmall(userinfo.getEmail() == null?"":userinfo.getEmail());
	                    userobj.setMobile(userinfo.getMobile() == null?"":userinfo.getMobile());
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
	                    
	                    String othloginid = SecurityClient.findCurrentLoginId(request);
	                    User othuser = userService.selectByLoginId(othloginid);
	                    if(null == othuser){
	                    	userService.insert(userobj);
	                    }
	                    user = userobj;
	                }
	            	
	            	
	            	session.setAttribute(Constant.ResourceType.UserResource.toString(), user);
	                
	                User xuser = userService.selectByLoginId("xadmin");
	                
	                NormalAuthicationProvider provider = new NormalAuthicationProvider();
	                provider.putButtonResource(request, xuser.getUserId());
	                provider.putMenuResource(request, xuser.getUserId());
	                provider.putUserMenuResource(request, xuser.getUserId());
	                
	                provider.putUserResource(request, user.getUserId());
            }
            
            } catch (Exception e) {
				e.printStackTrace();
			}
            
            chain.doFilter(servletRequest, servletResponse);
        
        }
    }

    protected boolean preFilter(ServletRequest servletRequest, ServletResponse servletResponse) {
        boolean skip = false;
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();

        if (!"/".equals(contextPath)) {
            requestURI = requestURI.substring(contextPath.length());
        }

        if (this.skipUrls != null) {
            for (String s : this.skipUrls) {
                skip = Pattern.matches(s, requestURI);
                if (skip) {
                    break;
                }
            }
        }
        return skip;
    }

    public void destroy() {
        this.filterList.clear();
        this.filterList = null;
    }

}

