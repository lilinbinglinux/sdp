package com.sdp.frame.web.service.impl.tenant;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.frame.web.entity.role.Role;
import com.sdp.frame.web.entity.tenant.Tenant;
import com.sdp.frame.web.service.tenant.TenantService;
@Service("tenantService")
public class TenantServiceImpl implements TenantService{
	
	@Resource
	private DaoHelper daoHelper;

	@Override
	public Map selectAll(String start, String length, Map<String, Object> paramMap) {
		return daoHelper.queryForPageList("com.bonc.frame.web.mapper.tenant.TenantMapper.selectAll", paramMap, start, length);
	}

	@Override
	public int deleteByTenantId(String tenantId) {
		
		return daoHelper.delete("com.bonc.frame.web.mapper.tenant.TenantMapper.deleteByPrimaryKey", tenantId);
	}

	@Override
	public int insert(Tenant tenant) {
		return daoHelper.insert("com.bonc.frame.web.mapper.tenant.TenantMapper.insertSelective", tenant);
	}

	@Override
	public Tenant selectByTenantId(String tenantId) {
		return (Tenant)daoHelper.queryOne("com.bonc.frame.web.mapper.tenant.TenantMapper.selectByPrimaryKey", tenantId);
	}

	@Override
	public int update(Tenant tenant) {
		return daoHelper.update("com.bonc.frame.web.mapper.tenant.TenantMapper.updateByPrimaryKeySelective", tenant);
	}

	@Override
	public List<Tenant> getAll() {
		return daoHelper.queryForList("com.bonc.frame.web.mapper.tenant.TenantMapper.getAll");
	}

}
