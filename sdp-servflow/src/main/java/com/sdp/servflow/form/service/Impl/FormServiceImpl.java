package com.sdp.servflow.form.service.Impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.servflow.form.entity.Form;
import com.sdp.servflow.form.service.FormService;
@Service("formService")
public class FormServiceImpl implements FormService{

	
	@Resource
	private DaoHelper daoHelper;
	
	@Override
	public Map selectAll(String start, String length, Map<String, Object> paramMap) {
		return daoHelper.queryForPageList("com.bonc.frame.web.mapper.form.FormMapper.selectAll", paramMap, start, length);
	}

	@Override
	public int insert(Form form) {
		return daoHelper.insert("com.bonc.frame.web.mapper.form.FormMapper.insertSelective", form);
	}

	@Override
	public Form selectById(String id) {
		// TODO Auto-generated method stub
		return (Form) daoHelper.queryOne("com.bonc.frame.web.mapper.form.FormMapper.selectByPrimaryKey", id);
	}

	@Override
	public int deleteByFormId(String id) {
		return daoHelper.delete("com.bonc.frame.web.mapper.form.FormMapper.deleteByPrimaryKey", id);
	}

	@Override
	public int update(Form form) {
		return daoHelper.update("com.bonc.frame.web.mapper.form.FormMapper.updateByPrimaryKey", form);

	}

}
