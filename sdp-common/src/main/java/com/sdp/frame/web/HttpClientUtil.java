package com.sdp.frame.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

public class HttpClientUtil {
    public static String doPost(String urlr){
        String body = null;
        try{  
            // Configure and open a connection to the site you will send the request  
            URL url = new URL(urlr);  
            URLConnection urlConnection = url.openConnection();  
            // 设置doOutput属性为true表示将使用此urlConnection写入数据  
            urlConnection.setDoOutput(true);  
            // 定义待写入数据的内容类型，我们设置为application/x-www-form-urlencoded类型  
            urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");

            InputStream inputStream = urlConnection.getInputStream();  
            String encoding = urlConnection.getContentEncoding();  
            body = IOUtils.toString(inputStream, encoding); 
            
        }catch(IOException e){  
           e.printStackTrace();
        }  
        return body;
    }
    
    public static String doGet(String url,String param) {       
        String result = "";
    try {
        String urlNameString = url + "?" + param;
        URL realUrl = new URL(urlNameString);
        // 打开和URL之间的连接
        URLConnection connection = realUrl.openConnection();
        // 设置通用的请求属性
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        // 建立实际的连接
        connection.connect();
        
        InputStream inputStream = connection.getInputStream(); 
        
        String encoding = connection.getContentEncoding();  
        result = IOUtils.toString(inputStream, encoding); 
       
    } catch (Exception e) {
        System.out.println("发送GET请求出现异常！" + e);
        e.printStackTrace();
    }
    return result;
}
    
    
    
    
    
    
    
    
    
    
    
//   public static String httpPost(String url,String param, boolean noNeedResponse){
//          //post请求返回结果
//          @SuppressWarnings("resource")
//          DefaultHttpClient httpClient = new DefaultHttpClient();
//          HttpPost method = new HttpPost(url);
//          String str = "";
//              try {
//                  if (null != param) {
//                      //解决中文乱码问题
//                      StringEntity entity = new StringEntity(param, "utf-8");
//                      entity.setContentEncoding("UTF-8");
//                      entity.setContentType("application/json");
//                      method.setEntity(entity);
//                  }
//                  HttpResponse result = httpClient.execute(method);
//                  url = URLDecoder.decode(url, "UTF-8");
//                  /**请求发送成功，并得到响应**/
//                  
//                  if (result.getStatusLine().getStatusCode() == 200) {
//                          /**读取服务器返回过来的json字符串数据**/
//                          str = EntityUtils.toString(result.getEntity());
//                          if (noNeedResponse) {
//                              return null;
//                          }
//                  }
//              } catch (UnsupportedCharsetException e) {
//                  e.printStackTrace();
//              } catch (ClientProtocolException e) {
//                  e.printStackTrace();
//              } catch (UnsupportedEncodingException e) {
//                  e.printStackTrace();
//              } catch (ParseException e) {
//                  e.printStackTrace();
//              } catch (IOException e) {
//                  e.printStackTrace();
//              }
//              System.out.println("~~~~~~~~"+str);
//          return str;
//      }
    
    
    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
}
