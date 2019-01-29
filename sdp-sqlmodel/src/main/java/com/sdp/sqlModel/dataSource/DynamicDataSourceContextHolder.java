package com.sdp.sqlModel.dataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sdp.SpringApplicationContext;
import com.sdp.common.BaseException;


public class DynamicDataSourceContextHolder {

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
	public static List<String> dataSourceIds = new ArrayList<String>();
	public static Map<String,Object> dataInfos= new HashMap<String,Object>();
	private static DynamicDataSourceRegister register;
	static {
		register= SpringApplicationContext.CONTEXT.getBean(DynamicDataSourceRegister.class);		
	}

	public static void setDataSourceType(String dataSourceType) throws BaseException {
		if(!containsDataSource(dataSourceType)){
			System.out.println("没有发现:"+dataSourceType+"的数据源,重新创建");
			register.addDataSource(dataSourceType.replace("xcloud", ""));
		}
		contextHolder.set(dataSourceType);
	}

	public static String getDataSourceType() {
		return contextHolder.get();
	}

	public static void clearDataSourceType() {
		contextHolder.remove();
	}

	/**
	 * 判断指定DataSrouce当前是否存在
	 *
	 * @param dataSourceId
	 * @return
	 * @author SHANHY
	 * @create  2016年1月24日
	 */
	public static boolean containsDataSource(String dataSourceId){
		return dataSourceIds.contains(dataSourceId);
	}

	public static Map<String,Object> getAll(){
		return dataInfos;
	}


}