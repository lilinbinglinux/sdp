package com.sdp.bcm.controller;

import com.sdp.bcm.constant.ProjectDictionary;
import com.sdp.bcm.entity.ProProject;
import com.sdp.bcm.entity.ProUser;
import com.sdp.bcm.exception.ErrorProjectMessageException;
import com.sdp.bcm.service.impl.ProProjectServiceImpl;
import com.sdp.bcm.utils.ResponseUtils;
import com.sdp.common.CurrentUserUtils;
import com.sdp.common.entity.BasePageForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lumeiling
 * @package com.bonc.bcm.controller
 * @create 2018-11-2018/11/23 下午1:53
 **/

@Controller
@RequestMapping("/bcm/v1/project")
public class ProProjectController {

    @Autowired
    private ProProjectServiceImpl proProjectService;

    /**
     * 保存当前项目ID
     * @param projectId
     * @return
     */
    @RequestMapping(value = {"/setId"}, method = RequestMethod.GET)
    public ResponseUtils saveProjectId(String projectId) {
        ResponseUtils result = new ResponseUtils();
        result.setData("");
        try {
            proProjectService.saveProjectId(projectId);
            result.setCode(ProjectDictionary.PROJECT_SUCCESS);
            result.setMessage("保存项目ID成功");
            return result;
        }catch (ErrorProjectMessageException e){
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 查看项目列表页面
     * @return
     */
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String proProjectPage() {
        return "bcm/project/proProject";
    }

    /**
     * 项目人员及角色管理页面
     * @param projectId
     * @return
     */
    @RequestMapping(value = {"/user/{projectId}"}, method = RequestMethod.GET)
    public String proUserPage(@PathVariable("projectId") String projectId) {
        return "bcm/project/proUser";
    }

//    /**
//     * 创建新项目
//     * @return
//     */
//    @RequestMapping(value = {"/newProject"}, method = RequestMethod.GET)
//    public String createProjectPage() {
//        return "bcm/createProject";
//    }
//
//    /**
//     * 修改项目信息
//     * @param projectId
//     * @return
//     */
//    @RequestMapping(value = {"/editProject/{projectId}"}, method = RequestMethod.GET)
//    public String editProjectPage(@PathVariable("projectId") String projectId) {
//        return "bcm/projectInfo";
//    }

    /**
     * 查看项目人员
     * @param projectId
     * @return
     */
    @RequestMapping(value = {"/selectAllUsers"},method = RequestMethod.GET)
    @ResponseBody
    public ResponseUtils findAllProjectUser(@RequestParam("projectId") String projectId) {
        ResponseUtils result = new ResponseUtils();
        try {
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("projectId", projectId);
//            return new ResponseEntity<>(proProjectService.findAllProjectUser(paramMap), HttpStatus.OK);
            result.setData(proProjectService.findAllProjectUser(paramMap));
            result.setCode(ProjectDictionary.PROJECT_SUCCESS);
            result.setMessage("获取项目人员列表成功");
        } catch (ErrorProjectMessageException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            result.setData("");
        }
        return result;
    }

    /**
     * 添加一个项目人员
     * @param proUser
     * @return
     */
    @RequestMapping(value = {"/saveProUser"},method = RequestMethod.POST)
    @ResponseBody
    public ResponseUtils insertProUser(@RequestBody ProUser proUser) {
        ResponseUtils result = new ResponseUtils();
        result.setData("");
        try {
            proProjectService.insertProUser(proUser);
            result.setCode(ProjectDictionary.PROJECT_SUCCESS);
            result.setMessage("添加项目人员成功");
        }catch (ErrorProjectMessageException e){
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 删除一个项目人员
     * @param proUser
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/deleteProUser"}, method = RequestMethod.DELETE)
    public ResponseUtils deleteProUser(@RequestBody ProUser proUser) {
        ResponseUtils result = new ResponseUtils();
        result.setData("");
        try {
            proProjectService.deleteProUser(proUser);
            result.setCode(ProjectDictionary.PROJECT_SUCCESS);
            result.setMessage("移除项目人员成功");
        }catch (ErrorProjectMessageException e){
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 查看一条项目记录
     * @param projectId
     * @return
     */
    @RequestMapping(value = {"/selectOneProject"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseUtils findOneProject(@RequestParam("projectId") String projectId) {
        ResponseUtils result = new ResponseUtils();
        try {
            result.setData(proProjectService.findOneProject(projectId));
            result.setCode(ProjectDictionary.PROJECT_SUCCESS);
            result.setMessage("查询一条项目信息成功");
        } catch (ErrorProjectMessageException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            result.setData("");
        }
        return result;
    }

    /**
     * 按照项目编码查询项目
     * @param projectCode
     * @return
     */
    @RequestMapping(value = {"/checkProjectCode"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseUtils findProjectByCode(@RequestParam("projectCode") String projectCode) {
        ResponseUtils result = new ResponseUtils();
        try {
            result.setData(proProjectService.checkProjectCode(projectCode));
            result.setCode(ProjectDictionary.PROJECT_SUCCESS);
            result.setMessage("按照项目编码查询项目成功");
        }catch (ErrorProjectMessageException e) {
            result.setData("");
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
        }
        return result;
    }


    /**
     * 查询当前用户参与项目
     * @param params
     * @return
     */
    @RequestMapping(value = {"/allProProject"}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseUtils findAllProProject(@RequestBody Map<String,Object> params) {
        ResponseUtils result = new ResponseUtils();
        try {
            BasePageForm baseForm = new BasePageForm();
            baseForm.setLength(params.get("pageSize").toString());
            Integer pageNum = Integer.parseInt((params.get("pageNo").toString())) - 1;
            Integer start = Integer.parseInt(params.get("pageSize").toString()) * pageNum;
            baseForm.setStart(start.toString());
            HashMap<String,Object> paramMap = new HashMap<>();
//            paramMap.put("tenantId", CurrentUserUtils.getInstance().getUser().getTenantId());
            paramMap.put("userId", CurrentUserUtils.getInstance().getUser().getLoginId());

            result.setData(proProjectService.findAllProject(baseForm, paramMap));
            result.setCode(ProjectDictionary.PROJECT_SUCCESS);
            result.setMessage("查询用户参与项目成功");
        } catch (ErrorProjectMessageException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            result.setData("");
        }
        return result;
    }

    /**
     * 查询当前用户参与项目(开发中、上线项目)
     * @param params
     * @return
     */
    @RequestMapping(value = {"/activeProProject"}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseUtils findActiveProProject(@RequestBody Map<String,Object> params) {
        ResponseUtils result = new ResponseUtils();
        try {
            BasePageForm baseForm = new BasePageForm();
            baseForm.setLength(params.get("pageSize").toString());
            Integer pageNum = Integer.parseInt((params.get("pageNo").toString())) - 1;
            Integer start = Integer.parseInt(params.get("pageSize").toString()) * pageNum;
            baseForm.setStart(start.toString());
            HashMap<String,Object> paramMap = new HashMap<>();
//            paramMap.put("tenantId", CurrentUserUtils.getInstance().getUser().getTenantId());
            paramMap.put("userId", CurrentUserUtils.getInstance().getUser().getLoginId());

            result.setData(proProjectService.findActiveProProject(baseForm, paramMap));
            result.setCode(ProjectDictionary.PROJECT_SUCCESS);
            result.setMessage("查询用户参与活跃项目成功");
        } catch (ErrorProjectMessageException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            result.setData("");
        }
        return result;
    }

    /**
     * 条件查询项目，支持项目名称|项目编码(当前租户下)
     * @param params
     * @return
     */
    @RequestMapping(value = {"/selectProjectByCondition"}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseUtils selectProjectByCondition(@RequestBody Map<String, Object> params) {
        ResponseUtils result = new ResponseUtils();
        try {
            BasePageForm baseForm = new BasePageForm();
            baseForm.setLength(params.get("pageSize").toString());
            Integer pageNum = Integer.parseInt((params.get("pageNo").toString())) - 1;
            Integer start = Integer.parseInt(params.get("pageSize").toString()) * pageNum;
            baseForm.setStart(start.toString());
            HashMap<String,Object> paramMap = new HashMap<>();
            paramMap.put("userId", CurrentUserUtils.getInstance().getUser().getLoginId());
            paramMap.put("searchKey", params.get("searchKey"));

            result.setData(proProjectService.selectProjectByCondition(baseForm, paramMap));
            result.setCode(ProjectDictionary.PROJECT_SUCCESS);
            result.setMessage("条件查询项目成功");
        } catch (ErrorProjectMessageException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            result.setData("");
        }
        return result;
    }


    /**
     * 条件查询项目，支持项目名称|项目编码(当前租户下；开发中、上线)
     * @param params
     * @return
     */
    @RequestMapping(value = {"/selectActiveProjectByCondition"}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseUtils selectActiveProjectByCondition(@RequestBody Map<String, Object> params) {
        ResponseUtils result = new ResponseUtils();
        try {
            BasePageForm baseForm = new BasePageForm();
            baseForm.setLength(params.get("pageSize").toString());
            Integer pageNum = Integer.parseInt((params.get("pageNo").toString())) - 1;
            Integer start = Integer.parseInt(params.get("pageSize").toString()) * pageNum;
            baseForm.setStart(start.toString());
            HashMap<String,Object> paramMap = new HashMap<>();
//            paramMap.put("tenantId", CurrentUserUtils.getInstance().getUser().getTenantId());
            paramMap.put("userId", CurrentUserUtils.getInstance().getUser().getLoginId());
            paramMap.put("searchKey", params.get("searchKey"));

            result.setData(proProjectService.selectActiveProjectByCondition(baseForm, paramMap));
            result.setCode(ProjectDictionary.PROJECT_SUCCESS);
            result.setMessage("条件查询活跃项目成功");
        } catch (ErrorProjectMessageException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            result.setData("");
        }
        return result;
    }


    /**
     * 新建项目
     * @param project
     * @return
     */
    @RequestMapping(value = {"/newProProject"}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseUtils insertProProject(@RequestBody ProProject project) {
        ResponseUtils result = new ResponseUtils();
        result.setData("");
        try {
            proProjectService.insertProject(project);
            result.setCode(ProjectDictionary.PROJECT_SUCCESS);
            result.setMessage("新增项目成功");
        }catch (ErrorProjectMessageException e){
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
        }
        return result;
    }


    /**
     * 更新项目
     * @param project
     * @return
     */
    @RequestMapping(value = {"/updateProProject"}, method = RequestMethod.PUT)
    @ResponseBody
    public ResponseUtils updateProject(@RequestBody ProProject project) {
        ResponseUtils result = new ResponseUtils();
        result.setData("");
        try{
            proProjectService.updateProject(project);
            result.setCode(ProjectDictionary.PROJECT_SUCCESS);
            result.setMessage("更新项目成功");
        }catch (ErrorProjectMessageException e){
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 删除项目
     * @param project
     * @return
     */
    @RequestMapping(value = {"/deleteProject"}, method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseUtils deleteProject(@RequestBody ProProject project) {
        ResponseUtils result = new ResponseUtils();
        result.setData("");
        try {
            proProjectService.delFlagProject(project);
            result.setCode(ProjectDictionary.PROJECT_SUCCESS);
            result.setMessage("删除项目成功");
        }catch (ErrorProjectMessageException e){
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     *  获取登陆用户的某个项目的角色
     * @param proUser
     * @return
     */
    @RequestMapping(value = {"/getUserRole"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseUtils getUserRole(ProUser proUser) {
        ResponseUtils result = new ResponseUtils();
        try {
            proUser.userId = CurrentUserUtils.getInstance().getUser().getLoginId();
            result.setData(proProjectService.getUserRole(proUser).getProjectRoleId());
            result.setCode(ProjectDictionary.PROJECT_SUCCESS);
            result.setMessage("获取用户项目角色成功");
        }catch (ErrorProjectMessageException e){
            result.setMessage(e.getMessage());
            result.setCode(e.getCode());
            result.setData("");
        }
        return result;
    }


    /**
     * 得到存在当前项目id
     * @return
     */
    @RequestMapping(value = {"/projectId"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getProjectId(){
        return new ResponseEntity<>(CurrentUserUtils.getInstance().getProjectId(), HttpStatus.OK);
    }
}
