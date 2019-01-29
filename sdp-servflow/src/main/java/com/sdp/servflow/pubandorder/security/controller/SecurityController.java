package com.sdp.servflow.pubandorder.security.controller;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.sdp.servflow.common.IPHelper;
import com.sdp.servflow.pubandorder.order.service.InterfaceOrderBeanService;
import com.sdp.servflow.pubandorder.pub.service.PublisherBeanService;
import com.sdp.servflow.pubandorder.security.service.SecurityCodeService;
import com.sdp.servflow.pubandorder.serve.ServeAuth;
import com.sdp.servflow.pubandorder.util.TokenProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 
 * 安全标识操作请求
 *
 * @author ZY
 * @version 2017年7月21日
 * @see SecurityController
 * @since
 */

@Controller
@RequestMapping("/security")
public class SecurityController {
    
    /**
     * SecurityCodeService
     */
    @Autowired
    private SecurityCodeService securityCodeService;
    
    /**
     * InterfaceOrderService
     */
    @Autowired
    private InterfaceOrderBeanService interfaceOrderService;
    
    @Autowired
    private ServeAuth ser;
    
    @Autowired
    private PublisherBeanService publisherBeanService;
    
    @RequestMapping("/index")
    public String index(){
        return "puborder/security/securityifream";
    }

    @RequestMapping("/getTokenId")
    @ResponseBody
    public Map<String,String> getTokenId(){
        String token_id = TokenProcessor.getInstance().generateTokeCode();
        
        //String appId = interfaceOrderService.getMaxAppId();

        /*if(!StringUtils.isBlank(appId)){
            appId = SeriNumber.producerNum(8,999999999,appId);
        }else{
            appId = SeriNumber.producerNum(8,999999999,"");
        }*/
        String appId = UUID.randomUUID().toString().replace("-","");
        Map<String,String> map = new HashMap<String,String>();
        map.put("token_id", token_id);
        map.put("appId", appId);
        
        return map;
    }
    
    @ResponseBody
    @RequestMapping(value = "/getValidateToken", method = RequestMethod.POST)
    public String getValidateToken(String orderid){
        String token_id = securityCodeService.getValidateToken(orderid); 
        return token_id;
    }


    /**
     * 根据测试数据测试注册API
     * @param request
     * @param serId
     * @param paramStr
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/apiTestOnline")
    public Object apiTestOnline(HttpServletRequest request, String serId, String paramStr){
        /*Response response = new Response();
        
        PublisherBean publisherBean = publisherBeanService.getPubById(id);
        
        //如果传入的测试参数与注册的接口提供的参数格式不相同
        if(!StringTypeUtil.getType(sampledata).equals(
            StringTypeUtil.getType(publisherBean.getSampledata())
            )){
            response = ResponseCollection.getSingleStion().get(7);
            return response;
        }
        
        return ser.apiOnlineTest(publisherBean,sampledata);*/



        String ip = IPHelper.getIpAddress(request);
        JSONObject commonObj = new JSONObject();
        System.out.println(paramStr);

        commonObj.put("appId", serId);
        commonObj.put("ip", ip);
        commonObj.put("tenant_id", "tenant_system");
        return ser.invocation(commonObj, paramStr);
    }

}
