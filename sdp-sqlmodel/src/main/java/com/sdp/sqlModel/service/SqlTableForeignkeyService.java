package com.sdp.sqlModel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdp.common.BaseException;
import com.sdp.common.CurrentUserUtils;
import com.sdp.common.constant.Dictionary;
import com.sdp.common.entity.Status;
import com.sdp.frame.web.entity.user.User;
import com.sdp.sqlModel.dataSource.DynamicDataSourceContextHolder;
import com.sdp.sqlModel.entity.SqlDataRes;
import com.sdp.sqlModel.entity.SqlDataResType;
import com.sdp.sqlModel.entity.SqlField;
import com.sdp.sqlModel.entity.SqlTable;
import com.sdp.sqlModel.entity.SqlTableForeignkey;
import com.sdp.sqlModel.function.Sql_switcher;
import com.sdp.sqlModel.mapper.SqlDataResMapper;
import com.sdp.sqlModel.mapper.SqlDataResTypeMapper;
import com.sdp.sqlModel.mapper.SqlFieldMapper;
import com.sdp.sqlModel.mapper.SqlTableForeignkeyMapper;
import com.sdp.sqlModel.mapper.SqlTableMapper;

@Service
public class SqlTableForeignkeyService {
	
	@Autowired SqlTableForeignkeyMapper sqlTableForeignkeyDao;
	@Autowired SqlFieldMapper sqlFieldDao;
	@Autowired SqlTableMapper sqlTableDao;
	@Autowired SqlDataResMapper sqlDataResDao;
	@Autowired SqlDataResTypeMapper sqlDataResTypeDao;
	@Autowired
    private TableToolService tableToolService;
	
	// --- 删除外键表数据 ---
//	@Transactional(rollbackFor = Exception.class)
	public Status deleteSqlTableForeignkey(String foreignKeyId) throws BaseException {
		Status status;
		HashMap<String, Object> req = new HashMap<String, Object>();
		User userInfo = CurrentUserUtils.getInstance().getUser();
		List<String> modifyList = new ArrayList<String>();
		SqlDataResType sqlDataResType = null;
		SqlDataRes sqlDataRes = null;
		if(foreignKeyId != null){
			SqlTableForeignkey sqlTableForeignkey = sqlTableForeignkeyDao.findOne(foreignKeyId);
			//查询表名称
			SqlTable sqlTable = sqlTableDao.findByTableId(sqlTableForeignkey.getJoinTable());
			if(sqlTable != null ){
				sqlDataRes = sqlDataResDao.findByDataResIdAndDataStatus(sqlTable.getDataResId(),"1");//修改添加启用状态
				if(sqlDataRes != null){
					sqlDataResType = sqlDataResTypeDao.findByDataResTypeIdAndDataStutas(sqlDataRes.getDataResTypeId(),"1");//修改添加启用状态
					if(sqlDataResType == null){
						throw new BaseException("sqlDataResType is wrong");
					}
				}else{
					throw new BaseException("sqlDataRes is wrong");
				}
				req.put("sqName", sqlTable.getTableSqlName());
				req.put("joinField", sqlTableForeignkey.getJoinField());
			}else{
				status = new Status("参数有误,删除数据失败", Dictionary.HttpStatus.INVALID_REQUEST.value);
				return status;
			}
			Map<String,Object> map = pointConstraintForeign( sqlDataRes, sqlDataResType, sqlTable);
			sqlTableForeignkeyDao.deleteByForeignKeyIdAndTenantId(foreignKeyId,userInfo.getTenantId());
			//调用工具类删除
			if(map != null && !map.isEmpty() && map.get(sqlTable.getTableSqlName()+sqlTableForeignkey.getJoinField()) != null && !map.get(sqlTable.getTableSqlName()+sqlTableForeignkey.getJoinField()).toString().equals("")){ req.put("joinField", map.get(sqlTable.getTableSqlName()+sqlTableForeignkey.getJoinField()));
				req.put("joinField", map.get(sqlTable.getTableSqlName()+sqlTableForeignkey.getJoinField()));
			}else{
				status = new Status("参数有误,删除数据失败", Dictionary.HttpStatus.INVALID_REQUEST.value);
				return status;
			}
			modifyList.add(Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).deleteForeignkey(req));
			if(modifyList != null && !modifyList.isEmpty()){
				if(!tableToolService.executeDdles(sqlDataRes,sqlDataResType,modifyList).equals("true")){
					throw new BaseException("drop index is wrong");
				}
			}
			status = new Status("删除外键表数据成功", Dictionary.HttpStatus.OK.value);
		}else{
			status = new Status("参数有误,删除数据失败", Dictionary.HttpStatus.INVALID_REQUEST.value);
		}
		return status;
	}
	 public Map<String,Object> pointConstraintForeign(SqlDataRes sqlDataRes,SqlDataResType sqlDataResType,SqlTable sqlTable){
	    	Map<String,Object> map = new HashMap<String,Object>();
	    	List<Map<String,Object>> foreignInfoes = new ArrayList<Map<String,Object>>();
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
	    	if(foreignInfoes != null && !foreignInfoes.isEmpty()){
	    		for (Map<String, Object> map2 : foreignInfoes) {
	    			map.put(map2.get("TABLE_NAME").toString().trim()+map2.get("COLUMN_NAME").toString().trim(), map2.get("CONSTRAINT_NAME"));
	    		}
	    	}
	    	return map;
	    }
	public List<SqlTableForeignkey> findSqlTableForeignkeyes(){
		User userInfo = CurrentUserUtils.getInstance().getUser();
		return sqlTableForeignkeyDao.findSqlTableForeignkeyes(userInfo.getTenantId());
	}
}
