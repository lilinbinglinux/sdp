package com.sdp.servflow.pubandorder.flowchart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * 显示流程图iframe
 *
 * @author ZY
 * @version 2017年8月8日
 * @see FlowchartIframeController
 * @since
 */
@Controller
@RequestMapping("/flowChartIframe")
public class FlowchartIframeController {
    
    @RequestMapping("/index")
    public String index(String id, Model model,String flowType ){
        model.addAttribute("id", id);
        model.addAttribute("flowType", flowType);
        return "process/process";
    }
    
    @RequestMapping("/layoutParam")
    public String layoutParam(){ 
        return "puborder/flowchart/flowchartparamiframe";
    }
    
    @RequestMapping("/ligerlayoutParam")
    public String ligerlayoutParam(){ 
        return "puborder/flowchart/ligeruiiframe";
    }

}
