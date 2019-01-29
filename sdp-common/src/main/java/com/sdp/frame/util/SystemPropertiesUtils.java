package com.sdp.frame.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** 
 * @author 作者: jxw 
 * @date 创建时间: 2016年12月23日 下午3:41:58 
 * @version 版本: 1.0 
*/
public class SystemPropertiesUtils {
	 //资源类型与类的对应关系的配置文件路径
	 private static final String PropertiesPath = "system.properties";
	
     private static Properties prop = new Properties();
     
     static {
    	 InputStream in = SystemPropertiesUtils.class.getClassLoader().getResourceAsStream(PropertiesPath);
    	 try {
 			prop.load(in);
 		} catch (IOException e) {
 			e.printStackTrace();
 		}finally{
 			try {
 				in.close();
 			} catch (IOException e) {
 				e.printStackTrace();
 			}
 		}
     }
     
     public static String getFrameStyle(){
    	 return prop.getProperty("system.frameStyle");
     }
     
     
     public static String getSystemTitle(){
    	 return prop.getProperty("system.title");
     }
     
     public static String getTheme(){
    	 return prop.getProperty("system.theme");
     }
     
     public static String isDebug(){
    	 return prop.getProperty("system.debug");
     }
     
     public static String getDate(){
         return prop.getProperty("Date");
     }
     
     public static String getSkipUrls(){
         return prop.getProperty("skipurls");
     }
     
     public static String getUrl(){
         return prop.getProperty("directionalityurl");
     }
     
     public static String getUrlTokenHttp(){
         return prop.getProperty("urlToken.http");
     }
     
     public static String getUrlTokenHttps(){
         return prop.getProperty("urlToken.https");
     }
     
     public static String getUrlApiHttp(){
         return prop.getProperty("urlApi.http");
     }
     
     public static String getUrlApiHttps(){
         return prop.getProperty("urlApi.https");
     }
     
     public static String apiPushOpen(){
         return prop.getProperty("apiPush.open");
     }
}

