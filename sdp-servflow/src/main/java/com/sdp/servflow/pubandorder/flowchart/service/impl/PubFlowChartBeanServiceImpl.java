package com.sdp.servflow.pubandorder.flowchart.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.servflow.pubandorder.flowchart.model.PubFlowChartBean;
import com.sdp.servflow.pubandorder.flowchart.service.PubFlowChartBeanService;
@Service
public class PubFlowChartBeanServiceImpl implements PubFlowChartBeanService{
	
	/**
     * DaoHelper
     */
    @Resource
    private DaoHelper daoHelper;

    @Override
    public void insert(PubFlowChartBean pubFlowChartBean) {
        daoHelper.insert("com.bonc.frame.web.mapper.puborder.flowChart.PubFlowChartBeanMapper.insert", pubFlowChartBean);
    }

    @Override
    public void deleteByCondition(Map<String, String> map) {
        daoHelper.delete("com.bonc.frame.web.mapper.puborder.flowChart.PubFlowChartBeanMapper.deleteByCondition", map);
    }

    @Override
    public List<PubFlowChartBean> select(String flowChartId) {
        return daoHelper.queryForList("com.bonc.frame.web.mapper.puborder.flowChart.PubFlowChartBeanMapper.select", flowChartId);
    }

	@Override
	public PubFlowChartBean getConditionBypubid(Map<String, String> map) {
		return (PubFlowChartBean) daoHelper.queryOne("com.bonc.frame.web.mapper.puborder.flowChart.PubFlowChartBeanMapper.selectBypubId", map);
	}

    @Override
    public List<PubFlowChartBean> getAllByCondition(Map<String, String> map) {
        return daoHelper.queryForList("com.bonc.frame.web.mapper.puborder.flowChart.PubFlowChartBeanMapper.getAllByCondition", map);
    }
}
