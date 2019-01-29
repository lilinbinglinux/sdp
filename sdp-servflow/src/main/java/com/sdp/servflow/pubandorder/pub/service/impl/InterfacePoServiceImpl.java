package com.sdp.servflow.pubandorder.pub.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.servflow.pubandorder.flowchart.model.PubFlowChartBean;
import com.sdp.servflow.pubandorder.flowchart.service.FlowChartService;
import com.sdp.servflow.pubandorder.flowchart.service.PubFlowChartBeanService;
import com.sdp.servflow.pubandorder.pub.model.InterfacePo;
import com.sdp.servflow.pubandorder.pub.model.ProjectBean;
import com.sdp.servflow.pubandorder.pub.model.PublisherBean;
import com.sdp.servflow.pubandorder.pub.service.InterfacePoService;
import com.sdp.servflow.pubandorder.pub.service.ProjectBeanService;
import com.sdp.servflow.pubandorder.pub.service.PublisherBeanService;

/**
 * 
 * 抽象类InterfacePo
 *
 * @author ZY
 * @version 2017年6月10日
 * @see InterfacePoServiceImpl
 * @since
 */
@Service
public class InterfacePoServiceImpl implements InterfacePoService{
    
    /**
     * daoHelper
     */
    @Resource
	private DaoHelper daoHelper;
	
    /**
     * 项目或模块Service
     */
    @Resource
    private ProjectBeanService projectService;
    @Autowired
    private FlowChartService flowChartService;
    @Autowired
    private  PubFlowChartBeanService pubFlowChartBeanService;
    
	/**
	 * API处理Service
	 */
    @Resource
	private PublisherBeanService publisherService;
	
	/**
	 * 获取页面树形数据
	 */
    @Override
    public List<InterfacePo> selectAll(){
        List<InterfacePo> pos = new ArrayList<InterfacePo>();
        
/*        InterfacePo allrootinterpo = setInterfacePo("allroot","服务","0","root",CurrentUserUtils.getInstance().getUser().getTenantId());
        pos.add(allrootinterpo);*/

        //设置树的字节点（模块）
        InterfacePo rootinterpo = setInterfacePo("ROOT","服务api","allroot","root",CurrentUserUtils.getInstance().getUser().getTenantId());
        pos.add(rootinterpo);
        
        InterfacePo rootinterpo2 = setInterfacePo("ROOT1", "服务编排", "allroot", "root1",CurrentUserUtils.getInstance().getUser().getTenantId());
        pos.add(rootinterpo2);
        
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("tenant_id", CurrentUserUtils.getInstance().getUser().getTenantId());
        List<ProjectBean> pros = projectService.getAllByCondition(paramMap);
        //获取所有的项目和模块
        for(ProjectBean pro: pros){
            InterfacePo interpo = setInterfacePo(pro.getProid(),pro.getName(),pro.getParentId(),pro.getTypeId(),pro.getTenant_id());
            pos.add(interpo);
        }
        
        //获取所有的接口
        Map<String,String> map = new HashMap<String,String>();
        map.put("tenant_id", CurrentUserUtils.getInstance().getUser().getTenantId());
        //map.put("checkstatus", "100");
        map.put("stylecode", "");
        List<PublisherBean> pubs = publisherService.getAllByCondition(map);
        for(PublisherBean pub: pubs){
            InterfacePo interpo = setInterfacePo(pub.getPubid(),pub.getName(),pub.getParentId(),pub.getTypeId(),pub.getTenant_id());
            pos.add(interpo);
        }
        
       
        
        return pos;
    }
    
    @Override
    public String delete(String id, String typeId,String tenant_id) {
        String flag = "";
        
        //如果是删除项目或模块，需要判断是否有子节点
        if (typeId.equals("0")||typeId.equals("1")) {
            
            //判断是否有直接的api
            Map<String,String> parammap = new HashMap<String,String>();
            parammap.put("parentId", id);
            parammap.put("tenant_id", tenant_id);
            List<PublisherBean> pubs = publisherService.getAllByCondition(parammap);
            
            //下面直接有api，则删除失败
            if(null != pubs && pubs.size()>0){
                flag = "deletefalse";
                return flag;
            }
            
            //如果是项目的话需判断下面是否有模块
            else if(typeId.equals("0")){
                Map<String,String> paramMap = new HashMap<String,String>();
                paramMap.put("parentId", id);
                paramMap.put("typeId", "1");
                parammap.put("tenant_id", tenant_id);
                List<ProjectBean> pros = projectService.getAllByCondition(paramMap);
                
                //下面有模块，则删除失败
                if(null != pros && pros.size()>0){
                    flag = "deletefalse";
                }else{
                    projectService.deleteByProId(id);
                    flag = "deletesuccess";
                }
            }
            else{
                projectService.deleteByProId(id);
                flag = "deletesuccess";
            }
        }
        
        //如果直接删除API，则无需判断
        else if ("2".equals(typeId)||"4".equals(typeId)) {
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("pubid", id);
            condition.put("tenant_id", tenant_id);
            
            if("2".equals(typeId)){
                Map<String, String> parammap = new HashMap<String, String>();
                parammap.put("pubid", id);
                parammap.put("tenant_id", tenant_id);
                //先查一下流程图的编排里是否存在，如果已经被编排则不能删除
                List<PubFlowChartBean> beans = pubFlowChartBeanService.getAllByCondition(parammap);
                if(beans != null&&beans.size()>0){
                    flag = "flowchartdeletefalse";
                }
                else{
                    publisherService.deleteByPubId(id);
                    publisherService.deletePubParamByPubCondition(condition);
                    flag = "deletesuccess";
                }
            }else{
                publisherService.deleteByPubId(id);
                publisherService.deletePubParamByPubCondition(condition);
                flag = "deletesuccess";
            }
        }
        //删除编排后的服务
        else if ("3".equals(typeId)) {
            flowChartService.deleteLaySerAll(id);
            flag = "deletesuccess";
        }
        return flag;
    }
    
    
    /**
     * 
     * Description:初始化 InterfacePo
     * 
     *@param Id
     *@param name
     *@param parentId
     *@param typeId
     *@return InterfacePo
     *
     * @see
     */
    private InterfacePo setInterfacePo(String id,String name,String parentId,String typeId,String tenant_id){
        InterfacePo interpo = new InterfacePo();
        interpo.setId(id);
        interpo.setName(name);
        interpo.setParentId(parentId);
        interpo.setTypeId(typeId);
        interpo.setTenant_id(tenant_id);
        return interpo;
    }

    


}
