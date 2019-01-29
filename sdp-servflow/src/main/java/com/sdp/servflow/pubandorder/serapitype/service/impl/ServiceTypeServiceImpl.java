package com.sdp.servflow.pubandorder.serapitype.service.impl;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.frame.util.IdUtil;
import com.sdp.servflow.pubandorder.serapitype.model.ServiceTypeBean;
import com.sdp.servflow.pubandorder.serapitype.service.ServiceTypeService;
import com.sdp.servflow.serlayout.datahandler.SerFlowDataConstant;

/**
 * Description:
 *
 * @author 牛浩轩
 * @date Created on 2017/10/31.
 */
@Service
public class ServiceTypeServiceImpl implements ServiceTypeService{
    /**
     * DaoHelper
     */
    @Resource
    private DaoHelper daoHelper;

    @Override
    public List<ServiceTypeBean> getAllByCondition(Map<String, String> map) {
        return daoHelper.queryForList("com.bonc.frame.web.mapper.puborder.ServiceTypeMapper.getAllByCondition", map);
    }

    @Override
    public List<ServiceTypeBean> selectgetTimeByCondition(ServiceTypeBean serviceTypeBean) {
        return daoHelper.queryForList("com.bonc.frame.web.mapper.puborder.ServiceTypeMapper.selectPage", serviceTypeBean);
    }

    @Override
    public List<ServiceTypeBean> getAllEqalsByCondition(Map<String, String> map) {
        return daoHelper.queryForList("com.bonc.frame.web.mapper.puborder.ServiceTypeMapper.getAllEqalsByCondition", map);
    }

    @Override
    public int insert(ServiceTypeBean serviceTypeBean) {
        serviceTypeBean.setSerTypeId(IdUtil.createId());
        serviceTypeBean.setCreatUser(CurrentUserUtils.getInstance().getUser().getLoginId());
        serviceTypeBean.setCreatTime(new Date(System.currentTimeMillis()));
        serviceTypeBean.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
        Map<String, Object> map = getPath(serviceTypeBean.getSerTypeId(), serviceTypeBean.getParentId(), serviceTypeBean.getSerTypeName());
        serviceTypeBean.setIdPath((String) map.get("idPath"));
        serviceTypeBean.setNamePath((String) map.get("namePath"));
        return daoHelper.insert("com.bonc.frame.web.mapper.puborder.ServiceTypeMapper.insert", serviceTypeBean);
    }

    @Override
    public int update(ServiceTypeBean serviceTypeBean) {
        return daoHelper.update("com.bonc.frame.web.mapper.puborder.ServiceTypeMapper.updateByPrimaryKey",serviceTypeBean);
    }

    @Override
    public int delete(String serTypeId) {
        return daoHelper.delete("com.bonc.frame.web.mapper.puborder.ServiceTypeMapper.deleteByPrimaryKey",serTypeId);
    }

    @Override
    public List<ServiceTypeBean> selectFilterDate() {
        Map<String, String> map = new HashMap<>();
        map.put("delFlag","0");
        return getAllByCondition(map);
    }

    /**
     * 获取id及name的层级路径
     * @param serTypeId
     * @param parentId
     * @param serTypeName
     * @return
     */
    private Map<String, Object> getPath(String serTypeId, String parentId, String serTypeName){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        StringBuilder idPathsb = new StringBuilder();
        StringBuilder namePathsb = new StringBuilder();
        idPathsb.insert(0, "/" + serTypeId);
        namePathsb.insert(0, "/" + serTypeName);

        do {
            Map<String, String> map = new HashMap<String, String>();
            map.put("serTypeId", parentId);
            List<ServiceTypeBean> list = getAllByCondition(map);
            if (list.size() > 0 && list != null){
                parentId = list.get(0).getParentId();
                idPathsb.insert(0, "/" + list.get(0).getSerTypeId());
                namePathsb.insert(0, "/" + list.get(0).getSerTypeName());
                resultMap.put("idPath", idPathsb.toString());
                resultMap.put("namePath", namePathsb.toString());
            }else {
                resultMap.put("idPath", idPathsb.toString());
                resultMap.put("namePath", namePathsb.toString());
            }
        } while (StringUtils.isNotBlank(parentId));

        return resultMap;
    }

	@Override
	public ServiceTypeBean selectByPrimaryKey(String id) {
		return (ServiceTypeBean)daoHelper.queryOne("com.bonc.frame.web.mapper.puborder.ServiceTypeMapper.selectByPrimaryKey",id);
	}

	@Override
	public List<ServiceTypeBean> selectCollection(String serTypeId) {
		List<ServiceTypeBean> beans = new ArrayList<ServiceTypeBean>();
		
		SerFlowDataConstant serFlowDataConstant = new SerFlowDataConstant();
		try {
			Field[] fields = serFlowDataConstant.getClass().getDeclaredFields(); 
			Map<String,String> map = new HashMap<String,String>();
			map.put("delFlag", "0");
			map.put("stopFlag", "0");
			map.put("tenantId", CurrentUserUtils.getInstance().getUser().getTenantId());
			for (Field field : fields) {
				System.out.println(field.get(serFlowDataConstant));
				//如果id本身为根节点，则需查到所有子节点
				if(serTypeId.equals(field.get(serFlowDataConstant))) {
					map.put("parentId", serTypeId);
					beans = getAllEqalsByCondition(map);
					
					ServiceTypeBean serviceTypeBean = selectByPrimaryKey(serTypeId);
					beans.add(serviceTypeBean);
					return beans;
				}
			}
			
			//如果id为子节点
			ServiceTypeBean serviceTypeBean = selectByPrimaryKey(serTypeId);
			ServiceTypeBean rootserviceTypeBean = null;
			String idpathStr = "";
			if(null != serviceTypeBean) {
				idpathStr = serviceTypeBean.getIdPath();
				map.put("parentId", idpathStr.substring(1,idpathStr.indexOf("/", 1)));
				rootserviceTypeBean = selectByPrimaryKey(idpathStr.substring(1,idpathStr.indexOf("/", 1)));
			}
			
			beans = getAllByCondition(map);
			if(null != rootserviceTypeBean) {
				beans.add(rootserviceTypeBean);
			}
			
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return beans;
	}
}
