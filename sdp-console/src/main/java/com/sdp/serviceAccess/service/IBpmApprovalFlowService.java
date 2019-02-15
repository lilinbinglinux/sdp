package com.sdp.serviceAccess.service;

import java.util.List;

import com.sdp.serviceAccess.entity.BpmApprovalFlow;

/**
 * 
* @Description: bpm工单审批历史信息Service
  @ClassName: BpmApprovalFlowService
* @author zy
* @date 2018年8月9日
* @company:www.sdp.com.cn
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月9日     zy      v1.0.0               修改原因
 */
public interface IBpmApprovalFlowService {

	List<BpmApprovalFlow> findAllByCondition(BpmApprovalFlow bpmApprovalFlow);

	BpmApprovalFlow findById(BpmApprovalFlow bpmApprovalFlow);

}
