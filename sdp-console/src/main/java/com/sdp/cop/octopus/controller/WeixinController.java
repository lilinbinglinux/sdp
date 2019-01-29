/*
 * 文件名：WeixinController.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月12日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sdp.cop.octopus.service.WeixinService;

import springfox.documentation.annotations.ApiIgnore;


/**
 * 向公众号推送消息controller
 * @author zhangyunzhen
 * @version 2017年7月12日
 * @see WeixinController
 * @since
 */
@ApiIgnore
@Controller
public class WeixinController {

    /**
     * weixinService
     */
    @Autowired
    private WeixinService weixinService;

    /**
     * Description: <br>
     *  上传图片素材
     * @param file 上传文件
     * @return media_id
     * @see
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadPicture(@RequestParam("file") MultipartFile file,HttpServletRequest request,String appName) {
        return weixinService.massMsg(file, request, appName);
    }
}
