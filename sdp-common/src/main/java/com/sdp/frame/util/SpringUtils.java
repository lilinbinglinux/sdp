package com.sdp.frame.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/** 
 * @author 作者: jxw 
 * @date 创建时间: 2016年11月8日 下午2:35:35 
 * @version 版本: 1.0 
 * 
 * @author 作者: rezhuang
 * @date 创建时间: 2017年12月4日15:18:14
 * @version 版本: 1.1 
*/
@Component
public class SpringUtils implements ApplicationContextAware{
	
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtils.applicationContext = applicationContext;
	}
    
	public static Object getBean(String name){
        return applicationContext.getBean(name);
    }
	 // 获取applicationContext
    public static ApplicationContext getApplicationContext() {
       return applicationContext;
    }
    // 通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
           return getApplicationContext().getBean(clazz);
    }

    // 通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
           return getApplicationContext().getBean(name, clazz);
    }
}

