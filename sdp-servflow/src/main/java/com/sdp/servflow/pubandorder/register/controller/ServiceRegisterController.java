package com.sdp.servflow.pubandorder.register.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.CurrentUserUtils;
import com.sdp.servflow.pubandorder.register.service.ServiceRegisterService;
import com.sdp.servflow.pubandorder.serapitype.model.ServiceTypeBean;
import com.sdp.servflow.pubandorder.serapitype.service.ServiceTypeService;
import com.sdp.servflow.pubandorder.servicebasic.model.ServiceMainBean;
import com.sdp.servflow.pubandorder.servicebasic.service.ServiceMainService;
import com.sdp.servflow.pubandorder.servicebasic.service.ServiceVersionService;
import com.sdp.servflow.serlayout.process.service.SerProcessNodeService;

/**
 * Description:
 *
 * @author Niu Haoxuan
 * @date Created on 2017/12/10.
 */
@Controller
@RequestMapping(value = "/serviceRegisterController")
public class ServiceRegisterController {

    /**
     * SerProcessNodeService
     */
    @Autowired
    private SerProcessNodeService serProcessNodeService;

    /**
     * ServiceRegisterService
     */
    @Autowired
    private ServiceRegisterService serviceRegisterService;

    /**
     * ServiceTypeService
     */
    @Autowired
    private ServiceTypeService serviceTypeService;

    /**
     * ServiceVersionService
     */
    @Autowired
    private ServiceVersionService serviceVersionService;

    /**
     * ServiceMainService
     */
    @Autowired
    private ServiceMainService serviceMainService;

    /**
     * 发布订阅——服务注册
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(Model model, String flowChartId, String serVersion, String serType) throws Exception{
        //获取serVerId
        Map<String, String> map = new HashMap<>();
        map.put("serId", flowChartId);
        map.put("serVersion", serVersion);
        List<ServiceMainBean> serviceMainBeanList = serviceMainService.getAllByCondition(map);
        String serVerId = serviceVersionService.getAllByCondition(map).get(0).getSerVerId();
        String serName = serviceMainBeanList.get(0).getSerName();

        model.addAttribute("flowChartId", flowChartId);
        model.addAttribute("serVerId",serVerId);
        model.addAttribute("tenantId", CurrentUserUtils.getInstance().getUser().getLoginId());
        model.addAttribute("serName",serName);
        model.addAttribute("serResume",serviceMainBeanList.get(0).getSerResume());

        model.addAttribute("serviceTypeId",serType);
        model.addAttribute("serFlowType",serType);

        if(StringUtils.isNotBlank(flowChartId)){
            Map<String,Object> jsonMap = serProcessNodeService.getNodeJson(flowChartId,serVerId,serType);
            model.addAttribute("serNodeArray", jsonMap.get("nodeJsonArray"));
        }
        return "/puborder/register/register";
    }

    /**
     * 保存节点
     * @param serNodeArray
     * @param serJoinArray
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addNode")
    @ResponseBody
    @Transactional(rollbackFor=RuntimeException.class)
    public String addStartNode(String serNodeArray, String serJoinArray, String serFlowType) throws Exception {
        try {
            serviceRegisterService.addStartNode(serNodeArray, serJoinArray, serFlowType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return "success";
    }

    /**
     * 更新节点
     * @param serId
     * @param updateFlag
     * @param serNodeArray
     * @param serJoinArray
     * @return
     * @throws Exception
     */
    @RequestMapping(value={"/updateNode"},method= RequestMethod.POST)
    @ResponseBody
    @Transactional(rollbackFor=RuntimeException.class)
    public String updateNode(String serId,String updateFlag,String serNodeArray,String serJoinArray, String serFlowType) throws Exception{
        try {
            serProcessNodeService.updateNode(serId,updateFlag,serNodeArray,serJoinArray,serFlowType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return "success";
    }

    /**
     * 查询服务类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectSerType")
    public List<ServiceTypeBean> selectSerType(){
        return serviceTypeService.selectFilterDate();
    }

}
