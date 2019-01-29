package com.sdp.servflow.pubandorder.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 
 * 实现页面ifream内容嵌套的请求
 *
 * @author ZY
 * @version 2017年7月6日
 * @see OrderIfreamController
 * @since
 */
@Controller
@RequestMapping("/orderIfream")
public class OrderIfreamController {
    
    /**
     * 
     * Description:右侧面板项目展示——项目ifream 
     * 
     *@return String
     *
     * @see
     */
    @RequestMapping(value = "/orderIframe")
    public String proIfream(String id ,Model model){
        model.addAttribute("id",id);
        return "puborder/order/order-interfaceapply";
    }
    
    /**
     * 
     * Description:右侧面板展示项目列表ifream 
     * 
     *@return String
     *
     * @see
     */
    @RequestMapping(value="/orderproject")
    public String orderProject(){
        return "puborder/order/order-project";
    }
    
    /**
     * 
     * Description:右侧面板项目展示——模块ifream 
     * 
     *@return String
     *
     * @see
     */
    @RequestMapping(value = "/publishifream")
    public String publishIfream(){
        return "puborder/order/order-publishmanage";
    }
    
    /**
     * 
     * Description:右侧面板项目展示——接口参数ifream 
     * 
     *@return String
     *
     * @see
     */
    @RequestMapping(value = "/orderparamifream")
    public String orderParamIfream(){
        return "puborder/order/order-reqmanage";
    }
}
