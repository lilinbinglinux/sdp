/*
 * 文件名：AppEmailAddrRecordInfo.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月7日
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

import com.sdp.cop.octopus.entity.AppBindEmailInfo;
import com.sdp.frame.base.dao.DaoHelper;


@Service
public class AppBindEmailDao {
	/**
	 * 输出日志
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(AppBindEmailDao.class);

	@Autowired
	private DaoHelper daoHelper;

	private static String BaseMapperUrl = "com.bonc.cop.octopus.dao.AppBindEmailDao.";

	/**
	 * Description: <br>
	 * 根据app名字查询记录
	 * 
	 * @param app
	 * @return
	 * @see
	 */
	public List<AppBindEmailInfo> findByApp(String app) {
		return daoHelper.queryForList(BaseMapperUrl + "selectByApp", app);
	}

	/**
	 * Description: <br>
	 * 根据app修改绑定邮箱信息
	 * 
	 * @param app
	 * @param email
	 * @return
	 * @see
	 */
	public int updateEmailByApp(String app, String email) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("app", app);
		map.put("emailAddr", email);
		return daoHelper.update(BaseMapperUrl + "updateByApp", map);
	}

	public int save(AppBindEmailInfo info) {
		if (info.getId() == null) {
			return daoHelper.insert(BaseMapperUrl + "insertSelective", info);
		}
		return daoHelper.update(BaseMapperUrl + "updateByPrimaryKeySelective",
				info);
	}

	public int delete(AppBindEmailInfo info) {
		return daoHelper.delete(BaseMapperUrl + "deleteByPrimaryKey",
				info.getId());
	}
}
