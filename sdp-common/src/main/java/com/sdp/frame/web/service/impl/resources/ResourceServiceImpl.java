package com.sdp.frame.web.service.impl.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.frame.util.IdUtil;
import com.sdp.frame.util.UserUtil;
import com.sdp.frame.web.entity.resources.Resources;
import com.sdp.frame.web.service.resources.ResourceService;

/**
 * @author 作者: 吕一凡 
 * @date 2017年1月11日 下午4:03:16 
 * @version 版本: 1.0
 */
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService{

	@Resource
	private DaoHelper daoHelper;
	@Override
	public List<Resources> selectAll(String userId) {
		return daoHelper.queryForList("com.sdp.frame.web.mapper.resources.ResourcesMapper.selectAllResourceList", userId);
	}

	@Override
	public List<Resources> selectAllButton(String userId) {
		return daoHelper.queryForList("com.sdp.frame.web.mapper.resources.ResourcesMapper.selectAllButtonResourceList", userId);
	}

	@Override
	public Resources selectByResourceId(String resourceId) {
		return (Resources)daoHelper.queryOne("com.sdp.frame.web.mapper.resources.ResourcesMapper.selectByPrimaryKey", resourceId);
	}

	@Override
	public int update(Resources resources) {
		return daoHelper.update("com.sdp.frame.web.mapper.resources.ResourcesMapper.updateByPrimaryKeySelective", resources);
	}

	@Override
	public int insert(Resources resources) {
		String resourcesId = IdUtil.createId();
		resources.setResourcesId(resourcesId);
		resources.setPath(resources.getPath()+"/"+resourcesId);
		return daoHelper.insert("com.sdp.frame.web.mapper.resources.ResourcesMapper.insertSelective", resources);
	}

	@Transactional
	@Override
	public int deleteByResourceId(String resourceId) throws Exception {
		int count = (int) daoHelper.queryOne("com.sdp.frame.web.mapper.resources.ResourcesMapper.selectChildResource", resourceId);
		if(count>0){
			throw new Exception("资源下有资源，请先删除子资源后再删除该资源。");
		}
		daoHelper.delete("com.sdp.frame.web.mapper.resources.ResourcesMapper.deleteRoleResourceRef", resourceId);
		daoHelper.delete("com.sdp.frame.web.mapper.resources.ResourcesMapper.deleteUserResourceRef", resourceId);
		return daoHelper.delete("com.sdp.frame.web.mapper.resources.ResourcesMapper.deleteByPrimaryKey", resourceId);
	}

	@Override
	public List<Resources> resourcesListTree() {
		List<Resources> list = new ArrayList<Resources>();
		Resources r = new Resources();
		r.setResourcesId("ROOT");
		r.setResourcesName("系统资源");
		r.setText("系统资源");
		List<Resources> resourceList= daoHelper.queryForList("com.sdp.frame.web.mapper.resources.ResourcesMapper.selectChildrenResource", null);
		r.setChildren(UserUtil.convertTree(resourceList));
		list.add(r);
		return list;
	}
	
	@Override
	public List<Resources> userMenuTree(String userId) {
		return daoHelper.queryForList("com.sdp.frame.web.mapper.resources.ResourcesMapper.selectUserResource", userId);
	}

	@Override
	public List<Resources> selectResourcesList() {
		return daoHelper.queryForList("com.sdp.frame.web.mapper.resources.ResourcesMapper.selectResourcesList");
	}

	@Override
	public Map selectPage(String start,String length,Map<String, Object> paramMap) {
		return daoHelper.queryForPageList("com.sdp.frame.web.mapper.resources.ResourcesMapper.selectPage", paramMap,start,length);
	}

}
