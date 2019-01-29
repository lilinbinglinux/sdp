package com.sdp.servflow.pubandorder.flowchart.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.servflow.pubandorder.flowchart.model.PubFlowChartBean;
import com.sdp.servflow.pubandorder.flowchart.service.PubFlowChartBeanService;

/**
 * 
 * 注册的服务进行编排后对外重新发布
 *
 * @author ZY
 * @version 2017年8月7日
 * @see PubFlowChartController
 * @since
 */

@Controller
@RequestMapping("/pubFlowChart")
public class PubFlowChartController {
    
    @Autowired
    private PubFlowChartBeanService pubFlowChartBeanService;
    
    /**
     * 
     * Description: 显示树形首页
     * 
     *@return String
     *
     * @see
     */
    @RequestMapping("/index")
    public String index(String id,Model model) {
        model.addAttribute("flowChartId", id);
        return "puborder/flowchart/proflowchart";
    }
    
    @RequestMapping(value={"/delete"},method=RequestMethod.POST)
    @ResponseBody
    public String delete(HttpServletRequest request,String flowChartId){
        String returnString="error";
        Map<String , String> map=new HashMap<String,String>();
        map.put("flowChartId", flowChartId);
        try {
            pubFlowChartBeanService.deleteByCondition(map);
            returnString="success";
        } catch (Exception e) {
//          log.xssInfo("context", e);
        }
        return returnString;
    }
    
    @RequestMapping(value={"/getByPrimaryKey"},method=RequestMethod.POST)
    @ResponseBody
    public PubFlowChartBean getByPrimaryKey(String flowChartId,String node_id){
        Map<String,String> map = new HashMap<String,String>();
        map.put("node_id", node_id);
        map.put("flowChartId", flowChartId);
        PubFlowChartBean bean = new PubFlowChartBean();
        List<PubFlowChartBean> beans = pubFlowChartBeanService.getAllByCondition(map);
        if(null != beans&&beans.size()>0){
            bean = beans.get(0);
        }
        return bean;
    }
    
}
