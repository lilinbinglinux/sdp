/**
 * 
 */
package com.sdp.serviceAccess.controller.rest;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sdp.common.page.Pagination;
import com.sdp.servflow.pubandorder.apiemploy.apiversion.ApiVersion;
import com.sdp.serviceAccess.entity.PProductCase;
import com.sdp.serviceAccess.service.IPProductNodeService;
import com.sdp.serviceAccess.util.JsonXMLUtils;

/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: PProductNodeController.java
* @Description: 该类的功能描述
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年8月2日 下午4:36:34 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月2日     renpengyuan      v1.0.0               修改原因
*/
@RestController
@RequestMapping("/api/productNode")
public class RestPProductNodeController {
	
	private static final Logger LOG = LoggerFactory.getLogger(RestPProductNodeController.class);
	
	@Autowired
	private IPProductNodeService nodeService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/nodesByCase"},method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String,Object>> nodesByCase(@RequestBody Map<String,Object> param) throws Exception{
		try{
			Pagination page = JsonXMLUtils.map2obj((Map<String, Object>)param.get("page"),Pagination.class); 
	    	PProductCase productCase = JsonXMLUtils.map2obj((Map<String, Object>)param.get("productCase"),PProductCase.class); 
			return new ResponseEntity<Map<String,Object>>(nodeService.caseByNodes(productCase, page,false),HttpStatus.OK);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/nodesByCase"},method=RequestMethod.POST)
	@ApiVersion(1)
	@ResponseBody
	public ResponseEntity<Map<String,Object>> nodesByCase1(@RequestBody Map<String,Object> param) throws Exception{
		try{
			Pagination page = JsonXMLUtils.map2obj((Map<String, Object>)param.get("page"),Pagination.class); 
	    	PProductCase productCase = JsonXMLUtils.map2obj((Map<String, Object>)param.get("productCase"),PProductCase.class); 
			return new ResponseEntity<Map<String,Object>>(nodeService.caseByNodes(productCase, page,true),HttpStatus.OK);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw e;
		}
	}

}
