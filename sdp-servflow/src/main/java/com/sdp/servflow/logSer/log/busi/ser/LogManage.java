package com.sdp.servflow.logSer.log.busi.ser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Component;

import com.sdp.servflow.logSer.log.exception.DataMissingException;
import com.sdp.servflow.logSer.log.model.LogBean;

/** 
* @Title: LogManage.java 
* @Description: 日志管理
* fkr 
* 2017年11月9日
*/ 
@Component
public class LogManage {
	
	private static List<LogBean> listA =new ArrayList<LogBean>(1000);
	private static List<LogBean> listB =new ArrayList<LogBean>(1000);
	private static String changeF ="A";
//	private static int paramMaxLength = 3000;
	
	public static List<LogBean> list = listA;
	public static List<LogBean> listCopy;
	 
	private static final Lock queueLock = new ReentrantLock();  
	
	
	/** 
	* @Title: addLog 
	* @Description: 租户id 订阅id 数据来源 成功失败结果编码  均不能为空  
	* tinybad
	*/ 
	public void addLog(LogBean lb) throws DataMissingException{
		if(strIsNull(lb.getTenant_id()) || strIsNull(lb.getOrderid()) 
				|| strIsNull(lb.getSourceType()) || strIsNull(lb.getCode())){
			throw new DataMissingException("data missing : "+lb.toString());
		}
		if(lb.getTenant_id() == null || "".equals(lb.getTenant_id().trim())
				|| lb.getOrderid() == null || "".equals(lb.getOrderid())
				|| lb.getSourceType() == null || "".equals(lb.getSourceType())
				|| lb.getCode() == null || "".equals(lb.getCode())){
			throw new DataMissingException("data missing : "+lb.toString());
		}
		if(lb.getStartTime()!=null && lb.getEndTime()!=null){
			lb.setUseTime(lb.getEndTime().getTime() -lb.getStartTime().getTime());
			lb.setStartTimeStr(lb.getStartTime().toString());
			lb.setEndTimeStr(lb.getEndTime().toString());
		}
		queueLock.lock();  
		try {
			if(list != null){
				list.add(lb);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{  
            queueLock.unlock();  
        }  
	}
	
	public boolean strIsNull(String s){
		return s==null || "".equals(s.trim());
	}
	
	public void changeContainer(){
		queueLock.lock();  
		try {
			if("A".equals(changeF)){
				changeF = "B";
				list = listB;
			}else if("B".equals(changeF)){
				changeF = "A";
				list = listA;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{  
            queueLock.unlock();  
        }  
	}
	
}
