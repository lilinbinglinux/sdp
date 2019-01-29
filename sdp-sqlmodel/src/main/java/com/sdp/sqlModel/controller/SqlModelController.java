package com.sdp.sqlModel.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.BaseException;
import com.sdp.common.entity.Status;
import com.sdp.sqlModel.entity.SqlDataRes;
import com.sdp.sqlModel.entity.SqlDataResType;
import com.sdp.sqlModel.entity.SqlLock;
import com.sdp.sqlModel.entity.SqlTable;
import com.sdp.sqlModel.entity.SqlTableForeignkey;
import com.sdp.sqlModel.entity.SqlTableType;
import com.sdp.sqlModel.service.SqlDataResService;
import com.sdp.sqlModel.service.SqlDataResTypeService;
import com.sdp.sqlModel.service.SqlLockService;
import com.sdp.sqlModel.service.SqlModelService;
import com.sdp.sqlModel.service.SqlTableForeignkeyService;
import com.sdp.sqlModel.service.SqlTableService;
import com.sdp.sqlModel.service.SqlTableTypeService;

	
	

/**
 * 
 * 数据库建模controller
 *
 */
@Controller
@RequestMapping("v1/sqlModel")
public class SqlModelController {

	@Autowired
	private SqlModelService sqlModelService;
	@Autowired
	private SqlTableTypeService sqlTableTypeService;
	@Autowired
	private SqlDataResTypeService sqlDataResTypeService;
	@Autowired
	private SqlDataResService sqlDataResService;
	@Autowired
	private SqlTableForeignkeyService sqlTableForeignkeyService;
	@Autowired
	private SqlTableService sqlTableService;
	@Autowired
	private SqlLockService sqlLockService;

	
	/**
     * 输出日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(SqlModelController.class);
    /**
     * 数据库建模模块--删除表数据
     * @param String tableId
     */
	@RequestMapping(value = {"/deletetableinfo/{tableId}"}, method = RequestMethod.GET)
    @ResponseBody
    @Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<Status> deleteTableInfo(@PathVariable("tableId") String tableId){
		Status status = null;
		try{
			status = sqlModelService.deleteByTableId(tableId);
		}catch (Exception ex) {
            LOG.error(ex.getMessage());
			ex.printStackTrace();
		}
	     return new ResponseEntity<>(status, HttpStatus.OK);
	}
	
	/**
     * 数据库建模模块--保存表类型数据 SQL_TABLE_TYPE
     * @param SqlTableType sqlTableType
     */
	@RequestMapping(value = {"/savesqltabletype"}, method = RequestMethod.GET)
    @ResponseBody
    @Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<SqlTableType> saveSqlTableType(SqlTableType sqlTableType){
		try{
			sqlTableTypeService.saveSqlTableType(sqlTableType);
		}catch (Exception ex) {
			LOG.error(ex.getMessage());
			ex.printStackTrace();
		}
	     return new ResponseEntity<>(sqlTableType, HttpStatus.OK);
	}
	
	/**
     * 数据库建模模块--删除表类型数据 SQL_TABLE_TYPE
     * @param SqlTableType sqlTableType
     */
	@RequestMapping(value = {"/deletesqltabletype"}, method = RequestMethod.GET)
    @ResponseBody
    @Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<Status> deleteSqlTableType(String tableTypeId){
		LOG.info("tableTypeId="+tableTypeId);
		Status status = null;
		try{
			status = sqlTableTypeService.deleteSqlTableType(tableTypeId);
		}catch (Exception ex) {
			LOG.error(ex.getMessage());
			ex.printStackTrace();
		}
	     return new ResponseEntity<>(status, HttpStatus.OK);
	}
	
	/**
     * 数据库建模模块--删除外键表数据 SQL_TABLE_FOREIGNKEY
     * @param SqlTableForeignkey sqlTableForeignkey
     */
	@RequestMapping(value = {"/deletesqltabletypeforeignkey"}, method = RequestMethod.GET)
    @ResponseBody
    @Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<Status> deleteSqlTableForeignkey(String foreignKeyId){
		Status status = null;
		try{
			status = sqlTableForeignkeyService.deleteSqlTableForeignkey(foreignKeyId);
		}catch (Exception ex) {
			LOG.error(ex.getMessage());
			ex.printStackTrace();
		}
	     return new ResponseEntity<>(status, HttpStatus.OK);
	}
	

	/**
     * 数据库建模模块--返回所有的表类型接口
     * @param String parentId
     */
	@RequestMapping(value = {"/tabletypenamelist"}, method = RequestMethod.GET)
    @ResponseBody
    @Produces(MediaType.APPLICATION_JSON)
	public List<Map<String,Object>> sqlTableTypeList(String parentId){
		List<Map<String,Object>> tableTypeNameList = null;
		LOG.info("------------");
		try{
			tableTypeNameList = sqlTableTypeService.selectSqlTableType(parentId);
		}catch (Exception ex) {
			LOG.error(ex.getMessage());
			ex.printStackTrace();
		}
	     return tableTypeNameList;
	}


	/**
	 * 数据库建模保存
	 * @return
	 * @throws BaseException 
	 */
	@RequestMapping(value = {"/saveSqlModel"}, method = RequestMethod.GET)
    @ResponseBody
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> saveSqlModel(Model model,SqlTable sqlTable) throws BaseException{
		Map<String,Object> map = new HashMap<String,Object>();
	    try {
	    	map = sqlModelService.save(sqlTable);
	    	if(map.get("code").toString().equals("000000")){
	    		map.put("sqlTable", sqlTable);
	    	}
	    	map.put("sqlTableType", sqlTableTypeService.findSqlTableType(sqlTable.getTableTypeId()));
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new BaseException(ex.getMessage());
        }
        return map;

    }

	/**
	 * 保存数据源类型
	 * @return
	 * @throws BaseException 
	 */
	@RequestMapping(value = {"/saveSqlDataResType"}, method = RequestMethod.GET)
    @ResponseBody
    @Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<SqlDataResType> saveSqlDataResType(SqlDataResType sqlDataResType) throws BaseException{
		 try {
			 sqlDataResTypeService.save(sqlDataResType);
		 } catch (Exception ex) {
	            LOG.error(ex.getMessage());
	            throw new BaseException(ex.getMessage());
         }
		return  new ResponseEntity<>(sqlDataResType, HttpStatus.OK);
	}
	/**
	 * 保存数据源
	 * @return
	 * @throws BaseException 
	 */
	@RequestMapping(value = {"/saveSqlDataRes"}, method = RequestMethod.GET)
    @ResponseBody
    @Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<SqlDataRes> saveSqlDataRes(SqlDataRes sqlDataRes) throws BaseException{
		sqlDataRes.setTenantId("test");
		try {
			sqlDataResService.save(sqlDataRes);
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.error(ex.getMessage());
			throw new BaseException(ex.getMessage());
		}
		return  new ResponseEntity<>(sqlDataRes, HttpStatus.OK);
	}


    /**
     * 数据库源删除
     * @param tableId
     * @throws BaseException 
     */
	@RequestMapping(value = {"/delete/byDataResId"}, method = RequestMethod.GET)
    @ResponseBody
    @Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<Status> deleteSqlDataRes(@RequestParam("dataResId") String dataResId) throws BaseException{
		Status status;
		try{
			status = sqlModelService.deleteByDataResId(dataResId);
		}catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new BaseException(ex.getMessage());
		}
	     return new ResponseEntity<>(status, HttpStatus.OK);
	}

	/**
	 * 数据库源类型删除
	 * @param tableId
	 * @throws BaseException 
	 */
	@RequestMapping(value = {"/delete/byDataResTypeId"}, method = RequestMethod.GET)
	@ResponseBody
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<Status> deleteSqlDataResType(@RequestParam("dataResTypeId") String dataResTypeId) throws BaseException{
		Status status;
		try{
			status = sqlModelService.deleteBySqlDataResTypeId(dataResTypeId);
		}catch (Exception ex) {
			LOG.error(ex.getMessage());
			throw new BaseException(ex.getMessage());
		}
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	/**
	 * 頁面跳轉
	 */
	@RequestMapping(value = {"/manage/dataSource"}, method = RequestMethod.GET)
	@Produces(MediaType.TEXT_PLAIN)
	public String manageDataSource(){
		return "dataModel/dataSource";
	}

	/**
	 * 頁面跳轉  画布页面
	 */
	@RequestMapping(value = {"/manage/databaseModeling"}, method = RequestMethod.GET)
	@Produces(MediaType.TEXT_PLAIN)
	public String databaseModeling(){
		return "dataModel/databaseCanvas";
	}

	/**
	 * 頁面跳轉  接口页面
	 */
	@RequestMapping(value = {"/manage/databaseApi/{modelId}"}, method = RequestMethod.GET)
	@Produces(MediaType.TEXT_PLAIN)
	public String databaseApi( Model model, @PathVariable("modelId") String modelId ){
		model.addAttribute("modelId", modelId);
		return "dataModel/databaseApi";
	}

	/**
	 * 頁面跳轉  数据源管理
	 */
	@RequestMapping(value = {"/manage/dataBase"}, method = RequestMethod.GET)
	@Produces(MediaType.TEXT_PLAIN)
	public String dataBase(){
		return "dataModel/databaseManage";
	}

	/**
	 * 頁面跳轉  画布页面
	 */
	@RequestMapping(value = {"/manage/dataBaseCanvas"}, method = RequestMethod.GET)
	@Produces(MediaType.TEXT_PLAIN)
	public String dataBaseCanvas(Model model,String tableTypeId){
		model.addAttribute("sqlTableType",sqlTableTypeService.findSqlTableType(tableTypeId));
		List<SqlTable> sqlTables = new ArrayList<SqlTable>();
		List<SqlTable> sqlTableResult = sqlTableService.findByTenantIdAndTableTypeId(tableTypeId);
		for (SqlTable sqlTable : sqlTableResult) {
			sqlTables.add(sqlTableService.findByTableId(sqlTable.getTableId()));
		}
		model.addAttribute("sqlTables", JSONArray.fromObject(sqlTables));
		return "dataModel/databaseCanvas";
	}

	/**
	 * 頁面跳轉  数据源管理 -> 数据源类型管理
	 */
	@RequestMapping(value = {"/manage/dataSourceType"}, method = RequestMethod.GET)
	@ResponseBody
	public SqlDataResType dataSourceType(Model model,String dataResTypeId){
		return sqlDataResTypeService.findSqlDataResType(dataResTypeId);
	}

	/**
	 * 頁面跳轉  数据源管理 -> 数据源类型管理
	 */
	@RequestMapping(value = {"/manage/dataSourceTypeManage"}, method = RequestMethod.GET)
	@Produces(MediaType.TEXT_PLAIN)
	public String dataSourceTypeManage(){
		return "dataModel/dataSourceTypeManage";
	}

	/**
	 * 頁面跳轉  数据源管理 -> 数据源类型管理
	 */
	@RequestMapping(value = {"/manage/databaseIndex"}, method = RequestMethod.GET)
	@Produces(MediaType.TEXT_PLAIN)
	public String databaseIndex(HttpServletRequest request,  Model model){
		String sessionId=request.getSession().getId();
		model.addAttribute("sessionId", sessionId);
		return "dataModel/databaseIndex";
	}

	/**
	 * 数据库源类型列表展示
	 * @param tableId
	 * @throws BaseException 
	 */
	@RequestMapping(value = {"/show/dataResTypeList"}, method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> dataResTypeList() throws BaseException{
		Map<String,Object> map = new HashMap<String,Object>();
		List<SqlDataResType> sqlDataResTypes = null;
		try{
			sqlDataResTypes= sqlDataResTypeService.findByTenantId();
			map.put("code", 0);
			map.put("msg", "success");
			map.put("count", sqlDataResTypes.size());
			map.put("data", sqlDataResTypes);
		}catch (Exception ex) {
			LOG.error(ex.getMessage());
			throw new BaseException(ex.getMessage());
		}
		return map;
	}


    /**
     * 頁面跳轉  新画布页面
     */
    @RequestMapping(value = {"/manage/dataBaseDraw/{dataResId}"}, method = RequestMethod.GET)
    @Produces(MediaType.TEXT_PLAIN)
    public String dataBaseDraw(@PathVariable("dataResId") String dataResId, Model model){

        model.addAttribute("dataResId", dataResId);
        return "dataModel/databaseCanvas";
    }

	/**
	 * 頁面跳轉  项目模块页面
	 */
	@RequestMapping(value = {"/manage/dataBaseProject"}, method = RequestMethod.GET)
	@Produces(MediaType.TEXT_PLAIN)
	public String dataBaseProject(){
		return "dataModel/databaseProject";
	}

	/**
	 * 数据库源类型下所有表类型以及表
	 * @param tableId
	 * @throws BaseException 
	 */
	@RequestMapping(value = {"/show/dataRes"}, method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> dataRes(String dataResId) throws BaseException{
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			map= sqlDataResService.findDataResId(dataResId);
			map.put("code", "000000");
		}catch (Exception ex) {
			map.put("code", "999999");
			map.put("msg", "查询异常");
			LOG.error(ex.getMessage());
			throw new BaseException(ex.getMessage());
		}
		return map;
	}

	/**
	 * 数据库源类型下--所有数据源
	 * @param tableId
	 * @throws BaseException 
	 */
	@RequestMapping(value = {"/show/dataResByTypeIdList"}, method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> dataResByTypeIdList(String dataResTypeId) throws BaseException{
		Map<String,Object> map = new HashMap<String,Object>();
		List<SqlDataRes> sqlDataRes = null;
		try{
			sqlDataRes= sqlDataResService.findByTenantIdAndDataResTypeId(dataResTypeId);
			map.put("code", 0);
			map.put("msg", "success");
			map.put("count", sqlDataRes.size());
			map.put("data", sqlDataRes);
		}catch (Exception ex) {
			LOG.error(ex.getMessage());
			throw new BaseException(ex.getMessage());
		}
		return map;
	}

	/**
	 * 数据库源列表展示
	 * @param tableId
	 * @throws BaseException 
	 */
	@RequestMapping(value = {"/show/dataResList"}, method = RequestMethod.GET)
	@ResponseBody
	public  Map<String,Object> dataResList() throws BaseException{
		Map<String,Object> map = new HashMap<String,Object>();
		List<SqlDataRes> sqlDataReses = null;
		try{
			sqlDataReses= sqlDataResService.findByTenantId();
			map.put("code", 0);
			map.put("msg", "success");
			map.put("count", sqlDataReses.size());
			map.put("data", sqlDataReses);
		}catch (Exception ex) {
			ex.printStackTrace();
			LOG.error(ex.getMessage());
			throw new BaseException(ex.getMessage());
		}
		return map;
	}
	/**
	 * 数据库源下--所有表数据
	 * @param tableId
	 * @throws BaseException 
	 */
	@RequestMapping(value = {"/show/byDataResIdTableList"}, method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> byDataResIdTableList(String dataResId) throws BaseException{
		Map<String,Object> map = new HashMap<String,Object>();
		List<SqlTable> sqlTable = null;
		try{
			sqlTable= sqlTableService.findByTenantId(dataResId);
			map.put("code", 0);
			map.put("msg", "success");
			map.put("count", sqlTable.size());
			map.put("data", sqlTable);
		}catch (Exception ex) {
			LOG.error(ex.getMessage());
			throw new BaseException(ex.getMessage());
		}
		return map;
	}
	/**
	 * 某表信息与字段信息
	 * @param tableId
	 */
	@RequestMapping(value = {"/show/tableFieldList"}, method = RequestMethod.GET)
	@ResponseBody
	public SqlTable tableFieldList(String tableId){
		return sqlTableService.findByTableId(tableId);
	}

	/**
	 * 新建外键
	 * @return
	 * @throws BaseException 
	 */
	@RequestMapping(value = {"/createForeignkey"}, method = RequestMethod.POST)
    @ResponseBody
	public ResponseEntity<SqlTableForeignkey> createForeignkey(Model model,@RequestBody HashMap<String, Object> req) throws BaseException{
		SqlTableForeignkey sqlTableForeignkey = new SqlTableForeignkey();
		List<String> fields = new ArrayList<String>();
		fields.add("mainTable");
		fields.add("mainField");
		fields.add("joinTable");
		fields.add("joinField");
		validate(req, fields);
	    try {
	    	SqlTable mainSqlTable= sqlTableService.findByTableId(req.get("mainTable").toString());
	    	SqlTable negativeSqlTable= sqlTableService.findByTableId(req.get("joinTable").toString());
	    	req.put("mainSqlName", mainSqlTable.getTableSqlName());
	    	req.put("negativeSqName", negativeSqlTable.getTableSqlName());
	    	if(mainSqlTable !=null && negativeSqlTable != null ){
	    		sqlModelService.createForeignkey(req,mainSqlTable,sqlTableForeignkey);
	    	}
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new BaseException(ex.getMessage());
        }
        return new ResponseEntity<>(sqlTableForeignkey, HttpStatus.OK);

    }
	
	private void validate(HashMap<String, Object> req,List<String> fields) throws BaseException {
		for(String field:fields){
			if(validateStr(req.get(field))){
				throw new BaseException("req info is wrong");
			}
		}
	}
	public boolean validateStr(Object req){
		return null==req||"".equals((req+"").trim());
	}
	
	
	/**
	 * 外键删除接口
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = {"/deleteForeignkey"}, method = RequestMethod.GET)
    @ResponseBody
    @Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<Status> deleteForeignkey(Model model,String foreignKeyId) throws Exception{
		Status status = null;
	    try {
	    	status = sqlTableForeignkeyService.deleteSqlTableForeignkey(foreignKeyId);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new Exception(ex.getMessage());
        }
        return new ResponseEntity<>(status, HttpStatus.OK);

    }
	/**
	 * 外键全量查询接口
	 * @return
	 */
	@RequestMapping(value = {"/findSqlTableForeignkeyes"}, method = RequestMethod.GET)
	@ResponseBody
	public List<SqlTableForeignkey> findSqlTableForeignkeyes(){
		return sqlTableForeignkeyService.findSqlTableForeignkeyes();
	}
	
	
	/**
     * 数据库建模模块--根据数据源Id查出所有表类型
     * @param String dataResId
     */
	@RequestMapping(value = {"/findtablesbydataresid"}, method = RequestMethod.GET)
    @ResponseBody
    @Produces(MediaType.APPLICATION_JSON)
	public List<SqlTableType> findTableByDtatResId(String dataResId){
		List<SqlTableType> tableList = null;
//		LOG.info("----dataResId-----" + dataResId);
		try{
			tableList = sqlTableTypeService.selectTableTypes(dataResId);
		}catch (Exception ex) {
			LOG.error(ex.getMessage());
			ex.printStackTrace();
		}
	     return tableList;
	}

	/**
	 * 查询表类型下所有表
	 * @return
	 */
	@RequestMapping(value = {"/findSqlTableByTableTypeId"}, method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findSqlTableByTableTypeId(String tableTypeId){
		return sqlTableService.findSqlTableByTableTypeId(tableTypeId);
	}
	
	/**
     * 数据源下已经存在的表名称
     * @param String dataResId
     */
	@RequestMapping(value = {"/checkTableName"}, method = RequestMethod.GET)
    @ResponseBody
	public List<String> checkTableName(String dataResId){
		List<String> tableList = new ArrayList<String>();;
		try{
			List<SqlTable> tables = sqlTableService.findByTenantId(dataResId);
			if(tables != null){
				for (SqlTable sqlTable : tables) {
					tableList.add(sqlTable.getTableSqlName());
				}
			}
		}catch (Exception ex) {
			LOG.error(ex.getMessage());
			ex.printStackTrace();
		}
	     return tableList;
	}

	/**
	 * 更新坐标接口
	 * @param tableIdes
	 * @return
	 */
	@RequestMapping(value="/saveCoordinate",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Status> saveCoordinate(String tableIdes){
		Status status = null;
		if(StringUtils.isBlank(tableIdes)){
			return new ResponseEntity<>(status, HttpStatus.EXPECTATION_FAILED);
		}
		JSONArray myJsonArray = JSONArray.fromObject(tableIdes);
		List<Map<String,Object>> tableIdInfo = (List)myJsonArray;
		try{
			status = sqlTableService.saveCoordinate(tableIdInfo);
		}catch (Exception ex) {
            LOG.error(ex.getMessage());
			ex.printStackTrace();
		}
		return new ResponseEntity<>(status, HttpStatus.OK);
	}
	/**
     * 校验数据类型下类型的名称是否存在
     * @param String dataResId
     */
	@RequestMapping(value = {"/checkTypeName"}, method = RequestMethod.GET)
    @ResponseBody
	public List<String> checkTypeName(String parentId,String dataResId){
		List<String> tableList = new ArrayList<String>();
		try{
			tableList = sqlTableTypeService.findByParentId(parentId,dataResId);
		}catch (Exception ex) {
			LOG.error(ex.getMessage());
			ex.printStackTrace();
		}
	     return tableList;
	}
	/**
	 * 校验数据时间最大长度
	 * @param String fieldName
	 * @param String dataResId
	 * @param String tableName
	 */
	@RequestMapping(value = {"/checkLength"}, method = RequestMethod.POST)
	@ResponseBody
	public int checkLength(@RequestBody HashMap<String, Object> req){
		int length = 0;
		try{
			length = sqlTableTypeService.findByFieldName(req);
		}catch (Exception ex) {
			LOG.error(ex.getMessage());
			ex.printStackTrace();
		}
		return length;
	}
	
	/**
	 * 数据源刷新接口
	 * @param dataResId
	 * @return
	 */
	@RequestMapping(value = {"/refreshTable/{dataResId}"}, method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> refreshTable(@PathVariable("dataResId") String dataResId){
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			map = sqlDataResService.refreshTable(dataResId);
		}catch (Exception ex) {
			map.put("code", "111111");
			LOG.error(ex.getMessage());
			ex.printStackTrace();
		}
		return map;
	}
	/**
	 * 校验是否有用户在操作表页面
	 * @param dataResId
	 * @return
	 */
	@RequestMapping(value = {"/checkStatus"}, method = RequestMethod.POST)
	@ResponseBody
	public boolean checkStatus(String dataResId,String uuid){
		boolean status = false;
		try{
			status = sqlLockService.checkStatus(dataResId,uuid);
		}catch (Exception ex) {
			LOG.error(ex.getMessage());
			ex.printStackTrace();
		}
		return status;
	}
	/**
	 * 校验是已经打开表页面
	 * @param dataResId
	 * @return
	 */
	@RequestMapping(value = {"/clickStatus"}, method = RequestMethod.POST)
	@ResponseBody
	public boolean clickStatus(String dataResId,String uuid){
		boolean status = true;
		try{
			status = sqlLockService.clickStatus(dataResId,uuid);
		}catch (Exception ex) {
			LOG.error(ex.getMessage());
			ex.printStackTrace();
		}
		return status;
	}
	/**
	 * 更改操作状态接口
	 * @param sqlLock
	 * @return
	 */
	@RequestMapping(value = {"/changeStatus"}, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Status> changeStatus(@RequestParam(required = false) SqlLock sqlLock,HttpServletRequest request){
		Status status = null;
		try{
			if(sqlLock != null && !StringUtils.isBlank(sqlLock.getDataResId())){
				status = sqlLockService.changeStatus(sqlLock);
			}else{
				String sessionId=request.getSession().getId();
				status = sqlLockService.changeStatusUuid(sessionId);
			}
		}catch (Exception ex) {
			LOG.error(ex.getMessage());
			ex.printStackTrace();
		}
		return new ResponseEntity<>(status, HttpStatus.OK);
	}
	
	/**
	 * 移表接口
	 */
	@RequestMapping(value = {"/changeType/{tableId}"}, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Status> changeType(@PathVariable("tableId") String tableId,String tableTypeId){
		Status status = null;
		try{
			status = sqlTableService.changeType(tableId,tableTypeId);
		}catch (Exception ex) {
			LOG.error(ex.getMessage());
			ex.printStackTrace();
		}
		return new ResponseEntity<>(status, HttpStatus.OK);
	}
	
}
