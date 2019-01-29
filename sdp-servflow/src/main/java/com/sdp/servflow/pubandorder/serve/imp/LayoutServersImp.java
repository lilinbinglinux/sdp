package com.sdp.servflow.pubandorder.serve.imp;


import com.sdp.frame.util.StringUtil;
import com.sdp.servflow.logSer.log.busi.ser.LogManage;
import com.sdp.servflow.logSer.log.exception.DataMissingException;
import com.sdp.servflow.logSer.log.model.LogBean;
import com.sdp.servflow.pubandorder.common.Response;
import com.sdp.servflow.pubandorder.init.ResponseCollection;
import com.sdp.servflow.pubandorder.node.analyze.factory.NodeFactoryImp;
import com.sdp.servflow.pubandorder.node.model.field.Field;
import com.sdp.servflow.pubandorder.node.model.field.XmlNode;
import com.sdp.servflow.pubandorder.node.model.node.*;
import com.sdp.servflow.pubandorder.serve.CheckParam;
import com.sdp.servflow.pubandorder.serve.LayoutServer;
import com.sdp.servflow.pubandorder.serve.ParameterMapping;
import com.sdp.servflow.pubandorder.serve.ServeAuth;
import com.sdp.servflow.pubandorder.serve.format.FormatConversion;
import com.sdp.servflow.pubandorder.serve.model.ProtocolData;
import com.sdp.servflow.pubandorder.util.IContants;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.sdp.servflow.pubandorder.util.MapUtil.getEndNode;
import static com.sdp.servflow.pubandorder.util.MapUtil.getStartNode;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


/****
 * 服务编排
 *
 * @author 任壮
 * @version 2017年8月16日
 * @see LayoutServersImp
 * @since
 */

//@Service("layoutServer")
//@Scope("prototype")
public class LayoutServersImp implements LayoutServer {
    private static final Logger LOGGER = Logger.getLogger(LayoutServersImp.class);

    /***
     * 参数校验
     */
    @Autowired
    private CheckParam checkParam;

    @Autowired
    private ServeAuth serveAuth;

    @Autowired
    private LogManage log;

    @Autowired
    private ParameterMapping mapping;

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
     *       order_name 必传
     *      "ser_id"
            "ser_version"
            "order_id"
            requestID  UUID
            "ipAddr"
            "appId"
            "tenant_id"
            "urlParam"
            //服务编排
             * 
             * 
           sourceType 0同步 1异步 2cas
     *
     * publicParam : ser_id 服务的id tenant_id 租户id
     */
    @Override
    public Response analysis(HashMap<String, Object> publicParam, String busiParm,
                             HttpClient httpClient, String esbXml) {
        busiParm = busiParm.trim();
        String order_id =(String)publicParam.get("order_id");
        String sourceType =(String)publicParam.get("sourceType");
        String order_name = (String)publicParam.get("order_name");
        Timestamp startTime = getCurrentTime();
        // TODO 将apiId和ser_id对应起来（暂时使用apiId代替ser_id）
        String ser_id = (String)publicParam.get("ser_id");
        //String version = (String)publicParam.get("ser_version");
        String tenant_id = (String)publicParam.get("tenant_id");

        Response response = null;
        String requestID = (String)publicParam.get("requestID");
        ProtocolData respParm = null;
        LogBean serLog = new LogBean();
        serLog.setLogType(1);
        serLog.setOrder_name(order_name);
        serLog.setRequestId(requestID);
        serLog.setPubapiId(ser_id);
        serLog.setOrderid(order_id);
        serLog.setRequestMsg(busiParm);
        serLog.setTenant_id(tenant_id);
        // 本次访问的唯一编码
        try {

            NodeFactoryImp factory = NodeFactoryImp.getOneNodeFactory();
            Map<String, Node> nodes = getNodes(esbXml, factory);
            // 获取所有id的集合
            Map<String, XmlNode> xmlNodes = factory.getXmlNode(nodes.values());
            StartNode startNode = (StartNode)getStartNode(nodes);
            EndNode endNode = (EndNode)getEndNode(nodes);



            Map<String, ProtocolData> resource = new HashMap<String, ProtocolData>();
            ProtocolData protocolData = new ProtocolData();
            protocolData.setRequestBody(busiParm);
            protocolData.setUrlParam((Map<String, Object>)publicParam.get("urlParam"));
            protocolData.setResponseBody(busiParm);
            protocolData.setReposneformat(endNode.getParam().getFormat());
            protocolData.setReqformat(startNode.getParam().getFormat());
            resource.put(startNode.getNodeId(), protocolData);

            String checkResult = checkParam(startNode, protocolData);
            if (checkResult != null) {
                response = ResponseCollection.getSingleStion().get(7);
                response.setResult(checkResult);
                return response;
            }

            // 可用参数集合
            Map<String, Object> respMap = null;

            boolean stopLoopFlag = false;
            // 前一个服务的id
            String preNodeId = "";// 第一个节点是开始节点 前一个节点是空字符串
            // 存放的是本次需要处理的信息
            List<String> theseNodes = getNodeId(startNode.getTargetlist());
            do {
                // 本次需要循环的list
                int serSize = theseNodes.size();
                // 不满足条件的集合
                int errCondotion = 0;
                boolean loopCont = true;
                loop: for (String nodeId : theseNodes) {
                    // 1代表的是条件判断
                    String typeId = nodes.get(nodeId).getNodeStyle();
                    if (Node.lineNodeStyle.equals(typeId)) {
                    	
                    	
                    	
                    	
                    	
                    	
                        // 不能继续执行时退出
                        if (!loopCont) {
                            break loop;
                        }
                        ConditionNode conditionNode = (ConditionNode)nodes.get(nodeId);
                        // 遍历当前符合条件的节点
                        // boolean condition = JudgeCondition(conditionNode,resource);
                        boolean condition = mapping.JudgeCondition(conditionNode, resource,
                            xmlNodes);
                        if (!condition) {// 判断是否满足条件(不符合条件的开始执行下个条件判断)
                            errCondotion++ ;
                            continue loop;
                        }
                        theseNodes = getNodeId(conditionNode.getTargetlist());
                        preNodeId = conditionNode.getNodeId();
                        loopCont = !condition;
                    }
                    else if (Node.interfaceNodeStyle.equals(typeId)) {
                        InterfaceNode node = (InterfaceNode)nodes.get(nodeId);
                        // 执行本次的接口 并将下一次需要遍历的服务存入
                        ConditionNode conditionNode = (ConditionNode)nodes.get(preNodeId);

                        ProtocolData target = mapping.mapping(conditionNode, resource, xmlNodes);
                        target.setReqformat(node.getInParameter().getFormat());
                        target.setReposneformat(node.getOutParameter().getFormat());
                        theseNodes = getNodeId(node.getTargetlist());
                        preNodeId = node.getNodeId();
                        // 服务调用（调用完后将返回值添加到resouce中 供后面的该服务调用）
                        // System.out.println("服务的请求值"+target);
                        LOGGER.info("服务的请求值" + target);
                        respParm = sendMessage(target, node, httpClient, requestID, publicParam);
                        LOGGER.info("服务的响应值" + respParm);
                        // 校验失败的信息
                        if (respMap != null) {
                            String checkFail = (String)respMap.get("checkFail");
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
                    }
                    else if (Node.endNodeStyle.equals(typeId)) {
                        EndNode end = (EndNode)nodes.get(nodeId);
                        ConditionNode conditionNode = (ConditionNode)nodes.get(preNodeId);
                        ProtocolData target = mapping.mapping(conditionNode, resource, xmlNodes);
                        target.setReqformat(end.getParam().getFormat());
                        target.setReposneformat(end.getParam().getFormat());
                        // 服务调用（调用完后将返回值添加到resouce中 供后面的该服务调用）
                        LOGGER.info("服务的最后的映射为" + target);
                        theseNodes = getNodeId(conditionNode.getTargetlist());
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
            }
            while (theseNodes != null && theseNodes.size() > 0 && !stopLoopFlag);

            // System.out.println("respParm"+respParm);
            if (response == null) {
                // 未处理的response认为是正常编排出的服务 最后一次相当于只有 入参的转换
                response = ResponseCollection.getSingleStion().get(1);
                response.setResult((String)respParm.getRequestBody());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response = ResponseCollection.getSingleStion().get(10);
            response.setResult(e.toString());
        }
        serLog.setStartTime(startTime);
        serLog.setEndTime(getCurrentTime());
        serLog.setResponseMsg(response.getResult());
        serLog.setResponseMsg(sourceType);
        serLog.setCode(response.getRespCode());
        if(StringUtils.isNotBlank(requestID)){
        try {
			log.addLog(serLog);
		} catch (DataMissingException e) {
			e.printStackTrace();
		}

        }
        return response;
    }

    /**
     * 转换list
     */

    private List<String> getNodeId(List<String> list) {
       /* List<String> nodeList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (String join : list) {
                nodeList.add(join.getEndNodeId());
            }

        }*/
        return list;
    }

    /***
     * Description: 调用注册服务
     * 
     * @param parm
     *            传入参数 json形式的字符串
     * @param requestID
     * @param publicParam
     *            注册服务相关参数
     * @return String json形式的字符串
     * @throws Exception
     * @see
     */
    private ProtocolData sendMessage(ProtocolData parm, InterfaceNode pubInterface,
                                     HttpClient httpClient, String requestID,
                                     HashMap<String, Object> publicParam)
        throws Exception {
        String order_id =(String)publicParam.get("order_id");
        String sourceType =(String)publicParam.get("sourceType");
        String busiParm = (String)parm.getRequestBody();
        String order_name = (String)publicParam.get("order_name");
        Timestamp start = getCurrentTime();

        ProtocolData data = serveAuth.server(pubInterface, parm, httpClient);
        // 保存日志
        String tenant_id = (String)publicParam.get("tenant_id");
        LogBean serLog = new LogBean();
        serLog.setRequestId(requestID);
        serLog.setOrder_name(order_name);
        serLog.setLogType(0);
        serLog.setPubapiId(pubInterface.getNodeId());
        serLog.setOrderid(order_id);
        serLog.setRequestMsg(busiParm);
        serLog.setTenant_id(tenant_id);
        serLog.setStartTime(start);
        serLog.setEndTime(getCurrentTime());
        serLog.setSourceType(sourceType);
        serLog.setResponseMsg((String)data.getResponseBody());
        if(StringUtils.isNotBlank(requestID)){
            log.addLog(serLog);
        }

        return data;
    }

    private String getTime(Date date) {
        String current = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date);
        return current;
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
                    value = FormatConversion.getValues((String)(protocolData.getRequestBody()),
                        field, null);
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

    /**
     * Description: 传入esbXml将其解析为不同类型的对象
     * 
     * @param esbXml
     * @return Map<String,Node>
     * @throws Exception
     * @see
     */
    private Map<String, Node> getNodes(String esbXml, NodeFactoryImp factory)
        throws Exception {
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