package com.sdp.servflow.serlayout.datahandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.servflow.pubandorder.serapitype.model.ServiceTypeBean;
import com.sdp.servflow.pubandorder.serapitype.service.ServiceTypeService;
import com.sdp.servflow.serlayout.process.model.ServiceInfoPo;
import com.sdp.servflow.serlayout.process.util.SerProcessConstantFlag;
import com.sdp.servflow.serlayout.sso.model.SerspLoginBean;
import com.sdp.servflow.serlayout.sso.service.SerspLoginBeanService;
import com.sdp.servflow.sysConfig.service.SysCommonCfgService;

import net.sf.json.JSONObject;

/**
 * 流程图数据来源
 * （ser_main，ser_version）
 * @author zy
 * @Date 2017年12月23日
 */
@Component
public class SerNormalData {
	
	/**
     * daoHelper
     */
    @Resource
    private DaoHelper daoHelper;
    
    @Autowired
	private ServiceTypeService serviceTypeService;
    
    @Resource
    private SerspLoginBeanService serspLoginBeanService;
    
    @Resource
    private SysCommonCfgService sysCommonCfgService;
	
	/**
	 * 暴露数据源
	 * （如果有新的数据源，则添加handler方法和数据源类型常量判断，
	 * 若数据源增多后，可修改为反射实现，但需修改性能处理，从缓存中取类和对应方法）
	 * @param flowChartId
	 * @param serVerId
	 * @param dataType
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getSerFlow(String flowChartId,String serVerId,String dataType) throws Exception {
		Map<String,Object> serflowMap = null;
		
		String typeStr = serTypeHandler(dataType);
		
		if(typeStr.equals(SerFlowDataConstant.spcas_id)) {
			//单点类型
			serflowMap = ssoSerflowDataHandler(flowChartId);
		}else {
			//普通类型（同步或异步）
			serflowMap =  normalSerflowDataHandler(flowChartId,serVerId);
		}
		return serflowMap;
		
	}
	
	
	public void serspLoginBeanHandler(JSONObject nodeobj,String flowxml,String updateFlag) {
		SerspLoginBean serspLoginBean = new SerspLoginBean();
		
		serspLoginBean.setSploginid(nodeobj.getString("flowChartId"));
		serspLoginBean.setSpname(nodeobj.getString("serName"));
		serspLoginBean.setSpcode(serspLoginBean.getSploginid().substring(0, 6));
		serspLoginBean.setSptypeId(nodeobj.getString("serviceType"));
		
		serspLoginBean.setSppath("");
		serspLoginBean.setSpresume(nodeobj.getString("serdesc"));
		serspLoginBean.setSpagreement("0");
		serspLoginBean.setSprestype("0");
		serspLoginBean.setSpflow(flowxml);
		serspLoginBean.setCreatime(new Date());
		serspLoginBean.setDelflag("0");
		serspLoginBean.setStopflag("0");
		serspLoginBean.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
		serspLoginBean.setLoginId(CurrentUserUtils.getInstance().getUser().getLoginId());
		
		if(updateFlag.equals(SerProcessConstantFlag.ADDUPDATE_ADD)) {
			serspLoginBeanService.insert(serspLoginBean);
		}else if(updateFlag.equals(SerProcessConstantFlag.ADDUPDATE_UPDATE)){
			serspLoginBeanService.update(serspLoginBean);
		}
		
	}
  
	
	/**
	 * 数据类型统一判断
	 * （现在分为两种，普通类型与）
	 * @param dataType
	 * @return
	 */
	public String serTypeHandler(String dataType) {
		String typeStr = dataType;
		
		ServiceTypeBean bean = serviceTypeService.selectByPrimaryKey(dataType);

		/**
		 * 类型需根据path来找到根路径，解析到root层
		 */

		if(null != bean&&null != bean.getParentId()) {
			String idpathStr = bean.getIdPath();
			typeStr = idpathStr.substring(1,idpathStr.indexOf("/", 1));
		}
		return typeStr;
	}
	
	
	
	private List<ServiceInfoPo> getServiceVersionBeanByCondition(Map<String,String> map) {
        return daoHelper.queryForList("com.bonc.frame.web.mapper.serlayout.SerLayoutMapper.getServiceVersionBeanByCondition",map);
    }

	
	/**
	 * 普通类型流程图数据源处理
	 * @param flowChartId
	 * @param serVerId
	 * @return
	 * @throws Exception
	 */
	private Map<String,Object> normalSerflowDataHandler(String flowChartId,String serVerId) throws Exception{
		Map<String,Object> serFlowMap = new HashMap<String,Object>();
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("serId", flowChartId);
		map.put("serVerId",serVerId);
		map.put("tenantId", CurrentUserUtils.getInstance().getUser().getTenantId());
		map.put("loginId", CurrentUserUtils.getInstance().getUser().getLoginId());
		System.out.println(map.get("tenantId"));
		System.out.println(map.get("loginId"));
		
		List<ServiceInfoPo> serviceInfoPos = getServiceVersionBeanByCondition(map);
		if(serviceInfoPos.size()>1 || serviceInfoPos == null){
			throw new Exception("查出结果不唯一");
		}else if(serviceInfoPos.size() == 0){
			Map<String,Object> jsonmap = new HashMap<String,Object>();
			jsonmap.put("nodeJsonArray", "");
			jsonmap.put("nodeJoinJsonArray", "");
			jsonmap.put("allJsonArray", "");
			
			serFlowMap.put("flag", "fail");
			serFlowMap.put("serFlow",jsonmap);
			return serFlowMap;
		}
		
		serFlowMap.put("flag", "success");
		serFlowMap.put("serFlow", serviceInfoPos.get(0).getSerFlow());
		return serFlowMap;
	}
	
	
	/**
	 * 单点数据来源处理
	 * @param sploginid
	 * @return
	 */
	private Map<String,Object> ssoSerflowDataHandler(String sploginid){
		Map<String,Object> serFlowMap = new HashMap<String,Object>();
		
		SerspLoginBean serspLoginBean = serspLoginBeanService.getAllByPrimaryKey(sploginid);
		if(null == serspLoginBean) {
			Map<String,Object> jsonmap = new HashMap<String,Object>();
			jsonmap.put("nodeJsonArray", "");
			jsonmap.put("nodeJoinJsonArray", "");
			jsonmap.put("allJsonArray", "");
			
			serFlowMap.put("flag", "fail");
			serFlowMap.put("serFlow",jsonmap);
			return serFlowMap;
		}
		
		serFlowMap.put("flag", "success");
		serFlowMap.put("serFlow", serspLoginBean.getSpflow());
		return serFlowMap;
	}
}
