package com.sdp.frame.web.service.impl.log;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.frame.util.IdUtil;
import com.sdp.frame.web.entity.log.ResourceLog;
import com.sdp.frame.web.service.log.ResourcesLogService;

/** 
 * @author 作者: jxw 
 * @date 创建时间: 2017年1月23日 下午5:12:43 
 * @version 版本: 1.0 
*/

@Service("resourceLogImpl")
public class ResourceLogImpl implements ResourcesLogService{
	
	@Resource
	private DaoHelper daoHelper;

	@Override
	public void doLog(ResourceLog log) {
		if(log!=null){
			log.setLogId(IdUtil.createId());
			log.setLogDate(new Date());
			daoHelper.insert("com.bonc.frame.web.mapper.log.ResourceLogMapper.insertSelective", log);
		}
		
	}

}

