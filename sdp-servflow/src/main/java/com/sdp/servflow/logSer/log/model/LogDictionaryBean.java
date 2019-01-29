package com.sdp.servflow.logSer.log.model;

/** 
* @Title: logDictionaryBean.java 
* @Description: 日志表路由类 
* fkr 
* 2017年11月9日
*/ 
public class LogDictionaryBean {
	 
	// id
	private Integer id;
	
	// 日期
	private Integer dayDateTime;
	
	// 日总量（过当日统计）
	private Integer dayTotalCount;
	
	// 表名称
	private String tableName;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDayDateTime() {
		return dayDateTime;
	}

	public void setDayDateTime(Integer dayDateTime) {
		this.dayDateTime = dayDateTime;
	}

	public Integer getDayTotalCount() {
		return dayTotalCount;
	}

	public void setDayTotalCount(Integer dayTotalCount) {
		this.dayTotalCount = dayTotalCount;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
