package com.sdp.mall.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bonc.security.common.ConfigurationContants;
import com.sdp.serviceAccess.service.IPProductService;

import net.sf.json.JSONArray;

/**
 *
 * 商城controller
 *
 */
@Controller
@RequestMapping("/")
public class MallController {
	@Autowired
	private IPProductService productService;

	@Autowired
	private Environment env;
	/**
	 * 页面跳转
	 */
	@RequestMapping(value = {"v1/mall/home", "/"}, method = RequestMethod.GET)
	public String mallHome(Model model) {
		/*JSONArray jsonArray = JSONArray
				.fromObject(productService.productInfosByCat());
		model.addAttribute("productInfosByCat", jsonArray);*/
		return "mall/home";
	}

	@RequestMapping(value = {
			"v1/mall/productsList"}, method = RequestMethod.GET)
	public String productsList(Model model) {
		/*
		 * JSONArray jsonArray =
		 * JSONArray.fromObject(productService.productInfosByCat());
		 * model.addAttribute("productInfosByCat",jsonArray);
		 */
		return "mall/productsList";
	}

	@RequestMapping(value = {
			"v1/mall/productDetails"}, method = RequestMethod.GET)
	public String productDetails(Model model) {
		/*
		 * JSONArray jsonArray =
		 * JSONArray.fromObject(productService.productInfosByCat());
		 * model.addAttribute("productInfosByCat",jsonArray);
		 */
		return "mall/productDetails";
	}

	@RequestMapping(value = {"v1/mall/orderResult"}, method = RequestMethod.GET)
	public String orderResult(Model model) {
		/*
		 * JSONArray jsonArray =
		 * JSONArray.fromObject(productService.productInfosByCat());
		 * model.addAttribute("productInfosByCat",jsonArray);
		 */
		return "mall/orderResult";
	}

	@RequestMapping(value = {
			"v1/mall/orderReceive"}, method = RequestMethod.POST)
	public String orderReceive() {
		return "mall/Receive";
	}

	@RequestMapping(value = {
			"v1/mall/toPay/{orderId}/{orderNo}"}, method = RequestMethod.GET)
	public String orderResult(Model model,
			@PathVariable("orderId") String orderId,
			@PathVariable("orderNo") String orderNo) {
		model.addAttribute("orderId", orderId);
		model.addAttribute("orderNo", orderNo);
		return "mall/send";
	}

	/**
	 * 获取application.properties配置（增加了bonc-security-base.properties中的securityServiceURL配置获取功能）
	 * 
	 * @param key
	 * @return
	 */
	@RequestMapping("/propconfig/{key:.+}")
	@ResponseBody
	public ResponseEntity<String> getConfig(@PathVariable String key) {
		try {
			if ("securityServiceURL".equalsIgnoreCase(key)) {
				return new ResponseEntity<String>(
						ConfigurationContants.SECURITY_SERVICEURL,
						HttpStatus.OK);
			}
			return new ResponseEntity<String>(env.getProperty(key),
					HttpStatus.OK);
		} catch (Exception e) {
			throw e;
		}
	}
}
