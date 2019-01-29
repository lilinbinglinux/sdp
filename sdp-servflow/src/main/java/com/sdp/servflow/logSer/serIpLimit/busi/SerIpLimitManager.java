package com.sdp.servflow.logSer.serIpLimit.busi;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdp.frame.util.StringUtil;
import com.sdp.servflow.logSer.serIpLimit.model.SerIpLimit;
import com.sdp.servflow.logSer.serIpLimit.service.SerIpLimitService;
import com.sdp.servflow.logSer.util.ToolUtils;

@Component
public class SerIpLimitManager {
	@Autowired
	private SerIpLimitService serIpLimitService;
	
//	@Autowired
//	private AppInitConfigBean appInitConfigBean;

//	private static ReadWriteLock RWL = new ReentrantReadWriteLock();
	// 黑白名单容器
	public static ConcurrentHashMap<String, ConcurrentHashMap<String,Object>> serIpLimitContainer;
	
	public static Date LAST_MODIFIED_TIME;
	
	private Logger logger = LoggerFactory.getLogger(SerIpLimitTasks.class);
	

	/** 
	* @Title: judgeIsAWhiteList 
	* @Description: tenant_id : 租户id
	* 				orderid : 订阅id
	* 				ip_addr : ip地址
	*/ 
	public boolean judgeIsAWhiteList(String tenant_id,String orderid,String ip_addr){
		try {
			return judgeIsAWhiteListBusi(tenant_id,orderid,ip_addr);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public boolean judgeIsAWhiteListBusi(String tenant_id,String orderid,String ip_addr) throws Exception{
		if(StringUtil.isNotEmpty(tenant_id)
				&&StringUtil.isNotEmpty(orderid)
				&&StringUtil.isNotEmpty(ip_addr)){
			if(serIpLimitContainer == null){
    			throw new NullPointerException("serIpLimitContainer is null, error!!!");
    		}
			String key=ToolUtils.getSpecialStitching(tenant_id,orderid);
			ConcurrentHashMap<String,Object> middleMap=serIpLimitContainer.get(key);
			if(middleMap!=null){
				for(Entry<String, Object> entry : middleMap.entrySet()) {
					Object o=entry.getValue();
					if(entry.getKey().equals("0")){
					// 白名单
						if(o instanceof String){
							String s=(String)o;
							if("all".equalsIgnoreCase(s)){
								return true;
							}else if("null".equalsIgnoreCase(s)){
								return false;
							}else{
								// 出现非法字符 都不给通过
								return false;
							}
						}else if(o instanceof ConcurrentHashMap){
							return ((ConcurrentHashMap)o).containsKey(ip_addr);
						}
					}else if(entry.getKey().equals("1")){
					// 黑名单
						if(o instanceof String){
							String s=(String)o;
							if("all".equalsIgnoreCase(s)){
								return false;
							}else if("null".equalsIgnoreCase(s)){
								return true;
							}else{
								// 出现非法字符 都不给通过
								return false;
							}
						}else if(o instanceof ConcurrentHashMap){
							return !((ConcurrentHashMap)o).containsKey(ip_addr);
						}
					}
				}
			}else{
				// 如果ser_ip_limit表里 没数据  默认给通过
				return true;
			}
		}
		return false;
	}
	
	// 是否更替缓存
	void replaceCache(){
		try {
//			if(appInitConfigBean.getCache_policy() == 1){
//				boolean refreshCache = false;
//				// 这个查找的值是去查询表最近一次更改过数据的时间，可能为空（为空代表最近 表结构 刚被修改过，且被修改后无数据的DML操作）
//				Date newDate = serIpLimitService.selectLastModifiedTimeSerIpLimit(appInitConfigBean.getSchemaName());
//				if(LAST_MODIFIED_TIME == null  && newDate == null){
//					// 对象为空，证明表结构被修改过，但是表里面的数据没有变化，所以不需要更换缓存
//				}else if(LAST_MODIFIED_TIME == null  && newDate != null){
//					// 这种情况证明表中数据后来被修改过，需要更换缓存，然后重新赋值 InstanceParam.LAST_MODIFIED_TIME
//					refreshCache = true;
//				}else if(LAST_MODIFIED_TIME != null  && newDate == null){
//					// 这种情况证明表后来表结构更换过，需要更换缓存，然后重新赋值 InstanceParam.LAST_MODIFIED_TIME，即置为空
//					refreshCache = true;
//				}else if(LAST_MODIFIED_TIME != null  && newDate != null){
//					// 比较值是否相等，若不相等，更换缓存，然后重新赋值 InstanceParam.LAST_MODIFIED_TIME
//					if(newDate.getTime() != LAST_MODIFIED_TIME.getTime()){
//						refreshCache = true;
//					}
//				}
//				if(refreshCache){
//					loadCache();
//					LAST_MODIFIED_TIME = newDate;
//				}
//			}else if(appInitConfigBean.getCache_policy() == 2){
//				loadCache();
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 
	* @Title: addSerIpLimit 
	* @Description: 单机版应用添加黑白名单  
	* 传参 : 
	* tenant_id : 租户id
	* order_id : 订阅id
	* name_type : 名单类型（0 白名单 1 黑名单）
	* ip_addrs : all/null/ip地址等
	* Separator : 分隔符
	*/ 
	public void addSerIpLimitRule(String tenant_id,String order_id,String name_type,String ip_addrs,String Separator){
		addAndUpdateSerIpLimitRule(tenant_id,order_id,name_type,ip_addrs,Separator);
	}
	
	/** 
	* @Title: addSerIpLimit 
	* @Description: 单机版应用修改黑白名单  
	* 传参 : 
	* tenant_id : 租户id
	* order_id : 订阅id
	* name_type : 名单类型（0 白名单 1 黑名单）
	* ip_addrs : all/null/ip地址等
	* Separator : 分隔符
	*/ 
	public void updateSerIpLimitRule(String tenant_id,String order_id,String name_type,String ip_addrs,String Separator){
		addAndUpdateSerIpLimitRule(tenant_id,order_id,name_type,ip_addrs,Separator);
	}
	
	/** 
	* @Title: addSerIpLimit 
	* @Description: 单机版应用添加与修改黑白名单(重新添加即为覆盖)，内部调用了清除该条数据缓存操作  removeSerIpLimitRule(tenant_id,order_id)
	*/ 
	public void addAndUpdateSerIpLimitRule(String tenant_id,String order_id,String name_type,String ip_addrs,String Separator){
//		try {
//			if(appInitConfigBean.getCache_policy() == 3){
//    			if(StringUtil.isNotEmpty(tenant_id)
//    					&& StringUtil.isNotEmpty(order_id)
//    					&& StringUtil.isNotEmpty(name_type)
//    					&& StringUtil.isNotEmpty(ip_addrs)){
//    				// 获取中间层map
//    				removeSerIpLimitRule(tenant_id,order_id);
//    				String key=ToolUtils.getSpecialStitching(tenant_id,order_id);
//    				ConcurrentHashMap<String,Object> middleMap=serIpLimitContainer.get(key);
//    				if(middleMap == null){
//    					//如果中间层map为空，则只能添加
//    					// 创建中间层map
//    					middleMap=new ConcurrentHashMap<String,Object>();
//    					if(ip_addrs.equalsIgnoreCase("all") || ip_addrs.equalsIgnoreCase("null")){
//    						// 如果为 all 或者 null 则中间层map的value值为String
//    						middleMap.put(name_type, ip_addrs);
//    					}else{
//    						// 创建内层map，<key,value>为 ip地址，true
//    						Map<String, Boolean> insideMap=new ConcurrentHashMap<String, Boolean>();
//    						if(StringUtil.isNotEmpty(Separator)){
//    							String[] arr= ip_addrs.split(Separator);
//    							if(arr != null && arr.length>0){
//    								for(String ip_addr:arr){
//    									insideMap.put(ip_addr, true);
//    								}
//    							}
//    						}else{
//    							insideMap.put(ip_addrs, true);
//    						}
//    						middleMap.put(name_type, insideMap);
//    					}
//    					serIpLimitContainer.put(key, middleMap);
//    				}
//    			}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	/** 
	* @Title: addSerIpLimit 
	* @Description: 单机版应用移除黑白名单  
	*/ 
	public void removeSerIpLimitRule(String tenant_id,String order_id){
//		try {
//			if(appInitConfigBean.getCache_policy() == 3){
//    			if(StringUtil.isNotEmpty(tenant_id)
//    					&& StringUtil.isNotEmpty(order_id)){
//    				String key=ToolUtils.getSpecialStitching(tenant_id,order_id);
//    				serIpLimitContainer.remove(key);
//    			}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	// 装载缓存
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void loadCache() {
		ConcurrentHashMap<String, ConcurrentHashMap<String,Object>> map=new ConcurrentHashMap<String, ConcurrentHashMap<String,Object>>();
		List<SerIpLimit> list = serIpLimitService.selectAllSerIpLimit();
		if(list!=null && list.size()>0){
			for(SerIpLimit sil:list){
				if(sil!=null){
					if(StringUtil.isNotEmpty(sil.getTenant_id()) 
							&& StringUtil.isNotEmpty(sil.getOrder_id()) 
							&& StringUtil.isNotEmpty(sil.getIp_addr())
							&& StringUtil.isNotEmpty(sil.getName_type())){
						// 获取中间层map
						String key=ToolUtils.getSpecialStitching(sil.getTenant_id(),sil.getOrder_id());
						ConcurrentHashMap<String,Object> middleMap=map.get(key);
						if(middleMap == null){
							// 创建中间层map
							middleMap=new ConcurrentHashMap<String,Object>();
							if(sil.getIp_addr().equalsIgnoreCase("all") || sil.getIp_addr().equalsIgnoreCase("null")){
								// 如果为 all 或者 null 则中间层map的value值为String
								middleMap.put(sil.getName_type(), sil.getIp_addr());
							}else{
								// 创建内层map，<key,value>为 ip地址，true
								Map<String, Boolean> insideMap=new ConcurrentHashMap<String, Boolean>();
								insideMap.put(sil.getIp_addr(), true);
								middleMap.put(sil.getName_type(), insideMap);
							}
							map.put(key, middleMap);
						}else{
							// 遍历中间层map，其实最多也只有一层遍历
							for(Entry<String, Object> entry : middleMap.entrySet()) {
								Object o=entry.getValue();
								if(o instanceof ConcurrentHashMap){
									ConcurrentHashMap insideMap = (ConcurrentHashMap) o;
									insideMap.put(sil.getIp_addr(), true);
								}
							}
						}
					}
				}
			}
		}
		serIpLimitContainer = map;
		logger.info("--- > ser_ip_limit replacement, refresh cache");
	}
}
