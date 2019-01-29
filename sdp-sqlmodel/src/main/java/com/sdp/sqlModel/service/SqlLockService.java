package com.sdp.sqlModel.service;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdp.common.CurrentUserUtils;
import com.sdp.common.constant.Dictionary;
import com.sdp.common.entity.Status;
import com.sdp.frame.web.entity.user.User;
import com.sdp.sqlModel.entity.SqlLock;
import com.sdp.sqlModel.mapper.SqlFieldMapper;
import com.sdp.sqlModel.mapper.SqlLockMapper;
import com.sdp.sqlModel.mapper.SqlTableMapper;
import com.sdp.util.DateUtils;

@Service
public class SqlLockService {
	@Autowired SqlTableMapper sqlTableDao;
	@Autowired SqlFieldMapper sqlFieldDao;
	@Autowired SqlLockMapper sqlLockMapper;
	
	private static final Logger log = LoggerFactory.getLogger(SqlLockService.class);

	@Transactional(rollbackFor=Exception.class)
	public boolean checkStatus(String dataResId,String uuid){
		boolean status = false;
		User userInfo = CurrentUserUtils.getInstance().getUser();
		if(userInfo == null){
			return status;
		}
		SqlLock sqlLock = sqlLockMapper.findByDataResId(dataResId);
		if(sqlLock != null){
			if(!sqlLock.getDataStatus().equals("1")){
				status = true;
			}else{
				int disparity = DateUtils.getOffMinute(sqlLock.getCreateDate(), new Date());
				if(disparity < 3){
//						if(sqlLock.getUuid() != null && sqlLock.getUuid().endsWith(uuid)){
//							//不能打开新页面
//						}else{
//							//可以打开新页面，但是不能操作
//						}
					if(sqlLock.getLoginId() != null && sqlLock.getLoginId().equals(userInfo.getLoginId()) 
							&& sqlLock.getUuid() != null && sqlLock.getUuid().endsWith(uuid)){
						status = true;
					}else{
						status = false;
					}
				}else{
					status = true;
				}
			}
			sqlLock.setLoginId(userInfo.getLoginId());
			sqlLockMapper.update(sqlLock);
		}else{
			sqlLock = new SqlLock();
			sqlLock.setDataResId(dataResId);
			sqlLock.setDataStatus("1");
			sqlLock.setUuid(uuid);
			status = true;
			sqlLock.setLoginId(userInfo.getLoginId());
			sqlLockMapper.save(sqlLock);
		}
		return status;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public boolean clickStatus(String dataResId,String uuid){
		boolean status = true;
		User userInfo = CurrentUserUtils.getInstance().getUser();
		if(userInfo == null){
			return status = false;
		}
		SqlLock sqlLock = sqlLockMapper.findByUuid(dataResId,uuid);
		if(sqlLock != null){
			status = false;
		}
		return status;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public Status changeStatus(SqlLock sqlLock){
		Status status;
		try{
			User userInfo = CurrentUserUtils.getInstance().getUser();
			if(userInfo == null){
				return new Status("失败",Dictionary.HttpStatus.NOT_FOUND.value );
			}
			sqlLock.setLoginId(userInfo.getLoginId());
			sqlLockMapper.update(sqlLock);
			status = new Status("成功",Dictionary.HttpStatus.OK.value );
		}catch(Exception e){
			e.printStackTrace();
			status = new Status("失败",Dictionary.HttpStatus.INVALID_REQUEST.value );
		}
		return status;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public Status changeStatusUuid(String uuid){
		Status status;
		try{
			sqlLockMapper.updateUuid(uuid);
			status = new Status("成功",Dictionary.HttpStatus.OK.value );
		}catch(Exception e){
			e.printStackTrace();
			status = new Status("失败",Dictionary.HttpStatus.INVALID_REQUEST.value );
		}
		return status;
	}
	
	
	
}
