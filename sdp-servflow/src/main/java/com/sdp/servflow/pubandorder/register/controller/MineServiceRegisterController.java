package com.sdp.servflow.pubandorder.register.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.util.JsonUtils;
import com.sdp.servflow.pubandorder.orderapistore.service.OrderInterfaceService;
import com.sdp.servflow.pubandorder.orderapistore.util.OrderCfgKey;
import com.sdp.servflow.pubandorder.servicebasic.model.ServiceVersionBean;
import com.sdp.servflow.pubandorder.servicebasic.service.ServiceMainService;
import com.sdp.servflow.pubandorder.servicebasic.service.ServiceVersionService;
import com.sdp.servflow.sysConfig.service.SysCommonCfgService;

/**
 * Description:
 *
 * @author Niu Haoxuan
 * @date Created on 2017/12/20.
 */
@Controller
@RequestMapping(value = "/mineServiceRegisterController")
public class MineServiceRegisterController {

    /**
     * ServiceMainService
     */
    @Resource
    private ServiceMainService serviceMainService;

    /**
     * ServiceVersionService
     */
    @Resource
    private ServiceVersionService serviceVersionService;

    /**
     * OrderInterfaceService
     */
    @Autowired
    private OrderInterfaceService orderInterfaceService;

    /**
     * SysCommonCfgService
     */
    @Autowired
    private SysCommonCfgService sysCommonCfgService;

    /**
     * 跳转至“我的注册”（查询发布订阅的相关信息）
     * @return
     */
    @RequestMapping(value = "/mineRegister")
    public String mineRegister(){
        return "/puborder/register/mineRegister";
    }

    /**
     * 查询发布订阅的相关服务
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectRegisterAsynch")
    public Map selectRegisterAsynch(String start, String length, String jsonStr) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> paramMap = JsonUtils.stringToCollect(jsonStr);
        paramMap.put("loginId", CurrentUserUtils.getInstance().getUser().getLoginId());
        paramMap.put("delFlag", "0");
        paramMap.put("synchFlag", "1");
        System.out.println(sysCommonCfgService.selectByKey("1004").getCfg_value());
        paramMap.put("baseHttpUrl", sysCommonCfgService.selectByKey("1004").getCfg_value());
        paramMap.put("baseWebServiceUrl", sysCommonCfgService.selectByKey("1005").getCfg_value());
        resultMap = serviceMainService.getAllAndSerTypeName(paramMap, start, length);
        System.out.println(resultMap);
        return resultMap;
    }

    /**
     * 异步注册时，根据serId和serVersion获取url
     * @param serId
     * @param serVersion
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/formSerIdToURL")
    public String formSerIdToURL(String serId, String serVersion) throws Exception {
        Map<String, String> serFlowMap = new HashMap<String, String>();
        String getUrl = null;
        serFlowMap.put("serId", serId);
        serFlowMap.put("serVersion", serVersion);
        List<ServiceVersionBean> serVerisonList = serviceVersionService.getAllByCondition(serFlowMap);
        if (serVerisonList != null && !serVerisonList.isEmpty()){
            String serFlow = serVerisonList.get(0).getSerFlow();
            //解析xml，获取服务的协议类型
            Map<String, String> jsonMap = orderInterfaceService.getParam(serFlow);
            if("http".equalsIgnoreCase(jsonMap.get("protocal"))){                                           //根据不同的协议生成不同的url
                getUrl = serviceMainService.getUrl(serId, OrderCfgKey.CFG_KEY_ASYNCHRONIZED_HTTP);
            }else if("webservice".equalsIgnoreCase(jsonMap.get("protocal"))){
                getUrl = serviceMainService.getUrl(serId, OrderCfgKey.CFG_KEY_ASYNCHRONIZED_WEBSERVICE);
            }else {
                getUrl = "请输入正确的协议！";
            }
        }
        return getUrl;
    }

    /**
     * 根据serId删除数据
     * @param serId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteServiceAsynch")
    public int deleteServiceAsynch(String serId, String serVersion){
        ServiceVersionBean serviceVersionBean = new ServiceVersionBean();
        if (serId != null || serId != ""){
            serviceVersionBean.setSerId(serId);
        }
        if (serVersion != null || serVersion != ""){
            serviceVersionBean.setSerVersion(serVersion);
        }
        int i = serviceMainService.delete(serId);
        int j = serviceVersionService.deleteBySerId(serviceVersionBean);
        if (i == 0 && j == 0){
            return 0;
        }else {
            return i+j;
        }

    }

}
