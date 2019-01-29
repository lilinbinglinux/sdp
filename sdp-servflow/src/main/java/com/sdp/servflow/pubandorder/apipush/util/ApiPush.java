package com.sdp.servflow.pubandorder.apipush.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.sdp.common.CurrentUserUtils;
import com.sdp.servflow.pubandorder.apipush.model.DockingBean;
import com.sdp.servflow.pubandorder.apipush.service.DockingService;
import com.sdp.servflow.pubandorder.orderapistore.model.OrderInterfaceBean;
import com.sdp.servflow.pubandorder.orderapistore.service.OrderInterfaceService;
import com.sdp.servflow.pubandorder.orderapistore.util.GetParam;
import com.sdp.servflow.pubandorder.serve.ShowServer;
import com.sdp.servflow.pubandorder.servicebasic.model.ServiceMainBean;
import com.sdp.servflow.pubandorder.servicebasic.model.ServiceVersionBean;
import com.sdp.servflow.pubandorder.servicebasic.service.ServiceMainService;
import com.sdp.servflow.pubandorder.servicebasic.service.ServiceVersionService;
import com.sdp.servflow.pubandorder.util.HanyuPinyinUtil;
import com.sdp.servflow.pubandorder.util.SslUtils;

/**
 * Created by niu on 2017/9/21.
 */
@Component
@RestController
@RequestMapping("/apiPush")
public class ApiPush {
    
    /**
     * ShowServer
     */
    @Autowired
    private ShowServer show;

    
    /**
     * DockingBean
     */
    @Autowired
    private DockingService dockingService;

    /**
     * OrderInterfaceService
     */
    @Autowired
    private OrderInterfaceService orderInterfaceService;

    /**
     * ServiceMainService
     */
    @Autowired
    private ServiceMainService serviceMainService;

    /**
     * ServiceVersionService
     */
    @Autowired
    private ServiceVersionService serviceVersionService;


    /**
     * 获取本机地址
     */
    @Value("${mine.url}")
    private String mine_url;
    
    /**
     * 获取浏览器地址
     */
    @Value("${directionalityurl}")
    private String url;
    
    /**
     * 获取token的http端口
     */
    @Value("${urlToken.http}")
    private String tokenPort;
    
    @Value("${urlToken.https}")
    private String tokenPorts;
    
    /**
     * 获取添加操作api的http端口
     */
    @Value("${urlApi.http}")
    private String apiPortHttp;
    
    /**
     * 获取添加操作api的https端口
     */
    @Value("${urlApi.https}")
    private String apiPortHttps;

    /**
     * 
     * 1、获取token
     * 2、添加api
     * 3、更新api
     * 
     *@param request
     * @throws IOException
     *
     * @see
     */
    @RequestMapping(value = { "/index" }, method = RequestMethod.GET)
    public String findPublisher(HttpServletRequest request, String orderId){
        String res = "error";
        
        //获取用户名
        String username = CurrentUserUtils.getInstance().getUser().getLoginId();
        String tenantId = CurrentUserUtils.getInstance().getUser().getTenantId();
        String password;
        if("admin".equals(username)){
            password = "admin123";
        }else{
            password = "Bonc123";
        }
        
        //获取session中的loginId，用于获取密钥和私钥
        //String loginIdToKey = (String)request.getSession().getAttribute("loginIdToKey");
        
        /**
         *第一步：获取token 
         */
        String tokensParam = sendPost1("http://"+url+":"+tokenPort+"/token","grant_type=client_credentials&scope=apim:api_create", username, tenantId);
        tokensParam = tokensParam.replace("{","").replace("}", "");
        String[] sourceStrArray = tokensParam.split(",")[3].split(":");
        String access_token = sourceStrArray[1].replace("\"", "");  //获取到的token

        /**
         * 拼接参数
         *
         * 获取appId
         */
        OrderInterfaceBean orderInterfaceBean = orderInterfaceService.selectByOrderId(orderId);
        String appId = orderInterfaceBean.getAppId();
        String serId = orderInterfaceBean.getSerId();
        String serVersion = orderInterfaceBean.getSerVersion();

        //获取xml
        Map<String, String> serviceVersionBeanMap = new HashMap<String, String>();
        serviceVersionBeanMap.put("serId", serId);
        serviceVersionBeanMap.put("serVersion", serVersion);
        List<ServiceVersionBean> serviceVersionBeanList = serviceVersionService.getAllByCondition(serviceVersionBeanMap);
        ServiceVersionBean serviceVersionBean = serviceVersionBeanList.get(0);

        //获取入参及出参
        GetParam getParam = new GetParam();
        Map<String, String> paramMap = new HashMap<>();
        paramMap = getParam.getParamToPage("0",serviceVersionBean.getSerFlow());
        String inputParamStr = paramMap.get("inputParam");

        //获取名称
        ServiceMainBean serviceMainBean = serviceMainService.getByPrimaryKey(serId);
        String name = serviceMainBean.getSerName();
        //将汉字转为拼音
        String nameLo = HanyuPinyinUtil.toLowerCase(name);
        Calendar time = Calendar.getInstance();
        int year = time.get(Calendar.YEAR);
        int month = time.get(Calendar.MONTH)+1;
        int date = time.get(Calendar.DATE);

        String apiDefinition = "{\"paths\":{\"/apis/"+appId+"\":" +
            "{\"post\":{\"x-auth-type\":\"Application & Application User\",\"responses\":{\"200\":{}}," +
            "\"parameters\":[{\"schema\":{\"type\":\"object\"},\"in\":\"body\",\"name\":\"Payload\"," +
            "\"description\":\""+inputParamStr+"\",\"required\":false}],\"x-throttling-tier\":\"无限制\"}}}," +
            "\"swagger\":\"2.0\",\"info\":{\"title\":\""+name+"\",\"version\":\"v1.0\"}}";
        
        System.out.println(apiDefinition);
        
        //获取全部参数 requestParamAll
        String[] transport = {"http"};
        String[] tiers = {"Gold", "Silver","Silver","Bronze"};
        JSONObject requestParamAll = new JSONObject();
        //requestParamAll.put("name", nameLo+"."+year+"."+month+"."+date);
        requestParamAll.put("name", nameLo+"."+year+"."+month+"."+date);
        requestParamAll.put("context", "/apis/"+appId);
        requestParamAll.put("version", "v1.0");
        requestParamAll.put("apiDefinition",Base64Utils.encodeToString(apiDefinition.getBytes()));
        requestParamAll.put("isDefaultVersion", false);
        requestParamAll.put("transport", transport);
        requestParamAll.put("tiers", tiers);
        requestParamAll.put("visibility", "PUBLIC");
        requestParamAll.put("endpointConfig",
        "{\"production_endpoints\":{\"url\":\""+mine_url+"/servflow/apisEmploy\",\"config\":null},\"sandbox_endpoints\":{\"url\":\""+mine_url+"\",\"config\":null},\"implementation_status\":\"managed\",\"endpoint_type\":\"http\"}");


        /**
         * 第二步：添加api
         */
        String apiParamAll;
        try {
            apiParamAll = sendPost2("http://"+url+":"+apiPortHttp+"/api/am/publisher/v0.9/apis", access_token, requestParamAll);
            System.out.println(apiParamAll);
            net.sf.json.JSONObject apiParamObject = net.sf.json.JSONObject.fromObject(apiParamAll);
            Map<String, String> apiParamMap = (Map)apiParamObject;
            String id = apiParamMap.get("id");
            System.out.println("apiParamMap"+apiParamMap);
            System.out.println("apiParamAll"+apiParamAll);
            
            DockingBean dockingBean = new DockingBean();
            dockingBean.setId(apiParamMap.get("id"));
            dockingBean.setName((String)requestParamAll.get("name"));
            dockingBean.setOrderId(orderId);
            dockingBean.setSerId(serId);
            dockingBean.setContext((String)requestParamAll.get("context"));
            dockingBean.setVersion((String)requestParamAll.get("version"));
            dockingBean.setVisibility((String)requestParamAll.get("visibility"));
            dockingBean.setProvider(apiParamMap.get("provider"));
            dockingBean.setParameters(inputParamStr);
            dockingService.insert(dockingBean);
            
            
        /**
         * 第三步：取得具有修改权限的token
         */
        String tokens = sendPost3("http://"+url+":"+tokenPort+"/token",
            "grant_type=client_credentials&scope=apim:api_publish", username, tenantId);
        System.out.println(tokens);
        net.sf.json.JSONObject jb = net.sf.json.JSONObject.fromObject(tokens);
        String token_publish = (String)jb.get("access_token");
        
        
        /**
         * 第四步：发布已经注册好的api（修改api的状态）
         */
        sendPost4("http://"+url+":"+apiPortHttp+"/api/am/publisher/v0.9/apis/change-lifecycle?apiId="+id+"&action=Publish",
            "apiId="+id+"&action=Publish",token_publish);

        res = "success";
        } catch (IOException e) {
            res = "error";
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 更新api
     * @param request
     * @param orderId
     * @return
     */
    @RequestMapping(value = { "/updateApi" }, method = RequestMethod.GET)
    public String updateApi(HttpServletRequest request, String orderId){
        String result = "error";
        /**
         * 第一步：获取token
         */
        //token
        String access_token = null;
        
        //获取用户名
        String username = CurrentUserUtils.getInstance().getUser().getLoginId();
        String tenantId = CurrentUserUtils.getInstance().getUser().getTenantId();
        String password = null;

        //获取登录用户
        String loginIdToKey = (String)request.getSession().getAttribute("loginIdToKey");
        if("admin".equals(username)){
            password = "admin";
        }else{
            password = "Bonc123";
        }
        
        String tokensParam = sendPost1("http://"+url+":"+tokenPort+"/token","grant_type=client_credentials&scope=apim:api_create", username, tenantId);
        if(tokensParam != null || tokensParam.length() > 0){
            tokensParam = tokensParam.replace("{","").replace("}", "");
            String[] sourceStrArray = tokensParam.split(",")[0].split(":");
            access_token = sourceStrArray[1].replace("\"", "");  //获取到的token
        }else{
            result = "error";
            return result;
        }
        
        /**
         * 第二步：根据orderId查找id（本地库）
         */
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        List<DockingBean> list = dockingService.getAllByCondition(map);
        String id = list.get(0).getId();
        
        /**
         * 第三步：获取要修改的参数
         */
        /**
         * 拼接参数
         *
         * 获取appId
         */
        OrderInterfaceBean orderInterfaceBean = orderInterfaceService.selectByOrderId(orderId);
        String appId = orderInterfaceBean.getAppId();
        String serId = orderInterfaceBean.getSerId();
        String serVersion = orderInterfaceBean.getSerVersion();

        //获取xml
        Map<String, String> serviceVersionBeanMap = new HashMap<String, String>();
        serviceVersionBeanMap.put("serId", serId);
        serviceVersionBeanMap.put("serVersion", serVersion);
        List<ServiceVersionBean> serviceVersionBeanList = serviceVersionService.getAllByCondition(serviceVersionBeanMap);
        ServiceVersionBean serviceVersionBean = serviceVersionBeanList.get(0);

        //获取入参及出参
        GetParam getParam = new GetParam();
        Map<String, String> paramMap = new HashMap<>();
        paramMap = getParam.getParamToPage("0",serviceVersionBean.getSerFlow());
        String inputParamStr = paramMap.get("inputParam");

        //获取名称
        ServiceMainBean serviceMainBean = serviceMainService.getByPrimaryKey(serId);
        String name = serviceMainBean.getSerName();
        //将汉字转为拼音
        String nameLo = HanyuPinyinUtil.toLowerCase(name);
        Calendar time = Calendar.getInstance();
        int year = time.get(Calendar.YEAR);
        int month = time.get(Calendar.MONTH)+1;
        int date = time.get(Calendar.DATE);
        
        String apiDefinition = "{\"paths\":{\"/apis/"+appId+"\":" +
            "{\"post\":{\"x-auth-type\":\"Application & Application User\",\"responses\":{\"200\":{}}," +
            "\"parameters\":[{\"schema\":{\"type\":\"object\"},\"in\":\"body\",\"name\":\"Payload\"," +
            "\"description\":\"Request Body\",\"required\":false}],\"x-throttling-tier\":\"无限制\"}}}," +
            "\"swagger\":\"2.0\",\"info\":{\"title\":\""+name+"\",\"version\":\"v1.0\"}}";
        
        
        
        //获取全部参数 requestParamAll
        String[] transport = {"http"};
        String[] tiers = {"Gold", "Silver","Silver","Bronze"};
        JSONObject requestParamAll = new JSONObject();
        requestParamAll.put("name", nameLo+year+"."+month+"."+date);
        requestParamAll.put("context", "/apis/"+appId);
        requestParamAll.put("version", "v1.0");
        requestParamAll.put("apiDefinition",Base64Utils.encodeToString(apiDefinition.getBytes()));
        requestParamAll.put("isDefaultVersion", false);
        requestParamAll.put("transport", transport);
        requestParamAll.put("tiers", tiers);
        requestParamAll.put("visibility", "PUBLIC");
        requestParamAll.put("endpointConfig",
        "{\"production_endpoints\":{\"url\":\""+mine_url+"/servflow/apisEmploy\",\"config\":null},\"sandbox_endpoints\":{\"url\":\""+mine_url+"\",\"config\":null},\"implementation_status\":\"managed\",\"endpoint_type\":\"http\"}");
        
        /**
         * 第四步：根据id修改，并推送至两个库
         */
        String apiParamAll;
        try {
            apiParamAll = sendPut5("http://"+url+":"+apiPortHttp+"/api/am/publisher/v0.9/apis/"+id, access_token, requestParamAll);
            System.out.println("发送前接受参数："+apiDefinition);
            System.out.println("发送后接受参数："+apiParamAll);
            
            net.sf.json.JSONObject apiParamObject = net.sf.json.JSONObject.fromObject(apiParamAll);
            Map<String, String> apiParamMap = (Map)apiParamObject;
            
            DockingBean dockingBean = new DockingBean();
            dockingBean.setId(apiParamMap.get("id"));
            dockingBean.setName((String)requestParamAll.get("name"));
            dockingBean.setContext((String)requestParamAll.get("context"));
            dockingBean.setVersion((String)requestParamAll.get("version"));
            dockingBean.setVisibility((String)requestParamAll.get("visibility"));
            dockingBean.setParameters(inputParamStr);
            dockingService.update(dockingBean);
            
            result = "success";
        }
        catch (IOException e) {
            result = "error";
            e.printStackTrace();
        }
        
        return result;
    }
    
    /**
     * 
     * Description:获取密钥和私钥 
     * 
     *@return String
     *
     * @see
     */
    public String sendGetPer(String loginId, String tenantId){
        String output;
        String result1 = "";
        try {
            StringBuilder result = new StringBuilder();
            String urlGetKey = "http://"+url+":"+apiPortHttp+"/store/site/pages/key.jag??username="+loginId+"&tenantId="+tenantId+"&applicationName=push";
            // 创建HttpClient对象
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet request = new HttpGet(urlGetKey);
            request.addHeader("Content-type", "application/json;charset=utf8");
            HttpResponse response = client.execute(request);
            BufferedReader br = new BufferedReader(
                new InputStreamReader((response.getEntity().getContent())));
            while ((output = br.readLine()) != null) {
                result.append(output);
            }
            result1 = result.toString();
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result1;
    } 
    
    /**
     * 
     * Description:获取token
     * 
     *@param url
     *@param param
     *@return String
     *
     * @see 
     */
    public String sendPost1(String url, String param, String username, String tenantId) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            String keyPre = sendGetPer(username, tenantId);
            net.sf.json.JSONObject keyPreObject = net.sf.json.JSONObject.fromObject(keyPre);
            Map<String, String> keyPreMap = (Map)keyPreObject;
            String consumer_key = keyPreMap.get("ConsumerKey");
            String consumer_secret = keyPreMap.get("ConsumerSecret");
            String key = Base64Utils.encodeToString((consumer_key+":"+consumer_secret).getBytes());
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Authorization", "Basic "+key);
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

    
    /**
     * 
     * Description:添加api
     * 
     *@param url
     *@param header
     *@param j
     *@return
     *@throws IOException String
     *
     * @see
     */
    public static String sendPost2(String url, String header, JSONObject j) throws IOException {
        String output = "";
        String result1 = "";
        try {
            StringBuilder result = new StringBuilder();
            
            // 创建HttpClient对象
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost request = new HttpPost(url);
            request.addHeader("Content-type", "application/json;charset=utf8");
            request.addHeader("Authorization", "Bearer "+header);
            request.setEntity(new StringEntity(j.toString(), Charset.forName("UTF-8")));
            //request.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
            HttpResponse response = client.execute(request);
            BufferedReader br = new BufferedReader(
                new InputStreamReader((response.getEntity().getContent())));
            while ((output = br.readLine()) != null) {
                result.append(output);
            }
            result1 = result.toString();
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result1;
    }
    
    public String sendPost3(String url, String param, String username, String tenantId){
        //http，通过ID改变状态
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            String keyPre = sendGetPer(username, tenantId);
            net.sf.json.JSONObject keyPreObject = net.sf.json.JSONObject.fromObject(keyPre);
            Map<String, String> keyPreMap = (Map)keyPreObject;
            String consumer_key = keyPreMap.get("ConsumerKey");
            String consumer_secret = keyPreMap.get("ConsumerSecret");
            String key = Base64Utils.encodeToString((consumer_key+":"+consumer_secret).getBytes());
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Authorization", "Basic "+key);
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
    
    public static String sendPost4(String url, String param, String token) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            if("https".equalsIgnoreCase(realUrl.getProtocol())){
                SslUtils.ignoreSsl();
             }
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");  
            conn.setRequestProperty("Authorization", "Bearer "+token);
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
            conn.getInputStream();
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
    
    /**
     * 
     * Description:更新api
     * 
     *@param url
     *@param header
     *@param j
     *@return
     *@throws IOException String
     *
     * @see
     */
    public static String sendPut5(String url, String header, JSONObject j) throws IOException {
        String output = "";
        String result1 = "";
        try {
            StringBuilder result = new StringBuilder();
            
            // 创建HttpClient对象
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPut request = new HttpPut(url);
            request.addHeader("Content-type", "application/json;charset=utf8");
            request.addHeader("Authorization", "Bearer "+header);
            request.setEntity(new StringEntity(j.toString(), Charset.forName("UTF-8")));
            HttpResponse response = client.execute(request);
            BufferedReader br = new BufferedReader(
                new InputStreamReader((response.getEntity().getContent())));
            while ((output = br.readLine()) != null) {
                result.append(output);
            }
            result1 = result.toString();
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result1;
    }
    
    //通过用户名和密码生成秘钥
    public static String encodeCredentials(String username, String password) {
        String cred = username + ":" + password;
        String encodedValue = null;
        byte[] encodedBytes = Base64.encodeBase64(cred.getBytes());
        encodedValue = new String(encodedBytes);
        System.out.println("encodedBytes " + new String(encodedBytes));

        byte[] decodedBytes = Base64.decodeBase64(encodedBytes);
        System.out.println("decodedBytes " + new String(decodedBytes));

        return encodedValue;

    }
    
}
