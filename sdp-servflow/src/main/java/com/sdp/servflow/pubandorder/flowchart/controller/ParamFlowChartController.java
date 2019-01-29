package com.sdp.servflow.pubandorder.flowchart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.servflow.pubandorder.flowchart.model.ParamFlowChartBean;
import com.sdp.servflow.pubandorder.flowchart.service.ParamFlowChartBeanService;

/**
 * 
 * 保存服务之间的映射关系
 *
 * @author ZY
 * @version 2017年8月21日
 * @see ParamFlowChartController
 * @since
 */
@Controller
@RequestMapping("/paramFlowChart")
public class ParamFlowChartController {
    
    @Autowired
    private ParamFlowChartBeanService paramFlowChartBeanService;
    
 /*   @RequestMapping(value={"/insertParamRelation"},method=RequestMethod.POST)
    @ResponseBody
    public void insertParamRelation(String pubrobj, String regexobj){
        paramFlowChartBeanService.insertObj(pubrobj, regexobj);
    }*/
    
/*    @RequestMapping(value={"/updateParamRelation"},method=RequestMethod.POST)
    @ResponseBody
    public void updateParamRelation(String pubrobj){
        paramFlowChartBeanService.updateParamRelation(pubrobj);
    }*/
    
    @RequestMapping(value={"/paramIsCount"},method=RequestMethod.POST)
    @ResponseBody
    public String paramIsCount(String flowChartId){
        return paramFlowChartBeanService.paramIsCount(flowChartId);
    } 
    
    
    @RequestMapping(value={"/findLayoutParam"},method=RequestMethod.POST)
    @ResponseBody
    public List<ParamFlowChartBean> findLayoutParam(String pubid,String flowChartId,String node_id){
    	//List<ParamFlowChartBean> paramList=paramFlowChartBeanService.findCondition(pubid);
    	//model.addAttribute("paramList", paramList);
        return paramFlowChartBeanService.findLayoutParam(pubid , flowChartId,node_id);
    } 
    
}
