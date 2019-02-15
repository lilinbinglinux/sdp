package com.sdp.serviceAccess.controller.rest;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.sdp.common.entity.Status;
import com.sdp.common.page.Pagination;
import com.sdp.serviceAccess.entity.BPMOrderProcess;
import com.sdp.serviceAccess.entity.BpmApprovalFlow;
import com.sdp.serviceAccess.exception.ProductAccessException;
import com.sdp.serviceAccess.service.IBPMOrderProcessService;
import com.sdp.serviceAccess.service.IBpmApprovalFlowService;
import com.sdp.serviceAccess.util.JsonXMLUtils;

/**
 * 
 * @Description: 审批任务接口
  @ClassName: BPMOrderProcessController
 * @author zy
 * @date 2018年8月9日
 * @company:www.sdp.com.cn
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年8月9日     zy      v1.0.0               修改原因
 */
@RestController
@RequestMapping("/api/bpmOrderProcess")
public class RestBPMOrderProcessController {

	private static final Logger LOG = LoggerFactory.getLogger(RestBPMOrderProcessController.class);

	/**
	 * bpmOrderProcessService
	 */
	@Autowired
	private IBPMOrderProcessService bpmOrderProcessService;

	/**
	 * bpmApprovalFlowService
	 */
	@Autowired
	private IBpmApprovalFlowService bpmApprovalFlowService;

	/**
	 * 
	 * 查询当前用户待办任务
	 * @param  参数
	 * @return ResponseEntity<List<BPMOrderProcess>>    返回类型
	 * @throws Exception 
	 */
	@RequestMapping(value = {"/pendingTask"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Pagination> auth_findPendingTask(@RequestBody Map<String,Object> param) throws Exception{
		try{
			Pagination page = JsonXMLUtils.map2obj((Map<String, Object>)param.get("page"),Pagination.class); 
			return new ResponseEntity<Pagination>(bpmOrderProcessService.findPendingTask(page),HttpStatus.OK);
		}catch(Exception e){
			throw new ProductAccessException(e.getMessage(), e);
		}
	}

	/**
	 * 分页查询当前用户的已办任务
	 * @param cOrderProcessForm cOrderProcessForm
	 * @return Map
	 */
	@RequestMapping(value = {"/finishTask"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Pagination> auth_findFinishTask(@RequestBody Map<String,Object> param){
		try {
			Pagination page = JsonXMLUtils.map2obj((Map<String, Object>)param.get("page"),Pagination.class);
			BPMOrderProcess bpmOrderProcess = JsonXMLUtils.map2obj((Map<String, Object>)param.get("bpmOrderProcess"),BPMOrderProcess.class); 
			return new ResponseEntity<Pagination>(bpmOrderProcessService.findFinishTask(bpmOrderProcess,page),HttpStatus.OK);
		} catch (Exception e) {
			throw new ProductAccessException(e.getMessage(), e);
		} 
	}

	/**
	 * 工单任务审批
	 */
	@RequestMapping(value = {"/{orderId}/approval"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<Object,Object>> saveApporovalTask(@RequestBody BpmApprovalFlow bpmApprovalFlow,@PathVariable("orderId") String orderId) {
		try {
			return new ResponseEntity<Map<Object,Object>>(bpmOrderProcessService.saveBPMApproveTask(bpmApprovalFlow),HttpStatus.OK);
		} catch (Exception e) {
			throw new ProductAccessException(e.getMessage(), e);
		}
	}


	/**
	 * 
	 * 签收和反签收待办任务
	 * @param  参数
	 * @return Status    返回类型
	 */
	@RequestMapping(value = {"/{orderId}/claimType/{claimType}/{pendtaskId}"}, method = RequestMethod.GET)
	@ResponseBody
	public Status claimTask(@PathVariable("orderId") String orderId, @PathVariable("claimType") Integer claimType, @PathVariable("pendtaskId") String pendtaskId) {
		Status status;
		try {
			status = bpmOrderProcessService.claimTask(orderId,claimType,pendtaskId);
		} catch (Exception e) {
			throw new ProductAccessException(e.getMessage(), e);
		}
		return status;
	}

	/**
	 * 
	 * 根据订单id查询历史审批
	 * @param  参数
	 * @return ResponseEntity<List<BpmApprovalFlow>>    返回类型
	 */
	@RequestMapping(value = {"/{orderId}/hisApproval"}, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<BpmApprovalFlow>> allBpmApprovalFlow(@PathVariable("orderId") String orderId){
		try {
			BpmApprovalFlow	bpmApprovalFlow = new BpmApprovalFlow(orderId);
			return new ResponseEntity<List<BpmApprovalFlow>>(bpmApprovalFlowService.findAllByCondition(bpmApprovalFlow),HttpStatus.OK);
		} catch (Exception e) {
			throw new ProductAccessException(e.getMessage(), e);
		}
	}


	/**
	 * 
	 * 根据订单id查询历史审批
	 * @param  参数
	 * @return ResponseEntity<List<BpmApprovalFlow>>    返回类型
	 */
	@RequestMapping(value = {"/test"}, method = RequestMethod.GET)
	@ResponseBody
	public String  authfind(){
		return "success";
	}

}
