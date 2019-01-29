/*
 * 文件名：BounceEmailRecordDao.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月6日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.cop.octopus.entity.BounceEmailLog;
import com.sdp.frame.base.dao.DaoHelper;


/**
 * 退信邮件记录dao
 * @author zhangyunzhen
 * @version 2017年7月6日
 * @see BounceEmailRecordDao
 * @since
 */
@Service
public class BounceEmailRecordDao {

	/**
	 * 输出日志
	 */
	private static final Logger	LOG				= LoggerFactory.getLogger(BounceEmailRecordDao.class);

	@Autowired
	private DaoHelper			daoHelper;

	private static String		BaseMapperUrl	= "com.bonc.cop.octopus.dao.BounceEmailRecordDao.";
	
    public List<BounceEmailLog> findByAppAndOriEmaTo(String app, String oriEmaTo) {
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("app", app);
    	map.put("oriEmaTo", oriEmaTo);
    	return daoHelper.queryForList(BaseMapperUrl + "selectByAppAndOriEmaTo", map);
    }

    public List<BounceEmailLog> findByApp(String app) {
    	return daoHelper.queryForList(BaseMapperUrl + "selectByApp", app);
    }
    
    public Map<String, Object> findRecordPage(Map<String, Object> paraMap, int start,int size) {
    	return daoHelper.queryForPageList(BaseMapperUrl + "selectByField", paraMap, Integer.toString(start), Integer.toString(size));
    }
    
	public int save(BounceEmailLog info) {
		if (info.getId() == null) {
			return daoHelper.insert(BaseMapperUrl + "insertSelective", info);
		}
		return daoHelper.update(BaseMapperUrl + "updateByPrimaryKeySelective", info);
	}
	
	public int delete (BounceEmailLog info) {
		return daoHelper.delete(BaseMapperUrl + "deleteByPrimaryKey", info.getId());
	}
}
