package com.sdp.servflow.pubandorder.orderapistore.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.util.StringUtil;
import com.sdp.servflow.pubandorder.common.Response;
import com.sdp.servflow.pubandorder.init.sysConfig.mapper.SysConfigSerMapper;
import com.sdp.servflow.pubandorder.orderapistore.model.OrderInterfaceBean;
import com.sdp.servflow.pubandorder.orderapistore.service.AsynchSerNodeService;
import com.sdp.servflow.pubandorder.orderapistore.service.OrderInterfaceService;
import com.sdp.servflow.pubandorder.orderapistore.util.GetParam;
import com.sdp.servflow.pubandorder.serapitype.model.ServiceTypeBean;
import com.sdp.servflow.pubandorder.serapitype.service.ServiceTypeService;
import com.sdp.servflow.pubandorder.serapplication.model.SerApplicationBean;
import com.sdp.servflow.pubandorder.serapplication.service.SerApplicationService;
import com.sdp.servflow.pubandorder.serve.LayoutServer;
import com.sdp.servflow.pubandorder.servicebasic.model.ServiceMainBean;
import com.sdp.servflow.pubandorder.servicebasic.model.ServiceVersionBean;
import com.sdp.servflow.pubandorder.servicebasic.service.ServiceMainService;
import com.sdp.servflow.pubandorder.servicebasic.service.ServiceVersionService;
import com.sdp.servflow.pubandorder.whitelist.model.WhiteListBean;
import com.sdp.servflow.pubandorder.whitelist.service.WhiteListService;
import com.sdp.servflow.serlayout.process.service.SerProcessNodeService;
import com.sdp.servflow.serlayout.process.util.SerProcessConstantFlag;

/**
 * @author niu
 * @date Created on 2017/10/19.
 */
@Controller
@RequestMapping(value = "/apiStore")
public class WareHouseController {

    /**
     * OrderInterfaceService
     */
    @Autowired
    private OrderInterfaceService orderInterfaceService;

    /**
     * SerApplicationService
     */
    @Autowired
    private SerApplicationService serApplicationService;

    /**
     * ServiceMainService
     */
    @Autowired
    private ServiceMainService serviceMainService;

    /**
     * ServiceVersionBean
     */
    @Autowired
    private ServiceVersionService serviceVersionService;

    /**
     * SysConfigSerMapper
     */
    @Autowired
    private SysConfigSerMapper sysConfigSerMapper;

    /**
     * SerProcessNodeService
     */
    @Autowired
    private SerProcessNodeService serProcessNodeService;

    /**
     * AsynchSerNodeService
     */
    @Autowired
    private AsynchSerNodeService asynchSerNodeService;

    /**
     * LayoutServer
     */
    @Autowired
    private LayoutServer layoutServer;

    /**
     * ServiceTypeService
     */
    @Autowired
    private ServiceTypeService serviceTypeService;

    /**
     * WhiteListService
     */
    @Autowired
    private WhiteListService whiteListService;

    /**
     * 跳至主页
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(String loginId, Model model){
        System.out.println(loginId);
        if(StringUtils.isNotEmpty(loginId)){
            model.addAttribute("loginId",loginId);
        }
        return "puborder/apistore/warehouse";
    }

    /**
     * 跳转到异步详情页
     * @param id
     * @param model
     * @param serVerId
     * @return
     */
    @RequestMapping(value = "/asynchronized")
    public String asynchronized(String id,String serVerId, String loginId, Model model) throws Exception {
        model.addAttribute("flowChartId", id);
        model.addAttribute("serVerId",serVerId);
        model.addAttribute("tenantId",CurrentUserUtils.getInstance().getUser().getLoginId());

        OrderInterfaceBean orderInterfaceBean = orderInterfaceService.selectContextPage(id);
        model.addAttribute("serId", id);
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
        model.addAttribute("flowType", orderInterfaceBean.getServiceMain().getSerType());

        if(StringUtil.isNotEmpty(orderInterfaceBean.getServiceMain().getSerType())){
            Map<String, String> map = new HashMap<String, String>();
            map.put("serTypeId", orderInterfaceBean.getServiceMain().getSerType());
            List<ServiceTypeBean> list = serviceTypeService.getAllByCondition(map);
            if (list != null){
                model.addAttribute("flowTypeName", list.get(0).getSerTypeName());
            }else{
                model.addAttribute("flowTypeName", "暂无分类");
            }
        }else{
            model.addAttribute("flowTypeName", "暂无分类");
        }

        if (StringUtil.isNotEmpty(orderInterfaceBean.getServiceMain().getSerResume())) {
            model.addAttribute("serResume",orderInterfaceBean.getServiceMain().getSerResume());
        } else {
            model.addAttribute("serResume","暂无说明信息");
        }

        if(StringUtils.isNotBlank(id)){
            Map<String,Object> jsonMap = asynchSerNodeService.getNodeJsonExceptloginId(id,serVerId);
            System.out.println(jsonMap);
            model.addAttribute("serNodeArray", jsonMap.get("nodeJsonArray").toString());
            model.addAttribute("serJoinArray", jsonMap.get("nodeJoinJsonArray").toString());
        }
        if(StringUtils.isNotEmpty(loginId)){
            model.addAttribute("subloginId",loginId);
        }

        return "puborder/apistore/asynchronized";
    }

    /**
     * 跳转至同步服务详情页
     * @param serId
     * @return
     */
    @RequestMapping(value = "/serviceDes", method = RequestMethod.GET)
    public String serviceDes(String serId, String loginId, String orderId, Model model){
        OrderInterfaceBean orderInterfaceBean = orderInterfaceService.selectContextPage(serId);
        model.addAttribute("serId", serId);
        model.addAttribute("serVerId", orderInterfaceBean.getServiceVersionBean().getSerVerId());
        model.addAttribute("serVersion", orderInterfaceBean.getServiceMain().getSerVersion());
        model.addAttribute("countTime",orderInterfaceBean.getCountTime());
        model.addAttribute("serName",orderInterfaceBean.getServiceMain().getSerName());
        model.addAttribute("loginId",orderInterfaceBean.getServiceMain().getLoginId());
        model.addAttribute("createDateString",orderInterfaceBean.getServiceMain().getCreateDateString());
        model.addAttribute("serCode",orderInterfaceBean.getServiceMain().getSerCode());

        if (StringUtil.isNotEmpty(orderInterfaceBean.getServiceMain().getSerResume())) {
            model.addAttribute("serResume",orderInterfaceBean.getServiceMain().getSerResume());
        } else {
            model.addAttribute("serResume","暂无说明信息");
        }

        model.addAttribute("serAgreement",orderInterfaceBean.getServiceVersionBean().getSerAgreement());
        model.addAttribute("serRestype",orderInterfaceBean.getServiceVersionBean().getSerRestType());
        model.addAttribute("serRequest",orderInterfaceBean.getServiceVersionBean().getSerRequest());
        model.addAttribute("serResponse",orderInterfaceBean.getServiceVersionBean().getSerResponse());
        model.addAttribute("serPoint",orderInterfaceBean.getServiceVersionBean().getSerPoint());
        model.addAttribute("synchFlag", "同步");

        if (orderInterfaceBean.getServiceType() != null) {
            model.addAttribute("serType",orderInterfaceBean.getServiceType().getSerTypeName());
            model.addAttribute("accFreq",orderInterfaceBean.getAccFreq());
            model.addAttribute("accFreqType",orderInterfaceBean.getAccFreqType());
        }

        if(StringUtils.isNotBlank(orderId)){
            OrderInterfaceBean orderInterfaceBean2 = new OrderInterfaceBean();
            orderInterfaceBean2.setOrderId(orderId);
            List<OrderInterfaceBean> orderInterfaceBeanList = orderInterfaceService.getAllByCondition(orderInterfaceBean2);
            orderInterfaceBeanList.get(0);
            model.addAttribute("orderId", orderId);
            model.addAttribute("orderName", orderInterfaceBeanList.get(0).getOrderName());
            model.addAttribute("orderCode", orderInterfaceBeanList.get(0).getOrderCode());
            model.addAttribute("protocal", orderInterfaceBeanList.get(0).getProtocal());
            model.addAttribute("accFreq", orderInterfaceBeanList.get(0).getAccFreq());
            model.addAttribute("accFreqType", orderInterfaceBeanList.get(0).getAccFreqType());
            model.addAttribute("limitIp", orderInterfaceBeanList.get(0).getLimitIp());
            model.addAttribute("appId", orderInterfaceBeanList.get(0).getAppId());
            model.addAttribute("url", orderInterfaceBeanList.get(0).getUrl());

            //查询黑白名单表
            if ("1".equals(orderInterfaceBeanList.get(0).getLimitIp())){
                WhiteListBean whiteListBean = new WhiteListBean();
                whiteListBean.setOrderId(orderId);
                List<WhiteListBean> whiteListBeanList = whiteListService.getAllByCondition(whiteListBean);
                model.addAttribute("nameType", whiteListBeanList.get(0).getNameType());

                StringBuilder sb = new StringBuilder();
                for (int i=0; i<whiteListBeanList.size(); i++){
                    sb.append(whiteListBeanList.get(i).getIpAddr() + ",");
                }
                model.addAttribute("ips", sb.toString().substring(0, sb.toString().length()-1));
            }
        }

        String user = CurrentUserUtils.getInstance().getUser().getLoginId();
        Map<String, String> map = new LinkedHashMap<>();
        map.put("creatUser", user);
        List<SerApplicationBean> applicationList = serApplicationService.getAllByCondition(map);
        map.clear();
        for (SerApplicationBean serApplicationBean: applicationList) {
            map.put(serApplicationBean.getApplicationId(),serApplicationBean.getApplicationName());
        }
        model.addAttribute("applicationList", map);
        if(StringUtils.isNotEmpty(loginId)){
            model.addAttribute("subloginId",loginId);
        }
        if(orderId != null){
            return "puborder/apistore/orderSynchronized";
        }else{
            return "puborder/apistore/synchronized";
        }
    }

    /**
     * 查询服务
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllByCondition", method = RequestMethod.POST)
    public List<ServiceMainBean> getAllByCondition(ServiceMainBean serviceMainBean){
        Map<String,String> map = new HashMap<>();
        map.put("serId", serviceMainBean.getSerId());
        map.put("serName", serviceMainBean.getSerName());
        map.put("serType", serviceMainBean.getSerType());
        map.put("serCode", serviceMainBean.getSerCode());
        map.put("serVersion", serviceMainBean.getSerVersion());
        map.put("serPath", serviceMainBean.getSerPath());
        map.put("serResume", serviceMainBean.getSerResume());
        map.put("synchFlag", serviceMainBean.getSynchFlag());
        map.put("delFlag", "0");
        map.put("stopFlag", serviceMainBean.getStopFlag());
        map.put("tenantId", serviceMainBean.getTenantId());
        map.put("loginId", serviceMainBean.getLoginId());
        map.put("stopFlag",serviceMainBean.getStopFlag());
        return serviceMainService.getAllByCondition(map);
    }

    /**
     * 获取示例参数
     * @param serviceVersionBean
     * @param num
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllByConditionVersionParam", method = RequestMethod.POST)
    public String getAllByConditionVersionParam(ServiceVersionBean serviceVersionBean, int num){
        Map<String, String> map = new HashMap<>();
        String Param = null;
        try {
            List<ServiceVersionBean> list = getAllByConditionVersion(serviceVersionBean);
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

    /**
     * 查询版本
     * @param serviceVersionBean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllByConditionVersion", method = RequestMethod.POST)
    public List<ServiceVersionBean> getAllByConditionVersion(ServiceVersionBean serviceVersionBean){
        Map<String, String> map = new HashMap<>();
        map.put("serVerId",serviceVersionBean.getSerVerId());
        map.put("serVersion",serviceVersionBean.getSerVersion());
        map.put("serId",serviceVersionBean.getSerId());
        map.put("delFlag",serviceVersionBean.getDelFlag());
        map.put("stopFlag",serviceVersionBean.getStopFlag());
        map.put("tenantId",serviceVersionBean.getTenantId());
        map.put("loginId",serviceVersionBean.getLoginId());
        return serviceVersionService.getAllByCondition(map);
    }

    /**
     * 查询订阅表相关内容
     * @param orderInterfaceBean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllByConditionOrderInterface", method = RequestMethod.POST)
    public List<OrderInterfaceBean> getAllByConditionOrderInterface(OrderInterfaceBean orderInterfaceBean){
        return orderInterfaceService.getAllByCondition(orderInterfaceBean);
    }

    /**
     * 插入订阅数据
     * @param orderInterfaceBean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insert(OrderInterfaceBean orderInterfaceBean){
        String orderId = orderInterfaceService.insert(orderInterfaceBean);
        return orderId;
    }

    /**
     * 更新订阅数据
     * @param orderInterfaceBean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public int update(OrderInterfaceBean orderInterfaceBean){
        return orderInterfaceService.update(orderInterfaceBean);
    }

    /**
     * 发布订阅（异步）插入订阅数据
     * @param orderInterfaceBean
     * @param serNodeArray
     * @param serJoinArray
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/changeAsynchronized", method = RequestMethod.POST)
    @Transactional(rollbackFor=RuntimeException.class)
    public String changeAsynchronized(OrderInterfaceBean orderInterfaceBean, String serNodeArray, String serJoinArray,String insertOrUpdateFlag) throws Exception{
        try {
            if("insert".equals(insertOrUpdateFlag)){
                OrderInterfaceBean orderInterfaceBean2 = new OrderInterfaceBean();
                orderInterfaceBean2.setSerId(orderInterfaceBean.getSerId());
                orderInterfaceBean2.setApplicationId(orderInterfaceBean.getApplicationId());
                List<OrderInterfaceBean> orderInterfaceBeanList = orderInterfaceService.getAllByCondition(orderInterfaceBean2);
                if(orderInterfaceBeanList == null || orderInterfaceBeanList.isEmpty()){
                    orderInterfaceService.insertAsynchronized(orderInterfaceBean, serNodeArray, serJoinArray, SerProcessConstantFlag.ADDUPDATE_ADD);
                }else{
                    return "fail";
                }
            }else if("update".equals(insertOrUpdateFlag)){
                orderInterfaceService.insertAsynchronized(orderInterfaceBean, serNodeArray, serJoinArray, SerProcessConstantFlag.ADDUPDATE_UPDATE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return "success";
    }

    /**
     * 根据serId查询orderinterface数据是否存在
     * 存在则更新
     * 不存在则新增
     *
     * @param orderInterfaceBean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/existAsynchronized")
    public String existAsynchronized(OrderInterfaceBean orderInterfaceBean){
        List<OrderInterfaceBean> list = orderInterfaceService.getAllByCondition(orderInterfaceBean);
        if (list == null || list.size() == 0){
            return "insert";
        }
        return "update";
    }

    /**
     * 查询错误码表信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllConditionCodeTable", method = RequestMethod.POST)
    public List<HashMap<String, Object>> getAllConditionCodeTable(){
        return sysConfigSerMapper.getCodeTable();
    }

    /**
     * 在线测试
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/testOnline")
    public String testOnline(ServiceVersionBean serviceVersionBean, String paramStr){
        HashMap<String, Object> publicParam = new HashMap<>();
        publicParam.put("ser_id", serviceVersionBean.getSerId());
        publicParam.put("ser_version", serviceVersionBean.getSerVersion());
        //publicParam.put("requestID", UUID.randomUUID().toString().replace("-",""));
        publicParam.put("tenant_id", CurrentUserUtils.getInstance().getUser().getTenantId());

        Map<String, String> map = new HashMap<>();
        map.put("serVersion",serviceVersionBean.getSerVersion());
        map.put("serId",serviceVersionBean.getSerId());
        List<ServiceVersionBean> list = serviceVersionService.getAllByCondition(map);
        String esbXml = list.get(0).getSerFlow();

        HttpClient httpClient = new HttpClient();
        Response response = layoutServer.analysis(publicParam, paramStr, httpClient, esbXml);
        String result = response.toString();
        return result;
    }

    /**
     * 相同应用下订阅的服务只能有一个,对其进行判断
     * @param orderInterfaceBean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectApplicationRepeated")
    public boolean selectApplicationRepeated(OrderInterfaceBean orderInterfaceBean){
        OrderInterfaceBean orderInterfaceBean2 = new OrderInterfaceBean();
        orderInterfaceBean2.setSerId(orderInterfaceBean.getSerId());
        orderInterfaceBean2.setApplicationId(orderInterfaceBean.getApplicationId());
        List<OrderInterfaceBean> orderInterfaceBeanList = orderInterfaceService.getAllByCondition(orderInterfaceBean2);
        if (orderInterfaceBeanList == null || orderInterfaceBeanList.size() == 0){
            return true;
        }
        return false;
    }
}
