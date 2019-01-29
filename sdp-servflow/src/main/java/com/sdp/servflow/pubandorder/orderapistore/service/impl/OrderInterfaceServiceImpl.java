package com.sdp.servflow.pubandorder.orderapistore.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.frame.util.IdUtil;
import com.sdp.servflow.logSer.flowControl.busi.FlowControlManager;
import com.sdp.servflow.pubandorder.node.analyze.factory.NodeFactoryImp;
import com.sdp.servflow.pubandorder.node.model.field.Field;
import com.sdp.servflow.pubandorder.node.model.node.EndNode;
import com.sdp.servflow.pubandorder.node.model.node.Node;
import com.sdp.servflow.pubandorder.node.model.node.StartNode;
import com.sdp.servflow.pubandorder.node.model.parameter.Parameter;
import com.sdp.servflow.pubandorder.orderServer.mapper.OrderInterfaceMapper;
import com.sdp.servflow.pubandorder.orderapistore.model.OrderInterfaceBean;
import com.sdp.servflow.pubandorder.orderapistore.service.OrderInterfaceService;
import com.sdp.servflow.pubandorder.orderapistore.util.OrderCfgKey;
import com.sdp.servflow.pubandorder.servicebasic.service.ServiceMainService;
import com.sdp.servflow.serlayout.process.service.impl.SerProcessNodeServiceImpl;

import net.sf.json.JSONObject;

/**
 * Description:OrderInterfaceServiceImpl
 *
 * @author Niu Haoxuan
 * @date Created on 2017/11/15.
 */
@Service
public class OrderInterfaceServiceImpl implements OrderInterfaceService{

    /**
     * DaoHelper
     */
    @Resource
    private DaoHelper daoHelper;

    /**
     * OrderInterfaceMapper
     */
    @Autowired
    private OrderInterfaceMapper orderInterfaceMapper;

    /**
     * SerProcessNodeService
     */
    @Autowired
    private SerProcessNodeServiceImpl serProcessNodeService;

    /**
     * ServiceMainService
     */
    @Autowired
    private ServiceMainService serviceMainService;

    /**
     * FlowControlManager
     */
    @Autowired
    private FlowControlManager flowControlManager;

    @Override
    public OrderInterfaceBean selectContextPage(String serId) {
        OrderInterfaceBean orderInterfaceBean = (OrderInterfaceBean) daoHelper.queryOne("com.bonc.servflow.pubandorder.serve.mapper.OrderInterfaceMapper.selectContextPage",serId);
        return orderInterfaceBean;
    }

    @Override
    public Map<String, String> getParam(String serFlow) throws Exception {
        StringBuilder inputStr = new StringBuilder();
        StringBuilder outputStr = new StringBuilder();
        Map<String, String> map = new HashMap<String, String>();
        String protocal = null;         //请求协议
        String reqformat = null;        //请求参数格式
        String respformat = null;       //相应参数格式

        InputStream is = new ByteArrayInputStream(serFlow.getBytes("utf-8"));
        SAXBuilder sb = new SAXBuilder();
        Document doc = sb.build(is);
        Element sourceRoot = doc.getRootElement();
        List<Element> list = sourceRoot.getChildren();
        NodeFactoryImp factory = NodeFactoryImp.getOneNodeFactory();
        for (Element element: list) {
            Node node = factory.createNode(element);
            //当为开始节点时
            if ((Node.startNodeStyle).equals(node.getNodeStyle())){
                StartNode startNode = (StartNode)node;
                Parameter parameter = startNode.getParam();
                //获取开始节点的入参
                if ((Parameter.inparameter).equals(parameter.getParameterType())) {
                    reqformat = parameter.getType();
                    List<Field> listField= parameter.getFildList();
                    inputStr.append("{");
                    for (Field field : listField) {
                        inputStr.append("\""+field.getName()+"\":\"请输入相应类型的参数, "+field.getType()+"\",");
                    }
                    if(inputStr.length() > 1){
                        inputStr.deleteCharAt(inputStr.length()-1);
                    }
                    inputStr.append("}");
                }
                protocal = startNode.getAgreement();
            }
            //当为结束节点时
            else if ((Node.endNodeStyle).equals(node.getNodeStyle())){
                EndNode endNode = (EndNode) node;
                Parameter parameter = endNode.getParam();
                //获取结束节点的出参
                if ((Parameter.outparameter).equals(parameter.getParameterType())){
                    respformat = parameter.getType();
                    List<Field> listField= parameter.getFildList();
                    outputStr.append("{");
                    for (Field field : listField) {
                        outputStr.append("\""+field.getName()+"\":\"暂无相应的参数\",");
                    }
                    if(outputStr.length() > 1){
                        outputStr.deleteCharAt(outputStr.length()-1);
                    }
                    outputStr.append("}");
                }
            }
        }
        String inputParamStr = inputStr.toString();
        String outputParamStr = outputStr.toString();
        map.put("inputParam",inputParamStr);
        map.put("outputParam",outputParamStr);
        map.put("protocal", protocal);
        map.put("reqformat", reqformat);
        map.put("respformat", respformat);
        return map;
    }

    @Override
    public String insert(OrderInterfaceBean orderInterfaceBean) {
        String url = null;
        String orderId = IdUtil.createId();
        orderInterfaceBean.setOrderId(orderId);
        orderInterfaceBean.setOrderCode(orderId.substring(orderId.length()-6,orderId.length()));
        orderInterfaceBean.setCreateDate(new Date());
        if ("http".equalsIgnoreCase(orderInterfaceBean.getProtocal())){
            url = serviceMainService.getUrl(orderInterfaceBean.getAppId(), OrderCfgKey.CFG_KEY_SYNCHRONIZED_HTTP);
        }else if("webservice".equalsIgnoreCase(orderInterfaceBean.getProtocal())){
            url = serviceMainService.getUrl(orderInterfaceBean.getAppId(), OrderCfgKey.CFG_KEY_SYNCHRONIZED_WEBSERVICE);
        }
        orderInterfaceBean.setUrl(url);

        if(orderInterfaceBean.getLoginId()==null){
            orderInterfaceBean.setLoginId(CurrentUserUtils.getInstance().getUser().getLoginId());
        }else{
            if(orderInterfaceBean.getLoginId().equals(CurrentUserUtils.getInstance().getUser().getLoginId())){
                orderInterfaceBean.setRepFlag("0");
            }else{
                orderInterfaceBean.setRepUserId(CurrentUserUtils.getInstance().getUser().getLoginId());
            }
        }
        orderInterfaceBean.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());

        //将限流相关数据放入内存
        if(orderInterfaceBean.getAccFreq()!=null && orderInterfaceBean.getAccFreqType() != null){
            flowControlManager.addFlowControlRule(CurrentUserUtils.getInstance().getUser().getTenantId(),
                    orderId, orderInterfaceBean.getAccFreq(),orderInterfaceBean.getAccFreqType());
        }else {
            flowControlManager.addFlowControlRule(CurrentUserUtils.getInstance().getUser().getTenantId(),
                    orderId, 0,null);
        }

        daoHelper.insert("com.bonc.servflow.pubandorder.serve.mapper.OrderInterfaceMapper.insert",orderInterfaceBean);
        return orderId;
    }

    @Override
    public int update(OrderInterfaceBean orderInterfaceBean) {
        if(orderInterfaceBean.getLoginId()==null || "".equals(orderInterfaceBean.getLoginId())){
            orderInterfaceBean.setLoginId(CurrentUserUtils.getInstance().getUser().getLoginId());
        }else{
            if(orderInterfaceBean.getLoginId().equals(CurrentUserUtils.getInstance().getUser().getLoginId())){
                orderInterfaceBean.setRepFlag("0");
            }else{
                orderInterfaceBean.setRepUserId(CurrentUserUtils.getInstance().getUser().getLoginId());
            }
        }

        //将内存中，限流相关数据更新
        if(orderInterfaceBean.getAccFreq()!=null && orderInterfaceBean.getAccFreqType() != null){
            flowControlManager.updateFlowControlRule(CurrentUserUtils.getInstance().getUser().getTenantId(),
                    orderInterfaceBean.getOrderId(), orderInterfaceBean.getAccFreq(),orderInterfaceBean.getAccFreqType());
        }else {
            flowControlManager.updateFlowControlRule(CurrentUserUtils.getInstance().getUser().getTenantId(),
                    orderInterfaceBean.getOrderId(), 0, null);
        }


        return daoHelper.update("com.bonc.servflow.pubandorder.serve.mapper.OrderInterfaceMapper.update",orderInterfaceBean);
    }

    @Override
    public String insertAsynchronized(OrderInterfaceBean orderInterfaceBean, String serNodeArray, String serJoinArray, String insertOrUpdateFlag) throws RuntimeException {
        try {
            Map<String,Object> map = serProcessNodeService.getSerProcessXml(serNodeArray, serJoinArray);
            JSONObject startObj = (JSONObject) map.get("startObj");
            String flowXml =  map.get("flowXml").toString();
            orderInterfaceBean.setAppResume(flowXml);

            if("add".equals(insertOrUpdateFlag)){
                insert(orderInterfaceBean);
            }else if("update".equals(insertOrUpdateFlag)){
                update(orderInterfaceBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return "success";
    }


    @Override
    public List<OrderInterfaceBean> getAllByCondition(OrderInterfaceBean orderInterfaceBean) {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderInterfaceBean.getOrderId());
        map.put("orderCode", orderInterfaceBean.getOrderCode());
        map.put("protocal", orderInterfaceBean.getProtocal());
        map.put("orderName", orderInterfaceBean.getOrderName());
        map.put("url", orderInterfaceBean.getUrl());
        map.put("reqformat", orderInterfaceBean.getReqformat());
        map.put("respformat", orderInterfaceBean.getRespformat());
        map.put("applicationId", orderInterfaceBean.getApplicationId());
        map.put("orderDesc", orderInterfaceBean.getOrderDesc());
        map.put("serId", orderInterfaceBean.getSerId());
        map.put("serVersion", orderInterfaceBean.getSerVersion());
        map.put("limitIp", orderInterfaceBean.getLimitIp());
        map.put("appId", orderInterfaceBean.getAppId());
        map.put("loginId", orderInterfaceBean.getLoginId());
        map.put("appResume", orderInterfaceBean.getAppResume());
        map.put("repFlag", orderInterfaceBean.getRepFlag());
        map.put("repUserId", orderInterfaceBean.getRepUserId());
        map.put("checkStatus", orderInterfaceBean.getCheckStatus());
        map.put("tenantId", orderInterfaceBean.getTenantId());
        return daoHelper.queryForList("com.bonc.servflow.pubandorder.serve.mapper.OrderInterfaceMapper.getAllByCondition",map);
    }

    @Override
    public Map selectInterfaceOrder(String start, String length, Map<String, Object> paramMap) {
        return orderInterfaceMapper.selectInterfaceOrder(paramMap,start,length);
    }


    @Override
    public void updateByPrimaryKey(OrderInterfaceBean orderInterfaceBean) {
        orderInterfaceMapper.updateByPrimaryKey(orderInterfaceBean);
    }

    @Override
    public Map selectMine(String start, String length, Map<String, Object> paramMap) {
        return orderInterfaceMapper.selectMine(paramMap,start,length);
    }

    @Override
    public void deleteOrderInter(Map<String, String> paramMap) {
        //将内存中，限流相关数据删除
        flowControlManager.removeFlowControlRule(paramMap.get("tenantId"),paramMap.get("orderId"));

        orderInterfaceMapper.deleteOrderInter(paramMap);
    }

    @Override
    public void deleteBySerIdAndVersion(Map<String, String> paramMap) {
        orderInterfaceMapper.deleteBySerIdAndVersion(paramMap);
    }


    @Override
    public OrderInterfaceBean selectByOrderId(String orderId) {
        return orderInterfaceMapper.selectByOrderId(orderId);
    }

    @Override
    public OrderInterfaceBean selectGetSerNameByOrderId(String orderId) {
        return (OrderInterfaceBean) daoHelper.queryOne("com.bonc.servflow.pubandorder.serve.mapper.OrderInterfaceMapper.selectGetSerNameByPrimaryKey",orderId);
    }
}
