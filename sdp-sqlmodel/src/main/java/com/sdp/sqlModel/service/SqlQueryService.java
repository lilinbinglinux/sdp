package com.sdp.sqlModel.service;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.sdp.common.BaseException;
import com.sdp.common.CurrentUserUtils;
import com.sdp.common.constant.Dictionary;
import com.sdp.common.entity.Status;
import com.sdp.frame.web.entity.user.User;
import com.sdp.sqlModel.dataSource.DynamicDataSourceContextHolder;
import com.sdp.sqlModel.entity.ProDataApi;
import com.sdp.sqlModel.entity.ProModel;
import com.sdp.sqlModel.entity.SqlDataRes;
import com.sdp.sqlModel.entity.SqlDataResType;
import com.sdp.sqlModel.entity.SqlField;
import com.sdp.sqlModel.entity.SqlShape;
import com.sdp.sqlModel.entity.SqlSubTable;
import com.sdp.sqlModel.entity.SqlSubTreasury;
import com.sdp.sqlModel.entity.SqlSubTreasuryDivision;
import com.sdp.sqlModel.entity.SqlTable;
import com.sdp.sqlModel.function.Sql_switcher;
import com.sdp.sqlModel.mapper.ProDataApiMapper;
import com.sdp.sqlModel.mapper.ProModelMapper;
import com.sdp.sqlModel.mapper.SqlDataResMapper;
import com.sdp.sqlModel.mapper.SqlDataResTypeMapper;
import com.sdp.sqlModel.mapper.SqlFieldMapper;
import com.sdp.sqlModel.mapper.SqlQueryMapper;
import com.sdp.sqlModel.mapper.SqlShapeMapper;
import com.sdp.sqlModel.mapper.SqlSubTableMapper;
import com.sdp.sqlModel.mapper.SqlSubTreasuryDivisionMapper;
import com.sdp.sqlModel.mapper.SqlSubTreasuryMapper;
import com.sdp.sqlModel.mapper.SqlTableMapper;
import com.sdp.util.CheckKeyWord;
import com.sdp.util.DateDemo;
import com.sdp.util.DateUtils;
import com.sdp.util.FreemarkerUtil;

import freemarker.template.Template;



/**
 * 数据库建模Service
 *
 * @author 
 */
@Service
public class SqlQueryService {

    @Autowired private SqlDataResTypeMapper sqlDataResTypeDao;
    @Autowired private SqlDataResMapper sqlDataResDao;
    @Autowired private SqlQueryMapper sqlQueryMapper;
    @Autowired private SqlTableMapper sqlTableMapper;
    @Autowired private SqlFieldMapper sqlFieldMapper;
    @Autowired private TableToolService tableToolService;
    @Autowired private ProDataApiMapper proDataApiMapper;
    @Autowired private ProModelMapper proModelMapper;
    @Autowired private CreateXmlFormat createXmlFormat;
    @Autowired private AnalysisXmlFormat analysisXmlFormat;
    @Autowired private SqlShapeMapper sqlShapeMapper;
    @Autowired private SqlSubTableMapper sqlSubTableMapper;
    @Autowired private SqlSubTreasuryMapper sqlSubTreasuryMapper;
	@Autowired SqlSubTreasuryDivisionMapper sqlSubTreasuryDivisionMapper;
    private ConcurrentHashMap<String, HashMap<String, HashMap<String, Object>>> tenantLabeles = new ConcurrentHashMap<>();
    
	 /**
     * 输出日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(SqlQueryService.class);
    
    /**
     * 单表查询
     * @param resp
     * @param tableName
     * @param conditiones
     * @param groupes
     * @param havinges
     * @param sortes
     * @param dbflag
     * @return
     * @throws BaseException 
     */
    public List<Map<String, Object>> pageQuery(HashMap<String,Object> req) throws BaseException{
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	HashMap<String,Object> resp = (HashMap<String, Object>) req.get("resp");
		String tableName =  (String) req.get("tableName");
		HashMap<String,Object> conditiones =  (HashMap<String, Object>) req.get("conditiones");
		List<String> sortes =  (List<String>) req.get("sortes");
//		int dbflag =  Integer.parseInt(req.get("dbflag").toString());
		SqlDataRes sqlDataRes = sqlDataResDao.findByDataResId(req.get("dataResId").toString());
		SqlDataResType sqlDataResType = sqlDataResTypeDao.findByDataResTypeId(sqlDataRes.getDataResTypeId());
		String pageSql = Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).pageModel(req, resp, tableName, conditiones, sortes, Integer.parseInt(sqlDataResType.getResType()));
		LOG.info("分页sql："+pageSql);
		try{//dataResId
			DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
			list=sqlQueryMapper.pageQuery(pageSql);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DynamicDataSourceContextHolder.clearDataSourceType();
		}
    	return list;
    }
    
    public String routeInfo(SqlTable sqlTable,User userInfo){
    	String fieldSqlName = "";
    	if(sqlTable != null){
    		List<SqlShape> sqlShapes = sqlShapeMapper.findByTableId(sqlTable.getTableId(), userInfo.getTenantId());
    		if(sqlShapes != null && !sqlShapes.isEmpty()){
    			for (SqlShape sqlShape : sqlShapes) {
    				if(sqlShape.getShapeType().equals("2")){//分表--0
    					SqlSubTable sqlSubTable = sqlSubTableMapper.findSqlSubTable(sqlShape.getShapeId(),  userInfo.getTenantId());
    					if(sqlSubTable != null){
    						fieldSqlName = sqlSubTable.getFieldSqlName();
    					}
    				}else if(sqlShape.getShapeType().equals("3")){//分库--1
    					SqlSubTreasury sqlSubTreasury = sqlSubTreasuryMapper.findSqlSubTreasury(sqlShape.getShapeId(),  userInfo.getTenantId());
    					if(sqlSubTreasury != null){
    						fieldSqlName = sqlSubTreasury.getFieldSqlName();
    					}
    				}
    			}
    		}
    	}
    	return fieldSqlName;
    }
    
    /**
     * 单条添加接口
     * @throws BaseException 
     */
    public Status insertSingle(Map<String,Object> fieldInfoes,String tableName, String dataResId,String value) throws BaseException{
    	Status status;
    	User userInfo = CurrentUserUtils.getInstance().getUser();
    	userInfo  = new User();
    	userInfo.setTenantId("tenant_system");
    	SqlTable sqlTable = tableSqlName(tableName,dataResId);
    	if(sqlTable == null){
    		status = new Status("未找到表信息！",Dictionary.HttpStatus.NOT_FOUND.value );
			return status;
    	}
    	String fieldName = routeInfo(sqlTable, userInfo);//获取分表字段
    	if(fieldName != null && !fieldName.equals("")){
			value = fieldInfoes.get(fieldName).toString();
    	}
    	Map<String, Object> map = isRoute(sqlTable, value);
    	SqlDataRes sqlDataRes = null;
    	SqlDataResType sqlDataResType = null;
    	if(map != null && map.get("sqlDataRes") != null && map.get("sqlDataResType") != null){
    		sqlDataRes = (SqlDataRes) map.get("sqlDataRes");
        	sqlDataResType = (SqlDataResType) map.get("sqlDataResType");
    	}else{
			sqlDataRes = sqlDataResDao.findByDataResId(sqlTable.getDataResId());
			sqlDataResType = sqlDataResTypeDao.findByDataResTypeId(sqlDataRes.getDataResTypeId());
    	}
		String sql = Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).insertSingleSql( fieldInfoes, map.get("tableSqlName").toString());
		LOG.info("添加sql："+sql);
		boolean resBoolean = true;
		if(sql != null && !sql.isEmpty()){
			try{//dataResId
				DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
				sqlQueryMapper.insert(sql);
			}catch(Exception e){
				resBoolean = false;
				e.printStackTrace();
			}finally{
				DynamicDataSourceContextHolder.clearDataSourceType();
			}
		}
		if(resBoolean){
			status = new Status("数据添加成功！",Dictionary.HttpStatus.OK.value );
		}else{
			status = new Status("添加数据失败，sql错误!",Dictionary.HttpStatus.NO_CONTENT.value );
		}
		return status;
    }
    /**
     * 批量添加接口
     * @throws BaseException 
     */
//    public Status insertModel(String tableName, List<Map<String,Object>> fieldValues,String dataResId,String value) throws BaseException{
//    	Status status;
//    	if(fieldValues != null && !fieldValues.isEmpty()){
//    		for (Map<String, Object> map : fieldValues) {
//    			insertSingle(map, tableName, dataResId, value);
//			}
//    	}
//    	status = new Status("数据添加成功！",Dictionary.HttpStatus.OK.value );
//		return status;
//    }
    
    /***
	 * 查询，添加语句之前做路由判断
	 */
	public Map<String, Object> isRoute(SqlTable sqlTable,String value){
//		String tableName="";
		User userInfo = CurrentUserUtils.getInstance().getUser();
		userInfo  = new User();
		userInfo.setTenantId("tenant_system");
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(sqlTable != null ){
			if(!StringUtils.isBlank(value)){
				//判断内存中是否有
				if(tenantLabeles.get(sqlTable.getTableId()) != null && !tenantLabeles.get(sqlTable.getTableId()).equals("")){
					map = isMemory( sqlTable, userInfo, value);
				}else{
					//获取表名称//获取表规则
					map = sqlShapes( sqlTable, userInfo, value);
				}
				if(map != null && !map.isEmpty()){
					SqlDataRes sqlDataRes = sqlDataResDao.findByDataResId(map.get("dataResId").toString());
					SqlDataResType sqlDataResType = sqlDataResTypeDao.findByDataResTypeId(sqlDataRes.getDataResTypeId());
					map.put("sqlDataRes", sqlDataRes);
					map.put("sqlDataResType", sqlDataResType);
					Map<String,Object> tableName = getTabelName( sqlDataRes, sqlDataResType, map.get("tableSqlName").toString());
					if(tableName != null && !tableName.isEmpty()){
					}else{
						//获取表字段信息
						tableInfo( sqlTable);
						//创建表
						sqlTable.setTableSqlName(map.get("tableSqlName").toString());
						HashMap<String,Object> typeMap = typeMap(sqlDataResType);
						String dataResTdOld = sqlTable.getDataResId(); 
						sqlTable.setDataResId(map.get("dataResId").toString());
						save( sqlDataRes, sqlDataResType, sqlTable,sqlTable.getSqlFieldes(),typeMap);
						sqlTable.setDataResId(dataResTdOld);
					}
				}
			}
			if(map != null && !map.isEmpty()){
				
			}else{
				map.put("tableSqlName", sqlTable.getTableSqlName());
			}
		}
//		map.put("sqlTable", sqlTable);//必须放最后
		return map;
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
     * 单表删除数据
     * @param tableName
     * @param conditions
     * @param sqlDataRes
     * @return 
     * @throws BaseException 
     */
	public Status deleteDataModel(String tableName,String conditions,String dataResId,String value) throws BaseException {
		Status status;
		LOG.info("conditions:" + conditions);
		SqlTable sqlTable = tableSqlName(tableName,dataResId);
		if(sqlTable == null){
    		status = new Status("未找到表信息！",Dictionary.HttpStatus.NOT_FOUND.value );
			return status;
    	}
		Map<String, Object> map = isRoute(sqlTable, value);
    	SqlDataRes sqlDataRes = null;
    	SqlDataResType sqlDataResType = null;
    	if(map != null && map.get("sqlDataRes") != null && map.get("sqlDataResType") != null){
    		sqlDataRes = (SqlDataRes) map.get("sqlDataRes");
        	sqlDataResType = (SqlDataResType) map.get("sqlDataResType");
    	}else{
			sqlDataRes = sqlDataResDao.findByDataResId(sqlTable.getDataResId());
			sqlDataResType = sqlDataResTypeDao.findByDataResTypeId(sqlDataRes.getDataResTypeId());
    	}
		//获取数据源标识
		int dbFlag = Integer.parseInt(sqlDataResType.getResType());
		StringBuffer sBuffer = new StringBuffer();
		//如果没有条件 整表删数据
		if(conditions==null||conditions.isEmpty()){
			//非DB2
			if(dbFlag!=3){
				sBuffer.append("TRUNCATE TABLE ");
				sBuffer.append(map.get("tableSqlName").toString());
			}else{//3:DB2
				sBuffer.append("ALTER TABLE ");
				sBuffer.append(map.get("tableSqlName").toString());
				sBuffer.append(" ACTIVATE NOT LOGGED INITIALLY WITH EMPTY TABLE ");
			}	
		}else{
			sBuffer.append("DELETE FROM ");
			sBuffer.append(map.get("tableSqlName").toString());
			sBuffer.append(" "+conditions);
		}
		String sql = sBuffer.toString();
		boolean resBoolean= true;
		try{//dataResId
			DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
			sqlQueryMapper.delete(sql);
		}catch(Exception e){
			resBoolean = false;
			e.printStackTrace();
		}finally{
			DynamicDataSourceContextHolder.clearDataSourceType();
		}
		if(resBoolean){
			status = new Status("deleteDataModel--删除数据成功",Dictionary.HttpStatus.OK.value );
		}else{
			status = new Status("deleteDataModel--删除数据失败，sql错误!",Dictionary.HttpStatus.NO_CONTENT.value );
		}
		return status;
	}
	
	
    /**
     * 单表更新数据
     * @param resp 
     * @param tableName
     * @param conditions--- id='a',name='aa'
     * @param dbflag
     * @param setList -- id='a',name='aa'
     * @return 
     * @throws BaseException 
     */
	public Status updateDataModel(String setString,String tableName,String conditions,String dataResId,String value) throws BaseException {
		Status status;
		SqlTable sqlTable = tableSqlName(tableName,dataResId);
		if(sqlTable == null){
    		status = new Status("未找到表信息！",Dictionary.HttpStatus.NOT_FOUND.value );
			return status;
    	}
		Map<String, Object> map = isRoute(sqlTable, value);
    	SqlDataRes sqlDataRes = null;
    	SqlDataResType sqlDataResType = null;
    	if(map != null && map.get("sqlDataRes") != null && map.get("sqlDataResType") != null){
    		sqlDataRes = (SqlDataRes) map.get("sqlDataRes");
        	sqlDataResType = (SqlDataResType) map.get("sqlDataResType");
    	}else{
			sqlDataRes = sqlDataResDao.findByDataResId(sqlTable.getDataResId());
			sqlDataResType = sqlDataResTypeDao.findByDataResTypeId(sqlDataRes.getDataResTypeId());
    	}
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("UPDATE ");
		sBuffer.append(map.get("tableSqlName").toString() + " SET ");
		sBuffer.append(" "+setString);
		if(!StringUtils.isBlank(conditions)){
			sBuffer.append(" WHERE "+conditions);
		}
		String sql = sBuffer.toString();
		boolean resBoolean = true;
		try{//dataResId
			DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
			sqlQueryMapper.update(sql);
		}catch(Exception e){
			resBoolean = false;
			e.printStackTrace();
		}finally{
			DynamicDataSourceContextHolder.clearDataSourceType();
		}
		if(resBoolean){
			status = new Status("updateDataModel--更新数据成功",Dictionary.HttpStatus.OK.value );
		}else{
			status = new Status("updateDataModel--更新数据失败，sql错误!",Dictionary.HttpStatus.NO_CONTENT.value );
		}
		return status;
	}
	
	public List<SqlDataRes> findAll(){
		return sqlDataResDao.findByTenantId("tenant_system");
	}
	
	
	public void saveProDataApi(ProDataApi proDataApi) {
		User userInfo = CurrentUserUtils.getInstance().getUser();
		String createBy = userInfo.getLoginId();
		String tenantId = userInfo.getTenantId();
		Date current = DateUtils.getCurrentDate();
		proDataApi.setTenantId(tenantId);
		proDataApi.setCreateDate(current);
		proDataApi.setCreateBy(createBy);
//		String inputAttr = createXmlFormat.createXml(proDataApi.getInputAttr());
//		proDataApi.setInputAttr(inputAttr);
//		String outputAttr = createXmlFormat.createXml(proDataApi.getOutputAttr());
//		proDataApi.setInputAttr(outputAttr);
//		proDataApi.setSqlText(proDataApi.getSqlText());
//		proDataApi.setDataApiName(proDataApi.getDataApiName());
//		proDataApi.setModelId(proDataApi.getModelId());
////		proDataApi.setDataApiId();
//		proDataApi.setDataResId(proDataApi.getDataResId());
		
		if(StringUtils.isNotBlank(proDataApi.getDataApiId())){
			proDataApiMapper.updateAll(proDataApi);
			return;
		}else{
			proDataApiMapper.save(proDataApi);
		}	
	}
	
	public ProDataApi findProDataApi(String dataApiId){
		ProDataApi proDataApi = proDataApiMapper.findProDataApi(dataApiId);
		//解析入参 出参
//		proDataApi.setInputAttr(analysisXmlFormat.readStringXml(proDataApi.getInputAttr()));
//		proDataApi.setOutputAttr(analysisXmlFormat.readStringXml(proDataApi.getOutputAttr()));
		return proDataApi;
	}
	
	public Status deleteProDataApi(String dataApiId){
		Status status ;
		proDataApiMapper.deleteProDataApi(dataApiId);
		status = new Status("deleteProDataApi--删除数据成功",Dictionary.HttpStatus.OK.value );
		return status;
	}
	
	public void saveProModel(ProModel proModel){
		User userInfo = CurrentUserUtils.getInstance().getUser();
		String createBy = userInfo.getLoginId();
		String tenantId = userInfo.getTenantId();
		Date current = DateUtils.getCurrentDate();
		proModel.setTenantId(tenantId);
		proModel.setCreateDate(current);
		proModel.setCreateBy(createBy);
		if(StringUtils.isNotBlank(proModel.getModelId())){
			proModelMapper.updateAll(proModel);
			return;
		}else{
			proModelMapper.save(proModel);
		}	
	}
	public ProModel findProModel(String modelId){
		return proModelMapper.findProModel(modelId);
	}
	
	public Status deleteProModel(String modelId){
		Status status ;
		proModelMapper.deleteProModel(modelId);
		status = new Status("deleteProModel--删除数据成功",Dictionary.HttpStatus.OK.value );
		return status;
	}
	
	/**
	 * map中同时包含tableSqlName与markVaule时才走分库判断
	 * markVaule===判断的值
	 * tableSqlName===表名称
	 * @param map
	 * @return
	 */
	public Map<String,Object> findDbFreemark(Map<String,Object> map){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//根据表名称查--分表字段--查map中字段值--根据规则查数据源
		SqlDataRes sqlDataRes = null;
    	SqlDataResType sqlDataResType = null;
		ProDataApi proDataApi = proDataApiMapper.findProDataApi(map.get("dataApiId").toString());
		if(proDataApi != null){
			if(map.get("tableSqlName") != null && !map.get("tableSqlName").toString().equals("") && 
					map.get("markVaule") != null && !map.get("markVaule").toString().equals("")){
				SqlTable sqlTable = tableSqlName(map.get("tableSqlName").toString(),proDataApi.getDataResId());
				String value = map.get("markVaule").toString();
				Map<String,Object> routeMap = isRoute(sqlTable, value);
				if(routeMap != null && routeMap.get("sqlDataRes") != null && routeMap.get("sqlDataResType") != null){
		    		sqlDataRes = (SqlDataRes) routeMap.get("sqlDataRes");
		        	sqlDataResType = (SqlDataResType) routeMap.get("sqlDataResType");
		        	map.put("tableSqlName", routeMap.get("tableSqlName"));
		    	}
			}else{
				sqlDataRes = sqlDataResDao.findByDataResId(proDataApi.getDataResId());
				String dataResTypeId = sqlDataRes.getDataResTypeId();
				sqlDataResType = sqlDataResTypeDao.findByDataResTypeId(dataResTypeId);
				if(sqlDataResType == null){
					resultMap.put("code", "888888");
					resultMap.put("msg", "未找到数据源!");
					return resultMap;
				}	
			}
		}else{
			resultMap.put("code", "888888");
			resultMap.put("msg", "未找到数据源!");
			return resultMap; 
		}
		//解析入参 出参
		String sql = proDataApi.getSqlText();
		try{
			Template keyTemp = FreemarkerUtil.getTemplate(sql);
			StringWriter valStr = new StringWriter();
			if(!FreemarkerUtil.print(keyTemp, map, valStr)){
				throw new BaseException("updateDataModel--更新数据时发生错误"); 
			}
			sql = valStr.toString();
		}catch(Exception e){
			resultMap.put("code", "777777");
			resultMap.put("msg", "sql替换异常");
			return resultMap; 
		}
		boolean resBoolean = true;
		String result =""; 
		try{//dataResId
			DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
			if(proDataApi.getOutputSample().equals("1")){//1--String
				result = sqlQueryMapper.stringQuery(sql);
			}else if(proDataApi.getOutputSample().equals("2")){//2--int
				Integer resultInt = sqlQueryMapper.integerQuery(sql);
				if(resultInt != null){
					result = String.valueOf(resultInt);
				}
			}else if(proDataApi.getOutputSample().equals("3")){//3--boolean
				Boolean resultLong = sqlQueryMapper.booleanQuery(sql);
				if(resultLong != null){
					result = String.valueOf(resultLong);
				}
			}else if(proDataApi.getOutputSample().equals("4")){//4--List
				result = JSON.toJSONString(sqlQueryMapper.listStringQuery(sql));
			}else if(proDataApi.getOutputSample().equals("5")){//5--Map
				result = JSON.toJSONString(sqlQueryMapper.mapQuery(sql));
			}else if(proDataApi.getOutputSample().equals("6")){//6--List<Map>
				result = JSON.toJSONString(sqlQueryMapper.listQuery(sql));
			}
		}catch(Exception e){
			resBoolean = false;
			e.printStackTrace();
		}finally{
			DynamicDataSourceContextHolder.clearDataSourceType();
		}
		if(resBoolean){
			resultMap.put("code", "000000");
			resultMap.put("msg", result);
		}else{
			resultMap.put("code", "999999");
			resultMap.put("msg", "查询异常");
		}
		return resultMap;
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
	/**
	 * 创建表
	 */
	public Map<String, Object> save(SqlDataRes sqlDataRes,SqlDataResType sqlDataResType,SqlTable sqlTable,List<SqlField> sqlFieldNew,HashMap<String,Object> haMap){
			Map<String,Object> map = new HashMap<String,Object>();
			String code = "000000"; 
			String result = "true";
			if(sqlDataResType.getResType() != null ){
				String sql = "" ;
				try {
					sql = Sql_switcher.getSqlFunc(Integer.parseInt(sqlDataResType.getResType())).createTable(haMap, sqlTable, sqlFieldNew);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(sql != null && !sql.equals("") && sqlFieldNew != null && !sqlFieldNew.isEmpty()){
					//执行建表语句
					result = tableToolService.executeDdl(sqlDataRes, sql, sqlDataResType);
					if(!result.equals("true")){
						//查是否存在表，存在的话获取表信息，不存在就删除系统中（sqltable）表信息结束----先判断外键，如果外键设计的表不存在系统，系统自动录入新表信息
						map.put("code", code);
						map.put("result", result);
						return map;
					}
					//非mysql 描述，索引信息添加
					if(sqlDataResType.getResType() != null && !sqlDataResType.getResType().equals("1")){
						map = modifyDescription(sqlDataRes,sqlDataResType,sqlTable, sqlFieldNew);
						return map;
					}
					////保存sqltable与sqlfield信息
					User userInfo = CurrentUserUtils.getInstance().getUser();
					userInfo  = new User();
					userInfo.setTenantId("tenant_system");
					String tableNewId = UUID.randomUUID().toString().replace("-", "");
					saveSqlTable(sqlTable.getTableId(), tableNewId, sqlTable, userInfo);
				}
			}
			map.put("code", code);
			map.put("result", result);
			return map;
	}
	public void saveSqlTable(String tableIdOld,String tableNewId,SqlTable sqlTable,User userInfo){
		sqlTableMapper.save(tableIdOld, tableNewId, sqlTable.getDataResId(),sqlTable.getTableSqlName());
		sqlFieldMapper.saveSqlField(tableIdOld, userInfo.getTenantId(), tableNewId);
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
		map.put("result", result);
		return map;
	}
	/**
	 * 删除表信息--回滚调用
	 * @param sqlTable
	 */
	public void deleteAll(SqlTable sqlTable){
		User userInfo = CurrentUserUtils.getInstance().getUser();
		//删除表信息
		sqlTableMapper.deleteByTableIdAndTenantId(sqlTable.getTableId(), userInfo.getTenantId());
		//删除field与外键信息
		sqlFieldMapper.deleteByTableIdAndTenantId(sqlTable.getTableId(), userInfo.getTenantId());
	}
	/**
	 * 查询表是否存在
	 * @return
	 */
	public boolean isTrue(HashMap<String, Object> map){
		try{//dataResId
			DynamicDataSourceContextHolder.setDataSourceType(map.get("dataResId").toString());
			sqlQueryMapper.findOne(map.get("tableSqlName").toString());
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			DynamicDataSourceContextHolder.clearDataSourceType();
		}
		return true;
	}
	
	/**
	 * 获取表名称
	 * @return
	 */
	public SqlTable tableSqlName(String tableName,String dataResId){
		User userInfo = CurrentUserUtils.getInstance().getUser();
		userInfo  = new User();
		userInfo.setTenantId("tenant_system");
		SqlTable sqlTable = sqlTableMapper.findTableName(tableName,userInfo.getTenantId(),dataResId);
		return sqlTable;
	}
	
	/**
	 * 判断内存中是否包含
	 * @return
	 */
	public HashMap<String, Object> isMemory(SqlTable sqlTable,User userInfo,String value){
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<SqlShape> sqlShapes = sqlShapeMapper.findByTableId(sqlTable.getTableId(), userInfo.getTenantId());
		if(sqlShapes != null && !sqlShapes.isEmpty()){
			for (SqlShape sqlShape : sqlShapes) {
				if(sqlShape.getShapeType().equals("2")){//分表--0
					String sqlName = sqlName( sqlTable.getTableId(), value, sqlTable.getTableSqlName());
					if(!StringUtils.isBlank(sqlName)){
						map.put("tableSqlName", sqlName);
						map.put("dataResId", sqlTable.getDataResId());
					}
				}else if(sqlShape.getShapeType().equals("3")){//分库--
					String dataResId = getDataResId(sqlTable.getTableId(), value);
					if(!StringUtils.isBlank(dataResId)){
						map.put("tableSqlName", sqlTable.getTableSqlName());
						map.put("dataResId", dataResId);
					}
				}
			}
		}
		return map;
	}
	
	/**
	 * 获取表字段信息--无表规则
	 * @return
	 */
	public SqlTable tableInfo(SqlTable sqlTable){
		LOG.info("tableID:"+sqlTable.getTableId());
		User userInfo = CurrentUserUtils.getInstance().getUser();
		userInfo  = new User();
		userInfo.setTenantId("tenant_system");
		if(sqlTable != null){
			List<SqlField> sqlFieldes = sqlFieldMapper.findByTableIdTenantId(sqlTable.getTableId(), userInfo.getTenantId());
			if(sqlFieldes != null && !sqlFieldes.isEmpty()){
				sqlTable.setSqlFieldes(sqlFieldes);
			}
		}
		return sqlTable;
	}
	
	
	/**
	 * 获取表形态信息
	 * @param sqlTable
	 * @param userInfo
	 * @return
	 */
	public HashMap<String, Object> sqlShapes(SqlTable sqlTable,User userInfo,String value){
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<SqlShape> sqlShapes = sqlShapeMapper.findByTableId(sqlTable.getTableId(), userInfo.getTenantId());
		if(sqlShapes != null && !sqlShapes.isEmpty()){
			for (SqlShape sqlShape : sqlShapes) {
				if(sqlShape.getShapeType().equals("2")){//分表--0
					SqlSubTable sqlSubTable = sqlSubTableMapper.findSqlSubTable(sqlShape.getShapeId(),  userInfo.getTenantId());
					if(sqlSubTable != null){
						addCurrentMap(sqlTable, sqlSubTable);
						String sqlName = sqlName( sqlTable.getTableId(), value, sqlTable.getTableSqlName());
						//数据源ID
						if(!StringUtils.isBlank(sqlName)){
							map.put("tableSqlName", sqlName);
							map.put("dataResId", sqlTable.getDataResId());
						}
					}
				}else if(sqlShape.getShapeType().equals("3")){//分库--1
					SqlSubTreasury sqlSubTreasury = sqlSubTreasuryMapper.findSqlSubTreasury(sqlShape.getShapeId(),  userInfo.getTenantId());
					if(sqlSubTreasury != null){
						//--查询子库信息
						List<SqlSubTreasuryDivision> sqlSubTreasuryDivisiones = sqlSubTreasuryDivisionMapper.findDivision(sqlSubTreasury.getSubTreasuryId(), userInfo.getTenantId());
						if(sqlSubTreasuryDivisiones != null && !sqlSubTreasuryDivisiones.isEmpty()){
							String dataResId =addTreasuryMap( sqlTable, sqlSubTreasury, sqlSubTreasuryDivisiones, value);
							if(!StringUtils.isBlank(dataResId)){
								map.put("tableSqlName", sqlTable.getTableSqlName());
								map.put("dataResId", dataResId);
							}
						}
					}
				}
			}
		}
		return map;
	}
	
	/**
	 * 获取分库数据源
	 */
	public String getDataResId(String tableId,String value){
		String dataResId = "";//(tableId,(1,(type[1,3],(typeValue1,sqlSubTreasuryDivisiones)))---表id,1是分库
		HashMap<String, HashMap<String, Object>> map = tenantLabeles.get(tableId);
		if(map != null && !map.isEmpty()){
			for	(String	key	:map.keySet()){
				if(key.equals("1")){
					HashMap<String, Object> typeMap = map.get("1");
					if(typeMap != null && !typeMap.isEmpty()){
						for	(String	typeMapkey	:typeMap.keySet()){
							HashMap<String, Object> valueMap = (HashMap<String, Object>) typeMap.get(typeMapkey);
							for	(String	valueMapkey	:valueMap.keySet()){
								if(typeMapkey.equals("1")){//1 String 2 数值 3 date//(tableId,(1,(type[1,3],(typeValue1,(typeValue2,sqlSubTreasuryDivisiones)))
									HashMap<String, Object> valueMap2 = (HashMap<String, Object>) valueMap.get(valueMapkey);
									for	(String	valueMapkey2 :valueMap2.keySet()){
										List<SqlSubTreasuryDivision> sqlSubTreasuryDivisiones = (List<SqlSubTreasuryDivision>) valueMap2.get(valueMapkey2);
										//找出匹配的数据源Id
										dataResId = dataResId( sqlSubTreasuryDivisiones, value.substring(Integer.parseInt(valueMapkey), Integer.parseInt(valueMapkey2)));
									}
								}else if(typeMapkey.equals("2")){//(tableId,(1,(type[1,3],(typeValue1,sqlSubTreasuryDivisiones)))
									List<SqlSubTreasuryDivision> sqlSubTreasuryDivisiones = (List<SqlSubTreasuryDivision>) valueMap.get(valueMapkey);
									dataResId = dataResIdNum( sqlSubTreasuryDivisiones, value);
									//找出匹配的数据源Id
								}else if(typeMapkey.equals("3")){
									List<SqlSubTreasuryDivision> sqlSubTreasuryDivisiones = (List<SqlSubTreasuryDivision>) valueMap.get(valueMapkey);
									//找出匹配的数据源Id
									dataResId = dataResId( sqlSubTreasuryDivisiones, value);
								}
							}
						}
					}
				}
			}
		}
		return dataResId;
	}
	/**
	 * 匹配数据源Id
	 * @return
	 */
	public String dataResId(List<SqlSubTreasuryDivision> sqlSubTreasuryDivisiones,String value){
		for (SqlSubTreasuryDivision sqlSubTreasuryDivision : sqlSubTreasuryDivisiones) {
			if(sqlSubTreasuryDivision.getTypeValue1().equals(value)){
				return sqlSubTreasuryDivision.getDataResId();
			}
		}
		return "";
	}
	/**
	 * 匹配数据源Id
	 * @return
	 */
	public String dataResIdNum(List<SqlSubTreasuryDivision> sqlSubTreasuryDivisiones,String value){
		for (SqlSubTreasuryDivision sqlSubTreasuryDivision : sqlSubTreasuryDivisiones) {
			int startInt = Integer.parseInt(sqlSubTreasuryDivision.getTypeValue1());
			int endInt = Integer.parseInt(sqlSubTreasuryDivision.getTypeValue2());
			int valueInt = Integer.parseInt(value);
			if(startInt <= valueInt && valueInt <= endInt){
				return sqlSubTreasuryDivision.getDataResId();
			}
		}
		return null;
	}
	
	/**
	 * 添加分库map
	 */
	public String addTreasuryMap(SqlTable sqlTable,SqlSubTreasury sqlSubTreasury,List<SqlSubTreasuryDivision> sqlSubTreasuryDivisiones,String value){
		String dataResId = "";//（1 String 2 数值 3 date）
		if(sqlSubTreasury.getSubTreasuryType().equals("2") || sqlSubTreasury.getSubTreasuryType().equals("3")){
			//(tableId,(1,(type[1,3],(typeValue1,sqlSubTreasuryDivisiones)))
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put(sqlSubTreasury.getTypeValue1(), sqlSubTreasuryDivisiones);
			HashMap<String,Object> map2 = new HashMap<String,Object>();
			map2.put(sqlSubTreasury.getSubTreasuryType(),map);
			HashMap<String, HashMap<String, Object>> map1 = new HashMap<String,HashMap<String, Object>>();
			map1.put("1", map2);
			tenantLabeles.put(sqlTable.getTableId(), map1);
			if(sqlSubTreasury.getSubTreasuryType().equals("2")){
				dataResId = dataResIdNum(sqlSubTreasuryDivisiones, value);
			}else{
				dataResId = dataResId( sqlSubTreasuryDivisiones, value);
			}
		}else if(sqlSubTreasury.getSubTreasuryType().equals("1")){
			//(tableId,(1,(type[1,3],(typeValue1,(typeValue2,sqlSubTreasuryDivisiones)))
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put(sqlSubTreasury.getTypeValue2(), sqlSubTreasuryDivisiones);
			HashMap<String,Object> map3 = new HashMap<String,Object>();
			map3.put(sqlSubTreasury.getTypeValue1(), map);
			HashMap<String,Object> map2 = new HashMap<String,Object>();
			map2.put(sqlSubTreasury.getSubTreasuryType(),map3);
			HashMap<String, HashMap<String, Object>> map1 = new HashMap<String,HashMap<String, Object>>();
			map1.put("1", map2);
			tenantLabeles.put(sqlTable.getTableId(), map1);
			dataResId = dataResId( sqlSubTreasuryDivisiones, value.substring(Integer.parseInt(sqlSubTreasury.getTypeValue1()), Integer.parseInt(sqlSubTreasury.getTypeValue2())));
		}
		return dataResId;
	}
	
	/***
	 * 分表添加map
	 */
	public void addCurrentMap(SqlTable sqlTable,SqlSubTable sqlSubTable){
		if(sqlSubTable.getSubTableType().equals("1")||sqlSubTable.getSubTableType().equals("3")){
			//(tableId,(0,(type[1,3],(fileName,typeValue1)))
//			HashMap<String,Object> map1 = new HashMap<String,Object>();
//			map1.put(sqlSubTable.getFieldSqlName(), sqlSubTable.getSubTableTypeValue1());
			HashMap<String, Object> map2 = new HashMap<String, Object>();
			map2.put(sqlSubTable.getSubTableType(), sqlSubTable.getSubTableTypeValue1());
			HashMap<String, HashMap<String, Object>> map = new HashMap<String,HashMap<String, Object>>();
			map.put("0", map2);
			tenantLabeles.put(sqlTable.getTableId(), map);
		}else if(sqlSubTable.getSubTableType().equals("2")){//(tableId,(0,(type[1,3],(fileName,(typeValue1,typeValue2))))
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put(sqlSubTable.getSubTableTypeValue1(), sqlSubTable.getSubTableTypeValue2());
//			HashMap<String, Object> map1 = new HashMap<String, Object>();
//			map1.put(sqlSubTable.getFieldSqlName(), map);
			HashMap<String, Object> map2 = new HashMap<String, Object>();
			map2.put(sqlSubTable.getSubTableType(), map);
			HashMap<String, HashMap<String, Object>> map3 = new HashMap<String,HashMap<String, Object>>();
			map3.put("0", map2);
			tenantLabeles.put(sqlTable.getTableId(), map3);
		}
	}
	
	/**
	 * 组装表名称
	 * @return
	 */
	public String sqlName(String tableId,String value,String tableName){
		String sqlName = "";
		HashMap<String, HashMap<String, Object>> map = tenantLabeles.get(tableId);
		if(map != null && !map.isEmpty()){
			for	(String	key	:map.keySet()){
				if(key.equals("0")){
					HashMap<String, Object> typeMap = map.get("0");
					if(typeMap != null && !typeMap.isEmpty()){
						for	(String	typeMapkey	:typeMap.keySet()){
							if(typeMapkey.equals("1")){//1 date 2 string(截取长度) 3 数值(100-1，200-2) 4 自定义名称 //(0,(type[1,3],(typeValue1,typeValue2)));(0,(type[1,3],typeValue2))
								String typeValue = (String) typeMap.get(typeMapkey);
								Map<String,Object> mapDate = DateDemo.getMonthDay(value);
								if(typeValue.equals("YYYY")){// String dateStr = "2013-11-10 18:45:39"; 
									sqlName=tableName+"_"+mapDate.get("year"); 
								}else if(typeValue.equals("YYYY-MM")){
									sqlName=tableName+"_"+mapDate.get("year")+"_"+mapDate.get("month"); 
								}else if(typeValue.equals("YYYY-MM-DD")){
									sqlName=tableName+"_"+mapDate.get("year")+"_"+mapDate.get("month")+"_"+mapDate.get("day"); 
								}
							}else if(typeMapkey.equals("2")){
								HashMap<String, Object> valueMap = map.get(typeMapkey);
								for	(String	valueMapkey	:valueMap.keySet()){
									//用截取比较值
									sqlName=tableName+"_"+value.substring(Integer.parseInt(valueMapkey), Integer.parseInt((String) valueMap.get(valueMapkey)));
								}
							}else if(typeMapkey.equals("3")){
								String typeValue = (String) typeMap.get(typeMapkey);
								//转正行取余数
								int remainder = Integer.parseInt(value)%Integer.parseInt((String) typeMap.get(typeMapkey));
								int remainderz = Integer.parseInt(value)/Integer.parseInt((String) typeMap.get(typeMapkey));
								if(0 < remainder){
									remainderz = remainderz+1;
								}
								sqlName=tableName+"_"+remainderz;
							}
						}
					}
				}
			}
		}
		return sqlName;
	}
	
}
