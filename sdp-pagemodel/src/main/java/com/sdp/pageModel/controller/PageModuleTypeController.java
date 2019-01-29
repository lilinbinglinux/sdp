/**  

* <p>Description: </p>  

* <p>Company: bonc.com</p>  

* @author zy 

* @date 2018年6月6日  

*/  
package com.sdp.pageModel.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.entity.Status;
import com.sdp.pageModel.entity.PageModuleType;
import com.sdp.pageModel.service.PageModuleTypeService;

/**  

* <p>Description: </p>  

* <p>Company: bonc.com</p>  

* @author zy 

* @date 2018年6月6日  

*/
@Controller
@RequestMapping("/v1/pageModuleType")
public class PageModuleTypeController {
	
	@Autowired
	private PageModuleTypeService pageModuleTypeService;
	
	/**
    *
    * 跳转页面：组件分类 页面
    * @param @return    参数
    * @return String    返回类型
    */
   @RequestMapping(value = "/moduleClassify", method = RequestMethod.GET)
   @Produces(MediaType.TEXT_PLAIN)
   public String moduleClassify(HttpServletResponse response, HttpServletRequest request){
       return "pageModel/componentsPicker";
   }
   
   /**
    * 新增组件类型数据
    * @param pageModuleType pageModuleType
    */
   @RequestMapping(value = {"/module/type"}, method = RequestMethod.POST)
   @ResponseBody
   @Produces(MediaType.APPLICATION_JSON)
   public ResponseEntity<Status> savePageModuleTypeInfo(PageModuleType pageModuleType){
       Status status = pageModuleTypeService.savePageModuleTypeInfo(pageModuleType);
       return new ResponseEntity<Status>(status, HttpStatus.OK);
   }

   /**
    * 删除组件类型
    * @param moduleTypeId moduleTypeId
    */
   @RequestMapping(value = {"/module/type/delete/{moduleTypeId}"}, method = RequestMethod.DELETE)
   @ResponseBody
   @Produces(MediaType.APPLICATION_JSON)
   public ResponseEntity<Status> deletePageModuleTypeInfo(@PathVariable("moduleTypeId") String moduleTypeId){
       Status status = pageModuleTypeService.deletePageModuleTypeInfo(moduleTypeId);
       return new ResponseEntity<Status>(status, HttpStatus.OK);
   }

   /**
    * 修改组件类型数据
    * @param pageModuleType pageModuleType
    */
   @RequestMapping(value = {"/module/type/update/{moduleTypeId}"}, method = RequestMethod.PUT)
   @ResponseBody
   @Produces(MediaType.APPLICATION_JSON)
   public ResponseEntity<Status> updatePageModuleTypeInfo(@PathVariable("moduleTypeId") String moduleTypeId, PageModuleType pageModuleType){
       Status status = pageModuleTypeService.updatePageModuleTypeInfo(moduleTypeId, pageModuleType);
       return new ResponseEntity<Status>(status, HttpStatus.OK);
   }
   
   /**
    * 查询当前资源类型数据
    * @param moduleTypeId moduleTypeId
    * @return
    */
   @RequestMapping(value = {"/module/type/{moduleTypeId}"}, method = RequestMethod.GET)
   @ResponseBody
   @Produces(MediaType.APPLICATION_JSON)
   public ResponseEntity<PageModuleType> findPageModuleTypeInfo(@PathVariable("moduleTypeId") String moduleTypeId){
       PageModuleType pageResult = pageModuleTypeService.findByPrimaryKey(moduleTypeId);
       return new ResponseEntity<PageModuleType>(pageResult, HttpStatus.OK);
   }


}
