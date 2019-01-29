package com.sdp.serviceAccess.controller.api.v1;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bonc.security.entity.Tenantinfo;
import com.sdp.common.page.Pagination;
import com.sdp.serviceAccess.service.IProductResQuotaService;

/**   
 * Copyright: Copyright (c) 2018 BONC
 * 
 * @ClassName: PProductResourcesQuotaController.java
 * @Description: 资源配额请求控制类
 *
 * @version: v1.0.0
 * @author: renpengyuan
 * @date: 2018年11月6日 下午4:34:38 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年8月2日     renpengyuan      v1.0.0               修改原因
 */
@Controller
@RequestMapping("/resource")
public class PProductResourcesQuotaController {


	@Autowired
	private IProductResQuotaService quotaService;

	private static final Logger LOG= LoggerFactory.getLogger(PProductResourcesQuotaController.class);

	@RequestMapping(value= {"/tenants"},method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Pagination> tenants(@RequestParam("pageSize")String pageSize,@RequestParam("pageNo")String pageNo) {
		try {
			Pagination page = new Pagination();
			page.setPageNo(Long.valueOf(pageNo));
			page.setPageSize(Integer.valueOf(pageSize));
			return new ResponseEntity<Pagination>(quotaService.getTenants(page),HttpStatus.OK);
		}catch(Exception e) {
			LOG.error("tenants get error",e);
			throw e;
		}
	}

	@RequestMapping(value= {"/quota/{tenantId}"},method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Pagination> quotaByTenant(@PathVariable("tenantId")String tenantId,@RequestParam("pageSize") String pageSize,@RequestParam("pageNo")String pageNo){
		try {
			Pagination page = new Pagination();
			page.setPageNo(Long.valueOf(pageNo));
			page.setPageSize(Integer.valueOf(pageSize));
			return new ResponseEntity<Pagination>(quotaService.findQuotaByTenant(tenantId, page),HttpStatus.OK);
		}catch(Exception e) {
			LOG.error("quota get error",e);
			throw e;
		}
	}


}
