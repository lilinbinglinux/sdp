package com.sdp.servflow.pubandorder.serapitype.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.util.IdUtil;
import com.sdp.servflow.pubandorder.serapitype.model.ServiceApiTypeBean;
import com.sdp.servflow.pubandorder.serapitype.service.ServiceApiTypeService;

/**
 * Description:接口类型维护
 *
 * @author niu
 * @date Created on 2017/10/25.
 */
@Controller
@RequestMapping("/serviceApiType")
public class ServiceApiTypeController {

    /**
     * ServiceApiTypeService
     */
    @Autowired
    private ServiceApiTypeService serviceApiTypeService;

    /**
     * 跳转至主页
     *
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "puborder/serviceapitype/serviceapitype";
    }

    /**
     * 查询类型
     *
     * @return List<ServiceApiTypeBean>
     */
    @ResponseBody
    @RequestMapping(value = "/selectFilterDate", method = RequestMethod.POST)
    public List<ServiceApiTypeBean> selectFilterDate() {
        List<ServiceApiTypeBean> serviceApiTypeList = serviceApiTypeService.selectFilterDate();
        return serviceApiTypeList;
    }

    /**
     * 查找单个
     * @param apiTypeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectOne", method = RequestMethod.POST)
    public List<ServiceApiTypeBean> selectOne(String apiTypeId){
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("apiTypeId", apiTypeId);
        return serviceApiTypeService.getAllByCondition(paramMap);
    }

    /**
     * 新增类型
     *
     * @param serviceApiTypeBean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public int insert(ServiceApiTypeBean serviceApiTypeBean) {
        serviceApiTypeBean.setApiTypeId(IdUtil.createId());
        serviceApiTypeBean.setCreatUser(CurrentUserUtils.getInstance().getUser().getUserName());
        serviceApiTypeBean.setCreatTime(new Date(System.currentTimeMillis()));
        int result = serviceApiTypeService.insert(serviceApiTypeBean);
        return result;
    }

    /**
     * 更新类型
     *
     * @param serviceApiTypeBean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public int update(ServiceApiTypeBean serviceApiTypeBean){
        int result = serviceApiTypeService.update(serviceApiTypeBean);
        return result;
    }

    /**
     * 逻辑删除类型
     * @param apiTypeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/logicDelete", method = RequestMethod.POST)
    public int logicDelete(String apiTypeId){
        ServiceApiTypeBean serviceApiTypeBean = new ServiceApiTypeBean();
        serviceApiTypeBean.setApiTypeId(apiTypeId);
        serviceApiTypeBean.setDelFlag("1");
        return serviceApiTypeService.update(serviceApiTypeBean);
    }

}
