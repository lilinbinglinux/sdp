package com.sdp.servflow.pubandorder.order.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.frame.util.IdUtil;
import com.sdp.servflow.pubandorder.order.model.InterfaceOrderBean;
import com.sdp.servflow.pubandorder.order.service.InterfaceOrderBeanService;

/**
 * 
 * 接口订阅模块Service对应的实现类
 *
 * @author 牛浩轩
 * @version 2017年6月12日
 * @see InterfaceOrderBeanServiceImpl
 * @since
 */
@Service
public class InterfaceOrderBeanServiceImpl implements InterfaceOrderBeanService{

    /**
     * DaoHelper
     */
    @Resource
	private DaoHelper daoHelper;
    
    @Override
    public Map selectMine(String start, String length, Map<String, Object> paramMap) {
        return daoHelper.queryForPageList("com.sdp.frame.web.mapper.puborder.InterfaceOrderBeanMapper.selectmine", paramMap, start, length);
    }

    @Override
    public InterfaceOrderBean selectByPrimaryKey(String orderId) {
        return (InterfaceOrderBean)daoHelper.queryOne("com.sdp.frame.web.mapper.puborder.InterfaceOrderBeanMapper.selectByPrimaryKey", orderId);
    }
    
    @Override
	public String insert(InterfaceOrderBean interfaceOrder) {
        String orderid = IdUtil.createId();
        interfaceOrder.setOrderid(orderid);
        interfaceOrder.setCreatedate(new Date());
/*        interfaceOrder.setTenant_id("tenant_system");
        interfaceOrder.setLogin_id("xadmin");*/
        
        interfaceOrder.setTenant_id(CurrentUserUtils.getInstance().getUser().getTenantId());
        interfaceOrder.setLogin_id(CurrentUserUtils.getInstance().getUser().getLoginId());
        interfaceOrder.setCheckstatus("100");
        
        daoHelper.insert("com.sdp.frame.web.mapper.puborder.InterfaceOrderBeanMapper.insert", interfaceOrder);
        return orderid;
    }

    @Override
    public void updateOrderInter(InterfaceOrderBean interfaceOrderBean) {
        daoHelper.update("com.sdp.frame.web.mapper.puborder.InterfaceOrderBeanMapper.updateByPrimaryKey", interfaceOrderBean);
    }

    @Override
    public void deleteOrderInter(String orderId) {
        daoHelper.delete("com.sdp.frame.web.mapper.puborder.InterfaceOrderBeanMapper.deleteByPrimaryKey", orderId);
    }

    @Override
    public String selectPubApiId(String orderid) {
        return (String)daoHelper.queryOne("com.sdp.frame.web.mapper.puborder.InterfaceOrderBeanMapper.selectpubapiId", orderid);
    }

    @Override
    public String getMaxAppId() {
        return (String)daoHelper.queryOne("com.sdp.frame.web.mapper.puborder.InterfaceOrderBeanMapper.getMaxAppId");
    }
    
    @Override
    public List<InterfaceOrderBean> getAllByCondition(Map<String, String> map) {
        return daoHelper.queryForList("com.sdp.frame.web.mapper.puborder.InterfaceOrderBeanMapper.getAllByCondition",map);
    }
    
    @Override
    public boolean isValidation(InterfaceOrderBean interfaceOrderBean) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("pubapiId", interfaceOrderBean.getPubapiId());
        map.put("name", interfaceOrderBean.getName());
        map.put("ordercode", interfaceOrderBean.getOrdercode());
        map.put("protocal", interfaceOrderBean.getProtocal());
        map.put("reqformat", interfaceOrderBean.getReqformat());
        map.put("tenant_id", CurrentUserUtils.getInstance().getUser().getTenantId());
        map.put("login_id", CurrentUserUtils.getInstance().getUser().getLoginId());
        
        List<InterfaceOrderBean> list = getAllByCondition(map);
        if(null != list && list.size()>0){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public InterfaceOrderBean upSort(String sort) {
        return (InterfaceOrderBean)daoHelper.queryOne("com.sdp.frame.web.mapper.puborder.InterfaceOrderBeanMapper.upsort", sort);
    }

}
