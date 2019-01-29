package com.sdp.servflow.logSer.flowControl.busi.cacheStrategy;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdp.servflow.logSer.flowControl.busi.CacheContext;
import com.sdp.servflow.logSer.flowControl.service.FlowControlService;

@Component
public class ISTableStrategy extends CacheFlushStrategy{
	
	@Autowired
	private FlowControlService flowControlService;
	
//	@Autowired
//	private AppInitConfigBean appInitConfigBean;
	
	@Autowired
	private CacheContext cacheContext;
	
	public static Date LAST_MODIFIED_TIME; 
	
	@Override
	public void flushCache() {
//		boolean refreshCache = false;
//		// 这个查找的值是去查询表最近一次更改过数据的时间，可能为空（为空代表最近 表结构 刚被修改过，且被修改后无数据的DML操作）
//		Date newDate = flowControlService.selectLastModifiedTimeSerIpLimit(appInitConfigBean.getSchemaName());
//		if(LAST_MODIFIED_TIME == null  && newDate == null){
//			// 对象为空，证明表结构被修改过，但是表里面的数据没有变化，所以不需要更换缓存
//		}else if(LAST_MODIFIED_TIME == null  && newDate != null){
//			// 这种情况证明表中数据后来被修改过，需要更换缓存，然后重新赋值 InstanceParam.LAST_MODIFIED_TIME
//			refreshCache = true;
//		}else if(LAST_MODIFIED_TIME != null  && newDate == null){
//			// 这种情况证明表后来表结构更换过，需要更换缓存，然后重新赋值 InstanceParam.LAST_MODIFIED_TIME，即置为空
//			refreshCache = true;
//		}else if(LAST_MODIFIED_TIME != null  && newDate != null){
//			// 比较值是否相等，若不相等，更换缓存，然后重新赋值 InstanceParam.LAST_MODIFIED_TIME
//			if(newDate.getTime() != LAST_MODIFIED_TIME.getTime()){
//				refreshCache = true;
//			}
//		}
//		if(refreshCache){
//			cacheContext.loadCache();
//			LAST_MODIFIED_TIME = newDate;
//		}
	}

}
