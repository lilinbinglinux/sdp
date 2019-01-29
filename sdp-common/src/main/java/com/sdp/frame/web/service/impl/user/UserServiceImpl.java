package com.sdp.frame.web.service.impl.user;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.frame.security.authication.provider.NormalAuthicationProvider;
import com.sdp.frame.util.IdUtil;
import com.sdp.frame.util.MD5Util;
import com.sdp.frame.web.entity.log.UserLoginLog;
import com.sdp.frame.web.entity.resources.Resources;
import com.sdp.frame.web.entity.user.User;
import com.sdp.frame.web.service.user.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	private static final String orgPasswd = "123456";

	@Resource
	private DaoHelper daoHelper;
	
	@Override
	public Map selectAll(String start,String length) {
		return (Map) daoHelper.queryForPageList("com.bonc.base.user.UserMapper.selectAll", null,start,length);
	}

	@Override
	public User selectByUserId(String userId) {
		return (User)daoHelper.queryOne("com.bonc.base.user.UserMapper.selectByPrimaryKey", userId);
	}

	@Override
	public User selectByLoginId(String loginId) {
		return (User)daoHelper.queryOne("com.bonc.base.user.UserMapper.selectByLoginId", loginId);
	}

	@Override
	@Transactional
	public int update(User user) {
		Date currentDate = new Date();
		user.setUpdateDate(currentDate);
		if(NormalAuthicationProvider.LOCK.equals(user.getState())){
			user.setLockDate(currentDate);
		}else{
			user.setLockLoginTimes(-1);
		}
		String orgIds = user.getOrgId();
		if(!StringUtils.isBlank(orgIds)){
			daoHelper.delete("com.bonc.base.user.UserMapper.deleteUserOrgRef", user.getUserId());
			String args[]  = orgIds.split(",");
			for(String arg:args){
				Map<String,Object> paramMap = new HashMap<String,Object>();
				paramMap.put("userId", user.getUserId());
				paramMap.put("orgId", arg);
				daoHelper.insert("com.bonc.base.user.UserMapper.insertUserOrgRef", paramMap);
			}
			user.setOrgId(null);
		}
		return daoHelper.update("com.bonc.base.user.UserMapper.updateByPrimaryKeySelective", user);
	}

	@Override
	@Transactional
	public int insert(User user) throws Exception {
		int count = (int) daoHelper.queryOne("selectCountByLoginId", user.getLoginId());
		if(count>0){
			throw new Exception("账号已存在。");
		}
		Date currentDate = new Date();
		user.setUserId(IdUtil.createId());
		user.setRegDate(currentDate);
		user.setPassword(MD5Util.Bit32(orgPasswd));
		user.setState(NormalAuthicationProvider.UNLOCK);
		System.out.println(CurrentUserUtils.getInstance().getUser());
		user.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
		user.setPwdState("1");
		
		String orgIds = user.getOrgId();
		if(!StringUtils.isBlank(orgIds)){
			String args[]  = orgIds.split(",");
			for(String arg:args){
				Map<String,Object> paramMap = new HashMap<String,Object>();
				paramMap.put("userId", user.getUserId());
				paramMap.put("orgId", arg);
				daoHelper.insert("com.bonc.base.user.UserMapper.insertUserOrgRef", paramMap);
			}
			user.setOrgId(null);
		}
		return daoHelper.insert("com.bonc.base.user.UserMapper.insertSelective", user);
				
	}

	@Override
	@Transactional
	public int deleteByUserId(String userId) throws Exception {
		if(userId.equals("admin")){
			throw new Exception("超级管理员用户无法删除！");
		}
		daoHelper.delete("com.bonc.base.user.UserMapper.deleteUserOrgRef", userId);
		daoHelper.delete("com.bonc.base.user.UserMapper.deleteUserRoleRef", userId);
		daoHelper.delete("com.bonc.base.user.UserMapper.deleteUserAuthRef", userId);
		daoHelper.delete("com.bonc.base.user.UserMapper.deleteByPrimaryKey", userId);
		return 0;
	}

	@Override
	public List<Resources> selectUserResource(String userId) {
		return daoHelper.queryForList("com.bonc.frame.web.mapper.resources.ResourcesMapper.selectUserRoleResource", userId);
	}

	@Override
	public void doUserLoginLog(UserLoginLog log) {
		log.setLoginDate(new Date());
		daoHelper.insert("com.bonc.base.user.UserMapper.insertLoginLog", log);
	}

	@Override
	public Map<String, Object> getUserByCondition(Map<String, Object> paramMap,String start,String length) {
		String orgIds = paramMap.get("orgIds").toString();
		if(!StringUtils.isBlank(orgIds)){
			String args[]  = orgIds.split(",");
			StringBuffer sb = new StringBuffer();
			for(String arg:args){
				sb.append("'").append(arg).append("'").append(",");
			}
			paramMap.put("orgIds", sb.deleteCharAt(sb.length()-1).toString());
		}
		return daoHelper.queryForPageList("com.bonc.base.user.UserMapper.selectUserByCondition", paramMap,start,length);
	}

	@Override
	public void initPasswd(String userId) throws NoSuchAlgorithmException {
		User user  =new User();
		user.setUserId(userId);
		user.setPassword(MD5Util.Bit32(orgPasswd));
		daoHelper.update("com.bonc.base.user.UserMapper.updateByPrimaryKeySelective", user);
	}

	@Transactional
	@Override
	public void userAuth(List<Map> list, String userId) {
		daoHelper.delete("com.bonc.base.user.UserMapper.deleteUserRoleRef", userId);
		for(Map map : list){
			daoHelper.insert("com.bonc.base.user.UserMapper.insertUserRoleRef", map);
		}
		
	}

	@Override
	public List<Map> selectRoleByUser(String userId) {
		return daoHelper.queryForList("com.bonc.base.user.UserMapper.selectRoleByUser",userId);
	}
}
