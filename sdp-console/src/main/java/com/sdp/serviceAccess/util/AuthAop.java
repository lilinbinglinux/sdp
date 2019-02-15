package com.sdp.serviceAccess.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sdp.common.CurrentUserUtils;
import com.sdp.common.constant.Dictionary;
import com.sdp.frame.util.StringUtil;
import com.sdp.frame.web.entity.user.User;

/**
 * 
* @Description: 查询权限切面
  @ClassName: AuthAop
* @author zy
* @date 2018年9月4日
* @company:www.sdp.com.cn
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年9月4日     zy      v1.0.0               修改原因
 */
@Aspect
@Component("authAop")
public class AuthAop {

	private static final Logger LOG =  LoggerFactory.getLogger(AuthAop.class);
	
	@Pointcut("execution(* com.sdp.serviceAccess.controller.rest.*.auth_*(..))")
	public void annotationPointCut() {
	}
	/**
	 * 权限切面，构建session中dataUser对象
	 * 1. 请求中有dataAuth参数，如果该参数为Dictionary.AuthToken.All.value，则dataUser对象tenantId和loginId都为空。其他情况见2
	 * 2.currentAuth依次取自请求中的dataAuth参数，若请求中dataAuth为空，currentAuth取自当前用户的dataAuth。分别根据currentAuth中的值为dataUser赋值。
	 * @param call
	 * @return
	 */
	@Before("annotationPointCut()")
	public Object twiceAsOld(JoinPoint call) {
		Object output = null;
		try {
			String clazz = call.getTarget().getClass().getName();
			String methodName = call.getSignature().getName();
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			// 执行的真实类名称
			HttpSession session = request.getSession();
			session.setAttribute(CurrentUserUtils.DATAUTH, null);
			User user = CurrentUserUtils.getInstance().getUser();
			User dataUser = new User();
			String currentAuth = null;
			Object[] args = call.getArgs();
			if (args != null && args.length > 0) {
				if ((args[0]) instanceof Map) {
					currentAuth = (String) ((Map) (args[0])).get(CurrentUserUtils.DATAUTH);
				}
			}
			if (StringUtil.isNotEmpty(currentAuth) && currentAuth.equals(Dictionary.AuthToken.All.value)) {
				dataUser.setDataAuth(currentAuth);
				LOG.info("current Class Name :{},current Method Name:{},loginId:{},tanantId:{}", clazz, methodName,
						dataUser.getLoginId(), dataUser.getTenantId());
				session.setAttribute(CurrentUserUtils.DATAUTH, dataUser);
			} else if (null != user) {
				if (!StringUtil.isNotEmpty(currentAuth)) {
					currentAuth = user.getDataAuth();
				}
				if (!StringUtil.isNotEmpty(currentAuth)) {
					throw new Exception("userAuth is null");
				}
				if (currentAuth.equals(Dictionary.AuthToken.TenantId.value)) {
					dataUser.setTenantId(user.getTenantId());
				} else if (currentAuth.equals(Dictionary.AuthToken.LoginId.value)) {
					dataUser.setTenantId(user.getTenantId());
					dataUser.setLoginId(user.getLoginId());
				}
				dataUser.setDataAuth(currentAuth);

				LOG.info("current Class Name :{},current Method Name:{},loginId:{},tanantId:{}", clazz, methodName,
						dataUser.getLoginId(), dataUser.getTenantId());
				session.setAttribute(CurrentUserUtils.DATAUTH, dataUser);
			} else {
				throw new Exception("userAuth is null");
			}
		} catch (Throwable e) {
			LOG.error("Time Aop error", e);
		}
		return output;
	}
	
}
