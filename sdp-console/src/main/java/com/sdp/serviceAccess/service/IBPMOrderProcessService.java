package com.sdp.serviceAccess.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.sdp.common.entity.Status;
import com.sdp.common.page.Pagination;
import com.sdp.serviceAccess.entity.BPMOrderProcess;
import com.sdp.serviceAccess.entity.BpmApprovalFlow;
import com.sdp.serviceAccess.entity.PProductOrder;

/**
 * 
* @Description: 工单处理Service
  @ClassName: IBPMOrderProcessService
* @author zy
* @date 2018年8月10日
* @company:www.sdp.com.cn
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月10日     zy      v1.0.0               修改原因
 */
public interface IBPMOrderProcessService {
	/**
     * 查询当前用户待办任务
     * @return list
     */
	Pagination findPendingTask(Pagination page);
	
	/**
     * 
    * 查询用户已办任务（分页）
    * @param  参数
    * @return Pagination    返回类型
     */
    Pagination findFinishTask(BPMOrderProcess bpmOrderProcess,Pagination page);
    
    /**
     * 
    * 查询当前审批流程历史任务
    * @param  参数
    * @return List<BpmApprovalFlow>    返回类型
     */
    List<BpmApprovalFlow> findApprovalFlowTask(String orderId);
    
    /**
     * 
    * 工单任务审批
    * @param  参数
    * @return Status    返回类型
     */
    Map<Object,Object> saveBPMApproveTask(BpmApprovalFlow bpmApprovalFlow);
    
    /**
     * 
    * 签收/反签收当前任务节点
    * @param  参数
    * @return Status    返回类型
     */
    Status claimTask(String orderId,Integer claimType, String pendtaskId);
    
    
    
    /**
	 *查询当前分类下的可用所有的流程信息 
	 */
	public JSONObject canUseProcess();

	
    
	

}
