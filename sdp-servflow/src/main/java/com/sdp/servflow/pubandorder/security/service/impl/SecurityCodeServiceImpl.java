package com.sdp.servflow.pubandorder.security.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.servflow.pubandorder.order.model.InterfaceOrderBean;
import com.sdp.servflow.pubandorder.order.service.InterfaceOrderBeanService;
import com.sdp.servflow.pubandorder.security.model.SecurityCodeBean;
import com.sdp.servflow.pubandorder.security.service.SecurityCodeService;

/**
 * 
 * SecurityCodeService实现类
 *
 * @author ZY
 * @version 2017年7月24日
 * @see SecurityCodeServiceImpl
 * @since
 */
@Service
public class SecurityCodeServiceImpl implements SecurityCodeService{
    
    /**
     * daoHelper
     */
    @Resource
    private DaoHelper daoHelper;
    
    @Resource
    private InterfaceOrderBeanService interfaceOrderBeanService;

    @Override
    public List<SecurityCodeBean> getAllByCondition(Map<String, String> map) {
        return daoHelper.queryForList("com.sdp.servflow.pubandorder.security.mapper.SecurityCodeBeanMapper.getAllByCondition", map);
    }

    @Override
    public void insert(SecurityCodeBean securityCodeBean) {
        securityCodeBean.setTenant_id(CurrentUserUtils.getInstance().getUser().getTenantId());
        securityCodeBean.setLogin_id(CurrentUserUtils.getInstance().getUser().getLoginId());
        daoHelper.insert("com.sdp.servflow.pubandorder.security.mapper.SecurityCodeBeanMapper.insert", securityCodeBean);
    }

    @Override
    public String getValidateToken(String orderid) {
        InterfaceOrderBean bean = interfaceOrderBeanService.selectByPrimaryKey(orderid);
        
        Map<String,String> map = new HashMap<String,String>();
        map.put("appId", bean.getAppId());
        map.put("login_id", CurrentUserUtils.getInstance().getUser().getLoginId());
        map.put("tenant_id", CurrentUserUtils.getInstance().getUser().getTenantId());
        List<SecurityCodeBean> list = getAllByCondition(map);
        
        //判断该用户是否已经申请过
        if(null != list&&list.size()>0){
            //无效
            return list.get(0).getToken_id();
        }else{
            //可重新生成
            return "";
        }
    }
    

}
