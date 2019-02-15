/**
 * 
 */
package com.sdp.serviceAccess.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sdp.serviceAccess.service.IPProductCaseService;

/**   
 * Copyright: Copyright (c) 2018 BONC
 * 
 * @ClassName: TimeAop.java
 * @Description: 打印当前调用的业务逻辑开销的时间
 *
 * @version: v1.0.0
 * @author: renpengyuan
 * @date: 2018年8月2日 下午4:15:04 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年8月2日     renpengyuan      v1.0.0               修改原因
 */
@Aspect
@Component("timeAop")
public class TimeAop {

	private static final Logger LOG =  LoggerFactory.getLogger(TimeAop.class);

	@Pointcut("execution(* com.sdp.serviceAccess.service.impl.*.*(..))")
	public void annotationPointCut() {
	}

	@Around("annotationPointCut()")
	public Object twiceAsOld(ProceedingJoinPoint joinPoint){
		Object output = null;
		try{
			long start = System.currentTimeMillis();
			output = joinPoint.proceed();
			long elapsedTime = System.currentTimeMillis() - start;

			// 执行的真实类名称
			String className = joinPoint.getTarget().getClass().getSimpleName();
			LOG.info("current Class Name :{},current Method Name:{},调用当前方法消耗时间:{} ms",className,joinPoint.getSignature().getName(),elapsedTime);
		}catch(Throwable e){
			LOG.error("Time Aop error",e);
		}
		return output;
	}

}
