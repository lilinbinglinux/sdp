package com.sdp.serviceAccess.controller.rest;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.sdp.serviceAccess.entity.POrderWays;
import com.sdp.serviceAccess.exception.ProductAccessException;
import com.sdp.serviceAccess.service.IBPMOrderProcessService;
import com.sdp.serviceAccess.service.IPOrderWaysService;
import com.sdp.serviceAccess.util.JsonXMLUtils;

/**
 * 
 * @Description: 所有订购方式接口
  @ClassName: RestPOrderWaysController
 * @author zy
 * @date 2018年8月23日
 * @company:www.bonc.com.cn
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年8月23日     zy      v1.0.0               修改原因
 */
@RestController
@RequestMapping("/api/porderWays")
public class RestPOrderWaysController {

	private static final Logger LOG = LoggerFactory.getLogger(RestPOrderWaysController.class);

	@Autowired
	private IPOrderWaysService pOrderWaysService;

	@Autowired
	private IBPMOrderProcessService bpmOrderService;

	@RequestMapping(value={"/allPorderWays"},method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<POrderWays>> allPorderWays(@RequestBody POrderWays porderWays) throws Exception{
		try{
			return new ResponseEntity<List<POrderWays>>(pOrderWaysService.allPorderWays(porderWays),HttpStatus.OK);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw e;
		}
	}

	/**
	 * 
	 * 查询当前可用的流程
	 * 
	 */
	@RequestMapping(value = {"/processes"}, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<JSONObject> canuseprocesses(){
		try {
			return new ResponseEntity<JSONObject>(bpmOrderService.canUseProcess(),HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new ProductAccessException(e.getMessage(), e);
		}
	}



}
