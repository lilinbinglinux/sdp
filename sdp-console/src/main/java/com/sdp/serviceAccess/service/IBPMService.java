package com.sdp.serviceAccess.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.sdp.common.page.Pagination;
import com.sdp.frame.web.entity.user.User;
import com.sdp.servflow.serlayout.sso.model.Userinfo;
import com.sdp.serviceAccess.entity.BPMOrderProcess;

/**
 * 
* @Description: bpm流程Service
  @ClassName: BPMService
* @author zy
* @date 2018年8月9日
* @company:www.sdp.com.cn
 */
public interface IBPMService {
	
	/**
     * 服务申请发起审批流程
     * @param orderId 服务申请订单号
     */
	void bpmStartProcess(String orderId,String loginId);
	
	/**
     * 查询用户待办任务列表
     * @param loginId loginId
     */
    List<BPMOrderProcess> bpmPendingProcess(User userinfo,Pagination page);
    
    /**
     * 查询当前流程是否结束
     * @param proInstId 流程实例Id
     * @return boolean
     */
    Boolean bpmProcessStatus(String proInstId);
    
    /**
     * 完成任务节点
     * @param loginId loginId
     * @param taskId taskId
     * @return boolean
     */
    Boolean bpmCompleteTask(String loginId, String taskId, Integer agreement);
    
    /**
     * 签收/反签收当前任务节点
     * @param loginId  loginId
     * @param taskId taskId
     * @param claimType claimType
     * @return boolean
     */
    Boolean claimTask(String loginId, String taskId, Integer claimType);
    
    
    /**
	 *查询当前分类下的可用所有的流程信息 
     * @throws Exception 
	 */
    public JSONObject canUseProcesses(String tenantId) throws Exception;

	
	
	

}
