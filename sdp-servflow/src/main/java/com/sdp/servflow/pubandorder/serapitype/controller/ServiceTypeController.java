package com.sdp.servflow.pubandorder.serapitype.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.servflow.pubandorder.serapitype.model.ServiceTypeBean;
import com.sdp.servflow.pubandorder.serapitype.service.ServiceTypeService;
import com.sdp.servflow.pubandorder.servicebasic.model.ServiceMainBean;
import com.sdp.servflow.pubandorder.servicebasic.service.ServiceMainService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author 牛浩轩
 * @date Created on 2017/10/31.
 */
@Controller
@RequestMapping("/ServiceType")
public class ServiceTypeController {
    /**
     * ServiceTypeService
     */
    @Autowired
    private ServiceTypeService serviceTypeService;

    /**
     * ServiceMainService
     */
    @Autowired
    private ServiceMainService serviceMainService;

    /**
     * 查询未被删除的类型
     *
     * @return List<ServiceTypeBean>
     */
    @ResponseBody
    @RequestMapping(value = "/selectFilterDate", method = RequestMethod.POST)
    public List<ServiceTypeBean> selectFilterDate() {
        List<ServiceTypeBean> serviceApiTypeList = serviceTypeService.selectFilterDate();
        return serviceApiTypeList;
    }

    /**
     * 查询类型
     * @param serviceTypeBean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllByCondition", method = RequestMethod.POST)
    public List<ServiceTypeBean> getAllByCondition(ServiceTypeBean serviceTypeBean) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("serTypeId", serviceTypeBean.getSerTypeId());
        map.put("serTypeName", serviceTypeBean.getSerTypeName());
        map.put("parentId", serviceTypeBean.getParentId());
        map.put("creatUser", serviceTypeBean.getCreatUser());
        map.put("delFlag", "0");
        map.put("stopFlag", serviceTypeBean.getStopFlag());
        List<ServiceTypeBean> serviceTypeList = serviceTypeService.getAllEqalsByCondition(map);
        return serviceTypeList;
    }

    /**
     * 用Map返回结果
     * @param serviceTypeBean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPageData")
    public Map<String, Object> getPageData(ServiceTypeBean serviceTypeBean){
        serviceTypeBean.setDelFlag("0");
        List<ServiceTypeBean> serviceTypeList = serviceTypeService.selectgetTimeByCondition(serviceTypeBean);
        /*if (serviceTypeList.size() == 0){
            serviceTypeBean.setSerTypeId(serviceTypeBean.getParentId());
            serviceTypeBean.setParentId(null);
            serviceTypeList = serviceTypeService.selectgetTimeByCondition(serviceTypeBean);
        }*/
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tatal", serviceTypeList.size());
        map.put("data", serviceTypeList);
        return map;
    }

    /**
     * 查找单个
     * @param serTypeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectOne", method = RequestMethod.POST)
    public List<ServiceTypeBean> selectOne(String serTypeId){
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("serTypeId", serTypeId);
        return serviceTypeService.getAllByCondition(paramMap);
    }

    /**
     * 新增类型
     *
     * @param serviceTypeBean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public int insert(ServiceTypeBean serviceTypeBean) {
        return serviceTypeService.insert(serviceTypeBean);
    }

    /**
     * 更新类型
     *
     * @param serviceApiTypeBean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public int update(ServiceTypeBean serviceApiTypeBean){
        int result = serviceTypeService.update(serviceApiTypeBean);
        return result;
    }

    /**
     * 逻辑删除类型
     * @param serTypeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/logicDelete", method = RequestMethod.POST)
    public int logicDelete(String serTypeId){
        ServiceTypeBean serviceTypeBean = new ServiceTypeBean();
        serviceTypeBean.setSerTypeId(serTypeId);
        serviceTypeBean.setDelFlag("1");
        return serviceTypeService.update(serviceTypeBean);
    }

    /**
     * 查询给类型是否被使用
     */
    @ResponseBody
    @RequestMapping(value = "/selectUsed")
    public String selectUsed(String serTypeId){
        Map<String, String> map = new HashMap<String, String>();
        map.put("serType", serTypeId);
        List<ServiceMainBean> list = serviceMainService.getAllByCondition(map);
        return list.size() > 0 ? "used" : "unused";
    }
}
