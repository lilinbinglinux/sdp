package com.sdp.serviceAccess.proxy.specific;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.converters.jackson.builder.StringInterningAmazonInfoBuilder;
import com.sdp.common.entity.Status;
import com.sdp.serviceAccess.entity.PProduct;
import com.sdp.serviceAccess.entity.PProductCase;
import com.sdp.serviceAccess.entity.PProductNode;
import com.sdp.serviceAccess.entity.ProductFieldItem;
import com.sdp.serviceAccess.entity.ProductRelyOnItem;
import com.sdp.serviceAccess.mapper.PContinueOrderMapper;
import com.sdp.serviceAccess.mapper.PProductCaseMapper;
import com.sdp.serviceAccess.mapper.PProductMapper;
import com.sdp.serviceAccess.mapper.PProductNodeMapper;
import com.sdp.serviceAccess.mapper.PProductOrderMapper;
import com.sdp.serviceAccess.protyTrans.ProptyTransDom4j;
import com.sdp.serviceAccess.protyTrans.ProtyTransContext;
import com.sdp.serviceAccess.proxy.model.CSvcBusiInfo;
import com.sdp.serviceAccess.service.IPProductService;
import com.sdp.serviceAccess.util.ItemsConvertUtils;

import java.util.*;

/**
 * 信息反馈
 *
 * @author zyz
 * 
 * @Modify: rpy  
 * @reason: 修改与新版控制台产品库整合
 */
@Component
public class BusinessHandleServiceImpl implements BusinessHandleService {

	private static Logger logger = LoggerFactory.getLogger(BusinessHandleServiceImpl.class);

	/**
	 * 实例信息dao
	 */
	@Autowired
	private PProductCaseMapper caseMapper;

	/*
	 * 服务mapper
	 * */
	@Autowired
	private PProductMapper productMapper;

	@Autowired
	private PProductNodeMapper nodeMapper;

	@Autowired
	private PProductOrderMapper orderMapper;

	@Autowired
	private PContinueOrderMapper continueMapper;

	@Autowired
	private IPProductService productService;

	private static final String RESULT_CODE_NAME = "code";

	private static final String RESULT_CODE_SUCCESS = "0";

	public static final String RESULT_CODE_INSTANCE_NO = "serviceId";

	private void packCaseAttr(String packCaseId,String tenantId,String packCaseAttr) {
		PProductCase productCase  = new PProductCase();
		productCase.setCaseId(packCaseId);
		productCase.setTenantId(tenantId);
		productCase = caseMapper.findById(productCase);
		if(productCase!=null) {
			productCase.setCaseAttr(packCaseAttr);
			caseMapper.update(productCase);
		}
	}

	/**
	 * 创建服务回调
	 *
	 * @param cSvcBusiInfo
	 * @param result
	 * @param feedback
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public Status createSVCOfHandleFeedback(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> result, Map<String, Object> feedback) {
		//检查请求参数
		Status status = checkParam(cSvcBusiInfo, result);
		if (status.getCode().intValue() != com.sdp.common.constant.Dictionary.CommonStatus.YES.value.intValue()) {
			status.setCode(com.sdp.common.constant.Dictionary.HttpStatus.INVALID_REQUEST.value);
			return status;
		}
		PProductCase pCase = new PProductCase();
		pCase.setTenantId(cSvcBusiInfo.getTenantId());
		pCase.setCaseId(cSvcBusiInfo.getInstanceNo());
		PProductCase instanceInfo = caseMapper.findById(pCase);
		if (instanceInfo == null) {
			if(cSvcBusiInfo.getServiceId().equalsIgnoreCase("api")||cSvcBusiInfo.getServiceId().equalsIgnoreCase("bdi")){
				status.setCode(com.sdp.common.constant.Dictionary.HttpStatus.OK.value);
				status.setMessage("服务创建成功");
				return status;
			}
			status.setCode(com.sdp.common.constant.Dictionary.HttpStatus.INVALID_REQUEST.value);
			status.setMessage("服务实例不存在");
			return status;
		}

		instanceInfo.setCreateBy(cSvcBusiInfo.getLoginId());
		instanceInfo.setCreateDate(new Date());
		instanceInfo.setTenantId(cSvcBusiInfo.getTenantId());
		instanceInfo.setReceipt((result==null||result.size()==0)?null:JSON.toJSONString(result));
		try {
			if (BusinessHandleServiceImpl.RESULT_CODE_SUCCESS.equals(result.get(BusinessHandleServiceImpl.RESULT_CODE_NAME).toString())) {
				//1 查询服务访问属性
				PProduct product = new PProduct();
				product.setProductId(cSvcBusiInfo.getServiceId());
				product= productMapper.findById(product);
				Map<String,List<ProductFieldItem>> requPros = productService.relyOnProductInfos(product);
				ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
				if(requPros!=null&&requPros.size()>=1) {
					//2.保存访问属性值
					//2.1 保存基本访问属性值
					//2.1.1 替换当前创建完成后的服务instanceInfo值。
					if(result.get(RESULT_CODE_INSTANCE_NO)!=null){
						instanceInfo.setCaseId(result.get(RESULT_CODE_INSTANCE_NO).toString());
					}
					String relyShips = instanceInfo.getCaseRelyShipAttr();
					List<ProductRelyOnItem> relyShipsOrm = null;
					if(StringUtils.isNotEmpty(relyShips)){
						relyShipsOrm = ItemsConvertUtils.convertToRelyOnBean(context.transToBean(relyShips));
					}
					saveBasicAccessValue(instanceInfo,cSvcBusiInfo.getLoginId(), requPros, feedback,relyShipsOrm);

					//2.2 保存集群访问属性信息
					saveNodesAccessValue(instanceInfo,cSvcBusiInfo.getLoginId(), requPros, feedback,relyShipsOrm);
				}
				//3.更新实例状态(服务创建成功默认为启动状态)
				instanceInfo.setCaseStatus(String.valueOf(com.sdp.common.constant.Dictionary.InstanceOrderState.NORMAL.value));
				// 设置服务开通时间
				//                instanceInfo.setOpenDate(new Date());
				// 计算并设置服务过期时间
				//                PProductOrder order = new PProductOrder();
				//                order.setTenantId(instanceInfo.getTenantId());
				//                order.setOrderId(instanceInfo.getOrderId());
				//                order = orderMapper.findById(order);
				//                PContinueOrder continueOrder  = new PContinueOrder();
				//                continueOrder.setTenantId(instanceInfo.getTenantId());
				//                continueOrder.setOrderId(instanceInfo.getOrderId());
				//                List<PContinueOrder> continues = continueMapper.findByCondition(continueOrder);
				//                if(order!=null){
				//                    CSvcAttrValueInfo time = orderDao.findTimeAttrValueByOrderId(orderInfos.get(0).getOrderId(), "model_time");
				//                    Optional<CSvcAttrValueInfo> optional = Optional.ofNullable(time);
				//                    optional.ifPresent(p -> {
				//                        Calendar instance = Calendar.getInstance();
				//                        instance.setTime(instanceInfo.getOpenDate());
				//                        instance.add(Calendar.MONTH, Integer.valueOf(p.getValueObject()));
				//                        Date expireDate = instance.getTime();
				//                        instanceInfo.setExpireDate(expireDate);
				//                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				//                        logger.info("=========开通时间：" + df.format(instanceInfo.getOpenDate()));
				//                        logger.info("=========时间期限：" + p.getValueObject());
				//                        logger.info("=========到期时间：" + df.format(expireDate));
				//                    });
				//                }

				updateInstanceState(instanceInfo, com.sdp.common.constant.Dictionary.InstanceWorkState.RUNNING.value, com.sdp.common.constant.Dictionary.NodeState.RUNNING.value);
			} else {
				updateInstanceState(instanceInfo, com.sdp.common.constant.Dictionary.InstanceWorkState.EXCEPTION.value, com.sdp.common.constant.Dictionary.NodeState.EXCEPTION.value);

				status.setCode(com.sdp.common.constant.Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value);
				status.setMessage((String) result.get("errDesc"));
				return status;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("服务创建回调方法：createSVCOfHandleFeedback出错");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

			if (instanceInfo != null) {
				updateInstanceState(instanceInfo, com.sdp.common.constant.Dictionary.InstanceWorkState.EXCEPTION.value, com.sdp.common.constant.Dictionary.NodeState.EXCEPTION.value);
			}

			status.setCode(com.sdp.common.constant.Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value);
			status.setMessage("服务创建失败");
			return status;
		}

		status.setCode(com.sdp.common.constant.Dictionary.HttpStatus.CREATED.value);
		status.setMessage("服务创建成功");
		return status;
	}

	/**
	 * 启动服务回调
	 *
	 * @param cSvcBusiInfo
	 * @param result
	 * @return
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Status startSVCOfHandleFeedback(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> result) {
		//检查请求参数
		Status status = checkParam(cSvcBusiInfo, result);
		if (status.getCode().intValue() != com.sdp.common.constant.Dictionary.CommonStatus.YES.value.intValue()) {
			return status;
		}
		PProductCase instanceInfo =new PProductCase();
		instanceInfo.setTenantId(cSvcBusiInfo.getTenantId());
		instanceInfo.setCaseId(cSvcBusiInfo.getInstanceNo());
		instanceInfo = caseMapper.findById(instanceInfo);
		if (instanceInfo == null) {
			status.setCode(com.sdp.common.constant.Dictionary.HttpStatus.INVALID_REQUEST.value);
			status.setMessage("服务实例不存在");
			return status;
		}

		if(StringUtils.isNotEmpty(cSvcBusiInfo.getLoginId())){
			instanceInfo.setCreateBy(cSvcBusiInfo.getLoginId());
		}
		instanceInfo.setCreateDate(new Date());

		try {
			if (BusinessHandleServiceImpl.RESULT_CODE_SUCCESS.equals(result.get(BusinessHandleServiceImpl.RESULT_CODE_NAME))) {
				updateInstanceState(instanceInfo, com.sdp.common.constant.Dictionary.InstanceWorkState.RUNNING.value, com.sdp.common.constant.Dictionary.NodeState.RUNNING.value);
			} else {
				//updateInstanceState(instanceInfo,com.bonc.common.constant.Dictionary.InstanceState.EXCEPTION.value, com.bonc.common.constant.Dictionary.NodeState.EXCEPTION.value);
				status.setCode(com.sdp.common.constant.Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value);
				status.setMessage((String) result.get("errDesc"));
				return status;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("服务启动回调方法：startSVCOfHandleFeedback出错");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

			//updateInstanceState(instanceInfo,com.bonc.common.constant.Dictionary.InstanceState.EXCEPTION.value, com.bonc.common.constant.Dictionary.NodeState.EXCEPTION.value);

			status.setCode(com.sdp.common.constant.Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value);
			status.setMessage("服务启动失败");
			return status;
		}

		status.setCode(com.sdp.common.constant.Dictionary.HttpStatus.OK.value);
		status.setMessage("服务启动成功");
		return status;
	}

	/**
	 * 停用服务回调
	 *
	 * @param cSvcBusiInfo
	 * @param result
	 * @return
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Status offSVCOfHandleFeedback(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> result) {
		//检查请求参数
		Status status = checkParam(cSvcBusiInfo, result);
		if (status.getCode().intValue() != com.sdp.common.constant.Dictionary.CommonStatus.YES.value.intValue()) {
			return status;
		}
		PProductCase instanceInfo = new PProductCase();
		instanceInfo.setTenantId(cSvcBusiInfo.getTenantId());
		instanceInfo.setCaseId(cSvcBusiInfo.getInstanceNo());
		instanceInfo = caseMapper.findById(instanceInfo);
		if (instanceInfo == null) {
			status.setCode(com.sdp.common.constant.Dictionary.HttpStatus.INVALID_REQUEST.value);
			status.setMessage("服务实例不存在");
			return status;
		}

		if(StringUtils.isNotEmpty(cSvcBusiInfo.getLoginId())){
			instanceInfo.setCreateBy(cSvcBusiInfo.getLoginId());
		}
		instanceInfo.setCreateDate(new Date());

		try {
			if (BusinessHandleServiceImpl.RESULT_CODE_SUCCESS.equals(result.get(BusinessHandleServiceImpl.RESULT_CODE_NAME))) {
				updateInstanceState(instanceInfo, com.sdp.common.constant.Dictionary.InstanceWorkState.STOP.value, com.sdp.common.constant.Dictionary.NodeState.STOP.value);
			} else {
				//updateInstanceState(instanceInfo,com.bonc.common.constant.Dictionary.InstanceState.EXCEPTION.value, com.bonc.common.constant.Dictionary.NodeState.EXCEPTION.value);
				status.setCode(com.sdp.common.constant.Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value);
				status.setMessage((String) result.get("errDesc"));
				return status;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("服务停止回调方法：startSVCOfHandleFeedback出错");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

			//updateInstanceState(instanceInfo,com.bonc.common.constant.Dictionary.InstanceState.EXCEPTION.value, com.bonc.common.constant.Dictionary.NodeState.EXCEPTION.value);

			status.setCode(com.sdp.common.constant.Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value);
			status.setMessage("服务停止失败");
			return status;
		}

		status.setCode(com.sdp.common.constant.Dictionary.HttpStatus.OK.value);
		status.setMessage("服务停止成功");
		return status;

	}

	/**
	 * 删除服务回调
	 *
	 * @param cSvcBusiInfo
	 * @param result
	 * @return
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Status deleteSVCOfHandleFeedback(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> result) {
		//检查请求参数
		Status status = checkParam(cSvcBusiInfo, result);
		if (status.getCode().intValue() != com.sdp.common.constant.Dictionary.CommonStatus.YES.value.intValue()) {
			return status;
		}

		PProductCase instanceInfo = new PProductCase();
		instanceInfo.setTenantId(cSvcBusiInfo.getTenantId());
		instanceInfo.setCaseId(cSvcBusiInfo.getInstanceNo());
		instanceInfo.setCreateBy(cSvcBusiInfo.getLoginId());
		instanceInfo = caseMapper.findById(instanceInfo);
		if (instanceInfo == null) {
			status.setCode(com.sdp.common.constant.Dictionary.HttpStatus.INVALID_REQUEST.value);
			status.setMessage("服务实例不存在");
			return status;
		}

		try {
			if (BusinessHandleServiceImpl.RESULT_CODE_SUCCESS.equals(result.get(BusinessHandleServiceImpl.RESULT_CODE_NAME))) {
				PProductNode node = new PProductNode();
				node.setTenantId(cSvcBusiInfo.getTenantId());
				node.setCaseId(instanceInfo.getCaseId());
				nodeMapper.deleteByCondition(node);
				//3.删除服务实例
				caseMapper.delete(instanceInfo);
			} else {
				//updateInstanceState(instanceInfo,com.bonc.common.constant.Dictionary.InstanceState.EXCEPTION.value, com.bonc.common.constant.Dictionary.NodeState.EXCEPTION.value);
				status.setCode(com.sdp.common.constant.Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value);
				status.setMessage("服务删除失败");
				return status;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("服务删除回调方法：startSVCOfHandleFeedback出错");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

			//updateInstanceState(instanceInfo,com.bonc.common.constant.Dictionary.InstanceState.EXCEPTION.value, com.bonc.common.constant.Dictionary.NodeState.EXCEPTION.value);

			status.setCode(com.sdp.common.constant.Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value);
			status.setMessage("服务删除失败");
			return status;
		}

		status.setCode(com.sdp.common.constant.Dictionary.HttpStatus.OK.value);
		status.setMessage("服务已经删除");
		return status;
	}


	/**
	 * 保存基本访问信息
	 *
	 * @param instanceInfo 服务实例信息
	 * @param accessInfos  访问属性集合
	 * @param feedback     反馈信息
	 */
	@SuppressWarnings("unchecked")
	private void saveBasicAccessValue(PProductCase instanceInfo,String loginId, Map<String,List<ProductFieldItem>> accessInfos, Map<String, Object> feedback,List<ProductRelyOnItem> relyShips) {
		boolean relyflag = false;
		if(CollectionUtils.isNotEmpty(relyShips)) {
			//说明pack 的访问属性有两个服务的，说明为依赖服务的pack 访问属性
			relyflag = true;
		}
		// TODORENPENGYAUN
		//通过当前的服务实例的依赖数据，把feedback的数据进行实例信息拆分，分别维护。
		ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
		if(relyflag) {
			Map<String,Object> tmpFeedBack= feedback;
			for(ProductRelyOnItem relyOnItem:relyShips) {
				String relyOnCaseId = relyOnItem.getRelyOnCaseId();
				String relyOnProductId =relyOnItem.getRelyOnProductCode();
				List<ProductFieldItem> fieldItems = accessInfos.get(relyOnProductId);
				if(CollectionUtils.isNotEmpty(fieldItems)) {
					String basicAccessValue= null;
					//如果为 依赖服务的，则feedback外面还有一层，如果不是，则和原来一样处理
					feedback = (Map<String, Object>) feedback.get(relyOnProductId);
					List<ProductFieldItem> caseAttrOrm = new ArrayList<>();
					for(ProductFieldItem info:fieldItems) {
						if(feedback!=null&&feedback.size()>=1) {
							Object obj = feedback.get(info.getProCode());
							if(obj!=null) {
								basicAccessValue= String.valueOf(obj);
								if (basicAccessValue!=null&&basicAccessValue.trim().length()>=1&&!"null".equals(basicAccessValue)) {
									info.setProValue(basicAccessValue);
									caseAttrOrm.add(info);
								}
							}
						}
					}
					packCaseAttr(relyOnCaseId, instanceInfo.getTenantId(), context.transToXml(ItemsConvertUtils.convertToFieldPro(caseAttrOrm), "fieldItems", "fieldItem"));
					feedback = tmpFeedBack;
				}
			}
			feedback= (Map<String, Object>) feedback.get(instanceInfo.getProductId());
		}

		List<ProductFieldItem> fieldItems = accessInfos.get(instanceInfo.getProductId());
		List<ProductFieldItem> caseAttrOrm = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(fieldItems)) {
			String basicAccessValue= null;
			for(ProductFieldItem info:fieldItems) {
				if(feedback!=null&&feedback.size()>=1) {
					Object obj = feedback.get(info.getProCode());
					if(obj!=null) {
						basicAccessValue= String.valueOf(obj);
						if (basicAccessValue!=null&&basicAccessValue.trim().length()>=1&&!"null".equals(basicAccessValue)) {
							info.setProValue(basicAccessValue);
							caseAttrOrm.add(info);
						}
					}
				}
			}
			instanceInfo.setCaseAttr(context.transToXml(ItemsConvertUtils.convertToFieldPro(caseAttrOrm), "fieldItems", "fieldItem"));
			caseMapper.update(instanceInfo);
		}

	}

	/**
	 * 保存集群访问信息
	 *
	 * @param instanceInfo 服务实例信息
	 * @param accessInfos  访问属性集合
	 * @param nodes        集群属性信息
	 */
	@SuppressWarnings("unchecked")
	private void saveNodesAccessValue(PProductCase instanceInfo,String loginId, Map<String,List<ProductFieldItem>> accessInfos, Map<String,Object> feedback,List<ProductRelyOnItem> relyShips) {
		boolean relyflag = false;
		if(CollectionUtils.isNotEmpty(relyShips)) {
			//说明pack 的访问属性有两个服务的，说明为依赖服务的pack 访问属性
			relyflag = true;
		}
		// TODORENPENGYAUN
		Map<String,Object> tmpFeedBack= feedback;
		List<PProductNode> nodesP = new ArrayList<PProductNode>();
		if (relyflag) {
			for (ProductRelyOnItem relyShip : relyShips) {
				List<ProductFieldItem> fieldItems = accessInfos.get(relyShip.getRelyOnProductCode());
				if (fieldItems != null && fieldItems.size() >= 1) {
					// 如果为 依赖服务的，则feedback外面还有一层，如果不是，则和原来一样处理
					feedback = (Map<String, Object>) feedback.get(relyShip.getRelyOnProductCode());
					packNodes(instanceInfo, nodesP, feedback, fieldItems);
					feedback = tmpFeedBack;
				}
			}
			feedback = (Map<String, Object>) feedback.get(instanceInfo.getProductId());
		}
		//本服务的nodes
		packNodes(instanceInfo, nodesP, feedback, accessInfos.get(instanceInfo.getProductId()));
		nodeMapper.save(nodesP);
	}

	private void packNodes(PProductCase instanceInfo,List<PProductNode> nodesP,Map<String,Object> feedback,List<ProductFieldItem> fieldItems) {
		if(feedback!=null&&feedback.size()>=1) {
			List<Map<String,Object>> nodesInfo = (List<Map<String, Object>>) feedback.get("nodes");
			ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
			if(nodesInfo==null||nodesInfo.size()==0) {
				return ;
			}
			for (Map<String, Object> map : nodesInfo) {
				//2.2.1 保存节点信息
				if(map.get("id") != null){
					PProductNode instanceNode = new PProductNode();
					nodesP.add(instanceNode);
					instanceNode.setCaseId(instanceInfo.getCaseId());
					instanceNode.setCaseStatus(String.valueOf(com.sdp.common.constant.Dictionary.NodeState.UNSTART.value));
					instanceNode.setNodeId((String) map.get("id"));
					instanceNode.setCreateDate(new Date());
					instanceNode.setUpdateDate(new Date());
					instanceNode.setCreateBy(instanceInfo.getCreateBy());
					instanceNode.setTenantId(instanceInfo.getTenantId());
					//2.2.2 保存节点访问属性值
					List<ProductFieldItem> caseAttrOrm = new ArrayList<>();
					for(ProductFieldItem info:fieldItems) {
						Object obj = map.get(info.getProCode());
						if(obj!=null) {
							String basicAccessValue= String.valueOf(obj);
							if (basicAccessValue!=null&&basicAccessValue.trim().length()>=1&&!"null".equals(basicAccessValue)) {
								info.setProValue(basicAccessValue);
								caseAttrOrm.add(info);
							}
						}
					}
					instanceNode.setCaseAttr(context.transToXml(ItemsConvertUtils.convertToFieldPro(fieldItems), "fieldItems", "fieldItem"));
				}
			}
		}
	}

	/**
	 * 更新实例状态
	 *
	 * @param instanceInfo  实例信息
	 * @param instanceState 实例状态
	 * @param nodeState     节点状态
	 */
	private void updateInstanceState(PProductCase instanceInfo, Integer instanceState, Integer nodeState) {
		//1.更改实例状态
		if (instanceState != null) {
			instanceInfo.setCaseStatus(instanceState.toString());
			System.out.println(JSON.toJSONString(instanceInfo));
			caseMapper.update(instanceInfo);
		}
		//2.实例若是集群，更改节点状态
		if (nodeState != null) {
			PProductNode node = new PProductNode();
			node.setTenantId(instanceInfo.getTenantId());
			node.setCaseId(instanceInfo.getCaseId());
			List<PProductNode> nodeInfos = nodeMapper.findByCondition(node);
			if (nodeInfos != null) {
				for (PProductNode nodeInfo : nodeInfos) {
					nodeInfo.setCaseStatus(nodeState.toString());
					nodeMapper.update(nodeInfo);
				}
			}
		}
	}

	/**
	 * 检查方法参数
	 *
	 * @param cSvcBusiInfo
	 * @param result
	 * @return
	 */
	private Status checkParam(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> result) {
		Status status = new Status();
		if (cSvcBusiInfo == null || result == null || cSvcBusiInfo.getServiceId() == null || cSvcBusiInfo.getInstanceNo() == null) {
			status.setCode(com.sdp.common.constant.Dictionary.HttpStatus.INVALID_REQUEST.value);
			status.setMessage("方法参数不正确");
			logger.info("====方法参数不正确====");
		} else {
			status.setCode(com.sdp.common.constant.Dictionary.CommonStatus.YES.value);
		}
		return status;
	}

	@Transactional
	@Override
	public Status changeResSVCOfHandleFeedback(CSvcBusiInfo cSvcBusiInfo, Map<String, Object> result,
			Map<String, Object> feedback) {
		//检查请求参数
		Status status = checkParam(cSvcBusiInfo, result);
		if (status.getCode().intValue() != com.sdp.common.constant.Dictionary.CommonStatus.YES.value.intValue()) {
			status.setCode(com.sdp.common.constant.Dictionary.HttpStatus.INVALID_REQUEST.value);
			return status;
		}
		PProductCase pCase = new PProductCase();
		pCase.setTenantId(cSvcBusiInfo.getTenantId());
		pCase.setCaseId(cSvcBusiInfo.getInstanceNo());
		PProductCase instanceInfo = caseMapper.findById(pCase);
		if (instanceInfo == null) {
			status.setCode(com.sdp.common.constant.Dictionary.HttpStatus.INVALID_REQUEST.value);
			status.setMessage("服务实例不存在");
			return status;
		}

		instanceInfo.setCreateBy(cSvcBusiInfo.getLoginId());
		instanceInfo.setCreateDate(new Date());
		instanceInfo.setTenantId(cSvcBusiInfo.getTenantId());
		instanceInfo.setReceipt((result==null||result.size()==0)?null:JSON.toJSONString(result));
		try {
			if (BusinessHandleServiceImpl.RESULT_CODE_SUCCESS.equals(result.get(BusinessHandleServiceImpl.RESULT_CODE_NAME).toString())) {
				//1 查询服务访问属性
				PProduct product = new PProduct();
				product.setProductId(cSvcBusiInfo.getServiceId());
				product= productMapper.findById(product);
				Map<String,List<ProductFieldItem>> requPros = productService.relyOnProductInfos(product);
				ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
				if(requPros!=null&&requPros.size()>=1) {

					String relyShips = instanceInfo.getCaseRelyShipAttr();
					List<ProductRelyOnItem> relyShipsOrm = null;
					if(StringUtils.isNotEmpty(relyShips)){
						relyShipsOrm = ItemsConvertUtils.convertToRelyOnBean(context.transToBean(relyShips));
					}
					saveBasicAccessValue(instanceInfo,cSvcBusiInfo.getLoginId(), requPros, feedback,relyShipsOrm);

					//2.1 删除原来的节点属性
					deleteNodes(instanceInfo, relyShipsOrm);
					//2.2 更新集群访问属性信息
					saveNodesAccessValue(instanceInfo,cSvcBusiInfo.getLoginId(), requPros, feedback,relyShipsOrm);
				}
				//3.更新实例状态(服务创建成功默认为启动状态)
				instanceInfo.setCaseStatus(String.valueOf(com.sdp.common.constant.Dictionary.InstanceOrderState.NORMAL.value));

				updateInstanceState(instanceInfo, com.sdp.common.constant.Dictionary.InstanceWorkState.RUNNING.value, com.sdp.common.constant.Dictionary.NodeState.RUNNING.value);
			} else {
				updateInstanceState(instanceInfo, com.sdp.common.constant.Dictionary.InstanceWorkState.EXCEPTION.value, com.sdp.common.constant.Dictionary.NodeState.EXCEPTION.value);

				status.setCode(com.sdp.common.constant.Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value);
				status.setMessage((String) result.get("errDesc"));
				return status;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("变更资源：changeResSVCOfHandleFeedback出错");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

			if (instanceInfo != null) {
				updateInstanceState(instanceInfo, com.sdp.common.constant.Dictionary.InstanceWorkState.EXCEPTION.value, com.sdp.common.constant.Dictionary.NodeState.EXCEPTION.value);
			}

			status.setCode(com.sdp.common.constant.Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value);
			status.setMessage("服务创建失败");
			return status;
		}

		status.setCode(com.sdp.common.constant.Dictionary.HttpStatus.CREATED.value);
		status.setMessage("服务创建成功");
		return status;
	}

	private void deleteNodes(PProductCase isntanceInfo,List<ProductRelyOnItem> relyShipsOrm) {
		List<String> params = new ArrayList<String>();
		if(relyShipsOrm!=null&&relyShipsOrm.size()>=0) {
			for(ProductRelyOnItem item:relyShipsOrm) {
				if(StringUtils.isNotEmpty(item.getRelyOnCaseId())) {
					params.add(item.getRelyOnCaseId());
				}
			}
		}
		params.add(isntanceInfo.getCaseId());
		PProductNode node= new PProductNode();
		node.setTenantId(isntanceInfo.getTenantId());
		node.setCaseIds(params);
		nodeMapper.deleteByCondition(node);
	}
}
