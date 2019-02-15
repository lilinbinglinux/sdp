package com.sdp.servflow.pubandorder.order.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.frame.util.IdUtil;
import com.sdp.servflow.pubandorder.order.model.OrderParamBean;
import com.sdp.servflow.pubandorder.order.model.OrderParamBeanList;
import com.sdp.servflow.pubandorder.order.model.OrderParamBeans;
import com.sdp.servflow.pubandorder.order.service.OrderParamBeanService;
import com.thoughtworks.xstream.XStream;

/**
 * 
 * 订阅参数的具体实现
 *
 * @author 牛浩轩
 * @version 2017年7月3日
 * @see OrderParamBeanServiceImpl
 * @since
 */
@Service
public class OrderParamBeanServiceImpl implements OrderParamBeanService{

    /**
     * DaoHelper
     */
    @Resource
    private DaoHelper daoHelper;
    
    @Override
    public int insert(OrderParamBean orderModel) {
        return daoHelper.insert("com.sdp.frame.web.mapper.puborder.MineOrderBeanMapper.insert", orderModel);
    }

    /**
     * xml解析后保存
     */
    @Override
    public void insertByxml(String xmlcontext, String orderid) {
        String type = "";
        XStream xstream = new XStream();
        xstream.alias("params", OrderParamBeans.class);
        xstream.alias("param", OrderParamBean.class);
        OrderParamBeans orderParamsNew = (OrderParamBeans)xstream.fromXML(xmlcontext); 
        if(StringUtils.isNotBlank(orderParamsNew.getType())){
            switch (orderParamsNew.getType()) {
                case "request":
                    type = "0";
                    break;
                case "response":
                    type = "1";
                    break;
                default: 
                    type = "0";
                    break;
            }
        }
        
        List<OrderParamBean> params = orderParamsNew.getAllparams();
        for(OrderParamBean param : params){
            if(null != param.getOrderParamBean()){
                param.setOrderparamId(IdUtil.createId());
                param.setType(type);
                param.setParentId("ROOT");
                param.setOrderid(orderid);
                param.setPubparamId("订阅参数id");
                this.insert(param);
                saveOrderSubParams(param.getOrderParamBean(), type, param.getOrderparamId(), orderid);
            }
            else{
                param.setOrderparamId(IdUtil.createId());
                param.setType(type);
                param.setParentId("ROOT");
                param.setOrderid(orderid);
                param.setPubparamId("订阅参数id");
                this.insert(param);
            }
        }
    }

    /**
     * 
     * Description: 递归查询并保存xml参数多层嵌套子参数 
     * 
     *@param params
     *@param type
     *@param parentId
     *@param orderid void
     *
     * @see
     */
    private void saveOrderSubParams(OrderParamBeanList params, String type, String parentId, String orderid){
        List<OrderParamBean> orderParams = params.getOrderParamBeanList();
        for(OrderParamBean orderParam : orderParams){
            orderParam.setOrderparamId(IdUtil.createId());
            orderParam.setType(type);
            orderParam.setParentId(parentId);
            orderParam.setOrderid(orderid);
            orderParam.setPubparamId("订阅参数id");
            this.insert(orderParam);
            if(null != orderParam.getOrderParamBean()){
                saveOrderSubParams(orderParam.getOrderParamBean(), type, orderParam.getOrderparamId(), orderid);
            }
        }
    }

    @Override
    public Map selectParam(String start, String length, Map<String, Object> paramMap) {
        return daoHelper.queryForPageList("com.sdp.frame.web.mapper.puborder.MineOrderBeanMapper.selectParam", paramMap, start, length);
    }
    
    
    
    
    
}
