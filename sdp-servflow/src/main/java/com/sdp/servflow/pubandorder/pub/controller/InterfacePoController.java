package com.sdp.servflow.pubandorder.pub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.CurrentUserUtils;
import com.sdp.servflow.pubandorder.pub.model.InterfacePo;
import com.sdp.servflow.pubandorder.pub.service.InterfacePoService;

/**
 * 
 * 抽象实体的Controller，处理树结构的初始化
 *
 * @author ZY
 * @version 2017年6月9日
 * @see InterfacePoController
 * @since
 */
@Controller
@RequestMapping("/interfacePo")
public class InterfacePoController {
	
    /**
     * recordInfoService
     */
    @Autowired
	private InterfacePoService interfacePoService;
	
	/**
	 * 
	 * Description: 显示树形首页
	 * 
	 *@return String
	 *
	 * @see
	 */
    @RequestMapping("/index")
    public String index() {
        return "puborder/promanage";
    }
    
    /**
     * 
     * Description:返回我的注册接口页面 
     * 
     *@return String
     *
     * @see
     */
    @RequestMapping("/publishMine")
    public String publishMine(){
        return "puborder/publishmine";
    }
    
	/**
	 * 
	 * Description: 获取所有节点数据
	 *
     *@return List<InterfacePo>
	 *
	 * @see
	 */
    @ResponseBody
    @RequestMapping("/selectAll")
    public List<InterfacePo> selectAll() {
        List<InterfacePo> pros = interfacePoService.selectAll();
        return pros;
    }
    
    /**
     * 
     * Description:根据类型和主键删除 
     * 
     *@param id
     *@param typeId
     *@return String
     *
     * @see
     */
    @ResponseBody
    @RequestMapping("/delete")
    public String delete(String id,String typeId) {
        String tenant_id = CurrentUserUtils.getInstance().getUser().getTenantId();
        return interfacePoService.delete(id,typeId,tenant_id);
    }
    
}
