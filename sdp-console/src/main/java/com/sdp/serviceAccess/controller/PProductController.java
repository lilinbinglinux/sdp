/**
 * 
 */
package com.sdp.serviceAccess.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdp.common.entity.Status;
import com.sdp.common.page.Pagination;
import com.sdp.serviceAccess.entity.PProduct;
import com.sdp.serviceAccess.service.IPProductService;
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
@Controller
@RequestMapping("/product")
public class PProductController {

	private static final Logger LOG = LoggerFactory.getLogger(PProductController.class);

	@Autowired
	private IPProductService productService;

	@RequestMapping(value={"/page"},method=RequestMethod.GET)
	public String toPage(){
		return "product/product";
	}

	@RequestMapping(value={"/regProductPage"},method=RequestMethod.GET)
	public String toRegProductPage(){
		return "product/regProduct";
	}
	@RequestMapping(value={"/productInfoPage"},method=RequestMethod.GET)
	public String toProductInfo(){
		return "product/productInfo";
	}
	@RequestMapping(value={"/configItemPage"},method=RequestMethod.GET)
	public String toConfigItem(){
		return "product/configItemPage";
	}
	@RequestMapping(value={"/consoleHome/page"},method=RequestMethod.GET)
	public String consoleHome(){
		return "product/consoleHome";
	}

	/*
	 * 服务浏览页面
	 * */
	@RequestMapping(value={"/productBrowsePage"},method=RequestMethod.GET)
	public String toProductBrowse(){
		return "product/productBrowse";
	}

	/*
	 * 我的服务实例列表页面
	 * */
	@RequestMapping(value={"/myServicePage"},method=RequestMethod.GET)
	public String toMyServicePage(Model model){
		return "product/myServiceList";
	}
	
//	/*
//	 * 码表管理页面
//	 */
//	@RequestMapping(value={"/codeTablePage"},method=RequestMethod.GET)
//	public String toCodeTablePage(Model model) {
//		return "product/codeTable";
//	}
	
	/*
	 * 服务实例列表页面
	 * */
	@RequestMapping(value={"/servicePage"},method=RequestMethod.GET)
	public String toServicePage(Model model){
		return "product/serviceList";
	}
	
    /*
     * 我的服务实例列表页面
     */
    @RequestMapping(value = { "/singleServicePage" }, method = RequestMethod.GET)
    public String toServicesPage(@RequestParam(value = "productId", required = true) String productId, Model model) {
        model.addAttribute("productId", productId);
        return "product/singleService";
    }

	/*
	 * 服务实例详情页面
	 */
	@RequestMapping(value = { "/serviceDetailsPage" }, method = RequestMethod.GET)
	public String toServiceDetails(@RequestParam(value = "productId", required = true) String productId, Model model) {
		model.addAttribute("productId", productId);
		return "product/serviceDetails";
	}
	

	/*
	 * 服务实例详情页面
	 * */
	@RequestMapping(value={"/servicePage/example/{productId}/{caseId}"},method=RequestMethod.GET)
	public String toServiceExamplePage(Model model, @PathVariable("productId") String productId, @PathVariable("caseId") String caseId){
		model.addAttribute("productId",productId);
		model.addAttribute("caseId",caseId);
		return "product/serviceExample";
	}

	/*
	 * 配额
	 * */
	@RequestMapping(value={"/quataPage"},method=RequestMethod.GET)
	public String toQuataPage(){
		return "product/quota";
	}

	/*
     * 我的配额
     * */
	@RequestMapping(value={"/quotaOfMine"},method=RequestMethod.GET)
	public String toMyQuataPage(){
		return "product/quotaOfMine";
	}

	/*
    * 配额申请页面
    * */
	@RequestMapping(value={"/quataApplication/{productId}"},method=RequestMethod.GET)
	public String toQuataApplication(Model model, @PathVariable("productId") String productId){
        model.addAttribute("productId",productId);
		return "product/quotaApply";
	}

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
}
