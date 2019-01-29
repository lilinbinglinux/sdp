package com.sdp.servflow.pubandorder.whitelist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.servflow.pubandorder.whitelist.model.WhiteListBean;
import com.sdp.servflow.pubandorder.whitelist.service.WhiteListService;

/**
 * Description:
 *
 * @author Niu Haoxuan
 * @date Created on 2017/12/4.
 */
@Controller
@RequestMapping(value = "/whiteList")
public class WhiteListController {
    /**
     * WhiteListService
     */
    @Autowired
    private WhiteListService whiteListService;

    /**
     * 插入黑名单白名单
     * @param whiteListBean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public int insert(WhiteListBean whiteListBean,String ipAddrs){
        return whiteListService.splitIps(whiteListBean, ipAddrs);
    }

    /**
     * 根据orderId删除黑名单白名单数据
     * @param orderId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int delete(String orderId){
        return whiteListService.delete(orderId);
    }

}
