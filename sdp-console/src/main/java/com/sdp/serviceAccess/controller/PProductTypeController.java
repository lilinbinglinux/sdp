/**
 * 
 */
package com.sdp.serviceAccess.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.entity.Status;
import com.sdp.common.page.Pagination;
import com.sdp.serviceAccess.entity.PProductType;
import com.sdp.serviceAccess.service.IPProductTypeService;
import com.sdp.serviceAccess.util.JsonXMLUtils;

/**   
 * Copyright: Copyright (c) 2018 BONC
 * 
 * @ClassName: PProductTypeController.java
 * @Description: 服务类型控制类
 *
 * @version: v1.0.0
 * @author: renpengyuan
 * @date: 2018年8月2日 下午4:31:26 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年8月2日     renpengyuan      v1.0.0               修改原因
 */
@Controller
@RequestMapping("/productType")
public class PProductTypeController {

	private static final Logger LOG = LoggerFactory.getLogger(PProductTypeController.class);

	@Autowired
	private IPProductTypeService proTypeService;

	@RequestMapping(value={"/index"}, method = RequestMethod.GET)
	public String index(){
		return "product/productType";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/categoryInfos"},method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Pagination> categoryInfos(@RequestBody Map<String,Object> param) throws Exception{
		ResponseEntity<Pagination> result;
		try{
			Pagination page = JsonXMLUtils.map2obj((Map<String, Object>)param.get("page"),Pagination.class); 
			PProductType productType = JsonXMLUtils.map2obj((Map<String, Object>)param.get("productType"),PProductType.class); 
			proTypeService.categoryInfos(page, productType);
			result = new ResponseEntity<Pagination>(page, HttpStatus.OK);
		}catch(Exception e){
			LOG.error("categoryInfos error",e);
			throw e;
		}
		return result;
	}

	@RequestMapping(value={"/createCategory"},method= RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Status> createCategory(@RequestBody PProductType productType){
		Status status;
		try{
			status= proTypeService.createCategory(productType);
		}catch(Exception e){
			LOG.error("createCategory error",e);
			throw e;
		}
		return new ResponseEntity<Status>(status, HttpStatus.OK);
	}

	@RequestMapping(value={"/modifyCategory"},method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Status> modifyCategory(@RequestBody PProductType productType){
		Status status;
		try{
			status= proTypeService.updateCategory(productType);
		}catch(Exception e){
			LOG.error("modifyCategory error",e);
			throw e;
		}
		return new ResponseEntity<Status>(status, HttpStatus.OK);
	}

	@RequestMapping(value={"/deleteCategory"},method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Status> deleteCategory(@RequestBody PProductType productType){
		Status status;
		try{
			status= proTypeService.deleteCategory(productType);
		}catch(Exception e){
			LOG.error("deleteCategory error",e);
			throw e;
		}
		return new ResponseEntity<Status>(status, HttpStatus.OK);
	}

	@RequestMapping(value={"/allCategory"},method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<?>> allCategory(){
		ResponseEntity<List<?>> result;
		try{
			result = new ResponseEntity<List<?>>(proTypeService.allCates(),HttpStatus.OK);
		}catch(Exception e){
			LOG.error("find cates error",e);
			throw e;
		}
		return result;

	}

	@RequestMapping(value={"/categoryInfos/{typeId}"},method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<PProductType> singleCate(@PathVariable("typeId") String typeId){
		ResponseEntity<PProductType> result;
		try{
			result = new ResponseEntity<PProductType>(proTypeService.singleCate(typeId), HttpStatus.OK);
		}catch(Exception e){
			LOG.error("category single find error",e);
			throw e;
		}
		return result;
	}

	@RequestMapping(value={"/categoryCodeVer/{typeCode}/{typeId}"},method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map<String,Object>> verfyTypeCode(@PathVariable("typeCode") String typeCode,@PathVariable("typeId")String typeId){
		ResponseEntity<Map<String,Object>> result;

		try{
			Map<String,Object> flag = new HashMap<String,Object>();
			flag.put("result", proTypeService.verfyCateCode(typeCode,typeId));
			result = new ResponseEntity<Map<String,Object>>(flag, HttpStatus.OK);
		}catch(Exception e){
			LOG.error("verfy code errror",e);
			throw e;
		}
		return result;
	}
	
	@RequestMapping(value= {"/cagegory/verfy/{typeName}/{typeId}"},method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map<String,Object>> verfyTypeName(@PathVariable("typeName") String typeName,@PathVariable("typeId")String typeId){
		ResponseEntity<Map<String,Object>> result;

		try{
			Map<String,Object> flag = new HashMap<String,Object>();
			flag.put("result", proTypeService.verfyCateName(typeName,typeId));
			result = new ResponseEntity<Map<String,Object>>(flag, HttpStatus.OK);
		}catch(Exception e){
			LOG.error("verfy name errror",e);
			throw e;
		}
		return result;
	}

}
