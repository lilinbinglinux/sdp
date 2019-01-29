package com.sdp.servflow.pubandorder.util;

import java.lang.reflect.Method;

import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

import com.alibaba.fastjson.JSON;

/***
 * 
 * 从文件中获取调用jar包方法的工具类
 *
 * @author 任壮
 * @version 2017年9月6日
 * @see Invoke
 * @since
 */
public class Invoke {
    
    /** 
     *  
     * @Description: 使用commons的jexl可实现将字符串变成可执行代码的功能 
     */  
    public static Object convertToCode(String jexlExp,Map<String,Object> map){    
        JexlEngine jexl=new JexlEngine();    
        Expression e = jexl.createExpression(jexlExp);    
        JexlContext jc = new MapContext();    
        for(String key:map.keySet()){    
            jc.set(key, map.get(key));    
        }    
        if(null==e.evaluate(jc)){    
            return "";    
        }    
        return e.evaluate(jc);    
    }
    
    
    public static Method initAddMethod() {  
        try {  
            Method add = URLClassLoader.class.getDeclaredMethod("addURL",  
                    new Class[] { URL.class });  
            add.setAccessible(true);  
            return add;  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
  
    public static Object callMethod(String url,String className,String method,String parameterTypes) {
        System.out.println("url"+url);
        /* 
         *  系统加载jar 
         */  
        try {  
                // 系统ClassLoader只能加载.jar  
                // 动态加载jar  
            Method  addURL = initAddMethod();  
            URLClassLoader    classloader = (URLClassLoader)ClassLoader.getSystemClassLoader();  
                 URL classUrl = new URL(url);  
                 addURL.invoke(classloader, new Object[] { classUrl });  
                  
                 Class<?> c = Class.forName(className);  
                 Object obj = c.newInstance();  
                 // 被调用函数的参数  
                  Method method2 = c.getDeclaredMethod(method, String.class );  
               return   method2.invoke(obj, parameterTypes);
        } catch (Exception exp) {  
            exp.printStackTrace();  
        }  
        return null;
    }
    
    
    
    
    public static void main(String[] args) {  
      //  String url = "file:C:/Users/Administrator/Desktop/esb/jars/20170912/xorder.jar"; // 包路径定义  
          String url = "file:C:/Users/Administrator/Desktop/esb/jars/2017912/xorder.jar"; // 包路径定义  
        
        String className = "com.bonc.servflow.test.TestEncapsulation";  
        String method = "execute";  
        HashMap<String,Object> sysParam = new HashMap<String,Object>();
        sysParam.put("method", "ABILITY_10002002");
        sysParam.put("format", "json");
        sysParam.put("appId", "501282");
        sysParam.put("timestamp", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));  
        sysParam.put("RegionId", "100000001008");  
        sysParam.put("busiSerial", "12");
        sysParam.put("version", "1.0");
        sysParam.put("operId", "100231302");
        sysParam.put("openId", "7d54957d-f29b-4f20-bb79-f0d8db049f3b");  
        sysParam.put("app_key", "5c6634a60608dffe6e1ac8a7b119c877");  //固定值，准入程序分配时生成的密钥（加密参数使用）。
        sysParam.put("accessToken", "1231456445");  //固定值，准入程序分配时生成的密钥（加密参数使用）。
        sysParam.put("msg", "23"); 
        System.out.println(callMethod(url, className, method, JSON.toJSONString(sysParam)));
  
/*         
         *  系统加载jar 
           
        try {  
                // 系统ClassLoader只能加载.jar  
                // 动态加载jar  
                 addURL = initAddMethod();  
                 classloader = (URLClassLoader)ClassLoader.getSystemClassLoader();  
               //  String url = "file:"+ System.getProperty("user.dir") + "/lib/yerasel/GetPI.jar"; // 包路径定义  
                 System.out.println(url);  
                 URL classUrl = new URL(url);  
                 addURL.invoke(classloader, new Object[] { classUrl });  
                  
                 Class<?> c = Class.forName(className);  
                 Method[] methods = c.getMethods();
                 for (Method method : methods) {
                     String methodName = method.getName();
                     System.out.println("方法名称:" + methodName);
                     Class<?>[] parameterTypes = method.getParameterTypes();
                     for (Class<?> clas : parameterTypes) {
                         String parameterName = clas.getName();
                         System.out.println("参数名称:" + parameterName);
                      //   Method method2 = clazz.getDeclaredMethod("execute",clas);
                     }
                     System.out.println("*****************************");
                 }
                 Object obj = c.newInstance();  
                 // 被调用函数的参数  
                 Class[] parameterTypes = {};  
                 Method method2 = c.getDeclaredMethod("execute",parameterTypes);  
                 method2.invoke(obj, null);  
        } catch (Exception exp) {  
            exp.printStackTrace();  
        } */ 
    }  
}
