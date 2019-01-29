package com.sdp.servflow.pubandorder.order.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.util.JsonUtils;
import com.sdp.servflow.pubandorder.order.model.InterfaceOrderBean;
import com.sdp.servflow.pubandorder.order.service.InterfaceOrderBeanService;
import com.sdp.servflow.pubandorder.security.model.SecurityCodeBean;
import com.sdp.servflow.pubandorder.security.service.SecurityCodeService;

/**
 * 
 * 接口订阅模块的Controller
 *
 * @author 牛浩轩
 * @version 2017年6月12日
 * @see InterOrderBeanController
 * @since
 */
@Controller
@RequestMapping("/interfaceOrder")
public class InterOrderBeanController {
    
    /**
     * InterfaceOrderService
     */
    @Autowired
    private InterfaceOrderBeanService interfaceOrderService;
    
    /**
     * SecurityCodeService
     */
    @Autowired
    private SecurityCodeService securityCodeService;
    
    /**
     * 
     * Description:返回订阅接口列表
     * 
     *@return String
     *
     * @see
     */
    @RequestMapping("/index")
    private String index(){
        return "puborder/order/order-promanage";
    }
    
    /**
     * 
     * Description: 返回主"我的订阅"
     * 
     *@return String
     *
     * @see
     */
    @RequestMapping("/indexMineOrder")
    private String indexMineOrder(){
        return "puborder/order/order-mine";
    }
    
    /**
     * 
     * Description: “我的订阅”修改参数
     * 
     *@return String
     *
     * @see
     */
    @RequestMapping("/indexMineOrderParam")
    private String indexMineOrderParam(){
        return "puborder/order/order-mineparam";
    }
    
    /**
     * 
     * Description: "我的订阅"模块加载
     * 
     *@param start
     *@param length
     *@param jsonStr
     *@param request
     *@return Map
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value="/selectMine",method=RequestMethod.POST)
    public Map selectMine(String start, String length, String jsonStr){
        Map<String, Object> paramMap = JsonUtils.stringToCollect(jsonStr);
        paramMap.put("tenant_id", CurrentUserUtils.getInstance().getUser().getTenantId());
        return interfaceOrderService.selectMine(start, length, paramMap);
    }
    
    /**
     * 
     * Description:根据orderid查询单个pubapiId 
     * 
     *@param orderid
     *@return String
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value="/selectPubApiId",method=RequestMethod.POST)
    public String selectPubApiId(String orderid){
        return interfaceOrderService.selectPubApiId(orderid);
    }
    
    /**
     * 
     * Description: 查询一条信息
     * 
     *@param orderId
     *@return InterfaceOrderBean
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value="/selectMineById",method=RequestMethod.POST)
    public InterfaceOrderBean selectMineById(String orderId){
        return interfaceOrderService.selectByPrimaryKey(orderId);
    }
    
    /**
     * 
     * Description: 根据pubid查询
     * 
     *@param orderId
     *@return InterfaceOrderBean
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value="/selectByPubid",method=RequestMethod.POST)
    public InterfaceOrderBean selectByPubid(String pubid){
        InterfaceOrderBean bean = new InterfaceOrderBean();
        Map<String,String> map = new HashMap<String,String>();
        map.put("tenant_id", CurrentUserUtils.getInstance().getUser().getTenantId());
        map.put("pubapiId", pubid);
        List<InterfaceOrderBean> list = interfaceOrderService.getAllByCondition(map);
        if(null != list&&list.size()>0){
            bean = list.get(0);
        }
        return bean;
    }
    
    /**
     * 
     * Description:新增接口订阅 
     * 
     *@param interfaceOrder
     *@param request void
     *@return orderid
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value="/insertorderinterface", method=RequestMethod.POST)
    public String insertOrderInterface(InterfaceOrderBean interfaceOrder,String token_id){
        
        //添加token安全信息
        SecurityCodeBean securityCodeBean = new SecurityCodeBean();
        securityCodeBean.setToken_id(token_id);
        securityCodeBean.setAppId(interfaceOrder.getAppId());
        securityCodeService.insert(securityCodeBean);
        
        return interfaceOrderService.insert(interfaceOrder);
    }
    
    /**
     * 
     * Description: 订阅接口修改
     * 
     *@param interfaceOrderBean void
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value="/updateOrderInterface", method=RequestMethod.POST)
    public void updateOrderInterface(InterfaceOrderBean interfaceOrderBean){
        interfaceOrderService.updateOrderInter(interfaceOrderBean);
    }
    
    /**
     * 
     * Description: 取消接口订阅
     * 
     *@param orderId void
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value="/deleteOrderInterface",method=RequestMethod.POST)
    public void deleteOrderInterface(String orderId){
        interfaceOrderService.deleteOrderInter(orderId);
    }
    
    @ResponseBody
    @RequestMapping(value="/isValidation",method=RequestMethod.POST)
    public String isValidation(InterfaceOrderBean interfaceOrderBean){
        boolean flag = interfaceOrderService.isValidation(interfaceOrderBean);
        if(flag){
            return "isValidate";
        }
        else{
            return "inValidate";
        }
    }
    
    /**
     * 
     * Description: 申请订阅接口
     * 
     *@param interfaceOrder
     *@return String
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value="/insertapplyorderinterface", method=RequestMethod.POST)
    public String insertApplyOrderInterface(InterfaceOrderBean interfaceOrder){
        return interfaceOrderService.insert(interfaceOrder);
    }
    
}
