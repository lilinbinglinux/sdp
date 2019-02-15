package com.sdp.servflow.pubandorder.pub.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.servflow.pubandorder.pub.model.ProjectBean;
import com.sdp.servflow.pubandorder.pub.service.ProjectBeanService;

/**
 * 
 * ProjectBeanService实现类
 *
 * @author ZY
 * @version 2017年6月10日
 * @see ProjectBeanServiceImpl
 * @since
 */
@Service
public class ProjectBeanServiceImpl implements ProjectBeanService{
    
    /**
     * DaoHelper
     */
    @Resource
    private DaoHelper daoHelper;
	
    @Override
    public Map selectAll(String start, String length, Map<String, Object> paramMap) {
    	return daoHelper.queryForPageList("com.sdp.frame.web.mapper.puborder.ProjectBeanMapper.selectAll", paramMap, start, length);
    }
    
    @Override
    public int insert(ProjectBean project) {
    	return daoHelper.insert("com.sdp.frame.web.mapper.puborder.ProjectBeanMapper.insert", project);
    }
    
    @Override
    public int update(ProjectBean project) {
    	return daoHelper.update("com.sdp.frame.web.mapper.puborder.ProjectBeanMapper.updateByPrimaryKey", project);
    }

    @Override
    public int deleteByProId(String proid) {
        return daoHelper.delete("com.sdp.frame.web.mapper.puborder.ProjectBeanMapper.deleteByPrimaryKey", proid);
    }

    @Override
    public List<ProjectBean> getAllByCondition(Map<String,String> map) {
        return daoHelper.queryForList("com.sdp.frame.web.mapper.puborder.ProjectBeanMapper.getAllByCondition",map);
    }

    @Override
    public ProjectBean getProById(String proid) {
        return (ProjectBean)daoHelper.queryOne("com.sdp.frame.web.mapper.puborder.ProjectBeanMapper.getByPrimaryKey",proid);
    }

}
