package com.sdp.sqlModel.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sdp.frame.web.entity.user.User;
import com.sdp.sqlModel.dataSource.DynamicDataSourceContextHolder;
import com.sdp.sqlModel.entity.SqlDataRes;
import com.sdp.sqlModel.entity.SqlDataResType;
import com.sdp.sqlModel.entity.SqlField;
import com.sdp.sqlModel.entity.SqlTable;
import com.sdp.sqlModel.entity.SqlTableForeignkey;
import com.sdp.sqlModel.entity.SqlTableType;
import com.sdp.sqlModel.mapper.SqlDataResMapper;
import com.sdp.sqlModel.mapper.SqlFieldMapper;
import com.sdp.sqlModel.mapper.SqlTableForeignkeyMapper;
import com.sdp.sqlModel.mapper.SqlTableMapper;
import com.sdp.util.DateUtils;
import com.sdp.util.SpringUtil;

public class UpdateTableInformation implements Runnable{

	private final static Logger 		logger= LoggerFactory.getLogger(UpdateTableInformation.class);
	private SqlDataResMapper sqlDataResDao = SpringUtil.getBean(SqlDataResMapper.class);
	private SqlTableMapper sqlTableMapper = SpringUtil.getBean(SqlTableMapper.class);
	private SqlTableForeignkeyMapper sqlTableForeignkeyMapper = SpringUtil.getBean(SqlTableForeignkeyMapper.class);
	private SqlFieldMapper sqlFieldMapper = SpringUtil.getBean(SqlFieldMapper.class);
	private SqlDataRes sqlDataRes;
	private SqlDataResType sqlDataResType;
	private SqlTableType sqlTableType;
	private User userInfo;
	private List<Map<String,Object>> tableNames;
	public SqlDataRes getSqlDataRes() {
		return sqlDataRes;
	}

	public void setSqlDataRes(SqlDataRes sqlDataRes) {
		this.sqlDataRes = sqlDataRes;
	}

	public List<Map<String, Object>> getTableNames() {
		return tableNames;
	}

	public void setTableNames(List<Map<String, Object>> tableNames) {
		this.tableNames = tableNames;
	}


	public SqlDataResType getSqlDataResType() {
		return sqlDataResType;
	}

	public void setSqlDataResType(SqlDataResType sqlDataResType) {
		this.sqlDataResType = sqlDataResType;
	}

	public SqlTableType getSqlTableType() {
		return sqlTableType;
	}

	public void setSqlTableType(SqlTableType sqlTableType) {
		this.sqlTableType = sqlTableType;
	}

	public User getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(User userInfo) {
		this.userInfo = userInfo;
	}

	@Override
	public void run() {
		try{
			logger.info("建表前："+ new Date());
			createTable();
			logger.info("建表后："+ new Date());
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("建表："+ e.getMessage());
		}
	}
	
	public void createTable(){
		for (Map<String,Object> table : tableNames) {
			SqlTable sqltableNew =sqlTableMapper.findTableInfo(sqlDataRes.getDataResId(), userInfo.getTenantId(), table.get("TABLE_NAME").toString());
			boolean addTable = false;   
			if(sqltableNew == null){
				sqltableNew.setTableSqlName(table.get("TABLE_NAME").toString());
				sqltableNew.setTableName(table.get("TABLE_NAME").toString());
				sqltableNew.setSortId(0);
				if(table.get("TABLE_COMMENT") != null && !table.get("TABLE_COMMENT").toString().equals("")){
					sqltableNew.setTableResume(table.get("TABLE_COMMENT").toString());
				}
				sqltableNew.setCreateBy(userInfo.getLoginId());
				sqltableNew.setDataResId(sqlDataRes.getDataResId());
				sqltableNew.setTenantId(userInfo.getTenantId());
				sqltableNew.setTableTypeId(sqlTableType.getTableTypeId());
				sqlTableMapper.savesqltable(sqltableNew);
				addTable = true;
			}
			//查询表结构信息--添加table与field表
			List<Map<String,Object>> tableInfoes = null;
			if(sqlDataResType.getResType().equals("1")){//mysql
				try{
					DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
					tableInfoes = sqlDataResDao.getTableInfoMysql(table.get("TABLE_NAME").toString(),sqlDataRes.getTableSchema());
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					DynamicDataSourceContextHolder.clearDataSourceType();
				}
				if(tableInfoes != null && !tableInfoes.isEmpty()){
    				fieldMysql( tableInfoes, table, sqltableNew, userInfo,sqlDataRes,addTable);
    			}
	    	}else if(sqlDataResType.getResType().equals("2")){//oracle
	    		try{
	    			DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
	    			tableInfoes = sqlDataResDao.getTableInfoOracle(table.get("TABLE_NAME").toString());
	    		}catch(Exception e){
	    			e.printStackTrace();
	    		}finally{
	    			DynamicDataSourceContextHolder.clearDataSourceType();
	    		}
	    		if(tableInfoes != null && !tableInfoes.isEmpty()){
	    			fieldCreate( tableInfoes, table, sqltableNew, userInfo, sqlDataResType,sqlDataRes,addTable);
    			}
	    	}else if(sqlDataResType.getResType().equals("3")){//DB2
	    		try{
	    			DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
	    			tableInfoes = sqlDataResDao.getTableInfoDb(table.get("TABLE_NAME").toString());
	    		}catch(Exception e){
	    			e.printStackTrace();
	    		}finally{
	    			DynamicDataSourceContextHolder.clearDataSourceType();
	    		}
	    		if(tableInfoes != null && !tableInfoes.isEmpty()){
	    			fieldCreate( tableInfoes, table, sqltableNew, userInfo, sqlDataResType,sqlDataRes,addTable);
    			}
	    	}else if(sqlDataResType.getResType().equals("5")){//sqlserver
	    		try{
	    			DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
//	    			tableInfoes = sqlDataResDao.getTableInfoesSqlserver(table.get("TABLE_NAME").toString());
	    			tableInfoes = sqlDataResDao.getTableInfoSqlserver(table.get("TABLE_NAME").toString());
	    		}catch(Exception e){
	    			e.printStackTrace();
	    		}finally{
	    			DynamicDataSourceContextHolder.clearDataSourceType();
	    		}
	    		if(tableInfoes != null && !tableInfoes.isEmpty()){
	    			fieldCreate( tableInfoes, table, sqltableNew, userInfo, sqlDataResType,sqlDataRes,addTable);
    			}
	    	}
			sqlTableForeignkeyMapper.deleteByMainTableAndTenantId(sqltableNew.getTableId(), userInfo.getTenantId());
			sqlTableForeignkeyMapper.deleteByJoinTableTenantId(sqltableNew.getTableId(),userInfo.getTenantId());
		}
		createForegin(tableNames, userInfo, sqlDataResType, sqlTableType, sqlDataRes);
	}
	/**
	 * 添加外键
	 * @param tableSchemas
	 * @param userInfo
	 * @param sqlDataResType
	 * @param sqlTableType
	 * @param sqlDataRes
	 */
	 public void createForegin(List<Map<String,Object>> tableNames,User userInfo,SqlDataResType sqlDataResType,SqlTableType sqlTableType,SqlDataRes sqlDataRes){
	    	//添加外键表
	    	List<Map<String,Object>> keyInfoes = null;
	    	 if(sqlDataResType.getResType().equals("2")){//oracle
	     		for (Map<String,Object> table : tableNames) {
	     			try{
	     				DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
	     				keyInfoes = sqlDataResDao.getForeignKeyInfoesOracle(table.get("TABLE_NAME").toString());
	     			}catch(Exception e){
	     				e.printStackTrace();
	     			}finally{
	     				DynamicDataSourceContextHolder.clearDataSourceType();
	     			}
	     			implementation( keyInfoes, userInfo, sqlDataResType, sqlTableType, sqlDataRes);
	        	}
	     	}else if(sqlDataResType.getResType().equals("3")){//DB2
	     		for (Map<String,Object> table : tableNames) {
	     			try{
	     				DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
	     				keyInfoes = sqlDataResDao.getForeignKeyInfoesDb(table.get("TABLE_NAME").toString());
	     			}catch(Exception e){
	     				e.printStackTrace();
	     			}finally{
	     				DynamicDataSourceContextHolder.clearDataSourceType();
	     			}
	     			implementation( keyInfoes, userInfo, sqlDataResType, sqlTableType, sqlDataRes);
	        	}
	     	}else if(sqlDataResType.getResType().equals("1")){//msyql
	     		try{
	    			DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
	    			keyInfoes = sqlDataResDao.getForeignKeyInfoesMysql(sqlDataRes.getTableSchema());
	    		}catch(Exception e){
	    			e.printStackTrace();
	    		}finally{
	    			DynamicDataSourceContextHolder.clearDataSourceType();
	    		}
	    		implementation( keyInfoes, userInfo, sqlDataResType, sqlTableType, sqlDataRes);
	    	}else if(sqlDataResType.getResType().equals("5")){//sqlserver
	    		try{
	    			DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
	    			keyInfoes = sqlDataResDao.getForeignKeyInfoesSqlserver();
	    		}catch(Exception e){
	    			e.printStackTrace();
	    		}finally{
	    			DynamicDataSourceContextHolder.clearDataSourceType();
	    		}
	    		implementation( keyInfoes, userInfo, sqlDataResType, sqlTableType, sqlDataRes);
	    	}
	    }
	    /**
	     * 存外键表
	     * @param keyInfoes
	     * @param userInfo
	     * @param sqlDataResType
	     * @param sqlTableType
	     * @param sqlDataRes
	     */
	    public void implementation(List<Map<String,Object>> keyInfoes,User userInfo,SqlDataResType sqlDataResType,SqlTableType sqlTableType,SqlDataRes sqlDataRes){
	    	//根据表名称查表id放入map("表名称":"ID") list("表名称")--入外键库
			List<String> nameList = new ArrayList<String>();
			HashMap<String, SqlTable> nameMap = new HashMap<String, SqlTable>();
			if(keyInfoes != null && !keyInfoes.isEmpty()){
				for (Map<String, Object> map2 : keyInfoes) {
					SqlTableForeignkey sqlTableForeignkey = new SqlTableForeignkey();
					//租户id 数据源id 表类型id 表sql名称 -- 
					if(!nameList.contains(map2.get("TABLE_NAME").toString())){
						SqlTable sqlTableInfo = sqlTableMapper.findTableInfo(sqlDataRes.getDataResId(),userInfo.getTenantId(),map2.get("TABLE_NAME").toString());
						if(sqlTableInfo != null){
							nameMap.put(map2.get("TABLE_NAME").toString(), sqlTableInfo);
						}else{
							continue;
						}
						nameList.add(map2.get("TABLE_NAME").toString());
		    		}
					if(!nameList.contains(map2.get("REFERENCED_TABLE_NAME").toString())){
						SqlTable sqlTableInfo = sqlTableMapper.findTableInfo(sqlDataRes.getDataResId(),userInfo.getTenantId(),map2.get("REFERENCED_TABLE_NAME").toString());
						nameMap.put(map2.get("REFERENCED_TABLE_NAME").toString(), sqlTableInfo);
						nameList.add(map2.get("REFERENCED_TABLE_NAME").toString());
					}
					
					sqlTableForeignkey.setJoinField(map2.get("COLUMN_NAME").toString());
					sqlTableForeignkey.setJoinTable(nameMap.get(map2.get("TABLE_NAME").toString()).getTableId());
					sqlTableForeignkey.setLineStart("left");
					sqlTableForeignkey.setLineEnd("left");
					sqlTableForeignkey.setMainField(map2.get("REFERENCED_COLUMN_NAME").toString());
					sqlTableForeignkey.setMainTable(nameMap.get(map2.get("REFERENCED_TABLE_NAME").toString()).getTableId());
					sqlTableForeignkey.setTenantId(userInfo.getTenantId());
					sqlTableForeignkey.setForeignX(nameMap.get(map2.get("REFERENCED_TABLE_NAME").toString()).getTableX());
					sqlTableForeignkey.setForeignY(nameMap.get(map2.get("REFERENCED_TABLE_NAME").toString()).getTableY());
					sqlTableForeignkeyMapper.save(sqlTableForeignkey);
				}
			}
	    }
	    
	    /**
	     * 创建field表除mysql外
	     * @param tableInfoes
	     * @param table
	     * @param sqlTable
	     * @param userInfo
	     * @param sqlDataResType
	     */
	    public void fieldCreate(List<Map<String,Object>> tableInfoes,Map<String,Object> table,SqlTable sqlTable,User userInfo,SqlDataResType sqlDataResType,SqlDataRes sqlDataRes,boolean addTabe){
	    	//查询主外键信息--更新field与添加外键表
	    	List<String> keyInfo = null;
	    	try{
				DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
				if(sqlDataResType.getResType().equals("2")){//oracle
		    		keyInfo = sqlDataResDao.getPrimaryKeyOracle(table.get("TABLE_NAME").toString());
		    	}else if(sqlDataResType.getResType().equals("3")){//DB2
		    		keyInfo = sqlDataResDao.getPrimaryKeyDb(table.get("TABLE_NAME").toString());
		    	}else if(sqlDataResType.getResType().equals("5")){//sqlserver
		    		keyInfo = sqlDataResDao.getPrimaryKeySqlserver(table.get("TABLE_NAME").toString());
		    	}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				DynamicDataSourceContextHolder.clearDataSourceType();
			}
	    	List<SqlField> sqlFieldes = null;
	    	if(!addTabe){
	    		sqlFieldes = sqlFieldMapper.findByTableIdTenantId(sqlTable.getTableId(), userInfo.getTenantId());	
	    	}
			for (Map<String, Object> map : tableInfoes) {
				SqlField sqlField = new SqlField();
				if(map.get("LENGTH") != null){
					sqlField.setFieldLen(Integer.parseInt(map.get("LENGTH").toString()));
				}else{
					sqlField.setFieldLen(0);
				}
				if(map.get("PRECISION") != null){
					sqlField.setFieldDigit(Integer.parseInt(map.get("PRECISION").toString()));
				}
				if(map.get("COLUMN_KEY") != null && !map.get("COLUMN_KEY").toString().equals("")){
					sqlField.setSortIndex("1");
				}else{
					sqlField.setSortIndex("0");
				}
				sqlField.setIsNull(map.get("IS_NULLABLE").toString());
				sqlField.setDateType(map.get("DATA_TYPE").toString());
				sqlField.setCreateBy(userInfo.getLoginId());
				sqlField.setFieldType(getType(map.get("DATA_TYPE").toString(),sqlDataResType.getResType()));
				sqlField.setCreateDate(DateUtils.getCurrentDate());
				sqlField.setFieldSqlName(map.get("COLUMN_NAME").toString());
				sqlField.setFieldName(map.get("COLUMN_NAME").toString());
				if(map.get("COLUMN_COMMENT") != null && !map.get("COLUMN_COMMENT").toString().equals("")){
					sqlField.setFieldResume(map.get("COLUMN_COMMENT").toString());
				}
				sqlField.setTableId(sqlTable.getTableId());
				sqlField.setTenantId(userInfo.getTenantId());
				if(keyInfo != null && !keyInfo.isEmpty() && keyInfo.contains(map.get("COLUMN_NAME").toString())){
					sqlField.setFieldKey("1");
				}else{
					sqlField.setFieldKey("0");
				}
				if(!addTabe){
					boolean judge = false;
					if(sqlFieldes !=null && !sqlFieldes.isEmpty()){
						for (SqlField sqlField1 : sqlFieldes) {
							if(sqlField1.getDateType() != null && sqlField1.getDateType().toLowerCase().equals(map.get("DATA_TYPE").toString().toLowerCase()) &&
									sqlField1.getFieldSqlName().toLowerCase().equals(map.get("COLUMN_NAME").toString().toLowerCase())){
								judge= true;
								sqlField.setFieldId(sqlField1.getFieldId());
								sqlFieldes.remove(sqlField1);
							}
						}
					}
					if(judge){
						sqlFieldMapper.update(sqlField);
					}else{
						sqlFieldMapper.save(sqlField);
					}
				}else{
					sqlFieldMapper.save(sqlField);
				}
				sqlFieldMapper.save(sqlField);
			}
			if(sqlFieldes !=null && !sqlFieldes.isEmpty()){
				for (SqlField sqlField2 : sqlFieldes) {
					sqlFieldMapper.delete(sqlField2.getFieldId());
				}
			}
	    }
	    /**
	     * mysql 入库field字段
	     * @param tableInfoes
	     * @param table
	     * @param sqlTable
	     * @param userInfo
	     */
	    public void fieldMysql(List<Map<String,Object>> tableInfoes,Map<String,Object> table,SqlTable sqlTable,User userInfo,SqlDataRes sqlDataRes,boolean addTabe){
			//查询主外键信息--更新field与添加外键表
	    	List<String> keyInfo = null;
	    	try{
				DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
				keyInfo = sqlDataResDao.getPrimaryKeyMysql(table.get("TABLE_NAME").toString(), sqlDataRes.getTableSchema());
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				DynamicDataSourceContextHolder.clearDataSourceType();
			}
	    	List<SqlField> sqlFieldes = null;
	    	if(!addTabe){
	    		sqlFieldes = sqlFieldMapper.findByTableIdTenantId(sqlTable.getTableId(), userInfo.getTenantId());	
	    	}
			for (Map<String, Object> map : tableInfoes) {
				String length = map.get("COLUMN_TYPE").toString();
				SqlField sqlField = new SqlField();
				if(!length.startsWith("enum") && !length.startsWith("set")){
					if(length.contains(",")){
						length = length.substring(length.lastIndexOf("(")+1, length.lastIndexOf(")"));
						String[] len = length.split(",");
						try{
							sqlField.setFieldDigit(Integer.parseInt(len[1]));
							sqlField.setFieldLen(Integer.parseInt(len[0]));
						}catch(Exception ex){
							sqlField.setFieldDigit(0);
							sqlField.setFieldLen(0);
						}
					}else{
						 if(length.contains("(")){
							 length = length.substring(length.lastIndexOf("(")+1, length.lastIndexOf(")"));
						 }else{
							 length = "0"; 
						 }
					}
				}else{
					length = "0";
				}
				if(map.get("COLUMN_KEY") != null && !map.get("COLUMN_KEY").toString().equals("")){
					sqlField.setSortIndex("1");
				}else{
					sqlField.setSortIndex("0");
				}
				sqlField.setIsNull(map.get("IS_NULLABLE").toString());
				sqlField.setDateType(map.get("DATA_TYPE").toString());
				sqlField.setCreateBy(userInfo.getLoginId());
				sqlField.setFieldType(getType(map.get("DATA_TYPE").toString(),sqlDataResType.getResType()));
				sqlField.setCreateDate(DateUtils.getCurrentDate());
				if(sqlField.getFieldLen() == null){
					sqlField.setFieldLen(Integer.parseInt(length));
				}
				sqlField.setFieldSqlName(map.get("COLUMN_NAME").toString());
				sqlField.setFieldName(map.get("COLUMN_NAME").toString());
				if(map.get("COLUMN_COMMENT") != null && !map.get("COLUMN_COMMENT").toString().equals("")){
					sqlField.setFieldResume(map.get("COLUMN_COMMENT").toString());
				}
				sqlField.setTableId(sqlTable.getTableId());
				sqlField.setTenantId(userInfo.getTenantId());
				if(keyInfo != null && !keyInfo.isEmpty() && keyInfo.contains(map.get("COLUMN_NAME").toString())){
					sqlField.setFieldKey("1");
				}else{
					sqlField.setFieldKey("0");
				}
				if(!addTabe){
					boolean judge = false;
					if(sqlFieldes !=null && !sqlFieldes.isEmpty()){
						for (int i = 0; i < sqlFieldes.size(); i++) {
							SqlField sqlField1 = sqlFieldes.get(i);
							if(sqlField1 != null){
								if(sqlField1.getDateType() != null && sqlField1.getDateType().toLowerCase().equals(map.get("DATA_TYPE").toString().toLowerCase()) &&
										sqlField1.getFieldSqlName().toLowerCase().equals(map.get("COLUMN_NAME").toString().toLowerCase())){
									judge= true;
									sqlField.setFieldId(sqlField1.getFieldId());
									sqlFieldes.remove(i);
									i--;
								}
							}
						}
					}
					if(judge){
						sqlFieldMapper.update(sqlField);
					}else{
						sqlFieldMapper.save(sqlField);
					}
				}else{
					sqlFieldMapper.save(sqlField);
				}
			}
			if(sqlFieldes !=null && !sqlFieldes.isEmpty()){
				for (SqlField sqlField2 : sqlFieldes) {
					sqlFieldMapper.delete(sqlField2.getFieldId());
				}
			}
	    }
	    
	public String getType(String typeString,String resType){
		try{
			if(resType.equals("1")){
				if(typeString.toLowerCase().equals("varchar")){
					return "2";
				}else if(typeString.toLowerCase().equals("int")){
					return "6";
				}else if(typeString.toLowerCase().equals("datetime")){
					return "3";
				}else if(typeString.toLowerCase().equals("blob")){
					return "5";
				}else if(typeString.toLowerCase().equals("longtext")){
					return "4";
				}else if(typeString.toLowerCase().equals("bigint")){
					return "7";
				}else if(typeString.toLowerCase().equals("float")){
					return "1";
				}else if(typeString.toLowerCase().equals("tinyint")){
					return "9";
				}
			}else if(resType.equals("2")){
				if(typeString.toLowerCase().equals("varchar2")){
					return "2";
				}else if(typeString.toLowerCase().equals("number")){
					return "6";
				}else if(typeString.toLowerCase().equals("date")){
					return "3";
				}else if(typeString.toLowerCase().equals("blob")){
					return "4";
				}else if(typeString.toLowerCase().equals("float")){
					return "1";
				}
			}else if(resType.equals("5")){
				if(typeString.toLowerCase().equals("varchar")){
					return "2";
				}else if(typeString.toLowerCase().equals("int")){
					return "6";
				}else if(typeString.toLowerCase().equals("datetime")){
					return "3";
				}else if(typeString.toLowerCase().equals("bigint")){
					return "7";
				}else if(typeString.toLowerCase().equals("nvarchar(max)")){
					return "4";
				}else if(typeString.toLowerCase().equals("float")){
					return "1";
				}else if(typeString.toLowerCase().equals("varbinary(max)")){
					return "5";
				}else if(typeString.toLowerCase().equals("bit")){
					return "9";
				}
			}else if(resType.equals("3")){
				if(typeString.toLowerCase().equals("varchar")){
					return "2";
				}else if(typeString.toLowerCase().equals("int")){
					return "6";
				}else if(typeString.toLowerCase().equals("decimal")){
					return "1";
				}else if(typeString.toLowerCase().equals("timestamp")){
					return "3";
				}else if(typeString.toLowerCase().equals("clob")){
					return "4";
				}else if(typeString.toLowerCase().equals("boolean")){
					return "9";
				}else if(typeString.toLowerCase().equals("blob")){
					return "5";
				}else if(typeString.toLowerCase().equals("bigint")){
					return "7";
				}
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return "10";
	}
}
