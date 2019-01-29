package com.sdp.serviceAccess.controller.api.v1;

import java.util.Map;

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

import com.sdp.serviceAccess.service.IPProductCaseService;

@Controller
@RequestMapping("v1/")
public class ThirdPartyIntegrationController {
	
	@Autowired
	private IPProductCaseService caseService;
	
    /**
     * 根据租户Id，查询租户的有效配额
     * @param tenantId tenantId
     * @param spCode 服务提供者code
     * @param svcCode 注册服务code
     * @param version 服务版本
     * @return list
     */
	 @RequestMapping(value = {"/tenantId/{tenantId}/svcorders"}, method = RequestMethod.GET)
	    @ResponseBody
	    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<Map<String, Object>> findQuota(@PathVariable("tenantId") String tenantId, String spCode, String svcCode, String version){
        try {
            return new ResponseEntity<Map<String, Object>>(caseService.findQuota(tenantId, svcCode), HttpStatus.OK);
        } catch (Exception e) {
	    	throw e;
        }
    }
}
