package com.sdp.servflow.pubandorder.flowchart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by duanhan on 2017/7/25.
 */
@Controller
@RequestMapping("/component")
public class ComponentControllr {

    @RequestMapping(value = { "/index" }, method = RequestMethod.GET)
    public String index() {
        return "component/index";
    }

}
