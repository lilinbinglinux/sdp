package com.sdp.serviceAccess.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.serviceAccess.entity.BpmApprovalFlow;
import com.sdp.serviceAccess.mapper.BpmApprovalFlowMapper;
import com.sdp.serviceAccess.service.IBpmApprovalFlowService;


/**
 * 
* @Description: bpm工单审批历史信息Service实现
  @ClassName: BpmApprovalFlowServiceImpl
* @author zy
* @date 2018年8月9日
* @company:www.bonc.com.cn
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月9日     zy      v1.0.0               修改原因
 */
@Service
public class BpmApprovalFlowServiceImpl implements IBpmApprovalFlowService{
	
	@Autowired
	private BpmApprovalFlowMapper bpmApprovalFlowMapper;

	@Override
	public List<BpmApprovalFlow> findAllByCondition(BpmApprovalFlow bpmApprovalFlow) {
		return bpmApprovalFlowMapper.findAllByCondition(bpmApprovalFlow);
	}

	@Override
	public BpmApprovalFlow findById(BpmApprovalFlow bpmApprovalFlow) {
		return bpmApprovalFlowMapper.findById(bpmApprovalFlow);
	}

}
