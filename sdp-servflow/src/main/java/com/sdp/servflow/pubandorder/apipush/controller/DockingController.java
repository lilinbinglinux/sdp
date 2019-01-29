package com.sdp.servflow.pubandorder.apipush.controller;

import net.sf.json.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.servflow.pubandorder.apipush.service.DockingService;
import com.sdp.servflow.pubandorder.orderapistore.model.OrderInterfaceBean;
import com.sdp.servflow.pubandorder.orderapistore.service.OrderInterfaceService;
import com.sdp.servflow.pubandorder.security.controller.SecurityController;
import com.sdp.servflow.pubandorder.servicebasic.model.ServiceMainBean;
import com.sdp.servflow.pubandorder.servicebasic.model.ServiceVersionBean;
import com.sdp.servflow.pubandorder.servicebasic.service.ServiceMainService;
import com.sdp.servflow.pubandorder.servicebasic.service.ServiceVersionService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 流程图推送
 *
 * @author 牛浩轩
 * @version 2017年9月26日
 * @see DockingController
 * @since
 */
@Controller
@RequestMapping("/dockingController")
public class DockingController {

    @Autowired
    private DockingService dockingService;

    @Autowired
    private OrderInterfaceService orderInterfaceService;

    @Autowired
    private ServiceMainService serviceMainService;

    @Autowired
    private ServiceVersionService serviceVersionService;

    /**
     * 查看推送开关
     */
    @Value("${apiPush.open}")
    private String open;

    /**
     * 推送数据
     * @param request
     * @param serId
     * @param serVerId
     * @return
     */
    @RequestMapping(value={"/pushESB"},method=RequestMethod.POST)
    @ResponseBody
    public String autoOrderSynchSer(HttpServletRequest request, String serId, String serVerId){
        String result;
        String orderId;

        ServiceMainBean serviceMainBean = serviceMainService.getByPrimaryKey(serId);
        ServiceVersionBean serviceVersionBean = serviceVersionService.getByPrimaryKey(serVerId);

        SecurityController serSecurityController = new SecurityController();
        Map<String, String> securityMap = serSecurityController.getTokenId();

        //判断是否已经被订阅，如果未被订阅，则自动订阅
        OrderInterfaceBean orderInterfaceBean = new OrderInterfaceBean();
        orderInterfaceBean.setSerId(serId);
        orderInterfaceBean.setSerVersion(serviceVersionBean.getSerVersion());
        List<OrderInterfaceBean> orderInterfaceBeanList = orderInterfaceService.getAllByCondition(orderInterfaceBean);
        if (orderInterfaceBeanList == null || orderInterfaceBeanList.isEmpty()){
            //自动订阅
            orderInterfaceBean.setOrderName(serviceMainBean.getSerName());
            orderInterfaceBean.setOrderSerName(serviceMainBean.getSerName());
            orderInterfaceBean.setProtocal(serviceVersionBean.getSerAgreement());
            orderInterfaceBean.setReqformat(serviceVersionBean.getSerRequest());
            orderInterfaceBean.setRespformat(serviceVersionBean.getSerResponse());
            orderInterfaceBean.setLimitIp("0");
            orderInterfaceBean.setAppId(securityMap.get("appId"));
            orderInterfaceBean.setRepFlag("0");
            //此处为自动审批
            orderInterfaceBean.setCheckStatus("1");
            orderId = orderInterfaceService.insert(orderInterfaceBean);
        }else{
            orderId = orderInterfaceBeanList.get(0).getOrderId();
        }

        if(orderId != null && orderId != ""){
            //进入推送
            result = dockingService.execute(request, orderId, serId, serVerId);
        }else{
            result = "error";
        }
        return result;
    }

    /**
     * 根据名称及版本修改状态
     * url暴露：http://127.0.0.1:8080/servflow/dockingController/updateApiState
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value={"/updateApiState"},method=RequestMethod.POST)
    public JSON updateApiState(HttpServletRequest request){
        String serName = request.getParameter("serName");
        String serVersion = request.getParameter("serVersion");
        String username = request.getParameter("username");
        byte[] serNameBytes = serName.getBytes();
        serNameBytes = Base64Utils.decode(serNameBytes);
        String serNameRes = new String(serNameBytes);

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("serName",serNameRes);
        paramMap.put("serVersion", serVersion.substring(1, serVersion.length()));
        paramMap.put("loginId", username);
        paramMap.put("delFlag","0");
        return dockingService.updateApiState(paramMap);
    }
}
