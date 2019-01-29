package com.sdp.pageModel.controller;

import java.util.Map;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.entity.BasePageForm;
import com.sdp.common.entity.Status;
import com.sdp.common.service.BaseUserInfoMapService;
import com.sdp.pageModel.entity.PageModule;
import com.sdp.pageModel.service.PageModuleService;

/**
 * @description:
 * @author: zhoutao
 * @version: 10:51 2018/4/18
 * @see:
 * @since:
 * @modified by:
 */
@Controller
@RequestMapping("/v1/pageModule")
public class PageModuleController {

    /**
     * PageModuleService
     */
    @Autowired
    private PageModuleService pageModuleService;


    /**
     *
     * 跳转页面：新增组件 页面
     * @param @return    参数
     * @return String    返回类型
     */
    @RequestMapping(value = "/addModule/{moduleTypeId}", method = RequestMethod.GET)
    public String addModule(Model model, @PathVariable("moduleTypeId") String moduleTypeId) {
        model.addAttribute("moduleTypeId", moduleTypeId);
        return "pageModel/addModule";
    }

    /**
     *
     * 跳转页面：修改组件 页面
     * @param @return    参数
     * @return String    返回类型
     */
    @RequestMapping(value = "/editModule/{moduleId}", method = RequestMethod.GET)
    public String editModule(Model model, @PathVariable("moduleId") String moduleId) {
        model.addAttribute("moduleId", moduleId);
        return "pageModel/editModule";
    }

    /**
     *
     * 跳转页面：新增组件预览 页面
     * @param @return    参数
     * @return String    返回类型
     */
    @RequestMapping(value = {"/moduleShow"}, method = RequestMethod.GET)
    public String moduleShow(Model model, @RequestParam(required = false) String moduleId){
        if (moduleId != ""){
            model.addAttribute("moduleId", moduleId);
        }
        return "pageModel/moduleShow";
    }


    /**
     * 查询组件类型数据详细信息
     * 查询当前组件类型所属的子集菜单，查询所属当前组件类型的组件
     * @param moduleTypeId moduleTypeId
     */
    @RequestMapping(value = {"/module/type/detail/{moduleTypeId}"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> findPageMuduleTypeDetailInfo(@PathVariable("moduleTypeId") String moduleTypeId){
        Map<String, Object> pageResult = pageModuleService.findPageMuduleTypeDetailInfo(moduleTypeId);
        return new ResponseEntity<Map<String,Object>>(pageResult, HttpStatus.OK);
    }

    /**
     * 分页查询当前组件类型下所属组件数据
     * @param baseForm baseForm
     * @param moduleTypeId moduleTypeId
     * @return PagingResult
     */
    @RequestMapping(value = {"/module/type/moduleinfo/{moduleTypeId}/{start}/{length}"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> findPageMuduleInfoing(BasePageForm baseForm, @PathVariable("moduleTypeId") String moduleTypeId, String selectKey){
    	Map<String,Object> paramMap = BaseUserInfoMapService.baseUserInfoMap();
    	paramMap.put("moduleTypeId", moduleTypeId);
    	paramMap.put("moduleName", selectKey);
    	Map<String,Object> pageModelMap = pageModuleService.selectPage(baseForm,paramMap);
        return new ResponseEntity<Map<String,Object>>(pageModelMap, HttpStatus.OK);
    }


    /**
     * 新增组件数据
     * @param moduleTypeId moduleTypeId
     * @param pageModule pageModule
     */
    @RequestMapping(value = {"/module/{moduleTypeId}"}, method = RequestMethod.POST)
    @ResponseBody
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<Status> savePageModuleInfo(@PathVariable("moduleTypeId") String moduleTypeId, PageModule pageModule){
        Status status = pageModuleService.savePageModuleInfo(moduleTypeId, pageModule);
        return new ResponseEntity<Status>(status, HttpStatus.OK);
    }

    /**
     * 删除组件数据
     * @param moduleId moduleId
     */
    @RequestMapping(value = {"/module/delete/{moduleId}"}, method = RequestMethod.DELETE)
    @ResponseBody
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<Status> deletePageModuleInfo(@PathVariable("moduleId") String moduleId){
        Status status = pageModuleService.deletePageModuleInfo(moduleId);
        return new ResponseEntity<Status>(status, HttpStatus.OK);
    }

    /**
     * 修改组件数据
     * @param moduleId moduleId
     * @param pageModule pageModule
     */
    @RequestMapping(value = {"/module/update/{moduleId}"}, method = RequestMethod.PUT)
    @ResponseBody
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<Status> updatePageModuleInfo(@PathVariable("moduleId") String moduleId, PageModule pageModule){
        Status status = pageModuleService.updatePageModuleInfo(moduleId, pageModule);
        return new ResponseEntity<Status>(status, HttpStatus.OK);
    }

    /**
     * 查询组件数据
     * @param moduleId moduleId
     */
    @RequestMapping(value = {"/module/{moduleId}"}, method = RequestMethod.GET)
    @ResponseBody
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<PageModule> findPageModuleInfo(@PathVariable("moduleId") String moduleId){
        PageModule pageResult = pageModuleService.findPageModuleInfo(moduleId);
        return new ResponseEntity<PageModule>(pageResult, HttpStatus.OK);
    }

}
