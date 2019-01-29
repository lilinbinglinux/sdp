/**  

* <p>Description: </p>  

* <p>Company: bonc.com</p>  

* @author zy 

* @date 2018年6月6日  

*/  
package com.sdp.pageModel.controller;

import java.util.Map;

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
import com.sdp.pageModel.entity.PageResType;
import com.sdp.pageModel.service.PageResTypeService;

/**  

* <p>Description: </p>  

* <p>Company: bonc.com</p>  

* @author zy 

* @date 2018年6月6日  

*/
@Controller
@RequestMapping("/v1/pageResType")
public class PageResTypeController {
	
	@Autowired
	private PageResTypeService pageResTypeService;
	
	/**
    *
    * 跳转页面：资源类型 页面
    * @param @return    参数
    * @return String    返回类型
    */
   @RequestMapping(value = "/resClassify", method = RequestMethod.GET)
   @Produces(MediaType.TEXT_PLAIN)
   public String pageTypes(HttpServletResponse response,HttpServletRequest request){
       return "pageModel/resourceClassification";
   }
   

   /**
    * 新增资源类型数据
    * @param pageResType pageResType
    */
   @RequestMapping(value = {"/restype"}, method = RequestMethod.POST)
   @ResponseBody
   @Produces(MediaType.APPLICATION_JSON)
   public ResponseEntity<Status> savePageResTypeInfo(PageResType pageResType){
       Status status = pageResTypeService.savePageResTypeInfo(pageResType);
       return new ResponseEntity<>(status, HttpStatus.OK);
   }
   
   /**
    * 删除资源类型
    * @param resTypeId resTypeId
    */
   @RequestMapping(value = {"/restype/delete/{resTypeId}"}, method = RequestMethod.DELETE)
   @ResponseBody
   @Produces(MediaType.APPLICATION_JSON)
   public ResponseEntity<Status> deletePageResTypeInfo(@PathVariable("resTypeId") String resTypeId){
       Status status = pageResTypeService.deletePageResTypeInfo(resTypeId);
       return new ResponseEntity<Status>(status, HttpStatus.OK);
   }
   
   /**
    * 修改资源类型数据
    * @param pageResType pageResType
    */
   @RequestMapping(value = {"/restype/update/{resTypeId}"}, method = RequestMethod.PUT)
   @ResponseBody
   @Produces(MediaType.APPLICATION_JSON)
   public ResponseEntity<Status> updatePageResTypeInfo(@PathVariable("resTypeId") String resTypeId, PageResType pageResType){
       Status status = pageResTypeService.updatePageResTypeInfo(resTypeId, pageResType);
       return new ResponseEntity<Status>(status, HttpStatus.OK);
   }
   
   /**
    * 查询资源类型数据详细信息
    * 查询当前资源类型所属的子集菜单，查询所属当前资源类型的资源
    * @param resTypeId resTypeId
    */
   @RequestMapping(value = {"/restype/detail/{resTypeId}"}, method = RequestMethod.GET)
   @ResponseBody
   @Produces(MediaType.APPLICATION_JSON)
   public ResponseEntity<Map<String, Object>> findPageResTypeDetailInfo(@PathVariable("resTypeId") String resTypeId){
       Map<String, Object> pageResult = pageResTypeService.findPageResTypeDetailInfo(resTypeId);
       return new ResponseEntity<Map<String, Object>>(pageResult, HttpStatus.OK);
   }
   
   /**
    * 查询当前资源类型数据
    * @param resTypeId
    * @return
    */
   @RequestMapping(value = {"/restype/{resTypeId}"}, method = RequestMethod.GET)
   @ResponseBody
   @Produces(MediaType.APPLICATION_JSON)
   public ResponseEntity<PageResType> findPageResTypeInfo(@PathVariable("resTypeId") String resTypeId){
       PageResType pageResult = pageResTypeService.findByPrimaryKey(resTypeId);
       return new ResponseEntity<PageResType>(pageResult, HttpStatus.OK);
   }


}
