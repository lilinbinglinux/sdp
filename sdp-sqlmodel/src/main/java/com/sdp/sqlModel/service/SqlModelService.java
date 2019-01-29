package com.sdp.sqlModel.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.sdp.common.BaseException;
import com.sdp.common.CurrentUserUtils;
import com.sdp.common.constant.Dictionary;
import com.sdp.common.entity.Status;
import com.sdp.frame.web.entity.user.User;
import com.sdp.sqlModel.dataSource.DynamicDataSourceContextHolder;
import com.sdp.sqlModel.entity.SqlDataRes;
import com.sdp.sqlModel.entity.SqlDataResType;
import com.sdp.sqlModel.entity.SqlField;
import com.sdp.sqlModel.entity.SqlShape;
import com.sdp.sqlModel.entity.SqlSubTable;
import com.sdp.sqlModel.entity.SqlSubTreasury;
import com.sdp.sqlModel.entity.SqlSubTreasuryDivision;
import com.sdp.sqlModel.entity.SqlTable;
import com.sdp.sqlModel.entity.SqlTableForeignkey;
import com.sdp.sqlModel.entity.SqlTableType;
import com.sdp.sqlModel.function.Sql_switcher;
import com.sdp.sqlModel.mapper.SqlDataResMapper;
import com.sdp.sqlModel.mapper.SqlDataResTypeMapper;
import com.sdp.sqlModel.mapper.SqlFieldMapper;
import com.sdp.sqlModel.mapper.SqlQueryMapper;
import com.sdp.sqlModel.mapper.SqlShapeMapper;
import com.sdp.sqlModel.mapper.SqlSubTableMapper;
import com.sdp.sqlModel.mapper.SqlSubTreasuryDivisionMapper;
import com.sdp.sqlModel.mapper.SqlSubTreasuryMapper;
import com.sdp.sqlModel.mapper.SqlTableForeignkeyMapper;
import com.sdp.sqlModel.mapper.SqlTableMapper;
import com.sdp.sqlModel.mapper.SqlTableTypeMapper;
import com.sdp.util.DateUtils;

/**
 * 数据库建模Service
 *
 * @author 
 */
@Service
//@Transactional
public class SqlModelService {

	@Autowired SqlTableMapper sqlTableDao;
	@Autowired SqlFieldMapper sqlFieldDao;
	@Autowired SqlQueryMapper sqlQueryMapper;
	@Autowired SqlTableForeignkeyMapper sqlTableForeignkeyDao;
	@Autowired SqlTableTypeMapper sqlTableTypeMapper;
//	@Autowired SqlPublicPartitionMapper sqlPublicPartitionMapper;
	@Autowired
	private SqlDataResMapper sqlDataResDao;
    @Autowired
    private SqlDataResTypeMapper sqlDataResTypeDao;
//    @Autowired
//    private SqlPartitionMapper sqlPartitionMapper;
//    @Autowired
//    private SqlSubdivisionMapper sqlSubdivisionMapper;
    @Autowired
    private SqlShapeMapper sqlShapeMapper;
    @Autowired
    private SqlSubTableMapper sqlSubTableMapper;
    @Autowired
    private SqlSubTreasuryMapper sqlSubTreasuryMapper;
	@Autowired SqlSubTreasuryDivisionMapper sqlSubTreasuryDivisionMapper;
    @Autowired
    private TableToolService tableToolService;
    
   
	 /**
     * 输出日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(SqlModelService.class);
    
    /**
	 * 数据库建模--删除表数据
     * @throws BaseException 
	 */
	public Status deleteByTableId(String tableId) throws BaseException {
		Status status;
		User userInfo = CurrentUserUtils.getInstance().getUser();
		String tenantId = userInfo.getTenantId();
        if (tableId!= null) {
        	SqlDataResType sqlDataResType = null;
        	//获取旧的数据信息
			SqlTable sqlTable = sqlTableDao.findByTableId(tableId);
        	//查询数据源与数据源类型信息
    		SqlDataRes sqlDataRes = sqlDataResDao.findByDataResIdAndDataStatus(sqlTable.getDataResId(),"1");//修改添加启用状态
    		if(sqlDataRes != null){
    			sqlDataResType = sqlDataResTypeDao.findByDataResTypeIdAndDataStutas(sqlDataRes.getDataResTypeId(),"1");//修改添加启用状态
    			if(sqlDataResType == null){
    				throw new BaseException("sqlDataResType is wrong");
    			}
    		}else{
    			throw new BaseException("sqlDataRes is wrong");
    		}
        	//查询是否存在指向该表的外键
        	List<SqlTableForeignkey> findForeignkey = sqlTableForeignkeyDao.findForeignkey(tableId,tenantId);
        	List<String> deleteList = new ArrayList<String>();
        	if(findForeignkey != null && !findForeignkey.isEmpty()){
        		Map<String,Object> constraintForeign = constraintName( sqlDataRes, sqlDataResType, sqlTable);
        		if(constraintForeign != null && constraintForeign.size() > 0){
        			for(SqlTableForeignkey sqlTableForeignkey : findForeignkey) {
    					SqlTable sqlTables =sqlTableDao.findByTableId(sqlTableForeignkey.getJoinTable());
    					if(sqlTables != null){
    						HashMap<String, Object> req = new HashMap<String, Object>();
    						if( constraintForeign.get(sqlTables.getTableSqlName()+sqlTableForeignkey.getJoinField()) != null && !constraintForeign.get(sqlTables.getTableSqlName()+sqlTableForeignkey.getJoinField()).toString().equals("")){
    							req.put("joinField", constraintForeign.get(sqlTables.getTableSqlName()+sqlTableForeignkey.getJoinField()));
    							req.put("sqName", sqlTables.getTableSqlName());
    							//删除查询到的外键
    							deleteList.add(Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).deleteForeignkey(req));
    						}
    					}
    				}
        		}
        	}
        	try{
				DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
				if(deleteList != null && !deleteList.isEmpty()){
					for (String string : deleteList) {
						sqlTableForeignkeyDao.deleteSql(string);
					}
				}
				String dropTble="DROP TABLE "+sqlTable.getTableSqlName();
				sqlTableForeignkeyDao.deleteSql(dropTble);
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				DynamicDataSourceContextHolder.clearDataSourceType();
			}
        	sqlTableForeignkeyDao.deleteByJoinTableTenantId(tableId,tenantId);
        	sqlTableForeignkeyDao.deleteByMainTableAndTenantId(tableId,tenantId);
        	sqlFieldDao.deleteByTableIdAndTenantId(tableId,tenantId);
        	sqlTableDao.deleteByTableIdAndTenantId(tableId,tenantId);
        	deletePartition( tableId, userInfo);
            status = new Status("删除数据成功",Dictionary.HttpStatus.OK.value );
        } else {
            status = new Status("删除数据失败，参数有误",Dictionary.HttpStatus.INVALID_REQUEST.value );
        }
        return status;
	}
	/**
	 * 删除分库分表分区数据
	 * @param tableId
	 * @param userInfo
	 */
	public void deletePartition(String tableId,User userInfo){
		List<SqlShape> sqlShapes = sqlShapeMapper.findByTableId(tableId, userInfo.getTenantId());
		if(sqlShapes != null && !sqlShapes.isEmpty()){
			for (SqlShape sqlShape : sqlShapes) {
				sqlShapeMapper.delete(sqlShape.getShapeId());
				if(sqlShape.getShapeType().equals("1")){//1 分区 2 分表 3 分库
				}else if(sqlShape.getShapeType().equals("2")){//
					SqlSubTable sqlSubTable = sqlSubTableMapper.findSqlSubTable(sqlShape.getShapeId(),  userInfo.getTenantId());
					if(sqlSubTable != null){
						sqlSubTableMapper.delete(sqlSubTable.getSubTableId());
					}
				}else if(sqlShape.getShapeType().equals("3")){//
					SqlSubTreasury sqlSubTreasury = sqlSubTreasuryMapper.findSqlSubTreasury(sqlShape.getShapeId(),  userInfo.getTenantId());
					if(sqlSubTreasury != null){
						sqlSubTreasuryMapper.deleteSqlSubTreasury(sqlShape.getShapeId(),  userInfo.getTenantId());
						sqlSubTreasuryDivisionMapper.deleteDivision(sqlSubTreasury.getSubTreasuryId(),  userInfo.getTenantId());
					}
				}
			}
		}
	}
	
    public Map<String,Object> constraintName(SqlDataRes sqlDataRes,SqlDataResType sqlDataResType,SqlTable sqlTable){
    	Map<String,Object> map = new HashMap<String,Object>();
    	List<Map<String,Object>> foreignInfoes = new ArrayList<Map<String,Object>>();
		try{//dataResId
			DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
			if(sqlDataResType.getResType().equals("1")){
    			foreignInfoes = sqlFieldDao.findForefignMysql(sqlTable.getTableSqlName(),sqlDataRes.getTableSchema()); //TABLE_SCHEMA
    		}else if(sqlDataResType.getResType().equals("2")){//oracle
    			foreignInfoes = sqlDataResDao.getForeignKeyInfoesOracle(sqlTable.getTableSqlName());
    		}else if(sqlDataResType.getResType().equals("3")){//DB2
    			foreignInfoes = sqlDataResDao.getForeignKeyInfoesDb(sqlTable.getTableSqlName());
    		}else if(sqlDataResType.getResType().equals("5")){//sqlserver
    			foreignInfoes = sqlFieldDao.findForefignSqlserver(sqlTable.getTableSqlName());
    		}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DynamicDataSourceContextHolder.clearDataSourceType();
		}
    	if(foreignInfoes != null && !foreignInfoes.isEmpty()){
    		for (Map<String, Object> map2 : foreignInfoes) {
    			map.put(map2.get("TABLE_NAME").toString().trim()+map2.get("COLUMN_NAME").toString().trim(), map2.get("CONSTRAINT_NAME"));
			}
    	}
    	return map;
    }
	/**
	 * 数据表信息保存
	 * @return 
	 * @throws Exception 
	 */
//    @Transactional(rollbackFor=RuntimeException.class)
	public Map<String, Object> save(SqlTable sqlTable){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//判断是否为新建表
		boolean	addTable = true;
		//旧表名修改
		String modifyTableName = "";
		SqlDataResType sqlDataResType = null;
		User userInfo = CurrentUserUtils.getInstance().getUser();
		Date current = DateUtils.getCurrentDate();
		sqlTable.setTenantId(userInfo.getTenantId());
		sqlTable.setCreateDate(current);
		sqlTable.setCreateBy(userInfo.getLoginId());
		//查询数据源与数据源类型信息
		SqlDataRes sqlDataRes = sqlDataResDao.findByDataResIdAndDataStatus(sqlTable.getDataResId(),"1");//修改添加启用状态
		if(sqlDataRes != null){
			sqlDataResType = sqlDataResTypeDao.findByDataResTypeIdAndDataStutas(sqlDataRes.getDataResTypeId(),"1");//修改添加启用状态
			if(sqlDataResType == null){
				throw new RuntimeException("sqlDataResType is wrong");
			}
		}else{
			throw new RuntimeException("sqlDataRes is wrong");
		}
		List<SqlField> sqlFieldes = sqlTable.getSqlFieldes();
		//需要删除的字段
		LOG.info("tableID:"+sqlTable.getTableId());
		List<SqlField> sqlFieldIdes = null;
		SqlTable sqlTableOld = null;
		if(!StringUtils.isBlank(sqlTable.getTableId())){
			//获取旧的数据信息
			sqlTableOld = sqlTableDao.findByTableId(sqlTable.getTableId());
			sqlFieldIdes = sqlFieldDao.findByTableIdTenantId(sqlTable.getTableId(),sqlTable.getTenantId());
			//英文名称发送变化修改表结构
			if(sqlTableOld != null){
				if(sqlTableOld.getTableSqlName() != null && !sqlTableOld.getTableSqlName().equals(sqlTable.getTableSqlName())){
					modifyTableName = sqlTableOld.getTableSqlName();
					LOG.info("需要处理---表结构:" + modifyTableName);
				}
			}else{
				resultMap.put("code", "111111");
				resultMap.put("result", "false");
				throw new RuntimeException();
			}
			if(sqlFieldIdes != null && !sqlFieldIdes.isEmpty()){
				addTable = false;
			}
			sqlTableDao.update(sqlTable);
		}else{
			sqlTableDao.savesqltable(sqlTable);
		}
		//记录新增表字段
		List<SqlField> sqlFieldNew = new ArrayList<SqlField>();
		//记录修改表字段
		List<HashMap<String,Object>> sqlFieldAlert = new ArrayList<HashMap<String,Object>>();
		//新增索引
		List<String> newSortIndex = new ArrayList<String>();
		//移除索引
		List<String> deleteSortIndex = new ArrayList<String>();
		//新增主键
		List<SqlField> newPrimarykey = new ArrayList<SqlField>();
		//未变化主键
		List<SqlField> primarykey = new ArrayList<SqlField>();
		//删除主键
		List<String> deletePrimarykey = new ArrayList<String>();
		if(sqlFieldes != null && !sqlFieldes.isEmpty()){
			for (SqlField sqlField : sqlFieldes) {
				sqlField.setTableId(sqlTable.getTableId());
				sqlField.setTenantId(userInfo.getTenantId());
				sqlField.setCreateDate(current);
				sqlField.setCreateBy(userInfo.getLoginId());
				//新增表字段集合
				if(!StringUtils.isBlank(sqlField.getFieldId())){
					SqlField sqlFieldOld = sqlFieldDao.findByFieldId(sqlField.getFieldId());
					if(sqlFieldOld != null){
						if(sqlFieldIdes != null ){//&&  sqlFieldIdes.contains(sqlFieldOld)
							for (SqlField field : sqlFieldIdes) {
								if(field.getFieldId().equals(sqlFieldOld.getFieldId())){
									sqlFieldIdes.remove(field);
									break;
								}
							}
						}
						if(sqlBoolean(sqlField,sqlFieldOld,sqlTable,userInfo.getTenantId())){
							sqlFieldAlert.add(getInfo(sqlField,sqlFieldOld));
						}
						//比较索引 oracle或者DB2 改表名情况下
						sortIndex(sqlDataResType, sqlField, sqlFieldOld, deleteSortIndex, newSortIndex, modifyTableName);
						//比较主键  判断表名称是否修改
						getPrimarykey(sqlField, modifyTableName, sqlFieldOld, newPrimarykey, primarykey, deletePrimarykey);
					}
					sqlFieldDao.update(sqlField);
				}else{
					sqlFieldNew.add(sqlField);
					//判断新加索引
					if(sqlField.getSortIndex().equals("1")){
						if(!sqlField.getFieldKey().equals("1")){
							newSortIndex.add(sqlField.getFieldSqlName());
						}
					}
					//新增主键
					if(sqlField.getFieldKey() != null && sqlField.getFieldKey().equals("1")){
						newPrimarykey.add(sqlField);
					}
					sqlFieldDao.save(sqlField);
				}
			}
		}
		//判断删除索引
		if(sqlFieldIdes != null && sqlFieldIdes.size() > 0){
			for (SqlField sqlField : sqlFieldIdes) {
				if(sqlField.getSortIndex().equals("1") && !sqlField.getFieldKey().equals("1")){
					deleteSortIndex.add(sqlField.getFieldSqlName());
				}
				//删除主键字段
				if(sqlField.getFieldKey() != null && sqlField.getFieldKey().equals("1")){
					deletePrimarykey.add(sqlField.getFieldSqlName());
				}
				sqlFieldDao.delete(sqlField.getFieldId());
			}
		}
		//查询表类型码表，转换为map<"1","varchar">
		HashMap<String,Object> typeMap = typeMap(sqlDataResType);
		//处理表结构逻辑
		//1.新增表
		if(addTable){
			resultMap = saveVertical(sqlDataRes,sqlDataResType,sqlTable,sqlFieldNew,typeMap,userInfo);
		}else{
			List<String> deleteList = new ArrayList<String>();
			List<String> newList = new ArrayList<String>();
			operation( sqlFieldIdes, sqlDataResType, deleteSortIndex, sqlTable, sqlFieldAlert, modifyTableName, sqlDataRes, newPrimarykey,
					primarykey, deletePrimarykey, deleteList, newList, userInfo);
			//2.修改表结构
			try{
				resultMap = modifyTable( sqlDataRes, sqlDataResType, sqlTable, modifyTableName, sqlFieldNew, sqlFieldAlert,sqlFieldIdes, deleteSortIndex,
						newSortIndex,typeMap,newPrimarykey,deletePrimarykey,primarykey,deleteList,newList,userInfo,sqlTableOld);
				//更新分区---
				//--新建临时表--导入数据--修改之前表名称--修改新表名称
				if(resultMap != null && resultMap.get("code").equals("000000")){
					resultMap = isChange( sqlTable, sqlDataRes, sqlDataResType,typeMap,userInfo );
				}
			}catch (Exception ex) {
				LOG.error(ex.getMessage());
				resultMap.put("code", "111111");
				resultMap.put("result", "false");
				throw new RuntimeException();
			}
		}
		return resultMap;
	}
	//分区是否变化--变化的话修改---(前提分区存在)
	public Map<String,Object> isChange(SqlTable sqlTable,SqlDataRes sqlDataRes,SqlDataResType sqlDataResType,HashMap<String,Object> haMap,User userInfo ){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("code", "000000");
		resultMap.put("result", "true");
		List<SqlShape> sqlShapes = sqlTable.getSqlShape();
		if(sqlShapes != null && !sqlShapes.isEmpty()){
			for (SqlShape sqlShape : sqlShapes) {
				if(sqlShape.getShapeType().equals("1") && sqlShape.getState().equals("0")){
					SqlShape shapeOld = sqlShapeMapper.findByShapeId(sqlShape.getShapeId());
					String partitionValue = sqlShape.getPartitionValue();
					String partitionValueOld = shapeOld.getPartitionValue();
					//--比较是否变化--
					if(!partitionValue.trim().equals(partitionValueOld.trim())){
						//变化--新建临时表--导入数据--修改之前表名称--修改新表名称
						String tableSqlName = sqlTable.getTableSqlName();
						List<SqlField> sqlFieldNew = sqlTable.getSqlFieldes();
						String uuid = UUID.randomUUID().toString();  //转化为String对象
						uuid = uuid.replace("-", "").substring(0, 7);
						sqlTable.setTableSqlName(tableSqlName+"_linshi"+uuid);
						String tableId = sqlTable.getTableId();
						sqlTable.setTableId(null);
						resultMap = editCreateTable( sqlDataRes, sqlDataResType, sqlTable, sqlFieldNew, haMap);//创建临时表
						sqlTable.setTableId(tableId);
						if(resultMap.get("code").equals("000000")){
							//导入数据---
							try{//dataResId
								DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
								sqlQueryMapper.insertInfo(sqlTable.getTableSqlName(), tableSqlName);
							}catch(Exception e){
								e.printStackTrace();
							}finally{
								DynamicDataSourceContextHolder.clearDataSourceType();
							}
							//修改之前表名称
							String result = tableToolService.executeDdl(sqlDataRes,Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).modifyName(tableSqlName+"_old"+uuid,tableSqlName),sqlDataResType);
							if(!result.equals("true")){
								sqlTable.setTableSqlName(tableSqlName);
								return resultMap;
							}else{
								String result1 = tableToolService.executeDdl(sqlDataRes,Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).modifyName(tableSqlName,sqlTable.getTableSqlName()),sqlDataResType);
								if(!result1.equals("true")){
									tableToolService.executeDdl(sqlDataRes,Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).modifyName(tableSqlName,tableSqlName+"_old"+uuid),sqlDataResType);
									sqlTable.setTableSqlName(tableSqlName);
									return resultMap;
								}
							}
							//保存SQLShape信息
//							User userInfo = CurrentUserUtils.getInstance().getUser();
							sqlShape.setTenantId(userInfo.getTenantId());
							sqlShapeMapper.update(sqlShape);
						}else{
							//异常--不保存SQLShape信息
						}
						sqlTable.setTableSqlName(tableSqlName);
					}
					return resultMap;
				}
			}
		}
		return resultMap;
	}
	
	/**
	 * 获取主键，索引，外键的约束名称以及修改
	 * @param sqlFieldIdes
	 * @param sqlDataResType
	 * @param deleteSortIndex
	 * @param sqlTable
	 * @param sqlFieldAlert
	 * @param modifyTableName
	 * @param sqlDataRes
	 * @param newPrimarykey
	 * @param primarykey
	 * @param deletePrimarykey
	 * @param deleteList
	 * @param newList
	 * @param userInfo
	 */
	public void operation(List<SqlField> sqlFieldIdes,SqlDataResType sqlDataResType,List<String> deleteSortIndex,SqlTable sqlTable,List<HashMap<String,Object>> sqlFieldAlert,
			String modifyTableName,SqlDataRes sqlDataRes,List<SqlField> newPrimarykey,List<SqlField> primarykey,List<String> deletePrimarykey,
			List<String> deleteList,List<String> newList,User userInfo){
		judgePrimary( newPrimarykey, primarykey, deletePrimarykey);
		//修改表名称，移除主键，删除栏位
		Map<String,Object> constraintForeign = constraintForeign( sqlDataRes, sqlDataResType, sqlTable, sqlFieldAlert, deletePrimarykey);
			//删除外键sql
			deleteForeginKey(constraintForeign,deletePrimarykey, newPrimarykey , sqlTable,  userInfo.getTenantId(),sqlDataResType,deleteList);
			//如果表名称变化添加相对应的外键
			addForeginKey(deletePrimarykey, newPrimarykey , sqlTable, modifyTableName, userInfo.getTenantId(),sqlDataResType,newList);
			deleteForeginKeyAlert(constraintForeign, sqlFieldAlert, deleteList, sqlTable, userInfo.getTenantId(), sqlDataResType);
			addForeginKeyAlert( sqlFieldAlert, newList, sqlTable, userInfo.getTenantId(), sqlDataResType);
			if(sqlFieldIdes != null && !sqlFieldIdes.isEmpty()){
				Map<String,Object> constraintForeignPoint = pointConstraintForeign( sqlDataRes, sqlDataResType, sqlTable, sqlFieldIdes);
				deleteForeginKeyFieldIdes(constraintForeignPoint,sqlFieldIdes, sqlTable, deleteList, modifyTableName, userInfo.getTenantId(), sqlDataResType);
				for (SqlField sqlField : sqlFieldIdes) {
					sqlTableForeignkeyDao.deleteByJoinTableJoinFieldTenantId(userInfo.getTenantId(), sqlField.getFieldSqlName(), sqlField.getTableId());
					sqlTableForeignkeyDao.deleteByMainTableMainFieldTenantId(userInfo.getTenantId(), sqlField.getFieldSqlName(), sqlField.getTableId());
				}
			}
			//查询主键，索引，外键约束名称
			constraintIndex( sqlDataResType, deleteSortIndex, sqlTable, modifyTableName, sqlDataRes);
			constraintPrimary( sqlDataResType, deletePrimarykey, sqlTable, modifyTableName, sqlDataRes);
	}
	public void deleteForeginKeyFieldIdes(Map<String,Object> map,List<SqlField> sqlFieldIdes,SqlTable sqlTable,List<String> deleteList,String modifyTableName,String tenantId,SqlDataResType sqlDataResType){
		if(sqlFieldIdes != null && !sqlFieldIdes.isEmpty()){
			for (SqlField sqlField : sqlFieldIdes) {
				if(!sqlField.getFieldKey().equals("1")){
					List<SqlTableForeignkey> sqlTableForeignkeyes= sqlTableForeignkeyDao.findForeignkeyInfo(tenantId, sqlField.getFieldSqlName(), sqlField.getTableId());
					if(sqlTableForeignkeyes != null && !sqlTableForeignkeyes.isEmpty() && map != null && !map.isEmpty()){
						for (SqlTableForeignkey sqlTableForeignkey : sqlTableForeignkeyes) {
								HashMap<String, Object> req = new HashMap<String, Object>();
								if(map.get(sqlTable.getTableSqlName()+sqlTableForeignkey.getJoinField()) != null && !map.get(sqlTable.getTableSqlName()+sqlTableForeignkey.getJoinField()).toString().equals("")){ req.put("joinField", map.get(sqlTable.getTableSqlName()+sqlTableForeignkey.getJoinField()));
									req.put("joinField", map.get(sqlTable.getTableSqlName()+sqlTableForeignkey.getJoinField()));
									req.put("sqName", sqlTable.getTableSqlName());
									//删除查询到的外键
									deleteList.add(Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).deleteForeignkey(req));
								}
//							}
						}
					}
				}
			}
		}
	}
    /**
     * 获取索引约束名称
     * @param sqlDataResType
     * @param deleteSortIndex
     * @param sqlTable
     * @param modifyTableName
     * @return
     */
    public void constraintIndex(SqlDataResType sqlDataResType,List<String> deleteSortIndex,SqlTable sqlTable,String modifyTableName,SqlDataRes sqlDataRes){
    	Map<String,Object> map = new HashMap<String,Object>();
    	List<String> deleteSort = new ArrayList<String>();
    	List<Map<String,Object>> indexInfoes = new ArrayList<Map<String,Object>>();
	    if(deleteSortIndex != null && !deleteSortIndex.isEmpty()){
	    	try{//dataResId
    			DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
    			if(sqlDataResType.getResType().equals("1")){
    				indexInfoes = sqlFieldDao.findIndexMysql(sqlTable.getTableSqlName());
    	    	}else if(sqlDataResType.getResType().equals("2")){//oracle
    	    		indexInfoes = sqlFieldDao.findIndexOracle(sqlTable.getTableSqlName());
    	    	}else if(sqlDataResType.getResType().equals("3")){//DB2
    	    		indexInfoes = sqlFieldDao.findIndexDb(sqlTable.getTableSqlName(),sqlDataRes.getTableSchema());
    	    	}else if(sqlDataResType.getResType().equals("5")){//sqlserver
    	    		indexInfoes = sqlFieldDao.findIndexSqlserver(sqlTable.getTableSqlName());
    	    	}
    		}catch(Exception e){
    			e.printStackTrace();
    			throw new RuntimeException("createTable is wrong");
    		}finally{
    			DynamicDataSourceContextHolder.clearDataSourceType();
    		}
	    	if(indexInfoes != null && !indexInfoes.isEmpty()){
	    		for (String string : deleteSortIndex) {
	    			for (Map<String, Object> map2 : indexInfoes) {
	    				if(map2.get("COLNAMES") != null && map2.get("INDNAME") != null && deleteSortIndex.contains(map2.get("COLNAMES").toString())){
	    					deleteSort.add(map2.get("INDNAME").toString());
//	    					deleteSortIndex.remove(map2.get("COLNAMES").toString());
//	    					deleteSortIndex.add(map2.get("INDNAME").toString());
	    				}
	    			}
	    		}
	    		deleteSortIndex.clear();
	    		if(deleteSort != null && !deleteSort.isEmpty()){
	    			deleteSortIndex.addAll(deleteSort);
	    		}
			}else{
				deleteSortIndex.clear(); 
			}
	    }	
    }
    /**
     * 获取主键约束名称
     * @param sqlDataResType
     * @param deletePrimarykey
     * @param sqlTable
     * @param modifyTableName
     * @return
     */
    public void constraintPrimary(SqlDataResType sqlDataResType,List<String> deletePrimarykey,SqlTable sqlTable,String modifyTableName,SqlDataRes sqlDataRes){
    	if(deletePrimarykey != null && !deletePrimarykey.isEmpty() && (sqlDataResType.getResType().equals("2") || sqlDataResType.getResType().equals("5"))){//sqlserver or oracle
    		String colName = "";
    		for (int i = 0; i < deletePrimarykey.size(); i++) {
				if(i==deletePrimarykey.size()-1){
					colName = "'"+deletePrimarykey.get(i)+"'";
				}else{
					colName = "'"+deletePrimarykey.get(i)+"',";
				}
			}
    		try{//dataResId
    			DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
    			List<String> getPrimaryInfoes = null;
    			if(sqlDataResType.getResType().equals("5")){
    				getPrimaryInfoes = sqlFieldDao.getPrimaryInfoes(sqlTable.getTableSqlName(),colName);
    			}else if(sqlDataResType.getResType().equals("2")){
    				getPrimaryInfoes = sqlFieldDao.getPrimaryOracles(sqlTable.getTableSqlName(),colName);
    			}
    			deletePrimarykey.clear();
    			if(getPrimaryInfoes != null && !getPrimaryInfoes.isEmpty()){
    				deletePrimarykey.addAll(getPrimaryInfoes);
    			}
    		}catch(Exception e){
    			e.printStackTrace();
    			throw new RuntimeException("createTable is wrong");
    		}finally{
    			DynamicDataSourceContextHolder.clearDataSourceType();
    		}
    	}
    }
    /**
     * 获取外键约束名称
     * @param sqlDataResType
     * @param deleteList
     * @param sqlTable
     * @param modifyTableName
     * @return
     */
    public Map<String,Object> constraintForeign(SqlDataRes sqlDataRes,SqlDataResType sqlDataResType,SqlTable sqlTable,List<HashMap<String,Object>> sqlFieldAlert,List<String> deletePrimarykey){
    	Map<String,Object> map = new HashMap<String,Object>();
    	List<Map<String,Object>> foreignInfoes = new ArrayList<Map<String,Object>>();
    	if((sqlFieldAlert != null && !sqlFieldAlert.isEmpty()) || (deletePrimarykey != null && !deletePrimarykey.isEmpty()) ){
    		try{//dataResId
    			DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
    			if(sqlDataResType.getResType().equals("1")){
        			foreignInfoes = sqlFieldDao.findForefignMysql(sqlTable.getTableSqlName(),sqlDataRes.getTableSchema()); //TABLE_SCHEMA
        		}else if(sqlDataResType.getResType().equals("2")){//oracle
        			foreignInfoes = sqlDataResDao.getForeignKeyInfoesOracle(sqlTable.getTableSqlName());
        		}else if(sqlDataResType.getResType().equals("3")){//DB2
        			foreignInfoes = sqlDataResDao.getForeignKeyInfoesDb(sqlTable.getTableSqlName());
        		}else if(sqlDataResType.getResType().equals("5")){//sqlserver
        			foreignInfoes = sqlFieldDao.findForefignSqlserver(sqlTable.getTableSqlName());
        		}
    		}catch(Exception e){
    			e.printStackTrace();
    			throw new RuntimeException("createTable is wrong");
    		}finally{
    			DynamicDataSourceContextHolder.clearDataSourceType();
    		}
    	}
    	if(foreignInfoes != null && !foreignInfoes.isEmpty()){
    		for (Map<String, Object> map2 : foreignInfoes) {
    			map.put(map2.get("TABLE_NAME").toString().trim()+map2.get("COLUMN_NAME").toString().trim(), map2.get("CONSTRAINT_NAME"));
			}
    	}
    	return map;
    }
    /**
     * 获取外键约束名称--表中某个字段外键约束
     * @param sqlDataResType
     * @param deleteList
     * @param sqlTable
     * @param modifyTableName
     * @return
     */
    public Map<String,Object> pointConstraintForeign(SqlDataRes sqlDataRes,SqlDataResType sqlDataResType,SqlTable sqlTable,List<SqlField> sqlFieldIdes){
    	Map<String,Object> map = new HashMap<String,Object>();
    	List<Map<String,Object>> foreignInfoes = new ArrayList<Map<String,Object>>();
    	if(sqlFieldIdes != null && !sqlFieldIdes.isEmpty() ){
    		try{//dataResId
    			DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
    			if(sqlDataResType.getResType().equals("1")){
    				foreignInfoes = sqlFieldDao.pointFindForefignMysql(sqlTable.getTableSqlName(),sqlDataRes.getTableSchema()); //TABLE_SCHEMA
    			}else if(sqlDataResType.getResType().equals("2")){//oracle
    				foreignInfoes = sqlDataResDao.pointForeignKeyInfoesOracle(sqlTable.getTableSqlName());
    			}else if(sqlDataResType.getResType().equals("3")){//DB2
    				foreignInfoes = sqlDataResDao.pointForeignKeyInfoesDb(sqlTable.getTableSqlName());
    			}else if(sqlDataResType.getResType().equals("5")){//sqlserver
    				foreignInfoes = sqlFieldDao.pointForefignSqlserver(sqlTable.getTableSqlName());
    			}
    		}catch(Exception e){
    			e.printStackTrace();
    			throw new RuntimeException("createTable is wrong");
    		}finally{
    			DynamicDataSourceContextHolder.clearDataSourceType();
    		}
    	}
    	if(foreignInfoes != null && !foreignInfoes.isEmpty()){
    		for (Map<String, Object> map2 : foreignInfoes) {
    			map.put(map2.get("TABLE_NAME").toString().trim()+map2.get("COLUMN_NAME").toString().trim(), map2.get("CONSTRAINT_NAME"));
    		}
    	}
    	return map;
    }
    
    public void deleteForeginKeyAlert(Map<String, Object> map,List<HashMap<String,Object>> sqlFieldAlert,List<String> deleteList,SqlTable sqlTable,String tenantId,SqlDataResType sqlDataResType){
    	if(sqlFieldAlert != null && !sqlFieldAlert.isEmpty()){
			for (HashMap<String, Object> hashMap : sqlFieldAlert) {
				if(hashMap.get("oldFieldKey").equals("1") && hashMap.get("fieldKey").equals("1") && !hashMap.get("oldFieldSqlName").equals(hashMap.get("fieldSqlName"))){
					List<SqlTableForeignkey> sqlTableForeignkeyes= sqlTableForeignkeyDao.findForeignkeyes(tenantId, hashMap.get("fieldSqlName").toString(), sqlTable.getTableId());
					if(sqlTableForeignkeyes != null && !sqlTableForeignkeyes.isEmpty()&& map!=null && !map.isEmpty()){
						for (SqlTableForeignkey sqlTableForeignkey : sqlTableForeignkeyes) {
							SqlTable sqlTables =sqlTableDao.findByTableId(sqlTableForeignkey.getJoinTable());
							if(sqlTables != null){
								HashMap<String, Object> req = new HashMap<String, Object>();
								if(map.get(sqlTables.getTableSqlName()+sqlTableForeignkey.getJoinField()) != null && !map.get(sqlTables.getTableSqlName()+sqlTableForeignkey.getJoinField()).toString().equals("")){
									req.put("joinField", map.get(sqlTables.getTableSqlName()+sqlTableForeignkey.getJoinField()));
									req.put("sqName", sqlTables.getTableSqlName());
									//删除查询到的外键
									deleteList.add(Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).deleteForeignkey(req));
								}
							}
						}
					}
				}
			}
		}
    }
    public void addForeginKeyAlert(List<HashMap<String,Object>> sqlFieldAlert,List<String> newList,SqlTable sqlTable,String tenantId,SqlDataResType sqlDataResType){
    	if(sqlFieldAlert != null && !sqlFieldAlert.isEmpty()){
    		for (HashMap<String, Object> hashMap : sqlFieldAlert) {
    			//db2做特殊处理
    			if(sqlDataResType.getResType().equals("3") && hashMap.get("oldFieldType").equals(hashMap.get("fieldType"))){
    				if(!hashMap.get("oldFieldSqlName").equals(hashMap.get("fieldSqlName")) || !(hashMap.get("oldFieldDigit")+"").equals(hashMap.get("fieldDigit")+"")
        					|| !hashMap.get("oldFieldLen").equals(hashMap.get("fieldLen"))){
    					List<String> addList = addForeignkey(sqlTable, tenantId, sqlDataResType, hashMap);
    					if(addList!= null && !addList.isEmpty()){
    						newList.addAll(addList);
    					}
    					List<String> addDbList = addDBForeignkey( sqlTable, tenantId, sqlDataResType, hashMap);
    					if(addDbList!= null && !addDbList.isEmpty()){
    						newList.addAll(addDbList);
    					}
    				}
    			}else{
    				if(hashMap.get("oldFieldKey").equals("1") && hashMap.get("fieldKey").equals("1") && !hashMap.get("oldFieldSqlName").equals(hashMap.get("fieldSqlName"))
        					&& hashMap.get("oldFieldType").equals(hashMap.get("fieldType"))){
    					List<String> addList = addForeignkey( sqlTable, tenantId, sqlDataResType, hashMap);
    					if(addList!= null && !addList.isEmpty()){
    						newList.addAll(addList);
    					}
    				}
    			}
    		}
    	}
    }
    
    public List<String> addDBForeignkey(SqlTable sqlTable,String tenantId,SqlDataResType sqlDataResType,HashMap<String, Object> hashMap){
    	List<String> newList = new ArrayList<String>();
    	List<SqlTableForeignkey> sqlTableForeignkeyes= sqlTableForeignkeyDao.findForeignkeyInfo(tenantId, hashMap.get("fieldSqlName").toString(), sqlTable.getTableId());
		if(sqlTableForeignkeyes != null && !sqlTableForeignkeyes.isEmpty()){
			for (SqlTableForeignkey sqlTableForeignkey : sqlTableForeignkeyes) {
				SqlTable sqlTables =sqlTableDao.findByTableId(sqlTableForeignkey.getMainTable());
				if(sqlTables != null){
					HashMap<String, Object> req = new HashMap<String, Object>();
					req.put("joinField", sqlTableForeignkey.getJoinField());
					req.put("negativeSqName", sqlTable.getTableSqlName());
					req.put("mainSqlName", sqlTables.getTableSqlName());
					req.put("mainField", sqlTableForeignkey.getMainField());
					//删除查询到的外键
					newList.add(Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).addForeignkey(req));
				}
			}
		}
		return newList;
    }
    public List<String> addForeignkey(SqlTable sqlTable,String tenantId,SqlDataResType sqlDataResType,HashMap<String, Object> hashMap){
    	List<String> newList = new ArrayList<String>();
    	List<SqlTableForeignkey> sqlTableForeignkeyes= sqlTableForeignkeyDao.findForeignkeyes(tenantId, hashMap.get("fieldSqlName").toString(), sqlTable.getTableId());
    	if(sqlTableForeignkeyes != null && !sqlTableForeignkeyes.isEmpty()){
    		for (SqlTableForeignkey sqlTableForeignkey : sqlTableForeignkeyes) {
    			SqlTable sqlTables =sqlTableDao.findByTableId(sqlTableForeignkey.getJoinTable());
    			if(sqlTables != null){
    				HashMap<String, Object> req = new HashMap<String, Object>();
    				req.put("joinField", sqlTableForeignkey.getJoinField());
    				req.put("negativeSqName", sqlTables.getTableSqlName());
    				req.put("mainSqlName", sqlTable.getTableSqlName());
    				req.put("mainField", hashMap.get("fieldSqlName"));
    				//删除查询到的外键
    				newList.add(Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).addForeignkey(req));
    			}
    		}
    	}
    	return newList;
    }
    
    public void judgePrimary(List<SqlField> newPrimarykey,List<SqlField> primarykey,List<String> deletePrimarykey){
    	if(newPrimarykey != null && !newPrimarykey.isEmpty()){//主键新增前全部删除主键
			if(primarykey != null && !primarykey.isEmpty()){
				for (SqlField sqlField : primarykey) {
					if(!deletePrimarykey.contains(sqlField.getFieldSqlName())){
						deletePrimarykey.add(sqlField.getFieldSqlName());
					}
				}
			}
		}else{
			if(deletePrimarykey != null && !deletePrimarykey.isEmpty()){
				if(primarykey != null && !primarykey.isEmpty()){
					newPrimarykey.addAll(primarykey);
					primarykey.clear();
				}
			}
		}
    }
    
    public void addForeginKey(List<String> deletePrimarykey,List<SqlField> newPrimarykey ,SqlTable sqlTable,String modifyTableName,String tenantId,SqlDataResType sqlDataResType,List<String> newList ){
    	if(deletePrimarykey != null && !deletePrimarykey.isEmpty() && !StringUtils.isBlank(modifyTableName)){
			//需要判断老的主键是否还是主键
			if(newPrimarykey != null && !newPrimarykey.isEmpty()){
				for (SqlField sqlField : newPrimarykey) {
					if(sqlField.getFieldKey().equals("1") && deletePrimarykey.contains(sqlField.getFieldSqlName())){
						for (String mainField : deletePrimarykey) {
				    		//根据表id，租户id，字段名称 查询外键表
				    		List<SqlTableForeignkey> sqlTableForeignkeyes= sqlTableForeignkeyDao.findForeignkeyes(tenantId, mainField, sqlTable.getTableId());
				    		if(sqlTableForeignkeyes != null && !sqlTableForeignkeyes.isEmpty()){
				    			for (SqlTableForeignkey sqlTableForeignkey : sqlTableForeignkeyes) {
				    				SqlTable sqlTables =sqlTableDao.findByTableId(sqlTableForeignkey.getJoinTable());
				    				if(sqlTables != null){
				    					HashMap<String, Object> req = new HashMap<String, Object>();
				    					req.put("joinField", sqlTableForeignkey.getJoinField());
				    					req.put("negativeSqName", sqlTables.getTableSqlName());
				    					req.put("mainSqlName", sqlTable.getTableSqlName());
				    					req.put("mainField", mainField);
				    					//删除查询到的外键
				    					newList.add(Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).addForeignkey(req));
				    				}
				    			}
				    		}
				    	}
					}
				}
			}
		}
    }
    public void deleteForeginKey(Map<String, Object> map,List<String> deletePrimarykey,List<SqlField> newPrimarykey ,SqlTable sqlTable,String tenantId,SqlDataResType sqlDataResType,List<String> deleteList ){
    	if(deletePrimarykey != null && !deletePrimarykey.isEmpty()){
	    	for (String mainField : deletePrimarykey) {
				//根据表id，租户id，字段名称 查询外键表
				List<SqlTableForeignkey> sqlTableForeignkeyes= sqlTableForeignkeyDao.findForeignkeyes(tenantId, mainField, sqlTable.getTableId());
				if(sqlTableForeignkeyes != null && !sqlTableForeignkeyes.isEmpty() && map!=null && !map.isEmpty()){
					for (SqlTableForeignkey sqlTableForeignkey : sqlTableForeignkeyes) {
						SqlTable sqlTables =sqlTableDao.findByTableId(sqlTableForeignkey.getJoinTable());
						if(sqlTables != null){
							HashMap<String, Object> req = new HashMap<String, Object>();
							if(map.get(sqlTables.getTableSqlName()+sqlTableForeignkey.getJoinField()) != null && !map.get(sqlTables.getTableSqlName()+sqlTableForeignkey.getJoinField()).toString().equals("")){
								req.put("joinField", map.get(sqlTables.getTableSqlName()+sqlTableForeignkey.getJoinField()));
								req.put("sqName", sqlTables.getTableSqlName());
								//删除查询到的外键
								deleteList.add(Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).deleteForeignkey(req));
							}
						}
					}
				}
			}
    	}
    }
    
    public HashMap<String, Object> getInfo(SqlField sqlField,SqlField sqlFieldOld){
    	HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("oldFieldSqlName", sqlFieldOld.getFieldSqlName());
		map.put("oldFieldDigit", sqlFieldOld.getFieldDigit());
		map.put("oldFieldLen", sqlFieldOld.getFieldLen());
		map.put("oldFieldType", sqlFieldOld.getFieldType());
		map.put("oldFieldKey", sqlFieldOld.getFieldKey());
		map.put("oldSortIndex", sqlFieldOld.getSortIndex());
		map.put("oldIsNull", sqlFieldOld.getIsNull());
		map.put("fieldSqlName", sqlField.getFieldSqlName());
		map.put("fieldDigit", sqlField.getFieldDigit());
		map.put("fieldLen", sqlField.getFieldLen());
		map.put("fieldType", sqlField.getFieldType());
		map.put("fieldKey", sqlField.getFieldKey());
		map.put("SortIndex", sqlField.getSortIndex());
		map.put("isNull", sqlField.getIsNull());
		map.put("fieldName", sqlField.getFieldName());
		return map;
    }
    
    public void getPrimarykey(SqlField sqlField,String modifyTableName,SqlField sqlFieldOld,List<SqlField> newPrimarykey,List<SqlField> primarykey,List<String> deletePrimarykey){
    	//比较主键  判断表名称是否修改
		if(!StringUtils.isBlank(modifyTableName)){
			if(sqlField.getFieldKey().equals("1")){
				newPrimarykey.add(sqlField);
			}
			if(sqlFieldOld.getFieldKey().equals("1")){
				deletePrimarykey.add(sqlFieldOld.getFieldSqlName());
			}
		}else{
			if(fieldBoolean(sqlField.getFieldKey(),sqlFieldOld.getFieldKey())){//主键变化全部 删除 重新添加
				if(sqlField.getFieldKey().equals("1")){
					newPrimarykey.add(sqlField);
				}
				//仅做判空使用
				if(sqlFieldOld.getFieldKey().equals("1")){
					deletePrimarykey.add(sqlField.getFieldSqlName());
				}
			}else{
				if(sqlField.getFieldKey().equals("1")){
					primarykey.add(sqlField);
				}
			}
		}
    }
    
    public void sortIndex(SqlDataResType sqlDataResType,SqlField sqlField,SqlField sqlFieldOld,List<String> deleteSortIndex,List<String> newSortIndex,String modifyTableName){
    	if((sqlDataResType.getResType().equals("2")||sqlDataResType.getResType().equals("3")) &&  !StringUtils.isBlank(modifyTableName)){
			if(sqlFieldOld.getSortIndex().equals("1") && !sqlFieldOld.getFieldKey().equals("1")){
				deleteSortIndex.add(sqlFieldOld.getFieldSqlName());
			}
			if(sqlField.getSortIndex().equals("1")&& !sqlField.getFieldKey().equals("1")){
				newSortIndex.add(sqlField.getFieldSqlName());
			}
		}else{
			if(fieldBoolean(sqlField.getSortIndex(),sqlFieldOld.getSortIndex())){
				if(sqlField.getSortIndex().equals("1")){
					//主键单独处理  
					if(!sqlField.getFieldKey().equals("1")){
						newSortIndex.add(sqlField.getFieldSqlName());
					}
				}else{
					if(!sqlFieldOld.getFieldKey().equals("1")){
						deleteSortIndex.add(sqlFieldOld.getFieldSqlName());
					}
				}
			}
		}
    }
    
	/**
	 * 比较数据源结构是否发送变化
	 * @param sqlField
	 * @param sqlFieldOld
	 * @return
	 */
	public boolean sqlBoolean(SqlField sqlField,SqlField sqlFieldOld,SqlTable sqlTable,String tenantId){
		if(fieldBoolean(sqlField.getFieldSqlName(),sqlFieldOld.getFieldSqlName()) || fieldBoolean(sqlField.getFieldType(),sqlFieldOld.getFieldType())
			|| fieldBoolean(sqlField.getFieldDigit(),sqlFieldOld.getFieldDigit()) || fieldBoolean(sqlField.getFieldLen(),sqlFieldOld.getFieldLen())||
			fieldBoolean(sqlField.getIsNull(),sqlFieldOld.getIsNull())){
			if(fieldBoolean(sqlField.getFieldSqlName(),sqlFieldOld.getFieldSqlName())){
				HashMap<String,Object> map = new HashMap<String,Object>();
				map.put("tenantId", tenantId);
				map.put("joinField", sqlFieldOld.getFieldSqlName());
				map.put("joinTable", sqlTable.getTableId());
				map.put("mainField", sqlFieldOld.getFieldSqlName());
				map.put("mainTable", sqlTable.getTableId());
				map.put("newJoinField", sqlField.getFieldSqlName());
				map.put("newMainField", sqlField.getFieldSqlName());
				sqlTableForeignkeyDao.updateByJoinTableJoinFieldTenantId(map);
				sqlTableForeignkeyDao.updateByMainTableMainFieldTenantId(map);
			}
			return true;
		}
		return false;
	}
	/**
	 * 判断两个实体是否相同
	 * @param obj
	 * @param objOld
	 * @return
	 */
	public boolean fieldBoolean(Object obj,Object objOld){
		if(objOld != null && !objOld.equals(obj)){
			return true;
		}else if(objOld == null && obj !=null && !String.valueOf(obj).equals("")){
			return true;
		}
		return false;
	}
	/**
	 * 删除数据源
	 * @param dataResId
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Status deleteByDataResId(String dataResId){
    	Status status = null;
    	User userInfo = CurrentUserUtils.getInstance().getUser();
    	//查询数据源是否在用，没有删除
    	int sqlTableLong = sqlTableDao.countByDataResIdAndTenantId(dataResId,userInfo.getTenantId());
    	if(sqlTableLong > 0){
    		status = new Status("删除数据失败，该数据正在使用，无法删除！",Dictionary.HttpStatus.INVALID_REQUEST.value );
        	return status;
    	}
    	sqlDataResDao.deleteByDataResId(dataResId);
    	sqlTableTypeMapper.deleteDataRes(userInfo.getTenantId(),dataResId);
    	status = new Status("删除数据成功",Dictionary.HttpStatus.OK.value );
    	return status;
    }
	/**
	 * 删除数据源类型
	 * @param dataResTypeId
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Status deleteBySqlDataResTypeId(String dataResTypeId){
    	Status status = null;
    	User userInfo = CurrentUserUtils.getInstance().getUser();
    	//查询数据源类型是否在用，没有删除
    	int sqlFieldLong =  sqlDataResDao.countByDataResTypeIdAndTenantId(dataResTypeId,userInfo.getTenantId());
    	if(sqlFieldLong > 0){
    		status = new Status("删除数据失败，该数据正在使用，无法删除！",Dictionary.HttpStatus.INVALID_REQUEST.value );
        	return status;
    	}
    	sqlDataResTypeDao.deleteByDataResTypeId(dataResTypeId);
    	status = new Status("删除数据成功",Dictionary.HttpStatus.OK.value );
    	return status;
    }
	/**
	 * 新建表
	 * @param sqlDataRes
	 * @param sqlDataResType
	 * @param sqlTable
	 * @param sqlFieldNew
	 * @param haMap
	 * @throws BaseException 
	 * @throws NumberFormatException 
	 */
	public Map<String,Object> createTable(SqlDataRes sqlDataRes,SqlDataResType sqlDataResType,SqlTable sqlTable,List<SqlField> sqlFieldNew,HashMap<String,Object> haMap,User userInfo){
		Map<String,Object> map = new HashMap<String,Object>();
		String code = "000000"; 
		String result = "true";
		if(sqlDataResType.getResType() != null ){
			String sql = "" ;
			try {
				sql = Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).createTable(haMap, sqlTable, sqlFieldNew);
			}  catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				deleteAll(sqlTable);
			}
			if(sql != null && !sql.equals("") && sqlFieldNew != null && !sqlFieldNew.isEmpty()){
				sql= sql+" "+savePartitionSql(sqlTable);
				//执行建表语句
				result = tableToolService.executeDdl(sqlDataRes, sql, sqlDataResType);
				if(!result.equals("true")){
					//查是否存在表，存在的话获取表信息，不存在就删除系统中（sqltable）表信息结束----先判断外键，如果外键设计的表不存在系统，系统自动录入新表信息
					code = rollbackCreate( sqlDataRes, sqlDataResType, sqlTable, userInfo);
					map.put("code", code);
					map.put("result", result);
					map.put("sqlDataResId", sqlDataRes.getDataResId());
					return map;
				}
				//保存分区信息
				try{
					savePartitionInfo(sqlTable);
				}catch (Exception e) {
					String dropTble="DROP TABLE "+sqlTable.getTableSqlName();
					result = tableToolService.executeDdl(sqlDataRes, dropTble, sqlDataResType);
		        	if(!result.equals("true")){
						//刷新数据源
		        		code="111111";
					}else{
						code="222222";
						deleteAll(sqlTable);
					}
				}
				//非mysql 描述，索引信息添加
				if(sqlDataResType.getResType() != null && !sqlDataResType.getResType().equals("1")){
					map = modifyDescription(sqlDataRes,sqlDataResType,sqlTable, sqlFieldNew);
					return map;
				}
			}
		}
		map.put("code", code);
		map.put("sqlDataResId", sqlDataRes.getDataResId());
		map.put("result", result);
		return map;
	}
	/**
	 * 编辑新建表
	 * @param sqlDataRes
	 * @param sqlDataResType
	 * @param sqlTable
	 * @param sqlFieldNew
	 * @param haMap
	 * @throws BaseException
	 * @throws NumberFormatException
	 */
	public Map<String,Object> editCreateTable(SqlDataRes sqlDataRes,SqlDataResType sqlDataResType,SqlTable sqlTable,List<SqlField> sqlFieldNew,HashMap<String,Object> haMap){
		Map<String,Object> map = new HashMap<String,Object>();
		String code = "000000";
		String result = "true";
		if(sqlDataResType.getResType() != null ){
			String sql = "" ;
			try {
				sql = Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).createTable(haMap, sqlTable, sqlFieldNew);
			}  catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				map.put("code", "333333");
				map.put("result", "false");
				return map;
			}
			if(sql != null && !sql.equals("") && sqlFieldNew != null && !sqlFieldNew.isEmpty()){
				sql= sql+" "+savePartitionSql(sqlTable);
				//执行建表语句
				result = tableToolService.executeDdl(sqlDataRes, sql, sqlDataResType);
				if(!result.equals("true")){
					String dropTble="DROP TABLE "+sqlTable.getTableSqlName();
					result = tableToolService.executeDdl(sqlDataRes, dropTble, sqlDataResType);
					map.put("code", "444444");
					map.put("result", "false");
					map.put("sqlDataResId", sqlDataRes.getDataResId());
					return map;
				}
				//非mysql 描述，索引信息添加
				if(sqlDataResType.getResType() != null && !sqlDataResType.getResType().equals("1")){
					map = modifyDescription(sqlDataRes,sqlDataResType,sqlTable, sqlFieldNew);
					return map;
				}
			}
		}
		map.put("code", code);
		map.put("sqlDataResId", sqlDataRes.getDataResId());
		map.put("result", result);
		return map;
	}
	/**----------------------------------------------------------------------
	 * 分表，分库保存
	 * @param sqlTable
	 * @return
	 */
	public Map<String,Object> saveVertical(SqlDataRes sqlDataRes,SqlDataResType sqlDataResType,SqlTable sqlTable,List<SqlField> sqlFieldNew,HashMap<String,Object> haMap,User userInfo){
		Map<String,Object> resultMap = createTable( sqlDataRes, sqlDataResType, sqlTable, sqlFieldNew, haMap,userInfo);
		if(resultMap.get("code").equals("000000")){
//			User userInfo = CurrentUserUtils.getInstance().getUser();
			String tableIdOld = sqlTable.getTableId();
			List<SqlShape> sqlShapes = sqlTable.getSqlShape();
			if(sqlShapes != null && !sqlShapes.isEmpty()){
				for (SqlShape sqlShape : sqlShapes) {
					if(sqlShape.getShapeType().equals("2")){//分表
						return saveTable( sqlShape, tableIdOld, userInfo, sqlDataRes, sqlDataResType, sqlTable, sqlFieldNew,haMap);
					}else if(sqlShape.getShapeType().equals("3")){//分库
						SqlSubTreasury sqlSubTreasury = sqlShape.getSqlSubTreasury();
						if(sqlSubTreasury != null ){
							return saveSubTreasury( sqlShape, tableIdOld, userInfo, sqlSubTreasury, sqlTable, sqlFieldNew, haMap);
						}
					}
				}
			}
		}
		return resultMap;
	}
	
	/**
	 * 分表
	 * @param sqlShape
	 * @param tableIdOld
	 * @param userInfo
	 * @param sqlDataRes
	 * @param sqlDataResType
	 * @param sqlTable
	 * @param sqlFieldNew
	 * @param haMap
	 * @return
	 */
	public Map<String,Object> saveTable(SqlShape sqlShape,String tableIdOld,User userInfo,SqlDataRes sqlDataRes,SqlDataResType sqlDataResType,SqlTable sqlTable,List<SqlField> sqlFieldNew,HashMap<String,Object> haMap){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("code", "000000");
		resultMap.put("result", "true");
		SqlSubTable sqlSubTable = sqlShape.getSqlSubTable();
		if(sqlSubTable != null ){
			sqlShape.setTableId(tableIdOld);
			sqlShape.setTenantId(userInfo.getTenantId());
			sqlShape.setCreateBy(userInfo.getLoginId());
			sqlShapeMapper.saveSqlShape(sqlShape);
			sqlSubTable.setShapeId(sqlShape.getShapeId());
			sqlSubTable.setTenantId(userInfo.getTenantId());
			sqlSubTable.setCreateBy(userInfo.getLoginId());
			sqlSubTableMapper.save(sqlSubTable);
		}
		return resultMap;
	}
	
	/**
	 * 分库保存
	 * @param sqlShape
	 * @param tableIdOld
	 * @param userInfo
	 * @param sqlSubTreasuryes
	 * @param sqlTable
	 * @param sqlFieldNew
	 * @param haMap
	 * @return
	 */
	public Map<String,Object> saveSubTreasury(SqlShape sqlShape,String tableIdOld,User userInfo,SqlSubTreasury sqlSubTreasury,SqlTable sqlTable,List<SqlField> sqlFieldNew,HashMap<String,Object> haMap){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("code", "000000");
		resultMap.put("result", "true");
//		List<String> subTreasuryIdList = new ArrayList<String>();
		Map<String,String> map = new HashMap<String,String>();
		List<SqlSubTreasuryDivision> sqlSubTreasuryDivisiones = sqlSubTreasury.getSqlSubTreasuryDivision();
		if(sqlSubTreasuryDivisiones != null && !sqlSubTreasuryDivisiones.isEmpty()){
			sqlShape.setTableId(tableIdOld);
			sqlShape.setCreateBy(userInfo.getLoginId());
			sqlShape.setTenantId(userInfo.getTenantId());
			sqlShapeMapper.saveSqlShape(sqlShape);
			//保存分库信息 
			sqlSubTreasury.setCreateBy(userInfo.getLoginId());
			sqlSubTreasury.setShapeId(sqlShape.getShapeId());
			sqlSubTreasury.setTenantId(userInfo.getTenantId());
			sqlSubTreasuryMapper.save(sqlSubTreasury);
//			subTreasuryIdList.add(sqlSubTreasury.getSubTreasuryId());
			for (SqlSubTreasuryDivision sqlSubTreasuryDivision : sqlSubTreasuryDivisiones) {
				if(!sqlTable.getDataResId().equals(sqlSubTreasuryDivision.getDataResId())){
					//查询数据源与数据源类型信息
					SqlDataRes sqlDataResNew = sqlDataResDao.findByDataResIdAndDataStatus(sqlSubTreasuryDivision.getDataResId(),"1");//修改添加启用状态
					SqlDataResType sqlDataResTypeNew = null;
					if(sqlDataResNew != null){
						sqlDataResTypeNew = sqlDataResTypeDao.findByDataResTypeIdAndDataStutas(sqlDataResNew.getDataResTypeId(),"1");//修改添加启用状态
						if(sqlDataResTypeNew == null){
							rolleSubTreasury( map, sqlTable);
							throw new RuntimeException("sqlDataResType is wrong");
						}
					}else{
						rolleSubTreasury( map, sqlTable);
						throw new RuntimeException("sqlDataRes is wrong");
					}
					//保存表信息
					String tableNewId = UUID.randomUUID().toString().replace("-", "");
					String dataResTdOld = sqlTable.getDataResId();
					sqlTable.setDataResId(sqlSubTreasuryDivision.getDataResId());
					saveSqlTable( tableIdOld, tableNewId, sqlTable, userInfo);
					sqlTable.setTableId(tableNewId);
					//建表运行
					Map<String,Object> sqlDataResMap = createTable( sqlDataResNew, sqlDataResTypeNew, sqlTable, sqlFieldNew, haMap,userInfo);
					if(sqlDataResMap.get("code").equals("000000")){
						map.put(tableNewId, sqlSubTreasuryDivision.getDataResId());
						sqlSubTreasuryDivision.setTenantId(userInfo.getTenantId());
						sqlSubTreasuryDivision.setCreateBy(userInfo.getLoginId());
						sqlSubTreasuryDivision.setSubTreasuryId(sqlSubTreasury.getSubTreasuryId());
						sqlSubTreasuryDivisionMapper.save(sqlSubTreasuryDivision);	
					}else{
						//如果发生异常 全部回滚--tableIdList
						rolleSubTreasury( map, sqlTable);
						return sqlDataResMap;
					}
					sqlTable.setDataResId(dataResTdOld);
					sqlTable.setTableId(tableIdOld);
				}else{
					map.put(sqlTable.getTableId(), sqlSubTreasuryDivision.getDataResId());
					sqlSubTreasuryDivision.setTenantId(userInfo.getTenantId());
					sqlSubTreasuryDivision.setCreateBy(userInfo.getLoginId());
					sqlSubTreasuryDivision.setSubTreasuryId(sqlSubTreasury.getSubTreasuryId());
					sqlSubTreasuryDivisionMapper.save(sqlSubTreasuryDivision);	
				}
			}
		}
		return resultMap;
	}
	
	public void rolleShape(Map<String,String> tableIdMap,SqlDataRes sqlDataRes,SqlDataResType sqlDataResType,SqlTable sqlTable){
		if(tableIdMap != null && !tableIdMap.isEmpty()){
			for (String	tableId	:tableIdMap.keySet()) {
				sqlTable.setTableId(tableId);
				String dropTble="DROP TABLE "+tableIdMap.get(tableId);
				tableToolService.executeDdl(sqlDataRes, dropTble, sqlDataResType);
				deleteAll(sqlTable);
			}
		}
	}
	public void rolleSubTreasury(Map<String,String> map,SqlTable sqlTable){
		//map.put(tableNewId, sqlSubTreasury.getDataResId());
		if(map != null && !map.isEmpty()){
			for (String	tableId	:map.keySet()) {
				//查询数据源与数据源类型信息
				SqlDataRes sqlDataRes = sqlDataResDao.findByDataResIdAndDataStatus(map.get(tableId),"1");//修改添加启用状态
				SqlDataResType sqlDataResType = null;
				if(sqlDataRes != null){
					sqlDataResType = sqlDataResTypeDao.findByDataResTypeIdAndDataStutas(sqlDataRes.getDataResTypeId(),"1");//修改添加启用状态
					if(sqlDataResType == null){
						throw new RuntimeException("sqlDataResType is wrong");
					}
				}else{
					throw new RuntimeException("sqlDataRes is wrong");
				}
				sqlTable.setTableId(tableId);
				String dropTble="DROP TABLE "+sqlTable.getTableSqlName();
				tableToolService.executeDdl(sqlDataRes, dropTble, sqlDataResType);
				deleteAll(sqlTable);
			}
		}
//		if(subTreasuryIdList != null && !subTreasuryIdList.isEmpty()){
//			for (String subTreasuryId : subTreasuryIdList) {
//				sqlSubTreasuryMapper.delete(subTreasuryId);
//			}
//		}
	}
	
	public void saveSqlTable(String tableIdOld,String tableNewId,SqlTable sqlTable,User userInfo){
		sqlTableDao.save(tableIdOld, tableNewId, sqlTable.getDataResId(),sqlTable.getTableSqlName());
		sqlFieldDao.saveSqlField(tableIdOld, userInfo.getTenantId(), tableNewId);
	}
	
	/**
	 * 分区的保存
	 * @param sqlTable
	 */
	public void savePartitionInfo(SqlTable sqlTable){
		User userInfo = CurrentUserUtils.getInstance().getUser();
		List<SqlShape> sqlShapes = sqlTable.getSqlShape();
		if(sqlShapes != null && !sqlShapes.isEmpty()){
			for (SqlShape sqlShape : sqlShapes) {
				if(sqlShape.getShapeType().equals("1")){
					sqlShape.setTableId(sqlTable.getTableId());
					sqlShape.setTenantId(userInfo.getTenantId());
					sqlShape.setCreateBy(userInfo.getLoginId());
					if(StringUtils.isBlank(sqlShape.getShapeId())){
						sqlShapeMapper.saveSqlShape(sqlShape);
					}else{
						sqlShapeMapper.update(sqlShape);
					}
				}
			}
		}
	}
		
	public String rollbackCreate(SqlDataRes sqlDataRes,SqlDataResType sqlDataResType,SqlTable sqlTable,User userInfo){
		String code = "222222"; 
		//查询表是否存在
//		User userInfo = CurrentUserUtils.getInstance().getUser();
		Map<String,Object> tableName = getTabelName( sqlDataRes, sqlDataResType, sqlTable.getTableSqlName());
		if(tableName != null && !tableName.isEmpty()){
			//查询系统是否存在该表（除了当前id）
			SqlTable sqlTableNew = sqlTableDao.findTable(sqlDataRes.getDataResId(), userInfo.getTenantId(), sqlTable.getTableId(), sqlTable.getTableSqlName());
			if(sqlTableNew == null){
				//刷新数据源code:000000成功；111111该表名称已经存在请刷新数据源；222222失败
				code = "111111"; 
			}
		}
		//--不存在删除系统中表信息（表名，表字段，表外键）
		deleteAll(sqlTable);
		return code;
	}
	
	/***
	 * 检查表是否已经存在
	 * @param sqlDataRes
	 * @param sqlDataResType
	 * @param sqlTable
	 * @return
	 */
	public Map<String,Object> getTabelName(SqlDataRes sqlDataRes,SqlDataResType sqlDataResType,String tableSqlName){
		Map<String,Object> tableName = null;
		try{
			DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
			if(sqlDataResType.getResType().equals("1")){//mysql
	    		tableName = sqlDataResDao.getNameMysql(sqlDataRes.getTableSchema(),tableSqlName);
	    	}else if(sqlDataResType.getResType().equals("2")){//oracle
	    		tableName = sqlDataResDao.getNameOracle(sqlDataRes.getTableSchema(),tableSqlName);
	    	}else if(sqlDataResType.getResType().equals("3")){//DB2
	    		tableName = sqlDataResDao.getNameDb(tableSqlName);
	    	}else if(sqlDataResType.getResType().equals("5")){//sqlserver
	    		tableName = sqlDataResDao.getNameSqlserver(tableSqlName);
	    	}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DynamicDataSourceContextHolder.clearDataSourceType();
		}
		return tableName;
	}
	/**
	 * 删除表信息--回滚调用
	 * @param sqlTable
	 */
	public void deleteAll(SqlTable sqlTable){
		User userInfo = CurrentUserUtils.getInstance().getUser();
		//删除表信息
		sqlTableDao.deleteByTableIdAndTenantId(sqlTable.getTableId(), userInfo.getTenantId());
		//删除field与外键信息
		sqlFieldDao.deleteByTableIdAndTenantId(sqlTable.getTableId(), userInfo.getTenantId());
		sqlTableForeignkeyDao.deleteByMainTableAndTenantId(sqlTable.getTableId(), userInfo.getTenantId());
		sqlTableForeignkeyDao.deleteByJoinTableTenantId(sqlTable.getTableId(),userInfo.getTenantId());
		deletePartition( sqlTable.getTableId(), userInfo);
	}
	
	/**
	 * 新建表后添加描述等信息（非mysql）
	 * @param sqlDataRes
	 * @param sqlDataResType
	 * @param sqlTable
	 * @param sqlFieldNew
	 * @throws BaseException 
	 */
	public Map<String,Object> modifyDescription(SqlDataRes sqlDataRes,SqlDataResType sqlDataResType,SqlTable sqlTable,List<SqlField> sqlFieldNew){
		Map<String,Object> map = new HashMap<String,Object>();
		String code = "000000";
		List<String> modifyList  = Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).modifyDescription(sqlTable,sqlFieldNew);
		String result = "true";
		if(modifyList != null && !modifyList.isEmpty()){
			result = tableToolService.executeDdles(sqlDataRes,sqlDataResType,modifyList);
			if(!result.equals("true")){
				String dropTble="DROP TABLE "+sqlTable.getTableSqlName();
				result = tableToolService.executeDdl(sqlDataRes, dropTble, sqlDataResType);
	        	if(!result.equals("true")){
					//刷新数据源
	        		code="111111";
				}else{
					code="222222";
					deleteAll(sqlTable);
				}
			}
		}
		map.put("code", code);
		map.put("sqlDataResId", sqlDataRes.getDataResId());
		map.put("result", result);
		return map;
	}
	/**
	 * 修改表结构信息
	 * @param sqlDataRes
	 * @param sqlDataResType
	 * @param sqlTable
	 * @param modifyTableName
	 * @param sqlFieldNew
	 * @param sqlFieldAlert
	 * @param sqlFieldIdes
	 * @param deleteSortIndex
	 * @param newSortIndex
	 * @param haMap
	 * @throws BaseException 
	 */
	public Map<String,Object> modifyTable(SqlDataRes sqlDataRes,SqlDataResType sqlDataResType,SqlTable sqlTable,String modifyTableName,List<SqlField> sqlFieldNew,List<HashMap<String,Object>> sqlFieldAlert,
			List<SqlField> sqlFieldIdes,List<String> deleteSortIndex,List<String> newSortIndex,HashMap<String,Object> haMap,List<SqlField> newPrimarykey,List<String> deletePrimarykey,
			List<SqlField> primarykey,List<String> deleteList,List<String> newList,User userInfo,SqlTable sqlTableOld){
		//
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<String> modifyList = new ArrayList<String>();
		if(deleteList != null && !deleteList.isEmpty()){
			modifyList.addAll(deleteList);
		}
		//1.修改表名
		if(!StringUtils.isBlank(modifyTableName)){
			modifyList.add(Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).modifyName(sqlTable.getTableSqlName(),modifyTableName));
		}
		//2.删除索引
		modifyList.addAll(Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).deleteIndex(deleteSortIndex,modifyTableName, sqlDataRes.getTableSchema(), sqlTable.getTableSqlName()));
		//删除主键
		if(deletePrimarykey != null && !deletePrimarykey.isEmpty()){
			modifyList.addAll(Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).deletePrimarykey(deletePrimarykey,haMap,modifyTableName,sqlTable.getTableSqlName()));
		}
		//3.删除字段alter table PLT_DIVIDE_LOG DROP COLUMN citycode;
		if(sqlDataResType.getResType().equals("5")){
			if(sqlFieldIdes != null && !sqlFieldIdes.isEmpty()){
				for (SqlField sqlField : sqlFieldIdes) {//sqlField.get("fieldSqlName")
					String sql = "select b.name from syscolumns a,sysobjects b where a.id=object_id('"+sqlTable.getTableSqlName()+"') and b.id=a.cdefault AND a.name = '"+sqlField.getFieldSqlName()+"' and b.name like 'DF%'";
					String constraintName = tableToolService.getUserGroupInfo(sqlDataRes,sql,sqlDataResType);
					if(constraintName != null && !constraintName.equals("")){
						//alter table sql_table_sqlsert drop constraint @CurrentName
						modifyList.add("alter table "+sqlTable.getTableSqlName()+" drop constraint "+constraintName);
					}
				}
			}
		}
		modifyList.addAll(Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).dropField(sqlFieldIdes,sqlTable.getTableSqlName()));
		//4.新增字段alter table tablename add (column datatype [default value][null/not null],….);
		modifyList.addAll(Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).addField(sqlFieldNew,haMap,sqlTable.getTableSqlName()));
		//5.修改字段名称与类型
		if(sqlDataResType.getResType().equals("5")){
			//移除字段约束
			if(sqlFieldAlert != null && !sqlFieldAlert.isEmpty()){
				for (HashMap<String,Object> sqlField : sqlFieldAlert) {//sqlField.get("fieldSqlName")
					String sql = "select b.name from syscolumns a,sysobjects b where a.id=object_id('"+sqlTable.getTableSqlName()+"') and b.id=a.cdefault AND a.name = '"+sqlField.get("oldFieldSqlName")+"' and b.name like 'DF%'";
					String constraintName = tableToolService.getUserGroupInfo(sqlDataRes,sql,sqlDataResType);
					if(constraintName != null && !constraintName.equals("")){
						//alter table sql_table_sqlsert drop constraint @CurrentName
						modifyList.add("alter table "+sqlTable.getTableSqlName()+" drop constraint "+constraintName);
					}
				}
			}
		}
		//
		modifyList.addAll(Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).modifyField(sqlFieldAlert,haMap,sqlTable.getTableSqlName()));
		//新增主键
		modifyList.addAll(Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).createPrimarykey(newPrimarykey,haMap,sqlTable.getTableSqlName(),primarykey));
		//6.新增索引（增删改索引）
		modifyList.addAll(Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).addIndex(newSortIndex,sqlTable.getTableSqlName()));
		if(newList != null && !newList.isEmpty()){
			modifyList.addAll(newList);
		}
		LOG.info("将要执行的sql语句是："+JSON.toJSONString(modifyList));
		String result = "true";
		String code = "000000";
		if(modifyList != null && !modifyList.isEmpty()){
			result = tableToolService.executeDdles(sqlDataRes,sqlDataResType,modifyList);
			if(!result.equals("true")){
				//获取最新表信息
				rollbackModify( sqlDataRes, sqlDataResType, sqlTable, userInfo, sqlTableOld);
				code= "222222";
			}
//			savePartition(sqlTable, sqlDataResType,sqlDataRes);
		}
		resultMap.put("code", code);
		resultMap.put("result", result);
		return resultMap;
	}
	/**
	 * 
	 * @param sqlDataRes
	 * @param sqlDataResType
	 * @param sqlTable
	 * @return
	 */
	public void rollbackModify(SqlDataRes sqlDataRes,SqlDataResType sqlDataResType,SqlTable sqlTable,User userInfo,SqlTable sqlTableOld){
		//查询表是否存在
		Map<String,Object> tableName = getTabelName( sqlDataRes, sqlDataResType, sqlTable.getTableSqlName());
		if(tableName != null && !tableName.isEmpty()){
			rollbackFiles( sqlDataRes, sqlDataResType, sqlTable, userInfo);
		}else{
			Map<String,Object> tableName1 = getTabelName( sqlDataRes, sqlDataResType, sqlTableOld.getTableSqlName());
			 if(tableName1 != null && !tableName1.isEmpty()){
				 sqlTable.setTableSqlName(sqlTableOld.getTableSqlName());
				 sqlTable.setTableName(sqlTableOld.getTableName());
				 sqlTable.setTableResume(sqlTableOld.getTableResume());
				 sqlTable.setSortId(sqlTableOld.getSortId());
				 sqlTableDao.update(sqlTable);
				 rollbackFiles( sqlDataRes, sqlDataResType, sqlTable, userInfo);
				}
		}
	}
	
	
	public void rollbackFiles(SqlDataRes sqlDataRes,SqlDataResType sqlDataResType,SqlTable sqlTable,User userInfo){
		//查询field集合
		List<SqlField> sqlFieldes = sqlFieldDao.findByTableIdTenantId(sqlTable.getTableId(), userInfo.getTenantId());
		//查询表结构信息--添加table与field表
		List<Map<String,Object>> tableInfoes = null;
		if(sqlDataResType.getResType().equals("1")){//mysql
			try{
				DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
				tableInfoes = sqlDataResDao.getTableInfoMysql(sqlTable.getTableSqlName(),sqlDataRes.getTableSchema());
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				DynamicDataSourceContextHolder.clearDataSourceType();
			}
			if(tableInfoes != null && !tableInfoes.isEmpty()){
				fieldMysql( tableInfoes, sqlTable, userInfo,sqlDataResType,sqlDataRes,sqlFieldes);
			}
    	}else if(sqlDataResType.getResType().equals("2")){//oracle
    		try{
    			DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
    			tableInfoes = sqlDataResDao.getTableInfoOracle(sqlTable.getTableSqlName());
    		}catch(Exception e){
    			e.printStackTrace();
    		}finally{
    			DynamicDataSourceContextHolder.clearDataSourceType();
    		}
    		if(tableInfoes != null && !tableInfoes.isEmpty()){
    			fieldCreate( tableInfoes, sqlTable, userInfo, sqlDataResType,sqlDataRes,sqlFieldes);
			}
    	}else if(sqlDataResType.getResType().equals("3")){//DB2
    		try{
    			DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
    			tableInfoes = sqlDataResDao.getTableInfoDb(sqlTable.getTableSqlName());
    		}catch(Exception e){
    			e.printStackTrace();
    		}finally{
    			DynamicDataSourceContextHolder.clearDataSourceType();
    		}
    		if(tableInfoes != null && !tableInfoes.isEmpty()){
    			fieldCreate( tableInfoes, sqlTable, userInfo, sqlDataResType,sqlDataRes,sqlFieldes);
			}
    	}else if(sqlDataResType.getResType().equals("5")){//sqlserver
    		try{
    			DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
//    			tableInfoes = sqlDataResDao.getTableInfoesSqlserver(table.get("TABLE_NAME").toString());
    			tableInfoes = sqlDataResDao.getTableInfoSqlserver(sqlTable.getTableSqlName());
    		}catch(Exception e){
    			e.printStackTrace();
    		}finally{
    			DynamicDataSourceContextHolder.clearDataSourceType();
    		}
    		if(tableInfoes != null && !tableInfoes.isEmpty()){
    			fieldCreate( tableInfoes, sqlTable, userInfo, sqlDataResType,sqlDataRes,sqlFieldes);
			}
    	}
		createForegin( userInfo, sqlDataResType, sqlDataRes,sqlTable);
	}
	
	
	
	public void createForegin(User userInfo,SqlDataResType sqlDataResType,SqlDataRes sqlDataRes,SqlTable sqlTable){
    	//添加外键表
    	List<Map<String,Object>> keyInfoes = null;
    	 if(sqlDataResType.getResType().equals("2")){//oracle
 			try{
 				DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
 				keyInfoes = sqlDataResDao.getForeignKeyInfoesOracle(sqlTable.getTableSqlName());
 			}catch(Exception e){
 				e.printStackTrace();
 			}finally{
 				DynamicDataSourceContextHolder.clearDataSourceType();
 			}
 			implementation( keyInfoes, userInfo, sqlDataResType, sqlDataRes,sqlTable);
     	}else if(sqlDataResType.getResType().equals("3")){//DB2
 			try{
 				DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
 				keyInfoes = sqlDataResDao.getForeignKeyInfoesDb(sqlTable.getTableSqlName());
 			}catch(Exception e){
 				e.printStackTrace();
 			}finally{
 				DynamicDataSourceContextHolder.clearDataSourceType();
 			}
 			implementation( keyInfoes, userInfo, sqlDataResType, sqlDataRes,sqlTable);
     	}else if(sqlDataResType.getResType().equals("1")){//msyql
     		try{
    			DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
    			keyInfoes = sqlDataResDao.getForeignKeyInfoesMysql(sqlDataRes.getTableSchema());
    		}catch(Exception e){
    			e.printStackTrace();
    		}finally{
    			DynamicDataSourceContextHolder.clearDataSourceType();
    		}
    		implementation( keyInfoes, userInfo, sqlDataResType, sqlDataRes,sqlTable);
    	}else if(sqlDataResType.getResType().equals("5")){//sqlserver
    		try{
    			DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
    			keyInfoes = sqlDataResDao.getForeignKeyInfoesSqlserver();
    		}catch(Exception e){
    			e.printStackTrace();
    		}finally{
    			DynamicDataSourceContextHolder.clearDataSourceType();
    		}
    		implementation( keyInfoes, userInfo, sqlDataResType, sqlDataRes,sqlTable);
    	}
    }
	 public void implementation(List<Map<String,Object>> keyInfoes,User userInfo,SqlDataResType sqlDataResType,SqlDataRes sqlDataRes,SqlTable sqlTable){
			if(keyInfoes != null && !keyInfoes.isEmpty()){
				List<SqlTableForeignkey> mainTable = sqlTableForeignkeyDao.findJoinForeignkey(sqlTable.getTableId(), userInfo.getTenantId());
				List<SqlTableForeignkey> joinTable = sqlTableForeignkeyDao.findForeignkey(sqlTable.getTableId(), userInfo.getTenantId());
				for (Map<String, Object> map2 : keyInfoes) {
					SqlTableForeignkey sqlTableForeignkey = new SqlTableForeignkey();
					//租户id 数据源id 表类型id 表sql名称 -- 
					boolean judge = false;
					if(map2.get("TABLE_NAME").toString().equals(sqlTable.getTableSqlName())){
						SqlTable sqlTableInfo = sqlTableDao.findTableInfo(sqlDataRes.getDataResId(),userInfo.getTenantId(),map2.get("REFERENCED_TABLE_NAME").toString());
						if(sqlTableInfo != null){
							sqlTableForeignkey.setJoinField(map2.get("COLUMN_NAME").toString());
							sqlTableForeignkey.setJoinTable(sqlTable.getTableId());
							sqlTableForeignkey.setMainField(map2.get("REFERENCED_COLUMN_NAME").toString());
							sqlTableForeignkey.setMainTable(sqlTableInfo.getTableId());
							sqlTableForeignkey.setTenantId(userInfo.getTenantId());
							if(joinTable != null && !joinTable.isEmpty()){
								for (SqlTableForeignkey sqlTableForeignkey2 : joinTable) {
									if(sqlTableForeignkey2.getJoinField().equals(map2.get("COLUMN_NAME").toString())){
										judge=true;
										sqlTableForeignkey.setForeignKeyId(sqlTableForeignkey2.getForeignKeyId());
										joinTable.remove(sqlTableForeignkey2);
									}
								}
							}
						}
		    		}
					if(map2.get("REFERENCED_TABLE_NAME").toString().equals(sqlTable.getTableSqlName())){
						SqlTable sqlTableInfo = sqlTableDao.findTableInfo(sqlDataRes.getDataResId(),userInfo.getTenantId(),map2.get("TABLE_NAME").toString());
						if(sqlTableInfo != null){
							sqlTableForeignkey.setJoinField(map2.get("COLUMN_NAME").toString());
							sqlTableForeignkey.setJoinTable(sqlTableInfo.getTableId());
							sqlTableForeignkey.setMainField(map2.get("REFERENCED_COLUMN_NAME").toString());
							sqlTableForeignkey.setMainTable(sqlTable.getTableId());
							sqlTableForeignkey.setTenantId(userInfo.getTenantId());
							if(mainTable != null && !mainTable.isEmpty()){
								for (SqlTableForeignkey sqlTableForeignkey2 : mainTable) {
									if(sqlTableForeignkey2.getMainField().equals(map2.get("REFERENCED_COLUMN_NAME").toString())){
										judge=true;
										sqlTableForeignkey.setForeignKeyId(sqlTableForeignkey2.getForeignKeyId());
										joinTable.remove(sqlTableForeignkey2);
									}
								}
							}
						}
					}
					if(judge){
						sqlTableForeignkeyDao.update(sqlTableForeignkey);
					}else{
						sqlTableForeignkey.setLineStart("left");
						sqlTableForeignkey.setLineEnd("right");
						sqlTableForeignkeyDao.save(sqlTableForeignkey);
					}
				}
			}
	    }
	public void fieldMysql(List<Map<String,Object>> tableInfoes,SqlTable sqlTable,User userInfo,SqlDataResType sqlDataResType,SqlDataRes sqlDataRes,List<SqlField> sqlFieldes){
		//查询主外键信息--更新field与添加外键表
    	List<String> keyInfo = null;
    	try{
			DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
			keyInfo = sqlDataResDao.getPrimaryKeyMysql(sqlTable.getTableSqlName(), sqlDataRes.getTableSchema());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DynamicDataSourceContextHolder.clearDataSourceType();
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
				sqlFieldDao.update(sqlField);
			}else{
				sqlFieldDao.save(sqlField);
			}
		}
		if(sqlFieldes !=null && !sqlFieldes.isEmpty()){
			for (SqlField sqlField2 : sqlFieldes) {
				sqlFieldDao.delete(sqlField2.getFieldId());
			}
		}
    }
	 public void fieldCreate(List<Map<String,Object>> tableInfoes,SqlTable sqlTable,User userInfo,SqlDataResType sqlDataResType,SqlDataRes sqlDataRes,List<SqlField> sqlFieldes){
	    	//查询主外键信息--更新field与添加外键表
	    	List<String> keyInfo = null;
	    	try{
				DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
				if(sqlDataResType.getResType().equals("2")){//oracle
		    		keyInfo = sqlDataResDao.getPrimaryKeyOracle(sqlTable.getTableSqlName());
		    	}else if(sqlDataResType.getResType().equals("3")){//DB2
		    		keyInfo = sqlDataResDao.getPrimaryKeyDb(sqlTable.getTableSqlName());
		    	}else if(sqlDataResType.getResType().equals("5")){//sqlserver
		    		keyInfo = sqlDataResDao.getPrimaryKeySqlserver(sqlTable.getTableSqlName());
		    	}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				DynamicDataSourceContextHolder.clearDataSourceType();
			}
			//////
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
				sqlField.setFieldType(getType(map.get("DATA_TYPE").toString(),sqlDataResType.getResType()));
				sqlField.setCreateBy(userInfo.getLoginId());
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
					sqlFieldDao.update(sqlField);
				}else{
					sqlFieldDao.save(sqlField);
				}
			}
			if(sqlFieldes !=null && !sqlFieldes.isEmpty()){
				for (SqlField sqlField2 : sqlFieldes) {
					sqlFieldDao.delete(sqlField2.getFieldId());
				}
			}
	    }
	
	/**
	 * 外键添加
	 * @param req
	 * @param mainSqlTable
	 * @return
	 * @throws BaseException 
	 * @throws NumberFormatException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String,Object> createForeignkey(HashMap<String, Object> req,SqlTable mainSqlTable,SqlTableForeignkey sqlTableForeignkey){
		sqlTableForeignkey.setMainTable(req.get("mainTable").toString());
		sqlTableForeignkey.setMainField(req.get("mainField").toString());
		sqlTableForeignkey.setJoinTable(req.get("joinTable").toString());
		sqlTableForeignkey.setJoinField(req.get("joinField").toString());
		sqlTableForeignkey.setForeignY(mainSqlTable.getTableY());
		sqlTableForeignkey.setForeignX(mainSqlTable.getTableX());
		if(req.get("lineStart") != null){
			sqlTableForeignkey.setLineStart(req.get("lineStart").toString());
		}
		if(req.get("lineEnd") != null){
			sqlTableForeignkey.setLineEnd(req.get("lineEnd").toString());
		}
		//获取主键
		List<SqlField> mainFielles = getKey(mainSqlTable);
		List<String> modifyList = new ArrayList<String>();
		//查询数据源与数据源类型信息
		User userInfo = CurrentUserUtils.getInstance().getUser();
		sqlTableForeignkey.setTenantId(userInfo.getTenantId());
		SqlDataResType sqlDataResType = null;
		SqlDataRes sqlDataRes = sqlDataResDao.findByDataResIdAndDataStatus(mainSqlTable.getDataResId(),"1");//修改添加启用状态
		if(sqlDataRes != null){
			sqlDataResType = sqlDataResTypeDao.findByDataResTypeIdAndDataStutas(sqlDataRes.getDataResTypeId(),"1");//修改添加启用状态
			if(sqlDataResType == null){
				throw new RuntimeException("sqlDataResType is wrong");
			}
		}else{
			throw new RuntimeException("sqlDataRes is wrong");
		}
		if(mainFielles != null && !mainFielles.isEmpty() ){
			List<String> fieldList = new ArrayList<String>();
			for (SqlField sqlField : mainFielles) {
				fieldList.add(sqlField.getFieldSqlName());
			}
			if(fieldList.contains(req.get("mainField").toString())){
				//添加外键
				modifyList.add(Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).addForeignkey(req));
			}
			if(modifyList != null && !modifyList.isEmpty()){
				if(!tableToolService.executeDdles(sqlDataRes,sqlDataResType,modifyList).equals("true")){
					throw new RuntimeException();
				}
			}
			if(StringUtils.isNotBlank(sqlTableForeignkey.getForeignKeyId())){
				sqlTableForeignkeyDao.update(sqlTableForeignkey);
			}else{
				sqlTableForeignkeyDao.save(sqlTableForeignkey);
			}
		}
		return req;
	}
	public List<SqlField> getKey(SqlTable sqlTable){
		List<SqlField> fielles = new ArrayList<SqlField>();
		List<SqlField> sqlFieldes = sqlTable.getSqlFieldes();
		if(sqlFieldes != null && sqlFieldes.size() >0){
			for (SqlField sqlField : sqlFieldes) {
				if(sqlField.getFieldKey().equals("1")){
					fielles.add(sqlField);
				}
			}
		}
		return fielles;
	}
	public List<SqlField> getField(SqlTable sqlTable,String mainField){
		List<SqlField> fielles = new ArrayList<SqlField>();
		List<SqlField> sqlFieldes = sqlTable.getSqlFieldes();
		if(sqlFieldes != null && sqlFieldes.size() >0){
			for (SqlField sqlField : sqlFieldes) {
				if(sqlField.getFieldSqlName().equals(mainField)){
					fielles.add(sqlField);
				}
			}
		}
		return fielles;
	}
	public HashMap<String,Object> typeMap(SqlDataResType sqlDataResType){
		//查询表类型码表，转换为map<"1","varchar">
		HashMap<String,Object> haMap = new HashMap<String,Object>();
		haMap.put("1", "float");
		haMap.put("2", "varchar");
		haMap.put("3", "datetime");
		haMap.put("4", "longtext");
		haMap.put("5", "blob");
		haMap.put("6", "int");
		haMap.put("7", "bigint");
		haMap.put("9", "tinyint");
		if(sqlDataResType.getResType().equals("2")){
			haMap.put("2", "varchar2");
			haMap.put("3", "date");
			haMap.put("4", "blob");
			haMap.put("6", "number");
			haMap.put("7", "number");
			haMap.put("9", "number");
		}else if(sqlDataResType.getResType().equals("5")){
			haMap.put("1", "real");
			haMap.put("4", "nvarchar(max)");
			haMap.put("5", "varbinary(max)");
			haMap.put("9", "bit");
		}
		// --- 4：DB2 ---
		else if(sqlDataResType.getResType().equals("3")){
			haMap.put("1", "decimal");
			haMap.put("3", "timestamp");
			haMap.put("4", "clob");
			haMap.put("9", "boolean");
		}
		return haMap;
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
	
	/**
	 * 分区
	 * @param sqlTable
	 * @return
	 */
	public String savePartitionSql(SqlTable sqlTable){
		String sql = "";
		List<SqlShape> sqlShapes = sqlTable.getSqlShape();
		if(sqlShapes != null && !sqlShapes.isEmpty()){
			for (SqlShape sqlShape : sqlShapes) {
				if(sqlShape.getShapeType().equals("1") && sqlShape.getState().equals("0")){
					sql = sqlShape.getPartitionValue();
				}
			}
		}
		return sql;
	}
	
	
	
}
