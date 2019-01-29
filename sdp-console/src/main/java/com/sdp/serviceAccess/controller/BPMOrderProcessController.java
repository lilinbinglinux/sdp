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
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.entity.Status;
import com.sdp.serviceAccess.entity.BPMOrderProcess;
import com.sdp.serviceAccess.entity.BpmApprovalFlow;
import com.sdp.serviceAccess.exception.ProductAccessException;
import com.sdp.serviceAccess.service.IBPMOrderProcessService;

/**
 * 
* @Description: 审批任务接口
  @ClassName: BPMOrderProcessController
* @author zy
* @date 2018年8月9日
* @company:www.bonc.com.cn
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月9日     zy      v1.0.0               修改原因
 */
@Controller
@RequestMapping("/v1/pro/pbmOrderProcess")
public class BPMOrderProcessController {
	
	private static final Logger LOG = LoggerFactory.getLogger(BPMOrderProcessController.class);
	
	/**
     * bpmOrderProcessService
     */
    @Autowired
    private IBPMOrderProcessService bpmOrderProcessService;
    
    /**
     * 
    * 查询当前用户待办任务
    * @param  参数
    * @return ResponseEntity<List<BPMOrderProcess>>    返回类型
     */
    @RequestMapping(value = {"/{orderId}"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<BPMOrderProcess>> findPendingTask(){
    		List<BPMOrderProcess> bpmOrderProcess = null;
    		try {
				//bpmOrderProcess = bpmOrderProcessService.findPendingTask();
			} catch (Exception e) {
				throw new ProductAccessException(e.getMessage(), e);
			}
    		return 	new ResponseEntity<List<BPMOrderProcess>>(bpmOrderProcess, HttpStatus.OK);
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



	@RequestMapping(value={"/page"},method=RequestMethod.GET)
	public String toPage(){
		return "product/process";
	}

    @RequestMapping(value={"/approve/{orderId}/{taskId}"},method=RequestMethod.GET)
    public String toApprove(Model model,@PathVariable("orderId") String orderId,@PathVariable("taskId") String taskId){
    		model.addAttribute("orderId",orderId);
    		model.addAttribute("taskId",taskId);
        return "product/approve";
    }

	@RequestMapping(value={"/approved/{orderId}"},method=RequestMethod.GET)
	public String toApprove(Model model,@PathVariable("orderId") String orderId){
		model.addAttribute("orderId",orderId);
		return "product/approved";
	}
}
