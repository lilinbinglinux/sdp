package com.sdp.serviceAccess.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sdp.common.CurrentUserUtils;
import com.sdp.common.constant.Dictionary;
import com.sdp.common.entity.Status;
import com.sdp.common.page.Pagination;
import com.sdp.frame.web.entity.user.User;
import com.sdp.serviceAccess.entity.BPMOrderProcess;
import com.sdp.serviceAccess.entity.BpmApprovalFlow;
import com.sdp.serviceAccess.entity.PProduct;
import com.sdp.serviceAccess.entity.PProductOrder;
import com.sdp.serviceAccess.exception.ProductAccessException;
import com.sdp.serviceAccess.mapper.BpmApprovalFlowMapper;
import com.sdp.serviceAccess.mapper.PProductMapper;
import com.sdp.serviceAccess.mapper.PProductOrderMapper;
import com.sdp.serviceAccess.service.IBPMOrderProcessService;
import com.sdp.serviceAccess.service.IBPMService;
import com.sdp.serviceAccess.service.IBpmApprovalFlowService;
import com.sdp.serviceAccess.service.IPProductOrderService;
import com.sdp.serviceAccess.service.IProductOperateCompontent;
import com.sdp.serviceAccess.util.BaseUtilsService;
import com.sdp.serviceAccess.util.MapObjUtil;

/**
 * 
 * @Description: 工单处理Service
  @ClassName: BPMOrderProcessServiceImpl
 * @author zy
 * @date 2018年8月9日
 * @company:www.sdp.com.cn
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年8月9日     zy      v1.0.0               修改原因
 */
@Service
public class BPMOrderProcessServiceImpl implements IBPMOrderProcessService {

	/**
	 * 输出日志
	 */
	private static final Logger LOG = LoggerFactory.getLogger(IBPMOrderProcessService.class);

	@Autowired
	private IBPMService bpmService;

	@Autowired
	private IProductOperateCompontent productOperateService;

	/**
	 * bpm流程跟踪信息地址
	 */
	@Value("${bpm_monitor_process}")
	private String bpm_monitor_process = "";

	@Autowired
	private IPProductOrderService productOrderService ;

	@Autowired
	private IBpmApprovalFlowService bpmApprovalFlowService;

	@Autowired
	private BpmApprovalFlowMapper bpmApprovalFlowMapper;

	@Autowired
	private PProductOrderMapper productOrderMapper;

	@Autowired
	private PProductMapper productMapper;


	/**
	 * 查询当前用户待办任务
	 * @return list
	 */
	public Pagination findPendingTask(Pagination page){
		User userInfo = CurrentUserUtils.getInstance().getUser();
		List<BPMOrderProcess> bpmOrderProcess = bpmService.bpmPendingProcess(userInfo,page);
		if(null == bpmOrderProcess) {
			throw new IllegalArgumentException("bpmOrderProcess is null");
		}
		try {
			Iterator<BPMOrderProcess> it = bpmOrderProcess.iterator();
			while(it.hasNext()) {
				BPMOrderProcess orderProcess = it.next();
				PProductOrder porder = new PProductOrder();
				porder.setBpmModelNo(orderProcess.getProcInstId());
				//porder.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
				List<PProductOrder> porders = productOrderMapper.findAllByLikeCondition(porder);
				if(porders.size()>1) {
					throw new IllegalArgumentException("select productOrder by bpmSvcId is more");
				}
				if(null != porders&&porders.size()!=0) {
					porder = porders.get(0);
					orderProcess.setOrderId(porder.getOrderId());
					orderProcess.setApplyUserName(porder.getCreateBy());
					orderProcess.setOrderStartDate(porder.getCreateDate());
					orderProcess.setMonitorProcessUrl(bpm_monitor_process + "?params.pDefId=" + porder.getBpmModelConfig() + "&params.pId=" + porder.getBpmModelNo());
					orderProcess.setProductName(porder.getProductName());
					orderProcess.setApplyName(porder.getApplyName());
				}else {
					it.remove();
				}
			}
		} catch (Exception e) {
			throw new ProductAccessException(e.getMessage(),e);
		}

		page.setList(bpmOrderProcess);
		//	page.setTotalCount(total);
		return page;
	}

	/**
	 * 
	 * 查询用户已办任务（分页）
	 * @param  参数
	 * @return Pagination    返回类型
	 */
	public Pagination findFinishTask(BPMOrderProcess bpmOrderProcess,Pagination page) {
		Pagination result;
		List<BPMOrderProcess> orderProcessList = new ArrayList<>();
		try{
			PProductOrder proOrder = new PProductOrder();
			proOrder.setOrderId(bpmOrderProcess.getOrderId());
			proOrder.setProductName(bpmOrderProcess.getProductName());
			proOrder.setStartTime(bpmOrderProcess.getOrderStartDate());
			proOrder.setExpireTime(bpmOrderProcess.getApprovalDate());
			String tenantId= CurrentUserUtils.getInstance().getDataUser().getTenantId();
			String loginId = CurrentUserUtils.getInstance().getDataUser().getLoginId();
			proOrder.setTenantId(tenantId);
			proOrder.setCreateBy(loginId);
			Long total = productOrderMapper.findFinishTaskTotalCount(proOrder);
			int startNum=(int) (page.getPageSize()*(page.getPageNo()-1));
			int num=page.getPageSize();
			Map<Object, Object> map = MapObjUtil.objectToMap(proOrder);
			map.put("startNum", startNum);
			map.put("num", num);
			List<PProductOrder> proOrders = productOrderService.findFinishTaskOrder(map);

			if(null != proOrders&&proOrders.size()>0) {
				for(PProductOrder pProductOrder:proOrders) {
					BPMOrderProcess orderProcess = new BPMOrderProcess();
					orderProcess.setOrderId(pProductOrder.getOrderId());
					orderProcess.setOrderStartDate(pProductOrder.getCreateDate());
					String proName = pProductOrder.getProductName()!=null?pProductOrder.getProductName():"";
					orderProcess.setProductName(proName);
					orderProcess.setApplyUserName(pProductOrder.getCreateBy());
					orderProcess.setProcInstId(pProductOrder.getBpmModelNo());
					orderProcess.setApprovalDate(pProductOrder.getBpmSignDate());
					orderProcess.setProcId(pProductOrder.getBpmModelConfig());
					orderProcess.setMonitorProcessUrl(bpm_monitor_process + "?params.pDefId=" + pProductOrder.getBpmModelConfig() + "&params.pId=" + pProductOrder.getBpmModelNo());
					orderProcessList.add(orderProcess);
				}
			}
			page.setList(orderProcessList);
			page.setTotalCount(total);
		}catch(Exception e){
			throw new ProductAccessException("findFinishTask error",e);
		}
		return page;

	}

	/**
	 * 
	 * 查询当前审批流程历史任务
	 * @param  参数
	 * @return List<BpmApprovalFlow>    返回类型
	 */
	public List<BpmApprovalFlow> findApprovalFlowTask(String orderId){
		List<BpmApprovalFlow> bpmflows;
		try {
			BpmApprovalFlow bpmApprovalFlow = new BpmApprovalFlow();
			bpmApprovalFlow.setOrderId(orderId);
			bpmflows = bpmApprovalFlowService.findAllByCondition(bpmApprovalFlow);
		} catch (Exception e) {
			throw new ProductAccessException("findApprovalFlowTask error",e);
		}
		return bpmflows;
	}

	/**
	 * 
	 * 工单任务审批
	 * @param  参数
	 * @return Status    返回类型
	 */
	public Map<Object, Object> saveBPMApproveTask(BpmApprovalFlow bpmApprovalFlow) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Status status;
		User userInfo = CurrentUserUtils.getInstance().getUser();
		PProductOrder productOrder = productOrderService.findByPriId(bpmApprovalFlow.getOrderId());
		try {
			boolean success = bpmService.bpmCompleteTask(userInfo.getLoginId(), bpmApprovalFlow.getPendtaskId(),
					bpmApprovalFlow.getIsAgreement());
			if (success) {
				// 查询当前任务流程是否结束
				boolean finish = bpmService.bpmProcessStatus(productOrder.getBpmModelNo());
				productOrder.setBpmSignDate(new Date());
				productOrderService.updateProOrder(productOrder);
				BaseUtilsService.saveBaseInfo(bpmApprovalFlow, Dictionary.Directive.SAVE.value);
				bpmApprovalFlowMapper.saveSingle(bpmApprovalFlow);
				if ((int) bpmApprovalFlow.getIsAgreement() == 1) {
					productOrder.setOrderStatus(Dictionary.OrderState.APPROVAL_PROCESS.value);
					if (finish) {
						productOrder.setOrderStatus(Dictionary.OrderState.APPROVAL_SUCCESS.value);
						PProduct product = new PProduct(productOrder.getProductId());
						// product.setTenantId(productOrder.getTenantId());
						product = productMapper.findById(product);
						if (StringUtils.isNotBlank(product.getApiAddr())) {
							// TODO 创建用户实例
							productOperateService.createService(productOrder);
						}
					} else {
						productOrder.setOrderStatus(Dictionary.OrderState.APPROVAL_PROCESS.value);
					}
				} else if ((int) bpmApprovalFlow.getIsAgreement() == 0) {// 不同意：驳回
					productOrder.setOrderStatus(Dictionary.OrderState.FAIL.value);
				}
				status = new Status("bpmApproval created success", Dictionary.HttpStatus.CREATED.value);
			} else {
				productOrder.setOrderStatus(Dictionary.OrderState.FAIL.value);
				status = new Status("bpmApproval created error", Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value);
			}
		} catch (Exception e) {
			productOrder.setOrderStatus(Dictionary.OrderState.FAIL.value);
			status = new Status(e.getMessage(), Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value);
			// throw new ProductAccessException(e.getMessage(), e);
		}
		BaseUtilsService.saveBaseInfo(productOrder, Dictionary.Directive.UPDATE.value);
		productOrderService.updateProOrder(productOrder);
		map.put("status", status);
		map.put("productOrder", productOrder);
		return map;
	}

	/**
	 * 
	 * 签收/反签收当前任务节点
	 * @param  参数
	 * @return Status    返回类型
	 */
	public Status claimTask(String orderId,Integer claimType, String pendtaskId) {
		Status status;
		User userInfo = CurrentUserUtils.getInstance().getUser();
		try {
			boolean success = bpmService.claimTask(userInfo.getLoginId(), pendtaskId, claimType);
			if(success) {
				status = new Status("bpmApproval created success", Dictionary.HttpStatus.CREATED.value);
			}else {
				status = new Status("bpmApproval created error", Dictionary.HttpStatus.CREATED.value);
			}
		} catch (Exception e) {
			throw new ProductAccessException("claimTask error", e);
		}
		return status;

	}

	/**
	 *查询当前分类下的可用所有的流程信息 
	 */
	public JSONObject canUseProcess(){
		try {
			return bpmService.canUseProcesses(CurrentUserUtils.getInstance().getUser().getTenantId());
		}catch(Exception e) {
			throw new ProductAccessException("can use processes get error",e);
		}
	}


}
