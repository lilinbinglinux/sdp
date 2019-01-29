package com.sdp.cop.octopus.dao;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.cop.octopus.entity.MessTemplate;
import com.sdp.frame.base.dao.DaoHelper;

@Service
public class MessTemplateDAO {
	private static final Logger LOG = LoggerFactory
			.getLogger(MessTemplateDAO.class);

	@Autowired
	private DaoHelper daoHelper;

	private static String BaseMapperUrl = "com.bonc.cop.octopus.dao.MessTemplateDAO.";

	public int deleteByPrimaryKey(Long id)
	{
		return this.daoHelper.delete(BaseMapperUrl + "deleteByPrimaryKey", id); 
	}

	public int insert(MessTemplate record)
	{
		return this.daoHelper.insert(BaseMapperUrl + "insert", record);
	}

	public int insertSelective(MessTemplate record)
	{
		return this.daoHelper.insert(BaseMapperUrl + "insertSelective", record);
	}

	public MessTemplate selectByPrimaryKey(Long id)
	{
		return (MessTemplate) this.daoHelper.queryOne(BaseMapperUrl + "selectByPrimaryKey", id);
	}

	public int updateByPrimaryKeySelective(MessTemplate record)
	{
		return this.daoHelper.update(BaseMapperUrl + "updateByPrimaryKeySelective", record);
	}

	public int updateByPrimaryKey(MessTemplate record)
	{
		return this.daoHelper.update(BaseMapperUrl + "updateByPrimaryKey", record);
	}

	public List<MessTemplate> selectAll()
	{
		return this.daoHelper.queryForList(BaseMapperUrl + "selectAll");
	}
	
	public List<MessTemplate> selectByCondition(MessTemplate messTemplate)
	{
		return this.daoHelper.queryForList(BaseMapperUrl + "selectByCondition", messTemplate);
	}
}