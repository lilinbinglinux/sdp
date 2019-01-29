package com.sdp.servflow.pubandorder.apiemploy;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sdp.frame.web.HttpClientUtil;
import com.sdp.servflow.common.IPHelper;
import com.sdp.servflow.pubandorder.apiemploy.apiversion.ApiVersion;
import com.sdp.servflow.pubandorder.common.Response;
import com.sdp.servflow.pubandorder.init.ResponseCollection;
import com.sdp.servflow.pubandorder.orderServer.Publish;
import com.sdp.servflow.pubandorder.orderServer.model.PublishBo;
import com.sdp.servflow.pubandorder.protocol.HttpUtil;
import com.sdp.servflow.pubandorder.serve.ServeAuth;
import com.sdp.servflow.serlayout.sso.model.SerspLoginBean;
import com.sdp.servflow.serlayout.sso.service.SerspLoginBeanService;

import net.sf.json.JSONObject;


/**
 * 对外暴露接口的验证和映射
 *
 * @author ZY
 * @version 2017年7月25日
 * @see ApisEmployController
 * @since
 */
@RestController
@RequestMapping("/{version}/apisEmploy")
public class ApisShopEmployController {
	
	@Autowired
	private SerspLoginBeanService serspLoginBeanService;

    /**
     * ServeAuth
     */
    @Autowired
    private ServeAuth ser;

    @Autowired
    private Publish publish;

    /**
     * Description:注册服务封装，服务对外发布
     * 
     * @param request
     * @return Response
     * @throws Exception
     *             Response
     * @see
     */
    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/apis", method = {RequestMethod.POST})
    public Object apishopEmploy(HttpServletRequest request, @RequestBody String paramStr)
        throws Exception {
        return  call(request, paramStr);
        }
    /**
     * Description:注册服务封装，服务对外发布
     * 
     * @param request
     * @return Response
     * @throws Exception
     *             Response
     * @see
     */
    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/apis/get", method = {RequestMethod.GET})
    public Object apishopEmployGet(HttpServletRequest request)
        throws Exception {
       return  call(request, "");
    }

   
    public Object call(HttpServletRequest request,  String paramStr){
    	String url = HttpUtil.getUrl(request);
        // TODO 如果配置了nigx就不能这么处理 了 这个方法获取将是nigix服务器的ip 解决方案：
        // 在nigix的转发规则中将原来的ip放入ip的请求头的一个自定义参数中，获取用户ip从请求头自定义参数中中获取
        String ip = IPHelper.getIpAddress(request);
        String tenantId = request.getParameter("tenantId");
        String appId = request.getParameter("appId");
        JSONObject commonObj = new JSONObject();
        
        if (StringUtils.isBlank(appId) || StringUtils.isBlank(tenantId) ) {
            return ResponseCollection.getSingleStion().get(19);
        }
        Map<String,Object> urlParam = new HashMap<String,Object>();
        for(Map.Entry<String, String[]> map :request.getParameterMap().entrySet()){
            if(map.getValue()!=null){
                urlParam.put(map.getKey(), map.getValue()[0]);
            }
        }
        commonObj.put("url", url);
        commonObj.put("appId", appId);
        commonObj.put("ip", ip);
        commonObj.put("tenant_id", tenantId);
        commonObj.put("urlParam",urlParam);
        commonObj.put("sourceType","0");
        return ser.invocation(commonObj, paramStr);
    
        
    }
    /**
     * Description:发布订阅时候 发布人的数据输入
     * 
     * @param request
     * @return Response
     * @throws Exception
     *             Response
     * @see
     */
    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/apisPublish", method = RequestMethod.POST)
    public Object apisPublish(HttpServletRequest request, @RequestBody String paramStr)
        throws Exception {
    	
    	String uuid = UUID.randomUUID().toString().replace("-", "");
        String ip = IPHelper.getIpAddress(request);
        String tenantId = request.getParameter("tenantId");
        String serId = request.getParameter("serId");

        PublishBo publisher = new PublishBo();
        publisher.setIp(ip);
        publisher.setSerId(serId);
        publisher.setMsg(paramStr);
        publisher.setTenantId(tenantId);
        publisher.setRequestId(uuid);

        publish.release(publisher);
        if (serId != null && serId.length() > 0 && tenantId != null && tenantId.length() > 0) {
        	Response  response =ResponseCollection.getSingleStion().get(1);
        	response.setRequestId(uuid);
            return response;
        }
        else {
        	Response  response =ResponseCollection.getSingleStion().get(19);
        	response.setRequestId(uuid);
            return response;
        }

    }

    
    
    /**
     * 单点组件暴漏接口
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/casPublish", method = RequestMethod.GET)
    public void casapisPublish(HttpServletRequest request,HttpServletResponse response) throws Exception {
    		HashMap<String,Object> commonParam = new HashMap<String,Object>();
    		
    		JSONObject urlParam = new JSONObject();
	        for(Map.Entry<String, String[]> map :request.getParameterMap().entrySet()){
	            if(map.getValue()!=null){
                    urlParam.put(map.getKey(), map.getValue()[0]);
                }
	        }
        System.out.println(urlParam.toString());
        commonParam.put("sploginid", request.getParameter("appId"));
        commonParam.put("ipAddr", IPHelper.getIpAddress(request));
        commonParam.put("tenant_id", request.getParameter("tenantId"));
        commonParam.put("urlParam",urlParam);
        
        SerspLoginBean bean = serspLoginBeanService.getAllByPrimaryKey(request.getParameter("appId"));
        commonParam.put("order_name",bean.getSpname());
        
        serspLoginBeanService.serAnalysis(request,response,commonParam,"");
    		
    }
    
    
    /**
     * 单点组件暴漏接口
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/testlogout", method = RequestMethod.GET)
    public void testlogout(HttpServletRequest request,HttpServletResponse response) throws Exception {
    		System.out.println("----------logout init-----------");
    		HttpClientUtil.doGet("http://127.0.0.1:8080/xIntegration/login/logout", "");
    	
    }
    
    

}
