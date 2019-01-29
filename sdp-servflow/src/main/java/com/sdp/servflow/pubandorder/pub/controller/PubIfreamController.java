package com.sdp.servflow.pubandorder.pub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * 实现页面ifream内容嵌套的请求
 *
 * @author ZY
 * @version 2017年7月6日
 * @see IfreamController
 * @since
 */
@Controller
@RequestMapping("/pubIfream")
public class PubIfreamController {
    
    /**
     * 
     * Description:右侧面板项目展示ifream 
     * 
     *@return String
     *
     * @see
     */
    @RequestMapping(value = "/proIframe")
    public String proifream(){
        return "puborder/proiframe";
    }
    
    /**
     * 
     * Description:右侧面板模块展示ifream 
     * 
     *@return String
     *
     * @see
     */
    @RequestMapping(value = "/promodelIframe")
    public String promodeliframe(){
        return "puborder/promodeliframe";
    }
    
    /**
     * 
     * Description:右侧面板设置参数ifream 
     * 
     *@return String
     *
     * @see
     */
    @RequestMapping(value = "/paramIframe")
    public String paramifream(){
        return "puborder/paramiframe";
    }


    @RequestMapping(value = "/apiparam")
    public String apiparam(){
        return "puborder/apiparam";
    }
    
}
