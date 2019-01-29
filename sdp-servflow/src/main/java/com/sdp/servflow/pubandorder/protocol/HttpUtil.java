package com.sdp.servflow.pubandorder.protocol;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.sdp.servflow.pubandorder.format.imp.XmlFormat;
import com.sdp.servflow.pubandorder.util.IContants;


/***
 * http请求的工具类
 *
 * @author 任壮
 * @version 2017年7月27日
 * @see HttpUtil
 * @since
 */
public class HttpUtil {
    /***
     * log日志
     */
    private static final Logger LOG= Logger.getLogger(HttpUtil.class);
    /**
     * 连接时间
     */
    private static int CON_TIME_OUT = 60000;
    /**
     * 超时时间
     */
    private static int READ_TIME_OUT = 120000;
    /**
     * 默认字符集
     */
    public static String DEFAULTCHARSWT="UTF-8";
    
    
    public static void main(String[] args) throws Exception {/*
        
        
        
        String soap =
            
  "      <?xml version=\"1.0\" encoding=\"UTF-8\"?>                                                               "
  +"  <soapenv:Envelope  xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"                             "
  +"  xmlns:weat=\"http://www.unicom.cn /cop/weather\" xmlns:loc=\"http://www. unicom.cn m/cop/weather/local\">    "
  +"  <soapenv:Header/>                                                                                        "
  +"  <soapenv:Body>                                                                                           "
  +"  <loc:getWeatherReq>                                                                                      "
  +"  <loc:date>20150316</loc:date>                                                                            "
  +"  <loc:location>nanjing</loc:location>                                                                     "
  +"  </loc:getWeatherReq>                                                                                     "
  +"  </soapenv:Body>                                                                                          "
  +"  </soapenv:Envelope>                                                                                      ";
        
        
        
        
        
        
        String url = " http://10.124.192.78:22222/getsoapweather";
        String soapRequestData = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
            + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
            + " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\""
            + " xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\""
            +"  xmlns:web=\"http://WebXml.com.cn/\">"
            + "<soap:Body>"
            +" <web:getFundCodeNameDataSet xmlns=\"http://WebXml.com.cn/\" />"
            + "</soap:Body>"
            + "</soap:Envelope>";
    
        HttpClient httpClient = new HttpClient();
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("Content-type", IContants.SOAP_1_1);
        try {
            System.out.println(post(url, soap, httpClient, headers));
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    */
    HashMap<String, Object> hash = new HashMap<String,Object>();
    hash.put("term", "福远渔");
    hash.put("key", "e6ee1f4fc9d4321dd83775d4ba0c8450");
    	
    	System.out.println(get("http://api.myships.com/DataApiServer/getShipId", hash, new HttpClient(), null));
    
    }

    /****
     * 
     * Description: post请求
     * 
     *@param url
     *@param param
     *@param httpClient
     *@param headers
     *@return
     *@throws Exception Map<String,Object>
     *
     * @see
     */
    @SuppressWarnings("deprecation")
    public static Map<String, Object> post(String url, Object param, HttpClient httpClient,Map<String, Object> headers) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        PostMethod postMethod = new PostMethod(url);
        try {
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(CON_TIME_OUT);
            //配置HTTP请求响应超时时间
            httpClient.getHttpConnectionManager().getParams().setSoTimeout(READ_TIME_OUT);

            //postMethod.setRequestHeader("charset", DEFAULTCHARSWT);
            if(headers!=null) { 
                for(Entry<String, Object> header:headers.entrySet()) {
                    postMethod.setRequestHeader(header.getKey(),(String)header.getValue());
                }
            }
            String contentType =  (String)headers.get("Content-type");
            if((contentType).contains(IContants.JSON)) {
               /* Map<String, Object> map = (Map<String, Object>)param;
                String paramString = JSONObject.toJSONString(map);*/
                postMethod.setRequestBody((String)param);
            }else if((contentType).contains(IContants.FORM) ) {
                Map<String, Object> map = (Map<String, Object>)param;
                NameValuePair[] data =mapToNameValuePair(map);   
                postMethod.setRequestBody(data);
            }else if((contentType).contains(IContants.XML) ||
                (contentType).contains(IContants.SOAP_1_1) ||
                (contentType).contains(IContants.SOAP_1_2)
                ) {
                String cutString = (String)param;
                byte[] b = cutString.getBytes("utf-8");
                InputStream is = new ByteArrayInputStream(b, 0, b.length);
                RequestEntity re = new InputStreamRequestEntity(is, b.length,
                    headers.get("Content-type")+"");
                postMethod.setRequestEntity(re);
            }
            
            httpClient.executeMethod(postMethod);
            result.put("responseHeader", nameValuePairToMap(postMethod.getResponseHeaders()));
            result.put("responseBody", postMethod.getResponseBodyAsString());
        }
        catch (Exception e) {
             e.printStackTrace();
            throw new Exception("post请求异常,url:"+url);
        }finally {
            postMethod.releaseConnection();
        }
        return result;
    }

     public static String getUrl(HttpServletRequest request) {
    	String strBackUrl = "http://" + request.getServerName() //服务器地址  
        + ":"   
        + request.getServerPort()           //端口号  
        + request.getContextPath()      //项目名称  
        + request.getServletPath()      //请求页面或其他地址  
    + "?" + (request.getQueryString()); //参数  
    	return strBackUrl;
    }
    
    
    
    
    /****
     * 
     * Description: Http get请求
     * 
     *@param url
     *@param params
     *@param httpClient 
     *@param headers    请求头信息
     *@return
     *@throws Exception Map<String,Object>
     *
     * @see
     */
    public static  Map<String, Object> get(String url, Map<String, Object> params, HttpClient httpClient,Map<String, Object> headers) throws Exception { 
        Map<String, Object> result = new HashMap<String, Object>();
        StringBuffer sb = new StringBuffer();
        
        
        if (params != null) {
            for (Entry<String, Object> e : params.entrySet()) {
                sb.append(e.getKey());
                sb.append("=");
                String value  = (String)e.getValue();
                sb.append(URLEncoder.encode(value, DEFAULTCHARSWT));
                sb.append("&");
            }
        }
        if (!"".equals(sb.toString().trim())) {
            url = url + "?" + sb.substring(0, sb.length() - 1).toString();
        }
        System.out.println("--url--"+url);
        GetMethod getMethod1 = new GetMethod(url);
        try {
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(CON_TIME_OUT);
            //配置HTTP请求响应超时时间
            httpClient.getHttpConnectionManager().getParams().setSoTimeout(READ_TIME_OUT);


            //getMethod1.setFollowRedirects(false);
           // getMethod1.setRequestHeader("Referer", "http://133.160.93.56:20110/aopoauth/login.jsp");
            //设置请求头的信息
            getMethod1.setRequestHeader("charset", DEFAULTCHARSWT);
            if(headers!=null) {
                for(Entry<String, Object> header:headers.entrySet())
                {
                    getMethod1.setRequestHeader(header.getKey(),(String)header.getValue());
                }
            }
            httpClient.executeMethod(getMethod1);
            result.put("responseHeader", nameValuePairToMap(getMethod1.getResponseHeaders()));
            result.put("responseBody", getMethod1.getResponseBodyAsString());
        }
        catch (Exception e) {
            throw new Exception("get请求异常,url:"+url);
        }finally {
            getMethod1.releaseConnection();
        }
        return result;
    }

    /****
     * 
     * Description: 将NameValuePair 转化为map
     * 
     *@param nameValuPair
     *@return Map<String,Object>
     *
     * @see
     */
    public static Map<String, Object> nameValuePairToMap(NameValuePair[] nameValuPair ){
        Map<String, Object> result = new HashMap<String, Object>();

        for(NameValuePair pair:nameValuPair)
        {
            result.put(pair.getName(), pair.getValue());
        }
        return result;
    }
    /****
     * 
     * Description: 将map 转化为NameValuePair
     * 
     *@param nameValuPair
     *@return Map<String,Object>
     *
     * @see
     */
    public static NameValuePair[]   mapToNameValuePair(Map<String, Object> map){
        NameValuePair[] pairs = new NameValuePair[map.size()];
        if(map==null||map.size()==0)
        {
            return pairs;
        }
        int i=0;
        for(Entry<String, Object> entry:map.entrySet())
        {
            NameValuePair pair =new NameValuePair(entry.getKey(), (String)entry.getValue());
            pairs[i] =pair;
            i++;
        }
        return pairs;
    }

}