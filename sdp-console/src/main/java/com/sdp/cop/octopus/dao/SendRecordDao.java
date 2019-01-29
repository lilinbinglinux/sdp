/*
 * 文件名：SendRecordDao.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年5月22日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.dao;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.cop.octopus.entity.SendRecordInfo;
import com.sdp.frame.base.dao.DaoHelper;

/**
 * SendRecordDao〉
 * 
 * @author zhangyunzhen
 * @version 2017年5月22日
 * @see SendRecordDao
 * @since
 */
@Service
public class SendRecordDao {
	/**
	 * 输出日志
	 */
	private static final Logger	LOG				= LoggerFactory.getLogger(SendRecordDao.class);

	@Autowired
	private DaoHelper			daoHelper;

	private static String		BaseMapperUrl	= "com.bonc.cop.octopus.dao.SendRecordDao.";

	public int save(SendRecordInfo info) {
		if (info.getRecordId() == null) {
			return daoHelper.insert(BaseMapperUrl + "insertSelective", info);
		}
		return daoHelper.update(BaseMapperUrl + "updateByPrimaryKeySelective", info);
	}

	public Map<String, Object> findRecordForPage(Map<String, Object> paraMap, int start,int size)
	{
		return daoHelper.queryForPageList(BaseMapperUrl + "selectByField", paraMap, Integer.toString(start), Integer.toString(size));
	}
}
