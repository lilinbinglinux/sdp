package com.sdp.servflow.pubandorder.pub.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.util.IdUtil;
import com.sdp.frame.util.JsonUtils;
import com.sdp.servflow.pubandorder.pub.model.ProjectBean;
import com.sdp.servflow.pubandorder.pub.service.ProjectBeanService;

/**
 * 
 * 处理项目和模块的请求Controller
 *
 * @author ZY
 * @version 2017年6月9日
 * @see ProController
 * @since
 */
@Controller
@RequestMapping("/pro")
public class ProjectBeanController {
	
    /**
     * ProjectBeanService
     */
    @Autowired
    private ProjectBeanService proService;

    /**
     * 
     * Description:分页获取项目或模块详情 
     * 
     *@param start
     *@param length
     *@param jsonStr
     *@param request
     *@return Map
     *
     * @see
     */
    @ResponseBody
    @RequestMapping("/selectPage")
    public Map selectPage(String start, String length, String jsonStr) {
        @SuppressWarnings("unchecked")
        Map<String, Object> paramMap = JsonUtils.stringToCollect(jsonStr);
        paramMap.put("tenantId", CurrentUserUtils.getInstance().getUser().getTenantId());
        return proService.selectAll(start, length, paramMap);
    }
    
    /**
     * 
     * Description: 添加项目或模块
     * 
     *@param project
     *@param request
     *@return int
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public int insert(ProjectBean project) {
        project.setProid(IdUtil.createId());
        project.setCreatedate(new Date());
        project.setTenant_id(CurrentUserUtils.getInstance().getUser().getTenantId());
    	return proService.insert(project);
    }
    
    /**
     * 
     * Description: 根据Id查询，用于update回显
     * 
     *@param id
     *@return ProjectBean
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value="/getProById",method=RequestMethod.POST)
    public ProjectBean getProById(String id){
        ProjectBean pro = proService.getProById(id);
        return pro;
    }
    
    /**
     * 
     * Description:更新数据库信息 
     * 
     *@param project void
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value="/update",method=RequestMethod.POST)
    public void update(ProjectBean project){
    	proService.update(project);
    }
    
    /**
     * 
     * Description:根据主键删除 
     * 
     *@param pubid
     *@return int
     *@throws Exception int
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value="/delete",method=RequestMethod.POST)
    public int  delete(String pubid) throws Exception{
        return proService.deleteByProId(pubid);
    }
	
}

