package com.sdp.servflow.pubandorder.pub.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.servflow.pubandorder.pub.model.PublisherBean;
import com.sdp.servflow.pubandorder.pub.service.PublisherBeanService;

/**
 * 
 * PublisherBeanService实现类
 *
 * @author ZY
 * @version 2017年6月10日
 * @see PublisherBeanServiceImpl
 * @since
 */
@Service
public class PublisherBeanServiceImpl implements PublisherBeanService{
    
    /**
     * daoHelper
     */
    @Resource
    private DaoHelper daoHelper;
    
    @SuppressWarnings("rawtypes")
    @Override
    public Map selectAll(String start, String length, Map<String, Object> paramMap) {
    	return daoHelper.queryForPageList("com.sdp.frame.web.mapper.puborder.PublisherBeanMapper.selectAll", paramMap, start, length);
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    public Map selectPage(String start, String length, Map<String, Object> paramMap) {
        return daoHelper.queryForPageList("com.sdp.frame.web.mapper.puborder.PublisherBeanMapper.selectPage", paramMap, start, length);
    }
    
    @Override
    public int insert(PublisherBean publisher) {
    	return daoHelper.insert("com.sdp.frame.web.mapper.puborder.PublisherBeanMapper.insertInterface", publisher);
    }
    
    @Override
    public int update(PublisherBean publisher) {
    	return daoHelper.update("com.sdp.frame.web.mapper.puborder.PublisherBeanMapper.updateByPrimaryKey", publisher);
    }

    @Override
    public int deleteByPubId(String pubid) {
        return daoHelper.delete("com.sdp.frame.web.mapper.puborder.PublisherBeanMapper.deleteByPrimaryKey", pubid);
    }
    
    @Override
    public int deleteByCondition(Map<String,String> map) {
        return daoHelper.delete("com.sdp.frame.web.mapper.puborder.PublisherBeanMapper.deleteByCondition", map);
    }

    @Override
    public List<PublisherBean> getAllByCondition(Map<String,String> map) {
        return daoHelper.queryForList("com.sdp.frame.web.mapper.puborder.PublisherBeanMapper.getAllByCondition",map);
    }

    @Override
    public PublisherBean getPubById(String pubid) {
        return (PublisherBean)daoHelper.queryOne("com.sdp.frame.web.mapper.puborder.PublisherBeanMapper.getByPrimaryKey", pubid);
    }

    public int deletePubParamByPubCondition(Map<String, Object> condition) {
        return daoHelper.delete("com.sdp.frame.web.mapper.puborder.PublisherBeanMapper.deletePubParamByPubCondition", condition);
     }

}
