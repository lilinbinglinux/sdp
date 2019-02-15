package com.sdp.servflow.pubandorder.serapitype.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.servflow.pubandorder.serapitype.model.ServiceApiTypeBean;
import com.sdp.servflow.pubandorder.serapitype.service.ServiceApiTypeService;

/**
 * description:
 *
 * @author niu
 * @date Created on 2017/10/24.
 */
@Service
public class ServiceApiTypeServiceImpl implements ServiceApiTypeService{

    /**
     * DaoHelper
     */
    @Resource
    private DaoHelper daoHelper;

    @Override
    public List<ServiceApiTypeBean> getAllByCondition(Map<String, String> map) {
        return daoHelper.queryForList("com.sdp.frame.web.mapper.puborder.ServiceApiTypeMapper.getAllByCondition", map);
    }

    @Override
    public int insert(ServiceApiTypeBean serviceApiTypeBean) {
        return daoHelper.insert("com.sdp.frame.web.mapper.puborder.ServiceApiTypeMapper.insert", serviceApiTypeBean);
    }

    @Override
    public int update(ServiceApiTypeBean serviceApiTypeBean) {
        return daoHelper.update("com.sdp.frame.web.mapper.puborder.ServiceApiTypeMapper.updateByPrimaryKey",serviceApiTypeBean);
    }

    @Override
    public int delete(String apiTypeId) {
        return daoHelper.delete("com.sdp.frame.web.mapper.puborder.ServiceApiTypeMapper.deleteByPrimaryKey",apiTypeId);
    }

    @Override
    public List<ServiceApiTypeBean> selectFilterDate(){
        List<ServiceApiTypeBean> serviceApiTypeBeanList = new ArrayList<>();
        Map<String,String> paramMap = new HashMap<>();
        //第一次查询，查询全部共享的类型
        paramMap.put("shareFlag","1");
        paramMap.put("delFlag","0");
        serviceApiTypeBeanList.addAll(getAllByCondition(paramMap));

        //第二次查询，查询本用户下，没有共享的类型
        paramMap.put("shareFlag","0");
        paramMap.put("creatUser", CurrentUserUtils.getInstance().getUser().getUserName());
        serviceApiTypeBeanList.addAll(getAllByCondition(paramMap));

        return serviceApiTypeBeanList;
    }
}
