package com.sdp.servflow.pubandorder.checkstatus.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.util.JsonUtils;
import com.sdp.servflow.pubandorder.orderapistore.model.OrderInterfaceBean;
import com.sdp.servflow.pubandorder.orderapistore.service.OrderInterfaceService;

/**
 * 
 * 接口审核
 *
 * @author 牛浩轩
 * @version 2017年8月9日
 * @see CheckStatusController
 * @since
 */
@Controller
@RequestMapping("/checkstatus")
public class CheckStatusController {

    @Autowired
    private OrderInterfaceService orderInterfaceService;

    /**
     * 
     * Description:跳转到主页 
     * 
     *@return String
     *
     * @see
     */
    @RequestMapping("/index")
    public String index(){
        return "puborder/checkstatus/index";
    }
    
    /**
     * 
     * Description:跳转到 pubInterfaceCheck
     * 
     *@return String
     *
     * @see
     */
    @RequestMapping("/pubinterfacecheck")
    public String pubInterfaceCheck(){
        return "puborder/checkstatus/pubinterfacecheck";
    }
    
    /**
     * 
     * Description:跳转到 pubInterfaceCheck
     * 
     *@return String
     *
     * @see
     */
    @RequestMapping("/orderinterfacecheck")
    public String orderInterfaceCheck(){
        return "puborder/checkstatus/orderinterfacecheck";
    }

    /**
     * 查询同步订阅
     * @return
     */
    @RequestMapping("/selectInterfaceOrder")
    @ResponseBody
    public Map selectInterfaceOrder(String start, String length, String jsonStr){
        Map<String, Object> paramMap = JsonUtils.stringToCollect(jsonStr);
        paramMap.put("tenant_id", CurrentUserUtils.getInstance().getUser().getTenantId());
        return orderInterfaceService.selectInterfaceOrder(start, length, paramMap);
    }

    /**
     * 更新审核状态
     * @param orderid
     * @param checkstatus
     * @return
     */
    @RequestMapping("/updatecheckstatus")
    @ResponseBody
    public String updatecheckstatus(String orderid, String checkstatus){
        String status = "";
        OrderInterfaceBean order = new OrderInterfaceBean();
        try {
            order.setOrderId(orderid);
            order.setCheckStatus(checkstatus);
            orderInterfaceService.updateByPrimaryKey(order);
            status = "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * 审核查看接口详细信息
     * @param orderId
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectGetSerNameByOrderId")
    public OrderInterfaceBean selectGetSerNameByOrderId(String orderId){
        return orderInterfaceService.selectGetSerNameByOrderId(orderId);
    }

}
