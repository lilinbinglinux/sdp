package com.sdp.servflow.pubandorder.servicebasic.service.impl;

import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.servflow.pubandorder.servicebasic.model.ServiceVersionBean;
import com.sdp.servflow.pubandorder.servicebasic.service.ServiceVersionService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author 牛浩轩
 * @date Created on 2017/11/8.
 */
@Service
public class ServiceVersionServiceImpl implements ServiceVersionService{

    /**
     * DaoHelper
     */
    @Resource
    private DaoHelper daoHelper;

    @Override
    public List<ServiceVersionBean> getAllByCondition(Map<String, String> map) {
        return daoHelper.queryForList("com.sdp.frame.web.mapper.puborder.ServiceVersionMapper.getAllByCondition",map);
    }

    @Override
    public int insert(ServiceVersionBean serviceVersionBean) {
        return daoHelper.insert("com.sdp.frame.web.mapper.puborder.ServiceVersionMapper.insert",serviceVersionBean);
    }

    @Override
    public int update(ServiceVersionBean serviceVersionBean) {
        return daoHelper.update("com.sdp.frame.web.mapper.puborder.ServiceVersionMapper.updateByPrimaryKey",serviceVersionBean);
    }

    @Override
    public int delete(String serVersion) {
        return daoHelper.delete("com.sdp.frame.web.mapper.puborder.ServiceVersionMapper.deleteByPrimaryKey",serVersion);
    }

    @Override
    public int deleteBySerId(ServiceVersionBean serviceVersionBean) {
        return daoHelper.delete("com.sdp.frame.web.mapper.puborder.ServiceVersionMapper.deleteBySerId",serviceVersionBean);
    }

    @Override
	public ServiceVersionBean getByPrimaryKey(String serVerId) {
		return (ServiceVersionBean)daoHelper.queryOne("com.sdp.frame.web.mapper.puborder.ServiceVersionMapper.getByPrimaryKey", serVerId);
	}

	@Override
	public List<ServiceVersionBean> getMaxVersionByCondition(Map<String,String> map) {
		return daoHelper.queryForList("com.sdp.frame.web.mapper.puborder.ServiceVersionMapper.getMaxVersionByCondition",map);
	}
}
