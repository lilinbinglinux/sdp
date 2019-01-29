package com.sdp.servflow.pubandorder.apipush.util;
//package com.bonc.servflow.pubandorder.apipush.util;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.bonc.servflow.common.CurrentUserUtils;
//import com.bonc.servflow.pubandorder.orderapistore.model.OrderInterfaceBean;
//import com.bonc.servflow.pubandorder.orderapistore.service.OrderInterfaceService;
//import com.bonc.servflow.pubandorder.orderapistore.util.GetParam;
//import com.bonc.servflow.pubandorder.servicebasic.model.ServiceMainBean;
//import com.bonc.servflow.pubandorder.servicebasic.model.ServiceVersionBean;
//import com.bonc.servflow.pubandorder.servicebasic.service.ServiceMainService;
//import com.bonc.servflow.pubandorder.servicebasic.service.ServiceVersionService;
//import com.bonc.servflow.pubandorder.util.HanyuPinyinUtil;
//import net.sf.json.JSONArray;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicNameValuePair;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.*;
//
///**
// * Description:
// * ApiPush与ApiPush2区别：
// *      1、取消token的限制
// *      2、新增分类的概念
// *
// * 推送发布步骤
// *      1、获取分类信息接口
// *      2、当没有所需要接口，创建分类接口
// *      3、创建并发布api接口
// *
// * @author Niu Haoxuan
// * @date Created on 2018/2/1.
// */
//@Component
//@RestController
//@RequestMapping("/apiPush2")
//public class ApiPush2 {
//
//    /**
//     * OrderInterfaceService
//     */
//    @Autowired
//    private OrderInterfaceService orderInterfaceService;
//
//    /**
//     * ServiceMainService
//     */
//    @Autowired
//    private ServiceMainService serviceMainService;
//
//    /**
//     * ServiceVersionService
//     */
//    @Autowired
//    private ServiceVersionService serviceVersionService;
//
//
//    /**
//     * 获取本机地址
//     */
//    @Value("${mine.url}")
//    private String BASE_MINE_URL;
//
//    /**
//     * 获取浏览器地址
//     */
//    @Value("${directionalityurl}")
//    private String BASE_DIRECTIONALITY_URL;
//    
//    /**
//     * 发布服务接口
//     */
//    @Value("${publishapi.url}")
//    private String BASE_PUBLISHAPI_URL;
//
//    /**
//     * 获取添加操作api的http端口
//     */
//    @Value("${urlApi.http}")
//    private String apiPortHttp;
//
//
//    /**
//     * 1、查询分类，看 “编排服务” 分类是否存在
//     * 2、分类不存在，则创建该分类
//     * 3、拼装参数
//     * 4、发送请求
//     *
//     * @param request
//     * @param orderId
//     * @return
//     */
//    public String findPublisher(HttpServletRequest request, String orderId, String serId, String serVerId){
//        String res = PushStaticParameterFlag.PUSH_SUCCESS;
//
//        //获取用户名
//        String username = CurrentUserUtils.getInstance().getUser().getLoginId();
//        String tenantId = CurrentUserUtils.getInstance().getUser().getTenantId();
//
//        //获取xml
//        Map<String, String> serviceVersionBeanMap = new HashMap<String, String>();
//        serviceVersionBeanMap.put("serVerId", serVerId);
//        List<ServiceVersionBean> serviceVersionBeanList = serviceVersionService.getAllByCondition(serviceVersionBeanMap);
//        ServiceVersionBean serviceVersionBean = serviceVersionBeanList.get(0);
//
//        //获取入参及出参
//        GetParam getParam = new GetParam();
//        Map<String, String> paramMap = new HashMap<>();
//        paramMap = getParam.getParamToPage("0",serviceVersionBean.getSerFlow());
//        String inputParamStr = paramMap.get("inputParam");
//
//        //获取appId
//        OrderInterfaceBean orderInterfaceBean = new OrderInterfaceBean();
//        orderInterfaceBean.setOrderId(orderId);
//        orderInterfaceBean.setSerVersion(serviceVersionBean.getSerVersion());
//        List<OrderInterfaceBean> orderInterfaceBeanList = orderInterfaceService.getAllByCondition(orderInterfaceBean);
//        String appId = orderInterfaceBeanList.get(0).getAppId();
//        String serVersion = orderInterfaceBeanList.get(0).getSerVersion();
//
//        //获取名称
//        ServiceMainBean serviceMainBean = serviceMainService.getByPrimaryKey(serId);
//        String name = serviceMainBean.getSerName();
//        String serResume = serviceMainBean.getSerResume();
//        //将汉字转为拼音
//        String nameLo = HanyuPinyinUtil.toLowerCase(name);
//        Calendar time = Calendar.getInstance();
//        int year = time.get(Calendar.YEAR);
//        int month = time.get(Calendar.MONTH)+1;
//        int date = time.get(Calendar.DATE);
//
//        System.out.println(inputParamStr);
//
//
//        String apiDefinition = "{\"paths\":{\"/*\":" +
//                "{\"post\":{\"x-auth-type\":\"Application %26 Application User\",\"responses\":{\"200\":{}}," +
//                "\"parameters\":[{\"schema\":{\"type\":\"object\"},\"in\":\"body\",\"name\":\"Payload\"," +
//                "\"description\":"+JSON.toJSONString(inputParamStr)+",\"required\":false}],\"x-throttling-tier\":\"无限制\"}}}," +
//                "\"swagger\":\"2.0\",\"info\":{\"title\":\""+name+"\",\"version\":\""+serVersion+"\"}}";
//
//
//        System.out.println(apiDefinition);
//        
//        int i1=BASE_MINE_URL.indexOf('/');
//		//第一次出现','的索引
//		int i2=BASE_MINE_URL.indexOf('/',i1+1);
//		//第二次出现
//		int i3=BASE_MINE_URL.indexOf('/',i2+1);
//		//第三次出现
//		String MINEURL=BASE_MINE_URL.substring(0,i3);
//        
//        String endpoint_config = "{\"production_endpoints\":{\"url\":\""+BASE_MINE_URL+"?tenantId="+tenantId+"&appId="+appId+"\",\"config\":null},\"sandbox_endpoints\":{\"url\":\""+MINEURL+"\",\"config\":null},\"implementation_status\":\"managed\",\"endpoint_type\":\"http\"}";
//
//        /**
//         * 第一步：查询全部分类
//         */
//        String allPublisherType = sendPost1(username, tenantId);
//        JSONObject allPublisherTypeObject =JSONObject.parseObject(allPublisherType);
//        Map<String, String> allPublisherTypeMap = (Map)allPublisherTypeObject;
//        JSONArray jsonArray = JSONArray.fromObject(allPublisherTypeMap.get("categorys"));
//        String typeNameAvailable = "0";
//        Integer typeIdAvailable = null;
//        for (Iterator<Map<String, Object>> iterator = jsonArray.iterator(); iterator.hasNext();){
//            Map<String, Object> publisherTypeMap = iterator.next();
//            //判断所需分类是否存在
//            if (PushStaticParameterFlag.PUBLISHER_TYPENAME.equals(publisherTypeMap.get("categoryName"))){
//                typeNameAvailable = "1";
//                typeIdAvailable = (Integer) publisherTypeMap.get("Id");
//                break;
//            }
//        }
//
//
//        /**
//         * 第二步：当分类不存在时，添加需要的分类
//         */
//        if ("0".equals(typeNameAvailable)){
//            String addCategoryResult = sendPost2(username, tenantId);
//            Map addCategoryResultMap = JSON.parseObject(addCategoryResult);
//            Map addCategoryResultDataMap = JSON.parseObject(String.valueOf(addCategoryResultMap.get("data")));
//            typeIdAvailable = (Integer) addCategoryResultDataMap.get("Id");
//        }
//
//
//        /**
//         * 第三步：将参数拼接为 requestParamAll
//         */
//        JSONObject requestParamAll = new JSONObject();
//        //requestParamAll.put("name", nameLo+"."+year+"."+month+"."+date);
//        requestParamAll.put("name", name);
//        requestParamAll.put("version", serVersion);
//        requestParamAll.put("visibility", "PUBLIC");
//        requestParamAll.put("category", typeIdAvailable);
//        if(serResume != null && "".equals(serResume)){
//            requestParamAll.put("description", serResume);
//        }else{
//            requestParamAll.put("description", "");
//        }
//        requestParamAll.put("implementation_methods","endpoint");
//        requestParamAll.put("tiersCollection","Unlimited,Gold,Silver,Bronze");
//        requestParamAll.put("transport_http","http");
//        //requestParamAll.put("swagger", Base64Utils.encodeToString(apiDefinition.getBytes()));
//        requestParamAll.put("swagger", apiDefinition);
//        requestParamAll.put("context", appId);
//        requestParamAll.put("defaultVersion", false);
//        requestParamAll.put("endpoint_config", endpoint_config);
//        //requestParamAll.put("endpoint_config",
//        //        "{\"production_endpoints\":{\"url\":\""+mine_url+"/servflow/apisEmploy\",\"config\":null},\"sandbox_endpoints\":{\"url\":\""+mine_url+"\",\"config\":null},\"implementation_status\":\"managed\",\"endpoint_type\":\"http\"}");
//        requestParamAll.put("userName", username);
//        requestParamAll.put("tenantId", tenantId);
//
//
//        /**
//         * 第四步：创建并发布服务接口
//         */
//        String apiParamAll;
//        apiParamAll = sendPost3(requestParamAll);
//        System.out.println(apiParamAll);
//        Map apiParamAllMap = JSON.parseObject(apiParamAll);
//        
//        if(null == apiParamAllMap) {
//        		return PushStaticParameterFlag.PUSH_FAIL;
//        }
//
//        //当推送成功，将数据库中的状态改为：已推送（"1"）
//        if ("false".equals(apiParamAllMap.get("error").toString())){
//            ServiceVersionBean serviceVersionBean2 = new ServiceVersionBean();
//            serviceVersionBean2.setSerVerId(serVerId);
//            serviceVersionBean2.setPushState("1");
//            serviceVersionService.update(serviceVersionBean2);
//
//            res = PushStaticParameterFlag.PUSH_SUCCESS;
//        }
//
//        return res;
//    }
//
//
//    /**
//     * 获取全部分类信息
//     * @param username
//     * @param tenantId
//     * @return
//     */
//    public String sendPost1(String username, String tenantId){
//        String output;
//        String result1 = null;
//        //String url = "http://api-manager:9763/api-manager/publisher/site/blocks/api-category/category-api/ajax/category-api.jag" ;
//        //String url = "http:///publisher:9763/publisher/site/blocks/api-category/category-api/ajax/category-api.jag" ;
//        String url = BASE_DIRECTIONALITY_URL + "?action=getAllCategorys&userName="+username+"&tenantId="+tenantId;
//
//        StringBuilder result = new StringBuilder();
//        try {
//            CloseableHttpClient client = HttpClients.createDefault();
//            HttpPost requesHttpPost = new HttpPost(url);
//            requesHttpPost.addHeader("Content-type", "application/json;charset=utf8");
//            HttpResponse response = client.execute(requesHttpPost);
//            BufferedReader br =  new BufferedReader(
//                    new InputStreamReader(response.getEntity().getContent()));
//            while ((output = br.readLine()) != null){
//                result.append(output);
//            }
//            result1 = result.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result1;
//    }
//
//
//    /**
//     * 新增分类信息
//     * @param username
//     * @param tenantId
//     * @return
//     */
//    public String sendPost2(String username, String tenantId){
//        String output;
//        String result1 = null;
//        String url = BASE_DIRECTIONALITY_URL + "?" +
//                "action=addCategory" +
//                "&userName="+ username +
//                "&tenantId="+ tenantId +
//                "&categoryName="+ PushStaticParameterFlag.PUBLISHER_TYPENAME +
//                "&pId=0" +
//                "&sort=" + PushStaticParameterFlag.PUBLISHER_SORT +
//                "&description=" + PushStaticParameterFlag.PUBLISHER_DESCRIPTION;
//        System.out.println("select all type information:" + url);
//
//        StringBuilder result = new StringBuilder();
//        try {
//            CloseableHttpClient client = HttpClients.createDefault();
//            HttpPost requesHttpPost = new HttpPost(url);
//            requesHttpPost.addHeader("Content-type", "application/json;charset=utf8");
//            HttpResponse response = client.execute(requesHttpPost);
//            BufferedReader br =  new BufferedReader(
//                    new InputStreamReader(response.getEntity().getContent()));
//            while ((output = br.readLine()) != null){
//                result.append(output);
//            }
//            result1 = result.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result1;
//    }
//
//
//    /**
//     * 发送post请求
//     * 推送服务，创建并发布服务接口
//     *
//     * @return
//     */
//    public String sendPost3(JSONObject requestParamAll){
//        String output;
//        String result1 = "";
//        /*String url = "http://"+directionalityUrl+":"+apiPortHttp+"/publisher/site/pages/addAndPublishApi.jag" +
//                "?userName=" + requestParamAll.get("userName") +
//                "&tenantId=" + requestParamAll.get("tenantId") +
//                "&name=" + requestParamAll.get("name") +
//                "&context=" + requestParamAll.get("context") +
//                "&version=" + requestParamAll.get("version") +
//                "&visibility=" + requestParamAll.get("visibility") +
//                "&category=" + requestParamAll.get("category") +
//                "&description=" + requestParamAll.get("description") +
//                "&implementation_methods=" + requestParamAll.get("implementation_methods") +
//                "&tiersCollection=" + requestParamAll.get("tiersCollection") +
//                "&transport_http=" + requestParamAll.get("transport_http") +
//                "&defaultVersion=" + requestParamAll.get("defaultVersion") +
//                "&swagger=" + requestParamAll.get("swagger") +
//                "&endpoint_config=" + requestParamAll.get("endpoint_config");   */
//        String url = BASE_PUBLISHAPI_URL + "?category=" + requestParamAll.get("category") + "&defaultVersion=" + requestParamAll.get("defaultVersion");
//        try {
//            //url = URLEncoder.encode(url,"utf-8");
//            StringBuilder result = new StringBuilder();
//            // 创建HttpClient对象
//            CloseableHttpClient client = HttpClients.createDefault();
//            HttpPost request = new HttpPost(url);
//            request.addHeader("Content-type", "application/x-www-form-urlencoded;charset=utf8");
//            //request.setEntity(new StringEntity(requestParamAll.toString(), Charset.forName("UTF-8")));
//
//            List params = new ArrayList();
//            params.add(new BasicNameValuePair("name", (String) requestParamAll.get("name")));
//            params.add(new BasicNameValuePair("version", (String) requestParamAll.get("version")));
//            params.add(new BasicNameValuePair("visibility", (String) requestParamAll.get("visibility")));
//            params.add(new BasicNameValuePair("description", (String) requestParamAll.get("description")));
//            params.add(new BasicNameValuePair("implementation_methods", (String) requestParamAll.get("implementation_methods")));
//            params.add(new BasicNameValuePair("tiersCollection", (String) requestParamAll.get("tiersCollection")));
//            params.add(new BasicNameValuePair("transport_http", (String) requestParamAll.get("transport_http")));
//            params.add(new BasicNameValuePair("swagger", (String) requestParamAll.get("swagger")));
//            params.add(new BasicNameValuePair("context", (String) requestParamAll.get("context")));
//            params.add(new BasicNameValuePair("endpoint_config", (String) requestParamAll.get("endpoint_config")));
//            params.add(new BasicNameValuePair("userName", (String) requestParamAll.get("userName")));
//            params.add(new BasicNameValuePair("tenantId", (String) requestParamAll.get("tenantId")));
//            request.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
//
//            HttpResponse response = client.execute(request);
//            BufferedReader br = new BufferedReader(
//                    new InputStreamReader(response.getEntity().getContent()));
//            System.out.println(br.toString());
//            while ((output = br.readLine()) != null) {
//            	System.out.println(output);
//                result.append(output);
//            }
//            result1 = result.toString();
//        }
//        catch (ClientProtocolException e) {
//            e.printStackTrace();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result1;
//    }
//    
//
//}
