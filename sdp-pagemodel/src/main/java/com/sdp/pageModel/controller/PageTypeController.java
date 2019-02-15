package com.sdp.pageModel.controller;

import java.util.Map;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sdp.common.entity.Status;
import com.sdp.pageModel.entity.PageType;
import com.sdp.pageModel.service.PageTypeService;


/**
 * 
* @Description: 页面模版类型控制层
  @ClassName: PageModuleController
* @author zy
* @date 2018年4月16日
* @company:www.sdp.com.cn
 */
@Controller
@RequestMapping("/v1/pageType")
public class PageTypeController {
	
	/**
	 * PageModuleTypeService
	 */
	@Autowired
    private PageTypeService pageTypeService;

	/**
	 * 
	* 跳转页面：页面模版类型页面
	* @param @return    参数
	* @return String    返回类型
	 */
	@RequestMapping(value = "/pageTypes", method = RequestMethod.GET)
	@Produces(MediaType.TEXT_PLAIN)
	public String pageTypes(Model model, @RequestParam(required = false) String pageTypeId){
		pageTypeId = pageTypeId != null? pageTypeId: "root";
		model.addAttribute("pageTypeId", pageTypeId);
		return "pageModel/pagesPicker";
	}

	/**
	 *
	 * 跳转页面：数据库建模页面分类
	 * @param @return    参数
	 * @return String    返回类型
	 */
	@RequestMapping(value = "/databasePagePicker/{pageTypeId}", method = RequestMethod.GET)
	public String databasePagesPicker(Model model, @PathVariable("pageTypeId") String pageTypeId) {
		model.addAttribute("pageTypeId", pageTypeId);
		return "pageModel/databasePagePicker";
	}

	/**
	 * 
	* (树形)获取指定类型节点下的所有页面类型
	* @param @param pageTypeId
	* @param @return    参数
	* @return ResponseEntity<String>    返回类型
	 */
	@RequestMapping(value = "/childDetail/{pageTypeId}", method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> findTreePageTypeByPageParentId(@PathVariable("pageTypeId") String pageTypeId){
		Map<String, Object> treeDatas = pageTypeService.findTreePageTypeByPageParentId(pageTypeId);
		return new ResponseEntity<Map<String, Object>>(treeDatas,HttpStatus.OK);
	}
	
	/**
	 * 
	* 根据pageTypeId查询页面类型详细信息
	* @param @param pageTypeId
	* @param @return    参数
	* @return ResponseEntity<List<PageType>>    返回类型
	 */
	@RequestMapping(value = "/detail/{pageTypeId}", method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<PageType> findPageTypeByPageTypeId(@PathVariable("pageTypeId") String pageTypeId){
		PageType pageType = pageTypeService.findByPrimaryKey(pageTypeId);
		return new ResponseEntity<PageType>(pageType,HttpStatus.OK);
	}
	
    /**
     * 新增页面模版类型
     */
    @RequestMapping(value = {"/savePageType"}, method = RequestMethod.POST)
    @ResponseBody
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<Status> savePageType(PageType pageType){
        Status status = pageTypeService.savePageType(pageType);
        return new ResponseEntity<Status>(status, HttpStatus.OK);
    }
    
    /**
     * 
    * 根据类型id删除页面类型
    * @param @param pageTypeId
    * @param @return    参数
    * @return ResponseEntity<Status>    返回类型
     */
    @RequestMapping(value = {"/{pageTypeId}"}, method = RequestMethod.DELETE)
    @ResponseBody
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<Status> deletePageTypeByPageTypeId(@PathVariable("pageTypeId") String pageTypeId){
        Status status = pageTypeService.deletePageTypeByPageTypeId(pageTypeId);
        return new ResponseEntity<Status>(status, HttpStatus.OK);
    }
    
    /**
     * 修改资源类型数据
     */
    @RequestMapping(value = {"/{pageTypeId}"}, method = RequestMethod.PUT)
    @ResponseBody
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<Status> updatePageTypeByPageTypeId(@PathVariable("pageTypeId") String pageTypeId, PageType pageType){
        Status status = pageTypeService.updatePageTypeByPageTypeId(pageTypeId, pageType);
        return new ResponseEntity<Status>(status, HttpStatus.OK);
    }
	

}
