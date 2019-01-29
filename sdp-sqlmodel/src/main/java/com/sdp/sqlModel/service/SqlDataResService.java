package com.sdp.sqlModel.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.web.entity.user.User;
import com.sdp.sqlModel.dataSource.DynamicDataSourceContextHolder;
import com.sdp.sqlModel.entity.SqlDataRes;
import com.sdp.sqlModel.entity.SqlDataResType;
import com.sdp.sqlModel.entity.SqlTable;
import com.sdp.sqlModel.entity.SqlTableType;
import com.sdp.sqlModel.mapper.SqlDataResMapper;
import com.sdp.sqlModel.mapper.SqlDataResTypeMapper;
import com.sdp.sqlModel.mapper.SqlFieldMapper;
import com.sdp.sqlModel.mapper.SqlTableForeignkeyMapper;
import com.sdp.sqlModel.mapper.SqlTableMapper;
import com.sdp.sqlModel.mapper.SqlTableTypeMapper;
import com.sdp.util.DateUtils;


/**
 * 数据源类型Service
 *
 * @author 
 */
@Service
public class SqlDataResService {
	 /**
     * 输出日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(SqlDataResService.class);
    
    @Autowired
    private SqlDataResMapper sqlDataResDao;
    
    @Autowired
    private SqlDataResTypeMapper sqlDataResTypeMapper;
    
    
    @Autowired
    private SqlTableForeignkeyMapper sqlTableForeignkeyMapper;
    
    @Autowired
    private SqlTableTypeMapper sqlTableTypeMapper;
    
    @Autowired
    private SqlTableMapper sqlTableMapper;
    
    @Autowired SqlTableTypeMapper sqlTableTypeDao;
    
    @Autowired SqlFieldMapper sqlFieldMapper;
    
    
    public void save(SqlDataRes sqlDataRes){
    	User userInfo = CurrentUserUtils.getInstance().getUser();
    	sqlDataRes.setTenantId(userInfo.getTenantId());
    	SqlDataResType sqlDataResType = sqlDataResTypeMapper.findByDataResTypeId(sqlDataRes.getDataResTypeId());
    	//添加表类型
    	SqlTableType sqlTableType = new SqlTableType();
    	boolean IsAvailable = false;
    	if(StringUtils.isNotBlank(sqlDataRes.getDataResId())){
    		//判断schemaName，ip，用户名与密码是否发生变化，发生变化删除表，表字段，外键等信息重新获取表信息
    		SqlDataRes sqlDataResOld = sqlDataResDao.findByDataResId(sqlDataRes.getDataResId());
    		if(sqlBoolean( sqlDataRes, sqlDataResOld,sqlDataResType)){
    			//查询表id-删除field与外键使用
    			List<String> sqlTableIdes= sqlTableMapper.findByDataResIdTenantId(sqlDataRes.getDataResId(), userInfo.getTenantId());
    			//删除表信息
    			sqlTableMapper.deleteByDataResIdAndTenantId(sqlDataRes.getDataResId(), userInfo.getTenantId());
    			//删除field与外键信息
    			if(sqlTableIdes != null && !sqlTableIdes.isEmpty()){
    				for (String string : sqlTableIdes) {
    					sqlFieldMapper.deleteByTableIdAndTenantId(string, userInfo.getTenantId());
    					sqlTableForeignkeyMapper.deleteByMainTableAndTenantId(string, userInfo.getTenantId());
    					sqlTableForeignkeyMapper.deleteByJoinTableTenantId(string,userInfo.getTenantId());
					}
    			}
    			IsAvailable = true;
    		}
    		sqlDataResDao.update(sqlDataRes);
    	}else{
    		sqlDataResDao.save(sqlDataRes);
    		IsAvailable = true;
    	}
    	if(IsAvailable){
    		//查询是否存在sqlTableType
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("dataResId", sqlDataRes.getDataResId());
			map.put("tableTypeName", sqlDataRes.getUsername()+"-"+sqlDataRes.getUsername());
			map.put("tenantId", userInfo.getTenantId());
			sqlTableType = sqlTableTypeDao.findTabeInfo(map);
			//创建sqlTableType
			if(sqlTableType == null){
				sqlTableType = saveSqlTableType(userInfo, sqlDataRes);
			}
    		getTableInfo( userInfo, sqlDataRes, sqlTableType, sqlDataResType);
    	}
    }
    
	public SqlTableType saveSqlTableType(User userInfo,SqlDataRes sqlDataRes){
		SqlTableType sqlTableType = new SqlTableType();
		String createBy = userInfo.getLoginId();
		String tenantId = userInfo.getTenantId();
		Date current = DateUtils.getCurrentDate();
    	sqlTableType.setTenantId(tenantId);
		sqlTableType.setCreateDate(current);
		sqlTableType.setCreateBy(createBy);
		sqlTableType.setParentId(sqlDataRes.getDataResId());
		sqlTableType.setTableTypeName(sqlDataRes.getUsername()+"-"+sqlDataRes.getUsername());//tableSchema-tableSchema
		sqlTableType.setDataResId(sqlDataRes.getDataResId());
    	sqlTableTypeDao.save(sqlTableType);
		return sqlTableType;
    }
    
    public void getTableInfo(User userInfo,SqlDataRes sqlDataRes,SqlTableType sqlTableType,SqlDataResType sqlDataResType){
    	//获取tableSchema下所有表名称
    	List<Map<String,Object>> tableNames = null;
    	try{
			DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
			if(sqlDataResType.getResType().equals("1")){//mysql
	    		tableNames = sqlDataResDao.getTableNameMysql(sqlDataRes.getTableSchema());
	    	}else if(sqlDataResType.getResType().equals("2")){//oracle
	    		tableNames = sqlDataResDao.getTableTableOracle();
	    	}else if(sqlDataResType.getResType().equals("3")){//DB2
	    		tableNames = sqlDataResDao.getTableDb();
	    	}else if(sqlDataResType.getResType().equals("5")){//sqlserver
	    		tableNames = sqlDataResDao.getTableSqlserver();
	    	}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DynamicDataSourceContextHolder.clearDataSourceType();
		}
    	if(tableNames != null && !tableNames.isEmpty()){
    		ExecutorService tableInfoThreadPool = Executors.newFixedThreadPool(1);
    		TableInformation tableInformation = new TableInformation();
    		tableInformation.setTableNames(tableNames);
    		tableInformation.setSqlDataRes(sqlDataRes);
    		tableInformation.setSqlDataResType(sqlDataResType);
    		tableInformation.setSqlTableType(sqlTableType);
    		tableInformation.setUserInfo(userInfo);
    		tableInfoThreadPool.execute(tableInformation);
    		tableInfoThreadPool.shutdown();
    	}
    }
    
    /**
	 * 比较数据源结构是否发送变化
	 * @param sqlField
	 * @param sqlFieldOld
	 * @return
	 */
	public boolean sqlBoolean(SqlDataRes sqlDataRes,SqlDataRes sqlDataResOld,SqlDataResType sqlDataResType){
		if(fieldBoolean(sqlDataRes.getDataResUrl(),sqlDataResOld.getDataResUrl()) || fieldBoolean(sqlDataRes.getPassword(),sqlDataResOld.getPassword())
			|| fieldBoolean(sqlDataRes.getUsername(), sqlDataResOld.getUsername())){
			return true;
		}
		if(sqlDataResType.getResType().equals("1") && fieldBoolean(sqlDataRes.getTableSchema(), sqlDataResOld.getTableSchema())){
			return true;
		}
		return false;
	}
	/**
	 * 判断两个实体是否相同，不同为true
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
    public List<SqlDataRes> findByTenantId(){
    	User userInfo = CurrentUserUtils.getInstance().getUser();
		return sqlDataResDao.findByTenantId(userInfo.getTenantId());
    }
    
    public List<SqlDataRes> findByTenantIdAndDataResTypeId(String dataResTypeId){
    	User userInfo = CurrentUserUtils.getInstance().getUser();
    	return sqlDataResDao.findByTenantIdAndDataResTypeId(userInfo.getTenantId(),dataResTypeId);
    }
        
    public Map<String,Object> findDataResId(String dataResId){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    	User userInfo = CurrentUserUtils.getInstance().getUser();
    	SqlDataRes sqlDataRes = sqlDataResDao.findByDataResId(dataResId);
    	List<SqlTableType> sqlTableTypes= sqlTableTypeMapper.selectTablesByDtatResId(userInfo.getTenantId(),dataResId);
    	if(sqlTableTypes != null && !sqlTableTypes.isEmpty()){
    		for (SqlTableType sqlTableType : sqlTableTypes) {
    			List<SqlTable> sqlTables= sqlTableMapper.findByTenantIdAndTableTypeId(userInfo.getTenantId(),sqlTableType.getTableTypeId());
    			Map<String,Object> map = new HashMap<String,Object>();
    			map.put("type", sqlTableType);
    			map.put("table", sqlTables);
    			list.add(map);
			}
    	}
    	resultMap.put("tableList", list);
    	resultMap.put("dataResList", sqlTableMapper.findByTenantIdAndTableTypeId(userInfo.getTenantId(),dataResId));
    	resultMap.put("sqlDataRes", sqlDataRes);
		return resultMap;
    }
    
    public Map<String,Object> refreshTable(String dataResId){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
//    	User userInfo = CurrentUserUtils.getInstance().getUser();
    	User userInfo = new User();
    	userInfo.setTenantId("tenant_system");
    	userInfo.setLoginId("admin");
    	SqlDataRes sqlDataRes = sqlDataResDao.findByDataResId(dataResId);
    	SqlDataResType sqlDataResType = sqlDataResTypeMapper.findByDataResTypeId(sqlDataRes.getDataResTypeId());
    	//添加表类型
    	SqlTableType sqlTableType = new SqlTableType();
    	Map<String,Object> map = new HashMap<String,Object>();
		map.put("dataResId", sqlDataRes.getDataResId());
		map.put("tableTypeName", sqlDataRes.getUsername()+"-"+sqlDataRes.getUsername());
		map.put("tenantId", userInfo.getTenantId());
		sqlTableType = sqlTableTypeDao.findTabeInfo(map);
		//创建sqlTableType
		if(sqlTableType == null){
			sqlTableType = saveSqlTableType(userInfo, sqlDataRes);
		}
		updateTableInfo( userInfo, sqlDataRes, sqlTableType, sqlDataResType);
		resultMap.put("code", "000000");
		return resultMap;
    }
    
    public void updateTableInfo(User userInfo,SqlDataRes sqlDataRes,SqlTableType sqlTableType,SqlDataResType sqlDataResType){
    	//获取tableSchema下所有表名称
    	List<Map<String,Object>> tableNames = null;
    	try{
			DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
			if(sqlDataResType.getResType().equals("1")){//mysql
	    		tableNames = sqlDataResDao.getTableNameMysql(sqlDataRes.getTableSchema());
	    	}else if(sqlDataResType.getResType().equals("2")){//oracle
	    		tableNames = sqlDataResDao.getTableTableOracle();
	    	}else if(sqlDataResType.getResType().equals("3")){//DB2
	    		tableNames = sqlDataResDao.getTableDb();
	    	}else if(sqlDataResType.getResType().equals("5")){//sqlserver
	    		tableNames = sqlDataResDao.getTableSqlserver();
	    	}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DynamicDataSourceContextHolder.clearDataSourceType();
		}
    	if(tableNames != null && !tableNames.isEmpty()){
    		ExecutorService tableInfoThreadPool = Executors.newFixedThreadPool(1);
    		UpdateTableInformation updateTableInformation = new UpdateTableInformation();
    		updateTableInformation.setTableNames(tableNames);
    		updateTableInformation.setSqlDataRes(sqlDataRes);
    		updateTableInformation.setSqlDataResType(sqlDataResType);
    		updateTableInformation.setSqlTableType(sqlTableType);
    		updateTableInformation.setUserInfo(userInfo);
    		tableInfoThreadPool.execute(updateTableInformation);
    		tableInfoThreadPool.shutdown();
    	}
    }
    
}
