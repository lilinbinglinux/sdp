/**
 * 
 */
package com.sdp.serviceAccess.controller.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdp.common.entity.Status;
import com.sdp.common.page.Pagination;
import com.sdp.serviceAccess.entity.PProduct;
import com.sdp.serviceAccess.entity.PProductType;
import com.sdp.serviceAccess.service.IPProductService;
import com.sdp.serviceAccess.service.IPProductTypeService;
import com.sdp.serviceAccess.util.JsonXMLUtils;

/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: PProductController.java
* @Description: 服务注册控制类
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年8月2日 下午4:32:53 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月2日     renpengyuan      v1.0.0               修改原因
*/
@RestController
@RequestMapping("/api/product")
public class RestPProductController {
	
	private static final Logger LOG = LoggerFactory.getLogger(RestPProductController.class);
	
	@Autowired
	private IPProductService productService;
    
	@Autowired
	private IPProductTypeService proTypeService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/allProducts"},method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Pagination> allProducts(@RequestBody Map<String,Object> param) throws Exception{
	    try{
	    	Pagination page = JsonXMLUtils.map2obj((Map<String, Object>)param.get("page"),Pagination.class); 
	    	PProduct product = JsonXMLUtils.map2obj((Map<String, Object>)param.get("product"),PProduct.class); 
	    	return new ResponseEntity<Pagination>(productService.allProducts(page, product,false),HttpStatus.OK);
	    }catch(Exception e){
	    	LOG.error(e.getMessage(),e);
	    	throw e;
	    }
	}
	
	@RequestMapping(value={"/resgProduct"},method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Status> resgProduct(String product, @RequestParam("productIcon") MultipartFile productIcon){
		try{
			PProduct product1 = JSON.toJavaObject(JSONObject.parseObject(product), PProduct.class);
	    	return new ResponseEntity<Status>(productService.createProduct(product1, productIcon),HttpStatus.OK);
	    }catch(Exception e){
	    	LOG.error(e.getMessage(),e);
	    	throw e;
	    }
	}
	
	
	@RequestMapping(value={"/modifyProduct"},method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Status> modifyProduct(String product, @RequestParam("productIcon") MultipartFile productIcon){
		try{
			PProduct product1 = JSON.toJavaObject(JSONObject.parseObject(product), PProduct.class);
	    	return new ResponseEntity<Status>(productService.modifyProduct(product1, productIcon),HttpStatus.OK);
	    }catch(Exception e){
	    	LOG.error(e.getMessage(),e);
	    	throw e;
	    }
	}
	
	@RequestMapping(value={"/singleProduct/{productId}"},method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<PProduct> singleProduct(@PathVariable("productId")String productId ){
		try{
			PProduct product = new PProduct();
			product.setProductId(productId);
	    	return new ResponseEntity<PProduct>(productService.singleProduct(product),HttpStatus.OK);
	    }catch(Exception e){
	    	LOG.error(e.getMessage(),e);
	    	throw e;
	    }
	}
	
	//扩展, 自定义套餐查询服务信息
	@RequestMapping(value={"/singleProduct/{productId}/checkKv"},method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<PProduct> singleProductByCheck(@PathVariable("productId")String productId ){
		try{
			PProduct product = new PProduct();
			product.setProductId(productId);
			product.setCheckKv("20");
	    	return new ResponseEntity<PProduct>(productService.singleProduct(product),HttpStatus.OK);
	    }catch(Exception e){
	    	LOG.error(e.getMessage(),e);
	    	throw e;
	    }
	}
	
	@RequestMapping(value={"/modifyStatus/{productId}/{status}"},method=RequestMethod.GET)
	@ResponseBody	
	public ResponseEntity<Status> modifyStatus(@PathVariable("productId")String productId,@PathVariable("status") String status){
		try{
			PProduct product = new PProduct();
			product.setProductId(productId);
			product.setProductStatus(status);
	    	return new ResponseEntity<Status>(productService.modifyStatus(product),HttpStatus.OK);
	    }catch(Exception e){
	    	LOG.error(e.getMessage(),e);
	    	throw e;
	    }
	}
	
	@RequestMapping(value={"/proItems"},method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Status> proItems(@RequestBody PProduct product){
		try{
	    	return new ResponseEntity<Status>(productService.updateProItems(product),HttpStatus.OK);
	    }catch(Exception e){
	    	LOG.error(e.getMessage(),e);
	    	throw e;
	    }
	}
	
	@RequestMapping(value={"/productCodeVerfy/{productCode}"},method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> proItems(@PathVariable("productCode") String  productCode){
		try{
	    	return new ResponseEntity<Boolean>(productService.verfyProductCode(productCode),HttpStatus.OK);
	    }catch(Exception e){
	    	LOG.error(e.getMessage(),e);
	    	throw e;
	    }
	}
	
	@RequestMapping(value={"/productInfosByCat"},method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Map<String,Object>>> productInfosByCat(){
		try{
			return new ResponseEntity<List<Map<String,Object>>>(productService.productInfosByCat(),HttpStatus.OK);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw e;
		}
	}
    @SuppressWarnings("unchecked")
	@RequestMapping(value={"/products"},method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Pagination> products(@RequestBody Map<String,Object> param) throws Exception{
	    try{
	    	Pagination page = JsonXMLUtils.map2obj((Map<String, Object>)param.get("page"),Pagination.class); 
	    	PProduct product = JsonXMLUtils.map2obj((Map<String, Object>)param.get("product"),PProduct.class); 
	    	return new ResponseEntity<Pagination>(productService.allProducts(page, product,true),HttpStatus.OK);
	    }catch(Exception e){
	    	LOG.error(e.getMessage(),e);
	    	throw e;
	    }
	}
    @RequestMapping(value={"/properties"},method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Status> createProperties(@RequestBody PProduct product){
		try {
			return new ResponseEntity<Status>(productService.createProperties(product),HttpStatus.OK);
		}catch(Exception e) {
			LOG.error(e.getMessage(),e);
			throw e;
		}
	}
	@RequestMapping(value={"/productsByCase"},method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> productsByCase(@RequestBody Map<String,Object> param) throws Exception{
		try{
			PProduct product = JsonXMLUtils.map2obj((Map<String, Object>)param.get("product"),PProduct.class); 
			String dataAuth = (String)param.get("dataAuth");
			List<PProduct> proList = productService.getProductByCase(product, dataAuth);
			List<PProductType> typeList = proTypeService.getAllPProductTypeByProduct(proList);
			Map<String, Object> resMap = new HashMap<String, Object>();
			resMap.put("products", proList);
			resMap.put("proTypes", typeList);
			return new ResponseEntity<Map<String, Object>>(resMap,HttpStatus.OK);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw e;
		}
	}
	
	/**
	 * 使用权限控制，请在请求中加入currentAuth，值的选择是Dictionary.AuthToken中内容
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/allProductsCntWithAuth"},method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Integer> auth_allProductsCnt(@RequestBody Map<String,Object> param) throws Exception{
	    try{
	    	PProduct product = JsonXMLUtils.map2obj((Map<String, Object>)param.get("product"),PProduct.class); 
	    	return new ResponseEntity<Integer>(productService.allProductsCntWithAuth(product),HttpStatus.OK);
	    }catch(Exception e){
	    	LOG.error(e.getMessage(),e);
	    	throw e;
	    }
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/allProductWithAuth"},method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Pagination> auth_allProducts(@RequestBody Map<String,Object> param) throws Exception{
	    try{
	    	Pagination page = JsonXMLUtils.map2obj((Map<String, Object>)param.get("page"),Pagination.class); 
	    	PProduct product = JsonXMLUtils.map2obj((Map<String, Object>)param.get("product"),PProduct.class); 
	    	return new ResponseEntity<Pagination>(productService.allProductsWithAuth(page, product,false),HttpStatus.OK);
	    }catch(Exception e){
	    	LOG.error(e.getMessage(),e);
	    	throw e;
	    }
	}
}
