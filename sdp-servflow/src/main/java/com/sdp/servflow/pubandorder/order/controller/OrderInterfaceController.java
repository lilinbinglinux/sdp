package com.sdp.servflow.pubandorder.order.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.util.JsonUtils;
import com.sdp.frame.util.StringUtil;
import com.sdp.servflow.pubandorder.orderapistore.model.OrderInterfaceBean;
import com.sdp.servflow.pubandorder.orderapistore.service.AsynchSerNodeService;
import com.sdp.servflow.pubandorder.orderapistore.service.OrderInterfaceService;
import com.sdp.servflow.pubandorder.orderapistore.util.GetParam;
import com.sdp.servflow.pubandorder.serapplication.model.SerApplicationBean;
import com.sdp.servflow.pubandorder.serapplication.service.SerApplicationService;
import com.sdp.servflow.pubandorder.servicebasic.model.ServiceVersionBean;
import com.sdp.servflow.pubandorder.servicebasic.service.ServiceVersionService;
import com.sdp.servflow.sysConfig.model.SysCommonCfg;
import com.sdp.servflow.sysConfig.service.SysCommonCfgService;

@Controller
@RequestMapping("/orderInterface")
public class OrderInterfaceController {

    @Autowired
    private OrderInterfaceService orderInterfaceService;

    @Autowired
    private ServiceVersionService serviceVersionService;

    @Autowired
    private SysCommonCfgService sysCommonCfgService;

    @Autowired
    private AsynchSerNodeService asynchSerNodeService;

    /**
     * SerApplicationService
     */
    @Autowired
    private SerApplicationService serApplicationService;

    @Value("${servflow.url}")
    private String apiurl;

    /**
     *
     * Description: 返回主"我的订阅"
     *
     *@return String
     *
     * @see
     */
    @RequestMapping("/indexMineOrder")
    private String indexMineOrder(Model model){
        String user = CurrentUserUtils.getInstance().getUser().getLoginId();
        Map<String, String> map = new LinkedHashMap<>();
        map.put("creatUser", user);
        List<SerApplicationBean> applicationList = serApplicationService.getAllByCondition(map);
        map.clear();
        for (SerApplicationBean serApplicationBean: applicationList) {
            map.put(serApplicationBean.getApplicationId(),serApplicationBean.getApplicationName());
        }
        model.addAttribute("applicationList", map);
        return "puborder/order/order-mine";
    }

    /**
     *
     * Description: "我的订阅"模块加载
     *
     *@param start
     *@param length
     *@param jsonStr
     *@return Map
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value="/selectMine",method= RequestMethod.POST)
    public Map selectMine(String start, String length, String jsonStr){
        Map<String, Object> paramMap = JsonUtils.stringToCollect(jsonStr);
        paramMap.put("tenant_id", CurrentUserUtils.getInstance().getUser().getTenantId());
        paramMap.put("login_id",CurrentUserUtils.getInstance().getUser().getLoginId());
        return orderInterfaceService.selectMine(start, length, paramMap);
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
    public String deleteOrderInterface(String orderId){
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("orderId",orderId);
        paramMap.put("tenantId",CurrentUserUtils.getInstance().getUser().getTenantId());
        String returnString = "";
        try {
            orderInterfaceService.deleteOrderInter(paramMap);
            returnString = "success";
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnString;
    }

    /**
     *
     * Description: “我的订阅”入参提示
     *
     *@return String
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value = "/indexMineOrderParam", method = RequestMethod.POST)
    public String indexMineOrderParam(String id, String serId, String serVersion, Integer num){
        Map<String, String> map = new HashMap<>();
        String Param = null;
        try {
            List<ServiceVersionBean> list = getAllByConditionVersion(serId, serVersion);
            GetParam getParam = new GetParam();
            //默认展示同步的入参与出参
            map = getParam.getParamToPage("0",list.get(0).getSerFlow());
            if (num == 0){
                Param = map.get("inputParam");
            }else {
                Param = map.get("outputParam");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Param;
    }

    @ResponseBody
    @RequestMapping(value="/getUrl",method = RequestMethod.POST)
    public String getUrl(String key){
        SysCommonCfg systemCommonCfg = sysCommonCfgService.selectByKey(key);
        return systemCommonCfg.getCfg_value();
    }

    public List<ServiceVersionBean> getAllByConditionVersion(String serId, String serVersion){
        Map<String, String> map = new HashMap<String, String>();
        map.put("serVersion",serVersion);
        map.put("serId",serId);
        return serviceVersionService.getAllByCondition(map);
    }

    /**
     * 传入serId，返回响应服务
     * @author 牛浩轩
     * @param orderInterfaceBean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/serviceIsEmpty")
    public List<OrderInterfaceBean> serviceIsEmpty(OrderInterfaceBean orderInterfaceBean){
        return orderInterfaceService.getAllByCondition(orderInterfaceBean);
    }

    /**
     * 回显异步服务
     */
    @RequestMapping(value = "/displayAsynchService")
    public String displayAsynchService(Model model,String orderId, String serId, String applicationId) throws Exception{

        model.addAttribute("flowChartId", serId);
        model.addAttribute("applicationId", applicationId);
        model.addAttribute("serVerId","1.0.0");
        model.addAttribute("orderId", orderId);
        model.addAttribute("tenantId",CurrentUserUtils.getInstance().getUser().getLoginId());

        OrderInterfaceBean orderInterfaceBean = orderInterfaceService.selectContextPage(serId);
        model.addAttribute("serId", serId);
        model.addAttribute("serName",orderInterfaceBean.getServiceMain().getSerName());
        model.addAttribute("serVerId", orderInterfaceBean.getServiceVersionBean().getSerVerId());
        model.addAttribute("serVersion", orderInterfaceBean.getServiceMain().getSerVersion());
        model.addAttribute("countTime",orderInterfaceBean.getCountTime());
        model.addAttribute("serName",orderInterfaceBean.getServiceMain().getSerName());
        model.addAttribute("loginId",orderInterfaceBean.getServiceMain().getLoginId());
        model.addAttribute("serCode",orderInterfaceBean.getServiceMain().getSerCode());
        model.addAttribute("serAgreement",orderInterfaceBean.getServiceVersionBean().getSerAgreement());
        model.addAttribute("serRestype",orderInterfaceBean.getServiceVersionBean().getSerRestType());
        model.addAttribute("serRequest",orderInterfaceBean.getServiceVersionBean().getSerRequest());
        model.addAttribute("serResponse",orderInterfaceBean.getServiceVersionBean().getSerResponse());
        model.addAttribute("serPoint",orderInterfaceBean.getServiceVersionBean().getSerPoint());
        model.addAttribute("synchFlag", "异步");
        if (StringUtil.isNotEmpty(orderInterfaceBean.getServiceMain().getSerResume())) {
            model.addAttribute("serResume",orderInterfaceBean.getServiceMain().getSerResume());
        } else {
            model.addAttribute("serResume","暂无说明信息");
        }


        if(StringUtils.isNotBlank(serId)){
            Map<String,Object> jsonMap = asynchSerNodeService.getNodeJson(orderId, serId);
            System.out.println(jsonMap.get("nodeJsonArray"));
            model.addAttribute("serNodeArray", jsonMap.get("nodeJsonArray").toString());
            model.addAttribute("serJoinArray", jsonMap.get("nodeJoinJsonArray").toString());

            OrderInterfaceBean orderInterfaceBean2 = new OrderInterfaceBean();
            orderInterfaceBean2.setOrderId(orderId);
            List<OrderInterfaceBean> orderInterfaceBeanList = orderInterfaceService.getAllByCondition(orderInterfaceBean2);
            if(orderInterfaceBeanList != null && orderInterfaceBeanList.size() > 0){
                model.addAttribute("orderDesc", orderInterfaceBeanList.get(0).getOrderDesc());
            }
        }
        return "/puborder/apistore/orderAsynchronized";
    }
}
