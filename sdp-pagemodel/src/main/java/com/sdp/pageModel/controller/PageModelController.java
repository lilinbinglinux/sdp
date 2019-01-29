package com.sdp.pageModel.controller;

import java.util.List;
import java.util.Map;

import com.sdp.common.CurrentUserUtils;
import com.sdp.common.entity.BasePageForm;
import com.sdp.common.entity.Status;
import com.sdp.common.service.BaseUserInfoMapService;
import com.sdp.pageModel.entity.PageModel;
import com.sdp.pageModel.service.PageModelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import net.sf.json.JSONObject;


/**
 * 
* @Description: 页面模版控制层
  @ClassName: PageModelController
* @author zy
* @date 2018年4月16日
* @company:www.bonc.com.cn
 */
@Controller
@RequestMapping("v1/pageModel")
public class PageModelController {
	
	@Autowired
	private PageModelService pageModelService;
	
	/**
	 * @desc 根据pageId返回页面信息到页面组装界面
	 * @param model
	 * @param pageId
	 * @return
	 */
	@RequestMapping(value = { "/pageVisualize/{pageId}" }, method = RequestMethod.GET)
	public String pageVisualize(Model model, @PathVariable("pageId") String pageId) {PageModel pageModel = pageModelService.findByPrimaryKey(pageId);
 		if (pageModel != null) {
 			pageModel.setLoginId(CurrentUserUtils.getInstance().getUser().getLoginId());
 			pageModel.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
 			pageModel.setProjectId(CurrentUserUtils.getInstance().getProjectId());
 
 		}
 		JSONObject pageModelObj = JSONObject.fromObject(pageModel);
 		model.addAttribute("pageModel", pageModelObj.toString());
	
 		return "pageModel/pageVisualize";}

	/**
	 * @desc 根据mode返回组装后的页面模板
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = { "/pageTemplate/{mode}" }, method = RequestMethod.GET)
	public String pageTemplate(Model model, @PathVariable("mode") String mode) {
		PageModel pageModel = new PageModel();
		if (mode.equals("pc")) {
			mode = "pageModel/pageComputer";
		} else if (mode.equals("mobile")){
			mode = "pageModel/pageMobile";
		} else {
			pageModel = pageModelService.findByPrimaryKey(mode);
			mode = "pageModel/pageTemplate";
		}
		pageModel.setLoginId(CurrentUserUtils.getInstance().getUser().getLoginId());
		pageModel.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
		pageModel.setProjectId(CurrentUserUtils.getInstance().getProjectId());
		JSONObject pageModelObj = JSONObject.fromObject(pageModel);
		model.addAttribute("pageModel", pageModelObj);
		return mode;
	}

	/**
	 * 
	* 获取指定类型节点下的所有页面模版(包括子类型中的所有页面模版)
	* @param @param pageTypeId
	* @param @return    参数
	* @return ResponseEntity<String>    返回类型
	 */
	@RequestMapping(value = "/subChildDetail", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<PageModel>> findChildDataByPageTypeId(Map<String,Object> paramMap){
		List<PageModel> pageModels = pageModelService.findByCondition(paramMap);
		return new ResponseEntity<List<PageModel>>(pageModels,HttpStatus.OK);
	}
	
	/**
	 * 
	* (分页)根据pageTypeId获取指定类型节点下的所有页面模版（不包括子节点下的页面模版）
	* @param @param pageTypeId
	* @param @return    参数
	* @return ResponseEntity<String>    返回类型
	 */
	@RequestMapping(value = "/childDetail/{pageTypeId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> findPageModelByPageTypeId(BasePageForm baseForm, @PathVariable("pageTypeId") String pageTypeId, String selectKey){
		Map<String,Object> paramMap = BaseUserInfoMapService.baseUserInfoMap();
		paramMap.put("pageTypeId", pageTypeId);
		paramMap.put("pageName", selectKey);
		Map<String, Object> pageModelMap = pageModelService.selectPage(baseForm,paramMap);
		return new ResponseEntity<Map<String, Object>>(pageModelMap,HttpStatus.OK);
	}
	
	
	/**
	 * 
	* 根据pageId查询页面类型详细信息
	* @param @param pageId
	* @param @return    参数
	* @return ResponseEntity<PageModel>    返回类型
	 */
	@RequestMapping(value = "/detail/{pageId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<PageModel> findPageModelByPageId(@PathVariable("pageId") String pageId){
		PageModel pageModel = pageModelService.findByPrimaryKey(pageId);
		return new ResponseEntity<PageModel>(pageModel,HttpStatus.OK);
	}
	
	/**
     * 新增页面模版
     */
    @RequestMapping(value = {"/savePageModel"}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Status> savePageModel(PageModel pageModel){
        Status status = pageModelService.savePageModel(pageModel);
        return new ResponseEntity<Status>(status, HttpStatus.OK);
    }

	/**
	 * 复制页面模版
	 */
	@RequestMapping(value = {"/copyPageModel"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Status> copyPageModel(PageModel pageModel){
		PageModel pageModelCopy = pageModelService.findByPrimaryKey(pageModel.getPageId());
		pageModel.setPageId("");
		pageModel.setPageJs(pageModelCopy.getPageJs());
		pageModel.setPageStyle(pageModelCopy.getPageStyle());
		pageModel.setPageText(pageModelCopy.getPageText());
		pageModel.setPagePureText(pageModelCopy.getPagePureText());
		pageModel.setPageTypeId(pageModelCopy.getPageTypeId());
		Status status = pageModelService.savePageModel(pageModel);
		return new ResponseEntity<Status>(status, HttpStatus.OK);
	}

    /**
     * 修改页面模版数据
     */
    @RequestMapping(value = {"/{pageId}"}, method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Status> updatePageModelByPageId(@PathVariable("pageId") String pageId, PageModel mageModel){
        Status status = pageModelService.updatePageModelByPageId(mageModel);
        return new ResponseEntity<Status>(status, HttpStatus.OK);
    }
	
    /**
     * 
    * 根据pageId删除页面模版
    * @param @param pageId
    * @param @return    参数
    * @return ResponseEntity<Status>    返回类型
     */
    @RequestMapping(value = {"/{pageId}"}, method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Status> deletePageModelByPageId(@PathVariable("pageId") String pageId){
        Status status = pageModelService.deletePageModelByPageId(pageId);
        return new ResponseEntity<Status>(status, HttpStatus.OK);
    }
	

}
