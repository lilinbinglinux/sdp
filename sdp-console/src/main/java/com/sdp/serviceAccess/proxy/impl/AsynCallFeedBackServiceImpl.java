package com.sdp.serviceAccess.proxy.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdp.common.entity.Status;
import com.sdp.serviceAccess.controller.PProductCaseController;
import com.sdp.serviceAccess.entity.POperInfo;
import com.sdp.serviceAccess.entity.PProduct;
import com.sdp.serviceAccess.entity.PProductCase;
import com.sdp.serviceAccess.entity.ProductRelyOnItem;
import com.sdp.serviceAccess.mapper.POperMapper;
import com.sdp.serviceAccess.mapper.PProductCaseMapper;
import com.sdp.serviceAccess.mapper.PProductMapper;
import com.sdp.serviceAccess.proxy.AsynCallFeedBackService;
import com.sdp.serviceAccess.proxy.model.CSvcBusiInfo;
import com.sdp.serviceAccess.proxy.model.FeedBackRequestParam;
import com.sdp.serviceAccess.proxy.model.ResponseResult;
import com.sdp.serviceAccess.proxy.specific.BusinessHandleService;

@Service
public class AsynCallFeedBackServiceImpl implements AsynCallFeedBackService {

	@Autowired
	BusinessHandleService busiNessHandleService;

	@Autowired
	POperMapper operMapper;

	@Autowired
	PProductCaseMapper caseMapper;

	@Autowired
	PProductMapper productMapper;

	private static final Logger LOG = LoggerFactory.getLogger(AsynCallFeedBackServiceImpl.class);

	private void packCase(String fromCaseId,String targetCaseId,String tenantId) {
		PProductCase productCase = new PProductCase();
		productCase.setCaseId(fromCaseId);
		productCase.setTenantId(tenantId);
		productCase = caseMapper.findById(productCase);
		if(productCase!=null&&StringUtils.isNotEmpty(targetCaseId)) {
			productCase.setTargetCaseId(targetCaseId);
			caseMapper.update(productCase);
		}
	}

	@Override
	public ResponseResult feedback(Long operId,FeedBackRequestParam feedBackRequestParam) {
		ResponseResult responseResult = new ResponseResult();
		responseResult.setCode("0");
		try {
			String code = feedBackRequestParam.getCode();
			String errCode = feedBackRequestParam.getErrCode();
			String errDesc = feedBackRequestParam.getErrDesc();
			String data = feedBackRequestParam.getData();
			if (StringUtils.isBlank(code)) { // code不能为null
				LOG.error("AsynCallFeedBackServiceImpl.feedback: 请求参数code不能为NULL");
				responseResult.setCode("-1");
				responseResult.setErrCode("101");// 参数不正确
				responseResult.setErrDesc("请求参数code不能为NULL");
				return responseResult;
			}
			POperInfo operInfo  = new POperInfo();
			operInfo.setOperId(operId);
			operInfo = operMapper.findById(operInfo);
			if (operInfo == null) { // 没有找到operId对应的记录
				LOG.error("AsynCallFeedBackServiceImpl.feedback: 根据operId在coper_info表里没有找到对应的记录");
				responseResult.setCode("-1");
				responseResult.setErrCode("101");
				responseResult.setErrDesc("根据operId在coper_info表里没有找到对应的记录");
				return responseResult;
			}
			if(StringUtils.isNotBlank(data)){
				if(data.length()>4096){
					data = data.substring(0, 4096);//表中result_info的长度为2048
				}
			}
			LOG.error("============================1");
			LOG.error("============================1");
			if (code.equals("0")) { // SP成功受理
				LOG.error("============================2");
				operInfo.setResultInfo(data);
				JSONObject busi = JSON.parseObject(operInfo.getBusiInfo());
				JSONObject result = JSON.parseObject(data);
				if(result!=null){
					// TODORENPENGYUAN 

					//通过操作信息回调后判断是否为以来服务，如果为依赖服务，则需要把依赖的所有服务实例instanceId进行修改，这些信息需要在构建csvBusiinfo的时候进行创建，csvBusiInfo则不止需要维护自己的instanceId，还需要维护依赖服务的instanceId。
					if("defaultCreateOperationComponent".equals(operInfo.getServiceName())) {
						if(busi.getBooleanValue("isRelyOnProduct")) {
							if(busi.get("relyOnAttrOrm")!=null) {
								String relyShipOrm = busi.getString("relyOnAttrOrm");
								System.out.println("relyShipOrm:"+relyShipOrm);
								List<ProductRelyOnItem> relyShips = JSONObject.parseArray(relyShipOrm, ProductRelyOnItem.class);
								if(relyShips!=null&&relyShips.size()>=1 ) {
									JSONObject tmpResult = result;
									for(ProductRelyOnItem relyShip:relyShips) {
										//								result = JSON.parseObject(result.getString(busi.getString("serviceId")));//如果是依赖服务的话，需要下钻一层，找到当前服务的return属性值。
										String relyResult = result.getString(relyShip.getRelyOnProductCode());
										if(StringUtils.isNotEmpty(relyResult)) {
											result= JSON.parseObject(relyResult);
											String caseId = relyShip.getRelyOnCaseId();
											String targetCaseId= result.getString("serviceId");
											relyShip.setRelyOnCaseId(StringUtils.isNotEmpty(targetCaseId)?targetCaseId:caseId);
											result =tmpResult;
											packCase(caseId, targetCaseId, operInfo.getTenantId());
										}
									}
									busi.put("relyOnAttrOrm", relyShips);
									result=result.getJSONObject(busi.getString("serviceId"));
								}
							}
						}
						if(result.get("serviceId")!=null){
							String caseId = busi.getString("instanceNo");
							busi.put("instanceNo",result.get("serviceId"));
							operInfo.setBusiInfo(JSON.toJSONString(busi));
							String tenantId = operInfo.getTenantId();
							packCase(caseId, result.getString("serviceId"), tenantId);
						}
					}
				}

				//调用外部接口
				Status  status =callBusinessHandleService(operInfo, code, errCode, errDesc, data);
				if(String.valueOf(status.getCode().intValue()).startsWith("2")){ //返回的状态码是2xx
					operInfo.setState(2);
					operMapper.update(operInfo);
					responseResult.setCode("0");
				} else {
					operInfo.setState(5);
					operMapper.update(operInfo);
					responseResult.setCode("-1");
					responseResult.setErrDesc("BusinessHandleService返回的结果不是2xx");
				}
			}
			if (code.equals("-1")) { // SP返回失败
				operInfo.setState(3);
				operInfo.setResultInfo(data);
				operMapper.update(operInfo);
				responseResult.setCode("0");
				//调用外部接口
				Status  status =callBusinessHandleService(operInfo, code, errCode, errDesc, feedBackRequestParam.getData());
				if(!String.valueOf(status.getCode().intValue()).startsWith("2")){ //返回的状态码不是2xx
					operInfo.setState(5);
					operMapper.update(operInfo);
					responseResult.setCode("-1");
					responseResult.setErrDesc("BusinessHandleService返回的结果不是2xx");
				}
			}	
		} catch (Exception ex) {
			ex.printStackTrace();
			responseResult.setErrCode("999");
			responseResult.setCode("-1");
			return responseResult;
		}
		return responseResult;
	}

	/**
	 * 调用外部接口
	 * @param operInfo
	 * @param code
	 * @param errCode
	 * @param errDesc
	 * @param data
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Status callBusinessHandleService(POperInfo pCase,String code,String errCode,String errDesc,String data){
		String serviceName = pCase.getServiceName();
		//构造接口请求参数
		CSvcBusiInfo cSvcBusiInfo = JSON.parseObject(pCase.getBusiInfo(), CSvcBusiInfo.class);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("code", code);
		result.put("errCode", errCode);
		result.put("errDesc", errDesc);
		LOG.error("============================4");
		Map<String,Object> feedback = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(data)){
			feedback = JSON.parseObject(data,Map.class); 
		}
		//根据服务类型调用相应的接口方法
		if(serviceName.contains("Create")){
			LOG.error("============================3");
			return busiNessHandleService.createSVCOfHandleFeedback(cSvcBusiInfo, result, feedback);
		}
		if(serviceName.contains("Start")){
			return busiNessHandleService.startSVCOfHandleFeedback(cSvcBusiInfo, result);
		}
		if(serviceName.contains("Stop")){
			return busiNessHandleService.offSVCOfHandleFeedback(cSvcBusiInfo, result);
		}
		if(serviceName.contains("Delete")){
			return busiNessHandleService.deleteSVCOfHandleFeedback(cSvcBusiInfo, result);
		}
		if(serviceName.contains("ChangeResource")||serviceName.contains("AddDependency")) {
			return busiNessHandleService.changeResSVCOfHandleFeedback(cSvcBusiInfo, result, feedback);
		}
		return null;
	}
}
