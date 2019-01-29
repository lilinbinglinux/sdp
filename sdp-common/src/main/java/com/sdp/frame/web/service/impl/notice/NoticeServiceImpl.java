package com.sdp.frame.web.service.impl.notice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.frame.util.IdUtil;
import com.sdp.frame.util.ResultUtil;
import com.sdp.frame.web.entity.notice.Notice;
import com.sdp.frame.web.entity.user.User;
import com.sdp.frame.web.service.notice.NoticeService;
import com.sdp.frame.web.vo.ResponseResult;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService{

	@Resource
	private DaoHelper daoHelper;
	
	@Override
	public Map<String, Object> getNoticeByCondition(
			Map<String, Object> paramMap, String start, String length) {
		return (Map) daoHelper.queryForPageList("com.bonc.frame.web.mapper.notice.NoticeMapper.pageQuery", paramMap,start,length);
	}

	@Override
	public Notice selectByNoticeId(String noticeId) {
		return (Notice)daoHelper.queryOne("com.bonc.frame.web.mapper.notice.NoticeMapper.selectByPrimaryKey", noticeId);
	}

	@Override
	@Transactional
	public int insert(Notice notice) throws Exception {
		notice.setNoticeId(IdUtil.createId());
		notice.setPubdate(new Date());
		return daoHelper.insert("com.bonc.frame.web.mapper.notice.NoticeMapper.insertSelective", notice);
	}

	@Override
	@Transactional
	public int update(Notice notice) {
		return daoHelper.update("com.bonc.frame.web.mapper.notice.NoticeMapper.updateByPrimaryKeySelective", notice);
	}

	@Override
	@Transactional
	public int deleteSendNoticeByNoticeId(List<String> noticeIdList) throws Exception {
		for(String noticeId : noticeIdList){			
			daoHelper.delete("com.bonc.frame.web.mapper.notice.NoticeMapper.deleteByPrimaryKey", noticeId);
			Map<String,String> paramMap = new HashMap<String,String>();
			paramMap.put("noticeId", noticeId);
			daoHelper.delete("com.bonc.frame.web.mapper.notice.NoticeMapper.deleteRecNotice", paramMap);
		}
		return noticeIdList.size();
	}

	@Override
	@Transactional
	public int publishNotice(Notice notice, List<String> orgIdList)
			throws Exception {
		List<User> userIdList = null;
		if("".equals(notice.getNoticeId())||notice.getNoticeId() == null){			
			this.insert(notice);
		}
		Map paramMap = new HashMap();
		Map<String,String> userMap = new HashMap<String,String>();
		for(String orgId : orgIdList){
			userIdList = daoHelper.queryForList("com.bonc.base.user.UserMapper.selectUserByOrgId", orgId);
			for(User user :userIdList){
				userMap.put(user.getUserId(), "");
			}
		}
		for (String userId : userMap.keySet()) {
			paramMap.put("noticeId", notice.getNoticeId());
			paramMap.put("userId", userId);
			paramMap.put("isRead", -1);
			daoHelper.insert("com.bonc.frame.web.mapper.notice.NoticeMapper.insertPublishNotice", paramMap);
		}
		notice.setState("1");//更改公告状态为发布成功
		this.update(notice);
		return userIdList.size();
	}

	@Override
	@Transactional
	public int deleteRecNotice(List<String> noticeIdList, String userId)
			throws Exception {
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		for(String noticeId : noticeIdList){			
			paramMap.put("noticeId", noticeId);
			daoHelper.delete("com.bonc.frame.web.mapper.notice.NoticeMapper.deleteRecNotice", paramMap);
		}
		return noticeIdList.size();
	}

	@Override
	public Map selectUnreadNotice(String userId) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		Map resultMap = (Map) daoHelper.queryForPageList("com.bonc.frame.web.mapper.notice.NoticeMapper.selectUnreadNotice", paramMap,"0","4");
		List<Notice> resultList = (List)resultMap.get("data");
		for(Notice notice : resultList){
			notice.setPubTime(sdf.format(notice.getPubdate()));;
		}
		return resultMap;
	}

	@Override
	//全部标记为已读
	public ResponseResult markRead(String loginUserId) {
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("userId", loginUserId);
		daoHelper.update("com.bonc.frame.web.mapper.notice.NoticeMapper.markRead", paramMap);
		return ResultUtil.createSuccessInfo();
	}

	@Override
	//已发送公告
	public Map<String, Object> noticeSend(Map<String, Object> paramMap, String start, String length) {
		return (Map<String, Object>) daoHelper.queryForPageList("com.bonc.frame.web.mapper.notice.NoticeMapper.pageNoticeSend", paramMap,start,length);
	}

	@Override
	//已收到公告
	public Map<String, Object> noticeRec(Map<String, Object> paramMap, String start, String length) {
		return (Map<String, Object>) daoHelper.queryForPageList("com.bonc.frame.web.mapper.notice.NoticeMapper.pageNoticeRec", paramMap,start,length);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Notice selectNoticeDetail(String noticeId,String loginUserId) {
		Notice result = (Notice)daoHelper.queryOne("com.bonc.frame.web.mapper.notice.NoticeMapper.selectByNoticeId", noticeId);
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("userId", loginUserId);
		paramMap.put("noticeId", noticeId);
		daoHelper.update("com.bonc.frame.web.mapper.notice.NoticeMapper.markRead", paramMap);
		return result;
	}

}
