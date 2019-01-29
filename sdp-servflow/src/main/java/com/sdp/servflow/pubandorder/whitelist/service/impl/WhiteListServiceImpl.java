package com.sdp.servflow.pubandorder.whitelist.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.frame.util.IdUtil;
import com.sdp.servflow.logSer.serIpLimit.busi.SerIpLimitManager;
import com.sdp.servflow.pubandorder.whitelist.model.WhiteListBean;
import com.sdp.servflow.pubandorder.whitelist.service.WhiteListService;

/**
 * Description:
 *
 * @author Niu Haoxuan
 * @date Created on 2017/11/28.
 */
@Service
public class WhiteListServiceImpl implements WhiteListService {

    /**
     * DaoHelper
     */
    @Resource
    private DaoHelper daoHelper;

    /**
     * SerIpLimitManager
     */
    @Autowired
    private SerIpLimitManager serIpLimitManager;

    @Override
    public int insert(WhiteListBean whiteListBean) {
        whiteListBean.setIpID(IdUtil.createId());
        whiteListBean.setCreateTime(new Date());
        whiteListBean.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
        return daoHelper.insert("com.bonc.servflow.pubandorder.mapper.WhiteListMapper.insert",whiteListBean);
    }

    @Override
    public int splitIps(WhiteListBean whiteListBean, String ipAddrs) {
        String[] ipAddrArray = ipAddrs.split(",");
        int result = 0;
        for (String ipAddr : ipAddrArray) {
            whiteListBean.setIpAddr(ipAddr);
            insert(whiteListBean);
            result++;
        }

        //将将过滤IP从内存删除
        serIpLimitManager.removeSerIpLimitRule(CurrentUserUtils.getInstance().getUser().getTenantId(),whiteListBean.getOrderId());

        //将过滤IP放入内存
        serIpLimitManager.addSerIpLimitRule(CurrentUserUtils.getInstance().getUser().getTenantId(),
                whiteListBean.getOrderId(), whiteListBean.getNameType(), ipAddrs, ",");
        return result;
    }

    @Override
    public int delete(String orderId) {
        //将将过滤IP从内存删除
        serIpLimitManager.removeSerIpLimitRule(CurrentUserUtils.getInstance().getUser().getTenantId(),orderId);
        return daoHelper.delete("com.bonc.servflow.pubandorder.mapper.WhiteListMapper.delete", orderId);
    }

    @Override
    public List<WhiteListBean> getAllByCondition(WhiteListBean whiteListBean){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ipID", whiteListBean.getIpID());
        map.put("appId", whiteListBean.getAppId());
        map.put("orderId", whiteListBean.getOrderId());
        map.put("ipAddr", whiteListBean.getIpAddr());
        map.put("nameType", whiteListBean.getNameType());
        map.put("tenantId", whiteListBean.getTenantId());
        return daoHelper.queryForList("com.bonc.servflow.pubandorder.mapper.WhiteListMapper.getAllByCondition",map);
    }
}
