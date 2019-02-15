package com.sdp.frame.web.service.impl.role;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.frame.util.IdUtil;
import com.sdp.frame.web.entity.resources.Resources;
import com.sdp.frame.web.entity.role.Role;
import com.sdp.frame.web.service.role.RoleService;

/**
 * 角色管理service接口，此接口内包含对角色信息的一些常用操作的封装.
 * @author 作者: 吕一凡 
 * @date 2017年1月17日 下午4:06:59 
 * @version 版本: 1.0
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService{

	@Resource
	private DaoHelper daoHelper;
	
	@Override
	public Map selectAll(String start, String length,Map<String,Object>paramMap) {
		return daoHelper.queryForPageList("com.sdp.frame.web.mapper.role.RoleMapper.selectAll", paramMap, start, length);
	}

	@Override
	public Role selectByRoleId(String roleId) {
		return (Role)daoHelper.queryOne("com.sdp.frame.web.mapper.role.RoleMapper.selectByPrimaryKey", roleId);
	}

	@Override
	public List<Resources> selectResourcesByRoleId(String roleId) {
		return daoHelper.queryForList("com.sdp.frame.web.mapper.role.RoleMapper.selectResourcesByRoleId", roleId);
	}

	@Override
	public int deleteResourcesByRoleId(String roleId) {
		return daoHelper.delete("com.sdp.frame.web.mapper.role.RoleMapper.deleteResourcesByRoleId", roleId);
	}

	@Override
	public int update(Role role) {
		return daoHelper.update("com.sdp.frame.web.mapper.role.RoleMapper.updateByPrimaryKeySelective", role);
	}

	@Override
	public int insert(Role role) {
		return daoHelper.insert("com.sdp.frame.web.mapper.role.RoleMapper.insertSelective", role);
	}

	@Transactional
	@Override
	public int deleteByRoleId(String roleId) {
		daoHelper.delete("com.sdp.frame.web.mapper.role.RoleMapper.deleteResourceRoleRef", roleId);
		daoHelper.delete("com.sdp.frame.web.mapper.role.RoleMapper.deleteUserRoleRef", roleId);
		return daoHelper.delete("com.sdp.frame.web.mapper.role.RoleMapper.deleteByPrimaryKey", roleId);
	}

	@Transactional
	@Override
	public void insertRoleResourceRef(List<Map> list,String roleId) throws Exception {
		daoHelper.delete("com.sdp.frame.web.mapper.role.RoleMapper.deleteResourceRoleRef", roleId);
		for(Map<String,Object> map:list){
			map.put("id", IdUtil.createId());
			daoHelper.insert("com.sdp.frame.web.mapper.role.RoleMapper.insertRoleResourceRef", map);
		}
	}

	@Override
	public List<Role> selectAll() {
		return daoHelper.queryForList("com.sdp.frame.web.mapper.role.RoleMapper.selectAll");
	}

}
