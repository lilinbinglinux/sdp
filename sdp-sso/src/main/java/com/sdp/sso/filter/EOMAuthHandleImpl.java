package com.sdp.sso.filter;
//
//package com.ssoConfig.filter;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StringUtils;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.bonc.frame.security.util.Constant;
//import com.bonc.frame.web.entity.user.User;
//import com.bonc.frame.web.service.user.UserService;
//import com.fream.SpringApplicationContext;
//import com.ssoConfig.service.ISecurityApiService;
//import com.ssoConfig.util.CurrentThreadContext;
//
///**
// *  单点登录后的模拟登录
// * @author song
// */
//@Component
//public class EOMAuthHandleImpl implements IAuthHandle{
//    @Autowired
//    private ISecurityApiService securityApiService;
//    
//    @Autowired
//    private UserService userService;
//
//    public boolean onSuccess(HttpServletRequest request, HttpServletResponse response, String loginId) {
//        SpringApplicationContext.CONTEXT.getAutowireCapableBeanFactory().autowireBean(this);
//        packageUserInfo(loginId,request.getSession(true));
//		return true;
//	}
//    /**
//     * 
//     * Description:封装登录用户相关信息 
//     * 
//     *@param casLoginId
//     *@param session void
//     *
//     * @see
//     */
//    private void packageUserInfo(String casLoginId,HttpSession session){
//        CurrentThreadContext.setValue(CurrentThreadContext.CURRENT_USER_ID, casLoginId);
//        Object sessionInfo = session.getAttribute(casLoginId);
//        if(sessionInfo!=null){
//            JSONObject userInfo = (JSONObject)session.getAttribute(casLoginId);
//            if(!StringUtils.isEmpty(userInfo.get("userName"))){
//                CurrentThreadContext.setValue(CurrentThreadContext.CURRENT_USER_NAME, userInfo.get("userName"));
//                CurrentThreadContext.setValue(CurrentThreadContext.CURRENT_USER_TENANTID, userInfo.get("tenantId"));
//                return;
//            }
//        }
//        
//        JSONObject userInfo = new JSONObject();
//        session.setAttribute(casLoginId, userInfo);
//        if(securityApiService==null){
//            securityApiService = SpringApplicationContext.CONTEXT.getBean(ISecurityApiService.class);
//        }
//        String userListStr = securityApiService.findUserList(casLoginId);
//        List<JSONObject> list = JSONArray.parseArray(userListStr,JSONObject.class);
//        if(!CollectionUtils.isEmpty(list)){
//            userInfo = list.get(0);
//            session.setAttribute(casLoginId, userInfo);
//            CurrentThreadContext.setValue(CurrentThreadContext.CURRENT_USER_NAME, userInfo.get("userName"));
//        }
//        String tenantId = securityApiService.findTenantId(casLoginId);
//        if(!StringUtils.isEmpty(tenantId)){
//            userInfo.put("tenantId", tenantId);
//            session.setAttribute(casLoginId, userInfo);
//            CurrentThreadContext.setValue(CurrentThreadContext.CURRENT_USER_TENANTID, tenantId);
//        }
//        
//        String users = securityApiService.findUserList(CurrentThreadContext.getCurrentUserId());
//        net.sf.json.JSONArray userArr = net.sf.json.JSONArray.fromObject(users);
//        net.sf.json.JSONObject userobj = net.sf.json.JSONObject.fromObject(userArr.get(0)); 
//        User user = userService.selectByLoginId(userobj.getString("loginId"));
//        System.out.println(user);
//        session.setAttribute(Constant.ResourceType.UserResource.toString(), user);
//        
//    }
//}
