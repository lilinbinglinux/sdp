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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sdp.common.entity.Status;
import com.sdp.serviceAccess.entity.PProductPackage;
import com.sdp.serviceAccess.service.IPProductPackageService;

/**   
 * Copyright: Copyright (c) 2018 BONC
 * 
 * @ClassName: PProductPackageController.java
 * @Description: 套餐控制类
 *
 * @version: v1.0.0
 * @author: renpengyuan
 * @date: 2018年8月2日 下午4:33:44 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年8月2日     renpengyuan      v1.0.0               修改原因
 */
@RestController
@RequestMapping("/api/productPackage")
public class RestPProductPackageController {
	
	@Autowired
	private IPProductPackageService productPackageService;

	private static final Logger LOG= LoggerFactory.getLogger(RestPProductPackageController.class);

	@RequestMapping(value={"/createPackage"},method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Status> createPackage(@RequestBody PProductPackage productPackage,Model model){
	     try{
	    	 return new ResponseEntity<Status>(productPackageService.createPackage(productPackage),HttpStatus.OK);
	     }catch(Exception e){
	    	 LOG.error(e.getMessage(),e);
	    	 throw e;
	     }
	}

	@RequestMapping(value={"/singlePackage/{packageId}"},method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<PProductPackage> singlePackage(@PathVariable("packageId")String packageId){
		try{
			return new ResponseEntity<PProductPackage>(productPackageService.singlePackageInfo(packageId),HttpStatus.OK);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw e;
		}
	}
    
	@RequestMapping(value={"/singlePackageApi/{packageId}/{tenantId}"},method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<PProductPackage> singlePackage(@PathVariable("packageId")String packageId,@PathVariable("tenantId")String tenantId){
		try{
			return new ResponseEntity<PProductPackage>(productPackageService.singlePackageInfo(packageId,tenantId),HttpStatus.OK);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw e;
		}
	}
	
	
	@RequestMapping(value={"/updatePackage"},method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Status> updatePackage(@RequestBody PProductPackage productPackage){
		try{
			return new ResponseEntity<Status>(productPackageService.updatePackage(productPackage),HttpStatus.OK);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw e;
		}
	}
	
	@RequestMapping(value={"/deletePackage"},method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Status> deletePackage(@RequestBody PProductPackage productPackage){
		try{
			return new ResponseEntity<Status>(productPackageService.deletePackage(productPackage),HttpStatus.OK);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw e;
		}
	}
	

	@RequestMapping(value={"/productPackages/{productId}"},method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map<String,Object>> productPackages(@PathVariable("productId")String productId){
		try{
			return new ResponseEntity<Map<String,Object>>(productPackageService.productPackages(productId),HttpStatus.OK);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw e;
		}
	}
	
	@RequestMapping(value={"/productPackagesApi/{productId}/{tenantId}"},method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map<String,Object>> productPackages(@PathVariable("productId")String productId,@PathVariable("tenantId")String tenantId){
		try{
			return new ResponseEntity<Map<String,Object>>(productPackageService.productPackages(productId,tenantId),HttpStatus.OK);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw e;
		}
	}
	

}
