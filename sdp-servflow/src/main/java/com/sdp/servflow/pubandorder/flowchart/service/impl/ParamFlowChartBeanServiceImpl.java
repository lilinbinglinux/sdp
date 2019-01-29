package com.sdp.servflow.pubandorder.flowchart.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.servflow.pubandorder.flowchart.model.ParamFlowChartBean;
import com.sdp.servflow.pubandorder.flowchart.service.ParamFlowChartBeanService;
@Service
public class ParamFlowChartBeanServiceImpl implements ParamFlowChartBeanService{
	
	/**
     * DaoHelper
     */
    @Resource
    private DaoHelper daoHelper;

    @Override
    public void insert(ParamFlowChartBean paramFlowChartBean) {
        daoHelper.insert("com.bonc.frame.web.mapper.puborder.flowChart.ParamFlowChartBeanMapper.insert", paramFlowChartBean);
    }
    
    @Override
    public void updateByPrimaryKey(ParamFlowChartBean paramFlowChartBean) {
        daoHelper.update("com.bonc.frame.web.mapper.puborder.flowChart.ParamFlowChartBeanMapper.updateByPrimaryKey", paramFlowChartBean);
    }
	
    //处理传回来的串存入数据库
    //@Override
    /*public void insertObj(String pubrobj, String regexobj) {
        List<ParamFlowChartBean> list = getObj(pubrobj);
        List<ParamFlowChartBean> list1 = getregexObj(regexobj);
        
        for(ParamFlowChartBean pflowchart:list){
            if(null != list1&&list1.size()>0){
                for( ParamFlowChartBean rflowchart : list1){
                    if(rflowchart.getNext_paramid().equals(pflowchart.getNext_paramid())){
                        pflowchart.setRegex(rflowchart.getRegex());
                        pflowchart.setRelationid(IdUtil.createId());
                        insert(pflowchart);
                    }else{
                        pflowchart.setRelationid(IdUtil.createId());
                        insert(pflowchart);
                    }
                }
            }else{
                pflowchart.setRelationid(IdUtil.createId());
                insert(pflowchart);
            }
        }
    }*/
    
   /* @Override
    public void updateParamRelation(String pubrobj) {
        List<ParamFlowChartBean> list = getObj(pubrobj);
        
        for(ParamFlowChartBean pflowchart:list){
            updateByPrimaryKey(pflowchart);
        }
    }*/

    @Override
    public void deleteByCondition(Map<String, String> map) {
        daoHelper.delete("com.bonc.frame.web.mapper.puborder.flowChart.ParamFlowChartBeanMapper.deleteByCondition", map);
    }

    @Override
    public List<ParamFlowChartBean> getAllByCondition(Map<String, String> map) {
        return daoHelper.queryForList("com.bonc.frame.web.mapper.puborder.flowChart.ParamFlowChartBeanMapper.getAllByCondition", map);
    }
    
    
   /* //把传回来的json串解成对象
    private List<ParamFlowChartBean> getObj(String pubrobj){
        List<ParamFlowChartBean> list = new ArrayList<ParamFlowChartBean>();
        String relationid = "";
        if(StringUtils.isNotBlank(pubrobj)){
            String ss[] = pubrobj.replace("[", "").replace("]", "").split("},");
            if(ss.length>0&&!ss[0].equals("")){
                for(int i=0;i<ss.length;i++){
                    if(!ss[i].contains("}")){
                        ss[i] = ss[i]+"}";
                    }
                    JSONObject jsonobj = JSONObject.fromObject(ss[i]);
                    
                    if(ss[i].contains("relationid"))
                        relationid = jsonobj.getString("relationid");
                    ParamFlowChartBean paramFlowChartBean = new ParamFlowChartBean(
                        relationid,
                        jsonobj.getString("pubid"),jsonobj.getString("flowChartId"),
                        jsonobj.getString("preparamid"),jsonobj.getString("preparamname"),
                        jsonobj.getString("nextparamid"),jsonobj.getString("nextparamname"),
                        jsonobj.getString("diamondid"),
                        CurrentUserUtils.getInstance().getUser().getLoginId(),
                        CurrentUserUtils.getInstance().getUser().getTenantId(),
                        "1",
                        ""
                        );
                    list.add(paramFlowChartBean);
                }
            }
            
        }
        return list;
    }
    
    //把传回来的json串解成对象
    private List<ParamFlowChartBean> getregexObj(String regexobj){
        List<ParamFlowChartBean> list = new ArrayList<ParamFlowChartBean>();
        String relationid = "";
        if(StringUtils.isNotBlank(regexobj)){
            String ss[] = regexobj.replace("[", "").replace("]", "").split("},");
            if(ss.length>0&&!ss[0].equals("")){
                for(int i=0;i<ss.length;i++){
                    if(!ss[i].contains("}")){
                        ss[i] = ss[i]+"}";
                    }
                    JSONObject jsonobj = JSONObject.fromObject(ss[i]);
                    
                    if(ss[i].contains("relationid"))
                        relationid = jsonobj.getString("relationid");
                    ParamFlowChartBean paramFlowChartBean = new ParamFlowChartBean(
                        relationid,
                        "","","","","",
                        jsonobj.getString("nextparamid"),
                        "", "", "", "",
                        jsonobj.getString("regex")
                        );
                    list.add(paramFlowChartBean);
                }
            }
            
        }
        return list;
    }*/

    @Override
    public String paramIsCount(String flowChartId) {
        return (String)daoHelper.queryOne("com.bonc.frame.web.mapper.puborder.flowChart.ParamFlowChartBeanMapper.paramIsCount", flowChartId);
    }

	@Override
	public List<ParamFlowChartBean> findLayoutParam(String pubid , String flowChartId,String node_id) {
		 Map<String, String> parammap = new HashMap<String, String>();
		 parammap.put("node_id", node_id);
		 parammap.put("pubid", pubid);
		 parammap.put("flowChartId", flowChartId);
		 parammap.put("type", "3");
		return daoHelper.queryForList("com.bonc.frame.web.mapper.puborder.flowChart.ParamFlowChartBeanMapper.findCondition", parammap);
	}

    
}
