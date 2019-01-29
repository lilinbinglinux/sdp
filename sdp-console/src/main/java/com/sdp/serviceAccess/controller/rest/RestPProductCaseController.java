/**
 * 
 */
package com.sdp.serviceAccess.controller.rest;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sdp.common.CurrentUserUtils;
import com.sdp.common.entity.Status;
import com.sdp.common.page.Pagination;
import com.sdp.servflow.pubandorder.apiemploy.apiversion.ApiVersion;
import com.sdp.serviceAccess.entity.PProductCase;
import com.sdp.serviceAccess.service.IPProductCaseService;
import com.sdp.serviceAccess.service.IProductOperateCompontent;
import com.sdp.serviceAccess.util.JsonXMLUtils;

/**   
 * Copyright: Copyright (c) 2018 BONC
 * 
 * @ClassName: PProductCaseController.java
 * @Description: 实例控制类
 *
 * @version: v1.0.0
 * @author: renpengyuan
 * @date: 2018年8月2日 下午4:35:52 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年8月2日     renpengyuan      v1.0.0               修改原因
 */
@RestController
@RequestMapping("/api/productCase")
public class RestPProductCaseController {

	@Autowired
	private IProductOperateCompontent operateComponent;

	@Autowired
	private IPProductCaseService caseService;

	@SuppressWarnings("unchecked")
	@Deprecated
	@RequestMapping(value={"/caseInfoByProduct"},method=RequestMethod.POST)
	public ResponseEntity<Pagination> caseInfoByProduct(@RequestBody Map<String,Object> params) throws Exception{
		try{
			Pagination page = JsonXMLUtils.map2obj((Map<String, Object>)params.get("page"),Pagination.class); 
//	    	String productId = params.get("productId").toString() ;
//	    	String dataAuth = params.get("dataAuth").toString();
           return new ResponseEntity<Pagination>(caseService.findByProduct(params,page,false),HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
		   throw e;
		}
	}
	
	@RequestMapping(value= {"/caseInfoByProduct"},method=RequestMethod.POST)
	@ApiVersion(1)
	public ResponseEntity<Pagination>  caseInfoByProduct1(@RequestBody Map<String,Object> params) throws Exception {
		try{
			Pagination page = JsonXMLUtils.map2obj((Map<String, Object>)params.get("page"),Pagination.class); 
//	    	String productId = params.get("productId").toString() ;
//	    	String dataAuth = params.get("dataAuth").toString();
           return new ResponseEntity<Pagination>(caseService.findByProduct(params, page,true),HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
		   throw e;
		}
	}
	
	@RequestMapping(value={"/updateCase"},method=RequestMethod.POST)
	public ResponseEntity<Status> updateCase(@RequestBody PProductCase productCase) throws Exception{
		try{
			boolean isNotStartOrStop = true;
			if(StringUtils.isBlank(productCase.getOperaType())||(!"10".equals(productCase.getOperaType())&&!"20".equals(productCase.getOperaType()))) {
				isNotStartOrStop = false;
			}
			if(isNotStartOrStop) {
				return new ResponseEntity<Status>(operateComponent.changeResource(productCase),HttpStatus.OK);
			}else {
				return new ResponseEntity<Status>(operateComponent.startOrStop(productCase),HttpStatus.OK);
			}
		}catch(Exception e){
			e.printStackTrace();
		   throw e;
		}
	}
	
	@RequestMapping(value={"/singleCase/{caseId}"},method=RequestMethod.GET)
	public ResponseEntity<PProductCase> singleCase(@PathVariable("caseId") String caseId) throws Exception{
		try{
			PProductCase productCase= new PProductCase();
			productCase.setCaseId(caseId);
			return new ResponseEntity<PProductCase>(caseService.singleCase(productCase),HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
		   throw e;
		}
	}
	
	@RequestMapping(value = { "/delete" }, method = RequestMethod.DELETE)
	public ResponseEntity<Status> deleteCase(@RequestBody PProductCase productCase) throws Exception {
		try {
			return new ResponseEntity<Status>(caseService.deleteCase(productCase), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/findCasePageWithAuth" }, method = RequestMethod.POST)
	public ResponseEntity<Pagination> auth_findCasePage(@RequestBody Map<String, Object> params) throws Exception {
		try {
			Pagination page = JsonXMLUtils.map2obj((Map<String, Object>) params.get("page"), Pagination.class);
			PProductCase productCase = JsonXMLUtils.map2obj((Map<String, Object>) params.get("productCase"),
					PProductCase.class);
			return new ResponseEntity<Pagination>(caseService.findCasePageWithAuth(productCase, page),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/findCaseCtnWithAuth" }, method = RequestMethod.POST)
	public ResponseEntity<Integer> auth_findCaseCtn(@RequestBody Map<String, Object> params) throws Exception {
		try {
			PProductCase productCase = JsonXMLUtils.map2obj((Map<String, Object>) params.get("productCase"),
					PProductCase.class);
			return new ResponseEntity<Integer>(caseService.finCaseCtnWithAuth(productCase),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
