/**
 * 
 */
package com.sdp.servflow.webservice;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.sdp.servflow.common.IPHelper;
import com.sdp.servflow.pubandorder.apiemploy.ApisShopEmployController;
import com.sdp.servflow.pubandorder.common.Response;
import com.sdp.servflow.pubandorder.format.imp.XmlFormat;
import com.sdp.servflow.pubandorder.init.ResponseCollection;
import com.sdp.servflow.pubandorder.orderServer.Publish;
import com.sdp.servflow.pubandorder.orderServer.model.PublishBo;
import com.sdp.servflow.webservice.model.TokenResponse;


/**
 * @author renpengyuan
 * @date 2017年9月25日
 */
@WebService()
public class WebServiceImpl implements WebServiceInterface {
    @Autowired
    private ApisShopEmployController restControoler;

    @Resource
    private WebServiceContext context;
    @Autowired
    private Publish publish;

    public static void main(String[] args) {
        String json = null;
        String xml = "<xml><respCode>00000</respCode><respDesc>调用成功</respDesc><result><![CDATA[<times />]]></result></xml>";
        if (xml.startsWith("<xml>")) {
            json = XmlFormat.otherToJson(xml);
        }
        else {
            json = xml;
        }
        Response response = com.alibaba.fastjson.JSONObject.parseObject(json, Response.class);
        System.out.println(response);
    }

    /**
     * SecurityCodeService
     */
    /** webservice apis 接口入口 */
    @Override
    public Response apis(String busiparam) {
        MessageContext mc = context.getMessageContext();
        HttpServletRequest request = (HttpServletRequest)(mc.get(MessageContext.SERVLET_REQUEST));

        Object responseObj = restControoler.call(request, busiparam);
        if (responseObj instanceof Response) {
            return (Response)responseObj;
        }
        else if (responseObj instanceof String) {
            String xml = (String)responseObj;
            String json = null;
            if (xml.startsWith("<xml>")) {
                json = XmlFormat.otherToJson(xml);
            }
            else {
                json = xml;
            }
            Response response = com.alibaba.fastjson.JSONObject.parseObject(json, Response.class);
            return response;
        }
        return null;
    }

    private boolean strIsEmpty(String param) {
        return !(param != null && param.trim().length() >= 1);
    }

    @Override
    public TokenResponse getToken(String key, String appId) {
        TokenResponse response = new TokenResponse();
        if (strIsEmpty(key) || strIsEmpty(appId)) {
            response.setCode("99999");
            response.setMsg("缺少信息,必传参数不能为空");
            return response;
        }
        response.setKey(key);
        response.setAccess_token(
            "fsdf" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        response.setToken_type("code");
        return response;
    }

    @Override
    public Response apisPublish(String busiparam) {
        MessageContext mc = context.getMessageContext();
        HttpServletRequest request = (HttpServletRequest)(mc.get(MessageContext.SERVLET_REQUEST));
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String ip = IPHelper.getIpAddress(request);
        String tenantId = request.getParameter("tenantId");
        String serId = request.getParameter("serId");

        PublishBo publisher = new PublishBo();
        publisher.setIp(ip);
        publisher.setSerId(serId);
        publisher.setMsg(busiparam);
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
}
