package com.sdp.servflow.logSer.log.busi.ser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdp.servflow.logSer.InstanceParam;
import com.sdp.servflow.logSer.log.model.LogBean;
import com.sdp.servflow.logSer.log.model.OrderInfoStatistics;
import com.sdp.servflow.logSer.log.model.OrderTotalStatistics;
import com.sdp.servflow.logSer.log.model.PubStatistics;
import com.sdp.servflow.logSer.log.model.Ser_Id_Version_Statistics;
import com.sdp.servflow.logSer.log.service.LogRecordService;
import com.sdp.servflow.logSer.log.service.LogSerTotalStatisticsService;
import com.sdp.servflow.logSer.log.service.OrderInfoStatisticsService;
import com.sdp.servflow.logSer.log.service.OrderTotalStatisticsService;
import com.sdp.servflow.logSer.util.ToolUtils;

@Component
public class LogStorageService {
	
	@Autowired
	private LogManage logManage;
	
	@Autowired
	private LogSerTotalStatisticsService logSerTotalStatisticsService;
	
	@Autowired
	private OrderTotalStatisticsService orderTotalStatisticsService;
	
	@Autowired
	private OrderInfoStatisticsService orderInfoStatisticsService;
	
	@Autowired
	private LogRecordService logRecordService;
	
	private Logger logger = LoggerFactory.getLogger(LogStorageService.class);
	
	// 这是用于统计 服务 的容器
	private static Map<Map<String,String>, PubStatistics> pubCountMap = new HashMap<Map<String,String>, PubStatistics>(1024);
	
	// 用于划分orderid、code、tenant_id 的不同 (同一orderid下  可能有不同的code(处理结果))
	private static Set<String> orderIdCodeTenId_Set=new HashSet<String>();
	// 用于划分orderid、dataResource、tenant_id 的不同 (orderid 与 dataResource唯一对应 0 同步 1异步 2cas)
	private static Set<String> orderIdDtResTenId_Set=new HashSet<String>();
	// k, v	K:orderid+tenant_id   v order_name
	private static Map<String, String> orderIdTen_OrderName = new HashMap<String,String>();
	
	public void dealBusi(){
		dealLogBusi();
	}
	
	private void dealLogBusi() {
		try {
			// 1.存储日志
			storageLog();
			
			// 2.数据库统计
			pubapiIdStatistics();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void pubapiIdStatistics() {
		Set<String> serVersionTenantId_Set_Copy=initSerVerSCodeTenId_Set();
		Set<String> orderIdTenId_Set_Copy=initSerVerSTenId_Set();
		Map<String, String> map_copy=initOrderIdTen_OrderName();
		if(serVersionTenantId_Set_Copy==null || serVersionTenantId_Set_Copy.size()==0){
			return;
		}
		
		int sendDate = Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		for(String s:serVersionTenantId_Set_Copy){
			String[] arr=s.split(InstanceParam.SPECIALCHAR);
			OrderInfoStatistics ls=new OrderInfoStatistics();
			
			String orderid=arr[0];
			String code=arr[1];
			String tenant_id=arr[2];
			
			if(arr.length!=3){
				System.err.println("error !!");
				return;
			}if(arr.length==3){
				ls.setOrderid(orderid);
				ls.setCode(code);
				ls.setTenant_id(tenant_id);
			}
			
			ls.setSendDate(sendDate);
			// 1.获得每个统计的发送数量
			Integer sendCount=orderInfoStatisticsService.selectOrderInfoSendCount(InstanceParam.CURRENT_TABLE_NAME,sendDate,ls);
			ls.setSendCount(sendCount);
			OrderInfoStatistics layoutSerStatistics = orderInfoStatisticsService.findOrderInfo(ls);
			if(layoutSerStatistics!=null){
				//数据库中存在 则更新
				logger.info("OrderInfoStatistics : update ---> orderid : {}  --- code : {} --- sendCount : {}",
						ls.getOrderid(),ls.getCode(),ls.getSendCount());
				orderInfoStatisticsService.orderInfoStatisticsUpdate(ls);
			}else{
				//数据库中不存在 则插入这条数据
				logger.info("OrderInfoStatistics : save ---> orderid : {} --- code : {} --- sendCount : {}",
						ls.getOrderid(),ls.getCode(),ls.getSendCount());
				orderInfoStatisticsService.orderInfoStatisticsSave(ls);
			}
		}
		
		
		
		for(String s:orderIdTenId_Set_Copy){
			String[] arr=s.split(InstanceParam.SPECIALCHAR);
			
			String orderid=arr[0];
			String sourceType=arr[1];
			String tenant_id=arr[2];
			
			Map<String,String> map=new HashMap<String,String>();
			map.put("orderid", orderid);
			map.put("sourceType", sourceType);
			map.put("tenant_id", tenant_id);
			
			Ser_Id_Version_Statistics sivs = null;
			// 获取 服务id 和 版本号
			// 根据类型判断是同步还是异步
			OrderTotalStatistics lts=new OrderTotalStatistics();
			lts.setOrderid(orderid);
			lts.setTenant_id(tenant_id);
			if("0".equals(sourceType)){
				// 服务类型：同步
				// 根据  order_interface 表 中的orderid 获取到 ser_id 和 ser_version
				// 再根据  order_interface 表 和 ser_main 表 ser_id 和 ser_version 两个字段关联，获取ser_name
				sivs = logSerTotalStatisticsService.selectSyncSeridVerIdName(map);
				lts.setSer_id(sivs.getSer_id());
				lts.setSer_version(sivs.getSer_version());
			}else if("1".equals(sourceType)){
				// 服务类型：异步
				// 根据 order_interface 表 中的orderid 获取到 ser_id
				// 再根据  order_interface 表 和 ser_main 表 ser_id 关联，获取ser_name
				sivs = logSerTotalStatisticsService.selectAsynSeridName(map);
				lts.setSer_id(sivs.getSer_id());
			}else if("2".equals(sourceType)){
				// 服务类型：cas
				// orderid 就是 ser_sp_login 表中的 sp_loginid 再获取到 sp_name
				sivs = logSerTotalStatisticsService.selectCASSeridName(map);
				lts.setSer_id(sivs.getSer_id());
			}
				
			// 1.添加 log_serversion_total_statistics 表 订阅详细记录
			lts.setSendDate(sendDate);
			OrderTotalStatistics sts=orderTotalStatisticsService.selectOrderTotalSendCount(lts);
			if(sts != null && sivs!=null){
				sts.setSer_id(sivs.getSer_id());
				if(sivs.getSer_version()!=null){
					sts.setSer_version(sivs.getSer_version());
				}
				sts.setSendDate(sendDate);
				sts.setOrderid(lts.getOrderid());
				String key=ToolUtils.getSpecialStitching(lts.getOrderid(),lts.getTenant_id());
				sts.setOrder_name(map_copy.get(key));
				sts.setTenant_id(lts.getTenant_id());
				sts.setSendCount(sts.getSuccessCount() + sts.getFailCount());
				OrderTotalStatistics serVersionTotalStatistics = orderTotalStatisticsService.findOrderTotalSta(lts);
				
				if(serVersionTotalStatistics!=null){
					//数据库中存在 则更新
					logger.info("SerVersion Total Statistics : update ---> orderid : {} --- sendCount : {} --- sendSuccess : {}"
							+ " --- failCount : {}",
							sts.getOrderid(),sts.getSendCount(),sts.getSuccessCount(),sts.getFailCount());
					orderTotalStatisticsService.orderTotalStaUpdate(sts);
				}else{
					//数据库中不存在 则插入这条数据
					logger.info("SerVersion Total Statistics : save ---> orderid : {} --- sendCount : {} --- sendSuccess : {}"
							+ " --- failCount : {}",
							sts.getOrderid(),sts.getSendCount(),sts.getSuccessCount(),sts.getFailCount());
					orderTotalStatisticsService.orderTotalStaSave(sts);
				}
				
			}
			sivs.setSendDate(sendDate);
			Ser_Id_Version_Statistics sivsTotal=logSerTotalStatisticsService.selectSer_id_version_nameSendCount(sivs);
			if(sivsTotal!=null){
				sivsTotal.setSer_id(sivs.getSer_id());
				if(sivs.getSer_version()!=null){
					sivsTotal.setSer_version(sivs.getSer_version());
				}
				sivsTotal.setSer_name(sivs.getSer_name());
				sivsTotal.setSendDate(sendDate);
				sivsTotal.setSourceType(sourceType);
				sivsTotal.setTenant_id(tenant_id);
				Ser_Id_Version_Statistics newSivs=logSerTotalStatisticsService.selectSer_id_Version_Name(sivs);
				if(newSivs != null){
					//数据库中存在 则更新
					logger.info("Ser_Id_Version_Statistics : update ---> ser_id : {} --- ser_version : {} --- ser_name : {}"
							+ " --- sendCount : {} --- sendSuccess : {} --- failCount : {}",
							sivsTotal.getSer_id(),sivsTotal.getSer_version() == null ? "":sivsTotal.getSer_version(),sivsTotal.getSer_name(),
									sivsTotal.getSendCount(),sivsTotal.getSuccessCount(),	sivsTotal.getFailCount()	
							);
					logSerTotalStatisticsService.ser_id_Version_NameUpdate(sivsTotal);
				}else{
					//数据库中不存在 则插入这条数据
					logger.info("Ser_Id_Version_Statistics : save ---> ser_id : {} --- ser_version : {} --- ser_name : {}"
							+ " --- sendCount : {} --- sendSuccess : {} --- failCount : {}",
							sivsTotal.getSer_id(),sivsTotal.getSer_version() == null ? "":sivsTotal.getSer_version(),sivsTotal.getSer_name(),
									sivsTotal.getSendCount(),sivsTotal.getSuccessCount(),	sivsTotal.getFailCount()	
							);
					logSerTotalStatisticsService.ser_id_Version_NameSave(sivsTotal);
				}
			}
		}
	}
	
	private void storageLog() throws Exception {
		LogManage.listCopy = LogManage.list;
		logManage.changeContainer();
		if(LogManage.listCopy!=null && LogManage.listCopy.size()>0){
			
			for(LogBean lb:LogManage.listCopy){
				if(1 == lb.getLogType()){
					detachLogRecord(lb);
				}
			}
			
			// 2.切分list，并保存日志
			List<List<LogBean>> bigList =  createList(LogManage.listCopy, 1000);
			int dayTime = Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date()));
			try {
				for(List<LogBean> list : bigList){
					logRecordService.insertBatchLog(InstanceParam.CURRENT_TABLE_NAME,InstanceParam.DISTRIBUTED_GUID,dayTime,list);
				}
			} catch (Exception e) {
				e.printStackTrace();
				// 清空统计记录 
				pubCountMap.clear();
				throw e;
			}
		}
		LogManage.listCopy.clear();
	}
	
	private void detachLogRecord(LogBean lb) {
		String key=ToolUtils.getSpecialStitching(lb.getOrderid(),lb.getCode(),lb.getTenant_id());
		if(!orderIdCodeTenId_Set.contains(key)){
			orderIdCodeTenId_Set.add(key);
			String key2=ToolUtils.getSpecialStitching(lb.getOrderid(),lb.getSourceType(),lb.getTenant_id());
			if(!orderIdDtResTenId_Set.contains(key2)){
				orderIdDtResTenId_Set.add(key2);
			}
			String key3=ToolUtils.getSpecialStitching(lb.getOrderid(),lb.getTenant_id());
			if(!orderIdTen_OrderName.containsKey(key3)){
				orderIdTen_OrderName.put(key3, lb.getOrder_name());
			}
		}
	}

	public static Set<String> initSerVerSCodeTenId_Set() {
		Set<String> serVersionTenantId_Set_Copy = orderIdCodeTenId_Set;
		orderIdCodeTenId_Set = new HashSet<String>();
		return serVersionTenantId_Set_Copy;
	}
	
	public static Set<String> initSerVerSTenId_Set() {
		Set<String> orderIdTenId_Set_Copy = orderIdDtResTenId_Set;
		orderIdDtResTenId_Set = new HashSet<String>();
		return orderIdTenId_Set_Copy;
	}
	
	public static Map<String, String> initOrderIdTen_OrderName() {
		Map<String, String> map_copy = orderIdTen_OrderName;
		orderIdTen_OrderName = new HashMap<String,String>();
		return map_copy;
	}
	
	/**
     * 将一个list切割成几个个数相同数量的list
     * */
	public static <T> List<List<T>>  createList(List<T> tmpList,int size) {  
    	List<List<T>> listArr = new ArrayList<List<T>>();  
    	//获取被拆分的数组个数  
    	int arrSize = tmpList.size()%size==0?tmpList.size()/size:tmpList.size()/size+1;  
    	for(int i=0;i<arrSize;i++) {  
    		List<T>  sub = new ArrayList<T>();  
    		//把指定索引数据放入到list中  
    		for(int j=i*size;j<=size*(i+1)-1;j++) {  
    			if(j<=tmpList.size()-1) {  
    				sub.add(tmpList.get(j));  
    			}  
    		}  
    		listArr.add(sub);  
    	}  
    	return listArr;  
    }
	
	public static Map<Map<String, String>, PubStatistics> getPubCountMap() {
		return pubCountMap;
	}

	public static void setPubCountMap(Map<String,String> map, PubStatistics pubStatistics) {
		pubCountMap.put(map, pubStatistics);
	}

	public static Map<Map<String,String>, PubStatistics> initPubCountMap() {
		Map<Map<String,String>, PubStatistics> pubCountMapCopy = getPubCountMap();
		pubCountMap = new HashMap<Map<String,String>, PubStatistics>(1024);
		return pubCountMapCopy;
	}
	
}
