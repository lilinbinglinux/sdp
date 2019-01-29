package com.sdp.cop.octopus.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.common.BoncException;
import com.sdp.common.entity.Status;
import com.sdp.common.page.Pagination;
import com.sdp.cop.octopus.entity.Message;
import com.sdp.frame.base.dao.DaoHelper;


/**
 * 站内信
 *
 */
@Service
public class MessageDAO {

	private static final Logger LOG = LoggerFactory
			.getLogger(MessageDAO.class);

	@Autowired
	private DaoHelper daoHelper;

	private static String BaseMapperUrl = "com.bonc.cop.octopus.dao.MessageDAO.";

	public int deleteByPrimaryKey(Long id) {
		return this.daoHelper.delete(BaseMapperUrl + "deleteByPrimaryKey", id);
	}

	public int insert(Message record) {
		return this.daoHelper.insert(BaseMapperUrl + "insert", record);
	}

	public int insertSelective(Message record) {
		return this.daoHelper.insert(BaseMapperUrl + "insertSelective", record);
	}

	public Message selectByPrimaryKey(Long id) {
		return (Message) this.daoHelper
				.queryOne(BaseMapperUrl + "selectByPrimaryKey", id);
	}

	public int updateByPrimaryKey(Message record) {
		return this.daoHelper.update(BaseMapperUrl + "updateByPrimaryKey",
				record);
	}

	public int updateFlagById(Long id, String flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("messFlag", flag);
		return this.daoHelper.update(BaseMapperUrl + "updateByPrimaryKeySelective", map);
	}

	public Status bathUpdateFlag(List<Message> message) throws BoncException {
		for (int i = 0, len = message.size(); i < len; i++) {
			Message mess = message.get(i);
			mess.setMessReadTime(new Date());
			if (this.updateFlagById(mess.getId(), mess.getMessFlag()) < 1) {
				throw new BoncException("数据库异常，更新失败");
			}
		}
		return new Status("修改成功", 200);
	}

	public Pagination findRecordPage(Map<String, Object> paraMap,
			Pagination page) {
		int startNum = (int) ((page.getPageNo() * page.getPageSize())
				- page.getPageSize());

		Map<String, Object> map = daoHelper.queryForPageList(
				BaseMapperUrl + "selectByField", paraMap,
				String.valueOf(startNum), String.valueOf(page.getPageSize()));
		page.setTotalCount((Long) (map.get("total")));
		page.setList((List<Message>) map.get("data"));
		return page;
	}

	public Integer findCnt(Map<String, Object> paraMap) {
		return (Integer) this.daoHelper.queryOne(BaseMapperUrl + "selectCnt",
				paraMap);
	}
}