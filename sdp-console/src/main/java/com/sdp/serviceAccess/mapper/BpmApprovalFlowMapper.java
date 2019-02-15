package com.sdp.serviceAccess.mapper;

import java.util.List;

import com.sdp.serviceAccess.entity.BpmApprovalFlow;

/**
 * 
* @Description: BPM审批历史Mapper
  @ClassName: BpmApprovalFlowMapper
* @author zy
* @date 2018年8月9日
* @company:www.sdp.com.cn
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月9日     zy      v1.0.0               修改原因
 */
public interface BpmApprovalFlowMapper extends BaseMapper<BpmApprovalFlow>{

	List<BpmApprovalFlow> findAllByCondition(BpmApprovalFlow bpmApprovalFlow);

}
