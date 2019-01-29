package com.sdp.servflow.pubandorder.serapplication.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.util.IdUtil;
import com.sdp.servflow.pubandorder.serapplication.model.SerApplicationBean;
import com.sdp.servflow.pubandorder.serapplication.service.SerApplicationService;

/**
 * Description:应用管理相关controller
 *
 * @author 牛浩轩
 * @date Created on 2017/11/10.
 */
@Controller
@RequestMapping(value = "/serApplication")
public class SerApplicationController {

    /**
     * SerApplicationService
     */
    @Autowired
    private SerApplicationService serApplicationService;

    /**
     * 返回主页
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(){
        return "puborder/serapplication/serapplication_2";
    }

    /**
     * 按条件查找应用
     * @param serApplicationBean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllByCondition")
    public List<SerApplicationBean> getAllByCondition(SerApplicationBean serApplicationBean){
        Map<String, String> map = new HashMap<String, String>();
        map.put("applicationId", serApplicationBean.getApplicationId());
        map.put("applicationName", serApplicationBean.getApplicationName());
        map.put("creatUser", CurrentUserUtils.getInstance().getUser().getLoginId());
        map.put("delFlag", "0");
        map.put("tenantId", serApplicationBean.getTenantId());
        return serApplicationService.getAllByCondition(map);
    }

    /**
     * 分页查找应用
     * @param jsonStr
     * @param start
     * @param length
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectPage")
    public Map<String, Object> selectPage(String jsonStr, String start, String length){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap = JSONObject.parseObject(jsonStr);
        paramMap.put("creatUser", CurrentUserUtils.getInstance().getUser().getLoginId());
        return serApplicationService.selectPage(paramMap, start, length);
    }

    /**
     * 新增应用
     * @param serApplicationBean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public int insert(SerApplicationBean serApplicationBean){
        serApplicationBean.setApplicationId(IdUtil.createId());
        serApplicationBean.setCreatUser(CurrentUserUtils.getInstance().getUser().getLoginId());
        serApplicationBean.setCreatTime(new Date(System.currentTimeMillis()));
        serApplicationBean.setDelFlag("0");
        serApplicationBean.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
        return serApplicationService.insert(serApplicationBean);
    }

    /**
     * 更新应用
     * @param serApplicationBean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public int update(SerApplicationBean serApplicationBean){
        return serApplicationService.update(serApplicationBean);
    }

    /**
     * 删除应用
     * @param applicationId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int delete(String applicationId){
        return serApplicationService.delete(applicationId);
    }

}
