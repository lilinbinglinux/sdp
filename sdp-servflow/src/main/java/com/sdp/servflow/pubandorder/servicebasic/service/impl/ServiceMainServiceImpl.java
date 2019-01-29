package com.sdp.servflow.pubandorder.servicebasic.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.servflow.pubandorder.servicebasic.model.ServiceMainBean;
import com.sdp.servflow.pubandorder.servicebasic.service.ServiceMainService;
import com.sdp.servflow.sysConfig.model.SysCommonCfg;
import com.sdp.servflow.sysConfig.service.SysCommonCfgService;

/**
 * Description:
 *
 * @author 牛浩轩
 * @date Created on 2017/11/8.
 */
@Service
public class ServiceMainServiceImpl implements ServiceMainService{

    /**
     * DaoHelper
     */
    @Resource
    private DaoHelper daoHelper;

    /**
     * SysCommonCfgService
     */
    @Resource
    private SysCommonCfgService sysCommonCfgService;

    /**
     * url获取
     */
    @Value("${servflow.url}")
    private String apiurl;

    @Override
    public List<ServiceMainBean> getAllByConditionLikeName(Map<String, String> map) {
        return daoHelper.queryForList("com.bonc.frame.web.mapper.puborder.ServiceMainMapper.getAllByConditionLikeName",map);
    }
    
    @Override
    public List<ServiceMainBean> getAllByCondition(Map<String, String> map) {
        return daoHelper.queryForList("com.bonc.frame.web.mapper.puborder.ServiceMainMapper.getAllByCondition",map);
    }

    @Override
    public List<ServiceMainBean> getBySerNameAndVersion(Map<String, String> map) {
        return daoHelper.queryForList("com.bonc.frame.web.mapper.puborder.ServiceMainMapper.getBySerNameAndVersion",map);
    }

    @Override
    public Map<String, Object> getAllAndSerTypeName(Map<String, Object> paramMap, String start, String length) {
        return daoHelper.queryForPageList("com.bonc.frame.web.mapper.puborder.ServiceMainMapper.getAllAndSerTypeName", paramMap, start, length);
    }

    @Override
    public Map<String, Object> selectPage(Map<String, Object> paramMap, String start, String length) {
        return daoHelper.queryForPageList("com.bonc.frame.web.mapper.puborder.ServiceMainMapper.selectPage", paramMap,start,length);
    }

    @Override
    public int insert(ServiceMainBean serviceMainBean) {
        return daoHelper.insert("com.bonc.frame.web.mapper.puborder.ServiceMainMapper.insert",serviceMainBean);
    }

    @Override
    public int update(ServiceMainBean serviceMainBean) {
        return daoHelper.update("com.bonc.frame.web.mapper.puborder.ServiceMainMapper.updateByPrimaryKey",serviceMainBean);
    }

    @Override
    public int delete(String serId) {
        return daoHelper.delete("com.bonc.frame.web.mapper.puborder.ServiceMainMapper.deleteByPrimaryKey",serId);
    }

    @Override
    public String getUrl(String id, String cgfKey) {
        SysCommonCfg sysCommonCfg = sysCommonCfgService.selectByKey(cgfKey);
        String tenantId = CurrentUserUtils.getInstance().getUser().getTenantId();
        String url = String.format(sysCommonCfg.getCfg_value() ,tenantId, id);
        return url;
    }

    @Override
	public ServiceMainBean getByPrimaryKey(String serId) {
		return (ServiceMainBean)daoHelper.queryOne("com.bonc.frame.web.mapper.puborder.ServiceMainMapper.getByPrimaryKey", serId);
	}
}
