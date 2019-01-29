package com.sdp.servflow.openstack.controller;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.util.IdUtil;
import com.sdp.frame.web.HttpClientUtil;
import com.sdp.servflow.pubandorder.pub.model.InterfacePo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * openstack和docker镜像管理页面
 *
 * @author ZY
 * @version 2017年9月24日
 * @see DockerFlowChartController
 */

@Controller
@RequestMapping("/docker")
public class DockerFlowChartController {
    /**
     * 跳转docker列表页面
     *
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "docker/dockerlist";
    }

    /**
     * 获取接口返回的数据，
     *
     * @return
     */
    @RequestMapping("/getTreeData")
    @ResponseBody
    public Object getTreeData() {
        String loginId = CurrentUserUtils.getInstance().getUser().getLoginId();
        String url = "http://10.124.192.44/api/v1/user/"+loginId+"/images";
        String str = HttpClientUtil.doGet(url, "");
        List<InterfacePo> dockerapis = new ArrayList<InterfacePo>();
        JSONArray sSer = JSONArray.fromObject(str);
        String tenantId = CurrentUserUtils.getInstance().getUser().getTenantId();



        if (sSer.size() > 0) {
            for (int i = 0; i < sSer.size(); i++) {
                JSONObject obj = JSONObject.fromObject(sSer.get(i));
                InterfacePo beani = new InterfacePo();
                beani.setId(IdUtil.createId());
                beani.setName(obj.getString("name"));
                beani.setParentId("ROOT");
                beani.setTypeId("0");
                beani.setTenant_id(tenantId);
                dockerapis.add(beani);

                JSONArray tagarray = JSONArray.fromObject(obj.get("tags"));
                for (int j = 0; j < tagarray.size(); j++) {
                    InterfacePo beanj = new InterfacePo();
                    beanj.setId(IdUtil.createId());
                    beanj.setName(tagarray.get(j).toString());
                    beanj.setParentId(beani.getId());
                    beanj.setTypeId("2");
                    beanj.setTenant_id(tenantId);
                    dockerapis.add(beanj);
                }
            }
        }
        JSONArray json = JSONArray.fromObject(dockerapis);
        return json;
    }

    /**
     * 跳转docker流程图页面
     *
     * @return
     */
    @RequestMapping("/flow")
    public String flow(String id, Model model) {
        model.addAttribute("flowChartId", id);
        return "docker/dockerflow";
    }


}
