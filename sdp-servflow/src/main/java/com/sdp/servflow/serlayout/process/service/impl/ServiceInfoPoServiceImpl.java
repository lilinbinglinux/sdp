package com.sdp.servflow.serlayout.process.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.servflow.pubandorder.orderapistore.service.OrderInterfaceService;
import com.sdp.servflow.pubandorder.servicebasic.model.ServiceMainBean;
import com.sdp.servflow.pubandorder.servicebasic.model.ServiceVersionBean;
import com.sdp.servflow.pubandorder.servicebasic.service.ServiceMainService;
import com.sdp.servflow.pubandorder.servicebasic.service.ServiceVersionService;
import com.sdp.servflow.serlayout.process.model.ServiceInfoPo;
import com.sdp.servflow.serlayout.process.service.ServiceInfoPoService;

import net.sf.json.JSONObject;

@Service
public class ServiceInfoPoServiceImpl implements ServiceInfoPoService{
	
	@Autowired
	private ServiceMainService serviceMainService;
	
	@Autowired
	private ServiceVersionService serviceVersionService;

    /**
     * @Authon Niu Haoxuan
     */
	@Autowired
	private OrderInterfaceService orderInterfaceService;
	
	/**
     * DaoHelper
     */
    @Resource
    private DaoHelper daoHelper;

	@Override
	public List<ServiceInfoPo> getAllByCondition(Map<String,String> map) {
		return daoHelper.queryForList("com.sdp.frame.web.mapper.serlayout.SerLayoutMapper.getAllByCondition",map);
	}
	
	@Override
	public List<ServiceInfoPo> getAllEqualInfoByCondition(Map<String,String> map) {
		return daoHelper.queryForList("com.sdp.frame.web.mapper.serlayout.SerLayoutMapper.getAllEqualInfoByCondition",map);
	}
	
	@Override
	public int getAllCount(Map<String,String> map) {
		return (int) daoHelper.queryOne("com.sdp.frame.web.mapper.serlayout.SerLayoutMapper.getAllCount",map);
	}
	
	@Override
	public List<ServiceInfoPo> getTreeData(String jsonStr,String startNum,String num){
		List<ServiceInfoPo> list = new ArrayList<ServiceInfoPo>();
		JSONObject paramobj = JSONObject.fromObject(jsonStr);
		
		Map<String,String> conmap = new HashMap<String,String>();
		conmap.put("delFlag", "0");
		conmap.put("synchFlag", "0");
		conmap.put("tenantId", CurrentUserUtils.getInstance().getUser().getTenantId());
		conmap.put("loginId", CurrentUserUtils.getInstance().getUser().getLoginId());
		conmap.put("startNum",startNum);
		conmap.put("num",num);
		
		if(paramobj != null) {
			//条件查询
			Iterator<String> sIterator = paramobj.keys();
			while(sIterator.hasNext()){
				String key = sIterator.next();
				String value = paramobj.getString(key);
				conmap.put(key, value);
			}
		}
		
		//所有流程图信息
		List<ServiceInfoPo> serviceInfoPoList = getAllByCondition(conmap);
		
		//主版本所有信息
		List<ServiceInfoPo> baseserviceInfoPoList = getAllEqualInfoByCondition(conmap);
		
		for(ServiceInfoPo baseserviceInfoPo:baseserviceInfoPoList){
			baseserviceInfoPo.setParentId("ROOT");
			//children版本
			List<ServiceInfoPo> children = new ArrayList<ServiceInfoPo>();
			
			for(ServiceInfoPo serviceInfoPo:serviceInfoPoList){
				String poserId = serviceInfoPo.getSerId();
				poserId = poserId != null ? poserId:"";
				
				String basesposerId = baseserviceInfoPo.getSerId();
				basesposerId = basesposerId != null ? basesposerId:"";
				
				String poserVersion = serviceInfoPo.getSerVersion();
				poserVersion = poserVersion != null ? poserVersion:"";
				
				String baseposerVersion = baseserviceInfoPo.getSerVersion();
				baseposerVersion = baseposerVersion != null ? baseposerVersion:"";
				
				if(poserId.equals(basesposerId) && 
						!poserVersion.equals(baseposerVersion)){
					serviceInfoPo.setSerName("");
					serviceInfoPo.setParentId(baseserviceInfoPo.getSerVerId());
					children.add(serviceInfoPo);
				}
			}
			
			
			baseserviceInfoPo.setChildren(children);
			list.add(baseserviceInfoPo);

		}
		
		return list;
		
	}

	@Override
	public String deleteSerProcess(ServiceInfoPo po) {
	    String serVersion = null;
		
		//ser_main更新
		//如果是删除最新版本，则把主表版本更新为下一个版本
		if(po.getParentId().equals("ROOT")){
			ServiceMainBean serviceMainBean = serviceMainService.getByPrimaryKey(po.getSerId());

            serVersion = serviceMainBean.getSerVersion();

			Map<String,String> map = new HashMap<String,String>();
			map.put("delFlag", "0");
			map.put("serId", po.getSerId());
			map.put("loginId", CurrentUserUtils.getInstance().getUser().getLoginId());
			map.put("tenantId",CurrentUserUtils.getInstance().getUser().getTenantId());
			
			List<ServiceVersionBean> serviceVersionBeans = serviceVersionService.getAllByCondition(map);
			
			if(serviceVersionBeans != null&&serviceVersionBeans.size()>1){
				serviceMainBean.setSerVersion(serviceVersionBeans.get(1).getSerVersion());
				serviceMainService.update(serviceMainBean);
			}else {
				serviceMainBean.setDelFlag("1");
				serviceMainService.update(serviceMainBean);
			}
		}
		
		//ser_version更新
		ServiceVersionBean serviceVersionBean = serviceVersionService.getByPrimaryKey(po.getSerVerId());
		serviceVersionBean.setDelFlag("1");
		serviceVersionService.update(serviceVersionBean);

		//如果该服务版本被订阅，则删除订阅
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("serId", po.getSerId());
        paramMap.put("serVersion", serVersion);
        orderInterfaceService.deleteBySerIdAndVersion(paramMap);

		return "success";
	}
	

}
