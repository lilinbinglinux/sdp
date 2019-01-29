package com.sdp.servflow.pubandorder.order.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.util.JsonUtils;
import com.sdp.frame.web.service.user.UserService;

/**
 * Description:
 *
 * @author Liu Wei
 * @date Created on 2017/12/14.
 */
@Controller
@RequestMapping("/subscription")
public class OrderSubscriptionController {

    @Autowired
    private UserService UserService;

    /**
     *
     * Description:跳转到代订阅
     *
     *@return String
     *
     * @see
     */
    @RequestMapping("/index")
    public String index(){
        return "puborder/order/Subscriptions";
    }

    @ResponseBody
    @RequestMapping("/list")
    public Map list(String start, String length,String jsonStr){
        String loginId = CurrentUserUtils.getInstance().getUser().getLoginId();
        Map<String,Object> paramMap = JsonUtils.stringToCollect(jsonStr);
        Map<String, Object> map = UserService.getUserByCondition(paramMap,start, length);
        return map;
    }
}
