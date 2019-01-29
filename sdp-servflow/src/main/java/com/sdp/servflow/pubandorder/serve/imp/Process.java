package com.sdp.servflow.pubandorder.serve.imp;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.sdp.frame.util.SpringUtils;
import com.sdp.frame.util.StringUtil;
import com.sdp.servflow.logSer.log.busi.ser.LogManage;
import com.sdp.servflow.logSer.log.exception.DataMissingException;
import com.sdp.servflow.logSer.log.model.LogBean;
import com.sdp.servflow.pubandorder.cache.NodesCacheModel;
import com.sdp.servflow.pubandorder.common.Response;
import com.sdp.servflow.pubandorder.init.ResponseCollection;
import com.sdp.servflow.pubandorder.node.analyze.factory.NodeFactoryImp;
import com.sdp.servflow.pubandorder.node.model.field.Field;
import com.sdp.servflow.pubandorder.node.model.field.XmlNode;
import com.sdp.servflow.pubandorder.node.model.node.ConditionNode;
import com.sdp.servflow.pubandorder.node.model.node.EndNode;
import com.sdp.servflow.pubandorder.node.model.node.GroupNode;
import com.sdp.servflow.pubandorder.node.model.node.InterfaceNode;
import com.sdp.servflow.pubandorder.node.model.node.Node;
import com.sdp.servflow.pubandorder.node.model.node.NodeJoin;
import com.sdp.servflow.pubandorder.node.model.node.StartNode;
import com.sdp.servflow.pubandorder.serve.CheckParam;
import com.sdp.servflow.pubandorder.serve.ParameterMapping;
import com.sdp.servflow.pubandorder.serve.ServeAuth;
import com.sdp.servflow.pubandorder.serve.format.FormatConversion;
import com.sdp.servflow.pubandorder.serve.model.Param;
import com.sdp.servflow.pubandorder.serve.model.ProtocolData;
import com.sdp.servflow.pubandorder.util.IContants;
import com.sdp.servflow.pubandorder.util.MapUtil;
import com.sdp.util.SpringUtil;

/**
 * 流程解析
 * 
 * 
 * 
 * 存对象时候不要使用threadlocal，否则并发执行会出现问题
 * 
 * @author 任壮
 * @date 2018年1月29日
 */
public class Process {
	private static final Logger LOGGER = Logger.getLogger(Process.class);
	// 线程池
	private static final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
	// 流程图需要的xml信息

	private static final ParameterMapping mapping = SpringUtils.getBean(ParameterMapping.class);

	private static final CheckParam checkParam = SpringUtil.getBean(CheckParam.class);

	private static final LogManage log = SpringUtil.getBean(LogManage.class);

	private static final ServeAuth serveAuth = SpringUtil.getBean(ServeAuth.class);

	final Map<String, ProtocolData> resource = new ConcurrentHashMap<String, ProtocolData>();

	// 自定义的数据库流程
	private String esbxml;
	// 业务参数和业务参数
	private Param param;
	// 所有节点的集合
	private Map<String, Node> nodes;

	private Map<String,XmlNode> xmlNodes; //解析完毕后的xmlNodes

	private EndNode endNode; //解析完毕后的endNode

	private StartNode startNode; //解析完毕后的startNode

	/**
	 * Description: 获取当前的系统时间（sql类型的）
	 *
	 * @return Timestamp
	 * @see
	 */
	private Timestamp getCurrentTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 对象转换
	 * 
	 * @param param
	 * @return
	 */
	private LogBean conversionFromParam(Param param) {
		LogBean serLog = new LogBean();
		serLog.setOrder_name(param.getOrder_name());
		serLog.setRequestId(param.getRequestID());
		serLog.setPubapiId(param.getSer_id());
		serLog.setOrderid(param.getOrder_id());
		serLog.setRequestMsg(param.getBusiParm());
		serLog.setTenant_id(param.getTenant_id());

		return serLog;

	}

	/**
	 * Description: 传入esbXml将其解析为不同类型的对象
	 * 
	 * @param esbXml
	 * @return Map<String,Node>
	 * @throws Exception
	 * @see
	 */
	private Map<String, Node> getNodes(String esbXml, NodeFactoryImp factory) throws Exception {
		InputStream is = new ByteArrayInputStream(esbXml.getBytes("utf-8"));
		SAXBuilder sb = new SAXBuilder();
		Document doc = sb.build(is);
		Element sourceRoot = doc.getRootElement();
		List<Element> list = sourceRoot.getChildren();
		Map<String, Node> map = new TreeMap<String, Node>();

		for (Element element : list) {
			Node node = factory.createNode(element);
			map.put(node.getNodeId(), node);

		}
		return map;
	}
	/******
	 * Description: 对请求参数进行校验
	 * 
	 * @param protocolData
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @see
	 */
	private String checkParam(StartNode node, ProtocolData protocolData) throws Exception {

		if (!protocolData.getReqformat().equals(IContants.JSON)) {
			// 只对json数据进行校验
			return null;
		}

		String checkResult = null;
		String parmType = null;
		List<Object> value = null;
		for (Field field : node.getParam().getFildList()) {
			// pub表中的数据类型(请求头 请求体等信息)
			parmType = field.getLocation();
			value = null;
			if (IContants.REQBODY.equals(parmType)) {
				if (StringUtil.isNotEmpty(field.getPath())) {
					value = FormatConversion.getValues((String) (protocolData.getRequestBody()), field, null);
				}
			}
			/** 校验参数 */
			String reqtype = field.getType();
			String maxlength = field.getLength();
			boolean isMust = field.isMust();
			checkResult = checkParam.checkParam(value, isMust, reqtype, null, maxlength);
			if (checkResult != null) {
				// 不满足条件
				checkResult = "[" + field.getName() + "]" + checkResult;
				break;
			}
		}

		return checkResult;
	}

	/***
	 * Description: 调用注册服务
	 * 
	 * @param parm
	 *            传入参数 json形式的字符串
	 * @param requestID
	 * @param param2
	 *            注册服务相关参数
	 * @return String json形式的字符串
	 * @throws Exception
	 * @see
	 */
	private ProtocolData sendMessage(final ProtocolData parm, final InterfaceNode pubInterface,
			final HttpClient httpClient, String requestID, Param param2) throws Exception {
		String order_id = param2.getOrder_id();
		String sourceType = param2.getSourceType();
		String busiParm = (String) parm.getRequestBody();
		String order_name = (String) param2.getOrder_name();
		Timestamp start = getCurrentTime();
		ProtocolData data = null;
		String retunCode = "00000";
		try {
			data = serveAuth.server(pubInterface, parm, httpClient);
		} catch (Exception e) {
			retunCode="40001";
			e.printStackTrace();
		}
		//TODO 保存在数据库中
		// 保存日志
		String tenant_id = (String) param2.getTenant_id();
		LogBean serLog = new LogBean();
		serLog.setRequestId(requestID);
		serLog.setOrder_name(order_name);
		serLog.setLogType(0);
		serLog.setPubapiId(pubInterface.getNodeId());
		serLog.setOrderid(order_id);
		serLog.setUrl(parm.getUrl());
		serLog.setRequestMsg(busiParm);
		serLog.setTenant_id(tenant_id);
		serLog.setStartTime(start);
		serLog.setEndTime(getCurrentTime());
		serLog.setSourceType(sourceType);
		serLog.setResponseMsg((String) data.getResponseBody());

		//日志的code 编码 00000代表接口正常返回   40001代表接口异常
		serLog.setCode(retunCode);

		if (StringUtils.isNotBlank(requestID)) {
			log.addLog(serLog);
		}

		return data;
	}

	/***
	 * 获取下一个节点
	 * 
	 * @param currentNodeId
	 * @return
	 */
	private List<String> getNextNdoeList(String currentNodeId) {
		Node node = nodes.get(currentNodeId);
		return node == null ? null : node.getTargetlist();
	}
	/**
	 * 获取下一个聚合结束节点
	 * @param currentNodeId
	 * @return
	 */
	private String getNextGroupNodeId(String currentNodeId) {
		Node node = nodes.get(currentNodeId);

		if(node.getNodeStyle().equalsIgnoreCase(((Node.groupNodeEndStyle)))) {
			System.out.println("-聚合节点-"+node.getNodeId());

			return node.getNodeId();
		}

		List<String> list =  node == null ? null : node.getTargetlist();
		if(list!=null) {

			return getNextGroupNodeId(list.get(0));

		}
		return null;
	}

	/***
	 * 
	 * @param param
	 * @param esbXml
	 *            Map<String, Node> nodes = getNodes(esbXml, factory);
	 * @param isChild
	 * @param startNodeId
	 * @return
	 */
	public Response analysis(final Param param, boolean isChild, String startNodeId) {
		Timestamp startTime = getCurrentTime();

		Response response = null;
		ProtocolData respParm = null;
		// 初始化日志
		LogBean serLog = conversionFromParam(param);
		serLog.setLogType(1);
		// 本次访问的唯一编码
		try {
			// 获取所有id的集合
			final Map<String, XmlNode> xmlNodes = this.xmlNodes;
			//  这里由适用方传入
			// StartNode startNode = (StartNode) getStartNode(nodes);

			EndNode endNode = this.endNode;
			String preNodeId = "";// 第一个节点是开始节点 前一个节点是空字符串
			ProtocolData protocolData = new ProtocolData();
			protocolData.setRequestBody(param.getBusiParm());
			protocolData.setUrlParam(param.getUrlParam());
			protocolData.setResponseBody(param.getBusiParm());
			protocolData.setReposneformat(endNode.getParam().getFormat());

			String checkResult = null;
			List<String> theseNodes;
			if (isChild) {

				NodeJoin nodeJoin = (NodeJoin) (nodes.get(startNodeId));
				//protocolData.setReqformat(interfaceNode.getInParameter().getFormat());
				//resource.put(interfaceNode.getNodeId(), protocolData);
				// 存放的是本次需要处理的信息
				theseNodes = nodeJoin.getTargetlist();
				//当前节点的值
				preNodeId = nodeJoin.getNodeId();

			} else {
				StartNode startNode = (StartNode) (nodes.get(startNodeId));
				protocolData.setReqformat(startNode.getParam().getFormat());
				resource.put(startNode.getNodeId(), protocolData);
				checkResult = checkParam(startNode, protocolData);
				// 存放的是本次需要处理的信息
				theseNodes = startNode.getTargetlist();
			}
			if (checkResult != null) {
				response = ResponseCollection.getSingleStion().get(7);
				response.setResult(checkResult);
				return response;
			}

			// 可用参数集合
			Map<String, Object> respMap = null;

			boolean stopLoopFlag = false;
			// 前一个服务的id

			CountDownLatch coumtDownLatch = null;
			do {
				// 本次需要循环的list
				int serSize = theseNodes.size();
				// 不满足条件的集合
				int errCondotion = 0;
				loop: for (String nodeId : theseNodes) {

					// 1代表的是条件判断
					String typeId = nodes.get(nodeId).getNodeStyle();
					if (Node.lineNodeStyle.equals(typeId)) {
						ConditionNode conditionNode = (ConditionNode) nodes.get(nodeId);
						// 遍历当前符合条件的节点
						// boolean condition = JudgeCondition(conditionNode,resource);
						boolean condition = mapping.JudgeCondition(conditionNode, resource, xmlNodes);
						if (!condition) {// 判断是否满足条件(不符合条件的开始执行下个条件判断)
							errCondotion++;
							continue loop;
						}
						theseNodes = conditionNode.getTargetlist();
						preNodeId = conditionNode.getNodeId();
					} else if (Node.groupNodeStartStyle.equals(typeId)) {

						// 碰见聚合开始节点，接下来的每个接口必须开启一个线程去处理
						GroupNode groupNode = (GroupNode) nodes.get(nodeId);

						//当前的开始聚合节点的下一个节点是执行聚合结束节点
						preNodeId = groupNode.getNodeId();
						List<String> list = new ArrayList<>();
						list.add(getNextGroupNodeId(preNodeId));
						theseNodes = list;

						List<String> nextNodes = groupNode.getTargetlist();

						int count = nextNodes == null ? 0 : nextNodes.size();
						final CountDownLatch curcoumtDownLatch = new CountDownLatch(count);
						coumtDownLatch = curcoumtDownLatch;

						if (theseNodes != null) {
							for ( final String cnodeId : nextNodes) {
								/*		List<String> nodeList = getNextNdoeList(cnodeId);
								final String nextNodeId = nodeList==null?null:nodeList.get(0);*/
								if(cnodeId != null) {
									cachedThreadPool.execute(new Runnable() {
										@Override
										public void run() {
											try {
												//  开启不同的线程去执行操作 (递归开线程调用)
												// resource.put("这里要被替换掉 ", null);

												analysis(param, true, cnodeId);
											} catch (Exception e) {
												e.printStackTrace();
											} finally {
												// 在执行到最后的时候执行减的操作。（让主线程中的聚合节点去await这个操作）

												curcoumtDownLatch.countDown();
											}
										}
									});
								}
							}
						}
					} else if (Node.groupNodeEndStyle.equals(typeId)) {

						if (isChild) {
							System.out.println("--子节点到了聚合节点---");
							return null; // 对于子流程执行到这个地方就可以返回了
						}

						// 聚合节点
						//  所有的子节点都执行完毕，才去执行这个操作
						LOGGER.info("到达聚合节点之前");
						if(coumtDownLatch !=null)
							coumtDownLatch.await();
						LOGGER.info("到达聚合节点之后");
						GroupNode node = (GroupNode) nodes.get(nodeId);
						theseNodes = node.getTargetlist();
						preNodeId = node.getNodeId();

					}

					else if (Node.interfaceNodeStyle.equals(typeId)) {

						//  执行接口的时候要保证之前所有的线程都执行完毕（把之前相关服务的线程狗加入join中）
						// 等待join都结束的时候 新开个线程保存本次的接口调用
						// 每次执行的时候吧线程保存到主线程中

						InterfaceNode node = (InterfaceNode) nodes.get(nodeId);
						// 执行本次的接口 并将下一次需要遍历的服务存入
						ConditionNode conditionNode = (ConditionNode) nodes.get(preNodeId);

						ProtocolData target = mapping.mapping(conditionNode, resource, xmlNodes);
						target.setReqformat(node.getInParameter().getFormat());
						target.setReposneformat(node.getOutParameter().getFormat());
						theseNodes = node.getTargetlist();
						preNodeId = node.getNodeId();
						// 服务调用（调用完后将返回值添加到resouce中 供后面的该服务调用）
						// System.out.println("服务的请求值"+target);
						LOGGER.info("服务的请求值" + target);

						respParm = sendMessage(target, node, new HttpClient(), param.getRequestID(), param);
						LOGGER.info("服务的响应值" + respParm);
						// 校验失败的信息
						if (respMap != null) {
							String checkFail = (String) respMap.get("checkFail");
							if (checkFail != null && checkFail.startsWith("[")) {
								// 校验失败
								stopLoopFlag = true;
								response = ResponseCollection.getSingleStion().get(7);
								response.setResult(checkFail);
								break loop;
							}
						}
						// 将返回参数做为传入参数传入公用的参数中
						resource.put(preNodeId, respParm);
					} else if (Node.endNodeStyle.equals(typeId)) {
						EndNode end = (EndNode) nodes.get(nodeId);
						ConditionNode conditionNode = (ConditionNode) nodes.get(preNodeId);
						ProtocolData target = mapping.mapping(conditionNode, resource, xmlNodes);
						target.setReqformat(end.getParam().getFormat());
						target.setReposneformat(end.getParam().getFormat());
						// 服务调用（调用完后将返回值添加到resouce中 供后面的该服务调用）
						LOGGER.info("服务的最后的映射为" + target);
						theseNodes = conditionNode.getTargetlist();
						preNodeId = conditionNode.getNodeId();
						// 代表是最后一个服务 结束前的映射
						respParm = target;
						stopLoopFlag = true;
						break loop;
					}

				}
				if (serSize == errCondotion) {
					response = ResponseCollection.getSingleStion().get(11);
					break;
				}
			} while (theseNodes != null && theseNodes.size() > 0 && !stopLoopFlag);

			// System.out.println("respParm"+respParm);
			if (response == null) {
				// 未处理的response认为是正常编排出的服务 最后一次相当于只有 入参的转换
				response = ResponseCollection.getSingleStion().get(1);
				response.setResult((String) respParm.getRequestBody());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = ResponseCollection.getSingleStion().get(10);
			response.setResult(e.toString());
		}
		serLog.setStartTime(startTime);
		serLog.setUrl(param.getUrl());
		serLog.setEndTime(getCurrentTime());
		serLog.setResponseMsg(response.getResult());
		serLog.setSourceType(param.getSourceType());
		serLog.setCode(response.getRespCode());
		// 子节点嵌套的不进行日志存储
		if (StringUtils.isNotBlank(param.getRequestID()) && !isChild) {
			try {
				log.addLog(serLog);
			} catch (DataMissingException e) {
				e.printStackTrace();
			}

		}
		return response;
	}

	public String getEsbxml() {
		return esbxml;
	}

	public void setEsbxml(String esbxml) {
		this.esbxml = esbxml;
	}

	public Param getParam() {
		return param;
	}

	public void setParam(Param param) {
		this.param = param;
	}

	public Process(String esbxml, Param param) throws Exception{
		super();
		this.esbxml = esbxml;
		this.param = param;
		NodeFactoryImp factory = NodeFactoryImp.getOneNodeFactory();
		NodesCacheModel nodesCache = NodesCacheModel.getcacheModel(param.getAppId());
		if(nodesCache==null){
			synchronized(Object.class){
				LOGGER.info(Thread.currentThread().getName()+"初始化缓存。。。。");
				nodesCache =  NodesCacheModel.getcacheModel(param.getAppId());
				if(nodesCache==null){
					Map<String, Node> tmpNodes= getNodes(esbxml, factory);
					nodesCache = new NodesCacheModel();
					nodesCache.setAppId(param.getAppId());
					nodesCache.setEndNode((EndNode)MapUtil.getEndNode(tmpNodes));
					nodesCache.setNodes(tmpNodes);
					nodesCache.setStartNode((StartNode)MapUtil.getStartNode(tmpNodes));
					nodesCache.setTenantId(param.getTenant_id());
					nodesCache.setXmlNodes(factory.getXmlNode(tmpNodes.values()));
					NodesCacheModel.setCacheModel(param.getAppId(), nodesCache);
				}
			}
		}
		LOGGER.info(Thread.currentThread().getName()+"使用缓存加载process。");
		this.endNode= nodesCache.getEndNode();
		this.startNode= nodesCache.getStartNode();
		this.xmlNodes = nodesCache.getXmlNodes();
		this.nodes= nodesCache.getNodes();
	}

	public Map<String, Node> getNodes() {
		return nodes;
	}

	public void setNodes(Map<String, Node> nodes) {
		this.nodes = nodes;
	}

	public Map<String, XmlNode> getXmlNodes() {
		return xmlNodes;
	}

	public void setXmlNodes(Map<String, XmlNode> xmlNodes) {
		this.xmlNodes = xmlNodes;
	}

	public EndNode getEndNode() {
		return endNode;
	}

	public void setEndNode(EndNode endNode) {
		this.endNode = endNode;
	}

	public StartNode getStartNode() {
		return startNode;
	}

	public void setStartNode(StartNode startNode) {
		this.startNode = startNode;
	}

}
