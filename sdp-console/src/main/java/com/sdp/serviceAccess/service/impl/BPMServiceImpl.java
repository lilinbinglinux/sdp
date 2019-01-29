package com.sdp.serviceAccess.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdp.common.constant.Dictionary;
import com.sdp.common.constant.Dictionary.SignToken;
import com.sdp.common.page.Pagination;
import com.sdp.frame.web.entity.user.User;
import com.sdp.serviceAccess.entity.BPMOrderProcess;
import com.sdp.serviceAccess.entity.POrderWays;
import com.sdp.serviceAccess.entity.PProductOrder;
import com.sdp.serviceAccess.exception.ProductAccessException;
import com.sdp.serviceAccess.service.IBPMService;
import com.sdp.serviceAccess.service.IPOrderWaysService;
import com.sdp.serviceAccess.service.IPProductOrderService;
import com.sdp.serviceAccess.util.BaseUtilsService;
import com.sdp.util.WebClientUtil;

/**
 * 
 * @Description: BPMService实现类
  @ClassName: BPMServiceImpl
 * @author zy
 * @date 2018年8月9日
 * @company:www.bonc.com.cn
 */
@Service
public class BPMServiceImpl implements IBPMService{

	/**
	 * bpm流程启动接口
	 */
	@Value("${bpm_start_process}")
	private String bpm_start_process = "";

	/**
	 * bpm待办任务列表
	 */
	@Value("${bpm_todo_task}")
	private String bpm_todo_task = "";

	/**
	 * bpm流程跟踪信息地址
	 */
	@Value("${bpm_monitor_process}")
	private String bpm_monitor_process = "";

	/**
	 * bpm办理当前任务节点
	 */
	@Value("${bpm_complete_task}")
	private String bpm_complete_task = "";

	/**
	 * bpm签收当前任务节点
	 */
	@Value("${bpm_claim_task}")
	private String bpm_claim_task = "";

	/**
	 * bpm反签收当前任务节点
	 */
	@Value("${bpm_unclaim_task}")
	private String bpm_unclaim_task = "";

	/**
	 * bpm查询当前流程实例是否结束
	 */
	@Value("${bpm_process_status}")
	private String bpm_process_status = "";

	/**
	 *bpm可用流程的信息 
	 */
	@Value("${bpm_process_canuse}")
	private String bpm_process_canuse="";
	
	@Value("${bpm_process_canuse_category}")
	private String category;

	@Autowired
	private IPProductOrderService productOrderService;

	@Autowired
	private IPOrderWaysService porderWaysService;

	/**
	 * 服务申请发起审批流程
	 * @param orderId 服务申请订单号
	 */
	public void bpmStartProcess(String orderId,String loginId){
		PProductOrder productOrder =  productOrderService.findByPriId(orderId);
		if(null == productOrder) {
			throw new IllegalArgumentException("productOrder is null"); 
		}
		POrderWays orderWay = porderWaysService.findByPriId(productOrder.getPwaysId());
		if(null == orderWay) {
			throw new IllegalArgumentException("orderWay is null"); 
		}
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("procDefId",orderWay.getPwaysconfig());
			params.put("loginId",loginId);
			String result = WebClientUtil.doGet(bpm_start_process,params);
			JSONObject jsonObject = JSON.parseObject(result);
			String processInstanceId = jsonObject.getString("processInstanceId");
			Boolean success = jsonObject.getBoolean("success");

			if (success) {
				productOrder.setBpmModelNo(processInstanceId);
				//					BaseUtilsService.saveBaseInfo(productOrder, Dictionary.Directive.SAVE.value);
				productOrder.setCreateDate(new Date());
				productOrder.setUpdateDate(new Date());
				productOrderService.updateProOrder(productOrder);
			}
		} catch (Exception e) {
			throw new ProductAccessException("bpmStartProcess error",e);
		}
	}

	/**
	 * 查询用户待办任务列表
	 * @param loginId loginId
	 */
	public List<BPMOrderProcess> bpmPendingProcess(User userinfo,Pagination page) {
		Map<String, Object> params = new HashMap<>();
		List<BPMOrderProcess> orderProcessList = new ArrayList<>();
		if(StringUtils.isNoneBlank(userinfo.getTenantId())) {
			params.put("loginId", userinfo.getLoginId());
		}
		int startNum=(int) (page.getPageSize()*(page.getPageNo()-1));
		int num=page.getPageSize();
		params.put("start",startNum);
		params.put("size",num);
		String result = WebClientUtil.doGet(bpm_todo_task, params);
		JSONObject jsonObject = JSON.parseObject(result);
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		if (jsonArray.size() > 0) {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject job = jsonArray.getJSONObject(i);
				BPMOrderProcess orderProcess = new BPMOrderProcess();
				orderProcess.setProcId(job.getString("PROC_DEF_ID"));
				orderProcess.setProcInstId(job.getString("PROC_INST_ID"));
				orderProcess.setTaskId(job.getString("ID"));
				if (job.getString("ASSIGNEE") != null) {
					orderProcess.setAssignee(SignToken.SIGNIN.value);
				} else {
					orderProcess.setAssignee(SignToken.NOTSIGNIN.value);
				}
				orderProcessList.add(orderProcess);
			}
		}
		return orderProcessList;
	}

	/**
	 * 查询当前流程是否结束
	 * @param proInstId 流程实例Id
	 * @return boolean
	 */
	public Boolean bpmProcessStatus(String proInstId){
		Boolean finished = false;
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("processInstanceId",proInstId);
			String result = WebClientUtil.doGet(bpm_process_status, params);
			JSONObject jsonObject = JSON.parseObject(result);
			finished = jsonObject.getBoolean("finished");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finished;
	}

	/**
	 * 完成任务节点
	 * @param loginId loginId
	 * @param taskId taskId
	 * @return boolean
	 */
	public Boolean bpmCompleteTask(String loginId, String taskId, Integer agreement){
		Boolean success = false;
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("loginId", loginId);
			params.put("taskId",taskId);
			if (agreement == 1) {
				params.put("action", "pass");
			} else {
				params.put("action", "nopass");
			}
			String result = WebClientUtil.doGet(bpm_complete_task, params);
			JSONObject jsonObject = JSON.parseObject(result);
			success = jsonObject.getBoolean("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	/**
	 * 签收/反签收当前任务节点
	 * @param loginId  loginId
	 * @param taskId taskId
	 * @param claimType claimType
	 * @return boolean
	 */
	public Boolean claimTask(String loginId, String taskId, Integer claimType){
		Boolean success = false;
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("loginId", loginId);
			params.put("taskId",taskId);
			String result;
			if (claimType == 10) {
				result = WebClientUtil.doGet(bpm_claim_task, params);
			} else {
				result = WebClientUtil.doGet(bpm_unclaim_task, params);
			}	
			JSONObject jsonObject = JSON.parseObject(result);
			success = jsonObject.getBoolean("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}


	/**
	 *查询当前分类下的可用所有的流程信息 
	 */
	public JSONObject canUseProcesses(String tenantId) throws Exception{
		try {
			Map<String,Object> params = new HashMap<String,Object>();
            	params.put("category", this.category);
            	params.put("start", 0);
            	params.put("size", 1000);
            	params.put("order", "asc");
            	params.put("sort", "DEPLOYTIME");
            	params.put("tenantId", tenantId);
            	return JSONObject.parseObject(WebClientUtil.doGet(bpm_process_canuse, params));
		}catch(Exception e) {
			throw e;
		}
	}


}
