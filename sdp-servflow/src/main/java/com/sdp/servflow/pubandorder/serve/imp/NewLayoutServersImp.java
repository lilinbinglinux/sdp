package com.sdp.servflow.pubandorder.serve.imp;

import static com.sdp.servflow.pubandorder.util.MapUtil.getEndNode;
import static com.sdp.servflow.pubandorder.util.MapUtil.getStartNode;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.frame.util.StringUtil;
import com.sdp.servflow.logSer.log.busi.ser.LogManage;
import com.sdp.servflow.logSer.log.exception.DataMissingException;
import com.sdp.servflow.logSer.log.model.LogBean;
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
import com.sdp.servflow.pubandorder.node.model.node.StartNode;
import com.sdp.servflow.pubandorder.serve.CheckParam;
import com.sdp.servflow.pubandorder.serve.LayoutServer;
import com.sdp.servflow.pubandorder.serve.ParameterMapping;
import com.sdp.servflow.pubandorder.serve.ServeAuth;
import com.sdp.servflow.pubandorder.serve.format.FormatConversion;
import com.sdp.servflow.pubandorder.serve.model.Param;
import com.sdp.servflow.pubandorder.serve.model.ProtocolData;
import com.sdp.servflow.pubandorder.util.IContants;

/**
 * 服务编排新的流程
 * 
 * @author 任壮
 * @date 2018年1月29日
 */

//@SuppressWarnings("unused")
@Service("layoutServer")
// @Scope("prototype")
public class NewLayoutServersImp implements LayoutServer {

	/***
	 * 参数校验
	 */
	@Autowired
	private CheckParam checkParam;

	@Autowired
	private ServeAuth serveAuth;

	@Autowired
	private LogManage log;


	/**
	 * Description: 获取当前的系统时间（sql类型的）
	 *
	 * @return Timestamp
	 * @see
	 */
	private Timestamp getCurrentTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	/****
	 * order_name 必传 "ser_id" "ser_version" "order_id" requestID UUID "ipAddr"
	 * "appId" "tenant_id" "urlParam" //服务编排
	 * url必传
	 * 
	 * sourceType 0同步 1异步 2cas
	 *
	 * publicParam : ser_id 服务的id tenant_id 租户id
	 */
	@Override
	public Response analysis(HashMap<String, Object> publicParam, String busiParm, HttpClient httpClient,
			String esbXml) {
		busiParm = busiParm.trim();
		String order_id = (String) publicParam.get("order_id");
		String sourceType = (String) publicParam.get("sourceType");
		String order_name = (String) publicParam.get("order_name");
		String ser_id = (String) publicParam.get("ser_id");
		String url  = (String) publicParam.get("url");
		// String version = (String)publicParam.get("ser_version");
		String tenant_id = (String) publicParam.get("tenant_id");
		String requestID = (String) publicParam.get("requestID");
		Map<String, Object> urlParam = (Map<String, Object>) publicParam.get("urlParam");
		//TODO 将这里的url存入数据库中
		Param param = new Param();
		param.setUrl(url);
		param.setBusiParm(busiParm);
		param.setOrder_id(order_id);
		param.setSourceType(sourceType);
		param.setRequestID(requestID);
		param.setSer_id(ser_id);
		param.setSourceType(sourceType);
		param.setTenant_id(tenant_id);
		param.setUrlParam(urlParam);
		param.setOrder_name(order_name);
		param.setAppId((String)publicParam.get("appId"));
		Process process= null;
		Response res = null;
		try {
			process = new Process(esbXml, param);
			StartNode startNode = process.getStartNode();
			res = process.analysis(param, false, startNode.getNodeId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
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
}
