package com.sdp.servflow.openstack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.frame.web.HttpClientUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * openstack和docker镜像管理页面
 *
 * @author ZY
 * @version 2017年9月24日
 * @see OpenstackFlowchatController
 * @since
 */

@Controller
@RequestMapping("/openstack")
public class OpenstackFlowchatController {
    /**
     * 跳转openstack列表页面
     * @return
     */
    @RequestMapping("/index")
    public String index(){
        return "openstack/openstacklist";
    }

    /**
     * 获取接口返回的数据，
     * @return
     */
    @RequestMapping("/list")
    public String getData(){
        return "";
    }
    /**
     * 跳转openstack流程图页面
     * @return
     */
    @RequestMapping("/flow")
    public String flow(String id, Model model) {
        model.addAttribute("flowChartId", id);
        return "openstack/openstackflow";
    }
    @ResponseBody
    @RequestMapping(value = "/getTreeData", method = RequestMethod.GET)
    public Object getTreeData( ) {
        String  url = "http://10.124.192.45/vm/images/list";
        String str = HttpClientUtil.doGet(url, "");
        JSONObject dlist = JSONObject.fromObject(str);
        JSONArray list = JSONArray.fromObject(dlist.get("data"));
        return list;
    }

    @ResponseBody
    @RequestMapping(value = "/getReturnMessage/{groupId}", method = RequestMethod.GET)
    public Object getReturnMessage(@PathVariable("groupId") String groupId) {
        String  url = "http://10.124.192.45/vm/cluster/groupCreate/status/"+groupId;
        String str = HttpClientUtil.doGet(url, "");
        JSONObject dataobj = JSONObject.fromObject(str);
        return dataobj;

    }

    @RequestMapping(value = { "/flowchartview" }, method = RequestMethod.GET)
    public String index(String id,Model model,String  flowType) {
        model.addAttribute("flowChartId", id);
        model.addAttribute("flowType", flowType);
        return "openstack/flowchartview";
    }




}
