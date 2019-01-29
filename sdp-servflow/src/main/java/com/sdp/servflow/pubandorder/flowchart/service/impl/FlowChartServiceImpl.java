package com.sdp.servflow.pubandorder.flowchart.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.servflow.pubandorder.flowchart.model.FlowChart;
import com.sdp.servflow.pubandorder.flowchart.service.FlowChartService;
import com.sdp.servflow.pubandorder.flowchart.service.ParamFlowChartBeanService;
import com.sdp.servflow.pubandorder.flowchart.service.ProcessNodeJoinService;
import com.sdp.servflow.pubandorder.flowchart.service.ProcessNodeService;
import com.sdp.servflow.pubandorder.flowchart.service.PubFlowChartBeanService;
import com.sdp.servflow.pubandorder.pub.service.PublisherBeanService;
import com.sdp.servflow.pubandorder.pub.service.ReqparamBeanService;
@Service
public class FlowChartServiceImpl implements FlowChartService{
	/**
     * DaoHelper
     */
    @Resource
    private DaoHelper daoHelper;
    
    @Autowired
    private ProcessNodeService processNodeService;
    
    @Autowired
    private ProcessNodeJoinService processNodeJoinService;
    
    @Autowired
    private PubFlowChartBeanService pubFlowChartBeanService;
    
    @Autowired
    private ParamFlowChartBeanService paramFlowChartBeanService;
    
    @Autowired
    private PublisherBeanService publisherBeanService;
    
    @Autowired
    private ReqparamBeanService reqparamBeanService;


	/**
	 * 根据条件查询流程图
	 */
	/*@Override
	public Pagination findAllByCondition(FlowChart flowChart, Pagination page) {
		PageHelper.startPage(Integer.valueOf(page.getPageNo()+""),page.getPageSize());
		List<FlowChart> list = flowChartDao.findAllByCondition(flowChart);
		PageInfo<FlowChart> pageinfo = new PageInfo<FlowChart>(list);
		page.setList(pageinfo.getList());
		page.setTotalCount(pageinfo.getTotal());
		return page;
	}*/

	/**
	 * 增加流程图
	 */
	@Override
	public void addFlowChart(FlowChart flowChart) {
	    daoHelper.insert("com.bonc.servflow.pubandorder.flowchart.mapper.FlowChartMapper.addFlowChart", flowChart);
	}

	/**
	 * 修改流程图
	 */
	@Override
	public void updateFlowChart(FlowChart flowChart) {
	    daoHelper.update("com.bonc.servflow.pubandorder.flowchart.mapper.FlowChartMapper.updateFlowChart",flowChart );
	}

	/**
	 * 删除流程图
	 */
	@Override
	public void deleteFlowChart(Map<String, String> map) {
		processNodeJoinService.deleteAll(map);
		processNodeService.deleteAll(map);
		daoHelper.delete("com.bonc.servflow.pubandorder.flowchart.mapper.FlowChartMapper.deleteFlowChart", map);
	}
	
	/**
	 * 修改流程图名称
	 */
	@Override
	public void updateName(FlowChart flowChart) {
	    daoHelper.update("com.bonc.servflow.pubandorder.flowchart.mapper.FlowChartMapper.updateName", flowChart);
	}

	/**
	 * 通过id查询流程图
	 */
	@Override
	public FlowChart findByIdAndTenantId(Map<String, String> map) {
	    return (FlowChart)daoHelper.queryOne("com.bonc.servflow.pubandorder.flowchart.mapper.FlowChartMapper.findByIdAndTenantId", map);
	}

    @Override
    public Map selectAll(String start, String length, Map<String, Object> paramMap) {
        return daoHelper.queryForPageList("com.bonc.servflow.pubandorder.flowchart.mapper.FlowChartMapper.selectAll", paramMap, start, length);
    }

    @Override
    public void deleteLaySerAll(String flowChartId) {
        
        //1.删除编排服务中的参数映射
        deleteParamFlowChart(flowChartId);
        
        //2.删除设置的业务参数和公共参数
        deleteReqparam(flowChartId);
        
        //3.删除服务节点
        deletePubFlowChart(flowChartId);
        
        //4.删除流程图对应的编排服务
        deletePublisher(flowChartId);
        
        //5.删除流程图
        Map<String , String> map=new HashMap<String,String>();
        map.put("flowChartId", flowChartId);
        map.put("tenant_id", CurrentUserUtils.getInstance().getUser().getTenantId());
        deleteFlowChart(map);
        
        //6.删除apipush中的数据(本地推送库)
        //dockingService.delete(flowChartId);
        
    }
    
    private void deletePublisher(String flowChartId){
        Map<String , String> publishermap=new HashMap<String,String>();
        publishermap.put("pubid", flowChartId);
        publishermap.put("typeId", "3");
        publishermap.put("tenant_id", CurrentUserUtils.getInstance().getUser().getTenantId());
        publisherBeanService.deleteByCondition(publishermap);
    }
    
    private void deletePubFlowChart(String flowChartId){
        Map<String , String> pubFlowChartmap=new HashMap<String,String>();
        pubFlowChartmap.put("flowChartId", flowChartId);
        pubFlowChartmap.put("tenant_id", CurrentUserUtils.getInstance().getUser().getTenantId());
        pubFlowChartBeanService.deleteByCondition(pubFlowChartmap);
    }
    
    private void deleteParamFlowChart(String flowChartId){
        Map<String , String> paramFlowChartmap=new HashMap<String,String>();
        paramFlowChartmap.put("flowChartId", flowChartId);
        paramFlowChartmap.put("tenant_id", CurrentUserUtils.getInstance().getUser().getTenantId());
        paramFlowChartBeanService.deleteByCondition(paramFlowChartmap);
    }
    
    private void deleteReqparam(String flowChartId){
        Map<String , String> reqparammap=new HashMap<String,String>();
        reqparammap.put("pubid", flowChartId);
        reqparammap.put("tenant_id", CurrentUserUtils.getInstance().getUser().getTenantId());
        reqparamBeanService.deleteByCondition(reqparammap);
    }
}
