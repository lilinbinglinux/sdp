package com.sdp.servflow.pubandorder.format.imp;

/**
 * 复杂JSON解析工具类
 *
 */
public class JsonTools {

    
    /**
     * 替换json字符串中的key
     * */
   public static String replaceJsonKey(String jsonString,String source, String target)
   {
       return jsonString.replaceAll("[\" ]"+source+"[^:\" ]*[\" ]*:", "\""+target+"\":"); 
   }

    public static void main(String[] args) throws Exception {
        
        String str="[{  \" username \"    :   123 ,   \"password\" :'pwd1',    time :\"2013-02-02\" } "
            +",{  \" username \"    :   123 ,   \"password\" :'789',    time :\"2013-02-02\" }] "; 
        str=replaceJsonKey(str, "password", "任壮");
        System.out.println(str);
        
        String a = "尊敬的联通用户，您好，近期有用户反映荣耀9部分型号手机在插有移动卡时不能自由切换选择我司提供的极速4G网络，为不影响您的使用感知，特友情提醒您的荣耀9手机型号为移动专网定制机，属非全网通，如您购买该型号手机时未获得相关信息，可根据《消费者权益保护法》相关规定，要求予以退换。";
        System.out.println(a.length());
        
    }


   
}