package com.sdp.servflow.pubandorder.apiemploy;
/*package com.bonc.servflow.pubandorder.apiemploy;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bonc.servflow.common.BaseException;
import com.bonc.servflow.common.CurrentUserUtils;
import com.bonc.servflow.common.IPHelper;
import com.bonc.servflow.pubandorder.apiemploy.adapter.JsonParamAdapter;
import com.bonc.servflow.pubandorder.apiemploy.adapter.XmlParamAdapter;
import com.bonc.servflow.pubandorder.common.Response;
import com.bonc.servflow.pubandorder.security.model.SecurityCodeBean;
import com.bonc.servflow.pubandorder.security.service.SecurityCodeService;
import com.bonc.servflow.pubandorder.serve.ServeAuth;
import com.bonc.servflow.pubandorder.util.IContants;
import com.bonc.servflow.pubandorder.util.StringTypeUtil;
import com.ssoConfig.service.ISecurityApiService;
import com.ssoConfig.util.CurrentThreadContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

*//**
 * 
 * 对外暴露接口的验证和映射
 *
 * @author ZY
 * @version 2017年7月25日
 * @see ApisEmployController
 * @since
 *//*
@RestController
@RequestMapping("/apisEmploy")
public class ApisEmployController {
    //private static final Logger LOG = LoggerFactory.getLogger(ApisEmployController.class);
    @Autowired
    private ISecurityApiService securityApiService;
    
    *//**
     * SecurityCodeService
     *//*
    @Autowired
    private SecurityCodeService securityCodeService;
    
    *//**
     * ServeAuth
     *//*
    @Autowired
    private ServeAuth ser;
    
    *//**
     * 
     * Description:注册服务封装，服务对外发布 
     * 
     *@param request
     *@return Response
     *@throws Exception Response
     *
     * @see
     *//*
    
    @ResponseBody
    @RequestMapping(value = "/apis/{appId}", method = RequestMethod.POST)
    public Response apisEmploy(HttpServletRequest request,@PathVariable String appId,String jsonStr,String flag) throws Exception{
        String ip = IPHelper.getIpAddress(request);
        String res = "";   
        String paramStr = "";
        
        if(null == flag||(!flag.equals("docker")&&!flag.equals("openstack"))){
          //获取访问参数
            BufferedReader br=request.getReader();  
            while ((res = br.readLine()) != null) {
                paramStr = paramStr + res.trim();
            } 
        }else{
            paramStr = jsonStr;
        }
        
        Map<String,Object> parammap = new HashMap<String,Object>(); 
        JSONObject commonObj = null;
        JSONObject busiObj = null;
   //     JSONObject reserveObj = null;
        
        //支持xml格式和json格式传参
        switch (StringTypeUtil.getType(paramStr)){
            case StringTypeUtil.Json:
                parammap = JsonParamAdapter.getParams(paramStr.trim());
                commonObj = (JSONObject)parammap.get(JsonParamAdapter.commonparamname); 
                busiObj = JSONObject.fromObject(parammap.get(JsonParamAdapter.busiparammname));  
               // reserveObj = JSONObject.fromObject(parammap.get(JsonParamAdapter.reserveparam));
                break;
            case StringTypeUtil.Xml:
                parammap = XmlParamAdapter.getParams(paramStr);
                commonObj = (JSONObject)parammap.get(XmlParamAdapter.commonparamname);
                busiObj = (JSONObject)parammap.get(XmlParamAdapter.othoaramname);
                //reserveObj = JSONObject.fromObject(parammap.get(JsonParamAdapter.reserveparam));
                break;
            default :
                return IContants.CODETABLE.get(7);
        }
        
        
        Map<String,String> map = new HashMap<String,String>();
        //map.put("token_id", commonObj.get("token_id").toString());
        //map.put("appId",commonObj.get("appId").toString());
        
        map.put("appId",appId);
        
        if(StringUtils.isNotBlank(appId)){
            map.put("appId",appId);
        }else{
            return IContants.CODETABLE.get(12);
        }
        
        if(StringUtils.isNotBlank(map.get("tenant_id"))){
            return IContants.CODETABLE.get(13);
        }else{
            commonObj.put("tenant_id", map.get("tenant_id"));
        }
        
        if(StringUtils.isNotBlank(map.get("login_id"))){
            return IContants.CODETABLE.get(13);
        }else{
            commonObj.put("login_id", map.get("login_id"));
        }
        
        
        commonObj.put("tenant_id", "tenant_system");
        commonObj.put("login_id", "xadmin");
        
        commonObj.put("ip", ip);
        return ser.invocation(commonObj, busiObj.toString(),null);
        
        if(reserveObj == null){
        }else{
            return ser.invocation(commonObj, busiObj.toString(),null);
        }
        
        
    }
    
    
    
    
    @ResponseBody
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public Object getUser(String phoneNumber) throws Exception{
        JSONObject json = new JSONObject();
        json.put("phoneNumber", phoneNumber);
        json.put("custName", "张三");
        if(phoneNumber.endsWith("4"))
        {
            json.put("serviceType", "4G");
        }else{
            json.put("serviceType", "23G");
        }
        json.put("cityId", "760");
        json.put("payMode", "预付费");
        return json;
    }
    @ResponseBody
    @RequestMapping(value = "/preCommit", method = RequestMethod.POST)
    public Object preCommit(@RequestBody String req ) throws Exception{
        JSONObject json = new JSONObject();
        json.put("acctFee","78.89" );
        json.put("orderId", "fsdf"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        return json;
    }
    @ResponseBody
    @RequestMapping(value = "/afterCommit", method = RequestMethod.POST)
    public Object afterCommit(@RequestBody String req ) throws Exception{
        JSONObject json = new JSONObject();
        json.put("code","00000" );
        json.put("result", "4G承诺低消办理成功");
        return json;
    }
    
    @ResponseBody
    @RequestMapping(value = "/order23G", method = RequestMethod.POST)
    public Object order23G(@RequestBody String req ) throws Exception{
        JSONObject json = new JSONObject();
        json.put("code","00000" );
        json.put("result", "23G承诺低消办理成功");
        return json;
    }
    
    
    
    
    
    
    
    @ResponseBody
    @RequestMapping(value = "/getToken", method = RequestMethod.POST)
    public Object getToken(@RequestBody String req ) throws Exception{
        com.alibaba.fastjson.JSONObject reqJSon = com.alibaba.fastjson.JSONObject.parseObject(req);
        JSONObject json = new JSONObject();
        
        if(reqJSon.get("key") ==null
            ||reqJSon.get("appId")==null
            )
        {
            json.put("code", "99999");
            json.put("msg","缺少信息");
        }
        
        json.put("key",reqJSon.get("key") );
        json.put("access_token", "fsdf"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        json.put("token_type", "code");
        return json;
    }
    
    @ResponseBody
    @RequestMapping(value = "/getCasUser", method = RequestMethod.GET)
    public String getCasUser(){
        System.out.println("loginid:  "+CurrentUserUtils.getInstance().getUser().getLoginId());
        return "init success----------------";
    }
}
*/