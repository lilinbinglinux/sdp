package com.sdp.servflow.pubandorder.orderapistore.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.servflow.pubandorder.node.analyze.abstractfactory.NodeFactory;
import com.sdp.servflow.pubandorder.node.analyze.factory.NodeFactoryImp;
import com.sdp.servflow.pubandorder.node.model.node.Node;
import com.sdp.servflow.pubandorder.orderapistore.model.OrderInterfaceBean;
import com.sdp.servflow.pubandorder.orderapistore.service.AsynchSerNodeService;
import com.sdp.servflow.pubandorder.orderapistore.service.OrderInterfaceService;
import com.sdp.servflow.serlayout.process.model.ServiceInfoPo;
import com.sdp.servflow.serlayout.process.service.SerProcessNodeHandlerService;

/**
 * Description:
 *
 * @author Niu Haoxuan
 * @date Created on 2017/12/22.
 */
@Service
public class AsynchSerNodeServiceImpl implements AsynchSerNodeService{

    /**
     * DaoHelper
     */
    @Autowired
    private DaoHelper daoHelper;

    /**
     * OrderInterfaceService
     */
    @Autowired
    private OrderInterfaceService orderInterfaceService;

    /**
     * SerProcessNodeHandlerService
     */
    @Autowired
    private SerProcessNodeHandlerService serProcessNodeHandlerService;

    /**
     * SerProcessNodeHandlerService
     */
    @Autowired
    private SerProcessNodeHandlerService nodeHandlerService;

    private List<ServiceInfoPo> getServiceVersionBeanByCondition(Map<String,String> map) {
        return daoHelper.queryForList("com.bonc.frame.web.mapper.serlayout.SerLayoutMapper.getServiceVersionBeanByCondition",map);
    }

    @Override
    public Map<String, Object> getNodeJson(String orderId, String serId) throws Exception {
        OrderInterfaceBean orderInterfaceBean = new OrderInterfaceBean();
        orderInterfaceBean.setOrderId(orderId);
        List<OrderInterfaceBean> list =  orderInterfaceService.getAllByCondition(orderInterfaceBean);
        if (list == null || list.size() == 0){
            return null;
        }
        String appResume = list.get(0).getAppResume();
        InputStream is = new ByteArrayInputStream(appResume.getBytes("utf-8"));
        SAXBuilder sb = new SAXBuilder();
        Document doc = sb.build(is);
        Element sourceRoot = doc.getRootElement();
        List<Element> list1 = sourceRoot.getChildren();
        List<Node> nodelist = new ArrayList<Node>();
        List<String> nodeJoinlist = new ArrayList<>();

        NodeFactory factory = NodeFactoryImp.getOneNodeFactory();
        for(Element element: list1)
        {
            Node node = factory.createNode(element);
            nodelist.add(node);
            List<String> joins = node.getTargetlist();
            for(String join:joins){
                nodeJoinlist.add(join);
            }
        }

        return serProcessNodeHandlerService.objTojson(nodelist, serId,"");
    }

    @Override
    public Map<String, Object> getNodeJsonExceptloginId(String flowChartId, String serVerId) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, String> selectMap = new HashMap<String, String>();
        selectMap.put("serId", flowChartId);
        selectMap.put("serVerId", serVerId);
        selectMap.put("tenantId", CurrentUserUtils.getInstance().getUser().getTenantId());
        List<ServiceInfoPo> serviceInfoPo = getServiceVersionBeanByCondition(selectMap);

        if(serviceInfoPo.size()>1 || serviceInfoPo == null){
            throw new Exception("查出结果不唯一");
        }else if(serviceInfoPo.size() == 0){
            Map<String, Object> jsonNodeMap = new HashMap<String, Object>();
            jsonNodeMap.put("nodeJsonArray","");
            jsonNodeMap.put("nodeJoinJsonArray","");
            jsonNodeMap.put("allJsonArray","");
            resultMap.put("serFlow",jsonNodeMap);
        }
        resultMap.put("serFlow",serviceInfoPo.get(0).getSerFlow());

        String serflowStr = serviceInfoPo.get(0).getSerFlow();
        InputStream is = new ByteArrayInputStream(serflowStr.getBytes("utf-8"));
        SAXBuilder builder=new SAXBuilder(false);      //选择xml解析器为默认
        Document document = builder.build(is);                  //得到Document
        Element books=document.getRootElement();                //得到根节点
        List<Element> booklist=books.getChildren();
        NodeFactory factory = NodeFactoryImp.getOneNodeFactory();

        List<Node> nodeList = new ArrayList<>();
        List<String> nodeJoinList = new ArrayList<>();
        for (Iterator<Element> iter = booklist.iterator(); iter.hasNext();){
            Node node = factory.createNode(iter.next());
            nodeList.add(node);
            List<String> nodeJoinLists = node.getTargetlist();
            for (String nodeJoin:nodeJoinLists) {
                nodeJoinList.add(nodeJoin);
            }
        }

        return nodeHandlerService.objTojson(nodeList, flowChartId, serVerId);
    }
}
