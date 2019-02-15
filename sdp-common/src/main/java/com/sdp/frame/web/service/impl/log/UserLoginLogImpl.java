package com.sdp.frame.web.service.impl.log;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.frame.web.service.log.UserLoginLogService;

@Service
public class UserLoginLogImpl implements UserLoginLogService{

	@Resource
	private DaoHelper daoHelper;
	
	@Override
	public Map selectAll(String start, String length, Map<String, Object> paramMap) {
		return daoHelper.queryForPageList("com.sdp.frame.web.mapper.log.UserLoginLogMapper.selectAll", paramMap, start, length);
	}

    @Override
    public void deleteLoginMessage(Integer date) {
        daoHelper.delete("com.sdp.frame.web.mapper.log.UserLoginLogMapper.deleteLoginMessage", date);
    }

}
