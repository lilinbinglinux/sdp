package com.sdp.servflow.pubandorder.protocol;


import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * 
 * webService工具类
 *
 * @author 任壮
 * @version 2017年7月27日
 * @see WebServiceUtil
 * @since
 */
public class WebServiceUtil {

    /**
     * 
     * Description: webService post工具类
     * 
     *@param url
     *@param parm
     *@return
     *@throws IOException String
     *
     * @see
     */
    @SuppressWarnings("deprecation")
    public static String post(String url, String parm)
        throws IOException {
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        // 3.设置请求参数
        postMethod.setRequestBody(parm);
        // 修改请求的头部
        postMethod.setRequestHeader("Content-Type", "text/xml; charset=utf-8");
        // 4.执行请求 ,结果码
        @SuppressWarnings("unused")
        int code = client.executeMethod(postMethod);
        // 5. 获取结果
        String result = postMethod.getResponseBodyAsString();
        return result;
    }

    public static void main(String[] args) throws IOException {
        String url ="http://WebXml.com.cn/getWeatherbyCityName";
        
        String xml=     " <?xml version=\"1.0\" encoding=\"utf-8\"?>"
        +"<ArrayOfString xmlns=\"http://WebXml.com.cn/\">"
        +  "  <string>string</string>"
        +   "  <string>string</string>"
        +  " </ArrayOfString>";
    System.out.println(post(url, xml));
    }
    
    
}
