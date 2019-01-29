package com.sdp.servflow.pubandorder.order.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.util.IdUtil;
import com.sdp.frame.util.JsonUtils;
import com.sdp.servflow.pubandorder.order.model.OrderParamBean;
import com.sdp.servflow.pubandorder.order.service.OrderParamBeanService;

import net.sf.json.JSONArray;

/**
 * 
 * 参数实现的具体要求
 *
 * @author 牛浩轩
 * @version 2017年7月3日
 * @see OrderParamBeanController
 * @since
 */
@Controller
@RequestMapping("/orderparambeancontroller")
public class OrderParamBeanController {

    /**
     * OrderParamBeanService
     */
    @Autowired
    private OrderParamBeanService orderParamBeanService;
    
    /**
     * 
     * Description: 添加订阅的参数
     * 
     *@param reqobj
     *@param orderid void
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value="/orderinsert", method = RequestMethod.POST)
    public void insert(String reqobj,String orderid){
        JSONArray json = JSONArray.fromObject(reqobj);
        List<OrderParamBean> list = JSONArray.toList(json, OrderParamBean.class);
        for(OrderParamBean orderParamBean : list){
            orderParamBean.setOrderparamId(IdUtil.createId());
            orderParamBean.setOrderid(orderid);
            if(orderParamBean.getPubparamId() == " " && orderParamBean.getPubparamId() == null){
                orderParamBean.setPubparamId("此处是订阅参数id");
            }
            orderParamBean.setParentId("ROOT");
            orderParamBean.setTenant_id(CurrentUserUtils.getInstance().getUser().getTenantId());
            orderParamBeanService.insert(orderParamBean);
        }
    }
    
    /**
     * 
     * Description: 通过xml的方式来添加参数
     * 
     *@param xmlcontext
     *@param orderid void
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value="/xmlorderparam", method = RequestMethod.POST)
    public void xmlOrderParam(String xmlcontext,String orderid){
        orderParamBeanService.insertByxml(xmlcontext, orderid);
    }
    
    /**
     * 
     * Description: 参数查询
     * 
     *@param start
     *@param length
     *@param jsonStr
     *@param request
     *@return Map
     *
     * @see
     */
    public Map selectParam(String start, String length, String jsonStr){
        Map<String, Object> paramMap = JsonUtils.stringToCollect(jsonStr);
        //paramMap.put("tenant_id", GetTenantIdTool.getCruentTenantId(request));
        
        return orderParamBeanService.selectParam(start, length, paramMap);
    } 
    
}
